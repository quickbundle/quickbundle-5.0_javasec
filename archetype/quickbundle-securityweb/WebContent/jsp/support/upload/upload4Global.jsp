<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>rm upload</title>
</head>
<body class="uploadPage">
<form name="form" method="post" enctype="multipart/form-data">
	<table align="center">
		<tr style="display:none">
			<td width="15%" align="right" nowrap><strong>基本信息</strong><br><br></td>
			<td width="45%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="25%" align="left">&nbsp;</td>
		</tr>
		<tr>
			<td width align="right">附件</td>
			<td width="45%" align="left">
				<input type="file" name="file1" inputName="附件" validate="notNull" value=""/><br><span class="font_remain_prompt"  id="remainPrompt_file1"></span>
			</td>
			<td width="15%" align="right"/>
			<td width="25%"><input name="button_upload" id="button_upload_id" class="button_ellipse" type="button" value="上传" onClickTo="javascript:upload_onClick()"></td>
		</tr>
		<tr>
			<td align="right">备注</td>
			<td colspan="3" align="left">
				<textarea name="upload_remark" class="textarea_limit_words" cols="60" rows="5" title="500" maxLength="500" id="remarkId"></textarea><br><span class="font_remain_prompt"  id="remainPrompt_remarkId"></span>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<%
	String uploadDir = request.getParameter("uploadDir");
	if(uploadDir == null || uploadDir.length() == 0) {
		uploadDir = RmUploadHelper.DEFAULT_UPLOAD_DIALOG_DIR;
	}
%>
<script type="text/javascript">
	function upload_onClick() {
		form.action = "upload4Global.jsp?uploadDir=<%=uploadDir%>";
		try {
			form.submit();
		} catch(e) {
			alert(e.message);
		}
	}
	var myResultFile = new Array();
	if(window.parent != null && (typeof window.parent.onUploadDone) == "function") {
<%  //取出List
	String[] myResultFile = null;
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new java.io.File(this.getServletContext().getRealPath(uploadDir))));
	for(int i=1; i<aResult.length; i++) {
		String[] fileInfo = (String[])aResult[i];
		if(myResultFile == null || myResultFile.length == 0 || myResultFile[0] == null || myResultFile[0].trim().length() == 0) {
			myResultFile = fileInfo;
			if(myResultFile.length >= 4 ) {
				myResultFile[3] = myResultFile[3];
			}
		}
	}
	if(myResultFile != null) {
		for(int j=0; j<myResultFile.length; j++) {
			out.println("myResultFile.push('" + myResultFile[j] + "');");
		}
		if(myResultFile.length < 6) {
			for(int k=0; k<6-myResultFile.length; k++) {
				out.println("myResultFile.push('');");  //补齐位置
			}
		}
	
		String formatUpload_remark = RmStringHelper.replaceStringToScript(((java.util.Map)aResult[0]).get("upload_remark"));
		
		formatUpload_remark = RmStringHelper.replaceStringByRule(formatUpload_remark, new String[][]{{",", "，"}});
		out.println("myResultFile.push('" + formatUpload_remark + "');");
		out.println("window.parent.onUploadDone(0, myResultFile);");
	}
} catch(Exception e) {
		e.printStackTrace();
		out.print("window.parent.onUploadDone(1, myResultFile, '上传文件失败，原因是：' + '" + e.getMessage() + "');");
}
%>
	}
</script>
