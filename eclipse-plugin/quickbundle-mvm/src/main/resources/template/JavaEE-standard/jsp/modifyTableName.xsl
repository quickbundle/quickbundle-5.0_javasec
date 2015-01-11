<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org">
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="no"/>
	<!--处理table-->
	<xsl:template match="table[1]">
		<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:if test="column[@isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
			<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
</xsl:if>
		<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<xsl:value-of select="$charLt"/>%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>

<xsl:value-of select="$charLt"/>%  //取出本条记录
    <xsl:value-of select="$TableNameVo"/> resultVo = null;  //定义一个临时的vo变量
    resultVo = (<xsl:value-of select="$TableNameVo"/>)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
    RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>div class="modal-header">
					<xsl:value-of select="$charLt"/>button data-dismiss="modal" class="close" type="button"><xsl:value-of select="$charLt"/>/button>
					<xsl:value-of select="$charLt"/>h3>修改<xsl:value-of select="$charLt"/>/h3>
				<xsl:value-of select="$charLt"/>/div>
				<xsl:value-of select="$charLt"/>div class="modal-body">
					<xsl:value-of select="$charLt"/>form action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/update" method="post">
						<xsl:value-of select="$charLt"/>div class="control-group">
							<xsl:value-of select="$charLt"/>div class="controls">
							<xsl:apply-templates mode="buildTableColumn_detailDisplay_newversion"/>
								<xsl:value-of select="$charLt"/>p>
									<xsl:value-of select="$charLt"/>input id="id" name="id" type="hidden" /> <xsl:value-of select="$charLt"/>input type="submit" class="btn blue btn-block" value="提交" />
								<xsl:value-of select="$charLt"/>/p>
							<xsl:value-of select="$charLt"/>/div>
						<xsl:value-of select="$charLt"/>/div>
					<xsl:value-of select="$charLt"/>/form>
				<xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
	</xsl:template>
</xsl:stylesheet>
