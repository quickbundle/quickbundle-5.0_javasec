<%@ page contentType="text/html; charset=UTF-8" session="false" language="java"%>
<%@page import="java.util.Enumeration"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.project.login.IRmLoginConstants"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%
	if(!"1".equals(request.getParameter("no_redirect"))) {
		HttpSession session = RmJspHelper.getSession(request, false);
		if(session != null && session.getAttribute(IGlobalConstants.RM_USER_VO) != null) {
			response.sendRedirect(request.getContextPath() + "/"); //index.jsp
		}
		if (RmConfig.getSingleton().isLoginCookie() && !IRmLoginConstants.RM_YES.equals(request.getAttribute(IRmLoginConstants.NO_COOKIE)) && request.getAttribute("alertStr") == null) {
			String login_id = RmJspHelper.getProfile(request, "login_id");
			String password = RmJspHelper.getProfile(request, "password");
			if (login_id != null && password != null && login_id.length() > 0 && password.length() > 0) {
				response.sendRedirect(request.getContextPath()
							+ "/RmLoginAction.do?cmd=login&" + IRmLoginConstants.Para.is_cookie.name() + "=1"
							+ "&login_id=" + RmStringHelper.encodeUrl(login_id)
							+ "&password=" + RmStringHelper.encodeUrl(password));
				return;
			}
		}
	}

%>