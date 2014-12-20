package org.quickbundle.third.struts.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.quickbundle.ICoreConstants;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.support.log.RmLogHelper;
import org.slf4j.Logger;

public class RmDispatchAction extends DispatchAction {
	static Logger log = RmLogHelper.getLogger("struts");
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(ICoreConstants.RM_ACTION_URI, request.getRequestURI());
        ActionForward af = super.execute(mapping, form, request, response);
        //跳转到目的页面
        if(af != null) {
        	log.debug(RmStringHelper.decodeUrl(af.getPath()));
        } else {
        	log.debug(RmStringHelper.decodeUrl(request.getRequestURL().toString()));
        }

//        //为workflow传递id
//        if(request.getParameter("rmFlowTargetUrl") != null && request.getParameter("rmFlowTargetUrl").trim().length() > 0) {
//        	Map<String, Object> mParameter = RmVoHelper.getMapFromRequest(request);
//        	mParameter.put(ICoreConstants.WF_FORM_ID, request.getAttribute(ICoreConstants.INSERT_FORM_ID));
//        	return RmJspHelper.rebuildForward(RmJspHelper.getForwardInstance(request.getParameter("rmFlowTargetUrl")), mParameter);
//        }
        
        return af;
        
    }
}
