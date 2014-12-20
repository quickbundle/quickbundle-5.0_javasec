/** @deprecated */
/************wangjian and Ajax script****/
var http_request = false;
//默认为异步
function send_request(url, p) {//初始化、指定处理函数、发送请求的函数
	http_request = false;
	
	//开始初始化XMLHttpRequest对象
	if(window.XMLHttpRequest) { //Mozilla 浏览器
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {//设置MiME类别
			http_request.overrideMimeType("text/xml");
		}
	}
	
	else if (window.ActiveXObject) { // IE浏览器
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	if (!http_request) { // 异常，创建对象实例失败
		window.alert("不能创建XMLHttpRequest对象实例.");
		return false;
	}

  	
	http_request.onreadystatechange = p;
	// 确定发送请求的方式和URL以及是否同步执行下段代码
	http_request.open("GET", url, true);
	http_request.send(null);
	/**
		function createXMLHttpRequest(){
		if (window.XMLHttpRequest){
	   		http_request = new XMLHttpRequest();
	    } else if (window.ActiveXObject){
	   		http_request = new ActiveXObject("Microsoft.XMLHTTP");
	  	}	
	}
	*/
}
//同步执行(或异步sync=true，异步，sync=false 同步)
function send_request(url, p,sync) {//初始化、指定处理函数、发送请求的函数
	http_request = false;
	
	//开始初始化XMLHttpRequest对象
	if(window.XMLHttpRequest) { //Mozilla 浏览器
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {//设置MiME类别
			http_request.overrideMimeType("text/xml");
		}
	}
	
	else if (window.ActiveXObject) { // IE浏览器
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {}
		}
	}
	if (!http_request) { // 异常，创建对象实例失败
		window.alert("不能创建XMLHttpRequest对象实例.");
		return false;
	}

  	
	http_request.onreadystatechange = p;
	// 确定发送请求的方式和URL以及是否同步执行下段代码
	http_request.open("GET", url, sync);
	http_request.send(null);
	/**
		function createXMLHttpRequest(){
		if (window.XMLHttpRequest){
	   		http_request = new XMLHttpRequest();
	    } else if (window.ActiveXObject){
	   		http_request = new ActiveXObject("Microsoft.XMLHTTP");
	  	}	
	}
	*/
}
function loadXML(responseText) {

	var xmlobj = new ActiveXObject("Microsoft.XMLDOM");
        xmlobj.loadXML(responseText);                

		return xmlobj;
    }
	
  /********wangjian add Ajax script end******/


var rmRequest_synchronized;
var rmXpath_synchronized;
var rmReturnXml_synchronized;

function getMineValue(strsql, xpath) { 
	rmRequest_synchronized = null;
	rmXpath_synchronized = null;
	rmReturnXml_synchronized = null;
	if(xpath != null) {
		rmXpath_synchronized = xpath;
	}
	return retrieveUrl_synchronized(dir_base + "/jsp/support/ajax/mineDataXml.jsp?strsql=" + strsql);
	}

  
	function retrieveUrl_synchronized(url) {
		try {
		    if (url != null && url != "") {
	      if (window.XMLHttpRequest) { // Non-IE browsers
	        rmRequest_synchronized = new XMLHttpRequest();
	        rmRequest_synchronized.onreadystatechange = processStateChange_synchronized;
	        try {
	          rmRequest_synchronized.open("get", convertURL(url), false);
	        } catch (e) {
	          alert(e);
	        }
	        rmRequest_synchronized.send(null);
	      } else if (window.ActiveXObject) { // IE
	        rmRequest_synchronized = new ActiveXObject("Microsoft.XMLHTTP");
	        if (rmRequest_synchronized) {
	          rmRequest_synchronized.onreadystatechange = processStateChange_synchronized;
	          rmRequest_synchronized.open("get", url, false);
	          rmRequest_synchronized.send();
	        }
	      }
	    }
	} catch(e) {
		alert(e.message);	
	}
	return rmReturnXml_synchronized;
}
function convertURL(url) {   
	//获取时间戳    
	var timstamp = (new Date()).valueOf();   
	//将时间戳信息拼接到url上   
	//url = "AJAXServer"    
	if (url.indexOf("?") >= 0) {   
		url = url + "&t=" + timstamp;   
	} else {   
		url = url + "?t=" + timstamp;    
		}   
		return url;   
	} 
  function processStateChange_synchronized() {
    if (rmRequest_synchronized.readyState == 4) { 
      if (rmRequest_synchronized.status == 200) {
        rmXml = rmRequest_synchronized.responseXML;
        if(rmXpath_synchronized == null) {
        	rmReturnXml_synchronized = rmXml;
        } else {
			rmReturnXml_synchronized = rmXml.documentElement.selectNodes(rmXpath_synchronized);
        }
      } else {
        alert("Problem:" + rmRequest_synchronized.statusText);
      }
    }
  }
  
  	var rmXmlHttpRequest;
	
	function callMineValue(strsql, processFunction_callback, submitType) { 
		return retrieveUrl_asynchronous(dir_base + "/jsp/support/ajax/mineDataXml.jsp", processFunction_callback, submitType, "strsql=" + strsql);
}

