<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.modules.message.vo.RmMessageVo" %>
<%@ page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<%  //判断是否只读
    boolean isReadOnly = false;
    if("1".equals(request.getParameter(IRmMessageConstants.REQUEST_IS_READ_ONLY))) {
        isReadOnly = true;
    }else if("1".equals(request.getParameter(IRmMessageConstants.REQUEST_IS_READ_ONLY))){
        isReadOnly = true;
    }
%>
<%  //取出List
	List<RmMessageVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmMessageConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmMessageConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<%  //是否跳往打印页面
	if("1".equals(request.getParameter("isExport"))) {  //如果isExport参数等于1
		session.setAttribute(IRmMessageConstants.REQUEST_QUERY_CONDITION, request.getAttribute(IRmMessageConstants.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
		RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
		session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
		response.sendRedirect(request.getContextPath() + "/message/export");  //跳转到定制导出页面
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
<%if(!isReadOnly) {%>
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return false;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return false;
		}
		window.location="<%=request.getContextPath()%>/message/update/" + ids;
	}
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return false;
		}
		if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			return false;
		}
    	form.action="<%=request.getContextPath()%>/message/delete?ids=" + ids;
    	form.submit();
	}
  	
	function toImport_onClick() {  //到导入页面
		window.location="<%=request.getContextPath()%>/message/import";
	}
   	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/message/insert";
	}
<%} %>
    function list_onClick(){  //简单的模糊查询
        form.action="<%=request.getContextPath()%>/message";
        form.submit();
    }
    function export_onClick(){  //导出
        form.isExport.value="1";
        form.target="_blank";
        form.submit();
        form.target="_self";    
        form.isExport.value="";
    }
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.action="<%=request.getContextPath()%>/message/detail/" + thisId;
		form.submit();
	}
	function toDoDblClick(thisId) {
		detail_onClick(thisId);
	}
</script>
</head>
<body>
<form name="form" method="post">

<div id="div_queryArea">
    <div class="div_queryButtons">
	    <input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:list_onClick()" value="查询" />
	    <input type="reset" class="button_ellipse" id="button_reset" value="清空" />
    </div>
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></td>
			<td>
				<input type="text" class="text_field" name="biz_keyword" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></td>
			<td>
				<input type="text" class="text_field" name="sender_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>" maxLength="9"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%></td>
			<td>
				<input type="text" class="text_field_reference" hiddenInputId="parent_message_id" name="parent_message_id_name" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>" value="" /><input type="hidden" name="parent_message_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onclick="javascript:getReference(new Array(form.parent_message_id, form.parent_message_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/message/reference?referenceInputType=radio');"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></td>
			<td>
				<input type="text" class="text_field" name="owner_org_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></td>
			<td>
				<input type="text" class="text_field" name="template_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>" maxLength="9"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></td>
			<td>
				<input type="text" class="text_field" name="is_affix" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>" maxLength="1"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></td>
			<td>
				<input type="text" class="text_field" name="record_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></td>
			<td>
				<input type="text" class="text_field" name="message_xml_context" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%>" maxLength="32767"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmMessageConstants.TABLE_NAME_DISPLAY %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
<%if(!isReadOnly) {%>
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<input type="button" class="button_ellipse" id="button_toImport" value="导入" onclick="javascript:toImport_onClick()" title="导入数据"/><%} %>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onclick="javascript:export_onClick();" title="按当前查询条件导出数据"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="id"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmMessageConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%>' property="biz_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>' property="sender_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>' property="parent_message_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%>' property="owner_org_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>' property="template_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>' property="is_affix" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%>' property="record_id" sortable="true"/>
	<layout:collectionItem width="8%" title='<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%>' property="message_xml_context" sortable="true"/>
	</layout:collection>

<%-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 --%>
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="isExport" value="">
<input type="hidden" name="queryCondition" value="">
<%=isReadOnly ? "<input type=\"hidden\" name=\"" + IRmMessageConstants.REQUEST_IS_READ_ONLY + "\" value=\"1\">" : ""%>

<%--begin 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/message/statistic/chart">图表统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/message/statistic/flash">Flash图表</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/message/statistic/table">交叉统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/message?<%=IRmMessageConstants.REQUEST_IS_READ_ONLY%>=1">只读模式</a>
</div>
<%--end --%>

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmMessageConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmMessageConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>