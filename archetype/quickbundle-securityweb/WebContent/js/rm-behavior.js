var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera
var isMaxthon = userAgent.indexOf("Maxthon") > -1 ; //判断是否傲游3.0
var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera ; //判断是否IE
var isFF = userAgent.indexOf("Firefox") > -1 ; //判断是否Firefox
var isSafari = userAgent.indexOf("Safari") > -1 && userAgent.indexOf("Chrome") < 1 ; //判断是否Safari
var isChrome = userAgent.indexOf("Chrome") > -1 ; //判断是否Chrome

function rmDebug(str) {
	if(!jQuery("#rmDebugTextarea").length) {
		jQuery("body").append("<div><textarea id='rmDebugTextarea' style='z-index:999;' rows='50' cols='180'></textarea></div>");
	}
	jQuery("#rmDebugTextarea").val(str);
}

//jQuery(window).error(function(){
//	return true;
//});
try{
	window.onerror = function(msg, url, line) {
		//alert(msg + "\n" + url + "\n" + line + "\n" + window.location.href);
		return false;
	};
}catch(e){}

jQuery(function(){
	try{
		//checkAllHiddenInputId(); //check readonly input without hiddenInputId
		if((typeof resetListJspQueryInputValue) != "undefined") {
			resetListJspQueryInput(resetListJspQueryInputValue);
		}
		if((typeof hiddenQueryDivValue) != "undefined") {
			hiddenQueryDiv(hiddenQueryDivValue);
		}
		jQuery("#div_funcNode").show();
		if(!systemDebugMode) {
			//jQuery(document)[0].oncontextmenu = function() {return false;} //disable right mouse click
		}
	}catch(e) { 
	   //alert(e.message); 
	}
	
	jQuery("#button_reset").each(function(){
		jQuery(this).click(function(event){
			jQuery("form").each(function(){
				this.reset();
			});
		});
	});
	try {
		initDivQueryArea();
	}catch(e) {
		
	}

});

function checkAllHiddenInputId() {
	for(var i=0; i<document.forms.length; i++) {
		var thisForm = document.forms[i];
		for(var j=0; j<thisForm.elements.length; j++) {
			var thisElement = thisForm.elements[j];
			if(thisElement.className == null || thisElement.className.indexOf("reference") < 0) {
				continue;
			}
			if( thisElement.hiddenInputId != null && thisElement.hiddenInputId != "" ) {
				continue;
			}
			if(thisElement.nextSibling != null && thisElement.nextSibling.onclick != null ) {
				//this is time reference
			} else {
				alert("form element:" + thisElement.name+ "(" + thisElement.inputName + ")is a reference input,\nbut it has not define hiddenInputId\n if it has not some hidden field,this is right,otherwise you must add it" );
			}
		}
	}
}

var defaultFieldColumn = -1;
function resetListJspQueryInput(bValue) {
	if(!bValue){
		return;
	}
	var fieldColumn = Math.floor(document.body.clientWidth/350);
	if(fieldColumn < 2) {
		fieldColumn = 2;
	} else if(fieldColumn > 4) {
		fieldColumn = 4;
	}
	if(defaultFieldColumn > 1) {
		fieldColumn = defaultFieldColumn;
	}
	try {
		relayoutInput(fieldColumn);
	}catch(e){};
}

function tableCanRelayout(qTable) {
	if(qTable == null || qTable.rows.length < 3) {
		return false;
	}
	if(qTable.className == "tableHeader") {
		return false;
	}
	if(qTable.rows[0].cells.length != 4) {
		return false;
	} else {
		for(var i=0; i<qTable.rows[0].cells.length; i++) {
			var thisCell = qTable.rows[0].cells[i];
			if(thisCell.innerHTML.trim() != "&nbsp;") {
				return false;
			}
		}
	}
	for(var i=1; i<qTable.rows.length; i++) {
		var thisRow = qTable.rows[i];
		if(thisRow.cells.length == 4 && thisRow.style.display != "none") {
			if((thisRow.cells[2].innerHTML.trim() == "" || thisRow.cells[2].innerHTML.trim() == "&nbsp;") && 
					(thisRow.cells[3].innerHTML.trim() == "" ||thisRow.cells[3].innerHTML.trim() == "&nbsp;")) {
				continue;
			}
			return false;
		}
	}
	return true;
}

