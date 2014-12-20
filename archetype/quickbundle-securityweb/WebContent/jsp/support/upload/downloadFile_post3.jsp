<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%  //
	if(request.getParameter("downloadSaveName") != null) {
		RmUploadHelper.download(request, response, request.getParameter("downloadSaveName"), request.getParameter("downloadRealName"), RmUploadHelper.DEFAULT_UPLOAD_DIALOG_DIR);
	}
%>
