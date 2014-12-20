<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants" %>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%  //判断是否为修改页面
  	RmFunctionNodeVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmFunctionNodeConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmFunctionNodeVo)request.getAttribute(IRmFunctionNodeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page imorg.quickbundle.project.RmProjectHelperHelper"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmFunctionNodeAction";
	function insert_onClick(){  //插入单条数据
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
    	form.target="_parent";
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
	    form.target="_parent";
    	form.submit();
	}
</script>
<%
	String default_total_code = "";
	String parent_total_code =  request.getParameter("parent_total_code") ;
	if(RmStringHelper.checkNotEmpty(parent_total_code)) {
		List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQuery("select max(TOTAL_CODE) max_total_code from RM_FUNCTION_NODE where TOTAL_CODE like '" + parent_total_code + "%' and length(TOTAL_CODE) = " + (parent_total_code.length()+RmConfig.getSingleton().getDefaultTreeCodeFirst().length()));
		if(lvo.size() > 0) {
			String max_total_code = lvo.get(0).getString("max_total_code");
			if(max_total_code != null && max_total_code.length() > 0) {
				String temp_code = max_total_code.substring(max_total_code.length() - RmConfig.getSingleton().getDefaultTreeCodeFirst().length());
				long nextLasePhaseCode = Long.valueOf(temp_code) + 1;
				default_total_code = max_total_code.substring(0, max_total_code.length() - RmConfig.getSingleton().getDefaultTreeCodeFirst().length()) + nextLasePhaseCode;
				
			} else {
				default_total_code = parent_total_code + RmConfig.getSingleton().getDefaultTreeCodeFirst();
			}
		}
	} else {
		default_total_code = RmConfig.getSingleton().getDefaultTreeCodeFirst();
	}
%>
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
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("node_type")%>：</td>
		<td align="left">
			<%=RmJspHelper.getSelectField("node_type", -1, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCTION_NODE_TYPE), "2", "inputName='" + IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("node_type") + "' validate='notNull;'", true) %>
		</td>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("function_property")%>：</td>
		<td align="left">
			<%=RmJspHelper.getSelectField("function_property", -1, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_FUNCTION_PROPERTY), "0", "inputName='" + IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("function_property") + "' validate='notNull;'", true) %>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td align="left">
			<input type="text" class="text_field" name="name" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>：</td>
		<td align="left">
			<%=RmJspHelper.getSelectField("enable_status", -1, RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_ENABLE_STATUS), "1", "inputName='" + IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("enable_status") + "' validate='notNull;'", true) %>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("funcnode_authorize_type")%>：</td>
		<td align="left">
		<%
			/**
			Map<String,String> mFuncnodeAuthorizeType = RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCNODE_AUTHORIZE_TYPE);
			Map<String,String> mapNodrType = new HashMap<String,String>();
			for(Map.Entry<String,String> mType:mFuncnodeAuthorizeType.entrySet()){
				if(!"0".equals(mType.getKey())){
					mapNodrType.put(mType.getKey(),mType.getValue());
				}
			}*/
		%>
		<%//=RmJspHelper.getSelectField("funcnode_authorize_type", -1, mapNodrType, "", "inputName='" + IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("funcnode_authorize_type") + "' validate='notNull;' ", false) %>
		<% if(isModify){ %>
			<%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCNODE_AUTHORIZE_TYPE, resultVo.getFuncnode_authorize_type())%>
			<input name="funcnode_authorize_type" type="hidden" value="0"/>
		<% }else{ %>
			<%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCNODE_AUTHORIZE_TYPE, "1")%>
			<input name="funcnode_authorize_type" type="hidden" value="1"/>
		<% } %>
		</td>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("total_code")%>：</td>
		<td align="left">
		<% if(isModify){ %>
			<input type="text" name="total_code" value="" readonly="readonly"/>
		<%} else{ %>
			<input type="text" name="total_code" value="<%=default_total_code %>" readonly="readonly"/>
		<%}%>
		
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("order_code")%></td>
		<td>
			<input type="text" class="text_field" name="order_code" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("order_code")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td colspan="3" align="left">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="description" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("data_value")%>：</td>
		<td colspan="3" align="left">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="data_value" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("data_value")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<!--
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%></td>
		<td colspan="3" align="left">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="pattern_value" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("total_code")%></td>
		<td>
			<input type="text" class="text_field" name="total_code" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("total_code")%>" value="<%=default_total_code%>" maxLength="100" validate="notNull;" readonly="readonly"/>
		</td>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("icon")%></td>
		<td align="left">
			<input type="text" class="text_field" name="icon" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("icon")%>" value="" maxLength="100" />
		</td>
	</tr>
	<!--
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("help_name")%></td>
		<td align="left">
			<input type="text" class="text_field" name="help_name" inputName="<%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("help_name")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>-->
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	</table>
  
<input type="hidden" name="id" value="">
<input type="hidden" name="parent_total_code" value="">
<%
	if(isModify && resultVo!=null){
%>
<input type="hidden" name="is_leaf" >
<% } %>
<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_save" value="保存" onClickTo="javascript:<%=isModify?"update_onClick()":"insert_onClick()"%>"/>
			<input type="button" class="button_ellipse" id="button_cancel" value="取消" onClick="javascript:history.go(-1)"/>
			<input type="reset" class="button_ellipse" id="button_reset" value="重置"/>
		</td>
	</tr>
</table>

</form>
</body>
</html>
<script type="text/javascript">
<%  //取出要修改的那条记录，并且回写表单
	if(isModify) {  //如果本页面是修改页面
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromVo(resultVo)));  //输出表单回写方法的脚本
  	} else {
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromRequest(request, IRmFunctionNodeConstants.DEFAULT_CONDITION_KEY_ARRAY)));  //输出表单回写方法的脚本
  	}
%>
autoPatchParentIdName("<%=IRmFunctionNodeConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>");
</script>