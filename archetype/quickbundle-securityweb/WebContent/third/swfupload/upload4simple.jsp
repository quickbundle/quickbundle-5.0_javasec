<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%
String superLink = null;
String name = null;
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new java.io.File(this.getServletContext().getRealPath("/upload/swf"))));
	for(int i=1; i<aResult.length; i++) {
		String[] fileInfo = (String[])aResult[i];
		name = fileInfo[1];
		superLink = request.getContextPath() + "/upload/swf/" + fileInfo[0];
	}
} catch(Exception e) {
	e.printStackTrace();
}
%>

<a target="_blank" href="<%=superLink%>"><%=name%></a>