function relayoutInput(fieldColumn) {
	var qTable = null;
	if(document.getElementById("div_simple") != null && document.getElementById("div_simple").getElementsByTagName("TABLE").length > 0) {
		qTable = document.getElementById("div_simple").getElementsByTagName("TABLE")[0];
	} else if(document.forms.length > 0 && document.forms[0].getElementsByTagName("TABLE").length > 0) {
		qTable = document.forms[0].getElementsByTagName("TABLE")[0];
	}
	if(!tableCanRelayout(qTable)) {
		return;
	}
	var tempFirstTr = null;
	//move td object
	var eles = [];
	for(var i=1; i<qTable.rows.length; i++) {
		eles.push({
			td1: qTable.rows[i].cells[0].innerHTML,
			td2: qTable.rows[i].cells[1].innerHTML,
			cols: qTable.rows[i].cells[1].colSpan,
			trDisplay: qTable.rows[i].style.display
		});
	}
	
	//delete original tr object
	var deleteIndex = 1;
	for(var i=0; i<eles.length; i++) {
		if(eles[i].trDisplay == "none" || eles[i].cols == 1) {
			qTable.deleteRow(deleteIndex);
		} else {
			deleteIndex += 1;
		}
	}
	if(qTable.rows[0].cells.length != fieldColumn*2) {
		qTable.deleteRow(0);
		firstTr = qTable.insertRow(0);
		for(var i=0; i<fieldColumn*2; i++) {
			var tempTd = firstTr.insertCell(-1);
			tempTd.innerHTML = "&nbsp;";
		}
	}

	var currentTr = null;
	var insertIndex = 1;
	var hiddenEles = [];
	for(var i=0; i<eles.length; i++) {
		if(eles[i].trDisplay == "none") {
			hiddenEles.push(eles[i]);
			continue;
		}
		if(eles[i].cols == 1) {
			if(currentTr == null) {
				currentTr = qTable.insertRow(insertIndex);
			}
			var tempTd1 = currentTr.insertCell(-1);
			tempTd1.innerHTML = eles[i].td1;
			tempTd1.align = "right";
			currentTr.insertCell(-1).innerHTML = eles[i].td2;
			if(currentTr.cells.length == fieldColumn*2) {
				currentTr = null;
				insertIndex ++;
			}
		} else if(eles[i].cols == 3) {
			if(currentTr != null) {
				for(var j=0; j<=(fieldColumn*2-currentTr.cells.length); j++) {
					currentTr.insertCell(-1).innerHTML = "&nbsp;";
				}
				currentTr = null;
				insertIndex ++;
			}
			qTable.rows[insertIndex].cells[1].colSpan = fieldColumn*2-1;
			insertIndex ++;
		}
	}
	if(currentTr != null && currentTr.cells.length > 0 && currentTr.cells[1].colSpan == 1 && parseInt(currentTr.cells.length) < parseInt(fieldColumn*2)) {
		var toAppendTdNum = fieldColumn*2 - currentTr.cells.length;
		currentTr.cells[currentTr.cells.length-1].colSpan = currentTr.cells[currentTr.cells.length-1].colSpan + toAppendTdNum;
	}
	if(hiddenEles.length > 0) {
		var hiddenTr = qTable.insertRow(-1);
		hiddenTr.style.display = "none";
		var hiddenTd = hiddenTr.insertCell(-1);
		hiddenTd.colSpan = fieldColumn*2;
		var totalHtml = "";
		for(var i=0; i<hiddenEles.length; i++) {
			totalHtml += (hiddenEles[i].td1 + hiddenEles[i].td2);
		}
		hiddenTd.innerHTML = totalHtml;
	}
	if(qTable.rows.length > 0) {
		for(var i=0; i<qTable.rows[0].cells.length; i++) {
			qTable.rows[0].cells[i].width = (100/(fieldColumn*2)) + "%";
		}
	}
	if(qTable.className == "mainTable" && qTable.doInit) {
		qTable.doInit();
	}
	
	try{
		if(!(/msie/i.test(navigator.userAgent))) {
			if(writeBackMapToForm) {
				writeBackMapToForm();
			}
			if(writeBackMapToFormFix_name) {
				writeBackMapToFormFix_name();
			}
		}
	}catch(e){}
}

