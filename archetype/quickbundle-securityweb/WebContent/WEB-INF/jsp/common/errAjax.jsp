<%
try {
	Map<String, String> result = new HashMap<String, String>();
	result.put("error", exception.getMessage());
	out.print(RmObjectMapper.getInstance().writeValueAsString(result));
} catch(Exception e) {
	e.printStackTrace();
}
%><%@page import="java.util.Map"%><%@page import="org.quickbundle.project.serializer.RmObjectMapper"%><%@page import="java.util.HashMap"%><%@page contentType="text/html; charset=UTF-8" language="java"%><%@ page isErrorPage="true" %>