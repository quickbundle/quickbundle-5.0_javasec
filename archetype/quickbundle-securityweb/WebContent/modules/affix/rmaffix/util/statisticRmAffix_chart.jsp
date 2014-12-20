<%@page contentType="text/html;charset=UTF-8"%>
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
<%@page import="org.quickbundle.third.jfreechart.WebChart"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants"%>
<%
	WebChart chart = new WebChart();
	List<String[]> lResult = RmProjectHelper.getCommonServiceInstance().query("select mime_type as rm_key, count(mime_type) as rm_count from RM_AFFIX group by mime_type", new RowMapper() {
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
			 return contextPath + "/RmAffixConditionAction.do?cmd=queryAll&mime_type=" + key;
		 }
	};
	//饼图2D
	String filename_pie = chart.generatePieChart("按" + IRmAffixConstants.TABLE_COLUMN_CHINESE.get("mime_type") + "统计", session, new PrintWriter(out), pug, false); //如最后一位参数是true，则为3D饼图
	String graphURL_pie = request.getContextPath() + "/rm/DisplayChart?filename=" + filename_pie;
	
	//柱图的链接定制
	CategoryURLGenerator cug = new CategoryURLGenerator() {
	    public String generateURL(CategoryDataset dataset, int series, int category) {
	    	return contextPath + "/RmAffixConditionAction.do?cmd=queryAll&mime_type=" + dataset.getColumnKey(series);
	    }
	    public Object clone() throws CloneNotSupportedException {
	    	return super.clone();
	    }
	};
	//柱图
	String filename_bar = chart.generateBarChart("按" + IRmAffixConstants.TABLE_COLUMN_CHINESE.get("mime_type") + "统计", session, new PrintWriter(out), cug, true);
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