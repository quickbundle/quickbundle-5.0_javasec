<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List,java.util.Iterator" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants" %>
<%  //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getAttribute(IRmCodeTypeConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	}else if("1".equals(request.getParameter(IRmCodeTypeConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	}
%>
<%  //取出List
	List lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmCodeTypeConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmCodeTypeConstants.REQUEST_BEANS);  //赋值给resultList
	}
	Iterator itLResult = null;  //定义访问List变量的迭代器
	if(lResult != null) {  //如果List变量不为空
		itLResult = lResult.iterator();  //赋值迭代器
	}
	RmCodeTypeVo resultVo = null;  //定义一个临时的vo变量
%>
<%  //是否跳往打印页面
	if("1".equals(request.getParameter("isPrint"))) {  //如果isPrint参数等于1
		session.setAttribute(IRmCodeTypeConstants.REQUEST_QUERY_CONDITION, request.getAttribute(IRmCodeTypeConstants.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
		RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
		session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
		response.sendRedirect(request.getContextPath() + "/modules/code/rmcodetype/exportRmCodeType_custom.jsp");  //跳转到定制导出页面
		return;
	}
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmCodeTypeConditionAction";
	var rmJspPath = "/condition";
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
		form.queryCondition.value = buildQueryCondition();  //获得组合后的查询条件的字符串描述
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
	function queryAll_onClick(){  //查询全部数据列表
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=queryAll";
		form.submit();
	}
	function export_onClick(){  //导出
    	form.isPrint.value="1";
    	form.target="_blank";
		form.submit();
 		form.target="_self";	
    	form.isPrint.value="";
   	}
	function toAdd_onClick() {  //到增加记录页面
		form.action="<%=request.getContextPath()%>/modules/code/rmcodetype" + rmJspPath + "/insertRmCodeType.jsp";
		form.submit();
	}
	function toReadOnly_onClick() {  //到只读
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=queryAll&<%=IRmCodeTypeConstants.REQUEST_IS_READ_ONLY + "=1"%>";
		form.submit();
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.id.value = thisId;  //赋值thisId给隐藏值id
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=detail";
		form.submit();
	}
	function buildQueryCondition() {  //构建简单查询
		var queryCondition = "";  //定义组合后的查询条件的字符串变量
		var qca = new Array();  //定义查询条件的数组变量,每一个可能的查询条件会被压入这个数组

		pushCondition(qca, "type_keyword");
				
		pushCondition(qca, "name");
				
		pushCondition(qca, "remark");
				
		if(qca.length >= 1) {  //如果有效的查询条件大于1
			queryCondition += " " + qca[0] + " ";  //组装第1个查询条件
		}
		for(var i=1; i<qca.length; i++) {  //从第2个开始循环查询条件
			queryCondition += " and " + qca[i] + " ";  //组装第2个以后的查询条件
		}
		//alert(queryCondition);  //测试显示组合后的查询条件的字符串描述
		return queryCondition;
	}
</script>
</head>
<body>
<script type="text/javascript">
	writeTableTop('列表页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">

<div id="div_simple" style="display:none;">
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right">类型关键字</td>
			<td>
				<input type="text" class="text_field" name="type_keyword" inputName="类型关键字" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">编码类型名称</td>
			<td>
				<input type="text" class="text_field" name="name" inputName="编码类型名称" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">备注</td>
			<td>
				<input type="text" class="text_field" name="remark" inputName="备注" maxLength="2000"/>
			<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
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
    <td class="tableHeaderMiddleTd"><h3>编码数据列表</h3></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
    	<%if(!isReadOnly) { %>
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_toReadOnly" value="只读" onClick="javascript:toReadOnly_onClick()" title="切换到只读模式"/>
		<%}%>
		<input type="button" class="button_ellipse" id="button_export" value="导出" onClick="javascript:export_onClick();" title="按当前查询条件导出数据"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
		<input type="button" class="button_ellipse" id="button_queryAll" value="查全部" onClick="javascript:queryAll_onClick()" title="清除条件，查询全部记录"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmCodeTypeConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="类型关键字" property="type_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title="编码类型名称" property="name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<%="<a style='cursor:pointer;text-decoration: underline;' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=rmValue%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="备注" property="remark" sortable="true"/>
	<layout:collectionItem width="10%"  title="操作" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<div id="div_operation" <%=isReadOnly ? "style=\"display:none\"" : ""%>>
		<a href="javascript:findOne_onClick('<%=rmValue%>')">修改</a>&nbsp;<a href="javascript:deleteOne_onClick('<%=rmValue%>')">删除</a>
		</div>
	</layout:collectionItem>
	
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="name"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="isPrint" value="">
<input type="hidden" name="queryCondition" value="">
<%=isReadOnly ? "<input type=\"hidden\" name=\"" + IRmCodeTypeConstants.REQUEST_IS_READ_ONLY + "\" value=\"1\">" : ""%>
</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmCodeTypeConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmCodeTypeConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
autoPatchParentIdName("<%=IRmCodeTypeConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>");
</script>