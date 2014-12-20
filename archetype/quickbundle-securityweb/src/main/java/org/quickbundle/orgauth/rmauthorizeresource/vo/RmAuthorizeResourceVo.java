//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresource/vo/RmAuthorizeResourceVo.java
//代码生成时,系统时间: 2010-11-27 22:08:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresource.vo --> RmAuthorizeResourceVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresource.vo;


import java.sql.Timestamp;


import org.quickbundle.base.vo.RmValueObject;

import org.quickbundle.orgauth.itf.vo.IRmAuthorizeResourceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAuthorizeResourceVo extends RmValueObject implements IRmAuthorizeResourceVo{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * authorize_id 表示: 权限类别ID
	 * 数据库注释: 
     */
    private String authorize_id;
	/**
     * old_resource_id 表示: 资源原始ID
	 * 数据库注释: 
     */
    private String old_resource_id;
	/**
     * default_access 表示: 默认直接访问
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String default_access;
	/**
     * default_is_affix_data 表示: 默认有附加数据
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String default_is_affix_data;
	/**
     * default_is_recursive 表示: 默认递归传播权限
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String default_is_recursive;
	/**
     * default_access_type 表示: 默认访问方式代码
	 * 数据库注释: 
     */
    private String default_access_type;
	/**
     * total_code 表示: 树形编码
	 * 数据库注释: 如果资源是树，可用编码体系存储树形关系
     */
    private String total_code;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
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
     * 获得权限类别ID
     * 
     * @return 权限类别ID
     */
	public String getAuthorize_id(){
		return authorize_id;
	}
	
    /**
     * 设置权限类别ID
     * 
     * @param authorize_id 权限类别ID
     */
	public void setAuthorize_id(String authorize_id){
		this.authorize_id = authorize_id;
	}
	
    /**
     * 获得资源原始ID
     * 
     * @return 资源原始ID
     */
	public String getOld_resource_id(){
		return old_resource_id;
	}
	
    /**
     * 设置资源原始ID
     * 
     * @param old_resource_id 资源原始ID
     */
	public void setOld_resource_id(String old_resource_id){
		this.old_resource_id = old_resource_id;
	}
	
    /**
     * 获得默认直接访问
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 默认直接访问
     */
	public String getDefault_access(){
		return default_access;
	}
	
    /**
     * 设置默认直接访问
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param default_access 默认直接访问
     */
	public void setDefault_access(String default_access){
		this.default_access = default_access;
	}
	
    /**
     * 获得默认有附加数据
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 默认有附加数据
     */
	public String getDefault_is_affix_data(){
		return default_is_affix_data;
	}
	
    /**
     * 设置默认有附加数据
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param default_is_affix_data 默认有附加数据
     */
	public void setDefault_is_affix_data(String default_is_affix_data){
		this.default_is_affix_data = default_is_affix_data;
	}
	
    /**
     * 获得默认递归传播权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 默认递归传播权限
     */
	public String getDefault_is_recursive(){
		return default_is_recursive;
	}
	
    /**
     * 设置默认递归传播权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param default_is_recursive 默认递归传播权限
     */
	public void setDefault_is_recursive(String default_is_recursive){
		this.default_is_recursive = default_is_recursive;
	}
	
    /**
     * 获得默认访问方式代码
     * 
     * @return 默认访问方式代码
     */
	public String getDefault_access_type(){
		return default_access_type;
	}
	
    /**
     * 设置默认访问方式代码
     * 
     * @param default_access_type 默认访问方式代码
     */
	public void setDefault_access_type(String default_access_type){
		this.default_access_type = default_access_type;
	}
	
    /**
     * 获得树形编码
     * 数据库注释: 如果资源是树，可用编码体系存储树形关系
     * @return 树形编码
     */
	public String getTotal_code(){
		return total_code;
	}
	
    /**
     * 设置树形编码
     * 数据库注释: 如果资源是树，可用编码体系存储树形关系
     * @param total_code 树形编码
     */
	public void setTotal_code(String total_code){
		this.total_code = total_code;
	}
	
    /**
     * 获得名称
     * 
     * @return 名称
     */
	public String getName(){
		return name;
	}
	
    /**
     * 设置名称
     * 
     * @param name 名称
     */
	public void setName(String name){
		this.name = name;
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
    

	public String getAccess_type() {
		return this.default_access_type;
	}

	public String getAuthorize_resource_id() {
		return this.id;
	}

	public String getIs_affix_data() {
		return this.default_is_affix_data;
	}

	public String getIs_recursive() {
		return this.default_is_recursive;
	}
    
}