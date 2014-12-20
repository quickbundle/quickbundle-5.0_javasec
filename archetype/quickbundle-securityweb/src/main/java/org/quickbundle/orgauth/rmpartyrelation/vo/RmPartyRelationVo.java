//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartyrelation/vo/RmPartyRelationVo.java
//代码生成时,系统时间: 2010-11-28 17:40:30
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyrelation.vo --> RmPartyRelationVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:30 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyrelation.vo;


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

public class RmPartyRelationVo extends RmValueObject{
    
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
     * parent_party_id 表示: 父团体ID
	 * 数据库注释: 
     */
    private String parent_party_id;
	/**
     * child_party_id 表示: 子团体ID
	 * 数据库注释: 
     */
    private String child_party_id;
	/**
     * parent_party_code 表示: 父团体全编码
	 * 数据库注释: 
     */
    private String parent_party_code;
	/**
     * child_party_code 表示: 子团体全编码
	 * 数据库注释: 
     */
    private String child_party_code;
	/**
     * child_party_level 表示: 子团体级别
	 * 数据库注释: 
     */
    private int child_party_level = -1; //默认是-1，表示未作明确指定
	/**
     * child_is_main_relation 表示: 子团体是否主关系
	 * 数据库注释: 
     */
    private String child_is_main_relation;
	/**
     * order_code 表示: 排序编码
	 * 数据库注释: 
     */
    private String order_code;
	/**
     * parent_party_name 表示: 父团体别名
	 * 数据库注释: 
     */
    private String parent_party_name;
	/**
     * child_party_name 表示: 子团体别名
	 * 数据库注释: 
     */
    private String child_party_name;
	/**
     * parent_party_type_id 表示: 父团体类型ID
	 * 数据库注释: 
     */
    private String parent_party_type_id;
	/**
     * child_party_type_id 表示: 子团体类型ID
	 * 数据库注释: 
     */
    private String child_party_type_id;
	/**
     * child_is_leaf 表示: 子团体是否末节点
	 * 数据库注释: 
     */
    private String child_is_leaf;
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
     * 获得父团体ID
     * 
     * @return 父团体ID
     */
	public String getParent_party_id(){
		return parent_party_id;
	}
	
    /**
     * 设置父团体ID
     * 
     * @param parent_party_id 父团体ID
     */
	public void setParent_party_id(String parent_party_id){
		this.parent_party_id = parent_party_id;
	}
	
    /**
     * 获得子团体ID
     * 
     * @return 子团体ID
     */
	public String getChild_party_id(){
		return child_party_id;
	}
	
    /**
     * 设置子团体ID
     * 
     * @param child_party_id 子团体ID
     */
	public void setChild_party_id(String child_party_id){
		this.child_party_id = child_party_id;
	}
	
    /**
     * 获得父团体全编码
     * 
     * @return 父团体全编码
     */
	public String getParent_party_code(){
		return parent_party_code;
	}
	
    /**
     * 设置父团体全编码
     * 
     * @param parent_party_code 父团体全编码
     */
	public void setParent_party_code(String parent_party_code){
		this.parent_party_code = parent_party_code;
	}
	
    /**
     * 获得子团体全编码
     * 
     * @return 子团体全编码
     */
	public String getChild_party_code(){
		return child_party_code;
	}
	
    /**
     * 设置子团体全编码
     * 
     * @param child_party_code 子团体全编码
     */
	public void setChild_party_code(String child_party_code){
		this.child_party_code = child_party_code;
	}
	
    /**
     * 获得子团体级别
     * 
     * @return 子团体级别
     */
	public int getChild_party_level(){
		return child_party_level;
	}
	
    /**
     * 设置子团体级别
     * 
     * @param child_party_level 子团体级别
     */
	public void setChild_party_level(int child_party_level){
		this.child_party_level = child_party_level;
	}
	
    /**
     * 获得子团体是否主关系
     * 
     * @return 子团体是否主关系
     */
	public String getChild_is_main_relation(){
		return child_is_main_relation;
	}
	
    /**
     * 设置子团体是否主关系
     * 
     * @param child_is_main_relation 子团体是否主关系
     */
	public void setChild_is_main_relation(String child_is_main_relation){
		this.child_is_main_relation = child_is_main_relation;
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
     * 获得父团体别名
     * 
     * @return 父团体别名
     */
	public String getParent_party_name(){
		return parent_party_name;
	}
	
    /**
     * 设置父团体别名
     * 
     * @param parent_party_name 父团体别名
     */
	public void setParent_party_name(String parent_party_name){
		this.parent_party_name = parent_party_name;
	}
	
    /**
     * 获得子团体别名
     * 
     * @return 子团体别名
     */
	public String getChild_party_name(){
		return child_party_name;
	}
	
    /**
     * 设置子团体别名
     * 
     * @param child_party_name 子团体别名
     */
	public void setChild_party_name(String child_party_name){
		this.child_party_name = child_party_name;
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
     * 获得子团体是否末节点
     * 
     * @return 子团体是否末节点
     */
	public String getChild_is_leaf(){
		return child_is_leaf;
	}
	
    /**
     * 设置子团体是否末节点
     * 
     * @param child_is_leaf 子团体是否末节点
     */
	public void setChild_is_leaf(String child_is_leaf){
		this.child_is_leaf = child_is_leaf;
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