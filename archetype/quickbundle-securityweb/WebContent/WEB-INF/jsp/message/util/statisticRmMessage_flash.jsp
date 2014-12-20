<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/swfobject.js"></script>
<script type="text/javascript">
	swfobject.embedSWF("<%=request.getContextPath()%>/third/ofc/open-flash-chart.swf", "my_chart_pie", "400", "400", "9.0.0", 
		"expressInstall.swf", {"data-file":"<%=request.getContextPath()%>/rmmessage/statistic/flash/data?type=pie"});
	swfobject.embedSWF("<%=request.getContextPath()%>/third/ofc/open-flash-chart.swf", "my_chart_bar", "500", "400", "9.0.0", 
		"expressInstall.swf", {"data-file":"<%=request.getContextPath()%>/rmmessage/statistic/flash/data?type=bar_3d"});
</script>
</head>
<body>
<div align="center" style="padding:5px 0px 0px 3px">
	<div id="my_chart_pie"></div>
</div>
<div align="center" style="padding:5px 0px 0px 3px">
	<div id="my_chart_bar"></div>
</div>
</body>
</html>