<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TabPanel demo</title>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<style>
html, body {
	width : 100%;
	height : 100%;
	padding : 0;
	margin : 0;
	overflow : hidden;
}
</style>
<link href="<%=request.getContextPath() %>/jsp/support/tabplugin/css/core.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/jsp/support/tabplugin/css/Toolbar.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/jsp/support/tabplugin/css/WindowPanel.css" rel="stylesheet" type="text/css"/>
<link href="<%=request.getContextPath() %>/jsp/support/tabplugin/css/TabPanel.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="<%=request.getContextPath() %>/jsp/support/tabplugin/js/TabPanel.js"></script>
<script type="text/javascript">
var tabpanel;
var toolbar;
var stotalcode;
var stotalcodeName;
/*
$(document).ready(function(){
	toolbar = new Toolbar({
        renderTo : 'toolbar',
		//border: 'top',
        items : [{
          type : 'button',
          text : 'OpenWindow',
          bodyStyle : 'new',
          useable : 'T',
          handler : function(){
			new WindowPanel({
				id : 'saveDictionary',
				title : '弹出层',
				//width : 100,
				//height : 100,
				 width: '60',
				dragable : true,
				html : '<iframe src="<%=request.getContextPath()%>/jsp/search_demo.jsp" frameborder="0" width="100%" height="100%"  scrolling="auto"></iframe>'
			});
          }
        },'-',{
          type : 'button',
          text : 'Click Add!!',
          bodyStyle : 'delete',
          useable : 'T',
          handler : function(){
            addTab();
          }
        }]
      });
	  //toolbar.render();

	  tabpanel = new TabPanel({
		renderTo:'tab',
		autoResizable:true,
		border:'none',
		active : 0,
		maxLength : 15,
		items : [{
			id:'mainpage',
			title:'主页',
			icon:'image/read-n.gif',
			html:'<iframe id="mainpageFrame" src="<%=request.getContextPath()%>/jsp/welcome.jsp"   width="100%" height="100%" frameborder="0"></iframe>',
			closable: false
		}]
	});
});
var i=0;
function addTab(str,ti,code){
	stotalcode = code;	
	stotalcodeName = ti;
  	var totalCode = '';
  	if(str.indexOf("?")>0){
  		totalCode='&totalCode='+code;
  	}else{
  		totalCode='?totalCode='+code;
  	}
  	
  	tabpanel.addTab({id:code,title:ti, html:'<iframe src="'+str+totalCode+'" width="100%" height="100%" id="'+code+'Frame" name="'+code+'Frame" frameborder="0"></iframe>'});
}
function show_div(w,h,title,url){
	new WindowPanel({
		id : 'searchDemo',
		title : title,
		width: w,
		height: h,
		dragable : true,
		html : '<iframe src="'+url+'" frameborder="0"   scrolling="auto" name="dframe"></iframe>'
	});
}


function panel_close(){
	  $('#WindowPanel_Back').remove();
	  $('#searchDemo').remove();
}

//关闭当前面板， label 面板id ；position 父id
function close_label(closelabel,position){
//先刷新，在关页面
	if(position != ""){
	   //window.setTimeout("flush_parent('"+position+"')",100);
	   flush_parent(position);
	}
	//设置延迟100
   window.setTimeout("tabpanel.kill('"+closelabel+"')",100);

}
//刷新父面板。position 父id
function flush_parent(position){
   	tabpanel.flush(position);
}
function getCode(){
	
}
*/
</script>
</head>
<body>
<div id="tab" style="width:400px;"></div>
</body>
</html>