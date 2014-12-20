//代码生成时,文件路径: E:/platform/myProject/qbrm/code/quickbundle-securityweb/src.open/org/quickbundle/modules/code/rmcodetype/web/RmCodeTypeConditionAction.java
//代码生成时,系统时间: 2010-04-08 21:15:45.656
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.modules.code.rmcodetype.web --> RmCodeTypeAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-04-08 21:15:45.656 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.modules.code.rmcodetype.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.modules.code.rmcodetype.service.IRmCodeTypeService;
import org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants;
import org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.cache.RmAuthorizeCache;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.third.struts.RmActionHelper;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmVoHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmCodeTypeConditionAction extends RmCodeTypeAction implements IRmCodeTypeConstants {
    
    /**
     * 从页面表单获取信息注入vo，并插入单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return RmActionHelper.rebuildForward(super.insert(mapping, form, request, response), RmVoHelper.getMapFromRequest(request , DEFAULT_CONDITION_KEY_ARRAY));
    }

    /**
     * 从页面的表单获取单条记录id，并删除单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return RmActionHelper.rebuildForward(super.delete(mapping, form, request, response), RmVoHelper.getMapFromRequest(request, DEFAULT_CONDITION_KEY_ARRAY));
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
        return RmActionHelper.rebuildForward(super.deleteMulti(mapping, form, request, response), RmVoHelper.getMapFromRequest(request, DEFAULT_CONDITION_KEY_ARRAY));
    }

    /**
     * 从页面的表单获取单条记录id，查出这条记录的值，并跳转到修改页面
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward find(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return super.find(mapping, form, request, response);
    }

    /**
     * 从页面表单获取信息注入vo，并修改单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward update(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return RmActionHelper.rebuildForward(super.update(mapping, form, request, response), RmVoHelper.getMapFromRequest(request, DEFAULT_CONDITION_KEY_ARRAY));
    }

    /**
     * 查询全部记录，分页显示，支持页面上触发的后台排序
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        String condition = request.getParameter(DEFAULT_CONDITION_KEY_ARRAY[0]);
//        if(condition == null) {
//            throw new RmCodeTypeException(MESSAGE_NO_CONDITION_KEY);
//        }
//        String queryCondition = " " + DEFAULT_CONDITION_KEY_ARRAY[0] + "='" + condition + "'";
    	String queryCondition = "";
        request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        return RmActionHelper.rebuildForward(simpleQuery(mapping, form, request, response), new String[]{REQUEST_QUERY_CONDITION, queryCondition});
    }

    /**
     * 从页面的表单获取单条记录id，并察看这条记录的详细信息
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward detail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest(request , DEFAULT_CONDITION_KEY_ARRAY));  //回写表单
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            request.setAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        return super.detail(mapping, form, request, response);
    }

    /**
     * 简单查询，分页显示，支持表单回写
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward simpleQuery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            request.setAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        IRmCodeTypeService service = getService();
        String queryCondition = super.getQueryCondition(request);  //从request中获得查询条件
        String bs_keyword = IOrgauthConstants.Authorize.RM_CODE_TYPE.bsKeyword();
        RmAuthorizeVo authorize = RmAuthorizeCache.getAuthorizeByBs_keyword(bs_keyword);
    
    	RmPageVo pageVo = RmJspHelper.transctPageVo(request, getService().getRecordCount(queryCondition, authorize, RmProjectHelper.getRmUserVo(request).getParty_id_all()));
    	String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
      	List<RmCodeTypeVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize(), authorize, RmProjectHelper.getRmUserVo(request).getParty_id_all());  //按条件查询全部,带排序
    	RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
    	request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
    	request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return mapping.findForward(FORWARD_LIST_PAGE);
    }

}
