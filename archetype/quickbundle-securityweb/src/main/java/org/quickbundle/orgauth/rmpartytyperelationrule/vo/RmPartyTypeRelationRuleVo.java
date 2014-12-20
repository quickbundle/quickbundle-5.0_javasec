//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartytyperelationrule/vo/RmPartyTypeRelationRuleVo.java
//代码生成时,系统时间: 2010-11-29 10:08:24
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartytyperelationrule.vo --> RmPartyTypeRelationRuleVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-29 10:08:24 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartytyperelationrule.vo;


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

public class RmPartyTypeRelationRuleVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * party_view_id 表示: 团体视图ID
	 * 数据库注释: 
     */
    private String party_view_id;
	/**
     * parent_party_type_id 表示: 父团体类型ID
	 * 数据库注释: 
     */
    private String parent_party_type_id;
    private String parent_party_type_id_name;
    
	public String getParent_party_type_id_name() {
		return parent_party_type_id_name;
	}

	public void setParent_party_type_id_name(String parentPartyTypeIdName) {
		parent_party_type_id_name = parentPartyTypeIdName;
	}

	/**
     * child_party_type_id 表示: 子团体类型ID
	 * 数据库注释: 
     */
    private String child_party_type_id;
    private String child_party_type_id_name;

	public String getChild_party_type_id_name() {
		return child_party_type_id_name;
	}

	public void setChild_party_type_id_name(String childPartyTypeIdName) {
		child_party_type_id_name = childPartyTypeIdName;
	}

	/**
     * rule_desc 表示: 规则描述
	 * 数据库注释: 
     */
    private String rule_desc;
	/**
     * is_insert_child_party 表示: 可新增子团体
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_insert_child_party;
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
     * 获得团体视图ID
     * 
     * @return 团体视图ID
     */
	public String getParty_view_id(){
		return party_view_id;
	}
	
    /**
     * 设置团体视图ID
     * 
     * @param party_view_id 团体视图ID
     */
	public void setParty_view_id(String party_view_id){
		this.party_view_id = party_view_id;
	}
	
    /**
     * 获得父团体类型ID
     * 
     * @return 父团体类型ID
     */
	public String getParent_party_type_id(){
		return parent_party_type_id;
	}
	
    /**
     * 设置父团体类型ID
     * 
     * @param parent_party_type_id 父团体类型ID
     */
	public void setParent_party_type_id(String parent_party_type_id){
		this.parent_party_type_id = parent_party_type_id;
	}
	
    /**
     * 获得子团体类型ID
     * 
     * @return 子团体类型ID
     */
	public String getChild_party_type_id(){
		return child_party_type_id;
	}
	
    /**
     * 设置子团体类型ID
     * 
     * @param child_party_type_id 子团体类型ID
     */
	public void setChild_party_type_id(String child_party_type_id){
		this.child_party_type_id = child_party_type_id;
	}
	
    /**
     * 获得规则描述
     * 
     * @return 规则描述
     */
	public String getRule_desc(){
		return rule_desc;
	}
	
    /**
     * 设置规则描述
     * 
     * @param rule_desc 规则描述
     */
	public void setRule_desc(String rule_desc){
		this.rule_desc = rule_desc;
	}
	
    /**
     * 获得可新增子团体
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 可新增子团体
     */
	public String getIs_insert_child_party(){
		return is_insert_child_party;
	}
	
    /**
     * 设置可新增子团体
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_insert_child_party 可新增子团体
     */
	public void setIs_insert_child_party(String is_insert_child_party){
		this.is_insert_child_party = is_insert_child_party;
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