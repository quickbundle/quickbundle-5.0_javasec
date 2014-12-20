//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuser/vo/RmUserVo.java
//代码生成时,系统时间: 2010-11-27 22:08:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuser.vo --> RmUserVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuser.vo;


import java.sql.Timestamp;

import org.quickbundle.project.login.IRmLoginVo;
import org.quickbundle.tools.helper.xml.RmXmlHelper;

import org.quickbundle.orgauth.custom.RmCustomUserVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmUserVo extends RmCustomUserVo implements IRmLoginVo{

	/**
	 * 获取根元素的属性值
	 * 
	 * @param attributeOfRoot
	 * @return
	 */
	public String valueOfCustomXml(String attributeOfRoot) {
		if(custom_xml == null || custom_xml.length() == 0) {
			return "";
		}
		return RmXmlHelper.getDocumentFromString(custom_xml).valueOf("/user/@" + attributeOfRoot);
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
     * organization_id 表示: 所属组织机构
	 * 数据库注释: 
     */
    private String organization_id;
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
     * agent_status 表示: 代理状态
	 * 数据库注释: $RM_AGENT_STATUS=代理状态{ 0=未代理, 1=已代理 }
     */
    private String agent_status;
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
	/**
     * last_custom_css 表示: 最后定制样式
	 * 数据库注释: 
     */
    private String last_custom_css;
	/**
     * is_affix 表示: 有无附件
	 * 数据库注释: 
     */
    private String is_affix;
	/**
     * function_permission 表示: 功能权限
	 * 数据库注释: 逗号分隔
     */
    private String function_permission;
	/**
     * data_permission 表示: 数据权限
	 * 数据库注释: 逗号分隔
     */
    private String data_permission;
	/**
     * custom1 表示: 自定义1
	 * 数据库注释: 
     */
    private String custom1;
	/**
     * custom2 表示: 自定义2
	 * 数据库注释: 
     */
    private String custom2;
	/**
     * custom3 表示: 自定义3
	 * 数据库注释: 
     */
    private String custom3;
	/**
     * custom4 表示: 自定义4
	 * 数据库注释: 
     */
    private String custom4;
	/**
     * custom5 表示: 自定义5
	 * 数据库注释: 
     */
    private String custom5;
	/**
     * custom_xml 表示: 扩展XML
	 * 数据库注释: 
     */
    private String custom_xml;
	/**
     * usable_status 表示: 数据可用状态
	 * 数据库注释: 
     */
    private String usable_status;
	/**
     * modify_date 表示: 修改日期
	 * 数据库注释: 
     */
    private Timestamp modify_date;
	/**
     * modify_ip 表示: 修改IP
	 * 数据库注释: 
     */
    private String modify_ip;
	/**
     * modify_user_id 表示: 修改用户ID
	 * 数据库注释: 
     */
    private String modify_user_id;        
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
     * 获得所属组织机构
     * 
     * @return 所属组织机构
     */
	public String getOrganization_id(){
		return organization_id;
	}
	
    /**
     * 设置所属组织机构
     * 
     * @param organization_id 所属组织机构
     */
	public void setOrganization_id(String organization_id){
		this.organization_id = organization_id;
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
     * 获得代理状态
     * 数据库注释: $RM_AGENT_STATUS=代理状态{ 0=未代理, 1=已代理 }
     * @return 代理状态
     */
	public String getAgent_status(){
		return agent_status;
	}
	
    /**
     * 设置代理状态
     * 数据库注释: $RM_AGENT_STATUS=代理状态{ 0=未代理, 1=已代理 }
     * @param agent_status 代理状态
     */
	public void setAgent_status(String agent_status){
		this.agent_status = agent_status;
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
	
    /**
     * 获得最后定制样式
     * 
     * @return 最后定制样式
     */
	public String getLast_custom_css(){
		return last_custom_css;
	}
	
    /**
     * 设置最后定制样式
     * 
     * @param last_custom_css 最后定制样式
     */
	public void setLast_custom_css(String last_custom_css){
		this.last_custom_css = last_custom_css;
	}
	
    /**
     * 获得有无附件
     * 
     * @return 有无附件
     */
	public String getIs_affix(){
		return is_affix;
	}
	
    /**
     * 设置有无附件
     * 
     * @param is_affix 有无附件
     */
	public void setIs_affix(String is_affix){
		this.is_affix = is_affix;
	}
	
    /**
     * 获得功能权限
     * 数据库注释: 逗号分隔
     * @return 功能权限
     */
	public String getFunction_permission(){
		return function_permission;
	}
	
    /**
     * 设置功能权限
     * 数据库注释: 逗号分隔
     * @param function_permission 功能权限
     */
	public void setFunction_permission(String function_permission){
		this.function_permission = function_permission;
	}
	
    /**
     * 获得数据权限
     * 数据库注释: 逗号分隔
     * @return 数据权限
     */
	public String getData_permission(){
		return data_permission;
	}
	
    /**
     * 设置数据权限
     * 数据库注释: 逗号分隔
     * @param data_permission 数据权限
     */
	public void setData_permission(String data_permission){
		this.data_permission = data_permission;
	}
	
    /**
     * 获得自定义1
     * 
     * @return 自定义1
     */
	public String getCustom1(){
		return custom1;
	}
	
    /**
     * 设置自定义1
     * 
     * @param custom1 自定义1
     */
	public void setCustom1(String custom1){
		this.custom1 = custom1;
	}
	
    /**
     * 获得自定义2
     * 
     * @return 自定义2
     */
	public String getCustom2(){
		return custom2;
	}
	
    /**
     * 设置自定义2
     * 
     * @param custom2 自定义2
     */
	public void setCustom2(String custom2){
		this.custom2 = custom2;
	}
	
    /**
     * 获得自定义3
     * 
     * @return 自定义3
     */
	public String getCustom3(){
		return custom3;
	}
	
    /**
     * 设置自定义3
     * 
     * @param custom3 自定义3
     */
	public void setCustom3(String custom3){
		this.custom3 = custom3;
	}
	
    /**
     * 获得自定义4
     * 
     * @return 自定义4
     */
	public String getCustom4(){
		return custom4;
	}
	
    /**
     * 设置自定义4
     * 
     * @param custom4 自定义4
     */
	public void setCustom4(String custom4){
		this.custom4 = custom4;
	}
	
    /**
     * 获得自定义5
     * 
     * @return 自定义5
     */
	public String getCustom5(){
		return custom5;
	}
	
    /**
     * 设置自定义5
     * 
     * @param custom5 自定义5
     */
	public void setCustom5(String custom5){
		this.custom5 = custom5;
	}
	
    /**
     * 获得扩展XML
     * 
     * @return 扩展XML
     */
	public String getCustom_xml(){
		return custom_xml;
	}
	
    /**
     * 设置扩展XML
     * 
     * @param custom_xml 扩展XML
     */
	public void setCustom_xml(String custom_xml){
		this.custom_xml = custom_xml;
	}
	
    /**
     * 获得数据可用状态
     * 
     * @return 数据可用状态
     */
	public String getUsable_status(){
		return usable_status;
	}
	
    /**
     * 设置数据可用状态
     * 
     * @param usable_status 数据可用状态
     */
	public void setUsable_status(String usable_status){
		this.usable_status = usable_status;
	}
	
    /**
     * 获得修改日期
     * 
     * @return 修改日期
     */
	public Timestamp getModify_date(){
		return modify_date;
	}
	
    /**
     * 设置修改日期
     * 
     * @param modify_date 修改日期
     */
	public void setModify_date(Timestamp modify_date){
		this.modify_date = modify_date;
	}
	
    /**
     * 获得修改IP
     * 
     * @return 修改IP
     */
	public String getModify_ip(){
		return modify_ip;
	}
	
    /**
     * 设置修改IP
     * 
     * @param modify_ip 修改IP
     */
	public void setModify_ip(String modify_ip){
		this.modify_ip = modify_ip;
	}
	
    /**
     * 获得修改用户ID
     * 
     * @return 修改用户ID
     */
	public String getModify_user_id(){
		return modify_user_id;
	}
	
    /**
     * 设置修改用户ID
     * 
     * @param modify_user_id 修改用户ID
     */
	public void setModify_user_id(String modify_user_id){
		this.modify_user_id = modify_user_id;
	}

    //结束rm_code_type的setter和getter方法
    
}