<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page errorPage="/jsp/common/err.jsp" %>
<%@page import="org.quickbundle.tools.helper.RmDateHelper"%>
<%@page import="org.quickbundle.tools.support.buffer.AbstractTaskQueue"%>
<%@page import="java.util.concurrent.CopyOnWriteArrayList"%>
<%@page import="org.quickbundle.tools.support.buffer.FlushQueueThread"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统缓冲器监控</title>
<script type="text/javascript">
function flushNow_onClick(bufClassName_, bufName_) {
	if(window.confirm("您确定要手动刷新缓存吗？")) {
		form.bufClassName.value = bufClassName_;
		form.bufName.value = bufName_;
		form.action="doFlushBuffer.jsp";
		event.srcElement.disabled = true;	
		form.submit();
	}
}
</script>
</head>
<body>
<form name="form" method="post" action="">
<div>
	<div style="padding:0px 0px 10px 60px"><input type="button" value="刷新本页面" onclick="window.location.reload();"/></div>
<%
	CopyOnWriteArrayList<AbstractTaskQueue> bufs = FlushQueueThread.getSingleton().getBufs();
	for(AbstractTaskQueue buf : bufs) {
%>
	<div style="padding:0px 0px 10px 10px">
		<table border="1">
			<tr>
				<td align="center" colspan="2"><b><%=buf.getName()%></b></td>
			</tr>
			<tr>
				<td align="right">类名</td>
				<td><%=buf.getClass().getCanonicalName()%></td>
			</tr>
			<tr>
				<td align="right">队列容量/最大容量</td>
				<td><%=buf.getSize() %> / <%=buf.getCapcity()%></td>
			</tr>
			<tr>
				<td align="right">自动刷新间隔</td>
				<td><%=buf.getExpire_interval()/1000 %>秒</td>
			</tr>
			<tr>
				<td align="right">指定下次刷新时间</td>
				<td><%
					long expire_time = buf.getExpire_time();
					if(expire_time >= Long.MAX_VALUE/2) {
						out.println("永远以后");
					} else {
						out.print(RmDateHelper.getFormatDateTimeDesc(expire_time));
						out.print(" (");
						out.print((expire_time-System.currentTimeMillis())/1000);
						out.print("秒后)");
					}
				%></td>
			</tr>
			<tr>
				<td align="right">操作</td>
				<td><input type="button" onclick="flushNow_onClick('<%=buf.getClass().getCanonicalName()%>', '<%=buf.getName()%>')" value="立刻手动刷新"></td>
			</tr>
		</table>
	</div>
<%
	}
%>
</div>
<input type="hidden" name="bufClassName" value="">
<input type="hidden" name="bufName" value="">
</form>
</body>
</html>