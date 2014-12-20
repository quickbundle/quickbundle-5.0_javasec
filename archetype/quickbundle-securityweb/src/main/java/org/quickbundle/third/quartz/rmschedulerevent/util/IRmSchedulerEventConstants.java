//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/rmschedulerevent/util/IRmSchedulerEventConstants.java
//代码生成时,系统时间: 2012-04-02 22:28:46
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.rmschedulerevent.util --> IRmSchedulerEventConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:46 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.rmschedulerevent.util;

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

public interface IRmSchedulerEventConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmSchedulerEventService";

    //Sql语句
    public final static String AFTER_SELECT_ALL   = "RM_SCHEDULER_EVENT.ID, RM_SCHEDULER_EVENT.JOB_NAME, RM_SCHEDULER_EVENT.JOB_GROUP, RM_SCHEDULER_EVENT.TRIGGER_NAME, RM_SCHEDULER_EVENT.TRIGGER_GROUP, RM_SCHEDULER_EVENT.FIRE_INSTANCE_ID, RM_SCHEDULER_EVENT.EVENT_TYPE, RM_SCHEDULER_EVENT.COST_MILLIS, RM_SCHEDULER_EVENT.RESULT_STATUS, RM_SCHEDULER_EVENT.CREATE_TIME, RM_SCHEDULER_EVENT.CREATE_IP, RM_SCHEDULER_EVENT.UUID, RM_SCHEDULER_EVENT.IS_ARCHIVE, RM_SCHEDULER_EVENT.RESULT";
    public final static String AFTER_SELECT_SHORT = "RM_SCHEDULER_EVENT.ID, RM_SCHEDULER_EVENT.JOB_NAME, RM_SCHEDULER_EVENT.JOB_GROUP, RM_SCHEDULER_EVENT.TRIGGER_NAME, RM_SCHEDULER_EVENT.TRIGGER_GROUP, RM_SCHEDULER_EVENT.FIRE_INSTANCE_ID, RM_SCHEDULER_EVENT.EVENT_TYPE, RM_SCHEDULER_EVENT.COST_MILLIS, RM_SCHEDULER_EVENT.RESULT_STATUS, RM_SCHEDULER_EVENT.CREATE_TIME, RM_SCHEDULER_EVENT.CREATE_IP, RM_SCHEDULER_EVENT.UUID, RM_SCHEDULER_EVENT.IS_ARCHIVE, RM_SCHEDULER_EVENT.RESULT";

    public final static String SQL_INSERT = "insert into RM_SCHEDULER_EVENT ( ID, JOB_NAME, JOB_GROUP, TRIGGER_NAME, TRIGGER_GROUP, FIRE_INSTANCE_ID, EVENT_TYPE, COST_MILLIS, RESULT_STATUS, CREATE_TIME, CREATE_IP, UUID, IS_ARCHIVE, RESULT) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_SCHEDULER_EVENT where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_SCHEDULER_EVENT ";

    public final static String SQL_FIND_BY_ID = "select " + AFTER_SELECT_ALL + " from RM_SCHEDULER_EVENT where RM_SCHEDULER_EVENT.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_SCHEDULER_EVENT set JOB_NAME=?, JOB_GROUP=?, TRIGGER_NAME=?, TRIGGER_GROUP=?, FIRE_INSTANCE_ID=?, EVENT_TYPE=?, COST_MILLIS=?, RESULT_STATUS=?, CREATE_TIME=?, UUID=?, IS_ARCHIVE=?, RESULT=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_SCHEDULER_EVENT.ID) from RM_SCHEDULER_EVENT";
    
    public final static String SQL_QUERY_ALL = "select " + AFTER_SELECT_SHORT + " from RM_SCHEDULER_EVENT";

	public final static String SQL_QUERY_ALL_EXPORT = "select " + AFTER_SELECT_ALL + " from RM_SCHEDULER_EVENT";
    
    //表名
    public final static String TABLE_NAME = "RM_SCHEDULER_EVENT";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "调度事件";
    
    //列名汉化
    @SuppressWarnings("unchecked")
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("job_name","作业名称");
		put("job_group","作业组");
		put("trigger_name","触发器名称");
		put("trigger_group","触发器组");
		put("fire_instance_id","触发实例ID");
		put("event_type","事件类型");
		put("cost_millis","耗时");
		put("result_status","结果状态");
		put("create_time","创建时间");
		put("create_ip","创建IP");
		put("uuid","唯一识别码");
		put("is_archive","是否归档");
		put("result","结果");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"event_type", "event_type_name"}; 
}