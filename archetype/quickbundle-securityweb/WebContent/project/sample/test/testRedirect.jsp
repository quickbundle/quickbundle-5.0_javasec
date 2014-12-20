<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
//url改变，基于浏览器的重定向
if("true".equals(request.getParameter("absolute"))) {
	response.sendRedirect(request.getContextPath() + "/project/sample/test/testRequest.jsp");	
} else {
	response.sendRedirect("testRequest.jsp");	
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>