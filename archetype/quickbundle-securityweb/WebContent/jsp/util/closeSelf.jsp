<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<script type="text/javascript">
<%
	String rmAlertMsg = request.getParameter("rmAlertMsg");
	rmAlertMsg = RmStringHelper.encode2Encode(rmAlertMsg, "iso8859-1", "GBK");
	if(rmAlertMsg != null) {
		out.println("alert('" + RmStringHelper.replaceStringToScript(rmAlertMsg) + "');");
	}
%>
	var win = window.open('index.html','_self','');
	//win.opener=1;
	win.close();
</script>