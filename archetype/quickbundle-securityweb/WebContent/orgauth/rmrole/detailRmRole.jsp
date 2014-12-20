<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo" %>
<%@ page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants" %>
<%  //取出本条记录
	RmRoleVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmRoleVo)request.getAttribute(IRmRoleConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmRoleAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.submit();
	} 
	function goUrl(){
	    if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    window.location.href="<%=request.getContextPath()%>/RmRoleAction.do?cmd=simpleQuery";
	} 
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>：</td>
		<td align="left"><%=RmStringHelper.prt(resultVo.getRole_code())%>&nbsp;</td>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td align="left"><%=RmStringHelper.prt(resultVo.getName())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>：</td>
		<td align="left"><%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_ENABLE_STATUS, resultVo.getEnable_status())%>&nbsp;</td>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%>：</td>
		<td align="left"><%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_system_level())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>：</td>
		<td align="left" colspan="3">
		<% 
		RmCommonVo cvo = null;
		if(RmStringHelper.checkNotEmpty(resultVo.getOwner_org_id())){ 
			List<RmCommonVo>  listVo = RmProjectHelper.getCommonServiceInstance().doQuery("select displayname from tm_company where id="+resultVo.getOwner_org_id()+" and USABLE_STATUS='1'");
			if(listVo.size() > 0 ){
				cvo = new RmCommonVo(listVo.get(0));
			}
		}
		%>
		<%=cvo!=null?cvo.getString("displayname"):""%>&nbsp;</td>
	</tr>
	<!--<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_recursive")%>：</td>
		<td><%=RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_recursive())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>-->
	<!--<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("matrix_code")%>：</td>
		<td colspan="3" align="left"><%=RmStringHelper.prt(resultVo.getMatrix_code())%>&nbsp;</td>
	</tr>-->
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td colspan="3" align="left"><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
	</tr>
	<!--<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("function_permission")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getFunction_permission())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("data_permission")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getData_permission())%>&nbsp;</td>
	</tr>-->
  </table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<%try{%>
<script type="text/javascript">
	function deletePartyRoleRelation_onClick(party_role_id){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/RmRoleAction.do?cmd=deletePartyRoleRelation&party_role_id=" + party_role_id;
		form.submit();
	}  
</script>
<br>
<table class="mainTable" style="width:80%" align="center" border="1">
	<tr>
		<td colspan="3" align="center"><h3>本角色的关联用户</h3></td>
	</tr>
	<tr>
		<td width="40%" align="center">用户姓名(帐号)</td>
		<td width="40%" align="center">关联组织名(id)()</td>
		<td width="20%" align="center">操作</td>
	</tr>	
	<%
		List<RmCommonVo> lu = RmProjectHelper.getCommonServiceInstance().doQuery("select U.*, PR.ID as PARTY_ROLE_ID, PR.OWNER_ORG_ID, (select NAME from RM_PARTY where RM_PARTY.USABLE_STATUS='1' and RM_PARTY.ID=PR.OWNER_ORG_ID) as OWNER_ORG_ID_NAME from RM_ROLE R join RM_PARTY_ROLE PR on R.ID=PR.ROLE_ID join RM_USER U on U.ID=PR.OWNER_PARTY_ID where U.USABLE_STATUS='1' and R.ID=" + resultVo.getId());
		for(RmCommonVo vo : lu) {
	%>	
	<tr>
		<td align="left"><a target="_blank" href="<%=request.getContextPath()%>/RmUserAction.do?cmd=detail&id=<%=vo.getString("id")%>"><%=vo.getString("name")%>(<%=vo.getString("login_id")%>)</a></td>
		<td align="left"><%=vo.getString("owner_org_id_name")%>(<%=vo.getString("owner_org_id")%>)</td>
		<td align="left"><input type="button" value="删除关联" onclick="deletePartyRoleRelation_onClick('<%=vo.getString("party_role_id")%>')"/></td>
	</tr>
	<%
		}
	%>	
	<tr>
		<td colspan="3" align="center">&nbsp;</td>
	</tr>
</table>
<br>
<%}catch(Exception e){e.printStackTrace();} %>

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onClick="javascript:delete_onClick();">
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:goUrl();" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(

	null
);
</script>
<br/><br/>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

</form>
</body>
</html>