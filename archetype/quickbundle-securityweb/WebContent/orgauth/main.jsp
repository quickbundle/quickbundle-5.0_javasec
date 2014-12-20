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
<title>rm-based architecture project</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<frameset cols="200,*" frameborder="NO" border="0"  framespacing="0" name="mainFrameSet" id="mainFrameSet">
	<frame src="<%=request.getContextPath()%>/orgauth/tree/functionNodeTree_menu.jsp" scrolling="AUTO" name="leftFrame" />
	<frameset rows="*,0" frameborder="NO" framespacing="0" name="contentFrameSet" id="contentFrameSet">
	  	<frame src="<%=request.getContextPath()%>/jsp/welcome.jsp" scrolling="auto" name="contentFrame" />
	  	<frame src="info.jsp" scrolling="no" name="infoFrame" />
  	</frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>
