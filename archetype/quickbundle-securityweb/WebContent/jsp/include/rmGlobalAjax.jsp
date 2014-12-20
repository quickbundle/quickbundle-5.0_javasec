<%@ page contentType="text/html; charset=UTF-8" language="java" %>

    <!-- ** CSS ** -->
    <!-- base library -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/resources/css/ext-all.css" />
    <!-- page specific -->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/examples/ux/css/LockingGridView.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/examples/grid/grid-examples.css" />
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/examples/form/forms.css"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/third/extjs/examples/samples.css"/>

    <!-- ** Javascript ** -->
    <!-- ExtJS library: base/adapter -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/adapter/ext/ext-base.js"></script>
    <!-- ExtJS library: all widgets -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/ext-all.js"></script>
    <!-- extensions -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/CheckColumn.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/LockingGridView.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/FieldLabeler.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/FieldReplicator.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/third/extjs/examples/ux/RowExpander.js"></script>
    
    <!-- 
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.2.min.js"></script>
    -->
    <script type="text/javascript">
    var dir_base = "<%=request.getContextPath()%>";
    var globalMyObj = null;
    function getReferenceExt(inputArray, urlPath, width, height){
    	if(width == undefined) {
    		width = 950;
    	}
    	if(height == undefined) {
    		height = 500;
    	}
    	var myObject = new Object();
    	myObject['urlPath'] = urlPath;
    	myObject['rmCheckReturnValue'] = inputArray[0].value;
    	myObject['rmCheckReturnName'] = inputArray[1].value;
    	globalMyObj = myObject;
        var windowQuery = new Ext.Window({
            title: '参照页面',
            collapsible: true,
            maximizable: true,
            width: width,
            height: height,
            layout: 'fit',
            plain: true,
            bodyStyle: 'padding:5px;',
            buttonAlign: 'center',
            html:'<iframe name="referenceContent" src="' + urlPath + '" frameborder="0" width="100%" height="100%"></iframe>',
            buttons: [{
                text: '确定',
                handler: function() {
			    	var rtObject = null;
					var thisInputObj = referenceContent.document.getElementsByName('checkbox_template');
					try {
						rtObject = document.frames['referenceContent'].toDoReadReference(thisInputObj);
					} catch(e) {
						rtObject = toDoReadReference(thisInputObj);
					}
					if(rtObject) {
						toDoWriteReference(inputArray, rtObject);
					}
					windowQuery.hide();
            	}
            },{
                text: '取消',
                handler: function() {
            		windowQuery.hide();
                }
            }]
        });
        windowQuery.show();
    }

	function toDoReadReference(thisInputObj) {
		var ids = findSelections(thisInputObj,"id");  //取得多选框的选择项
		if(ids == null) {  //如果ids为空
	  		alert("请选择一条记录!")
	  		return false;
		}
		var rtObject = new Object();
		if(referenceContent.document.form.referenceInputType.value == "checkbox" && referenceContent.document.getElementById("rmCheckReturnValue") != null && referenceContent.document.getElementById("rmCheckReturnName") != null) {
			rtObject.realValue = referenceContent.document.getElementById("rmCheckReturnValue").value.replace(/(^,)|(,$)/g, "");
			var reg = new RegExp(NAME_SPLICT_KEY, "g");
			rtObject.displayName = referenceContent.document.getElementById("rmCheckReturnName").value.replace(reg, ",");
			rtObject.displayName = rtObject.displayName.replace(/(^,)|(,$)/g, "");
		} else {
			rtObject.realValue = ids + "";
			try {
				var aDisplayName = new Array();
				for(var i=0; i<ids.length; i++) {
					for(var j=0;j<thisInputObj.length;j++){  //循环checkbox组
						if(thisInputObj[j].value == ids[i]) {
							aDisplayName.push(thisInputObj[j].displayName);
							break;
						}
					}
				}
				rtObject.displayName = aDisplayName + "";
			} catch(e) {
				rtObject.displayName = ids;
			}
		}
		return rtObject;
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
    function toDoWriteReference(inputArray, rtObj) {
    	var textValue = inputArray[0];
    	var textName = inputArray[1];
    	if(rtObj != undefined && rtObj!=null){
    		setTargetValue(textName, rtObj['displayName']);
    		setTargetValue(textValue, rtObj['realValue']);
    	}
    }
    function setTargetValue(target, value) {
    	if(target.length == null) {
        	if(target.setValue) {
            	target.setValue(value);
        	} else {
        		target.value = value;
        	}
    	} else {
    		for(var i=0; i<target.length; i++) {
    			target[i].value = value;
    		}
    	}
    }
    
    function formatDate(value){
        if (Ext.isEmpty(value)) {
            return '';  
        } else {  
            if (Ext.isDate(value))  
                return Ext.util.Format.date(value, 'Y-m-d');
            else  
                return Ext.util.Format.date(new Date(value), 'Y-m-d');
        } 
    }

	// create reusable renderer
	Ext.util.Format.comboRenderer = function(combo){
	    return function(value){
	        var record = combo.findRecord(combo.valueField, value);
	        return record ? record.get(combo.displayField) : combo.valueNotFoundText;
	    }
	}
	
    // shorthand alias
    var fm = Ext.form;

    //ReferenceField
    
    Ext.form.ReferenceField = Ext.extend(Ext.form.TriggerField,  {

    	initComponent : function(){

	    },
	
	    initEvents: function() {
	        Ext.form.DateField.superclass.initEvents.call(this);
	        this.keyNav = new Ext.KeyNav(this.el, {
	            "down": function(e) {
	                this.onTriggerClick();
	            },
	            scope: this,
	            forceKeyDown: true
	        });
	    },
	
	    onTriggerClick : function(){
	        if(this.disabled){
	            return;
	        }
	        if(this.menu == null){
	            this.menu = new Ext.menu.DateMenu({
	                hideOnClick: false,
	                focusOnSelect: false
	            });
	        }
	        getReferenceExt([this, this], this.urlPath);
	        this.onFocus();
	    }
	
	});
	Ext.reg('referencefield', Ext.form.ReferenceField);


	Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/images/icon/s.gif';
	//消息提示框
	Ext.example = function(){
	    var msgCt;

	    function createBox(t, s){
	        return ['<div class="msg">',
	                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
	                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
	                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
	                '</div>'].join('');
	    }
	    return {
	        msg : function(title, format){
	            if(!msgCt){
	                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
	            }
	            msgCt.alignTo(document, 't-t');
	            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
	            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true);
	            m.slideIn('t').pause(1).ghost("t", {remove:true});
	        },
	        init : function(){
	            var lb = Ext.get('lib-bar');
	            if(lb){
	                lb.show();
	            }
	        }
	    };
	}();

	Ext.example.shortBogusMarkup = '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.';
	Ext.example.bogusMarkup = '<p>Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Sed metus nibh, sodales a, porta at, vulputate eget, dui. Pellentesque ut nisl. Maecenas tortor turpis, interdum non, sodales non, iaculis ac, lacus. Vestibulum auctor, tortor quis iaculis malesuada, libero lectus bibendum purus, sit amet tincidunt quam turpis vel lacus. In pellentesque nisl non sem. Suspendisse nunc sem, pretium eget, cursus a, fringilla vel, urna.<br/><br/>Aliquam commodo ullamcorper erat. Nullam vel justo in neque porttitor laoreet. Aenean lacus dui, consequat eu, adipiscing eget, nonummy non, nisi. Morbi nunc est, dignissim non, ornare sed, luctus eu, massa. Vivamus eget quam. Vivamus tincidunt diam nec urna. Curabitur velit.</p>';

	Ext.onReady(Ext.example.init, Ext.example);


	// old school cookie functions
	var Cookies = {};
	Cookies.set = function(name, value){
	     var argv = arguments;
	     var argc = arguments.length;
	     var expires = (argc > 2) ? argv[2] : null;
	     var path = (argc > 3) ? argv[3] : '/';
	     var domain = (argc > 4) ? argv[4] : null;
	     var secure = (argc > 5) ? argv[5] : false;
	     document.cookie = name + "=" + escape (value) +
	       ((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
	       ((path == null) ? "" : ("; path=" + path)) +
	       ((domain == null) ? "" : ("; domain=" + domain)) +
	       ((secure == true) ? "; secure" : "");
	};

	Cookies.get = function(name){
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		var j = 0;
		while(i < clen){
			j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return Cookies.getCookieVal(j);
			i = document.cookie.indexOf(" ", i) + 1;
			if(i == 0)
				break;
		}
		return null;
	};

	Cookies.clear = function(name) {
	  if(Cookies.get(name)){
	    document.cookie = name + "=" +
	    "; expires=Thu, 01-Jan-70 00:00:01 GMT";
	  }
	};

	Cookies.getCookieVal = function(offset){
	   var endstr = document.cookie.indexOf(";", offset);
	   if(endstr == -1){
	       endstr = document.cookie.length;
	   }
	   return unescape(document.cookie.substring(offset, endstr));
	};

    </script>