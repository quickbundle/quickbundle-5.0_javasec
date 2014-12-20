//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/rmschedulerevent/vo/RmSchedulerEventVo.java
//代码生成时,系统时间: 2012-04-02 22:28:46
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.rmschedulerevent.vo --> RmSchedulerEventVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:46 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.rmschedulerevent.vo;


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

public class RmSchedulerEventVo extends RmValueObject{
    
    //开始rm_code_type的属性
    
	/**
     * id 表示: 主键
	 * 数据库注释: 
     */
    private String id;
	/**
     * job_name 表示: 作业名称
	 * 数据库注释: 
     */
    private String job_name;
	/**
     * job_group 表示: 作业组
	 * 数据库注释: 
     */
    private String job_group;
	/**
     * trigger_name 表示: 触发器名称
	 * 数据库注释: 
     */
    private String trigger_name;
	/**
     * trigger_group 表示: 触发器组
	 * 数据库注释: 
     */
    private String trigger_group;
	/**
     * fire_instance_id 表示: 触发实例ID
	 * 数据库注释: 
     */
    private String fire_instance_id;
	/**
     * event_type 表示: 事件类型
	 * 数据库注释: 
     */
    private String event_type;
	/**
     * cost_millis 表示: 耗时
	 * 数据库注释: 
     */
    private long cost_millis;
	/**
     * result_status 表示: 结果状态
	 * 数据库注释: 
     */
    private String result_status;
	/**
     * create_time 表示: 创建时间
	 * 数据库注释: 
     */
    private Timestamp create_time;
	/**
     * create_ip 表示: 创建IP
	 * 数据库注释: 
     */
    private String create_ip;
	/**
     * uuid 表示: 唯一识别码
	 * 数据库注释: 
     */
    private String uuid;
	/**
     * is_archive 表示: 是否归档
	 * 数据库注释: 
     */
    private String is_archive;        
	/**
     * result 表示: 结果
	 * 数据库注释: 
     */
    private String result;        
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
     * 获得作业名称
     * 
     * @return 作业名称
     */
	public String getJob_name(){
		return job_name;
	}
	
    /**
     * 设置作业名称
     * 
     * @param job_name 作业名称
     */
	public void setJob_name(String job_name){
		this.job_name = job_name;
	}
	
    /**
     * 获得作业组
     * 
     * @return 作业组
     */
	public String getJob_group(){
		return job_group;
	}
	
    /**
     * 设置作业组
     * 
     * @param job_group 作业组
     */
	public void setJob_group(String job_group){
		this.job_group = job_group;
	}
	
    /**
     * 获得触发器名称
     * 
     * @return 触发器名称
     */
	public String getTrigger_name(){
		return trigger_name;
	}
	
    /**
     * 设置触发器名称
     * 
     * @param trigger_name 触发器名称
     */
	public void setTrigger_name(String trigger_name){
		this.trigger_name = trigger_name;
	}
	
    /**
     * 获得触发器组
     * 
     * @return 触发器组
     */
	public String getTrigger_group(){
		return trigger_group;
	}
	
    /**
     * 设置触发器组
     * 
     * @param trigger_group 触发器组
     */
	public void setTrigger_group(String trigger_group){
		this.trigger_group = trigger_group;
	}
	
    /**
     * 获得触发实例ID
     * 
     * @return 触发实例ID
     */
	public String getFire_instance_id(){
		return fire_instance_id;
	}
	
    /**
     * 设置触发实例ID
     * 
     * @param fire_instance_id 触发实例ID
     */
	public void setFire_instance_id(String fire_instance_id){
		this.fire_instance_id = fire_instance_id;
	}
	
    /**
     * 获得事件类型
     * 
     * @return 事件类型
     */
	public String getEvent_type(){
		return event_type;
	}
	
    /**
     * 设置事件类型
     * 
     * @param event_type 事件类型
     */
	public void setEvent_type(String event_type){
		this.event_type = event_type;
	}
	
    /**
     * 获得耗时
     * 
     * @return 耗时
     */
	public long getCost_millis(){
		return cost_millis;
	}
	
    /**
     * 设置耗时
     * 
     * @param cost_millis 耗时
     */
	public void setCost_millis(long cost_millis){
		this.cost_millis = cost_millis;
	}
	
    /**
     * 获得结果状态
     * 
     * @return 结果状态
     */
	public String getResult_status(){
		return result_status;
	}
	
    /**
     * 设置结果状态
     * 
     * @param result_status 结果状态
     */
	public void setResult_status(String result_status){
		this.result_status = result_status;
	}
	
    /**
     * 获得创建时间
     * 
     * @return 创建时间
     */
	public Timestamp getCreate_time(){
		return create_time;
	}
	
    /**
     * 设置创建时间
     * 
     * @param create_time 创建时间
     */
	public void setCreate_time(Timestamp create_time){
		this.create_time = create_time;
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
     * 获得唯一识别码
     * 
     * @return 唯一识别码
     */
	public String getUuid(){
		return uuid;
	}
	
    /**
     * 设置唯一识别码
     * 
     * @param uuid 唯一识别码
     */
	public void setUuid(String uuid){
		this.uuid = uuid;
	}
	
    /**
     * 获得是否归档
     * 
     * @return 是否归档
     */
	public String getIs_archive(){
		return is_archive;
	}
	
    /**
     * 设置是否归档
     * 
     * @param is_archive 是否归档
     */
	public void setIs_archive(String is_archive){
		this.is_archive = is_archive;
	}
	
    /**
     * 获得结果
     * 
     * @return 结果
     */
	public String getResult(){
		return result;
	}
	
    /**
     * 设置结果
     * 
     * @param result 结果
     */
	public void setResult(String result){
		this.result = result;
	}
	
    //结束rm_code_type的setter和getter方法
    
}