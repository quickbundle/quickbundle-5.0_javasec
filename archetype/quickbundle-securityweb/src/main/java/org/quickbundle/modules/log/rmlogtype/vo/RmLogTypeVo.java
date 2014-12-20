//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/modules/log/rmlogtype/vo/RmLogTypeVo.java
//代码生成时,系统时间: 2010-12-04 14:05:23
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.log.rmlogtype.vo --> RmLogTypeVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-12-04 14:05:23 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlogtype.vo;


import java.sql.Timestamp;

import java.math.BigDecimal;


import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmLogTypeVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * bs_keyword 表示: 业务关键字
	 * 数据库注释: 
     */
    private String bs_keyword;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
	/**
     * is_record 表示: 是否记录
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_record;
	/**
     * is_alone_table 表示: 是否单独建表
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_alone_table;
	/**
     * table_name 表示: 日志数据表名
	 * 数据库注释: 
     */
    private String table_name;
	/**
     * pattern_value 表示: 正则匹配
	 * 数据库注释: 
     */
    private String pattern_value;
	/**
     * match_priority 表示: 匹配优先级
	 * 数据库注释: 
     */
    private BigDecimal match_priority;
	/**
     * max_log_sum 表示: 最大日志量
	 * 数据库注释: 
     */
    private BigDecimal max_log_sum;
	/**
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
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
     * 获得业务关键字
     * 
     * @return 业务关键字
     */
	public String getBs_keyword(){
		return bs_keyword;
	}
	
    /**
     * 设置业务关键字
     * 
     * @param bs_keyword 业务关键字
     */
	public void setBs_keyword(String bs_keyword){
		this.bs_keyword = bs_keyword;
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
     * 获得是否记录
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 是否记录
     */
	public String getIs_record(){
		return is_record;
	}
	
    /**
     * 设置是否记录
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_record 是否记录
     */
	public void setIs_record(String is_record){
		this.is_record = is_record;
	}
	
    /**
     * 获得是否单独建表
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 是否单独建表
     */
	public String getIs_alone_table(){
		return is_alone_table;
	}
	
    /**
     * 设置是否单独建表
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_alone_table 是否单独建表
     */
	public void setIs_alone_table(String is_alone_table){
		this.is_alone_table = is_alone_table;
	}
	
    /**
     * 获得日志数据表名
     * 
     * @return 日志数据表名
     */
	public String getTable_name(){
		return table_name;
	}
	
    /**
     * 设置日志数据表名
     * 
     * @param table_name 日志数据表名
     */
	public void setTable_name(String table_name){
		this.table_name = table_name;
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
     * 获得匹配优先级
     * 
     * @return 匹配优先级
     */
	public BigDecimal getMatch_priority(){
		return match_priority;
	}
	
    /**
     * 设置匹配优先级
     * 
     * @param match_priority 匹配优先级
     */
	public void setMatch_priority(BigDecimal match_priority){
		this.match_priority = match_priority;
	}
	
    /**
     * 获得最大日志量
     * 
     * @return 最大日志量
     */
	public BigDecimal getMax_log_sum(){
		return max_log_sum;
	}
	
    /**
     * 设置最大日志量
     * 
     * @param max_log_sum 最大日志量
     */
	public void setMax_log_sum(BigDecimal max_log_sum){
		this.max_log_sum = max_log_sum;
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