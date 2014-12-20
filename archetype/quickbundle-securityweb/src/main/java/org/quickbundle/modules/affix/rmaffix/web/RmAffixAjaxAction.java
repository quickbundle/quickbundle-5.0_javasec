//代码生成时,文件路径: E:/platform/myProject/navinfo/code/nifl/src/main/java/org/quickbundle/modules/affix/rmaffix/web/RmAffixReadOnlyAction.java
//代码生成时,系统时间: 2010-07-26 01:03:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> nifl
 * 
 * 文件名称: org.quickbundle.modules.affix.rmaffix.web --> RmAffixConditionAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-07-26 01:03:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.affix.rmaffix.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmAffixAjaxAction extends RmAffixAction implements IRmAffixConstants {
    /**
     * 批量保存，没有主键的insert，有主键的update
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward insertUpdateBatch(ActionMapping mapping, ActionForm form, final HttpServletRequest request, HttpServletResponse response) throws Exception {
    	super.insertUpdateBatch(mapping, form, request, response);
        return mapping.findForward(FORWARD_OUTPUT_AJAX_PAGE);
    }

    /**
     * 从页面的表单获取多条记录id，并删除多条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward deleteMulti(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	super.deleteMulti(mapping, form, request, response);
    	return mapping.findForward(FORWARD_OUTPUT_AJAX_PAGE);
    }
    
    /**
     * 简单查询，分页显示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward simpleQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.simpleQuery(mapping, form, request, response);
        return mapping.findForward(FORWARD_OUTPUT_AJAX_PAGE);
    }
    
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.detail(mapping, form, request, response);
        //显示的把vo放入request
        request.setAttribute(REQUEST_OUTPUT_OBJECT, request.getAttribute(REQUEST_BEAN));
        return mapping.findForward(FORWARD_OUTPUT_AJAX_PAGE);
    }
}