var rmXmlHttpRequest_update;

function callUpdateData(strsql, processFunction_callback, submitType) {
	return retrieveUrl_asynchronousUpdate(dir_base + "/jsp/support/ajax/updateData.jsp", processFunction_callback, submitType, "strsql=" + escape(strsql) );
	}
  
	function retrieveUrl_asynchronous(url, processFunction_callback, submitType, parameters) {
		rmXmlHttpRequest = null;
		if(submitType == undefined || (submitType != "GET" && submitType != "POST")) {
		submitType = "GET";
	}
	try {
	    if (url != null && url != "") {
	      if (window.XMLHttpRequest) { // Non-IE browsers
	        rmXmlHttpRequest = new XMLHttpRequest();
	        rmXmlHttpRequest.onreadystatechange = processFunction_callback;
	        try {
	        	if(submitType == "GET") {
	        		if(url.indexOf("?") > 0) {
	        			url += "&" + parameters;
	        		} else {
	        			url += "?" + parameters;
	        		}
					rmXmlHttpRequest.open("GET", url, true);
	        		rmXmlHttpRequest.send(null);
	        	} else if(submitType == "POST") {
	        		rmXmlHttpRequest.open("POST", url, true);
	        		rmXmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
	        		rmXmlHttpRequest.send(parameters);
	        	}
	        } catch (e) {
	          alert(e);
	        }
	      } else if (window.ActiveXObject) { // IE
	        rmXmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
	        if (rmXmlHttpRequest) {
	          rmXmlHttpRequest.onreadystatechange = processFunction_callback;
	        	if(submitType == "GET") {
	        		if(url.indexOf("?") > 0) {
	        			url += "&" + parameters;
	        		} else {
	        			url += "?" + parameters;
	        		}
					rmXmlHttpRequest.open("GET", url, true);
	        		rmXmlHttpRequest.send(null);
	        	} else if(submitType == "POST") {
	        		rmXmlHttpRequest.open("POST", url, true);
	        		rmXmlHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
	        		rmXmlHttpRequest.send(parameters);
	        	}
	        }
	      }
	    }
	} catch(e) {
		alert(e.message);	
	}
}

  function retrieveUrl_asynchronousUpdate(url, processFunction_callback, submitType, parameters) {
	rmXmlHttpRequest_update = null;
	if(submitType == undefined || (submitType != "GET" && submitType != "POST")) {
		submitType = "GET";
	}
	try {
	    if (url != null && url != "") {
	      if (window.XMLHttpRequest) { // Non-IE browsers
	        rmXmlHttpRequest_update = new XMLHttpRequest();
	        rmXmlHttpRequest_update.onreadystatechange = processFunction_callback;
	        try {
	        	if(submitType == "GET") {
	        		if(url.indexOf("?") > 0) {
	        			url += "&" + parameters;
	        		} else {
	        			url += "?" + parameters;
	        		}
					rmXmlHttpRequest_update.open("GET", url, true);
	        		rmXmlHttpRequest_update.send(null);
	        	} else if(submitType == "POST") {
	        		rmXmlHttpRequest_update.open("POST", url, true);
	        		rmXmlHttpRequest_update.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
	        		rmXmlHttpRequest_update.send(parameters);
	        	}
	        } catch (e) {
	          alert(e);
	        }
	      } else if (window.ActiveXObject) { // IE
	        rmXmlHttpRequest_update = new ActiveXObject("Microsoft.XMLHTTP");
	        if (rmXmlHttpRequest) {
	          rmXmlHttpRequest_update.onreadystatechange = processFunction_callback;
	        	if(submitType == "GET") {
	        		if(url.indexOf("?") > 0) {
	        			url += "&" + parameters;
	        		} else {
	        			url += "?" + parameters;
	        		}
					rmXmlHttpRequest_update.open("GET", url, true);
	        		rmXmlHttpRequest_update.send(null);
	        	} else if(submitType == "POST") {
	        		rmXmlHttpRequest_update.open("POST", url, true);
	        		rmXmlHttpRequest_update.setRequestHeader("Content-type", "application/x-www-form-urlencoded;");
	        		rmXmlHttpRequest_update.send(parameters);
	        	}
	        }
	      }
	    }
	} catch(e) {
		alert(e.message);	
	}
}

