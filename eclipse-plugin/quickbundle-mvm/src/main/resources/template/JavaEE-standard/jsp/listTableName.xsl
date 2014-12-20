<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@ page import="java.util.List" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<xsl:if test="column[@isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
</xsl:if>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.base.web.page.RmPageVo"%>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.vo.<xsl:value-of select="$TableNameVo"/>" %>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>%  //判断是否只读
    boolean isReadOnly = false;
    if("1".equals(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_IS_READ_ONLY))) {
        isReadOnly = true;
    } else if("1".equals(request.getParameter(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_IS_READ_ONLY))){
        isReadOnly = true;
    } 
%>
<xsl:value-of select="$charLt"/>%  //取出List
    List<xsl:value-of select="$charLt"/><xsl:value-of select="$TableNameVo"/>> lResult = null;  //定义结果列表的List变量
    if(request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_BEANS) != null) {  //如果request中的beans不为空
        lResult = (List)request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_BEANS);  //赋值给resultList
    }
%>
<xsl:if test="contains(@customBundleCode, 'importExport')">
<xsl:value-of select="$charLt"/>%  //是否跳往打印页面
    if("1".equals(request.getParameter("isExport"))) {  //如果isExport参数等于1
        session.setAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_QUERY_CONDITION, request.getAttribute(<xsl:value-of select="$ITableNameConstants"/>.REQUEST_QUERY_CONDITION).toString());  //把查询条件放到session中
        RmPageVo pageVo = (RmPageVo)request.getAttribute("RM_PAGE_VO");
        session.setAttribute("RECORD_COUNT", String.valueOf(pageVo.getRecordCount()));
        response.sendRedirect(request.getContextPath() + "/<xsl:value-of select="$tableDirName"/>/export");  //跳转到定制导出页面
        return;
    }
%>
</xsl:if>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>%@ include file="/jsp/include/rmGlobal.jsp" %>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>bean:message key="qb.web_title"/><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>script type="text/javascript">
<xsl:value-of select="$charLt"/>%if(!isReadOnly) {%>
    function findCheckbox_onClick() {  //从多选框到修改页面
        var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
        if(ids == null) {  //如果ids为空
            alert("请选择一条记录!")
            return false;
        }
        if(ids.length > 1) {  //如果ids有2条以上的纪录
            alert("只能选择一条记录!")
            return false;
        }
        window.location="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/update/" + ids;
    }
    function deleteMulti_onClick(){  //从多选框物理删除多条记录
        var ids = findSelections("checkbox_template","id");  //取得多选框的选择项
        if(ids == null) {  //如果ids为空
            alert("请选择记录!")
            return false;
        }
        if(!confirm("是否彻底删除该数据？")) {  //如果用户在确认对话框按"确定"
            return false;
        }
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/delete?ids=" + ids;
        form.submit();
    }
  	<xsl:if test="contains(@customBundleCode, 'importExport')">
    function toImport_onClick() {  //到导入页面
        window.location="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/import";
    }</xsl:if>
    function toAdd_onClick() {  //到增加记录页面
        window.location="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/insert";
    }
<xsl:value-of select="$charLt"/>%} %>
    function list_onClick(){  //简单的模糊查询
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>";
        form.submit();
    }
    <xsl:if test="contains(@customBundleCode, 'importExport')">
    function export_onClick(){  //导出
        form.isExport.value="1";
        form.target="_blank";
        form.submit();
        form.target="_self";    
        form.isExport.value="";
    }
    </xsl:if>
    function refresh_onClick() {  //刷新本页
        form.submit();
    }
    function detail_onClick(thisId) {  //实现转到详细页面
        form.action="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/detail/" + thisId;
        form.submit();
    }
    function toDoDblClick(thisId) {
        detail_onClick(thisId);
    }
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
<xsl:value-of select="$charLt"/>form name="form" method="post">

<xsl:value-of select="$charLt"/>div id="div_queryArea">
    <xsl:value-of select="$charLt"/>div class="div_queryButtons">
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_ok" onclickto="javascript:list_onClick()" value="查询" />
        <xsl:value-of select="$charLt"/>input type="reset" class="button_ellipse" id="button_reset" value="清空" />
    <xsl:value-of select="$charLt"/>/div>
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

<xsl:value-of select="$charLt"/>table class="tableHeader">
  <xsl:value-of select="$charLt"/>tr>
    <xsl:value-of select="$charLt"/>td width="1%"><xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/bg_mcontentL.gif" /><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td class="tableHeaderMiddleTd"><xsl:value-of select="$charLt"/>b><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY %>列表<xsl:value-of select="$charLt"/>/b><xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td class="tableHeaderMiddleTd" width="60%" align="right">
