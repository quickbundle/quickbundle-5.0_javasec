<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" xmlns:fn="http://www.w3.org/2005/04/xpath-functions" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="/meta/tables/table[position()=1 or @tableName=/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]/@tableName]">
		<xsl:apply-templates mode="dictionary"/>
	</xsl:template>
	<!--循环列，如果有数据字典就处理-->
	<xsl:template match="column" mode="dictionary">
		<xsl:param name="columnName" select="@columnName"/>
		<xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
		<xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		<xsl:param name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
		<xsl:param name="humanDisplayTypeKeywordName" select="substring-after(@humanDisplayTypeKeyword, '=')"/>
		<xsl:if test="not($columnName=$tablePk)">
			<xsl:choose>
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
					<xsl:choose>
						<xsl:when test="../column[(@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox') and concat($humanDisplayTypeKeyword,'=',$humanDisplayTypeKeywordName)=@humanDisplayTypeKeyword][1]/@columnName=@columnName">
	public final static String DICTIONARY_<xsl:value-of select="$humanDisplayTypeKeyword"/> = "<xsl:value-of select="$humanDisplayTypeKeyword"/>";
</xsl:when>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise/>
			</xsl:choose>
		</xsl:if>
	</xsl:template>
</xsl:stylesheet>
