<%@page contentType="text/html;charset=UTF-8" language="java"%><%@page import="org.quickbundle.config.RmConfig"%><%@page import="org.quickbundle.tools.helper.RmStringHelper"%><%
	String rootXmlSource = request.getParameter("rootXmlSource");
	if(RmStringHelper.checkEmpty(rootXmlSource)){
		rootXmlSource = (String)request.getAttribute("rootXmlSource");
	}
	if(rootXmlSource == null || rootXmlSource.length() == 0) {
		rootXmlSource = "";
	}
	String inputType = request.getParameter("inputType");
	if(inputType == null || inputType.length() == 0 || (!"noInput".equals(inputType) && !"checkbox".equals(inputType) && !"radio".equals(inputType))) {
		inputType = "noInput";		
	}
	String outputType = request.getParameter("outputType");
	if(outputType == null || outputType.length() == 0 || (!"doubleClick".equals(outputType) && !"writeToPage".equals(outputType))) {
		outputType = "writeToPage";		
	}
	String submitType = request.getParameter("submitType");
	if(submitType == null || submitType.length() == 0 || (!"parentPriority".equals(submitType) && !"submitAll".equals(submitType))) {
		submitType = "submitAll";
	}
	String nodeRelationType = request.getParameter("nodeRelationType");
	if(nodeRelationType == null || nodeRelationType.length() == 0 || (!"hasRelation".equals(nodeRelationType) && !"noRelation".equals(nodeRelationType))) {
		nodeRelationType = "noRelation";
	}
	String returnTextType = request.getParameter("returnTextType");
	if(returnTextType == null || returnTextType.length() == 0 || (!"plugParent".equals(returnTextType) && !"self".equals(returnTextType) && !"plugRoot".equals(returnTextType))) {
		returnTextType = "self";
	}
	String expandType = request.getParameter("expandType");
	if(expandType == null || expandType.length() == 0 || (!"single".equals(expandType) && !"multiple".equals(expandType))) {
		expandType = "multiple";
	}
	String xmlSourceType = request.getParameter("xmlSourceType");
	if(xmlSourceType == null || xmlSourceType.length() == 0  || (!"nodeCode".equals(xmlSourceType) && !"urlPath".equals(xmlSourceType))) {
		xmlSourceType = "urlPath";
	}
	String enableCookie = request.getParameter("enableCookie");
	if(enableCookie == null || enableCookie.length() == 0  || (!"true".equals(enableCookie) && !"false".equals(enableCookie))) {
		enableCookie = "true";
	}
	String enableDrag = request.getParameter("enableDrag");
	if(enableDrag == null || enableDrag.length() == 0  || (!"true".equals(enableDrag) && !"false".equals(enableDrag))) {
		enableDrag = "false";
	}
	String treeUniqueName = request.getParameter("treeUniqueName");
	if(treeUniqueName == null || treeUniqueName.length() == 0) {
		treeUniqueName = "";
	}
	String defaultFocusNode = request.getParameter("defaultFocusNode");
	if(defaultFocusNode == null || defaultFocusNode.length() == 0) {
		defaultFocusNode = "";
	}
	String defaultSelectedNodes = request.getParameter("defaultSelectedNodes");
	if(defaultSelectedNodes == null || defaultSelectedNodes.length() == 0) {
		defaultSelectedNodes = "";
	}
	String defaultNotSelectedNodes = request.getParameter("defaultNotSelectedNodes");
	if(defaultNotSelectedNodes == null || defaultNotSelectedNodes.length() == 0) {
		defaultNotSelectedNodes = "";
	}
	String defaultSelectedNodesValue = request.getParameter("defaultSelectedNodesValue");
	if(defaultSelectedNodesValue == null || defaultSelectedNodesValue.length() == 0) {
		defaultSelectedNodesValue = "";
	}
	String defaultNotSelectedNodesValue = request.getParameter("defaultNotSelectedNodesValue");
	if(defaultNotSelectedNodesValue == null || defaultNotSelectedNodesValue.length() == 0) {
		defaultNotSelectedNodesValue = "";
	}
	String enableStatusText = request.getParameter("enableStatusText");
	if(enableStatusText == null || enableStatusText.length() == 0  || (!"true".equals(enableStatusText) && !"false".equals(enableStatusText))) {
		enableStatusText = "false";
	}
