<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.modules.code.rmcodedata.vo.RmCodeDataVo" %>
<%@ page import="org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants" %>
<%  //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getAttribute(IRmCodeDataConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	} else if("1".equals(request.getParameter(IRmCodeDataConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	} 
%>
<%  //取出本条记录
	RmCodeDataVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmCodeDataVo)request.getAttribute(IRmCodeDataConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmCodeDataConditionAction";
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
		<td align="right">编码类型ID：</td>
		<td><%=RmStringHelper.prt(resultVo.getCode_type_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">数据关键字：</td>
		<td><%=RmStringHelper.prt(resultVo.getData_key())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">启用状态：</td>
		<td><%=org.quickbundle.project.RmGlobalReference.get(IRmCodeDataConstants.DICTIONARY_RM_YES_NOT, resultVo.getEnable_status())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">数据值：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getData_value())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">完整编码：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getTotal_code())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">备注：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getRemark())%>&nbsp;</td>
	</tr>
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">
<input type="hidden" name="code_type_id" value="<%=RmStringHelper.prt(resultVo.getCode_type_id())%>"><table align="center">
	<tr>
		<td><br>
			<%if(!isReadOnly) { %>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onClick="javascript:delete_onClick();">
			<%}%>
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(

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
	
