<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%  //翻页
	RmPageVo pageVo = (RmPageVo) request.getAttribute(IGlobalConstants.RM_PAGE_VO);
	if(pageVo.getRecordCount() == 0) {
		out.println("<br><div>&nbsp;&nbsp;没有相关记录！</div><br>");
	}
	String location_href = request.getRequestURI();
	if(request.getQueryString() != null) {
		location_href = location_href + "?" + request.getQueryString();
	}
	location_href = location_href.replaceAll("[?&]RM_CURRENT_PAGE=[\\d]*", "");
	String str_current_page = location_href.indexOf("?") > -1 ? "&RM_CURRENT_PAGE=" : "?RM_CURRENT_PAGE=";
%>
<%
	String css_p = request.getParameter("css_p");
	if(css_p == null || css_p.length() == 0){
		css_p="1";
	}else if(css_p.equals("2")){
		css_p = "2";
	}else if(css_p.equals("3")){
		css_p = "3";
	}else{
		css_p="1";
	}
%>
<div class="page<%=css_p%>">
	<%if(pageVo.getCurrentPage()>1){%><a href="<%=location_href%>">首页</a><%}%>
	<%if(pageVo.getCurrentPage()>1){%><a href="<%=location_href%><%=str_current_page%><%=pageVo.getCurrentPage()-1%>">&lt;&lt;上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;<%}%>
	<%
	for(int i=1; i<=pageVo.getPageCount(); i++) { 
		if(pageVo.getCurrentPage() == i) {
			out.print("<span>" + i + "</span>\n");
		} else {
			out.print("<strong><a href=\"" + location_href + str_current_page + i +"\">" + i + "</a></strong> ");
		}
	} 
	%>
	<%if(pageVo.getPageCount()>=pageVo.getCurrentPage()+1){%>&nbsp;&nbsp;&nbsp;&nbsp;<a href="<%=location_href%><%=str_current_page%><%=pageVo.getCurrentPage()+1%>">下一页 &gt;&gt;</a><%}%>
	<%if(pageVo.getPageCount()>pageVo.getCurrentPage()){%><a href="<%=location_href%><%=str_current_page%><%=pageVo.getPageCount()%>">末页</a><%}%></div>
<script type="text/javascript">

</script>