%><script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery/jquery.transform.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/support/deeptree/deeptree.js"></script>
<script type="text/javascript">
	var dir_base = "<%=request.getContextPath()%>";
	var systemDebugMode = <%=RmConfig.getSingleton().isSystemDebugMode() ? "true" : "false"%>;

	//定义全局参数
	var inputType = "<%=inputType%>";  //输入方式, 包括noInput, checkbox, radio
	var outputType = "<%=outputType%>";  //输出方式, 包括doubleClick， writeToPage
	var submitType = "<%=submitType%>";  //提交策略, 包括parentPriority, submitAll
	var nodeRelationType = "<%=nodeRelationType%>";  //节点的关联策略, 包括hasRelation, noRelation
	var returnTextType = "<%=returnTextType%>";  //返回文本策略, 包括加上plugParent, self, plugRoot
	var expandType = <%="single".equals(expandType)?true:false%>;  //展开策略, 包括single, multiple
	var xmlSourceType = "<%=xmlSourceType%>";  //xml数据源获得类型, 包括nodeCode, urlPath
	var enableCookie = <%=enableCookie%>;  //是否启用cookie, 对为默认展开的节点不生效
	var enableDrag = <%=enableDrag%>;  //是否启用拖拽功能
	var treeUniqueName = "<%=treeUniqueName%>";  //树的唯一标识
	var rootXmlSource = "<%=rootXmlSource%>";  //根xml源文件, 转义影射：&-->%26, =-->%3D
	var defaultFocusNode = "<%=defaultFocusNode%>";  //默认聚焦的节点
	var defaultSelectedNodes = "<%=defaultSelectedNodes%>";  //默认选中的节点，表示id
	var defaultNotSelectedNodes = "<%=defaultNotSelectedNodes%>";  //默认不选中的节点，表示id
	var defaultSelectedNodesValue = "<%=defaultSelectedNodesValue%>";  //默认选中的节点，表示returnValue
	var defaultNotSelectedNodesValue = "<%=defaultNotSelectedNodesValue%>";  //默认不选中的节点，表示returnValue
	var enableStatusText = <%=enableStatusText%>;  //是否在status显示选中的节点文本


	//定义全局变量
	var gradeMapChild = new Object(); //本对象中保存每个节点的子节点列表
	var gradeMapParent = new Object(); //保存每个节点的父节点
	var rootNode = new Array(); //定义根节点
	var indexObject = 0;  //定义全局对象索引
	var currentObjectId_cookie = null; //用于cookie处理的临时变量
	var currentObjectId = null; //用于动态增删改的临时变量
	var currentItem_Drag = null; //用于拖拽的临时变量
	
	var currentObjectId_defaultOpen = null; //定义自动展开的临时变量, 200611270940add
	
	//定义全局标志符
	var prefixDiv = "deeptree_div";
	var prefixTreeImg = "deeptree_treeImg";
	var prefixLogoImg = "deeptree_logoImg";
	var prefixCheckbox = "deeptree_checkbox";
	var prefixRadio = "deeptree_radio";
	var prefixSpan = "deeptree_span";
	var prefixConcat_parent = "P";  //与父节点连接符
	var prefixConcat_xsltNumber = "-";  //原始id与xslt中连接序号符
	var prefixConcat_index = ">";  //xslt计数器与全局对象索引连接符
	var prefixTreeNode = "TreeNode";  //树类型的tabName
	var prefixRootMapCode = "";  //根节点在全局Map中的值
	var prefixConcat_returnText = "-->";  //全局对象索引连接符
	var cookieEnable = "1";  //cookie中启用状态的表示
	var prefixConcat_cookie = "; ";  //cookie分隔符

	//定义全局路径
	var treeImagePath = "<%=request.getContextPath()%>/jsp/support/deeptree/image";  //树图标路径
	var xslPath = "<%=request.getContextPath()%>/jsp/support/deeptree/deeptree_xsl.jsp?inputType=" + inputType + "&treeImagePath=" + treeImagePath;  //xslt文件相对路径


	function doOnLoad() {
		//拖拽
		if(enableDrag) {
			document.attachEvent("onmousedown", _dt_itemMouseDown);
			document.attachEvent("onmousemove", _dt_itemMouseMove);
			document.attachEvent("onmouseup", _dt_itemMouseUp);
			document.attachEvent("onmouseover", _dt_itemMouseOver);
			document.attachEvent("onmouseout", _dt_itemMouseOut);
		}
		try {
			var deeptree = document.getElementById("deeptree");
			if(deeptree != null) {
				if(enableCookie) {
					applyCookie();
				} else {
					deeptree.autoExpand();
				}
			}
		} catch(e) {
		}
	}
	
		
	//TODO
	function expandAllNode() {
		recursiveExpandChildNode_deep(prefixRootMapCode);
	}
	
	function recursiveExpandChildNode_deep(thisId) {
		var thisDiv = getObjectById(prefixDiv + thisId);
		if(thisDiv != null) {  //如果非空，则展开并递归
			currentObjectId_cookie = thisDiv.id;
			document.getElementById("deeptree").ExpandObject();
		}
		if(gradeMapChild[thisId] != null) {
			for(var i=0; i<gradeMapChild[thisId].length; i++) {
				recursiveExpandChildNode_deep(gradeMapChild[thisId][i]);
			}
		}
	}
	
	function collapseAllNode() {
		if(gradeMapChild[prefixRootMapCode] != null) {
			for(var i=0; i<gradeMapChild[prefixRootMapCode].length; i++) {
				var thisDiv = getObjectById(prefixDiv + gradeMapChild[prefixRootMapCode][i]);
				if(thisDiv != null) {  //如果非空，则展开并递归
					currentObjectId_cookie = thisDiv.id;
					document.getElementById("deeptree").CollapseObject();
				}
			}
		}
	}

	function applyCookie() {
		var arrayCookie = getArrayFromCookie(cookieEnable);
		arrayCookie.push(prefixRootMapCode);
		recursiveApplyCookie(prefixRootMapCode, arrayCookie);
	}

	function recursiveApplyCookie(key, arrayCookie) {
		if(!arrayHasString(arrayCookie, key)) {
			return false;
		}
		var thisDiv = getObjectById(prefixDiv + key);
		if(thisDiv != null) {  //如果非空，则展开并递归
			currentObjectId_cookie = thisDiv.id;
			document.getElementById("deeptree").ExpandObject();
		}
		if(thisDiv != null || key == prefixRootMapCode) {
			var thisDivChild = gradeMapChild[key];
			if(thisDivChild == undefined || thisDivChild == null)
				return false;
			for(var a=0; a<thisDivChild.length; a++) {
				recursiveApplyCookie(thisDivChild[a], arrayCookie);
			}
		}
	}

	function getArrayFromCookie(value) {
		var arrayOpenInfo = document.cookie.split(prefixConcat_cookie);
		var rtArray = new Array();
		for(var i=0; i<arrayOpenInfo.length; i++) {
			if(arrayOpenInfo[i].length > value.length + 1) {
				if(arrayOpenInfo[i].substring(arrayOpenInfo[i].length - value.length) == value) {
					rtArray.push(arrayOpenInfo[i].substring(0, arrayOpenInfo[i].length - value.length - 1));
				}
			}
		}
		//处理树的前缀
		for(var i=0; i<rtArray.length; i++) {
			rtArray[i] = rtArray[i].substring(treeUniqueName.length);
		}
		return rtArray;
	}

	//全选/不选所有的checkbox
 	function setAllCheckbox(selectedStatus) {
		obj = window.document.getElementsByName(prefixCheckbox);
		for(i=0;i<obj.length;i++){
			obj[i].checked = selectedStatus;
		}
	}

