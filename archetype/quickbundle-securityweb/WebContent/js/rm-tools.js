//document.onkeydown=keyDown;

function keyDown() {
	/*	
	//屏蔽鼠标右键、Ctrl+n、shift+F10、F5刷新、退格键
	if((window.event.altKey)&&
	  ((window.event.keyCode==37)|| //屏蔽 Alt+ 方向键 ←
	  (window.event.keyCode==39))){ //屏蔽 Alt+ 方向键 →
	  alert("不准你使用ALT+方向键前进或后退网页！");
	  event.returnValue=false;
	  }
	  
	if ((event.ctrlKey)&&(event.keyCode==78)) //屏蔽 Ctrl+n
	    {alert("禁止新建窗口！");
	    event.returnValue=false;}
	if ((event.shiftKey)&&(event.keyCode==121)) //屏蔽 shift+F10
	    {alert("禁止使用该菜单！");
	    event.returnValue=false;}
	 if (window.event.srcElement.tagName == "A" && window.event.shiftKey)
	window.event.returnValue = false; //屏蔽 shift 加鼠标左键新开一网页
	*/
	if ((event.ctrlKey)&&(event.keyCode==81)) {  //CTRL+Q
		//alert(event.keyCode);
		//event.returnValue=false;
	}

}

//TODO 默认时间
//打开日期参照
function getYearMonthDay(textDate, path, defaultDate, event){
	var returnAry;
	if(path == undefined) {
		path = "../../";
	}
	var str = "status:1;dialogHeight:230px;dialogWidth:300px;";
	event = window.event || event;
	if(event) {
		str += "dialogTop: "+event.screenY+"px; dialogLeft: "+event.screenX+"px";
	}
	returnAry = window.showModalDialog(path + "js/calendar/calendar.htm","日期参照",str);
	if (returnAry !=null) {
		jQuery(textDate).val(returnAry);
		focus();
	}
}

function getYearMonth(textMonth,path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/month/selectMonth.htm";
	strFeatures = "dialogWidth=224px;dialogHeight=150px;center=1;help=0;resizable=0;scroll=1;status=0;location=1";
	var defaultMonth = textMonth.value;
	var returnMonth = showModalDialog(path,defaultMonth,strFeatures);
	if(returnMonth == null)
	{ 
		returnMonth="";
	}
	textMonth.value = returnMonth;
}

function getHourMinuteSecond(textMonth,path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/time/hourMinuteSecond.htm";
	strFeatures = "dialogWidth=224px;dialogHeight=126px;center=yes;location=0;resizable=0;titlebar=0;scrollbars=0;help=no";
	var defaultTime = textMonth.value;
	var returnMonth = showModalDialog(path,defaultTime,strFeatures);
	if(returnMonth != null) { 
		textMonth.value = returnMonth;
	}
}

function getYearMonthDayHourMinuteSecond(textMonth,path) {
	if(path == undefined) {
		path = "../../";
	}
	path = path + "js/time/yearMonthDayHourMinuteSecond.htm";
	strFeatures = "dialogWidth=320px;dialogHeight=350px;center=yes;location=0;resizable=0;titlebar=0;scrollbars=0;help=no";
	var defaultTime = textMonth.value;
	var returnMonth = showModalDialog(path,defaultTime,strFeatures);
	if(returnMonth != null) { 
		textMonth.value = returnMonth;
	}
}