//insert a button, hidden query div
function hiddenQueryDiv(bValue) {
	if(!bValue){
		return;
	}
	if(document.getElementById("button_ok") == null) {
		return;
	}
	if(document.getElementById("button_moreCondition") != null) {
		document.getElementById("button_moreCondition").style.display = "none";
	}
	if(document.getElementById("div_simple") != null && document.getElementById("div_simple").firstChild != null) {
		var divSimple = document.getElementById("div_simple");
		
		divSimple.insertAdjacentHTML("beforeBegin", '<div style="padding:5px 0px 0px 30px" align="left"><input type="button" class="button_ellipse" id="button_moreConditionControl" onclick="if(document.getElementById(\'div_simple\').style.display == \'none\') {document.getElementById(\'div_simple\').style.display = \'block\';this.value=\'\u67e5\u8be2\u6761\u4ef6<<\'} else {document.getElementById(\'div_simple\').style.display = \'none\';this.value=\'\u67e5\u8be2\u6761\u4ef6>>\';};resetListTableHeight();" value="\u67e5\u8be2\u6761\u4ef6>>"/></div>');
		if(document.forms[0].queryCondition.value == "") {
			divSimple.style.display = "none";
		} else {
			divSimple.style.display = "block";
		}
		divSimple.insertAdjacentHTML("afterBegin", '<div style="position:absolute;top:5px;left:140px;"><span id="span_button"></span></div>');
		if(document.getElementById("button_reset") != null) {
			document.getElementById("span_button").insertAdjacentElement("afterBegin", document.getElementById("button_reset"));
		}
		document.getElementById("span_button").insertAdjacentText("afterBegin", " ");
		document.getElementById("span_button").insertAdjacentElement("afterBegin", document.getElementById("button_ok"));
	}
}

function initDivQueryArea() {
	if(document.getElementById("div_queryArea") != null) {
		document.getElementById("div_queryArea").insertAdjacentHTML("beforeBegin",
		'<div style="padding:5px 0px 0px 30px" align="left"><input type="button" class="button_ellipse" id="button_moreConditionControl" onclick="if(document.getElementById(\'div_queryArea\').style.display == \'none\') {document.getElementById(\'div_queryArea\').style.display = \'block\';this.value=\'\u67e5\u8be2\u6761\u4ef6<<\'} else {document.getElementById(\'div_queryArea\').style.display = \'none\';this.value=\'\u67e5\u8be2\u6761\u4ef6>>\';};resetListTableHeight();" value="\u67e5\u8be2\u6761\u4ef6>>"/></div>'		
		);
		document.getElementById("div_queryArea").style.display = "none";
	}
	
}

function pup(){
	jQuery("#Layer1").css({"display":"block","top":(document.documentElement.scrollTop+40)+"px"});
	jQuery("#ma").css({"display":"block","height":document.documentElement.scrollHeight+"px"});
	jQuery("select").css("display","none");
}
function closeDiv(){
	jQuery("#Layer1").css("display","none");
	jQuery("#ma").css("display","none");
	jQuery("select").css("display","");
}

String.prototype.endWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substring(this.length-str.length)==str)
	  return true;
	else
	  return false;
	return true;
};

String.prototype.startWith=function(str){
	if(str==null||str==""||this.length==0||str.length>this.length)
	  return false;
	if(this.substr(0,str.length)==str)
	  return true;
	else
	  return false;
	return true;
};


jQuery(function(){ //二级菜单
	jQuery(".div_buttons").each(function(){
		var buttonClick = jQuery(this).prev();
		var divButtons = jQuery(this);
		var outerContainer = jQuery("<span/>").insertBefore(buttonClick);
		buttonClick.appendTo(outerContainer);
		jQuery(this).appendTo(outerContainer);
		var divButtons_onClick = function(event){
			event.stopPropagation();
			var offset = jQuery(event.target).offset();
			divButtons.css({top:(offset.top + jQuery(event.target).height() + 3)+"px", left:offset.left});
			if(divButtons.css("display") == "block") {
				divButtons.css("display", "none");
			} else {
            	divButtons.css("display", "block");
			}
		};
		buttonClick.click(divButtons_onClick);
		divButtons.click(divButtons_onClick);
	});
	
	jQuery(document).click(function(event) {
		jQuery(".div_buttons").css("display", "none");
	});
});

