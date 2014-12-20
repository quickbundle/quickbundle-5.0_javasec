<%@page contentType="text/html; charset=UTF-8" language="java"%><%
try {
	String str = "";
	if (request.getAttribute("org.apache.struts.action.EXCEPTION") != null) {
	    Exception e = (Exception) request.getAttribute("org.apache.struts.action.EXCEPTION");
	    str = e.toString();
	}
	Map result = new HashMap();
	result.put("error", str);
	out.print(RmObjectMapper.getInstance().writeValueAsString(result));
} catch(Exception e) {
	e.printStackTrace();
}
%><%@page import="org.quickbundle.project.serializer.RmObjectMapper"%><%@page import="java.util.HashMap"%><%@page import="java.util.Map"%>