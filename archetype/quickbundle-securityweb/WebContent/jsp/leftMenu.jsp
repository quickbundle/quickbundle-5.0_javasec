<%@page import="org.quickbundle.tools.context.RmUrlHelper"%>
<%@page import="org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo"%>
<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/jsp/include/rmGlobal.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>标题</title>
<meta name="keywords" content="关键字" />
<meta name="description" content="描述" />
<style type="text/css">
body {margin:0;padding:0;font:12px;line-height:20px;background:#f4f4f4;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/menu/swf-menu.js"></script>
</head>
<body>
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td valign="top">
		<table width="100%" height="100%" cellpadding="0" cellspacing="3" id="swf_menu">
			<tr>
				<td width="100%" height="100%">
				<table width="100%" border="0" cellpadding="0" cellspacing="0"
					class="left_menu">
<%
	String total_code = request.getParameter("total_code");

	//if(total_code == null || total_code.length() == 0) {
	   // total_code = "";
	//}
	
	if(total_code!=null&&!total_code.trim().equals("")){
		//IRmUserService userService = (IRmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);
		//定义功能节点的Map
		Map mPermissionFuncNode = null;//userService.getFunctionPermission(RmJspHelper.getRmLoginInfo(request).getId());
		String currentParentTotal_code = "";
		for(Iterator itMFuncNode = mPermissionFuncNode.keySet().iterator(); itMFuncNode.hasNext(); ) {
			String thisTotalCode = itMFuncNode.next().toString();
		    RmFunctionNodeVo vo = (RmFunctionNodeVo)mPermissionFuncNode.get(thisTotalCode);
		    if(!thisTotalCode.startsWith(total_code) || total_code.equals(thisTotalCode)) {
		    	continue;
		    }
		    //如果新的父节点开始了
			if(currentParentTotal_code.length() > 0 && !currentParentTotal_code.equals(thisTotalCode) && thisTotalCode.length() == (total_code.length() + 3)) {
	%>
					</div>
					<div class="clear"></div>
				</td>
			</tr>
			
	<%		}
			if(thisTotalCode.length() == (total_code.length() + 3)) {
	%>
						<tr>
							<td class="left_tit"><a href="javascript:showl(<%=thisTotalCode%>, '<%=vo.getName() %>')" hidefocus="true"><%=vo.getName() %></a></td>
						</tr>
						<tr>
							<td id="menu<%=thisTotalCode%>" style="height:100%;display:<%=currentParentTotal_code.length()==0 ? "block" : "none" %>;">
							
	<%if(currentParentTotal_code.length()==0) {%>
	<script language="javascript">
		lastMenuId = "<%=thisTotalCode%>";
		lastMenuId_name = "<%=vo.getName() %>";
	</script>	
	<%}%>

	<%
				if("1".equals(vo.getIs_leaf())) {
	%>
			<div class="left_mt">
			<iframe name="tree<%=vo.getId() %>" id="shortcutList" src="<%=RmUrlHelper.replaceParameter(vo.getData_value(), request)%>" frameborder="0"  width="100%" style="height:100%" scrolling="auto"></iframe>
	<%
				} else {
	%>
								<div class="left_mpic">
	<%
				}
				currentParentTotal_code = thisTotalCode;
			} else if(thisTotalCode.startsWith(currentParentTotal_code)) {
			    String link = RmUrlHelper.replaceParameter(vo.getData_value(), request);
	%>
									<ul>
										<li>
										<%
											if(vo.getIcon() != null && !vo.getIcon().equals(""))
											{
										 %>
										<a target="contentFrame" href="<%=link%>">
												<img src="<%=request.getContextPath()%><%=vo.getIcon()%>" width="50" height="50" border="0"> 
										</a>
										<% } %>
										<div>
										<a target="contentFrame" href="<%=link%>"><%=vo.getName() %></a></div>
										</li>
									</ul>
	<%
			}
		}
	%>
		
		
	<%}%>
				</table>
				</td>
			</tr>
		</table>
		</td>
		<td width="10" rowspan="3" id="swfleft" class="swf_clo" onclick="swfl()">
			<img src="<%=request.getContextPath()%>/images/close.gif">
		</td>
	</tr>
</table>
</body>
</html>