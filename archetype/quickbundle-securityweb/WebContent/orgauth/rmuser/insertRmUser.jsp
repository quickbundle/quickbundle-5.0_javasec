<%@page import="org.quickbundle.orgauth.cache.RmPartyTypeCache"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmuser.vo.RmUserVo" %>
<%@ page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%  //判断是否为修改页面
  	RmUserVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmUserConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmUserVo)request.getAttribute(IRmUserConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
	String parent_party_id = "" ;
	/*
	String admin_type = RmProjectHelper.getRmUserVo(request).getAdmin_type();
	Map<String, Object> adminTypeMap = RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE);
	Map<String, Object> mAdminType =new HashMap<String,Object>();
	for(Map.Entry<String, Object> entry:adminTypeMap.entrySet()){
		if(!"3".equals(entry.getKey())){
			mAdminType.put(entry.getKey(),entry.getValue());
		}
	}

	if(!"3".equals(RmProjectHelper.getRmUserVo(request).getAdmin_type())){
		if(RmProjectHelper.getRmUserVo(request).getParty_id_org()!=null){
			//公司id
			parent_party_id = RmProjectHelper.getRmUserVo(request).getParty_id_org();
		}
	}

	String org_name = "";
	String org_id = "";
	if(isModify && resultVo !=null){
		List<RmCommonVo> cVoList = RmProjectHelper.getCommonServiceInstance().doQuery("select P.ID,P.NAME from RM_PARTY P where P.ID in(select PR.PARENT_PARTY_ID from RM_USER U join RM_PARTY_RELATION PR on U.ID=PR.CHILD_PARTY_ID where U.USABLE_STATUS='1' and U.ID="+resultVo.getId()+" and PR.PARTY_VIEW_ID="+IOrgauthConstants.PartyView.DEFAULT.id()+") and P.USABLE_STATUS='1'");
		for(RmCommonVo vo:cVoList){
			org_name += vo.getString("name")+",";
			org_id += vo.getString("id")+",";
		}
		if(org_name.length()>0){
			org_name = org_name.substring(0,org_name.length()-1);
			org_id = org_id.substring(0,org_id.length()-1);
		}
	}
	*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmUserAction";
	function insert_onClick(){  //插入单条数据
		if(!validateLoginId()){
			return false;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
		if(!validateLoginId()){
			return false;
		}
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
    	form.submit();
	}
	function openTree(organization_id,organization_name){ //组织树
		var sViewId = $("#view_id").val();
		//alert(sViewId);
		var urlPath = '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.DEFAULT.value()%>&enableCookie=true' + 
			"&defaultSelectedNodesValue=" + organization_id + 
			'&inputType=radio&parent_party_id=<%=parent_party_id %>&submit_bk=<%=IOrgauthConstants.OrgTree.COMPANY.value()+","+IOrgauthConstants.OrgTree.DEPARTMENT.value()+","+IOrgauthConstants.OrgTree.DEFAULT%>&include_self=1&view_id=' + sViewId;
		getPartyWindow(new Array(organization_id,organization_name), '<%=request.getContextPath()%>/', urlPath, '350', '500');
	}
	//ajax验证
	$.ajaxSetup({async:false,type:'POST'});
	function validateLoginId(){
		var sValue = true;
		$.getJSON("<%=request.getContextPath()%>/RmUserAction.do?cmd=validateLoginId", 
			{
				login_id:$("#login_id").val(),
				id:$("#user_id").val()
			}, 
			function(data){
				if(data == '1') {
					//alert("登录账号重复！");
					writeValidateInfo("登录账号已被使用，请重新输入！",document.getElementById("login_id"));
					sValue = false;
				}else{
					sValue = true;
				}
			});
		return sValue;
	}
	function goUrl(){
	    if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    window.location.href="<%=request.getContextPath()%>/RmUserAction.do?cmd=simpleQuery";
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="16%">&nbsp;</td>
		<td width="34%">&nbsp;</td>
		<td align="right" width="17%">&nbsp;</td>
		<td width="33%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td align="left">
			<input type="text" class="text_field" name="name" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="isFilterChar;notNull;"/>
		</td>
		<td align="right"><span class="style_required_red">* </span><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")%>：</td>
		<td align="left"><%=RmJspHelper.getSelectField("lock_status", -1, RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS), "1", " class='text_field_select' inputName='" + IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status") + "' validate='notNull;'", false) %></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>：</td>
		<td align="left">
			<% if(isModify && resultVo != null){ %>
				<input type="text" class="text_field" id="login_id" name="login_id" readonly="readonly" maxLength="100" />
			<% }else{ %>
			<input type="text" class="text_field" id="login_id" name="login_id" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>" value="" maxLength="100" validate="notNull;"/>
			<% } %>
		</td>
		<td align="right"><span class="style_required_red">* </span><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>：</td>
		<td align="left">
        <input type="text"  class="text_field" name="email" inputname="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>" validate="notNull;isEmail" maxlength="100" />
        </td>
	</tr>
	
		<!-- 
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id")%>：</td>
		<td align="left">
		<input type="text" class="text_field_readonly" hiddenInputId="employee_id" name="employee_id" inputName="员工ID" value="" />
		<input class="text_field_readonly" type="text" name="employee_id"><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.employee_id, ''), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.USER.value()%>&enableCookie=true&inputType=radio&rootXmlSource=authorizeUser&parent_party_id=<%=parent_party_id %>&include_self=1&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id()%>');"/>
		</td>
		-->
       <tr>
		<td align="right"><%if(!isModify){ %><span class="style_required_red">* </span><%} %>输入密码：</td>
		<td align="left"><input  type="password" class="text_field" id="newkw" name="password" validate="<%if(!isModify){ %>notNull;isSmallLength;<%} %>isValidString;notContainChinese;isFilterChar;" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("password")%>" value="" maxlength="20" /></td>
        <td align="right"><%if(!isModify){ %><span class="style_required_red">* </span><%} %>密码确认：</td>	
        <td align="left">
        	<input  type="password" class="text_field" name="password2" validate="<%if(!isModify){ %>notNull;isSmallLength;<%} %>isValidString;ispwd;notContainChinese;" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("password")%>" value="" maxlength="20" /></td>
        </td>
       </tr> 
	<!--  
	<tr>
		<td align="right"><span class="style_required_red">* </span>组织视图：</td>
		<td>		
		<%
			/*此项目中默认只选择一个视图
			List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery("select ID, NAME from RM_PARTY_VIEW");
			Map<String, String> mpv = new HashMap<String, String>();
			for(RmCommonVo cvo : lcvo) {
				mpv.put(cvo.getString("id"), cvo.getString("name"));
			}
			out.print(RmJspHelper.getSelectField("view_id", -1, mpv, IOrgauthConstants.PARTY_VIEW_ID_STANDARD_ORG, " id='view_id'  class='text_field_select' ", false));
			*/
		%>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>组织类型：</td>
		<td colspan="3" align="left">		
		<%
			/**
			List<RmCommonVo> ptVoList = RmProjectHelper.getCommonServiceInstance().doQuery("select ID, NAME ,BS_KEYWORD from RM_PARTY_TYPE");
			Map<String, String> mpt = new HashMap<String, String>();
			for(RmCommonVo ptVo : ptVoList) {
				if("RM_USER".equals(ptVo.getString("bs_keyword"))){ //只显示用户
					mpt.put(ptVo.getString("id"), ptVo.getString("name"));
				}
			}
			out.print(RmJspHelper.getSelectField("party_type_id", -1, mpt, "", " id='party_type_id' class='text_field_select' ", false));
			**/
		%>
		</td>	
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>：</td>
		<td align="left">
		<input type="hidden" name="party_type_id"  value="<%--=IOrgauthConstants.Config.isUserRelationParty.value() ? RmPartyTypeCache.getPartyType(IRmUserConstants.TABLE_NAME).getId() : ""--%>"/>
        <input type="text"  class="text_field" name="email" inputname="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>" value="" maxlength="100" />
        </td>	
	</tr>
	-->
<%if(IOrgauthConstants.Config.isUserRelationParty.value()) { %>
	<tr>
		<td align="right">所属组织：</td>
	  	<td align="left" colspan="3">
	  		<input type="text" class="text_field_reference_readonly" name="organization_name" inputName="所属组织" hiddenInputId="organization_id" id="organization_name" /><img class="refButtonClass" src="<%=request.getContextPath() %>/images/09.gif" onClick="openTree(organization_id,organization_name)"/>
      		<input type="hidden" value="" name="organization_id" id="organization_id">
      	</td>
	</tr>
<%} %>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td colspan="3" align="left">
			<textarea class="textarea_limit_words" cols="90" rows="5" validate="isMaxLength" maxLength="200" name="description" inputName="<%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("description")%>" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">用户权限类型</td>
		<td colspan="3">
			<%=RmJspHelper.getSelectField("admin_type", -1, RmGlobalReference.get(IGlobalConstants.DICTIONARY_RM_ADMIN_TYPE), "1", "inputName='用户权限类型'", true) %>
		</td>
	</tr>
</table>
	<table align="center">
		<tr>
			<td><br>
				<input type="button" class="button_ellipse" id="button_save" value="保存" onClickTo="javascript:<%=isModify?"update_onClick()":"insert_onClick()"%>"/>
				<input type="button" class="button_ellipse" id="button_cancel" value="返回" onClick="goUrl()"/>
			</td>
		</tr>
	</table>

	<input type="hidden" name="id" value="">
	<input type="hidden" name="view_id" value="<%=IOrgauthConstants.PartyView.DEFAULT.id() %>" id="view_id">
	<input type="hidden" name="isInherit" value="0">
	<input type="hidden" name="oldName" value="<%= resultVo!=null ? resultVo.getName():""%>">
	<input type="hidden" name="party_type_id"  value="<%=IOrgauthConstants.Config.isUserRelationParty.value() && RmPartyTypeCache.getPartyType(IRmUserConstants.TABLE_NAME) != null ? RmPartyTypeCache.getPartyType(IRmUserConstants.TABLE_NAME).getId() : ""%>"/>
	<%if(isModify){%>
	<input type="hidden"  name="last_login_date"  />
	<input type="hidden"  name="last_login_ip"  />
	<input type="hidden"  name="login_sum"  />
	<input type="hidden"  name="login_status"  />
	<% } %>
	<input type="hidden"  name="user_id" value="<%=resultVo != null ?resultVo.getId():""%>" id="user_id">
</form>
</body>
</html>
<script type="text/javascript">
<%  //取出要修改的那条记录，并且回写表单
	if(isModify) {  //如果本页面是修改页面
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromVo(resultVo), new String[]{"password"}));  //输出表单回写方法的脚本
  	}
%>
</script>