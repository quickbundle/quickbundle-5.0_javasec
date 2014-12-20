<%@page contentType="text/html;charset=UTF-8"%>
<%@page import="org.quickbundle.third.jfreechart.WebChart"%>
<%@page import="java.io.PrintWriter" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.jfree.chart.urls.StandardCategoryURLGenerator"%>
<%@page import="org.jfree.data.category.CategoryDataset"%>
<%@page import="org.jfree.chart.urls.CategoryURLGenerator"%>
<%@page import="org.jfree.data.general.PieDataset"%>
<%@page import="org.jfree.chart.urls.PieURLGenerator"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.modules.message.IRmMessageConstants"%>
<%
	WebChart chart = new WebChart();
	List<String[]> lResult = RmProjectHelper.getCommonServiceInstance().query("select template_id as rm_key, count(template_id) as rm_count from RM_MESSAGE group by template_id", new RowMapper() {
	    public Object mapRow(ResultSet rs, int i) throws SQLException {
	    	return new String[]{rs.getString("rm_key"), rs.getString("rm_count")};
	    }
	});
	for(String[] array : lResult) {
		chart.setValue(array[0], Integer.parseInt(array[1]));
	}
	final String contextPath = request.getContextPath();
	//饼图的链接定制
	PieURLGenerator pug = new PieURLGenerator() {
		public String generateURL(PieDataset dataset, Comparable key, int pieIndex) {
			return contextPath + "/rmmessage?REQUEST_IS_READ_ONLY=1&template_id=" + key;
		}
	};
	//饼图2D
	String filename_pie = chart.generatePieChart("按" + IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id") + "统计", session, new PrintWriter(out), pug, false); //如最后一位参数是true，则为3D饼图
	String graphURL_pie = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_pie;
	
	//柱图的链接定制
	CategoryURLGenerator cug = new CategoryURLGenerator() {
	    public String generateURL(CategoryDataset dataset, int series, int category) {
	    	return contextPath + "/rmmessage?REQUEST_IS_READ_ONLY=1&template_id=" + dataset.getColumnKey(series);
	    }
	};
	//柱图
	String filename_bar = chart.generateBarChart("按" + IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id") + "统计", session, new PrintWriter(out), cug, true);
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