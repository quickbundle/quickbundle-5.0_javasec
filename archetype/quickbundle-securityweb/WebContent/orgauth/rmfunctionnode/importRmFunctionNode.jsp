<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.io.File"%>
<%@page import="jxl.Workbook"%>
<%@page import="jxl.Sheet"%>
<%@page import="jxl.biff.WritableRecordData"%>
<%@page import="jxl.write.WritableWorkbook"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.third.excel.ImportExcelVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.third.excel.RmExcelHandler"%>
<%@page import="org.quickbundle.tools.helper.io.RmZipHelper"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.service.IRmFunctionNodeService" %>
<%
	boolean isSubmit = false;
	if("1".equals(request.getParameter("isSubmit"))) {
	    isSubmit = true;
	}
	ImportExcelVo rtVo = null;
	int insertSum = 0;
	String downloadUrl = null;
	String errorMsg = "";
	try {
		if(isSubmit) {
		    String upload_saveName = request.getParameter("upload_saveName");
		    upload_saveName = session.getServletContext().getRealPath("/") + RmUploadHelper.DEFAULT_UPLOAD_DIALOG_DIR + RmUploadHelper.SYSTEM_FILE_SEPARATOR + upload_saveName;
		    File zipFile = new File(upload_saveName);
			File f = RmZipHelper.getExcelFromFile(zipFile);
		    //处理Excel
		    final HttpServletRequest request2 = request;
		    rtVo = RmExcelHandler.getListDataFromSheet(f, RmFunctionNodeVo.class.getName(), new RmExcelHandler.IValidateData() {
		        public String isValid(Object obj) {
		            String errorMsg = "";
		            RmFunctionNodeVo vo = (RmFunctionNodeVo)obj;

	if(vo.getNode_type().length() == 0 ) { errorMsg += "Node_type不能为空;"; }
				
	if(vo.getFunction_property().length() == 0 ) { errorMsg += "Function_property不能为空;"; }
				
	if(vo.getName().length() == 0 ) { errorMsg += "Name不能为空;"; }
				
	if(vo.getEnable_status().length() == 0 ) { errorMsg += "Enable_status不能为空;"; }
				
		            RmVoHelper.markCreateStamp(request2, vo);
		            return errorMsg;
		        }
		    });
		    if(rtVo.getErrorMsg() == null) {
		        RmFunctionNodeVo[] diseaseVos = (RmFunctionNodeVo[])rtVo.getLData().toArray(new RmFunctionNodeVo[0]);
		        IRmFunctionNodeService service = (IRmFunctionNodeService)RmBeanFactory.getBean(IRmFunctionNodeConstants.SERVICE_KEY);
		        insertSum = service.insert(diseaseVos).length;
	    if(rtVo.getRecordSum()-rtVo.getLData().size() > 0) {
	        File errorExcel2 = new File(zipFile.getParent() + RmUploadHelper.SYSTEM_FILE_SEPARATOR + RmUploadHelper.getUniqueString() + rtVo.getErrorExcel().getName());
	        rtVo.getErrorExcel().renameTo(errorExcel2);
	        downloadUrl = request.getContextPath() + "/" + errorExcel2.toString().substring(session.getServletContext().getRealPath("/").length());
	    }
		    } else {
		        isSubmit = false;
		        errorMsg = rtVo.getErrorMsg();
		    }
		}
	} catch(Exception e) {
	    e.printStackTrace();
	    errorMsg = "发生了异常:" + e.getMessage() + "\n\n上传数据包不合要求，请确认为zip或者xls格式";
	    isSubmit = false;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
  	function import_onClick(){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="importRmFunctionNode.jsp";
    	form.submit();
	}
</script>
</head>
<body>
<script type="text/javascript">
<%
	if(errorMsg != null && errorMsg.length() > 0) {
	    out.print("alert('" + RmStringHelper.replaceStringToScript(errorMsg) + "')");
	}
%>
</script>
<form name="form" method="post">
<br/>
<h3>批量导入</h3>
<table class="table_div_content">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right">下载模板：</td>
		<td>
			<a href="#"><u>批量导入<%=IRmFunctionNodeConstants.TABLE_NAME_CHINESE %>模板</u></a>
		</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>Excel文件(Zip或Excel格式)</td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="upload_oldName" validate="notNull;" inputName="Excel文件和图片(Zip格式)" value="" maxLength="200" hiddenInputId="upload_saveName,upload_isImage,upload_abbreviatoryName,upload_saveNameWidthHeight,upload_abbreviatoryNameWidthHeight,upload_remark"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getUploadWindow(new Array(form.upload_saveName, form.upload_oldName, form.upload_isImage, form.upload_abbreviatoryName, form.upload_saveNameWidthHeight, form.upload_abbreviatoryNameWidthHeight, form.upload_remark),'<%=request.getContextPath()%>/', 800, 600, '1-1');"/>
			<input type="hidden" name="upload_saveName">
			<input type="hidden" name="upload_isImage">
			<input type="hidden" name="upload_abbreviatoryName">
			<input type="hidden" name="upload_saveNameWidthHeight">
			<input type="hidden" name="upload_abbreviatoryNameWidthHeight">
			<input type="hidden" name="upload_remark">
			<input name="button_save" class="button_ellipse" type="button" value="导入" onClickTo="javascript:import_onClick()">
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
<%if(isSubmit) { %>
	<tr>
		<td align="right">操作统计：</td>
		<td colspan="3">本次操作您上传了<span class="emphasis_text">&nbsp;<%=rtVo.getRecordSum()%>&nbsp;</span>条数据，其中有<span class="emphasis_text">&nbsp;<%=rtVo.getRecordSum()-rtVo.getLData().size()%>&nbsp;</span>条错误数据，成功插入<span class="emphasis_text">&nbsp;<%=insertSum%>&nbsp;</span>条数据。</td>
	</tr>
	<tr>
		<td align="right">下载Excel：</td>
		<td>
			<a href="<%=downloadUrl%>"><u><%if(downloadUrl != null){%>未导入的错误数据<% }%></u></a>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
<%} %>
</table>
            
<input type="hidden" name="isSubmit" value="1">

</form>			
</body>
</html>