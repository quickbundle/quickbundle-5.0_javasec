//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/org/quickbundle/project/login/RmUserVo.java
//代码生成时,系统时间: 2010-11-27 22:08:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.project.login --> RmUserVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.project.login;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.quickbundle.base.vo.RmValueObject;
import org.quickbundle.third.webservice.TimestampAdapter;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
@XmlType(name = "org.quickbundle.project.login.RmUserVo")
public class RmUserVo extends RmValueObject implements IRmLoginVo{
	
	private String parent_party_id;
	private String parent_party_name;
	private String parent_party_code;
	public String getParent_party_id() {
		return parent_party_id;
	}
	public void setParent_party_id(String parent_party_id) {
		this.parent_party_id = parent_party_id;
	}
	public String getParent_party_name() {
		return parent_party_name;
	}
	public void setParent_party_name(String parent_party_name) {
		this.parent_party_name = parent_party_name;
	}
	public String getParent_party_code() {
		return parent_party_code;
	}
	public void setParent_party_code(String parent_party_code) {
		this.parent_party_code = parent_party_code;
	}


	private List<String> menuList = new ArrayList<String>();
    public List<String> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<String> menuList) {
		this.menuList = menuList;
	}

    //开始rm_code_type的属性
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * name 表示: 姓名
	 * 数据库注释: 
     */
    private String name;
	/**
     * lock_status 表示: 激活/锁定状态
	 * 数据库注释: $RM_LOCK_STATUS=激活、锁定状态{ 0=锁定, 1=激活状态 }
     */
    private String lock_status;
	/**
     * login_id 表示: 登录名
	 * 数据库注释: 
     */
    private String login_id;
	/**
     * password 表示: 密码
	 * 数据库注释: 
     */
    private String password;
	/**
     * authen_type 表示: 认证方式
	 * 数据库注释: 
     */
    private String authen_type;
	/**
     * employee_id 表示: 员工ID
	 * 数据库注释: 
     */
    private String employee_id;
	/**
     * email 表示: 电子邮箱
	 * 数据库注释: 
     */
    private String email;
	/**
     * admin_type 表示: 用户权限类型
	 * 数据库注释: $RM_ADMIN_TYPE=用户权限类型{ 0=临时用户, 1=前台用户, 2=普通用户, 9=超级管理员(admin,一般用于数据初始化) }
     */
    private String admin_type;
	/**
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
	/**
     * login_status 表示: 登录状态
	 * 数据库注释: 
     */
    private String login_status;
	/**
     * last_login_date 表示: 最后登录时间
	 * 数据库注释: 
     */
    private Timestamp last_login_date;
	/**
     * last_login_ip 表示: 最后登录IP
	 * 数据库注释: 
     */
    private String last_login_ip;
	/**
     * login_sum 表示: 累计登录次数
	 * 数据库注释: 
     */
    private int login_sum;
    
    //结束rm_code_type的属性
        
        
    //开始rm_code_type的setter和getter方法
    /**
     * 获得主键
     * 
     * @return 主键
     */
	public String getId(){
		return id;
	}
	
    /**
     * 设置主键
     * 
     * @param id 主键
     */
	public void setId(String id){
		this.id = id;
	}
    /**
     * 获得姓名
     * 
     * @return 姓名
     */
	public String getName(){
		return name;
	}
	
    /**
     * 设置姓名
     * 
     * @param name 姓名
     */
	public void setName(String name){
		this.name = name;
	}
	
    /**
     * 获得激活/锁定状态
     * 数据库注释: $RM_LOCK_STATUS=激活、锁定状态{ 0=锁定, 1=激活状态 }
     * @return 激活/锁定状态
     */
	public String getLock_status(){
		return lock_status;
	}
	
    /**
     * 设置激活/锁定状态
     * 数据库注释: $RM_LOCK_STATUS=激活、锁定状态{ 0=锁定, 1=激活状态 }
     * @param lock_status 激活/锁定状态
     */
	public void setLock_status(String lock_status){
		this.lock_status = lock_status;
	}
	
    /**
     * 获得登录名
     * 
     * @return 登录名
     */
	public String getLogin_id(){
		return login_id;
	}
	
    /**
     * 设置登录名
     * 
     * @param login_id 登录名
     */
	public void setLogin_id(String login_id){
		this.login_id = login_id;
	}
	
    /**
     * 获得密码
     * 
     * @return 密码
     */
	public String getPassword(){
		return password;
	}
	
    /**
     * 设置密码
     * 
     * @param password 密码
     */
	public void setPassword(String password){
		this.password = password;
	}
	
    /**
     * 获得认证方式
     * 
     * @return 认证方式
     */
	public String getAuthen_type(){
		return authen_type;
	}
	
    /**
     * 设置认证方式
     * 
     * @param authen_type 认证方式
     */
	public void setAuthen_type(String authen_type){
		this.authen_type = authen_type;
	}
	
    /**
     * 获得员工ID
     * 
     * @return 员工ID
     */
	public String getEmployee_id(){
		return employee_id;
	}
	
    /**
     * 设置员工ID
     * 
     * @param employee_id 员工ID
     */
	public void setEmployee_id(String employee_id){
		this.employee_id = employee_id;
	}
	/**
     * 获得电子邮箱
     * 
     * @return 电子邮箱
     */
	public String getEmail(){
		return email;
	}
	
    /**
     * 设置电子邮箱
     * 
     * @param email 电子邮箱
     */
	public void setEmail(String email){
		this.email = email;
	}
	
    /**
     * 获得用户权限类型
     * 数据库注释: $RM_ADMIN_TYPE=用户权限类型{ 0=临时用户, 1=前台用户, 2=普通用户, 9=超级管理员(admin,一般用于数据初始化) }
     * @return 用户权限类型
     */
	public String getAdmin_type(){
		return admin_type;
	}
	
    /**
     * 设置用户权限类型
     * 数据库注释: $RM_ADMIN_TYPE=用户权限类型{ 0=临时用户, 1=前台用户, 2=普通用户, 9=超级管理员(admin,一般用于数据初始化) }
     * @param admin_type 用户权限类型
     */
	public void setAdmin_type(String admin_type){
		this.admin_type = admin_type;
	}
	
    /**
     * 获得描述
     * 
     * @return 描述
     */
	public String getDescription(){
		return description;
	}
	
    /**
     * 设置描述
     * 
     * @param description 描述
     */
	public void setDescription(String description){
		this.description = description;
	}
	
    /**
     * 获得登录状态
     * 
     * @return 登录状态
     */
	public String getLogin_status(){
		return login_status;
	}
	
    /**
     * 设置登录状态
     * 
     * @param login_status 登录状态
     */
	public void setLogin_status(String login_status){
		this.login_status = login_status;
	}
	
    /**
     * 获得最后登录时间
     * 
     * @return 最后登录时间
     */
	@XmlJavaTypeAdapter(TimestampAdapter.class)
	public Timestamp getLast_login_date(){
		return last_login_date;
	}
	
    /**
     * 设置最后登录时间
     * 
     * @param last_login_date 最后登录时间
     */
	public void setLast_login_date(Timestamp last_login_date){
		this.last_login_date = last_login_date;
	}
	
    /**
     * 获得最后登录IP
     * 
     * @return 最后登录IP
     */
	public String getLast_login_ip(){
		return last_login_ip;
	}
	
    /**
     * 设置最后登录IP
     * 
     * @param last_login_ip 最后登录IP
     */
	public void setLast_login_ip(String last_login_ip){
		this.last_login_ip = last_login_ip;
	}
	
    /**
     * 获得累计登录次数
     * 
     * @return 累计登录次数
     */
	public int getLogin_sum(){
		return login_sum;
	}
	
    /**
     * 设置累计登录次数
     * 
     * @param login_sum 累计登录次数
     */
	public void setLogin_sum(int login_sum){
		this.login_sum = login_sum;
	}
    //结束rm_code_type的setter和getter方法
    
	

	/********************************************************
	 * 开始定制RmUserVo的扩展登录信息                             
	 ********************************************************/
	/**
	 * 登录失败信息
	 */
	private String loginFailed;
	public String getLoginFailed() {
		return this.loginFailed;
	}
	public void setLoginFailed(String failedInfo) {
		this.loginFailed = failedInfo;
	}
	/**
	 * 登录时间key值
	 */
	public static final String A_LOGIN_TIME = "A_LOGIN_TIME";
	
	/**
	 * 最后操作key值
	 */
	public static final String A_LAST_OPERATION = "A_LAST_OPERATION";
	
	/**
	 * 用户的扩展属性信息
	 */
	private Map<String, String> mAttribute = new HashMap<String, String>();;
    
	/**
	 * 存放用于的扩展属性信息，适用于存放字符串
	 * @return
	 */
	public Map<String, String> getMAttribute() {
		return mAttribute;
	}
	
	/**
	 * 用户的私有缓存
	 */
	private Map<String, Object> mCache = new HashMap<String, Object>();

	/**
	 * 存放用户的私有缓存信息，适用于存放缓存对象
	 * @return
	 */
	public Map<String, Object> getMCache() {
		return mCache;
	}
	
	/**
	 * 获得用户在业务中被引用的id，一般就是id
	 * @return
	 */
	public String getParty_id() {
		return getId();
	}

	/********************************************************
	 * 开始定制RmUserVo的组织结构扩展信息                             
	 ********************************************************/
	//orgauth begin
	/**
	 * 当前所属的组织party_id
	 */
	private String party_id_org;
	
	public String getParty_id_org() {
		return party_id_org;
	}
	public void setParty_id_org(String partyIdOrg) {
		party_id_org = partyIdOrg;
	}
	
	/**
	 * 当前所属的组织party_id
	 */
	private String party_id_org_name;
	
	public String getParty_id_org_name() {
		return party_id_org_name;
	}

	public void setParty_id_org_name(String partyIdOrgName) {
		party_id_org_name = partyIdOrgName;
	}

	/**
	 * 直属的公司party_id列表
	 */
	private String[] party_id_owner;

	/**
	 * 关联的所有Party_id列表（包含了用户所属的顶点组织）
	 */
	private String[] party_id_all;
	
	/**
	 * @return 直属的公司party_id列表
	 */
	public String[] getParty_id_owner() {
		return party_id_owner;
	}
	/**
	 * 设置直属的公司party_id列表
	 * @param partyIdOwner
	 */
	public void setParty_id_owner(String[] partyIdOwner) {
		party_id_owner = partyIdOwner;
	}
	/**
	 * @return 关联的所有Party_id列表（包含了用户所属的顶点组织）
	 */
	public String[] getParty_id_all() {
		return party_id_all;
	}
	/**
	 * 设置关联的所有Party_id列表（包含了用户所属的顶点组织）
	 * @param partyIdAll
	 */
	public void setParty_id_all(String[] partyIdAll) {
		party_id_all = partyIdAll;
	}
	//orgauth end
	
	public static class RmUserSessionVo extends RmUserVo {
		private String clusterNodeId;
		private String sessionId;
		private long creationTime;
		private long lastAccessedTime;
		private long maxInactiveInterval;
		private boolean newSession;
		private String locale;
		/**
		 * @return the clusterNodeId
		 */
		public String getClusterNodeId() {
			return clusterNodeId;
		}
		/**
		 * @param clusterNodeId the clusterNodeId to set
		 */
		public void setClusterNodeId(String clusterNodeId) {
			this.clusterNodeId = clusterNodeId;
		}
		/**
		 * @return the sessionId
		 */
		public String getSessionId() {
			return sessionId;
		}
		/**
		 * @param sessionId the sessionId to set
		 */
		public void setSessionId(String sessionId) {
			this.sessionId = sessionId;
		}
		/**
		 * @return the creationTime
		 */
		public long getCreationTime() {
			return creationTime;
		}
		/**
		 * @param creationTime the creationTime to set
		 */
		public void setCreationTime(long creationTime) {
			this.creationTime = creationTime;
		}
		/**
		 * @return the lastAccessedTime
		 */
		public long getLastAccessedTime() {
			return lastAccessedTime;
		}
		/**
		 * @param lastAccessedTime the lastAccessedTime to set
		 */
		public void setLastAccessedTime(long lastAccessedTime) {
			this.lastAccessedTime = lastAccessedTime;
		}
		/**
		 * @return the maxInactiveInterval
		 */
		public long getMaxInactiveInterval() {
			return maxInactiveInterval;
		}
		/**
		 * @param maxInactiveInterval the maxInactiveInterval to set
		 */
		public void setMaxInactiveInterval(long maxInactiveInterval) {
			this.maxInactiveInterval = maxInactiveInterval;
		}
		/**
		 * @return the newSession
		 */
		public boolean isNewSession() {
			return newSession;
		}
		/**
		 * @param newSession the newSession to set
		 */
		public void setNewSession(boolean newSession) {
			this.newSession = newSession;
		}
		/**
		 * @return the locale
		 */
		public String getLocale() {
			return locale;
		}
		/**
		 * @param locale the locale to set
		 */
		public void setLocale(String locale) {
			this.locale = locale;
		}
		
	}
}