//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmfunctionnode/util/IRmFunctionNodeConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:40
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmfunctionnode.util --> IRmFunctionNodeConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:40 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmfunctionnode.util;

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

public interface IRmFunctionNodeConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmFunctionNodeService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_FUNCTION_NODE ( ID, NODE_TYPE, FUNCTION_PROPERTY, NAME, ENABLE_STATUS, TOTAL_CODE, ORDER_CODE, FUNCNODE_AUTHORIZE_TYPE, DESCRIPTION, DATA_VALUE, PATTERN_VALUE, IS_LEAF, ICON, HELP_NAME, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_FUNCTION_NODE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_FUNCTION_NODE ";

    public final static String SQL_FIND_BY_ID = "select RM_FUNCTION_NODE.ID, RM_FUNCTION_NODE.NODE_TYPE, RM_FUNCTION_NODE.FUNCTION_PROPERTY, RM_FUNCTION_NODE.NAME, RM_FUNCTION_NODE.ENABLE_STATUS, RM_FUNCTION_NODE.TOTAL_CODE, RM_FUNCTION_NODE.ORDER_CODE, RM_FUNCTION_NODE.FUNCNODE_AUTHORIZE_TYPE, RM_FUNCTION_NODE.DESCRIPTION, RM_FUNCTION_NODE.DATA_VALUE, RM_FUNCTION_NODE.PATTERN_VALUE, RM_FUNCTION_NODE.IS_LEAF, RM_FUNCTION_NODE.ICON, RM_FUNCTION_NODE.HELP_NAME, RM_FUNCTION_NODE.USABLE_STATUS, RM_FUNCTION_NODE.MODIFY_DATE, RM_FUNCTION_NODE.MODIFY_IP, RM_FUNCTION_NODE.MODIFY_USER_ID from RM_FUNCTION_NODE where RM_FUNCTION_NODE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_FUNCTION_NODE set NODE_TYPE=?, FUNCTION_PROPERTY=?, NAME=?, ENABLE_STATUS=?, TOTAL_CODE=?, ORDER_CODE=?, FUNCNODE_AUTHORIZE_TYPE=?, DESCRIPTION=?, DATA_VALUE=?, PATTERN_VALUE=?, IS_LEAF=?, ICON=?, HELP_NAME=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_FUNCTION_NODE.ID) from RM_FUNCTION_NODE";
    
    public final static String SQL_QUERY_ALL = "select RM_FUNCTION_NODE.ID, RM_FUNCTION_NODE.NODE_TYPE, RM_FUNCTION_NODE.FUNCTION_PROPERTY, RM_FUNCTION_NODE.NAME, RM_FUNCTION_NODE.ENABLE_STATUS, RM_FUNCTION_NODE.TOTAL_CODE, RM_FUNCTION_NODE.ORDER_CODE, RM_FUNCTION_NODE.FUNCNODE_AUTHORIZE_TYPE, RM_FUNCTION_NODE.DESCRIPTION, RM_FUNCTION_NODE.DATA_VALUE, RM_FUNCTION_NODE.PATTERN_VALUE, RM_FUNCTION_NODE.IS_LEAF, RM_FUNCTION_NODE.ICON, RM_FUNCTION_NODE.HELP_NAME from RM_FUNCTION_NODE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_FUNCTION_NODE.ID, RM_FUNCTION_NODE.NODE_TYPE, RM_FUNCTION_NODE.FUNCTION_PROPERTY, RM_FUNCTION_NODE.NAME, RM_FUNCTION_NODE.ENABLE_STATUS, RM_FUNCTION_NODE.TOTAL_CODE, RM_FUNCTION_NODE.ORDER_CODE, RM_FUNCTION_NODE.FUNCNODE_AUTHORIZE_TYPE, RM_FUNCTION_NODE.DESCRIPTION, RM_FUNCTION_NODE.DATA_VALUE, RM_FUNCTION_NODE.PATTERN_VALUE, RM_FUNCTION_NODE.IS_LEAF, RM_FUNCTION_NODE.ICON, RM_FUNCTION_NODE.HELP_NAME, RM_FUNCTION_NODE.USABLE_STATUS, RM_FUNCTION_NODE.MODIFY_DATE, RM_FUNCTION_NODE.MODIFY_IP, RM_FUNCTION_NODE.MODIFY_USER_ID from RM_FUNCTION_NODE";
    
    //表名
    public final static String TABLE_NAME = "RM_FUNCTION_NODE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "功能节点";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("node_type","类型");
		put("function_property","功能性质");
		put("name","名称");
		put("enable_status","启用/禁用状态");
		put("total_code","完全编码");
		put("order_code","排序编码");
		put("funcnode_authorize_type","权限类型");
		put("description","功能说明");
		put("data_value","URL值");
		put("pattern_value","正则匹配");
		put("is_leaf","是否末点");
		put("icon","图片");
		put("help_name","帮助文件名");
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