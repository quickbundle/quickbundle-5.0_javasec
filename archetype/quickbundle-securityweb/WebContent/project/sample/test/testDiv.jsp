<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>
<h3>大容量表格</h3>
</p>
<%
	for(int i=0; i<20000; i++) {
%>	
	<div>
		<input type="checkbox"/>
		<span>随机的数据量<%=i %></span>
		<span>随机的数据量<%=i %></span>
		<span>随机的数据量<%=i %></span>
		<span>字段<%=i %></span>
		<span>随机的数据量<%=i %></span>
		<span>随机的数据量<%=i %></span>
		<span>随机的数据量<%=i %></span>
		<span>随机的数据量<%=i %></span>
				<span>随机的数据量<%=i %></span>
						<span>随机的数据量<%=i %></span>
	</div>
<% }%>
</body>
</html>