/**********************工具性方法 **********************/
	function arrayHasString(tempArray, myString) {  //判断tempArray是否包含myString
		var returnMyValue = false;
		for(var i=0; i<tempArray.length; i++) {
			if(tempArray[i] == myString) {
				returnMyValue = true;
				break;
			}
		}
		return returnMyValue;
	}
	
	function getArrayDeleteString(tempArray, myString) {
		var count = 0;
		var rtArray = new Array();
		if(tempArray == null) {
			return rtArray;
		}
		for(var i=0; i<tempArray.length; i++) {
			if(tempArray[i] != myString) {
				var tempStr = tempArray[i];
				rtArray.push(tempStr);
			}
		}
		return rtArray;
	}
	
	function getObjectById(id) {  //为了能让htc中的函数得到对象
		if(document.getElementById != undefined) {
			return document.getElementById(id);			
		} else {
			alert("当前浏览器不支持用getElementById方法取对象！");
			return null;
		}
	}
	
	function getTreeNode(thisObj) {  //获得当前节点div及其后代节点
		if(thisObj.getAttribute("hasChild") == "1") {
			//TODO
			return getOuterHtml(thisObj) + thisObj.nextSibling.outerHTML;
		} else {
			return getOuterHtml(thisObj);
		}
	}
	function getOuterHtml(thisObj){
		var div = document.createElement('div');
		//将复制的elemCopy插入到空div节点中
		div.appendChild(thisObj);
		//返回div的HTML内容
		return div.innerHTML;
	};
	
	function recursiveCloneNode(thisObj) {  //递归的克隆html节点
		if(thisObj == undefined || thisObj == null)
			return "";
		var returnObj = thisObj.cloneNode();
		for(var a=0; a<thisObj.childNodes.length; a++) {
			returnObj.appendChild(recursiveCloneNode(thisObj.childNodes[a]));
		}
		return returnObj;
	}
	
	function _dt_createMoveDIV(obj){
		var d = document.createElement("DIV");
		//cursor:pointer;
		d.innerHTML = '<div style="white-space:nowrap;background:blue;color:white;border:2px solid gray;white-font-size:9pt;position:absolute;top:0px;0px;display:block;">'
			+obj.innerText
			+ '</div>';
		d = d.childNodes[0];
		document.body.appendChild(d);
		return d;
	}
	
	function getHiddenValueByEvent(eventId) {  //从事件对象获取某个对象的隐藏值
		var mapId = eventId;
		if(eventId.length > prefixDiv.length && eventId.substring(0, prefixDiv.length) == prefixDiv) {
			mapId = eventId.substring(prefixDiv.length);
		} else if(eventId.length > prefixTreeImg.length && eventId.substring(0, prefixTreeImg.length) == prefixTreeImg) {
			mapId = eventId.substring(prefixTreeImg.length);
		} else if(eventId.length > prefixLogoImg.length && eventId.substring(0, prefixLogoImg.length) == prefixLogoImg) {
			mapId = eventId.substring(prefixLogoImg.length);
		} else if(eventId.length > prefixCheckbox.length && eventId.substring(0, prefixCheckbox.length) == prefixCheckbox) {
			mapId = eventId.substring(prefixCheckbox.length);
		} else if(eventId.length > prefixRadio.length && eventId.substring(0, prefixRadio.length) == prefixRadio) {
			mapId = eventId.substring(prefixRadio.length);
		} else if(eventId.length > prefixSpan.length && eventId.substring(0, prefixSpan.length) == prefixSpan) {
			mapId = eventId.substring(prefixSpan.length);
		}
		var thisDiv = getObjectById(prefixDiv + mapId);
		return thisDiv.childNodes[0];
	}
	
	function getMapIdByEvent(eventId) {  //从事件对象获取某个对象的mapId
		var mapId = eventId;
		if(eventId.length > prefixDiv.length && eventId.substring(0, prefixDiv.length) == prefixDiv) {
			mapId = eventId.substring(prefixDiv.length);
		} else if(eventId.length > prefixTreeImg.length && eventId.substring(0, prefixTreeImg.length) == prefixTreeImg) {
			mapId = eventId.substring(prefixTreeImg.length);
		} else if(eventId.length > prefixLogoImg.length && eventId.substring(0, prefixLogoImg.length) == prefixLogoImg) {
			mapId = eventId.substring(prefixLogoImg.length);
		} else if(eventId.length > prefixCheckbox.length && eventId.substring(0, prefixCheckbox.length) == prefixCheckbox) {
			mapId = eventId.substring(prefixCheckbox.length);
		} else if(eventId.length > prefixRadio.length && eventId.substring(0, prefixRadio.length) == prefixRadio) {
			mapId = eventId.substring(prefixRadio.length);
		} else if(eventId.length > prefixSpan.length && eventId.substring(0, prefixSpan.length) == prefixSpan) {
			mapId = eventId.substring(prefixSpan.length);
		}
		return mapId;
	}
