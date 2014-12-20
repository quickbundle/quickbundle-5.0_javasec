package org.quickbundle.modules.lock;

import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.modules.lock.rmlock.service.IRmLockService;
import org.quickbundle.modules.lock.rmlock.util.IRmLockConstants;
import org.quickbundle.modules.lock.rmlock.vo.RmLockVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.listener.RmRequestMonitor;
import org.quickbundle.tools.helper.RmDateHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;

public class RmLockHelper {
	private static String DEFAULT_LOCK_BS_KEYWORD = "default";
	
	private static IRmLockService getLockService() {
		return (IRmLockService)RmBeanFactory.getBean(IRmLockConstants.SERVICE_KEY);
	}
	
	/**
	 * 对集群环境下的互斥业务加基于数据库的锁，代码示例：
		try {
			if(RmLockHelper.lock("1000100000000000001", "2")) {
				//正常业务操作
			} else {
				throw new RuntimeException("他人正在操作数据: 2");
			}
		} finally {
			RmLockHelper.unlock("1000100000000000001", "2");
		}
	 * @param user_id
	 * @param value
	 * @return
	 */
	public static boolean lock(String user_id, String value) {
		return lock(user_id, value, DEFAULT_LOCK_BS_KEYWORD);
	}
	
	/**
	 * 对集群环境下的互斥业务加基于数据库的锁，代码示例：
		try {
			if(RmLockHelper.lock("1000100000000000001", "2", "a");	) {
				//正常业务操作
			} else {
				throw new RuntimeException("他人正在操作数据: a->2");
			}
		} finally {
			RmLockHelper.unlock("1000100000000000001", "2", "a");
		}
	 * @param user_id 为null或""时，不能重复获得业务锁。否则可以重复获得此user_id曾经拥有的业务锁
	 * @param value 锁的内容，一般是业务主键
	 * @param bs_keyword 业务类型的关键字
	 * @return
	 */
	public static boolean lock(String user_id, String value, String bs_keyword) {
		RmLockVo vo = new RmLockVo();
		vo.setUser_id(user_id);
		vo.setBs_keyword(bs_keyword);
		vo.setLock_content(value);
		vo.setLock_date(RmDateHelper.getSysTimestamp());
		if(RmRequestMonitor.getCurrentThreadRequest() != null) {
			RmPopulateHelper.populate(vo, RmRequestMonitor.getCurrentThreadRequest());
		}
		boolean result = getLockService().executeLock(vo);
		return result;
	}
	
	/**
	 * 解锁
	 * @param user_id
	 * @param value
	 */
	public static void unlock(String user_id, String value) {
		unlock(user_id, value, DEFAULT_LOCK_BS_KEYWORD);
	}
	
	/**
	 * 解锁
	 * @param user_id
	 * @param value
	 * @param bs_keyword
	 */
	public static void unlock(String user_id, String value, String bs_keyword) {
		IRmLockService ls = getLockService();
		int sum = ls.deleteLock(user_id, value, bs_keyword);
		if(sum != 1) {
//			throw new RmLockException("没有释放业务锁");
		}
	}
	
	/**
	 * 释放该用户获得的所有业务锁
	 */
	public static void releaseLock(String user_id) {
		if(getLockService().queryByCondition("USER_ID='" + user_id + "'", null).size() > 0) {
			RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_LOCK where USER_ID=" + user_id);
		}
	}
}
