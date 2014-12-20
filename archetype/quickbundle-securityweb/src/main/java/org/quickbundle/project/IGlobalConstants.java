package org.quickbundle.project;

import org.quickbundle.ICoreConstants;
import org.quickbundle.modules.code.IRmCodeConstants;

/**
 * 保存全局的一些常量
 * 
 * @author  
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */
public interface IGlobalConstants extends ICoreConstants, IRmCodeConstants {

    //项目组个性化的全局配置

	
    //框架内置全局性的配置，用于struts页面跳转
    public final static String FORWARD_TO_QUERY_ALL = "toQueryAll";
    public final static String FORWARD_LIST_PAGE = "listPage";
    public final static String FORWARD_UPDATE_PAGE = "updatePage";
    public final static String FORWARD_DETAIL_PAGE = "detailPage";
    public final static String FORWARD_REFERENCE_PAGE = "referencePage";
    public final static String FORWARD_STATISTIC_PAGE = "statisticPage";
    
    //页面中的列表控件宽度定义
    public final static String JSP_LAYOUT_WIDTH = "99%";
    
    //request处理中的key值
    public final static String REQUEST_ID = "id";
    public final static String REQUEST_IDS = "ids";    
    public final static String REQUEST_BEAN = "bean";
    public final static String REQUEST_BEANS = "beans";
    public final static String REQUEST_QUERY_CONDITION = "queryCondition";
    public final static String REQUEST_WRITE_BACK_FORM_VALUES = "writeBackFormValues";
    public final static String REQUEST_REFERENCE_INPUT_TYPE = "referenceInputType";
    
    //系统级定义    
    public final static String[] DESC_CREATE_DATE = new String[]{"modify_date", "ts"};  //描述创建时间
    public final static String DESC_CREATE_IP = "modify_ip";  //描述创建IP
    public final static String DESC_CREATE_USER_ID = "modify_user_id";  //描述创建用户ID
    public final static String[] DESC_MODIFY_DATE = new String[]{"modify_date", "ts"};  //描述修改时间
    public final static String DESC_MODIFY_IP = "modify_ip";  //描述修改IP
    public final static String DESC_MODIFY_USER_ID = "modify_user_id";  //描述修改用户ID
	
	//不存在的ID
	public final static String NOT_EXIST_ID = "NOT_EXIST_ID";
	public final static String UN_KNOWN = "UN_KNOWN";
	
    //消息定义
    public final static String MESSAGE_NO_CONDITION_KEY = "没有得到查询条件关键字！";
    
    public final static String DESC_USABLE_STATUS = "usable_status";
    public final static String RM_YES = "1";  //是的定义
    public final static String RM_NO = "0";  //否的定义
    public final static String DESC_USABLE_STATUS_EVALUATE_ENABLE = DESC_USABLE_STATUS + "='" + RM_YES + "'";
    public final static String DESC_USABLE_STATUS_EVALUATE_DISABLE = DESC_USABLE_STATUS + "='" + RM_NO + "'";
    
    public final static String REQUEST_IS_READ_ONLY = "REQUEST_IS_READ_ONLY";
    public final static String REQUEST_STATISTIC_HANDLER = "statisticHandler";
    
    public final static String FORWARD_OUTPUT_AJAX_PAGE = "OUTPUT_AJAX_PAGE";
    public final static String REQUEST_OUTPUT_OBJECT = "REQUEST_OUTPUT_OBJECT";
    
    public final static String FORWARD_DOWNLOAD_STATISTIC_FILE_PAGE = "downloadStatisticFilePage";
    
    public final static String RM_PAGE_SIZE = "RM_PAGE_SIZE";
    public final static String RM_CURRENT_PAGE = "RM_CURRENT_PAGE";
    public final static String RM_PAGE_VO = "RM_PAGE_VO";
    public final static String RM_ORDER_STR = "RM_ORDER_STR";
    
    public final static String DEFAULT_DATA_SOURCE = "dataSource";
    
    public static final String SORT_SYMBOL_ASC = "ASC";
    public static final String SORT_SYMBOL_DESC = "DESC";
    public static final String ORDER_BY_SYMBOL = " ORDER BY ";
    
    public static final String RM_ACTION_URI = "RM_ACTION_URI";
    
    //login session
    public final static String RM_USER_VO = "RM_USER_VO";
    //临时单点登录
    public final static String RM_SSO_TEMP = "RM_SSO_TEMP";

	/**
	 * request的参数
	 */
	public enum SystemPara {
		//通知用户的系统消息
		system_message;
	}

    //Action返回值的key
    public final static String EXECUTE_ROW_COUNT = "EXECUTE_ROW_COUNT";
    
    public final static String INSERT_FORM_ID = "INSERT_FORM_ID";
    
    public final static String RM_AJAX_JSON = "RM_AJAX_JSON";
    public final static String RM_JSON_TOTAL_COUNT = "totalCount";
    public final static String RM_AJAX_RECORD_SIZE = "RM_AJAX_RECORD_SIZE";
    public final static String RM_AJAX_SPLIT_KEY = "$";
    
    public final static String RM_NAMESPACE_SPLIT_KEY = "^";
    
    //工作流参数
    public final static String WF_FORM_ID = "WF_FORM_ID";
    public final static String REQUEST_WF_PROCESSID ="REQUEST_WF_PROCESSID";//工作流实例Id参数
    public final static String WF_LAST_PERSON ="WF_LAST_PERSON";
    public final static String WF_OWNER="WF_OWNER";
    public final static String WF_LAST_DATE ="WF_LAST_DATE"; 
    public final static String WF_PROCESS_TYPE ="WF_PROCESS_TYPE";
    public final static String WF_DESC_ORDER_CODE = "WF_DESC_ORDER_CODE";
    public final static String WF_FORM_ID_P = "WF_FORM_ID_P";
    
}