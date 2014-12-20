<%@page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo" %>
<%@page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants" %>
<%
	String referenceInputType = String.valueOf(request.getAttribute(IRmAffixConstants.REQUEST_REFERENCE_INPUT_TYPE));
	if(referenceInputType == null || referenceInputType.length() == 0 || (!"checkbox".equals(referenceInputType.toLowerCase()) && !"radio".equals(referenceInputType.toLowerCase()))) {
		referenceInputType = "radio";		
	}
	//取出List
	List<RmAffixVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmAffixConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmAffixConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<script type="text/javascript">
	var rmActionName = "RmAffixAction";
	function simpleQuery_onClick(){  //简单的模糊查询
		form.queryCondition.value = buildQueryCondition();  //获得组合后的查询条件的字符串描述
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=queryReference";
    	form.submit();
  	}
	function refresh_onClick(){  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		//参照页面默认不进去细览
	}
	function buildQueryCondition() {  //构建简单查询
		var queryCondition = "";  //定义组合后的查询条件的字符串变量
		var qca = new Array();  //定义查询条件的数组变量,每一个可能的查询条件会被压入这个数组

		pushCondition(qca, "bs_keyword");
				
		pushCondition(qca, "record_id");
				
		pushCondition(qca, "order_number_from", ">=", "", "order_number");
		pushCondition(qca, "order_number_to", "<=", "", "order_number");
				
		pushCondition(qca, "title");
				
		pushCondition(qca, "old_name");
				
		pushCondition(qca, "save_name");
				
		pushCondition(qca, "save_size_from", ">=", "", "save_size");
		pushCondition(qca, "save_size_to", "<=", "", "save_size");
				
		pushCondition(qca, "mime_type");
				
		pushCondition(qca, "encoding");
				
		pushCondition(qca, "description");
				
		pushCondition(qca, "author");
				
		if(qca.length >= 1) {  //如果有效的查询条件大于1
			queryCondition += " " + qca[0] + " ";  //组装第1个查询条件
		}
		for(var i=1; i<qca.length; i++) {  //从第2个开始循环查询条件
			queryCondition += " and " + qca[i] + " ";  //组装第2个以后的查询条件
		}
		//alert(queryCondition);  //测试显示组合后的查询条件的字符串描述
		return queryCondition;
	}
</script>
</head>
<body>
<form name="form" method="post">

<div id="div_simple">
	<table class="table_query">
		<tr>
			<td width="20%">&nbsp;</td>
			<td width="35%">&nbsp;</td>
			<td width="20%">&nbsp;</td>
			<td width="25%">&nbsp;</td>
		</tr>
		<tr>
			<td align="right">业务关键字</td>
			<td>
				<input type="text" class="text_field" name="bs_keyword" inputName="业务关键字" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">主记录ID</td>
			<td>
				<input type="text" class="text_field" name="record_id" inputName="主记录ID" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">顺序数</td>
			<td>
				<input type="text" class="text_field_half" name="order_number_from" inputName="顺序数" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="order_number_to" inputName="顺序数" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">标题</td>
			<td>
				<input type="text" class="text_field" name="title" inputName="标题" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">原文件名</td>
			<td>
				<input type="text" class="text_field" name="old_name" inputName="原文件名" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">真实文件大小</td>
			<td>
				<input type="text" class="text_field_half" name="save_size_from" inputName="真实文件大小" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;<input type="text" class="text_field_half" name="save_size_to" inputName="真实文件大小" value="" columnSize="" decimalDigits="0" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">内容类型</td>
			<td>
				<%=org.quickbundle.tools.helper.RmJspHelper.getSelectField("mime_type", -1, org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE), "", "inputName='内容类型'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">编码</td>
			<td>
				<input type="text" class="text_field" name="encoding" inputName="编码" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">描述</td>
			<td>
				<input type="text" class="text_field" name="description" inputName="描述" maxLength="2000"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right">作者</td>
			<td>
				<input type="text" class="text_field" name="author" inputName="作者" maxLength="100"/>
				<input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:simpleQuery_onClick()" value="查询" />
				<input type="reset" class="button_ellipse" id="button_reset" value="清空" />
				<input type="button" class="button_ellipse" id="button_moreCondition" onclick="javascript:changeSearch_onClick(this);" value="更多条件" />
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		</table>
</div> 
<div id="div_advanced" style="display:none;">
	<table class="table_query">
		<tr>
			<td width="20%"></td>
			<td width="35%"></td>
			<td width="20%"></td>
			<td width="25%"></td>
		</tr>
		<!-- 需要隐藏查询条件剪切到这里 -->
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmAffixConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="title"/>
		<input type="<%="checkbox".equals(referenceInputType)?"checkbox":""%><%="radio".equals(referenceInputType)?"radio":""%>" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>" />
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
		<%
			Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
			RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmAffixConstants.RM_PAGE_VO);
			out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
		%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="业务关键字" property="bs_keyword" sortable="true"/>
	<layout:collectionItem width="8%" title="主记录ID" property="record_id" sortable="true"/>
	<layout:collectionItem width="8%" title="顺序数" property="order_number" sortable="true"/>
	<layout:collectionItem width="8%" title="标题" property="title" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="title"/>
		<%="<a class='aul' onclick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=rmValue%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="原文件名" property="old_name" sortable="true"/>
	<layout:collectionItem width="8%" title="真实文件大小" property="save_size" sortable="true"/>
	<layout:collectionItem width="8%" title="内容类型" property="mime_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="mime_type"/>
		<%=org.quickbundle.project.RmGlobalReference.get(IRmAffixConstants.DICTIONARY_RM_MINE_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title="编码" property="encoding" sortable="true"/>
	<layout:collectionItem width="8%" title="描述" property="description" sortable="true"/>
	<layout:collectionItem width="8%" title="作者" property="author" sortable="true"/>
	</layout:collection>
		
<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
<input type="hidden" name="referenceInputType" value="<%=referenceInputType%>">

<input type="hidden" id="rmCheckReturnValue" name="rmCheckReturnValue" value="">
<input type="hidden" id="rmCheckReturnName" name="rmCheckReturnName" value="">

</form>
</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmAffixConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmAffixConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>