/**********************工具性方法 **********************/

/**********************开始表单提交相关方法 **********************/

function clearChild(checkedArray) {  //把父结点已经提交过的节点过滤掉，返回列表
	var submitStringArray = new Array(0);
	for(var i=0; i<checkedArray.length; i++) {
		var parentString = gradeMapParent[checkedArray[i]];
		if(parentString != undefined && !arrayHasString(checkedArray,parentString)) {
			var tempStr = checkedArray[i];
			submitStringArray.push(tempStr);
		}
	}
	return submitStringArray;
}

function filterHidden(checkedArray) {  //把节点中display:none的节点过滤掉
	var submitStringArray = new Array(0);
	for(var i=0; i<checkedArray.length; i++) {
		var thisCheckbox = getObjectById(prefixCheckbox + checkedArray[i]);
		if(thisCheckbox.style.display != "none") {
			var tempStr = checkedArray[i];
			submitStringArray.push(tempStr);
		}
	}
	return submitStringArray;
}

function getParentObject(childObj){  //把childObj的父节点的name，id，type封装到Object，返回
	var parentString = prefixDiv + gradeMapParent[childObj.id.substring(prefixDiv.length)];
	var returnObj = new Object();
	if(parentString == undefined || parentString == null ) {
		returnObj["parentName"] = null;
		returnObj["parentId"] = null;
		returnObj["parentType"] = null;
	} else if( parentString == prefixDiv + prefixRootMapCode ) {
		var tempObj = getObjectById(parentString);
		returnObj["parentName"] = getParentNameList(childObj, tempObj);
		returnObj["parentId"] = null;
		returnObj["parentType"] = null;
	} else {
		var tempObj = getObjectById(parentString);
		returnObj["parentName"] = getParentNameList(childObj, tempObj);
		returnObj["parentId"] = tempObj.getAttribute("returnValue");
		returnObj["parentType"] = tempObj.getAttribute("detailedType");
	}
	return returnObj;
}
	
