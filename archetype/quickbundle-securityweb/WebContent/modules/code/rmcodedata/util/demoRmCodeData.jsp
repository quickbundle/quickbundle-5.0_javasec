<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmJspHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.modules.code.rmcodedata.util.IRmCodeDataConstants" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
</head>
<body>
<script type="text/javascript">
	writeTableTop('排序页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">
<br/>
<h3>列表参照例子</h3>
<table class="table_div_content">
	<tr>
		<td align="right"><%=IRmCodeDataConstants.TABLE_NAME_CHINESE%></td>
		<td>
			<input type="text" class="text_field_reference_readonly" hiddenInputId="record_id" name="record_id_name"/><input type="hidden" name="record_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.record_id, form.record_id_name),'<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmCodeDataAction.do?cmd=queryReference&referenceInputType=radio');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表例子 -->
<script type="text/javascript">
var childTableTabs  =  new Array(
	new Array ("子表信息0","<%=request.getContextPath()%>/RmCodeDataConditionAction.do?cmd=queryAll&code_type_id=0"),
	new Array ("子表信息1","<%=request.getContextPath()%>/RmCodeDataConditionAction.do?cmd=queryAll&code_type_id=1")
); 
</script>
<br/><br/>
<h3>带页签集成多个子表例子</h3>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

<input type="hidden" name="queryCondition" value=""/>

</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmCodeDataConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmCodeDataConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>
