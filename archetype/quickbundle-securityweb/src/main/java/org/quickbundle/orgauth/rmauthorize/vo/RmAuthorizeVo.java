//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorize/vo/RmAuthorizeVo.java
//代码生成时,系统时间: 2010-11-27 22:08:41
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorize.vo --> RmAuthorizeVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:41 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorize.vo;


import java.sql.Timestamp;

import org.dom4j.Document;
import org.quickbundle.base.vo.RmValueObject;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAuthorizeVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
	/**
     * bs_keyword 表示: 业务关键字
	 * 数据库注释: 
     */
    private String bs_keyword;
	/**
     * is_alone_table 表示: 是否单独建表
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_alone_table;
	/**
     * authorize_resource_table_name 表示: 授权资源表名
	 * 数据库注释: 
     */
    private String authorize_resource_table_name;
	/**
     * authorize_resrec_table_name 表示: 授权记录表名
	 * 数据库注释: 
     */
    private String authorize_resrec_table_name;
	/**
     * authorize_affix_table_name 表示: 授权资源附加数据表名
	 * 数据库注释: 
     */
    private String authorize_affix_table_name;
	/**
     * settiing_option 表示: 权限配置选项类型
	 * 数据库注释: $RM_OPTION_TYPE=权限配置{ 1=单选, 2=多选, 3=定制 }
     */
    private String settiing_option;
	/**
     * custom_code 表示: 定制代码
	 * 数据库注释: 
     */
    private String custom_code;
	/**
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
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
     * 获得授权资源表名
     * 
     * @return 授权资源表名
     */
	public String getAuthorize_resource_table_name(){
		return authorize_resource_table_name;
	}
	
    /**
     * 设置授权资源表名
     * 
     * @param authorize_resource_table_name 授权资源表名
     */
	public void setAuthorize_resource_table_name(String authorize_resource_table_name){
		this.authorize_resource_table_name = authorize_resource_table_name;
	}
	
    /**
     * 获得授权记录表名
     * 
     * @return 授权记录表名
     */
	public String getAuthorize_resrec_table_name(){
		return authorize_resrec_table_name;
	}
	
    /**
     * 设置授权记录表名
     * 
     * @param authorize_resrec_table_name 授权记录表名
     */
	public void setAuthorize_resrec_table_name(String authorize_resrec_table_name){
		this.authorize_resrec_table_name = authorize_resrec_table_name;
	}
	
    /**
     * 获得授权资源附加数据表名
     * 
     * @return 授权资源附加数据表名
     */
	public String getAuthorize_affix_table_name(){
		return authorize_affix_table_name;
	}
	
    /**
     * 设置授权资源附加数据表名
     * 
     * @param authorize_affix_table_name 授权资源附加数据表名
     */
	public void setAuthorize_affix_table_name(String authorize_affix_table_name){
		this.authorize_affix_table_name = authorize_affix_table_name;
	}
	
    /**
     * 获得权限配置选项类型
     * 数据库注释: $RM_OPTION_TYPE=权限配置{ 1=单选, 2=多选, 3=定制 }
     * @return 权限配置选项类型
     */
	public String getSettiing_option(){
		return settiing_option;
	}
	
    /**
     * 设置权限配置选项类型
     * 数据库注释: $RM_OPTION_TYPE=权限配置{ 1=单选, 2=多选, 3=定制 }
     * @param settiing_option 权限配置选项类型
     */
	public void setSettiing_option(String settiing_option){
		this.settiing_option = settiing_option;
	}
	
    /**
     * 获得定制代码
     * 
     * @return 定制代码
     */
	public String getCustom_code(){
		return custom_code;
	}
	
    /**
     * 设置定制代码
     * 
     * @param custom_code 定制代码
     */
	public void setCustom_code(String custom_code){
		this.custom_code = custom_code;
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
	
	/**
	 * 
	 * 
<authorize table_name="RM_CODE_TYPE" join_column="TYPE_KEYWORD" pk_column="ID" key_column="NAME">
<consumer>
<table sql_after_from="RM_CODE_TYPE" join_table="RM_CODE_TYPE" join_table_column="TYPE_KEYWORD"/>
</consumer>
<rm_authorize_resource>
<default_access>0</default_access>
<default_is_affix_data>0</default_is_affix_data>
<default_is_recursive>0</default_is_recursive>
<default_access_type>1</default_access_type>
</rm_authorize_resource>
</authorize>
	 * 获得规则
	 * @return
	 */
	public Document getRuleCustomCode() {
		if(!"3".equals(settiing_option) || custom_code == null || custom_code.trim().length() == 0) {
			return null;
		}
		try {
			return RmXmlHelper.getDocumentFromString(custom_code);
		} catch (Exception e) {
			RmLogHelper.getLogger(this.getClass()).warn("getRuleCustomCode():" + e.toString() + " cause:" + e.getCause());
			return null;
		}
	}
	
    //结束rm_code_type的setter和getter方法
    
}