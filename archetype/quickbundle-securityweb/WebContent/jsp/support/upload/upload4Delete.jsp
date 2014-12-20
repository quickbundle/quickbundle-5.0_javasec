<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.tools.helper.io.RmFileHelper"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%
	String uploadDir = request.getParameter("uploadDir");
	if(uploadDir == null || uploadDir.length() == 0) {
		uploadDir = RmUploadHelper.DEFAULT_UPLOAD_DIALOG_DIR;
	}
%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<html>
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>rm upload</title>
</head>
<body>
<form name="form" action="upload4Delete.jsp?uploadDir=<%=uploadDir%>" method="post">
	<input type="text" name="toDeleteFile" value="">
</form>
</body>
</html>

<script type="text/javascript">
	var alertStr = "删除了以下文件:";
<%
	int index = 0;
	String[] aToDelete = RmJspHelper.getArrayFromRequest(request, "toDeleteFile");
	for(int i=0; i<aToDelete.length; i++) {
		java.io.File thisFile = new java.io.File(this.getServletContext().getRealPath(uploadDir) + RmUploadHelper.SYSTEM_FILE_SEPARATOR + aToDelete[i]);
		if(RmFileHelper.delete(thisFile)) {
			index ++;
			out.println("    alertStr += '\\n" + thisFile.getName() + "';");
		} else {
			//System.out.println("  thisFile.exists()?" + thisFile.exists() + "  thisFile.canWrite()?" + thisFile.canWrite() + "  thisFile.delete()?" + thisFile.delete() + "  thisFile.rename(" + thisFile.toString() + ".garbage" + ")?" + thisFile.renameTo(new java.io.File(thisFile.toString() + ".garbage")));
			out.println("    alertStr += '\\n" + thisFile + " delete fail: exists()?" + thisFile.exists() + " canWrite()?" + thisFile.canWrite() + "';");
		}
	}
	if(index > 0) {
		out.println("        alert(alertStr);");
	} else if(aToDelete.length > 0) {
		out.println("        alert('服务器并没有找到要删除的文件!');");
	}
%>
</script>