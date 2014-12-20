<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.util.RmSequenceMap"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%  //取出List
	String queryCondition = session.getAttribute(IRmUserConstants.REQUEST_QUERY_CONDITION).toString();
	Integer recordCount = Integer.parseInt(session.getAttribute("RECORD_COUNT").toString());
	session.removeAttribute(IRmUserConstants.REQUEST_QUERY_CONDITION);
	session.removeAttribute("RECORD_COUNT");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmJspPath = "";
	function export_onClick(){  //导出Excel
		form.action="<%=request.getContextPath()%>/orgauth/rmuser" + rmJspPath + "/exportRmUser_excel.jsp";
		clickAllSelectMultiple(form.custom_column);
		form.submit();
	}
	function checkExportNo() {
		if(form.export_all.checked) {
			document.getElementById("span_no").style.visibility = "hidden";
		} else {
			document.getElementById("span_no").style.visibility = "visible";
		}
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<h3>定制导出字段</h3>
<table class="table_div_content">
	<tr>
		<td width="20%" align="right">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td width="15%" align="right">&nbsp;</td>
		<td width="30%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><h4>选择导出数量：</h4></td>
		<td colspan="3">全部导出&nbsp;&nbsp;<input type="checkbox" name="export_all" onclick="checkExportNo();">&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class="button_ellipse" id="button_export" value="导出" onClick="javascript:export_onClick();"><br><br>
			<span id="span_no">只导出第&nbsp;&nbsp;<input name="no_from" class="text_field_half" type="text" size="2" maxlength="10" value="<%=recordCount == 0 ? "" : "1"%>">&nbsp;&nbsp;到&nbsp;&nbsp;第<input name="no_to" class="text_field_half" type="text" size="2" maxlength="10" value="<%=recordCount >= 20 ? "20" : recordCount%>">（一共有<%=recordCount%>条数据）</span>
		</td>
	</tr>
	<%
		Map<String, String> m = new RmSequenceMap<String, String>();

		m.put("name", IRmUserConstants.TABLE_COLUMN_CHINESE.get("name"));
		
		m.put("lock_status", IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status"));
		m.put("lock_status_name", IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status") + "_name");
				
		m.put("login_id", IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id"));
		
		m.put("password", IRmUserConstants.TABLE_COLUMN_CHINESE.get("password"));
		
		m.put("authen_type", IRmUserConstants.TABLE_COLUMN_CHINESE.get("authen_type"));
		
		m.put("organization_id", IRmUserConstants.TABLE_COLUMN_CHINESE.get("organization_id"));
		
		m.put("employee_id", IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id"));
		
		m.put("email", IRmUserConstants.TABLE_COLUMN_CHINESE.get("email"));
		
		m.put("admin_type", IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type"));
		m.put("admin_type_name", IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type") + "_name");
				
		m.put("description", IRmUserConstants.TABLE_COLUMN_CHINESE.get("description"));
		
		m.put("agent_status", IRmUserConstants.TABLE_COLUMN_CHINESE.get("agent_status"));
		m.put("agent_status_name", IRmUserConstants.TABLE_COLUMN_CHINESE.get("agent_status") + "_name");
				
		m.put("login_status", IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_status"));
		
		m.put("last_login_date", IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_date"));
		
		m.put("last_login_ip", IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_ip"));
		
		m.put("login_sum", IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_sum"));
		
		m.put("last_custom_css", IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_custom_css"));
		
		m.put("is_affix", IRmUserConstants.TABLE_COLUMN_CHINESE.get("is_affix"));
		
		m.put("function_permission", IRmUserConstants.TABLE_COLUMN_CHINESE.get("function_permission"));
		
		m.put("data_permission", IRmUserConstants.TABLE_COLUMN_CHINESE.get("data_permission"));
		
		m.put("custom1", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom1"));
		
		m.put("custom2", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom2"));
		
		m.put("custom3", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom3"));
		
		m.put("custom4", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom4"));
		
		m.put("custom5", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom5"));
		
		m.put("custom_xml", IRmUserConstants.TABLE_COLUMN_CHINESE.get("custom_xml"));
		
	%>
	<tr>
		<td align="right"><h4>选择字段：</h4></td>
		<td colspan="3"><br/>
			<table>
				<tr>
					<td>
						已选字段<br>
						<%=RmJspHelper.getSelectFieldMultiple("custom_column", -1, m, new String[0], " inputName='已选字段' style='height:500' ", 0)%>						
					</td>
					<td>
						<table border="0">
							<tr><td>
								<input name="moveToRight" class="button_ellipse" type="button" value="&lt;&lt;"  onClick="javascript:moveList_onClick(custom_column2, custom_column);" >		
							</td></tr>
							<tr><td>
								<input name="moveToLeft" class="button_ellipse" type="button" value="&gt;&gt;"   onClick="javascript:moveList_onClick(custom_column, custom_column2);" >		
							</td></tr>
						</table>
					</td>
					<td>
						备选字段<br>
						<%=RmJspHelper.getSelectFieldMultiple("custom_column2", -1, m, new String[0], " inputName='备选字段' style='height:500' ", 1)%>						
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>

<input type="hidden" name="queryCondition" value="<%=queryCondition%>">

</form>
</body>
</html>