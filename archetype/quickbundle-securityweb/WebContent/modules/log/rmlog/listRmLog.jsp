<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@ page import="org.quickbundle.modules.log.rmlog.vo.RmLogVo" %>
<%@ page import="org.quickbundle.modules.log.rmlog.util.IRmLogConstants" %>



<%  //取出List
	List<RmLogVo> lResult = null;  //定义结果列表的List变量
	if(request.getAttribute(IRmLogConstants.REQUEST_BEANS) != null) {  //如果request中的beans不为空
		lResult = (List)request.getAttribute(IRmLogConstants.REQUEST_BEANS);  //赋值给resultList
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmLogAction";
	var rmJspPath = "";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + ids;
		form.submit();
	}
	function findOne_onClick(id) {
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find&id=" + id;
		form.submit();
	}
	function deleteOne_onClick(id) {
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + id;
			form.submit();
		}
	}
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
	    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=deleteMulti&ids=" + ids;
	    	form.submit();
		}
	}
	function simpleQuery_onClick(){  //简单的模糊查询
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=simpleQuery";
    	form.submit();
  	}
  	
	function toAdd_onClick() {  //到增加记录页面
		window.location="<%=request.getContextPath()%>/modules/log/rmlog" + rmJspPath + "/insertRmLog.jsp";
	}
	function refresh_onClick() {  //刷新本页
		form.submit();
	}
	function detail_onClick(thisId) {  //实现转到详细页面
		form.id.value = thisId;  //赋值thisId给隐藏值id
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=detail";
		form.submit();
	}
	function toDoDblClick(thisId) {
		detail_onClick(thisId);
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
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("log_type_id")%></td>
			<td>
				<input type="text" class="text_field_reference" hiddenInputId="log_type_id" name="log_type_id_name" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("log_type_id")%>" value="" /><input type="hidden" name="log_type_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.log_type_id, form.log_type_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmLogTypeAction.do?cmd=queryReference&referenceInputType=radio');"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_date")%></td>
			<td>
				<input type="text" class="text_field_half_reference_readonly" name="action_date_from" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_date")%>"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.action_date_from,'<%=request.getContextPath()%>/', '', event);"/>&nbsp;到&nbsp;<input type="text" class="text_field_half_reference_readonly" name="action_date_to" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_date")%>"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.action_date_to,'<%=request.getContextPath()%>/');"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_ip")%></td>
			<td>
				<input type="text" class="text_field" name="action_ip" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_ip")%>" maxLength="22"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_module")%></td>
			<td>
				<input type="text" class="text_field" name="action_module" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_module")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_type")%></td>
			<td>
				<%=RmJspHelper.getSelectField("action_type", -1, RmGlobalReference.get(IRmLogConstants.DICTIONARY_RM_OPERATION_TYPE), "", "inputName='" + IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_type") + "'", true) %>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%></td>
			<td>
				<input type="text" class="text_field" name="owner_org_id" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id")%></td>
			<td>
				<input type="text" class="text_field" name="user_id" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id")%>" maxLength="9"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id_name")%></td>
			<td>
				<input type="text" class="text_field" name="user_id_name" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id_name")%>" maxLength="100"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_uuid")%></td>
			<td>
				<input type="text" class="text_field" name="action_uuid" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_uuid")%>" maxLength="25"/>
			</td>
			<td align="right"></td>
			<td></td>
		</tr>
		<tr>
			<td align="right"><%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("content")%></td>
			<td>
				<input type="text" class="text_field" name="content" inputName="<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("content")%>" maxLength="2000"/>
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
		<!-- 将需要隐藏的查询条件剪切到这里 -->
	</table>
</div>

<table class="tableHeader">
  <tr>
    <td width="1%"><img src="<%=request.getContextPath()%>/images/bg_mcontentL.gif" /></td>
    <td class="tableHeaderMiddleTd"><b><%=IRmLogConstants.TABLE_NAME_CHINESE %>列表</b></td>
    <td class="tableHeaderMiddleTd" width="60%" align="right">
		<input type="button" class="button_ellipse" id="button_toAdd" value="新增" onClick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
		<input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onClick="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
		<input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onClick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/>
		<input type="button" class="button_ellipse" id="button_approve" value="审批" onClick="" title="审批单据"/>
		<input type="button" class="button_ellipse" id="button_refresh" value="刷新" onClick="javascript:refresh_onClick();" title="刷新当前页面"/>
    </td>
    <td width="1%" align="right"><img src="<%=request.getContextPath()%>/images/bg_mcontentR.gif" /></td>
  </tr>
</table>

<layout:collection headLocked="true" name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
	<layout:collectionItem width="1%" title="<input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<bean:define id="rmDisplayName" name="rmBean" property="id"/>
		<input type="checkbox" name="checkbox_template" value="<%=rmValue%>" displayName="<%=rmDisplayName%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="3%"  title="序" style="text-align:center;">
	<%
		Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
		RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(IRmLogConstants.RM_PAGE_VO);
		out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
	%>
		<bean:define id="rmValue" name="rmBean" property="id"/>
		<input type="hidden" signName="hiddenId" value="<%=rmValue%>"/>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("log_type_id")%>' property="log_type_id" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="log_type_id"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_date")%>' property="action_date" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="action_date"/>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue, 19)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_ip")%>' property="action_ip" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="action_ip"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_module")%>' property="action_module" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="action_module"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_type")%>' property="action_type" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="action_type"/>
		<%=RmGlobalReference.get(IRmLogConstants.DICTIONARY_RM_OPERATION_TYPE, rmValue)%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("owner_org_id")%>' property="owner_org_id" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="owner_org_id"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id")%>' property="user_id" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="user_id"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("user_id_name")%>' property="user_id_name" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="user_id_name"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("action_uuid")%>' property="action_uuid" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="action_uuid"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	<layout:collectionItem width="8%" title='<%=IRmLogConstants.TABLE_COLUMN_CHINESE.get("content")%>' property="content" sortable="true">
		<bean:define id="rmValue" name="rmBean" property="content"/>
		<%="<a class='aul' onClick='javascript:detail_onClick(getRowHiddenId())'>"%>
		<%=org.quickbundle.tools.helper.RmStringHelper.prt(rmValue)%>
		<%="</a>"%>
	</layout:collectionItem>
	</layout:collection>

<!-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 -->
<jsp:include page="/jsp/include/page.jsp" />

<input type="hidden" name="id" value="">
<input type="hidden" name="queryCondition" value="">
</form>

<%--start 生成页面汇总，正式部署前删除以下代码 --%>
<div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	</div>
<!-- end -->

</body>
</html>
<script type="text/javascript">
<%  //表单回写
	if(request.getAttribute(IRmLogConstants.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
		out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(IRmLogConstants.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
	}
%>
</script>