jQuery(function(){ //所有按钮
	jQuery(".button_ellipse").each(function(){
		jQuery(this).attr("class", "btn3_mouseout");
		jQuery(this).mouseover(function(){
			jQuery(this).attr("class", "btn3_mouseover");
		});
		jQuery(this).mouseout(function(){
			jQuery(this).attr("class", "btn3_mouseout");
		});
		jQuery(this).mousedown(function(){
			jQuery(this).attr("class", "btn3_mousedown");
		});
		jQuery(this).mouseup(function(){
			jQuery(this).attr("class", "btn3_mouseup");
		});
		jQuery(this).click(function(event){
			var onclickto = jQuery(this).attr("onclickto");
			if(jQuery(this).next().attr("class") != "div_buttons") { //只要不是div_buttons之前的按钮，一律隐藏二级菜单
				jQuery(".div_buttons").css("display", "none");
			}
			if( onclickto != null && (typeof onclickto.length != "undefined") && onclickto.length >= 0 ){
				beginValidate = true;
				if (!checkAllForms()) {
					event.cancelBubble = true;
					beginValidate = false;
					return false;
				} 		
				beginValidate = false;
				var rtValue = eval(onclickto);
				if( rtValue != null && !rtValue ){
					event.cancelBubble = true;
					return false;
				}
				disableAllButton();
				showWait(event);
			}
		});
	});
});

jQuery(function(){ //复选框
	jQuery(".rm_checkbox").each(function(){
		var cbx = jQuery(this);
		jQuery("input[name=" + jQuery(this).attr("hiddenInputId") + "]").each(function(){
			if(jQuery(this).val() == '1') {
				cbx.prop("checked", true);
			} else {
				cbx.prop("checked", false);
				jQuery(this).val('0');
			}
		});
		jQuery(this).click(function(event){
			jQuery("input[name=" + jQuery(this).attr("hiddenInputId") + "]").each(function(){
				jQuery(this).val(cbx.prop("checked") ? '1' : '0');
			});
		});
	});
});

jQuery(function(){ //隔行变色
	jQuery(".mainTable, .rowTable").each(function(){
		jQuery("[tagName=TR]", jQuery(this)).each(function(i){
			if(i % 2 == 0) {
				jQuery(this).addClass("tdc1");
			} else {
				jQuery(this).addClass("tdc2");
			}
		});
	});
});

function initStyleBehavior() {
	jQuery(".text_field_readonly, .textarea_field_readonly, .text_field_reference_readonly, .text_field_half_readonly, .text_field_half_reference_readonly").each(function(){
		jQuery(this).attr("readonly", true);
		jQuery(this).mousedown(function(event){
			if(jQuery(this).attr("hiddenInputId") != null) {
				if(event.button == 2 && confirm("确定要清除内容吗?")) {
					jQuery(this).val("");
					var aHidden = jQuery(this).attr("hiddenInputId").split(",");
					for(var i=0; i<aHidden.length; i++) {
						jQuery("#" + aHidden[i]).val("");
						jQuery("input[name=" + aHidden[i] + "]").val("");
					}
				}
			}
		});
	});
	jQuery(".text_field_reference_readonly, .text_field_half_reference_readonly").each(function(){
		//转化原有日期组件为新日期组件
		if(this.nextSibling != null && this.nextSibling.className == "refButtonClass"
			&& this.nextSibling.onclick != null && String(this.nextSibling.onclick).indexOf("getYearMonthDay(") > -1) {
			jQuery(this.nextSibling).hide();
			var oldWidth = jQuery(this).css("width");
			if(document.all){
				oldWidth = this.currentStyle.width;
			}else{
				oldWidth = window.getComputedStyle(this, null).getPropertyValue('width');
			}
			if(oldWidth.indexOf("px") > 0) {
				oldWidth = (Number(oldWidth.substring(0, oldWidth.indexOf("px"))) + 18) + oldWidth.substring(oldWidth.indexOf("px"));
			}
			jQuery(this).attr("class", "Wdate");
			jQuery(this).css("width", oldWidth);
			this.onclick = function() {
				WdatePicker();
			};
		}
	});
	jQuery(".text_date, .text_date_half").each(function(){
		if(!this.onclick) {
			this.onclick = function() {
				WdatePicker();
			};
		}
	});
	jQuery(".text_datetime, .text_datetime_half").each(function(){
		if(!this.onclick) {
			this.onclick = function() {
				WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',alwaysUseStartDate:true});
			};
		}
	});
}
jQuery(function(){ //只读表单和日期元素
	initStyleBehavior();
});

