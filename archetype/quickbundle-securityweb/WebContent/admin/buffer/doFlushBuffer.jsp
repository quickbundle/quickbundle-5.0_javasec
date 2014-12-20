<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/jsp/common/err.jsp" %>
<%@page import="org.quickbundle.tools.support.buffer.FlushQueueThread"%>
<%@page import="org.quickbundle.tools.support.buffer.AbstractTaskQueue"%>
<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String info = "";
	String bufClassName = request.getParameter("bufClassName");
	String bufName = request.getParameter("bufName");
	CopyOnWriteArrayList<AbstractTaskQueue> bufs = FlushQueueThread.getSingleton().getBufs();
	for(AbstractTaskQueue buf : bufs) {
		if(buf.getClass().getCanonicalName().equals(bufClassName) && buf.getName().equals(bufName)) {
	buf.tryFlush();
	info = "刷新成功!";
		}
	}
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
<%if(info.length() > 0){%>
//alert("<%=info%>");
window.location.href = "listBuffer.jsp";
<%}%>
</script>
</head>
<body>

</body>
</html>