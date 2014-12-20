<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="org.quickbundle.modules.lock.RmLockHelper"%>
<%@page import="org.quickbundle.modules.lock.rmlock.service.IRmLockService"%>
<%@page import="org.quickbundle.modules.lock.rmlock.util.IRmLockConstants"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.tools.helper.RmDateHelper"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
/*
	DataSource ds = (DataSource)RmBeanFactory.getBean("dataSource");
	out.print("dataSource: " + ds);
	
	out.print("<br/><br/>");
	DataSource ds_soa = (DataSource)RmBeanFactory.getBean("dataSource_soa");
	out.print("dataSource_soa: " + ds_soa);
	*/
	
if("testInsert".equals(request.getParameter("cmd"))) {
	RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_LOCK set  user_id=? where id=1000100100000000040", new Object[]{RmDateHelper.getSysTimestamp().toString()});
} else {
	try {
		//boolean lock = RmLockHelper.lock("1000100000000000001", "2");
		/*
		System.out.println(lock);
		lock = RmLockHelper.lock("1000100000000000001", "2");
		System.out.println(lock);
		lock = RmLockHelper.lock("", "2");
		System.out.println(lock);
		*/
	} finally {
		RmLockHelper.unlock("1000100000000000001", "2");
	}
}
%>
</body>
</html>