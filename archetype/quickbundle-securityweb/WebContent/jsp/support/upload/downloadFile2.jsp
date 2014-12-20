<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
	String save_name_in_war = request.getParameter("save_name_in_war");
	String file_name = request.getParameter("file_name");
	if(file_name == null || file_name.length() == 0) {
		file_name = save_name_in_war;
	}
	RmUploadHelper.download(request, response, save_name_in_war, file_name, "/");
%>
	
