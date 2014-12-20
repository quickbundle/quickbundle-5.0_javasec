//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/util/IRmCodeTypeConstants.java
//代码生成时,系统时间: 2010-04-08 21:15:45.984
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.util --> IRmCodeTypeConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.984 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.util;

import org.quickbundle.project.IGlobalConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public interface IRmCodeTypeConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmCodeTypeService";

    //Sql语句
    public final static String AFTER_SELECT_ALL   = "RM_CODE_TYPE.ID, (SELECT COUNT(ID) FROM RM_CODE_DATA WHERE RM_CODE_DATA.CODE_TYPE_ID=RM_CODE_TYPE.ID) DATA_SUM, RM_CODE_TYPE.TYPE_KEYWORD, RM_CODE_TYPE.NAME, RM_CODE_TYPE.REMARK, RM_CODE_TYPE.USABLE_STATUS, RM_CODE_TYPE.MODIFY_DATE, RM_CODE_TYPE.MODIFY_IP, RM_CODE_TYPE.MODIFY_USER_ID";
    public final static String AFTER_SELECT_SHORT = "RM_CODE_TYPE.ID, (SELECT COUNT(ID) FROM RM_CODE_DATA WHERE RM_CODE_DATA.CODE_TYPE_ID=RM_CODE_TYPE.ID) DATA_SUM, RM_CODE_TYPE.TYPE_KEYWORD, RM_CODE_TYPE.NAME, RM_CODE_TYPE.REMARK";

    public final static String SQL_INSERT = "insert into RM_CODE_TYPE ( ID, TYPE_KEYWORD, NAME, REMARK, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_CODE_TYPE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_CODE_TYPE ";

    public final static String SQL_FIND_BY_ID = "select " + AFTER_SELECT_ALL + " from RM_CODE_TYPE where RM_CODE_TYPE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_CODE_TYPE set TYPE_KEYWORD=?, NAME=?, REMARK=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_CODE_TYPE.ID) from RM_CODE_TYPE";

    public final static String SQL_QUERY_ALL = "select " + AFTER_SELECT_SHORT + " from RM_CODE_TYPE";
    
	public final static String SQL_QUERY_ALL_EXPORT = "select " + AFTER_SELECT_ALL + " from RM_CODE_TYPE";

    //消息定义
    public final static String MESSAGE_NO_CONDITION_KEY = "没有得到查询条件关键字！";
    
    //表名
    public final static String TABLE_NAME = "RM_CODE_TYPE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "编码类型";
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"type_keyword" };
}
