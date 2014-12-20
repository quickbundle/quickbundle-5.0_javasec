<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.orgauth.util.impl.RmOrgService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.orgauth.rmuser.vo.RmUserVo" %>
<%@ page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%  //取出List
	List<RmUserVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmUserConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmUserConstants.REQUEST_BEANS);  //赋值给resultList
	}
	//System.out.println("===="+RmProjectHelper.getRmUserVo(request).getCompany().getDisplayname());
	//TmCustomOrgService tmService = (TmCustomOrgService)RmBeanFactory.getBean("ITmCustomOrgService");
	//tmService.checkParent("1000201100000000001",IOrgauthConstants.PartyView.DEFAULT.id());
%>
<%  //是否跳往打印页面
	if("1".equals(request.getParameter("isExport"))) {  //如果isExport参数等于1
		session.setAttribute(IRmUserConstants.REQUEST_QUERY_CONDITION, request.getAttribute(IRmUserConstants.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
		RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
		session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
		response.sendRedirect(request.getContextPath() + "/orgauth/rmuser/exportRmUser_custom.jsp");  //跳转到定制导出页面
		return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmUserAction";
	var rmJspPath = "";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + ids;
		form.submit();
	}
	function role_onClick(){
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return;
		}
		/*
		var id = document.getElementById("employeeId_"+ids).value;
		if(id==null||id.length==0){
			alert("该用户没有员工ID!");
			return false;
		}*/
		form.action="<%=request.getContextPath()%>/orgauth/rmrole/set_role.jsp?id=" + ids;
		form.submit();
	}	
	function findOne_onClick(id) {
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + id;
		form.submit();
	}
	function deleteOne_onClick(id) {
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + id;
			form.submit();
		}
	}
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + ids;
	    	form.submit();
		}
	}
	function updatePassword_onClick() {  //批量生成安全密码
 		var ids_ = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids_ == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("是否重置所选用户的安全密码？\n\n本操作会给用户发邮件，可能耗时5-10秒/每用户")) {  //如果用户在确认对话框按"确定"
			//form.ids.value = ids_;  //赋值thisId给隐藏值id
			form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=updatePassword&ids=" + ids_;
	    	form.submit();
		}			
	}
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
  	
	function toImport_onClick() {  //到导入页面
		window.location="<%=request.getContextPath()%>/orgauth/rmuser" + rmJspPath + "/importRmUser.jsp";
	}
	function export_onClick(){  //导出
    	form.isExport.value="1";
    	form.target="_blank";
		form.submit();
 		form.target="_self";	
    	form.isExport.value="";
   	}
   	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/orgauth/rmuser" + rmJspPath + "/insertRmUser.jsp";
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.id.value = thisId;  //赋值thisId给隐藏值id
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=detail";
		form.submit();
	}
	function toDoDblClick(thisId) {
		detail_onClick(thisId);
	}
</script>
</head>
<body>
<form name="form" method="post">

<div id="div_simple">
	<table width="878" class="table_query">
		<tr>
			<td width="23%">&nbsp;</td>
			<td width="27%">&nbsp;</td>
			<td width="13%">&nbsp;</td>
			<td width="37%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
			<td align="left">
				<input type="text" class="text_field" name="name" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>" maxLength="100"/>
			</td>
			<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")%>：</td>
			<td align="left"><%=RmJspHelper.getSelectField("lock_status", -1, RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS), "", " class='text_field_select' inputName='" + IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status") + "'", true) %></td>
		</tr>
		<tr>
			<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>：</td>
			<td align="left">
				<input type="text" class="text_field" name="login_id" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>" maxLength="100"/>
			</td>
			<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>：</td>
			<td align="left"><input type="text" class="text_field" name="email" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>" maxLength="100"/></td>
		</tr>
		<tr>
			<td align="right">所属组织：</td>
			<td align="left">
				<input type="text" class="text_field_reference_readonly" name="parent_party_id_name" value="" /><input type="hidden" name="parent_party_id"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.parent_party_id, parent_party_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.DEPARTMENT.value()%>&enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>&submit_bk=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>,<%=IOrgauthConstants.OrgTree.DEPARTMENT.value()%>');"/>
			</td>
			<td align="right">&nbsp;</td>
			<td align="left">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"></td>
			<td>
			<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		</table>
</div>
<div id="div_advanced" style="display:none;">
	<table class="table_query">
		<tr>
			<td width="20%"></td>
			<td width="35%"></td>
			<td width="20%"></td>
			<td width="25%"></td>
		</tr>
		<!-- 将需要隐藏的查询条件剪切到这里 -->
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmUserConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_toAuthorize" value="角色设置" onClick="javascript:role_onClick();" title="授权"/>
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onClick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<!--<input type="button" class="button_ellipse" id="button_toImport" value="导入" onClick="javascript:toImport_onClick()" title="导入数据"/>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onClick="javascript:export_onClick();" title="按当前查询条件导出数据"/>-->
		<input type="button" class="button_ellipse" id="button_password" value="密码重置" onClick="javascript:updatePassword_onClick();" title="密码重置"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="name"/>
		<bean:define id="employeeIdValue" name="rmBean" property="employee_id"/>
		<input type="hidden" value="<%=employeeIdValue!=null?employeeIdValue:"" %>" id="employeeId_<%=rmValue %>">
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmUserConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>' property="name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='所属组织' property="parent_party_name" sortable="true">
		<bean:define id="parent_party_id" name="rmBean" property="parent_party_id"/>
		<bean:define id="parent_party_code" name="rmBean" property="parent_party_code"/>
		<bean:define id="parent_party_name" name="rmBean" property="parent_party_name"/>
		<%
			StringBuilder orgFullPath = new StringBuilder();
			try {
				if(parent_party_id != null && parent_party_id.toString().length() > 0) {
					RmCommonVo thisParent = new RmCommonVo();
					thisParent.put("parent_party_code", parent_party_code.toString());
					List<RmCommonVo> lThisParent = new ArrayList<RmCommonVo>();
					lThisParent.add(thisParent);
					List<RmCommonVo> lParent = RmOrgService.getInstance().listAncestor(parent_party_id.toString(), IOrgauthConstants.PartyView.DEFAULT.id(), lThisParent);
					//lParent.remove(0);
					for(RmCommonVo vo : lParent) {
						if(orgFullPath.length() > 0) {
							orgFullPath.append("->");
						}
						orgFullPath.append(vo.getString("child_party_name"));
					}
				}
			}catch(Exception e) {
				e.printStackTrace();
				orgFullPath.append(parent_party_name.toString());
			}
		%>
		<span title="<%=orgFullPath%>"><%=orgFullPath%></span>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")%>' property="lock_status" sortable="true" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="lock_status"/>
		<%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>' property="login_id" sortable="true"/>
	<%-- 
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("organization_id")%>' property="organization_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id")%>' property="employee_id" sortable="true"/>
	--%>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>' property="email" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type")%>' property="admin_type" sortable="true" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="admin_type"/>
		<%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE, rmValue)%>
	</layout:collectionItem>
	<%-- 
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_status")%>' property="login_status" sortable="true" style="text-align:center;"/>
	--%>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_date")%>' property="last_login_date" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="last_login_date"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_ip")%>' property="last_login_ip" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_sum")%>' property="login_sum" sortable="true" style="text-align:center;"/>
	</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="isExport" value="">
<input type="hidden" name="queryCondition" value="">

</form>

<%--start 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	</div>
<!-- end -->

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmUserConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmUserConstants.REQUEST_WRITE_BACK_FORM_VALUES), new String[]{"checkbox_template"}));  //输出表单回写方法的脚本
	}
%>
</script>