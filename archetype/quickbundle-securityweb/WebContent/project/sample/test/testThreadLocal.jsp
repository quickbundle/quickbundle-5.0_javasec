<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.quickbundle.project.login.RmUserVo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	if(RmProjectHelper.getCurrentThreadRequest() != null) {
		RmUserVo vo = RmProjectHelper.getCurrentUser();
		out.print(vo);
	} else {
		out.print("ThreadLocal没有用户数据");
	}
%>
</body>
</html>