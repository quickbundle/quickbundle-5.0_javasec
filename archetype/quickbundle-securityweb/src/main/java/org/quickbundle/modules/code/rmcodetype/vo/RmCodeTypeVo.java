//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/vo/RmCodeTypeVo.java
//代码生成时,系统时间: 2010-04-08 21:15:46.031
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.vo --> RmCodeTypeVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:46.015 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.vo;


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

public class RmCodeTypeVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库中的注释: 
     */
    private String id;
    /**数据数量*/
    private String data_sum;
    /**获得数据数量*/
	public String getData_sum() {
		return data_sum;
	}
	/**设置数据数量*/
	public void setData_sum(String dataSum) {
		data_sum = dataSum;
	}

	/**
     * type_keyword 表示: 类型关键字
	 * 数据库中的注释: 
     */
    private String type_keyword;
	/**
     * name 表示: 编码类型名称
	 * 数据库中的注释: 
     */
    private String name;
	/**
     * remark 表示: 备注
	 * 数据库中的注释: 
     */
    private String remark;
	/**
     * usable_status 表示: 数据可用状态
	 * 数据库中的注释: 
     */
    private String usable_status;
	/**
     * create_date 表示: 创建日期
	 * 数据库中的注释: 
     */
    private Timestamp create_date;
	/**
     * create_ip 表示: 创建IP
	 * 数据库中的注释: 
     */
    private String create_ip;
	/**
     * create_user_id 表示: 创建用户ID
	 * 数据库中的注释: 
     */
    private String create_user_id;
	/**
     * modify_date 表示: 修改日期
	 * 数据库中的注释: 
     */
    private Timestamp modify_date;
	/**
     * modify_ip 表示: 修改IP
	 * 数据库中的注释: 
     */
    private String modify_ip;
	/**
     * modify_user_id 表示: 修改用户ID
	 * 数据库中的注释: 
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
     * 获得类型关键字
     * 
     * @return 类型关键字
     */
	public String getType_keyword(){
		return type_keyword;
	}
	
    /**
     * 设置类型关键字
     * 
     * @param type_keyword 类型关键字
     */
	public void setType_keyword(String type_keyword){
		this.type_keyword = type_keyword;
	}
	
    /**
     * 获得编码类型名称
     * 
     * @return 编码类型名称
     */
	public String getName(){
		return name;
	}
	
    /**
     * 设置编码类型名称
     * 
     * @param name 编码类型名称
     */
	public void setName(String name){
		this.name = name;
	}
	
    /**
     * 获得备注
     * 
     * @return 备注
     */
	public String getRemark(){
		return remark;
	}
	
    /**
     * 设置备注
     * 
     * @param remark 备注
     */
	public void setRemark(String remark){
		this.remark = remark;
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
     * 获得创建日期
     * 
     * @return 创建日期
     */
	public Timestamp getCreate_date(){
		return create_date;
	}
	
    /**
     * 设置创建日期
     * 
     * @param create_date 创建日期
     */
	public void setCreate_date(Timestamp create_date){
		this.create_date = create_date;
	}
	
    /**
     * 获得创建IP
     * 
     * @return 创建IP
     */
	public String getCreate_ip(){
		return create_ip;
	}
	
    /**
     * 设置创建IP
     * 
     * @param create_ip 创建IP
     */
	public void setCreate_ip(String create_ip){
		this.create_ip = create_ip;
	}
	
    /**
     * 获得创建用户ID
     * 
     * @return 创建用户ID
     */
	public String getCreate_user_id(){
		return create_user_id;
	}
	
    /**
     * 设置创建用户ID
     * 
     * @param create_user_id 创建用户ID
     */
	public void setCreate_user_id(String create_user_id){
		this.create_user_id = create_user_id;
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
