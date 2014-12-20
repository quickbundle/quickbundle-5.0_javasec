<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.tools.support.visitsum.RmVisitSumTextHelper"%>
<div class="bottom">
	<div><a target="_blank" href="<%=request.getContextPath()%>/">首页</a> | <a target="_blank" href="http://www.quickbundle.org/">联系我们</a> | 
	您是本站第&nbsp;<span style="background-color:#000000; color:#00FF00; font-family: "黑体";">&nbsp;<%=RmVisitSumTextHelper.increaseVisitSum(request)%>&nbsp;</span>&nbsp;位访问者&nbsp;&nbsp;&nbsp; </div>
</div>
