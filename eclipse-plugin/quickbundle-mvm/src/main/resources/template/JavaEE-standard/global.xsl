<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:str="http://www.quickbundle.org" version="2.0" exclude-result-prefixes="str">
	<!--导入全局定义-->
	<xsl:import href="../publicRoot.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="xml" omit-xml-declaration="no" encoding="UTF-8"/>
	<!--定义/project中的全局变量-->
	<xsl:variable name="webAppName" select="/meta/project/webAppName"/>
	<xsl:variable name="authorName" select="/meta/project/authorName"/>
	<xsl:variable name="javaPackageName" select="/meta/project/javaPackageName"/>
	<xsl:variable name="jspSourcePath" select="/meta/project/jspSourcePath"/>
	<!--定义java文件的类名-->
	<xsl:variable name="ITableNameConstants" select="concat('I', $tableFormatNameUpperFirst, 'Constants')"/>
	<xsl:variable name="TableNameVo" select="concat($tableFormatNameUpperFirst, 'Vo')"/>
	<!--定义包名/目录名-->
	<xsl:variable name="javaPackageTableDir" select="concat($javaPackageName, '.', $tableDirName)"/>
	<xsl:variable name="jspSourceTableDir"><xsl:value-of select="$jspSourcePath"/><xsl:if test="not($jspSourcePath='')">/</xsl:if><xsl:value-of select="$tableDirName"/></xsl:variable>
	<!--自定义函数，获得某个Java文件的非JavaDoc注释，调用java文件或concat字符串实现-->
	<xsl:function name="str:getJavaFileComment">
		<xsl:param name="authorNameVar" as="xs:string"/>
<xsl:text>/*
 * 功能描述:
 * 版本历史: </xsl:text><xsl:value-of select="format-dateTime(current-dateTime(),'[Y0001]-[M01]-[D01] [H01]:[m01]:[s01]')"/><xsl:text>, 创建1.0.0版 (</xsl:text><xsl:value-of select="$authorNameVar"/>)<xsl:text>
 */</xsl:text>
	</xsl:function>
	<!--自定义函数，获得某个Class的JavaDoc注释，调用java文件或concat字符串实现-->
	<xsl:function name="str:getClassComment">
		<xsl:param name="authorNameVar" as="xs:string"/>
