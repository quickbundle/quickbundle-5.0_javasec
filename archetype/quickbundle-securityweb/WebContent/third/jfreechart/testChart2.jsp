<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="org.quickbundle.third.jfreechart.WebChart"%>
<%@ page import = "java.io.PrintWriter" %>
<%

WebChart chart = new WebChart();
chart.setValue("六月",500);
chart.setValue("七月",580);
chart.setValue("八月",828);

String filename = chart.generatePieChart("www.SenTom.net 网站访问统计表", session, new

PrintWriter(out));
String graphURL = request.getContextPath() + "/rm/DisplayChart?filename=" + filename;

%>

<HTML>
<HEAD>
<TITLE>www.sentom.net</TITLE>
</HEAD>
<BODY>
<P ALIGN="CENTER">
<img src="<%= graphURL %>"  border=0 usemap="#<%=

filename %>">
</P>
</BODY>
</HTML>