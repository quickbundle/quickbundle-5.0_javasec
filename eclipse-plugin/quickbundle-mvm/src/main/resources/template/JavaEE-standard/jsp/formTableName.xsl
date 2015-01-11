<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java"%>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<xsl:value-of select="$charLt"/>%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<xsl:value-of select="$charLt"/>%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<xsl:value-of select="$charLt"/>%@ page trimDirectiveWhitespaces="true"%>
<xsl:value-of select="$charLt"/>!DOCTYPE html>
<xsl:value-of select="$charLt"/>html lang="en">
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/cipher/global.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body class="page-header-fixed page-sidebar-closed">
<xsl:value-of select="$charLt"/>jsp:include page="/jsp/cipher/header.jsp"><xsl:value-of select="$charLt"/>/jsp:include>
<xsl:value-of select="$charLt"/>div class="page-container row-fluid">
<xsl:value-of select="$charLt"/>jsp:include page="/jsp/bootstrap/item.jsp"><xsl:value-of select="$charLt"/>/jsp:include>
			<xsl:value-of select="$charLt"/>div class="page-content">
			<xsl:value-of select="$charLt"/>div id="portlet-config-edit" class="modal hide">
			<xsl:value-of select="$charLt"/>/div>
			<xsl:value-of select="$charLt"/>div id="portlet-config-add" class="modal hide">
				<xsl:value-of select="$charLt"/>div class="modal-header">
					<xsl:value-of select="$charLt"/>button data-dismiss="modal" class="close" type="button"><xsl:value-of select="$charLt"/>/button>
					<xsl:value-of select="$charLt"/>h3>新增<xsl:value-of select="$charLt"/>/h3>
				<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>div class="modal-body">
					<xsl:value-of select="$charLt"/>form action="<xsl:value-of select="$charLt"></xsl:value-of>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/insert"
						method="post">
						<xsl:value-of select="$charLt"/>div class="control-group">
							<xsl:value-of select="$charLt"/>div class="controls">
								<xsl:apply-templates mode="buildTableColumn_newinsertInput"/>
								<xsl:value-of select="$charLt"/>p>
									<xsl:value-of select="$charLt"/>input type="submit" class="btn blue btn-block" value="提交" />
								<xsl:value-of select="$charLt"/>/p>
							<xsl:value-of select="$charLt"/>/div>
						<xsl:value-of select="$charLt"/>/div>
					<xsl:value-of select="$charLt"/>/form>
				<xsl:value-of select="$charLt"/>/div>
			<xsl:value-of select="$charLt"/>/div>
			<xsl:value-of select="$charLt"/>div class="container-fluid">
				<xsl:value-of select="$charLt"/>div class="row-fluid">
					<xsl:value-of select="$charLt"/>div class="span12">
						<xsl:value-of select="$charLt"/>h3 class="page-title">
							<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%>管理<xsl:value-of select="$charLt"/>small><xsl:value-of select="$charLt"/>/small>
						<xsl:value-of select="$charLt"/>/h3>
						<xsl:value-of select="$charLt"/>ul class="breadcrumb">
							<xsl:value-of select="$charLt"/>li><xsl:value-of select="$charLt"/>i class="icon-home"><xsl:value-of select="$charLt"/>/i> <xsl:value-of select="$charLt"/>a href="index.html">首页<xsl:value-of select="$charLt"/>/a> <xsl:value-of select="$charLt"/>i
								class="icon-angle-right"><xsl:value-of select="$charLt"/>/i><xsl:value-of select="$charLt"/>/li>
							<xsl:value-of select="$charLt"/>li><xsl:value-of select="$charLt"/>a href="#">表管理<xsl:value-of select="$charLt"/>/a> <xsl:value-of select="$charLt"/>i class="icon-angle-right"><xsl:value-of select="$charLt"/>/i><xsl:value-of select="$charLt"/>/li>
							<xsl:value-of select="$charLt"/>li><xsl:value-of select="$charLt"/>a href="#"><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME%><xsl:value-of select="$charLt"/>/a><xsl:value-of select="$charLt"/>/li>
						<xsl:value-of select="$charLt"/>/ul>
					<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>div class="row-fluid">
					<xsl:value-of select="$charLt"/>div class="span6">
						<xsl:value-of select="$charLt"/>div class="portlet box blue">
							<xsl:value-of select="$charLt"/>div class="portlet-title">
								<xsl:value-of select="$charLt"/>div class="caption">
									<xsl:value-of select="$charLt"/>i class="icon-cogs"><xsl:value-of select="$charLt"/>/i>查询
								<xsl:value-of select="$charLt"/>/div>
								<xsl:value-of select="$charLt"/>div class="tools">
									<xsl:value-of select="$charLt"/>a href="javascript:;" class="collapse"><xsl:value-of select="$charLt"/>/a>
								<xsl:value-of select="$charLt"/>/div>
							<xsl:value-of select="$charLt"/>/div>
							<xsl:value-of select="$charLt"/>div class="portlet-body">
								<xsl:value-of select="$charLt"/>form action="<xsl:value-of select="$charLt"></xsl:value-of>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>" method="post">
									<xsl:value-of select="$charLt"/>div class="control-group">
									<xsl:value-of select="$charLt"/>div class="controls">
										<xsl:apply-templates mode="buildTableColumn_queryInput_newversion"/>
										<xsl:value-of select="$charLt"/>p>
											<xsl:value-of select="$charLt"/>input type="submit" class="btn blue btn-block" value="查询" />
										<xsl:value-of select="$charLt"/>/p>
										<xsl:value-of select="$charLt"/>/div>
									<xsl:value-of select="$charLt"/>/div>
								<xsl:value-of select="$charLt"/>/form>
							<xsl:value-of select="$charLt"/>/div>
						<xsl:value-of select="$charLt"/>/div>
					<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>div class="row-fluid">
				<xsl:value-of select="$charLt"/>div class="span12">
					<xsl:value-of select="$charLt"/>div class="portlet box blue">
						<xsl:value-of select="$charLt"/>div class="portlet-title">
							<xsl:value-of select="$charLt"/>div class="caption">
								<xsl:value-of select="$charLt"/>i class="icon-globe"><xsl:value-of select="$charLt"/>/i>列表
							<xsl:value-of select="$charLt"/>/div>
							<xsl:value-of select="$charLt"/>div class="tools">
										<xsl:value-of select="$charLt"/>a href="#" onclick="update();" data-toggle="modal" class="config"><xsl:value-of select="$charLt"/>/a> 
										<xsl:value-of select="$charLt"/>a href="javascript:;" onclick="_delete();" class="halflings-icon white trash"><xsl:value-of select="$charLt"/>/a>
										<xsl:value-of select="$charLt"/>a href="#portlet-config-add" data-toggle="modal" class="halflings-icon white file"><xsl:value-of select="$charLt"/>/a> 
										<xsl:value-of select="$charLt"/>a href="javascript:;" class="collapse"><xsl:value-of select="$charLt"/>/a>
									<xsl:value-of select="$charLt"/>/div>
						<xsl:value-of select="$charLt"/>/div>
						<xsl:value-of select="$charLt"/>div class="portlet-body no-more-tables">
							<xsl:value-of select="$charLt"/>table class="table-bordered table-striped table-condensed cf">
								<xsl:value-of select="$charLt"/>thead>
									<xsl:value-of select="$charLt"/>tr>
									<xsl:value-of select="$charLt"/>th style="width: 40px">选择<xsl:value-of select="$charLt"/>/th>
									<xsl:apply-templates mode="buildTableColumn_queryInput_newlisttr"/>
									<xsl:value-of select="$charLt"/>/tr>
								<xsl:value-of select="$charLt"/>/thead>
								<xsl:value-of select="$charLt"/>tbody>
									<xsl:value-of select="$charLt"/>c:forEach items="${beans}" var="list" varStatus="rowCounter"
												begin='0' step='1'>
												<xsl:value-of select="$charLt"/>tr>
													<xsl:apply-templates mode="buildTableColumn_queryInput_newlisttr2"/>
												<xsl:value-of select="$charLt"/>/tr>
								 <xsl:value-of select="$charLt"/>/c:forEach>
								<xsl:value-of select="$charLt"/>/tbody>
							<xsl:value-of select="$charLt"/>/table>
							<xsl:value-of select="$charLt"/>form name="form" method="post">
							<xsl:value-of select="$charLt"/>jsp:include page="/jsp/include/page.jsp"><xsl:value-of select="$charLt"/>/jsp:include>
							<xsl:value-of select="$charLt"/>/form>
						<xsl:value-of select="$charLt"/>/div>
					<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>/div>
			<xsl:value-of select="$charLt"/>/div>
			<xsl:value-of select="$charLt"/>/div>
	<xsl:value-of select="$charLt"/>/div>
	<xsl:value-of select="$charLt"/>/div>

	<xsl:value-of select="$charLt"/>div class="footer">
		<xsl:value-of select="$charLt"/>div class="footer-inner">2013  Metronic by keenthemes<xsl:value-of select="$charLt"/>/div>
		<xsl:value-of select="$charLt"/>div class="footer-tools">
			<xsl:value-of select="$charLt"/>span class="go-top"> <xsl:value-of select="$charLt"/>i class="icon-angle-up"><xsl:value-of select="$charLt"/>/i>
			<xsl:value-of select="$charLt"/>/span>
		<xsl:value-of select="$charLt"/>/div>
	<xsl:value-of select="$charLt"/>/div>
	
	<xsl:value-of select="$charLt"/>script type="text/javascript">
	function _delete() {
		var allcheck = $(".checkmain");
		var deletes = "";
		for (var g = 0; g <xsl:value-of select="$charLt"/> allcheck.length; g++) {
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
 				form.action="<xsl:value-of select="$charLt"></xsl:value-of>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/delete?ids=" + deletes;
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
		for (var g = 0; g <xsl:value-of select="$charLt"></xsl:value-of> allcheck.length; g++) {
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
		$("#portlet-config-edit").load("<xsl:value-of select="$charLt"></xsl:value-of>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/modify/" + updateDate);
	}
	function list_onClick() {
        form.action="<xsl:value-of select="$charLt"></xsl:value-of>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>";
		form.submit();
	}
	<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
</xsl:template>
</xsl:stylesheet>