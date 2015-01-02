<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
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
	List<RmAffixVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmAffixConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmAffixConstants.REQUEST_BEANS);  //赋值给resultList
	}
	System.out.println(RmVoHelper.writeBackListToRowTable("AA", lResult));
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
	    return ;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmAffixAction";
	var rmJspPath = "";
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
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + ids;
		form.submit();
	}
	function findOne_onClick(id) {
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + id;
		form.submit();
	}
	function deleteOne_onClick(id) {
		if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + id;
		form.submit();
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
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + ids;
    	form.submit();
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
    	//form.target="_blank";
    	form.target="hiddenIframe";
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
	function approve_onClick() {
		a = 1;
		alert(  "内层table:" + widthObj(document.getElementById("strutslayout_div_scroll").children[0])
				+ "\n\ndiv:" + widthObj(document.getElementById("strutslayout_div_scroll")) 
				+ "\n\ntd2:" + widthObj(document.getElementById("strutslayout_div_scroll").parentNode)
				+ "\n\ntd1:" + widthObj(document.getElementById("strutslayout_div_scroll").parentNode.parentNode.parentNode.children[0].children[0])
				+ "\n\ntable:" + widthObj(document.getElementById("strutslayout_div_scroll").parentNode.parentNode.parentNode.parentNode)
				+ "\n\nouter div:" + widthObj(document.getElementById("strutslayout_div_scroll").parentNode.parentNode.parentNode.parentNode.parentNode)
			);
		widthObj(document.getElementById("strutslayout_div_scroll"));
	}
	function widthObj(obj) {
		return obj.tagName +  ":clientWidth=" + obj.clientWidth + "\noffsetWidth=" + obj.offsetWidth + "\nscrollWidth=" + obj.scrollWidth + "\nstyle.width=" + obj.style.width + "\nwidth=" + obj.width;
	}
	function approve_onClick2() {
		var nodiv = document.getElementsByTagName("DIV")[7];
		alert(
				"nodiv:" + widthObj(nodiv)
				+ "\n\ntd2:" + widthObj(nodiv.parentNode)
				+ "\n\ntr2:" + widthObj(nodiv.parentNode.parentNode)
				+ "\n\ntable2:" + widthObj(nodiv.parentNode.parentNode.parentNode.parentNode)
				+ "\n\ntd1:" + widthObj(nodiv.parentNode.parentNode.parentNode.parentNode.parentNode)
				+ "\n\ntr1:" + widthObj(nodiv.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode)
				+ "\n\ntable1:" + widthObj(nodiv.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode)
			);
		document.getElementById("tt").value = nodiv.parentNode.parentNode.parentNode.parentNode.parentNode.outerHTML;
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
			<td align="right">业务关键字</td>
			<td>
				<input type="text" class="text_field" name="bs_keyword" inputName="业务关键字" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">主记录ID</td>
			<td>
				<input type="text" class="text_field" name="record_id" inputName="主记录ID" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">顺序数</td>
			<td>
				<input type="text" class="text_field_half" name="order_number_from" inputName="顺序数" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="order_number_to" inputName="顺序数" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">标题</td>
			<td>
				<input type="text" class="text_field" name="title" inputName="标题" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">原文件名</td>
			<td>
				<input type="text" class="text_field" name="old_name" inputName="原文件名" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">真实文件大小</td>
			<td>
				<input type="text" class="text_field_half" name="save_size_from" inputName="真实文件大小" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="save_size_to" inputName="真实文件大小" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">内容类型</td>
			<td>
				<%=org.quickbundle.tools.helper.RmJspHelper.getSelectField("mime_type", -1, org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE), "", "inputName='内容类型'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">编码</td>
			<td>
				<input type="text" class="text_field" name="encoding" inputName="编码" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">描述</td>
			<td>
				<input type="text" class="text_field" name="description" inputName="描述" maxLength="2000"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">作者</td>
			<td>
				<input type="text" class="text_field" name="author" inputName="作者" maxLength="100"/>
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
		<!-- 将需要隐藏的查询条件剪切到这里 -->
	</table>
</div>

<table class="tableHeader">
  <tr>
	<td width="1%" class="tableHeaderLeftTd"></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmAffixConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
    	<input type="button" class="button_ellipse" id="button_toManage" value="维护" title="跳转到新增页面"/><div class="div_buttons" >
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
    	</div>
		<input type="button" class="button_ellipse" id="button_approve" value="审批" onclick="" title="审批单据"/>
		<input type="button" class="button_ellipse" id="button_toImport" value="导入" onclick="javascript:toImport_onClick()" title="导入数据"/>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onclickto="javascript:export_onClick();" title="按当前查询条件导出数据"/>
		<input type="button" class="button_ellipse" id="button_print" value="打印" onclickto="javascript:print_onClick();" title="打印当前数据"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
  </tr>
</table>

<layout:collection lineWrap="false" name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="title"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmAffixConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="业务关键字" property="bs_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title="主记录ID" property="record_id" sortable="true"/>
	<layout:collectionItem width="8%" title="顺序数" property="order_number" sortable="true"/>
	<layout:collectionItem width="8%" title="标题" property="title" sortable="true" locked="true">
		<bean:define id="rmValue" name="rmBean" property="title"/>
		<%="<a class='aul' onclick='javascript:detail_onClick(getRowHiddenId())'>"%>
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
	
	
	
	
	
	
	<layout:collectionItem width="8%" title="原文件名" property="old_name" sortable="true"/>
	<layout:collectionItem width="8%" title="真实文件大小" property="save_size" sortable="true"/>
	<layout:collectionItem width="8%" title="内容类型" property="mime_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="mime_type"/>
		<%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="编码" property="encoding" sortable="true"/>
	<layout:collectionItem width="8%" title="描述" property="description" sortable="true"/>
	<layout:collectionItem width="8%" title="作者" property="author" sortable="true"/>
		<layout:collectionItem width="8%" title="原文件名" property="old_name" sortable="true"/>
	<layout:collectionItem width="8%" title="真实文件大小" property="save_size" sortable="true"/>
	<layout:collectionItem width="8%" title="内容类型" property="mime_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="mime_type"/>
		<%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="编码" property="encoding" sortable="true"/>
	<layout:collectionItem width="8%" title="描述" property="description" sortable="true"/>
	<layout:collectionItem width="8%" title="作者" property="author" sortable="true"/>
</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="isExport" value="">
<input type="hidden" name="isPrint" value="">
<input type="hidden" name="queryCondition" value="">
<%=isReadOnly ? "<input type=\"hidden\" name=\"" + IRmAffixConstants.REQUEST_IS_READ_ONLY + "\" value=\"1\">" : ""%>

</form>

<%--start 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/util/statisticRmAffix_chart.jsp">图表统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/util/statisticRmAffix_ofc.jsp">Flash图表</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixAction.do?cmd=statistic">交叉统计</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/util/demoRmAffix.jsp">参照DEMO</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/modules/affix/rmaffix/insertRmAffix.jsp">新增页面</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixReadOnlyAction.do?cmd=queryAll">只读模式</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixConditionAction.do?cmd=queryAll&<%=IRmAffixConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>=1">带条件模式</a>
	<a class="aul" target="_blank" href="<%=request.getContextPath()%>/RmAffixConditionAction.do?cmd=queryAll&<%=IRmAffixConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>=1&<%=IRmAffixConstants.REQUEST_IS_READ_ONLY%>=1">带条件只读</a>
</div>
<!-- end -->
<div style="display:none">
<input type="button" class="button_ellipse" id="button_approve" value="test" onClick="approve_onClick()" title="审批单据"/>
<textarea id="tt" rows="50" cols="180"></textarea>
</div>

<iframe name="hiddenIframe" style="display:none"></iframe>
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
<%--@ include file="/jsp/include/orgButton.jsp" --%>