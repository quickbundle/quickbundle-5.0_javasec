<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmuseronlinerecord.vo.RmUserOnlineRecordVo" %>
<%@ page import="org.quickbundle.orgauth.rmuseronlinerecord.util.IRmUserOnlineRecordConstants" %>
<%  //判断是否为修改页面
  	RmUserOnlineRecordVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmUserOnlineRecordConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmUserOnlineRecordVo)request.getAttribute(IRmUserOnlineRecordConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
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
	var rmActionName = "RmUserOnlineRecordConditionAction";
	function insert_onClick(){  //插入单条数据
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
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
		<td align="right"><span class="style_required_red">* </span><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("user_id")%></td>
		<td>
			<input type="text" class="text_field_reference" validate='notNull;' hiddenInputId="user_id" name="user_id_name" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("user_id")%>" value="" /><input type="hidden" name="user_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.user_id, form.user_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmUserAction.do?cmd=queryReference&referenceInputType=radio');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_time")%></td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="login_time" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_time")%>" validate="notNull;"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.login_time,'<%=request.getContextPath()%>/');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("cluster_node_id")%></td>
		<td>
			<input type="text" class="text_field" name="cluster_node_id" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("cluster_node_id")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_sign")%></td>
		<td>
			<input type="text" class="text_field" name="login_sign" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_sign")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_ip")%></td>
		<td>
			<input type="text" class="text_field" name="login_ip" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_ip")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_uuid")%></td>
		<td>
			<input type="text" class="text_field" name="login_uuid" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("login_uuid")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("logout_time")%></td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="logout_time" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("logout_time")%>" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.logout_time,'<%=request.getContextPath()%>/');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("logout_type")%></td>
		<td>
			<%=RmJspHelper.getSelectField("logout_type", -1, RmGlobalReference.get(IRmUserOnlineRecordConstants.DICTIONARY_RM_LOGOUT_TYPE), "", "inputName='" + IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("logout_type") + "' ", true) %>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("online_time")%></td>
		<td>
			<input type="text" class="text_field" name="online_time" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("online_time")%>" value="" integerDigits="38" decimalDigits="0" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("last_operation")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="last_operation" inputName="<%=IRmUserOnlineRecordConstants.TABLE_COLUMN_CHINESE.get("last_operation")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	</table>
  
<input type="hidden" name="id" value="">

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
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromRequest(request, IRmUserOnlineRecordConstants.DEFAULT_CONDITION_KEY_ARRAY)));  //输出表单回写方法的脚本
  	}
%>
autoPatchParentIdName("<%=IRmUserOnlineRecordConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>");
</script>