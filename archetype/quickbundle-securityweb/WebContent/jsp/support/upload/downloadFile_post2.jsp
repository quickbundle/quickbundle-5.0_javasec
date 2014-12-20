<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>rm upload</title>
</head>
<body>
<form name="form" method="post" action="downloadFile_post3.jsp">

<input type="hidden" name="downloadSaveName" value="<%=request.getParameter("downloadSaveName")%>">
<input type="hidden" name="downloadRealName" value="<%=request.getParameter("downloadRealName")%>">
<input type="hidden" name="uploadDir" value="<%=(request.getParameter("uploadDir") != null) ? request.getParameter("uploadDir") : RmUploadHelper.DEFAULT_UPLOAD_DIR %>">
<input type="hidden" name="rmIsSelfSubmit" value="0">

</form>
</body>
</html>
<script type="text/javascript">
	form.submit();
</script>