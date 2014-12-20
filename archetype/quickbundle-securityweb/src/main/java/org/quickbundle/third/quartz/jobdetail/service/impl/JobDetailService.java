//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobdetail/service/impl/JobDetailService.java
//代码生成时,系统时间: 2012-04-02 22:28:47
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobdetail.service.impl --> JobDetailService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:47 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobdetail.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.third.quartz.jobdetail.service.IJobDetailService;
import org.quickbundle.third.quartz.jobdetail.util.IJobDetailConstants;
import org.quickbundle.third.quartz.jobdetail.vo.JobDetailVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobDetailService extends RmService implements IJobDetailService, IJobDetailConstants {
    
	Scheduler getScheduler() {
		return (Scheduler)RmBeanFactory.getBean(ISchedulerConstants.QUARTZ_SHEDULER);
	}
	
    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     * @throws SchedulerException 
     * @throws ClassNotFoundException 
     */
    public String insert(JobDetailVo vo) throws ClassNotFoundException, SchedulerException {
    	getScheduler().addJob(vo.toQuartzJobDetail(), false);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
		return null;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     * @throws SchedulerException 
     * @throws ClassNotFoundException 
     */
    public String[] insert(JobDetailVo[] vos) throws ClassNotFoundException, SchedulerException {
    	List<String> ids = new ArrayList<String>(vos.length);
    	for(JobDetailVo vo : vos) {
    		ids.add(insert(vo));
    	}
    	return ids.toArray(new String[0]);
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     * @throws SchedulerException 
     */
    public int delete(String job_name, String job_group) throws SchedulerException {
    	boolean success = getScheduler().deleteJob(JobKey.jobKey(job_name, job_group));
    	//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + String.valueOf(id));
    	if(success) {
    		return 1;
    	} else {
    		return 0;
    	}
    }

    /**
     * 删除多条记录
     * 
     * @param job_name_groups 用于删除的记录的ids
     * @return 成功删除的记录数
     */
    public int delete(String[][] job_name_groups) throws SchedulerException {
    	List<JobKey> jobKeys = new ArrayList<JobKey>(job_name_groups.length);
    	for(String[] name_group : job_name_groups) {
    		jobKeys.add(JobKey.jobKey(name_group[0], name_group[1]));
    	}
    	boolean success = getScheduler().deleteJobs(jobKeys);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
    	if(success) {
    		return jobKeys.size();
    	} else {
    		return 0;
    	}
    }

    /**
     * 根据Id进行查询
     * 
     * @param id 用于查找的id
     * @return 查询到的VO对象
     * @throws SchedulerException 
     */
    public JobDetailVo find(String job_name, String job_group) throws SchedulerException {
		JobDetailVo vo = JobDetailVo.fromQuartzJobDetail(getScheduler().getJobDetail(JobKey.jobKey(job_name, job_group)));
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     */
    public int update(JobDetailVo vo) throws ClassNotFoundException, SchedulerException{
    	getScheduler().addJob(vo.toQuartzJobDetail(), true);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getJob_name()));
		return 1;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(JobDetailVo[] vos) throws ClassNotFoundException, SchedulerException{
		int sum = 0;
    	for(JobDetailVo vo : vos) {
    		sum += update(vo);
    	}
    	return sum;
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(JobDetailVo[] vos) {
		throw new UnsupportedOperationException();
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     * @throws SchedulerException 
     */
    public int getRecordCount(Map<String,String> queryCondition) throws SchedulerException {
    	GroupMatcher gm = GroupMatcher.jobGroupContains("");
    	if(queryCondition.containsKey("job_group")) {
    		gm = GroupMatcher.jobGroupContains(queryCondition.get("job_group"));
    	}
    	Set<JobKey> jobKeys = getScheduler().getJobKeys(gm);
    	if(queryCondition.containsKey("job_name")) {
    		for(Iterator<JobKey> iter = jobKeys.iterator();iter.hasNext();) {
    			JobKey jk = iter.next();
    			if(!jk.getName().contains(queryCondition.get("job_name"))) {
    				iter.remove();
    			}
    		}
    	}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum + ", queryCondition=" + queryCondition);
		return jobKeys.size();
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<JobDetailVo> queryByCondition(Map<String,String> queryCondition, String orderStr) throws SchedulerException {
        return queryByCondition(queryCondition, orderStr, -1, -1);
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @return 查询到的VO列表
     */
    public List<JobDetailVo> queryByCondition(Map<String,String> queryCondition, String orderStr, int startIndex, int size) throws SchedulerException {
        return queryByCondition(queryCondition, orderStr, startIndex, size, false);
    }
    
    /**
     * 功能: 通过查询条件获得所有的VO对象列表，带翻页，带排序字符，根据selectAllClumn判断是否查询所有字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字符
     * @param startIndex 开始位置(第一条是1，第二条是2...)
     * @param size 查询多少条记录(size小于等于0时,忽略翻页查询全部)
     * @param selectAllClumn 是否查询所有列，即 SELECT * FROM ...(适用于导出)
     * @return 查询到的VO列表
     * @throws SchedulerException 
     */
    public List<JobDetailVo> queryByCondition(Map<String,String> queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) throws SchedulerException {
    	List<JobDetailVo> vos = new LinkedList<JobDetailVo>();
    	GroupMatcher gm = GroupMatcher.jobGroupContains("");
    	if(queryCondition.containsKey("job_group")) {
    		gm = GroupMatcher.jobGroupContains(queryCondition.get("job_group"));
    	}
    	Set<JobKey> jks = getScheduler().getJobKeys(gm);
    	if(queryCondition.containsKey("job_name")) {
    		for(Iterator<JobKey> iter = jks.iterator();iter.hasNext();) {
    			JobKey jk = iter.next();
    			if(!jk.getName().contains(queryCondition.get("job_name"))) {
    				iter.remove();
    			}
    		}
    	}
    	for(JobKey jk : jks) {
    		JobDetailVo vo = null;
    		try {
    			vo = JobDetailVo.fromQuartzJobDetail(getScheduler().getJobDetail(jk));
			} catch (org.quartz.JobPersistenceException e) {
				vo = new JobDetailVo();
				vo.setJob_name(jk.getName());
				vo.setJob_group(jk.getGroup());
			}
    		vos.add(vo);
    	}
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return vos;
    }
    
	public int execute(String[][] job_name_groups) throws SchedulerException {
    	for(String[] name_group : job_name_groups) {
    		getScheduler().triggerJob(JobKey.jobKey(name_group[0], name_group[1]));
    	}
    	return job_name_groups.length;
	}
}