//代码生成时,文件路径: E:/message/src/main/java/org/quickbundle/modules/message/rmmessage/vo/RmMessageVo.java
//代码生成时,系统时间: 2013-04-05 15:09:10
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> message
 * 
 * 文件名称: org.quickbundle.modules.message.rmmessage.vo --> RmMessageVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2013-04-05 15:09:10 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.message.vo;


import java.sql.Timestamp;
import java.util.List;

import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmMessageVo extends RmValueObject{

	private static final long serialVersionUID = 1;

	List<RmMessageReceiverVo> body = null;
	
	public List<RmMessageReceiverVo> getBody() {
		return body;
	}

	public void setBody(List<RmMessageReceiverVo> body) {
		this.body = body;
	}
	
	//开始vo的属性
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private Long id;
	/**
     * biz_keyword 表示: 业务关键字
	 * 数据库注释: 
     */
    private String biz_keyword;
	/**
     * sender_id 表示: 发送人ID
	 * 数据库注释: 
     */
    private Long sender_id;
	/**
     * parent_message_id 表示: 父消息ID
	 * 数据库注释: 
     */
    private Long parent_message_id;
	/**
     * owner_org_id 表示: 所属组织ID
	 * 数据库注释: 
     */
    private String owner_org_id;
	/**
     * template_id 表示: 模板ID
	 * 数据库注释: 
     */
    private Long template_id;
	/**
     * is_affix 表示: 有无附件
	 * 数据库注释: 
     */
    private String is_affix;
	/**
     * record_id 表示: 主记录ID
	 * 数据库注释: 
     */
    private String record_id;
	/**
     * message_xml_context 表示: 消息XML内容
	 * 数据库注释: 
     */
    private String message_xml_context;
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
    private Long modify_user_id;        
    //结束vo的属性
        
        
    //开始vo的setter和getter方法
    
    /**
     * 获得主键
     * 
     * @return 主键
     */
	public Long getId(){
		return id;
	}
	
    /**
     * 设置主键
     * 
     * @param id 主键
     */
	public void setId(Long id){
		this.id = id;
	}
	
    /**
     * 获得业务关键字
     * 
     * @return 业务关键字
     */
	public String getBiz_keyword(){
		return biz_keyword;
	}
	
    /**
     * 设置业务关键字
     * 
     * @param biz_keyword 业务关键字
     */
	public void setBiz_keyword(String biz_keyword){
		this.biz_keyword = biz_keyword;
	}
	
    /**
     * 获得发送人ID
     * 
     * @return 发送人ID
     */
	public Long getSender_id(){
		return sender_id;
	}
	
    /**
     * 设置发送人ID
     * 
     * @param sender_id 发送人ID
     */
	public void setSender_id(Long sender_id){
		this.sender_id = sender_id;
	}
	
    /**
     * 获得父消息ID
     * 
     * @return 父消息ID
     */
	public Long getParent_message_id(){
		return parent_message_id;
	}
	
    /**
     * 设置父消息ID
     * 
     * @param parent_message_id 父消息ID
     */
	public void setParent_message_id(Long parent_message_id){
		this.parent_message_id = parent_message_id;
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
     * 获得模板ID
     * 
     * @return 模板ID
     */
	public Long getTemplate_id(){
		return template_id;
	}
	
    /**
     * 设置模板ID
     * 
     * @param template_id 模板ID
     */
	public void setTemplate_id(Long template_id){
		this.template_id = template_id;
	}
	
    /**
     * 获得有无附件
     * 
     * @return 有无附件
     */
	public String getIs_affix(){
		return is_affix;
	}
	
    /**
     * 设置有无附件
     * 
     * @param is_affix 有无附件
     */
	public void setIs_affix(String is_affix){
		this.is_affix = is_affix;
	}
	
    /**
     * 获得主记录ID
     * 
     * @return 主记录ID
     */
	public String getRecord_id(){
		return record_id;
	}
	
    /**
     * 设置主记录ID
     * 
     * @param record_id 主记录ID
     */
	public void setRecord_id(String record_id){
		this.record_id = record_id;
	}
	
    /**
     * 获得消息XML内容
     * 
     * @return 消息XML内容
     */
	public String getMessage_xml_context(){
		return message_xml_context;
	}
	
    /**
     * 设置消息XML内容
     * 
     * @param message_xml_context 消息XML内容
     */
	public void setMessage_xml_context(String message_xml_context){
		this.message_xml_context = message_xml_context;
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
	public Long getModify_user_id(){
		return modify_user_id;
	}
	
    /**
     * 设置修改用户ID
     * 
     * @param modify_user_id 修改用户ID
     */
	public void setModify_user_id(Long modify_user_id){
		this.modify_user_id = modify_user_id;
	}
	
    //结束vo的setter和getter方法
    
}