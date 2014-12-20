<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.tools.helper.io.RmFileHelper"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%
	String uploadDir = request.getParameter("uploadDir");
	if(uploadDir == null || uploadDir.length() == 0) {
		uploadDir = RmUploadHelper.DEFAULT_UPLOAD_DIR;
	}
	String feedbackInfo = "";
	Object[] aResult = new Object[0];
	if(request.getParameter("to_delete_affix") != null && request.getParameter("to_delete_affix").trim().length() > 0) {
		String to_delete_affix = request.getParameter("to_delete_affix");
		if(RmFileHelper.delete(new File(this.getServletContext().getRealPath(uploadDir + "/" + to_delete_affix)))) {
			feedbackInfo = "彻底删除" + to_delete_affix;
		} else {
			feedbackInfo = "服务器未找到" + to_delete_affix + "，或删除失败";
		}
	} else if(request.getParameter("default_affix") != null && request.getParameter("default_affix").trim().length() > 0) {
		aResult = new Object[2];
		aResult[1] = new String[]{request.getParameter("default_affix"), request.getParameter("default_affix")}; //.replaceFirst("[^/]*?/", "")
	} else {
		aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new java.io.File(this.getServletContext().getRealPath(uploadDir))));
		if(aResult.length > 1){
			feedbackInfo = "上传成功";
		}
	}
%>

<%@page import="java.io.File"%><html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>rm upload</title>
</head>
<body class="uploadPage">
<form name="form" method="post" enctype="multipart/form-data">
<div align="left">
<%if(aResult.length > 1){%>
		<a target="_blank" href="<%=request.getContextPath() + "/" + uploadDir + "/" + ((String[])aResult[1])[0]%>"><%=((String[])aResult[1])[1]%></a>&nbsp;&nbsp;
		<a href="uploadSingle.jsp?to_delete_affix=<%=((String[])aResult[1])[0]%>&allow_type=<%=request.getParameter("allow_type") != null ? request.getParameter("allow_type") : "" %>">删除</a>
<%} else {%>
	<input type="file" name="file1" inputName="附件" validate="notNull" onChange="submit_onChange()"  value=""/>
<%}%>
<span class="font_remain_prompt"  id="remainPrompt_file1"></span>

<input type="hidden" name="affix_save_name" value="<%=aResult.length > 1 ? ((String[])aResult[1])[0] : "" %>"/>
<input type="hidden" name="affix_old_name" value="<%=aResult.length > 1 ? ((String[])aResult[1])[1] : "" %>"/>
<input type="hidden" name="allow_type" value="<%=request.getParameter("allow_type") != null ? request.getParameter("allow_type") : "" %>"/>
</div>
</form>
</body>
</html>
<script type="text/javascript">
	function submit_onChange() {
		var fileValue = form.file1.value;
		if(fileValue.lastIndexOf(".") < 0) {
			writeInfoToId("remainPrompt_file1", "要上传的文件要求有后缀名:" + form.allow_type.value);
			return;
		} else {
			fileValue = fileValue.substring(fileValue.lastIndexOf(".") + 1);
			if(form.allow_type.value != "" && form.allow_type.value.indexOf(fileValue) < 0) {
				writeInfoToId("remainPrompt_file1", "要上传的文件要求有后缀名:" + form.allow_type.value);
				return;
			}
		}
		form.action = "uploadSingle.jsp?allow_type=<%=request.getParameter("allow_type") != null ? request.getParameter("allow_type") : "" %>";
		form.submit();
	}
	writeInfoToId("remainPrompt_file1", "<%=feedbackInfo%>");
</script>
