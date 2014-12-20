<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="publicRoot.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="xml" omit-xml-declaration="no" encoding="UTF-8"/>
	<!--处理meta-->
	<xsl:template match="meta">
		<results>
			<xsl:apply-templates/>
		</results>
	</xsl:template>
	<!--处理table-->
	<xsl:template match="/meta/tables/table[@tableName=str:getMainTableName(/meta)]">
		<result>
			<xsl:attribute name="tableFormatNameUpperFirst" select="$tableFormatNameUpperFirst"/>
		</result>
	</xsl:template>
</xsl:stylesheet>
