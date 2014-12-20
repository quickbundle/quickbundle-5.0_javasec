package org.quickbundle.modules.message;

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

public interface IRmMessageConstants extends IGlobalConstants {

    //表名、显示名
    public final static String TABLE_NAME = "RM_MESSAGE";
    public final static String TABLE_NAME_DISPLAY = "消息";
    public final static String TABLE_PK = "id";
    //列名汉化
    @SuppressWarnings({ "unchecked", "serial" })
    public final static Map<String, String> TABLE_COLUMN_DISPLAY = new CaseInsensitiveMap(){{
		put("id","主键");
		put("biz_keyword","业务关键字");
		put("sender_id","发送人ID");
		put("parent_message_id","父消息ID");
		put("owner_org_id","所属组织ID");
		put("template_id","模板ID");
		put("is_affix","有无附件");
		put("record_id","主记录ID");
		put("message_xml_context","消息XML内容");
		put("usable_status","数据可用状态");
		put("modify_date","修改日期");
		put("modify_ip","修改IP");
		put("modify_user_id","修改用户ID");
    }};


    //子表1-消息收件人-表名、显示名
    public final static String TABLE_NAME_RM_MESSAGE_RECEIVER = "RM_MESSAGE_RECEIVER";
    public final static String TABLE_NAME_DISPLAY_RM_MESSAGE_RECEIVER = "消息收件人";
    public final static String TABLE_PK_RM_MESSAGE_RECEIVER = "id";
    //子表1-列名汉化
    @SuppressWarnings({"unchecked", "serial" })
    public final static Map<String, String> TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER = new CaseInsensitiveMap(){{
		put("id","主键");
		put("message_id","消息ID");
		put("receiver_id","接收人ID");
		put("is_handle","是否办理");
		put("handle_date","办理时间");
		put("handle_result","办理结果");
		put("usable_status","数据可用状态");
		put("modify_date","修改日期");
		put("modify_ip","修改IP");
		put("modify_user_id","修改用户ID");
    }};
    
    //日志类型名称
    public final static String LOG_TYPE_NAME = TABLE_NAME_DISPLAY + "管理";
}