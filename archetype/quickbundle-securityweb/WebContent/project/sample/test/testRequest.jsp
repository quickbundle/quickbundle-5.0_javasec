<%@ page contentType="text/html; charset=UTF-8" session="false" language="java" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="org.quickbundle.tools.support.path.RmPathHelper"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.Reader"%>
<%@page import="javax.servlet.jsp.tagext.TryCatchFinally"%>


<%@page import="java.util.Iterator"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.net.Inet4Address"%><html>
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
<%
System.out.println("request.getHeader:");
Enumeration enume = request.getHeaderNames();
for(Object obj; enume.hasMoreElements(); ) {
	obj = enume.nextElement();
	System.out.println(obj + ":" + request.getHeader(obj.toString()));
}

System.out.println("request.getAttribute:");
enume = request.getAttributeNames(); 
for(Object obj; enume.hasMoreElements(); ) {
	obj = enume.nextElement();
	System.out.println(obj + ":" + request.getAttribute(obj.toString()));
}
%>
	<br>request.getHeader("Cookie")=<%=request.getHeader("Cookie")%>
	<br>request.getSession(false)=<%request.getSession(false);%>
    <br>basePath=<%=basePath%>
    <br>request.getRemoteAddr()=<%=request.getRemoteAddr()%>
    <br>request.getRemoteHost()=<%=request.getRemoteHost()%>
    <br>request.getRemotePort()=<%=request.getRemotePort()%>
    <br>request.getRemoteUser()=<%=request.getRemoteUser()%>
    <br>RmProjectHelper.getIp(request)=<%=RmProjectHelper.getIp(request)%>
    <br>Inet4Address.getLocalHost().getHostAddress()=<%=Inet4Address.getLocalHost().getHostAddress()%>
	<br>
    <br>request.getRealPath("/")=<%=request.getSession().getServletContext().getRealPath("/")%>
    <br>this.getServletContext().getRealPath("/")=<%=this.getServletContext().getRealPath("/")%>
    <br>this.getServletConfig().getServletContext().getRealPath("/")=<%=this.getServletConfig().getServletContext().getRealPath("/")%>
    <br>application.getRealPath("/")=<%=application.getRealPath("/")%>
    <br>
	<br>application.getClass().getClassLoader().getResource(".").getPath()=<%=application.getClass().getClassLoader().getResource(".").getPath()%>
    <br>new java.io.File(".").getAbsoluteFile()=<%=new java.io.File(".").getAbsoluteFile()%>
    <br>RmPathHelper.getWarDir()=<%=RmPathHelper.getWarDir()%>
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
	HttpSession session = request.getSession(false);
	if(session != null) {
		for(Enumeration en = session.getAttributeNames(); en.hasMoreElements();) {
			String key = en.nextElement().toString();
			System.out.println(key + "=" + session.getAttribute(key));
		}
	}
	%>

  </body>
</html>
