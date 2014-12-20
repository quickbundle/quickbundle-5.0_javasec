<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo" %>
<%@ page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants" %>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%  //取出List
	List<RmRoleVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmRoleConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmRoleConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<%  //是否跳往打印页面
	if("1".equals(request.getParameter("isExport"))) {  //如果isExport参数等于1
		session.setAttribute(IRmRoleConstants.REQUEST_QUERY_CONDITION, request.getAttribute(IRmRoleConstants.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
		RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
		session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
		response.sendRedirect(request.getContextPath() + "/orgauth/rmrole/exportRmRole_custom.jsp");  //跳转到定制导出页面
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
	var rmActionName = "RmRoleAction";
	resetListJspQueryInputValue=false;
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
	function authorize_onClick(){
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return;
		}
		form.action="<%=request.getContextPath()%>/orgauth/rmrole/role_moudle.jsp?id=" + ids+"&lazy_init=0&submitType=submitAll&nodeRelationType=hasRelation&lazy_init=0&inputType=checkbox&enableCookie=false";
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
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
  	
	function toImport_onClick() {  //到导入页面
		window.location="<%=request.getContextPath()%>/orgauth/rmrole" + rmJspPath + "/importRmRole.jsp";
	}
	function export_onClick(){  //导出
    	form.isExport.value="1";
    	form.target="_blank";
		form.submit();
 		form.target="_self";	
    	form.isExport.value="";
   	}
   	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/orgauth/rmrole" + rmJspPath + "/insertRmRole.jsp";
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
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>：</td>
			<td align="left">
				<input type="text" class="text_field" name="role_code" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>" maxLength="100"/>
			</td>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
			<td align="left">
				<input type="text" class="text_field" name="name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>" maxLength="100"/>
			</td>
		</tr>

		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>：</td>
			<td align="left">
				<%=RmJspHelper.getSelectField("enable_status", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_ENABLE_STATUS), "", " class='text_field_select' inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status") + "'", true) %>
			</td>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%>：</td>
			<td align="left">
				<%=RmJspHelper.getSelectField("is_system_level", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT), "", " class='text_field_select' inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level") + "'", true) %>
			</td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>：</td>
			<td align="left">
				<%
					String party_id_org = RmProjectHelper.getRmUserVo(request) != null ? RmProjectHelper.getRmUserVo(request).getParty_id_org() : null;
				%>
				<input type="text" class="text_field_readonly" hiddenInputId="owner_org_id" name="owner_org_id_name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>" value="" /><input type="hidden" name="owner_org_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.owner_org_id, form.owner_org_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>&enableCookie=true&inputType=radio&parent_party_id=<%=party_id_org != null ? party_id_org : ""%>&include_self=1&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id()%>');"/>
			</td>
			<!--<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%>：</td>
			<td align="left">
				<%=RmJspHelper.getSelectField("is_recursive", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT), "", " class='text_field_select' inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive") + "'", true) %>
			</td>-->
		</tr>
		<!--
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%>：</td>
			<td>
				<%=RmJspHelper.getSelectField("is_recursive", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT), "", "inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive") + "'", true) %>
			</td>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%></td>
			<td align="left">
				<input type="text" class="text_field" name="matrix_code" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%>" maxLength="500"/>
			</td>
		</tr>
		-->
		<tr>
			<!--<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%></td>-->
			<td>
				<!--<input type="text" class="text_field" name="description" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500"/>
			--><input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
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
    <td class="tableHeaderMiddleTd"><b><%=IRmRoleConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
	    <input type="button" class="button_ellipse" id="button_toAuthorize" value="授权" onClick="javascript:authorize_onClick();" title="授权"/>
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onClick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<!--<input type="button" class="button_ellipse" id="button_approve" value="审批" onClick="" title="审批单据"/>
		<input type="button" class="button_ellipse" id="button_toImport" value="导入" onClick="javascript:toImport_onClick()" title="导入数据"/>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onClick="javascript:export_onClick();" title="按当前查询条件导出数据"/>-->
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="name"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmRoleConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>' property="role_code" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>' property="name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>' property="enable_status" sortable="true" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="enable_status"/>
		<%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_ENABLE_STATUS, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%>' property="is_system_level" sortable="true" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="is_system_level"/>
		<%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>' property="owner_org_id_name" sortable="true" style="text-align:center;"/>
	<%-- 
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%>' property="is_recursive" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="is_recursive"/>
		<%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%>' property="matrix_code" sortable="true"/>
	--%>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>' property="description" sortable="true"/>
	</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="isExport" value="">
<input type="hidden" name="queryCondition" value="">
<input type="hidden" name="usable_status" value="1">
</form>

<%--start 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	</div>
<!-- end -->

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmRoleConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmRoleConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>