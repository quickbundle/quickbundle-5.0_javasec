//代码生成时,文件路径: e:/download/quickbundle-securityweb/src/main/java/orgauth/rmrole/web/RmRoleAction.java
//代码生成时,系统时间: 2010-11-27 22:08:38
//代码生成时,操作系统用户: Administrator

/*
 * 系统名称:单表模板 --> quickbundle-securityweb
 * 
 * 文件名称: org.quickbundle.orgauth.rmrole.web --> RmRoleAction.java
 * 
 * 功能描述:
 * 
 * 版本历史: 2010-11-27 22:08:38 创建1.0.0版 (白小勇)
 *  
 */

package org.quickbundle.orgauth.rmrole.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.quickbundle.base.beans.factory.RmBeanFactory;
import org.quickbundle.base.web.page.RmPageVo;
import org.quickbundle.config.RmConfig;
import org.quickbundle.orgauth.IOrgauthConstants;
import org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo;
import org.quickbundle.orgauth.rmpartyrole.util.IRmPartyRoleConstants;
import org.quickbundle.orgauth.rmrole.service.IRmRoleService;
import org.quickbundle.orgauth.rmrole.util.IRmRoleConstants;
import org.quickbundle.orgauth.rmrole.vo.RmRoleVo;
import org.quickbundle.project.IGlobalConstants;
import org.quickbundle.project.RmProjectHelper;
import org.quickbundle.project.cache.RmSqlCountCache;
import org.quickbundle.project.common.vo.RmCommonVo;
import org.quickbundle.project.login.RmUserVo;
import org.quickbundle.third.struts.RmActionHelper;
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

public class RmRoleAction extends RmDispatchAction implements IRmRoleConstants {

