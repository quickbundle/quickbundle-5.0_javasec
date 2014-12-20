<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page errorPage="/jsp/common/err.jsp" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tld/struts-layout.tld" prefix="layout" %>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo" %>
<%@ page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants" %>
<%  //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getParameter(IRmAffixConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	}else if("1".equals(request.getParameter(IRmAffixConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	}
%>
<%  //取出List
	List<RmAffixVo> lResult = ((IRmAffixService)RmBeanFactory.getBean(IRmAffixConstants.SERVICE_KEY)).queryByCondition(null, null);  //定义结果列表的List变量
	request.setAttribute(IRmAffixConstants.REQUEST_BEANS, lResult);
	if(request.getAttribute(IRmAffixConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmAffixConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<%  //是否跳往导出Excel页面
	if("1".equals(request.getParameter("isExport"))) {  //如果isExport参数等于1
		session.setAttribute(IRmAffixConstants.REQUEST_QUERY_CONDITION, request.getAttribute(IRmAffixConstants.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
		RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
		session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
		response.sendRedirect(request.getContextPath() + "/modules/affix/rmaffix/exportRmAffix_custom.jsp");  //跳转到定制导出页面
		return;
	}
	if("1".equals(request.getParameter("isPrint"))) {  //如果isPrintExcel参数等于1
		request.setAttribute("abc", "ABCCCCCCCCcc");
		request.setAttribute("ddd", "DDDDDDDDDDDDDDDD1111");
	    //提供数据
	    Map<String, Object> m = new HashMap<String, Object>();
	    m.put("list", request.getAttribute(IRmAffixConstants.REQUEST_BEANS));         
	    //生成Excel。文件名放在request的IExcelConstant.DOWNLOAD_FILE属性中
	    //UtPrint.print("RmAffix.xls", request , m, request);
	    //UtPrint.download(request, response, out);
	    return ;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.quickbundle.modules.affix.rmaffix.service.IRmAffixService"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%><html>
<head>
<!--%@ include file="/jsp/include/rmGlobal.jsp" %-->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
<!--
/* 开始列表控件 */
table.listCss, th.listCss, th.listCss2, td.listCss {
	vertical-align:middle;
	border:0px solid #9EB4CD;
	border-width:0px;
	border-spacing:0px;
	border-collapse:0px;
}
table.listCss {
	word-wrap: normal;
	word-break: keep-all;
	overflow:   hidden;
}
th.listCss {
	background-color: #EBEBEB;/*表格控件表头背景颜色*/
	color:#000000;
	line-height: 22px;
	font-weight: bold;
	text-align: center;
	word-break: keep-all;
}
th.listCss2 {
	line-height: 22px;
	background-color: #EBF3F7;
	font-weight: bold;
	text-align: center;
}
td.listCss {
	color:#000000;	/*表格控件列表文字颜色465c80*/
	text-align:left;
	line-height: 22px;
}
/* 结束列表控件 */
-->
</style>
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmAffixAction";
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
		window.location="<%=request.getContextPath()%>/modules/affix/rmaffix" + rmJspPath + "/importRmAffix.jsp";
	}
	function export_onClick(){  //导出
    	form.isExport.value="1";
    	form.target="_blank";
		form.submit();
 		form.target="_self";	
    	form.isExport.value="";
   	}
	function print_onClick(){  //打印
    	form.isPrint.value="1";
    	form.target="_blank";
		form.submit();
 		form.target="_self";	
    	form.isPrint.value="";
   	}
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/modules/affix/rmaffix" + rmJspPath + "/insertRmAffix.jsp";
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
			<td align="right">业务关键字</td>
			<td>
				<input type="text" class="text_field" name="bs_keyword" inputName="业务关键字" maxLength="100"/>
			</td>
			<td align="right">主记录ID</td>
			<td>
				<input type="text" class="text_field" name="record_id" inputName="主记录ID" maxLength="25"/>
			</td>
		</tr>
		<tr>
			<td align="right">顺序数</td>
			<td>
				<input type="text" class="text_field_half" name="order_number_from" inputName="顺序数" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="order_number_to" inputName="顺序数" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right">作者</td>
			<td>
				<input type="text" class="text_field" name="author" inputName="作者" maxLength="100"/>
			<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
			</td>
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

<table class="tableHeader" id="table_header">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmAffixConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
    	<input type="button" class="button_ellipse" id="button_toManage" value="维护" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onClick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
    	<!-- <div class="div_buttons"></div> -->
		<input type="button" class="button_ellipse" id="button_approve" value="审批" onClick="" title="审批单据"/>
		<input type="button" class="button_ellipse" id="button_toImport" value="导入" onClick="javascript:toImport_onClick()" title="导入数据"/>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onClick="javascript:export_onClick();" title="按当前查询条件导出数据"/>
		<input type="button" class="button_ellipse" id="button_print" value="打印" onClick="javascript:print_onClick();" title="打印当前数据"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>
<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="title"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		out.print(rmOrderNumber+1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="业务关键字" property="bs_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title="主记录ID" property="record_id" sortable="true"/>
	<layout:collectionItem width="8%" title="顺序数" property="order_number" sortable="true"/>
	<layout:collectionItem width="8%" title="标题" property="title" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="title"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=rmValue%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="原文件名" property="old_name" sortable="true"/>
	<layout:collectionItem width="8%" title="真实文件大小" property="save_size" sortable="true"/>
	<layout:collectionItem width="8%" title="内容类型" property="mime_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="mime_type"/>
		<%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="编码" property="encoding" sortable="true"/>
	<layout:collectionItem width="8%" title="描述" property="description" sortable="true"/>
	<layout:collectionItem width="8%" title="作者" property="author" sortable="true"/>
	
	<layout:collectionItem width="8%" title="重复开始原文件名" property="old_name" sortable="true"/>
	<layout:collectionItem width="8%" title="真实文件大小" property="save_size" sortable="true"/>
	<layout:collectionItem width="8%" title="内容类型" property="mime_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="mime_type"/>
		<%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="编码" property="encoding" sortable="true"/>
	<layout:collectionItem width="8%" title="描述" property="description" sortable="true"/>
	<layout:collectionItem width="8%" title="作者" property="author" sortable="true"/>
	</layout:collection>

<input type="hidden" name="id" value="">
<input type="hidden" name="isExport" value="">
<input type="hidden" name="isPrint" value="">
<input type="hidden" name="queryCondition" value="">
<%=isReadOnly ? "<input type=\"hidden\" name=\"" + IRmAffixConstants.REQUEST_IS_READ_ONLY + "\" value=\"1\">" : ""%>

</form>

<%--start 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/ajax/listRmAffix.jsp">ExtJS</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/util/statisticRmAffix_chart.jsp">图表统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixAction.do?cmd=statistic">交叉统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/util/demoRmAffix.jsp">参照DEMO</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/insertRmAffix.jsp">新增页面</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixReadOnlyAction.do?cmd=queryAll">只读模式</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixConditionAction.do?cmd=queryAll&<%=IRmAffixConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>=1">带条件模式</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixConditionAction.do?cmd=queryAll&<%=IRmAffixConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>=1&<%=IRmAffixConstants.REQUEST_IS_READ_ONLY%>=1">带条件只读</a>
</div>
<!-- end -->

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmAffixConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmAffixConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
<%if(isReadOnly) {%>
	rmActionName = "RmAffixReadOnlyAction";
	rmJspPath = "/readonly";
	rmHiddenFormElement(document.all["button_toAdd"]);
	rmHiddenFormElement(document.all["button_deleteMulti"]);
	rmHiddenFormElement(document.all["button_findCheckbox"]);
	rmHiddenFormElement(document.all["button_toImport"]);
	rmHiddenFormElementByName("div_funcNode");
<%}%>
</script>