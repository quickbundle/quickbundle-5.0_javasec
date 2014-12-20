//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/util/IRmAffixConstants.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.util --> IRmAffixConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix.util;

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

public interface IRmAffixConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmAffixService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_AFFIX ( ID, BS_KEYWORD, RECORD_ID, ORDER_NUMBER, TITLE, OLD_NAME, SAVE_NAME, SAVE_SIZE, MIME_TYPE, ENCODING, DESCRIPTION, AUTHOR, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_AFFIX where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_AFFIX ";

    public final static String SQL_FIND_BY_ID = "select RM_AFFIX.ID, RM_AFFIX.BS_KEYWORD, RM_AFFIX.RECORD_ID, RM_AFFIX.ORDER_NUMBER, RM_AFFIX.TITLE, RM_AFFIX.OLD_NAME, RM_AFFIX.SAVE_NAME, RM_AFFIX.SAVE_SIZE, RM_AFFIX.MIME_TYPE, RM_AFFIX.ENCODING, RM_AFFIX.DESCRIPTION, RM_AFFIX.AUTHOR, RM_AFFIX.USABLE_STATUS, RM_AFFIX.MODIFY_DATE, RM_AFFIX.MODIFY_IP, RM_AFFIX.MODIFY_USER_ID from RM_AFFIX where RM_AFFIX.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_AFFIX set BS_KEYWORD=?, RECORD_ID=?, ORDER_NUMBER=?, TITLE=?, OLD_NAME=?, SAVE_NAME=?, SAVE_SIZE=?, MIME_TYPE=?, ENCODING=?, DESCRIPTION=?, AUTHOR=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_AFFIX.ID) from RM_AFFIX";
    
	public final static String SQL_QUERY_ALL_EXPORT = "select RM_AFFIX.ID, RM_AFFIX.BS_KEYWORD, RM_AFFIX.RECORD_ID, RM_AFFIX.ORDER_NUMBER, RM_AFFIX.TITLE, RM_AFFIX.OLD_NAME, RM_AFFIX.SAVE_NAME, RM_AFFIX.SAVE_SIZE, RM_AFFIX.MIME_TYPE, RM_AFFIX.ENCODING, RM_AFFIX.DESCRIPTION, RM_AFFIX.AUTHOR, RM_AFFIX.USABLE_STATUS, RM_AFFIX.MODIFY_DATE, RM_AFFIX.MODIFY_IP, RM_AFFIX.MODIFY_USER_ID from RM_AFFIX";
	
    public final static String SQL_QUERY_ALL = SQL_QUERY_ALL_EXPORT;//"select RM_AFFIX.ID, RM_AFFIX.BS_KEYWORD, RM_AFFIX.RECORD_ID, RM_AFFIX.ORDER_NUMBER, RM_AFFIX.TITLE, RM_AFFIX.OLD_NAME, RM_AFFIX.SAVE_SIZE, RM_AFFIX.MIME_TYPE, RM_AFFIX.ENCODING, RM_AFFIX.DESCRIPTION, RM_AFFIX.AUTHOR from RM_AFFIX";
    
    //表名
    public final static String TABLE_NAME = "RM_AFFIX";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "附件";

    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
    	put("ID","主键");
    	put("BS_KEYWORD","业务关键字");
    	put("mime_type", "文件类型");
    	put("RECORD_ID","主记录ID");
    	put("ORDER_NUMBER","顺序数");
    	put("TITLE","标题");
    	put("OLD_NAME","原文件名");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DEFAULT_SQL_WHERE_USABLE = ""; //" where " + DESC_USABLE_STATUS_EVALUATE_ENABLE
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " where ";
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"mime_type" };
}