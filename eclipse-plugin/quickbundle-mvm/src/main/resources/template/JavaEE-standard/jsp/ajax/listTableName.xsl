<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:str="http://www.quickbundle.org" >
	<!--导入全局定义-->
	<xsl:import href="../../global.xsl"/>
	<!--忽略xml声明-->
	<xsl:output method="text" encoding="UTF-8" escape-uri-attributes="yes"/>
	<!--处理table-->
	<xsl:template match="table[1]">
<xsl:value-of select="$charLt"/>%@ page contentType="text/html; charset=UTF-8" language="java" %>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.IGlobalConstants"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.config.RmConfig"%>
<xsl:if test="column[@isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.project.RmGlobalReference"%>
</xsl:if>
<xsl:value-of select="$charLt"/>%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<xsl:value-of select="$charLt"/>%@ page import="<xsl:value-of select="$javaPackageTableDir"/>.<xsl:value-of select="$ITableNameConstants"/>" %>
<xsl:value-of select="$charLt"/>!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<xsl:value-of select="$charLt"/>html>
<xsl:value-of select="$charLt"/>head>
<xsl:value-of select="$charLt"/>meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<xsl:value-of select="$charLt"/>title><xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY%><xsl:value-of select="$charLt"/>/title>
<xsl:value-of select="$charLt"/>jsp:include page="/jsp/include/rmGlobalAjax.jsp" />
<xsl:value-of select="$charLt"/>script type="text/javascript">
Ext.onReady(function(){
<!--开始列循环：ajax页面枚举下拉参照定义-->
<xsl:for-each select="column[not(@columnName=$tablePk) and @isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
	<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
	//枚举下拉参照，用于表格编辑
	var rm_<xsl:value-of select="$columnNameFormatLower"/> = new fm.ComboBox({
        id: 'rm_<xsl:value-of select="$columnNameFormatLower"/>',
        typeAhead: true,
        triggerAction: 'all',
        transform: 'rmData_<xsl:value-of select="$columnNameFormatLower"/>',
        lazyRender: true,
        valueField: '<xsl:value-of select="$columnNameFormatLower"/>',
        value: '',
        emptyText: '',
        listClass: 'x-combo-list-small'
    });

	//枚举下拉参照，用于查询
	var rmQuery_<xsl:value-of select="$columnNameFormatLower"/> = new fm.ComboBox({
        id: 'rmQuery_<xsl:value-of select="$columnNameFormatLower"/>',
        typeAhead: true,
        triggerAction: 'all',
        transform: '<xsl:value-of select="$columnNameFormatLower"/>',
        lazyRender: true,
        listClass: 'x-combo-list-small',
        width:270
    });
</xsl:for-each>
<!--结束列循环：ajax页面枚举下拉参照定义-->
    //查询条件输入表单
    var formQuery = new Ext.form.FormPanel({
        renderTo: 'formQuery',
        labelAlign: 'top',
        frame:true,
        bodyStyle: 'padding: 1px',
        items: [
		<!--开始列循环：ajax页面formQuery定义-->
		<xsl:for-each select="column[not(@columnName=$tablePk) and @isBuild='true']">
			<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
			<xsl:variable name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
			<xsl:if test="position() mod 3 = 1">
		{
			layout:'column',
			items:[</xsl:if>
				{
					columnWidth: .33,
					layout: 'form',<xsl:choose>
					<!--处理rm.listReference(列表参照)-->
					<xsl:when test="@humanDisplayType='rm.listReference'">
					items: [{
						xtype: 'referencefield', fieldLabel: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>', anchor:'95%', name: '<xsl:value-of select="$columnNameFormatLower"/>', 
						urlPath: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="$humanDisplayTypeKeyword"/>/reference?referenceInputType=radio'
					}]</xsl:when>
					<!--处理rm.dictionary.select or rm.dictionary.checkbox(人性化展现方式)-->
					<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
	                items: [{
	                    xtype: 'compositefield',
	                    name: '<xsl:value-of select="$columnNameFormatLower"/>',
	                    fieldLabel: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>',
	                    items: [rmQuery_<xsl:value-of select="$columnNameFormatLower"/>]
	                }]</xsl:when>
					<!--处理数字-->
					<xsl:when test="@humanDisplayType='default' and (@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer') ">
	                items: [{
	                    xtype: 'compositefield',
	                    fieldLabel: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>',
	                    items: [
	                        {xtype: 'textfield', name: '<xsl:value-of select="$columnNameFormatLower"/>_from'},
	                        {xtype: 'displayfield', value: '到'},
	                        {xtype: 'textfield',    name: '<xsl:value-of select="$columnNameFormatLower"/>_to'}
	                    ]
	                }]</xsl:when>
					<!--处理日期、时间戳-->
					<xsl:when test="@humanDisplayType='default' and (@dataType='java.sql.Date' or @dataType='java.sql.Timestamp')">
	                items: [{
	                    xtype: 'compositefield',
	                    fieldLabel: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>',
	                    items: [
	                        {xtype: 'datefield', format:'Y-m-d', width: 100, name: '<xsl:value-of select="$columnNameFormatLower"/>_from'},
	                        {xtype: 'displayfield', value: '到'},
	                        {xtype: 'datefield', format:'Y-m-d', width: 100, name: '<xsl:value-of select="$columnNameFormatLower"/>_to'}
	                    ]
	                }]</xsl:when>
					<!--默认情况-->
					<xsl:otherwise>
					items: [{xtype: 'textfield', fieldLabel: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>', name: '<xsl:value-of select="$columnNameFormatLower"/>', anchor:'95%'}]</xsl:otherwise>
				</xsl:choose>
				}<xsl:if test="not(position() mod 3 = 0 or position() = last())">,</xsl:if>
				<xsl:if test="position() mod 3 = 0 or position()=last()">
            ]
        }<xsl:if test="not(position()=last())">,</xsl:if></xsl:if>
		</xsl:for-each>
		<!--结束列循环：ajax页面formQuery定义-->  
        ],

        buttons: [{
            text: '查询',
            handler: function() {
                if (formQuery.form.isValid()) {
                    store.load();
                    windowQuery.hide();
                }
            }
        },{
            text: '清空',
            handler: function() {
                formQuery.form.reset();
            }
        },{
            text: '关闭',
            handler: function() {
                windowQuery.hide();
            }
        }]
    });

    //查询条件对话框
    var windowQuery = new Ext.Window({
        title: '查询条件输入',
        collapsible: true,
        maximizable: true,
        width: 900,
        height: 500,
        layout: 'fit',
        plain: true,
        bodyStyle: 'padding:5px;',
        buttonAlign: 'center',
        items: formQuery
    });

    //每一行的checkbox框
    var selectionModel = new Ext.grid.CheckboxSelectionModel({});

    //每一行的展开+号
    var expander = new Ext.ux.grid.RowExpander({
        tpl : new Ext.Template(
            '<xsl:value-of select="$charLt"/>div style="padding:5px 0px 5px 45px">{<xsl:value-of select="$keyColumnFormatLower"/>}<xsl:value-of select="$charLt"/>/div>'
        )
    });

    //带锁定功能的列定义
    var columnModel = new Ext.ux.grid.LockingColumnModel({
        defaults: {
            sortable: true           
        },
        columns: [
            selectionModel,
            //expander, //可点击展开的+号
            {
                header: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$tablePkFormatLower"/>")%>',
                dataIndex: '<xsl:value-of select="$tablePkFormatLower"/>',
                width: 0 //主键默认隐藏
            },
		<!--开始列循环：ajax页面columnModel定义-->
		<xsl:for-each select="column[not(@columnName=$tablePk) and @isBuild='true']">
			<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
			<xsl:variable name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
			{
                header: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_COLUMN_DISPLAY.get("<xsl:value-of select="$columnNameFormatLower"/>")%>',
                dataIndex: '<xsl:value-of select="$columnNameFormatLower"/>',<xsl:choose>
				<!--处理rm.listReference(列表参照)-->
				<xsl:when test="@humanDisplayType='rm.listReference'">
				editor: new fm.ReferenceField({ <xsl:if test="@nullable='NO'">allowBlank: false, </xsl:if>urlPath: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/<xsl:value-of select="$humanDisplayTypeKeyword"/>/reference?referenceInputType=radio' })</xsl:when>
				<!--处理rm.dictionary.select or rm.dictionary.checkbox-->
				<xsl:when test="@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox'">
				editor: rm_<xsl:value-of select="$columnNameFormatLower"/>,
                renderer: Ext.util.Format.comboRenderer(rm_<xsl:value-of select="$columnNameFormatLower"/>)</xsl:when>
				<!--处理日期参照-->
				<xsl:when test="@dataType='java.sql.Date' or @dataType='java.sql.Timestamp'">
                width: 130,
                renderer: formatDate,
                editor: new fm.DateField({ <xsl:if test="@nullable='NO'">allowBlank: false, </xsl:if>format: 'Y-m-d' })</xsl:when>
				<!--处理数字-->
				<xsl:when test="@dataType='java.math.BigDecimal' or @dataType='java.lang.Long' or @dataType='java.lang.Integer'">
                width: 50,
                editor: new fm.NumberField({ <xsl:if test="@nullable='NO'">allowBlank: false</xsl:if> })</xsl:when>
				<!--默认其它类型的字段。处理普通的text-->
				<xsl:otherwise>
                editor: new fm.TextField({ <xsl:if test="@nullable='NO'">allowBlank: false</xsl:if> })</xsl:otherwise></xsl:choose>
			}<xsl:if test="not(position()=last())">,</xsl:if>
		</xsl:for-each>
		<!--结束列循环：ajax页面columnModel定义-->     
        ]
    });

    //主表格对应的数据
    var store = new Ext.data.JsonStore({
        autoDestroy: true,
        
        proxy: new Ext.data.HttpProxy({
            url: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/api/<xsl:value-of select="@tableDirName"/>',
            method: 'GET'
        }),
        remoteSort: true,
        root: '<xsl:value-of select="$charLt"/>%=IGlobalConstants.REQUEST_BEANS%>',
        idProperty: '<xsl:value-of select="$tablePkFormatLower"/>',
        totalProperty: 'totalCount',
        fields: [
			{name: '<xsl:value-of select="$tablePkFormatLower"/>'}<xsl:if test="count(column[not(@columnName=$tablePk)])>0">,</xsl:if>
		<!--开始列循环：ajax页面store定义-->
		<xsl:for-each select="column[not(@columnName=$tablePk)]">
			<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
			<xsl:choose>
				<!--处理日期参照-->
				<xsl:when test="@dataType='java.sql.Date'">
			{name: '<xsl:value-of select="$columnNameFormatLower"/>', type:'date', dateFormat:'Y-m-d'}</xsl:when>
				<!--处理时间戳参照-->
				<xsl:when test="@dataType='java.sql.Timestamp'">
			{name: '<xsl:value-of select="$columnNameFormatLower"/>', type:'date', dateFormat:'Y-m-d H:i:s'}</xsl:when>
				<!--处理数字-->
				<xsl:when test="@dataType='java.math.BigDecimal'">
			{name: '<xsl:value-of select="$columnNameFormatLower"/>', type: 'float'}</xsl:when>
				<!--处理数字-->
				<xsl:when test="@dataType='java.lang.Long' or @dataType='java.lang.Integer'">
			{name: '<xsl:value-of select="$columnNameFormatLower"/>', type: 'int'}</xsl:when>
				<!--默认其它类型的字段。处理普通的text-->
				<xsl:otherwise>
			{name: '<xsl:value-of select="$columnNameFormatLower"/>'}</xsl:otherwise>
			</xsl:choose>
			<xsl:if test="not(position()=last())">,</xsl:if>
		</xsl:for-each>
		<!--结束列循环：ajax页面store定义-->
        ]
    });

    //状态栏提示
    var statusBar = new Ext.menu.TextItem({
        text: ''
    });

    //分页工具栏
    var pagingToolbar = new Ext.PagingToolbar({
        pageSize: <xsl:value-of select="$charLt"/>%=RmConfig.getSingleton().getDefaultPageSize()%>,
        store: store,
        displayInfo: true,
        displayMsg: '第{0}－{1}条 / 共{2}条',
        emptyMsg: "没有相关记录！",
        items:[
            '-',
            {
                pressed: true,
                enableToggle:true,
                text: '预览',
                cls: 'x-btn-text-icon details',
                toggleHandler: function(btn, pressed){
                    var view = grid.getView();
                    view.showPreview = pressed;
                    view.refresh();
                }
            },
            '<xsl:value-of select="$charNbsp"/><xsl:value-of select="$charNbsp"/>',
            statusBar
        ]
    });

    //主表格定义
    var grid = new Ext.grid.EditorGridPanel({
        title: '<xsl:value-of select="$charLt"/>%=<xsl:value-of select="$ITableNameConstants"/>.TABLE_NAME_DISPLAY%>列表',
        store: store,
        colModel: columnModel,
        sm: selectionModel,
        height: 500,
        clicksToEdit: 1, //单击进入编辑状态
        //plugins: expander, //可点击展开的+号
        bbar: pagingToolbar,
        renderTo: 'grid',
        view: new Ext.ux.grid.LockingGridView(),
        tbar: [{
            text: '保存',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/save.gif',
            handler : function(){
                var records = store.getModifiedRecords();
                if(records != null <xsl:value-of select="$charAmp"/><xsl:value-of select="$charAmp"/> records.length > 0){
                    var params = {};
<xsl:value-of select="$charLt"/>%if(RmConfig.getSingleton().isSubmitJson()) {%>
                    var json = [];
                    Ext.each(records, function(item) {
                        json.push(item.data);
                    });
                    params["<xsl:value-of select="$charLt"/>%=IGlobalConstants.RM_AJAX_JSON%>"] = Ext.util.JSON.encode(json);
<xsl:value-of select="$charLt"/>%} else {%> 
                    params["<xsl:value-of select="$charLt"/>%=IGlobalConstants.RM_AJAX_RECORD_SIZE%>"] = records.length;
                    var sk = "<xsl:value-of select="$charLt"/>%=IGlobalConstants.RM_AJAX_SPLIT_KEY%>";
                    for(var i=0; i<xsl:value-of select="$charLt"/>records.length; i++) {
                        var rec = records[i];
						params["<xsl:value-of select="$tablePkFormatLower"/>" + sk + i] = rec.get('<xsl:value-of select="$tablePkFormatLower"/>');
		<!--开始列循环：ajax页面grid组装参数定义-->
		<xsl:for-each select="column[not(@columnName=$tablePk) and @isBuild='true']">
			<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
						params["<xsl:value-of select="$columnNameFormatLower"/>" + sk + i] = rec.get('<xsl:value-of select="$columnNameFormatLower"/>');</xsl:for-each>
		<!--结束列循环：ajax页面grid组装参数定义-->
                    }
<xsl:value-of select="$charLt"/>%}%>
                    Ext.Ajax.request({
                        url: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/api/<xsl:value-of select="@tableDirName"/>/batch',
                        method: 'POST',
                        params: params,
                        success: function(response) {
                            var obj = Ext.decode(response.responseText);
                            if(obj.error != null) {
                                Ext.Msg.alert("业务异常", obj.error);
                            } else if(obj.<xsl:value-of select="$charLt"/>%=IGlobalConstants.EXECUTE_ROW_COUNT%> != null){
                                var int_insert_update = obj.<xsl:value-of select="$charLt"/>%=IGlobalConstants.EXECUTE_ROW_COUNT%>;
                                Ext.Msg.alert("信息", "数据保存成功，新增" + int_insert_update[0] + "条，修改" + (int_insert_update[1] &lt; 0 ? (records.length-int_insert_update[0]) : int_insert_update[1]) + "条", function() {
                                    store.commitChanges();
                                    store.reload();
                                });
                            }
                        },
                        failure: function(response) {
                            Ext.Msg.alert("警告", "数据更新失败，请稍后再试！");
                        }
                    }); 
                } else {
                    Ext.Msg.alert("警告", "没有需要保存的数据！");
                }
            }
        },
        {
            text: '取消',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/cancel.gif',
            handler : function(){
                store.rejectChanges();
                Ext.example.msg('消息', "取消了当前表格的修改");
            }
        },
        {
            text: '增行',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/add.gif',
            handler : function(){
                var RmAffix = grid.getStore().recordType;
                var p = new RmAffix({
                <xsl:value-of select="$charLt"/>%
                    int index = 0;
                    for(Object key : request.getParameterMap().keySet()) {
                        if(index > 0) {
                            out.print(",\n");
                        }
                        out.print(key + ": '" + RmStringHelper.replaceStringToScript(request.getParameter(key.toString())) + "'");
                        index ++;
                    }
                %>
                });
                grid.stopEditing();
                store.insert(store.getCount(), p);
                grid.startEditing(store.getCount()-1, 2);
            }
        },
        {
            text: '删除',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/delete.gif',
            handler : function(){
                var selModel = grid.getSelectionModel();
                if (selModel.hasSelection()) {
                    Ext.Msg.confirm("警告", "确定要删除吗？", function(button) {
                        if (button == "yes") {
                            var ids = "";
                            var selections = selModel.getSelections();
                            Ext.each(selections, function(item) {
								if(item.get('<xsl:value-of select="$tablePkFormatLower"/>') != null) {
									ids += item.get('<xsl:value-of select="$tablePkFormatLower"/>') + ",";
                                }
                            });
                            ids = ids.replace(/,$/g, "");
                            if(ids != '') {
                                Ext.Ajax.request({
                                    url: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/api/<xsl:value-of select="@tableDirName"/>/delete', 
                                    method: 'POST',
                                    params: {ids: ids},
                                    success: function(response) {
                                        Ext.Msg.alert("信息", "数据删除成功", function() {
                                            store.remove(selections);
                                        });
                                    },
                                    failure: function(response) {
                                        Ext.Msg.alert("警告", "数据删除失败，请稍后再试！");
                                    }
                                }); 
                            } else {
                                store.remove(selections);
                            }
                        }
                    });
                } else {
                    Ext.Msg.alert("错误", "没有任何行被选中，无法进行删除操作！");
                }
            }
        },
        {
            text: '查询',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/search.gif',
            handler : function(){
                if(windowQuery.hidden) {
                    windowQuery.show();
                } else {
                    windowQuery.hide();
                }
            }
        },
        {
            xtype:'splitbutton',
            text: '刷新',
            icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/refresh.gif',
            handler : function(){
                store.reload();
            },
            menu: [{
                text: '首页', 
                icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/s.gif',
                handler: function() {
                    pagingToolbar.moveFirst();
                }
            },
            {
                text: '上一页', 
                icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/s.gif',
                handler: function() {
                    pagingToolbar.movePrevious();
                }
            },
            {
                text: '下一页', 
                icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/s.gif',
                handler: function() {
                    pagingToolbar.moveNext();
                }
            },
            {
                text: '末页', 
                icon: '<xsl:value-of select="$charLt"/>%=request.getContextPath()%>/images/icon/s.gif',
                handler: function() {
                    pagingToolbar.moveLast();
                }
            }]
        }]
    });

    //在读取json数据前预置parameter参数和formQuery字段
    store.on("beforeload", function(thiz, options) {
        statusBar.update("");
        Ext.iterate(formQuery.form.getValues(), function(key, value) {
            thiz.baseParams[key] = value;
        }, this);
        <xsl:value-of select="$charLt"/>%
            for(Object key : request.getParameterMap().keySet()) {
                out.println("thiz.baseParams['" + key + "'] = '" + RmStringHelper.replaceStringToScript(request.getParameter(key.toString())) + "';");
            }
        %>
    });

    //载入json数据后更新状态栏
    store.on("load", function(thiz, options) {
        statusBar.update("数据载入完毕！");
        window.setTimeout(function() { statusBar.update(''); }, 2000);
    });

    //手动载入数据
    store.load();
});
<xsl:value-of select="$charLt"/>/script>
<xsl:value-of select="$charLt"/>/head>
<xsl:value-of select="$charLt"/>body>
    <xsl:value-of select="$charLt"/>%-- 绑定主表格 --%>
    <xsl:value-of select="$charLt"/>div id="grid"><xsl:value-of select="$charLt"/>/div>
    <xsl:value-of select="$charLt"/>%-- 绑定查询条件输入表单 --%>
    <xsl:value-of select="$charLt"/>div id="formQuery" style="display:none"><xsl:value-of select="$charLt"/>/div>
    <xsl:value-of select="$charLt"/>%
<xsl:for-each select="column[not(@columnName=$tablePk) and @isBuild='true' and (@humanDisplayType='rm.dictionary.select' or @humanDisplayType='rm.dictionary.checkbox')]">
	<xsl:variable name="columnNameFormatLower" select="lower-case(str:filter(@columnName,@filterKeyword,@filterType))"/>
	<xsl:variable name="humanDisplayTypeKeyword" select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>
    	out.print(RmJspHelper.getSelectField("rmData_<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>), "", "id='rmData_<xsl:value-of select="$columnNameFormatLower"/>' style='display: none;'", false));
    	out.print(RmJspHelper.getSelectField("<xsl:value-of select="$columnNameFormatLower"/>", -1, RmGlobalReference.get(<xsl:value-of select="$ITableNameConstants"/>.DICTIONARY_<xsl:value-of select="str:substring-before2(@humanDisplayTypeKeyword, '=')"/>), "", "id='<xsl:value-of select="$columnNameFormatLower"/>' style='display: none;'", true));
</xsl:for-each>
    %>
<xsl:value-of select="$charLt"/>/body>
<xsl:value-of select="$charLt"/>/html> 
</xsl:template>
</xsl:stylesheet>