<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.tools.support.statistic.RmStatisticHandler"%>
<%@page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<%  //取出本条记录
	RmStatisticHandler sh = null;
	sh = (RmStatisticHandler)request.getAttribute(IRmMessageConstants.REQUEST_STATISTIC_HANDLER);  //从request中取出RmStatisticHandler, 赋值给sh
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmMessageAction";
	function print_onClick(){  //打印
		window.print();
	}
	function exportExcel_onClick(){  //导出到Excel
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.target = "_blank";
	    form.action="<%=request.getContextPath()%>/rmmessage/statistic/table/export";
    	form.submit();
	}  
</script>
</head>
<body>
<form name="form" method="post">
<br>
<h3>统计<%=IRmMessageConstants.TABLE_NAME_DISPLAY%></h3>
<table class="table_div_content">
	<tr>
		<td align="right">
			<input type="button" class="button_ellipse" id="button_print"  value="打印" onclick="javascript:print_onClick();" />
			<input type="button" class="button_ellipse" id="button_export" value="导出Excel" onclick="javascript:exportExcel_onClick();" />
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
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmMessageConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmMessageConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>