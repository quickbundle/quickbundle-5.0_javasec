//代码生成时,文件路径: E:/message/src/main/java/org/quickbundle/modules/message/rmmessagereveiver/vo/RmMessageReveiverVo.java
//代码生成时,系统时间: 2013-04-05 15:09:07
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> message
 * 
 * 文件名称: org.quickbundle.modules.message.rmmessagereveiver.vo --> RmMessageReveiverVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2013-04-05 15:09:07 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.message.vo;


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

public class RmMessageReceiverVo extends RmValueObject{
    
	private static final long serialVersionUID = 1L;
	
    //开始vo的属性
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private Long id;
	/**
     * message_id 表示: 消息ID
	 * 数据库注释: 
     */
    private Long message_id;
	/**
     * receiver_id 表示: 接收人ID
	 * 数据库注释: 
     */
    private Long receiver_id;
	/**
     * is_handle 表示: 是否办理
	 * 数据库注释: 
     */
    private String is_handle;
	/**
     * handle_date 表示: 办理时间
	 * 数据库注释: 
     */
    private Timestamp handle_date;
	/**
     * handle_result 表示: 办理结果
	 * 数据库注释: 
     */
    private String handle_result;
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
     * 获得消息ID
     * 
     * @return 消息ID
     */
	public Long getMessage_id(){
		return message_id;
	}
	
    /**
     * 设置消息ID
     * 
     * @param message_id 消息ID
     */
	public void setMessage_id(Long message_id){
		this.message_id = message_id;
	}
	
    /**
     * 获得接收人ID
     * 
     * @return 接收人ID
     */
	public Long getReceiver_id(){
		return receiver_id;
	}
	
    /**
     * 设置接收人ID
     * 
     * @param receiver_id 接收人ID
     */
	public void setReceiver_id(Long receiver_id){
		this.receiver_id = receiver_id;
	}
	
    /**
     * 获得是否办理
     * 
     * @return 是否办理
     */
	public String getIs_handle(){
		return is_handle;
	}
	
    /**
     * 设置是否办理
     * 
     * @param is_handle 是否办理
     */
	public void setIs_handle(String is_handle){
		this.is_handle = is_handle;
	}
	
    /**
     * 获得办理时间
     * 
     * @return 办理时间
     */
	public Timestamp getHandle_date(){
		return handle_date;
	}
	
    /**
     * 设置办理时间
     * 
     * @param handle_date 办理时间
     */
	public void setHandle_date(Timestamp handle_date){
		this.handle_date = handle_date;
	}
	
    /**
     * 获得办理结果
     * 
     * @return 办理结果
     */
	public String getHandle_result(){
		return handle_result;
	}
	
    /**
     * 设置办理结果
     * 
     * @param handle_result 办理结果
     */
	public void setHandle_result(String handle_result){
		this.handle_result = handle_result;
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