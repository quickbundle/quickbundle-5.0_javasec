<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import = "java.io.PrintWriter" %>
<%@page import="java.util.List"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.quickbundle.third.jfreechart.WebChart"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%
	WebChart chart = new WebChart();
	List lResult = RmProjectHelper.getCommonServiceInstance().query("select code_type_id value, count(code_type_id) sum from RM_CODE_DATA group by value", new RowMapper() {
	    public Object mapRow(ResultSet rs, int i) throws SQLException {
	    	return new String[]{rs.getString("value"), rs.getString("sum")};
	    }
	});
	for(Iterator itLResult = lResult.iterator(); itLResult.hasNext(); ) {
	    String[] array = (String[])itLResult.next();
		chart.setValue(array[0], Integer.parseInt(array[1]));
	}
	String filename_pie = chart.generatePieChart("按code_type_id统计", session, new PrintWriter(out));
	String graphURL_pie = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_pie;
	
	String filename_bar = chart.generateBarChart("按code_type_id统计", session, new PrintWriter(out));
	String graphURL_bar = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_bar;
%>

<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
</head>
<body>
<p align="center">
	<img src="<%=graphURL_pie %>" usemap="#<%=filename_pie %>">
</p>
<p align="center">
	<img src="<%=graphURL_bar %>" usemap="#<%=filename_bar %>">
</p>
</body>
</html>
