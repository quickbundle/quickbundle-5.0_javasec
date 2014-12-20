<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>rm upload</title>
</head>
<body class="uploadPage">
<form name="form" action="testCallUpload.jsp" method="post" enctype="multipart/form-data">
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
			<td width="25%"><input name="button_upload" class="button_ellipse" type="button" value="上传" onClickTo="javascript:upload_onClick()"></td>
		</tr>
		<tr>
			<td align="right">备注</td>
			<td colspan="3" align="left">
				<textarea name="upload_remark" class="textarea_limit_words" cols="60" rows="5" title="500" maxLength="500" id="remarkId"></textarea>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
<script type="text/javascript">
	function upload_onClick() {
		form.action = "testCallUpload.jsp";
		form.submit();
	}
	var myResultFile = new Array();
	if(window.parent != null && (typeof window.parent.onUploadDone) == "function") {
<%  //取出List
	String[] myResultFile = null;
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new java.io.File(this.getServletContext().getRealPath("bac"))).setSpecifyFileName("fdsafsd.jpg"));
} catch(Exception e) {
		e.printStackTrace();
		out.print("window.parent.onUploadDone(1, myResultFile, '上传文件失败，原因是：\n' + '" + e.getMessage() + "');");
}
%>
	}
</script>
