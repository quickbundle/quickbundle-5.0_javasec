//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/log/rmlog/util/exception/RmLogException.java
//代码生成时,系统时间: 2010-11-30 22:31:36
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> cu-tm
 * 
 * 文件名称: org.quickbundle.modules.log.rmlog.util.exception --> RmLogException.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:31:36 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlog.util;

import org.quickbundle.base.exception.RmRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmLogException extends RmRuntimeException {
	/**
	 * 构造函数
	 */
	public RmLogException() {
		super();
	}
    /**
     * 构造函数:
     * @param msg
     */
    public RmLogException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数:
     * @param t
     */
    public RmLogException(Throwable t) {
        super(t);
    }

    /**
     * 构造函数:
     * @param msg
     * @param t
     */
    public RmLogException(String msg, Throwable t) {
        super(msg, t);
    }
    
	/**
     * 构造函数:
     * @param msg
     * @param t
     * @param returnObj
     */
    public RmLogException(String msg, Throwable t, Object returnObj) {
        super(msg, t, returnObj);
    }
}