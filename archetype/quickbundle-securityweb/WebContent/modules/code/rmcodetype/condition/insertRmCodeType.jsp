<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.vo.RmCodeTypeVo" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants" %>
<%  //判断是否为修改页面
  	RmCodeTypeVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmCodeTypeConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmCodeTypeVo)request.getAttribute(IRmCodeTypeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmCodeTypeConditionAction";
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
<script type="text/javascript">
	writeTableTop('<%=isModify?"修改页面":"新增页面"%>','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
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
		<td align="right"><span class="style_required_red">* </span>类型关键字</td>
		<td>
			<input type="text" class="text_field" name="type_keyword" inputName="类型关键字" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">编码类型名称</td>
		<td>
			<input type="text" class="text_field" name="name" inputName="编码类型名称" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">备注</td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="remark" inputName="备注" maxLength="2000" ></textarea>
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
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%  //取出要修改的那条记录，并且回写表单
	if(isModify) {  //如果本页面是修改页面
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromVo(resultVo)));  //输出表单回写方法的脚本
  	} else {
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromRequest(request, IRmCodeTypeConstants.DEFAULT_CONDITION_KEY_ARRAY)));  //输出表单回写方法的脚本
  	}
%>
autoPatchParentIdName("<%=IRmCodeTypeConstants.DEFAULT_CONDITION_KEY_ARRAY[0]%>");
</script>
