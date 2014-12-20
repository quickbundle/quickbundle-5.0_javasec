//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuseronlinerecord/util/IRmUserOnlineRecordConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:36
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuseronlinerecord.util --> IRmUserOnlineRecordConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:36 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuseronlinerecord.util;

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

public interface IRmUserOnlineRecordConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmUserOnlineRecordService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_USER_ONLINE_RECORD ( ID, USER_ID, LOGIN_TIME, CLUSTER_NODE_ID, LOGIN_SIGN, LOGIN_IP, LOGIN_UUID, LOGOUT_TIME, LOGOUT_TYPE, ONLINE_TIME, LAST_OPERATION, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_USER_ONLINE_RECORD where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_USER_ONLINE_RECORD ";

    public final static String SQL_FIND_BY_ID = "select RM_USER_ONLINE_RECORD.ID, RM_USER_ONLINE_RECORD.USER_ID, RM_USER_ONLINE_RECORD.LOGIN_TIME, RM_USER_ONLINE_RECORD.CLUSTER_NODE_ID, RM_USER_ONLINE_RECORD.LOGIN_SIGN, RM_USER_ONLINE_RECORD.LOGIN_IP, RM_USER_ONLINE_RECORD.LOGIN_UUID, RM_USER_ONLINE_RECORD.LOGOUT_TIME, RM_USER_ONLINE_RECORD.LOGOUT_TYPE, RM_USER_ONLINE_RECORD.ONLINE_TIME, RM_USER_ONLINE_RECORD.LAST_OPERATION, RM_USER_ONLINE_RECORD.USABLE_STATUS, RM_USER_ONLINE_RECORD.MODIFY_DATE, RM_USER_ONLINE_RECORD.MODIFY_IP, RM_USER_ONLINE_RECORD.MODIFY_USER_ID from RM_USER_ONLINE_RECORD where RM_USER_ONLINE_RECORD.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_USER_ONLINE_RECORD set USER_ID=?, LOGIN_TIME=?, CLUSTER_NODE_ID=?, LOGIN_SIGN=?, LOGIN_IP=?, LOGIN_UUID=?, LOGOUT_TIME=?, LOGOUT_TYPE=?, ONLINE_TIME=?, LAST_OPERATION=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_USER_ONLINE_RECORD.ID) from RM_USER_ONLINE_RECORD";
    
    public final static String SQL_QUERY_ALL = "select RM_USER_ONLINE_RECORD.ID, RM_USER_ONLINE_RECORD.USER_ID, RM_USER_ONLINE_RECORD.LOGIN_TIME, RM_USER_ONLINE_RECORD.CLUSTER_NODE_ID, RM_USER_ONLINE_RECORD.LOGIN_SIGN, RM_USER_ONLINE_RECORD.LOGIN_IP, RM_USER_ONLINE_RECORD.LOGIN_UUID, RM_USER_ONLINE_RECORD.LOGOUT_TIME, RM_USER_ONLINE_RECORD.LOGOUT_TYPE, RM_USER_ONLINE_RECORD.ONLINE_TIME, RM_USER_ONLINE_RECORD.LAST_OPERATION from RM_USER_ONLINE_RECORD";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_USER_ONLINE_RECORD.ID, RM_USER_ONLINE_RECORD.USER_ID, RM_USER_ONLINE_RECORD.LOGIN_TIME, RM_USER_ONLINE_RECORD.CLUSTER_NODE_ID, RM_USER_ONLINE_RECORD.LOGIN_SIGN, RM_USER_ONLINE_RECORD.LOGIN_IP, RM_USER_ONLINE_RECORD.LOGIN_UUID, RM_USER_ONLINE_RECORD.LOGOUT_TIME, RM_USER_ONLINE_RECORD.LOGOUT_TYPE, RM_USER_ONLINE_RECORD.ONLINE_TIME, RM_USER_ONLINE_RECORD.LAST_OPERATION, RM_USER_ONLINE_RECORD.USABLE_STATUS, RM_USER_ONLINE_RECORD.MODIFY_DATE, RM_USER_ONLINE_RECORD.MODIFY_IP, RM_USER_ONLINE_RECORD.MODIFY_USER_ID from RM_USER_ONLINE_RECORD";
    
    //表名
    public final static String TABLE_NAME = "RM_USER_ONLINE_RECORD";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "用户在线记录";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("user_id","用户ID");
		put("login_time","登录时间");
		put("cluster_node_id","集群节点ID");
		put("login_sign","客户端会话标识");
		put("login_ip","登录IP");
		put("login_uuid","唯一识别码");
		put("logout_time","注销时间");
		put("logout_type","注销类型");
		put("online_time","在线时间");
		put("last_operation","最后操作");
		put("usable_status","数据可用状态");
		put("modify_date","修改日期");
		put("modify_ip","修改IP");
		put("modify_user_id","修改用户ID");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"user_id" };
}