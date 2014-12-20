package org.quickbundle.project.login;

import java.sql.Timestamp;

public interface IRmLoginVo {

	/**
	 * @return 登录帐号
	 */
	String getLogin_id();
	
	/**
	 * @param 设置登录帐号login_id
	 */
	void setLogin_id(String login_id);

	/**
	 * @return 最后登录时间
	 */
	Timestamp getLast_login_date();

	/**
	 * @return 最后登录IP
	 */
	String getLast_login_ip();

	/**
	 * @return 登录后的USER_ID
	 */
	String getId();

	/**
	 * @return 用户类型
	 */
	String getAdmin_type();

	/**
	 * @return 密码
	 */
	String getPassword();
	
	/**
	 * 设置密码
	 * @param password
	 */
	void setPassword(String password);
	
	/**
	 * @return 登录失败信息
	 */
	String getLoginFailed();
	
	/**
	 * 设置登录失败信息
	 * @param alertStr
	 */
	void setLoginFailed(String failedInfo);
	
    /**
     * 设置激活/锁定状态：$RM_LOCK_STATUS=激活、锁定状态{ 0=锁定, 1=激活状态 }
     * @param lock_status 激活/锁定状态
     */
	public void setLock_status(String lock_status);
	
    /**
     * $RM_LOCK_STATUS=激活、锁定状态{ 0=锁定, 1=激活状态 }
     * @return 激活/锁定状态
     */
	public String getLock_status();

}
