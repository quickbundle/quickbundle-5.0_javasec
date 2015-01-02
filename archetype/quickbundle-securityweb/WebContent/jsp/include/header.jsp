<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeSet"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.apache.struts.Globals"%>
<%@page import="java.util.Locale"%>
<%
	String locale = request.getParameter("locale");
	if(locale != null) {
		if(locale.length() > 0) {
			Locale lo = null;
			if(locale.indexOf("_") > -1) {
				String language = locale.substring(0, locale.indexOf("_"));
				String country = locale.substring(locale.indexOf("_")+1);
				lo = new Locale(language, country);
			} else {
				lo = new Locale(locale);
			}
			session.setAttribute(Globals.LOCALE_KEY, lo);
		} else {
			session.removeAttribute(Globals.LOCALE_KEY);	
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>rm-based architecture project</title>
<style type="text/css">
<!--
.toptab_bg{
	background: fixed url(<%=request.getContextPath()%>/images/default/top_bg.jpg) repeat-x;
}
.logo_bg{
	background-image: url(<%=request.getContextPath()%>/images/default/logo.gif);
	background-repeat: no-repeat;
}
#header{
	background: fixed url(<%=request.getContextPath()%>/images/default/top_bg1.jpg) repeat-x;
	height:34px;
}
.logo{
	background-repeat: no-repeat;
	padding-left:200px;
}
.logo span{
	float:left;
	padding-top:3px;
}
.right{
	float:right;
	margin-top:10px;
	padding-right:15px;
}
#menu{
	clear:both;
	height:26px;
	border-top: 1px solid #E4E1DA;
	border-left: none;
	background: #F2F0EE url('<%=request.getContextPath()%>/images/default/bg_btn.png') top left;
}
.buttons-active {
	font-weight: bold;
	color: #993300 !important;
	background: #FFF1BB !important;
}
/*===========top主菜单class start===================*/
.buttons{
	white-space: ;
	overflow: hidden;
	border-left: 1px solid #E4E1DA;
	background: #F2F0EE url('<%=request.getContextPath()%>/images/default/bg_btn.png') top left;
}
.buttons a:link, .buttons a:active, .buttons a:visited {
	float: left;
	display: block;
	padding: 4px 20px;
	color: #645A44;
	text-decoration: none;
	border-left: 1px solid white;
	border-right: 1px solid #E4E1DA;
}
.buttons a:hover {
	color: #395500;
	background: #F4FADF url('<%=request.getContextPath()%>/images/default/bg_btn_hover.png') top left repeat-x;
}

.nav-right{
	float:right;
	width:180px;
}
.nav-left{
	float:left;
}
#spacer{
	margin-top:-3px;
}
-->
</style>
<script language="javascript">
<!--
var selmenu = function(menuId){
    var menuLinks=document.getElementById("menu").getElementsByTagName("a");
   	if(menuLinks != null && menuLinks.length>0){
   		for(var i=0;i<menuLinks.length;i++){
   			if(menuId==menuLinks[i]){
   				menuLinks[i].className ="buttons-active";
   			}else {
   				menuLinks[i].className ="";
   			}
   		}
   	}
}
function goMain(e){
   top.infoFrame.startCover(e.href);
   return false;
}

