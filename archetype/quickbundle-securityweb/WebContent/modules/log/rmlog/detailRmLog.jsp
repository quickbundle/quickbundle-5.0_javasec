<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.modules.log.rmlog.vo.RmLogVo" %>
<%@ page import="org.quickbundle.modules.log.rmlog.util.IRmLogConstants" %>
<%  //取出本条记录
	RmLogVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmLogVo)request.getAttribute(IRmLogConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmLogAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.submit();
	}  
</script>
</head>
<body>
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
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("log_type_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLog_type_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_date")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getAction_date())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_ip")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getAction_ip())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_module")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getAction_module())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_type")%>：</td>
		<td><%=RmGlobalReference.get(IRmLogConstants.DICTIONARY_RM_OPERATION_TYPE, resultVo.getAction_type())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getOwner_org_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getUser_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getUser_id_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_uuid")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getAction_uuid())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("content")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getContent())%>&nbsp;</td>
	</tr>
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onClick="javascript:delete_onClick();">
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(

	null
);
</script>
<br/><br/>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

</form>
</body>
</html>