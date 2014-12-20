//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmuser/web/RmUserAction.java
//代码生成时,系统时间: 2010-11-27 22:08:37
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmuser.web --> RmUserAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:37 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmuser.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.config.RmClusterConfig;
import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.custom.impl.RmCustomOrgService;
import org.quickbundle.orgauth.rmuser.service.IRmUserService;
import org.quickbundle.orgauth.rmuser.util.IRmUserConstants;
import org.quickbundle.orgauth.rmuser.vo.RmUserVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.listener.RmSessionListener;
import org.quickbundle.project.login.IRmLoginConstants;
import org.quickbundle.project.login.IRmSessionService;
import org.quickbundle.project.login.RmUserVo.RmUserSessionVo;
import org.quickbundle.third.struts.actions.RmDispatchAction;
import org.quickbundle.tools.helper.RmJspHelper;
import org.quickbundle.tools.helper.RmPopulateHelper;
import org.quickbundle.tools.helper.RmSqlHelper;
import org.quickbundle.tools.helper.RmStringHelper;
import org.quickbundle.tools.helper.RmVoHelper;

/**
 * 功能、用途、现存BUG:
 * 
 * @author 白小勇
 * @version 1.0.0
 * @see 需要参见的其它类
 * @since 1.0.0
 */

public class RmUserAction extends RmDispatchAction implements IRmUserConstants {

    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    IRmUserService getService() {
        return (IRmUserService) RmBeanFactory.getBean(SERVICE_KEY);  //得到Service对象,受事务控制
    }
    
