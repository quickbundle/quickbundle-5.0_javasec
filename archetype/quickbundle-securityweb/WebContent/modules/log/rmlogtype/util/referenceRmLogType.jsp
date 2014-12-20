<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.modules.log.rmlogtype.vo.RmLogTypeVo" %>
<%@page import="org.quickbundle.modules.log.rmlogtype.util.IRmLogTypeConstants" %>
<%
	String referenceInputType = String.valueOf(request.getAttribute(IRmLogTypeConstants.REQUEST_REFERENCE_INPUT_TYPE));
	if(referenceInputType == null || referenceInputType.length() == 0 || (!"checkbox".equals(referenceInputType.toLowerCase()) && !"radio".equals(referenceInputType.toLowerCase()))) {
		referenceInputType = "radio";		
	}
	//取出List
	List<RmLogTypeVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmLogTypeConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmLogTypeConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmLogTypeAction";
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
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%></td>
			<td>
				<input type="text" class="text_field" name="bs_keyword" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
			<td>
				<input type="text" class="text_field" name="name" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_record")%></td>
			<td>
				<%=RmJspHelper.getSelectField("is_record", -1, RmGlobalReference.get(IRmLogTypeConstants.DICTIONARY_RM_YES_NOT), "", "inputName='" + IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_record") + "'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_alone_table")%></td>
			<td>
				<%=RmJspHelper.getSelectField("is_alone_table", -1, RmGlobalReference.get(IRmLogTypeConstants.DICTIONARY_RM_YES_NOT), "", "inputName='" + IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_alone_table") + "'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("table_name")%></td>
			<td>
				<input type="text" class="text_field" name="table_name" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("table_name")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%></td>
			<td>
				<input type="text" class="text_field" name="pattern_value" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%>" maxLength="500"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%></td>
			<td>
				<input type="text" class="text_field_half" name="match_priority_from" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="match_priority_to" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%>" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%></td>
			<td>
				<input type="text" class="text_field_half" name="max_log_sum_from" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="max_log_sum_to" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%>" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
			<td>
				<input type="text" class="text_field" name="description" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("custom_xml")%></td>
			<td>
				<input type="text" class="text_field" name="custom_xml" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("custom_xml")%>" maxLength="2000"/>
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
    <td class="tableHeaderMiddleTd"><b><%=IRmLogTypeConstants.TABLE_NAME_CHINESE %>列表</b></td>
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
			RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmLogTypeConstants.RM_PAGE_VO);
			out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
		%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%>' property="bs_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("name")%>' property="name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_record")%>' property="is_record" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="is_record"/>
		<%=RmGlobalReference.get(IRmLogTypeConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_alone_table")%>' property="is_alone_table" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="is_alone_table"/>
		<%=RmGlobalReference.get(IRmLogTypeConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("table_name")%>' property="table_name" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%>' property="pattern_value" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%>' property="match_priority" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%>' property="max_log_sum" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("description")%>' property="description" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("custom_xml")%>' property="custom_xml" sortable="true"/>
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
	if(request.getAttribute(IRmLogTypeConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmLogTypeConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>