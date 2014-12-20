<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.util.RmKeyCountList"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants"%>
<%
	final RmKeyCountList<String> kc = new RmKeyCountList<String>(); 
	RmProjectHelper.getCommonServiceInstance().query("select mime_type as rm_key, count(mime_type) as rm_count from RM_AFFIX group by mime_type", new RowMapper() {
	    public Object mapRow(ResultSet rs, int i) throws SQLException {
	    	kc.put(rs.getString("rm_key"), rs.getLong("rm_count"));
	    	return null;
	    }
	});
%>
{
  "title":{
    "text":  "按<%=IRmAffixConstants.TABLE_COLUMN_CHINESE.get("mime_type")%>统计",
    "style": "{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}"
  },
 
  "y_legend":{
    "text": "count",
    "style": "{color: #736AFF; font-size: 12px;}"
  },
 
  "elements":[
    {
      "type":      "<%=request.getParameter("type") != null ? request.getParameter("type") : "pie"%>",
      "alpha":     0.5,
      "colour":    "#9933CC",
      "text":      "<%=IRmAffixConstants.TABLE_COLUMN_CHINESE.get("mime_type")%>",
      "font-size": 10,
      "tip": "<%="pie".equals(request.getParameter("type")) ? "#val#/#total# #percent#" : "#val#"%>",
      "values" :   <%="pie".equals(request.getParameter("type")) ? kc.getJsonKeyCount() : kc.getJsonCount()%>
    }    
  ],
 
  "x_axis":{
  	"3d": 1,
    "stroke":1,
    "tick_height":10,
    "colour":"#d000d0",
    "grid_colour":"#00ff00",
    "labels": {
    	"labels": <%=kc.getJsonKey()%>
    }
   },
 
  "y_axis":{
    "stroke":      4,
    "tick_length": 3,
    "colour":      "#d000d0",
    "grid_colour": "#00ff00",
    "offset":      0,
    "max":         <%=kc.getMaxCount4Bar()%>
  }
}