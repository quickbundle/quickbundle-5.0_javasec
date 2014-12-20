<%@ page isErrorPage="true" %>
<%@page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.apache.struts.action.ActionMessages"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.base.exception.RmExceptionVo"%>
<%@page import="org.quickbundle.third.excel.RmExcelHandler"%>
<%@page import="org.quickbundle.third.struts.action.RmExceptionHandler"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%try { %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
A {
	 COLOR: blue; FONT-FAMILY: verdana, arial, sans-serif; TEXT-DECORATION: underline;
}
 A:LINK {
	 COLOR: blue; FONT-FAMILY: verdana, arial, sans-serif; TEXT-DECORATION: underline;
}
 A:VISITED {
	 COLOR: blue; FONT-FAMILY: verdana, arial, sans-serif; TEXT-DECORATION: underline;
}
A:ACTIVE {
	 COLOR: blue; FONT-FAMILY: verdana, arial, sans-serif; TEXT-DECORATION: underline;
}
 A:HOVER {
	 COLOR: blue; FONT-FAMILY: verdana, arial, sans-serif; TEXT-DECORATION: underline;
}
</style>
<title></title>
<script type="text/javascript">
//<!--
	function isIFrameSelf(){
		try {
			if(window.top ==window) {
				return false;
			} else {
				return true;
			}
		}catch(e){return true;}
	}
	
	function toHome() {
		//if(!isIFrameSelf()){ 
			window.history.back();
		//}
	}
	//window.setTimeout("toHome()",5000);
//-->
</script>
</head>

<body>
<div style="padding:50px;">
<%
	Throwable e = exception;
	if (request.getAttribute("org.apache.struts.action.EXCEPTION") != null) {
		e = (Exception) request.getAttribute("org.apache.struts.action.EXCEPTION");
	}
	if(e != null) {
		RmExceptionVo exceptionVo = RmExceptionHandler.getException(e);
%>
	<div>
		<div style="font-size:100%;">很抱歉，您的操作出错了，请<a href="javascript:toHome();" style="font-size:110%;">点击这里</a>返回上一页重试！如仍存在该问题，请与系统管理员联系。</div>
		<div style="padding:10px;color:RED;font-weight:bold;font-size:130%;">
			<span style="color:black;font-weight:normal;"><%=exceptionVo.getTitle() != null && exceptionVo.getTitle().length() > 0 ? exceptionVo.getTitle() + "：" : ""%></span>
			<%=exceptionVo.getDescription() != null ? exceptionVo.getDescription() : RmStringHelper.prt(e.getLocalizedMessage())%>
		</div>
	</div>
	<div style="display:<%=RmConfig.getSingleton().isSystemDebugMode() ? "block" : "none" %>;color:#FFFFFF">
<% 
		e.printStackTrace();
		out.println(e.toString());
		out.println(RmStringHelper.getStackTraceStr(e, 50).replaceAll("\\n", "\n<br>"));
		if(e.getCause() != null) {
			out.println("\n<br><br><br>Caused by: " + e.getCause().getClass().getName() + "<br>");
			out.println(RmStringHelper.getStackTraceStr(e.getCause(), 50).replaceAll("\\n", "\n<br></span>"));
		}
	}
%>
	</div>
	
<%
	if (request.getAttribute("org.apache.struts.action.ERROR") != null) {
		ActionMessages am = (ActionMessages) request.getAttribute("org.apache.struts.action.ERROR");
		for(Iterator itP = am.properties(); itP.hasNext(); ) {
			String key = itP.next().toString();
	          //out.println("<br/>\n");
		}
	}
%>
</div>
</body>
</html>
<%
} catch(Throwable e3) {
	e3.printStackTrace();
}
%>