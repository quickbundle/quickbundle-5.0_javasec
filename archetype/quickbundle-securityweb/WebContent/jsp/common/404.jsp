<%@ page isErrorPage="true" %><%@ page contentType="text/html; charset=UTF-8" language="java"%><%
	if(request.getAttribute("javax.servlet.forward.request_uri") != null && request.getAttribute("javax.servlet.forward.request_uri").toString().matches("^.*((\\.(jsp|do|py))|/)$")) { //业务url展示友好页面
		response.setStatus(HttpServletResponse.SC_OK);
	} else { //其它资源返回404
		response.setStatus(HttpServletResponse. SC_NOT_FOUND);
	}
%>
<html>
	<head>
		<title>Error Page</title>
	</head>
	<body>
		<font face="Comic Sans MS" size=4>
		<blockquote>
		<center>
		<h1><font color=red>404 Error Page</font></h1>
		</center>
		<p>Sorry, you requested a page that does not exist.
		</blockquote>
		</font>
	</body>
</html>
