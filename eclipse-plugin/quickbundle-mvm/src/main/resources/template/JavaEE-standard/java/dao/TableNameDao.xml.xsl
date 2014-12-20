<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<xsl:param name="targetFullPath"/>
	<!--处理table-->
	<xsl:template match="/meta/tables/table[position()=1 or @tableName=/meta/relations/mainTable[@tableName=/meta/tables/table[1]/@tableName]/refTable[count(middleTable)=0]/@tableName]">
		<xsl:result-document href="{$targetFullPath}/{str:getTableFormatNameUpperFirst(/meta, @tableName)}Dao.xml">
			<xsl:variable name="thisTablePk" select="str:getTablePk(/meta, @tableName)"/>
			<xsl:value-of select="$charLt"/>?xml version="1.0" encoding="UTF-8"?>
<xsl:value-of select="$charLt"/>!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" 
"http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<xsl:value-of select="$charLt"/>mapper namespace="<xsl:value-of select="$javaPackageTableDir"/>.dao.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Dao">

  <xsl:value-of select="$charLt"/>insert id="insert" parameterType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    insert into <xsl:value-of select="@tableName"/> ( <xsl:value-of select="$thisTablePk"/>,
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">
				<xsl:value-of select="@columnName"/>
				<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>)
    values ( #{<xsl:value-of select="str:getTablePkFormatLower(/meta, @tableName)"/>}, 
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">#{<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/><xsl:choose>
							<xsl:when test="@dataTypeDb='DATETIME' or @dataTypeDb='TIMESTAMP'">,jdbcType=TIMESTAMP</xsl:when>
							<xsl:when test="@dataTypeDb='VARCHAR' or @dataTypeDb='CHAR' or @dataTypeDb='DATE' or @dataTypeDb='TIME' or @dataTypeDb='DECIMAL'">,jdbcType=<xsl:value-of select="@dataTypeDb"/></xsl:when>
							<xsl:otherwise></xsl:otherwise>
						</xsl:choose>}<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>)
  <xsl:value-of select="$charLt"/>/insert>
    
  <xsl:value-of select="$charLt"/>delete id="delete" parameterType="int">
    delete from <xsl:value-of select="@tableName"/> where <xsl:value-of select="$thisTablePk"/>=#{<xsl:value-of select="str:getTablePkFormatLower(/meta, @tableName)"/>}
  <xsl:value-of select="$charLt"/>/delete>
  
  <xsl:value-of select="$charLt"/>delete id="deleteMulti" parameterType="int">
    delete from <xsl:value-of select="@tableName"/> where <xsl:value-of select="$thisTablePk"/> in 
    <xsl:value-of select="$charLt"/>foreach collection="array" index="index" item="item" open="(" separator="," close=")">  
      #{item}   
    <xsl:value-of select="$charLt"/>/foreach>
  <xsl:value-of select="$charLt"/>/delete>

  <xsl:value-of select="$charLt"/>update id="update" parameterType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    update <xsl:value-of select="@tableName"/> set 
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">
				<xsl:value-of select="@columnName"/>=#{<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/><xsl:choose>
							<xsl:when test="@dataTypeDb='DATETIME' or @dataTypeDb='TIMESTAMP'">,jdbcType=TIMESTAMP</xsl:when>
							<xsl:when test="@dataTypeDb='VARCHAR' or @dataTypeDb='CHAR' or @dataTypeDb='DATE' or @dataTypeDb='TIME' or @dataTypeDb='DECIMAL'">,jdbcType=<xsl:value-of select="@dataTypeDb"/></xsl:when>
							<xsl:otherwise></xsl:otherwise>
						</xsl:choose>}<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>  
    where <xsl:value-of select="$thisTablePk"/>=#{<xsl:value-of select="str:getTablePkFormatLower(/meta, @tableName)"/>}
  <xsl:value-of select="$charLt"/>/update>

  <xsl:value-of select="$charLt"/>select id="get" parameterType="string" resultType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    select <xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>, 
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">
				<xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>
				<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>
    from <xsl:value-of select="@tableName"/> 
    where <xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>=#{<xsl:value-of select="str:getTablePkFormatLower(/meta, @tableName)"/>}
  <xsl:value-of select="$charLt"/>/select>

  <xsl:value-of select="$charLt"/>select id="getCount" parameterType="string" resultType="int" useCache="true">
    select count(<xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>) from <xsl:value-of select="@tableName"/>
			<xsl:value-of select="$charLt"/>if test="value != null and value.length > 0">
    where ${value}
    <xsl:value-of select="$charLt"/>/if>
  <xsl:value-of select="$charLt"/>/select>
  
  <xsl:value-of select="$charLt"/>select id="list" parameterType="map" resultType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    select <xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>, 
      <xsl:for-each select="column[not(@columnName=$thisTablePk) and @isBuild_list='true']">
				<xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>
				<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>
    from <xsl:value-of select="@tableName"/>
			<xsl:value-of select="$charLt"/>if test="queryCondition != null and queryCondition != ''">
    where ${queryCondition}
    <xsl:value-of select="$charLt"/>/if>
    <xsl:value-of select="$charLt"/>if test="orderStr != null and orderStr != ''">
        order by ${orderStr}
    <xsl:value-of select="$charLt"/>/if>
  <xsl:value-of select="$charLt"/>/select>
  
  <xsl:value-of select="$charLt"/>select id="listAllColumn" parameterType="map" resultType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    select <xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>, 
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">
				<xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>
				<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>
    from <xsl:value-of select="@tableName"/>
			<xsl:value-of select="$charLt"/>if test="queryCondition != null and queryCondition != ''">
    where ${queryCondition}
    <xsl:value-of select="$charLt"/>/if>
    <xsl:value-of select="$charLt"/>if test="orderStr != null and orderStr != ''">
        order by ${orderStr}
    <xsl:value-of select="$charLt"/>/if>
  <xsl:value-of select="$charLt"/>/select>

  <xsl:value-of select="$charLt"/>select id="search" parameterType="map" resultType="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="str:getTableFormatNameUpperFirst(/meta, @tableName)"/>Vo">
    select <xsl:value-of select="@tableName"/>.<xsl:value-of select="$thisTablePk"/>, 
      <xsl:for-each select="column[not(@columnName=$thisTablePk)]">
				<xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>
				<xsl:if test="not(position()=last())">, </xsl:if>
				<xsl:if test="position() mod 4=0"><xsl:text>
      </xsl:text></xsl:if>
			</xsl:for-each>
    from <xsl:value-of select="@tableName"/>
			<xsl:value-of select="$charLt"/>trim prefix="where" prefixOverrides="and|or">  
      <xsl:for-each select="column[not(@columnName=$thisTablePk) and @isBuild='true']">
				<xsl:value-of select="$charLt"/>if test="<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/> != null and <xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/> != ''">
        <xsl:choose>
					<!--处理text或textarea，大于3个字符-->
					<xsl:when test="@dataType='java.lang.String' and @maxLength&gt;3">and <xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/> like '%${<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/>}%'
      </xsl:when>
					<!--处理时间/数字-->
					<xsl:when test="@dataType='java.sql.Timestamp' or @dataType='java.sql.Date' or @dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">and <xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>=#{<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/>}
      </xsl:when>
					<xsl:otherwise>and <xsl:value-of select="../@tableName"/>.<xsl:value-of select="@columnName"/>=#{<xsl:value-of select="str:getColumnNameFormatLower(/meta, ../@tableName, @columnName)"/>}
      </xsl:otherwise>
				</xsl:choose>
				<xsl:value-of select="$charLt"/>/if>
      </xsl:for-each>
			<xsl:value-of select="$charLt"/>/trim>
    <xsl:value-of select="$charLt"/>if test="orderStr != null and orderStr != ''">
        order by ${orderStr}
    <xsl:value-of select="$charLt"/>/if>
  <xsl:value-of select="$charLt"/>/select>

<xsl:value-of select="$charLt"/>/mapper>
    </xsl:result-document>
	</xsl:template>
</xsl:stylesheet>
