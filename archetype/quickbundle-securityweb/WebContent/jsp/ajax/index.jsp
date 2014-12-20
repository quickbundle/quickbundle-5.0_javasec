<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="en" xmlns:ext="http://www.extjs.com/docs">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>ExtJS 3.2.1 API Documentation</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/resources/css/ext-all.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/docs/resources/docs.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/docs/resources/style.css" />    
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/third/extjs/resources/favicon.ico" />
	<link rel="icon" href="<%=request.getContextPath()%>/third/extjs/resources/favicon.ico" />
	<style type="text/css">
	.loading-indicator {
	    font-size: 11px;
	    background-image: url(<%=request.getContextPath()%>/third/extjs/resources/images/default/grid/loading.gif);
	}
	</style>
	<!-- GC -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/adapter/ext/ext-base.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/ext-all.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/TabCloseMenu.js"></script>
    <script type="text/javascript" src="docs.jsp"></script>
    <script type="text/javascript" src="tree.jsp"></script>

</head>
<body scroll="no" id="docs">
  <div id="loading-mask" style=""></div>
  <div id="loading">
    <div class="loading-indicator"><img src="<%=request.getContextPath()%>/third/extjs/docs/resources/extanim32.gif" width="32" height="32" style="margin-right:8px;" align="absmiddle"/>Loading...</div>
  </div>

  <div id="classes"></div>

  <div id="main"></div>
  
  <select id="search-options" style="display:none">
    <option>Starts with</option>
    <option>Ends with</option>
    <option>Any Match</option>
</select>
  </body>
</html>
