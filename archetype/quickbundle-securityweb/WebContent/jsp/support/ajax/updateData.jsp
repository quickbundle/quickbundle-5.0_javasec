<%@ page contentType="text/xml;charset=UTF-8" language="java" %><%
try {
	String strsql = request.getParameter("strsql");
	int sum = org.quickbundle.project.RmProjectHelper.getCommonServiceInstance().doUpdate(strsql);
	out.print(sum);
} catch(Exception e) {
	e.printStackTrace();
}%>
