<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo"%>
<%@page import="org.quickbundle.orgauth.cache.RmAuthorizeCache"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@ include file="/jsp/include/web/g.jsp" %>
<%  //取出List
	String bs_keyword = request.getParameter("bs_keyword");
	String old_resource_id = request.getParameter("old_resource_id");
	RmAuthorizeVo authorize = RmAuthorizeCache.getAuthorizeByBs_keyword(bs_keyword);
	List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQueryPage("SELECT A.*, B.NAME AS RM_DISPLAY_COLUMN FROM RM_AUTHORIZE_RESOURCE_RECORD A JOIN RM_PARTY B ON A.PARTY_ID = B.ID join RM_AUTHORIZE_RESOURCE C ON A.AUTHORIZE_RESOURCE_ID=C.ID WHERE C.AUTHORIZE_ID=" + authorize.getId() + " and C.OLD_RESOURCE_ID='" + old_resource_id + "'");
	pageContext.setAttribute(IGlobalConstants.REQUEST_BEANS, lvo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmAuthorizeResourceAction";
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			form.party_ids.value = ids;
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteRm_authorize_resource_record";
	    	form.submit();
		}
	}
	function add_onClick() {  //到增加记录页面
		var inputValueName = new Object();
		getReference(new Array(form.party_ids, inputValueName), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmPartyAction.do?cmd=queryReference&referenceInputType=checkbox');
		if(form.party_ids.value != "") {
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insertRm_authorize_resource_record";
	    	form.submit();
		}
	}
	function addEmployee_onClick() {  //到增加记录页面
		var inputValueName = new Object();
		getPartyWindow(new Array(form.party_ids, inputValueName), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.EMPLOYEE.value()%>&enableCookie=true&inputType=checkbox');
		if(form.party_ids.value != "") {
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insertRm_authorize_resource_record";
	    	form.submit();
		}
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
</script>
</head>
<body>
<form name="form" method="post">

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b>关联RmParty列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_addEmployee" value="新增员工" onClick="javascript:addEmployee_onClick();" title="新增关联的员工"/>
		<input type="button" class="button_ellipse" id="button_add" value="新增Party" onClick="javascript:add_onClick();" title="新增关联的RmParty"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="party_id"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IGlobalConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="party_id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="名称">
		<bean:define id="rmValue" name="rmBean" property="rm_display_column"/>
		<bean:define id="party_id" name="rmBean" property="party_id"/>
		<%="<a class='aul' target='_blank' href='" + request.getContextPath() + "/RmPartyReadOnlyAction.do?cmd=detail&id=" + party_id + "&" + IGlobalConstants.REQUEST_IS_READ_ONLY +  "=1'>"%>
		<%=rmValue%>
		<%="</a>"%>
	</layout:collectionItem>
</layout:collection>
<jsp:include page="/jsp/include/page.jsp" />
<input type="hidden" name="bs_keyword" value="<%=bs_keyword%>">
<input type="hidden" name="old_resource_id" value="<%=old_resource_id%>">
<input type="hidden" name="party_ids" value="">
</form>
</body>
</html>
							