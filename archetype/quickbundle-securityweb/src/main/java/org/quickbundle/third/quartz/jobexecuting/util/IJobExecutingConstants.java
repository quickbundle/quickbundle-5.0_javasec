//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobexecuting/util/IJobExecutingConstants.java
//代码生成时,系统时间: 2012-04-02 22:28:49
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobexecuting.util --> IJobExecutingConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:49 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobexecuting.util;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.quickbundle.project.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IJobExecutingConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IJobExecutingService";
    
    //表名
    public final static String TABLE_NAME = "JOB_EXECUTING";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "执行中作业";
    
    //列名汉化
    @SuppressWarnings("unchecked")
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("job_name","作业名称");
		put("job_group","作业组");
		put("trigger_name","触发器名称");
		put("trigger_group","触发器组");
		put("recovering","被恢复");
		put("refire_count","重触发次数");
		put("fire_time","实际触发时间");
		put("scheduled_fire_time","计划触发时间");
		put("previous_fire_time","上次触发时间");
		put("next_fire_time","下次触发时间");
		put("result","结果");
		put("job_run_time","运行时间");
		put("fire_instance_id","触发实例ID");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"job_name", "job_name_name"}; 
}