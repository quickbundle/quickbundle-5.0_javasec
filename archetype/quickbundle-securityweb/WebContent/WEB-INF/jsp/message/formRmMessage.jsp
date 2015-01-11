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
<%@ include file="/jsp/cipher/global.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=IRmMessageConstants.TABLE_NAME%></title>
</head>
<body class="page-header-fixed">
<jsp:include page="/jsp/cipher/header.jsp"></jsp:include>
<div class="page-container row-fluid">
<div class="page-sidebar nav-collapse collapse">
			<ul class="page-sidebar-menu">
				<li>
					<div class="sidebar-toggler hidden-phone"></div>
				</li>
				<li>
					<form class="sidebar-search">
						<div class="input-box">
							<a href="javascript:;" class="remove"></a> <input type="text"
								placeholder="Search..." /> <input type="button" class="submit"
								value=" " />
						</div>
					</form>
				</li>
				<li class=" "><a href="<%=request.getContextPath()%>/jsp/cipher_index.jsp"> <i
						class="icon-home"></i> <span class="title">首页</span>
				</a></li>
				<li class="start active "><a href="javascript:;"> <i
						class="icon-cogs"></i> <span class="title">控制台</span> <span
						class="selected"></span>
				</a>
					<ul class="sub-menu">
						<li><a href="<%=request.getContextPath()%>/message"><%=IRmMessageConstants.TABLE_NAME%></a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i
						class="icon-bookmark-empty"></i> <span class="title">功能1</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="ui_general.html">功能1菜单</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-table"></i>
						<span class="title">功能2</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="form_layout.html"> 功能2菜单</a></li>
						<li><a href="form_samples.html"> 功能2菜单</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i
						class="icon-briefcase"></i> <span class="title">功能3</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="page_timeline.html"> <i class="icon-time">
							</i>功能3菜单
						</a></li>
					</ul></li>
				<li class=""><a href="javascript:;"> <i class="icon-gift"></i>
						<span class="title">功能4</span> <span class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="extra_profile.html"> 功能4菜单</a></li>
					</ul></li>
					
					<li><a class="active" href="javascript:;"> <i
						class="icon-sitemap"></i> <span class="title">功能5</span> <span
						class="arrow "></span>
				</a>
					<ul class="sub-menu">
						<li><a href="javascript:;"> 功能5菜单 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
							</ul></li>
						<li><a href="javascript:;"> 功能5菜单 <span class="arrow"></span>
						</a>
							<ul class="sub-menu">
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
								<li><a href="#">功能5菜单</a></li>
							</ul></li>
						<li><a href="#"> 功能5菜单 </a></li>
					</ul></li>
			</ul>
			</div>
			<div class="page-content">
			<div id="portlet-config-edit" class="modal hide">
				<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>修改</h3>
				</div>
				<div class="modal-body">
					<form action="<%=request.getContextPath()%>/message/update" method="post">
						<div class="control-group">
							<div class="controls">
							<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></span> <input class="m-wrap" name="biz_keyword" id="biz_keyword" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></span> <input class="m-wrap" name="sender_id" id="sender_id" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%></span> <input class="m-wrap" name="parent_message_id" id="parent_message_id" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></span> <input class="m-wrap" name="owner_org_id" id="owner_org_id" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></span> <input class="m-wrap" name="template_id" id="template_id" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></span> <input class="m-wrap" name="is_affix" id="is_affix" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></span> <input class="m-wrap" name="record_id" id="record_id" type="text"  />
								</div>
		<div class="input-prepend">
									<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> <input class="m-wrap" name="message_xml_context" id="message_xml_context" type="text"  />
								</div>
		<p>
									<input id="id" name="id" type="hidden" /> <input type="submit" class="btn blue btn-block" value="提交" />
								</p>
							</div>
						</div>
					</form>
				</div>
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
							<input type="text" class="text_field_half" name="sender_id_from" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;</div>
						<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%></td>
		<td>
			<input type="text" class="text_field_reference"  hiddenInputId="parent_message_id" name="parent_message_id_name" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>" value="" /><input type="hidden" name="parent_message_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onclick="javascript:getReference(new Array(form.parent_message_id, form.parent_message_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/rmmessage/reference?referenceInputType=radio');"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></span> 
										<input class="m-wrap" name="owner_org_id" type="text" maxLength="25"/>
							</div>
					<div class="input-prepend">
							<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></span>
							<input type="text" class="text_field_half" name="template_id_from" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>" value="" columnSize="" decimalDigits="0" />&nbsp;到&nbsp;</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></span> 
										<input class="m-wrap" name="is_affix" type="text" maxLength="1"/>
							</div>
					<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></span> 
										<input class="m-wrap" name="record_id" type="text" maxLength="25"/>
							</div>
					<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> 
										<input class="m-wrap" name="message_xml_context" type="text" maxLength="32767"/>
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
							<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></span>
							<input type="text" class="text_field_half" name="sender_id_from" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>" value="" columnSize="" decimalDigits="0" />
							</div>
							&nbsp;到&nbsp;<div class="input-prepend">
							<input type="text" class="text_field_half" name="sender_id_to" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>" value="" columnSize="" decimalDigits="0" />
						</div>
						<input type="text" class="text_field_reference" hiddenInputId="parent_message_id" name="parent_message_id_name" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>" value="" /><input type="hidden" name="parent_message_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onclick="javascript:getReference(new Array(form.parent_message_id, form.parent_message_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/rmmessage/reference?referenceInputType=radio');"/>
			<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></span> 
										<input class="m-wrap" name="owner_org_id" type="text" maxLength="25"/>
							</div>
						<div class="input-prepend">
							<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></span>
							<input type="text" class="text_field_half" name="template_id_from" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>" value="" columnSize="" decimalDigits="0" />
							</div>
							&nbsp;到&nbsp;<div class="input-prepend">
							<input type="text" class="text_field_half" name="template_id_to" inputName="<%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>" value="" columnSize="" decimalDigits="0" />
						</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></span> 
										<input class="m-wrap" name="is_affix" type="text" maxLength="1"/>
							</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></span> 
										<input class="m-wrap" name="record_id" type="text" maxLength="25"/>
							</div>
						<div class="input-prepend">
										<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> 
										<input class="m-wrap" name="message_xml_context" type="text" maxLength="32767"/>
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
										<a href="" onclick="update();" data-toggle="modal" class="config"></a> 
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
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></th>
		<th><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></th>
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
						<td><c:out value="${list.biz_keyword}"></c:out></td>
										<td><c:out value="${list.sender_id}"></c:out></td>
										<td><c:out value="${list.parent_message_id}"></c:out></td>
										<td><c:out value="${list.owner_org_id}"></c:out></td>
										<td><c:out value="${list.template_id}"></c:out></td>
										<td><c:out value="${list.is_affix}"></c:out></td>
										<td><c:out value="${list.record_id}"></c:out></td>
										<td><c:out value="${list.message_xml_context}"></c:out></td>
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
		 $.ajax({
             type: "GET",
             url: "<%=request.getContextPath()%>/message/get/" + updateDate,
             dataType: "json",
             success: function(res) {
             
			$("#portlet-config-edit").find("input[id=id]").val(res.id);
		
			$("#portlet-config-edit").find("input[id=biz_keyword]").val(res.biz_keyword);
		
			$("#portlet-config-edit").find("input[id=sender_id]").val(res.sender_id);
		
			$("#portlet-config-edit").find("input[id=parent_message_id]").val(res.parent_message_id);
		
			$("#portlet-config-edit").find("input[id=owner_org_id]").val(res.owner_org_id);
		
			$("#portlet-config-edit").find("input[id=template_id]").val(res.template_id);
		
			$("#portlet-config-edit").find("input[id=is_affix]").val(res.is_affix);
		
			$("#portlet-config-edit").find("input[id=record_id]").val(res.record_id);
		
			$("#portlet-config-edit").find("input[id=message_xml_context]").val(res.message_xml_context);
		
            }
         });
	}
	function list_onClick() {
        form.action="<%=request.getContextPath()%>/message";
		form.submit();
	}
	</script>
</body>
</html>