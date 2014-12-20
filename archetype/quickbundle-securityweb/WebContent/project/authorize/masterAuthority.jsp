<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm execute</title>
</head>
<body>
<script type="text/javascript">
	writeTableTop('执行页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>

<%
	String bs_keyword = request.getParameter("bs_keyword");
	String old_resource_id = request.getParameter("old_resource_id");
%>

	<table class="table_div_content" align="center"> 
		<tr>
			<td>
				<iframe name="resource_frame" scrolling="no" frameborder="0" WIDTH="100%" HEIGHT="80" src="<%=request.getContextPath()%>/orgauth/rmauthorizeresource/initRmResource.jsp?bs_keyword=<%=bs_keyword%>&old_resource_id=<%=old_resource_id%>"></iframe>
			</td>
		</tr>
		<!-- //TODO -->
		<tr id="authorize_frame_tr">
			<td>
				<iframe name="authorize_frame" id="authorize_frame_id" frameborder="0" WIDTH="100%" HEIGHT="360" src="<%=request.getContextPath()%>/orgauth/rmauthorizeresource/middle/listRm_authorize_resource_record.jsp?bs_keyword=<%=bs_keyword%>&old_resource_id=<%=old_resource_id%>"></iframe>
			</td>
		</tr>
		
	</table>	
	
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>