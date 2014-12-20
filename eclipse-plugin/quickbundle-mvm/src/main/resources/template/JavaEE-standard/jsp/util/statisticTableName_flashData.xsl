<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="application/json; charset=UTF-8" language="java" %><xsl:value-of select="$charLt"/>%
    final RmKeyCountList<xsl:value-of select="$charLt"/>String> kc = new RmKeyCountList<xsl:value-of select="$charLt"/>String>(); 
    RmProjectHelper.getCommonServiceInstance().query("select <xsl:value-of select="$statisticColumnFormatLower"/>  as rm_key, count(*) as rm_count from <xsl:value-of select="@tableName"/> group by <xsl:value-of select="$statisticColumnFormatLower"/> ", new RowMapper() {
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            String key = rs.getString("rm_key");
            kc.put(key == null ? "" : key, rs.getLong("rm_count"));
            return null;
        }
    });
    System.out.println(kc.getJsonKey());
%>{
  "title":{
    "text":  "按<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$statisticColumnFormatLower"/>")%>统计",
    "style": "{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}"
  },

  "y_legend":{
    "text": "Open Flash Chart",
    "style": "{color: #736AFF; font-size: 12px;}"
  },

  "elements":[
    {
      "type":      "<xsl:value-of select="$charLt"/>%=request.getParameter("type") != null ? request.getParameter("type") : "pie"%>",
      "alpha":     0.5,
      "colour":    "#9933CC",
      "text--":      "<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$statisticColumnFormatLower"/>")%>",
      "font-size--": 10,
      "values" :   <xsl:value-of select="$charLt"/>%="pie".equals(request.getParameter("type")) ? kc.getJsonKeyCount() : kc.getJsonCount()%>
    }
  ],

  "x_axis":{
    "stroke":       1,
    "tick_height":  10,
    "colour":      "#d000d0",
    "grid_colour": "#00ff00",
    "labels": {
      "labels": <xsl:value-of select="$charLt"/>%=kc.getJsonKey()%>
    },
    "3d":         5
   },

  "y_axis":{
    "stroke":      4,
    "tick_length": 3,
    "steps":       10,
    "colour":      "#d000d0",
    "grid_colour": "#00ff00",
    "offset":      0,
    "max":         <xsl:value-of select="$charLt"/>%=kc.getMaxCount4Bar()%>
  }

/* <xsl:value-of select="$charAmp"/>x_axis_steps=2<xsl:value-of select="$charAmp"/> */

}
<xsl:value-of select="$charLt"/>%@page import="java.sql.ResultSet"%>
<xsl:value-of select="$charLt"/>%@page import="java.sql.SQLException"%>
<xsl:value-of select="$charLt"/>%@page import="java.util.List"%>
<xsl:value-of select="$charLt"/>%@page import="org.springframework.jdbc.core.RowMapper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.util.RmKeyCountList"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmProjectHelper"%>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>"%>
</xsl:template>
</xsl:stylesheet>
