//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmauthorizeresource/web/RmAuthorizeResourceConditionAction.java
//代码生成时,系统时间: 2010-11-27 22:08:42
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmauthorizeresource.web --> RmAuthorizeResourceConditionAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:42 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmauthorizeresource.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.dom4j.Document;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.orgauth.cache.RmAuthorizeCache;
import org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo;
import org.quickbundle.orgauth.rmauthorizeresource.service.IRmAuthorizeResourceService;
import org.quickbundle.orgauth.rmauthorizeresource.util.IRmAuthorizeResourceConstants;
import org.quickbundle.orgauth.rmauthorizeresource.util.RmAuthorizeResourceException;
import org.quickbundle.orgauth.rmauthorizeresource.vo.RmAuthorizeResourceVo;
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

public class RmAuthorizeResourceConditionAction extends RmAuthorizeResourceAction implements IRmAuthorizeResourceConstants {
    
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
        String condition = request.getParameter(DEFAULT_CONDITION_KEY_ARRAY[0]);
        if(condition == null) {
            throw new RmAuthorizeResourceException(MESSAGE_NO_CONDITION_KEY);
        }
        String queryCondition = " " + DEFAULT_CONDITION_KEY_ARRAY[0] + "='" + condition + "'";
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
        IRmAuthorizeResourceService service = getService();
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        //从RM_AUTHORIZE表分析是否需要left join目标表查询数据
        RmAuthorizeVo authorize = null;
        if(request.getParameter("bs_keyword") != null) {
        	authorize = RmAuthorizeCache.getAuthorizeByBs_keyword(request.getParameter("bs_keyword"));
        } else {
        	authorize = RmAuthorizeCache.getAuthorizeById(request.getParameter("authorize_id"));
        }
        Document authRule = authorize.getRuleCustomCode();
        if(authRule == null) {
        	RmPageVo pageVo = RmJspHelper.transctPageVo(request, getCount(queryCondition));
        	String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        	List<RmAuthorizeResourceVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        	RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        	request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        	request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        } else {
        	RmPageVo pageVo = RmJspHelper.transctPageVo(request, getService().getRecordCount(queryCondition, authorize));
        	String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
          	List<RmAuthorizeResourceVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize(), authorize);  //按条件查询全部,带排序
        	RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        	request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        	request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        }
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return mapping.findForward(FORWARD_LIST_PAGE);
    }
	
    /**
     * 参照信息查询，带简单查询，分页显示，支持表单回写
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryReference(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        simpleQuery(mapping, form, request, response);
        request.setAttribute(REQUEST_REFERENCE_INPUT_TYPE, request.getParameter(REQUEST_REFERENCE_INPUT_TYPE));  //传送输入方式,checkbox或radio
        return mapping.findForward(FORWARD_REFERENCE_PAGE);
    }
    
    /**
     * 初始化资源
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward initResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] old_resource_ids = RmJspHelper.getArrayFromRequest(request, "old_resource_ids");  //从request获取多条记录id
        int initCount = 0;  //定义成功删除的记录数
        if (old_resource_ids != null && old_resource_ids.length != 0) {
        	initCount = getService().executeInitResource(request.getParameter("bs_keyword"), old_resource_ids).size();  //初始化多条记录
        }
        request.setAttribute(EXECUTE_ROW_COUNT, initCount);  //sql影响的行数放入request属性
    	return RmActionHelper.rebuildForward(mapping.findForward(FORWARD_TO_QUERY_ALL), RmVoHelper.getMapFromRequest(request , DEFAULT_CONDITION_KEY_ARRAY));
    }
}