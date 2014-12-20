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
import org.springframework.core.NestedRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmRuntimeException extends NestedRuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造函数
	 */
	public RmRuntimeException() {
		super(null);
	}

    /**
     * 构造函数:
     * @param msg
     */
    public RmRuntimeException(String msg) {
        super(msg);
    }
	
	/**
	 * 构造函数
	 * @param t
	 */
	public RmRuntimeException(Throwable t) {
		super(null, t);
	}
    
    /**
     * 构造函数:
     * @param msg
     * @param t
     */
    public RmRuntimeException(String msg, Throwable t) {
        super(msg, t);
    }
    

    /**
     * 构造函数:
     * @param msg
     * @param t
     * @param returnObj
     */
    public RmRuntimeException(String msg, Throwable t, Object returnObj) {
        super(msg, t);
        this.returnObj = returnObj;
    }
    
    /**
     * @return 返回 returnObj。
     */
    public Object getReturnObj() {
        return returnObj;
    }
    
	//Exception可以组合一个Object
    private Object returnObj = null;
}