function getParentNameList(childObj,parentObj){  //返回childObj往顶递归到根节点的字符串集
	var returnStr = "";
	if(returnTextType == "self") {
		return childObj.getAttribute("text");
	}
	if(parentObj != null && ( (returnTextType == "plugRoot") || (returnTextType == "plugParent" && !arrayHasString(rootNode, parentObj.id.substring(prefixDiv.length))) )) {
		var tempParentObj = getObjectById(prefixDiv + gradeMapParent[parentObj.id.substring(prefixDiv.length)]);
		returnStr = getParentNameList(parentObj,tempParentObj) + prefixConcat_returnText + childObj.getAttribute("text");				
	} else {
		returnStr = childObj.getAttribute("text");
	}
	return returnStr;
}
/**********************结束表单提交相关方法 **********************/

</script>
<script type="text/javascript">
	var expDays = 7;
	var exp = new Date(); 
	exp.setTime(exp.getTime() + (expDays*24*60*60*1000));

	function getCookieVal (offset) { 
		var endstr = document.cookie.indexOf (";", offset); 
		if (endstr == -1) 
			endstr = document.cookie.length; 
		return unescape(document.cookie.substring(offset, endstr));
	}
	
	function GetCookie (name) { //读取cookie中的信息
		name = treeUniqueName + name;  //为每棵树起个名字
		var arg = name + "="; 
		var alen = arg.length; 
		var clen = document.cookie.length; 
		var i = 0; 
		while (i < clen) { 
			var j = i + alen; 
			if (document.cookie.substring(i, j) == arg) 
				return getCookieVal (j); 
			i = document.cookie.indexOf(" ", i) + 1; 
			if (i == 0) 
				break; 
		} 
		return null;
	}
	function SetCookie (name, value) {   //设置Cookie内容
		name = treeUniqueName + name;  //为每棵树起个名字
		var argv = SetCookie.arguments; 
		var argc = SetCookie.arguments.length; 
		var expires = (argc > 2) ? argv[2] : null; 
		var path = (argc > 3) ? argv[3] : null; 
		var domain = (argc > 4) ? argv[4] : null; 
		var secure = (argc > 5) ? argv[5] : false; 
		document.cookie = name + "=" + escape (value) + 
		((expires == null) ? "" : ("; expires=" + expires.toGMTString())) + 
		((path == null) ? "" : ("; path=" + path)) + 
		((domain == null) ? "" : ("; domain=" + domain)) + 
		((secure == true) ? "; secure" : "");
	}
	function DeleteCookie (name) {
		name = treeUniqueName + name;  //为每棵树起个名字
		//var exp = new Date(); 
		//exp.setTime (exp.getTime() - 1); 
		// 历史记录
		var cval = GetCookie (name); 
		document.cookie = name + "=" + cval + "; expires=" + "Fri, 02-Jan-1970 00:00:00 GMT";  //exp.toGMTString();
	}
