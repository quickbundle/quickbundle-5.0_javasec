<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.service.<xsl:value-of select="$tableFormatNameUpperFirst"/>Service"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@page import="java.io.File"%>
<xsl:value-of select="$charLt"/>%@page import="jxl.Workbook"%>
<xsl:value-of select="$charLt"/>%@page import="jxl.Sheet"%>
<xsl:value-of select="$charLt"/>%@page import="jxl.biff.WritableRecordData"%>
<xsl:value-of select="$charLt"/>%@page import="jxl.write.WritableWorkbook"%>
<xsl:value-of select="$charLt"/>%@page import="org.springframework.jdbc.core.RowMapper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.third.excel.ImportExcelVo"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmProjectHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.third.excel.RmExcelHandler"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.io.RmZipHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%
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
            rtVo = RmExcelHandler.getListDataFromSheet(f, <xsl:value-of select="$TableNameVo"/>.class.getName(), new RmExcelHandler.IValidateData() {
                public String isValid(Object obj) {
                    String errorMsg = "";
                    <xsl:value-of select="$TableNameVo"/> vo = (<xsl:value-of select="$TableNameVo"/>)obj;
<xsl:apply-templates mode="buildTableColumn_validateNotNull"/>
                    RmVoHelper.markCreateStamp(request2, vo);
                    return errorMsg;
                }
            });
            if(rtVo.getErrorMsg() == null) {
                <xsl:value-of select="$TableNameVo"/>[] diseaseVos = (<xsl:value-of select="$TableNameVo"/>[])rtVo.getLData().toArray(new <xsl:value-of select="$TableNameVo"/>[0]);
                <xsl:value-of select="$tableFormatNameUpperFirst"/>Service service = RmBeanFactory.getBean(<xsl:value-of select="$tableFormatNameUpperFirst"/>Service.class);
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
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>bean:message key="qb.web_title"/><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>script type="text/javascript">
    function import_onClick(){  //保存修改后的单条数据
        if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
            return false;
        }
        form.action="import<xsl:value-of select="$tableFormatNameUpperFirst"/>.jsp";
        form.submit();
    }
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>script type="text/javascript">
<xsl:value-of select="$charLt"/>%
    if(errorMsg != null <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> errorMsg.length() > 0) {
        out.print("alert('" + RmStringHelper.replaceStringToScript(errorMsg) + "')");
    }
%>
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>form name="form" method="post">
<xsl:value-of select="$charLt"/>br/>
<xsl:value-of select="$charLt"/>h3>批量导入<xsl:value-of select="$charLt"/>/h3>
<xsl:value-of select="$charLt"/>table class="table_div_content">
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right" width="20%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td width="35%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td align="right" width="20%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td width="25%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right">下载模板：<xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td>
            <xsl:value-of select="$charLt"/>a href="#"><xsl:value-of select="$charLt"/>u>批量导入<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY %>模板<xsl:value-of select="$charLt"/>/u><xsl:value-of select="$charLt"/>/a>
        <xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td colspan="4"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span>Excel文件(Zip或Excel格式)<xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td>
            <xsl:value-of select="$charLt"/>input type="text" class="text_field_reference_readonly" name="upload_oldName" validate="notNull;" inputName="Excel文件和图片(Zip格式)" value="" maxLength="200" hiddenInputId="upload_saveName,upload_isImage,upload_abbreviatoryName,upload_saveNameWidthHeight,upload_abbreviatoryNameWidthHeight,upload_remark"/><xsl:value-of select="$charLt"/>img class="refButtonClass" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/09.gif" onclick="javascript:getUploadWindow(new Array(upload_saveName, upload_oldName, upload_isImage, upload_abbreviatoryName, upload_saveNameWidthHeight, upload_abbreviatoryNameWidthHeight, upload_remark),'<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/', 800, 600, '1-1');"/>
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_saveName">
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_isImage">
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_abbreviatoryName">
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_saveNameWidthHeight">
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_abbreviatoryNameWidthHeight">
            <xsl:value-of select="$charLt"/>input type="hidden" name="upload_remark">
            <xsl:value-of select="$charLt"/>input name="button_save" class="button_ellipse" type="button" value="导入" onclickto="javascript:import_onClick()">
        <xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td colspan="4"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>%if(isSubmit) { %>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right">操作统计：<xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td colspan="3">本次操作您上传了<xsl:value-of select="$charLt"/>span class="emphasis_text"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>%=rtVo.getRecordSum()%><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/span>条数据，其中有<xsl:value-of select="$charLt"/>span class="emphasis_text"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>%=rtVo.getRecordSum()-rtVo.getLData().size()%><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/span>条错误数据，成功插入<xsl:value-of select="$charLt"/>span class="emphasis_text"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>%=insertSum%><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/span>条数据。<xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right">下载Excel：<xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td>
            <xsl:value-of select="$charLt"/>a href="<xsl:value-of select="$charLt"/>%=downloadUrl%>"><xsl:value-of select="$charLt"/>u><xsl:value-of select="$charLt"/>%if(downloadUrl != null){%>未导入的错误数据<xsl:value-of select="$charLt"/>% }%><xsl:value-of select="$charLt"/>/u><xsl:value-of select="$charLt"/>/a>
        <xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>%} %>
<xsl:value-of select="$charLt"/>/table>
            
<xsl:value-of select="$charLt"/>input type="hidden" name="isSubmit" value="1">

<xsl:value-of select="$charLt"/>/form>          
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
</xsl:template>

	<!--打印导入非空校验循环-->
	<xsl:template match="column" mode="buildTableColumn_validateNotNull">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:if test="@nullable='NO'">
			<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
				<xsl:choose>
					<!--处理java.sql.Timestamp, java.sql.Date, java.math.BigDecimal-->
					<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date' or @dataType='java.math.BigDecimal'">
	if(vo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>() == null ) { errorMsg += "<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>不能为空;"; }
				</xsl:when>
					<xsl:when test="@dataType='java.lang.Short' or @dataType='java.lang.Integer' or @dataType='java.lang.Long'">

				</xsl:when>
					<xsl:otherwise>
	if(vo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>().length() == 0 ) { errorMsg += "<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>不能为空;"; }
				</xsl:otherwise>
				</xsl:choose>
			</xsl:if>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
