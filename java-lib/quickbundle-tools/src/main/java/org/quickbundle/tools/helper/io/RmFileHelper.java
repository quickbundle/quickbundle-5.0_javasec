package org.quickbundle.tools.helper.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.util.RmString;

public class RmFileHelper {
	
	/**
	 * @param fileList
	 * @return
	 */
	public static List<String> getFiles(String fileList) {
		List<String> result = new ArrayList<String>();
		String[] files = fileList.split(",");
		for(String f : files) {
			f = f.trim();
			if(f.length() == 0) {
				continue;
			}
			if(f.indexOf("*") > -1) {
				f = formatToFile(f).replaceAll("[/\\\\]+", "/");
				String beforeAndStar = f.substring(0, f.indexOf("*")+1);
				String afterStar = f.substring(f.indexOf("*")+1);
				Pattern pParent = Pattern.compile("^(.*[/])([^/]+?)$");
				Matcher mt = pParent.matcher(beforeAndStar);
				if(mt.find()) {
					String parent = mt.group(1);
					String abstractFile = mt.group(2) + afterStar;
					String newFilePattern = abstractFile.replaceAll("([.-])", "\\\\$1");
					newFilePattern = newFilePattern.replaceAll("\\*", ".*");
					
					final String fNewFilePattern = newFilePattern;
					final File fParent = new File(parent);
					if(!fParent.exists()) {
						continue;
					}
					final String formatFParent = fParent.getAbsolutePath().replaceAll("[/\\\\]+", "/");
					List<File> fFinals = recursiveGetFile(fParent, new FileFilter() {
						public boolean accept(File pathname) {
							String formatF = pathname.getAbsolutePath().replaceAll("[/\\\\]+", "/");
							String tail = formatF.substring(formatFParent.length());
							return Pattern.matches(fNewFilePattern, tail);
						}
					});
					for(File fFinal : fFinals) {
						if(!result.contains(fFinal.getAbsolutePath())) {
							result.add(fFinal.getAbsolutePath());
						}
					}
				}
			} else {
				if(!result.contains(f)) {
					result.add(f);
				}
			}
		}		
		return result;
	}

	/**
	 * 递归获得文件
	 * 
	 * @param f
	 * @param ff
	 * @return
	 */
	static List<File> recursiveGetFile(File f, FileFilter ff) {
		List<File> lf = new ArrayList<File>();
		if (f.isFile()) {
			if (ff.accept(f)) {
				lf.add(f);
			}
		} else {
			File[] fs = f.listFiles();
			for (File tempF : fs) {
				lf.addAll(recursiveGetFile(tempF, ff));
			}
		}
		return lf;
	}
	
