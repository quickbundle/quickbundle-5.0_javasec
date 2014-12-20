<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo"%>
<%@page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.orgauth.rmrole.service.impl.RmRoleService"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.orgauth.cache.RmAuthorizeCache"%>
<%@page import="org.quickbundle.orgauth.cache.RmPartyTypeCache"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理 - 角色管理 - 授权</title>
<style type="text/css">
html { overflow-y:scroll; border-left:1px #f4f4f4 solid;}
</style>
<%
	String id = request.getParameter("id"); //角色ID
	RmRoleService rService = (RmRoleService)RmBeanFactory.getBean(IRmRoleConstants.SERVICE_KEY);
	RmRoleVo rvo =  rService.find(id);
	String partyTypeName = RmPartyTypeCache.getPartyType(IRmRoleConstants.TABLE_NAME).getName();
	//取出已经赋给的功能菜单权限
	String old_code_id = "";
	List<RmCommonVo> authorizeResourceListId = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT AR.OLD_RESOURCE_ID  FROM RM_AUTHORIZE_RESOURCE_RECORD ARR JOIN RM_AUTHORIZE_RESOURCE AR ON ARR.AUTHORIZE_RESOURCE_ID=AR.ID WHERE ARR.PARTY_ID="+id);
	for(RmCommonVo vo :authorizeResourceListId){
		old_code_id += vo.get("old_resource_id")+",";
	}
	if(old_code_id!=null&&!old_code_id.equals("")){
		old_code_id = old_code_id.substring(0,old_code_id.length()-1);
	}
%>
</head>
<body>
<form name="form1" method="post">   
<br>
<br>
<br>
<table width=98% align=center border=0 cellpadding=0 cellspacing=0>
	  <tr>
		<td>
          <b><font class=p12>角色名称：</font><font color=red class=p14><%=rvo.getName() %></font></b>&nbsp;&nbsp;
          <b><font class=p12>团体类别：</font><font color=red class=p14><%=partyTypeName %></font></b>
        </td>
	  </tr>
</table>
<br>
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="maintab" align="center">
	<tr > 
	    <td>
			<table width=100% align=center border=0 bgcolor="#ffffff">
				<tr><input type="hidden" name="old_code_id" value="<%=RmStringHelper.prt(old_code_id)%>">   
					<td align=center>
						<iframe id="iframe_role_function_node_tree" name="iframe_role_function_node_tree" frameborder="0" width="100%" height="400" src="<%=request.getContextPath()%>/orgauth/tree/functionNodeTree_role.jsp?submitType=submitAll&nodeRelationType=hasRelation&lazy_init=0&inputType=checkbox&enableCookie=false"></iframe>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
<br>
<center>
	<input type="checkbox" title="全选/不选" onclick="iframe_role_function_node_tree.setAllCheckbox(this.checked)">
	<input name="button" class="button_ellipse" type="button" onclick="javascript:updateRoleFunctionNode()" value="确 定">
	<input name="reset"  class="button_ellipse" type="button" onclick="javascript:resetRoleFunctionNode()" value="重 置">
	<input type="button" class="button_ellipse" name="button" value="返 回" onclick="javascript:history.back()">
	
	<input type="hidden" value="" name="function_node_ids" id="function_node_ids">
	<input type="hidden" value="" name="function_node_id_names" id="function_node_id_names">
	<input type="hidden" value="<%=id%>" name="role_id"> 
</center>
</form>
</body>
</html>
<script type="text/javascript">
	function updateRoleFunctionNode() {
		var submitObjectArray = window.frames["iframe_role_function_node_tree"].returnValueName();
		var alertStr = "";
		for(var i=0; i<submitObjectArray.length; i++) {
			if(alertStr != "") {
				alertStr += ",";
			}
			alertStr += submitObjectArray[i]["returnValue"];
		}
		$("#function_node_ids").val(alertStr);

		alertStr = "";
		for(var i=0; i<submitObjectArray.length; i++) {
			if(alertStr != "") {
				alertStr += ",";
			}
			alertStr += submitObjectArray[i]["childName"];
		}
		$("#function_node_id_names").val(alertStr);
		
		//alert(alertStr);
		form1.action="<%=request.getContextPath()%>/RmRoleAction.do?cmd=updateRoleFunctionNode";
		form1.submit();
	}
	function resetRoleFunctionNode()
	{
		window.iframe_role_function_node_tree.location.reload();
	}
</script>