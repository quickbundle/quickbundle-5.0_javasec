/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.util --> RmRuntimeException.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-4-7 21:09:19 创建1.0.0版 (Administrator)
 * 
 */
package org.quickbundle.base.exception;
import org.springframework.core.NestedCheckedException;


/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmCheckedException extends NestedCheckedException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Object returnObj = null;
    
    /**
     * @return 返回 returnObj。
     */
    public Object getReturnObj() {
        return returnObj;
    }
    
    /**
     * 构造函数:
     * @param msg
     */
    public RmCheckedException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数:
     * @param msg
     * @param ex
     */
    public RmCheckedException(String msg, Throwable ex) {
        super(msg, ex);
    }

    /**
     * 构造函数:
     * @param msg
     * @param ex
     * @param returnObj
     */
    public RmCheckedException(String msg, Throwable ex, Object returnObj) {
        super(msg, ex);
        this.returnObj = returnObj;
    }

    /**
     * 构造函数:
     * @param msg
     * @param returnObj
     */
    public RmCheckedException(String msg, Object returnObj) {
        super(msg);
        this.returnObj = returnObj;
    }
    

}
