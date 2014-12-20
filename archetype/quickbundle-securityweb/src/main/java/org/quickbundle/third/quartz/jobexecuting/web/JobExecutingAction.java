//代码生成时,文件路径: E:/platform/myProject/svn/oss/quickbundle/trunk/quickbundle-securityweb/src/main/java/org/quickbundle/third/quartz/jobexecuting/web/JobExecutingAction.java
//代码生成时,系统时间: 2012-04-02 22:28:49
//代码生成时,操作系统用户: qb

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.third.quartz.jobexecuting.web --> JobExecutingAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2012-04-02 22:28:49 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.third.quartz.jobexecuting.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quartz.Scheduler;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.third.struts.actions.RmDispatchAction;
import org.quickbundle.third.quartz.jobexecuting.service.IJobExecutingService;
import org.quickbundle.third.quartz.jobexecuting.util.IJobExecutingConstants;
import org.quickbundle.third.quartz.jobexecuting.vo.JobExecutingVo;
import org.quickbundle.third.quartz.util.ISchedulerConstants;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class JobExecutingAction extends RmDispatchAction implements IJobExecutingConstants {

	Scheduler getScheduler() {
		return (Scheduler)RmBeanFactory.getBean(ISchedulerConstants.QUARTZ_SHEDULER);
	}
	
    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IJobExecutingService getService() {
        return (IJobExecutingService) RmBeanFactory.getBean(SERVICE_KEY);  //得到Service对象,受事务控制
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
        IJobExecutingService service = getService();
        List<JobExecutingVo> beans = service.queryByCondition();  //按条件查询全部,带排序
        //如果采用下边这句，就是不带翻页的，同时需要删掉list页面的page.jsp相关语句
        //beans = service.queryByCondition(queryCondition, orderStr);  //查询全部,带排序
        request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
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
     * 启动调度器
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getScheduler().start();
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    /**
     * 停止调度器
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward shutdown(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getScheduler().shutdown(true);
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
    /**
     * 挂起
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward standby(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getScheduler().standby();
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    /**
     * 暂停调度器
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward pauseAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getScheduler().pauseAll();
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    /**
     * 继续调度器
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward resumeAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	getScheduler().resumeAll();    	
    	return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
}