<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" session="false" pageEncoding="UTF-8"%>
<%@page import="org.quickbundle.project.login.UserUniqueLoginVo"%>
<%@page import="org.quickbundle.project.login.IRmLoginConstants"%>
<%
	UserUniqueLoginVo uniqueUserVo = (UserUniqueLoginVo)request.getAttribute(IRmLoginConstants.USER_UNIQUE_LOGIN_VO);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.quickbundle.tools.helper.RmStringHelper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<p>&nbsp;</p>
<div align="center">
<form action="<%=request.getContextPath()%>/RmLoginAction.do?cmd=login" method="post">
	<div>
		<div><h3>该用户(<%=uniqueUserVo.getLogin_id()%>)已经从<%
			out.print(uniqueUserVo.getOnline_system() != null ? uniqueUserVo.getOnline_system().getTitle() : "别处");
		%>登录：</h3></div>
		
		<table>
<%if(uniqueUserVo.getLogin_ip() != null) {%>
			<tr><td align="right">登录IP：</td><td align="left"><%=uniqueUserVo.getLogin_ip()%></td></tr>
<%} %>
<%if(uniqueUserVo.getLogin_date() != null) {%>
			<tr><td align="right">登录时间：</td><td align="left"><%=RmStringHelper.prt(uniqueUserVo.getLogin_date(), 19)%></td></tr>
<%} %>
<%if(uniqueUserVo.getLogin_date() != null) {%>
			<tr><td align="right">累计在线：</td><td align="left"><%=RmDateHelper.parseToTimeDesciption(System.currentTimeMillis()-uniqueUserVo.getLogin_date().getTime())%></td></tr>
<%} %>
<%if(uniqueUserVo.getDescription() != null) {%>
			<tr><td align="right">描述：</td><td align="left"><%=uniqueUserVo.getDescription()%></td></tr>
<%} %>
		</table>
	</div>
	<h3>您确定要强制登录吗？(这可能会造成另一用户被下线)</h3>
	<input type="hidden" name="<%=IRmLoginConstants.Para.is_force_login%>" value="0">
	<input type="hidden" name="ban_online_system" value="<%=uniqueUserVo.getOnline_system() != null ? uniqueUserVo.getOnline_system().name() : ""%>"/>

	<input type="button" value="确定" onclick="form.<%=IRmLoginConstants.Para.is_force_login%>.value='1';form.submit();">
	
	<input type="button" value="取消" onclick="form.<%=IRmLoginConstants.Para.is_force_login%>.value='0';form.submit();">
</form>
</div>
</body>
</html>