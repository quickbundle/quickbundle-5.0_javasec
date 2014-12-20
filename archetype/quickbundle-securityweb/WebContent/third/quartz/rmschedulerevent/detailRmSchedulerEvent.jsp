<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo" %>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.util.IRmSchedulerEventConstants" %>
<%  //取出本条记录
	RmSchedulerEventVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmSchedulerEventVo)request.getAttribute(IRmSchedulerEventConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmSchedulerEventAction";
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
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getJob_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getJob_group())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTrigger_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTrigger_group())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getFire_instance_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getEvent_type())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getCost_millis())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getResult_status())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getCreate_time())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getCreate_ip())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getUuid())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("is_archive")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getIs_archive())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getResult())%>&nbsp;</td>
	</tr>
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<table align="center">
	<tr>
		<td><br>
		<%--
			<input type="button" class="button_ellipse" id="button_update" value="修改" onclick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onclickto="javascript:delete_onClick();">
		--%>
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onclick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<%-- 开始子表信息，带页签集成多个子表 --%>
<script type="text/javascript">
var childTableTabs  =  new Array(

	null
);
</script>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<%-- 结束子表信息 --%>

</form>
</body>
</html>