<xsl:value-of select="$charLt"/>%if(!isReadOnly) {%>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_toAdd" value="新增" onclick="javascript:toAdd_onClick();" title="跳转到新增页面"/>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_deleteMulti" value="删除" onclickto="javascript:deleteMulti_onClick();" title="删除所选的记录"/>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_findCheckbox" value="修改" onclick="javascript:findCheckbox_onClick();" title="跳转到修改所选的某条记录"/><xsl:value-of select="$charLt"/>%} %>
<xsl:if test="contains(@customBundleCode, 'importExport')">
<xsl:value-of select="$charLt"/>%if(!isReadOnly) {%>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_toImport" value="导入" onclick="javascript:toImport_onClick()" title="导入数据"/> <xsl:value-of select="$charLt"/>%} %>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_export" value="导出" onclick="javascript:export_onClick();" title="按当前查询条件导出数据"/></xsl:if>
        <xsl:value-of select="$charLt"/>input type="button" class="button_ellipse" id="button_refresh" value="刷新" onclickto="javascript:refresh_onClick();" title="刷新当前页面"/>
    <xsl:value-of select="$charLt"/>/td>
    <xsl:value-of select="$charLt"/>td width="1%" align="right"><xsl:value-of select="$charLt"/>img src="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/bg_mcontentR.gif" /><xsl:value-of select="$charLt"/>/td>
  <xsl:value-of select="$charLt"/>/tr>
<xsl:value-of select="$charLt"/>/table>

<xsl:value-of select="$charLt"/>layout:collection name="beans" id="rmBean" styleClass="listCss" width="100%" indexId="rmOrderNumber" align="center" sortAction="0">
    <xsl:value-of select="$charLt"/>layout:collectionItem width="1%" title="<xsl:value-of select="$charLt"/>input type='checkbox' pdType='control' control='checkbox_template'/>" style="text-align:center;">
        <xsl:value-of select="$charLt"/>bean:define id="rmValue" name="rmBean" property="<xsl:value-of select="$tablePkFormatLower"/>"/>
        <xsl:value-of select="$charLt"/>bean:define id="rmDisplayName" name="rmBean" property="<xsl:value-of select="$keyColumnFormatLower"/>"/>
        <xsl:value-of select="$charLt"/>input type="checkbox" name="checkbox_template" value="<xsl:value-of select="$charLt"/>%=rmValue%>" displayName="<xsl:value-of select="$charLt"/>%=rmDisplayName%>"/>
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
<xsl:if test="contains(@customBundleCode, 'importExport')">
<xsl:value-of select="$charLt"/>input type="hidden" name="isExport" value="">
</xsl:if>
<xsl:value-of select="$charLt"/>input type="hidden" name="queryCondition" value="">
<xsl:value-of select="$charLt"/>%=isReadOnly ? "<xsl:value-of select="$charLt"/>input type=\"hidden\" name=\"" + <xsl:value-of select="$ITableNameConstants"/>.REQUEST_IS_READ_ONLY + "\" value=\"1\">" : ""%>

<xsl:value-of select="$charLt"/>%--begin 生成页面汇总，正式部署前删除以下代码 --%>
<xsl:value-of select="$charLt"/>div id="div_funcNode" style="padding:20px 10px 10px 0px; display:none" align="right">
	<xsl:if test="contains(@customBundleCode, 'ajax')">
    <xsl:value-of select="$charLt"/>a class="aul" target="_blank" href="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/ajax">Ajax<xsl:value-of select="$charLt"/>/a>
	</xsl:if>
	<xsl:if test="contains(@customBundleCode, 'statistic')">
    <xsl:value-of select="$charLt"/>a class="aul" target="_blank" href="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/chart">图表统计<xsl:value-of select="$charLt"/>/a>
    <xsl:value-of select="$charLt"/>a class="aul" target="_blank" href="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/flash">Flash图表<xsl:value-of select="$charLt"/>/a>
    <xsl:value-of select="$charLt"/>a class="aul" target="_blank" href="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>/statistic/table">交叉统计<xsl:value-of select="$charLt"/>/a>
	</xsl:if>
    <xsl:value-of select="$charLt"/>a class="aul" target="_blank" href="<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="@tableDirName"/>?<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.REQUEST_IS_READ_ONLY%>=1">只读模式<xsl:value-of select="$charLt"/>/a>
<xsl:value-of select="$charLt"/>/div>
<xsl:value-of select="$charLt"/>%--end --%>

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