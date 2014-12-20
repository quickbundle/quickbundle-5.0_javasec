package org.quickbundle.third.struts.action;


import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;
import org.apache.struts.util.ModuleException;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.quickbundle.base.exception.RmExceptionVo;
import org.quickbundle.tools.helper.xml.RmXmlHelper;
import org.quickbundle.tools.support.path.RmPathHelper;

public class RmExceptionHandler extends ExceptionHandler {
    
    private static volatile Document exceptionDoc = null;
    
    private static Document getExcpetionDoc() throws Exception {
        String xmlPath = RmPathHelper.getWarDir() + "/WEB-INF/config/rm/exception.xml";
        xmlPath = RmXmlHelper.formatToUrl(xmlPath);
        if(exceptionDoc == null) {
            synchronized (RmExceptionHandler.class) {
                if(exceptionDoc == null) {
                    exceptionDoc = RmXmlHelper.parse(xmlPath);
                }
            }
        }
        return exceptionDoc;
    }
    
    public static RmExceptionVo getException(Throwable e) {
        RmExceptionVo exceptionVo = new RmExceptionVo();
        try {
            Node nodeException = getExcpetionDoc().selectSingleNode("/java.lang.Exception/exception[@name='" + e.getClass().getName() + "']");
            if(nodeException != null) {
                Element eleException = (Element)nodeException;
                for (Object eleMessageO : eleException.selectNodes("message")) {
                	Element eleMessage = (Element)eleMessageO;
                    if(matchesFullCause(e, eleMessage.valueOf("@matches"))) {
                        exceptionVo.setTitle(eleMessage.valueOf("@title"));
                        exceptionVo.setDescription(eleMessage.valueOf("@description"));
                        return exceptionVo;
                    }
                }
                exceptionVo.setTitle(eleException.valueOf("@title"));
                exceptionVo.setDescription(eleException.valueOf("@description"));
                return exceptionVo;
            }
            Throwable rootCause = findRootCause(e);
            exceptionVo.setTitle(rootCause.getClass().getName());
            exceptionVo.setDescription(rootCause.getLocalizedMessage());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return exceptionVo;
    }
    
    public static Throwable findRootCause(Throwable e) {
    	Throwable result = e;
    	while (result.getCause() != null) {
    		result = result.getCause();
		}
    	return result;
    }
    
    private static boolean matchesFullCause(Throwable e, String p) {
    	if(Pattern.compile(p, Pattern.DOTALL).matcher(e.getMessage()).find()) {
    		return true;
    	} else if(e.getCause() != null) {
    		return matchesFullCause(e.getCause(), p);
    	} else {
    		return false;
    	}
    }
    
    public ActionForward execute(Exception ex, ExceptionConfig ae, ActionMapping mapping, ActionForm formInstance, HttpServletRequest request, HttpServletResponse response) throws ServletException {

        ActionForward forward = null;
        ActionMessage error = null;
        String property = null;

        if (ae.getPath() != null) {
            forward = new ActionForward(ae.getPath());
        } else {
            forward = mapping.getInputForward();
        }
        if (ex instanceof ModuleException) {
            error = ((ModuleException) ex).getActionMessage();
            property = ((ModuleException) ex).getProperty();
        } else {
            error = new ActionMessage(ae.getKey(), ex.getMessage());
            property = error.getKey();
        }

        logException(ex);

        request.setAttribute("org.apache.struts.action.EXCEPTION", ex);
        storeException(request, property, error, forward, ae.getScope());
        if(mapping == null) {
        	return forward;
        } 
        if(request.getRequestURI().matches("^.*/\\w+AjaxAction\\.do$")) {
        	return mapping.findForward("MessageAgentErrorAjax");
        }
        return mapping.findForward("MessageAgentError");
    }
}