/*
 * 功能描述:
 * 
 * 版本历史: 2005-11-19 19:16:49 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.mda.mvm;

import java.io.File;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.quickbundle.mda.RmTransform;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.io.RmFileHelper;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

/**
 * @author 白小勇
 */
public class CodegenEngine {
	
    public final static String FILE_CONCAT = "/";

    /**
     * mainRule 表示: 规则Document
     */
    private Document mainRule = null;

    /**
     * baseXsltSourcePath 表示: xslt文件的路径, 一般在插件安装目录下
     */
    private String templatePath = null;

    /**
     * baseProjectPath 表示: 生成的目标项目路径,可能在任何目录
     */
    private String baseProjectPath = null;

    /**
     * quickbundleHome 表示: 可能随时改变的rule.xml路径,一般在临时目录下
     */
    private String quickbundleHome = null;
    
    private Document mvmDoc = null;
    
    /**
     * 构造函数:
     * 
     * @param ruleXml
     * @param codegenConfig
     */
    public CodegenEngine(String ruleXml) {
        try {
            this.quickbundleHome = new File(RmXmlHelper.formatToFile(ruleXml)).getParent();
            this.mainRule = RmXmlHelper.parse(ruleXml);
            //初始化java路径
            this.templatePath = RmXmlHelper.formatToUrl(FileLocator.toFileURL(this.getClass().getClassLoader().getResource("template")).toString());
            this.baseProjectPath = RmXmlHelper.formatToUrl(mainRule.getRootElement().valueOf("//rules/codegen//@baseProjectPath"));
            String codegenConfig = mainRule.valueOf("/rules/codegen/mvms/mvm[contextName=../@contextName]/@codegenConfig");
            if(codegenConfig == null || codegenConfig.length() == 0) {
            	codegenConfig = mainRule.valueOf("/rules/codegen/mvms/mvm[1]/codegenConfig");
            }
            this.mvmDoc = RmXmlHelper.parse(this.templatePath + "/" + codegenConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private String getFilterTableName(String currentTableXmlPath) {
        String filterTableName = "";
        currentTableXmlPath = RmXmlHelper.formatToUrl(currentTableXmlPath);
        try {
        	String finalTemplatePath = templatePath + "buildFilterTableName.xsl";
        	String result = RmTransform.getStringFromTransform(finalTemplatePath, currentTableXmlPath);
            Document tempResultsDoc = new SAXReader().read(new StringReader(result));
            filterTableName = tempResultsDoc.valueOf("/results/result[position()=1]/@tableFormatNameUpperFirst");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return filterTableName;
    }

    @SuppressWarnings("unchecked")
	public Object[] generateFiles(IProgressMonitor monitor) throws DocumentException, MalformedURLException {
        Object[] aObj = new Object[2];
        int index = 0; //统计文件数
        StringBuilder returnLog = new StringBuilder();
        List<Element> lTableXmls = mainRule.selectNodes("/rules/database/tableTos/tableTo");
        if(monitor != null) {
            monitor.beginTask("begin generate code......", mvmDoc.selectNodes(".//file").size() * lTableXmls.size());
        }
        for (Element thisTableTo : lTableXmls) {
        	int result = doGenerate(monitor, thisTableTo, returnLog);
        	index += result;
        }
        log(returnLog.toString());
        aObj[0] = String.valueOf(index);
        aObj[1] = returnLog;
        return aObj;
    }
    
    @SuppressWarnings("unchecked")
	private int doGenerate(IProgressMonitor monitor, Element thisTableTo, StringBuilder returnLog) throws MalformedURLException, DocumentException {
    	int result = 0;
    	String toTableNameKeyword = mainRule.valueOf("/rules/codegen/@toTableNameKeyword");
        String originalTableName = thisTableTo.getText();
        String currentTableXmlPath = RmXmlHelper.formatToUrl(quickbundleHome + FILE_CONCAT + thisTableTo.valueOf("@xmlName"));
        Document docCurrentTable = RmXmlHelper.parse(currentTableXmlPath);
        String filterTableName = getFilterTableName(currentTableXmlPath);
        String tableDirName = docCurrentTable.valueOf("/meta/tables/table[1]/@tableDirName");
        List<Element> lFile = mvmDoc.selectNodes(".//file");
        for (Element eleFile : lFile) {
            //取出当前rule的组件编码
            String bundleCode = eleFile.valueOf("@bundleCode");
            if(bundleCode != null && bundleCode.length() > 0 && docCurrentTable != null) {
            	String customBundleCode = docCurrentTable.valueOf("/meta/tables/table[@tableName='" + originalTableName + "']/@customBundleCode");
            	//如果定制编码不包含当前rule的组件编码，跳过
            	if(!customBundleCode.matches("^[\\w,]*" + bundleCode + "[\\w,]*$")) {
            		continue;
            	}
            }
            //得到当前这组的基本路径
            String outPutPath = getOutPutPath(eleFile);
            //得到最终路径
            String xsltPath = templatePath + eleFile.valueOf("@xsltPath");
            String outputFile = eleFile.valueOf("@outputFile");
            outputFile = fillUpOutput(outputFile, toTableNameKeyword, eleFile, filterTableName, tableDirName, outPutPath);
            String afterKeyWord = eleFile.valueOf("@afterKeyWord");
            if (afterKeyWord.length() == 0) { //java和jsp文件
            	if("true".equals(eleFile.valueOf("@result-document"))) {
            		String outputFolder = eleFile.valueOf("@outputFolder");
            		outputFolder = fillUpOutput(outputFolder, toTableNameKeyword, eleFile, filterTableName, tableDirName, outPutPath);
            		if("".equals(eleFile.valueOf("@outputFile"))) {
            			TemplateHelper.outPutFile4ResultDocument(xsltPath, currentTableXmlPath, outputFolder);
            		} else {
            			if("".equals(eleFile.valueOf("@outputFolder"))) {
            				outputFolder = new File(RmFileHelper.formatToFile(outputFile)).getParent();
            			}
            			TemplateHelper.outPutFile4ResultDocument(xsltPath, currentTableXmlPath, outputFolder, outputFile);
            		}
            	} else {
            		TemplateHelper.outPutFile(xsltPath, currentTableXmlPath, outputFile);
            	}
            } else { //配置文件
            	TemplateHelper.outPutFile(xsltPath, currentTableXmlPath, outputFile, afterKeyWord, "true".equals(eleFile.valueOf("@rowIsUnique")));                        
            }
            returnLog.append("\r\nxslt = ")
            	.append(RmFileHelper.formatToFile(xsltPath))
            	.append("\r\nmetaXml = ")
            	.append(RmFileHelper.formatToFile(currentTableXmlPath))
            	.append("\r\noutput=")
            	.append(RmFileHelper.formatToFile(outputFile));
            if(afterKeyWord != null && afterKeyWord.length() > 0) {
            	returnLog.append("\r\nposition = ")
            	.append(afterKeyWord);
            }
            returnLog.append("\r\n");
            result++;
            if(monitor != null) {
                monitor.worked(1);
                String displayOutputFile = RmXmlHelper.formatToUrlNoPrefix(outputFile);
                String tempStr = displayOutputFile;
                if(displayOutputFile.length() > 140) {
                    tempStr = displayOutputFile.substring(0,12) + "..." + displayOutputFile.substring(displayOutputFile.length()-125);
                }
                monitor.setTaskName(tempStr);
            }
        }
        return result;
    }
    
    private String fillUpOutput(String output, String toTableNameKeyword, Element eleFile, String filterTableName, String tableDirName, String baseTargetPath) {
    	StringBuilder result = new StringBuilder();
        if (toTableNameKeyword != null && toTableNameKeyword.length() > 0) { //把TableName替换成表名
            output = RmStringHelper.replaceFirst(output, toTableNameKeyword, filterTableName);
        }
        if ("java".equals(eleFile.valueOf("../@filesType")) || "jsp".equals(eleFile.valueOf("../@filesType"))) {
            output = tableDirName + FILE_CONCAT + output;
        } else if ("config".equals(eleFile.valueOf("../@filesType"))) {

        }
        result.append(baseProjectPath)
        	.append(FILE_CONCAT)
        	.append(baseTargetPath)
        	.append(FILE_CONCAT)
        	.append(output);
        return result.toString();
    }
    
    private String getOutPutPath(Element eleFile) {
    	String outputPath = eleFile.valueOf("../@outputPath");
		if(outputPath == null || outputPath.trim().length() == 0) {
			return "";
		}
		StringBuffer result = new StringBuffer();
		Pattern pData = Pattern.compile("\\$\\{(.*?)\\}");
		Matcher mData = pData.matcher(outputPath);
		while(mData.find()) {
			//在循环中找出{}的表达式
			String exp = mData.toMatchResult().group(1);
			//处理表达式，添加到结果
			mData.appendReplacement(result, mainRule.valueOf("/rules/project/" + exp));
		}
		mData.appendTail(result);
		return result.toString();
    }
    
    /**
     * 功能: 写入日志
     * 
     * @param traceMessage
     */
    public void log(String traceMessage) {
        System.out.println("\n" + new Timestamp(System.currentTimeMillis()) + ": " + traceMessage);
    }

    /**
     * 功能: java -jar ranminXmlGenerateCode.jar C:\Docume~1\Administrator\LocalS~1\Temp\ranminXmlGenerateCode\rule***.xml
     *
     * @param args
     */
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        String rulesXmlPath = null;
        String baseXsltSourcePath = null;
        if (args != null && args.length >= 1) {
            rulesXmlPath = args[0];
            File tempFile = new File(rulesXmlPath);
            baseXsltSourcePath = tempFile.getParent() + "\\";
            System.out.println(baseXsltSourcePath);
        } else {
            System.out.println("usage: java -jar ranminXmlGenerateCode.jar rulesXmlPath!");
            return;
        }
        try {
            CodegenEngine ce = new CodegenEngine(rulesXmlPath);
            Object[] rtObj = ce.generateFiles(null);
            System.out.println("一共生成了" + String.valueOf(rtObj[0]) + "个文件！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        t = System.currentTimeMillis() - t;
        System.out.println("耗时" + t / 1000 + "秒！");
    }
}