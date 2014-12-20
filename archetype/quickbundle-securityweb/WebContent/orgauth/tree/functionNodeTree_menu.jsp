<%@page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>rm-based architecture project</title>
<jsp:include page="/jsp/support/deeptree/include/globalDeepTree.jsp" />
<script type="text/javascript">
//初始大小
$(function(){
	//setlayerNavHeight();
})
//当窗口大小改变时重新设置大小
$(window).resize(function(){
	setTimeout(setlayerNavHeight,0);
});  

//设置leftNav的高度的函数
function setlayerNavHeight(){
	hi = (document.compatMode == 'CSS1Compat') ? document.documentElement.clientHeight : document.body.clientHeight;  //获取可见区大小
	hi = hi - 38;  
	$(".leftNavWrapper").css("height",hi);
}
</script>

<style type="text/css">
<!--
html,body {  
	padding:0px;
	margin:0px;
	color:#000000;
	font-size:12px;
	line-height:2em;
	background-color:#fff;
	font-family:宋体,arial, helvetica, sans-serif;
	scrollbar-face-color: #d8dff1;
	scrollbar-highlight-color: #ffffff;
	scrollbar-shadow-color: #cad1eb;
	scrollbar-3dlight-color: #ffffff;
	scrollbar-arrow-color:  #001a6b;
	scrollbar-track-color: #f0f0f0;
	scrollbar-darkshadow-color: #cad1eb;
	overflow-x:hidden;
	overflow-y:auto;
}

/*left frame style*/
.leftBody h3{
	background:url(<%=request.getContextPath()%>/images/bg_leftbodyHL.gif) 0 0 no-repeat;
	margin:0;
	font-size:12px;
	font-weight:normal;
	padding:0 0 0 10px;
}
.leftBody h3 span{
	background:url(<%=request.getContextPath()%>/images/bg_leftbodyHR.gif) top right no-repeat;
	height:30px;
	width:100%;
	display:block;
	text-align:center;
	margin:0;
	vertical-align:bottom;
	padding-top:1mm;
	line-height:30px;
}
.leftBody .leftNavWrapper{
	margin:0;
	padding:8px 0 0 0;
	overflow:auto;
	background:url(<%=request.getContextPath()%>/images/bg_leftbodyNav.gif) left top repeat-x;
}
.leftBody .leftNav{
	margin:0 0 0 8px;		
}

.UrsInfo { border-bottom:1px dotted #FFF;border-top:1px  dotted #FFF; margin-bottom:0px; padding:5px; color:#000;}

.dtree {
	font-family: Verdana, Geneva, Arial, Helvetica, sans-serif, 宋体;
	color: #666;
	white-space: nowrap;
}
.dtree img {
	border: 0px;
	vertical-align: middle;
}
.dtree a {
	color: #333;
	text-decoration: none;
}
.dtree a.node, .dtree a.nodeSel {
	white-space: nowrap;
	padding: 1px 2px 1px 2px;
}
.dtree a.node:hover, .dtree a.nodeSel:hover {
	color: #333;
	text-decoration: underline;
}
.dtree a.nodeSel {
	background-color: #c0d2ec;
}
.dtree .clip {
	overflow: hidden;
}
.isHand {
	cursor:pointer
}
span a.hidden {
    color: #ACACAC;
    text-decoration: none;
}
 -->
</style>
<script type="text/javascript">
	rootXmlSource = "functionNodeTreeData.jsp?cmd=leftMenuData&total_code=<%=request.getParameter("party_total_code") != null ? request.getParameter("party_total_code") : ""%>&enable_status=1&node_type=3";
	//定义全局路径
	var treeImagePath = "<%=request.getContextPath()%>/jsp/support/deeptree/image";  //树图标路径
	var xslPath = "<%=request.getContextPath()%>/jsp/support/deeptree/deeptree_xsl.jsp?inputType=" + inputType + "&treeImagePath=" + treeImagePath;  //xslt文件相对路径

	//根据需要可以重写的方法
	function toDoMouseClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseDbClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
		var thisHiddenValue = getHiddenValueByEvent(obj.id);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.outerHTML);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.parentNode.outerHTML);
	}
	
	function toDoMouseOver(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseOut(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}

/**********************开始表单提交相关方法 **********************/

	function returnValueName() {  //获得所有选择的checkbox
		currentId = "";
		var checkedArray = new Array(0);		
		var submitStringArray = new Array(0);
		var submitObjectArray = new Array(0);
				
		var obj = null;
		if(inputType == "checkbox") {
			obj = window.document.getElementsByName(prefixCheckbox);
			for(i=0;i<obj.length;i++){
				if(obj[i].checked){
					checkedArray.push(obj[i].id.substring(prefixCheckbox.length));
				}
			}
			if(submitType == "parentPriority") {
				submitStringArray = clearChild(checkedArray);  //全面扫描checkedArray,把其中冗余的字节点信息去掉,Id列表放入submitStringArray
			} else {
				submitStringArray = filterHidden(checkedArray);
			}
		} else if(inputType == "radio") {
			obj = window.document.getElementsByName(prefixRadio);
			for(i=0;i<obj.length;i++){
				if(obj[i].checked){
					checkedArray.push(obj[i].id.substring(prefixRadio.length));
				}
			}
			submitStringArray = checkedArray;
		}
		for(var i=0; i<submitStringArray.length; i++) {  //把要提交的checkbox属性填满放入submitObjectArray
			var tempObj = new Object();
			var thisObj = getObjectById(prefixDiv + submitStringArray[i]);
			var parentObj = getParentObject(thisObj);
			tempObj["childName"] = thisObj.getAttribute("text");
			tempObj["childId"] = thisObj.getAttribute("returnValue");
			tempObj["returnValue"] = thisObj.getAttribute("returnValue");
			tempObj["parentName"] = parentObj["parentName"];
			tempObj["parentId"] = parentObj["parentId"];
			//tempObj["detailedType"] = thisObj.detailedType; 
			
			submitObjectArray[submitObjectArray.length] = tempObj;
		}
		
		//测试一下
		/*
		var alertStr = "";
		for(var i=0; i<submitObjectArray.length; i++) {
			alertStr += submitObjectArray[i]["returnValue"] + prefixConcat_returnText + submitObjectArray[i]["childName"] + prefixConcat_returnText + submitObjectArray[i]["childId"] + prefixConcat_returnText + submitObjectArray[i]["parentName"] + prefixConcat_returnText + submitObjectArray[i]["parentId"] + "\n";
		}
		alert(alertStr);
		*/
		if(submitObjectArray.length == 0) {
			alert("请选择一条记录!");
			return false;
		}
		window.returnValue = submitObjectArray;
		if(outputType == "doubleClick") {
		window.close();
		} else {
		window.close();
		}
	}

/**********************结束表单提交相关方法 **********************/

</script>
</head>
<body onLoad="doOnLoad()" class="leftBody" >
<form name="form" method="post">
<h3>
	<span>
		<img title="刷新" onClick="location.reload();" src="<%=request.getContextPath()%>/images/icon/refresh_tree.gif" class="isHand">&nbsp;&nbsp;
		<img title="全部展开" onClick="expandAllNode()" src="<%=request.getContextPath()%>/images/icon/expand_all.gif" class="isHand">&nbsp;&nbsp;
		<img title="全部折叠" onClick="collapseAllNode();" src="<%=request.getContextPath()%>/images/icon/collapse_all.gif" class="isHand">
	</span>
</h3>

<div class="dtree">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" class="dtree">
	<tr>
		<td valign="top" nowrap>
			<div id="deeptree" class="deeptree"></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>