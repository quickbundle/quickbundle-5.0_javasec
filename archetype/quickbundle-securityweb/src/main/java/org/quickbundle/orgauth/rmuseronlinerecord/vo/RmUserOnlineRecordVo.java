//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuseronlinerecord/vo/RmUserOnlineRecordVo.java
//代码生成时,系统时间: 2010-11-27 22:08:36
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuseronlinerecord.vo --> RmUserOnlineRecordVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:36 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuseronlinerecord.vo;


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

public class RmUserOnlineRecordVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * user_id 表示: 用户ID
	 * 数据库注释: 
     */
    private String user_id;
	/**
     * login_time 表示: 登录时间
	 * 数据库注释: 
     */
    private Timestamp login_time;
	/**
     * cluster_node_id 表示: 集群节点ID
	 * 数据库注释: 
     */
    private String cluster_node_id;
	/**
     * login_sign 表示: 客户端会话标识
	 * 数据库注释: 
     */
    private String login_sign;
	/**
     * login_ip 表示: 登录IP
	 * 数据库注释: 
     */
    private String login_ip;
	/**
     * login_uuid 表示: 唯一识别码
	 * 数据库注释: 
     */
    private String login_uuid;
	/**
     * logout_time 表示: 注销时间
	 * 数据库注释: 
     */
    private Timestamp logout_time;
	/**
     * logout_type 表示: 注销类型
	 * 数据库注释: $RM_LOGOUT_TYPE=注销类型{ 1=正常注销, 2=超时退出, 3=被强制登录替换, 4=被管理员强制退出 }
     */
    private String logout_type;
	/**
     * online_time 表示: 在线时间
	 * 数据库注释: 
     */
    private BigDecimal online_time;
	/**
     * last_operation 表示: 最后操作
	 * 数据库注释: 
     */
    private String last_operation;
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
     * 获得登录时间
     * 
     * @return 登录时间
     */
	public Timestamp getLogin_time(){
		return login_time;
	}
	
    /**
     * 设置登录时间
     * 
     * @param login_time 登录时间
     */
	public void setLogin_time(Timestamp login_time){
		this.login_time = login_time;
	}
	
    /**
     * 获得集群节点ID
     * 
     * @return 集群节点ID
     */
	public String getCluster_node_id(){
		return cluster_node_id;
	}
	
    /**
     * 设置集群节点ID
     * 
     * @param cluster_node_id 集群节点ID
     */
	public void setCluster_node_id(String cluster_node_id){
		this.cluster_node_id = cluster_node_id;
	}
	
    /**
     * 获得客户端会话标识
     * 
     * @return 客户端会话标识
     */
	public String getLogin_sign(){
		return login_sign;
	}
	
    /**
     * 设置客户端会话标识
     * 
     * @param login_sign 客户端会话标识
     */
	public void setLogin_sign(String login_sign){
		this.login_sign = login_sign;
	}
	
    /**
     * 获得登录IP
     * 
     * @return 登录IP
     */
	public String getLogin_ip(){
		return login_ip;
	}
	
    /**
     * 设置登录IP
     * 
     * @param login_ip 登录IP
     */
	public void setLogin_ip(String login_ip){
		this.login_ip = login_ip;
	}
	
    /**
     * 获得唯一识别码
     * 
     * @return 唯一识别码
     */
	public String getLogin_uuid(){
		return login_uuid;
	}
	
    /**
     * 设置唯一识别码
     * 
     * @param login_uuid 唯一识别码
     */
	public void setLogin_uuid(String login_uuid){
		this.login_uuid = login_uuid;
	}
	
    /**
     * 获得注销时间
     * 
     * @return 注销时间
     */
	public Timestamp getLogout_time(){
		return logout_time;
	}
	
    /**
     * 设置注销时间
     * 
     * @param logout_time 注销时间
     */
	public void setLogout_time(Timestamp logout_time){
		this.logout_time = logout_time;
	}
	
    /**
     * 获得注销类型
     * 数据库注释: $RM_LOGOUT_TYPE=注销类型{ 1=正常注销, 2=超时退出, 3=被强制登录替换, 4=被管理员强制退出 }
     * @return 注销类型
     */
	public String getLogout_type(){
		return logout_type;
	}
	
    /**
     * 设置注销类型
     * 数据库注释: $RM_LOGOUT_TYPE=注销类型{ 1=正常注销, 2=超时退出, 3=被强制登录替换, 4=被管理员强制退出 }
     * @param logout_type 注销类型
     */
	public void setLogout_type(String logout_type){
		this.logout_type = logout_type;
	}
	
    /**
     * 获得在线时间
     * 
     * @return 在线时间
     */
	public BigDecimal getOnline_time(){
		return online_time;
	}
	
    /**
     * 设置在线时间
     * 
     * @param online_time 在线时间
     */
	public void setOnline_time(BigDecimal online_time){
		this.online_time = online_time;
	}
	
    /**
     * 获得最后操作
     * 
     * @return 最后操作
     */
	public String getLast_operation(){
		return last_operation;
	}
	
    /**
     * 设置最后操作
     * 
     * @param last_operation 最后操作
     */
	public void setLast_operation(String last_operation){
		this.last_operation = last_operation;
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