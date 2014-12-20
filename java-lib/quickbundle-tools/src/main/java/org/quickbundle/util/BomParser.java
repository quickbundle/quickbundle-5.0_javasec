package org.quickbundle.util;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
  
public class BomParser {   
	private static final int BOM_SIZE = 4;
  
    public static void clearBomMark(File file) {   
        if (file.isDirectory()) {   
            for (File f : file.listFiles(new FileFilter() {
				public boolean accept(File pathname) {
					return pathname.isDirectory() || pathname.getName().matches(".*\\.java$");
				}
			})) {
            	clearBomMark(f);   
            }   
        } else {
        	replaceBom(file);
        }   
    }

	private static void replaceBom(File file) {
        FileInputStream fis = null;   
        InputStream is = null;
        PushbackInputStream pbis = null;
        ByteArrayOutputStream baos = null;   
        OutputStream out = null;   
        try {   
            fis = new FileInputStream(file);   
            is = new BufferedInputStream(fis);
            pbis = new PushbackInputStream(is, BOM_SIZE);
            baos = new ByteArrayOutputStream();   
           
            String encoding = null;
            byte bom[] = new byte[BOM_SIZE];
            int n, unread;
            n = pbis.read(bom, 0, bom.length);

            if ( (bom[0] == (byte)0x00) && (bom[1] == (byte)0x00) &&
                        (bom[2] == (byte)0xFE) && (bom[3] == (byte)0xFF) ) {
               encoding = "UTF-32BE";
               unread = n - 4;
            } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) &&
                        (bom[2] == (byte)0x00) && (bom[3] == (byte)0x00) ) {
               encoding = "UTF-32LE";
               unread = n - 4;
            } else if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB) &&
                  (bom[2] == (byte)0xBF) ) {
               encoding = "UTF-8";
               unread = n - 3;
            } else if ( (bom[0] == (byte)0xFE) && (bom[1] == (byte)0xFF) ) {
               encoding = "UTF-16BE";
               unread = n - 2;
            } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) ) {
               encoding = "UTF-16LE";
               unread = n - 2;
            } else {
               // Unicode BOM mark not found, unread all bytes
               unread = n;
            }    
            if (unread > 0) {
            	pbis.unread(bom, (n - unread), unread);
            }

            if (encoding != null) {
                System.out.println("clear " + file.getAbsolutePath());
                byte[] b = new byte[1024];
                int bytes;
                while ((bytes = pbis.read(b)) != -1) {   
                    baos.write(b, 0, bytes);
                }
                File newFile = new File(file.getAbsoluteFile() + ".bom.tmp");
                out = new FileOutputStream(newFile);
                baos.writeTo(out);
                baos.flush();
				try {
	                if (fis != null) {   
	                    fis.close();   
	                }   
	                if (is != null) {   
	                    is.close();   
	                }
	                if (pbis != null) {   
	                	pbis.close();   
	                }
	                if (out != null) {   
	                	out.close();   
	                }
	                if (baos != null) {   
	                    baos.close();   
	                }
				} catch (Exception e) {
					e.printStackTrace();
				}
                file.delete();
                newFile.renameTo(file.getAbsoluteFile());
            }   
        } catch (Exception e) {   
            e.printStackTrace();
        } finally {   
            try {
                if (fis != null) {   
                    fis.close();   
                }   
                if (is != null) {   
                    is.close();   
                }
                if (pbis != null) {   
                	pbis.close();   
                }
                if (out != null) {   
                	out.close();   
                }
                if (baos != null) {   
                    baos.close();   
                }  
            } catch (Exception e) {   
                e.printStackTrace();
            }   
        }   
	}
	
    
    public static void main(String[] args) {   
//        if (args.length < 2) {               
//        	return;   
//        }   
//        if (args[1].startsWith(".")) {   
//            args[1] = args[1].substring(1);   
//        }
        //File file = new File(args[0] + args[1]);   
    	File file= new File("E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src");
        System.out.println("java code：" + file.getAbsolutePath());   
        BomParser.clearBomMark(file);   
    }
}   