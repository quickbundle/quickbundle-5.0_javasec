<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" xmlns:fn="http://www.w3.org/2005/04/xpath-functions" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xdt="http://www.w3.org/2005/02/xpath-datatypes" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" omit-xml-declaration="yes" encoding="UTF-8"/>
	<!--处理table-->
	<xsl:template match="table[1]">

#<xsl:value-of select="$dbProductName"/>
jdbc.minPoolSize=1
jdbc.maxPoolSize=50
jdbc.validateTestSql=<xsl:choose>
<xsl:when test="substring(upper-case($dbProductName),1,3)='DB2'">select current timestamp from sysibm.sysdummy1</xsl:when>
<xsl:otherwise>select 0 from dual</xsl:otherwise>
</xsl:choose>
hibernate.dialect=org.hibernate.dialect.<xsl:choose>
<xsl:when test="upper-case($dbProductName)='MYSQL'">MySQLDialect</xsl:when>
<xsl:when test="upper-case($dbProductName)='POSTGRESQL'">PostgreSQL82Dialect</xsl:when>
<xsl:when test="upper-case($dbProductName)='ORACLE'">Oracle10gDialect</xsl:when>
<xsl:when test="substring(upper-case($dbProductName),1,3)='DB2'">DB2Dialect</xsl:when>
<xsl:when test="upper-case($dbProductName)='MICROSOFT SQL SERVER'">SQLServerDialect</xsl:when>
<xsl:when test="upper-case($dbProductName)='H2'">H2Dialect</xsl:when>
<xsl:when test="upper-case($dbProductName)='HSQL DATABASE ENGINE'">HSQLDialect</xsl:when>
<xsl:otherwise>ToManualConfigDialect</xsl:otherwise>
</xsl:choose>
mybatis.dialect=<xsl:choose>
<xsl:when test="upper-case($dbProductName)='MYSQL'">MYSQL</xsl:when>
<xsl:when test="upper-case($dbProductName)='POSTGRESQL'">POSTGRESQL</xsl:when>
<xsl:when test="upper-case($dbProductName)='ORACLE'">ORACLE</xsl:when>
<xsl:when test="substring(upper-case($dbProductName),1,3)='DB2'">DB2</xsl:when>
<xsl:when test="upper-case($dbProductName)='MICROSOFT SQL SERVER'">SQLSERVER</xsl:when>
<xsl:when test="upper-case($dbProductName)='H2'">H2</xsl:when>
<xsl:when test="upper-case($dbProductName)='HSQL DATABASE ENGINE'">HSQL</xsl:when>
<xsl:otherwise>ToManualConfigDialect</xsl:otherwise>
</xsl:choose>
jdbc.driverClassName=<xsl:value-of select="$driver"/>
jdbc.url=<xsl:value-of select="$url"/>
jdbc.username=<xsl:value-of select="$userName"/>
jdbc.password=<xsl:value-of select="$password"/>

	</xsl:template>
</xsl:stylesheet>
