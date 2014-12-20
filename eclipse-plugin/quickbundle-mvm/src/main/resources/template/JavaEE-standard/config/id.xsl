<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" xmlns:fn="http://www.w3.org/2005/04/xpath-functions" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="/meta/tables/table[position()=1 or @tableName=/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]/@tableName]">
		<xsl:value-of select="$charLt"/>table table_code="1001" table_name="<xsl:value-of select="@tableName"/>" id_name="<xsl:value-of select="$tablePk"/>"/>
	</xsl:template>
</xsl:stylesheet>
