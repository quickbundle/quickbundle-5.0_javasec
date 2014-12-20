<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="org.quickbundle.third.quartz.util.ISchedulerConstants"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.third.quartz.jobtrigger.vo.JobTriggerVo" %>
<%@ page import="org.quickbundle.third.quartz.jobtrigger.util.IJobTriggerConstants" %>
<%  //取出List
	List<JobTriggerVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IJobTriggerConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IJobTriggerConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "JobTriggerAction";
	var rmJspPath = "";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template",["trigger_name", "trigger_group"]);  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return false;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + ids;
		form.submit();
	}
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template",["trigger_name", "trigger_group"]);  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return false;
		}
		if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			return false;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + ids;
    	form.submit();
	}
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
  	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/third/quartz/jobtrigger" + rmJspPath + "/insertJobTrigger.jsp";
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.id.value = thisId;  //赋值thisId给隐藏值id
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=detail";
		form.submit();
	}
	function toDoDblClick(thisId, thisCheckbox) {
		detail_onClick([thisCheckbox.getAttribute("trigger_name"), thisCheckbox.getAttribute("trigger_group")]);
	}
	function pause_onClick(){
		var ids = findSelections("checkbox_template",["trigger_name", "trigger_group"]);  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=pause&ids=" + ids;
    	form.submit();
	}
	function resume_onClick(){
		var ids = findSelections("checkbox_template",["trigger_name", "trigger_group"]);  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=resume&ids=" + ids;
    	form.submit();
	}
	var defaultListTableHeightPara = 50;
</script>
</head>
<body>
<form name="form" method="post">

<div id="div_simple">
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%></td>
			<td>
				<input type="text" class="text_field" name="trigger_name" inputName="<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%></td>
			<td>
				<input type="text" class="text_field" name="trigger_group" inputName="<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>" maxLength="100"/>
				<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IJobTriggerConstants.TABLE_NAME_CHINESE %>列表</b> (服务器时间：<%=RmDateHelper.getSysDateTimeMillis() %>)</td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<input type="button" class="button_ellipse" id="button_pauseCheckbox" value="暂停" onClick="javascript:pause_onClick();" title="暂停所选的记录"/>
		<input type="button" class="button_ellipse" id="button_resumeCheckbox" value="继续" onClick="javascript:resume_onClick();" title="继续所选的记录"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="trigger_name"/>
		<bean:define id="rmDisplayName" name="rmBean" property="trigger_name"/>
		<bean:define id="trigger_group" name="rmBean" property="trigger_group"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" trigger_name="<%=rmValue%>" trigger_group="<%=trigger_group%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IJobTriggerConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="trigger_name"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>' property="trigger_name" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>' property="trigger_group" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("job_name")%>' property="job_name" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("job_group")%>' property="job_group" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("cron_expression")%>' property="cron_expression" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("next_fire_time")%>' property="next_fire_time" sortable="false">
		<bean:define id="next_fire_time" name="rmBean" property="next_fire_time"/>
		<%
		long lNext_fire_time = Long.parseLong(String.valueOf(next_fire_time));
		out.print(lNext_fire_time > 0 ? RmDateHelper.getFormatDateTimeDesc(lNext_fire_time) : ""); 
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("prev_fire_time")%>' property="prev_fire_time" sortable="false">
		<bean:define id="prev_fire_time" name="rmBean" property="prev_fire_time"/>
		<%
		long lPrev_fire_time = Long.parseLong(String.valueOf(prev_fire_time));
		out.print(lPrev_fire_time > 0 ? RmDateHelper.getFormatDateTimeDesc(lPrev_fire_time) : ""); 
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("priority")%>' property="priority" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("trigger_state")%>' property="trigger_state" sortable="false">
		<bean:define id="trigger_state" name="rmBean" property="trigger_state"/>
		<%=RmStringHelper.prt(ISchedulerConstants.MAP_TRIGGER_STATE.get(String.valueOf(trigger_state)))%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("start_time")%>' property="start_time" sortable="false">
		<bean:define id="start_time" name="rmBean" property="start_time"/>
		<%
		long lStart_time = Long.parseLong(String.valueOf(start_time));
		out.print(lStart_time > 0 ? RmDateHelper.getFormatDateTimeDesc(lStart_time) : ""); 
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("end_time")%>' property="end_time" sortable="false">
		<bean:define id="end_time" name="rmBean" property="end_time"/>
		<%
		long lEnd_time = Long.parseLong(String.valueOf(end_time));
		out.print(lEnd_time > 0 ? RmDateHelper.getFormatDateTimeDesc(lEnd_time) : ""); 
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("calendar_name")%>' property="calendar_name" sortable="false"/>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("misfire_instr")%>' property="misfire_instr" sortable="false">
		<bean:define id="misfire_instr" name="rmBean" property="misfire_instr"/>
		<%=RmStringHelper.prt(ISchedulerConstants.MAP_MISFIRE_INSTRUCTION.get(String.valueOf(misfire_instr)))%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IJobTriggerConstants.TABLE_COLUMN_CHINESE.get("description")%>' property="description" sortable="false"/>
</layout:collection>

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
</form>

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IJobTriggerConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IJobTriggerConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>