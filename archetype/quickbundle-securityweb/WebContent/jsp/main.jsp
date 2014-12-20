<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
  	String total_code = request.getParameter("total_code");
  	if(total_code == null){
  		total_code = "default";
  	}
%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<html>
<head>
<title>rm-based architecture project</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<frameset cols="181,9,*" frameborder="NO" border="0"  framespacing="0" name="mainFrameSet" id="mainFrameSet">
	<frame src="<%=request.getContextPath()%>/jsp/funcTree.jsp?total_code=<%=total_code%>" scrolling="AUTO" name="leftFrame" />
	<frame src="frameMid.jsp" scrolling="NO" name="midFrame" />
	<frameset rows="*,0" frameborder="NO" framespacing="0" name="contentFrameSet" id="contentFrameSet">
	  	<frame src="<%=request.getContextPath()%>/jsp/container.jsp" scrolling="auto" name="contentFrame" />
	  	<frame src="info.jsp" scrolling="no" name="infoFrame" />
  	</frameset>
</frameset>
<noframes><body>
</body></noframes>
</html>
