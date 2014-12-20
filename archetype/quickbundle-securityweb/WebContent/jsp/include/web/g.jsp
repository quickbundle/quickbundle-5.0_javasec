<%@page import="org.quickbundle.project.IGlobalConstants"%><%@ page contentType="text/html; charset=UTF-8" language="java" %><%@page import="org.quickbundle.base.web.page.RmPageVo"%><% 
	//前台分页
	int page_size = 20;
	if(request.getParameter("RM_PAGE_SIZE") != null) {
		page_size = Integer.parseInt(request.getParameter("RM_PAGE_SIZE"));
	}
	int current_page = 1;
	if(request.getParameter("RM_CURRENT_PAGE") != null) {
		current_page = Integer.parseInt(request.getParameter("RM_CURRENT_PAGE"));
	}
	request.setAttribute(IGlobalConstants.RM_CURRENT_PAGE, new int[]{page_size, current_page});
%>