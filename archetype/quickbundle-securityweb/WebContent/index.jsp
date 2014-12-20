<%@ page contentType="text/html; charset=UTF-8" session="false" language="java" %>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.config.RmClusterConfig"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<% 
	try {
		//RmClusterConfig.initLocalhostInfo(request);
	} catch(Throwable e) {
		e.printStackTrace();
	}
	HttpSession session = RmJspHelper.getSession(request, false);
	if(session != null && session.getAttribute(IGlobalConstants.RM_USER_VO) != null) { //url改变，基于浏览器的重定向
		//跳到开发主页面
		request.getRequestDispatcher("jsp/index0.jsp").forward(request, response);
		//跳到带权限的主页面
		//request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
		//response.sendRedirect("jsp/index.jsp"); //弃用的写法
	} else { //url不改变，内部重定向
		request.getRequestDispatcher("project/sample/login/login.jsp?no_redirect=1").forward(request, response);
	}
%>