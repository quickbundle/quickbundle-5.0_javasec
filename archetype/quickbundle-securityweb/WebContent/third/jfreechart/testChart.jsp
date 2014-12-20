<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.jfree.chart.JFreeChart"%>
<%@ page import="org.jfree.chart.plot.PiePlot"%>
<%@ page import="org.jfree.chart.ChartRenderingInfo"%>
<%@ page import="org.jfree.chart.servlet.ServletUtilities"%>
<%@ page import="org.jfree.chart.entity.StandardEntityCollection"%>
<%

	DefaultPieDataset data = new DefaultPieDataset();
	data.setValue("六月",500);
	data.setValue("七月",580);
	data.setValue("八月",828);
	
	PiePlot plot = new PiePlot(data);
	
	JFreeChart chart = new JFreeChart("",JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	chart.setBackgroundPaint(java.awt.Color.white);//可选，设置图片背景色
	chart.setTitle("www.SenTom.net 网站访问统计表");//可选，设置图片标题
	
	ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
	//500是图片长度，300是图片高度
	
	String filename = ServletUtilities.saveChartAsPNG(chart, 500, 300, info, session);
	String graphURL = request.getContextPath() + "/rm/DisplayChart?filename=" + filename;

%>
<%@page import="org.jfree.data.general.DefaultPieDataset"%>
<HTML>
<HEAD>
<TITLE>www.sentom.net</TITLE>
</HEAD>
<BODY>
<P ALIGN="CENTER">
<img src="<%= graphURL %>" width=500 height=300 border=0 usemap="#<%= filename

%>">
</P>
</BODY>
</HTML>