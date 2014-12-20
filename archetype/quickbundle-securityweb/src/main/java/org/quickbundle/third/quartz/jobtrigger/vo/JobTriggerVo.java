//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobtrigger/vo/JobTriggerVo.java
//代码生成时,系统时间: 2012-04-02 22:28:48
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobtrigger.vo --> JobTriggerVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:48 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobtrigger.vo;


import java.text.ParseException;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobTriggerVo extends RmValueObject{
	
	public CronTrigger toQuartzCronTrigger() throws ParseException {
		CronTriggerImpl ct = new CronTriggerImpl();
		ct.setName(trigger_name);
		ct.setGroup("".equals(trigger_group) ? null : trigger_group);
		ct.setCalendarName("".equals(calendar_name) ? null : calendar_name);
		ct.setCronExpression(cron_expression);
		ct.setDescription(description);
		if(end_time > 0) {
			ct.setEndTime(new Date(end_time));
		}
		ct.setJobGroup("".equals(job_group) ? null : job_group);
		ct.setJobName(job_name);
		ct.setMisfireInstruction(misfire_instr);
//		ct.setNextFireTime(new Date(next_fire_time));
//		ct.setPreviousFireTime(new Date(prev_fire_time));
		ct.setPriority(priority);
		if(start_time > 0) {
			ct.setStartTime(new Date(start_time));
		}
		ct.setJobDataMap(dataMap);
		return ct;
	}
	
	public static JobTriggerVo fromQuartzCronTrigger(Trigger ct){
		JobTriggerVo vo = new JobTriggerVo();
		vo.setTrigger_name(ct.getKey().getName());
		vo.setTrigger_group(ct.getKey().getGroup());
		vo.setCalendar_name(ct.getCalendarName());
		if(ct instanceof CronTrigger) {
			vo.setCron_expression(((CronTrigger)ct).getCronExpression());
		}
		vo.setDescription(ct.getDescription());
		vo.setEnd_time(ct.getEndTime() != null ? ct.getEndTime().getTime() : 0);
		vo.setJob_name(ct.getJobKey().getName());
		vo.setJob_group(ct.getJobKey().getGroup());
		vo.setMisfire_instr(ct.getMisfireInstruction());
		vo.setNext_fire_time(ct.getNextFireTime().getTime());
		vo.setPrev_fire_time(ct.getPreviousFireTime() != null ? ct.getPreviousFireTime().getTime() : 0);
		vo.setPriority(ct.getPriority());
		vo.setStart_time(ct.getStartTime().getTime());
		vo.setDataMap(ct.getJobDataMap());
		return vo;
	}
	
	private JobDataMap dataMap;
	public JobDataMap getDataMap() {
		return dataMap;
	}
	public void setDataMap(JobDataMap dataMap) {
		this.dataMap = dataMap;
	}
    //开始vo的属性
    
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
     * cron_expression 表示: Cron表达式
	 * 数据库注释: 
     */
    private String cron_expression;
	/**
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
	/**
     * next_fire_time 表示: 上次触发时间
	 * 数据库注释: 
     */
    private long next_fire_time;
	/**
     * prev_fire_time 表示: 下次触发时间
	 * 数据库注释: 
     */
    private long prev_fire_time;
	/**
     * priority 表示: 优先级
	 * 数据库注释: 
     */
    private int priority;
	/**
     * trigger_state 表示: 触发器状态
	 * 数据库注释: 
     */
    private String trigger_state;
	/**
     * start_time 表示: 开始时间
	 * 数据库注释: 
     */
    private long start_time;
	/**
     * end_time 表示: 结束时间
	 * 数据库注释: 
     */
    private long end_time;
	/**
     * calendar_name 表示: 日历名称
	 * 数据库注释: 
     */
    private String calendar_name;
	/**
     * misfire_instr 表示: 未触发时指令
	 * 数据库注释: 
     */
    private int misfire_instr;        
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
     * 获得Cron表达式
     * 
     * @return Cron表达式
     */
	public String getCron_expression(){
		return cron_expression;
	}
	
    /**
     * 设置Cron表达式
     * 
     * @param cron_expression Cron表达式
     */
	public void setCron_expression(String cron_expression){
		this.cron_expression = cron_expression;
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
     * 获得上次触发时间
     * 
     * @return 上次触发时间
     */
	public long getNext_fire_time(){
		return next_fire_time;
	}
	
    /**
     * 设置上次触发时间
     * 
     * @param next_fire_time 上次触发时间
     */
	public void setNext_fire_time(long next_fire_time){
		this.next_fire_time = next_fire_time;
	}
	
    /**
     * 获得下次触发时间
     * 
     * @return 下次触发时间
     */
	public long getPrev_fire_time(){
		return prev_fire_time;
	}
	
    /**
     * 设置下次触发时间
     * 
     * @param prev_fire_time 下次触发时间
     */
	public void setPrev_fire_time(long prev_fire_time){
		this.prev_fire_time = prev_fire_time;
	}
	
    /**
     * 获得优先级
     * 
     * @return 优先级
     */
	public int getPriority(){
		return priority;
	}
	
    /**
     * 设置优先级
     * 
     * @param priority 优先级
     */
	public void setPriority(int priority){
		this.priority = priority;
	}
	
    /**
     * 获得触发器状态
     * 
     * @return 触发器状态
     */
	public String getTrigger_state(){
		return trigger_state;
	}
	
    /**
     * 设置触发器状态
     * 
     * @param trigger_state 触发器状态
     */
	public void setTrigger_state(String trigger_state){
		this.trigger_state = trigger_state;
	}
	
    /**
     * 获得开始时间
     * 
     * @return 开始时间
     */
	public long getStart_time(){
		return start_time;
	}
	
    /**
     * 设置开始时间
     * 
     * @param start_time 开始时间
     */
	public void setStart_time(long start_time){
		this.start_time = start_time;
	}
	
    /**
     * 获得结束时间
     * 
     * @return 结束时间
     */
	public long getEnd_time(){
		return end_time;
	}
	
    /**
     * 设置结束时间
     * 
     * @param end_time 结束时间
     */
	public void setEnd_time(long end_time){
		this.end_time = end_time;
	}
	
    /**
     * 获得日历名称
     * 
     * @return 日历名称
     */
	public String getCalendar_name(){
		return calendar_name;
	}
	
    /**
     * 设置日历名称
     * 
     * @param calendar_name 日历名称
     */
	public void setCalendar_name(String calendar_name){
		this.calendar_name = calendar_name;
	}
	
    /**
     * 获得未触发时指令
     * 
     * @return 未触发时指令
     */
	public int getMisfire_instr(){
		return misfire_instr;
	}
	
    /**
     * 设置未触发时指令
     * 
     * @param misfire_instr 未触发时指令
     */
	public void setMisfire_instr(int misfire_instr){
		this.misfire_instr = misfire_instr;
	}
	
    //结束vo的setter和getter方法
    
}