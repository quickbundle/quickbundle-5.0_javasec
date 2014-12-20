<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.orgauth.util.impl.RmOrgService"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style text="text/css">
table tr td{font-size:18px ;}
</style>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
</head>
<body>
<form name="form" method="post">
<br>
<br>
<table>
	<tr>
		<td colspan="2">
			<table border="1">
				<tr>
					<td colspan="3"><font color="red"><b>获取当前用户相关信息</b></font></td>
				</tr>
				<tr>
					<td width="20%"><b>功能示例</b></td>
					<td width="55%"><b>代码</b></td>
					<td width="25%"><b>说明</b></td>
				</tr>				
				<tr>
					<td align="left">登录名</td>
					<td align="left">RmProjectHelper.getRmLoginId(request)</td>
					<td align="left">Jsp、Action 中条调用</td>
				</tr>
				<tr>
					<td align="left">获取Request</td>
					<td align="left">RmProjectHelper.getCurrentThreadRequest()</td>
					<td align="left">Service 中调用获取request方法</td>
				</tr>	
				<tr>
					<td align="left">获取员工ID</td>
					<td align="left">RmProjectHelper.getRmUserVo(request).getId()</td>
					<td align="left">用户所属员工ID</td>
				</tr>
				<tr>
					<td align="left">获取员工所属公司</td>
					<td align="left">RmProjectHelper.getRmUserVo(request).getParty_id_company()</td>
					<td align="left">返回Map &lt;id,名称&gt; </td>
				</tr>
				<tr>
					<td align="left">获取员工所属部门</td>
					<td align="left">RmProjectHelper.getRmUserVo(request).getParty_id_department()</td>
					<td align="left">返回Map &lt;id,名称&gt; </td>
				</tr>
				<!-- 
				<tr>
					<td align="left">获取当前登录用户公司对象</td>
					<td align="left">RmProjectHelper.getRmUserVo(request).getCompany()</td>
					<td align="left">&nbsp;</td>
				</tr>
				 -->
			</table>	
		</td>
	</tr>
	<tr>
		<td>
			<table border="1">
				<tr>
					<td colspan="2"><font color="red"><b>组织树参照(单选)</b></font></td>
				</tr>
				<tr>
					<td>默认(所有类型)</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name2" inputName="名称" value="" /><input type="hidden" name="tree_id2"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id2, form.tree_name2), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>公司树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name" inputName="名称" value="" /><input type="hidden" name="tree_id"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id, form.tree_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>&enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>部门树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name9" inputName="名称" value="" /><input type="hidden" name="tree_id9"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id9, form.tree_name9), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.DEPARTMENT.value()%>&enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr style="display:none">
					<td>岗位树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name6" inputName="名称" value="" /><input type="hidden" name="tree_id6"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id6, form.tree_name6), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.STATION.value()%>&enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>员工树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name8" inputName="名称" value="" /><input type="hidden" name="tree_id8"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id8, form.tree_name8), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.EMPLOYEE.value()%>&enableCookie=true&inputType=radio&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>根据公司ID获取公司树</td>
					<%//=org.quickbundle.tools.helper.RmStringHelper.encodeUrl("orgauth/tree/orgData.jsp?lazy_init=0&party_view_id=1000200700000000001&parent_party_id=1000201100000000158&party_type_bs_keyword=TM_COMPANY")%>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name11" inputName="名称" value="" /><input type="hidden" name="tree_id11"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id11, form.tree_name11), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.EMPLOYEE.value()%>&enableCookie=true&inputType=radio&rootXmlSource=ut%2Forgauth%2Ftree%2ForgData.jsp%3Flazy_init%3D0%26party_view_id%3D1000200700000000001%26parent_party_id%3D1000201100000000158%26party_type_bs_keyword%3DTM_COMPANY');"/></td>
				</tr>
			</table>		
		</td>
		<td>
			<table border="1">
				<tr>
					<td colspan="2"><font color="red"><b>组织树参照(多选)</b></font></td>
				</tr>
				<tr>
					<td>默认(所有类型)</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name2a" inputName="名称" value="" /><input type="hidden" name="tree_id2a"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id2a, form.tree_name2a), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?enableCookie=true&inputType=checkbox');"/></td>
				</tr>
				<tr>
					<td>公司树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_namea" inputName="名称" value="" /><input type="hidden" name="tree_ida"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_ida, form.tree_namea), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>&enableCookie=true&inputType=checkbox&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>部门树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name9a" inputName="名称" value="" /><input type="hidden" name="tree_id9a"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id9a, form.tree_name9a), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.DEPARTMENT.value()%>&enableCookie=true&inputType=checkbox&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr style="display:none">
					<td>岗位树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name6a" inputName="名称" value="" /><input type="hidden" name="tree_id6a"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id6a, form.tree_name6a), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.STATION.value()%>&enableCookie=true&inputType=checkbox&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>员工树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name8a" inputName="名称" value="" /><input type="hidden" name="tree_id8a"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id8a, form.tree_name8a), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.EMPLOYEE.value()%>&enableCookie=true&inputType=checkbox&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id() %>');"/></td>
				</tr>
				<tr>
					<td>根据公司ID获取员工+角色树</td>
					<td><input type="text" class="text_field_reference" validate='notNull;'  name="tree_name17" inputName="名称" value="" /><input type="text" name="tree_id17"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.tree_id17, form.tree_name17), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.ROLE_EMPLOYEE.value()%>&inputType=checkbox&nodeRelationType=noRelation&parent_party_id=1000201100000000022');"/></td>
				</tr>	
			</table>		
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<table  border="1">
				<tr>
					<td colspan="2">
					<font color="red"><b>相关SQL语句</b></font>
					</td>
				</tr>
				<tr>
					<td width="30%">获取所有公司sql语句</td>
					<td width="70%"></td>
				</tr>			
			</table>
		</td>
	</tr>
	<div>
		<a href="<%=request.getContextPath()%>/jsp/support/deeptree/deeptree.jsp?rootXmlSource=<%=request.getContextPath()%>/project/basedoc/adminDivisionData.jsp">行政区划树</a>
	</div>
</table>
</form>
</body>
</html>