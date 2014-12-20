package org.quickbundle.mda.mvm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.TransformerException;

import org.quickbundle.mda.RmTransform;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

public class TemplateHelper {

    public static String getJavaFileDescComment(String outputFile) {
        StringBuilder result = new StringBuilder();
        result.append("//代码生成时,文件路径: ")
        	.append(RmXmlHelper.formatToUrlNoPrefix(outputFile))
        	.append("\n")
        	.append("//代码生成时,系统时间:")
        	.append(getSysDateTime())
        	.append(", 操作系统用户:")
        	.append(System.getProperty("user.name"))
        	.append("\n\n");
        return result.toString();
    }

    private static String getSysDateTime() {
    	return new Timestamp(System.currentTimeMillis()).toString().substring(0,19);
    }
    
    /**
     * 功能:输出转化文件
     * 
     * @param xsltPath
     * @param myTableDoc
     * @param outputFile
     * @throws TransformerException
     */
    public static void outPutFile(String xsltPath, String myTableXml, String outputFile) {
        outputFile = RmXmlHelper.formatToFile(outputFile);
        RmFileHelper.initParentDir(outputFile); //创建父目录
        try {
            String context = RmTransform.getStringFromTransform(xsltPath, myTableXml);
            if (outputFile.endsWith(".java")) {
                context = getJavaFileDescComment(outputFile) + context;
            }
            RmFileHelper.saveFile(context, outputFile);
        } catch (Exception e) {
        	EclipseLog.logError("xslt=" + xsltPath + ", metaXml=" + myTableXml + "," + e.toString(), e);
            e.printStackTrace();
        }
    }
    
    /**
     * 功能:转化文件，模板有初始化参数
     * 
     * @param xsltPath
     * @param myTableXml
     * @param outputFile
     * @param mAttribute
     */
    public static void outPutFile4ResultDocument(String xsltPath, String myTableXml, String outputFolder) {
    	outPutFile4ResultDocument(xsltPath, myTableXml, outputFolder, null);
    }
    /**
     * 功能:转化文件，模板有初始化参数
     * 
     * @param xsltPath
     * @param myTableXml
     * @param outputFile
     * @param mAttribute
     */
    public static void outPutFile4ResultDocument(String xsltPath, String myTableXml, String outputFolder, String outputFile) {
    	RmFileHelper.initSelfDir(RmXmlHelper.formatToFile(outputFolder));
    	if(outputFile != null && outputFile.length() > 0) {
    		outputFile = RmXmlHelper.formatToFile(outputFile);
    		RmFileHelper.initParentDir(outputFile); //创建目录
    	}
    	Map<String, Object> mAttribute = new HashMap<String, Object>();
    	mAttribute.put("targetFullPath", RmXmlHelper.formatToUrl(outputFolder));
    	try {
    		String context = RmTransform.getStringFromTransform(xsltPath, myTableXml, mAttribute);
    		if(outputFile != null) {
    			if (outputFile.endsWith(".java")) {
    				context = getJavaFileDescComment(outputFile) + context;
    			}
    			RmFileHelper.saveFile(context, outputFile);
    		}
    	} catch (Exception e) {
    		EclipseLog.logError("xslt=" + xsltPath + ", metaXml=" + myTableXml + "," + e.toString(), e);
    		e.printStackTrace();
    	}
    	{//如果目录为空则删除
    		removeFolderIfEmpty(outputFolder);
    		if(outputFile != null && outputFile.length() > 0) {
    			removeParentFolderIfEmpty(outputFile);
    		}
    	}
    }
    
    static void removeFolderIfEmpty(String folder) {
    	File fTargetFolder = new File(RmXmlHelper.formatToFile(folder));
    	if(fTargetFolder.isDirectory() && fTargetFolder.list().length == 0) {
    		fTargetFolder.delete();
    	}
    }
    static void removeParentFolderIfEmpty(String file) {
    	File fTargetFolder = new File(RmXmlHelper.formatToFile(file)).getParentFile();
    	if(fTargetFolder.isDirectory() && fTargetFolder.list().length == 0) {
    		fTargetFolder.delete();
    	}
    }

