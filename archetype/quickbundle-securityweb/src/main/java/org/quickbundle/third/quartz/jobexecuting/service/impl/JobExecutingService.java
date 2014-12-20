//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobexecuting/service/impl/JobExecutingService.java
//代码生成时,系统时间: 2012-04-02 22:28:49
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobexecuting.service.impl --> JobExecutingService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:49 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobexecuting.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.third.quartz.jobexecuting.service.IJobExecutingService;
import org.quickbundle.third.quartz.jobexecuting.util.IJobExecutingConstants;
import org.quickbundle.third.quartz.jobexecuting.vo.JobExecutingVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobExecutingService extends RmService implements IJobExecutingService, IJobExecutingConstants {
	
	Scheduler getScheduler() {
		return (Scheduler)RmBeanFactory.getBean(ISchedulerConstants.QUARTZ_SHEDULER);
	}

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @return 查询到的VO列表
     */
    public List<JobExecutingVo> queryByCondition() throws SchedulerException {
    	List<JobExecutionContext> ecs = getScheduler().getCurrentlyExecutingJobs();
    	List<JobExecutingVo> vos = new ArrayList<JobExecutingVo>(ecs.size());
    	for(JobExecutionContext ec : ecs) {
    		vos.add(JobExecutingVo.fromQuartzJobExecutionContext(ec));
    	}
        return vos;
    }

}