<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>选择参照</title>
</head>
<body class="referencePage">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr height="90%">
    <td>
		<iframe name="referenceContent" width="100%" height="100%" onload="javascript:referenceContent_onload();">
		
		</iframe>
	</td>
  </tr>
  <tr height="10%">
    <td align="center">
	    <input type="button" class="button_ellipse" name="button_ok" value="确定" onClick="ok_onClick();">&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="button" class="button_ellipse" name="button_cancel" value="取消" onClick="cancel_onClick();">
    </td>
  </tr>
</table>
<form id="form" name="form" method="post">
	<input type="hidden" id="rmCheckReturnValue" name="rmCheckReturnValue" value="">
	
</form>
</body>
</html>
<script type="text/javascript">
	var NAME_SPLICT_KEY = "#RM_SPLICT#";
	function ok_onClick(){  //确定
    	var rtObject = null;
		var thisInputObj = referenceContent.document.getElementsByName('checkbox_template');
		try {
			rtObject = document.frames['referenceContent'].toDoReadReference(thisInputObj);
		} catch(e) {
			rtObject = toDoReadReference(thisInputObj);
		}
		if(rtObject) {
	 	 	window.returnValue = rtObject;
			window.close();
		}
  	}
	function toDoReadReference(thisInputObj) {
		var ids = findSelections(thisInputObj,"id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return false;
		}
		var objKeys = ["displayName"];
		if(referenceContent.objKeys != null) {
			objKeys = referenceContent.objKeys;
		}
		var rtObject = new Object();
		if(referenceContent.document.form.referenceInputType.value == "checkbox"  && referenceContent.document.getElementById("rmCheckReturnValue") != null) {
			rtObject.realValue = referenceContent.document.getElementById("rmCheckReturnValue").value.replace(/(^,)|(,$)/g, "");
			var reg = new RegExp(NAME_SPLICT_KEY, "g");
			for(var k=0; k<objKeys.length; k++) {
				if(referenceContent.document.form.elements['rm_input' + (k+1)] != null) {
					rtObject[objKeys[k]] = referenceContent.document.form.elements['rm_input' + (k+1)].value.replace(reg, ",");
					rtObject[objKeys[k]] = rtObject[objKeys[k]].replace(/(^,)|(,$)/g, "");
				}
			}
		} else {
			rtObject.realValue = ids + "";
			var objs = {};
			for(var k=0; k<objKeys.length; k++) {
				objs[objKeys[k]] = [];
			}
			for(var i=0; i<ids.length; i++) {
				for(var j=0; j<thisInputObj.length; j++){  //循环checkbox组
					if(thisInputObj[j].value == ids[i]) {
						for(var k=0; k<objKeys.length; k++) {
							objs[objKeys[k]].push(thisInputObj[j].getAttribute(objKeys[k]));
						}
						break;
					}
				}
			}
			for(var k=0; k<objKeys.length; k++) {
				rtObject[objKeys[k]] = objs[objKeys[k]] + "";
			}
		}
		return rtObject;
	}

	function cancel_onClick(){  //取消
		window.close();
	}
	function findSelections(checkboxName, idName) {  //从列表中找出选中的id值列表
		var elementCheckbox = checkboxName;  //通过name取出所有的checkbox
		var number = 0;  //定义游标
		var ids = null;  //定义id值的数组
		for(var i=0;i<elementCheckbox.length;i++){  //循环checkbox组
			if(elementCheckbox[i].checked) {  //如果被选中
				number += 1;  //游标加1
				if(ids == null) {
					ids = new Array(0);
				}
				ids.push(elementCheckbox[i].value);  //加入选中的checkbox
			}
		}
		return ids;
	}
	var myObject = window.dialogArguments;
	if(myObject['inputArray'] != null && myObject['inputArray'].length > 0) {
		form.elements['rmCheckReturnValue'].value = myObject['inputArray'][0].value + ",";
		for(var i=1; i<myObject['inputArray'].length; i++) {
			form.insertAdjacentHTML("beforeEnd", "<input id='rm_input" + i + "' type='hidden' name='input" + i + "' />");
		}
		for(var i=1; i<myObject['inputArray'].length; i++) {
			if(myObject['inputArray'][i].value != null) {
				form.elements['rm_input' + i].value = (myObject['inputArray'][i].value.replace(/,/g, NAME_SPLICT_KEY) + NAME_SPLICT_KEY);
			}
		}
	}
	form.action = myObject.urlPath;
	form.target = "referenceContent";
	form.submit();

	function referenceContent_onload() {
		for(var i=1; i<myObject['inputArray'].length; i++) {
			referenceContent.form.insertAdjacentHTML("beforeEnd", "<input id='rm_input" + i + "' type='hidden' name='rm_input" + i + "' />");
		}
		for(var i=1; i<myObject['inputArray'].length; i++) {
			if(myObject['inputArray'][i].value != null) {
				referenceContent.form.elements['rm_input' + i].value = (myObject['inputArray'][i].value.replace(/,/g, NAME_SPLICT_KEY) + NAME_SPLICT_KEY);
			}
		}
		referenceContent.writeBackMapToForm();
		var tableListCss = null;
		var tables = referenceContent.document.getElementsByTagName("TABLE");
		for(var i=0; i<tables.length; i++) {
			if(tables[i].className == "listCss") {
				tableListCss = tables[i];
			}
		}
		//refreshCheckHidden_onLoad();
	}
</script>