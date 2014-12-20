<%@ page contentType="application/json; charset=UTF-8" language="java" %><%
	final RmKeyCountList<String> kc = new RmKeyCountList<String>(); 
	RmProjectHelper.getCommonServiceInstance().query("select template_id as rm_key, count(*) as rm_count from RM_MESSAGE group by template_id", new RowMapper() {
	    public Object mapRow(ResultSet rs, int i) throws SQLException {
	    	String key = rs.getString("rm_key");
	    	kc.put(key == null ? "" : key, rs.getLong("rm_count"));
	    	return null;
	    }
	});
	System.out.println(kc.getJsonKey());
%>{
  "title":{
    "text":  "按<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>统计",
    "style": "{font-size: 20px; color:#0000ff; font-family: Verdana; text-align: center;}"
  },

  "y_legend":{
    "text": "Open Flash Chart",
    "style": "{color: #736AFF; font-size: 12px;}"
  },

  "elements":[
    {
      "type":      "<%=request.getParameter("type") != null ? request.getParameter("type") : "pie"%>",
      "alpha":     0.5,
      "colour":    "#9933CC",
      "text--":      "<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>",
      "font-size--": 10,
      "values" :   <%="pie".equals(request.getParameter("type")) ? kc.getJsonKeyCount() : kc.getJsonCount()%>
    }
  ],

  "x_axis":{
    "stroke":       1,
    "tick_height":  10,
    "colour":      "#d000d0",
    "grid_colour": "#00ff00",
    "labels": {
      "labels": <%=kc.getJsonKey()%>
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
    "max":         <%=kc.getMaxCount4Bar()%>
  }

/* &x_axis_steps=2& */

}
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.util.RmKeyCountList"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.modules.message.IRmMessageConstants"%>