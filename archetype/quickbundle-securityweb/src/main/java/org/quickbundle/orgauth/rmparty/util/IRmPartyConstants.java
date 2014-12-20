//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmparty/util/IRmPartyConstants.java
//代码生成时,系统时间: 2010-11-28 17:40:30
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmparty.util --> IRmPartyConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:30 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmparty.util;

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

public interface IRmPartyConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmPartyService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_PARTY ( ID, PARTY_TYPE_ID, OLD_PARTY_ID, NAME, IS_INHERIT, EMAIL, DESCRIPTION, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_PARTY where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_PARTY ";

    public final static String SQL_FIND_BY_ID = "select RM_PARTY.ID, RM_PARTY.PARTY_TYPE_ID, " +
    		"(select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY.PARTY_TYPE_ID) PARTY_TYPE_ID_NAME, " +
    		"RM_PARTY.OLD_PARTY_ID, RM_PARTY.NAME, RM_PARTY.IS_INHERIT, RM_PARTY.EMAIL, RM_PARTY.DESCRIPTION, RM_PARTY.CUSTOM1, RM_PARTY.CUSTOM2, RM_PARTY.CUSTOM3, RM_PARTY.CUSTOM4, RM_PARTY.CUSTOM5, RM_PARTY.USABLE_STATUS, RM_PARTY.MODIFY_DATE, RM_PARTY.MODIFY_IP, RM_PARTY.MODIFY_USER_ID from RM_PARTY where RM_PARTY.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_PARTY set PARTY_TYPE_ID=?, OLD_PARTY_ID=?, NAME=?, IS_INHERIT=?, EMAIL=?, DESCRIPTION=?, CUSTOM1=?, CUSTOM2=?, CUSTOM3=?, CUSTOM4=?, CUSTOM5=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_PARTY.ID) from RM_PARTY";
    
    public final static String SQL_QUERY_ALL = "select RM_PARTY.ID, RM_PARTY.PARTY_TYPE_ID, " +
    		"(select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY.PARTY_TYPE_ID) PARTY_TYPE_ID_NAME, " +
    		"RM_PARTY.OLD_PARTY_ID, RM_PARTY.NAME, RM_PARTY.IS_INHERIT, RM_PARTY.EMAIL, RM_PARTY.DESCRIPTION from RM_PARTY";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_PARTY.ID, RM_PARTY.PARTY_TYPE_ID, " +
			"(select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY.PARTY_TYPE_ID) PARTY_TYPE_ID_NAME, " +
			"RM_PARTY.OLD_PARTY_ID, RM_PARTY.NAME, RM_PARTY.IS_INHERIT, RM_PARTY.EMAIL, RM_PARTY.DESCRIPTION, RM_PARTY.CUSTOM1, RM_PARTY.CUSTOM2, RM_PARTY.CUSTOM3, RM_PARTY.CUSTOM4, RM_PARTY.CUSTOM5, RM_PARTY.USABLE_STATUS, RM_PARTY.MODIFY_DATE, RM_PARTY.MODIFY_IP, RM_PARTY.MODIFY_USER_ID from RM_PARTY";
    
    //表名
    public final static String TABLE_NAME = "RM_PARTY";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "团体";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("party_type_id","团体类型ID");
		put("old_party_id","团体原始ID");
		put("name","名称");
		put("is_inherit","是否继承权限");
		put("email","电子邮件");
		put("description","描述");
		put("custom1","自定义1");
		put("custom2","自定义2");
		put("custom3","自定义3");
		put("custom4","自定义4");
		put("custom5","自定义5");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"party_type_id" };
}