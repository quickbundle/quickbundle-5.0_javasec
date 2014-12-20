<%@ page contentType="text/javascript; charset=UTF-8" language="java" %>

Docs.classData ={"id":"apidocs","iconCls":"icon-docs","text":"我的工作台","singleClickExpand":true,"children":[
	{"id":"rm_support","text":"扩展功能","iconCls":"icon-pkg","cls":"package","singleClickExpand":true, children:[
		{"href":"<%=request.getContextPath()%>/RmCodeTypeAction.do?cmd=queryAll","text":"编码表","id":"RmCodeType","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
		,
		{"href":"<%=request.getContextPath()%>/RmAffixAction.do?cmd=queryAll","text":"附件","id":"RmAffix","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
	]}
	,
	{"id":"rm_profiler","text":"性能分析","iconCls":"icon-pkg","cls":"package","singleClickExpand":true, children:[
		{"href":"<%=request.getContextPath()%>/admin/log/request_profiler.py","text":"Request分析器","id":"request_profiler","isClass":true,"iconCls":"icon-cmp","cls":"cls","leaf":true}
		,
		{"href":"<%=request.getContextPath()%>/admin/log/sql_profiler.py","text":"SQL分析器","id":"sql_profiler","isClass":true,"iconCls":"icon-cmp","cls":"cls","leaf":true}
		,
		{"href":"<%=request.getContextPath()%>/admin/log/method_profiler.py","text":"Method分析器","id":"method_profiler","isClass":true,"iconCls":"icon-cmp","cls":"cls","leaf":true}
	]}
	,
	{"href":"<%=request.getContextPath()%>/admin/proxool","text":"连接池监控","id":"rm_proxool","isClass":true,"iconCls":"icon-cmp","cls":"cls","leaf":true}
	,
	{"href":"<%=request.getContextPath()%>/third/swfupload/simpleupload.jsp","text":"大文本字段","id":"RmClob","isClass":true,"iconCls":"icon-cmp","cls":"cls","leaf":true}
	,
	{"href":"<%=request.getContextPath()%>/modules/affix/rmaffix/ajax/listRmAffix.jsp","text":"附件Ajax","id":"row_editor_data","isClass":true,"iconCls":"icon-cls","cls":"cls","leaf":true}
	<jsp:include page="tree_default.jsp" />
]};

Docs.icons = {
	"Ext.chart.Axis":"icon-cls"
	,
	"Ext.chart.BarChart":"icon-cmp"
	};
    