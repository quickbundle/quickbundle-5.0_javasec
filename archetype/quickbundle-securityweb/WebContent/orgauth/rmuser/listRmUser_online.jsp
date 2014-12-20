<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@page import="java.sql.Timestamp"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.base.web.servlet.RmHolderServlet"%>
<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.orgauth.rmuser.vo.RmUserVo" %>
<%@page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%try{ %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.quickbundle.project.listener.RmSessionListener"%><html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmUserAction";
	var rmJspPath = "";
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function forceLogoutUser_onClick(session_id, user_id) {
		if(confirm("是否强制下线该用户？")) {  //如果用户在确认对话框按"确定"
			form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=forceLogoutUser&user_id=" + user_id + "&session_id=" + session_id;
			form.submit();
		}
	}
	function toDoDblClick(thisId) {
	}
</script>
</head>
<body>
<form name="form" method="post">

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b>在线用户列表 <span style='color:RED'><%=RmStringHelper.prt(request.getAttribute(IRmUserConstants.ONLINE_USER_MODE)) %></span></b> (超时退出时间：<%=RmDateHelper.parseToTimeDesciption(session.getMaxInactiveInterval()*1000)%>)</td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmUserConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>' property="name" sortable="false">
		<bean:define id="rmValue" name="rmBean" property="name"/>
		<bean:define id="id" name="rmBean" property="id"/>
		<%if(rmValue != null && rmValue.toString().length() > 0){%>
			<a target="_blank" href="<%=request.getContextPath() %>/RmUserAction.do?cmd=detail&id=<%=id%>" ><%=rmValue%></a>
		<%}%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>' property="login_id" sortable="false">
		<bean:define id="login_id" name="rmBean" property="login_id"/>
		<bean:define id="sessionId" name="rmBean" property="sessionId"/>
		<span title="<%=sessionId%>"><%=login_id%></span>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='权限类型' property="admin_type" sortable="false" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="admin_type"/>
		<%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='访问时间' property="last_login_date" sortable="false">
		<bean:define id="last_login_date" name="rmBean" property="last_login_date"/>
		<bean:define id="creationTime" name="rmBean" property="creationTime"/>
		<%
			if(last_login_date != null) {
				out.print("<span title='" + RmStringHelper.prt(last_login_date, 19) + " 登录系统'>" + RmDateHelper.parseToTimeDesciption(System.currentTimeMillis() - ((Timestamp)last_login_date).getTime()));
			} else if(creationTime != null && Long.parseLong(creationTime.toString()) > 0) {
				out.print("<span title='" + RmDateHelper.getFormatDateTimeDesc(Long.parseLong(creationTime.toString())) + " 匿名访问系统' style='color:blue'>" + RmDateHelper.parseToTimeDesciption(System.currentTimeMillis() - Long.parseLong(creationTime.toString())));
			}
		%>
	</layout:collectionItem>
	<layout:collectionItem width="6%" title='用户IP' property="last_login_ip" sortable="false"/>
	<layout:collectionItem width="6%" title='累计登录' property="login_sum" sortable="false" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="login_sum"/>
		<%= rmValue != null && ((Integer)rmValue).intValue()>=0 ? rmValue : ""%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='最后操作时间' property="lastAccessedTime" sortable="false" style="text-align:center;">
		<bean:define id="lastAccessedTime" name="rmBean" property="lastAccessedTime"/>
		<%=((Long)lastAccessedTime).longValue() > 0 ? RmStringHelper.prt(new Timestamp((Long)lastAccessedTime).toString(), 19) : "" %>
	</layout:collectionItem>
	<layout:collectionItem width="5%" title='思考时间' property="lastAccessedTime" sortable="false" style="text-align:center;">
		<bean:define id="lastAccessedTime" name="rmBean" property="lastAccessedTime"/>
		<%if(((Long)lastAccessedTime).longValue() > 0) {
			long thinkTime = System.currentTimeMillis()- ((Long)lastAccessedTime).longValue();
		%><%=thinkTime > 1000*60*5 ? "<B style='color:RED'>" : ""%><%=RmDateHelper.parseToTimeDesciption(thinkTime) %><%=thinkTime > 1000*60*5 ? "</B>" : ""%><%} %>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='距超时退出' property="maxInactiveInterval" sortable="false" style="text-align:center;">
		<bean:define id="lastAccessedTime" name="rmBean" property="lastAccessedTime"/>
		<bean:define id="maxInactiveInterval" name="rmBean" property="maxInactiveInterval"/>
		<% if(((Long)lastAccessedTime).longValue() > 0) {
				long thinkTime = System.currentTimeMillis()- ((Long)lastAccessedTime).longValue();
				long remainTimeout = Long.parseLong(maxInactiveInterval.toString()) - thinkTime;
				out.print(RmDateHelper.parseToTimeDesciption(remainTimeout));
			}
		%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='服务器名' property="clusterNodeId" sortable="false" style="text-align:center;"/>
	<layout:collectionItem width="4%"  title="操作" style="text-align:center;">
		<bean:define id="session_id" name="rmBean" property="sessionId"/>
		<bean:define id="id" name="rmBean" property="id"/>
		<div id="div_operation">
		<%if(session.getId().equals(session_id)) {%>
		
		<%} else if(session_id != null){ %>
		<a href="javascript:forceLogoutUser_onClick('<%=session_id%>', '<%=id%>')">强制下线</a>
		<%} %>
		</div>
	</layout:collectionItem>
	</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
</form>

</body>
</html>
<%}catch(Exception e){e.printStackTrace();}%>