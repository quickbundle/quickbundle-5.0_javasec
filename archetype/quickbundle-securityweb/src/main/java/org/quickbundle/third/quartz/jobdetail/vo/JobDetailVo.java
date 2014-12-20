//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobdetail/vo/JobDetailVo.java
//代码生成时,系统时间: 2012-04-02 22:28:47
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobdetail.vo --> JobDetailVo.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:47 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobdetail.vo;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.impl.JobDetailImpl;
import org.quickbundle.base.vo.RmValueObject;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobDetailVo extends RmValueObject{
	
	public JobDetail toQuartzJobDetail() throws ClassNotFoundException {
		JobDetailImpl jd = new JobDetailImpl();
		jd.setDescription(description);
		jd.setDurability("1".equals(is_durable));
		jd.setName(job_name);
		jd.setGroup("".equals(job_group) ? null : job_group);
		Class cls = Class.forName(job_class_name);
		jd.setJobClass(cls);
		jd.setRequestsRecovery("1".equals(requests_recovery));
		jd.setJobDataMap(dataMap);
		return jd;
	}
	
	public static JobDetailVo fromQuartzJobDetail(JobDetail jd){
		JobDetailVo vo = new JobDetailVo();
		vo.setDescription(jd.getDescription());
		vo.setIs_durable(jd.isDurable() ? "1" : "0");
		vo.setJob_name(jd.getKey().getName());
		vo.setJob_group(jd.getKey().getGroup());
		vo.setJob_class_name(jd.getJobClass().getName());
		vo.setRequests_recovery(jd.requestsRecovery() ? "1" : "0");
		vo.setDataMap(jd.getJobDataMap());
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
     * description 表示: 描述
	 * 数据库注释: 
     */
    private String description;
	/**
     * job_class_name 表示: 作业类名
	 * 数据库注释: 
     */
    private String job_class_name;
	/**
     * is_durable 表示: 持久化
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String is_durable;
	/**
     * requests_recovery 表示: 可恢复
	 * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     */
    private String requests_recovery;        
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
     * 获得作业类名
     * 
     * @return 作业类名
     */
	public String getJob_class_name(){
		return job_class_name;
	}
	
    /**
     * 设置作业类名
     * 
     * @param job_class_name 作业类名
     */
	public void setJob_class_name(String job_class_name){
		this.job_class_name = job_class_name;
	}
	
    /**
     * 获得持久化
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 持久化
     */
	public String getIs_durable(){
		return is_durable;
	}
	
    /**
     * 设置持久化
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param is_durable 持久化
     */
	public void setIs_durable(String is_durable){
		this.is_durable = is_durable;
	}
	
    /**
     * 获得可恢复
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @return 可恢复
     */
	public String getRequests_recovery(){
		return requests_recovery;
	}
	
    /**
     * 设置可恢复
     * 数据库注释: $RM_YES_NOT=是、否{ 0=否, 1=是 }
     * @param requests_recovery 可恢复
     */
	public void setRequests_recovery(String requests_recovery){
		this.requests_recovery = requests_recovery;
	}
	
    //结束vo的setter和getter方法
    
}