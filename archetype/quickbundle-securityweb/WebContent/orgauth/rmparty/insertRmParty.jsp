<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmparty.vo.RmPartyVo" %>
<%@ page import="org.quickbundle.orgauth.rmparty.util.IRmPartyConstants" %>
<%  //判断是否为修改页面
  	RmPartyVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmPartyConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmPartyVo)request.getAttribute(IRmPartyConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
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
	var rmActionName = "RmPartyAction";
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
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("party_type_id")%></td>
		<td>
			<input type="text" class="text_field_reference" validate='notNull;' hiddenInputId="party_type_id" name="party_type_id_name" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("party_type_id")%>" value="" /><input type="hidden" name="party_type_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.party_type_id, form.party_type_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmPartyTypeAction.do?cmd=queryReference&referenceInputType=radio');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("old_party_id")%></td>
		<td>
			<input type="text" class="text_field" name="old_party_id" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("old_party_id")%>" value="" maxLength="25" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
		<td>
			<input type="text" class="text_field" name="name" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("is_inherit")%></td>
		<td>
			<input type="checkbox" class="rm_checkbox" hiddenInputId="is_inherit" name="is_inherit_rmCheckbox"/><input type="hidden" name="is_inherit"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("email")%></td>
		<td>
			<input type="text" class="text_field" name="email" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("email")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="description" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom1")%></td>
		<td>
			<input type="text" class="text_field" name="custom1" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom1")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom2")%></td>
		<td>
			<input type="text" class="text_field" name="custom2" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom2")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom3")%></td>
		<td>
			<input type="text" class="text_field" name="custom3" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom3")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom4")%></td>
		<td>
			<input type="text" class="text_field" name="custom4" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom4")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom5")%></td>
		<td>
			<input type="text" class="text_field" name="custom5" inputName="<%=IRmPartyConstants.TABLE_COLUMN_CHINESE.get("custom5")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
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
  	}
%>
</script>