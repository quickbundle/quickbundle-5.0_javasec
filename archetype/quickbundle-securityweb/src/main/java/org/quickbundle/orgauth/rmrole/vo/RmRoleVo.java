//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmrole/vo/RmRoleVo.java
//代码生成时,系统时间: 2010-11-27 22:08:38
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmrole.vo --> RmRoleVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:38 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmrole.vo;


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

public class RmRoleVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * role_code 表示: 角色编码
	 * 数据库注释: 
     */
    private String role_code;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
	/**
     * enable_status 表示: 启用/禁用
	 * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 1=启用, 0=禁用 }
     */
    private String enable_status;
	/**
     * is_system_level 表示: 是否全局角色
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 } 如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填
     */
    private String is_system_level;
	/**
     * owner_org_id 表示: 所属组织ID
	 * 数据库注释: 
     */
    private String owner_org_id;
	/**
     * owner_org_name 表示: 所属组织名称
	 * 数据库注释: 
     */
    private String owner_org_id_name;

	public String getOwner_org_id_name() {
		return owner_org_id_name;
	}

	public void setOwner_org_id_name(String ownerOrgIdName) {
		owner_org_id_name = ownerOrgIdName;
	}

	/**
     * is_recursive 表示: 是否传播给下级
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_recursive;
	/**
     * matrix_code 表示: 不相容矩阵编码
	 * 数据库注释: 
     */
    private String matrix_code;
	/**
     * description 表示: 角色描述
	 * 数据库注释: 
     */
    private String description;
	/**
     * function_permission 表示: 功能权限_简单
	 * 数据库注释: 逗号分隔
     */
    private String function_permission;
	/**
     * data_permission 表示: 数据权限_简单
	 * 数据库注释: 逗号分隔
     */
    private String data_permission;
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
     * 获得角色编码
     * 
     * @return 角色编码
     */
	public String getRole_code(){
		return role_code;
	}
	
    /**
     * 设置角色编码
     * 
     * @param role_code 角色编码
     */
	public void setRole_code(String role_code){
		this.role_code = role_code;
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
     * 获得启用/禁用
     * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 1=启用, 0=禁用 }
     * @return 启用/禁用
     */
	public String getEnable_status(){
		return enable_status;
	}
	
    /**
     * 设置启用/禁用
     * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 1=启用, 0=禁用 }
     * @param enable_status 启用/禁用
     */
	public void setEnable_status(String enable_status){
		this.enable_status = enable_status;
	}
	
    /**
     * 获得是否全局角色
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 } 如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填
     * @return 是否全局角色
     */
	public String getIs_system_level(){
		return is_system_level;
	}
	
    /**
     * 设置是否全局角色
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 } 如果不是全局角色，则OWNER_ORG_ID、IS_RECURSIVE必填
     * @param is_system_level 是否全局角色
     */
	public void setIs_system_level(String is_system_level){
		this.is_system_level = is_system_level;
	}
	
    /**
     * 获得所属组织ID
     * 
     * @return 所属组织ID
     */
	public String getOwner_org_id(){
		return owner_org_id;
	}
	
    /**
     * 设置所属组织ID
     * 
     * @param owner_org_id 所属组织ID
     */
	public void setOwner_org_id(String owner_org_id){
		this.owner_org_id = owner_org_id;
	}
	
    /**
     * 获得是否传播给下级
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 是否传播给下级
     */
	public String getIs_recursive(){
		return is_recursive;
	}
	
    /**
     * 设置是否传播给下级
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_recursive 是否传播给下级
     */
	public void setIs_recursive(String is_recursive){
		this.is_recursive = is_recursive;
	}
	
    /**
     * 获得不相容矩阵编码
     * 
     * @return 不相容矩阵编码
     */
	public String getMatrix_code(){
		return matrix_code;
	}
	
    /**
     * 设置不相容矩阵编码
     * 
     * @param matrix_code 不相容矩阵编码
     */
	public void setMatrix_code(String matrix_code){
		this.matrix_code = matrix_code;
	}
	
    /**
     * 获得角色描述
     * 
     * @return 角色描述
     */
	public String getDescription(){
		return description;
	}
	
    /**
     * 设置角色描述
     * 
     * @param description 角色描述
     */
	public void setDescription(String description){
		this.description = description;
	}
	
    /**
     * 获得功能权限_简单
     * 数据库注释: 逗号分隔
     * @return 功能权限_简单
     */
	public String getFunction_permission(){
		return function_permission;
	}
	
    /**
     * 设置功能权限_简单
     * 数据库注释: 逗号分隔
     * @param function_permission 功能权限_简单
     */
	public void setFunction_permission(String function_permission){
		this.function_permission = function_permission;
	}
	
    /**
     * 获得数据权限_简单
     * 数据库注释: 逗号分隔
     * @return 数据权限_简单
     */
	public String getData_permission(){
		return data_permission;
	}
	
    /**
     * 设置数据权限_简单
     * 数据库注释: 逗号分隔
     * @param data_permission 数据权限_简单
     */
	public void setData_permission(String data_permission){
		this.data_permission = data_permission;
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