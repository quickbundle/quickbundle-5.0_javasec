<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@ include file="/jsp/include/web/g.jsp" %>
<%  //判断是否只读
    boolean isReadOnly = false;
    if("1".equals(request.getAttribute(IGlobalConstants.REQUEST_IS_READ_ONLY))) {
        isReadOnly = true;
    } else if("1".equals(request.getParameter(IGlobalConstants.REQUEST_IS_READ_ONLY))){
        isReadOnly = true;
    } 
%>
<%  //取出List
	String message_id = request.getParameter("message_id");
	List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQueryPage("select a.*, b.NAME as RM_DISPLAY_COLUMN from RM_M_MESSAGE_USER a join RM_USER b on a.USER_ID = b.ID where a.MESSAGE_ID=" + message_id);
	pageContext.setAttribute(IGlobalConstants.REQUEST_BEANS, lvo);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
<%if(!isReadOnly) {%>
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return false;
		}
		if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			return false;
		}
		form.user_ids.value = ids;
    	form.action="<%=request.getContextPath()%>/message/deleteRm_m_message_user";
    	form.submit();
	}
	function add_onClick() {  //到增加记录页面
		var inputValueName = new Object();
		getReference(new Array(form.user_ids, inputValueName), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmUserAction.do?cmd=queryReference&referenceInputType=checkbox');
		if(form.user_ids.value != "") {
	    	form.action="<%=request.getContextPath()%>/message/insertRm_m_message_user";
	    	form.submit();
		}
	} <%} %>
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
    <td class="tableHeaderMiddleTd"><b>关联用户列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
<%if(!isReadOnly) {%>
		<input type="button" class="button_ellipse" id="button_add" value="新增" onclick="javascript:add_onClick();" title="新增关联的用户"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/> <%} %>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="user_id"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IGlobalConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="user_id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="名称">
		<bean:define id="rmValue" name="rmBean" property="rm_display_column"/>
		<bean:define id="user_id" name="rmBean" property="user_id"/>
		<%="<a class='aul' target='_blank' href='" + request.getContextPath() + "/RmUserReadOnlyAction.do?cmd=detail&id=" + user_id + "&" + IGlobalConstants.REQUEST_IS_READ_ONLY +  "=1'>"%>
		<%=rmValue%>
		<%="</a>"%>
	</layout:collectionItem>
</layout:collection>
<jsp:include page="/jsp/include/page.jsp" />
<input type="hidden" name="message_id" value="<%=message_id%>">
<input type="hidden" name="user_ids" value="">
</form>
</body>
</html>
							