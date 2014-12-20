<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.tools.support.statistic.RmStatisticHandler"%>
<%@page import="org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants" %>
<%  //取出本条记录
	RmStatisticHandler sh = null;
	sh = (RmStatisticHandler)request.getAttribute(IRmCodeDataConstants.REQUEST_STATISTIC_HANDLER);  //从request中取出RmStatisticHandler, 赋值给sh
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmCodeDataAction";
	function print_onClick(){  //打印
		window.print();
	}
	function exportExcel_onClick(){  //导出到Excel
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.target = "_blank";
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=statistic_exportExcel";
    	form.submit();
	}  
</script>
</head>
<body>
<script type="text/javascript">
	writeTableTop('详细页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">
<br>
<h3>统计<%=IRmCodeDataConstants.TABLE_NAME_CHINESE%></h3>
<table class="table_div_content">
	<tr>
		<td align="right">
			<input type="button" class="button_ellipse" id="button_print"  value="打印" onClick="javascript:print_onClick();" />
			<input type="button" class="button_ellipse" id="button_export" value="导出Excel" onClick="javascript:exportExcel_onClick();" />
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onclick="javascript:history.go(h-1);" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td>
<%=sh.toHtml()%>
		</td>
	</tr>
</table>

<input type="hidden" name="queryCondition" value="">

</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmCodeDataConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmCodeDataConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>
