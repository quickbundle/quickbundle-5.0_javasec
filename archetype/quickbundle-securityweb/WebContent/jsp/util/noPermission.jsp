<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div>
	<h3>您没有访问此页面的权限，请联系管理员为您的用户更新授权信息。</h3>
	<%
		String url = (String)request.getAttribute("javax.servlet.forward.request_uri");
		if(url == null) {
			url = request.getRequestURL().toString();
		}
	%>
	<p style="color:red;"><%=url%></p>
</div>
</body>
</html>