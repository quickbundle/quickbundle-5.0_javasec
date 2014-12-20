//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobtrigger/service/impl/JobTriggerService.java
//代码生成时,系统时间: 2012-04-02 22:28:48
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobtrigger.service.impl --> JobTriggerService.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:48 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobtrigger.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.service.RmService;
import org.quickbundle.third.quartz.jobtrigger.service.IJobTriggerService;
import org.quickbundle.third.quartz.jobtrigger.util.IJobTriggerConstants;
import org.quickbundle.third.quartz.jobtrigger.vo.JobTriggerVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobTriggerService extends RmService implements IJobTriggerService, IJobTriggerConstants {

	Scheduler getScheduler() {
		return (Scheduler)RmBeanFactory.getBean(ISchedulerConstants.QUARTZ_SHEDULER);
	}
	
    /**
     * 插入单条记录
     * 
     * @param vo 用于添加的VO对象
     * @return 若添加成功，返回新生成的Oid
     * @throws ParseException 
     * @throws SchedulerException 
     */
    public String insert(JobTriggerVo vo) throws SchedulerException, ParseException {
    	getScheduler().scheduleJob(vo.toQuartzCronTrigger());
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "插入了1条记录,id=" + String.valueOf(id));
		return null;
    }
    
    /**
     * 插入多条记录
     *
     * @param vos 用于添加的VO对象数组
     * @return 返回新生成的id数组
     */
    public String[] insert(JobTriggerVo[] vos) {
		throw new UnsupportedOperationException();
    }

    /**
     * 删除单条记录
     * 
     * @param id 用于删除的记录的id
     * @return 成功删除的记录数
     * @throws SchedulerException 
     */
    public int delete(String trigger_name, String trigger_group) throws SchedulerException {
    	boolean success = getScheduler().unscheduleJob(TriggerKey.triggerKey(trigger_name, trigger_group));
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
     * @param ids 用于删除的记录的ids
     * @return 成功删除的记录数
     * @throws SchedulerException 
     */
    public int delete(String trigger_name_groups[][]) throws SchedulerException {
    	List<TriggerKey> tks = new ArrayList<TriggerKey>(trigger_name_groups.length);
    	for(String[] name_group : trigger_name_groups) {
    		tks.add(TriggerKey.triggerKey(name_group[0], name_group[1]));
    	}
    	boolean success = getScheduler().unscheduleJobs(tks);
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "删除了" + sum + "条记录,id=" + RmStringHelper.ArrayToString(ids, ","));
    	if(success) {
    		return tks.size();
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
    public JobTriggerVo find(String trigger_name, String trigger_group) throws SchedulerException {
		JobTriggerVo vo = JobTriggerVo.fromQuartzCronTrigger(getScheduler().getTrigger(TriggerKey.triggerKey(trigger_name, trigger_group)));
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "察看了1条记录,id=" + id);
		return vo;
    }

    /**
     * 更新单条记录
     * 
     * @param vo 用于更新的VO对象
     * @return 成功更新的记录数
     * @throws SchedulerException 
     * @throws ParseException 
     */
    public int update(JobTriggerVo vo) throws SchedulerException, ParseException {
    	getScheduler().unscheduleJob(TriggerKey.triggerKey(vo.getTrigger_name(), vo.getTrigger_group()));
    	getScheduler().scheduleJob(vo.toQuartzCronTrigger());
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "更新了" + sum + "条记录,id=" + String.valueOf(vo.getTrigger_name()));
		return 1;
    }

    /**
     * 批量更新修改多条记录
     * 
     * @param vos 更新的VO对象数组
     * @return 成功更新的记录最终数量
     */
	public int update(JobTriggerVo[] vos) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 批量保存，没有主键的insert，有主键的update
	 * 
	 * @param vos 更新的VO对象数组
	 * @return new int[2]{insert的记录数, update的记录数}	
	 */
	public int[] insertUpdateBatch(JobTriggerVo[] vos) {
		throw new UnsupportedOperationException();
	}

    /**
     * 查询总记录数，带查询条件
     * 
     * @param queryCondition 查询条件
     * @return 总记录数
     * @throws SchedulerException 
     */
    public int getRecordCount(Map<String, String> queryCondition) throws SchedulerException {
    	GroupMatcher gm = GroupMatcher.triggerGroupContains("");
    	if(queryCondition.containsKey("trigger_group")) {
    		gm = GroupMatcher.triggerGroupContains(queryCondition.get("trigger_group"));
    	}
    	Set<TriggerKey> tks = getScheduler().getTriggerKeys(gm);
    	if(queryCondition.containsKey("trigger_name")) {
    		for(Iterator<TriggerKey> iter = tks.iterator();iter.hasNext();) {
    			TriggerKey tk = iter.next();
    			if(!tk.getName().contains(queryCondition.get("trigger_name"))) {
    				iter.remove();
    			}
    		}
    	}
		//RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "查询到了总记录数,count=" + sum + ", queryCondition=" + queryCondition);
		return tks.size();
    }

    /**
     * 功能: 通过查询条件获得所有的VO对象列表，不带翻页查全部，只查询必要的字段
     *
     * @param queryCondition 查询条件, queryCondition等于null或""时查询全部
     * @param orderStr 排序字段
     * @return 查询到的VO列表
     */
    public List<JobTriggerVo> queryByCondition(Map<String, String> queryCondition, String orderStr) throws SchedulerException {
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
    public List<JobTriggerVo> queryByCondition(Map<String, String> queryCondition, String orderStr, int startIndex, int size) throws SchedulerException {
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
    public List<JobTriggerVo> queryByCondition(Map<String, String> queryCondition, String orderStr, int startIndex, int size, boolean selectAllClumn) throws SchedulerException {
    	List<JobTriggerVo> vos = new LinkedList<JobTriggerVo>();
    	GroupMatcher gm = GroupMatcher.triggerGroupContains("");
    	if(queryCondition.containsKey("trigger_group")) {
    		gm = GroupMatcher.triggerGroupContains(queryCondition.get("trigger_group"));
    	}
    	Set<TriggerKey> tks = getScheduler().getTriggerKeys(gm);
    	if(queryCondition.containsKey("trigger_name")) {
    		for(Iterator<TriggerKey> iter = tks.iterator();iter.hasNext();) {
    			TriggerKey tk = iter.next();
    			if(!tk.getName().contains(queryCondition.get("trigger_name"))) {
    				iter.remove();
    			}
    		}
    	}
    	for(TriggerKey tk : tks) {
    		JobTriggerVo vo = JobTriggerVo.fromQuartzCronTrigger(getScheduler().getTrigger(tk));
    		vo.setTrigger_state(getScheduler().getTriggerState(tk).name());
    		vos.add(vo);
    		
    	}
        //RmProjectHelper.log(TABLE_LOG_TYPE_NAME, "按条件查询了多条记录,recordCount=" + lResult.size() + ", startIndex=" + startIndex + ", size=" + size + ", queryCondition=" + queryCondition + ", orderStr=" + orderStr + ", selectAllClumn=" + selectAllClumn);
        return vos;
    }
    
	public int doPause(String trigger_name_groups[][]) throws SchedulerException {
    	for(String[] name_group : trigger_name_groups) {
    		getScheduler().pauseTrigger(TriggerKey.triggerKey(name_group[0], name_group[1]));
    	}
    	return trigger_name_groups.length;
	}

	public int doResume(String trigger_name_groups[][]) throws SchedulerException {
    	for(String[] name_group : trigger_name_groups) {
    		getScheduler().resumeTrigger(TriggerKey.triggerKey(name_group[0], name_group[1]));
    	}
    	return trigger_name_groups.length;
	}
}