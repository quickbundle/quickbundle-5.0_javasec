//代码生成时,文件路径: D:/rc/svn/fm/code/cu-tm/src/main/java/modules/lock/rmlock/util/IRmLockConstants.java
//代码生成时,系统时间: 2010-11-30 22:19:59
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.lock.rmlock.util --> IRmLockConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-30 22:19:59 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.lock.rmlock.util;

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

public interface IRmLockConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmLockService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_LOCK ( ID, USER_ID, BS_KEYWORD, LOCK_CONTENT, LOCK_DATE, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_LOCK where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_LOCK ";

    public final static String SQL_FIND_BY_ID = "select RM_LOCK.ID, RM_LOCK.USER_ID, RM_LOCK.BS_KEYWORD, RM_LOCK.LOCK_CONTENT, RM_LOCK.LOCK_DATE, RM_LOCK.USABLE_STATUS, RM_LOCK.MODIFY_DATE, RM_LOCK.MODIFY_IP, RM_LOCK.MODIFY_USER_ID from RM_LOCK where RM_LOCK.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_LOCK set USER_ID=?, BS_KEYWORD=?, LOCK_CONTENT=?, LOCK_DATE=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_LOCK.ID) from RM_LOCK";
    
    public final static String SQL_QUERY_ALL = "select RM_LOCK.ID, RM_LOCK.USER_ID, RM_LOCK.BS_KEYWORD, RM_LOCK.LOCK_CONTENT, RM_LOCK.LOCK_DATE from RM_LOCK";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_LOCK.ID, RM_LOCK.USER_ID, RM_LOCK.BS_KEYWORD, RM_LOCK.LOCK_CONTENT, RM_LOCK.LOCK_DATE, RM_LOCK.USABLE_STATUS, RM_LOCK.MODIFY_DATE, RM_LOCK.MODIFY_IP, RM_LOCK.MODIFY_USER_ID from RM_LOCK";
    
    //表名
    public final static String TABLE_NAME = "RM_LOCK";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "业务锁";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("user_id","用户ID");
		put("bs_keyword","业务关键字");
		put("lock_content","加锁关键值");
		put("lock_date","锁定时间");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"id" };
}