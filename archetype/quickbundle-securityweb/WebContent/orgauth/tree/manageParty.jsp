<%@page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.orgauth.cache.RmPartyTypeCache"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.orgauth.rmparty.service.IRmPartyService"%>
<%@page import="org.quickbundle.orgauth.rmparty.util.IRmPartyConstants"%>
<%@page import="org.quickbundle.orgauth.rmparty.vo.RmPartyVo"%>
<%@page import="org.quickbundle.orgauth.rmpartytype.service.impl.RmPartyTypeService"%>
<%@page import="org.quickbundle.orgauth.rmpartytype.util.IRmPartyTypeConstants"%>
<%@page import="org.quickbundle.orgauth.rmpartytype.vo.RmPartyTypeVo"%>
<%@page import="org.quickbundle.orgauth.rmpartyrelation.service.IRmPartyRelationService"%>
<%@page import="org.quickbundle.orgauth.rmpartyrelation.util.IRmPartyRelationConstants"%>
<%@page import="org.quickbundle.orgauth.rmpartyrelation.vo.RmPartyRelationVo"%>
<%
	//团体类型
	String party_type = request.getParameter("party_type");
	if("relationViewRootNode".equals(request.getParameter("cmd"))) {
		RmPartyRelationVo prvo = new RmPartyRelationVo();
		RmPopulateHelper.populate(prvo, request);
		prvo.setParty_view_id(request.getParameter("view_id"));
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		prService.insert(prvo);
		response.sendRedirect("manageParty.jsp?refresh_parent=1&view_id=" + request.getParameter("view_id") + "&child_party_code=" + request.getParameter("parent_party_code"));
	} else if("relationChildNode".equals(request.getParameter("cmd"))) {
		RmPartyRelationVo prvo = new RmPartyRelationVo();
		RmPopulateHelper.populate(prvo, request);
		prvo.setParty_view_id(request.getParameter("view_id"));
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		prService.insert(prvo);
		response.sendRedirect("manageParty.jsp?refresh_parent=1&view_id=" + request.getParameter("view_id") + "&child_party_code=" + request.getParameter("parent_party_code"));
	} else if("addViewRootNode".equals(request.getParameter("cmd"))) {
		//团体
		RmPartyVo pvo = new RmPartyVo();
		RmPopulateHelper.populate(pvo, request);
		IRmPartyService pService = (IRmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
		String id = pService.insert(pvo);

		//团体关系
		RmPartyRelationVo prvo = new RmPartyRelationVo();
		prvo.setParty_view_id(request.getParameter("view_id"));
		prvo.setChild_party_id(pvo.getId());
		prvo.setChild_party_name(pvo.getName());
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		prService.insert(prvo);
		RmJspHelper.goUrlWithAlert(request, response, "创建根节点成功", "/jsp/util/refreshParent.jsp");
	} else if("insertChildNode".equals(request.getParameter("cmd"))) {
		//团体
		RmPartyVo pvo = new RmPartyVo();
		RmPopulateHelper.populate(pvo, request);
		IRmPartyService pService = (IRmPartyService)RmBeanFactory.getBean(IRmPartyConstants.SERVICE_KEY);
		String id = pService.insert(pvo);
		//团体关系
		RmPartyRelationVo prvo = new RmPartyRelationVo();
		prvo.setParty_view_id(request.getParameter("view_id"));
		prvo.setParent_party_id(request.getParameter("parent_party_id"));
		prvo.setChild_party_id(pvo.getId());
		prvo.setChild_party_name(pvo.getName());
		IRmPartyRelationService prService = (IRmPartyRelationService)RmBeanFactory.getBean(IRmPartyRelationConstants.SERVICE_KEY);
		prService.insert(prvo);
		RmJspHelper.goUrlWithAlert(request, response, "新增子节点成功", "/jsp/util/refreshParent.jsp");
	} else if("deleteChildNode".equals(request.getParameter("cmd"))) {
		RmProjectHelper.getCommonServiceInstance().doUpdateBatch(new String[]{
			"delete from RM_PARTY_RELATION where CHILD_PARTY_CODE='" + request.getParameter("parent_party_code") + "' and party_view_id=" + request.getParameter("view_id"),
			"delete from RM_PARTY where ID=" + request.getParameter("parent_party_id")
		});
		RmJspHelper.goUrlWithAlert(request, response, "删除本节点成功", "/jsp/util/refreshParent.jsp");
	} else if("deleteChildRelation".equals(request.getParameter("cmd"))) {
		RmProjectHelper.getCommonServiceInstance().doUpdateBatch(new String[]{
				"delete from RM_PARTY_RELATION where CHILD_PARTY_CODE='" + request.getParameter("parent_party_code") + "' and party_view_id=" + request.getParameter("view_id")
			});
			RmJspHelper.goUrlWithAlert(request, response, "删除本关系成功", "/jsp/util/refreshParent.jsp");
	} else if("updateOrderCode".equals(request.getParameter("cmdInput"))) {
		RmProjectHelper.getCommonServiceInstance().doUpdate("update RM_PARTY_RELATION SET ORDER_CODE=? WHERE CHILD_PARTY_CODE=?", 
				new String[]{request.getParameter("order_code"), request.getParameter("child_party_code")});
	} else if("updateThisPartyName".equals(request.getParameter("cmdInput"))) {
		String thisPartyName = request.getParameter("this_party_name");
		String thisPartyId = request.getParameter("parent_party_id");
		RmProjectHelper.getCommonServiceInstance().doUpdateBatch(new String[]{
				"update RM_PARTY_RELATION SET CHILD_PARTY_NAME='" + thisPartyName + "' WHERE CHILD_PARTY_ID=" + thisPartyId, 
				"update RM_PARTY_RELATION SET PARENT_PARTY_NAME='" + thisPartyName + "' WHERE PARENT_PARTY_ID=" + thisPartyId,
				"update RM_PARTY SET NAME='" + thisPartyName + "' WHERE ID=" + thisPartyId,
		});
	}
	
	String view_id = request.getParameter("view_id");
	//当前的party
	RmCommonVo cvo = null;
	String child_party_code = request.getParameter("child_party_code");
	if(child_party_code == null) {
		child_party_code = "";
	} else {
		List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery("select * from RM_PARTY_RELATION where child_party_code='" + child_party_code + "'");
		if(lcvo.size() > 0) {
			cvo = lcvo.get(0);
		}
	}
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%
	String ptCondition = null; 
	if(cvo != null){
		ptCondition = "BS_KEYWORD in(select c.BS_KEYWORD from RM_PARTY a " +
				"join RM_PARTY_TYPE_RELATION_RULE b on a.PARTY_TYPE_ID=b.PARENT_PARTY_TYPE_ID " +  
				"join RM_PARTY_TYPE c on b.CHILD_PARTY_TYPE_ID=c.id " + 
				"where b.PARTY_VIEW_ID=" + view_id + " and a.ID=" + cvo.getString("child_party_id") + ")";
	} else {
		ptCondition = "BS_KEYWORD in (select a.BS_KEYWORD from RM_PARTY_TYPE a join RM_PARTY_TYPE_RELATION_RULE b on a.ID=b.CHILD_PARTY_TYPE_ID " +
				" where b.PARENT_PARTY_TYPE_ID='' or b.PARENT_PARTY_TYPE_ID is null )";
	}
	//当前节点规则
	RmPartyTypeService partyTypeService = (RmPartyTypeService)RmBeanFactory.getBean(IRmPartyTypeConstants.SERVICE_KEY);
	List<RmPartyTypeVo> lAvailablePartyType = partyTypeService.queryByCondition(ptCondition, null);
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form" method="post">
<table class="mainTable" width="100%">
	<tr>
		<td align="left">
			<table width="100%">
		<%if(cvo != null) { %>
				<tr>
					<td align="right">本节点</td>
					<td>
						<%=cvo.getString("child_party_name") %>(ID:<%=cvo.getString("child_party_id") %>)
					</td>
				</tr>
				<tr>
					<td align="right">本关系</td>
					<td>
						父节点<%=cvo.getString("parent_party_name") %>(子编码:<%=cvo.getString("child_party_code") %>)
					</td>
				</tr>
				<tr>
					<td align="right">名称</td>
					<td>
						<input type="text" class="text_field_half" name="this_party_name" maxLength="100" value="<%=cvo.getString("child_party_name")%>" />
						<input type="button" onclick="updateThisPartyName_onClick();" class="button_ellipse" value="更新名称">
					</td>
				</tr>
				<tr>
					<td align="right">排序值</td>
					<td>
						<input type="text" class="text_field_half" name="order_code" maxLength="100" value="<%=cvo.getString("order_code")%>" />
						<input type="button" onclick="if(!getConfirm()) {return false;} form.cmdInput.value='updateOrderCode';form.submit();" class="button_ellipse" value="更新排序值">
					</td>
				</tr>
		<%} %>
				<tr>
					<td align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("party_type_id")%></td>
					<td>
						<select name="party_type_id" id="party_type_id" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("party_type_id")%>" validate="notNull;" >
							<option value="">请选择</option>
							<% for(RmPartyTypeVo vo : lAvailablePartyType){ %>
							<option value="<%=vo.getId()%>"  id="<%=vo.getBs_keyword()%>"><%=vo.getName() %></option>
							<%} %>
						</select>
						<script type="text/javascript">
							document.getElementById("party_type_id").value='<%=request.getParameter("r_type")==null?"":request.getParameter("r_type")%>'
						</script>
						<!--<input type="text" class="text_field_reference_readonly" validate='notNull;' hiddenInputId="party_type_id" name="party_type_id_name" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("party_type_id")%>" value="" /><input type="hidden" name="party_type_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.party_type_id, form.party_type_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmPartyTypeAction.do?cmd=queryReference&referenceInputType=radio');"/>
					--></td>
				</tr>
				<tr>
					<td  align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
					<td>
						<input type="text" class="text_field" name="name" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
					</td>
				</tr>
				<tr>
					<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
					<td>
						<textarea class="textarea_limit_words" cols="60" rows="5" name="description" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500" ></textarea>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="center"">
		<% if(child_party_code!=null&&!child_party_code.equals("")) {%>
			<input type="button" onclickto="javascript:insertChildNode()" class="button_ellipse" value="创建子节点" />
			<input type="button" onclick="javascript:relationChildNode()" class="button_ellipse" value="关联子节点" />
			<input type="button" onclick="javascript:form.action='manageParty.jsp?cmd=deleteChildNode';form.submit()" class="button_ellipse" value="删除本节点" />
			<input type="button" onclick="javascript:form.action='manageParty.jsp?cmd=deleteChildRelation';form.submit()" class="button_ellipse" value="删除本关系" />
		<% } %>	
			<input type="button" class="button_ellipse" onclickto="javascript:addViewRootNode()" value="创建根节点">		
			<input type="button" class="button_ellipse" onclick="javascript:relationViewRootNode()" value="关联根节点">
		</td>
	</tr>
</table>

<input type="hidden" name="view_id" value="<%=view_id%>">
<input type="hidden" name="parent_party_id" value="<%=cvo != null ? cvo.getString("child_party_id") : ""%>">
<input type="hidden" name="parent_party_name" value="<%=cvo != null ? cvo.getString("child_party_name") : ""%>">
<input type="hidden" name="parent_party_code" value="<%=child_party_code%>">
<input type="hidden" name="child_party_id" value="">
<input type="hidden" name="child_party_name" value="">

<input type="hidden" name="is_inherit" value="1">
	
<input type="hidden" name="cmdInput" value="">
	
</form>
</body>
</html>
<script type="text/javascript">
function insertChildNode() {
	if($("#party_type_id").val() == '<%=RmPartyTypeCache.getPartyType(IOrgauthConstants.OrgTree.USER.value()).getId()%>') {
		alert("请在“员工管理”节点中新增用户！");
		return false;
	}
	form.action="manageParty.jsp?cmd=insertChildNode";
	form.submit();
}

function relationChildNode() {
	getReference(new Array(form.child_party_id, form.child_party_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmPartyAction.do?cmd=queryReference&referenceInputType=radio');
	if(form.child_party_id.value != "") {
		form.action="manageParty.jsp?cmd=relationChildNode";
		form.submit();
	}
}

function addViewRootNode() {
	if($("#party_type_id").val() == '<%=RmPartyTypeCache.getPartyType(IOrgauthConstants.OrgTree.USER.value()).getId()%>') {
		alert("请在“员工管理”节点中新增用户！");
		return false;
	}
	form.action="manageParty.jsp?cmd=addViewRootNode";
	form.submit();
}

function relationViewRootNode() {
	getReference(new Array(form.child_party_id, form.child_party_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmPartyAction.do?cmd=queryReference&referenceInputType=radio');
	if(form.child_party_id.value != "") {
		form.action="manageParty.jsp?cmd=relationViewRootNode";
		form.submit();
	}
}

function updateThisPartyName_onClick() {
	if(form.this_party_name.value == '') {
		alert('名称不能为空');
		form.this_party_name.focus();
		return;
	};
	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
		return false;
	}
	form.cmdInput.value='updateThisPartyName';
	form.submit();
	
}

<%if("1".equals(request.getParameter("refresh_parent"))) { %>
	parent.location.reload();
<%}%>
</script>