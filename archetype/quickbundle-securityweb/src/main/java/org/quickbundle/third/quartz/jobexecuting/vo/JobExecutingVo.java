//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobexecuting/vo/JobExecutingVo.java
//代码生成时,系统时间: 2012-04-02 22:28:49
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobexecuting.vo --> JobExecutingVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:49 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobexecuting.vo;


import java.math.BigDecimal;
import java.sql.Timestamp;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobExecutingVo extends RmValueObject{
	
	public static JobExecutingVo fromQuartzJobExecutionContext(JobExecutionContext ec){
		JobExecutingVo vo = new JobExecutingVo();
		
		vo.setTrigger_name(ec.getTrigger().getKey().getName());
		vo.setTrigger_group(ec.getTrigger().getKey().getGroup());
		vo.setJob_name(ec.getJobDetail().getKey().getName());
		vo.setJob_group(ec.getJobDetail().getKey().getGroup());
		
		vo.setFire_instance_id(ec.getFireInstanceId());
		vo.setFire_time(ec.getFireTime() != null ? new Timestamp(ec.getFireTime().getTime()) : null);
		vo.setJob_run_time(ec.getJobRunTime() > 0 ? ec.getJobRunTime() : System.currentTimeMillis() - ec.getFireTime().getTime());
		
		vo.setNext_fire_time(ec.getNextFireTime() != null ? new Timestamp(ec.getNextFireTime().getTime()) : null);
		vo.setPrevious_fire_time(ec.getPreviousFireTime() != null ? new Timestamp(ec.getPreviousFireTime().getTime()) : null);
		vo.setRecovering(ec.isRecovering() ? "1" : "0");
		vo.setRefire_count(ec.getRefireCount());
		vo.setResult(ec.getResult() != null ? ec.getResult().toString() : "");
		
		vo.setScheduled_fire_time(ec.getScheduledFireTime() != null ? new Timestamp(ec.getScheduledFireTime().getTime()) : null);
		
		vo.setDataMap(ec.getMergedJobDataMap());
		
		return vo;
	}
	
	private JobDataMap dataMap;
	
    
    //开始vo的属性
    
	public JobDataMap getDataMap() {
		return dataMap;
	}

	public void setDataMap(JobDataMap dataMap) {
		this.dataMap = dataMap;
	}

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
     * recovering 表示: 被恢复
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String recovering;
	/**
     * refire_count 表示: 重触发次数
	 * 数据库注释: 
     */
    private int refire_count;
	/**
     * fire_time 表示: 实际触发时间
	 * 数据库注释: 
     */
    private Timestamp fire_time;
	/**
     * scheduled_fire_time 表示: 计划触发时间
	 * 数据库注释: 
     */
    private Timestamp scheduled_fire_time;
	/**
     * previous_fire_time 表示: 上次触发时间
	 * 数据库注释: 
     */
    private Timestamp previous_fire_time;
	/**
     * next_fire_time 表示: 下次触发时间
	 * 数据库注释: 
     */
    private Timestamp next_fire_time;
	/**
     * result 表示: 结果
	 * 数据库注释: 
     */
    private String result;
	/**
     * job_run_time 表示: 运行时间
	 * 数据库注释: 
     */
    private long job_run_time;
	/**
     * fire_instance_id 表示: 触发实例ID
	 * 数据库注释: 
     */
    private String fire_instance_id;        
    //结束vo的属性
        
        
    //开始vo的setter和getter方法
    
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
     * 获得被恢复
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 被恢复
     */
	public String getRecovering(){
		return recovering;
	}
	
    /**
     * 设置被恢复
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param recovering 被恢复
     */
	public void setRecovering(String recovering){
		this.recovering = recovering;
	}
	
    /**
     * 获得重触发次数
     * 
     * @return 重触发次数
     */
	public int getRefire_count(){
		return refire_count;
	}
	
    /**
     * 设置重触发次数
     * 
     * @param refire_count 重触发次数
     */
	public void setRefire_count(int refire_count){
		this.refire_count = refire_count;
	}
	
    /**
     * 获得实际触发时间
     * 
     * @return 实际触发时间
     */
	public Timestamp getFire_time(){
		return fire_time;
	}
	
    /**
     * 设置实际触发时间
     * 
     * @param fire_time 实际触发时间
     */
	public void setFire_time(Timestamp fire_time){
		this.fire_time = fire_time;
	}
	
    /**
     * 获得计划触发时间
     * 
     * @return 计划触发时间
     */
	public Timestamp getScheduled_fire_time(){
		return scheduled_fire_time;
	}
	
    /**
     * 设置计划触发时间
     * 
     * @param scheduled_fire_time 计划触发时间
     */
	public void setScheduled_fire_time(Timestamp scheduled_fire_time){
		this.scheduled_fire_time = scheduled_fire_time;
	}
	
    /**
     * 获得上次触发时间
     * 
     * @return 上次触发时间
     */
	public Timestamp getPrevious_fire_time(){
		return previous_fire_time;
	}
	
    /**
     * 设置上次触发时间
     * 
     * @param previous_fire_time 上次触发时间
     */
	public void setPrevious_fire_time(Timestamp previous_fire_time){
		this.previous_fire_time = previous_fire_time;
	}
	
    /**
     * 获得下次触发时间
     * 
     * @return 下次触发时间
     */
	public Timestamp getNext_fire_time(){
		return next_fire_time;
	}
	
    /**
     * 设置下次触发时间
     * 
     * @param next_fire_time 下次触发时间
     */
	public void setNext_fire_time(Timestamp next_fire_time){
		this.next_fire_time = next_fire_time;
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
	
    /**
     * 获得运行时间
     * 
     * @return 运行时间
     */
	public long getJob_run_time(){
		return job_run_time;
	}
	
    /**
     * 设置运行时间
     * 
     * @param job_run_time 运行时间
     */
	public void setJob_run_time(long job_run_time){
		this.job_run_time = job_run_time;
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
	
    //结束vo的setter和getter方法
    
}