    IRmSessionService getSessionService() {
    	return (IRmSessionService)RmBeanFactory.getBean(IRmSessionService.class.getName());
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
        RmUserVo vo = new RmUserVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        String id = null;
        if(IOrgauthConstants.Config.isUserRelationParty.value()) {
        	String pratyViewId = request.getParameter("view_id");
        	String partyTypeId = request.getParameter("party_type_id");
        	String isInherit = request.getParameter("isInherit");
        	String[] parentPartyIds = request.getParameter("organization_id").split(",");
        	String[] organizationNames = request.getParameter("organization_name").split(",");
        	vo.setOrganization_id("");
        	id = RmCustomOrgService.getInstance().insertParty(vo, pratyViewId, partyTypeId, parentPartyIds,organizationNames,isInherit);  //插入单条记录
        } else {
        	id = getService().insert(vo);  //插入单条记录
        }
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
        int deleteCount = getService().delete(request.getParameter(REQUEST_ID));  //删除单条记录
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
        String[] id = RmJspHelper.getArrayFromRequest(request, REQUEST_IDS);  //从request获取多条记录id
        int deleteCount = 0;  //定义成功删除的记录数
        if (id != null && id.length != 0) {
        	if(IOrgauthConstants.Config.isUserRelationParty.value()) {
        		deleteCount = RmCustomOrgService.getInstance().deleteParty(id);  //删除多条记录
        	} else {
        		deleteCount = getService().delete(id);  //删除多条记录
        	}
        }
        request.setAttribute(EXECUTE_ROW_COUNT, deleteCount);  //sql影响的行数放入request属性
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
        RmUserVo vo = new RmUserVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳
        int count = 0;
    	if(IOrgauthConstants.Config.isUserRelationParty.value()) {
    		String pratyViewId = request.getParameter("view_id");
    		String partyTypeId = request.getParameter("party_type_id");
    		String isInherit = request.getParameter("isInherit");
    		String[] parentPartyIds = RmStringHelper.parseToArrayIgnoreEmpty(request.getParameter("organization_id"), ",");
    		String[] parentPartyNames = RmStringHelper.parseToArrayIgnoreEmpty(request.getParameter("organization_name"), ",");
    		String[] oldParentPartyIds = RmStringHelper.parseToArrayIgnoreEmpty(request.getParameter("oldParentPartyIds"), ",");
    		String oldName = request.getParameter("oldName");
    		count = RmCustomOrgService.getInstance().updateParty(vo, pratyViewId,oldName, partyTypeId, parentPartyIds,oldParentPartyIds ,isInherit);  //更新单条记录
    		vo.setOrganization_id("");
    	} else {
    		count = getService().update(vo);  //更新单条记录
    	}
    	
        request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
        return mapping.findForward(FORWARD_TO_QUERY_ALL);
    }
    
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
    	List<RmUserVo> lvo = RmPopulateHelper.populateAjax(RmUserVo.class, request);
    	for(RmUserVo vo : lvo) {
    		if(vo.getId() != null && vo.getId().trim().length() > 0) {
    			RmVoHelper.markModifyStamp(request, vo);
    		} else {
    			RmVoHelper.markCreateStamp(request, vo);
    		}
    	}
    	int[] sum_insert_update = getService().insertUpdateBatch(lvo.toArray(new RmUserVo[0]));
    	request.setAttribute(REQUEST_OUTPUT_OBJECT, sum_insert_update);
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
        IRmUserService service = getService();
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<RmUserVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
        //如果采用下边这句，就是不带翻页的，同时需要删掉list页面的page.jsp相关语句
        //beans = service.queryByCondition(queryCondition, orderStr);  //查询全部,带排序
        RmJspHelper.saveOrderStr(orderStr, request);  //保存排序信息
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
        RmUserVo bean = getService().find(request.getParameter(REQUEST_ID));  //通过id获取vo
        request.setAttribute(REQUEST_BEAN, bean);  //把vo放入request
        if(RM_YES.equals(request.getParameter(REQUEST_IS_READ_ONLY))) {
            request.setAttribute(REQUEST_IS_READ_ONLY, request.getParameter(REQUEST_IS_READ_ONLY));
        }
        return mapping.findForward(FORWARD_DETAIL_PAGE);
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
     * 批量生成安全密码
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updatePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String[] ids = RmJspHelper.getArrayFromRequest(request, REQUEST_IDS);  //从request获取多条记录id
        StringBuilder info = new StringBuilder();
        int count = 0;  //定义成功删除的记录数
        if (ids != null && ids.length != 0) {
        	for(String id : ids) {
        		try {
        			count += getService().updatePassword(id); //生成安全密码
				} catch (Exception e) {
					RmUserVo vo = getService().find(id);
					info.append("<div>");
					info.append(vo.getLogin_id());
					info.append(", ");
					info.append(vo.getName());
					info.append(", ");
					info.append(vo.getEmail());
					info.append(", ");
					info.append(e.toString());
					info.append("</div>");
				}
        	}
        }
        request.setAttribute(EXECUTE_ROW_COUNT, count);  //sql影响的行数放入request属性
        if(info.length() > 0) {
        	request.getSession().setAttribute("info", info.toString());
        }
        String alertStr = "成功更新" + count + "条";
        if((ids.length - count) > 0) {
        	alertStr += ", 失败" + (ids.length - count) + "条";
        }
        RmJspHelper.goUrlWithAlert(request, response, alertStr, mapping.findForward(FORWARD_TO_QUERY_ALL).getPath());
        return null;
    }
	
    
    /**
     * 功能: 从request中获得查询条件
     *
     * @param request
     * @return
     */
    protected String getQueryCondition(HttpServletRequest request) {
        String queryCondition = null;
        if(request.getAttribute(REQUEST_QUERY_CONDITION) != null) {  //如果request.getAttribute中有，就不取request.getParameter
            queryCondition = request.getAttribute(REQUEST_QUERY_CONDITION).toString();
        } else {
			List<String> lQuery = new ArrayList<String>();
			
			if(IOrgauthConstants.Config.isUserRelationParty.value()) {
				org.quickbundle.project.login.RmUserVo userVo = (org.quickbundle.project.login.RmUserVo)RmProjectHelper.getRmUserVo(request);
				if(userVo !=null && !IOrgauthConstants.UserAdminType.ADMIN.value().equals(userVo.getAdmin_type()) && userVo.getParty_id_org()!=null){
					RmCommonVo cVo = RmProjectHelper.getCommonServiceInstance().doQuery("select distinct(PR.CHILD_PARTY_CODE) from RM_PARTY_RELATION PR where PR.CHILD_PARTY_ID=" + userVo.getParty_id_org()+" and PR.PARTY_VIEW_ID="+IOrgauthConstants.PartyView.DEFAULT.id()).get(0);
					lQuery.add("exists(select * from RM_USER U2 join RM_PARTY_RELATION PR on U2.ID=PR.CHILD_PARTY_ID where U2.USABLE_STATUS='1' and U2.ID=RM_USER.ID and PR.CHILD_PARTY_CODE like '" + cVo.getString("child_party_code") + "%')");
				}
			}
			if(request.getParameter("parent_party_id") != null && request.getParameter("parent_party_id").length() > 0) {
				lQuery.add("RM_PARTY_RELATION.CHILD_PARTY_CODE LIKE " +
						"" +
						"(select " +
						RmSqlHelper.getFunction(RmSqlHelper.Function.CONCAT, RmConfig.getSingleton().getDatabaseProductName(), 
								"PR9.CHILD_PARTY_CODE", "'%'") +
						" FROM RM_PARTY_RELATION PR9 WHERE PR9.CHILD_PARTY_ID=" + request.getParameter("parent_party_id") + 
						")");
			}
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".name", request.getParameter("name"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".lock_status", request.getParameter("lock_status"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".login_id", request.getParameter("login_id"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".password", request.getParameter("password"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".authen_type", request.getParameter("authen_type"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".organization_id", request.getParameter("organization_id"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".employee_id", request.getParameter("employee_id"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".email", request.getParameter("email"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".admin_type", request.getParameter("admin_type"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".description", request.getParameter("description"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".agent_status", request.getParameter("agent_status"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".login_status", request.getParameter("login_status"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".last_login_date", request.getParameter("last_login_date_from"), RmSqlHelper.TYPE_DATE_GREATER_EQUAL));
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".last_login_date", request.getParameter("last_login_date_to"), RmSqlHelper.TYPE_DATE_LESS_EQUAL));	
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".last_login_ip", request.getParameter("last_login_ip"), RmSqlHelper.TYPE_CHAR_LIKE));
			
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".login_sum", request.getParameter("login_sum_from"), RmSqlHelper.TYPE_NUMBER_GREATER_EQUAL));
    		lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".login_sum", request.getParameter("login_sum_to"), RmSqlHelper.TYPE_NUMBER_LESS_EQUAL));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".last_custom_css", request.getParameter("last_custom_css"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".is_affix", request.getParameter("is_affix"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".function_permission", request.getParameter("function_permission"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".data_permission", request.getParameter("data_permission"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom1", request.getParameter("custom1"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom2", request.getParameter("custom2"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom3", request.getParameter("custom3"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom4", request.getParameter("custom4"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom5", request.getParameter("custom5"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".custom_xml", request.getParameter("custom_xml"), RmSqlHelper.TYPE_CHAR_LIKE));

			queryCondition = RmSqlHelper.appendQueryStr(lQuery.toArray(new String[0]));
        }
        return queryCondition;
    }
    
    /**
     * 得到缓存中查询条件对应的count(*)记录数，如返回-1则执行查询
     * 
     * @param queryCondition
     * @return
     */
    protected int getCount(String queryCondition) {
    	int count = RmSqlCountCache.getCount(TABLE_NAME, queryCondition);
    	if(count < 0) {
    		count = getService().getRecordCount(queryCondition);
    		RmSqlCountCache.putCount(TABLE_NAME, queryCondition, count);
    	}
    	return count;
    }
    
    /**
     *  验证登录账号不能重复
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward validateLoginId(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginId = request.getParameter("login_id");
        String id = request.getParameter("id");
        String status = "1";
    	if(RmStringHelper.checkNotEmpty(loginId)){
    		List<RmCommonVo> resultList = null;
    		if(RmStringHelper.checkNotEmpty(id)){
    			resultList=RmProjectHelper.getCommonServiceInstance().doQuery("select ID from RM_USER  where USABLE_STATUS='1' and LOGIN_ID='"+loginId+"' and ID !="+id);
    		}else{
    			resultList=RmProjectHelper.getCommonServiceInstance().doQuery("select ID from RM_USER  where USABLE_STATUS='1' and LOGIN_ID='"+loginId+"'");
    		}
    		if(resultList.size()==0){
    			status = "0";
    		}
    	}
    	response.getWriter().print(status);
        return null;
    }
    
    /**
     * 查询所有在线用户，分页显示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryOnlineUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	if(RmConfig.getSingleton().isClusterMode()) {
    		return queryOnlineUserCluster(mapping, form, request, response);
    	} else {
    		return queryOnlineUserLocalhost(mapping, form, request, response);
    	}
    }
    
    /**
     * 查询所有在线用户，分页显示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryOnlineUserLocalhost(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String, HttpSession> mSession = RmSessionListener.getSessions();
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, mSession.size());
        List<RmUserSessionVo> beans = new ArrayList<RmUserSessionVo>();
        Collection<HttpSession> cSession = mSession.values();
        int index = 0;
        for(HttpSession session : cSession) {
        	index ++;
        	if(index < pageVo.getStartIndex()) {
        		continue;
        	} else if(index >= pageVo.getStartIndex()+pageVo.getPageSize()) {
        		break;
        	}
    		RmUserSessionVo userVoNew = new RmUserSessionVo();
    		if(session.getAttribute(RM_USER_VO) != null) {
    			org.quickbundle.project.login.RmUserVo userVoInSession = (org.quickbundle.project.login.RmUserVo)session.getAttribute(RM_USER_VO);
    			userVoNew.setId(userVoInSession.getId());
    			userVoNew.setLogin_id(userVoInSession.getLogin_id());
    			userVoNew.setName(userVoInSession.getName());
    			userVoNew.setAdmin_type(userVoInSession.getAdmin_type());
    			userVoNew.setDescription(userVoInSession.getDescription());
    			userVoNew.setEmail(userVoInSession.getEmail());
    			userVoNew.setLast_login_date(userVoInSession.getLast_login_date());
    			userVoNew.setLast_login_ip(userVoInSession.getLast_login_ip());
    			userVoNew.setLogin_sum(userVoInSession.getLogin_sum());
    			{ //custom owner org this login, localhost begin
    				userVoNew.setParty_id_org_name(userVoInSession.getParty_id_org_name());
    			} //custom owner org this login, localhost end
    		} else {
    			userVoNew.setLogin_id("未登录");
    			userVoNew.setLogin_sum(-1);
    			if(session.getAttribute(IRmLoginConstants.LAST_ACCESS_IP) != null) {
    				userVoNew.setLast_login_ip(session.getAttribute(IRmLoginConstants.LAST_ACCESS_IP).toString());
    			}
    		}
    		userVoNew.setSessionId(session.getId());
    		//session创建时间
    		userVoNew.setCreationTime(session.getCreationTime());
    		//最后访问时间
    		userVoNew.setLastAccessedTime(session.getLastAccessedTime());
    		//最大非活动间隔
    		userVoNew.setMaxInactiveInterval(session.getMaxInactiveInterval() * 1000);
    		//登录服务器主机名
    		userVoNew.setClusterNodeId(RmClusterConfig.getSingleton().getSelfId());
    		beans.add(userVoNew);
        }
        request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        request.setAttribute(ONLINE_USER_MODE, "单机模式");
        return mapping.findForward("listPage_online");
    }
    
    /**
     * 查询所有在线用户，分页显示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward queryOnlineUserCluster(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, getSessionService().getRecordCount(null));
        List<RmUserSessionVo> beans = getSessionService().queryByCondition(null, null, pageVo.getStartIndex(), pageVo.getPageSize());
        request.setAttribute(REQUEST_BEANS, beans);  //把结果集放入request
        request.setAttribute(ONLINE_USER_MODE, "集群模式");
        return mapping.findForward("listPage_online");
    }
    
    /**
     * 查询所有在线用户，分页显示
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */
    public ActionForward forceLogoutUser(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String session_id = request.getParameter("session_id");
		//session_id是必备参数
		if(session_id == null || session_id.length() == 0) {
			return mapping.findForward("toQueryOnlineUser");
		}
		getSessionService().forceLogoutUser(request.getParameter("user_id"), session_id);
        return mapping.findForward("toQueryOnlineUser");
    }
}