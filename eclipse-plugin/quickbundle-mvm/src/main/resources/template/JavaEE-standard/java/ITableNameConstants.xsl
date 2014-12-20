<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" xmlns:fn="http://www.w3.org/2005/04/xpath-functions" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="table[1]">
		<xsl:value-of select="str:getJavaFileComment($authorName)"/>
package <xsl:value-of select="$javaPackageTableDir"/>;

import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.quickbundle.project.IGlobalConstants;

<xsl:value-of select="str:getClassComment($authorName)"/>

public interface <xsl:value-of select="$ITableNameConstants"/> extends IGlobalConstants {

    //表名、显示名
    public final static String TABLE_NAME = "<xsl:value-of select="@tableName"/>";
    public final static String TABLE_NAME_DISPLAY = "<xsl:value-of select="@tableNameDisplay"/>";
    public final static String TABLE_PK = "<xsl:value-of select="$tablePkFormatLower"/>";
    //列名汉化
    @SuppressWarnings({ "unchecked", "serial" })
    public final static Map<xsl:value-of select="$charLt"/>String, String> TABLE_COLUMN_DISPLAY = new CaseInsensitiveMap(){{<xsl:apply-templates mode="table_column_display_put"/>
    }};

<xsl:for-each select="/meta/tables/table[@tableName=/meta/relations/mainTable[@tableName=$tableName]/refTable[count(middleTable)=0]/@tableName]">
    //子表<xsl:value-of select="position()"/>-<xsl:value-of select="str:getTableNameDisplay(/meta, @tableName)"/>-表名、显示名
    public final static String TABLE_NAME_<xsl:value-of select="@tableName"/> = "<xsl:value-of select="@tableName"/>";
    public final static String TABLE_NAME_DISPLAY_<xsl:value-of select="@tableName"/> = "<xsl:value-of select="str:getTableNameDisplay(/meta, @tableName)"/>";
    public final static String TABLE_PK_<xsl:value-of select="@tableName"/> = "<xsl:value-of select="str:getTablePkFormatLower(/meta, @tableName)"/>";
    //子表<xsl:value-of select="position()"/>-列名汉化
    @SuppressWarnings({"unchecked", "serial" })
    public final static Map<xsl:value-of select="$charLt"/>String, String> TABLE_COLUMN_DISPLAY_<xsl:value-of select="@tableName"/> = new CaseInsensitiveMap(){{<xsl:apply-templates mode="table_column_display_put"/>
    }};
</xsl:for-each>
    
    //日志类型名称
    public final static String LOG_TYPE_NAME = TABLE_NAME_DISPLAY + "管理";
}
</xsl:template>
<!--处理TABLE_COLUMN_DISPLAY的循环部分-->
<xsl:template match="column" mode="table_column_display_put">
    <xsl:param name="columnName" select="@columnName"/>
    <xsl:param name="columnNameFormat" select="str:filter(@columnName,@filterKeyword,@filterType)"/>
    <xsl:param name="columnNameFormatLower" select="lower-case($columnNameFormat)"/>
		put("<xsl:value-of select="$columnNameFormatLower"/>","<xsl:value-of select="@columnNameDisplay"/>");</xsl:template>
</xsl:stylesheet>
