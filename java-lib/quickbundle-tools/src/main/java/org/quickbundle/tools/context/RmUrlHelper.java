package org.quickbundle.tools.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.quickbundle.tools.helper.RmVmHelper;
import org.quickbundle.tools.support.log.RmLogHelper;

public class RmUrlHelper {
    
    
    /**
     * 对url中的<%=...%>部分解析，如<%=org.quickbundle.project.RmProjectHelper.getRmLoginId(request)%>
     * @param str
     * @param request
     * @return
     */
    public static String replaceParameter(String str, HttpServletRequest request) {
    	return replaceParameter(str, request, null);
    }
    
    /**
     * 对url中的<%=...%>部分解析，如<%=org.quickbundle.project.RmProjectHelper.getRmLoginId(request)%>
     * @param str
     * @param request
     * @return
     */
    public static String replaceParameter(String str, HttpServletRequest request, Map<String, Object> mContext) {
        if(str.indexOf("<%=") > -1) {
            try {
                RmLogHelper.getLogger(RmUrlHelper.class).info("RmUrlHelper.replaceParameter(" + str + "): Thread.currentThread()=" + Thread.currentThread());
                HttpSession session = request.getSession(false);
                if(mContext == null) {
                	mContext = new HashMap<String, Object>();
                }
                mContext.put("request", request);
                mContext.put("session", session);
                while (str.indexOf("<%=") > -1) {
                    String tempStr = str.substring(str.indexOf("<%="), str.indexOf("%>") + "%>".length());
                    String tempKey = tempStr.substring("<%=".length(), tempStr.length() - "%>".length());
                    String tempValue = String.valueOf(RmVmHelper.runJavaCode(tempKey, mContext));
                    str = str.substring(0, str.indexOf("<%=")) + tempValue + str.substring(str.indexOf("%>") + "%>".length());
                }
            } catch (Exception e) {
            	RmLogHelper.getLogger(RmUrlHelper.class).error("RmUrlHelper.replaceParameter(" + str + "):" + e.toString());
            }
        }
       
        return str;
    }
}
