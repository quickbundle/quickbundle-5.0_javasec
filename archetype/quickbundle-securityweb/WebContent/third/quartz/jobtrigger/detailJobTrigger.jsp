<%@page import="org.quickbundle.third.quartz.util.ISchedulerConstants"%>
<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="java.util.Map.Entry"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.third.quartz.jobtrigger.vo.JobTriggerVo" %>
<%@ page import="org.quickbundle.third.quartz.jobtrigger.util.IJobTriggerConstants" %>
<%  //取出本条记录
	JobTriggerVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (JobTriggerVo)request.getAttribute(IJobTriggerConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "JobTriggerAction";
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
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTrigger_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTrigger_group())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("job_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getJob_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("job_group")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getJob_group())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("cron_expression")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getCron_expression())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("next_fire_time")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getNext_fire_time() > 0 ? RmDateHelper.getFormatDateTimeDesc(resultVo.getNext_fire_time()): "" )%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("prev_fire_time")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getPrev_fire_time() > 0 ? RmDateHelper.getFormatDateTimeDesc(resultVo.getPrev_fire_time()): "" )%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("priority")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getPriority())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_state")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTrigger_state())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("start_time")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getStart_time() > 0 ? RmDateHelper.getFormatDateTimeDesc(resultVo.getStart_time()): "" )%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("end_time")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getEnd_time() > 0 ? RmDateHelper.getFormatDateTimeDesc(resultVo.getEnd_time()): "" )%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("calendar_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getCalendar_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("misfire_instr")%>：</td>
		<td><%=RmStringHelper.prt(ISchedulerConstants.MAP_MISFIRE_INSTRUCTION.get(String.valueOf(resultVo.getMisfire_instr())))%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<table name="tb_params">
	<tr>
		<th>名称</th>
		<th>描述</th>
		<th ></th>
		<th></th>
	</tr>
	<%
	if(resultVo != null && resultVo.getDataMap() != null){
		for(Entry<String, Object> en : resultVo.getDataMap().entrySet()){
	%>		
	<tr param='1'>
		<td >
			<input type="text" class="text_field_readonly" pname="1" value="<%=en.getKey() %>"></input>
		</td>
		<td>
			<input type="text" class="text_field_readonly" pdes="1" value="<%=en.getValue() %>"></input>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<%
		}
	}
	%>
</table>
<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getTrigger_name())%>,<%=RmStringHelper.prt(resultVo.getTrigger_group())%>">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onclick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onclickto="javascript:delete_onClick();">
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