    /**
     * 功能:输出转化后的字符到指定文件
     * 
     * @param xsltPath
     * @param myTableDoc
     * @param outputFile
     * @param afterKeyWord
     */
    public static void outPutFile(String xsltPath, String myTableXml, String outputFile, String afterKeyWord, boolean rowIsUnique) {
        outputFile = RmXmlHelper.formatToUrl(outputFile);
        RmFileHelper.initParentDir(outputFile); //创建父目录
        String context = "";
        try {
        	context = RmTransform.getStringFromTransform(xsltPath, myTableXml);
		} catch (Exception e) {
			EclipseLog.logError("xslt=" + xsltPath + ", metaXml=" + myTableXml + "," + e.toString(), e);
			e.printStackTrace();
		}
        
        writeToRandomFile(outputFile, context, afterKeyWord, rowIsUnique);
    }

    /**
     * 功能: 随机访问文件tartetPath,把context插到afterKeyWord后边
     * 
     * @param outputFile
     * @param context
     * @param afterKeyWord
     */
    public static void writeToRandomFile(String outputFile, String content, String afterKeyWord, boolean rowIsUnique) {
        BufferedReader in = null;
        RandomAccessFile rf = null;
        outputFile = RmXmlHelper.formatToFile(outputFile);
        StringBuffer outputFileStr = new StringBuffer();
        try {
            if (new File(outputFile).exists()) { //检查是否已经存在相同代码
            	in = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile), "UTF-8"));
                String s1 = null;
                while ((s1 = in.readLine()) != null) {
                    outputFileStr.append(s1 + "\n");
                }
                if (outputFileStr.toString().indexOf(content.trim()) >= 0) { //已经存在 （截掉制表符，减少重复写入文件的可能）
                    return;
                }
            }
            if(rowIsUnique) { //如果每一行数据不能重复
                Set<String> sOutputFileRow = new HashSet<String>();
                String[] aOutputFileRow = outputFileStr.toString().split("\n");
                for (int i = 0; i < aOutputFileRow.length; i++) {
                    if(aOutputFileRow[i].trim().length() > 0) {
                    	sOutputFileRow.add(aOutputFileRow[i].trim());
                    }
                }
                outputFileStr = null;
                //开始找位置
                String line = null;
                long position = 0;
                rf = new RandomAccessFile(outputFile, "rw");
                while (true) {
                    line = rf.readLine();
                    if (line != null) {
                        if (line.trim().equals(afterKeyWord)) {
                            position = rf.getFilePointer();
                            break;
                        }
                    } else {
                        position = rf.getFilePointer();
                        break;
                    }
                }
                StringBuffer originRemain = new StringBuffer();
                while ((line = rf.readLine()) != null) {
                    originRemain.append(line + "\n");
                }
                rf.seek(position);
                //开始写文件
                String[] aContentRow = content.split("\n");
                for (int i = 0; i < aContentRow.length; i++) {
                    String tempContentRow = null;
                    if(sOutputFileRow.contains(aContentRow[i].trim())) { //如果出现重复行
                        if(outputFile.endsWith("xml")) {
                            tempContentRow = "<!--" + aContentRow[i] + "-->";
                        } else if(outputFile.endsWith("java")) {
                            tempContentRow = "//" + aContentRow[i];
                        } else if(outputFile.endsWith("properties")) {
                            tempContentRow = "#" + aContentRow[i];
                        }
                    } else {
                        tempContentRow = aContentRow[i];
                    }
                    tempContentRow += "\n";
                    rf.write(tempContentRow.getBytes("UTF-8"));
                }
                rf.writeBytes(originRemain.toString());
            } else { //每一行数据可以重复
                //开始找位置
                String line = null;
                long position = 0;
                rf = new RandomAccessFile(outputFile, "rw");
                while (true) {
                    line = rf.readLine();
                    if (line != null) {
                        if (line.trim().equals(afterKeyWord)) {
                            position = rf.getFilePointer();
                            break;
                        }
                    } else {
                        position = rf.getFilePointer();
                        break;
                    }
                }
                StringBuffer originRemain = new StringBuffer();
                while ((line = rf.readLine()) != null) {
                    originRemain.append(line + "\n");
                }
                rf.seek(position);
                //开始写文件
                rf.write(content.getBytes("UTF-8"));
                //rf.write(new String(originRemain.getBytes("iso8859-1"),"UTF-8").getBytes("UTF-8"));
                rf.writeBytes(originRemain.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(in != null) {
                    in.close();
                }
                if (rf != null) {
                    rf.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

}
