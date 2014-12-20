/*
 * 系统名称: QuickBundle --> rmdemo
 * 
 * 文件名称: org.quickbundle.tools.helper --> RmLogHelper.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2006-3-17 13:34:51 创建1.0.0版 (baixiaoyong)
 * 
 */
package org.quickbundle.tools.support.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public class RmLogHelper {
	public static Logger getLogger(String className) {
		return LoggerFactory.getLogger(className);
	}

	public static Logger getLogger(Class class_) {
		return getLogger(class_.getName());
	}

	public static void trace(Class class_, String str) {
		getLogger(class_).trace(str);
	}

	public static void debug(Class class_, String str) {
		getLogger(class_).debug(str);
	}

	public static void info(Class class_, String str) {
		getLogger(class_).info(str);
	}

	public static void warn(Class class_, String str) {
		getLogger(class_).warn(str);
	}

	public static void error(Class class_, String str) {
		getLogger(class_).error(str);
	}
}