//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresourcerecord/util/IRmAuthorizeResourceRecordConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:43
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresourcerecord.util --> IRmAuthorizeResourceRecordConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:43 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresourcerecord.util;

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

public interface IRmAuthorizeResourceRecordConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmAuthorizeResourceRecordService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_AUTHORIZE_RESOURCE_RECORD ( ID, AUTHORIZE_RESOURCE_ID, PARTY_ID, AUTHORIZE_STATUS, IS_AFFIX_DATA, IS_RECURSIVE, ACCESS_TYPE, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_AUTHORIZE_RESOURCE_RECORD where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_AUTHORIZE_RESOURCE_RECORD ";

    public final static String SQL_FIND_BY_ID = "select RM_AUTHORIZE_RESOURCE_RECORD.ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_RESOURCE_ID, RM_AUTHORIZE_RESOURCE_RECORD.PARTY_ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_STATUS, RM_AUTHORIZE_RESOURCE_RECORD.IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE_RECORD.IS_RECURSIVE, RM_AUTHORIZE_RESOURCE_RECORD.ACCESS_TYPE, RM_AUTHORIZE_RESOURCE_RECORD.USABLE_STATUS, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_DATE, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_IP, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_USER_ID from RM_AUTHORIZE_RESOURCE_RECORD where RM_AUTHORIZE_RESOURCE_RECORD.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_AUTHORIZE_RESOURCE_RECORD set AUTHORIZE_RESOURCE_ID=?, PARTY_ID=?, AUTHORIZE_STATUS=?, IS_AFFIX_DATA=?, IS_RECURSIVE=?, ACCESS_TYPE=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_AUTHORIZE_RESOURCE_RECORD.ID) from RM_AUTHORIZE_RESOURCE_RECORD";
    
    public final static String SQL_QUERY_ALL = "select RM_AUTHORIZE_RESOURCE_RECORD.ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_RESOURCE_ID, RM_AUTHORIZE_RESOURCE_RECORD.PARTY_ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_STATUS, RM_AUTHORIZE_RESOURCE_RECORD.IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE_RECORD.IS_RECURSIVE, RM_AUTHORIZE_RESOURCE_RECORD.ACCESS_TYPE from RM_AUTHORIZE_RESOURCE_RECORD";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_AUTHORIZE_RESOURCE_RECORD.ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_RESOURCE_ID, RM_AUTHORIZE_RESOURCE_RECORD.PARTY_ID, RM_AUTHORIZE_RESOURCE_RECORD.AUTHORIZE_STATUS, RM_AUTHORIZE_RESOURCE_RECORD.IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE_RECORD.IS_RECURSIVE, RM_AUTHORIZE_RESOURCE_RECORD.ACCESS_TYPE, RM_AUTHORIZE_RESOURCE_RECORD.USABLE_STATUS, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_DATE, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_IP, RM_AUTHORIZE_RESOURCE_RECORD.MODIFY_USER_ID from RM_AUTHORIZE_RESOURCE_RECORD";
    
    //表名
    public final static String TABLE_NAME = "RM_AUTHORIZE_RESOURCE_RECORD";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "授权记录";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("authorize_resource_id","授权资源ID");
		put("party_id","访问者团体ID");
		put("authorize_status","授权情况(允许或拒绝)");
		put("is_affix_data","有附加数据");
		put("is_recursive","递归传播权限");
		put("access_type","访问方式代码");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"authorize_resource_id" };
}