//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/util/exception/RmAffixException.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.util.exception --> RmAffixException.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix.util;

import org.quickbundle.base.exception.RmRuntimeException;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAffixException extends RmRuntimeException {
	/**
	 * 构造函数
	 */
	public RmAffixException() {
		super();
	}
    /**
     * 构造函数:
     * @param msg
     */
    public RmAffixException(String msg) {
        super(msg);
    }
    
    /**
     * 构造函数:
     * @param t
     */
    public RmAffixException(Throwable t) {
        super(t);
    }

    /**
     * 构造函数:
     * @param msg
     * @param t
     */
    public RmAffixException(String msg, Throwable t) {
        super(msg, t);
    }
    
    /**
     * 构造函数:
     * @param msg
     * @param t
     * @param returnObj
     */
    public RmAffixException(String msg, Throwable t, Object returnObj) {
        super(msg, t, returnObj);
    }
}