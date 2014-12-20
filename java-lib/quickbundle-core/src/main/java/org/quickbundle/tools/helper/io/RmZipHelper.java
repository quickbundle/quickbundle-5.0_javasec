/*
 * 系统名称:QuickBundle --> cncpur
 * 
 * 文件名称: org.quickbundle.tools.support.encrypt --> RmZipHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2007-12-7 下午01:55:13 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.tools.helper.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipFile;
import org.quickbundle.tools.helper.RmStringHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmZipHelper {
	public final static String ZIP_ENCODING = "GBK";
    public final static int MAX_ZIP_SIZE = 10 * 1024 * 1024;
    public final static int MAX_TEMPLATE_SIZE = 200 * 1024;
    private final static int BUFFER_SIZE = 16384;
    
    public static File createZipFile(File[] files) {
    	return createZipFile(files, new File(new File(System.getProperty("java.io.tmpdir")) + File.separator + "zip" + File.separator + UUID.randomUUID() + ".zip"));
    }
    
    public static File createZipFile(File[] files, File target) {
    	try {
    		target.getParentFile().mkdirs();
    		org.apache.tools.zip.ZipOutputStream zos = new org.apache.tools.zip.ZipOutputStream(new FileOutputStream(target));
    		zos.setEncoding(ZIP_ENCODING);
    		for (int i = 0; i < files.length; i++) {
    			fileZip(zos, files[i], "");
    		}
    		zos.close();
    	} catch (Exception e) {
    		throw new RuntimeException("Failed to process ZIP file.", e);
    	}
    	return target;
    }
    
    //实现zip文件加入ZipOutputStream
    private static void fileZip(org.apache.tools.zip.ZipOutputStream zos, File f, String base) throws Exception {
        // 如果传入的是目录
        if (f.isDirectory()) {
            File[] fl = f.listFiles();

            if (base == null) {
                base = "";
            } else if (base.length() == 0) {
                base = f.getName() + "/";
            } else {
                base = base + f.getName() + "/";
            }
            for (int i = 0; i < fl.length; i++) {
                fileZip(zos, fl[i], base);
            }
        } else if (f.isFile()) {
            zos.putNextEntry(new org.apache.tools.zip.ZipEntry(base + f.getName()));
            FileInputStream in = new FileInputStream(f);
            byte[] bb = new byte[2048];
            int aa = 0;
            while ((aa = in.read(bb)) != -1) {
                zos.write(bb, 0, aa);
            }
            in.close();
        }
    }
    
    /**
     * 解压缩文件zipFile保存在directory目录下
     * 
     * @param directory
     * @param zipFile
     */
    public static void unZip(String zipFile, String directory) {
    	try {
			extractFile(new ZipFile(zipFile, ZIP_ENCODING), directory);
		} catch (IOException e) {
			throw new RuntimeException("Failed to process ZIP file.", e);
		}
    }
    
    /**
     * @deprecated sun默认的ZIP有乱码
     * 解压缩文件
     * 
     * @param zis
     * @param file
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws Exception
     */
    private static void fileUnZip (ZipInputStream zis, File file) throws FileNotFoundException, IOException {
        java.util.zip.ZipEntry zip = null;
        while((zip = zis.getNextEntry()) != null) {
        	String name = zip.getName();
        	File f=  new File(file.getAbsolutePath() + File.separator + name);
        	if(zip.isDirectory()) {
        		f.mkdirs();
        	} else {
        		f.getParentFile().mkdirs();
                f.createNewFile();
                BufferedOutputStream bos = null;
                try {
                	bos = new BufferedOutputStream(new FileOutputStream(f));
                	byte b[] = new byte[2048];
                	int aa = 0;
                	while ((aa = zis.read(b)) != -1) {
                		bos.write(b, 0, aa);
                	}
                	bos.flush();
				} finally {
					bos.close();
				}
                bos.close();
        	}
        }
        
    }

    /**
     * @deprecated 递归效率低
     * 解压缩文件
     * 
     * @param zis
     * @param file
     * @throws Exception
     */
    private static void fileUnZip_recursive (ZipInputStream zis, File file) throws Exception {
        java.util.zip.ZipEntry zip = zis.getNextEntry();
        if (zip == null)
            return;

        String name = zip.getName();
        File f = new File(file.getAbsolutePath() + "/" + name);
        if (zip.isDirectory()) {
            f.mkdirs();
            fileUnZip_recursive(zis, file);
        } else {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (Exception e) {
                System.out.println(RmStringHelper.testAllEncode(f.toString()));
                e.printStackTrace();
            }
            FileOutputStream fos = new FileOutputStream(f);
            byte b[] = new byte[2048];
            int aa = 0;
            while ((aa = zis.read(b)) != -1) {
                fos.write(b, 0, aa);
            }
            fos.close();
            fileUnZip_recursive(zis, file);
        }
    }
    
	/**
	 * Extract the file and folder structure of a ZIP file into the specified directory
	 * 
	 * @param archive       The ZIP archive to extract
	 * @param extractDir    The directory to extract into
	 */
	public static void extractFile(ZipFile archive, String extractDir)
	{
	    String fileName;
	    String destFileName;
	    byte[] buffer = new byte[BUFFER_SIZE];
	    extractDir = extractDir + File.separator;
	    try
	    {
	        for (Enumeration e = archive.getEntries(); e.hasMoreElements();)
	        {
	            org.apache.tools.zip.ZipEntry entry = (org.apache.tools.zip.ZipEntry) e.nextElement();
	            if (!entry.isDirectory())
	            {
	                fileName = entry.getName();
	                fileName = fileName.replace('/', File.separatorChar);
	                destFileName = extractDir + fileName;
	                File destFile = new File(destFileName);
	                String parent = destFile.getParent();
	                if (parent != null)
	                {
	                    File parentFile = new File(parent);
	                    if (!parentFile.exists()) parentFile.mkdirs();
	                }
	                InputStream in = new BufferedInputStream(archive.getInputStream(entry), BUFFER_SIZE);
	                OutputStream out = new BufferedOutputStream(new FileOutputStream(destFileName), BUFFER_SIZE);
	                int count;
	                while ((count = in.read(buffer)) != -1)
	                {
	                    out.write(buffer, 0, count);
	                }
	                in.close();
	                out.close();
	            }
	            else
	            {
	                File newdir = new File(extractDir + entry.getName());
	                newdir.mkdirs();
	            }
	        }
	    }
	    catch (Exception e)
	    {
	        throw new RuntimeException("Failed to process ZIP file.", e);
	    }
	}

    /**
     * 功能: 从上传的文件中获取xls
     *
     * @param zipFile
     * @return
     */
    public static File getExcelFromFile(File zipFile) {
        if(!zipFile.exists()) {
            return null;
        } else if(zipFile.toString().endsWith(".xls")) {
            return zipFile;
        } else if(zipFile.toString().endsWith(".zip")){
            //解压缩
            File unzipFolder = new File(System.getProperty("java.io.tmpdir") + File.separator + "rm" +  File.separator +  zipFile.getName() + "_unzip");
            RmZipHelper.unZip(zipFile.toString(), unzipFolder.toString());
            File[] aUnzipFile = unzipFolder.listFiles();
            for (int i = 0; i < aUnzipFile.length; i++) {
                if(aUnzipFile[i].toString().endsWith(".xls") && aUnzipFile[i].isFile()) {
                    return aUnzipFile[i];
                }
            }
            return null;
        } else {
            throw new RuntimeException("文件不是.xls或.zip格式");
        }
    }
    
    public static void main(String args[]) throws Exception {
    	String f = "E:/download/temp";
//        RmZipHelper.createZipFile(new File[] { new File("/home/qb/download/hi") });
        //RmZipHelper.unZip( "E:/platform/myProject/qbrm/code/quickbundle-securityweb/WebContent/WEB-INF/archive/perl/perl.zip", f);
//        File file = new File(f);
//        System.out.println(RmStringHelper.fileToString(file));
    	RmZipHelper.unZip("E:/download/a.zip", f);
//    	extractFile(new ZipFile(new File("C:/Users/qb/Pictures/pic.zip"), "GBK"), f);
//        createZipFile(new File[]{new File("E:/download/pic")}, new File("E:/download/a.zip"));
    }

}
