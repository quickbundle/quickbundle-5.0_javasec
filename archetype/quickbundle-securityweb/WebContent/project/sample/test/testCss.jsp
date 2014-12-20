<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<script src="<%=request.getContextPath()%>/js/rm-insert.js" type=text/javascript></script>
<script src="<%=request.getContextPath()%>/third/ckeditor/ckeditor.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
</head>
<body>
<script type="text/javascript">
	writeTableTop('详细页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">
<table class="table_noFrame">
	<tr>
		<td valign="middle">
			<input name="button_update" class="button_ellipse" type="button" value="修改" onClick="javascript:find_onClick();">
			<input name="button_delete" class="button_ellipse" type="button" value="删除" onClick="javascript:delete_onClick();">
			<input name="button_back" class="button_ellipse" type="button" value="返回"  onclick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<div id="ccParent0"> 
<table class="table_div_control">
	<tr> 
		<td>
			<img src="<%=request.getContextPath()%>/images/icon/07-0.gif" class="div_control_image" onClick="javascript:hideshow('ccChild0',this,'<%=request.getContextPath()%>/')">详细页面
		</td>
	</tr>
</table>
</div>

<div id="ccChild0"> 
<table class="table_div_content">
<tr><td>
	<table>
		<tr>
			<td width="15%" align="right" nowrap><strong>基本信息</strong><br><br></td>
			<td width="35%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="35%" align="left">&nbsp;</td>
		</tr>
		<tr>
			<td align="right" nowrap>测试input=text</td>
			<td align="left" colspan="3">
				<input type="text" class="text_field" name="name1" maxLength="50"/><br/>
				<input type="text" class="text_field_readonly" name="name2" value="init value" maxLength="50"/><br/>
				<input type="text" class="text_field_reference" name="name3" maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDayHourMinuteSecond(form.name3,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/><br/>
				<input type="text" class="text_field_reference_readonly" name="name4" hiddenInputId="name4_hiddenId1,name4_hiddenId2,name4_hiddenId3," maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDayHourMinuteSecond(form.name4,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/><input type="text" id="name4_hiddenId1"> <input type="text" id="name4_hiddenId2"> <input type="text" id="name4_hiddenId3"><br/>
				<input type="text" class="text_field_half" name="name51" maxLength="50"/>&nbsp;到&nbsp;<input type="text" class="text_field_half" name="name52" maxLength="50"/><br/>
				<input type="text" class="text_field_half_readonly" name="name61" value="init value" maxLength="50"/>&nbsp;到&nbsp;<input type="text" class="text_field_half_readonly" name="name62" maxLength="50"/><br/>
				<input type="text" class="text_field_half_reference" name="name71" maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDay(form.name71,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/>&nbsp;到&nbsp;<input type="text" class="text_field_half_reference" name="name72" maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDay(form.name72,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/><br/>
				<input type="text" class="text_field_half_reference_readonly" name="name81" maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDay(form.name81,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/>&nbsp;到&nbsp;<input type="text" class="text_field_half_reference_readonly" name="name82" maxLength="50"/><img class="refButtonClass" onClick="javascript:getYearMonthDay(form.name82,'<%=request.getContextPath()%>/');" src="<%=request.getContextPath()%>/images/09.gif"/><br/>
			</td>
		</tr>
		<tr>
			<td align="right">测试textarea</td>
			<td colspan="3" align="left">
				<textarea id="textarea1" inputName="摘要" class="textarea_limit_words" name="textarea1" cols="60" rows="5" maxLength="5000" ></textarea>
			</td>
		</tr>
		<tr>
			<td align="right">测试textarea</td>
			<td colspan="3" align="left">
				<textarea id="textarea2" inputName="摘要" class="textarea_fckEditor" name="textarea2" cols="60" rows="5" maxLength="500" ></textarea>
			</td>
		</tr>
	</table>
</td></tr>
</table>
</div>

</form>			
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
