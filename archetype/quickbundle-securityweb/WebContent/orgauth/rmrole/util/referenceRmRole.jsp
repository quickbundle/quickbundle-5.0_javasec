<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo" %>
<%@page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants" %>
<%
	String referenceInputType = String.valueOf(request.getAttribute(IRmRoleConstants.REQUEST_REFERENCE_INPUT_TYPE));
	if(referenceInputType == null || referenceInputType.length() == 0 || (!"checkbox".equals(referenceInputType.toLowerCase()) && !"radio".equals(referenceInputType.toLowerCase()))) {
		referenceInputType = "radio";		
	}
	//取出List
	List<RmRoleVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmRoleConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmRoleConstants.REQUEST_BEANS);  //赋值给resultList
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
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=queryReference";
    	form.submit();
  	}
	function refresh_onClick(){  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		//参照页面默认不进去细览
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
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
			<td>
				<input type="text" class="text_field" name="name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%></td>
			<td>
				<%=RmJspHelper.getSelectField("is_system_level", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT), "", "inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level") + "'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%></td>
			<td>
				<input type="text" class="text_field" name="owner_org_id" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>" maxLength="25"/>
			<input type="text" class="text_field_reference" hiddenInputId="owner_org_id" name="owner_org_id_name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>" value="" /><input type="hidden" name="owner_org_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.owner_org_id, form.owner_org_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?enableCookie=true&inputType=radio');"/>
    </td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%></td>
			<td>
				<%=RmJspHelper.getSelectField("is_recursive", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT), "", "inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive") + "'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%></td>
			<td>
				<input type="text" class="text_field" name="matrix_code" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%>" maxLength="500"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
			<td>
				<input type="text" class="text_field" name="description" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500"/>
			<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
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
		<!-- 需要隐藏查询条件剪切到这里 -->
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmRoleConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="name"/>
		<input type="<%="checkbox".equals(referenceInputType)?"checkbox":""%><%="radio".equals(referenceInputType)?"radio":""%>" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>" />
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
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>' property="name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%>' property="is_system_level" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="is_system_level"/>
		<%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>' property="owner_org_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%>' property="is_recursive" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="is_recursive"/>
		<%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%>' property="matrix_code" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>' property="description" sortable="true"/>
	</layout:collection>
		
<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
<input type="hidden" name="referenceInputType" value="<%=referenceInputType%>">

<input type="hidden" id="rmCheckReturnValue" name="rmCheckReturnValue" value="">
<input type="hidden" id="rmCheckReturnName" name="rmCheckReturnName" value="">

</form>
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmRoleConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmRoleConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>