//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorize/util/IRmAuthorizeConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:41
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorize.util --> IRmAuthorizeConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:41 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorize.util;

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

public interface IRmAuthorizeConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmAuthorizeService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_AUTHORIZE ( ID, NAME, BS_KEYWORD, IS_ALONE_TABLE, AUTHORIZE_RESOURCE_TABLE_NAME, AUTHORIZE_RESREC_TABLE_NAME, AUTHORIZE_AFFIX_TABLE_NAME, SETTIING_OPTION, CUSTOM_CODE, DESCRIPTION, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_AUTHORIZE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_AUTHORIZE ";

    public final static String SQL_FIND_BY_ID = "select RM_AUTHORIZE.ID, RM_AUTHORIZE.NAME, RM_AUTHORIZE.BS_KEYWORD, RM_AUTHORIZE.IS_ALONE_TABLE, RM_AUTHORIZE.AUTHORIZE_RESOURCE_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_RESREC_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_AFFIX_TABLE_NAME, RM_AUTHORIZE.SETTIING_OPTION, RM_AUTHORIZE.CUSTOM_CODE, RM_AUTHORIZE.DESCRIPTION, RM_AUTHORIZE.USABLE_STATUS, RM_AUTHORIZE.MODIFY_DATE, RM_AUTHORIZE.MODIFY_IP, RM_AUTHORIZE.MODIFY_USER_ID from RM_AUTHORIZE where RM_AUTHORIZE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_AUTHORIZE set NAME=?, BS_KEYWORD=?, IS_ALONE_TABLE=?, AUTHORIZE_RESOURCE_TABLE_NAME=?, AUTHORIZE_RESREC_TABLE_NAME=?, AUTHORIZE_AFFIX_TABLE_NAME=?, SETTIING_OPTION=?, CUSTOM_CODE=?, DESCRIPTION=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_AUTHORIZE.ID) from RM_AUTHORIZE";
    
    public final static String SQL_QUERY_ALL = "select RM_AUTHORIZE.ID, RM_AUTHORIZE.NAME, RM_AUTHORIZE.BS_KEYWORD, RM_AUTHORIZE.IS_ALONE_TABLE, RM_AUTHORIZE.AUTHORIZE_RESOURCE_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_RESREC_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_AFFIX_TABLE_NAME, RM_AUTHORIZE.SETTIING_OPTION, RM_AUTHORIZE.DESCRIPTION from RM_AUTHORIZE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_AUTHORIZE.ID, RM_AUTHORIZE.NAME, RM_AUTHORIZE.BS_KEYWORD, RM_AUTHORIZE.IS_ALONE_TABLE, RM_AUTHORIZE.AUTHORIZE_RESOURCE_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_RESREC_TABLE_NAME, RM_AUTHORIZE.AUTHORIZE_AFFIX_TABLE_NAME, RM_AUTHORIZE.SETTIING_OPTION, RM_AUTHORIZE.CUSTOM_CODE, RM_AUTHORIZE.DESCRIPTION, RM_AUTHORIZE.USABLE_STATUS, RM_AUTHORIZE.MODIFY_DATE, RM_AUTHORIZE.MODIFY_IP, RM_AUTHORIZE.MODIFY_USER_ID from RM_AUTHORIZE";
    
    //表名
    public final static String TABLE_NAME = "RM_AUTHORIZE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "权限类别";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("name","名称");
		put("bs_keyword","业务关键字");
		put("is_alone_table","是否单独建表");
		put("authorize_resource_table_name","授权资源表名");
		put("authorize_resrec_table_name","授权记录表名");
		put("authorize_affix_table_name","授权资源附加数据表名");
		put("settiing_option","权限配置选项类型");
		put("custom_code","定制代码");
		put("description","描述");
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