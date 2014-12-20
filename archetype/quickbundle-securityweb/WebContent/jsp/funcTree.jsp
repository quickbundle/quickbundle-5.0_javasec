<%@ page contentType="text/html; charset=UTF-8" language="java"%><%@page import="java.util.Map"%><%@page import="java.util.Iterator"%><%@page import="org.quickbundle.tools.helper.RmJspHelper"%><%try{ %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<style type="text/css">
<!--
.isHand {
	cursor:pointer
}
.nav{
}
.nav UL {
PADDING: 0px; 
DISPLAY: block; 
MARGIN: 0px; 
LIST-STYLE-TYPE: none; 
HEIGHT: 21px; 
BACKGROUND-COLOR: #970B0B;
COLOR: #ffffff; 
}
.nav LI {
BORDER-RIGHT: #ffffff 1px solid; 
DISPLAY: block; 
FLOAT: left; 
HEIGHT: 21px;
font-family:Arial, Helvetica, sans-serif;
    font-size:12px;
}
.nav LI A {
PADDING:1px 15px 0; 
DISPLAY: block; 
FONT-WEIGHT: none; 
COLOR: #ffffff; 
LINE-HEIGHT: 20px; 
TEXT-DECORATION: none;
}
.nav LI A:hover {
COLOR:#562505; 
BACKGROUND-COLOR:#FF6600; 
TEXT-DECORATION: none;
}
.current{
color:#ffffff;
background:#fceb81;/* FF9900*/
}
.nav li#date{
color:#ffffff;
PADDING:2px 15px 0; 
}
-->
</style>
<script src="<%=request.getContextPath()%>/js/tree/folderDocTree.js" type=text/javascript></script>
<script type="text/javascript">
function expandAllNode() {
	for(var i=0; i<indexOfEntries.length; i++) {
		if(indexOfEntries[i].setState != null) {
			indexOfEntries[i].setState(true);
		}
	}
}
function collapseAllNode() {
	indexOfEntries[0].setState(false);
	indexOfEntries[0].setState(true);
}
</script>
</head>
<body>
<div class="nav">
	<table width="165" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center">
				<img title="刷新" onClick="location.reload();" src="<%=request.getContextPath()%>/images/icon/refresh_tree.gif" class="isHand">&nbsp;&nbsp; 
				<img title="全部展开" onClick="expandAllNode()" src="<%=request.getContextPath()%>/images/icon/expand_all.gif" class="isHand">&nbsp;&nbsp; 
				<img title="全部折叠" onClick="collapseAllNode();" src="<%=request.getContextPath()%>/images/icon/collapse_all.gif" class="isHand"> 
			</td>
		</tr>
		<tr>
			<td background="<%=request.getContextPath()%>/images/tree_bg_all.gif" style="background-repeat:no-repeat">
<script type="text/javascript">
	var USETEXTLINKS = 1; //有无超链接
	var STARTALLOPEN = 1; //是否全部展开
	classPath = "<%=request.getContextPath()%>/js/tree/icon/";
	ftv2blank = "ftv2blank.gif";
	ftv2doc = "ftv2doc.gif";
	ftv2folderclosed = "ftv2folderclosed.gif";
	ftv2folderopen = "ftv2folderopen.gif";
	ftv2lastnode = "ftv2lastnode.gif";
	ftv2link = "ftv2link.gif";
	ftv2mlastnode = "ftv2mlastnode.gif";
	ftv2mnode = "ftv2mnode.gif";
	ftv2node = "ftv2node.gif";
	ftv2plastnode = "ftv2plastnode.gif";
	ftv2pnode = "ftv2pnode.gif";
	ftv2vertline = "tv2vertline.gif";
	basefrm="contentFrame";
	foldersTree = gFld("&nbsp;<bean:message key="qb.my_workspace"/>","","diffFolder.gif","diffFolder-0.gif");

	var toClickNodes = new Array();
<%
String total_code = request.getParameter("total_code");
if(total_code == null){
	total_code = "default";
}
%>

<%if(total_code.indexOf("default") > -1) {%>
<jsp:include page="funcTree_default.jsp" />
<%}%>

<%if(total_code.indexOf("bundle") > -1) {%>
<jsp:include page="funcTree_bundle.jsp" />
<%}%>

<!--startFunctionNodes-->
	initializeDocument();
	try {
		for(var i=0; i<toClickNodes.length; i++ ) {
			clickOnNode(eval(toClickNodes[i] + ".id"));
		}
	} catch(e){}
</script>
			</td>
		</tr>
		<tr>
			<td background="<%=request.getContextPath()%>/images/tree_bg_footer.gif" height="1px" style="background-repeat:no-repeat">&nbsp;</td>
		</tr>
	</table>
</div>
</body>
</html><%
}catch(Exception e) {
	e.printStackTrace();
}
%>