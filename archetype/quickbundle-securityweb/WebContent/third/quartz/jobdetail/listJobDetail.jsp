<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.third.quartz.jobdetail.vo.JobDetailVo" %>
<%@ page import="org.quickbundle.third.quartz.jobdetail.util.IJobDetailConstants" %>
<%  //取出List
	List<JobDetailVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IJobDetailConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IJobDetailConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "JobDetailAction";
	var rmJspPath = "";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template",["job_name", "job_group"]);  //取得多选框的选择项
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
 		var ids = findSelections("checkbox_template",["job_name", "job_group"]);  //取得多选框的选择项
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
		window.location="<%=request.getContextPath()%>/third/quartz/jobdetail" + rmJspPath + "/insertJobDetail.jsp";
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
		detail_onClick([thisCheckbox.getAttribute("job_name"), thisCheckbox.getAttribute("job_group")]);
	}
	function execute_onClick(){
		var ids = findSelections("checkbox_template",["job_name", "job_group"]);  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("确定执行任务？")) {  //如果用户在确认对话框按"确定"
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=exec&ids=" + ids;
	    	form.submit();
		}
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
			<td align="right"><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_name")%></td>
			<td>
				<input type="text" class="text_field" name="job_name" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_group")%></td>
			<td>
				<input type="text" class="text_field" name="job_group" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_group")%>" maxLength="100"/>
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
    <td class="tableHeaderMiddleTd"><b><%=IJobDetailConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<input type="button" class="button_ellipse" id="button_execute" value="立即执行" onClick="javascript:execute_onClick();" title="立即执行作业"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="job_name"/>
		<bean:define id="rmDisplayName" name="rmBean" property="job_name"/>
		<bean:define id="job_group" name="rmBean" property="job_group"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" job_name="<%=rmValue%>" job_group="<%=job_group%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="2%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IJobDetailConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="job_name"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="6%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_name")%>' property="job_name" sortable="false"/>
	<layout:collectionItem width="6%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_group")%>' property="job_group" sortable="false"/>
	<layout:collectionItem width="10%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_class_name")%>' property="job_class_name" sortable="false"/>
	<layout:collectionItem width="3%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("is_durable")%>' property="is_durable" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="is_durable"/>
		<%=RmGlobalReference.get(IJobDetailConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="3%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("requests_recovery")%>' property="requests_recovery" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="requests_recovery"/>
		<%=RmGlobalReference.get(IJobDetailConstants.DICTIONARY_RM_YES_NOT, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="20%" title='<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("description")%>' property="description" sortable="false"/>
</layout:collection>

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
</form>

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IJobDetailConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IJobDetailConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>