</script>

<script type="text/javascript">

function insertTreeNode(mapId, dataObj) {  //标准对外接口,mapId是兄弟的id
	currentObjectId = prefixSpan + mapId;
	if(dataObj.dataType == "text") {  //如果id本身不是一个div对象
		var myXml = document.all.xmlDataIsland.documentElement;
		var tempTreeNode = myXml.childNodes[0];
		if(dataObj.id != undefined)
			tempTreeNode.attributes[0].value = dataObj.id;
		if(dataObj.text != undefined)
			tempTreeNode.attributes[1].value = dataObj.text;
		if(dataObj.hasChild != undefined)
			tempTreeNode.attributes[2].value = dataObj.hasChild;
		if(dataObj.xmlSource != undefined)
			tempTreeNode.attributes[3].value = dataObj.xmlSource;
	} else if(dataObj.dataType == "outerHTML"){
		currentItem_Drag = dataObj.outerHTML;
	}
	document.getElementById("deeptree").getXmlDataIslandValue();
	currentItem_Drag = null; // 销毁临时变量
}

function deleteTreeNode(mapId, notClearMap) {  //标准对外接口
	var thisDiv = getObjectById(prefixDiv + mapId);
	var toDeletedObj = thisDiv;
	if(thisDiv.hasChild == "1") {
		var toDeletedObjChildren = thisDiv.nextSibling;
		toDeletedObjChildren.removeNode(true);
	}
	toDeletedObj.removeNode(true);
	if(!notClearMap == "notClearMap")
		recursiveDeleteNodeFromMap(mapId);
}

function recursiveDeleteNodeFromMap(mapId) {
	gradeMapParent[mapId] = undefined;
	var tempChildren = gradeMapChild[mapId];
	if(tempChildren != undefined && tempChildren != null) {
		for(var i=0; i<tempChildren.length; i++) {
			recursiveDeleteNodeFromMap(tempChildren[i]);
		}
	}
	gradeMapChild[mapId] = undefined;
}

function updateTreeNode(mapId, text) {  //标准对外接口
	var thisSpan = getObjectById(prefixSpan + mapId);
	thisSpan.innerHTML = text;
}

