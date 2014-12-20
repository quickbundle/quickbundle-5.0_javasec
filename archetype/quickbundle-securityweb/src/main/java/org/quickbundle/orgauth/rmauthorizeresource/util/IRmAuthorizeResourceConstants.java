//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresource/util/IRmAuthorizeResourceConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresource.util --> IRmAuthorizeResourceConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresource.util;

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

public interface IRmAuthorizeResourceConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmAuthorizeResourceService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_AUTHORIZE_RESOURCE ( ID, AUTHORIZE_ID, OLD_RESOURCE_ID, DEFAULT_ACCESS, DEFAULT_IS_AFFIX_DATA, DEFAULT_IS_RECURSIVE, DEFAULT_ACCESS_TYPE, TOTAL_CODE, NAME, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_AUTHORIZE_RESOURCE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_AUTHORIZE_RESOURCE ";

    public final static String SQL_FIND_BY_ID = "select RM_AUTHORIZE_RESOURCE.ID, RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID, RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_RECURSIVE, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS_TYPE, RM_AUTHORIZE_RESOURCE.TOTAL_CODE, RM_AUTHORIZE_RESOURCE.NAME, RM_AUTHORIZE_RESOURCE.USABLE_STATUS, RM_AUTHORIZE_RESOURCE.MODIFY_DATE, RM_AUTHORIZE_RESOURCE.MODIFY_IP, RM_AUTHORIZE_RESOURCE.MODIFY_USER_ID from RM_AUTHORIZE_RESOURCE where RM_AUTHORIZE_RESOURCE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_AUTHORIZE_RESOURCE set AUTHORIZE_ID=?, OLD_RESOURCE_ID=?, DEFAULT_ACCESS=?, DEFAULT_IS_AFFIX_DATA=?, DEFAULT_IS_RECURSIVE=?, DEFAULT_ACCESS_TYPE=?, TOTAL_CODE=?, NAME=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_AUTHORIZE_RESOURCE.ID) from RM_AUTHORIZE_RESOURCE";
    
    public final static String SQL_QUERY_ALL = "select RM_AUTHORIZE_RESOURCE.ID, RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID, RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_RECURSIVE, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS_TYPE, RM_AUTHORIZE_RESOURCE.TOTAL_CODE, RM_AUTHORIZE_RESOURCE.NAME from RM_AUTHORIZE_RESOURCE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_AUTHORIZE_RESOURCE.ID, RM_AUTHORIZE_RESOURCE.AUTHORIZE_ID, RM_AUTHORIZE_RESOURCE.OLD_RESOURCE_ID, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_AFFIX_DATA, RM_AUTHORIZE_RESOURCE.DEFAULT_IS_RECURSIVE, RM_AUTHORIZE_RESOURCE.DEFAULT_ACCESS_TYPE, RM_AUTHORIZE_RESOURCE.TOTAL_CODE, RM_AUTHORIZE_RESOURCE.NAME, RM_AUTHORIZE_RESOURCE.USABLE_STATUS, RM_AUTHORIZE_RESOURCE.MODIFY_DATE, RM_AUTHORIZE_RESOURCE.MODIFY_IP, RM_AUTHORIZE_RESOURCE.MODIFY_USER_ID from RM_AUTHORIZE_RESOURCE";
    
    //表名
    public final static String TABLE_NAME = "RM_AUTHORIZE_RESOURCE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "授权资源";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("authorize_id","权限类别ID");
		put("old_resource_id","资源原始ID");
		put("default_access","默认直接访问");
		put("default_is_affix_data","默认有附加数据");
		put("default_is_recursive","默认递归传播权限");
		put("default_access_type","默认访问方式代码");
		put("total_code","树形编码");
		put("name","名称");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"authorize_id", "authorize_id_name", "bs_keyword"};
}