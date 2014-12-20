<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.modules.code.rmcodetype.util.IRmCodeTypeConstants" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>中国网通（集团）有限公司河北省分公司企业信息门户</title>
<script type="text/javascript">
	var rmActionName = "RmCodeTypeAjaxAction";
	function findCheckbox_onClick() {  //从多选框到修改页面
		var ids = rmListData.findSelections();  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return;
		}
		if(ids.length > 1) {  //如果ids有2条以上的纪录
	  		alert("只能选择一条记录!")
	  		return;
		}
		rmListData.findRecord(ids);
	}  
	function deleteMulti_onClick(){  //从多选框物理删除多条记录
 		var ids = rmListData.findSelections();  //取得多选框的选择项
		if(ids == null)	{  //如果ids为空
			alert("请选择记录!")
			return;
		}
		if(confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
			rmListData.deleteRecord(ids);
		}
	}
	function refresh_onClick(){  //刷新本页
		rmListData.refreshData();
	}

	function save_onClick() {
		if(form.id.value == "") {  //插入单条数据
			rmListData.insertRecord();
			form.reset();
		} else {  //保存修改后的单条数据
	    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
	  			return false;
			}
			rmListData.updateRecord(form.id.value);
			form.id.value = "";
		}
		return false;
	}

	function RmCodeTypeListData() {
		this.columnName = new Array("type_keyword","multi_value_desc","is_tree","name","remark");
		this.columnChineseName = new Array("类型关键字","多值描述","是否树形","编码类型名称","备注");
		this.columnPk = "id";
		this.actionPath = dir_base + "/" + rmActionName + ".do?cmd=";
	}
	RmCodeTypeListData.prototype = new ListDataHandler();
	var rmListData = new RmCodeTypeListData();
</script>
</head>
<body onload="rmListData.initTable();">
<script type="text/javascript">
	writeTableTop('列表页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">

<div id="ccParent0"> 
<table class="table_div_control">
	<tr> 
		<td>
			<img src="<%=request.getContextPath()%>/images/icon/07-0.gif" class="div_control_image" onClick="javascript:hideshow('ccChild0',this,'<%=request.getContextPath()%>/')" >&nbsp;<span id="span_insert_update">新增</span><%=IRmCodeTypeConstants.TABLE_NAME_CHINESE%>
		</td>
	</tr>
</table>
</div>

<div id="ccChild0"> 
<table class="table_div_content">
<tr><td>
	<table class="table_noFrame" width="100%">
		<tr>
			<td width="15%" align="right" nowrap><strong>基本信息</strong><br><br></td>
			<td width="35%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="35%" align="left">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><span class="style_required_red">* </span>类型关键字</td>
			<td align="left">
				<input type="text" class="text_field" name="type_keyword" inputName="类型关键字" value="" maxLength="100" validate="notNull;"/>
			</td>
			<td align="right"></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right">多值描述</td>
			<td colspan="3" align="left">
				<textarea class="textarea_limit_words" cols="60" rows="5" name="multi_value_desc" inputName="多值描述" maxLength="32767.5" ></textarea>
			</td>
		</tr>
		<tr>
			<td align="right"><span class="style_required_red">* </span>是否树形</td>
			<td align="left">
				<%=org.quickbundle.tools.helper.RmJspHelper.getSelectField("is_tree", -1, org.quickbundle.project.RmGlobalReference.get(IRmCodeTypeConstants.DICTIONARY_RM_YES_NOT), "", "inputName='是否树形' validate='notNull;'", true) %>
			</td>
			<td align="right"></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right">编码类型名称</td>
			<td align="left">
				<input type="text" class="text_field" name="name" inputName="编码类型名称" value="" maxLength="100" />
			</td>
			<td align="right"></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right">备注</td>
			<td colspan="3" align="left">
				<textarea class="textarea_limit_words" cols="60" rows="5" name="remark" inputName="备注" maxLength="32767.5" ></textarea>
			</td>
		</tr>
		<tr>
			<td align="right"></td>
			<td align="left"></td>
			<td align="right"></td>
			<td align="left"><input name="button_ok" class="button_ellipse" type="button" value="保存" onClickTo="javascript:save_onClick()"></td>
		</tr>
	</table>
</td></tr>
</table>
</div>
					
<div id="ccParent1"> 
<table class="table_div_control">
	<tr> 
		<td>
			<img src="<%=request.getContextPath()%>/images/icon/07-0.gif" class="div_control_image" onClick="javascript:hideshow('ccChild1',this,'<%=request.getContextPath()%>/')">&nbsp;明细表格
		</td>
		<td> 
			<table align="right">
				<tr>
					<td class="td_hand" onClick="javascript:form.reset();initMyKeyDown();"><img src="<%=request.getContextPath()%>/images/icon/add.gif" class="div_control_image">新增</td>
					<td class="td_hand" onClick="javascript:deleteMulti_onClick();"><img src="<%=request.getContextPath()%>/images/icon/delete.gif" class="div_control_image">删除</td>
					<td class="td_hand" onClick="javascript:findCheckbox_onClick();"><img src="<%=request.getContextPath()%>/images/icon/modify.gif" class="div_control_image">修改</td>
					<td class="td_hand" onClick="javascript:refresh_onClick();"><img src="<%=request.getContextPath()%>/images/icon/refresh.gif" class="div_control_image">刷新</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<div id="ccChild1"> 
<table class="table_div_content2">
	<tr>
		<td id="td_listData">

		</td>
	</tr>
</table>
</div>
<br><br><br>
<input type="hidden" name="id" value="">
</form>
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
