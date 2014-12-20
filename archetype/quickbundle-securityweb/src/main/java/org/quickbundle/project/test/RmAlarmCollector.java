package org.quickbundle.project.test;

import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.listener.RmGlobalMonitor;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmStringHelper;

public class RmAlarmCollector {
	/**
	 * 根据传入的msg和Throwable对象，自动采集服务器上下文信息生成标题和正文
	 * @param msg
	 * @param e
	 * @return new String[]{subject, content}
	 */
	public static String[] createInfo(Object msg, Throwable e) {
    	StringBuilder subject = new StringBuilder();
    	if(RmClusterConfig.getLocalhostInfo() != null) {
    		subject.append(RmClusterConfig.getLocalhostInfo().getLocalhostUrlPath());
    	} else {
    		subject.append(RmClusterConfig.getSingleton().getSelfNode().get(RmClusterConfig.NodeKey.baseUrl.name()));
    	}
    	if(subject.toString().startsWith("http://")) {
    		subject.delete(0, "http://".length());
    	}
    	subject.append(" ");
    	if(msg != null) {
    		if(msg.toString().length() < 200) {
    			subject.append(msg);
    		} else {
    			subject.append(msg.toString().substring(0, 200));
    		}
    	} else if(e != null){
    		subject.append(e.getMessage());
    	}
    	String br = "";//"<br/>";
    	StringBuilder content = new StringBuilder();
    	{ //系统信息
    		content.append("UUID: ");
    		content.append(RmGlobalMonitor.getShortUniqueUUID());
    		content.append("\n");
    		
    		content.append("Thread.currentThread(): ");
    		content.append(Thread.currentThread().getId());
    		content.append(" | ");
    		content.append(Thread.currentThread().getName());
    		content.append(" | ");
    		content.append(Thread.currentThread().getClass().getName());
    		content.append(br);
    		content.append("\n");
    	}
    	if(msg != null) { //消息
    		content.append("Message: ");
    		content.append(msg.toString());
    		content.append(br);
    		content.append("\n\n");
    	}
    	if(e != null) { //异常
    		content.append("Throwable: ");
    		content.append(e.toString());
    		content.append("\n");
    		content.append(RmStringHelper.getStackTraceStr(e, 100000));
    		content.append(br);
    		content.append("\n\n");
    	}
    	{ //调用栈
    		String callStack = Arrays.deepToString(Thread.currentThread().getStackTrace());
    		if(callStack.length() > 100000) {
    			callStack = callStack.substring(0, 100000);
    			content.append("[stack string length:" + callStack.length() + "]");
    		}
    		content.append("Thread.getStackTrace: ");
    		callStack = callStack.replaceAll(", ", ",\n");
    		content.append(callStack);
    		content.append(br);
    		content.append("\n\n");
    	}
    	
    	if(RmRequestMonitor.getCurrentThreadRequest() != null) { //Request信息
    		HttpServletRequest request = RmRequestMonitor.getCurrentThreadRequest();
    		content.append("request.getRequestURL: ");
    		content.append(request.getRequestURL());
    		content.append("\n");

    		content.append("request.getMethod: ");
    		content.append(request.getMethod());
    		content.append("\n");
    		
    		content.append("request.getRemoteAddr: ");
    		content.append(RmProjectHelper.getIp(request));
    		content.append("\n\n");
    		
    		content.append("request.getParameter:\n");
    		Enumeration enume = request.getParameterNames();
    		for(Object key; enume.hasMoreElements(); ) {
    			key = enume.nextElement();
    			content.append(key);
    			content.append(":");
    			content.append(Arrays.deepToString(request.getParameterValues((String)key)));
    			content.append("\n");
    		}
    		content.append(br);
    		content.append("\n\n");
    		
    		content.append("request.getHeader:\n");
    		enume = request.getHeaderNames();
    		for(Object key; enume.hasMoreElements(); ) {
    			key = enume.nextElement();
    	    	content.append(key);
    	    	content.append(":");
    	    	content.append(request.getHeader((String)key));
    	    	content.append("\n");
    		}
    		content.append(br);
    		content.append("\n\n");

    		content.append("request.getAttribute:\n");
    		enume = request.getAttributeNames();
    		for(Object key; enume.hasMoreElements(); ) {
    			key = enume.nextElement();
    	    	content.append(key);
    	    	content.append(":");
    	    	content.append(request.getAttribute((String)key));
    	    	content.append("\n");
    		}
    		content.append(br);
    		content.append("\n\n");
    		
    		HttpSession session = request.getSession(false);
    		if(session != null) {
    			content.append("session.getId: ");
    			content.append(session.getId());
    			content.append("\n");
        		content.append("session.getAttribute:\n");
        		enume = session.getAttributeNames();
        		for(Object key; enume.hasMoreElements(); ) {
        			key = enume.nextElement();
        	    	content.append(key);
        	    	content.append(":");
        	    	content.append(session.getAttribute((String)key));
        	    	content.append("\n");
        		}
        		content.append(br);
        		content.append("\n\n");
    		}
    	}
    	return new String[]{subject.toString(), content.toString()};
	}
}
