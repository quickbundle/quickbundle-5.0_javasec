<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%
  	String total_code = request.getParameter("total_code");
  	if(total_code == null){
  		total_code = "101101";
  	}
%>
<title>rm-based architecture project</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<frameset rows="100,*" cols="*" frameborder="yes" framespacing="0">
  <frame src="<%=request.getContextPath()%>/jsp/include/header.jsp?total_code=<%=total_code%>" naame="top" scrolling="No" noresize="noresize"  />
  <frameset id="mains" rows="*" cols="180,*" framespacing="0" frameborder="no" border="0">
    <frame src="<%=request.getContextPath()%>/jsp/leftMenu.jsp?total_code=<%=total_code%>" name="left" scrolling="No" noresize="noresize" id="left" title="left" />
    <frame src="<%=request.getContextPath()%>/jsp/main.jsp?total_code=<%=total_code%>" name="contentFrame" scrolling="auto" />
  </frameset>
</frameset>


</html>
