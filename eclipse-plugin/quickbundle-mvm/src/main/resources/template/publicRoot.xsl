<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE xsl:stylesheet [
	<!ENTITY nbsp "&#160;">
	<!ENTITY xmlBr "&#10;">
]>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:str="http://www.quickbundle.org" version="2.0" exclude-result-prefixes="str">
	<!--定义输出格式-->
	<xsl:output method="text" encoding="UTF-8" omit-xml-declaration="yes" standalone="yes" indent="yes"/>
	<!--定义如何处理空白字符-->
	<xsl:strip-space elements=""/>
	<xsl:preserve-space elements="*"/>
	<!--抽取XML中的公共元素-->
	<!--定义/database中的全局变量-->
	<xsl:variable name="driver" select="/meta/database/driver"/>
	<xsl:variable name="url" select="/meta/database/url"/>
	<xsl:variable name="userName" select="/meta/database/userName"/>
	<xsl:variable name="password" select="/meta/database/password"/>
	<xsl:variable name="dbProductName" select="/meta/database/dbProductName"/>
	<!--定义mainTable(默认主表)的全局变量-->
	<xsl:variable name="tableName" select="str:getMainTableName(/meta)"/>
	<xsl:variable name="tableNameDisplay" select="str:getTableNameDisplay(/meta, $tableName)"/>
	<xsl:variable name="tableFilterKeyword" select="str:getTableFilterKeyword(/meta, $tableName)"/>
    <xsl:variable name="tableDirName" select="str:getTableDirName(/meta, $tableName)"/>
	<xsl:variable name="tablePk" select="str:getTablePk(/meta, $tableName)"/>
	<xsl:variable name="statisticColumn" select="str:getStatisticColumn(/meta, $tableName)"/>
	<xsl:variable name="keyColumn" select="str:getKeyColumn(/meta, $tableName)"/>
	<xsl:variable name="tableComment" select="str:getTableComment(/meta, $tableName)"/>
	<!--定义mainTable(默认主表)的处理过的表名全局变量-->
	<xsl:variable name="tableFormatName" select="str:getTableFormatName(/meta, $tableName)"/>
	<xsl:variable name="tableFormatNameUpperFirst" select="str:getTableFormatNameUpperFirst(/meta, $tableName)"/>
	<xsl:variable name="tableFormatNameLowerFirst" select="str:getTableFormatNameLowerFirst(/meta, $tableName)"/>
	<!--定义mainTable(默认主表)的处理过的主键和外键-->
	<xsl:variable name="tablePkFormat" select="str:getTablePkFormat(/meta, $tableName)"/>
	<xsl:variable name="tablePkFormatLower" select="str:getTablePkFormatLower(/meta, $tableName)"/>
	<xsl:variable name="tablePkDisplay" select="str:getTablePkDisplay(/meta, $tableName)"/>
	<xsl:variable name="statisticColumnFormat" select="str:getStatisticColumnFormat(/meta, $tableName)"/>
	<xsl:variable name="statisticColumnFormatLower" select="str:getStatisticColumnFormatLower(/meta, $tableName)"/>
	<xsl:variable name="statisticColumnDisplay" select="str:getStatisticColumnDisplay(/meta, $tableName)"/>
	<xsl:variable name="keyColumnFormat" select="str:getKeyColumnFormat(/meta, $tableName)"/>
	<xsl:variable name="keyColumnFormatLower" select="str:getKeyColumnFormatLower(/meta, $tableName)"/>
	<xsl:variable name="keyColumnDisplay" select="str:getKeyColumnDisplay(/meta, $tableName)"/>
	<!--定义主键的Java类型-->
    <xsl:variable name="tablePkClass" select="str:getPkColumnClass(/meta, $tableName, $tablePk)"/>
	<!--定义全局标记分隔符，为了统一过滤并批量替换-->
	<xsl:variable name="division">RM_FLAG_DIVISION</xsl:variable>
	<!--定义全局分隔符-->
	<xsl:variable name="prefix">_</xsl:variable>
	<!--<-->
	<xsl:variable name="charLt">&lt;</xsl:variable>
	<!-->-->
	<xsl:variable name="charGt">&gt;</xsl:variable>
	<!--&-->
	<xsl:variable name="charAmp">&amp;</xsl:variable>
	<!--"-->
	<xsl:variable name="charQuot">&quot;</xsl:variable>
	<!--'-->
	<xsl:variable name="charApos">&apos;</xsl:variable>
	<!--nbsp-->
	<xsl:variable name="charNbsp">&amp;nbsp;</xsl:variable>
	<!--回车-->
	<xsl:variable name="charBr">&#13;</xsl:variable>
	<!--tab键-->
	<xsl:variable name="charTab">&#09;</xsl:variable>
	<!--空格字符串-->
	<xsl:variable name="charSpace">&#32;</xsl:variable>
	<!--自定义函数，取key之前的部分，如果无key，则返回整个字符串-->
	<xsl:function name="str:substring-before2">
		<xsl:param name="word" as="xs:string"/>
		<xsl:param name="keyStr" as="xs:string"/>
		<xsl:choose>
			<xsl:when test="substring-before($word, $keyStr)=''">
				<xsl:sequence select="$word"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:sequence select="substring-before($word, $keyStr)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--自定义函数，把字符串首字母变大写，其余字母小写-->
	<xsl:function name="str:upperFirst">
		<xsl:param name="word" as="xs:string"/>
		<xsl:choose>
			<xsl:when test="not(string-length($word)=0)">
				<xsl:sequence select="concat(upper-case(substring($word,1,1)),lower-case(substring($word,2,string-length($word))))"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:sequence select="$word"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--（带规则）自定义函数，针对表名操作，把字符串首字母变大写，其余字母采取不同策略-->
	<xsl:function name="str:upperFirstTableName">
		<xsl:param name="word" as="xs:string"/>
		<xsl:param name="filterKeyword" as="xs:string"/>
		<xsl:param name="filterType" as="xs:string"/>
		<xsl:choose>
			<xsl:when test="$filterType='default'">
				<xsl:sequence select="concat(upper-case(substring($word,1,1)),substring($word,2))"/>
			</xsl:when>
			<xsl:when test="$filterType='lowerCase'">
				<xsl:sequence select="str:upperFirst($word)"/>
			</xsl:when>
			<xsl:when test="$filterType='specify'">
				<xsl:sequence select="concat(upper-case(substring($filterKeyword,1,1)),substring($filterKeyword,2))"/>
			</xsl:when>
			<xsl:when test="$filterType='minus'">
				<xsl:sequence select="str:upperFirst(str:filter($word, $filterKeyword, $filterType))"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:sequence select="str:upperFirst($word)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--（带规则）自定义函数，针对表名操作，把字符串首字母变大写，其余字母采取不同策略-->
	<xsl:function name="str:lowerFirstTableName">
		<xsl:param name="word" as="xs:string"/>
		<xsl:param name="filterKeyword" as="xs:string"/>
		<xsl:param name="filterType" as="xs:string"/>
		<xsl:variable name="upperFirstTableNameVar" select="str:upperFirstTableName($word, $filterKeyword, $filterType)"/>
		<xsl:sequence select="concat(lower-case(substring($upperFirstTableNameVar,1,1)),substring($upperFirstTableNameVar,2,string-length($upperFirstTableNameVar)))"/>
	</xsl:function>
	<!--（带规则）自定义函数，根据指定的规则处理字符串-->
	<xsl:function name="str:filter">
		<xsl:param name="word" as="xs:string"/>
		<xsl:param name="filterKeyword" as="xs:string"/>
		<xsl:param name="filterType" as="xs:string"/>
		<xsl:choose>
			<xsl:when test="$filterType='default'">
				<xsl:sequence select="$word"/>
			</xsl:when>
			<xsl:when test="$filterType='lowerCase'">
				<xsl:sequence select="lower-case($word)"/>
			</xsl:when>
			<xsl:when test="$filterType='specify'">
				<xsl:sequence select="$filterKeyword"/>
			</xsl:when>
			<xsl:when test="$filterType='minus'">
				<xsl:choose>
					<xsl:when test="string-length($word)=0">
						<xsl:sequence select="$word"/>
					</xsl:when>
					<xsl:otherwise>
						<xsl:choose>
							<xsl:when test="starts-with(lower-case($word), lower-case($filterKeyword))">
								<xsl:sequence select="lower-case(substring($word, string-length($filterKeyword)+1))"/>
							</xsl:when>
							<xsl:otherwise>
								<xsl:sequence select="lower-case($word)"/>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:sequence select="lower-case($word)"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--************自定义函数，获得mainTable的tableName************-->
	<xsl:function name="str:getMainTableName">
		<xsl:param name="meta"/>
		<xsl:choose>
			<xsl:when test="$meta/relations/mainTable">
				<xsl:sequence select="$meta/relations/mainTable/@tableName"/>
			</xsl:when>
			<xsl:otherwise>
				<xsl:sequence select="$meta/tables/table[1]/@tableName"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--************根据指定表名，获得xml的原始属性************-->
	<!--自定义函数，获得指定tableName的tableNameDisplay-->
	<xsl:function name="str:getTableNameDisplay">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@tableNameDisplay"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tableFilterKeyword-->
	<xsl:function name="str:getTableFilterKeyword">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@tableFilterKeyword"/>
	</xsl:function>
    <!--自定义函数，获得指定tableName的tableDirName-->
    <xsl:function name="str:getTableDirName">
        <xsl:param name="meta"/>
        <xsl:param name="tableNameVar" as="xs:string"/>
        <xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@tableDirName"/>
    </xsl:function>
	<!--自定义函数，获得指定tableName的tablePk-->
	<xsl:function name="str:getTablePk">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@tablePk"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的statisticColumn-->
	<xsl:function name="str:getStatisticColumn">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@statisticColumn"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的keyColumn-->
	<xsl:function name="str:getKeyColumn">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="keyColumn" select="$meta/tables/table[@tableName=$tableNameVar]/@keyColumn"/>
		<xsl:sequence select="$keyColumn"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tableComment-->
	<xsl:function name="str:getTableComment">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/@tableComment"/>
	</xsl:function>
	<!--************根据指定表名，获得处理过的表名全局变量************-->
	<!--自定义函数，获得指定tableName的tableFormatName-->
	<xsl:function name="str:getTableFormatName">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="str:filter($tableNameVar, str:getTableFilterKeyword($meta, $tableNameVar), 'specify')"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tableFormatNameUpperFirst-->
	<xsl:function name="str:getTableFormatNameUpperFirst">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="str:upperFirstTableName($tableNameVar, str:getTableFilterKeyword($meta, $tableNameVar), 'specify')"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tableFormatNameLowerFirst-->
	<xsl:function name="str:getTableFormatNameLowerFirst">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="str:lowerFirstTableName($tableNameVar, str:getTableFilterKeyword($meta, $tableNameVar), 'specify')"/>
	</xsl:function>
	<!--************根据指定表名，获得处理过的主键和外键************-->
	<!--自定义函数，获得指定tableName的tablePkFormat-->
	<xsl:function name="str:getColumnNameFormat">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:param name="columnNameVar" as="xs:string"/>
		<xsl:sequence select="str:filter($columnNameVar, $meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$columnNameVar]/@filterKeyword, $meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$columnNameVar]/@filterType)"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tablePkFormat-->
	<xsl:function name="str:getColumnNameFormatLower">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:param name="columnNameVar" as="xs:string"/>
		<xsl:sequence select="lower-case(str:getColumnNameFormat($meta, $tableNameVar, $columnNameVar))"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tablePkFormat-->
	<xsl:function name="str:getTablePkFormat">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="tablePkVar" select="str:getTablePk($meta, $tableNameVar)"/>
		<xsl:sequence select="str:getColumnNameFormat($meta, $tableNameVar, $tablePkVar)"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tablePkFormatLower-->
	<xsl:function name="str:getTablePkFormatLower">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="tablePkFormatVar" select="str:getTablePkFormat($meta, $tableNameVar)"/>
		<xsl:sequence select="lower-case($tablePkFormatVar)"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的tablePkDisplay-->
	<xsl:function name="str:getTablePkDisplay">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="tablePkVar" select="str:getTablePk($meta, $tableNameVar)"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$tablePkVar]/@columnNameDisplay"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的statisticColumnFormat-->
	<xsl:function name="str:getStatisticColumnFormat">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="statisticColumnVar" select="str:getStatisticColumn($meta, $tableNameVar)"/>
		<xsl:sequence select="str:getColumnNameFormat($meta, $tableNameVar, $statisticColumnVar)"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的statisticColumnFormatLower-->
	<xsl:function name="str:getStatisticColumnFormatLower">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="lower-case(str:getStatisticColumnFormat($meta, $tableNameVar))"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的statisticColumnDisplay-->
	<xsl:function name="str:getStatisticColumnDisplay">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="statisticColumnVar" select="str:getStatisticColumn($meta, $tableNameVar)"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$statisticColumnVar]/@columnNameDisplay"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的keyColumnFormat-->
	<xsl:function name="str:getKeyColumnFormat">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="keyColumnVar" select="str:getKeyColumn($meta, $tableNameVar)"/>
		<xsl:sequence select="str:getColumnNameFormat($meta, $tableNameVar, $keyColumnVar)"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的keyColumnFormatLower-->
	<xsl:function name="str:getKeyColumnFormatLower">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="lower-case(str:getKeyColumnFormat($meta, $tableNameVar))"/>
	</xsl:function>
	<!--自定义函数，获得指定tableName的keyColumnDisplay-->
	<xsl:function name="str:getKeyColumnDisplay">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="keyColumnVar" select="str:getKeyColumn($meta, $tableNameVar)"/>
		<xsl:sequence select="$meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$keyColumnVar]/@columnNameDisplay"/>
	</xsl:function>
	<!--自定义函数，获得指定子表tableName针对mainTable的外键-->
	<xsl:function name="str:getRefColumn">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:sequence select="$meta/relations/mainTable/refTable[@tableName=$tableNameVar]/@refColumn"/>
	</xsl:function>
	<!--自定义函数，获得指定子表tableName针对mainTable的外键的FormatLower-->
	<xsl:function name="str:getRefColumnFormatLower">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:variable name="refColumnVar" select="str:getRefColumn($meta, $tableNameVar)"/>
		<xsl:sequence select="lower-case((str:filter($refColumnVar, $meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$refColumnVar]/@filterKeyword, $meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$refColumnVar]/@filterType)))"/>
	</xsl:function>
	<!--自定义函数，获得指定表tableName的PK/FK列的Class-->
	<xsl:function name="str:getPkColumnClass">
		<xsl:param name="meta"/>
		<xsl:param name="tableNameVar" as="xs:string"/>
		<xsl:param name="pkColumnVar" as="xs:string"/>
		<xsl:choose>
		  <xsl:when test="$meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$pkColumnVar]/@dataType='java.lang.Long'">Long</xsl:when>
		  <xsl:when test="$meta/tables/table[@tableName=$tableNameVar]/column[@columnName=$pkColumnVar]/@dataType='java.lang.String'">String</xsl:when>
		  <xsl:otherwise>String</xsl:otherwise>
		</xsl:choose>
	</xsl:function>
	<!--处理根元素-->
	<xsl:template match="/">
		<xsl:apply-templates/>
	</xsl:template>
	<xsl:template match="text()|@*"/>
</xsl:stylesheet>
