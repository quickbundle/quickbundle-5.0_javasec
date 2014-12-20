//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobtrigger/util/IJobTriggerConstants.java
//代码生成时,系统时间: 2012-04-02 22:28:48
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobtrigger.util --> IJobTriggerConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:48 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobtrigger.util;

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

public interface IJobTriggerConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IJobTriggerService";
    
    //表名
    public final static String TABLE_NAME = "JOB_TRIGGER";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "作业部署";
    
    //列名汉化
    @SuppressWarnings("unchecked")
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("job_name","作业名称");
		put("job_group","作业组");
		put("trigger_name","触发器名称");
		put("trigger_group","触发器组");
		put("cron_expression","Cron表达式");
		put("description","描述");
		put("next_fire_time","下次触发时间");
		put("prev_fire_time","上次触发时间");
		put("priority","优先级");
		put("trigger_state","触发器状态");
		put("start_time","开始时间");
		put("end_time","结束时间");
		put("calendar_name","日历名称");
		put("misfire_instr","未触发时指令");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"trigger_name", "trigger_group"}; 
}