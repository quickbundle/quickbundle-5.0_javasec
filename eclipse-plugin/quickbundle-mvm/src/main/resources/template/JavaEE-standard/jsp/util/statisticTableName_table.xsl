<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.support.statistic.RmStatisticHandler"%>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%  //取出本条记录
    RmStatisticHandler sh = null;
    sh = (RmStatisticHandler)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_STATISTIC_HANDLER);  //从request中取出RmStatisticHandler, 赋值给sh
%>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>bean:message key="qb.web_title"/><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>script type="text/javascript">
    var rmActionName = "<xsl:value-of select="$tableFormatNameUpperFirst"/>Action";
    function print_onClick(){  //打印
        window.print();
    }
    function exportExcel_onClick(){  //导出到Excel
        if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
            return false;
        }
        form.target = "_blank";
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/table/export";
        form.submit();
    }  
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>form name="form" method="post">
<xsl:value-of select="$charLt"/>br>
<xsl:value-of select="$charLt"/>h3>统计<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY%><xsl:value-of select="$charLt"/>/h3>
<xsl:value-of select="$charLt"/>table class="table_div_content">
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td align="right">
            <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_print"  value="打印" onclick="javascript:print_onClick();" />
            <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_export" value="导出Excel" onclick="javascript:exportExcel_onClick();" />
            <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_back" value="返回"  onclick="javascript:history.go(h-1);" />
            <xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
    <xsl:value-of select="$charLt"/>tr>
        <xsl:value-of select="$charLt"/>td>
<xsl:value-of select="$charLt"/>%=sh.toHtml()%>
        <xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>/table>

<xsl:value-of select="$charLt"/>input type="hidden" name="queryCondition" value="">

<xsl:value-of select="$charLt"/>/form>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
<xsl:value-of select="$charLt"/>script type="text/javascript">
<xsl:value-of select="$charLt"/>%  //表单回写
    if(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的bean不为空
        out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
    }
%>
<xsl:value-of select="$charLt"/>/script>
</xsl:template>
</xsl:stylesheet>
