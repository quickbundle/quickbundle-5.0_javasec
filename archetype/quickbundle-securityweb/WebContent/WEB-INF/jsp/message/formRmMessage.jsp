<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/jsp/bootstrap/global.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=IRmMessageConstants.TABLE_NAME%></title>
</head>
<body class="page-header-fixed page-sidebar-closed">
<jsp:include page="/jsp/bootstrap/header.jsp"></jsp:include>
<div class="page-container row-fluid">
<jsp:include page="/jsp/bootstrap/item.jsp"></jsp:include>
			<div class="page-content">
			<div id="portlet-config-edit" class="modal hide">
				
			</div>
			<div id="portlet-config-add" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>新增</h3>
				</div>
				<div class="modal-body">
					<form action="<%=request.getContextPath()%>/message/insert"
						method="post">
						<div class="control-group">
							<div class="controls">
								<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></span> 
										<input class="m-wrap" name="biz_keyword" type="text" maxLength="25"/>
							</div>
					<div class="input-prepend">
							<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></span>
							<input type="text" class="m-wrap" name="sender_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;</div>
						
					<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></span> 
										<input class="m-wrap" name="owner_org_id" type="text" maxLength="25"/>
							</div>
					<div class="input-prepend">
							<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></span>
							<input type="text" class="m-wrap" name="template_id" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;</div>
						<div class="input-prepend">
						<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></span>
			<input type="text" class="m-wrap" bs_keyword="<%=IRmMessageConstants.TABLE_NAME%>" record_id="" name="is_affix" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>" value="1" readonly="readonly" maxLength="1" />
	</div>
	<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></span> 
										<input class="m-wrap" name="record_id" type="text" maxLength="25"/>
							</div>
					<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> 
										<input class="m-wrap" name="message_xml_context" type="text" maxLength="32767"/>
							</div>
						<div class="input-prepend">
				<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status")%></span> 
				<%=RmJspHelper.getSelectField("usable_status", -1, RmGlobalReference.get(IRmMessageConstants.DICTIONARY_RM_ADMIN_TYPE), "", "inputName='" + IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status") + "'", true) %>
				</div>
				<div class="input-prepend">
												<div class="controls">
														<span class="add-on">时间</span> <input name="modify_date" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_date")%>" />
												</div>
										</div>
							<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_ip")%></span> 
										<input class="m-wrap" name="modify_ip" type="text" maxLength="22"/>
							</div>
					<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%></span>
			<input type="text" class="m-wrap"  hiddenInputId="modify_user_id" name="modify_user_id_name" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%>" value="" /><input type="hidden" name="modify_user_id">
	</div>
	<p>
									<input type="submit" class="btn blue btn-block" value="提交" />
								</p>
							</div>
						</div>
					</form>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<h3 class="page-title">
							<%=IRmMessageConstants.TABLE_NAME%>管理<small></small>
						</h3>
						<ul class="breadcrumb">
							<li><i class="icon-home"></i> <a href="index.html">首页</a> <i
								class="icon-angle-right"></i></li>
							<li><a href="#">表管理</a> <i class="icon-angle-right"></i></li>
							<li><a href="#"><%=IRmMessageConstants.TABLE_NAME%></a></li>
						</ul>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span6">
						<div class="portlet box blue">
							<div class="portlet-title">
								<div class="caption">
									<i class="icon-cogs"></i>查询
								</div>
								<div class="tools">
									<a href="javascript:;" class="collapse"></a>
								</div>
							</div>
							<div class="portlet-body">
								<form action="<%=request.getContextPath()%>/message" method="post">
									<div class="control-group">
									<div class="controls">
										<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></span> 
										<input class="m-wrap" name="biz_keyword" type="text" maxLength="25"/>
							</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> 
										<input class="m-wrap" name="message_xml_context" type="text" maxLength="32767"/>
							</div>
						<div class="input-prepend">
				<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status")%></span> 
				<%=RmJspHelper.getSelectField("usable_status", -1, RmGlobalReference.get(IRmMessageConstants.DICTIONARY_RM_ADMIN_TYPE), "", "inputName='" + IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status") + "'", true) %>
				</div>
			<div class="input-prepend">
												<div class="controls">
													<div class="controls">
														<span class="add-on">时间</span> <input name="modify_date_from" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_date")%>" />
													</div>
											</div>
										</div>到&nbsp;<div class="input-prepend">
												<div class="controls">
													<div class="controls">
														<span class="add-on">时间</span> 
														<input name="modify_date_to" class="m-wrap m-ctrl-medium date-picker" readonly size="16" type="text" value="" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_date")%>" />
													</div>
											</div>
										</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_ip")%></span> 
										<input class="m-wrap" name="modify_ip" type="text" maxLength="22"/>
							</div>
						<div class="input-prepend">
				<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%></span> 
					<input type="text" class="m-wrap" hiddenInputId="modify_user_id" name="modify_user_id_name" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%>" value="" /><input type="hidden" name="modify_user_id">
				</div>
			<p>
											<input type="submit" class="btn blue btn-block" value="查询" />
										</p>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="row-fluid">
				<div class="span12">
					<div class="portlet box blue">
						<div class="portlet-title">
							<div class="caption">
								<i class="icon-globe"></i>列表
							</div>
							<div class="tools">
										<a href="#" onclick="update();" data-toggle="modal" class="config"></a> 
										<a href="javascript:;" onclick="_delete();" class="halflings-icon white trash"></a>
										<a href="#portlet-config-add" data-toggle="modal" class="halflings-icon white file"></a> 
										<a href="javascript:;" class="collapse"></a>
									</div>
						</div>
						<div class="portlet-body no-more-tables">
							<table class="table-bordered table-striped table-condensed cf">
								<thead>
									<tr>
									<th style="width: 40px">选择</th>
									<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_date")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_ip")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%></th>
		</tr>
								</thead>
								<tbody>
									<c:forEach items="${beans}" var="list" varStatus="rowCounter"
												begin='0' step='1'>
												<tr>
													<td>
								<div class="checkbox">
										<ins id="ischeck">
													<input type="hidden" class="checkmain" ischecked="false" value="<c:out value="${list.id}"></c:out>" />
															</ins>
														</div>
													</td>
						<td align="center"><c:out value="${list.biz_keyword}"></c:out></td>
										<td align="center"><c:out value="${list.is_affix}"></c:out></td>
										<td align="center"><c:out value="${list.message_xml_context}"></c:out></td>
										<td align="center"><c:out value="${list.usable_status}"></c:out></td>
										<td align="center"><c:out value="${list.modify_date}"></c:out></td>
										<td align="center"><c:out value="${list.modify_ip}"></c:out></td>
										<td align="center"><c:out value="${list.modify_user_id}"></c:out></td>
										</tr>
								 </c:forEach>
								</tbody>
							</table>
							<form name="form" method="post">
							<jsp:include page="/jsp/include/page.jsp"></jsp:include>
							</form>
						</div>
					</div>
				</div>
			</div>
			</div>
	</div>
	</div>

	<div class="footer">
		<div class="footer-inner">2013  Metronic by keenthemes</div>
		<div class="footer-tools">
			<span class="go-top"> <i class="icon-angle-up"></i>
			</span>
		</div>
	</div>
	
	<script type="text/javascript">
	function _delete() {
		var allcheck = $(".checkmain");
		var deletes = "";
		for (var g = 0; g < allcheck.length; g++) {
			var b = $(allcheck[g]).val();
			var c = $(allcheck[g]).attr("ischecked");
			if (c == "true") {
				deletes += b;
				if (g != allcheck.length -1) {
					deletes += ",";
				}
			}
		}
		if (deletes == "") {
			$.teninedialog({
	             title:'请选择记录',
	             content:'请至少选择一条记录'
	         });
			return false;
		}
		 $.teninedialog({
             title:'系统提示',
             content:'确认删除所选记录?',
             otherButtons:["确定","取消"],
             otherButtonStyles:['btn-primary','btn-primary'],
             bootstrapModalOption:{keyboard: true},
             clickButton:function(sender,modal,index){
 			if (index == 0) {
 				form.action="<%=request.getContextPath()%>/message/delete?ids=" + deletes;
 		        form.submit();
 			}
 			$(this).closeDialog(modal);
             }
         });
	}
	function update() {
		var allcheck = $(".checkmain");
		var selectupdate = 0;
		var updateDate = "";
		for (var g = 0; g < allcheck.length; g++) {
			var b = $(allcheck[g]).val();
			var c = $(allcheck[g]).attr("ischecked");
			if (c == "true") {
				selectupdate++;
				updateDate = b;
			}
		}
		if (selectupdate == 0) {
			$.teninedialog({
	             title:'请选择记录',
	             content:'请至少选择一条记录'
	         });
			return false;
		} 
		if (selectupdate > 1) {
			$.teninedialog({
	             title:'选择记录数目错误',
	             content:'只能选择一条记录进行修改'
	         });
			return false;
		}
		$("#portlet-config-edit").modal("show");
		$("#portlet-config-edit").load("<%=request.getContextPath()%>/message/modify/" + updateDate);
	}
	function list_onClick() {
        form.action="<%=request.getContextPath()%>/message";
		form.submit();
	}
	</script>
</body>
</html>