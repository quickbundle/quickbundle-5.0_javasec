/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.path --> RmPathHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2006-7-7 20:33:10 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.tools.support.path;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quickbundle.base.web.servlet.RmHolderServlet;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmPathHelper {

	/**
	 * 功能: 创建一个用于热部署tomcat的xml文件
	 * 
	 * 
	 */
	public static File createWarXml(String tomcatContextFile) {
		Document doc = DocumentHelper.createDocument();
		Element context = doc.addElement("Context");
		context.addAttribute("path", "/" + getWarName());
		context.addAttribute("docBase", getWarDir().toString());
		context.addAttribute("debug", "0");
		context.addAttribute("reloadable", "false");
		context.addAttribute("privileged", "true");
		if(tomcatContextFile == null) {
			tomcatContextFile = getProjectDir().toString() + File.separator + getWarName() + ".xml";
		}
		RmXmlHelper.saveXmlToPath(doc, tomcatContextFile);
		return new File(tomcatContextFile);
	}
	

	/**
	 * 功能: 获得war目录。默认从缓存Servlet上下文取，如没有直接取RmGlobalReference所在目录(后者适用于开发环境)
	 * 
	 * @return
	 */
	public static File getWarDir() {
		File result = null;
		try {
			ClassLoader cl = Thread.currentThread().getContextClassLoader();
			if(cl == null) {
				cl = ClassLoader.getSystemClassLoader();
			}
			Class clz = cl.loadClass("org.quickbundle.config.RmLoadConfig");
			Method customMethod = clz.getDeclaredMethod("getWarDirCustom",new Class[]{});
	        if(customMethod != null) {
	        	result = (File)customMethod.invoke(null ,new Object[]{});
	        }
		} catch (java.lang.NoSuchMethodException e) {
			//ignore NoSuchMethodException
		} catch (java.lang.ClassNotFoundException e) {
			//ignore NoSuchMethodException
		} catch (java.lang.NoClassDefFoundError e) {
			//ignore NoSuchMethodException
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		if(result != null) {
			return result;
		}
		String defaultRealPath = null;
		try {
			defaultRealPath = RmHolderServlet.getDefaultRealPath("/");
		} catch (java.lang.NoClassDefFoundError e) {
			//ignore
		}
		if (defaultRealPath != null) {
			return new File(defaultRealPath);
		} else {
			File fileClasses = new File(getClassRootPath(RmBaseConfig.class));
			return findParentDir(fileClasses, "WEB-INF").getParentFile();
		}
	}

	/**
	 * 功能: 得到war名称。默认从缓存Servlet上下文取，如没有直接取warDir的name(后者适用于开发环境)
	 * 
	 * @return
	 */
	public static String getWarName() {
		String defaultServletContextName = null;
		try {
			defaultServletContextName = RmHolderServlet.getDefaultServletContextName();
		} catch (java.lang.NoClassDefFoundError e) {
			//ignore
		}
		if(defaultServletContextName == null || defaultServletContextName.length() == 0) {
			try {
	            //web.xml命名空间
	            Map<String, String> defaultNameSpaceMap = new HashMap<String, String>();  
	            defaultNameSpaceMap.put("q", "http://java.sun.com/xml/ns/javaee");
	            //读入web.xml
	            Document docWebXml = RmXmlHelper.parse(getWebInfDir() + File.separator + "web.xml", defaultNameSpaceMap);
	            defaultServletContextName = docWebXml.valueOf("/q:web-app/q:display-name");
			} catch (Exception e) {
				//ignore
			}
		}
		if(defaultServletContextName == null || defaultServletContextName.length() == 0) {
			defaultServletContextName = getWarDir().getName();
		}
		return defaultServletContextName;
	}

	/**
	 * 功能: 获得WEB-INF目录。默认从缓存Servlet上下文取，如没有直接取RmGlobalReference所在目录(后者适用于开发环境)
	 * 
	 * @return
	 */
	public static File getWebInfDir() {
		return new File(getWarDir() + File.separator + "WEB-INF");
	}
	
	/**
	 * 功能: 用于开发环境中获取项目的路径，规则是取warDir的父目录
	 * 
	 * @return
	 */
	public static File getProjectDir() {
		return getWarDir().getParentFile();
	}

	/**
	 * 功能: 用于开发环境中获取项目的名称，规则是取warDir的父目录的name
	 * 
	 * @return
	 */
	public static String getProjectName() {
		return getProjectDir().getName();
	}

	/**
	 * 功能: 递归的寻找父目录中第一个出现fileKey的目录
	 * 
	 * @param file
	 * @param fileKey
	 * @return
	 */
	public static File findParentDir(File file, String fileKey) {
		if (fileKey.equals(file.getName())) {
			return file;
		} else if (file.getParentFile() != null) {
			return findParentDir(file.getParentFile(), fileKey);
		} else {
			return file.getParentFile();
		}
	}

	/**
	 * 在类中取得当前文件所在的相对路径与绝对路径
	 * 
	 * @return String
	 */
	public static String getClassRootPath(Class thisClass) {
		String rtStr = "";
		String url = String.valueOf(thisClass.getResource(""));
		try {
			rtStr = URLDecoder.decode(url, RmBaseConfig.getSingleton().getDefaultEncode());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		rtStr = RmXmlHelper.formatToFile(rtStr);
		return rtStr;
	}

	public static int count = 0;

	/**
	 * 功能: 从File中找出className
	 * 
	 * @param file
	 * @param className
	 */
	public static void findClassFromFile(File[] file, String className) {
		count = 0; // 初始化count
		String absoluteclassname = className.replace('.', '/') + ".class";
		RmLogHelper.getLogger(RmPathHelper.class).info("Find class [" + className + "] in Path [" + file + "] Results:");
		for (int i = 0; i < file.length; i++) {
			findClassInLocalSystem(file[i].toString(), absoluteclassname);
		}
		if (count == 0) {
			RmLogHelper.getLogger(RmPathHelper.class).warn("Error:Can't Find Such Jar File!");
		}
	}

	private static void findClassInLocalSystem(String path, String classname) {
		if (path.charAt(path.length() - 1) != '\\') {
			path += '\\';
		}
		File file = new File(path);
		if (!file.exists()) {
			RmLogHelper.getLogger(RmPathHelper.class).error("Error: Path not Existed! Please Check it out!");
			return;
		}
		String[] filelist = file.list();
		for (int i = 0; i < filelist.length; i++) {
			File temp = new File(path + filelist[i]);
			if ((temp.isDirectory() && !temp.isHidden() && temp.exists())) {
				findClassInLocalSystem(path + filelist[i], classname);
			} else {
				if (filelist[i].endsWith("jar")) {
					try {
						java.util.jar.JarFile jarfile = new java.util.jar.JarFile(path + filelist[i]);
						for (Enumeration e = jarfile.entries(); e.hasMoreElements();) {
							String name = e.nextElement().toString();
							if (name.equals(classname)) {
								RmLogHelper.getLogger(RmPathHelper.class).info("No." + (++count));
								RmLogHelper.getLogger(RmPathHelper.class).info("Jar Package:" + path + filelist[i]);
								RmLogHelper.getLogger(RmPathHelper.class).info(name);
							}
						}
					} catch (Exception eee) {
					}
				}
			}
		}
	}

    /**
     * 初始化webAppRootKey(如:rmdemo.root)
     * 
     * @return
     */
    public static String initWarRoot() {
        String warRoot = RmPathHelper.getWarName() + ".root";
        System.setProperty(warRoot, RmXmlHelper.formatToUrl(RmPathHelper.getWarDir().toString()) + "/");
        return warRoot + "=" + System.getProperty(warRoot);
    }

	public static void showHowToUsage() {
		RmLogHelper.getLogger(RmPathHelper.class).info("Usage: Java -cp . JarClassFind <source path> <source class name>");
		RmLogHelper.getLogger(RmPathHelper.class).info("Usage: Java -classpath . JarClassFind <source path> <source class name>");
		RmLogHelper.getLogger(RmPathHelper.class).info("");
		RmLogHelper.getLogger(RmPathHelper.class).info("<source path>:\t\tPath to Find eg:F:\\JDK");
		RmLogHelper.getLogger(RmPathHelper.class).info("<source class name>:\tClass to Find eg:java.applet.Applet");
	}

	public static void main(String[] args) {
		args = new String[] {getWebInfDir() + File.separator + "lib", RmPathHelper.class.getName()};
		if (args.length < 2) {
			showHowToUsage();
			return;
		} else {
			findClassFromFile(new File[] { new File(args[0]) }, args[1]);
		}
		System.out.println("RmPathHelper.getWarDir()=" + RmPathHelper.getWarDir());
		System.out.println("RmPathHelper.getWarName()=" + RmPathHelper.getWarName());
		System.out.println("RmPathHelper.getWebInfDir()=" + RmPathHelper.getWebInfDir());
		System.out.println("RmPathHelper.getProjectDir()=" + RmPathHelper.getProjectDir());
		System.out.println("RmPathHelper.getProjectName()=" + RmPathHelper.getProjectName());
		try {
			RmLogHelper.getLogger(RmPathHelper.class).info(String.valueOf(ClassLoader.getSystemResource("")));
			RmLogHelper.getLogger(RmPathHelper.class).info(String.valueOf(getWebInfDir()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}