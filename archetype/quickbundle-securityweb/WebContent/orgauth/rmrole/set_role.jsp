<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo"%>
<%@page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.orgauth.rmrole.service.impl.RmRoleService"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.orgauth.rmparty.service.IRmPartyService"%>
<%@page import="org.quickbundle.orgauth.rmparty.util.IRmPartyConstants"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统管理 - 角色管理 - 授权</title>
<style type="text/css">
html { overflow-y:scroll; border-left:1px #f4f4f4 solid;}
</style>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmRoleAction";
	function updateRole() {
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insertPartyRole";
    	form.submit();
	}
	function goListRmUser(){
		form.action="<%=request.getContextPath()%>/RmUserAction.do?cmd=simpleQuery";
    	form.submit();
	}
</script>
<%
	String parent_party_id = "" ;
	if(!"3".equals(RmProjectHelper.getRmUserVo(request).getAdmin_type())){
		if(RmProjectHelper.getRmUserVo(request).getParty_id_org()!=null){
			//公司id
			parent_party_id = RmProjectHelper.getRmUserVo(request).getParty_id_org();
		}
	}
	String party_id = request.getParameter("id"); //用户ID
	String party_name="";
	if(RmStringHelper.checkNotEmpty(party_id) && !"3".equals(RmProjectHelper.getRmUserVo(request).getAdmin_type())){
		party_name = ((IRmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY)).find(party_id).getName();
	}
	//查询该员工赋予的角色
	List<RmCommonVo> roleListVo = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT R.ID,PR.OWNER_ORG_ID,P.NAME from RM_ROLE R JOIN RM_PARTY_ROLE PR on R.ID=PR.ROLE_ID left join RM_PARTY P on P.OLD_PARTY_ID=PR.OWNER_ORG_ID  where R.USABLE_STATUS = '1' and PR.OWNER_PARTY_ID="+party_id);
	Map<String, List<RmCommonVo>> mResult = new HashMap();
	for(RmCommonVo vo:roleListVo){
    	  if(mResult.get(vo.getString("id")) == null) {
    		  mResult.put(vo.getString("id"), new ArrayList<RmCommonVo>());
    	  }
    	  mResult.get(vo.getString("id")).add(vo);
	}
	Map<String,String[]> mRole = new HashMap<String,String[]>();
	for(String key:mResult.keySet()){
		String companyName = "";
		String companyId = "";
    	List<RmCommonVo> listv = mResult.get(key);
    	RmCommonVo sVo = listv.get(0);
    	String[] strs = new String[2];
    	for(RmCommonVo bVo:listv){
    		if(RmStringHelper.checkNotEmpty(bVo.getString("name")) && RmStringHelper.checkNotEmpty(bVo.getString("owner_org_id"))){
    			companyName += bVo.getString("name")+",";
    			companyId += bVo.getString("owner_org_id")+",";
    		}
    	}
    	if(RmStringHelper.checkNotEmpty(companyName)){
    		strs[0]=companyName.substring(0,companyName.length()-1);
    		strs[1]=companyId.substring(0,companyId.length()-1);
    	}
    	mRole.put(sVo.getString("id"),strs);
    }
    String[] roleIds = new String[mRole.size()];
    int j = 0;
    Map sWriteBack = new HashMap();
    for(Map.Entry<String,String[]> mr:mRole.entrySet()){
    	roleIds[j]=mr.getKey();
		sWriteBack.put("company_id_"+mr.getKey(),mr.getValue().length>1 ? RmStringHelper.prt(mr.getValue()[1]):"");
		sWriteBack.put("company_name_"+mr.getKey(),mr.getValue().length>1 ? RmStringHelper.prt(mr.getValue()[0]):"");
		j++;
    }
	//根据权限查询角色
	//RmCommonVo cVo = RmProjectHelper.getCommonServiceInstance().doQuery("select distinct(PR.CHILD_PARTY_CODE) from RM_PARTY_RELATION PR where PR.CHILD_PARTY_ID="+userVo.getCompany().getId()).get(0);
	List<RmCommonVo> roleList = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT ID,NAME,IS_SYSTEM_LEVEL, ROLE_CODE FROM RM_ROLE where USABLE_STATUS = '1'");
	List<RmCommonVo> isSystemLevelList = new ArrayList<RmCommonVo>();
	List<RmCommonVo> notSystemLevelList = new ArrayList<RmCommonVo>();
	for(RmCommonVo vo:roleList){
		if("1".equals(vo.getString("is_system_level"))){
			isSystemLevelList.add(vo);
		}else{
			notSystemLevelList.add(vo);
		}
	}

%>
</head>
<body>
<form name="form" method="post">
<br>
<br>
<table width=98% align=center border=0 cellpadding=0 cellspacing=0 class="mainTable">
	  <tr>
		<td>
          <b><font class=p12>系统管理 	&gt;&gt; 用户管理	&gt;&gt; 分配角色 	&gt;&gt; 当前员工名称：</font><font color=red class=p14><%=party_name %></font></b>&nbsp;&nbsp;
        </td>
	  </tr>
</table>
<br>
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="mainTable" align="center">
	<tr>
		<td><b>全局角色</b></td>
	</tr>
	<tr> 
    	<td>
			<table width=100% align=center border=0 bgcolor="#ffffff">
				<% 
				int i = 0 ;
				for(RmCommonVo isSystemLevelVo:isSystemLevelList) {
					if(i==0){
				%>
				<tr>
				<% } else if(i!=0 && i%2==0) {%>
				</tr>
				<tr>
				<% }else {%>
				
				<% } %>
				<td width="15%">
				<label><input type="checkbox" name="role_name" value="<%=isSystemLevelVo.getString("id") %>"/><%=isSystemLevelVo.getString("name") %>(<%=isSystemLevelVo.getString("role_code") %>)</label>
				</td>
				<td align="left">
					<input type="text" class="text_field_reference_readonly" hiddenInputId="company_id_<%=isSystemLevelVo.getString("id") %>"  validate='notNull;' name="company_name_<%=isSystemLevelVo.getString("id") %>" id="company_name_<%=isSystemLevelVo.getString("id") %>" inputName="名称" value="" /><input type="hidden"  name="company_id_<%=isSystemLevelVo.getString("id") %>" id="company_id_<%=isSystemLevelVo.getString("id") %>"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.company_id_<%=isSystemLevelVo.getString("id") %>, form.company_name_<%=isSystemLevelVo.getString("id") %>), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>&enableCookie=true&inputType=checkbox&parent_party_id=<%=parent_party_id %>&include_self=1&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id()%>');" />
				</td>
				<% 	i++;} %>
			</table>
		</td>
	</tr>
	<tr>
		<td><b>&nbsp;</b></td>
	</tr>	
