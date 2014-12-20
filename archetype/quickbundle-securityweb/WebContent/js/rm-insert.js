/*
var idxInput = new Array();  //需要监听的input数组列表
function initMyKeyDown() {  //初始化键盘监听
	try {
		var enabledInput = new Array('text','button','select-one','textarea');  //定义需要监听的input类型
		for(var i=0; i<document.all.length; i++) {  //循环document的所有对象
			var thisObj = document.all[i];  //定义当前处理对象的临时变量
			if(arrayHasString(enabledInput,thisObj.type)) {  //如果thisObj的type在enabledInput的数组中
				idxInput[idxInput.length] = thisObj;  //把thisObj加入需要监听input数组列表
			}
		}
		for(var j=0; j<idxInput.length; j++) {  //循环需要监听的input数组列表
			if(idxInput[j] != null && (idxInput[j].type == "text" || idxInput[j].type == "textarea") && !idxInput[j].readOnly) {  //如果是text或textarea
				try {
					idxInput[j].focus();  //聚焦第1个input对象
				} catch(e) {
				}
				break;  //完成聚焦, 跳出循环
			}
		}
	} catch(e) {
		//alert(e.message);
	}
}
document.onkeydown = function(e) {  //处理回车事件
	e = window.event || e;
	if(e.keyCode!=13) {  //如果不是enter键
		e.cancelBubble = false;  //继续冒泡
		return true;  //继续处理键盘事件
	}
	if(e.srcElement.type == "textarea" && !e.ctrlKey) {  //如果是textarea,不是ctrl+enter,也返回
		e.cancelBubble = false; 
		return true; 
	}
	var toFocus = 0;  //定义游标
	for(var i=0; i<idxInput.length; i++) {  //循环需要监听的input数组列表
		if(idxInput[i]== e.srcElement) {  //如果事件来源在需要监听的input数组列表中
			if(i == (idxInput.length -1)) {  //如果到了最后一个监听input对象
				toFocus = 0;  //游标归0
			} else {
				toFocus = i +1 ;  //游标加1
			}
			break;  //完成处理,退出循环
		}
	}
	var focusObj = idxInput[toFocus];  //定义聚焦的对象
	if(focusObj != undefined && focusObj.focus != undefined) {  //如果可以聚焦
		try {
			focusObj.focus();  //聚焦对象
		} catch(e) {
			if(focusObj.type == "button") {  //如果是按钮
				focusObj.click();
			}
		}
	}
};

if(window.addEventListener) {
	window.addEventListener("load",initMyKeyDown,false);
} else {
	window.attachEvent("onload", initMyKeyDown);
}
*/