function transferFormValueToString(form) {
	var aValue = new Array();
	var mValue = new Object();
	for(var i=0; i<form.elements.length; i++) {
		var thisElement = form.elements[i]; 
		var tempName = thisElement.name;
		if(!arrayHasString(aValue, thisElement.name)) {
			aValue.push(tempName);
			mValue[tempName] = "";
		}
		if(thisElement.tagName.toUpperCase() == "TEXTAREA" || thisElement.tagName.toUpperCase() == "SELECT") {
			mValue[tempName] = thisElement.value;
		} else if(thisElement.tagName.toUpperCase() == "INPUT") {
			if(thisElement.type == "checkbox" || thisElement.type == "radio") {
				if(thisElement.check) {
					if(mValue[tempName] != "") {
						mValue[tempName] += ",";
					} 
					mValue[tempName] = thisElement.value;
				}
			} else {
				mValue[tempName] = thisElement.value;
			}
		}
	}
	var str = "";
	for(var i=0; i<aValue.length; i++) {
		if(str != "") {
			str += "&";
		}
		str += aValue[i] + "=" + escape(mValue[aValue[i]]);
	}
	//alert(str);
	return str;
	
}

		//回写表单
	function writeBackValueToForm(inputName, thisValueOrArray) {
		//TODO 整合
		if(form.elements[inputName] == undefined) {
			return false;
		}
		if(form.elements[inputName].value != undefined) {  //如果有value属性，直接赋值
			form.elements[inputName].value = thisValueOrArray;			
		} 
		if(form.elements[inputName].length != undefined ) {  //没有value属性
			if(thisValueOrArray[0] == undefined) {
				thisValue = new Array();
				thisValue[thisValue.length] = thisValueOrArray;							
			}
			if(form.elements[inputName].length != null) {  //length不为空
				var tempLength = form.elements[inputName].length;
				for(var j=0; j<tempLength; j++) {
					var thisObj = form.elements[inputName][j];
					for(var k=0; k<thisValue.length; k++) {
						if(thisObj.value == thisValue[k]) {  //如有选中，继续循环
							if( thisObj.checked != undefined) {
								thisObj.checked = true;	
								break;									
							} else if( thisObj.selected != undefined) {
								thisObj.selected = true;								
								break;
							}
						} else {                             //如没有选中，察看下一个
								if( thisObj.checked != undefined) {
									thisObj.checked = false;	
								} else if( thisObj.selected != undefined) {
									thisObj.selected = false;								
								}	
							}
						}
					}
				} 	
							
			}
		}
  
  function getNodeText(node, xpath) {
  	if(node != null && node.selectSingleNode(xpath) != null) {
  		return node.selectSingleNode(xpath).text;
  	} else {
  		return null;
  	}
  }
  
    function ListDataHandler() {
		this.columnName = new Array();
		this.columnChineseName = new Array();
		this.columnPk = "id";
	this.actionPath = "";
	this.table = null;
	this.tableBody = null;
	this.bodyScrollTop = null;

	this.initTable = function() {
		var thisTable = document.createElement("table");
		var cssStr = "width: 100%; border: 1px; text-align:center; background-color:#93B3CA";
		thisTable.setAttribute("style", cssStr);
		thisTable.style.cssText = cssStr;

		var thisTableHead = document.createElement("thead");
		thisTable.appendChild(thisTableHead);

		myNewRow = thisTableHead.insertRow();
		myNewRow.bgColor = "#B8D5F5";

		myNewRow.insertCell().innerHTML = "<input name=myCheckAll type=checkbox class=check value=checkbox onclick='javascript: rmListData.checkAll(this.checked)'>";
		myNewRow.insertCell().innerHTML = "序";
		for(var i=0; i<this.columnChineseName.length; i++) {
			myNewRow.insertCell().innerHTML = this.columnChineseName[i];
		}

		document.getElementById("td_listData").appendChild(thisTable);
		this.table = document.getElementById("td_listData").getElementsByTagName("table")[0];
		this.refreshData();
	};

	this.refreshData = function() {
		if(rmXmlHttpRequest_update != null && rmXmlHttpRequest_update.readyState != 4) {
			return;
		}
		this.bodyScrollTop = window.document.body.scrollTop;
		if(this.table.getElementsByTagName("tbody").length > 0) {
			this.table.removeChild(this.table.getElementsByTagName("tbody")[0]);
		}
		//alert(this.table.outerHTML);
		var thisTableBody = document.createElement("tbody");
		this.table.appendChild(thisTableBody);
		this.tableBody = thisTableBody;

		retrieveUrl_asynchronous(this.actionPath + "queryAll&RM_PAGE_SIZE_KEY=99999999", function() {
			if(rmXmlHttpRequest.readyState == 4) {
				if(rmXmlHttpRequest.status == 200) {
					var xml = rmXmlHttpRequest.responseXML;
					var rmDatas = xml.selectNodes("/rmdatas/rmdata");
					for(var i=0; i<rmDatas.length; i++) {
						var thisTr = document.createElement("tr");
						thisTr.bgColor = "#FFFFFF";
						rmListData.tableBody.appendChild(thisTr);
						
						var td1 = document.createElement("td");
						td1.innerHTML = "<input name=idcheckbox type=checkbox>";
						thisTr.appendChild(td1);

						var td2 = document.createElement("td");
						td2.innerHTML = (i+1) + "<input type=hidden name=hiddenObj value=" + getNodeText(xml, "/rmdatas/rmdata[" + i + "]/" + rmListData.columnPk + "/text()") + ">";
						thisTr.appendChild(td2);

						for(var j=0; j<rmListData.columnName.length; j++) {
							var tdTemp = document.createElement("td");
							var xpath = "/rmdatas/rmdata[" + i + "]/" + rmListData.columnName[j] + "/text()";
							var columnValue = getNodeText(xml, xpath);
							if(columnValue == null) {
								columnValue = "";
							}
							tdTemp.innerHTML = columnValue;
							thisTr.appendChild(tdTemp);
						}
					}
					window.document.body.scrollTop = rmListData.bodyScrollTop;
				}
			}
		});
	};

	this.checkAll = function (bSelected) {
		var elements = this.tableBody.getElementsByTagName("input");
		for(var i=0; i<elements.length; i++) {
			if(elements[i].type == "checkbox") {
				elements[i].checked = bSelected;
			}
		}
	};

	this.findSelections = function () {
		var hiddenObjs = this.tableBody.getElementsByTagName("input");
		var ids = null;  //定义id值的数组
		for(var i=0;i<hiddenObjs.length;i++){  //循环checkbox组
			if(hiddenObjs[i].type != "hidden") {
				continue;
			}
			var thisCheckbox = hiddenObjs[i].parentNode.parentNode.getElementsByTagName("input")[0];
			if(thisCheckbox.checked) {  //如果被选中
				if(ids == null) {
					ids = new Array(0);
				}
				ids.push(hiddenObjs[i].value);  //加入选中的checkbox
			}
		}
		return ids;
	};

	this.insertRecord = function () {  // 新增行
		retrieveUrl_asynchronousUpdate(this.actionPath + "insert", function() {
			rmListData.refreshData();
		}, "POST", transferFormValueToString(form));
	};

	this.findRecord = function (ids) {  // 新增行
		retrieveUrl_asynchronous(this.actionPath + "find", function() {
			if(rmXmlHttpRequest.readyState == 4) {
				if(rmXmlHttpRequest.status == 200) {
					var xml = rmXmlHttpRequest.responseXML;
					var rmData = xml.selectSingleNode("/rmdatas/rmdata");
					for(var j=0; j<rmData.childNodes.length; j++) {
						var thisNode = rmData.childNodes[j];
						writeBackValueToForm(thisNode.nodeName, thisNode.text);
					}
				}
			}
		}, "GET", "id=" + ids);
	};

	this.updateRecord = function () {  // 新增行
		retrieveUrl_asynchronousUpdate(this.actionPath + "update", function() {
			rmListData.refreshData();
		}, "POST", transferFormValueToString(form));
	};


	this.deleteRecord = function(ids) {  //删除文件
		retrieveUrl_asynchronousUpdate(this.actionPath + "deleteMulti", function() {
			rmListData.refreshData();
		}, "POST", "ids=" + ids);
	};
}