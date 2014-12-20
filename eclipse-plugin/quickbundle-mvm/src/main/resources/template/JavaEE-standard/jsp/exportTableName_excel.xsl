<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@ page import="java.util.HashSet"%>
<xsl:value-of select="$charLt"/>%@ page import="java.util.Set"%>
<xsl:value-of select="$charLt"/>%@ page import="java.util.List, java.util.Iterator" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.Workbook" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.write.WritableWorkbook" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.write.WritableSheet" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.format.Alignment" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.write.Label" %>
<xsl:value-of select="$charLt"/>%@ page import="jxl.write.WritableCellFormat" %>
<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.base.web.page.RmPageVo"%>
<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.tools.helper.RmStringHelper"%>
<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.tools.helper.RmDateHelper"%>
<xsl:if test="column[@isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
</xsl:if>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.service.<xsl:value-of select="$tableFormatNameUpperFirst"/>Service" %>
<xsl:value-of select="$charLt"/>%
    <xsl:value-of select="$tableFormatNameUpperFirst"/>Service service = RmBeanFactory.getBean(<xsl:value-of select="$tableFormatNameUpperFirst"/>Service.class);
    List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> lResult = null;  //定义结果列表的List变量
    String queryCondition = request.getParameter("queryCondition");
    if(request.getParameterValues("export_all") == null) {
        int noFrom = Integer.parseInt(request.getParameter("no_from"));
        int noTo = Integer.parseInt(request.getParameter("no_to"));
        lResult = service.list(queryCondition, null, noFrom, noTo, true);  
    } else {
        lResult = service.list(queryCondition, null, -1, -1, true);
    }
    String check_all = request.getParameter("export_all");

    <xsl:value-of select="$TableNameVo"/> resultVo = null;  //定义一个临时的vo变量
    Set<xsl:value-of select="$charLt"/>String> sColumn = new HashSet<xsl:value-of select="$charLt"/>String>();
    String[] aColumn = request.getParameterValues("custom_column");
    for(int i=0; i<xsl:value-of select="$charLt"/>aColumn.length; i++) {
        sColumn.add(aColumn[i]);
    }
    try {
        response.setContentType("application/msexcel");
        response.setHeader("Content-disposition", "attachment; filename=" + RmDateHelper.getJoinedSysDateTime() + "export.xls");
        WritableWorkbook wbook = Workbook.createWorkbook(response.getOutputStream());  //建立excel文件
        WritableSheet wsheet = wbook.createSheet("第一页", 0); //sheet名称
        WritableCellFormat cellFormatNumber = new WritableCellFormat();
        cellFormatNumber.setAlignment(Alignment.RIGHT);
        
        int rowIndex = 0;
        int columnIndex = 0;
        wsheet.addCell(new Label(columnIndex, rowIndex, "dt"));
        wsheet.addCell(new Label(columnIndex, rowIndex+1, "sn"));
        wsheet.addCell(new Label(columnIndex ++, rowIndex+2, "序"));
<xsl:apply-templates mode="buildTableColumn_exportHead"/>
        wsheet.addCell(new Label(columnIndex++, rowIndex+2, "主键"));
        
        RmPageVo pageVo = new RmPageVo();
        if(pageContext.getRequest().getAttribute("RM_PAGE_VO") != null) {
            pageVo = (RmPageVo)pageContext.getRequest().getAttribute("RM_PAGE_VO");
        }
        int startIndex = (pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + 1;
        rowIndex = 2;
        //开始行循环
        for(Iterator<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> itLResult = lResult.iterator(); itLResult.hasNext(); ) {  //循环列
            rowIndex ++;
            columnIndex = 0;
            resultVo = itLResult.next();
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, (startIndex ++) + "" ));
<xsl:apply-templates mode="buildTableColumn_exportEnglishBody"/>
            wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>())));
        }
        wbook.write();
        if(wbook != null) {
            wbook.close();
        }
        out.clear();
        pageContext.pushBody();
    } catch(Exception e) {
        e.printStackTrace();
    }
%></xsl:template>

	<!--打印数据类型、英文、中文表头列表-->
	<xsl:template match="column" mode="buildTableColumn_exportHead">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
		if(sColumn.contains("<xsl:value-of select="$columnNameFormatLower"/>")) {
			<xsl:choose>
			<xsl:when test="@dataType='java.lang.String'">wsheet.addCell(new Label(columnIndex, rowIndex, ""));</xsl:when>
			<xsl:otherwise>wsheet.addCell(new Label(columnIndex, rowIndex, "<xsl:value-of select="@dataType"/>"));</xsl:otherwise>
			</xsl:choose>
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "<xsl:value-of select="$columnNameFormatLower"/>"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")));
		}<xsl:choose>
		<!--处理rm.dictionary.select checkbox(人性化展现方式)-->
		<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
		if(sColumn.contains("<xsl:value-of select="$columnNameFormatLower"/>_name")) {
			wsheet.addCell(new Label(columnIndex, rowIndex, ""));
			wsheet.addCell(new Label(columnIndex, rowIndex+1, "<xsl:value-of select="$columnNameFormatLower"/>_name"));
			wsheet.addCell(new Label(columnIndex ++, rowIndex+2, <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "_name"));
		}
		</xsl:when>
		<xsl:otherwise>
		</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	<!--打印表体列表-->
	<xsl:template match="column" mode="buildTableColumn_exportEnglishBody">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
			if(sColumn.contains("<xsl:value-of select="$columnNameFormatLower"/>")) {<xsl:choose>
				<!--处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())));
			}
			if(sColumn.contains("<xsl:value-of select="$columnNameFormatLower"/>_name")) {
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>, <xsl:choose><xsl:when test="@dataType='java.lang.Integer'">String.valueOf(resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())</xsl:when><xsl:otherwise>resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>()</xsl:otherwise></xsl:choose>)));
			</xsl:when>
				<!--处理时间参照-->
				<xsl:when test="@humanDisplayType='default' and (@dataType='java.sql.Timestamp' or @dataType='java.sql.Date')">
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>(), 19)));
			</xsl:when>
				<xsl:otherwise>
				wsheet.addCell(new Label(columnIndex ++ , rowIndex, RmStringHelper.prt(resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())));
		</xsl:otherwise>
		</xsl:choose>
			}
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>