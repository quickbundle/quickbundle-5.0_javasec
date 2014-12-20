<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.Reader"%>
<%@page import="java.util.Iterator"%><html>
  <head>
  <%!public String getStrFromArray(String[] str) {
		String rtStr = "";
		if(str == null ) {
			return null;
		}
		for(int i=0; i<str.length; i++) {
			rtStr += str[i] + ",";
		}
		return rtStr;
	}%>

    
    <title>你好My JSP 'MyJsp.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  <body>
  
    <br><br>basePath=<%=basePath%>
    <br>request.getRemoteAddr()=<%=request.getRemoteAddr()%>
    <br>request.getRemoteHost()=<%=request.getRemoteHost()%>
    <br>request.getRemotePort()=<%=request.getRemotePort()%>
    <br>request.getRemoteUser()=<%=request.getRemoteUser()%>
	<br>
    <br>request.getRealPath("/")=<%=request.getSession().getServletContext().getRealPath("/")%>
    <br>this.getServletContext().getRealPath("/")=<%=this.getServletContext().getRealPath("/")%>
    <br>this.getServletConfig().getServletContext().getRealPath("/")=<%=this.getServletConfig().getServletContext().getRealPath("/")%>
    <br>application.getRealPath("/")=<%=application.getRealPath("/")%>
    <br>
	<br>application.getClass().getClassLoader().getResource(".").getPath()=<%=application.getClass().getClassLoader().getResource(".").getPath()%>
    <br>new java.io.File(".").getAbsoluteFile()=<%=new java.io.File(".").getAbsoluteFile()%>
    <br><br>
    <%
    out.print(System.getProperties().toString().replaceAll(", ", "<br>"));
    %>
    <br><br>Runtime.getRuntime().totalMemory()=<%=Runtime.getRuntime().totalMemory()%>
    <br>Runtime.getRuntime().freeMemory()=<%=Runtime.getRuntime().freeMemory()%>
    <br>Runtime.getRuntime().maxMemory()=<%=Runtime.getRuntime().maxMemory()%><br><br><br>
    <h1>System.getProperties()</h1>
	<%
		for(Iterator itSP = System.getProperties().keySet().iterator(); itSP.hasNext(); ) {
			String key = itSP.next().toString();
			Object value = System.getProperty(key);
	%>
		<br><%=key%>=<%=value%>
	<%
		}
	%>

  </body>
</html>
