//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/modules/log/rmlogtype/util/IRmLogTypeConstants.java
//代码生成时,系统时间: 2010-12-04 14:05:23
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.log.rmlogtype.util --> IRmLogTypeConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-12-04 14:05:23 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.log.rmlogtype.util;

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

public interface IRmLogTypeConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmLogTypeService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_LOG_TYPE ( ID, BS_KEYWORD, NAME, IS_RECORD, IS_ALONE_TABLE, TABLE_NAME, PATTERN_VALUE, MATCH_PRIORITY, MAX_LOG_SUM, DESCRIPTION, CUSTOM_XML, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_LOG_TYPE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_LOG_TYPE ";

    public final static String SQL_FIND_BY_ID = "select RM_LOG_TYPE.ID, RM_LOG_TYPE.BS_KEYWORD, RM_LOG_TYPE.NAME, RM_LOG_TYPE.IS_RECORD, RM_LOG_TYPE.IS_ALONE_TABLE, RM_LOG_TYPE.TABLE_NAME, RM_LOG_TYPE.PATTERN_VALUE, RM_LOG_TYPE.MATCH_PRIORITY, RM_LOG_TYPE.MAX_LOG_SUM, RM_LOG_TYPE.DESCRIPTION, RM_LOG_TYPE.CUSTOM_XML, RM_LOG_TYPE.USABLE_STATUS, RM_LOG_TYPE.MODIFY_DATE, RM_LOG_TYPE.MODIFY_IP, RM_LOG_TYPE.MODIFY_USER_ID from RM_LOG_TYPE where RM_LOG_TYPE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_LOG_TYPE set BS_KEYWORD=?, NAME=?, IS_RECORD=?, IS_ALONE_TABLE=?, TABLE_NAME=?, PATTERN_VALUE=?, MATCH_PRIORITY=?, MAX_LOG_SUM=?, DESCRIPTION=?, CUSTOM_XML=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_LOG_TYPE.ID) from RM_LOG_TYPE";
    
    public final static String SQL_QUERY_ALL = "select RM_LOG_TYPE.ID, RM_LOG_TYPE.BS_KEYWORD, RM_LOG_TYPE.NAME, RM_LOG_TYPE.IS_RECORD, RM_LOG_TYPE.IS_ALONE_TABLE, RM_LOG_TYPE.TABLE_NAME, RM_LOG_TYPE.PATTERN_VALUE, RM_LOG_TYPE.MATCH_PRIORITY, RM_LOG_TYPE.MAX_LOG_SUM, RM_LOG_TYPE.DESCRIPTION, RM_LOG_TYPE.CUSTOM_XML from RM_LOG_TYPE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_LOG_TYPE.ID, RM_LOG_TYPE.BS_KEYWORD, RM_LOG_TYPE.NAME, RM_LOG_TYPE.IS_RECORD, RM_LOG_TYPE.IS_ALONE_TABLE, RM_LOG_TYPE.TABLE_NAME, RM_LOG_TYPE.PATTERN_VALUE, RM_LOG_TYPE.MATCH_PRIORITY, RM_LOG_TYPE.MAX_LOG_SUM, RM_LOG_TYPE.DESCRIPTION, RM_LOG_TYPE.CUSTOM_XML, RM_LOG_TYPE.USABLE_STATUS, RM_LOG_TYPE.MODIFY_DATE, RM_LOG_TYPE.MODIFY_IP, RM_LOG_TYPE.MODIFY_USER_ID from RM_LOG_TYPE";
    
    //表名
    public final static String TABLE_NAME = "RM_LOG_TYPE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "日志类型";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("bs_keyword","业务关键字");
		put("name","名称");
		put("is_record","是否记录");
		put("is_alone_table","是否单独建表");
		put("table_name","日志数据表名");
		put("pattern_value","正则匹配");
		put("match_priority","匹配优先级");
		put("max_log_sum","最大日志量");
		put("description","描述");
		put("custom_xml","扩展XML");
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