function getInfoDialog(urlPath) {
	var width = 700;
	var height = 500;
	var xposition = 0; 
	var yposition = 0;
	if ((parseInt(navigator.appVersion) >= 4 )) {
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	var windowProperty = "width=" + width + "," 
		+ "height=" + height + ","
		+ "location=0," 
		+ "menubar=0,"
		+ "resizable=1,"
		+ "scrollbars=1,"
		+ "status=1," 
		+ "titlebar=0,"
		+ "toolbar=0,"
		+ "hotkeys=0,"
		+ "screenx=" + xposition + "," //Netscape
		+ "screeny=" + yposition + "," //Netscape
		+ "left=" + xposition + "," //IE
		+ "top=" + yposition; //IE
	getDialog(urlPath, "1", "", width, height, windowProperty);
}

function getDialog(urlPath, isModelessDialog, windowName, width, height, windowProperty){
	if(urlPath == undefined) {
		return null;	
	}
	if(isModelessDialog == undefined) {
		isModelessDialog = "0";	
	}
	if(windowName == undefined) {
		windowName = "myAnonymousWindow";	
	}
	if(width == undefined) {
		width = "700";	
	}
	if(height == undefined) {
		height = "500";	
	}
	var xposition = 0; 
	var yposition = 0;
	if ((parseInt(navigator.appVersion) >= 4 )) {
		xposition = (screen.width - width) / 2;
		yposition = (screen.height - height) / 2;
	}
	if(windowProperty == undefined) {
		if(isModelessDialog == "1") {
			windowProperty= "width=" + width + "," 
			+ "height=" + height + "," 
			+ "location=0," 
			+ "menubar=0,"
			+ "resizable=1,"
			+ "scrollbars=1,"
			+ "status=0," 
			+ "titlebar=0,"
			+ "toolbar=0,"
			+ "hotkeys=0,"
			+ "screenx=" + xposition + "," //Netscape
			+ "screeny=" + yposition + "," //Netscape
			+ "left=" + xposition + "," //IE
			+ "top=" + yposition; //IE 	
		} else {
			windowProperty = 'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;';
		}
	}
	if(isModelessDialog == "1") {
		window.open( urlPath,windowName,windowProperty );
		return new Object();
	} else {
		var returnObj = window.showModalDialog(urlPath,windowName,windowProperty);
		if(returnObj != undefined) {
			return returnObj;	
		} else {
			return new Object();	
		}
	}
}

function getReference(inputArray, path, urlPath, width, height){
	if(path == undefined) {
		path = dir_base;
	}
	if(width == undefined) {
		width = 950;
	}
	if(height == undefined) {
		height = 700;
	}
	var myObject = new Object();
	myObject['inputArray'] = inputArray;
	//urlPath放最后
	myObject['urlPath'] = urlPath;
	var rtObj = window.showModalDialog(path + 'jsp/support/globalReference.jsp', myObject, 'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWriteReference(inputArray, rtObj);
}

function toDoWriteReference(inputArray, rtObj) {
	if(inputArray != null && rtObj != null && (typeof rtObj) == 'object') {
		var index = 0;
		for(var rto in rtObj) {
			if(index >= inputArray.length) {
				break;
			}
			setTargetValue(inputArray[index], rtObj[rto]);
			index ++;
		}
	}
}

function setTargetValue(target, value) {
	//对null预处理为""
	if(value == null) {
		value = "";
	}
	if(target.length == null) {
		jQuery(target).val(value);
	} else {
		for(var i=0; i<target.length; i++) {
			jQuery(target[i]).val(value);
		}
	}
}

function getUploadWindow(inputArray, path, width, height, fileNumberFromTo, uploadDir, openType){
	if(path == null) {
		path = "../../";
	}
	if(width == null) {
		width = 800;
	}
	if(height == null) {
		height = 600;
	}
	if(fileNumberFromTo == null) {
		fileNumberFromTo = "";
	}
	if(uploadDir == null) {
		uploadDir = "";
	}
	if(openType == null) {
		openType = "WRITE";
	}
	
	var myObject = new Object();
	try {
		if(inputArray.length >= 1) {
			if(inputArray[0].value != null && inputArray[0].value != "" ) {
				var oldData = new Array();
				var aSaveName = inputArray[0].value.split(",");
				var aOldName = inputArray.length >= 2 ? inputArray[1].value.split(",") : null;
				var aIsImage = inputArray.length >= 3 ? inputArray[2].value.split(",") : null;
				var aAbbreviatoryName = inputArray.length >= 4 ? inputArray[3].value.split(",") : null;
				var aOldWidthHeight = inputArray.length >= 5 ? inputArray[4].value.split(",") : null;
				var aAbbreviatoryWidthHeight = inputArray.length >= 6 ? inputArray[5].value.split(",") : null;
				var aUploadRemark = inputArray.length >= 7 ? inputArray[6].value.split(",") : null;

				for(var i=0; i<aSaveName.length; i++) {
					var tempArray = new Array();
					tempArray[0] = aSaveName[i];
					tempArray[1] = (aOldName == null) ? "" : aOldName[i];
					tempArray[2] = (aIsImage == null) ? "" : aIsImage[i];
					tempArray[3] = aAbbreviatoryName == null ? "" : aAbbreviatoryName[i];
					tempArray[4] = aOldWidthHeight == null ? "" : aOldWidthHeight[i];
					tempArray[5] = aAbbreviatoryWidthHeight == null ? "" : aAbbreviatoryWidthHeight[i];
					tempArray[6] = aUploadRemark == null ? "" : aUploadRemark[i];
					oldData[oldData.length] = tempArray;
				}
				myObject.oldData = oldData;
			}
		}
	} catch(e) {
		//alert("a  0:" + e.message);
	}
	var rtObj = window.showModalDialog(path + 'jsp/support/upload/globalUpload.jsp?fileNumberFromTo=' + fileNumberFromTo + '&uploadDir=' + uploadDir + "&openType=" + openType, myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWriteUpload(inputArray, rtObj);
}

function toDoWriteUpload(inputArray, rtObj) {
	var textValue = inputArray[0];
	var textName = (inputArray.length >= 2) ? inputArray[1] : null;
	var textIsImage = (inputArray.length >= 3) ? inputArray[2] : null;
	var textAbbreviatoryName = inputArray.length >= 4 ? inputArray[3] : null;
	var textSaveNameWidthHeight = inputArray.length >= 5 ? inputArray[4] : null;
	var textAbbreviatoryNameWidthHeight = inputArray.length >= 6 ? inputArray[5] : null;
	var textUploadRemark = inputArray.length >= 7 ? inputArray[6] : null;
	
	var value1 = "";
	var name1 = "";
	var isImage1 = "";
	var abbreviatoryName = "";
	var saveNameWidthHeight = "";
	var abbreviatoryNameWidthHeight = "";
	var uploadRemark = "";
	
	if(rtObj!=null && rtObj.length > 0){
		for(var i=0; i<rtObj.length-1; i++) {
			value1 += rtObj[i][0] + ",";
			name1 += rtObj[i][1] + ",";
			isImage1 += rtObj[i][2] + ",";
			abbreviatoryName += rtObj[i][3] + ",";
			saveNameWidthHeight += rtObj[i][4] + ",";
			abbreviatoryNameWidthHeight += rtObj[i][5] + ",";
			uploadRemark += rtObj[i][6] + ",";
		}
		value1 += rtObj[rtObj.length-1][0];
		name1 += rtObj[rtObj.length-1][1];
		isImage1 += rtObj[rtObj.length-1][2];
		abbreviatoryName += rtObj[rtObj.length-1][3];
		saveNameWidthHeight += rtObj[rtObj.length-1][4];
		abbreviatoryNameWidthHeight += rtObj[rtObj.length-1][5];
		uploadRemark += rtObj[rtObj.length-1][6];

		jQuery(textValue).val(value1);

		if(textName != null) {
			textName.value = name1;
		}
		if(textIsImage != null) {
			textIsImage.value = isImage1;
		}
		if(textAbbreviatoryName != null) {
			textAbbreviatoryName.value = abbreviatoryName;
		}
		if(textSaveNameWidthHeight != null) {
			textSaveNameWidthHeight.value = saveNameWidthHeight;
		}
		if(textAbbreviatoryNameWidthHeight != null) {
			textAbbreviatoryNameWidthHeight.value = abbreviatoryNameWidthHeight;
		}
		if(textUploadRemark != null) {
			textUploadRemark.value = uploadRemark;
		}
	}
}

function getUploadWindowWithObj(inputObj, path, width, height){
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 700;
	}
	if(height == undefined) {
		height = 500;
	}
	var myObject = new Object();
	myObject.oldData = inputObj;
	var rtObj = window.showModalDialog(path + 'jsp/support/upload/globalUpload.jsp', myObject,'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	return rtObj;
}

//获取组织结构参照
function getPartyWindow(inputArray, path, urlPath, width, height) {
	if(path == undefined) {
		path = "../../";
	}
	if(width == undefined) {
		width = 400;
	}
	if(height == undefined) {
		height = 600;
	}

	var myObject = new Object();
	myObject.oldData = inputArray;

	var myObject = new Object();
	var rtObj = window.showModalDialog(urlPath, myObject, 'dialogHeight=' + height + 'px;dialogWidth=' + width + 'px;');
	toDoWritePartyWindow(inputArray, rtObj);
}

function toDoWritePartyWindow(inputArray, rtObj) {
	var textValue = inputArray[0];
	var textName = inputArray[1];

	if(inputArray != null && rtObj != null && rtObj.length > 0){
		var allTextValue = "";
		var allTextName = "";
		for(var i=0; i<rtObj.length-1; i++) {
			allTextValue += rtObj[i]['returnValue'] + ",";
			allTextName += rtObj[i]['childName'] + ",";
		}
		allTextValue += rtObj[rtObj.length-1]['returnValue'];
		allTextName += rtObj[rtObj.length-1]['childName'];
		textValue.value = allTextValue;
		textName.value = allTextName;
		
		//TODO 通用
		var index = 0;
		for(var rto in rtObj) {
			if(index >= inputArray.length) {
				break;
			}
			//setTargetValue(inputArray[index], rtObj[rto]);
			index ++;
		}
	}
}

function getRmts() {
	return "rmts=" + (new Date()).valueOf();
}

function getConfirm(msg) {  //确认选择框
  	if(msg == undefined || msg == null || msg=="") {
		msg = "您确定此操作吗?";
	}
  	if(confirm(msg)) {
  		return true;
  	} else {
  		return false;
  	}
}

//写表的顶部
function writeTableTop(pageTitle, path) {
	return;
	if(pageTitle == undefined) {
		pageTitle = "示范页面";	
	}
	if(path == undefined) {
		path = "../../";
	}
	document.write("<table width='100%' height='100%' border='0' cellpadding='0' cellspacing='0'>                                                                 				");																																																																																																																																																												
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("     <td width='14' valign='top' height='29'><img src='" + path + "images/con_t_left.jpg' width='14' height='29'></td>                                                    			");																																																																																																																																																													
	document.write("    <td background='" + path + "images/con_t_tabg.jpg' valign='top'><table  border='0' cellpadding='0' cellspacing='0' background='" + path + "images/con_t_bg.jpg'>			");																																																																																																																																																													
	document.write("      <tr  style='background-color: #1885C8;'>                                                                                                                                 			");																																																																																																																																																													
	document.write("        <td valign='top'><img src='" + path + "images/con_t_m.jpg' width='13' height='29'></td>                                                               			");																																																																																																																																																													
	document.write("        <td class='2bt' background='" + path + "images/con_t_bg.jpg'>" + pageTitle + "</td>                                                                                                         			");																																																																																																																																																													
	document.write("        <td width='41' valign='top'><img src='" + path + "images/con_t_c.jpg' width='41' height='29'></td>                                                    			");																																																																																																																																																													
	document.write("      </tr>                                                                                                                                			");																																																																																																																																																													
	document.write("    </table></td>                                                                                                                          			");																																																																																																																																																													
	document.write("    <td width='15' valign='top'><img src='" + path + "images/con_t_rig.jpg' width='15' height='29'></td>                                                      			");																																																																																																																																																													
	document.write("  </tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("   <tr>                                                                                                                                    			");																																																																																																																																																													
	document.write("    <td background='" + path + "images/con_l_bg.jpg'>&nbsp;</td>                                                                                 			");																																																																																																																																																													
	document.write("    <td bgcolor='#EDF4FD'>                                                                                                                 			");																																																																																																																																																																									
	document.write("<table width='100%' height='100%'  border='0' cellpadding='6' cellspacing='1'>											");																																																																																																																											
	document.write("       <tr>                                                                   											");																																																																																																																											
	document.write("         <td valign='top'> ");	
}

//写表的底部
function writeTableBottom(path) {
	return;
	if(path == undefined) {
		path = "../../";
	}
	document.write("</td>");
	document.write("</tr>");
	document.write("</table>");
	document.write("</td>");
	document.write("     <td background='" + path + "images/con_t_rg.jpg'>&nbsp;</td>");
	document.write("   </tr>");
	document.write("   <tr>");
	document.write("     <td valign='bottom' background='" + path + "images/con_l_bg.jpg'><img src='" + path + "images/con_d_left.jpg' width='14' height='15'></td>");
	document.write("     <td height='15' background='" + path + "images/con_d_ng.jpg'>&nbsp;</td>");
	document.write("     <td valign='bottom' background='" + path + "images/con_t_rg.jpg'><img src='" + path + "images/con_d_rig.jpg' width='15' height='15'></td>");
	document.write("   </tr>");
	document.write(" </table>");	
}

function writeInfoToId(fontId, info) {
	var remainPromptFont;
	if(document.getElementById)
		remainPromptFont = document.getElementById(fontId);
	if(remainPromptFont != null)
		remainPromptFont.innerHTML = info;
}

//把某个div合起来或展开
function hideshow(which,img,path,isClosed) {
	if(path == undefined) {
		path = "../../";
	}
	if (!document.getElementById|document.all) {
		return;
	}
	else {
		if (document.getElementById) {
			oWhich = eval ("document.getElementById('" + which + "')");
		} else {
			oWhich = eval ("document.all." + which);
		}
	}

	window.focus();
	if(oWhich != null) {
		if (isClosed || oWhich.style.display=="none") {
			oWhich.style.display="";
			if(img!=undefined)
				img.src=path + "images/icon/07-0.gif";
		}
		else {
			oWhich.style.display="none";
			if(img!=undefined)img.src=path + "images/icon/07.gif";
		}		
	}
}


//以下是识别不同浏览器
// work around bug in xpcdom Mozilla 0.9.1
//window.saveNavigator = window.navigator;

//TODO js报错监控
function defaultOnError(msg, url, line) {
	try {
		// customize this for your site
		if (top.location.href.indexOf('_files/errors/') == -1)
			top.location = '/evangelism/xbProjects/_files/errors/index.html?msg=' + escape(msg) + '&url=' + escape(url) + '&line=' + escape(line);
	} catch(e) {
	}
}

// Display Error page...  
// XXX: more work to be done here
//
function reportError(message) {
	try {
		// customize this for your site
		if (top.location.href.indexOf('_files/errors/') == -1)
			top.location = '/evangelism/xbProjects/_files/errors/index.html?msg=' + escape(message);
	} catch(e) {
	}
}

function detectBrowser()
{
	var oldOnError = window.onerror;
	var element = null;
	
	window.onerror = defaultOnError;

	navigator.OS		= '';
	navigator.version	= 0;
	navigator.org		= '';
	navigator.family	= '';

	var platform;
	if (typeof(window.navigator.platform) != 'undefined')
	{
		platform = window.navigator.platform.toLowerCase();
		if (platform.indexOf('win') != -1)
			navigator.OS = 'win';
		else if (platform.indexOf('mac') != -1)
			navigator.OS = 'mac';
		else if (platform.indexOf('unix') != -1 || platform.indexOf('linux') != -1 || platform.indexOf('sun') != -1)
			navigator.OS = 'nix';
	}

	var i = 0;
	var ua = window.navigator.userAgent.toLowerCase();
	
	if (ua.indexOf('opera') != -1)
	{
		i = ua.indexOf('opera');
		navigator.family	= 'opera';
		navigator.org		= 'opera';
		navigator.version	= parseFloat('0' + ua.substr(i+6), 10);
	}
	else if ((i = ua.indexOf('msie')) != -1)
	{
		navigator.org		= 'microsoft';
		navigator.version	= parseFloat('0' + ua.substr(i+5), 10);
		
		if (navigator.version < 4)
			navigator.family = 'ie3';
		else
			navigator.family = 'ie4';
	}
	else if (typeof(window.controllers) != 'undefined' && typeof(window.locationbar) != 'undefined')
	{
		i = ua.lastIndexOf('/');
		navigator.version = parseFloat('0' + ua.substr(i+1), 10);
		navigator.family = 'gecko';

		if (ua.indexOf('netscape') != -1)
			navigator.org = 'netscape';
		else if (ua.indexOf('compuserve') != -1)
			navigator.org = 'compuserve';
		else
			navigator.org = 'mozilla';
	}
	else if ((ua.indexOf('mozilla') !=-1) && (ua.indexOf('spoofer')==-1) && (ua.indexOf('compatible') == -1) && (ua.indexOf('opera')==-1)&& (ua.indexOf('webtv')==-1) && (ua.indexOf('hotjava')==-1))
	{
	    var is_major = parseFloat(navigator.appVersion);
    
		if (is_major < 4)
			navigator.version = is_major;
		else
		{
			i = ua.lastIndexOf('/');
			navigator.version = parseFloat('0' + ua.substr(i+1), 10);
		}
		navigator.org = 'netscape';
		navigator.family = 'nn' + parseInt(navigator.appVersion);
	}
	else if ((i = ua.indexOf('aol')) != -1 )
	{
		// aol
		navigator.family	= 'aol';
		navigator.org		= 'aol';
		navigator.version	= parseFloat('0' + ua.substr(i+4), 10);
	}

	navigator.DOMCORE1	= (typeof(document.getElementsByTagName) != 'undefined' && typeof(document.createElement) != 'undefined');
	navigator.DOMCORE2	= (navigator.DOMCORE1 && typeof(document.getElementById) != 'undefined' && typeof(document.createElementNS) != 'undefined');
	navigator.DOMHTML	= (navigator.DOMCORE1 && typeof(document.getElementById) != 'undefined');
	navigator.DOMCSS1	= ( (navigator.family == 'gecko') || (navigator.family == 'ie4') );

	navigator.DOMCSS2   = false;
	if (navigator.DOMCORE1)
	{
		element = document.createElement('p');
		navigator.DOMCSS2 = (typeof(element.style) == 'object');
	}

	navigator.DOMEVENTS	= (typeof(document.createEvent) != 'undefined');

	window.onerror = oldOnError;
}

//移动选择框中的列表项（option），要求：列表项的value值从0开始，依次增加
//参数selectName:选择框的名称，moveType：移动类型——top置首，up向上，down向下，bottom置底
function moveSelect_onClick(selectObj,moveType) {
	var sel = selectObj;
	var val = parseInt(sel.value,10);
	
	if(moveType=="top") {
		if(val==0) {
			alert("已经是首位！");
			return false;
		}
		var temp = sel[val].text;
		for(var i=val;i>0;i--) {
			sel[i].text = sel[i-1].text;
		}
		sel[0].text = temp;
		sel.selectedIndex = 0;
	}
	
	if(moveType=="up") {
		if(val==0) {
			alert("已经是首位！");
			return false;
		}
		var temp = sel[val-1].text;
		sel[val-1].text = sel[val].text;
		sel[val].text = temp;
		sel.selectedIndex = val-1;
	}
	
	if(moveType=="down") {
		if(val>=sel.size-1) {
			alert("已经是末尾！");
			return false;
		}
		var temp = sel[val+1].text;
		sel[val+1].text = sel[val].text;
		sel[val].text = temp;
		sel.selectedIndex = val+1;
	}
	
	if(moveType=="bottom") {
		if(val>=sel.size-1) {
			alert("已经是末尾！");
			return false;
		}
		var temp = sel[val].text;
		for(var i=val;i<sel.size-1;i++) {
			sel[i].text = sel[i+1].text;
		}
		sel[sel.size-1].text = temp;
		sel.selectedIndex = sel.size-1;
	}
}

detectBrowser();

function ignoreWriteBackValue(inputObj) {
	if(inputObj.parentElement == null || inputObj.parentElement.parentElement == null) {
		return false;
	}
	if(jQuery(inputObj.parentElement.parentElement).attr("class") != null 
			&& jQuery(inputObj.parentElement.parentElement).attr("class").indexOf("rowPrototype") > -1) {
		return true;
	}
	return false;
}

//回写表单
function writeBackValue(inputName) {
	jQuery(":input[name='" + inputName + "']", document.forms[0]).each(function() {
		if(mForm[inputName] != null) {
			var thisJ = jQuery(this);
			if(ignoreWriteBackValue(this)) {
				return true;
			}
			var objValue = mForm[inputName];
			if(this.tagName.toUpperCase() == "INPUT" && (thisJ.attr("type").toUpperCase() == "RADIO" || thisJ.attr("type").toUpperCase() == "CHECKBOX")) {
				var thisSelected = false;
				if(typeof objValue == "string") {
					thisSelected = thisJ.val() == objValue;
				} else if(typeof objValue == "object" && objValue.length != null) {
					for(var i=0; i<objValue.length; i++) {
						if(objValue[i] == thisJ.val()) {
							thisSelected = true;
							break;
						}
					}
				}
				if(thisSelected) {
					this.checked = true;
				} else {
					this.checked = false;
				}
			} else {
				thisJ.val(objValue);
			}
		}
	});
	return;
}
	  
function getFormValue(inputObjString) {  //获取表单value
  	var inputObj = form.elements[inputObjString];
  	if(inputObj == undefined || typeof inputObj == "string") {
  		//alert(inputObjString + '不是有效的输入表单');
  		return false;
  	}
  	if(inputObj.value != undefined) {
  		return inputObj.value;
  	} else if(inputObj.length != undefined){
  		if(inputObj.length != null) {
  			var rtValue = "";
  			for(var i=0; i<inputObj.length; i++) {
  				if(inputObj[i].checked) {
  					if(rtValue == "") {
	  					rtValue += inputObj[i].value;  					
  					} else {
  						rtValue += "," + inputObj[i].value; 
  					}

  				}
  			}
  			return rtValue;
  		} else {
  			return "";
	  		}
	  	}
	}
  
	function moveList_onClick(from,to) {
	  	var fromList = from;
		var fromLen = fromList.options.length;
		
		var toList = to;
		var toLen = toList.options.length;
		
		var current = fromList.selectedIndex;
		while(current > -1) {
			var o = fromList.options[current];
			var t = o.text;
			var v = o.value;
			var optionName = new Option(t,v,false,false);
			toList.options[toLen] = optionName;
			toLen ++;
			fromList.options[current] = null;
			current = fromList.selectedIndex;
		}
	}
	
	function clickAllSelectMultiple(thisSelect) {
		if(thisSelect != null && thisSelect.options != null) {
			for(var i=0; i<thisSelect.options.length; i++) {
				thisSelect.options[i].selected = true;
			}
		}
	}
	
	function arrayHasString(tempArray, myString) {
		var returnMyValue = false;
		for(var i=0; i<tempArray.length; i++) {
			if(tempArray[i] == myString) {
				returnMyValue = true;
			}
		}
		return returnMyValue;
	}

	
	function pushCondition_old(myArray, pageRealValue, operate1, operate2, thisField, relationType) {  //压入查询条件
	if(operate1 == undefined) {
		operate1 = " like '%";
		operate2 = "%' ";
	} else if(operate2 == undefined) {
		operate2 = "";
	}
	if(thisField == undefined) {
		thisField = pageRealValue;
	}
	if(relationType == undefined) {
		relationType = "single";
	}
	if(pageRealValue == null || pageRealValue == "" || getFormValue(pageRealValue) == "") {
		return false;
	}
	if(relationType == "single") {
		myArray.push(" " + thisField + " " + operate1 + getFormValue(pageRealValue) + operate2);
	} else if(relationType == "multiple") {
		var targetValue = getFormValue(pageRealValue);
		if(targetValue.indexOf(",") < 0) {
			myArray.push(" " + thisField + " " + operate1 + targetValue + operate2);
		} else {
			var aTargetValue = targetValue.split(",");
			var strwhere = " (";
			for(var i=0; i<aTargetValue.length; i++) {
				if(i > 0) {
					strwhere += " and ";						
				}
				strwhere += " " + thisField + " " + operate1 + aTargetValue[i] + operate2;
			}
			strwhere += ") ";
			myArray.push(strwhere);
		}
	}
}


function pushCondition(myArray, pageRealValue, operate1, operate2, thisField,stopTime,relationType) {  //压入查询条件
	try {
		form.RM_CURRENT_PAGE.value=1;
	} catch(e){}
	if(operate1 == undefined) {
		operate1 = " like '%";
		operate2 = "%' ";
	} else if(operate2 == undefined) {
		operate2 = "";
	}
	if(thisField == undefined) {
		thisField = pageRealValue;
	}
	if(relationType == undefined) {
		relationType = "single";
	}

	if(pageRealValue == null || pageRealValue == "" || getFormValue(pageRealValue) == "") {
		return false;
	}
	if(relationType == "single") {
		var temp = getFormValue(pageRealValue);
		if(stopTime=="1"){
		    temp = temp+" 23:59:59";
		}
        var tempVal = "";
		myArray.push(" " + thisField + " " + operate1 + temp + operate2);
	} else if(relationType == "multiple") {
		var targetValue = getFormValue(pageRealValue);
		if(targetValue.indexOf(",") < 0) {
			myArray.push(" " + thisField + " " + operate1 + targetValue + operate2);
		} else {
			var aTargetValue = targetValue.split(",");
			var strwhere = " (";
			for(var i=0; i<aTargetValue.length; i++) {
				if(i > 0) {
					strwhere += " and ";						
				}
				strwhere += " " + thisField + " " + operate1 + aTargetValue[i] + operate2;
			}
			strwhere += ") ";
			myArray.push(strwhere);
		}
	}
}

function writeValidateInfo(info, thisObj) {
	var inputName = getInputNameFromObject(thisObj);
	//info = inputName + "的输入有误！\n" + info;
	
	if(rmConfigValidateInfoType.indexOf("writePage") >= 0) {
		writeValidateInfoAfterObject(info, thisObj);
	}
	if(rmConfigValidateInfoType.indexOf("writeAlert") >= 0) {
		writeValidateInfoAlert(info, thisObj);
	}
	if(!rmTempStatusIsFocus) {
		setRmInputError(thisObj);
		rmTempStatusIsFocus = true;
	}
}

function setRmInputError(_frm) {
	try {
		//_frm.click();  // click会导致文件上传错误
		_frm.focus();
		_frm.style.borderStyle="dashed";
		_frm.style.borderColor="rgb(255,50,0)";
		if( _frm.value != null && _frm.value.length > 0 ){
			//_frm.style.backgroundColor = "highlight";
			//_frm.style.color = "white";
		} 
	} catch(e) {
		alert(e.message);
	}
	
	try {
		//_frm.select();
	} catch(e) {
		//alert(e.message);
	}
}

function writeValidateInfoAlert(info, thisObj) {
	if(!rmTempStatusIsAlert) {
		alert(info);
		rmTempStatusIsAlert = true;
	}
}

function writeValidateInfoAfterObject(info, thisObj) {  //写校验信息
	var validateInfoObj = null;
	thisObj = getValidatePosition(thisObj);
	if(thisObj.nextSibling != null && thisObj.nextSibling.nextSibling != null 
		&& thisObj.nextSibling.tagName != null && thisObj.nextSibling.tagName.toUpperCase() == "BR" 
		&& thisObj.nextSibling.nextSibling.tagName.toUpperCase() == "SPAN" && thisObj.nextSibling.nextSibling.className == "font_remain_prompt") {
		validateInfoObj = thisObj.nextSibling.nextSibling;
	} else if( info.length > 0) {
		thisObj.insertAdjacentHTML("afterEnd", "<br/><span class=font_remain_prompt></span>");
		validateInfoObj = thisObj.nextSibling.nextSibling;
	}
	if(validateInfoObj != null && (validateInfoObj.innerHTML.length > 0 || info.length > 0)) {
		validateInfoObj.innerHTML = info;
	}
}

function getValidatePosition(thisObj) {
	if(thisObj.nextSibling != null && thisObj.nextSibling.className == "refButtonClass") {
		thisObj = getValidatePosition(thisObj.nextSibling);
	} else if(thisObj.nextSibling != null && thisObj.nextSibling.type == "hidden"){
		thisObj = getValidatePosition(thisObj.nextSibling);
	} else if(thisObj.nextSibling != null && thisObj.nextSibling.nodeValue != null && thisObj.nextSibling.nodeValue.indexOf("到") == 1){
		thisObj = getValidatePosition(thisObj.nextSibling);
	} else if(thisObj.nodeValue != null && thisObj.nodeValue.indexOf("到") == 1) {
		thisObj = getValidatePosition(thisObj.nextSibling);
	}
	return thisObj;
}

function getInputNameFromObject(thisInput) {
	var inputName = thisInput.getAttribute("inputName");
	if ( inputName == null || inputName.length == 0 ){
		inputName = thisInput.name;
		if ( inputName == null || inputName.length == 0 ){
			inputName = "";
		}
	}
	return inputName;
}

//当前选中的页签
var childTableCurrentSelectIndex = 0;
function HovIt(objname) {
	if(childTableCurrentSelectIndex!=objname) {
		var btn1='tabInfotd'+objname;
		var btn2=btn1+'R';
		document.getElementById(btn1).className = 'tabInfoHovT';
		document.getElementById(btn2).className = "tabInfoHovTR";
	}
}
function DefIt(objname) {
	if(childTableCurrentSelectIndex!=objname) {
		var btn1='tabInfotd'+objname;
		var btn2=btn1+'R';
		document.getElementById(btn1).className = 'tabInfoDefT';
		document.getElementById(btn2).className = "tabInfoDefTR";
	}
}
function SelIt(objname,strurl) {
	var btn1;
	var btn2;
	var maxTabs = 0;
	if(childTableTabs != null && childTableTabs.length != null) {
		maxTabs = childTableTabs.length;
	}
	for(i=0;i<maxTabs;i++) {
		btn1='tabInfotd'+i;
		btn2=btn1+'R'; 
		if(document.getElementById(btn1) != null) {
			document.getElementById(btn1).className = 'tabInfoDefT';			
		}
		if(document.getElementById(btn2) != null) {
			document.getElementById(btn2).className = "tabInfoDefTR";			
		}
	}
	btn1='tabInfotd'+objname;
	btn2=btn1+'R';
	document.getElementById(btn1).className = 'tabInfoSelT';
	document.getElementById(btn2).className = "tabInfoSelTR";
	if(childTableCurrentSelectIndex != objname) {
		document.getElementById('tabInfo_frame').src = strurl;
		childTableCurrentSelectIndex = objname;
	}
}
function writeChildTableTabs(frameHeight, iSelect){
	var haveTab = false;
	for(var i=0; i<childTableTabs.length; i++){
		if(childTableTabs[i] != null) {
			haveTab = true;
			break;
		}
	}
	if(!haveTab) {
		return;
	}
	if(frameHeight == undefined) {
		frameHeight = 430;
	}
	if(iSelect == undefined) {
		iSelect = 0;  //默认第1个页签被选中
	}
	var tabstr = "";
	tabstr += "<table border='0' align='center' cellspacing='0' cellpadding='0' id='igtabtabInfo' width='100%' height='100%' style='Z-INDEX:108;'>";
	tabstr += "<tr>";
	tabstr += "<td>";
	tabstr += "<table id='tabInfo_tbl' cellspacing='0' cellpadding='0' border='0' width='100%'>";
	var maxRowWidthSum = document.body.clientWidth - 80;
	var currentRowWidthSum = 0;
	var validTabs = [];
	for(var i=0; i<childTableTabs.length; i++) {
		if(childTableTabs[i] != null) {
			validTabs.push(childTableTabs[i]);
		}
	}
	var aTagStr = new Array();
	var aTabStrTail = new Array();
	for(var i=0; i<validTabs.length; i++){
		var thisTab = "";
		thisTab += "<td>";
		thisTab += "<table cellspacing='0' cellpadding='0' border='0' height='100%' width='100%'>";
		thisTab += "<tr>";
		var leftWidth = 10 + validTabs[i][0].length * 12;
		if(validTabs.length > 0){
			var class1 = "tabInfoDefT";
			var class2 = "tabInfoDefTR";
			if(i==iSelect){
				class1 = "tabInfoSelT";
				class2 = "tabInfoSelTR";
			}
			var styleStr = "width:" + leftWidth + "px;";
			thisTab += "<td nowrap id='tabInfotd"+ i +"' align='center' class='" + class1 + "' style='" + styleStr + "' height='22px' unselectable='on' onmouseover='HovIt(\""+ i +"\")' onmouseout='DefIt(\""+ i +"\")' onclick='SelIt(\""+ i +"\",\""+ validTabs[i][1] +"\")'>"+ validTabs[i][0] +"</td>";
			thisTab += "<td nowrap id='tabInfotd"+ i +"R' class='" + class2 + "' width='6px' unselectable='on' onmouseover='HovIt(\""+ i +"\")' onmouseout='DefIt(\""+ i +"\")' onclick='SelIt(\""+ i +"\",\""+ validTabs[i][1] +"\")'>　</td>";
		}
		thisTab += "</tr>";
		thisTab += "</table>";
		thisTab += "</td>";
		//tail
		var tagProperty = "";
		if(validTabs[i].length > 2) {
			tagProperty = validTabs[i][2];
		}
		if(tagProperty.indexOf("tail=true") > -1) {
			aTabStrTail.push(thisTab);
		} else {
			aTagStr.push(thisTab);
		}
		if(currentRowWidthSum > maxRowWidthSum || i == validTabs.length-1) {
			tabstr += "<tr valign='center'><td><table style='width:100%'><tr>";
			for(var row in aTagStr) {
				tabstr += aTagStr[row];
			}
			tabstr += "<td nowrap style='font-size:2px;cursor:default;border-width:0px 0px 1px 0px;border-color:#949878;border-style:Solid;width:90%;'>&nbsp;</td>";
			for(var row in aTabStrTail) {
				tabstr += aTabStrTail[row];
			}
			tabstr += "</tr></table><td></tr>";
			currentRowWidthSum = 0;
			aTagStr = new Array();
			aTabStrTail = new Array();
		} else {
			currentRowWidthSum += (leftWidth + 6);
		}
	}
	tabstr += "</table>";
	tabstr += "</td>";
	tabstr += "</tr>";
	tabstr += "<tr>";
	tabstr += "<td id='tabInfo_cp' bgcolor='#FEFCFD' style='border-width:0px 1px 1px 1px;border-color:#949878;border-style:Solid;background-color:#FEFCFD;padding:3px 0px 0px 0px;height:"+ frameHeight +"px;width:100%;' align='center'>";
	tabstr += "<iframe id='tabInfo_frame' src='" + childTableTabs[iSelect][1] + "' style='' frameborder='0' width='100%' height='100%' scrolling='yes'></iframe>";
	tabstr += "<div width='100%' height='100%' id='tabInfo_empty' style='display:none'>　</div>";
	tabstr += "</td>";
	tabstr += "</tr>";
	tabstr += "</table>";
	document.getElementById("childTableTabsDiv").innerHTML = tabstr;
}

if(window.addEventListener) {
	window.addEventListener("load",doOnLoad_childTableTabs,false);
} else {
	window.attachEvent("onload", doOnLoad_childTableTabs);
}

var childTableTabsHeight = 500;
function doOnLoad_childTableTabs() {
	if(typeof childTableTabs != 'undefined' && document.getElementById("childTableTabsDiv") != null) {
		if(childTableTabs != null && childTableTabs.length > 0 && document.getElementById("childTableTabsDiv") != null) {
			writeChildTableTabs(childTableTabsHeight);
		}
	}
}

function writeBackMapToForm() {
	//为了防止页面抛异常
}
function writeBackMapToFormFix_name() {
	for(var i=0; i<indexArray.length; i++) {
		var inputName = indexArray[i];
		jQuery("input[name='" + inputName + "_name']", document.forms[0]).each(function() {
			if(jQuery(this).val() == "" 
				&& jQuery(this).attr("class").indexOf("reference") > -1
				&& jQuery("input[name='" + inputName + "']", document.forms[0]).val() != "") {
					jQuery(this).val(jQuery("input[name='" + inputName + "']", document.forms[0]).val());
			}
		});
	}
}

function getLayoutHiddenObjectById(id) {
	if(id == null) {
		return null;
	}
	var allInput = document.getElementsByTagName("input");
	for(var i=0; i<allInput.length; i++) {
		if(allInput[i].type == "hidden" && allInput[i].signName == "hiddenId" && allInput[i].value == id) {
			return allInput[i];
		}
	}
	return null;
}

/**
* 启用或禁用按钮
*/
function SetButtonEnabledState(sButtonID, bEnabledState){
	var e = new Error();
	try {
		var oBtn = eval("document.all." + sButtonID);
		oBtn.disabled = !bEnabledState;
		oBtn.style.cursor = ((bEnabledState) ? "hand" : "default");
	}catch (e) {
		return;
	}
	try {
		var oBtnImage = eval("document.all." + sButtonID + "_Image");
		oBtnImage.filters.Gray.enabled   = !bEnabledState;
		oBtnImage.filters.alpha.enabled  = !bEnabledState;
	}catch (e) {
	}
}

function rmSetupAuthorize(formName, accessType) {
	var element = window.document.getElementsByName(formName);
	if(element != null && element.length > 0) {
		if(element.length == 1) {
			var tempElement = element[0];
			if(tempElement.type == "hidden") {
				return;
			}
			if(accessType == "DISABLED") {
				rmSetDisableFormElement(tempElement);
			} else if (accessType == "READ") {
				rmSetReadFormElement(tempElement);
			} else if (accessType == "WRITE") {
				//什么也不做
			} else if (accessType == "") {  //表示没有任何权限
				rmHiddenFormElement(tempElement.parentNode.previousSibling, "visibility", "hiddenChild");
				rmHiddenFormElement(tempElement.parentNode, "visibility", "hiddenChild");
			}
		} else {
			//循环处理，如法炮制
			for(var i=0; i<element.length; i++) {
				var tempElement = element[i];
				if(accessType == "DISABLED") {
					rmSetDisableFormElement(tempElement);
				} else if (accessType == "READ") {
					rmSetReadFormElement(tempElement);
				} else if (accessType == "WRITE") {
					//什么也不做
				} else if (accessType == "") {  //表示没有任何权限
					rmHiddenFormElement(tempElement.parentNode, "visibility", "hiddenChild");
					rmHiddenFormElement(tempElement.parentNode.previousSibling, "visibility", "hiddenChild");
				}
			}
		}

	}
}

function rmSetDisableFormElement(formElement) {
	formElement.insertAdjacentHTML("beforeBegin", formElement.outerHTML);
	var disabledElement = formElement.previousSibling;
	disabledElement.disabled = true;
	disabledElement.style.backgroundColor = "#CCCCCC";
	disabledElement.style.color = "#000000";

	rmHiddenFormElement(formElement);

}

function rmSetReadFormElement(formElement) {
	if(formElement.rmtype == "accessory") {
		formElement.rmauthorize = "READ";
		return;
	}
	var thisElementValue = "";
	if(formElement.tagName != null && formElement.tagName.toUpperCase() == "SELECT") {
		for(var i=0; i<formElement.options.length; i++) {
			var thisOption = formElement.options[i];
			if(!thisOption.selected) {
				continue;
			}
			if(thisElementValue != "") {
				thisElementValue = ",";
			}
			thisElementValue += thisOption.text;
		}
	} else {
		thisElementValue = formElement.value;
	}
	if(formElement.tagName.toUpperCase() == "TEXTAREA") {
		thisElementValue = thisElementValue.replace(/\n/g, "<br>");
	}
	var readOnlyStr = "<span>";
	readOnlyStr += thisElementValue + "</span>";
	formElement.insertAdjacentHTML("beforeBegin", readOnlyStr);
	var readOnlyElement = formElement.previousSibling;
	
	rmHiddenFormElement(formElement);
}

function rmHiddenFormElementByName(formElement, hiddenType, hiddenContent) {
	var aEle = document.getElementsByName(formElement);
	for(var i=0; i<aEle.length; i++) {
		rmHiddenFormElement(aEle[i], hiddenType, hiddenContent);
	}
}

function rmHiddenFormElement(formElement, hiddenType, hiddenContent) {
	if(hiddenType == undefined ) {
		hiddenType = "display";
	}
	if(hiddenContent == undefined) {
		hiddenContent = "hiddenAll";
	}
	if(formElement == null) {
		return false;
	}
	if(hiddenContent == "hiddenAll") {
		if(hiddenType == "display") {
			formElement.style.display = "none";
		} else if(hiddenType == "visibility") {
			formElement.style.visibility = "hidden";
		}
		var thisImg = formElement.nextSibling;
		if(thisImg != null && thisImg.tagName == "IMG" && thisImg.className == "refButtonClass") {
			thisImg.style.display = "none";
		} else if(thisImg != null && thisImg.type == "hidden"){
			rmHiddenFormElement(thisImg.nextSibling);
		}
	} else if(hiddenContent == "hiddenChild") {
		for(var i=0; i<formElement.all.length; i++) {
			if(hiddenType == "display") {
				formElement.all[i].style.display = "none";
			} else if(hiddenType == "visibility") {
				formElement.all[i].style.visibility = "hidden";
			}
		}
		if(formElement.align == "right") {  //说明它是左边的说明文字
			formElement.innerText = " ";			
		}
	}
}


function enableDocumentByTagName(tagName) {
	var elements = document.getElementsByTagName(tagName);
	for(var i=0; i<elements.length; i++) {
		if(elements[i].disabled) {
			elements[i].disabled = false;
		}
	}
}

function findSelections(checkboxName, idName) {  //从列表中找出选中的id值列表
	var elementCheckbox = document.getElementsByName(checkboxName);  //通过name取出所有的checkbox
	var number = 0;  //定义游标
	var ids = null;  //定义id值的数组
	for(var i=0;i<elementCheckbox.length;i++){  //循环checkbox组
		if(elementCheckbox[i].checked) {  //如果被选中
			number += 1;  //游标加1
			if(ids == null) {
				ids = new Array(0);
			}
			if((typeof idName) == "string") {
				//加入选中的checkbox
				if(idName == "id" || idName == "value" || idName == null) {
					if(elementCheckbox[i].value != null && elementCheckbox[i].value.length > 0) {
						ids.push(elementCheckbox[i].value);
					}
				} else {
					if(elementCheckbox[i].getAttribute(idName) != null && elementCheckbox[i].getAttribute(idName).length > 0) {
						ids.push(elementCheckbox[i].getAttribute(idName));
					}
				}
			} else if((typeof idName) == "object" && idName.length > 0){
				var rowData = new Array();
				for(var j=0; j<idName.length; j++) {
					if(elementCheckbox[i].getAttribute(idName[j]) != null && elementCheckbox[i].getAttribute(idName[j]).length > 0) {
						rowData.push(elementCheckbox[i].getAttribute(idName[j]));
					}
				}
				ids.push(rowData);
			}
		}
	}
	return ids;
}

function findCheckboxes(checkboxName){	//从列表中找出选中的checkbox对象
	var checkboxes = new Array(); //定义要返回的被选中的checkbox数组
	var elementCheckbox = document.getElementsByName(checkboxName);  //通过name取出所有的checkbox
	for(var i=0;i<elementCheckbox.length;i++){  //循环checkbox组
		if(elementCheckbox[i].checked) {  //如果被选中
			checkboxes.push(elementCheckbox[i]);  //加入选中的checkbox
		}
	}
	return checkboxes;
}

function getRowHiddenId(e) {  //从事件取得本行的id
	e = window.event || e;
	var thisA = e.srcElement || e.target;
	if(thisA.tagName != "TD") {
		thisA = thisA.parentNode;
	}
	var thisTr = thisA.parentNode;  //定义本行的tr对象
	var thisHidden = getObjectByNameRecursive(thisTr, "hiddenId");  //从thisTr递归的取出name是hiddenId的对象
	if(thisHidden != undefined && thisHidden != null) {  //如果thisHidden不为空
		return thisHidden.value;
	} else {
		return null;
	}
}

function getObjectByNameRecursive(thisObj, thisName) {  //从thisObj递归的取出name是thisName的对象
	var rtHiddenInput = null;  //定义返回的变量
	for(var i=0; i<thisObj.childNodes.length; i++) {  //循环thisObj的字节点
		var tempObj = thisObj.childNodes[i];  //定义当前临时节点
		if(tempObj.getAttribute && tempObj.getAttribute("signName") == thisName) {  //如果当前临时节点的name等于thisName
			rtHiddenInput = tempObj;  //当前临时节点就是返回的对象
			break;  //完成了目标对象的查询,退出循环
		} else {
			rtHiddenInput = getObjectByNameRecursive(tempObj, thisName);  //递归的找自己的子节点
			if(rtHiddenInput != null) {  //如果返回值不为空
				break;  //完成了目标对象的查询,退出循环
			}
		}
	}
	return rtHiddenInput;
}

function defaultFormatDouble(value, fractionDigits) {
	var str = value + "";
	if(str.indexOf(".") > -1 && str.indexOf(".") + fractionDigits + 1 < str.length) {
		return str.substring(0,str.indexOf(".") + fractionDigits + 1);
	} else {
		return str;
	}
}

//返回strData的长度(Unicode长度为2，非Unicode长度为1)
function getStrLength(str){
	var len = 0;
	for(var i=0;i<str.length;i++){
		if ( str.charCodeAt(i) < 0 || str.charCodeAt(i) > 255)
			len += 2;
		else
			len ++;
	}
	return len;
}

function changeSearch_onClick(thisButton) {
	if(thisButton.value == "更多条件") {
		thisButton.value = "简单条件";
		document.getElementById("div_advanced").style.display = "block";
	} else if(thisButton.value == "简单条件") {
		thisButton.value = "更多条件";
		document.getElementById("div_advanced").style.display = "none";
	}
	resetListTableHeight();
}
var defaultListTableHeightPara = 82;
function resetListTableHeight() {
	try {
		var thisObj = null;
		var thisTable = document.getElementsByTagName("TABLE");
		for (var i=0; i<thisTable.length; i++) {
			var thisTh = getThFromTable(thisTable[i]);  //thisTable[i].getElementsByTagName("th")
			if (thisTh != null && thisTh != "") {
				//if (thisTh.className == "listCss") {
				///支持多class
				if (thisTh.className.match(new RegExp("(^|\\s)" + "listCss" + "( \\s|$)"))){
					thisObj = thisTable[i];
				}
			}
		}
		var div_scroll = document.getElementById("strutslayout_div_scroll");
		if(div_scroll != null) {
			div_scroll.style.width = div_scroll.parentNode.parentNode.parentNode.parentNode.parentNode.clientWidth - div_scroll.parentNode.parentNode.parentNode.children[0].children[0].clientWidth;
		}
		var outerDiv =thisObj.parentNode.parentNode.parentNode.parentNode.parentNode;
		if(outerDiv.tagName == "TABLE") {
			outerDiv = outerDiv.parentNode;
		}
		
		var oo = getLTWH(outerDiv);
		var newHeight = (window.document.body.clientHeight-parseInt(oo.top)-defaultListTableHeightPara);
		if(parent != null && parent.name == "mainFrame1") {
			newHeight = (parent.document.body.clientHeight-parseInt(oo.top)-defaultListTableHeightPara);
		}
		//alert("parent.name=" + parent.name + "\nwindow.name=" + window.name + "\nparent.document.body.clientHeight=" + parent.document.body.clientHeight + "\nwindow.document.body.clientHeight=" + window.document.body.clientHeight + "\nparseInt(oo.top)=" + parseInt(oo.top) + "\nouterDiv.style.height:" + outerDiv.style.height + "\nnewHeight:" + newHeight);
		outerDiv.style.height = newHeight;
	} catch(e){
		//alert("resetListTableHeight:" + e.message);
	}	
}

/*
兼容IE和FF返回目标对象包含边框的left、top、width、height值。其中left、top是相对于document.body的坐标。
需要参数1个： [DOM]o=[DOM] 要取值的对象。
*/
function getLTWH(o)
{
    function getCurrentStyle(style)
    {
        var number=parseInt(o.currentStyle[style]);
        return isNaN(number)?0:number;
    }
    function getComputedStyle(style)
    {
        return parseInt(document.defaultView.getComputedStyle(o,null).getPropertyValue(style));
    }
    var oLTWH=
    {
        "left":o.offsetLeft,
        "top":o.offsetTop,
        "width":o.offsetWidth,
        "height":o.offsetHeight
    };
    while(true)
    {
        o=o.offsetParent;
        if(o==(document.body&&null))break;
        oLTWH.left+=o.offsetLeft;
        oLTWH.top+=o.offsetTop;
        var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera ; // 判断是否IE
        var isFF = userAgent.indexOf("Firefox") > -1 ; // 判断是否Firefox
        if(isIE)
        {
            oLTWH.left+=getCurrentStyle("borderLeftWidth");
            oLTWH.top+=getCurrentStyle("borderTopWidth");
        }
        if(isFF)
        {
            oLTWH.left+=getComputedStyle("border-left-width");
            oLTWH.top+=getComputedStyle("border-top-width");
        }
    }
    return oLTWH;
}

//从Table标签里面发现Th标签
function getThFromTable(thisObj) {
	if (thisObj != undefined && thisObj != null && (typeof thisObj == "object") ) {
		if (!thisObj.hasChildNodes()) {
			return null;
		} else {
			for(var i=0; i<thisObj.childNodes.length; i++) {
				var thisChild = thisObj.childNodes[i];
				if (thisChild.tagName != undefined) {
					if (thisChild.tagName.toLowerCase() == "th") {
						return thisChild;
					} else {
						var tempResult = getThFromTable(thisChild);
						if (tempResult != null) {
							return tempResult;
						}
					}
				}
			
			}
		}
	}
}

function autoPatchParentIdName(parentId) {
	if(document.form[0][parentId + "_name"] != null) {
		document.form[0][parentId + "_name"].className = "text_field_reference_readonly";
		if(document.form[0][parentId + "_name"] != null) {
			document.form[0][parentId + "_name"].value = document.form[0][parentId + "_name"].value;
		} else {			
			document.form[0][parentId + "_name"].value = document.form[0][parentId].value;
		}
	}
}

//begin rm-waiting.js
function initProgress() {
	if(document.getElementById("progress") == null) {
		jQuery("body").append("<div id='progress' style='z-index:1000;position:absolute;display:none;border:1px black solid;padding:3px 3px 3px 3px;background-color:#FFFFFF;font-family: sans-serif;font-size: 90%'><img src='"+dir_base+"/images/waiting.gif' align='texttop'>请稍候...</div>");
	}
	if(document.getElementById("msgdlg") == null) {
		jQuery("body").append("<div id='msgdlg' title='消息'></div>");
	}
}

// 显示结果信息的对话框
function showMessage(msg, options) {
    $("#msgdlg").html(msg);
    $("#dialog:ui-dialog").dialog("destroy");
    $("#msgdlg").dialog({
		modal: true,
		width: 400,
		height: 300,
		buttons: {
			OK: function () {
				$(this).dialog("close");
				window.location.href = options.redirectUrl;
			}
		}
    });
}

// 显示结果信息的对话框
function showErrorMessage(msg, options) {
	$("#msgdlg").html(msg);
	$("#dialog:ui-dialog").dialog("destroy");
	$("#msgdlg").dialog({
		modal: true,
		width: 600,
		height: 400,
		buttons: {
			OK: function () {
				$(this).dialog("close");
			}
		}
	});
}
  	
function showWait(e) {
	try {
		initProgress();
		e = window.event || e;
		var srcEle = e.srcElement || e.target;
        top.isloaded = 1;
        if (srcEle.type != "button" && srcEle.type != "BUTTON") {
        	return false;
        } 
        jQuery(window.document.getElementById("progress")).css("top", e.clientY-10);
        jQuery(window.document.getElementById("progress")).css("left", e.clientX-10);
        appInstallWaitShow(); 
	} catch(e) {
	}
}

function hideWait() {
	appInstallWaitHide(); 
	enableAllButton();
}

function appInstallWaitShow() {
	initProgress();
	window.document.getElementById("progress").style.display = "block";
}

function appInstallWaitHide() {
	initProgress();
	window.document.getElementById("progress").style.display = "none";
}

function enableAllButton() {
	jQuery(":button").each(function(){
		this.disabled = false;
	});
}

function disableAllButton() {
	jQuery(":button").each(function(){
		this.disabled = true;
	});
}
//end waiting.js

/**
 * 选择有效的checkbox值
 * @param checkboxName  
 * @param hiddenName 隐藏域的名字
 * @param checkValue 和隐藏域的比较值
 * @param isEqual    判断类型   true 等于有效  false 不等于有效
 * @param printInfo  错误提示信息
 * @returns
 */
function getValidatedIds(checkboxName,hiddenName,checkValue,isEqual,printInfo){
	var elementCheckbox =  document.getElementsByName(checkboxName); //取得多选框的选择项
	var hiddenVbillstatus = document.getElementsByName(hiddenName);
	var ids = new Array(0);  //定义id值的数组
	for(var i=0;i<elementCheckbox.length;i++){  //循环checkbox组
		if(elementCheckbox[i].checked) {  //如果被选中
			if(isEqual){ //判断类型 true等于有效  false不等于有效
				if(hiddenVbillstatus[i].value == checkValue){ //等于有效
					ids.push(elementCheckbox[i].value);  //加入有效的checkbox
				}else{
					alert(printInfo);
					return null;
				}
			}else{
				if(hiddenVbillstatus[i].value != checkValue){ //不等于有效
					ids.push(elementCheckbox[i].value);  //加入有效的checkbox
				}else{
					alert(printInfo);
					return null;
				}
			}
		}
	}
	if(ids.length == 0){  //未选择选项  提示
		alert("请选择记录!");
		return null;
	}
	return ids;
}

/**
 * 数字加千分位
 * @param intInput
 * @returns
 */
function FormatNumber(intInput) {
    //将输入参数转换为字符串形式
    var strInput = Math.abs(intInput).toString();
    //如果有小数，把小数部分提取出来
    var strXS = "";
    if (strInput.indexOf(".", 0) != -1) {
        strXS = strInput.substring(strInput.split(".")[0].length, strInput.length);
        strInput = strInput.split(".")[0];
    }
    //获取输入参数的长度
    var iLen = strInput.length;
    //如果输入参数的长度小于等于3，则直接返回
    //否则，再进行处理
    if (iLen <= 3) {
    	if(strXS.length == 0){//以两位小数形势显示
        	strXS = ".00";
        }else if(strXS.length ==2 ){
        	strXS = strXS + "0";
        }
        return strInput + strXS;
    } else {
        //首先取模，以作为起始点，每3位截取一次存入数组，最后再进行拼接返回
        var iMod = iLen % 3;
        //每3位截取的起始点  
        var iStart = iMod;
        //每3位截取的存储数组
        var aryReturn = [];
        //循环处理：每3位截取一次 存储到数组
        while (iStart + 3 <= iLen) {
            aryReturn[aryReturn.length] = strInput.substring(iStart, iStart + 3);
            iStart = iStart + 3;
        }
        //将数组中的数据连接起来
        aryReturn = aryReturn.join(",");
        //处理输入参数长度不是3的倍数的情况
        if (iMod != 0) {
            aryReturn = strInput.substring(0, iMod) + "," + aryReturn;
        }
        //处理负数的情况
        if (intInput < 0) { aryReturn = "-" + aryReturn; }
        if(strXS.length == 0){//以两位小数形势显示
        	strXS = ".00";
        }else if(strXS.length ==2 ){
        	strXS = strXS + "0";
        }
        return aryReturn + strXS;
    }
}

var currentNamespace = "";
jQuery(function(){
	//Tabs
	jQuery('#rowTabs').each(function() {
		jQuery(this).tabs({
			create : function(e, ui) {
				if(ui.panel.selector.length > "#rowTabs-".length) {
					currentNamespace = ui.panel.selector.substring("#rowTabs-".length);
				}
			},
			activate : function(e, ui) {
				if(ui.newPanel.selector.length > "#rowTabs-".length) {
					currentNamespace = ui.newPanel.selector.substring("#rowTabs-".length);
				}
			}
		});
	});

	//hover states on the static widgets
	jQuery('#dialog_link, ul#icons li').hover(
		function() { jQuery(this).addClass('ui-state-hover'); }, 
		function() { jQuery(this).removeClass('ui-state-hover'); }
	);
});

function findRowTable(rowTableNamespace) {
	return jQuery("table[class='rowTable'][namespace='" + rowTableNamespace + "']").get(0);
}

var rowConfig={
	focusColor:"#EBF3F7", //默认聚焦颜色
	selectedColor:"#DEEEF8", //默认选中行的颜色
	oddColor:"#FFFFFF", //默认表体奇数行颜色
	evenColor:"#FFFFFF" //默认表体偶数行颜色 "#F6F6F6"
};
var rowBgColors = [rowConfig.evenColor, rowConfig.oddColor];
function addRow_onClick(rowTableNamespace){  //插入单条数据
	if(rowTableNamespace == null) {
		rowTableNamespace = currentNamespace;
	}
	var rowTable = findRowTable(rowTableNamespace);
	var rowPrototype = getRowPrototype(rowTable);
	if(rowPrototype == null) {
		alert("未设置原型行");
		return false;
	}
	var rowNew = rowTable.insertRow(-1);
	for(var i=0; i<rowPrototype.cells.length; i++) {
		var cellNew = rowNew.insertCell(-1);
		cellNew.innerHTML = rowPrototype.cells[i].innerHTML;
		if(rowPrototype.cells[i].style.display != null) {
			cellNew.style.display = rowPrototype.cells[i].style.display;
		}
	}
	jQuery(rowNew).each(function(){
		jQuery(":input", jQuery(this)).each(function(i){
			this.disabled = false;
			jQuery(this).attr("validate", jQuery(this).attr("validatePrototype"));
			jQuery(this).addClass("rowNewInput");
			if(this.name != null) {
				this.name = rowTableNamespace + "^" + jQuery(this).attr("name");
			}
		});
		jQuery(this).dblclick(function(){
			var currentCbx = jQuery("input:checkbox[name*='rmRowSelecter']:not(disabled)", jQuery(this));
			currentCbx.prop("checked", !currentCbx.prop("checked"));
			resetRowStyle(this);
		});
	});
	initRowsStyle(rowTable);
	return rowNew;
}

function resetRowStyle(tr) {
	var currentCbx = jQuery("input:checkbox[name*='rmRowSelecter']:not(disabled)", jQuery(tr));
	if(currentCbx.prop("checked")) {
		jQuery(tr).css("background-color", rowConfig.selectedColor);
	} else {
		jQuery(tr).css("background-color", jQuery(tr).attr("defaultColor"));
	}
}

function initRowsStyle(rowTable) {
	jQuery("tr", jQuery(rowTable)).each(function(i){
		if(i>1){
			jQuery(this).css("background-color", rowBgColors[(i+1)%2]);
			jQuery(this).attr("defaultColor", rowBgColors[(i+1)%2]);
			jQuery(this).mouseover(function(){
				jQuery(this).css("background-color", rowConfig.focusColor);
			});
			jQuery(this).mouseout(function(){
				resetRowStyle(this);
			});
		}
	});
	initStyleBehavior();
}

function getRowPrototype(rowTable) {
	if(rowTable == null || rowTable.rows == null || rowTable.rows.length == 0) {
		return null;
	}
	for(var i=0; i<rowTable.rows.length; i++) {
		if(jQuery(rowTable.rows[i]).hasClass("rowPrototype")) {
			return rowTable.rows[i];
		}
	}
}

function removeRow_onClick(rowTableNamespace) {
	if(rowTableNamespace == null) {
		rowTableNamespace = currentNamespace;
	}
	var rowTable = findRowTable(rowTableNamespace);
	if(rowTable.rows.length <= 2){
		alert("还没有要选中的记录");
	}
	var selecter = jQuery("input:checked[name*='rmRowSelecter']:not(disabled)", rowTable);
	if(selecter.length==0 && rowTable.rows.length>2){
		  alert("请选择一条记录!");
		  return false;
	}
	selecter.each(function() {
		if(this.checked) {
			var thisTr = this.parentNode.parentNode;
			var thisTable = thisTr.parentNode;
			trIndex = getTrIndex(thisTable, thisTr);
			if(trIndex >= 0) {
				thisTable.deleteRow(trIndex);
			}
		}
	});
}

function getTrIndex(table, tr) {
	for(var i=0; i<table.rows.length; i++) {
		if(tr == table.rows[i]) {
			return i;
		}
	}
	return -2;
}
/*根据浏览器（非IE）来获取innerHTML的value问题 start*/
var Sys = {};
var ua = navigator.userAgent.toLowerCase();
try{
	if (window.ActiveXObject)
		Sys.ie = ua.match(/msie ([\d.]+)/)[1]
	else if (document.getBoxObjectFor)
		Sys.firefox = ua.match(/firefox\/([\d.]+)/)[1]
	else if (window.MessageEvent && !document.getBoxObjectFor)
		Sys.chrome = ua.match(/chrome\/([\d.]+)/)[1]
	else if (window.opera)
		Sys.opera = ua.match(/opera.([\d.]+)/)[1]
	else if (window.openDatabase)
		Sys.safari = ua.match(/version\/([\d.]+)/)[1];
}catch(e){}

function setFFCHROME(obj,obj1){//FF下手动加值
	//input 暂时实用文本域，有radio,checkbox,另外处理
	var inputs=obj.getElementsByTagName("input");
	var html=obj.innerHTML;
	var allins=html.match(/<input .+?>/g);
	for(var i=0;i<inputs.length;i++){
		var tmp=allins[i].replace(/value=""/,'value="'+inputs[i].value+'"');
		html=html.replace(allins[i],tmp);
	}
	//select
	var selects=obj.getElementsByTagName("select");
	for(var i=0;i<selects.length;i++){
	    var tmp=selects[i].value;
	    html=html.replace('<option value="'+tmp+'">','<option value="'+tmp+'" selected>');
	}
	var tareas=obj.getElementsByTagName("textarea");
	for(var i=0;i<tareas.length;i++){
		var tmp=tareas[i].value;
		var tmp1=tareas[i].outerHTML;
		var tmp2=tmp1.replace("><",">"+tmp+"<");
		html=html.replace(tmp1,tmp2);
	}
	obj1.innerHTML=html;
}


/*火狐下需加这段代码以便支持outerHTML
if(typeof(HTMLElement)!="undefined" && !window.opera){
	HTMLElement.prototype.__defineGetter__("outerHTML",function(){
		var a=this.attributes, str="<"+this.tagName, i=0;
		for(i=a.length-1;i>=0;i--)
		if(a[i].specified)
		str+=" "+a[i].name+'="'+a[i].value+'"';
		if(!this.canHaveChildren)
		return (str+" />").toLowerCase();
		return (str+">"+this.innerHTML+"</"+this.tagName+">").toLowerCase();
	});
	HTMLElement.prototype.__defineSetter__("outerHTML",function(s){
		var d = document.createElement("DIV"); d.innerHTML = s;
		for(var i=0; i<d.childNodes.length; i++)
		this.parentNode.insertBefore(d.childNodes[i], this);
		this.parentNode.removeChild(this);
	});
	HTMLElement.prototype.__defineGetter__("canHaveChildren",function(){
		return !/^(area|base|basefont|col|frame|hr|img|br|input|isindex|link|meta|param)$/.test(this.tagName.toLowerCase());
	});
}*/
/*根据浏览器（非IE）来获取innerHTML的value问题 end*/

function copyRow_onClick(rowTableNamespace) {
	if(rowTableNamespace == null) {
		rowTableNamespace = currentNamespace;
	}
	var rowTable = findRowTable(rowTableNamespace);
	if(rowTable.rows.length <= 2){
		alert("还没有要选中的记录");
	}
	var selecter = jQuery("input:checkbox[name*='rmRowSelecter']:not(disabled)", rowTable);
	var selectedRowCount = 0;
	selecter.each(function() {
		if(this.checked) {
			selectedRowCount ++;
		}
	});
	if(selectedRowCount > 1) {
		alert("复制时最多选中一行");
		return false;
	}
	if(selectedRowCount == 0 && rowTable.rows.length >= 3){
		alert("请选择一条记录!");
		return false;
	}
	selecter.each(function() {
		if(this.checked) {
			var thisTr = this.parentNode.parentNode;
			var thisTable = thisTr.parentNode;
			var rowNew = thisTable.insertRow(-1);
			for(var i=0; i<thisTr.cells.length; i++) {
				var cellNew = rowNew.insertCell(-1);
				if(Sys.ie){
					cellNew.innerHTML = thisTr.cells[i].innerHTML;
				} else {
					setFFCHROME(thisTr.cells[i],cellNew);
				}
				if(thisTr.cells[i].style.display != null) {
					cellNew.style.display = thisTr.cells[i].style.display;
				}
			}
			if(this.name != null) {
				this.name = rowTableNamespace + "^" + jQuery(this).attr("name");
			}
			jQuery(":input", rowNew).each(function(i){
				if(this.name != null && this.name.indexOf(rowTableNamespace + "^") != 0) {
					this.name = rowTableNamespace + "^" + jQuery(this).attr("name");
				}
			});
			jQuery(rowNew).dblclick(function(){
				var currentCbx = jQuery("input:checkbox[name*='rmRowSelecter']:not(disabled)", jQuery(this));
				currentCbx.prop("checked", !currentCbx.prop("checked"));
				resetRowStyle(this);
			});
		}
	});
	initRowsStyle(rowTable);
}

function writeBackListToRowTable(rowTableNamespace, jsonArray) {
	var rowTable = findRowTable(rowTableNamespace);
	for(var i=0; i<jsonArray.length; i++) {
		var trNew = addRow_onClick(rowTableNamespace);
		var json = jsonArray[i];
		for(var key in json) {
			jQuery(":input[name='" + rowTableNamespace + "^" + key + "']", trNew).each(function() {
				if(json[key] != undefined) {
					jQuery(this).val(json[key]);
				}
			});
		}
		
	}
}

function findInput(eventObj, inputName) {
	var thisTr = eventObj.parentNode.parentNode;
	var rowTableNamespace = thisTr.parentNode.parentNode.getAttribute("namespace");
	return jQuery("input[name='" + rowTableNamespace + "^" + inputName + "']", thisTr).get(0);
}
