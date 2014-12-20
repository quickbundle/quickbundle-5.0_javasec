<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>File Upload Example</title>
</head>
<body>
</body>
</html>
<script type="text/javascript">
	if(window.parent != null && (typeof window.parent.CKEDITOR.tools.callFunction) == "function") {
<%  //取出List
	String[] myResultImage = {"", ""};
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new java.io.File(this.getServletContext().getRealPath("/upload/ckeditor"))));
	for(int i=1; i<aResult.length; i++) {
		String[] fileInfo = (String[])aResult[i];
		if("".equals(myResultImage[0])) {
			myResultImage[0] = request.getContextPath() + "/upload/ckeditor/" + fileInfo[0];
			myResultImage[1] = fileInfo[1];
		}
	}
%>
		window.parent.CKEDITOR.tools.callFunction(1, '<%=myResultImage[0]%>', '');
		//window.parent.OnUploadCompleted(0, '<%=myResultImage[0]%>', '<%=RmStringHelper.encode2Encode(myResultImage[1], "GBK", "UTF-8")%>', '成功上传文件！');
<%
} catch(Exception e) {
	e.printStackTrace();
%>
		window.parent.CKEDITOR.tools.callFunction(1, '<%=myResultImage[0]%>', '<%=RmStringHelper.encode2Encode(myResultImage[1], "GBK", "UTF-8")%>, 上传文件失败！');
		//window.parent.OnUploadCompleted(1, '<%=myResultImage[0]%>', '<%=RmStringHelper.encode2Encode(myResultImage[1], "GBK", "UTF-8")%>', '上传文件失败！\n' + '<%=e.getMessage()%>');
<%
	e.printStackTrace();
}
%>
	}
</script>
