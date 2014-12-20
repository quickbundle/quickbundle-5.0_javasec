//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmpartyrole/vo/RmPartyRoleVo.java
//代码生成时,系统时间: 2010-11-27 22:08:39
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyrole.vo --> RmPartyRoleVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:39 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyrole.vo;


import java.sql.Timestamp;


import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmPartyRoleVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * owner_party_id 表示: 团体ID
	 * 数据库注释: 
     */
    private String owner_party_id;
	/**
     * role_id 表示: 角色ID
	 * 数据库注释: 
     */
    private String role_id;
	/**
     * owner_org_id 表示: 所属组织ID
	 * 数据库注释: 关联角色时，指定所属的组织ID
     */
    private String owner_org_id;
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
     * 获得团体ID
     * 
     * @return 团体ID
     */
	public String getOwner_party_id(){
		return owner_party_id;
	}
	
    /**
     * 设置团体ID
     * 
     * @param owner_party_id 团体ID
     */
	public void setOwner_party_id(String owner_party_id){
		this.owner_party_id = owner_party_id;
	}
	
    /**
     * 获得角色ID
     * 
     * @return 角色ID
     */
	public String getRole_id(){
		return role_id;
	}
	
    /**
     * 设置角色ID
     * 
     * @param role_id 角色ID
     */
	public void setRole_id(String role_id){
		this.role_id = role_id;
	}
	
    /**
     * 获得所属组织ID
     * 数据库注释: 关联角色时，指定所属的组织ID
     * @return 所属组织ID
     */
	public String getOwner_org_id(){
		return owner_org_id;
	}
	
    /**
     * 设置所属组织ID
     * 数据库注释: 关联角色时，指定所属的组织ID
     * @param owner_org_id 所属组织ID
     */
	public void setOwner_org_id(String owner_org_id){
		this.owner_org_id = owner_org_id;
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