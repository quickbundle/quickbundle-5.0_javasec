//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartytyperelationrule/util/IRmPartyTypeRelationRuleConstants.java
//代码生成时,系统时间: 2010-11-29 10:08:24
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartytyperelationrule.util --> IRmPartyTypeRelationRuleConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-29 10:08:24 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartytyperelationrule.util;

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

public interface IRmPartyTypeRelationRuleConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmPartyTypeRelationRuleService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_PARTY_TYPE_RELATION_RULE ( ID, PARTY_VIEW_ID, PARENT_PARTY_TYPE_ID, CHILD_PARTY_TYPE_ID, RULE_DESC, IS_INSERT_CHILD_PARTY, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_PARTY_TYPE_RELATION_RULE where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_PARTY_TYPE_RELATION_RULE ";

    public final static String SQL_FIND_BY_ID = "select RM_PARTY_TYPE_RELATION_RULE.ID, RM_PARTY_TYPE_RELATION_RULE.PARTY_VIEW_ID, RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID) PARENT_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID) CHILD_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.RULE_DESC, RM_PARTY_TYPE_RELATION_RULE.IS_INSERT_CHILD_PARTY, RM_PARTY_TYPE_RELATION_RULE.USABLE_STATUS, RM_PARTY_TYPE_RELATION_RULE.MODIFY_DATE, RM_PARTY_TYPE_RELATION_RULE.MODIFY_IP, RM_PARTY_TYPE_RELATION_RULE.MODIFY_USER_ID from RM_PARTY_TYPE_RELATION_RULE where RM_PARTY_TYPE_RELATION_RULE.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_PARTY_TYPE_RELATION_RULE set PARTY_VIEW_ID=?, PARENT_PARTY_TYPE_ID=?, CHILD_PARTY_TYPE_ID=?, RULE_DESC=?, IS_INSERT_CHILD_PARTY=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_PARTY_TYPE_RELATION_RULE.ID) from RM_PARTY_TYPE_RELATION_RULE";
    
    public final static String SQL_QUERY_ALL = "select RM_PARTY_TYPE_RELATION_RULE.ID, RM_PARTY_TYPE_RELATION_RULE.PARTY_VIEW_ID, RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID) PARENT_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID) CHILD_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.RULE_DESC, RM_PARTY_TYPE_RELATION_RULE.IS_INSERT_CHILD_PARTY from RM_PARTY_TYPE_RELATION_RULE";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_PARTY_TYPE_RELATION_RULE.ID, RM_PARTY_TYPE_RELATION_RULE.PARTY_VIEW_ID, RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.PARENT_PARTY_TYPE_ID) PARENT_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID, (select NAME from RM_PARTY_TYPE where RM_PARTY_TYPE.ID=RM_PARTY_TYPE_RELATION_RULE.CHILD_PARTY_TYPE_ID) CHILD_PARTY_TYPE_ID_NAME, RM_PARTY_TYPE_RELATION_RULE.RULE_DESC, RM_PARTY_TYPE_RELATION_RULE.IS_INSERT_CHILD_PARTY, RM_PARTY_TYPE_RELATION_RULE.USABLE_STATUS, RM_PARTY_TYPE_RELATION_RULE.MODIFY_DATE, RM_PARTY_TYPE_RELATION_RULE.MODIFY_IP, RM_PARTY_TYPE_RELATION_RULE.MODIFY_USER_ID from RM_PARTY_TYPE_RELATION_RULE";
    
    //表名
    public final static String TABLE_NAME = "RM_PARTY_TYPE_RELATION_RULE";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "团体类型关系规则";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("party_view_id","团体视图ID");
		put("parent_party_type_id","父团体类型");
		put("child_party_type_id","子团体类型");
		put("rule_desc","规则描述");
		put("is_insert_child_party","可新增子团体");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"party_view_id", "party_view_id_name"};
}