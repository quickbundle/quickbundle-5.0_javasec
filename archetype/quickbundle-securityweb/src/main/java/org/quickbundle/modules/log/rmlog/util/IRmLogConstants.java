//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/log/rmlog/util/IRmLogConstants.java
//代码生成时,系统时间: 2010-11-30 22:31:36
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> cu-tm
 * 
 * 文件名称: org.quickbundle.modules.log.rmlog.util --> IRmLogConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:31:36 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlog.util;

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

public interface IRmLogConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmLogService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_LOG ( ID, LOG_TYPE_ID, ACTION_DATE, ACTION_IP, ACTION_MODULE, ACTION_TYPE, OWNER_ORG_ID, USER_ID, USER_ID_NAME, ACTION_UUID, CONTENT) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_LOG where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_LOG ";

    public final static String SQL_FIND_BY_ID = "select RM_LOG.ID, RM_LOG.LOG_TYPE_ID, RM_LOG.ACTION_DATE, RM_LOG.ACTION_IP, RM_LOG.ACTION_MODULE, RM_LOG.ACTION_TYPE, RM_LOG.OWNER_ORG_ID, RM_LOG.USER_ID, RM_LOG.USER_ID_NAME, RM_LOG.ACTION_UUID, RM_LOG.CONTENT from RM_LOG where RM_LOG.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_LOG set LOG_TYPE_ID=?, ACTION_DATE=?, ACTION_IP=?, ACTION_MODULE=?, ACTION_TYPE=?, OWNER_ORG_ID=?, USER_ID=?, USER_ID_NAME=?, ACTION_UUID=?, CONTENT=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_LOG.ID) from RM_LOG";
    
    public final static String SQL_QUERY_ALL = "select RM_LOG.ID, RM_LOG.LOG_TYPE_ID, RM_LOG.ACTION_DATE, RM_LOG.ACTION_IP, RM_LOG.ACTION_MODULE, RM_LOG.ACTION_TYPE, RM_LOG.OWNER_ORG_ID, RM_LOG.USER_ID, RM_LOG.USER_ID_NAME, RM_LOG.ACTION_UUID, RM_LOG.CONTENT from RM_LOG";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_LOG.ID, RM_LOG.LOG_TYPE_ID, RM_LOG.ACTION_DATE, RM_LOG.ACTION_IP, RM_LOG.ACTION_MODULE, RM_LOG.ACTION_TYPE, RM_LOG.OWNER_ORG_ID, RM_LOG.USER_ID, RM_LOG.USER_ID_NAME, RM_LOG.ACTION_UUID, RM_LOG.CONTENT from RM_LOG";
    
    //表名
    public final static String TABLE_NAME = "RM_LOG";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "日志";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("log_type_id","日志类型ID");
		put("action_date","操作时间");
		put("action_ip","操作IP");
		put("action_module","操作模块");
		put("action_type","操作类型");
		put("owner_org_id","所属组织ID");
		put("user_id","用户ID");
		put("user_id_name","用户名称");
		put("action_uuid","唯一识别码");
		put("content","操作内容");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"log_type_id" };
}