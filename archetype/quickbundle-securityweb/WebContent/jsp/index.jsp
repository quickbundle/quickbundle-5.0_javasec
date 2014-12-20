<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%
  	String total_code = request.getParameter("total_code");
  	if(total_code == null){
  		total_code = "default";
  	}
%>
<html>
<head>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<title><bean:message key="qb.web_title"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<frameset rows="34,*" cols="*" frameborder="NO"  bordercolor="#0F4069"  framespacing="0" noresize>
	<frame src="<%=request.getContextPath()%>/jsp/include/header.jsp?total_code=<%=total_code%>" scrolling="NO" name="topFrame" />
	<frame src="<%=request.getContextPath()%>/orgauth/main.jsp" scrolling="NO" name="contentFrameSet" />
</frameset>
<noframes><body>
</body></noframes>
</html>