    /**
     * 功能: 将路径格式化为url --> file:///c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrl(String filePath) {
    	if(filePath == null) {
    		return null;
    	}
        filePath = filePath.replaceFirst("file:[/\\\\]*", "");
        filePath = filePath.replaceAll("[/\\\\]+", "/");
        if (filePath.startsWith("/")) {
            filePath = "file://" + filePath;        	
        } else {
        	filePath = "file:///" + filePath;        	
        }
        return filePath;
    }
    
    /**
     * 功能: 将路径格式化为url --> c:/rmdemo.log
     * 
     * @param filePath
     * @return
     */
    public static String formatToUrlNoPrefix(String filePath) {
    	if(filePath == null) {
    		return null;
    	}
    	filePath = formatToUrl(filePath);
    	if(File.separator.endsWith("/")) {
    		filePath = filePath.substring("file://".length());
    	} else {
    		filePath = filePath.substring("file:///".length());
    	}
    	return filePath;
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     * 
     * @param filePath
     * @param osSeparatorStr 指定当前操作系统分隔符
     * @return
     */
    public static String formatToFile(String filePath, String osSeparatorStr) {
    	if(filePath == null) {
    		return null;
    	}
        String osSeparatorRegex = ("\\".equals(osSeparatorStr)) ? "\\\\" : osSeparatorStr;
        filePath = filePath.replaceFirst("file:[/\\\\]*", "");
        filePath = filePath.replaceAll("[/\\\\]+", osSeparatorRegex);
        //自动补齐Linux下的/
        if("/".equals(osSeparatorStr) && !filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }
        return filePath;
    }
    
    /**
     * 功能: 将路径格式化为File形式 --> c:\rmdemo.log
     *
     * @param filePString
     * @return
     */
    public static String formatToFile(String filePString) {
        return formatToFile(filePString, File.separator);
    }

    /**
     * 显示数据前过滤掉null
     * 
     * @param myString
     * @return
     */
    public static String prt(String myString) {
        if (myString != null)
            return myString;
        else
            return "";
    }

    /**
     * 功能: 拷贝文件
     * 
     * @param sourceFile
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static boolean copyFile_reader(File sourceFile, File targetFile) throws Exception {
        BufferedReader in1 = null;
        PrintWriter out1 = null;
        initParentDir(targetFile.getPath());
        try {
            in1 = new BufferedReader(new FileReader(sourceFile));
            out1 = new PrintWriter(new BufferedWriter(new FileWriter(targetFile)));
            String s = null;
            while ((s = in1.readLine()) != null) {
                out1.println(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (in1 != null) {
                    out1.close();
                }
                if (out1 != null) {
                    out1.close();
                }
            } catch (Exception e) {
            }
        }
    }
    
    /**
     * 功能: 拷贝文件
     * 
     * @param sourceFile
     * @param targetFile
     * @return
     * @throws Exception
     */
    private static boolean copyFile_old(File sourceFile, File targetFile) throws Exception {
    	FileInputStream input = null; 
    	BufferedInputStream inBuff = null;
    	FileOutputStream output = null;
    	BufferedOutputStream outBuff = null;
    	try {
        	// 新建文件输入流并对它进行缓冲
            input = new FileInputStream(sourceFile);
            inBuff=new BufferedInputStream(input);
     
            // 新建文件输出流并对它进行缓冲
            output = new FileOutputStream(targetFile);
            outBuff = new BufferedOutputStream(output);
            
            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len =inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
            
            //关闭流
            inBuff.close();
            outBuff.close();
            output.close();
            input.close();
            return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
            try {
                if (input != null) {
                	input.close();
                }
                if (inBuff != null) {
                	inBuff.close();
                }
                if (output != null) {
                	output.close();
                }
                if (outBuff != null) {
                	outBuff.close();
                }
            } catch (Exception e) {
            }
		}
    }

    /**
     * 功能: 保存String到targetFile中
     * 
     * @param context
     * @param targetFile
     * @return
     * @throws Exception
     */
    public static boolean saveFile(String context, String targetFile) throws Exception {
        BufferedReader in1 = null;
        BufferedWriter out1 = null;
        initParentDir(targetFile);
        try {
            in1 = new BufferedReader(new StringReader(context));
            out1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile), RmBaseConfig.getSingleton().getDefaultEncode()));
            String s = null;
            boolean isFirstLine = true;
            while ((s = in1.readLine()) != null) {
            	if(!isFirstLine) {
            		out1.write("\n");
            	}
                out1.write(s);
        		isFirstLine = false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                if (in1 != null) {
                    out1.close();
                }
                if (out1 != null) {
                    out1.close();
                }
            } catch (Exception e) {
            }
        }
    }

    /**
     * 功能: 为目标创建父目录
     * 
     * @param targetPath
     */
    public static String initParentDir(String targetPath) {
        String formatPath = formatToFile(targetPath);
        File targetFile = new File(formatPath);
        if (!targetFile.getParentFile().exists()) {
            targetFile.getParentFile().mkdirs();
        }
        return targetFile.getParent();
    }

    /**
     * 功能: 为目标创建目录并返回
     * 
     * @param targetPath
     */
    public static String initSelfDir(String targetPath) {
        String formatPath = formatToFile(targetPath);
        File targetFile = new File(formatPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        return targetFile.getAbsolutePath();
    }
    
    /**
     * 复制整个文件夹内容
     * 
     * @param oldPath String 原文件路径
     * @param newPath String 复制后路径
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        oldPath = formatToFile(oldPath);
        newPath = formatToFile(newPath);
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.close();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * 功能: 随机访问文件tartetPath,把context插到afterKeyWord后边
     * 
     * @param targetPath
     * @param context
     * @param afterKeyWord
     */
    public static void writeToRandomFile(String targetPath, String context, String afterKeyWord) {
        BufferedReader in = null;
        RandomAccessFile rf = null;
        targetPath = formatToFile(targetPath);
        try {
            if (new File(targetPath).exists()) { //检查是否已经存在相同代码
                in = new BufferedReader(new FileReader(targetPath));
                String s1 = null;
                StringBuilder tempStr = new StringBuilder();
                boolean isFirstLine = true;
                while ((s1 = in.readLine()) != null) {
                	if(!isFirstLine) {
                		tempStr.append("\n");
                	}
                	tempStr.append(s1);
            		isFirstLine = false;
                }
                if (tempStr.indexOf(context) >= 0) { //已经存在
                    return;
                }
            }
            {
                String line = null;
                long position = 0;
                rf = new RandomAccessFile(targetPath, "rw");
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
                StringBuilder originRemain = new StringBuilder();
                boolean isFirstLine = true;
                while ((line = rf.readLine()) != null) {
                	if(!isFirstLine) {
                		originRemain.append("\n");
                	}
                	originRemain.append(line);
            		isFirstLine = false;
                }
                rf.seek(position);
                rf.write(context.getBytes(RmBaseConfig.getSingleton().getDefaultEncode()));
                rf.writeBytes(originRemain.toString());
            }

        } catch (FileNotFoundException e) {
        	throw new RuntimeException(e);
        } catch (IOException e) {
        	throw new RuntimeException(e);
        } finally {

            try {
                if (rf != null) {
                    rf.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }

        }
    }
    
	/**
	 * 递归删除目录
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static void delDir(String filepath) throws IOException {
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						delDir(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
				f.delete();
			}
		}
	}
	
	/**
	 * 移动文件
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IOException
	 */
	public static boolean renameTo(File source, File target) throws IOException {
		if (!source.renameTo(target)) {
			copyFile(source, target);
			return source.delete();
		}
		return true;
	}


    /**
     * 执行逻辑删除文件，把文件移动到全局配置的回收站。否则直接删除文件
     * 可以在rm.xml设置开关
     * @param f 源文件
     * @return 如果移动失败，不会删除源文件，返回false。 如果移动成功，删除源文件，返回true
     * @throws IOException 
     */
    public static boolean delete(File f) throws IOException {
    	if(RmBaseConfig.getSingleton().isLogicDeleteFile()) {
    		String recycle = RmBaseConfig.getSingleton().getRecycleBinFolder();
    		File fRecycle = new File(recycle);
    		if(!fRecycle.exists()) {
    			fRecycle.mkdirs();
    		}
    		File fToDelete = new File(recycle + File.separator + System.nanoTime() + "$" + f.getName());
    		if (!f.renameTo(fToDelete)) {
				copyFile(f, fToDelete);
				f.delete();
    		}
    		return true;
    	} else {
    		return f.delete();
    	}
    }
    
    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The file copy buffer size (30 MB)
     */
    private static final long FILE_COPY_BUFFER_SIZE = ONE_MB * 30;
    
    /**
     * Copies a file to a new location preserving the file date.
     * <p>
     * This method copies the contents of the specified source file to the
     * specified destination file. The directory holding the destination file is
     * created if it does not exist. If the destination file exists, then this
     * method will overwrite it.
     * <p>
     * <strong>Note:</strong> This method tries to preserve the file's last
     * modified date/times using {@link File#setLastModified(long)}, however
     * it is not guaranteed that the operation will succeed.
     * If the modification operation fails, no indication is provided.
     * 
     * @param srcFile  an existing file to copy, must not be <code>null</code>
     * @param destFile  the new file, must not be <code>null</code>
     * 
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFileToDirectory(File, File)
     */
    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    /**
     * Copies a file to a new location.
     * <p>
     * This method copies the contents of the specified source file
     * to the specified destination file.
     * The directory holding the destination file is created if it does not exist.
     * If the destination file exists, then this method will overwrite it.
     * <p>
     * <strong>Note:</strong> Setting <code>preserveFileDate</code> to
     * <code>true</code> tries to preserve the file's last modified
     * date/times using {@link File#setLastModified(long)}, however it is
     * not guaranteed that the operation will succeed.
     * If the modification operation fails, no indication is provided.
     *
     * @param srcFile  an existing file to copy, must not be <code>null</code>
     * @param destFile  the new file, must not be <code>null</code>
     * @param preserveFileDate  true if the file date of the copy
     *  should be the same as the original
     *
     * @throws NullPointerException if source or destination is <code>null</code>
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs during copying
     * @see #copyFileToDirectory(File, File, boolean)
     */
    public static void copyFile(File srcFile, File destFile,
            boolean preserveFileDate) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (srcFile.exists() == false) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        }
        if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        }
        File parentFile = destFile.getParentFile();
        if (parentFile != null) {
            if (!parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            }
        }
        if (destFile.exists() && destFile.canWrite() == false) {
            throw new IOException("Destination '" + destFile + "' exists but is read-only");
        }
        doCopyFile(srcFile, destFile, preserveFileDate);
    }
    
    /**
     * Internal copy file method.
     * 
     * @param srcFile  the validated source file, must not be <code>null</code>
     * @param destFile  the validated destination file, must not be <code>null</code>
     * @param preserveFileDate  whether to preserve the file date
     * @throws IOException if an error occurs
     */
    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input  = fis.getChannel();
            output = fos.getChannel();
            long size = input.size();
            long pos = 0;
            long count = 0;
            while (pos < size) {
                count = size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos;
                pos += output.transferFrom(input, pos, count);
            }
        } finally {
            closeQuietly(output);
            closeQuietly(fos);
            closeQuietly(input);
            closeQuietly(fis);
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "'");
        }
        if (preserveFileDate) {
            destFile.setLastModified(srcFile.lastModified());
        }
    }
    
    /**
     * Unconditionally close a <code>Closeable</code>.
     * <p>
     * Equivalent to {@link Closeable#close()}, except any exceptions will be ignored.
     * This is typically used in finally blocks.
     * <p>
     * Example code:
     * <pre>
     *   Closeable closeable = null;
     *   try {
     *       closeable = new FileReader("foo.txt");
     *       // process closeable
     *       closeable.close();
     *   } catch (Exception e) {
     *       // error handling
     *   } finally {
     *       IOUtils.closeQuietly(closeable);
     *   }
     * </pre>
     *
     * @param closeable the object to close, may be null or already closed
     * @since 2.0
     */
    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }
    

	public static String fileToString(File file) throws IOException {
		StringBuilder str = new StringBuilder();
		long totalSize = 0;
		str.append("卷（");
		str.append(file.toString());
		str.append("）的文件夹 PATH 列表\n");
		str.append("卷信息\t");
		str.append(" 存在:");
		str.append(file.exists());
		str.append("  是文件夹:");
		str.append(file.isDirectory());
		str.append("  能读:");
		str.append(file.canRead());
		str.append("  能写:");
		str.append(file.canWrite());
		str.append("  是否隐藏:");
		str.append(file.isHidden());
		str.append("  最后修改时间:");
		str.append(RmDateHelper.getFormatDateTimeDesc(file.lastModified()));
		str.append("\n");
		str.append(file.getAbsoluteFile());
		str.append("\n");
		RmString rmstr = listFileRecursive(file, "│├─", totalSize);
		str.append(rmstr.toString());
		str.append("\n总大小:");
		str.append(((Long)rmstr.getAttribute("totalSize")).longValue() / 1024);
		str.append(" k, ");
		str.append(((Long)rmstr.getAttribute("totalSize")).longValue());
		str.append("B.");
		return str.toString();
	}

    private static RmString listFileRecursive(File file, String sign, long totalSize) throws IOException {
		RmString rmstr = new RmString();
		if (rmstr.getAttribute("totalSize") == null) {
			rmstr.addAttribute("totalSize", new Long(0));
		}
		StringBuilder str = new StringBuilder();
		File[] fileChild = file.listFiles();
		if (fileChild != null) {
			int fileSum = 0, folderSum = 0;
			{ // 计算文件和文件夹个数
				for (int i = 0; i < fileChild.length; i++) {
					if (fileChild[i].isFile()) {
						fileSum++;
					} else if (fileChild[i].isDirectory()) {
						folderSum++;
					}
				}
			}
			for (int i = 0; i < fileChild.length; i++) {
				if (fileChild[i].isFile()) {
					fileSum++;
					if (folderSum > 0) {
                        str.append(sign.replaceAll("│├─", "│  "));
                        str.append(fileChild[i].getName());
                        str.append("\n");
					} else {
						str.append(sign.replaceAll("│├─", "   "));
						str.append(fileChild[i].getName());
						str.append("\n");
					}
					{ // 计算大小
                        long currentSize = ((Long)rmstr.getAttribute("totalSize")).longValue() + fileChild[i].length();
						rmstr.addAttribute("totalSize", new Long(currentSize));
					}
				}
			}
			if (fileSum > 0 && folderSum > 0) {
				str.append(sign.replaceAll("│├─", "│  "));
				str.append("\n");
			}
			int tempFolderIndex = 0;
			for (int i = 0; i < fileChild.length; i++) {
				if (fileChild[i].isDirectory()) {
					RmString tempRmstr = null;
					if (tempFolderIndex == folderSum - 1) {
						str.append(sign.replaceAll("│├─", "└──"));
						str.append(fileChild[i].getName());
						str.append("\n");
                        tempRmstr = listFileRecursive(fileChild[i], (sign + "   ").replaceAll("│├─   ", "   │├─"), totalSize);
					} else {
						str.append(sign.replaceAll("│├─", "├──"));
						str.append(fileChild[i].getName());
						str.append("\n");
                        tempRmstr = listFileRecursive(fileChild[i], (sign + "   ").replaceAll("│├─   ", "│  │├─"), totalSize);
					}
					{
                        long currentSize = ((Long)rmstr.getAttribute("totalSize")).longValue() + ((Long)tempRmstr.getAttribute("totalSize")).longValue();
						rmstr.addAttribute("totalSize", new Long(currentSize));
						str.append(tempRmstr);
					}
					tempFolderIndex++;
				}
			}
		}
		rmstr.setValue(str.toString());
		return rmstr;
	}
    

	public static void main(String[] args) {
		List<String> lf = getFiles("E:/platform/myProject/qbrm/code/quickbundle-securityweb/src/main/*/vo/Rm*java,E:/platform/myProject/qbrm/code/quickbundle-securityweb/src/test/*/*java");
		for(String s : lf) {
			System.out.println(s);
		}
	}
}
