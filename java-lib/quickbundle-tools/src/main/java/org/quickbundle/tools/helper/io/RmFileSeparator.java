/*
 * 系统名称:QuickBundle --> cncpur
 * 
 * 文件名称: org.quickbundle.erpex.dec.support --> F.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2008-3-5 下午04:18:46 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.tools.helper.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.RandomAccessFile;

/**
 * 文件分隔器:给定文件的路径和每一块要拆分的大小，就可以按要求拆分文件
 * 如果指定的块给原文件都还要大，为了不动原文件，就生成另一个文件，以.bak为后缀，这样可以保证原文件
 * 如果是程序自动拆分为多个文件，那么后缀分别为".part序号"，这样就可以方便文件的合并了 原理：很简单，就是利用是输入输出流，加上随机文件读取。
 */
public class RmFileSeparator {
    
    public final static String STR_SEPARATE = "_separate";
    
    String FileName = null;// 原文件名

    long FileSize = 0;// 原文件的大小

    long BlockNum = 0;// 可分的块数

    public RmFileSeparator() {
    }

    /**
     * 
     * @param fileAndPath
     *            原文件名及路径
     */
    private void getFileAttribute(String fileAndPath)// 取得原文件的属性
    {
        File file = new File(fileAndPath);
        FileName = file.getName();
        FileSize = file.length();
    }

    /**
     * 
     * @param blockSize
     *            每一块的大小
     * @return 能够分得的块数
     */
    private long getBlockNum(long blockSize)// 取得分块数
    {
        long fileSize = FileSize;
        if (fileSize <= blockSize)// 如果分块的小小只够分一个块
            return 1;
        else {
            if (fileSize % blockSize > 0) {
                return fileSize / blockSize + 1;
            } else
                return fileSize / blockSize;
        }
    }

    /**
     * 
     * @param fileAndPath
     *            原文件及完整路径
     * @param currentBlock
     *            当前块的序号
     * @return 现在拆分后块的文件名
     */
    private String generateSeparatorFileName(String fileAndPath, int currentBlock)// 生成折分后的文件名，以便于将来合将
    {
        return fileAndPath + ".part" + currentBlock;
    }

    /**
     * 
     * @param fileAndPath
     *            原文件及完整路径
     * @param fileSeparateName
     *            文件分隔后要生成的文件名，与原文件在同一个目录下
     * @param blockSize
     *            当前块要写的字节数
     * @param beginPos
     *            从原文件的什么地方开始读取
     * @return true为写入成功，false为写入失败
     */
    private boolean writeFile(String fileAndPath, String fileSeparateName, long blockSize, long beginPos)// 往硬盘写文件
    {

        RandomAccessFile raf = null;
        FileOutputStream fos = null;
        byte[] bt = new byte[1024];
        long writeByte = 0;
        int len = 0;
        try {
            raf = new RandomAccessFile(fileAndPath, "r");
            raf.seek(beginPos);
            fos = new FileOutputStream(fileSeparateName);
            while ((len = raf.read(bt)) > 0) {
                if (writeByte < blockSize)// 如果当前块还没有写满
                {
                    writeByte = writeByte + len;
                    if (writeByte <= blockSize)
                        fos.write(bt, 0, len);
                    else {
                        len = len - (int) (writeByte - blockSize);
                        fos.write(bt, 0, len);
                    }
                }
            }
            fos.close();
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (fos != null)
                    fos.close();
                if (raf != null)
                    raf.close();
            } catch (Exception f) {
                f.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * 
     * @param fileAndPath
     *            原文路径及文件名
     * @param blockSize
     *            要拆分的每一块的大小
     * @return true为拆分成功，false为拆分失败
     */
    private boolean separatorFile(String fileAndPath, long blockSize, String newFolder)// 折分文件主函数
    {
        getFileAttribute(fileAndPath);// 将文件的名及大小属性取出来
        // System.out.println("FileSize:"+FileSize);
        // System.out.println("blockSize:"+blockSize);
        BlockNum = getBlockNum(blockSize);// 取得分块总数
        // System.out.println("BlockNum:"+BlockNum);
        // System.exit(0);
        if (BlockNum == 1)// 如果只能够分一块，就一次性写入
            blockSize = FileSize;
        long writeSize = 0;// 每次写入的字节
        long writeTotal = 0;// 已经写了的字节
        String FileCurrentNameAndPath = null;
        for (int i = 1; i <= BlockNum; i++) {
            if (i < BlockNum)
                writeSize = blockSize;// 取得每一次要写入的文件大小
            else
                writeSize = FileSize - writeTotal;
            if (BlockNum == 1) {
                FileCurrentNameAndPath = fileAndPath + ".bak";                
            } else {
                if(newFolder == null || newFolder.length() == 0) {
                    newFolder = fileAndPath;
                }
                FileCurrentNameAndPath = generateSeparatorFileName(newFolder, i);                
            }
            // System.out.print("本次写入:"+writeSize);
            if (!writeFile(fileAndPath, FileCurrentNameAndPath, writeSize, writeTotal))// 循环往硬盘写文件
                return false;
            writeTotal = writeTotal + writeSize;
            // System.out.println(" 总共写入:"+writeTotal);
        }
        return true;
    }
    
    public static File separate(String file, long blockSize) {
        RmFileSeparator separator = new RmFileSeparator();
        File newFolder = new File(file + STR_SEPARATE + File.separator + new File(file).getName());
        newFolder.getParentFile().mkdirs();
        if(separator.separatorFile(file, blockSize, newFolder.toString())) {
            return new File(newFolder.toString()).getParentFile();
        } else {
            return null;
        }
    }
    
    public static void main(String[] args) {
    	try {
    		File f1 = new File("D:/dev/mysql/sql/yidaba_sicms.sql");
    		BufferedReader br = new BufferedReader(new FileReader(f1));
    		
    		File f2 = new File("D:/dev/mysql/sql/yidaba_sicms2.sql"); 
    		BufferedWriter bw = new BufferedWriter(new FileWriter(f2));
    		String s = null;
    		while((s = br.readLine()) != null) {
    			s = s.replaceAll("GBK", "utf8");
    			s = s.replaceAll("gb2312", "utf8");
    			bw.write(s);
    			bw.write("\n");
    		}
    		bw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
	}

}
