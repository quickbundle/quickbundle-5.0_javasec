package org.quickbundle.third.struts;

import java.util.Map;

import org.apache.struts.action.ActionForward;
import org.quickbundle.config.RmBaseConfig;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.util.RmSequenceMap;


public class RmActionHelper {
	/**
	 * 功能: 得到1个ActionForward实例，默认重定向
	 *
	 * @param forwardPath 想要跳往的页面url，如/jsp/org.quickbundle.test.htm
	 * @return
	 */
	public static ActionForward getForwardInstance(String forwardPath) {
	      return getForwardInstance(null, forwardPath, true);
	}

	
	/**
	 * 功能: 得到1个ActionForward实例
	 *
	 * @param name 名称关键词
	 * @param forwardPath 想要跳往的页面url，如/jsp/test.htm
	 * @param isRedirect 是否重定向
	 * @return
	 */
	public static ActionForward getForwardInstance(String name, String forwardPath, boolean isRedirect) {
	      return new ActionForward(name, forwardPath, isRedirect);
	}
	
	/**
	 * 功能: 得到1个ActionForward实例，在跳往指定页面前alert一个信息
	 *
	 * @param forwardPath 想要跳往的页面url，如/jsp/org.quickbundle.test.htm
	 * @param alertStr alert的信息
	 * @return
	 */
	public static ActionForward getForwardInstanceWithAlert(String forwardPath, String alertStr) {
	    String tempAlertUrl = "/jsp/util/rmAlertForward.jsp?rm_alertStr=" + RmStringHelper.encodeUrl(alertStr) + "&rm_targetForwardPath=" + RmStringHelper.encodeUrl(forwardPath);
	    return getForwardInstance(tempAlertUrl);
	}
	
	/**
	 * 功能: 重构Forward
	 *
	 * @param forward
	 * @param key_value 必须是长度为2的数组，key_value[0]是key值，key_value[1]是value值
	 * @return
	 */
	public static ActionForward rebuildForward(ActionForward forward, String[] key_value) {
	    Map<String, Object> mValue = new RmSequenceMap<String, Object>();
	    mValue.put(key_value[0], key_value[1]);
	    return rebuildForward(forward, mValue);
	}
	
	/**
	 * 功能: 重构Forward
	 *
	 * @param forward
	 * @param mValue
	 * @param aNeedName 关注的字段
	 * @return
	 */
	public static ActionForward rebuildForward(ActionForward forward, Map<String, String> mValue, String[] aNeedName) {
	    Map<String, Object> mNeedValue = new RmSequenceMap<String, Object>();
	    for(int i=0; i<aNeedName.length; i++) {
	        mNeedValue.put(aNeedName[i], mValue.get(aNeedName[i]));
	    }
	    return rebuildForward(forward, mNeedValue);
	}
	
	/**
	 * 功能: 重构Forward
	 *
	 * @param forward
	 * @param mValue
	 * @return
	 */
	public static ActionForward rebuildForward(ActionForward forward, Map<String, Object> mValue) {
        ActionForward myForward = new ActionForward();
	    myForward.setName(forward.getName());
	    myForward.setPath(forward.getPath());
	    myForward.setRedirect(forward.getRedirect());
	    if(myForward.getPath() != null && myForward.getPath().indexOf("?") > 0) {
	        myForward.setPath(myForward.getPath() + "&" + encodeUrlParameter(mValue));
	    } else {
	        myForward.setPath(myForward.getPath() + "?" + encodeUrlParameter(mValue));
	    }
	    return myForward;
	}


	/**
	 * 功能: 把Map中的值依次取出来，以URL传值的方式拼成字符串
	 * 
	 * @param mValue
	 * @return
	 */
	public static String encodeUrlParameter(Map<String, Object> mValue) {
		return encodeUrlParameter(mValue, new String[0]);
	}

	/**
	 * 功能: 把Map中的值依次取出来，以URL传值的方式拼成字符串
	 * 
	 * @param mValue
     * @param ignoreName 忽略的field
	 * @return
	 */
    public static String encodeUrlParameter(Map<String, Object> mValue, String[] ignoreName) {
		StringBuilder str = new StringBuilder();
        for(Map.Entry<String, Object> en : mValue.entrySet()) {
			String tempKey = en.getKey();
            String tempValue = en.getValue() == null ? "" : en.getValue().toString();
			if (tempKey.startsWith("RM") || tempKey.startsWith("RANMIN")) {
				// TODO
                if(!tempKey.equals(RmBaseConfig.PageKey.RM_PAGE_SIZE.key())&&!tempKey.equals(RmBaseConfig.PageKey.RM_CURRENT_PAGE.key())&& !tempKey.equals(RmBaseConfig.PageKey.RM_ORDER_STR.key())){
					continue;
				}
			}
            if (RmStringHelper.arrayContainString(ignoreName, tempKey)) {
				continue;
			}
			if (str.length() > 0) {
				str.append("&");
			}
			str.append(tempKey);
			str.append("=");
			str.append(RmStringHelper.encodeUrl(tempValue));
		}
		return str.toString();
	}
}
