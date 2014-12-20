<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%
	//超过maxFeedback后，不提示附件还剩多少了
	int maxFeedback = 100;
	int minFileNumber = 0;
	//默认上传的最大附件数量
	int maxFileNumber = 100;
	try {
		String fileNumberFromTo = request.getParameter("fileNumberFromTo");
		if(fileNumberFromTo != null && fileNumberFromTo.length() > 0) {
			if(fileNumberFromTo.indexOf("-") > -1) {
				if(fileNumberFromTo.indexOf("-") == 0 && fileNumberFromTo.length() > 1) {
					maxFileNumber = Integer.parseInt(fileNumberFromTo.substring(fileNumberFromTo.indexOf("-")+1, fileNumberFromTo.length()));
				} else if(fileNumberFromTo.indexOf("-") == (fileNumberFromTo.length()-1) && fileNumberFromTo.length() > 1) {
					minFileNumber = Integer.parseInt(fileNumberFromTo.substring(0, fileNumberFromTo.indexOf("-")));
				} else {
					minFileNumber = Integer.parseInt(fileNumberFromTo.substring(0, fileNumberFromTo.indexOf("-")));
					maxFileNumber = Integer.parseInt(fileNumberFromTo.substring(fileNumberFromTo.indexOf("-")+1, fileNumberFromTo.length()));
				}
			} else {
				minFileNumber = Integer.parseInt(fileNumberFromTo);
			}
		}
	} catch(Exception e) {
		e.printStackTrace();
	}
	String uploadDir = request.getParameter("uploadDir");
	if(uploadDir == null || uploadDir.length() == 0) {
		uploadDir = RmUploadHelper.DEFAULT_UPLOAD_DIALOG_DIR;
	}
	String openType = request.getParameter("openType");
	if(openType == null || openType.length() == 0) {
		openType = "WRITE";
	}
%>