<xsl:text>/**
 * 功能、现存BUG:
 *
 * @author </xsl:text><xsl:value-of select="$authorNameVar"/><xsl:text>
 */</xsl:text>
	</xsl:function>
	<!--开始各种可复用的列循环处理-->
	<!--处理各列的循环新增输入框，用于insertTableName.jsp-->
	<xsl:template match="column" mode="buildTableColumn_insertInput">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true' and not(@humanDisplayType='default' and @dataType='java.lang.String' and (../column[$columnName=concat(@columnName, '_name')]/@humanDisplayType='rm.listReference' or ../column[$columnName=concat(@columnName, '_NAME')]/@humanDisplayType='rm.listReference' or ../column[$columnName=concat(@columnName, '_name')]/@humanDisplayType='rm.orgReference' or ../column[$columnName=concat(@columnName, '_NAME')]/@humanDisplayType='rm.orgReference'))">
			<xsl:choose>
				<!--首先处理rm.dictionary.select(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectField("<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>), "", "inputName='" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "' <xsl:if test="@nullable='NO'">validate='notNull;'</xsl:if>", true) %>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--首先处理rm.dictionary.checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.checkbox'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="checkbox" class="rm_checkbox" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_rmCheckbox"/><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.fckEditor(html编辑器)-->
				<xsl:when test="@humanDisplayType='rm.fckEditor'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3">
			<xsl:value-of select="$charLt"/>textarea class="textarea_fckEditor" cols="60" rows="5" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" maxLength="<xsl:if test="@maxLength div 2 >= 1">
						<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
					</xsl:if>
					<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>><xsl:value-of select="$charLt"/>/textarea>
		<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.listReference(列表参照)-->
				<xsl:when test="@humanDisplayType='rm.listReference'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="text" class="text_field_reference" <xsl:if test="@nullable='NO'">validate='notNull;'</xsl:if> hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"><xsl:value-of select="$charLt"/>img class="refButtonClass" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/09.gif" onclick="javascript:getReference(new Array(form.<xsl:value-of select="$columnNameFormatLower"/>, form.<xsl:value-of select="$columnNameFormatLower"/>_name), '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/', '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="$humanDisplayTypeKeyword"/>/reference?referenceInputType=radio');"/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.orgReference(组织结构参照)-->
				<xsl:when test="@humanDisplayType='rm.orgReference'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="text" class="text_field_reference" <xsl:if test="@nullable='NO'">validate='notNull;'</xsl:if> hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"><xsl:value-of select="$charLt"/>img class="refButtonClass" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/09.gif" onclick="javascript:getPartyWindow(new Array(form.<xsl:value-of select="$columnNameFormatLower"/>, form.<xsl:value-of select="$columnNameFormatLower"/>_name), '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/', '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/ut/orgauth/tree/org.jsp?enableCookie=true<xsl:value-of select="$charAmp"/>inputType=radio');"/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.affix(多附件上传)-->
				<xsl:when test="@humanDisplayType='rm.affix'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3">
			<xsl:value-of select="$charLt"/>input type="text" class="rm_affix" bs_keyword="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%>" record_id="<xsl:value-of select="$charLt"/>%=isModify ? resultVo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>() : ""%>" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="false" maxLength="<xsl:if test="@maxLength div 2 >= 1">
						<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
					</xsl:if>
					<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>" />
		<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理default(默认)-->
				<xsl:when test="@humanDisplayType='default' or @humanDisplayType=''">
					<xsl:choose>
						<!--处理textarea，大于1000个字符-->
						<xsl:when test="@dataType='java.lang.String' and @maxLength&gt;=1000">
							<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
								<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
							<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3">
			<xsl:value-of select="$charLt"/>textarea class="textarea_limit_words" cols="60" rows="5" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" maxLength="<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>><xsl:value-of select="@defaultValue"/>
							<xsl:value-of select="$charLt"/>/textarea>
		<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
						<!--处理时间参照-->
						<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date'">
							<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
								<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
							<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="text" class="text_date" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
						<!--处理数字-->
						<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
							<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
								<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
							<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="text" class="text_field" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="<xsl:value-of select="@defaultValue"/>" integerDigits="<xsl:value-of select="@maxLength"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
						<!--默认其它类型的字段。处理普通的text，小于1000个字符-->
						<xsl:otherwise>
							<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
								<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
							<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>
			<xsl:value-of select="$charLt"/>input type="text" class="text_field" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="<xsl:value-of select="@defaultValue"/>" maxLength="<xsl:if test="@maxLength div 2 >= 1">
								<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
							</xsl:if>
							<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>/>
		<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<!--新版insertTableXslt-->
	<xsl:template match="column" mode="buildTableColumn_newinsertInput">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true' and not(@humanDisplayType='default' and @dataType='java.lang.String' and (../column[$columnName=concat(@columnName, '_name')]/@humanDisplayType='rm.listReference' or ../column[$columnName=concat(@columnName, '_NAME')]/@humanDisplayType='rm.listReference' or ../column[$columnName=concat(@columnName, '_name')]/@humanDisplayType='rm.orgReference' or ../column[$columnName=concat(@columnName, '_NAME')]/@humanDisplayType='rm.orgReference'))">
			<xsl:choose>
				<!--首先处理rm.dictionary.select(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select'">
				
				<xsl:value-of select="$charLt"/>div class="input-prepend">
				<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
				<xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectField("<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>), "", "inputName='" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "'", true) %>
				<xsl:value-of select="$charLt"/>/div>
				</xsl:when>
				<!--首先处理rm.dictionary.checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.checkbox'">
				<xsl:value-of select="$charLt"/>div class="input-prepend">
				<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
				<xsl:value-of select="$charLt"/>input type="checkbox" class="rm_checkbox" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_rmCheckbox"/><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"/>
				<xsl:value-of select="$charLt"/>/div>
				</xsl:when>
				<!--处理rm.fckEditor(html编辑器)-->
				<xsl:when test="@humanDisplayType='rm.fckEditor'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:if test="@nullable='NO'">
						<xsl:value-of select="$charLt"/>span class="style_required_red">* <xsl:value-of select="$charLt"/>/span></xsl:if>
					<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3">
			<xsl:value-of select="$charLt"/>textarea class="textarea_fckEditor" cols="60" rows="5" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" maxLength="<xsl:if test="@maxLength div 2 >= 1">
						<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
					</xsl:if>
					<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>" <xsl:if test="@nullable='NO'">validate="notNull;"</xsl:if>><xsl:value-of select="$charLt"/>/textarea>
		<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.listReference(列表参照)-->
				<xsl:when test="@humanDisplayType='rm.listReference'">
					<xsl:value-of select="$charLt"/>div class="input-prepend">
					<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span>
			<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" <xsl:if test="@nullable='NO'">validate='notNull;'</xsl:if> hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>">
	<xsl:value-of select="$charLt"/>/div>
	</xsl:when>
				<!--处理rm.orgReference(组织结构参照)-->
				<xsl:when test="@humanDisplayType='rm.orgReference'">
					<xsl:value-of select="$charLt"/>div class="input-prepend">
		<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span>
			<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" <xsl:if test="@nullable='NO'">validate='notNull;'</xsl:if> hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>">
	<xsl:value-of select="$charLt"/>/div>
	</xsl:when>
				<!--处理rm.affix(多附件上传)-->
				<xsl:when test="@humanDisplayType='rm.affix'">
					<xsl:value-of select="$charLt"/>div class="input-prepend">
						<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span>
			<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" bs_keyword="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%>" record_id="<xsl:value-of select="$charLt"/>%=isModify ? resultVo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>() : ""%>" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="false" maxLength="<xsl:if test="@maxLength div 2 >= 1">
						<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
					</xsl:if>
					<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>" />
	<xsl:value-of select="$charLt"/>/div>
	</xsl:when>
				<!--处理default(默认)-->
				<xsl:when test="@humanDisplayType='default' or @humanDisplayType=''">
					<xsl:choose>
						<!--处理textarea，大于1000个字符-->
						<xsl:when test="@dataType='java.lang.String' and @maxLength&gt;=1000">
							<xsl:value-of select="$charLt"/>div class="input-prepend">
										<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
										<xsl:value-of select="$charLt"/>input class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" type="text" maxLength="<xsl:if test="@maxLength div 2 >= 1">
							 <xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
							</xsl:if>
							<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>"/>
							<xsl:value-of select="$charLt"/>/div>
						</xsl:when>
						<!--处理时间参照-->
						<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date'">
							<xsl:value-of select="$charLt"/>div class="input-prepend">
												<xsl:value-of select="$charLt"/>div class="controls">
														<xsl:value-of select="$charLt"/>span class="add-on">时间<xsl:value-of select="$charLt"/>/span> <xsl:value-of select="$charLt"/>input name="<xsl:value-of select="$columnNameFormatLower"/>" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" />
												<xsl:value-of select="$charLt"/>/div>
										<xsl:value-of select="$charLt"/>/div>
							</xsl:when>
						<!--处理数字-->
						<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
							<xsl:value-of select="$charLt"/>div class="input-prepend">
							<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span>
							<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" columnSize="<xsl:value-of select="@columnSize"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" /><xsl:value-of select="$charNbsp"/>到<xsl:value-of select="$charNbsp"/>
						<xsl:value-of select="$charLt"/>/div>
						</xsl:when>
						<!--默认其它类型的字段。处理普通的text，小于1000个字符-->
						<xsl:otherwise>
							<xsl:value-of select="$charLt"/>div class="input-prepend">
										<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
										<xsl:value-of select="$charLt"/>input class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" type="text" maxLength="<xsl:if test="@maxLength div 2 >= 1">
							 <xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
							</xsl:if>
							<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>"/>
							<xsl:value-of select="$charLt"/>/div>
					</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<!--新版ajax更新-->
	<xsl:template match="column" mode="buildTableColumn_queryInput_neweditajax">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="$columnName=$tablePk">
			$("#portlet-config-edit").find("input[id=<xsl:value-of select="$columnNameFormatLower"/>]").val(res.<xsl:value-of select="$columnNameFormatLower"/>);
		</xsl:if>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
			$("#portlet-config-edit").find("input[id=<xsl:value-of select="$columnNameFormatLower"/>]").val(res.<xsl:value-of select="$columnNameFormatLower"/>);
		</xsl:if>
	</xsl:template>
	
	<!--新版修改-->
	<xsl:template match="column" mode="buildTableColumn_queryInput_newupdate">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
		<xsl:value-of select="$charLt"/>div class="input-prepend">
									<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> <xsl:value-of select="$charLt"/>input class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" id="<xsl:value-of select="$columnNameFormatLower"/>" type="text"  />
								<xsl:value-of select="$charLt"/>/div>
		</xsl:if>
	</xsl:template>
	
	<!--新版列表第一段-->
	<xsl:template match="column" mode="buildTableColumn_queryInput_newlisttr">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
                                                                    <xsl:value-of select="$charLt"/>th><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/th>
		</xsl:if>
	</xsl:template>
	<!--新版列表第二段-->
	<xsl:template match="column" mode="buildTableColumn_queryInput_newlisttr2">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
						<xsl:if test="$columnName=$tablePk">
						<xsl:value-of select="$charLt"/>td>
								<xsl:value-of select="$charLt"/>div class="checkbox">
										<xsl:value-of select="$charLt"/>ins id="ischeck">
													<xsl:value-of select="$charLt"/>input type="hidden" class="checkmain" ischecked="false" value="<xsl:value-of select="$charLt"/>c:out value="${list.<xsl:value-of select="$tablePkFormatLower"/>}"><xsl:value-of select="$charLt"/>/c:out>" />
															<xsl:value-of select="$charLt"/>/ins>
														<xsl:value-of select="$charLt"/>/div>
													<xsl:value-of select="$charLt"/>/td>
						</xsl:if>
		
										<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
																				<xsl:value-of select="$charLt"/>td align="center"><xsl:value-of select="$charLt"/>c:out value="${list.<xsl:value-of select="$columnNameFormatLower"/>}"><xsl:value-of select="$charLt"/>/c:out><xsl:value-of select="$charLt"/>/td>
										</xsl:if>
		</xsl:template>
	
	
	
	
	<!--新版处理各列的循环查询输入框-->
	<xsl:template match="column" mode="buildTableColumn_queryInput_newversion">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
				<xsl:choose>
				<!--首先处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select'  or @humanDisplayType='rm.dictionary.checkbox'">
				<xsl:value-of select="$charLt"/>div class="input-prepend">
				<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
				<xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectField("<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>), "", "inputName='" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "'", true) %>
				<xsl:value-of select="$charLt"/>/div>
			</xsl:when>
				<!--处理rm.listReference(列表参照)-->
				<xsl:when test="@humanDisplayType='rm.listReference'">
				<xsl:value-of select="$charLt"/>div class="input-prepend">
				<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
					<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>">
				<xsl:value-of select="$charLt"/>/div>
			</xsl:when>
				<!--处理rm.orgReference(组织结构参照)-->
				<xsl:when test="@humanDisplayType='rm.orgReference'">
				<xsl:value-of select="$charLt"/>div class="input-prepend">
					<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>">
			<xsl:value-of select="$charLt"/>/div>
			</xsl:when>
				<!--处理default(默认)-->
				<xsl:when test="@humanDisplayType='default' or @humanDisplayType='rm.fckEditor' or @humanDisplayType=''">
					<!--格式化choose-->
					<xsl:choose>
						<!--处理时间参照-->
						<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date'">
										<xsl:value-of select="$charLt"/>div class="input-prepend">
												<xsl:value-of select="$charLt"/>div class="controls">
													<xsl:value-of select="$charLt"/>div class="controls">
														<xsl:value-of select="$charLt"/>span class="add-on">时间<xsl:value-of select="$charLt"/>/span> <xsl:value-of select="$charLt"/>input name="<xsl:value-of select="$columnNameFormatLower"/>_from" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" />
													<xsl:value-of select="$charLt"/>/div>
											<xsl:value-of select="$charLt"/>/div>
										<xsl:value-of select="$charLt"/>/div>到<xsl:value-of select="$charNbsp"/>
										<xsl:value-of select="$charLt"/>div class="input-prepend">
												<xsl:value-of select="$charLt"/>div class="controls">
													<xsl:value-of select="$charLt"/>div class="controls">
														<xsl:value-of select="$charLt"/>span class="add-on">时间<xsl:value-of select="$charLt"/>/span> 
														<xsl:value-of select="$charLt"/>input name="<xsl:value-of select="$columnNameFormatLower"/>_to" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" />
													<xsl:value-of select="$charLt"/>/div>
											<xsl:value-of select="$charLt"/>/div>
										<xsl:value-of select="$charLt"/>/div>
						</xsl:when>
						<!--处理数字-->
						<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
						<xsl:value-of select="$charLt"/>div class="input-prepend">
							<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span>
							<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>_from" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" columnSize="<xsl:value-of select="@columnSize"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" />
							<xsl:value-of select="$charLt"/>/div>
							<xsl:value-of select="$charNbsp"/>到<xsl:value-of select="$charNbsp"/>
							<xsl:value-of select="$charLt"/>div class="input-prepend">
							<xsl:value-of select="$charLt"/>input type="text" class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>_to" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" columnSize="<xsl:value-of select="@columnSize"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" />
						<xsl:value-of select="$charLt"/>/div>
						</xsl:when>
						<!--其他字段都按此处理。处理普通的text，小于1000个字符-->
						<xsl:otherwise>
									<xsl:value-of select="$charLt"/>div class="input-prepend">
										<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
										<xsl:value-of select="$charLt"/>input class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" type="text" maxLength="<xsl:if test="@maxLength div 2 >= 1">
							 <xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
							</xsl:if>
							<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>"/>
							<xsl:value-of select="$charLt"/>/div>
						</xsl:otherwise>
						</xsl:choose>
						</xsl:when>
						</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	<!--处理各列的循环查询输入框-->
	<xsl:template match="column" mode="buildTableColumn_queryInput">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
			<xsl:value-of select="$charLt"/>tr>
			<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/td>
			<xsl:value-of select="$charLt"/>td>
				<xsl:choose>
				<!--首先处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select'  or @humanDisplayType='rm.dictionary.checkbox'">
					<xsl:value-of select="$charLt"/>%=RmJspHelper.getSelectField("<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>), "", "inputName='" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>") + "'", true) %>
			</xsl:when>
				<!--处理rm.listReference(列表参照)-->
				<xsl:when test="@humanDisplayType='rm.listReference'">
					<xsl:value-of select="$charLt"/>input type="text" class="text_field_reference" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"><xsl:value-of select="$charLt"/>img class="refButtonClass" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/09.gif" onclick="javascript:getReference(new Array(form.<xsl:value-of select="$columnNameFormatLower"/>, form.<xsl:value-of select="$columnNameFormatLower"/>_name), '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/', '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="$humanDisplayTypeKeyword"/>/reference?referenceInputType=radio');"/>
			</xsl:when>
				<!--处理rm.orgReference(组织结构参照)-->
				<xsl:when test="@humanDisplayType='rm.orgReference'">
					<xsl:value-of select="$charLt"/>input type="text" class="text_field_reference" hiddenInputId="<xsl:value-of select="$columnNameFormatLower"/>" name="<xsl:value-of select="$columnNameFormatLower"/>_name" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" /><xsl:value-of select="$charLt"/>input type="hidden" name="<xsl:value-of select="$columnNameFormatLower"/>"><xsl:value-of select="$charLt"/>img class="refButtonClass" src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/09.gif" onclick="javascript:getPartyWindow(new Array(form.<xsl:value-of select="$columnNameFormatLower"/>, form.<xsl:value-of select="$columnNameFormatLower"/>_name), '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/', '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/ut/orgauth/tree/org.jsp?enableCookie=true<xsl:value-of select="$charAmp"/>inputType=radio');"/>
			</xsl:when>
				<!--处理default(默认)-->
				<xsl:when test="@humanDisplayType='default' or @humanDisplayType='rm.fckEditor' or @humanDisplayType=''">
					<!--格式化choose-->
					<xsl:choose>
						<!--处理时间参照-->
						<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date'">
							<xsl:value-of select="$charLt"/>input type="text" class="text_date_half" name="<xsl:value-of select="$columnNameFormatLower"/>_from" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>"/><xsl:value-of select="$charNbsp"/>到<xsl:value-of select="$charNbsp"/>
							<xsl:value-of select="$charLt"/>input type="text" class="text_date_half" name="<xsl:value-of select="$columnNameFormatLower"/>_to" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>"/>
			</xsl:when>
						<!--处理数字-->
						<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
							<xsl:value-of select="$charLt"/>input type="text" class="text_field_half" name="<xsl:value-of select="$columnNameFormatLower"/>_from" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" columnSize="<xsl:value-of select="@columnSize"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" /><xsl:value-of select="$charNbsp"/>到<xsl:value-of select="$charNbsp"/>
							<xsl:value-of select="$charLt"/>input type="text" class="text_field_half" name="<xsl:value-of select="$columnNameFormatLower"/>_to" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" value="" columnSize="<xsl:value-of select="@columnSize"/>" decimalDigits="<xsl:value-of select="@decimalDigits"/>" />
			</xsl:when>
						<!--其他字段都按此处理。处理普通的text，小于1000个字符-->
						<xsl:otherwise>
							<xsl:value-of select="$charLt"/>input type="text" class="text_field" name="<xsl:value-of select="$columnNameFormatLower"/>" inputName="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>" maxLength="<xsl:if test="@maxLength div 2 >= 1">
								<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
							</xsl:if>
							<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>"/>
			</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
			</xsl:choose>
			<xsl:choose>
				<!-- or (position()=(last()-1) and (../column[position()=last() and @columnName=../@tablePk]))-->
				<xsl:when test="@columnName=(../column[@isBuild_list='true'][last()]/@columnName)">
					<xsl:value-of select="$charTab"/>
					<xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<xsl:value-of select="$charLt"/>input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
			</xsl:when>
			</xsl:choose>
		</xsl:if>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
			<xsl:value-of select="$charLt"/>/td>
			<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>/td>
			<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>/tr>
		</xsl:if>
	</xsl:template>
	<!--处理循环列表控件标签-->
	<xsl:template match="column" mode="buildTableColumn_listLayout">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild_list='true'">
			<xsl:choose>
				<!--处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
					<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true">
		<xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>%=RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>, rmValue)%>
	<xsl:value-of select="$charLt"/>/layout:collectionItem>
	</xsl:when>
				<!--处理rm.affix(多附件上传)-->
				<xsl:when test="@humanDisplayType='rm.affix'">
					<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true">
		<xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>bean:define id="pk" name="rmBean" property="<xsl:value-of select="$tablePkFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>%="1".equals(rmValue) ? "<xsl:value-of select="$charLt"/>a target='_blank' href='" + request.getContextPath() + "/third/swfupload/globalUpload.jsp?bs_keyword=" + <xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME + "<xsl:value-of select="$charAmp"/>record_id=" + pk + "<xsl:value-of select="$charAmp"/>REQUEST_IS_READ_ONLY=1'><xsl:value-of select="$charLt"/>img align='center' width='30' height='30' src='" + request.getContextPath() + "/images/icon/affix.gif' /><xsl:value-of select="$charLt"/>/a>" : "" %>
	<xsl:value-of select="$charLt"/>/layout:collectionItem>
	</xsl:when>
				<!--处理时间戳参照-->
				<xsl:when test="@humanDisplayType='default' and @dataType='java.sql.Timestamp'">
					<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true">
		<xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	<xsl:value-of select="$charLt"/>/layout:collectionItem>
	</xsl:when>
				<!--处理日期参照-->
				<xsl:when test="@humanDisplayType='default' and @dataType='java.sql.Date'">
					<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true">
		<xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 10)%>
	<xsl:value-of select="$charLt"/>/layout:collectionItem>
	</xsl:when>
				<xsl:otherwise>
					<xsl:choose>
						<!--关键列-->
						<xsl:when test="$columnName=$keyColumn">
							<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true">
		<xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$columnNameFormatLower"/>"/>
		<xsl:value-of select="$charLt"/>%="<xsl:value-of select="$charLt"/>a class='aul' onclick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<xsl:value-of select="$charLt"/>%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<xsl:value-of select="$charLt"/>%="<xsl:value-of select="$charLt"/>/a>"%>
	<xsl:value-of select="$charLt"/>/layout:collectionItem>
	</xsl:when>
						<!--默认情况-->
						<xsl:otherwise>
							<xsl:value-of select="$charLt"/>layout:collectionItem width="8%" title='<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>' property="<xsl:value-of select="$columnNameFormatLower"/>" sortable="true"/>
	</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	
	<!--新版处理各列的循环detail展现-->
	<xsl:template match="column" mode="buildTableColumn_detailDisplay_newversion">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
			<xsl:choose>
				<!--处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
				<xsl:value-of select="$charLt"/>div class="input-prepend">
				<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
				<xsl:value-of select="$charLt"/>%=RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>, resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())%><xsl:value-of select="$charNbsp"/>
				<xsl:value-of select="$charLt"/>input class="m-wrap" type="text" value="<xsl:value-of select="$charLt"/>%=RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>, resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())%>" /><xsl:value-of select="$charNbsp"/>"<xsl:value-of select="$charNbsp"/>
				<xsl:value-of select="$charLt"/>/div>
				
			</xsl:when>
				<!--处理rm.affix不做处理-->
				<xsl:when test="@humanDisplayType='rm.affix'">
					<xsl:value-of select="$charLt"/>div class="input-prepend">
					<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
					<xsl:value-of select="$charLt"/>input class="m-wrap" type="text" bs_keyword="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%>" record_id="<xsl:value-of select="$charLt"/>%=resultVo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>()%>">${bean.<xsl:value-of select="$columnNameFormatLower"/>}<xsl:value-of select="$charNbsp"/>
	<xsl:value-of select="$charLt"/>/div>
	</xsl:when>
				<!--处理textarea，大于1000个字符-->
				<xsl:when test="not(@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox') and @dataType='java.lang.String' and @maxLength &gt;= 1000">
					<xsl:value-of select="$charLt"/>div class="input-prepend">
		<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
		<xsl:value-of select="$charLt"/>input class="m-wrap" type="text" value="${bean.<xsl:value-of select="$columnNameFormatLower"/>}" /><xsl:value-of select="$charNbsp"/>
	<xsl:value-of select="$charLt"/>/div>
	</xsl:when>
				<xsl:otherwise>
				<xsl:value-of select="$charLt"/>div class="input-prepend">
					<xsl:value-of select="$charLt"/>span class="add-on"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%><xsl:value-of select="$charLt"/>/span> 
					<xsl:value-of select="$charLt"/>input class="m-wrap" name="<xsl:value-of select="$columnNameFormatLower"/>" id="<xsl:value-of select="$columnNameFormatLower"/>" type="text" value ="${bean.<xsl:value-of select="$columnNameFormatLower"/>}" maxLength="<xsl:if test="@maxLength div 2 >= 1">
					<xsl:value-of select="format-number(floor(@maxLength div 2),'#')"/>
					</xsl:if>
					<xsl:if test="@maxLength div 2 &lt; 1">1</xsl:if>"/>
					<xsl:value-of select="$charLt"/>/div>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
	
	
	
	<!--处理各列的循环detail展现-->
	<xsl:template match="column" mode="buildTableColumn_detailDisplay">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk) and @isBuild='true'">
			<xsl:choose>
				<!--处理rm.dictionary.select checkbox(人性化展现方式)-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>：<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charLt"/>%=RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/>, resultVo.get<xsl:value-of select="str:upperFirst($columnNameFormatLower)"/>())%><xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理rm.affix-->
				<xsl:when test="@humanDisplayType='rm.affix'">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>：<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3"><xsl:value-of select="$charLt"/>span class="rm_affix" bs_keyword="<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%>" record_id="<xsl:value-of select="$charLt"/>%=resultVo.get<xsl:value-of select="str:upperFirst($tablePkFormatLower)"/>()%>">${bean.<xsl:value-of select="$columnNameFormatLower"/>}<xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/span><xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<!--处理textarea，大于1000个字符-->
				<xsl:when test="not(@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox') and @dataType='java.lang.String' and @maxLength &gt;= 1000">
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>：<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td colspan="3">${<xsl:value-of select="$columnNameFormatLower"/>}<xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$charLt"/>tr>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>：<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td>${bean.<xsl:value-of select="$columnNameFormatLower"/>}<xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td align="right"><xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
		<xsl:value-of select="$charLt"/>td><xsl:value-of select="$charNbsp"/>
					<xsl:value-of select="$charLt"/>/td>
	<xsl:value-of select="$charLt"/>/tr>
	</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
