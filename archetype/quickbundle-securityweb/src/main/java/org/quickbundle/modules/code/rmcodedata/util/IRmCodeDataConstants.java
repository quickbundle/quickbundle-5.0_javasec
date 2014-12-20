//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodedata/util/IRmCodeDataConstants.java
//代码生成时,系统时间: 2010-04-08 21:15:47.109
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodedata.util --> IRmCodeDataConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:47.109 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodedata.util;

import org.quickbundle.project.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IRmCodeDataConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmCodeDataService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_CODE_DATA ( ID, CODE_TYPE_ID, DATA_KEY, ENABLE_STATUS, DATA_VALUE, TOTAL_CODE, REMARK, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_CODE_DATA where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_CODE_DATA ";

    public final static String SQL_FIND_BY_ID = "select RM_CODE_DATA.ID, RM_CODE_DATA.CODE_TYPE_ID, (SELECT RM_CODE_TYPE.TYPE_KEYWORD FROM RM_CODE_TYPE WHERE RM_CODE_TYPE.ID=RM_CODE_DATA.CODE_TYPE_ID) CODE_TYPE_ID_NAME, RM_CODE_DATA.DATA_KEY, RM_CODE_DATA.ENABLE_STATUS, RM_CODE_DATA.DATA_VALUE, RM_CODE_DATA.TOTAL_CODE, RM_CODE_DATA.REMARK, RM_CODE_DATA.USABLE_STATUS, RM_CODE_DATA.MODIFY_DATE, RM_CODE_DATA.MODIFY_IP, RM_CODE_DATA.MODIFY_USER_ID from RM_CODE_DATA where RM_CODE_DATA.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_CODE_DATA set CODE_TYPE_ID=?, DATA_KEY=?, ENABLE_STATUS=?, DATA_VALUE=?, TOTAL_CODE=?, REMARK=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_CODE_DATA.ID) from RM_CODE_DATA";
    
    public final static String SQL_QUERY_ALL = "select RM_CODE_DATA.ID, RM_CODE_DATA.CODE_TYPE_ID, (SELECT RM_CODE_TYPE.TYPE_KEYWORD FROM RM_CODE_TYPE WHERE RM_CODE_TYPE.ID=RM_CODE_DATA.CODE_TYPE_ID) CODE_TYPE_ID_NAME, RM_CODE_DATA.DATA_KEY, RM_CODE_DATA.ENABLE_STATUS, RM_CODE_DATA.DATA_VALUE, RM_CODE_DATA.TOTAL_CODE, RM_CODE_DATA.REMARK from RM_CODE_DATA";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_CODE_DATA.ID, RM_CODE_DATA.CODE_TYPE_ID, (SELECT RM_CODE_TYPE.TYPE_KEYWORD FROM RM_CODE_TYPE WHERE RM_CODE_TYPE.ID=RM_CODE_DATA.CODE_TYPE_ID) CODE_TYPE_ID_NAME, RM_CODE_DATA.DATA_KEY, RM_CODE_DATA.ENABLE_STATUS, RM_CODE_DATA.DATA_VALUE, RM_CODE_DATA.TOTAL_CODE, RM_CODE_DATA.REMARK, RM_CODE_DATA.USABLE_STATUS, RM_CODE_DATA.MODIFY_DATE, RM_CODE_DATA.MODIFY_IP, RM_CODE_DATA.MODIFY_USER_ID from RM_CODE_DATA";

    
    //表名
    public final static String TABLE_NAME = "RM_CODE_DATA";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "编码数据";
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ORDER_BY_SYMBOL + "DATA_KEY";
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"code_type_id", "code_type_id_name" };
}
