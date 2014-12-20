<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%!
/**
 * 得到Throwable的堆栈信息
 * 
 * @param t
 * @param rows 最长多少行
 * @return
 */
public static String getStackTraceStr(Throwable t, int rows) {
	StringBuilder result = new StringBuilder();
	Throwable currentE = t;
	while(currentE != null) {
		if(currentE != t) {
			result.append("Caused by: ");
		}
		result.append(currentE.toString());
		result.append("\n");
		for (int i = 0; i < currentE.getStackTrace().length; i++) {
			if (i >= rows) {
				result.append("......\n");
				break;
			}
			result.append(currentE.getStackTrace()[i]);
			result.append("\n");
		}
		currentE = currentE.getCause();
	}
	return result.toString();
}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>find classpath</title>
</head>
<body>
<%try{ %>
<%
	if(request.getParameter("classNames") != null && request.getParameter("classNames").trim().length() > 0) {
		String[] classNames = request.getParameter("classNames").trim().split("[\\s]+");
		for(String className : classNames) {
			className = className.trim();
			if(className.length() == 0) {
				continue;
			}
			ClassLoader cl = null;
			try{
				out.print("<br/><div>");
				cl = Thread.currentThread().getContextClassLoader();
%>
<span style="color:blue;">ContextClassLoader: <%=className %></span>|
<%
				String clResouce = cl.loadClass(className).getResource("") + "";
				out.print(clResouce);
			}catch(Throwable e) {
				String info = getStackTraceStr(e, 10000).replaceAll("\n", "<br/>");
				out.print("<span>");
				out.print(info);
				out.print("</span>");
			}
			try{
				ClassLoader systemCl = ClassLoader.getSystemClassLoader();
				if(systemCl != cl) {
%>
<br/><span style="color:red;">SystemClassLoader&nbsp;: <%=className %></span>|<%=systemCl.loadClass(className).getResource("")%>
<%
				}
			}catch(Throwable e) {
				String info = getStackTraceStr(e, 10000).replaceAll("\n", "<br/>");
				out.print("<span>");
				out.print(info);
				out.print("</span>");
			}
			out.print("</div>");
		}
	}
%>
<div>
<form method="post">
<div style="color:red;padding-top:3px">提示：多个类名用回车分隔<div>
<textarea name="classNames" cols="80" rows="15"><%if(request.getParameter("classNames") != null && request.getParameter("classNames").trim().length() > 0){out.print(request.getParameter("classNames"));}else{%>
org.quickbundle.config.RmConfig
org.springframework.util.Log4jConfigurer
<%} %></textarea>
<div><input type="submit" value="提交"></div>
</form>
</div>
<%}catch(Throwable e) {e.printStackTrace();out.print("\n<br/>");out.print(getStackTraceStr(e, 10000).replaceAll("\n", "<br/>"));}%>
</body>
</html>