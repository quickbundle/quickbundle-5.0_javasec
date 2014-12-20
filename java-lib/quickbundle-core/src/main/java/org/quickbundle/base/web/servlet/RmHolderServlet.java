/*
 * 系统名称:基于冉闵开发工具 --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.support.god --> ExecuteSqlServlet.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2006-7-10 19:42:48 创建1.0.0版 (baixiaoyong)
 *  
 */
package org.quickbundle.base.web.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quickbundle.itf.ILoadOnStartup;


/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmHolderServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	private static ServletContext defaultServletContext = null;

    public static ServletContext getDefaultServletContext() {
        return defaultServletContext;
    }
    
    public static void setDefaultServletContext(ServletContext servletContext) {
    	defaultServletContext = servletContext;
    }
    
    public static String getDefaultServletContextName() {
    	if(defaultServletContext != null) {
    		return defaultServletContext.getServletContextName();
    	}
    	return null;
    }
    
    public static String getDefaultRealPath(String path) {
    	if(defaultServletContext != null) {
    		return defaultServletContext.getRealPath(path);
    	}
    	return null;
    }

    public void destroy() {
    }   

	/**
	 * 第一个执行的servlet，用于初始化配置
	 * 
	 */
    public void init() throws ServletException {
        super.init();
        //第一个Servlet的上下文，被初始化进缓存
        if(defaultServletContext == null) {
            defaultServletContext = this.getServletContext();        	
        }
        //初始化init-classes中定义的类
        String initClasses = getServletConfig().getInitParameter("init-classes");
        String[] aInitClasses = initClasses.trim().split(",");
        for(int i =0; i<aInitClasses.length; i++) {
            String tempClassName = aInitClasses[i].trim();
            try {
                ILoadOnStartup loadOnStartup = (ILoadOnStartup)Class.forName(tempClassName).newInstance();
                loadOnStartup.init(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}