jQuery(function(){
	jQuery("body").append("<div id='div_out_qq' class='out_qq' style='position:absolute;'></div>");
	//快速搜索-精确反查
	jQuery(".text_qqp").each(function(){
		jQuery(this).blur(function(event) {
			if(this.getAttribute("qq") == null || this.getAttribute("qqpRegex") == null) {
				return;
			}
			qqiRegex = eval(this.getAttribute("qqpRegex"));
			var q = jQuery(this).val();
			var thisInput = this;
			if(q.match(qqiRegex)) {
				var url = dir_base + "/modules/quickquery/qqp.jsp?qq=" + this.getAttribute("qq") + "&q=" + encodeURIComponent(q);
				jQuery.getJSON(url, function(data) {
					jQuery(thisInput).val(data.rm_key_column);
					if(thisInput.getAttribute("hiddenInputId") != null) {
						jQuery(document.forms[0][thisInput.getAttribute("hiddenInputId")]).val(q);
					}
				});
			}
		});
	});
	//快速搜索-索引下拉输入
	jQuery(".text_qqs").each(function() {
		jQuery(this).keyup(function(event){
			event = event || window.event;
			var divOut = document.getElementById("div_out_qq");
			if(event.keyCode == 40 && divOut.style.display == "block"){//如果按键是↓，将第一个结果选中，并进行赋值
				if(querySelect.style.display != "none"){
					querySelect.focus();
					querySelect.selectedIndex = 0;
					setQueryField(querySelect);
				}
			}else{
				if(hasResult || this.value.length == lastQueryLen){//如果有记录，可以继续查询
					getListData(this);
				}
			}
			clearValidateObj(this);
		});
		jQuery(this).click(function(event){
			query_string = "";
			getListData(this);
		});
		jQuery(this).mousedown(function(event){
			event = event || window.event;
			if (window.event.button==2) {
				clearTextAndId(this);
			}
		});
		jQuery(this).dblclick(function(){
			clearTextAndId(this);
			closeTishiByClick();
			clearValidateObj(this);
		});
		jQuery(this).blur(function(event){
			event = event || window.event;
			if(hiddenId && hiddenId.value == ""){
				clearTextAndId(this);
			}
		});
		jQuery(this).mouseover(function(event){
			event = event || window.event;
			if(this.value == ""){
				this.title = "首字母、汉字模糊快查\n\n双击鼠标左键清除";
			}else{
				this.title = this.value;
			}
		});
	});
});

	var queryObj ;		//当前查询的输入框对象
 	var hiddenId;		//配套的隐藏id
 	var query_string;	//上一次查询的条件
 	var minChar = 2;	//查询时，至少要2个以上的字符
 	var hasResult = true;	//没有查出结果
 	var lastQueryLen = 0;   //最后一次查询的长度
 	var queryTableFlag = false; //是否查询表单，而不是录入表单
 	var validateArray;	//校验专用的array 举例如下：
 	
	function needQqs(obj) {
		var jobj = jQuery(obj);
		if(jobj.hasClass("text_qqp") && jobj.attr("qq") != null && jobj.attr("qqpRegex") != null) {
			qqiRegex = eval(jobj.attr("qqpRegex"));
			if(jobj.val().match(qqiRegex)) { 
				return false;
			}
		}
		return true;
	}
	
 	//清除输入框和隐藏的id
 	function clearTextAndId(thisObj){
 		if(!queryTableFlag)
 			thisObj.value = "";
 		if(hiddenId != null ){
			hiddenId.value = "";
		}
		hasResult = true;
 	}
 	
 	//赋值给输入框，并把id赋值给隐藏的id输入框
	function setQueryField(selectObj){
		if(hiddenId != null){
			if(selectObj.selectedIndex>-1){
				queryObj.value = selectObj[selectObj.selectedIndex].text;
				hiddenId.value = selectObj.value;
			}
		}
		query_string = "";
	}
 	
 	//通过回车键或空格键关闭提示框，本身不赋值
 	function closeTishiByEnter(){
 		if(event.keyCode == 13 || event.keyCode == 32){
 			closeTishiByClick();
		}
 	}
 	
 	//通过鼠标双击关闭提示框，本身不赋值
 	function closeTishiByClick(){
 		var divOut = document.getElementById("div_out_qq");
		divOut.style.display = "none";//关闭提示框
 		if(queryObj){
 			queryObj.focus();	//输入框获得焦点
			//queryObj.select();	//输入框内容全选
			//关闭提示，此时可以进行有效性判断
			try{
				validateQQ(queryObj);
			}catch(e){
				alert(e.message);
			}
		}
 	}


	//进行查询：此时对查询对象赋值：queryObj和hiddenId.
 	function getListData(obj){
 		if(!needQqs(obj)) {
 			return;
 		}
 		try{
	 		if(obj.parentElement.parentElement.parentElement.parentElement.className == "table_query"){
	 			queryTableFlag = true;
	 		}
 		}catch(e){}
		queryObj = obj;	//当前查询的输入框对象
		if(typeof(queryObj.hiddenInputId) != "undefined"){
 			hiddenId = document.form[queryObj.hiddenInputId];//根据输入框查找隐藏的id字段
			hiddenId.value = "";	//再次查询时 清空id的值
		}
 		if((obj.value.length >= minChar && query_string != obj.value)|| event.keyCode == 40){//查询条件必须1个字符以上，并且两次查询的内容不一致，或者按了↓才执行查询
 			var divOut = document.getElementById("div_out_qq");
	 		if(divOut.style.display =="block")
	 			divOut.style.display = "none";
 			query_string = obj.value;	//当前查询条件
 			var ajaxUrl = dir_base + "/modules/quickquery/qqs.jsp?qq=" + obj.getAttribute("qq") + "&q=" + encodeURIComponent(obj.value);
	  		query_synchronized(queryObj, ajaxUrl);
	  	}
  	}
 
  	//自动匹配
  	function getIdValueByText(hiddenId,objValue){
  		if(hiddenId != null){
  			if(querySelect.options.length>0){
		  		for(var i=0;i < querySelect.options.length;i++){
		  			if(objValue == querySelect.options[i].text)
		  				hiddenId.value = querySelect.options[i].value;
		  		}
	  		}
  		}
  	}
  	
  	//后台调用查询
  	function query_synchronized(obj,url) {
  		var XmlHttp = new ActiveXObject("MSXML2.XMLHTTP.3.0");
  		
  		XmlHttp.onreadystatechange=function(){
  			if(XmlHttp.readyState==4){
  				if(XmlHttp.status==200){
  					var divOut = document.getElementById("div_out_qq");
  					divOut.className = "quick_query_tishi"; //设置样式，其实就是啥样式都没有
  					divOut.innerHTML = XmlHttp.responseText;//设置内容，就是查询结果
  					//确定显示位置，并显示出来，如果查询结果为空，则隐藏。
  					var left=0;
  					var top=0;
  					while(obj && obj.offsetParent){//此循环得到文件域对象在页面中的绝对位置
  				        top  += obj["offsetTop"];
  				        left += obj["offsetLeft"];
  				        obj = obj.offsetParent;
  				    }
  				    divOut.style.left = left + "px";//设置提示层的位置,左
  				    divOut.style.top = (top + 20) + "px";//设置提示层的位置,上
  				    //位置确定完毕
  				    if(divOut.innerHTML != ""){
  				    	divOut.style.display = "block";//设置提示层可见
  				    	
  				    	//判断有没有符合条件的记录
  				    	if(divOut.innerHTML.indexOf("FONT")>-1){//如果没有记录
  				    		hasResult = false;
  				    	}else{//如果有记录
	  				    	//默认选中第一个
  				    		getIdValueByText(hiddenId,queryObj.value);//自动匹配
  				    		hasResult = true;
		  					lastQueryLen = queryObj.value.length;
  				    	}
  				 	 }else{
  				 	 	divOut.style.display = "none";
  				 	 }
					
  				}else{
  				}
  			}else{
  			}
  		}
  		
  		XmlHttp.open("GET", url, true);
  		XmlHttp.send();
 	}
 	
 	
	//ajax调用返回方法 之校验快查结果
	function xmlHttpForValidateQQ(sql_qq, compareObj, compareName, queryObj) {
		if(compareObj.value == "" || queryObj.value == "")//如果要比较的字段尚未有值，就不比较
			return false;
		
		//构建3个参数
		var aParams = new Array();       
        aParams.push("sql_qq="+sql_qq);
        aParams.push("inputName="+encodeURIComponent(queryObj.inputName));
        aParams.push("compareName="+encodeURIComponent(compareName));
		var sBody = aParams.join("&"); 
		var url = "<%=request.getContextPath()%>/jsp/util/quickQueryValidate.jsp";
		var xmlhttp;
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		xmlhttp.onreadystatechange=function(){
		  if (xmlhttp.readyState==4 && xmlhttp.status==200){
		    xmlDoc=xmlhttp.responseXML;
		    //alert(xmlDoc.xml);
		    var retStr = xmlDoc.getElementsByTagName("retStr")[0].childNodes[0].nodeValue;
		    if(retStr == "fail"){
		    	var result = xmlDoc.getElementsByTagName("result")[0].attributes[0].value;
				alert(result);
				clearTextAndId(queryObj);
			}
		  }
		}
		xmlhttp.open("POST",url,true);
		xmlhttp.setRequestHeader("Cache-Control","no-cache");
		xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded"); 
		xmlhttp.send(sBody);
	}
	
  	/**
  	*	方法：快查结果匹配校验，需要每个模块重构此方法，具体可以参考本方法。
  	*	功能：判断快查的结果是否与某一个字段匹配，比如，收货单位id是否与结算单位Id匹配
  	*			如果匹配即正常显示，如果不匹配，弹出说明，并且将快查结果清空。
  	*	参数：queryObj - 快查的字段对象
  	*/
	function validateQQ(queryObj){
		if(validateArray != null){//如果定义了校验对
			for(var i=0;i < validateArray.length;i++){
				var valiArr = validateArray[i];
				var compareObj = document.form[valiArr[3]];	//比较的输入框对象
				var compareName = valiArr[4];				//比较字段的中文名称
				var sql_qq = valiArr[6].toUpperCase();		//查询sql，转大写
				if(queryObj.name == valiArr[0]){			//如果，此时快查的对象在数组里存在
					if(sql_qq.indexOf("WHERE") > 0){
						sql_qq += " AND   ";
					}else{
						sql_qq += " WHERE " ;
					}
					sql_qq += valiArr[5] +" = '"+compareObj.value+"' AND "+ valiArr[2] +" = '"+document.form[queryObj.hiddenInputId].value+"' ";
					xmlHttpForValidateQQ(sql_qq.toUpperCase(),compareObj,compareName,queryObj);//提交校验
				}
			}
		}
	}
	//校验方法完毕
	
	//清除相互校验的字段：互相校验的字段中，如果比较字段发生改变，就要删除快查字段的结果
	var compareObjMap = new Map();
 	function initValidateInput(){
 		try{
	 		if(validateArray != null){//如果定义了校验对
	 			for(var i=0;i < validateArray.length; i++){
					var valiArr		= validateArray[i];
					var compareObj	= document.form[valiArr[3]];	//比较的输入框对象
					var queryObj	= document.form[valiArr[0]];	//相应的快查字段
					if(!compareObjMap.containsKey(compareObj.name)){
						var queryObjArr = new Array();
						queryObjArr.push(queryObj);
						compareObjMap.put(compareObj.name,queryObjArr);
					}else{
						var queryObjArrTmp = compareObjMap.get(compareObj.name);
						queryObjArrTmp.push(queryObj);
						compareObjMap.remove(compareObj.name);
						compareObjMap.put(compareObj.name,queryObjArrTmp);
					}
				}
	 		}
	 		
	 		//循环map，为所有比较对象增加清除方法
	 		var allKeys = compareObjMap.keys();
	 		
	 		var thisCompareObj;
	 		for(var j=0;j < allKeys.length;j++){
	 		
	 			thisCompareObj = document.form[allKeys[j]];		//比较的字段
 				
 				//只考虑下拉框
	 			if(thisCompareObj.type == "select-one"){//如果是select单选
	 				var queryObjArrTmp = compareObjMap.get(allKeys[j]);	//快查的字段
	 				thisCompareObj.attachEvent("onchange",function (){ for(var m=0;m< queryObjArrTmp.length;m++){clearQQ(queryObjArrTmp[m]);}});
	 				compareObjMap.remove(allKeys[j]);
	 			}
	 		}
	 		
 		}catch(e){alert(e.message);}
 	}
 	
 	//删除本查询字段为比较字段的快查字段
 	function clearValidateObj(queryObj){
 		if(compareObjMap.containsKey(queryObj.hiddenInputId)){
 			var queryObjArrTmp = compareObjMap.get(queryObj.hiddenInputId);
 			for(var m=0;m< queryObjArrTmp.length;m++){
 				clearQQ(queryObjArrTmp[m]);
 			}
 		}
 	}
 	
 	function clearQQ(qqObj){
 		qqObj.value = "";
 		document.form[qqObj.hiddenInputId].value = "";
 	}
	initValidateInput();