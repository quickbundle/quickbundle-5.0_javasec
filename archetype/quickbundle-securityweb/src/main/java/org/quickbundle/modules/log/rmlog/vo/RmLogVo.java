//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/log/rmlog/vo/RmLogVo.java
//代码生成时,系统时间: 2010-11-30 22:31:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> cu-tm
 * 
 * 文件名称: org.quickbundle.modules.log.rmlog.vo --> RmLogVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:31:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlog.vo;


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

public class RmLogVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * log_type_id 表示: 日志类型ID
	 * 数据库注释: 
     */
    private String log_type_id;
	/**
     * action_date 表示: 操作时间
	 * 数据库注释: 
     */
    private Timestamp action_date;
	/**
     * action_ip 表示: 操作IP
	 * 数据库注释: 
     */
    private String action_ip;
	/**
     * action_module 表示: 操作模块
	 * 数据库注释: 
     */
    private String action_module;
	/**
     * action_type 表示: 操作类型
	 * 数据库注释: $RM_OPERATION_TYPE=操作类型{ 0=新增类, 1=删除类, 2=修改类, 3=查询类, 9=通用 }
     */
    private String action_type;
	/**
     * owner_org_id 表示: 所属组织ID
	 * 数据库注释: 
     */
    private String owner_org_id;
	/**
     * user_id 表示: 用户ID
	 * 数据库注释: 
     */
    private String user_id;
	/**
     * user_id_name 表示: 用户名称
	 * 数据库注释: 
     */
    private String user_id_name;
	/**
     * action_uuid 表示: 唯一识别码
	 * 数据库注释: 
     */
    private String action_uuid;
	/**
     * content 表示: 操作内容
	 * 数据库注释: 
     */
    private String content;        
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
     * 获得日志类型ID
     * 
     * @return 日志类型ID
     */
	public String getLog_type_id(){
		return log_type_id;
	}
	
    /**
     * 设置日志类型ID
     * 
     * @param log_type_id 日志类型ID
     */
	public void setLog_type_id(String log_type_id){
		this.log_type_id = log_type_id;
	}
	
    /**
     * 获得操作时间
     * 
     * @return 操作时间
     */
	public Timestamp getAction_date(){
		return action_date;
	}
	
    /**
     * 设置操作时间
     * 
     * @param action_date 操作时间
     */
	public void setAction_date(Timestamp action_date){
		this.action_date = action_date;
	}
	
    /**
     * 获得操作IP
     * 
     * @return 操作IP
     */
	public String getAction_ip(){
		return action_ip;
	}
	
    /**
     * 设置操作IP
     * 
     * @param action_ip 操作IP
     */
	public void setAction_ip(String action_ip){
		this.action_ip = action_ip;
	}
	
    /**
     * 获得操作模块
     * 
     * @return 操作模块
     */
	public String getAction_module(){
		return action_module;
	}
	
    /**
     * 设置操作模块
     * 
     * @param action_module 操作模块
     */
	public void setAction_module(String action_module){
		this.action_module = action_module;
	}
	
    /**
     * 获得操作类型
     * 数据库注释: $RM_OPERATION_TYPE=操作类型{ 0=新增类, 1=删除类, 2=修改类, 3=查询类, 9=通用 }
     * @return 操作类型
     */
	public String getAction_type(){
		return action_type;
	}
	
    /**
     * 设置操作类型
     * 数据库注释: $RM_OPERATION_TYPE=操作类型{ 0=新增类, 1=删除类, 2=修改类, 3=查询类, 9=通用 }
     * @param action_type 操作类型
     */
	public void setAction_type(String action_type){
		this.action_type = action_type;
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
     * 获得用户ID
     * 
     * @return 用户ID
     */
	public String getUser_id(){
		return user_id;
	}
	
    /**
     * 设置用户ID
     * 
     * @param user_id 用户ID
     */
	public void setUser_id(String user_id){
		this.user_id = user_id;
	}
	
    /**
     * 获得用户名称
     * 
     * @return 用户名称
     */
	public String getUser_id_name(){
		return user_id_name;
	}
	
    /**
     * 设置用户名称
     * 
     * @param user_id_name 用户名称
     */
	public void setUser_id_name(String user_id_name){
		this.user_id_name = user_id_name;
	}
	
    /**
     * 获得唯一识别码
     * 
     * @return 唯一识别码
     */
	public String getAction_uuid(){
		return action_uuid;
	}
	
    /**
     * 设置唯一识别码
     * 
     * @param action_uuid 唯一识别码
     */
	public void setAction_uuid(String action_uuid){
		this.action_uuid = action_uuid;
	}
	
    /**
     * 获得操作内容
     * 
     * @return 操作内容
     */
	public String getContent(){
		return content;
	}
	
    /**
     * 设置操作内容
     * 
     * @param content 操作内容
     */
	public void setContent(String content){
		this.content = content;
	}
	
    //结束rm_code_type的setter和getter方法
    
}