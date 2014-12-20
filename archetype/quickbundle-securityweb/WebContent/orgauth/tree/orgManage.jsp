<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%
	//获取view_id
	String view_id = request.getParameter("view_id");
	if(view_id == null) {
		//party_view_id的默认值是RM_PARTY_VIEW表的第一条记录
		List<RmCommonVo> lPartyViewId = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT ID FROM RM_PARTY_VIEW ORDER BY ID", 1, 1);
		if(lPartyViewId.size() > 0) {
			view_id = lPartyViewId.get(0).getString("id");
		} else {
			view_id = IOrgauthConstants.PartyView.DEFAULT.id();			
		}
	}
%>
<html>
<head>
<title>rm-based architecture project</title>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<jsp:include page="/jsp/support/deeptree/include/globalDeepTree.jsp" />
<script type="text/javascript">
	rootXmlSource = "<%=request.getContextPath() + "/orgauth/tree/orgData.jsp?cmd=" + IOrgauthConstants.OrgTree.DEFAULT.value() + "&enableCookie=true&is_href=1&lazy_init=1&view_id=" + view_id%>";

	//根据需要可以重写的方法
	function toDoMouseClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseDbClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
		var thisHiddenValue = getHiddenValueByEvent(obj.id);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.outerHTML);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.parentNode.outerHTML);
	}
	
	function toDoMouseOver(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseOut(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}

</script>
</head>
<body onLoad="doOnLoad()" topmargin=0 leftmargin=0 >
<form name="form" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
  <tr> 
     <td width="300" valign="top" align="left">     

		<div name="deeptree" id="deeptree" class="deeptree"></div>

    </td>
    <td width="*" valign="top" align="left">
    </td>
  </tr>
</table>
</form>
</body>
</html>