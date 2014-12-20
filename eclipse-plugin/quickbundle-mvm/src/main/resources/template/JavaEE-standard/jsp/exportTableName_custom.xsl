<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@page import="java.util.Map"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.util.RmSequenceMap"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%  //取出List
    String queryCondition = session.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_QUERY_CONDITION).toString();
    Integer recordCount = Integer.parseInt(session.getAttribute("RECORD_COUNT").toString());
    session.removeAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_QUERY_CONDITION);
    session.removeAttribute("RECORD_COUNT");
%>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>bean:message key="qb.web_title"/><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>script type="text/javascript">
    function export_onClick(){  //导出Excel
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="$tableDirName"/>/export";
        clickAllSelectMultiple(form.custom_column);
        form.submit();
    }
    function checkExportNo() {
        if(form.export_all.checked) {
            document.getElementById("span_no").style.visibility = "hidden";
        } else {
            document.getElementById("span_no").style.visibility = "visible";
        }
    }
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>form name="form" method="post">
<xsl:value-of select="$charLt"/>br/>
<xsl:value-of select="$charLt"/>h3>定制导出字段<xsl:value-of select="$charLt"/>/h3>
<xsl:value-of select="$charLt"/>table class="table_div_content">
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td width="20%" align="right"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td width="35%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td width="15%" align="right"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td width="30%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>h4>选择导出数量：<xsl:value-of select="$charLt"/>/h4><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td colspan="3">全部导出<xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>input type="checkbox" name="export_all" onclick="checkExportNo();"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_export" value="导出" onclickto="javascript:export_onClick();"><xsl:value-of select="$charLt"/>br><xsl:value-of select="$charLt"/>br>
            <xsl:value-of select="$charLt"/>span id="span_no">只导出第<xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>input name="no_from" class="text_field_half" type="text" size="2" maxlength="10" value="<xsl:value-of select="$charLt"/>%=recordCount == 0 ? "" : "1"%>"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/>到<xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/>第<xsl:value-of select="$charLt"/>input name="no_to" class="text_field_half" type="text" size="2" maxlength="10" value="<xsl:value-of select="$charLt"/>%=recordCount >= 20 ? "20" : recordCount%>">（一共有<xsl:value-of select="$charLt"/>%=recordCount%>条数据）<xsl:value-of select="$charLt"/>/span>
        <xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>%
        Map<xsl:value-of select="$charLt"/>String, String> m = new RmSequenceMap<xsl:value-of select="$charLt"/>String, String>();
<xsl:apply-templates mode="buildTableColumn_exportMap"/>
    %>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>h4>选择字段：<xsl:value-of select="$charLt"/>/h4><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>td colspan="3"><xsl:value-of select="$charLt"/>br/>
            <xsl:value-of select="$charLt"/>table>
                <xsl:value-of select="$charLt"/>tr>
                    <xsl:value-of select="$charLt"/>td>
                        已选字段<xsl:value-of select="$charLt"/>br>
                        <xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectFieldMultiple("custom_column", -1, m, new String[0], " inputName='已选字段' style='height:400px' ", 0)%>                     
                    <xsl:value-of select="$charLt"/>/td>
                    <xsl:value-of select="$charLt"/>td>
                        <xsl:value-of select="$charLt"/>table border="0">
                            <xsl:value-of select="$charLt"/>tr><xsl:value-of select="$charLt"/>td>
                                <xsl:value-of select="$charLt"/>input name="moveToRight" class="button_ellipse" type="button" value="<xsl:value-of select="$charAmp"/>lt;<xsl:value-of select="$charAmp"/>lt;"  onclick="javascript:moveList_onClick(custom_column2, custom_column);" >     
                            <xsl:value-of select="$charLt"/>/td><xsl:value-of select="$charLt"/>/tr>
                            <xsl:value-of select="$charLt"/>tr><xsl:value-of select="$charLt"/>td>
                                <xsl:value-of select="$charLt"/>input name="moveToLeft" class="button_ellipse" type="button" value="<xsl:value-of select="$charAmp"/>gt;<xsl:value-of select="$charAmp"/>gt;"   onclick="javascript:moveList_onClick(custom_column, custom_column2);" >     
                            <xsl:value-of select="$charLt"/>/td><xsl:value-of select="$charLt"/>/tr>
                        <xsl:value-of select="$charLt"/>/table>
                    <xsl:value-of select="$charLt"/>/td>
                    <xsl:value-of select="$charLt"/>td>
                        不选字段<xsl:value-of select="$charLt"/>br>
                        <xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectFieldMultiple("custom_column2", -1, m, new String[0], " inputName='不选字段' style='height:400px' ", 1)%>                        
                    <xsl:value-of select="$charLt"/>/td>
                <xsl:value-of select="$charLt"/>/tr>
            <xsl:value-of select="$charLt"/>/table>
        <xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>/table>

<xsl:value-of select="$charLt"/>input type="hidden" name="queryCondition" value="<xsl:value-of select="$charLt"/>%=queryCondition%>">

<xsl:value-of select="$charLt"/>/form>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
</xsl:template>

	<!--打印Map循环-->
	<xsl:template match="column" mode="buildTableColumn_exportMap">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
		m.put("<xsl:value-of select="$columnNameFormatLower"/>", <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>"));
		<xsl:choose>
				<!--处理rm.dictionary.select or rm.dictionary.checkbox(人性化展现方式)-->
		<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">m.put("<xsl:value-of select="$columnNameFormatLower"/>_name", <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "_name");
				</xsl:when>
				<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
