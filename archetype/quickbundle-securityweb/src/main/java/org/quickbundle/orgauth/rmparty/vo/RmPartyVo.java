//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmparty/vo/RmPartyVo.java
//代码生成时,系统时间: 2010-11-28 17:40:30
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmparty.vo --> RmPartyVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:30 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmparty.vo;


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

public class RmPartyVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * party_type_id 表示: 团体类型ID
	 * 数据库注释: 
     */
    private String party_type_id;
    private String party_type_id_name;
	/**
     * old_party_id 表示: 团体原始ID
	 * 数据库注释: 
     */
    private String old_party_id;
	/**
     * name 表示: 名称
	 * 数据库注释: 
     */
    private String name;
	/**
     * is_inherit 表示: 是否继承权限
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_inherit;
	/**
     * email 表示: 电子邮件
	 * 数据库注释: 
     */
    private String email;
	/**
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
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
     * 获得团体类型ID
     * 
     * @return 团体类型ID
     */
	public String getParty_type_id(){
		return party_type_id;
	}
	
    /**
     * 设置团体类型ID
     * 
     * @param party_type_id 团体类型ID
     */
	public void setParty_type_id(String party_type_id){
		this.party_type_id = party_type_id;
	}
	
	public String getParty_type_id_name() {
		return party_type_id_name;
	}

	public void setParty_type_id_name(String partyTypeIdName) {
		party_type_id_name = partyTypeIdName;
	}

    /**
     * 获得团体原始ID
     * 
     * @return 团体原始ID
     */
	public String getOld_party_id(){
		return old_party_id;
	}
	
    /**
     * 设置团体原始ID
     * 
     * @param old_party_id 团体原始ID
     */
	public void setOld_party_id(String old_party_id){
		this.old_party_id = old_party_id;
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
     * 获得是否继承权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 是否继承权限
     */
	public String getIs_inherit(){
		return is_inherit;
	}
	
    /**
     * 设置是否继承权限
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_inherit 是否继承权限
     */
	public void setIs_inherit(String is_inherit){
		this.is_inherit = is_inherit;
	}
	
    /**
     * 获得电子邮件
     * 
     * @return 电子邮件
     */
	public String getEmail(){
		return email;
	}
	
    /**
     * 设置电子邮件
     * 
     * @param email 电子邮件
     */
	public void setEmail(String email){
		this.email = email;
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