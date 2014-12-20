//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/rmschedulerevent/util/exception/RmSchedulerEventException.java
//代码生成时,系统时间: 2012-04-02 22:28:45
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.rmschedulerevent.util.exception --> RmSchedulerEventException.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:45 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.rmschedulerevent.util;

import org.quickbundle.base.exception.RmRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmSchedulerEventException extends RmRuntimeException {
	/**
	 * 构造函数
	 */
	public RmSchedulerEventException() {
		super();
	}
    /**
     * 构造函数:
     * @param msg
     */
    public RmSchedulerEventException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数:
     * @param t
     */
    public RmSchedulerEventException(Throwable t) {
        super(t);
    }

    /**
     * 构造函数:
     * @param msg
     * @param t
     */
    public RmSchedulerEventException(String msg, Throwable t) {
        super(msg, t);
    }
    
	/**
     * 构造函数:
     * @param msg
     * @param t
     * @param returnObj
     */
    public RmSchedulerEventException(String msg, Throwable t, Object returnObj) {
        super(msg, t, returnObj);
    }
}