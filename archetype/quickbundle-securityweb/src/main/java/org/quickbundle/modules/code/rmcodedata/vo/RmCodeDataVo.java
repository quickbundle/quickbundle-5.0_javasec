//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodedata/vo/RmCodeDataVo.java
//代码生成时,系统时间: 2010-04-08 21:15:47.14
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodedata.vo --> RmCodeDataVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:47.14 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodedata.vo;


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

public class RmCodeDataVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库中的注释: 
     */
    private String id;
	/**
     * code_type_id 表示: 编码类型ID
	 * 数据库中的注释: 
     */
    private String code_type_id;
    
    private String code_type_id_name;
	public String getCode_type_id_name() {
		return code_type_id_name;
	}
	public void setCode_type_id_name(String code_type_id_name) {
		this.code_type_id_name = code_type_id_name;
	}

	/**
     * data_key 表示: 数据关键字
	 * 数据库中的注释: 
     */
    private String data_key;
	/**
     * enable_status 表示: 启用状态
	 * 数据库中的注释: 1--enable 0--disable
     */
    private String enable_status;
	/**
     * data_value 表示: 数据值
	 * 数据库中的注释: 
     */
    private String data_value;
	/**
     * total_code 表示: 完整编码
	 * 数据库中的注释: 
     */
    private String total_code;
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
     * 获得编码类型ID
     * 
     * @return 编码类型ID
     */
	public String getCode_type_id(){
		return code_type_id;
	}
	
    /**
     * 设置编码类型ID
     * 
     * @param code_type_id 编码类型ID
     */
	public void setCode_type_id(String code_type_id){
		this.code_type_id = code_type_id;
	}
	
    /**
     * 获得数据关键字
     * 
     * @return 数据关键字
     */
	public String getData_key(){
		return data_key;
	}
	
    /**
     * 设置数据关键字
     * 
     * @param data_key 数据关键字
     */
	public void setData_key(String data_key){
		this.data_key = data_key;
	}
	
    /**
     * 获得启用状态
     * 数据库中的注释: 1--enable 0--disable
     * @return 启用状态
     */
	public String getEnable_status(){
		return enable_status;
	}
	
    /**
     * 设置启用状态
     * 数据库中的注释: 1--enable 0--disable
     * @param enable_status 启用状态
     */
	public void setEnable_status(String enable_status){
		this.enable_status = enable_status;
	}
	
    /**
     * 获得数据值
     * 
     * @return 数据值
     */
	public String getData_value(){
		return data_value;
	}
	
    /**
     * 设置数据值
     * 
     * @param data_value 数据值
     */
	public void setData_value(String data_value){
		this.data_value = data_value;
	}
	
    /**
     * 获得完整编码
     * 
     * @return 完整编码
     */
	public String getTotal_code(){
		return total_code;
	}
	
    /**
     * 设置完整编码
     * 
     * @param total_code 完整编码
     */
	public void setTotal_code(String total_code){
		this.total_code = total_code;
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
