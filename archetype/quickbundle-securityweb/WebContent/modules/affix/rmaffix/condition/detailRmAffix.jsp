<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo" %>
<%@ page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants" %>
<%  //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getAttribute(IRmAffixConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	} else if("1".equals(request.getParameter(IRmAffixConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	} 
%>
<%  //取出本条记录
	RmAffixVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmAffixVo)request.getAttribute(IRmAffixConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmAffixConditionAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
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
		<td align="right">业务关键字：</td>
		<td><%=RmStringHelper.prt(resultVo.getBs_keyword())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">主记录ID：</td>
		<td><%=RmStringHelper.prt(resultVo.getRecord_id())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">顺序数：</td>
		<td><%=RmStringHelper.prt(resultVo.getOrder_number())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">标题：</td>
		<td><%=RmStringHelper.prt(resultVo.getTitle())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">原文件名：</td>
		<td><%=RmStringHelper.prt(resultVo.getOld_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">真实储存路径：</td>
		<td><%=RmStringHelper.prt(resultVo.getSave_name())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">真实文件大小：</td>
		<td><%=RmStringHelper.prt(resultVo.getSave_size())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">内容类型：</td>
		<td><%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, resultVo.getMime_type())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">编码：</td>
		<td><%=RmStringHelper.prt(resultVo.getEncoding())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">描述：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
	</tr>
	<tr>
		<td align="right">作者：</td>
		<td><%=RmStringHelper.prt(resultVo.getAuthor())%>&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</table>

<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">
<input type="hidden" name="mime_type" value="<%=RmStringHelper.prt(resultVo.getMime_type())%>"><table align="center">
	<tr>
		<td><br>
			<%if(!isReadOnly) { %>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_delete" value="删除" onclickto="javascript:delete_onClick();">
			<%}%>
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(

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
</body>
</html>
	