function logout_onClick() {
	if(window.confirm("您确定要退出系统吗?")) {
		parent.location.href = "<%=request.getContextPath()%>/RmLoginAction.do?cmd=logout&toUrl=/project/sample/login/login.jsp";
	}
}
//-->
</script>
</head>
<body>
<form>
<div id="header">
	<img src="<%=request.getContextPath()%>/images/logo.png" style="padding:2px 0px 0px 2px;" align="left" alt="LOGO" id="Any"></img>
	<div class="logo">
		<span>
  			[ <bean:message key="a.welcome_you"/>， <%=RmStringHelper.prt(RmProjectHelper.getRmLoginId(request))%>  ]   
  			<%String ts = new Timestamp(System.currentTimeMillis()).toString(); %> 
	 		<%=ts.substring(0,4)%><bean:message key="a.year"/><%=ts.substring(5,7)%><bean:message key="a.month"/><%=ts.substring(8,10)%><bean:message key="a.day"/> <bean:message key='<%="a.week" + (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1)%>'/>
	 		<bean:message key="qb.language"/>：
			<select name="locale" onchange="form.submit()">
				<option value="">--<bean:message key="qb.default"/>--</option>
				<option value="en" <%=session.getAttribute(Globals.LOCALE_KEY) != null && "en".equals(((Locale)session.getAttribute(Globals.LOCALE_KEY)).toString().toLowerCase()) ? "selected" : "" %>>English</option>
				<option value="zh_CN" <%=session.getAttribute(Globals.LOCALE_KEY) != null && "zh_cn".equals(((Locale)session.getAttribute(Globals.LOCALE_KEY)).toString().toLowerCase()) ? "selected" : "" %>>简体中文</option>
			</select>
	 	</span>
	</div>
	<div class="right"> 
	  	<img src="<%=request.getContextPath()%>/images/default/m_home.gif"   align="middle"></img><a href="<%=request.getContextPath()%>/index.jsp" target="_parent"><bean:message key="qb.homepage"/></a>&nbsp;&nbsp;
	  	<a href="#" onclick="window.open('<%=request.getContextPath()%>/orgauth/changePassword.jsp')"><bean:message key="qb.change_password"/></a>
	  	<img src="<%=request.getContextPath()%>/images/default/m_logout.gif" align="middle"></img><span onclick="javascript:logout_onClick()" style="cursor:pointer"><bean:message key="qb.logout"/></span>
	</div>
</div>
<%
	String total_code = request.getParameter("total_code");
	if(total_code == null){
		total_code = "";
	}
	//高亮选中默认的菜单
	if(total_code != null && total_code.length() > 0) {
%>
<script type="text/javascript">
jQuery(document).ready(function(){
	selmenu(document.getElementById("PageLink_<%=total_code%>"));
}); 
</script>
<%
	}
	Map<String, String> mFp = new HashMap<String, String>();//.getFunctionPermission(RmJspHelper.getRmLoginInfo(request).getId());
%>
<div id="menu"<%if(!"1".equals(request.getParameter("menu"))){%> style="display:none"<%}%>>
	<div class="buttons nav-left">
    <%

    	for(Iterator<String> itMFp = mFp.keySet().iterator(); itMFp.hasNext(); ) {
			String tempCode = itMFp.next().toString();
			if(tempCode.length() == 6) {
				
    %>
		<span>
			<a id="PageLink_<%=tempCode%>" onclick="return selmenu(this);"  href="<%=request.getContextPath()%>/jsp/index.jsp?total_code=<%=tempCode%>" target="leftFrame"><%=tempCode%></a>
		</span>
	<%
			}
		}
	%>
        <span>
        	<a id="PageLink_default" onclick="return selmenu(this);" href="<%=request.getContextPath()%>/jsp/main.jsp?total_code=default" target="mainFrame"><bean:message key="qb.default"/></a>
        </span>
        <span>
        	<a id="PageLink_bundle" onclick="return selmenu(this);" href="<%=request.getContextPath()%>/jsp/main.jsp?total_code=bundle" target="mainFrame"><bean:message key="qb.bundle"/></a>
        </span>
        <span>
        	<a id="PageLink_orgauth" onclick="return selmenu(this);" href="<%=request.getContextPath()%>/orgauth/main.jsp" target="mainFrame"><bean:message key="qb.accessControlMenu"/></a>
        </span>  
	</div>
	<div class="nav-right">
	    <!--  <a id="PageLink_9" onClick="return goMain(this);" href="#" target="mainFrame">-->
	    <img id="sms" src="<%=request.getContextPath()%>/images/default/ico_mail.gif" border="0" alt="短信"/>
	    <!-- </a>-->
	</div>
</div>
<div id="spacer" style="background: url(<%=request.getContextPath()%>/images/default/iwpmi2.gif) top left; width:100%; height:3px">
	<img src="<%=request.getContextPath()%>/images/default/spacer.gif" height="3"/>
</div>
</form>
</body>
</html>