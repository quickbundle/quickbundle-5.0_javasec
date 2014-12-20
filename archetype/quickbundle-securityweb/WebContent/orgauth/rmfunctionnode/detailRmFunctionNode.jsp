<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.vo.RmFunctionNodeVo" %>
<%@ page import="org.quickbundle.orgauth.rmfunctionnode.util.IRmFunctionNodeConstants" %>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%  //取出本条记录
	RmFunctionNodeVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmFunctionNodeVo)request.getAttribute(IRmFunctionNodeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmFunctionNodeAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.target="_parent";
		form.submit();
	}  
	function insertChild_onClick(){  //插入子节点
    	form.action="<%=request.getContextPath()%>/orgauth/rmfunctionnode/insertRmFunctionNode.jsp?parent_total_code=<%=resultVo.getTotal_code()%>";
	    form.submit();
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
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("node_type")%>：</td>
		<td><%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCTION_NODE_TYPE, resultVo.getNode_type())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("function_property")%>：</td>
		<td><%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_FUNCTION_PROPERTY, resultVo.getFunction_property())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getName())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("enable_status")%>：</td>
		<td><%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_ENABLE_STATUS, resultVo.getEnable_status())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("total_code")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getTotal_code())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("order_code")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getOrder_code())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("funcnode_authorize_type")%>：</td>
		<td><%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_FUNCNODE_AUTHORIZE_TYPE, resultVo.getFuncnode_authorize_type())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("data_value")%>：</td>
		<td align="left"><%=RmStringHelper.prt(resultVo.getData_value())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("is_leaf")%>：</td>
		<td align="left" colspan="3"><%=RmGlobalReference.get(IRmFunctionNodeConstants.DICTIONARY_RM_YES_NOT, resultVo.getIs_leaf())%>&nbsp;</td>	
	</tr>
	<!--<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getPattern_value())%>&nbsp;</td>
	</tr>-->
	<!--
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("icon")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getIcon())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmFunctionNodeConstants.TABLE_COLUMN_CHINESE.get("help_name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getHelp_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>-->
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">
<input type="hidden" name="total_code" value="<%=RmStringHelper.prt(resultVo.getTotal_code())%>">
<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onClick="javascript:delete_onClick();">
			<input type="button" class="button_ellipse" id="button_insertChild" value="新建菜单" onClick="javascript:insertChild_onClick()"/>
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
<iframe width="100%" height="500" frameBorder="0" src="<%=request.getContextPath()%>/project/authorize/masterAuthority.jsp?bs_keyword=<%=IOrgauthConstants.Authorize.FUNCTION_NODE.bsKeyword()%>&old_resource_id=<%=resultVo.getTotal_code()%>"></iframe>
</body>
</html>