<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="java.util.Map"%>
<%@page import="org.quartz.JobDataMap"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.third.quartz.util.ISchedulerConstants"%>
<%@page import="org.quartz.Scheduler"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.third.quartz.jobexecuting.vo.JobExecutingVo" %>
<%@ page import="org.quickbundle.third.quartz.jobexecuting.util.IJobExecutingConstants" %>
<%  //取出List
	List<JobExecutingVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IJobExecutingConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IJobExecutingConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "JobExecutingAction";
	var rmJspPath = "";
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function start_onClick(){ 
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=start";
    	form.submit();
  	}
	function pauseAll_onClick(){ 
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=pauseAll";
    	form.submit();
  	}
	function resumeAll_onClick(){ 
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=resumeAll";
    	form.submit();
  	}
	function standby_onClick(){ 
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=standby";
    	form.submit();
  	}
	function shutdown_onClick(){ 
    	if(!getConfirm("本操作会完全停止任务调度器实例，且不能重启(必须重启中间件才能重启调度器服务)，您确定吗？")) {
  			return false;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=shutdown";
    	form.submit();
  	}
	var defaultListTableHeightPara = 50;
</script>
</head>
<body>
<form name="form" method="post">
<%
	Scheduler scheduler = (Scheduler)RmBeanFactory.getBean(ISchedulerConstants.QUARTZ_SHEDULER);
%>
<div style="padding:3px 0px 3px 10px">
<%!String printStatus(HttpServletRequest request, boolean yes) {
	if(yes) {
		return "<img src=" + request.getContextPath() + "/images/yes.png>";
	} else {
		return "<img src=" + request.getContextPath() + "/images/no.png>";
	}
}
%>
	<span style="font-size: 120%; font-weight:bold; padding:0px 5px">
		任务调度器实例<%=printStatus(request, !scheduler.isShutdown())%>
		<!-- Started<%=printStatus(request, scheduler.isStarted())%> -->
		&nbsp;&nbsp;任务调度<%=printStatus(request, !scheduler.isInStandbyMode())%>
	</span>
	&nbsp;&nbsp;<input type="button" class="button_ellipse" value="启动调度器" onclickto="javascript:start_onClick();" />
	&nbsp;&nbsp;<input type="button" class="button_ellipse" value="挂起调度器" onclickto="javascript:standby_onClick();" />
	&nbsp;&nbsp;<input type="button" class="button_ellipse" value="暂停所有任务" onclickto="javascript:pauseAll_onClick();" />		
	&nbsp;&nbsp;<input type="button" class="button_ellipse" value="继续所有任务" onclickto="javascript:resumeAll_onClick();" />
	<!-- 
	&nbsp;&nbsp;<input type="button" class="button_ellipse" value="停止调度器" onclickto="javascript:shutdown_onClick();" />	
	 -->	
</div>
<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IJobExecutingConstants.TABLE_NAME_CHINESE %>列表</b> (服务器时间：<%=RmDateHelper.getSysDateTimeMillis() %>)</td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="job_name"/>
		<bean:define id="rmDisplayName" name="rmBean" property="job_name"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		out.print(rmOrderNumber);
	%>
		<bean:define id="rmValue" name="rmBean" property="job_name"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("job_name")%>' property="job_name" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("job_group")%>' property="job_group" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>' property="trigger_name" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>' property="trigger_group" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("recovering")%>' property="recovering" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="recovering"/>
		<%=RmGlobalReference.get(IJobExecutingConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("refire_count")%>' property="refire_count" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("fire_time")%>' property="fire_time" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="fire_time"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("scheduled_fire_time")%>' property="scheduled_fire_time" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="scheduled_fire_time"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("previous_fire_time")%>' property="previous_fire_time" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="previous_fire_time"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("next_fire_time")%>' property="next_fire_time" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="next_fire_time"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("job_run_time")%>' property="job_run_time" sortable="false">
		<bean:define id="job_run_time" name="rmBean" property="job_run_time"/>
		<%long lJob_run_time = Long.parseLong(String.valueOf(job_run_time));%>
		<span title="<%=lJob_run_time%>毫秒"><%=RmDateHelper.parseToTimeDesciption(lJob_run_time) %></span>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%>' property="fire_instance_id" sortable="false"/>
	<layout:collectionItem width="8%" title='合并参数'>
		<bean:define id="dataMap" name="rmBean" property="dataMap"/>
		<%
		JobDataMap jdm = (JobDataMap)dataMap;
		for(Map.Entry<String, Object> en : jdm.entrySet()) {
			out.print(en.getKey() + "=" + en.getValue() + ";");
		}
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobExecutingConstants.TABLE_COLUMN_CHINESE.get("result")%>' property="result" sortable="false"/>
</layout:collection>

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
</form>

</body>
</html>