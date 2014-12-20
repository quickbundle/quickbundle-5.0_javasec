<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
if("1".equals(request.getParameter("isSubmit"))) {
	response.addHeader("cumail", request.getParameter("cumail"));
	response.sendRedirect(request.getContextPath() + "/project/sample/login/login.jsp");
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
	<div>
		用户名：<input type="text" name="cumail" value="">
	</div>
	<div>
		<input type="submit" value="提交">
	</div>
	<input type="hidden" name="isSubmit" value="1">
</form>
</body>
</html>