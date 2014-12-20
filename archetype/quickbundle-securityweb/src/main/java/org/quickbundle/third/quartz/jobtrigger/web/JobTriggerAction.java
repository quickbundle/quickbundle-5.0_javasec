//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobtrigger/web/JobTriggerAction.java
//代码生成时,系统时间: 2012-04-02 22:28:47
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobtrigger.web --> JobTriggerAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:47 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobtrigger.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.third.struts.actions.RmDispatchAction;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.third.quartz.jobtrigger.service.IJobTriggerService;
import org.quickbundle.third.quartz.jobtrigger.util.IJobTriggerConstants;
import org.quickbundle.third.quartz.jobtrigger.vo.JobTriggerVo;
import org.quickbundle.third.quartz.util.SchedulerHelper;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmVoHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobTriggerAction extends RmDispatchAction implements IJobTriggerConstants {

    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IJobTriggerService getService() {
        return (IJobTriggerService) RmBeanFactory.getBean(SERVICE_KEY);  //得到Service对象,受事务控制
    }

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
        JobTriggerVo vo = new JobTriggerVo();
        vo.setDataMap(SchedulerHelper.parseDataMap(request));//注入json格式的params
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        String id = getService().insert(vo);  //插入单条记录
        request.setAttribute(IGlobalConstants.INSERT_FORM_ID, id);  //新增记录的id放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
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
    	String[] name_group = request.getParameter(REQUEST_ID).split(",");
    	int deleteCount = getService().delete(name_group[0], name_group[1]);  //删除单条记录
        request.setAttribute(EXECUTE_ROW_COUNT, deleteCount);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
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
        String[][] name_groups = getArrayFromRequest(request.getParameter(REQUEST_IDS));
        int deleteCount = 0;  //定义成功删除的记录数
        if (name_groups != null && name_groups.length != 0) {
            deleteCount = getService().delete(name_groups);  //删除多条记录
        }
        request.setAttribute(EXECUTE_ROW_COUNT, deleteCount);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    public ActionForward pause(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String[][] name_groups = getArrayFromRequest(request.getParameter(REQUEST_IDS));
    	int count = 0;  //定义成功暂停的记录数
    	if (name_groups != null && name_groups.length != 0) {
    		count = getService().doPause(name_groups);  //暂停多条记录
    	}
    	request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    public ActionForward resume(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String[][] name_groups = getArrayFromRequest(request.getParameter(REQUEST_IDS));
    	int count = 0;  //定义成功暂停的记录数
    	if (name_groups != null && name_groups.length != 0) {
    		count = getService().doResume(name_groups);  //暂停多条记录
    	}
    	request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
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
        detail(mapping, form, request, response);
        return mapping.findForward(FORWARD_UPDATE_PAGE);
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
        JobTriggerVo vo = new JobTriggerVo();
        vo.setDataMap(SchedulerHelper.parseDataMap(request));//注入json格式的params
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳
        int count = getService().update(vo);  //更新单条记录
        request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
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
        IJobTriggerService service = getService();
        Map<String, String> queryCondition = getQueryCondition(request);
        //String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        //如果采用下边这句，就是不带翻页的，同时需要删掉list页面的page.jsp相关语句
        List<JobTriggerVo> beans = service.queryByCondition(queryCondition, null);  //查询全部,带排序
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, beans.size());
        //RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
        request.setAttribute(REQUEST_QUERY_CONDITION, queryCondition);
        request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        request.setAttribute(REQUEST_WRITE_BACK_FORM_VALUES, RmVoHelper.getMapFromRequest((HttpServletRequest) request));  //回写表单
        return mapping.findForward(FORWARD_LIST_PAGE);
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
        request.setAttribute(REQUEST_QUERY_CONDITION, "");
        simpleQuery(mapping, form, request, response);
        return mapping.findForward(FORWARD_LIST_PAGE);
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
    	String[] name_group = request.getParameter(REQUEST_ID).split(",");
        JobTriggerVo bean = getService().find(name_group[0], name_group[1]);  //通过name, group获取vo
        request.setAttribute(REQUEST_BEAN, bean);  //把vo放入request
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            request.setAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        return mapping.findForward(FORWARD_DETAIL_PAGE);
    }

	
    
    /**
     * 功能: 从request中获得查询条件
     *
     * @param request
     * @return
     */
    protected Map<String,String> getQueryCondition(HttpServletRequest request) {
        Map<String,String> queryCondition = new HashMap<String,String>(); //从request中获得查询条件
        {
        	if(request.getParameter("trigger_name") != null && request.getParameter("trigger_name").length() > 0) {
        		queryCondition.put("trigger_name", request.getParameter("trigger_name"));
        	}
        	if(request.getParameter("trigger_group") != null && request.getParameter("trigger_group").length() > 0) {
        		queryCondition.put("trigger_group", request.getParameter("trigger_group"));
        	}
        }
        return queryCondition;
    }
    
    /**
     * 
     * @param ids
     * @return
     */
    String[][] getArrayFromRequest(String ids) {
    	List<String[]> name_group = new ArrayList<String[]>();
    	int index = 0;
    	for(String id : ids.split(",")) {
    		if(index % 2 == 0) {
    			name_group.add(new String[2]);
    			name_group.get(name_group.size()-1)[0] = id;
    		} else {
    			name_group.get(name_group.size()-1)[1] = id;
    		}
    		index ++;
    	}
    	return (String[][])name_group.toArray(new String[0][0]);
    }
}