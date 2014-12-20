<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
</head>
<body>

</body>
</html>

<script type="text/javascript">
try {
<%
	String rm_alertStr = request.getParameter("rm_alertStr");
	String rm_targetForwardPath = request.getParameter("rm_targetForwardPath");
	if(rm_alertStr == null || rm_targetForwardPath == null) {
		out.println("alert('调用本页面不正确，请检查程序');");
	} else {
		rm_alertStr = RmStringHelper.replaceStringToScript(rm_alertStr);
		out.println("alert('" + rm_alertStr + "');");
		rm_targetForwardPath = request.getContextPath() + rm_targetForwardPath;
		rm_targetForwardPath = RmStringHelper.replaceStringToScript(rm_targetForwardPath);
		out.println("window.location.href='';");
		out.println("document.location.replace('" + rm_targetForwardPath + "');");
	}
%>
} catch(e) {
	alert("rmAlertForward.jsp页面的脚本程序出现异常: + \n" + e.message);
}
</script>