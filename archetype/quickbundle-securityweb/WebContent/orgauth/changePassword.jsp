<%@page import="org.quickbundle.project.secure.RmCryptoHelper"%>
<%@page import="org.quickbundle.orgauth.rmuser.service.IRmUserService"%>
<%@page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants"%>
<%@page import="org.quickbundle.project.login.RmUserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%

	if("1".equals(request.getParameter("isSubmit2"))) {
		IRmUserService userService = (IRmUserService)RmBeanFactory.getBean(IRmUserConstants.SERVICE_KEY);
		RmUserVo vo = userService.find(RmProjectHelper.getRmUserId(request));
		System.out.println(RmCryptoHelper.digestPassword(request.getParameter("old_password"), vo.getLogin_id()) + 
				"\n" + request.getParameter("old_password") + " " + vo.getLogin_id() + "\n" + 
				vo.getPassword());
		if(!vo.getPassword().equals(RmCryptoHelper.digestPassword(request.getParameter("old_password"), vo.getLogin_id()))) {
			RmJspHelper.goUrlWithAlert(request, response, "旧密码输入错误，请重试!", "/orgauth/changePassword.jsp");
			return;
		}
		boolean success = userService.executeChangePassword(vo.getId(), request.getParameter("password"));
		if(success) {
			RmJspHelper.goUrlWithAlert(request, response, "修改密码成功! 用户名:" + vo.getLogin_id() + " 新密码:" + request.getParameter("password"), "/jsp/util/closeSelf.jsp");
			return;
		} else {
			RmJspHelper.goUrlWithAlert(request, response, "修改失败，请重试!", "/orgauth/changePassword.jsp");
			return;
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改密码</title>
</head>
<body>
<form method="post" onsubmit="if(password2.value != password.value){alert('2次输入的密码不一致,请重试');password.focus();password.select();return false;}">
<div>
	<div>
		<div>请输入旧密码：<input type="password" name="old_password" /><div>
	</div>
	<div><h1>请输入新密码</h1></div>
	<div>
		<div>输入新密码：<input type="password" name="password" /><div>
		<div>新密码确认：<input type="password" name="password2" /><div>
		<div><input type="submit" value="保存新密码" /><div>
	</div>
</div>
<input type="hidden" name="isSubmit2" value="1" />
</form>
</body>
</html>