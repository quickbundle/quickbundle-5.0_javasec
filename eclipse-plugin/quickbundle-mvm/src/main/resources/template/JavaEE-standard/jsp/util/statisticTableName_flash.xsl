<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>script type="text/javascript" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/js/swfobject.js"><xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>script type="text/javascript">
    swfobject.embedSWF("<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/third/ofc/open-flash-chart.swf", "my_chart_pie", "400", "400", "9.0.0", 
        "expressInstall.swf", {"data-file":"<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/flash/data?type=pie"});
    swfobject.embedSWF("<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/third/ofc/open-flash-chart.swf", "my_chart_bar", "500", "400", "9.0.0", 
        "expressInstall.swf", {"data-file":"<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/flash/data?type=bar_3d"});
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>div align="center" style="padding:5px 0px 0px 3px">
    <xsl:value-of select="$charLt"/>div id="my_chart_pie"><xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>div align="center" style="padding:5px 0px 0px 3px">
    <xsl:value-of select="$charLt"/>div id="my_chart_bar"><xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
</xsl:template>
</xsl:stylesheet>