function getCurrentObject() {  //取出当前的span
	if(currentObjectId != null)
		return getObjectById(currentObjectId);
	else 
		return null;
}

function insertTreeNode_onClick() {
	var thisObj = getCurrentObject();
	if(thisObj != null) {
		var dataObj = new Object();
		dataObj.dataType = "text";
		dataObj.id = "1";
		dataObj.text = form.text_insert.value;
		dataObj.hasChild = "0";
		dataObj.xmlSource = "xmlData2.xml";
		insertTreeNode(thisObj.id.substring(prefixSpan.length), dataObj);		
	}
}


function deleteTreeNode_onClick() {
	var thisObj = getCurrentObject();
	if(thisObj != null)
		deleteTreeNode(thisObj.id.substring(prefixSpan.length));
}


function updateTreeNode_onClickText() {
	var thisObj = getCurrentObject();
	if(thisObj != null) {
		form.text_update.value = thisObj.parentNode.text;
	}
}

function updateTreeNode_onClick() {
	var thisObj = getCurrentObject();
	if(thisObj != null)
		updateTreeNode(thisObj.id.substring(prefixSpan.length), form.text_update.value);
}
</script>

<xml id="xmlDataIsland">
<?xml version="1.0" encoding="UTF-8"?>
<Trees>
	<TreeNode id="1" 
		text="未指定名称" 
		hasChild="0" 
		xmlSource="xmlData2.xml" 
		defaultOpen="0" 
		logoImagePath="" 
		statusFlag="" 
		title="" 
		hrefPath="" 
		target="" 
		dbClick="" 
		orderStr="" 
		returnValue="" 
		isSelected="" 
		indeterminate="" 
		thisType="" 
		detailedType="" 
		isSubmit="" 
		parentId="" 
		childIds="">
	</TreeNode>
</Trees>
</xml>

<script type="text/javascript">

function dragTreeNodeToBefore(fromMapId, toMapId) {  //把fromMapId拖拽到toMapId前边
	var fromCloneOuterHTML = getTreeNode(getObjectById(prefixDiv + fromMapId));
	var dataObj = new Object();
	dataObj.dataType = "outerHTML";
	dataObj.outerHTML = fromCloneOuterHTML;
	insertTreeNode(toMapId, dataObj);
	deleteTreeNode(fromMapId, "notClearMap");
}

function isAncestorOfDescendant(ancestorMapId, descendantMapId) {
	if(gradeMapParent[descendantMapId] == undefined) {
		return false;
	} else if(gradeMapParent[descendantMapId] == ancestorMapId) {
		return true;
	} else {
		return isAncestorOfDescendant(ancestorMapId, gradeMapParent[descendantMapId]);
	}
}

function checkObjectDrag(checkType, e) {
	e = window.event || e;
	var obj = e.srcElement || e.target;
	if(checkType == "mouseDown") {  //校验按下鼠标
		if(e.button != 1)  //左键有效
			return false;
		if(obj.tagName != "SPAN")  //Span有效
			return false;
		if(gradeMapParent[obj.id.substring(prefixSpan.length)] == undefined)  //必须在gradeMapParent中有效
			return false;
		if(arrayHasString(rootNode, obj.id.substring(prefixSpan.length))){  //不能移动根节点
			return false;
		}
		return true;
	} else if(checkType == "mouseMove") {  //校验鼠标移动
		return true;
	} else if(checkType == "mouseUp") {  //校验放开鼠标
		return true;
	} else if(checkType == "mouseOver") {  //校验鼠标移动到某个对象上
		if(_dt_itemMouseDown.moveObject == null)
			return false;
		if(_dt_itemMouseDown.downObject == null) {
			return false;
		}
		var downObj = _dt_itemMouseDown.downObject;
		if(obj.tagName != "SPAN")
			return false;
		if(gradeMapParent[obj.id.substring(prefixSpan.length)] == undefined) {
			return false;
		}
		if(arrayHasString(rootNode, obj.id.substring(prefixSpan.length))){
			return false;
		}
		if(downObj.id == obj.id) {
			return false;
		}
		if(isAncestorOfDescendant(downObj.id.substring(prefixSpan.length), obj.id.substring(prefixSpan.length))) {
			return false;
		}
		return true;
	} else if(checkType == "mouseOut") {  //校验鼠标离开某个对象
		return true;
	} else {
		return false;
	}
}