</table>
<br>
<table width="98%" border="0" cellspacing="1" cellpadding="0" class="mainTable" align="center">
	<tr>
		<td><b>公司角色</b></td>
	</tr>
	<tr> 
    	<td>
			<table width=100% align=center border=0 bgcolor="#ffffff">
				<% 
					int k = 0;
					for(RmCommonVo notSystemLevelVo:notSystemLevelList) { 
						if(k==0){	
				%>
				<tr>
				<% } else if(k!=0 && k%5==0) {%>
				</tr>
				<tr>				
				<% }else {%>
				
				<% } %>
				<td width="15%"><label><input type="checkbox" name="role_name" value="<%=notSystemLevelVo.getString("id") %>"/><%=notSystemLevelVo.getString("name") %>(<%=notSystemLevelVo.getString("role_code") %>)</label>&nbsp;&nbsp;</td>
				<% k++;	} %>
			</table>
		</td>
	</tr>
</table>
<br>
<center><input type="hidden"" value="<%=party_id%>" name="id">
	<input name="submit1" class="button_ellipse" type="button" onclick="javascript:updateRole()" value="确 定">
	<input type="button" class="button_ellipse" name="button" value="返 回" onclick="goListRmUser()">
</center>
</form>	
<script type="text/javascript">
<%
	if(roleIds!=null&&roleIds.length>0){
		sWriteBack.put("role_name", roleIds);
		out.print(RmVoHelper.writeBackMapToForm(sWriteBack));  //输出表单回写方法的脚本
	}
%>
</script>
</body>
</html>
