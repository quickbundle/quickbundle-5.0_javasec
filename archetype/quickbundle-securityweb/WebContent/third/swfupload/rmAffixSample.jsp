<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<style type="text/css">
<!--
.rm_affix {
	behavior:url("<%=request.getContextPath()%>/css/htc/rm-affix.htc");
}
-->
</style>


<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>批量上传的例子</title>
<script type="text/javascript">

</script>
</head>
<body>
<script type="text/javascript">
	writeTableTop('详细页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>

	<tr>
		<td align="right">单据附件：</td>
		<td colspan="3"><span class=rm_affix record_id=1000100100000000001 bs_keyword=RM_AFFIX_SAMPLE>&nbsp;</span></td>
	</tr>
</table>

</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">

</script>
	