function _dt_itemMouseDown(){
	e = window.event || e;
	var obj = e.srcElement || e.target;
	if(!checkObjectDrag("mouseDown", e)) {
		return false;
	}
	if(obj.moveObject == null){
		obj.moveObject = _dt_createMoveDIV(obj);
	}
	_dt_itemMouseDown.button = e.button;  //自己定义的属性
	_dt_itemMouseDown.downObject = obj;
	_dt_itemMouseDown.moveObject = obj.moveObject;
}
function _dt_createMoveDIV(obj){
	var d = document.createElement("DIV");
	//cursor:pointer;
	d.innerHTML = '<div style="white-space:nowrap;background:blue;color:white;border:2px solid gray;white-font-size:9pt;position:absolute;top:0px;0px;display:block;">'
		+obj.innerText
		+ '</div>';
	d = d.childNodes[0];
	document.body.appendChild(d);
	return d;
}
function _dt_moveObjectWithMouse(obj, e){
	var top = e.clientY;
	var left = e.clientX;
	top -= 24;
	left -= obj.clientWidth/2;
	obj.style.posTop = top;
	obj.style.posLeft = left;
}
function _dt_itemMouseMove(){
	if(!checkObjectDrag("mouseMove", event)) {
		return false;
	}
	if(_dt_itemMouseDown.moveObject != null){
		document.attachEvent("onselectstart", _dt_itemSelectStart);  //取消选择文本
		_dt_itemMouseDown.moveObject.style.display = "block";
		_dt_moveObjectWithMouse(_dt_itemMouseDown.moveObject, event);
	}
}
function _dt_itemMouseUp(){
	if(!checkObjectDrag("mouseUp", event)) {
		return false;
	}
	document.detachEvent("onselectstart", _dt_itemSelectStart);
	if(_dt_itemMouseDown.moveObject != null){
		_dt_itemMouseDown.moveObject.style.display = "none";
		if(_dt_itemMouseOver.overObject != null){
			if(_dt_itemMouseDown.downObject.id != _dt_itemMouseOver.overObject.id){
				if(_dt_itemMouseDown.button == 1){
					dragTreeNodeToBefore(_dt_itemMouseDown.downObject.id.substring(prefixSpan.length), _dt_itemMouseOver.overObject.id.substring(prefixSpan.length));
				}
			}
			_dt_restoreBackground(_dt_itemMouseOver.overObject);
			_dt_itemMouseOver.overObject = null;
		}
		_dt_itemMouseDown.moveObject = null;
	}
	_dt_itemMouseDown.downObject = null;  //如果放开鼠标, 则清除对象
}
function _dt_changeBackground(obj){
	obj.className = "dragOver";
}
function _dt_restoreBackground(obj){
	if(obj == null)
		return  false;
	obj.className = "MouseOver";
}
function _dt_itemMouseOver(e){
	e = window.event || e;
	var obj = e.srcElement || e.target;
	if(!checkObjectDrag("mouseOver", event)) {
		return false;
	}
	_dt_itemMouseOver.overObject = obj;  //自己定义的属性
	_dt_changeBackground(obj);
}
function _dt_itemMouseOut(){
	if(!checkObjectDrag("mouseOut", event)) {
		return false;
	}
	if(_dt_itemMouseOver.overObject != null){
		_dt_restoreBackground(_dt_itemMouseOver.overObject);
		_dt_itemMouseOver.overObject = null;
	}
}
function _dt_itemSelectStart(){
	return false;
}
if(!systemDebugMode) {
	jQuery(document)[0].oncontextmenu = function() {return false;}
}
</script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/jsp/support/deeptree/deeptree.css">