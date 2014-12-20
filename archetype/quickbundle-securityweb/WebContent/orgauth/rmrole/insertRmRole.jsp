<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.orgauth.rmrole.vo.RmRoleVo" %>
<%@page import="org.quickbundle.orgauth.rmrole.util.IRmRoleConstants" %>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%  //判断是否为修改页面
  	RmRoleVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmRoleConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmRoleVo)request.getAttribute(IRmRoleConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
	String parent_party_id = "" ;
	String parent_party_name = "" ;
	if(!"3".equals(RmProjectHelper.getRmUserVo(request).getAdmin_type())){
		if(RmProjectHelper.getRmUserVo(request).getParty_id_org()!=null){
			//公司id
			parent_party_id = RmProjectHelper.getRmUserVo(request).getParty_id_org();
			parent_party_name = RmProjectHelper.getRmUserVo(request).getParty_id_org_name();
		}
	}
	if(isModify && RmStringHelper.checkNotEmpty(resultVo.getOwner_org_id())){
		parent_party_id = resultVo.getOwner_org_id();
		RmCommonVo vo = null;
		List<RmCommonVo> listVo = RmProjectHelper.getCommonServiceInstance().doQuery("select p.name from RM_PARTY p where p.USABLE_STATUS='1' and p.id="+parent_party_id);
		if(listVo.size()>0){
			vo = listVo.get(0);
			parent_party_name =RmStringHelper.prt(vo.getString("name"));
		}
	}
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
	var rmActionName = "RmRoleAction";
	function insert_onClick(){  //插入单条数据
		if(!validateRoleCode()){
			return false;
		}
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
		if(!validateRoleCode()){
			return false;
		}
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
    	form.submit();
	}
	function tree_onClick(owner_org_id,owner_org_id_name){
		getPartyWindow(new Array(owner_org_id, owner_org_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=IOrgauthConstants.OrgTree.COMPANY.value()%>&enableCookie=true&inputType=radio&parent_party_id=<%=parent_party_id %>&include_self=1&view_id=<%=IOrgauthConstants.PartyView.DEFAULT.id()%>');
	}
	function owner_onclick(sc){
		if(sc.checked){
			$("#owner_id").html("");
			$("#owner_id_name").html("");
		}else{
			$("#owner_id").html("<input type='text' class='text_field_readonly' value='<%=RmStringHelper.prt(parent_party_name) %>' hiddenInputId='owner_org_id' validate='notNull;' name='owner_org_id_name' inputName='<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>' value='' /><input type='hidden' value='<%=RmStringHelper.prt(parent_party_id) %>' name='owner_org_id'><img class='refButtonClass' src='<%=request.getContextPath()%>/images/09.gif' onClick='tree_onClick(owner_org_id,owner_org_id_name)' />");
			$("#owner_id_name").html("<span class='style_required_red'>* </span><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>：");
		}
	}
	//ajax验证
	$.ajaxSetup({async:false,type:'POST'});
	function validateRoleCode(){
		var sValue = true;
		$.getJSON("<%=request.getContextPath()%>/RmRoleAction.do?cmd=validateRoleCode", 
			{
			    role_code:$("#role_code").val(),
				id:$("#role_id").val()
			}, 
			function(data){
				if(data == '1') {
					writeValidateInfo("角色编码已被使用，请重新输入！",document.getElementById("role_code"));
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
	    window.location.href="<%=request.getContextPath()%>/RmRoleAction.do?cmd=simpleQuery";
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table width="1151" class="mainTable">
	<tr>
		<td align="right" width="24%">&nbsp;</td>
		<td width="20%">&nbsp;</td>
		<td align="right" width="27%">&nbsp;</td>
		<td width="29%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td align="left">
			<input type="text" class="text_field" name="name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>：</td>
		<td align="left">
			<input type="text" class="text_field" id="role_code" name="role_code" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("role_code")%>" value="" maxLength="100" validate="notNull;"/>
		</td>	
		<td align="right"><span class="style_required_red">* </span><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>：</td>
		<td align="left">
			<%=RmJspHelper.getSelectField("enable_status", -1, RmGlobalReference.get(IRmRoleConstants.DICTIONARY_RM_ENABLE_STATUS), "1", "inputName='" + IRmRoleConstants.TABLE_COLUMN_CHINESE.get("enable_status") + "' validate='notNull;'", true) %>
		</td>
	</tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("is_system_level")%>：</td>
		<td align="left">
			<input type="checkbox" class="rm_checkbox" hiddenInputId="is_system_level" name="is_system_level_rmCheckbox" id="is_system_level_rmCheckbox" onclick="owner_onclick(this)" /><input type="hidden" name="is_system_level"/>
		</td>
		<td align="right"><div id="owner_id_name"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>：</div></td>
		<td align="left">
			<div id="owner_id" align="left">
				<input type="text" class="text_field_readonly"  value="<%=RmStringHelper.prt(parent_party_name) %>" hiddenInputId="owner_org_id" name="owner_org_id_name" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>" value="" /><input type="hidden" value="<%=RmStringHelper.prt(parent_party_id) %>" name="owner_org_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="tree_onClick(owner_org_id,owner_org_id_name)"/>        
        	</div>
        </td>
	</tr>
	<tr>
		<td align="right"><%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td colspan="3" align="left">
			<textarea class="textarea_limit_words" cols="80" rows="5" name="description" inputName="<%=IRmRoleConstants.TABLE_COLUMN_CHINESE.get("description")%>" ></textarea>
		</td>
	</tr>
	</table>
	<input type="hidden" name="id" value="">
	<input type="hidden" id="role_id" name="role_id" value="<%=resultVo != null ?resultVo.getId():"" %>"/>
	<table align="center">
		<tr>
			<td><br>
				<input type="button" class="button_ellipse" id="button_save" value="保存" onClickTo="javascript:<%=isModify?"update_onClick()":"insert_onClick()"%>"/>
				<input type="button" class="button_ellipse" id="button_cancel" value="返回" onClick="javascript:goUrl()"/>
			</td>
		</tr>
	</table>

</form>
</body>
</html>
<script type="text/javascript">
<%
	if(isModify && "1".equals(resultVo.getIs_system_level())){
%>		
	$("#owner_id").html("");
	$("#owner_id_name").html("");
<%	}
%>
<%  //取出要修改的那条记录，并且回写表单
	if(isModify) {  //如果本页面是修改页面
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromVo(resultVo)));  //输出表单回写方法的脚本
  	}
%>
</script>