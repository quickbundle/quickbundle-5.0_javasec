//代码生成时,文件路径: E:/quickbundle-securityweb/src/main/java/orgauth/rmpartyview/util/IRmPartyViewConstants.java
//代码生成时,系统时间: 2010-11-28 17:40:27
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmpartyview.util --> IRmPartyViewConstants.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-28 17:40:27 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmpartyview.util;

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

public interface IRmPartyViewConstants extends IGlobalConstants {

    //Service的规范化名称
    public final static String SERVICE_KEY = "IRmPartyViewService";

    //Sql语句
    public final static String SQL_INSERT = "insert into RM_PARTY_VIEW ( ID, BS_KEYWORD, NAME, VIEW_DESC, USABLE_STATUS, MODIFY_DATE, MODIFY_IP, MODIFY_USER_ID) values ( ?, ?, ?, ?, ?, ?, ?, ? )";
    
    public final static String SQL_DELETE_BY_ID = "delete from RM_PARTY_VIEW where ID=?";
    
    public final static String SQL_DELETE_MULTI_BY_IDS = "delete from RM_PARTY_VIEW ";

    public final static String SQL_FIND_BY_ID = "select RM_PARTY_VIEW.ID, RM_PARTY_VIEW.BS_KEYWORD, RM_PARTY_VIEW.NAME, RM_PARTY_VIEW.VIEW_DESC, RM_PARTY_VIEW.USABLE_STATUS, RM_PARTY_VIEW.MODIFY_DATE, RM_PARTY_VIEW.MODIFY_IP, RM_PARTY_VIEW.MODIFY_USER_ID from RM_PARTY_VIEW where RM_PARTY_VIEW.ID=?";

    public final static String SQL_UPDATE_BY_ID = "update RM_PARTY_VIEW set BS_KEYWORD=?, NAME=?, VIEW_DESC=?, USABLE_STATUS=?, MODIFY_DATE=?, MODIFY_IP=?, MODIFY_USER_ID=?  where ID=?";
    
    public final static String SQL_COUNT = "select count(RM_PARTY_VIEW.ID) from RM_PARTY_VIEW";
    
    public final static String SQL_QUERY_ALL = "select RM_PARTY_VIEW.ID, RM_PARTY_VIEW.BS_KEYWORD, RM_PARTY_VIEW.NAME, RM_PARTY_VIEW.VIEW_DESC from RM_PARTY_VIEW";

	public final static String SQL_QUERY_ALL_EXPORT = "select RM_PARTY_VIEW.ID, RM_PARTY_VIEW.BS_KEYWORD, RM_PARTY_VIEW.NAME, RM_PARTY_VIEW.VIEW_DESC, RM_PARTY_VIEW.USABLE_STATUS, RM_PARTY_VIEW.MODIFY_DATE, RM_PARTY_VIEW.MODIFY_IP, RM_PARTY_VIEW.MODIFY_USER_ID from RM_PARTY_VIEW";
    
    //表名
    public final static String TABLE_NAME = "RM_PARTY_VIEW";
    
    //表名汉化
    public final static String TABLE_NAME_CHINESE = "团体视图";
    
    //列名汉化
    public final static Map<String, String> TABLE_COLUMN_CHINESE = new CaseInsensitiveMap(){{
		
		put("id","主键");
		put("bs_keyword","业务关键字");
		put("name","名称");
		put("view_desc","规则描述");
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