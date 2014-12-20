<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@page import="java.util.List" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmVoHelper" %>
<xsl:if test="column[@isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
</xsl:if>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>" %>
<xsl:value-of select="$charLt"/>%@page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%
    String referenceInputType = String.valueOf(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_REFERENCE_INPUT_TYPE));
    if(referenceInputType == null || referenceInputType.length() == 0 || (!"checkbox".equals(referenceInputType.toLowerCase()) <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> !"radio".equals(referenceInputType.toLowerCase()))) {
        referenceInputType = "radio";       
    }
    //取出List
    List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> lResult = null;  //定义结果列表的List变量
    if(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_BEANS) != null) {  //如果request中的beans不为空
        lResult = (List)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_BEANS);  //赋值给resultList
    }
%>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>bean:message key="qb.web_title"/><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>script type="text/javascript">
    var rmActionName = "<xsl:value-of select="$tableFormatNameUpperFirst"/>Action";
    function simpleQuery_onClick(){  //简单的模糊查询
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/reference";
        form.submit();
    }
    function refresh_onClick(){  //刷新本页
        form.submit();
    }
    function detail_onClick(thisId) {  //实现转到详细页面
        //参照页面默认不进去细览
    }
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>form name="form" method="post">

<xsl:value-of select="$charLt"/>div id="div_simple">
    <xsl:value-of select="$charLt"/>table class="table_query">
        <xsl:value-of select="$charLt"/>tr>
            <xsl:value-of select="$charLt"/>td width="20%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="35%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="20%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="25%"><xsl:value-of select="$charNbsp"/><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>/tr>
		<xsl:apply-templates mode="buildTableColumn_queryInput"/>
        <xsl:value-of select="$charLt"/>/table>
<xsl:value-of select="$charLt"/>/div> 
<xsl:value-of select="$charLt"/>div id="div_advanced" style="display:none;">
    <xsl:value-of select="$charLt"/>table class="table_query">
        <xsl:value-of select="$charLt"/>tr>
            <xsl:value-of select="$charLt"/>td width="20%"><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="35%"><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="20%"><xsl:value-of select="$charLt"/>/td>
            <xsl:value-of select="$charLt"/>td width="25%"><xsl:value-of select="$charLt"/>/td>
        <xsl:value-of select="$charLt"/>/tr>
        <xsl:value-of select="$charLt"/>%-- 需要隐藏查询条件剪切到这里 --%>
    <xsl:value-of select="$charLt"/>/table>
<xsl:value-of select="$charLt"/>/div>

<xsl:value-of select="$charLt"/>table class="tableHeader">
  <xsl:value-of select="$charLt"/>tr>
    <xsl:value-of select="$charLt"/>td width="1%"><xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/bg_mcontentL.gif" /><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td class="tableHeaderMiddleTd"><xsl:value-of select="$charLt"/>b><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY %>列表<xsl:value-of select="$charLt"/>/b><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td class="tableHeaderMiddleTd" width="60%" align="right">
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    <xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td width="1%" align="right"><xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/bg_mcontentR.gif" /><xsl:value-of select="$charLt"/>/td>
  <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>/table>

<xsl:value-of select="$charLt"/>layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
    <xsl:value-of select="$charLt"/>layout:collectionItem width="1%" title="<xsl:value-of select="$charLt"/>input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
        <xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$tablePkFormatLower"/>"/>
        <xsl:value-of select="$charLt"/>bean:define id="rmDisplayName" name="rmBean" property="<xsl:value-of select="$keyColumnFormatLower"/>"/>
        <xsl:value-of select="$charLt"/>input type="<xsl:value-of select="$charLt"/>%="checkbox".equals(referenceInputType)?"checkbox":""%><xsl:value-of select="$charLt"/>%="radio".equals(referenceInputType)?"radio":""%>" name="checkbox_template" value="<xsl:value-of select="$charLt"/>%=rmValue%>" displayName="<xsl:value-of select="$charLt"/>%=rmDisplayName%>" />
    <xsl:value-of select="$charLt"/>/layout:collectionItem>
    <xsl:value-of select="$charLt"/>layout:collectionItem width="3%"  title="序" style="text-align:center;">
        <xsl:value-of select="$charLt"/>%
            Integer rmOrderNumber = (Integer)pageContext.getAttribute("rmOrderNumber");
            RmPageVo pageVo = (RmPageVo)pageContext.getRequest().getAttribute(<xsl:value-of select="$ITableNameConstants"/>.RM_PAGE_VO);
            out.print((pageVo.getCurrentPage() - 1) * pageVo.getPageSize() + rmOrderNumber.intValue() + 1);
        %>
        <xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$tablePkFormatLower"/>"/>
        <xsl:value-of select="$charLt"/>input type="hidden" signName="hiddenId" value="<xsl:value-of select="$charLt"/>%=rmValue%>"/>
    <xsl:value-of select="$charLt"/>/layout:collectionItem>
	<xsl:apply-templates mode="buildTableColumn_listLayout"/>
    <xsl:value-of select="$charLt"/>/layout:collection>
        
<xsl:value-of select="$charLt"/>%-- 下边这句是翻页, 如果去掉就不带翻页了,同时注意Action中也要调整方法 --%>
<xsl:value-of select="$charLt"/>jsp:include page="/jsp/include/page.jsp" />

<xsl:value-of select="$charLt"/>input type="hidden" name="id" value="">
<xsl:value-of select="$charLt"/>input type="hidden" name="queryCondition" value="">
<xsl:value-of select="$charLt"/>input type="hidden" name="referenceInputType" value="<xsl:value-of select="$charLt"/>%=referenceInputType%>">

<xsl:value-of select="$charLt"/>input type="hidden" id="rmCheckReturnValue" name="rmCheckReturnValue" value="">
<xsl:value-of select="$charLt"/>input type="hidden" id="rmCheckReturnName" name="rmCheckReturnName" value="">

<xsl:value-of select="$charLt"/>/form>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html>
<xsl:value-of select="$charLt"/>script type="text/javascript">
<xsl:value-of select="$charLt"/>%  //表单回写
    if(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_WRITE_BACK_FORM_VALUES) != null) {  //如果request中取出的表单回写bean不为空
        out.print(RmVoHelper.writeBackMapToForm((java.util.Map)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_WRITE_BACK_FORM_VALUES)));  //输出表单回写方法的脚本
    }
%>
<xsl:value-of select="$charLt"/>/script>
</xsl:template>
</xsl:stylesheet>
