<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.modules.log.rmlogtype.vo.RmLogTypeVo" %>
<%@ page import="org.quickbundle.modules.log.rmlogtype.util.IRmLogTypeConstants" %>
<%  //判断是否为修改页面
  	RmLogTypeVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmLogTypeConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmLogTypeVo)request.getAttribute(IRmLogTypeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
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
	var rmActionName = "RmLogTypeAction";
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
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%></td>
		<td>
			<input type="text" class="text_field" name="bs_keyword" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
		<td>
			<input type="text" class="text_field" name="name" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_record")%></td>
		<td>
			<input type="checkbox" class="rm_checkbox" hiddenInputId="is_record" name="is_record_rmCheckbox"/><input type="hidden" name="is_record"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("is_alone_table")%></td>
		<td>
			<input type="checkbox" class="rm_checkbox" hiddenInputId="is_alone_table" name="is_alone_table_rmCheckbox"/><input type="hidden" name="is_alone_table"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("table_name")%></td>
		<td>
			<input type="text" class="text_field" name="table_name" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("table_name")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="pattern_value" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("pattern_value")%>" maxLength="500" validate="notNull;"></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%></td>
		<td>
			<input type="text" class="text_field" name="match_priority" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("match_priority")%>" value="" integerDigits="10" decimalDigits="0" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%></td>
		<td>
			<input type="text" class="text_field" name="max_log_sum" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("max_log_sum")%>" value="" integerDigits="38" decimalDigits="0" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="description" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("custom_xml")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="custom_xml" inputName="<%=IRmLogTypeConstants.TABLE_COLUMN_CHINESE.get("custom_xml")%>" maxLength="2000" ></textarea>
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
  	}
%>
</script>