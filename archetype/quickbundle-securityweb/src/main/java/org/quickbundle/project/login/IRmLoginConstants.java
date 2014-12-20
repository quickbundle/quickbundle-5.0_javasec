package org.quickbundle.project.login;

import org.quickbundle.project.IGlobalConstants;

public interface IRmLoginConstants extends IGlobalConstants {
	/**
	 * 强制不使用cookie
	 */
	public final static String NO_COOKIE = "NO_COOKIE";
	
	/**
	 * 如果用户选择了强制登录，session中会放LoginVo
	 */
	public final static String FORCE_LOGIN_VO = "FORCE_LOGIN_VO";
	
	/**
	 * 强制退出的老用户的信息
	 */
	public final static String USER_UNIQUE_LOGIN_VO = "USER_UNIQUE_LOGIN_VO";
	
	/**
	 * session存放匿名用户的IP的key
	 */
	public final static String LAST_ACCESS_IP = "LAST_ACCESS_IP";
	
	public enum LoginForward {
		//跳转到强制登录确认界面
		LOGIN_FORCE_CONFIRM("loginForceConfirm"),
		
		//跳转到登录页面的struts配置
		TO_LOGIN("toLogin");
		private String value;
		LoginForward(String value_) {
			this.value = value_;
		}
		
		public String value() {
			return this.value;
		}
	}
	
	/**
	 * request的参数
	 */
	public enum Para {
		login_id,
		password,
		verifyCode,
		
		alertStr,
		
		//是否来自cookie
		is_cookie,
		
		//是否在cookie中记住登录状态 
		is_cookie_login_status,
		
		//是否强制登录
		is_force_login,
		
		//自动跳转
		toUrl;
	}
	
	
	public final static String LOGOUT_TYPE = "LOGOUT_TYPE"; 
	
	public enum LogoutType {
		//正常注销
		NOTMAL("1"),
		
		//超时退出
		TIMEOUT("2"),
		
		//强制登录被下线
		FORCE_REPLACE("3"),
		
		//被管理员强制下线
		FORCE_LOGOUT("4");
		
		private String value;
		LogoutType(String value_) {
			this.value = value_;
		}
		
		public String value() {
			return this.value;
		}
	}
	
	/**
	 * 临时session的时间
	 */
	public final static long SESSION_TIMEOUT_SHORT = 60 * 3;
}
