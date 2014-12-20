//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmfunctionnode/vo/RmFunctionNodeVo.java
//代码生成时,系统时间: 2010-11-27 22:08:40
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmfunctionnode.vo --> RmFunctionNodeVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:40 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmfunctionnode.vo;


import java.sql.Timestamp;

import org.quickbundle.base.vo.RmValueObject;

import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.itf.vo.IRmAuthorizeResourceVo;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmFunctionNodeVo extends RmValueObject implements IRmAuthorizeResourceVo{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * node_type 表示: 类型
	 * 数据库注释: $RM_FUNCTION_NODE_TYPE=功能节点类型{ 1=子系统、一级模块, 2=子模块、功能, 3=页面按钮 }
     */
    private String node_type;
	/**
     * function_property 表示: 功能性质
	 * 数据库注释: $FUNCTION_PROPERTY=功能性质{ 0=可执行功能节点, 1=虚功能节点, 2=可执行功能帧 }
     */
    private String function_property;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
	/**
     * enable_status 表示: 启用/禁用状态
	 * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 0=禁用, 1=启用 }
     */
    private String enable_status;
	/**
     * total_code 表示: 完全编码
	 * 数据库注释: 
     */
    private String total_code;
	/**
     * order_code 表示: 排序编码
	 * 数据库注释: 
     */
    private String order_code;
	/**
     * funcnode_authorize_type 表示: 权限类型
	 * 数据库注释: $RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{ 0=超级管理员初始化节点, 1=普通节点 }
     */
    private String funcnode_authorize_type;
	/**
     * description 表示: 功能说明
	 * 数据库注释: 
     */
    private String description;
	/**
     * data_value 表示: URL值
	 * 数据库注释: 
     */
    private String data_value;
	/**
     * pattern_value 表示: 正则匹配
	 * 数据库注释: 
     */
    private String pattern_value;
	/**
     * is_leaf 表示: 是否末点
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_leaf;
	/**
     * icon 表示: 图片
	 * 数据库注释: 
     */
    private String icon;
	/**
     * help_name 表示: 帮助文件名
	 * 数据库注释: 
     */
    private String help_name;
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
     * 获得类型
     * 数据库注释: $RM_FUNCTION_NODE_TYPE=功能节点类型{ 1=子系统、一级模块, 2=子模块、功能, 3=页面按钮 }
     * @return 类型
     */
	public String getNode_type(){
		return node_type;
	}
	
    /**
     * 设置类型
     * 数据库注释: $RM_FUNCTION_NODE_TYPE=功能节点类型{ 1=子系统、一级模块, 2=子模块、功能, 3=页面按钮 }
     * @param node_type 类型
     */
	public void setNode_type(String node_type){
		this.node_type = node_type;
	}
	
    /**
     * 获得功能性质
     * 数据库注释: $FUNCTION_PROPERTY=功能性质{ 0=可执行功能节点, 1=虚功能节点, 2=可执行功能帧 }
     * @return 功能性质
     */
	public String getFunction_property(){
		return function_property;
	}
	
    /**
     * 设置功能性质
     * 数据库注释: $FUNCTION_PROPERTY=功能性质{ 0=可执行功能节点, 1=虚功能节点, 2=可执行功能帧 }
     * @param function_property 功能性质
     */
	public void setFunction_property(String function_property){
		this.function_property = function_property;
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
     * 获得启用/禁用状态
     * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 0=禁用, 1=启用 }
     * @return 启用/禁用状态
     */
	public String getEnable_status(){
		return enable_status;
	}
	
    /**
     * 设置启用/禁用状态
     * 数据库注释: $RM_ENABLE_STATUS=启用、禁用{ 0=禁用, 1=启用 }
     * @param enable_status 启用/禁用状态
     */
	public void setEnable_status(String enable_status){
		this.enable_status = enable_status;
	}
	
    /**
     * 获得完全编码
     * 
     * @return 完全编码
     */
	public String getTotal_code(){
		return total_code;
	}
	
    /**
     * 设置完全编码
     * 
     * @param total_code 完全编码
     */
	public void setTotal_code(String total_code){
		this.total_code = total_code;
	}
	
    /**
     * 获得排序编码
     * 
     * @return 排序编码
     */
	public String getOrder_code(){
		return order_code;
	}
	
    /**
     * 设置排序编码
     * 
     * @param order_code 排序编码
     */
	public void setOrder_code(String order_code){
		this.order_code = order_code;
	}
	
    /**
     * 获得权限类型
     * 数据库注释: $RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{ 0=超级管理员初始化节点, 1=普通节点 }
     * @return 权限类型
     */
	public String getFuncnode_authorize_type(){
		return funcnode_authorize_type;
	}
	
    /**
     * 设置权限类型
     * 数据库注释: $RM_FUNCNODE_AUTHORIZE_TYPE=功能节点权限类型{ 0=超级管理员初始化节点, 1=普通节点 }
     * @param funcnode_authorize_type 权限类型
     */
	public void setFuncnode_authorize_type(String funcnode_authorize_type){
		this.funcnode_authorize_type = funcnode_authorize_type;
	}
	
    /**
     * 获得功能说明
     * 
     * @return 功能说明
     */
	public String getDescription(){
		return description;
	}
	
    /**
     * 设置功能说明
     * 
     * @param description 功能说明
     */
	public void setDescription(String description){
		this.description = description;
	}
	
    /**
     * 获得URL值
     * 
     * @return URL值
     */
	public String getData_value(){
		return data_value;
	}
	
    /**
     * 设置URL值
     * 
     * @param data_value URL值
     */
	public void setData_value(String data_value){
		this.data_value = data_value;
	}
	
    /**
     * 获得正则匹配
     * 
     * @return 正则匹配
     */
	public String getPattern_value(){
		return pattern_value;
	}
	
    /**
     * 设置正则匹配
     * 
     * @param pattern_value 正则匹配
     */
	public void setPattern_value(String pattern_value){
		this.pattern_value = pattern_value;
	}
	
    /**
     * 获得是否末点
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 是否末点
     */
	public String getIs_leaf(){
		return is_leaf;
	}
	
    /**
     * 设置是否末点
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_leaf 是否末点
     */
	public void setIs_leaf(String is_leaf){
		this.is_leaf = is_leaf;
	}
	
    /**
     * 获得图片
     * 
     * @return 图片
     */
	public String getIcon(){
		return icon;
	}
	
    /**
     * 设置图片
     * 
     * @param icon 图片
     */
	public void setIcon(String icon){
		this.icon = icon;
	}
	
    /**
     * 获得帮助文件名
     * 
     * @return 帮助文件名
     */
	public String getHelp_name(){
		return help_name;
	}
	
    /**
     * 设置帮助文件名
     * 
     * @param help_name 帮助文件名
     */
	public void setHelp_name(String help_name){
		this.help_name = help_name;
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
 

	/**
	 * @return RM_AUTHORIZE_RESOURCE的id
	 */
	public String getAuthorize_resource_id() {
		return null;
	}
	
    /**
     * @return RM_AUTHORIZE的id
     */
    public String getAuthorize_id() {
    	return IOrgauthConstants.Authorize.FUNCTION_NODE.id();
    }
    
    /**
     * @return 资源原始id
     */
    public String getOld_resource_id() {
    	return this.total_code;
    }
    
    /**
     * @return 默认能否直接访问（公开权限）
     */
    public String getDefault_access() {
    	return IOrgauthConstants.RM_NO;
    }
    
    /**
     * @return 有无附加数据
     */
    public String getIs_affix_data() {
    	return IOrgauthConstants.RM_NO;
    }
    
    /**
     * @return 递归传播权限
     */
    public String getIs_recursive() {
    	return IOrgauthConstants.RM_NO;
    }
    
    /**
     * @return 访问方式代码
     */
    public String getAccess_type() {
    	return "1";
    }
}