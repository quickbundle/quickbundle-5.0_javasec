//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmrole/util/IRmRoleConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:38
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmrole.util --> IRmRoleConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:38 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmrole.util;

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

public interface IRmRoleConstants extends IGlobalConstants {
    
    public final static String FORWARD_USER_QUERALL = "USER_QUERALL";

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmRoleService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_ROLE ( ID, ROLE_CODE, NAME, ENABLE_STATUS, IS_SYSTEM_LEVEL, OWNER_ORG_ID, IS_RECURSIVE, MATRIX_CODE, DESCRIPTION, FUNCTION_PERMISSION, DATA_PERMISSION, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_ROLE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_ROLE ";

    public final static String SQL_FIND_BY_ID = "select RM_ROLE.ID, RM_ROLE.ROLE_CODE, RM_ROLE.NAME, RM_ROLE.ENABLE_STATUS, RM_ROLE.IS_SYSTEM_LEVEL, RM_ROLE.OWNER_ORG_ID, (select NAME from RM_PARTY where RM_PARTY.ID=RM_ROLE.OWNER_ORG_ID) OWNER_ORG_ID_NAME, RM_ROLE.IS_RECURSIVE, RM_ROLE.MATRIX_CODE, RM_ROLE.DESCRIPTION, RM_ROLE.FUNCTION_PERMISSION, RM_ROLE.DATA_PERMISSION, RM_ROLE.USABLE_STATUS, RM_ROLE.MODIFY_DATE, RM_ROLE.MODIFY_IP, RM_ROLE.MODIFY_USER_ID from RM_ROLE where RM_ROLE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_ROLE set ROLE_CODE=?, NAME=?, ENABLE_STATUS=?, IS_SYSTEM_LEVEL=?, OWNER_ORG_ID=?, IS_RECURSIVE=?, MATRIX_CODE=?, DESCRIPTION=?, FUNCTION_PERMISSION=?, DATA_PERMISSION=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";

    public final static String SQL_COUNT = "select count(RM_ROLE.ID) from RM_ROLE";
    
    public final static String SQL_QUERY_ALL = "select RM_ROLE.ID, RM_ROLE.ROLE_CODE, RM_ROLE.NAME, RM_ROLE.ENABLE_STATUS, RM_ROLE.IS_SYSTEM_LEVEL, RM_ROLE.OWNER_ORG_ID,(select NAME from RM_PARTY where RM_PARTY.ID=RM_ROLE.OWNER_ORG_ID) OWNER_ORG_ID_NAME, RM_ROLE.IS_RECURSIVE, RM_ROLE.MATRIX_CODE, RM_ROLE.DESCRIPTION from RM_ROLE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_ROLE.ID, RM_ROLE.ROLE_CODE, RM_ROLE.NAME, RM_ROLE.ENABLE_STATUS, RM_ROLE.IS_SYSTEM_LEVEL, RM_ROLE.OWNER_ORG_ID, RM_ROLE.IS_RECURSIVE, RM_ROLE.MATRIX_CODE, RM_ROLE.DESCRIPTION, RM_ROLE.FUNCTION_PERMISSION, RM_ROLE.DATA_PERMISSION, RM_ROLE.USABLE_STATUS, RM_ROLE.MODIFY_DATE, RM_ROLE.MODIFY_IP, RM_ROLE.MODIFY_USER_ID from RM_ROLE";
    
    //表名
    public final static String TABLE_NAME = "RM_ROLE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "角色";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("role_code","角色编码");
		put("name","名称");
		put("enable_status","启用/禁用");
		put("is_system_level","是否全局角色");
		put("owner_org_id","所属组织");
		put("is_recursive","是否传播给下级");
		put("matrix_code","不相容矩阵编码");
		put("description","角色描述");
		put("function_permission","功能权限_简单");
		put("data_permission","数据权限_简单");
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