jQuery(function(){ //限定字数的文本输入框
	jQuery(".textarea_limit_words").each(function(){
		jQuery(this).focus(function(event){
			checkMaxInput(this);
		});
		jQuery(this).keydown(function(event){
			checkMaxInput(this);
		});
		jQuery(this).keyup(function(event){
			checkMaxInput(this);
		});
	});
});
function checkMaxInput(thisObj) {
	if(thisObj == undefined || thisObj == null)
		return false;
	var maxLength = 500;
	if(thisObj.maxLength != null && thisObj.maxLength != '') {
		maxLength = thisObj.maxLength;	
	}
	//如果该值大于255，就表示是汉字，长度加2，否则长度加1
	var byteLength=0;
	var str=thisObj.value;
	var afterStr;
	for(var i=0;i<str.length;i++){
	if(i<=maxLength){
//	  if(str.charCodeAt(i)>255){
//		byteLength+=2;
//	 	}else{
	   byteLength++;
//		 }
	   if(byteLength<=maxLength){
	   }else{ afterStr=str.substring(0,i);
	    thisObj.value = afterStr;
	 	writeValidateInfoAfterObject("<span class='style_required_red'>对不起，您输入字太多了！</span>", thisObj);
	 	break;
	 	}
	  }
	}
	if(byteLength<maxLength){
		writeValidateInfoAfterObject("<span class='style_required_red'>您还能输入" + (maxLength - byteLength) + "字！</span>", thisObj );
	} else if(byteLength==maxLength) {
		writeValidateInfoAfterObject("<span class='style_required_red'>您输入完毕！</span>", thisObj );
	}
}

jQuery(function(){ //超文本编辑器
	jQuery(".textarea_fckEditor").each(function(){
		var status = {
			isLoad: false
		};
		if(status.isLoad) {
			return;
		}
	    CKEDITOR.replace(jQuery(this).attr("name"), { customConfig : './custom/rm-config.js' });
	    status.isLoad = true;
	});
});

jQuery(function(){ //多附件批量上传
	jQuery(".rm_affix").each(function(){
		function doInitAffix() {
			var parentTd = this.parentNode;
			var objId = "obj_" + this.name + "_" + jQuery(this).attr("bs_keyword") + "_" + jQuery(this).attr("record_id");
			jQuery("#" + objId).each(function() {
				this.parentNode.removeChild(this);
			});
			if(jQuery(this).attr("bs_keyword") != null && jQuery(this).attr("bs_keyword") != "" && jQuery(this).attr("record_id") != null && jQuery(this).attr("record_id") != "") {
				jQuery(this).css("display", "none");
				var rmAffixLink = dir_base + '/third/swfupload/globalUpload.jsp?bs_keyword=' + jQuery(this).attr("bs_keyword") + '&record_id=' + jQuery(this).attr("record_id");
				if(jQuery(this).attr("encoding") != null) {
					rmAffixLink += "&encoding=" + jQuery(this).attr("encoding");
				}
				if(jQuery(this).attr("description") != null) {
					rmAffixLink += "&description=" + jQuery(this).attr("description");
				}
				if(jQuery(this).attr("author") != null) {
					rmAffixLink += "&author=" + jQuery(this).attr("author");
				}
				if(jQuery(this).attr("tagName").toUpperCase() == "SPAN") {
					rmAffixLink += '&REQUEST_IS_READ_ONLY=1';
				}
				parentTd.insertAdjacentHTML('beforeEnd', '<iframe id="' + objId + '" width="100%" onload="this.height=this.contentWindow.document.body.scrollHeight + 30" name="contentFrame" frameBorder="0" scrolling="no"  src="' + rmAffixLink + '" />');
			} else {
				jQuery(this).attr("readonly", true);
				parentTd.insertAdjacentHTML('beforeEnd', '<span id="' + objId + '" class="font_remain_prompt">请保存单据后，再进入修改状态才能上传附件！</span>');
			}
		}
		this.initAffix = doInitAffix;
		this.initAffix();
	});
});

jQuery(function(){ //单附件上传
	jQuery(".rm_affix_single").each(function(){
		jQuery(this).attr("readonly", true);
		var parentTd = this.parentNode;
		var str_allow_type = "";
		if(jQuery(this).attr("allow_type") != null && jQuery(this).attr("allow_type") != "") {
			str_allow_type = "&allow_type=" + jQuery(this).attr("allow_type");
		}
		var oldNameHtml = "<input type='hidden' name='" + jQuery(this).attr("name") + "_old_name' />";
		var htmlCode = oldNameHtml + 
		'<iframe scrolling="auto" align="left" onLoad="window.document.form.' + 
		jQuery(this).attr("name") + 
		'.value = this.contentWindow.document.form.affix_save_name.value;' + 
		'window.document.form.' + 
		jQuery(this).attr("name") + '_old_name' + 
		'.value = this.contentWindow.document.form.affix_old_name.value;' + 
		'" width="100%" height="35" name="uploadFrame" frameBorder="0" scrolling="no"  src="' + dir_base + '/jsp/support/upload/single/uploadSingle.jsp?default_affix=' + jQuery(this).val() + str_allow_type + '" />';
		parentTd.insertAdjacentHTML('beforeEnd', htmlCode);
	});
});

