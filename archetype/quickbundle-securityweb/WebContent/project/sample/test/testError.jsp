<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	if("1".equals(request.getParameter("isSubmit"))) {
		if(RmProjectHelper.logFatal(request.getParameter("msg"), null)) {
%>
<script type="text/javascript">alert("发送严重错误报告成功！");</script>
<%
		} else {
			%>
<script type="text/javascript">alert("发送严重错误报告失败！");</script>
			<%
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="post">
	<textarea name="msg" cols="60" rows="10">数据库服务器负载过高，请注意！</textarea>
	<br/>
	<input type="submit" value="生成严重错误报告" />
	<input type="hidden" name="isSubmit" value="1" />
</form>
<%
	List<String> l = null;
%>
</body>
</html>