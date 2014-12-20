//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartyview/util/exception/RmPartyViewException.java
//代码生成时,系统时间: 2010-11-28 17:40:27
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyview.util.exception --> RmPartyViewException.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:27 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyview.util;

import org.quickbundle.base.exception.RmRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmPartyViewException extends RmRuntimeException {
	/**
	 * 构造函数
	 */
	public RmPartyViewException() {
		super();
	}
    /**
     * 构造函数:
     * @param msg
     */
    public RmPartyViewException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数:
     * @param t
     */
    public RmPartyViewException(Throwable t) {
        super(t);
    }

    /**
     * 构造函数:
     * @param msg
     * @param t
     */
    public RmPartyViewException(String msg, Throwable t) {
        super(msg, t);
    }
    
	/**
     * 构造函数:
     * @param msg
     * @param t
     * @param returnObj
     */
    public RmPartyViewException(String msg, Throwable t, Object returnObj) {
        super(msg, t, returnObj);
    }
}