/*****************strutsLayout begin*****************/
jQuery(function(){ //strutsLayout列表控件
	var config={
		defaultColor:"#DEE5F0",	//默认列表颜色
		focusColor:"#EBF3F7",	//默认聚焦颜色
		selectedColor:"#DEEEF8",	//默认单击颜色
		theadColor:"#EBF3F7",	//默认表头颜色
		oddColor:"#FFFFFF",	//默认表体奇数行颜色
		evenColor:"#F6F6F6"	//默认表体偶数行颜色
	};
	var cache={
		tempColor1:"",
		tempColor2:""
	};
	
	function getEventObj(thisEvent) {
		e = thisEvent || window.event;
		if(e == undefined || e == null ) {
			alert("当前的对象为空!");
			return null;
		} else {
			return e.srcElement || e.target;
		}
	}
	
	function getCheckboxFromTr(thisTr) {
		if(thisTr.childNodes.length == 0) {
			return null;
		} else {
			for(var i=0; i<thisTr.childNodes.length; i++) {
				var thisChild = thisTr.childNodes[i];
				if(thisChild.type == "checkbox" || thisChild.type == "radio") {
					return thisChild;
				} else {
					var tempResult = getCheckboxFromTr(thisChild);
					if(tempResult != null) {
						return tempResult;
					}
				}
			}
		}
	}
	
	function getObjectByName(name) {
		return window.document.getElementsByName(name);
	}
	
	function getObjectById(id) {
		return window.document.getElementById(id);
	}
	
	//获得表体颜色（奇数和偶数行）
	function getTbodyColor(thisObj) {
		if(thisObj.getElementsByTagName("TABLE").length > 0) {
			var qTables = thisObj.getElementsByTagName("TABLE");
			for(var j=0; j<qTables.length; j++) {
				var qTable = qTables[j];
				for(var i=1; i<qTable.rows.length; i++) {
					var thisTr = qTable.rows[i];
					if(thisTr.originalColor != null) {
						thisTr.style.backgroundColor = thisTr.originalColor;
					} else {
						if ((i+1)%2 == 1) {
							thisTr.style.backgroundColor = config.evenColor;
						} else {
							thisTr.style.backgroundColor = config.oddColor;
						}
						thisTr.originalColor = thisTr.style.backgroundColor;
					}
				}
			}
		}
		
		var thisDivCheckbox = getObjectByName("checkbox_template");
		for (var i=0; i<thisDivCheckbox.length; i++) {
			var thisDivTr = thisDivCheckbox[i].parentNode.parentNode;
			//选中后保留颜色
			if (thisDivCheckbox[i].checked) {
				thisDivTr.style.backgroundColor = config.selectedColor;
			}
		}
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
	
	function refreshCheckHidden(thisCheckbox) {
		var NAME_SPLICT_KEY = "#RM_SPLICT#";
		if(getObjectById("rmCheckReturnValue") != null) {
			var rv = getObjectById("rmCheckReturnValue");
			var keys = ["displayName"];
			try {
				keys = objKeys;
			} catch(e){}
			var rns = []; 
			for(var i=0; i<keys.length; i++) {
				rns.push(getObjectById("rm_input" + (i+1)));
			}
			if(thisCheckbox.checked) {
				rv.value += thisCheckbox.value + ",";
				for(var i=0; i<rns.length; i++) {
					if(rns[i] != null) {
						rns[i].value += thisCheckbox[keys[i]] + NAME_SPLICT_KEY;				
					}
				}
			} else {
				if(rv.value.indexOf(thisCheckbox.value + ",") > -1) {
					var preStr = rv.value.substring(0, rv.value.indexOf(thisCheckbox.value + ","));
					var afStr = rv.value.substring(preStr.length + (thisCheckbox.value + ",").length);
					rv.value = preStr + afStr;
	
					var preStrMatch = preStr.match(/,/g);
					var preCount = 0;
					if(preStrMatch != null) {
						preCount = preStrMatch.length;
					}
					for(var i=0; i<rns.length; i++) {
						if(rns[i] == null) {
							continue;
						}
						var newRn = "";
						var aRnsi = rns[i].value.split(NAME_SPLICT_KEY);
						for(var j=0; j<aRnsi.length; j++) {
							if(j == preCount || aRnsi[j] == "") {
								continue;
							}
							newRn += aRnsi[j] + NAME_SPLICT_KEY;
						}
						rns[i].value = newRn;
					}
				}
			}
		}
	}
	
	function doInit() {
		var thisObj = null;
		var thisTable = this.getElementsByTagName("table");
		for (var i=0; i<thisTable.length; i++) {
			var thisTh = getThFromTable(thisTable[i]);  //thisTable[i].getElementsByTagName("th")
			if (thisTh != null && thisTh != "") {
				//if (thisTh.className == "listCss") {
				///支持多class
				if (thisTh.className.match(new RegExp("(^|\\s)" + "listCss" + "(\\s|$)"))){
					thisObj = thisTable[i];
				}
			}
		}
		var thisThead = null;
		if(thisObj != null) {
			thisThead = getThFromTable(thisObj);  //thisObj.getElementsByTagName("th")
		}
		if (thisThead != null && thisThead != "") {
			var thisTr = thisThead.parentNode;
			thisTr.style.backgroundColor = config.theadColor;
		}
		getTbodyColor(this);
		refreshCheckHidden_onLoad();
		resetListTableHeight();
	}
	jQuery('.listCss').each(function(){
		if(this.tagName != "TABLE") {
			return;
		}
	 	//处理文档载入事件
		this.doInit = doInit;
		this.doInit();
		jQuery(this).mouseover(function(event){ //处理鼠标进入事件
			var thisObj = getEventObj(event);
			if (thisObj.tagName.toLowerCase() == "td" && thisObj.className != null && thisObj.className != "" ) {
				event.stopPropagation();
				thisObj.style.cursor = "pointer";
				var thisTr = thisObj.parentNode;
				if(thisTr.tagName.toLowerCase() != "tr") {
					thisTr = thisTr.parentNode;
				}
				thisTr.style.backgroundColor = config.focusColor;
			}
		});
		
		jQuery(this).mouseout(function(event){ //处理鼠标离开事件
			var thisObj = getEventObj(event);
			if (thisObj.tagName.toLowerCase() == "td") {
				getTbodyColor(this);
			} 
		});
		
		jQuery(this).click(function(event){ //处理鼠标单击事件
			var isContinue = true; 
			
			if(window.toDoGlobalClick) {
				 window.toDoGlobalClick(getEventObj(event));
			}
			
			if(window.toDoClick) {
				var thisObj = getEventObj(event);
				var thisTag = undefined;
				if (thisObj.tagName.toLowerCase() != "input" && thisObj.tagName.toLowerCase() != "img") {
					if (thisObj.firstChild != null && thisObj.firstChild != "") {
						thisTag = thisObj.firstChild.tagName;
					}
				}
				if(thisObj.tagName.toLowerCase() == "td" && (thisTag == undefined || thisTag != undefined && thisTag.toLowerCase() != "table")) {
					var thisTr = thisObj.parentNode;
					var thisCheckbox = getCheckboxFromTr(thisTr);
					if(thisCheckbox != undefined && thisCheckbox != null) {
						isContinue = window.toDoClick(thisCheckbox.value);
					}
				}
			}
			
			if(!isContinue) {
				event.cancelBubble = true;
				return;
			}
			var thisObj = getEventObj(event);
			var thisTag = undefined;
			if (thisObj.tagName.toLowerCase() != "input" && thisObj.tagName.toLowerCase() != "img") {
				if (thisObj.firstChild != null && thisObj.firstChild != "") {
					thisTag = thisObj.firstChild.tagName;
				}
			}
			if(thisObj.tagName.toLowerCase() == "input" && thisObj.type == "checkbox" && thisObj.pdType == "control") {
				var tempControl = thisObj.control;
				var aCheckbox = getObjectByName(tempControl);
				if(aCheckbox == null) {
				
				} else if(aCheckbox.length == null && !aCheckbox.disabled) {
					aCheckbox.checked = thisObj.checked;
					refreshCheckHidden(aCheckbox);
				} else {
					for(var i=0; i<aCheckbox.length; i++) {
						if(aCheckbox[i].disabled) {
							continue;
						}
						aCheckbox[i].checked = thisObj.checked;
						refreshCheckHidden(aCheckbox[i]);
					}
				}
			} else if(thisObj.tagName.toLowerCase() == "td" && (thisTag == undefined || thisTag != undefined && thisTag.toLowerCase() != "table")
			  && thisObj.parentNode.parentNode.parentNode.parentNode.tagName.toLowerCase() != "th") {
				var thisTr = thisObj.parentNode;
				var thisCheckbox = getCheckboxFromTr(thisTr);
				if(thisCheckbox != undefined && thisCheckbox != null) {
					thisCheckbox.checked = !thisCheckbox.checked;
					refreshCheckHidden(thisCheckbox);
				}
			} else if(thisObj.tagName.toLowerCase() == "input" && thisObj.type == "checkbox") {
				refreshCheckHidden(thisObj);
			}
			getTbodyColor(this);
			
			if(window.toDoClick_beforeEnd) {
				var thisObj = getEventObj(event);
				var thisTag = undefined;
				if (thisObj.tagName.toLowerCase() != "input" && thisObj.tagName.toLowerCase() != "img") {
					if (thisObj.firstChild != null && thisObj.firstChild != "") {
						thisTag = thisObj.firstChild.tagName;
					}
				}
				if(thisObj.tagName.toLowerCase() == "td" && (thisTag == undefined || thisTag != undefined && thisTag.toLowerCase() != "table")) {
					var thisTr = thisObj.parentNode;
					var thisCheckbox = getCheckboxFromTr(thisTr);
					if(thisCheckbox != undefined && thisCheckbox != null) {
						isContinue = window.toDoClick_beforeEnd(thisCheckbox.value);
					}
				}
			}
			
			event.cancelBubble = true;
		});
		
		jQuery(this).dblclick(function(event){ //处理鼠标双击事件
			var thisObj = getEventObj(event);
			var thisTag = undefined;
			if (thisObj.tagName.toLowerCase() != "input" && thisObj.tagName.toLowerCase() != "img") {
				if (thisObj.firstChild != null && thisObj.firstChild != "") {
					thisTag = thisObj.firstChild.tagName;
				}
			}
			if(thisObj.tagName.toLowerCase() == "td" && (thisTag == undefined || thisTag != undefined && thisTag.toLowerCase() != "table")) {
				var thisTr = thisObj.parentNode;
				var thisCheckbox = getCheckboxFromTr(thisTr);
				if(thisCheckbox != undefined && thisCheckbox != null) {
					if(window.toDoDblClick) {
						try{
							window.toDoDblClick(thisCheckbox.value, thisCheckbox);
						} catch(e) {
							window.toDoDblClick(thisCheckbox.value);
						}
					} else if(parent.NAME_SPLICT_KEY && parent.ok_onClick && thisCheckbox.type == "radio") {
						thisCheckbox.checked = true;
						parent.ok_onClick();
					}
				}
			}
		});
	});
});

function refreshCheckHidden_onLoad() {
	if(jQuery("#rmCheckReturnValue").length > 0) {
		var rvValue = jQuery("#rmCheckReturnValue").val();
		var elementCheckbox = window.document.getElementsByName("checkbox_template");  //通过name取出所有的checkbox
		for(var i=0; i<elementCheckbox.length; i++){  //循环checkbox组
			if(rvValue.indexOf(elementCheckbox[i].value + ",") > -1) {  //如果被选中
				elementCheckbox[i].checked = true;
			}
		}
	}
}
/*****************strutsLayout end*****************/

jQuery(function(){ //原型行禁用
	jQuery(".rowPrototype").each(function(){
		jQuery(":input", jQuery(this)).each(function(i){
			jQuery(this).attr("disabled", true);
			if(jQuery(this).attr("validate") != null) {
				jQuery(this).attr("validatePrototype", jQuery(this).attr("validate"));
				jQuery(this).attr("validate", "");
			}
		});
	});
});

jQuery(function(){ //控制checkbox
	jQuery(".rowCheckboxControl").each(function(){
		jQuery(this).click(function() {
			var thisTable = this.parentNode.parentNode.parentNode;
			var controlChecked = this.checked;
			jQuery("input:checkbox[name*='rmRowSelecter']:not(disabled)", thisTable).each(function() {
				if(this.checked != controlChecked) {
					this.checked = controlChecked;
				}
			});
		});
	});
});
