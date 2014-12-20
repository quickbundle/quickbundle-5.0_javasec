<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/project/sample/login/loginRedirect.jsp" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobalMin.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
<!--
function writeBackVerifyCode() {
	if(document.getElementById("onload_js")){  
	    var el=document.getElementById("onload_js");  
	    var p=el.parentNode;    
	    p.removeChild(el);     
	}  
	var head = document.getElementsByTagName("head").item(0);    
	var script = document.createElement("script");    
	script.src = "<%=request.getContextPath()%>/jsp/support/verify/onLoad_initValue.jsp";  
	script.id = "onload_js";
	head.appendChild(script);  
}
function about_onClick() {
	//TODO 扩展关于
}
function submit_onClick() {
	if(form.login_id.value == "") {
		alert("请输入用户名！");
		form.login_id.focus();
		return false;
	}
}
//-->
</script>
</head>

<body style="background-color:#FFFFFF;" onload="form.login_id.focus();">
<br/>
<h1 align="center">&nbsp;</h1>
<form name="form"  method="post" target="_top" action="<%=request.getContextPath()%>/RmLoginAction.do?cmd=login" >
<!-- background="<%=request.getContextPath()%>/images/default/login.gif" -->
<table align="center" border="0" cellpadding="1">
	<tr height="100">
		<td colspan="2" align="center">
			<h1 title="rename in i18n/ApplicationResources*.properties"><bean:message key="qb.web_title"/></h1>
		</td>
	</tr>
	<tr height="15">
		<td nowrap align="right"><bean:message key="qb.login_id"/></td>
		<td align="left"><input type="text" class="text_field" style="width:140px" name="login_id" maxLength="50" value="admin"></td>
	</tr>
	<tr height="15">
		<td nowrap align="right"><bean:message key="qb.password"/></td>
		<td align="left"><input type="password" class="text_field" style="width:140px" name="password" maxLength="20" value="111111"></td>
	</tr>
	<%if(RmConfig.getSingleton().isLoginValidateVerifyCode()) { %>
	<tr height="15">
		<td nowrap align="right"><bean:message key="qb.verifyCode"/></td>
		<td align="left">
			<input type="text" maxlength=4 size=6 name=verifyCode value=""/>
			<img onload="writeBackVerifyCode();" style="cursor:pointer" title="看不清？再点一下" onclick="javascript:this.src='<%=request.getContextPath()%>/jsp/support/verify/buildVerifyCode.jsp?height=24&width=70&rmrandom=' + new Date()" src="<%=request.getContextPath()%>/jsp/support/verify/buildVerifyCode.jsp?height=24&width=70" />
		</td>
	</tr>
	<%} %>
	<tr height="15" align="left">
		<td>&nbsp;</td>
		<td>
			<input name="button_submit" type="submit" value="<bean:message key="qb.login"/>" class="button_ellipse" /> 
		</td>
	</tr>
	<tr>
		<td nowrap colspan="2">
			<% 
				if(request.getAttribute("alertStr") != null && request.getAttribute("alertStr").toString().length() > 0) {
				    out.print("<div align='center' style='color:red'>" + request.getAttribute("alertStr").toString() + "</div>");
				}
			%>
		</td>
	</tr>
</table>              

<input type="hidden" name="isSubmit" value="1">

</form>

</body>
</html>
<script type="text/javascript">
	if(this != top) {
		top.location.href = this.location.href;
	}
</script>