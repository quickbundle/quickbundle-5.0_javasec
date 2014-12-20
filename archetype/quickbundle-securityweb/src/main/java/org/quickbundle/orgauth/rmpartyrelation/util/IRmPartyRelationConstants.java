//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartyrelation/util/IRmPartyRelationConstants.java
//代码生成时,系统时间: 2010-11-28 17:40:29
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyrelation.util --> IRmPartyRelationConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:29 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyrelation.util;

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

public interface IRmPartyRelationConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmPartyRelationService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_PARTY_RELATION ( ID, PARTY_VIEW_ID, PARENT_PARTY_ID, CHILD_PARTY_ID, PARENT_PARTY_CODE, CHILD_PARTY_CODE, CHILD_PARTY_LEVEL, CHILD_IS_MAIN_RELATION, ORDER_CODE, PARENT_PARTY_NAME, CHILD_PARTY_NAME, PARENT_PARTY_TYPE_ID, CHILD_PARTY_TYPE_ID, CHILD_IS_LEAF, CUSTOM1, CUSTOM2, CUSTOM3, CUSTOM4, CUSTOM5, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_PARTY_RELATION where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_PARTY_RELATION ";

    public final static String SQL_FIND_BY_ID = "select RM_PARTY_RELATION.ID, RM_PARTY_RELATION.PARTY_VIEW_ID, RM_PARTY_RELATION.PARENT_PARTY_ID, RM_PARTY_RELATION.CHILD_PARTY_ID, RM_PARTY_RELATION.PARENT_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_LEVEL, RM_PARTY_RELATION.CHILD_IS_MAIN_RELATION, RM_PARTY_RELATION.ORDER_CODE, RM_PARTY_RELATION.PARENT_PARTY_NAME, RM_PARTY_RELATION.CHILD_PARTY_NAME, RM_PARTY_RELATION.PARENT_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_IS_LEAF, RM_PARTY_RELATION.CUSTOM1, RM_PARTY_RELATION.CUSTOM2, RM_PARTY_RELATION.CUSTOM3, RM_PARTY_RELATION.CUSTOM4, RM_PARTY_RELATION.CUSTOM5, RM_PARTY_RELATION.USABLE_STATUS, RM_PARTY_RELATION.MODIFY_DATE, RM_PARTY_RELATION.MODIFY_IP, RM_PARTY_RELATION.MODIFY_USER_ID from RM_PARTY_RELATION where RM_PARTY_RELATION.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_PARTY_RELATION set PARTY_VIEW_ID=?, PARENT_PARTY_ID=?, CHILD_PARTY_ID=?, PARENT_PARTY_CODE=?, CHILD_PARTY_CODE=?, CHILD_PARTY_LEVEL=?, CHILD_IS_MAIN_RELATION=?, ORDER_CODE=?, PARENT_PARTY_NAME=?, CHILD_PARTY_NAME=?, PARENT_PARTY_TYPE_ID=?, CHILD_PARTY_TYPE_ID=?, CHILD_IS_LEAF=?, CUSTOM1=?, CUSTOM2=?, CUSTOM3=?, CUSTOM4=?, CUSTOM5=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_PARTY_RELATION.ID) from RM_PARTY_RELATION";
    
    public final static String SQL_QUERY_ALL = "select RM_PARTY_RELATION.ID, RM_PARTY_RELATION.PARTY_VIEW_ID, RM_PARTY_RELATION.PARENT_PARTY_ID, RM_PARTY_RELATION.CHILD_PARTY_ID, RM_PARTY_RELATION.PARENT_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_LEVEL, RM_PARTY_RELATION.CHILD_IS_MAIN_RELATION, RM_PARTY_RELATION.ORDER_CODE, RM_PARTY_RELATION.PARENT_PARTY_NAME, RM_PARTY_RELATION.CHILD_PARTY_NAME, RM_PARTY_RELATION.PARENT_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_IS_LEAF from RM_PARTY_RELATION";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_PARTY_RELATION.ID, RM_PARTY_RELATION.PARTY_VIEW_ID, RM_PARTY_RELATION.PARENT_PARTY_ID, RM_PARTY_RELATION.CHILD_PARTY_ID, RM_PARTY_RELATION.PARENT_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_CODE, RM_PARTY_RELATION.CHILD_PARTY_LEVEL, RM_PARTY_RELATION.CHILD_IS_MAIN_RELATION, RM_PARTY_RELATION.ORDER_CODE, RM_PARTY_RELATION.PARENT_PARTY_NAME, RM_PARTY_RELATION.CHILD_PARTY_NAME, RM_PARTY_RELATION.PARENT_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_PARTY_TYPE_ID, RM_PARTY_RELATION.CHILD_IS_LEAF, RM_PARTY_RELATION.CUSTOM1, RM_PARTY_RELATION.CUSTOM2, RM_PARTY_RELATION.CUSTOM3, RM_PARTY_RELATION.CUSTOM4, RM_PARTY_RELATION.CUSTOM5, RM_PARTY_RELATION.USABLE_STATUS, RM_PARTY_RELATION.MODIFY_DATE, RM_PARTY_RELATION.MODIFY_IP, RM_PARTY_RELATION.MODIFY_USER_ID from RM_PARTY_RELATION";
    
    //表名
    public final static String TABLE_NAME = "RM_PARTY_RELATION";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "团体关系";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("party_view_id","团体视图ID");
		put("parent_party_id","父团体ID");
		put("child_party_id","子团体ID");
		put("parent_party_code","父团体全编码");
		put("child_party_code","子团体全编码");
		put("child_party_level","子团体级别");
		put("child_is_main_relation","子团体是否主关系");
		put("order_code","排序编码");
		put("parent_party_name","父团体别名");
		put("child_party_name","子团体别名");
		put("parent_party_type_id","父团体类型ID");
		put("child_party_type_id","子团体类型ID");
		put("child_is_leaf","子团体是否末节点");
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
    public final static String[] DEFAULT_CONDITION_KEY_ARRAY = new String[]{"party_view_id" };
}