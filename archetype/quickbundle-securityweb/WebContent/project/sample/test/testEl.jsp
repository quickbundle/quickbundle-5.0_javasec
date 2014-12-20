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

    
    <title>test el</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  <body>
  
   <p>pageContext=${pageContext}
   <p>param=${param}
   <p>paramValues=${paramValues}
   <p>header=${header}
   <p>headerValues${headerValues}
   <p>cookie=${cookie}
   <p>initParam=${initParam}
   <p>pageScope=${pageScope}
   <p>requestScope=${requestScope}
   <p>sessionScope=${sessionScope}
   <p>applicationScope=${applicationScope}
  </body>
</html>
