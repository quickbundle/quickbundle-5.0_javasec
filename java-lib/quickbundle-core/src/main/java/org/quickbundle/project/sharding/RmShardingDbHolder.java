/*
 * 系统名称: quickBundle.org --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.project.sharding --> CustomerContextHolder.java
 * 
 * 功能描述:
 * 
 * 版本历史:
 * 2008-8-7 下午01:33:07 创建1.0.0版 (Administrator)
 * 
 */
package org.quickbundle.project.sharding;

import org.quickbundle.base.exception.RmRuntimeException;

public class RmShardingDbHolder {
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

	/**
	 * 功能: 设置当前线程的频道ID，数据源会自动切换到dataSource_频道ID
	 * 
	 * @param customerType
	 */
	public static void setShardingId(String customerType) {
		if (customerType == null || customerType.trim().length() == 0) {
			throw new RmRuntimeException("指定的分频道数据库为空，非法操作！");
		}
		contextHolder.set(customerType);
	}

	/**
	 * 功能: 获取当前线程的频道ID
	 * 
	 * @return
	 */
	public static String getShardingId() {
		return (String) contextHolder.get();
	}

	/**
	 * 功能: 清理当前频道ID，数据源会自动切换到dataSource，即主数据库
	 * 
	 */
	public static void clearShardingId() {
		contextHolder.remove();
	}

}
