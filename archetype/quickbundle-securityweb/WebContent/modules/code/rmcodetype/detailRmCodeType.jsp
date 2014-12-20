<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants" %>
<%  //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getAttribute(IRmCodeTypeConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	} else if("1".equals(request.getParameter(IRmCodeTypeConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	} 
%>
<%  //取出本条记录
	RmCodeTypeVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmCodeTypeVo)request.getAttribute(IRmCodeTypeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmCodeTypeAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.submit();
	}  
</script>
</head>
<body>
<script type="text/javascript">
	writeTableTop('详细页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right">类型关键字：</td>
		<td><%=RmStringHelper.prt(resultVo.getType_keyword())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">编码类型名称：</td>
		<td><%=RmStringHelper.prt(resultVo.getName())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">备注：</td>
		<td colspan="3"><span bs_keyword="<%=IRmCodeTypeConstants.TABLE_NAME%>" record_id="<%=resultVo.getId()%>"><%=RmStringHelper.prt(resultVo.getRemark())%>&nbsp;</span></td>
	</tr>
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onClick="javascript:delete_onClick();">
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(
	new Array ('编码数据','<%=request.getContextPath()%>/RmCodeDataConditionAction.do?cmd=queryAll&code_type_id=<%=resultVo.getId()%>&code_type_id_name=<%=resultVo.getName()%><%=isReadOnly ? "&" + org.quickbundle.project.IGlobalConstants.REQUEST_IS_READ_ONLY + "=1" : ""%>')
);
</script>
<br/><br/>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%if(isReadOnly) {%>
	rmActionName = "RmCodeTypeReadOnlyAction";
	rmJspPath = "/readonly";
	rmHiddenFormElement(document.all["button_update"]);
	rmHiddenFormElement(document.all["button_delete"]);
<%}%>
</script>
	
