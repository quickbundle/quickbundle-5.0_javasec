//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuser/util/IRmUserConstants.java
//代码生成时,系统时间: 2010-11-27 22:08:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuser.util --> IRmUserConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuser.util;

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

public interface IRmUserConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmUserService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_USER ( ID, NAME, LOCK_STATUS, LOGIN_ID, PASSWORD, AUTHEN_TYPE, ORGANIZATION_ID, EMPLOYEE_ID, EMAIL, ADMIN_TYPE, DESCRIPTION, AGENT_STATUS, LOGIN_STATUS, LAST_LOGIN_DATE, LAST_LOGIN_IP, LOGIN_SUM, LAST_CUSTOM_CSS, IS_AFFIX, FUNCTION_PERMISSION, DATA_PERMISSION, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, CUSTOM_XML, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_USER where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_USER ";

    public final static String SQL_FIND_BY_ID = "select RM_USER.ID, RM_USER.NAME, RM_USER.LOCK_STATUS, RM_USER.LOGIN_ID, RM_USER.PASSWORD, RM_USER.AUTHEN_TYPE, RM_USER.ORGANIZATION_ID, RM_USER.EMPLOYEE_ID, RM_USER.EMAIL, RM_USER.ADMIN_TYPE, RM_USER.DESCRIPTION, RM_USER.AGENT_STATUS, RM_USER.LOGIN_STATUS, RM_USER.LAST_LOGIN_DATE, RM_USER.LAST_LOGIN_IP, RM_USER.LOGIN_SUM, RM_USER.LAST_CUSTOM_CSS, RM_USER.IS_AFFIX, RM_USER.FUNCTION_PERMISSION, RM_USER.DATA_PERMISSION, RM_USER.CUSTOM1, RM_USER.CUSTOM2, RM_USER.CUSTOM3, RM_USER.CUSTOM4, RM_USER.CUSTOM5, RM_USER.CUSTOM_XML, RM_USER.USABLE_STATUS, RM_USER.MODIFY_DATE, RM_USER.MODIFY_IP, RM_USER.MODIFY_USER_ID from RM_USER where RM_USER.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_USER set NAME=?, LOCK_STATUS=?, LOGIN_ID=?, PASSWORD=?, AUTHEN_TYPE=?, ORGANIZATION_ID=?, EMPLOYEE_ID=?, EMAIL=?, ADMIN_TYPE=?, DESCRIPTION=?, AGENT_STATUS=?, LOGIN_STATUS=?, LAST_LOGIN_DATE=?, LAST_LOGIN_IP=?, LOGIN_SUM=?, LAST_CUSTOM_CSS=?, IS_AFFIX=?, FUNCTION_PERMISSION=?, DATA_PERMISSION=?, CUSTOM1=?, CUSTOM2=?, CUSTOM3=?, CUSTOM4=?, CUSTOM5=?, CUSTOM_XML=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_USER.ID) from RM_USER " +
    		"left join RM_PARTY_RELATION on RM_USER.ID=RM_PARTY_RELATION.CHILD_PARTY_ID";

    public final static String SQL_QUERY_ALL = "select RM_USER.ID, RM_USER.NAME, RM_USER.LOCK_STATUS, RM_USER.LOGIN_ID, RM_USER.AUTHEN_TYPE, RM_USER.ORGANIZATION_ID, RM_USER.EMPLOYEE_ID, RM_USER.EMAIL, RM_USER.ADMIN_TYPE, RM_USER.AGENT_STATUS, RM_USER.LOGIN_STATUS, RM_USER.LAST_LOGIN_DATE, RM_USER.LAST_LOGIN_IP, RM_USER.LOGIN_SUM, RM_USER.IS_AFFIX " +
    		", RM_PARTY_RELATION.PARENT_PARTY_ID, RM_PARTY_RELATION.PARENT_PARTY_NAME, RM_PARTY_RELATION.PARENT_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_CODE " +
    		"from RM_USER left join RM_PARTY_RELATION on RM_USER.ID=RM_PARTY_RELATION.CHILD_PARTY_ID";

    public final static String SQL_QUERY_ALL_EXPORT = "select RM_USER.ID, RM_USER.NAME, RM_USER.LOCK_STATUS, RM_USER.LOGIN_ID, RM_USER.PASSWORD, RM_USER.AUTHEN_TYPE, RM_USER.ORGANIZATION_ID, RM_USER.EMPLOYEE_ID, RM_USER.EMAIL, RM_USER.ADMIN_TYPE, RM_USER.DESCRIPTION, RM_USER.AGENT_STATUS, RM_USER.LOGIN_STATUS, RM_USER.LAST_LOGIN_DATE, RM_USER.LAST_LOGIN_IP, RM_USER.LOGIN_SUM, RM_USER.LAST_CUSTOM_CSS, RM_USER.IS_AFFIX, RM_USER.FUNCTION_PERMISSION, RM_USER.DATA_PERMISSION, RM_USER.CUSTOM1, RM_USER.CUSTOM2, RM_USER.CUSTOM3, RM_USER.CUSTOM4, RM_USER.CUSTOM5, RM_USER.CUSTOM_XML, RM_USER.USABLE_STATUS, RM_USER.MODIFY_DATE, RM_USER.MODIFY_IP, RM_USER.MODIFY_USER_ID from RM_USER";
	
	//表名
    public final static String TABLE_NAME = "RM_USER";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "用户";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("name","姓名");
		put("lock_status","激活/锁定状态");
		put("login_id","登录名");
		put("password","密码");
		put("authen_type","认证方式");
		put("organization_id","所属组织机构");
		put("employee_id","员工ID");
		put("email","电子邮箱");
		put("admin_type","用户权限类型");
		put("description","描述");
		put("agent_status","代理状态");
		put("login_status","登录状态");
		put("last_login_date","最后登录时间");
		put("last_login_ip","最后登录IP");
		put("login_sum","累计登录次数");
		put("last_custom_css","最后定制样式");
		put("is_affix","有无附件");
		put("function_permission","功能权限");
		put("data_permission","数据权限");
		put("custom1","自定义1");
		put("custom2","自定义2");
		put("custom3","自定义3");
		put("custom4","自定义4");
		put("custom5","自定义5");
		put("custom_xml","扩展XML");
		put("usable_status","数据可用状态");
		put("modify_date","修改日期");
		put("modify_ip","修改IP");
		put("modify_user_id","修改用户ID");
    }};
    
    //日志类型名称
    public final static String TABLE_LOG_TYPE_NAME = TABLE_NAME_CHINESE + "管理";
    
    //默认启用的查询条件
    public final static String DESC_USABLE_STATUS_EVALUATE_ENABLE = "RM_USER." + DESC_USABLE_STATUS + "='" + RM_YES + "'";
    public final static String DEFAULT_SQL_WHERE_USABLE = " where " + DESC_USABLE_STATUS_EVALUATE_ENABLE;
    
    public final static String DEFAULT_SQL_CONTACT_KEYWORD = " and "; // where 
    
    //默认的排序字段
    public final static String DEFAULT_ORDER_BY_CODE = ""; //ORDER_BY_SYMBOL + DESC_ORDER_CODE
    
    //子表查询条件，[0]作为默认条件查询字段
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"id" };
    
    public final static String ONLINE_USER_MODE = "ONLINE_USER_MODE";
}