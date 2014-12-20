package org.quickbundle.project.listener;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmUUIDHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmRequestMonitor {
	
    /**
     * 把每次的Request绑定到当前线程
     */
    public static final ThreadLocal<ServletRequest> tlCurrentRequest = new ThreadLocal<ServletRequest>();
    
	//request统计的Logger
	private static final Logger logRequest = RmLogHelper.getLogger("rmrequest");
	//request统计的Logger，用于控制台输出
	private static final Logger logRequest2 = RmLogHelper.getLogger("rmrequest2");
    
    /**
     * 绑定的sql数量
     * new long[]{普通SQL数, 批更新次数, 批更新SQL数, SQL执行累计时间}
     */
    public static final ThreadLocal<long[]> tlSqlCount = new ThreadLocal<long[]>();
	
    /**
     * 功能: 获取当前线程的HttpServletRequest(request)对象
     *
     * @return
     */
    public static HttpServletRequest getCurrentThreadRequest() {
		ServletRequest request = RmRequestMonitor.tlCurrentRequest.get();
        if(request == null) {
        	return null;
        } else if(request instanceof HttpServletRequest) {
        	return (HttpServletRequest)request;
        } else {
            throw new RuntimeException("request:" + request.getClass() + ", not supported!");
        }
    }
    
    /**
     * 功能: 输出请求执行时间和sql数量的日志
     *
     * @param thisClass
     * @param request
     */
    public static void logTlSqlCount(HttpServletRequest request) {
        long executeTime = -1l;
        if(RmGlobalMonitor.uniqueUUID.get() != null) {
        	long startTime = RmUUIDHelper.getTimestamp(RmGlobalMonitor.uniqueUUID.get());
        	long nowUUID = RmUUIDHelper.getSysTimestamp();
            executeTime = (nowUUID - startTime) /10000;
        }
        long[] sqlCount = tlSqlCount.get();
        StringBuilder sb = new StringBuilder();
        sb.append(System.currentTimeMillis());
        sb.append("|");
        sb.append(executeTime);
        sb.append(",");
        //记录sql执行总时间
        if(sqlCount != null) {
        	sb.append(sqlCount[3]);
        } else {
        	sb.append("0");
        }
        sb.append("|");
        //记录sql批次和sql数量
        if(sqlCount != null) {
        	sb.append(sqlCount[0]);
        	sb.append(",");
        	sb.append(sqlCount[1]);
        	sb.append(",");
        	sb.append(sqlCount[2]);
        } else {
        	sb.append("0,0,0");
        }
        sb.append("|");
        sb.append(RmGlobalMonitor.getShortUniqueUUID());
        sb.append(".");
        sb.append(Thread.currentThread().getId());
        {
        	sb.append(".");
        	String user = getRmLoginId(request);
        	if(user != null) {
        		sb.append(user);
        	} 
        }
        sb.append("|");
        sb.append(request.getRequestURI());
        if(request.getParameter("cmd") != null) {
        	sb.append("?cmd=");
        	sb.append(request.getParameter("cmd"));
        }
        sb.append("|");
        String qs = "";
        if(request.getQueryString() != null) {
        	qs = request.getQueryString();
        	qs = RmStringHelper.encode2Encode(qs, "iso8859-1", RmBaseConfig.getSingleton().getDefaultEncode());
        }
        sb.append(qs);
    	if (request.getAttribute("org.apache.struts.action.EXCEPTION") != null) {
    		Exception exception = (Exception) request.getAttribute("org.apache.struts.action.EXCEPTION");
        	sb.append("|");
        	sb.append(exception.getClass().getName());
        	sb.append(":");
        	sb.append(exception.getMessage() != null ? exception.getMessage().trim() : "");
        }
    	String sbStr = sb.toString().replaceAll("[\\r\\n]+", " ");
    	logRequest.debug(sbStr);
    	//if(sqlCount != null && (sqlCount[0] > 0 || sqlCount[1] > 0)) {
    		logRequest2.debug(sbStr);
    	//}
    }
    
    static String getRmLoginId(HttpServletRequest request) {
    	Class clz = null;
    	Object loginId = null;
    	try {
    		clz = Class.forName("org.quickbundle.project.RmProjectHelper");
    		loginId = clz.getMethod("getRmLoginId", ServletRequest.class, boolean.class).invoke(clz, new Object[]{request, false});
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
    	return loginId != null ? loginId.toString() : null;
    }
}
