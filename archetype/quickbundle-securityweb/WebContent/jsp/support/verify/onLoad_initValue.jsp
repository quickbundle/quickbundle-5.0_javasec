<%@ page contentType="text/html; charset=UTF-8" session="false" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
function onLoad_initValue() {
	var serverVerifyCode = '<%
HttpSession session = RmJspHelper.getSession(request, false);
if(session != null && session.getAttribute("randomStr") != null) {
	out.print(session.getAttribute("randomStr"));
}
%>';
	document.forms[0].verifyCode.value = serverVerifyCode;
}
try {
	onLoad_initValue();
} catch(e){}