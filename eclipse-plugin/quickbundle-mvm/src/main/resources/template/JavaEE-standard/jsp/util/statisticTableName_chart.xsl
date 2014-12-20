<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@page contentType="text/html;charset=UTF-8"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.third.jfreechart.WebChart"%>
<xsl:value-of select="$charLt"/>%@page import="java.io.PrintWriter" %>
<xsl:value-of select="$charLt"/>%@page import="java.sql.ResultSet"%>
<xsl:value-of select="$charLt"/>%@page import="java.sql.SQLException"%>
<xsl:value-of select="$charLt"/>%@page import="java.util.Iterator"%>
<xsl:value-of select="$charLt"/>%@page import="java.util.List"%>
<xsl:value-of select="$charLt"/>%@page import="org.jfree.chart.urls.StandardCategoryURLGenerator"%>
<xsl:value-of select="$charLt"/>%@page import="org.jfree.data.category.CategoryDataset"%>
<xsl:value-of select="$charLt"/>%@page import="org.jfree.chart.urls.CategoryURLGenerator"%>
<xsl:value-of select="$charLt"/>%@page import="org.jfree.data.general.PieDataset"%>
<xsl:value-of select="$charLt"/>%@page import="org.jfree.chart.urls.PieURLGenerator"%>
<xsl:value-of select="$charLt"/>%@page import="org.springframework.jdbc.core.RowMapper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmProjectHelper"%>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>"%>
<xsl:value-of select="$charLt"/>%
    WebChart chart = new WebChart();
    List<xsl:value-of select="$charLt"/>String[]> lResult = RmProjectHelper.getCommonServiceInstance().query("select <xsl:value-of select="$statisticColumnFormatLower"/>  as rm_key, count(<xsl:value-of select="$statisticColumnFormatLower"/> ) as rm_count from <xsl:value-of select="@tableName"/> group by <xsl:value-of select="$statisticColumnFormatLower"/> ", new RowMapper() {
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            return new String[]{rs.getString("rm_key"), rs.getString("rm_count")};
        }
    });
    for(String[] array : lResult) {
        chart.setValue(array[0], Integer.parseInt(array[1]));
    }
    final String contextPath = request.getContextPath();
    //饼图的链接定制
    PieURLGenerator pug = new PieURLGenerator() {
        public String generateURL(PieDataset dataset, Comparable key, int pieIndex) {
            return contextPath + "/<xsl:value-of select="@tableDirName"/>?REQUEST_IS_READ_ONLY=1<xsl:value-of select="$charAmp"/><xsl:value-of select="$statisticColumnFormatLower"/> =" + key;
        }
    };
    //饼图2D
    String filename_pie = chart.generatePieChart("按" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$statisticColumnFormatLower"/> ") + "统计", session, new PrintWriter(out), pug, false); //如最后一位参数是true，则为3D饼图
    String graphURL_pie = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_pie;
    
    //柱图的链接定制
    CategoryURLGenerator cug = new CategoryURLGenerator() {
        public String generateURL(CategoryDataset dataset, int series, int category) {
            return contextPath + "/<xsl:value-of select="@tableDirName"/>?REQUEST_IS_READ_ONLY=1<xsl:value-of select="$charAmp"/><xsl:value-of select="$statisticColumnFormatLower"/> =" + dataset.getColumnKey(series);
        }
    };
    //柱图
    String filename_bar = chart.generateBarChart("按" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$statisticColumnFormatLower"/> ") + "统计", session, new PrintWriter(out), cug, true);
    String graphURL_bar = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_bar;
%>

<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title>rm-based architecture project<xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>p align="center">
    <xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=graphURL_pie %>" usemap="#<xsl:value-of select="$charLt"/>%=filename_pie %>">
<xsl:value-of select="$charLt"/>/p>
<xsl:value-of select="$charLt"/>p align="center">
    <xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=graphURL_bar %>" usemap="#<xsl:value-of select="$charLt"/>%=filename_bar %>">
<xsl:value-of select="$charLt"/>/p>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
</xsl:template>
</xsl:stylesheet>
