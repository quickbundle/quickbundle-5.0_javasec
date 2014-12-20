<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%--@page import="nc.bs.framework.common.NCLocator"--%>
<%--@page import="org.quickbundle.project.tools.RmAlarmCollector"--%>
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
<title>find Ejb</title>
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
			try{
				out.print("<br/><div>");
				Object obj = null;//NCLocator.getInstance().lookup(className);
%>
    <span style="color:blue;">ContextClassLoader: <%=className %></span>|<%=obj%>
<%
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
nc.itf.uap.sf.ILogin3Service
nc.itf.icitf.channel.IChannelMessageSave
nc.itf.icitf.channel.IMoneyDetail 
nc.itf.icitf.channel.IDiffTermKeyInfo
<%} %></textarea>
<div><input type="submit" value="提交"></div>
</form>
</div>
<%}catch(Throwable e) {e.printStackTrace();out.print("\n<br/>");out.print(getStackTraceStr(e, 10000).replaceAll("\n", "<br/>"));}%>
</body>
</html>