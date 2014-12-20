<%@ page errorPage="/jsp/common/err.jsp" %><%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%><%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/tld/struts-layout.tld" prefix="layout" %><%@page import="org.quickbundle.config.RmConfig"%><%@page import="org.quickbundle.tools.support.log.RmLogHelper"%><%@page import="org.quickbundle.tools.helper.RmStringHelper"%><%@page import="org.quickbundle.tools.helper.RmJspHelper"%><%@page import="org.quickbundle.project.IGlobalConstants"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery.form.js"></script>
<script type="text/javascript"> 
var dir_base = "<%=request.getContextPath()%>";
var windowOpenReturnValue = null;
var systemDebugMode = <%=RmConfig.getSingleton().isSystemDebugMode() ? "true" : "false"%>;
var resetListJspQueryInputValue = true;
var hiddenQueryDivValue = true;
doInitForm = function(e){
	try {
		if(!(/msie/i.test(navigator.userAgent)) && document.forms.length > 0) {
			form = document.forms[0];
		}
	}catch(e){}
};
if(window.addEventListener) {
	window.addEventListener("load",doInitForm,false);
} else {
	window.attachEvent("onload", doInitForm);
}
<%
{ //system message
	HttpSession session2 = RmJspHelper.getSession(request, false);
	if(session2 != null) {
		Object systemMessage = session2.getAttribute(IGlobalConstants.SystemPara.system_message.name());
		if(systemMessage != null) {
			out.print("alert(\"" + RmStringHelper.replaceStringToScript(systemMessage) + "\");");
			session2.removeAttribute(IGlobalConstants.SystemPara.system_message.name());
		}
	}
}
%></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-third.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-tools.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-behavior.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/json2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/main.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/jquery/css/smoothness/jquery-ui-1.11.2.custom.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-ui-1.11.2.custom.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/rm-project.js"></script>