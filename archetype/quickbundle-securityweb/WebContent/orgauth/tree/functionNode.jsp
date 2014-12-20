<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<html>
<head>
<title>rm-based architecture project</title>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<jsp:include page="/jsp/support/deeptree/include/globalDeepTree.jsp" />
<script type="text/javascript">

	if(rootXmlSource == null || rootXmlSource == "") {
		rootXmlSource = "<%=request.getContextPath() + "/orgauth/tree/functionNodeTreeData.jsp?cmd=manageFunctionNodeData&lazy_init=1&is_href=1"%>";
		//xmlData.xml?
	}

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
	var alertStr = "";
	for(var i=0; i<submitObjectArray.length; i++) {
		alertStr += submitObjectArray[i]["returnValue"] + prefixConcat_returnText + submitObjectArray[i]["childName"] + prefixConcat_returnText + submitObjectArray[i]["childId"] + prefixConcat_returnText + submitObjectArray[i]["parentName"] + prefixConcat_returnText + submitObjectArray[i]["parentId"] + "\n";
	}
	//alert(alertStr);
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
<body onLoad="doOnLoad()" topmargin=0 leftmargin=0 >

<form name="form" method="post">
<table border="0" width="100%" cellspacing="0" cellpadding="0">

  <tr> 
     <td width="300" valign="top" align="left">&nbsp;&nbsp;&nbsp;&nbsp; 
   
		<!--树开始-->    
		<div name="deeptree" id="deeptree" class="deeptree"></div>
		<!--树结束-->
    </td>
     <td width="*" valign="top" align="left">
     	<table>
     		<tr><td>
				<iframe frameborder="0" width="800" height="800" name="operation_frame" src="<%
					if(RmProjectHelper.getCommonServiceInstance().doQueryForInt("select count(*) from RM_FUNCTION_NODE") == 0) {
						out.print(request.getContextPath() + "/orgauth/rmfunctionnode/insertRmFunctionNode.jsp?parent_total_code=");
					}else{
						out.print(request.getContextPath() +"/RmFunctionNodeAction.do?cmd=queryAll");
					}
				%>"></iframe>
     		</td></tr>
     	</table>
    </td>
  </tr>
</table>

</form>
</body>
</html>