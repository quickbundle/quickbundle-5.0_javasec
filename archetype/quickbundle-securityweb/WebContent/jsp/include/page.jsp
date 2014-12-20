<%@ page contentType="text/html; charset=UTF-8" language="java" %><%@page import="org.quickbundle.base.web.page.RmPageVo"%><%@page import="org.quickbundle.project.IGlobalConstants"%><%  //翻页
	int[] aSize = new int[]{15, 25, 50};
	if(request.getParameter("size_list") != null && request.getParameter("size_list").trim().length() > 0) {
	    try {
		    String[] aSize_list = request.getParameter("size_list").split(",");
		    aSize = new int[aSize_list.length];
		    for(int i=0; i<aSize_list.length; i++) {
		    	aSize[i] = Integer.parseInt(aSize_list[i]);
		    }
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}
	RmPageVo pageVo = new RmPageVo(0,0);
	if(request.getAttribute(IGlobalConstants.RM_PAGE_VO) != null) {
		pageVo = (RmPageVo) request.getAttribute(IGlobalConstants.RM_PAGE_VO);
	}
%><table border="0" cellspacing="1" cellpadding="1" class="maintab" align="center">
  <tr >
    <td align="center" class="pagebg">
	    共&nbsp;<b><%=pageVo.getRecordCount() %></b>&nbsp;条记录&nbsp;&nbsp;-&nbsp;
	    <span id="page">
		    <%if(pageVo.getCurrentPage()>1){%><a href="JavaScript:firstPage();"><u><%}%>首页<%if(pageVo.getCurrentPage()>1){%></u></a><%}%>
			<%if(pageVo.getCurrentPage()>1){%><a href="JavaScript:upPage();"><u><%}%>上一页<%if(pageVo.getCurrentPage()>1){%></u></a><%}%>
			<%if(pageVo.getPageCount()>=pageVo.getCurrentPage()+1){%><a href="JavaScript:downPage();"><u><%}%>下一页<%if(pageVo.getPageCount()>=pageVo.getCurrentPage()+1){%></u></a><%}%>
			<%if(pageVo.getPageCount()>pageVo.getCurrentPage()){%><a href="JavaScript:lastPage();"><u><%}%>末页<%if(pageVo.getPageCount()>pageVo.getCurrentPage()){%></u></a><%}%>
		</span>
		&nbsp;-&nbsp;&nbsp;第&nbsp;<b class="c_00f"><%=pageVo.getCurrentPage()%></b>/<%=pageVo.getPageCount()%>&nbsp;页
	    <span id="num">显示 <% for(int i=0; i<aSize.length; i++) { %><%if(pageVo.getPageSize() == aSize[i]){%><b><font title="当前是每页<%=aSize[i]%>条记录" color=red><%=aSize[i]%></font></b> <%} else {%><a href="javascript:changePageSize(<%=aSize[i]%>)" title="切换为每页显示<%=aSize[i]%>条记录"><%=aSize[i]%></a> <%} %><% } %>条</span> 
	    转到第<input name="RM_CURRENT_PAGE_INPUT" type="text" size="1"  onmouseover="this.focus();" onfocus="this.select();" validate="notGlobalFilterChar"/>页&nbsp;
	    <input name="" class="button_ellipse" type="button" onClick="javascript:goAppointedPage()" value="GO"/>
	    	<input name="<%=IGlobalConstants.RM_CURRENT_PAGE%>" type="hidden" value="<%=(pageVo.getCurrentPage() == 0) ? "1" : String.valueOf(pageVo.getCurrentPage()) %>">
			<input name="<%=IGlobalConstants.RM_PAGE_SIZE%>" type="hidden" value="<%=pageVo.getPageSize()%>">
			<input name="RM_PAGE_SUM" type="hidden" value="<%=pageVo.getPageCount()%>">
    </td>
  </tr>
</table>
<script type="text/javascript">
function changePageSize(page_size) {
	form.<%=IGlobalConstants.RM_PAGE_SIZE%>.value = page_size;
	form.submit();
}
function rebuildUri() {
	var thisAction = null;
	if(form.action != null && form.action != "") {
		thisAction = form.action;
	} else {
		thisAction = location.href;
	}
	var reg = /&<%=IGlobalConstants.RM_CURRENT_PAGE%>=\d+/g;
	form.action = thisAction.replace(reg,"");
}
function firstPage(){  //去首页
	rebuildUri();
	form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value=1;
	form.submit();
}
function upPage(){  //去上一页
	rebuildUri();
	form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value--;
	form.submit();
}
function downPage(){  //去下一页
	rebuildUri();
	form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value++;
	form.submit();
}
function lastPage(){  //去末页
	rebuildUri();
	form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value=<%=pageVo.getPageCount()%>;
	form.submit();
}
function goAppointedPage(){  //直接跳到某页
	rebuildUri();
	checkPageNoKey();
	form.submit();
}
function checkPageNoKey() {
	if(!form.RM_CURRENT_PAGE_INPUT.value.match(/\d+/g)){
    	form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value = 1;
    	return;
    }
	if(parseInt(form.RM_CURRENT_PAGE_INPUT.value) <= 0 || parseInt(form.RM_CURRENT_PAGE_INPUT.value) >  parseInt(form.RM_PAGE_SUM.value)) {
		form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value = 1;
	} else {
		form.<%=IGlobalConstants.RM_CURRENT_PAGE%>.value = form.RM_CURRENT_PAGE_INPUT.value;
	}
}
</script>