    /**
     * 得到Service对象
     * 
     * @return Service对象
     */
    public IRmRoleService getService() {
        return (IRmRoleService) RmBeanFactory.getBean(SERVICE_KEY);  //得到Service对象,受事务控制
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
        RmRoleVo vo = new RmRoleVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markCreateStamp(request,vo);  //打创建时间,IP戳
        //全局角色没有所属组织ID
        if("1".equals(request.getParameter("is_system_level"))){
        	vo.setOwner_org_id(null);
        }
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
        	if(IOrgauthConstants.Config.isRoleRelationParty.value()) {
        		List<RmCommonVo> roleList = RmProjectHelper.getCommonServiceInstance().doQuery("select id from RM_PARTY_ROLE where RM_PARTY_ROLE.ROLE_ID in("+RmStringHelper.parseToSQLString(id)+")");
        		String msg = "删除失败！";
        		if(roleList != null && roleList.size() == 0){
        			deleteCount = getService().delete(id);  //删除多条记录
        			msg = "删除成功";
        		}
        		return RmActionHelper.getForwardInstanceWithAlert(mapping.findForward(FORWARD_TO_QUERY_ALL).getPath(),msg);  
        	}else{
        		//deleteCount = getService().delete(id);  //删除多条记录
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
        RmRoleVo vo = new RmRoleVo();
        RmPopulateHelper.populate(vo, request);  //从request中注值进去vo
        RmVoHelper.markModifyStamp(request,vo);  //打修改时间,IP戳
        //全局角色没有所属组织ID
        if("1".equals(request.getParameter("is_system_level"))){
        	vo.setOwner_org_id(null);
        }
        int count = getService().update(vo);  //更新单条记录
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
    	List<RmRoleVo> lvo = RmPopulateHelper.populateAjax(RmRoleVo.class, request);
    	for(RmRoleVo vo : lvo) {
    		if(vo.getId() != null && vo.getId().trim().length() > 0) {
    			RmVoHelper.markModifyStamp(request, vo);
    		} else {
    			RmVoHelper.markCreateStamp(request, vo);
    		}
    	}
    	int[] sum_insert_update = getService().insertUpdateBatch(lvo.toArray(new RmRoleVo[0]));
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
        IRmRoleService service = getService();
        String queryCondition = getQueryCondition(request);  //从request中获得查询条件
        RmPageVo pageVo = RmJspHelper.transctPageVo(request, getCount(queryCondition));
        String orderStr = RmJspHelper.getOrderStr(request);  //得到排序信息
        List<RmRoleVo> beans = service.queryByCondition(queryCondition, orderStr, pageVo.getStartIndex(), pageVo.getPageSize());  //按条件查询全部,带排序
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
        RmRoleVo bean = getService().find(request.getParameter(REQUEST_ID));  //通过id获取vo
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
     * 功能: 从request中获得查询条件
     *
     * @param request
     * @return
     */
    protected String getQueryCondition(HttpServletRequest request) {
        String queryCondition = null;
        if(request.getAttribute(REQUEST_QUERY_CONDITION) != null && !"".equals(request.getAttribute(REQUEST_QUERY_CONDITION))) {  //如果request.getAttribute中有，就不取request.getParameter
            queryCondition = request.getAttribute(REQUEST_QUERY_CONDITION).toString();
        } else {
			List<String> lQuery = new ArrayList<String>();
			
			if(IOrgauthConstants.Config.isUserRelationParty.value()) {
		    	RmUserVo userVo = (RmUserVo)RmProjectHelper.getRmUserVo(request);
		    	if(userVo !=null && !IOrgauthConstants.UserAdminType.ADMIN.value().equals(userVo.getAdmin_type()) && userVo.getParty_id_org()!=null){
		    		RmCommonVo cVo = RmProjectHelper.getCommonServiceInstance().doQuery("select distinct(PR.CHILD_PARTY_CODE) from RM_PARTY_RELATION PR where PR.CHILD_PARTY_ID="+userVo.getParty_id_org()).get(0);
		    		lQuery.add("exists(select * from RM_ROLE R2 left join RM_PARTY_RELATION PR on R2.OWNER_ORG_ID=" + RmSqlHelper.getFunction(RmSqlHelper.Function.TO_CHAR, RmConfig.getSingleton().getDatabaseProductName(), "PR.CHILD_PARTY_ID") + " where R2.USABLE_STATUS='1' and R2.ID=RM_ROLE.ID and PR.CHILD_PARTY_CODE like '" + cVo.getString("child_party_code") + "%') or IS_SYSTEM_LEVEL=1");
		        }
			}
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".usable_status", "1", RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".name", request.getParameter("name"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".role_code", request.getParameter("role_code"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".is_system_level", request.getParameter("is_system_level"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".owner_org_id", request.getParameter("owner_org_id"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".is_recursive", request.getParameter("is_recursive"), RmSqlHelper.TYPE_CUSTOM, "='", "'"));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".matrix_code", request.getParameter("matrix_code"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".description", request.getParameter("description"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".function_permission", request.getParameter("function_permission"), RmSqlHelper.TYPE_CHAR_LIKE));
			
			lQuery.add(RmSqlHelper.buildQueryStr(TABLE_NAME + ".data_permission", request.getParameter("data_permission"), RmSqlHelper.TYPE_CHAR_LIKE));
			

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
     * 从页面表单获取信息注入vo，并插入单条记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response 
     * @return
     * @throws Exception
     */    
    public ActionForward insertPartyRole(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String id = request.getParameter("id");
    	String [] role_ids = request.getParameterValues("role_name");
    	if(RmStringHelper.checkNotEmpty(id)){
    		getService().insertRm_party_roles(id, role_ids);
    	}
    	//return RmJspHelper.getForwardInstanceWithAlert("/orgauth/rmrole/set_role.jsp?id=" + id, "角色设置成功");
    	return mapping.findForward(FORWARD_USER_QUERALL);
    }


    /**
     * 保存角色授权
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateRoleFunctionNode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String role_id = request.getParameter("role_id");
        String function_node_code = request.getParameter("function_node_ids");        
        //重新添加授权记录和授权资源
        String[] nodeCode = RmStringHelper.parseToArray(function_node_code);
        String[] nodeCode_name = RmStringHelper.parseToArray(request.getParameter("function_node_id_names"));
        if(nodeCode != null && role_id!=null && (nodeCode.length>0 || role_id.trim().length()>0)){
        	List<RmFunctionNodeVo> lvo = new ArrayList<RmFunctionNodeVo>();
        	for(int i=0; i<nodeCode.length; i++) {
        		RmFunctionNodeVo vo = new RmFunctionNodeVo();
        		vo.setTotal_code(nodeCode[i]);
        		if(nodeCode_name.length >= i+1) {
        			vo.setName(nodeCode_name[i]);
        		}
        		lvo.add(vo);
        	}
        	this.getService().insertPartyResource(lvo.toArray(new RmFunctionNodeVo[0]), role_id);
        }
        return RmActionHelper.getForwardInstanceWithAlert(mapping.findForward(FORWARD_TO_QUERY_ALL).getPath(), "操作成功!");
    } 

    /**
     * 功能: 删除中间表RM_PARTY_ROLE数据
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteRmm_party_role(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String role_id = request.getParameter("role_id");
    	String[] owner_party_ids = request.getParameter("owner_party_ids").split(",");
    	int count = getService().deleteRm_party_role(role_id, owner_party_ids);
    	return RmActionHelper.getForwardInstanceWithAlert("/orgauth/rmrole/middle/listRmm_party_role.jsp?role_id=" + role_id, "删除了" + count + "条记录!");
    } 
    /**
     *  验证角色编号不能重复
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward validateRoleCode(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String roleCode = request.getParameter("role_code");
        String id = request.getParameter("id");
        String status = "1";
    	if(RmStringHelper.checkNotEmpty(roleCode)){
    		List<RmCommonVo> resultList = null;
    		if(RmStringHelper.checkNotEmpty(id)){
    			resultList=RmProjectHelper.getCommonServiceInstance().doQuery("select ID from RM_ROLE  where USABLE_STATUS='1' and ROLE_CODE='"+roleCode+"' and ID !="+id);
    		}else{
    			resultList=RmProjectHelper.getCommonServiceInstance().doQuery("select ID from RM_ROLE  where USABLE_STATUS='1' and  ROLE_CODE='"+roleCode+"'");
    		}
    		if(resultList.size()==0){
    			status = "0";
    		}
    	}
    	response.getWriter().print(status);
        return null;
    }
    
    /**
     *  删除角色团体关联
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deletePartyRoleRelation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	RmProjectHelper.getCommonServiceInstance().doUpdate("delete from RM_PARTY_ROLE WHERE ID=" + request.getParameter("party_role_id"));
    	RmSqlCountCache.clearCount(IRmPartyRoleConstants.SERVICE_KEY);
        return RmActionHelper.getForwardInstance("/RmRoleAction.do?cmd=detail&id=" + request.getParameter("id"));
    }
}