<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>File Upload Example</title>
</head>
<body>
<%  //处理下载
	if(request.getParameter("downloadSaveName") != null) {
		RmUploadHelper.download(request, response, request.getParameter("downloadSaveName"), request.getParameter("downloadRealName"));
	}
%>
<form name="testForm" method="post" action="testUploadHelperTools.jsp" enctype="multipart/form-data">

<input type="file" name = "file1">
<br>
<input type="file" name = "file1">
<br>
<input type="file" name = "file1">
<br>
<input type="submit" name = "upload" value="上传">
<br>
<br>
<br>
<%  //取出List
	String[] myResultImage = {"", ""};
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request);
%>
您上传了<%=aResult.length%>个文件:<br>
<% 
	for(int j=1; j<aResult.length; j++) {
		String[] fileInfo = (String[]) aResult[j];
		if("".equals(myResultImage[0])) {
			myResultImage[0] = request.getContextPath() + "/" + RmUploadHelper.DEFAULT_UPLOAD_DIR + "/" + fileInfo[0];
			myResultImage[1] = fileInfo[1];
		}
		for(int i=0; i<fileInfo.length; i++) {
			if(i == 0 || i == 3) {
				out.print("<a target='_blank' href='" + request.getContextPath() + "/" + RmUploadHelper.DEFAULT_UPLOAD_DIR + "/" + fileInfo[i] + "'>" + fileInfo[1] + "</a>&nbsp;&nbsp;&nbsp;&nbsp;\n");
				out.print("<a href='testUploadHelperTools.jsp?downloadSaveName=" + fileInfo[i] + "&downloadRealName=" + fileInfo[1] + "' target='_blank'>点此下载</a><br>\n");
				if("1".equals(fileInfo[2])) {
					out.print("<img src='" + request.getContextPath() + "/" + RmUploadHelper.DEFAULT_UPLOAD_DIR + "/" + fileInfo[i] + "'/>\n");
				}
			}
		}
		out.print("<br>");
	}
} catch(Exception e) {
	//e.printStackTrace();
}
%>

<input type="hidden" name = "hiddenvalue" value="111">
<input type="checkbox" name="cb" id="cb1" value="1">
<input type="checkbox" name="cb" id="cb2" value="2">
<input type="checkbox" name="cb" id="cb3" value="3">
<input type="checkbox" name="cb" id="cb4" value="4">
</form>
</body>
</html>
<script type="text/javascript">
	if(window.parent != null && (typeof window.parent.OnUploadCompleted) == "function") {
		window.parent.OnUploadCompleted(0, '<%=myResultImage[1]%>', '<%=myResultImage[0]%>', '成功上传文件');
	}
</script>