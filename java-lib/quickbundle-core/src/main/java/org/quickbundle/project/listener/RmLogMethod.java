/*
 * 系统名称:QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.project.log --> Fd.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2008-9-11 上午09:33:35 创建1.0.0版 (Administrator)
 * 
 */
package org.quickbundle.project.listener;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmUUIDHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmLogMethod {
	private static final Logger logMethod = RmLogHelper.getLogger("rmmethod");
	private static final Logger logException = RmLogHelper.getLogger("rmexception");
	
	//方法开始的毫秒数
    private static final ThreadLocal<Long> methodHolder = new ThreadLocal<Long>();
    
    //方法栈的调用层数
    private static final ThreadLocal<Integer> methodStackDeep = new ThreadLocal<Integer>();
    
    //本次线程中，是否在本方法创建的UUID?
    private static final ThreadLocal<Boolean> hereCreateUUID = new ThreadLocal<Boolean>();
    
    public void beforeMethod(JoinPoint jp) {
        if(methodStackDeep.get() == null) { //方法栈的顶层
        	if(RmGlobalMonitor.uniqueUUID.get() == null) {
        		RmGlobalMonitor.uniqueUUID.set(RmUUIDHelper.generateUUID());
        		hereCreateUUID.set(true);
        	}
        	methodHolder.set(System.currentTimeMillis());
        	methodStackDeep.set(0);
        } else { //下层方法栈
        	methodStackDeep.set(methodStackDeep.get() + 1);
        }
    }
    
    /**
     * 递归获取异常的cause
     * 
     * @param t
     * @param sb
     * @return
     */
    private String getAllCause(Throwable t) {
    	if(t == null || t.getCause() == null) {
    		return "";
    	}
    	List<String> lCause = new LinkedList<String>();
    	recursionAppendCause(t, lCause);
    	return Arrays.toString(lCause.toArray(new String[0]));
    }
    
    private void recursionAppendCause(Throwable t, List<String> lCause) {
    	if(t.getCause() == null || lCause.size() > RmBaseConfig.getSingleton().getMaxCircleCount()) {
    		return;
    	} else {
    		Throwable tCause = t.getCause();
    		lCause.add(t.toString());
    		recursionAppendCause(tCause, lCause);
    	}
    }

    public void afterThrowingMethod(JoinPoint jp, Throwable t) {
    	logException.error(jp.getTarget().getClass() + "." + jp.getSignature().getName() + " " + t.toString() + " cause:" + getAllCause(t));
    }
    
    public void afterReturningMethod(JoinPoint jp, Object r) {
        try {
        	if(methodStackDeep.get() > 0) { //下层方法栈
        		methodStackDeep.set(methodStackDeep.get() -1);
        	} else { //顶层方法栈
        		doLog(jp, r);
        		methodHolder.remove();
        		methodStackDeep.remove();
        		//清除标识
        		if(hereCreateUUID.get() != null && hereCreateUUID.get().booleanValue()) {
        			//清除UUID
        			RmGlobalMonitor.uniqueUUID.remove();
        			hereCreateUUID.remove();
        		}
        	}
        } catch (Exception e) {
        	logException.error(jp.getTarget().getClass() + "." + jp.getSignature().getName() + " afterMethod " + e.toString() + " cause:" + getAllCause(e));
        }
    }
    
    void doLog(JoinPoint jp, Object r) {
        long now = System.currentTimeMillis();
    	long executeTime = now - methodHolder.get().longValue();
        StringBuilder sb = new StringBuilder();
        //时间戳
        sb.append(now);
        sb.append("|");
        //执行时间
        sb.append(executeTime);
        sb.append("|");
        //返回结果监控
        int rSize = -1;
        if(r != null) {
        	if(r instanceof Collection) {
        		rSize = ((Collection)r).size();
        	} else if(r instanceof Map && !"org.quickbundle.project.common.vo.RmCommonVo".equals(r.getClass().getName())) {
        		rSize = ((Map)r).size();
        	} else if(r instanceof Object[]) {
        		rSize = ((Object[])r).length;
        	}
        }
        if(rSize > -1) {
        	sb.append(rSize);
        }
        sb.append("|");
        //线程信息
        sb.append(RmGlobalMonitor.getShortUniqueUUID());
        sb.append(".");
        sb.append(Thread.currentThread().getId());
        sb.append("|");
        sb.append(jp.getTarget().getClass().getName());
        sb.append("|");
        sb.append(jp.getSignature().getName());
        sb.append("(");
        Signature sig = jp.getSignature();
        if(sig instanceof MethodSignature) {
        	Class[] aClass = ((MethodSignature) sig).getParameterTypes();
        	for(int i=0; i<aClass.length; i++) {
        		if(i > 0) {
        			sb.append(",");
        		}
        		sb.append(aClass[i].getName());
        	}
        }
        sb.append(")");
    	logMethod.debug(sb.toString().replaceAll("[\\r\\n]+", " "));
    }
}