<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var a = 7.0;
	var b = 0.8;
	//alert(a*b + "\n" + (a.mul(b)));

$(function(){
	$("#div_main").each(function(){
	//alert(this == $("#div_main"));
	});
	$("#div_main").get(0).hello = function(abcccc){
		//alert("hihihi:" + abcccc);
	};
	//rmDebug("4444444444444" + "\n\n" + $("#div_main").get(0).outerHTML);
	$("#div_main")[0].hello("nihao");
});


</script>
</head>
<body>
<div id="div_main">
<a target="_blank" href="<%=request.getContextPath()%>/">hello,world!</a>
</div>

<script>  
function fnOpen(){  
    document.title="测试用";//将文档标题改变成新窗口的标题,给用户一个假像  
    window.resizeTo(0,0);//将窗口变小.  
    window.moveTo(screen.availWidth/2,screen.availHeight/2);//移到屏幕中央  
    window.showModalDialog("<%=request.getContextPath()%>","","dialogWidth:"+screen.availWidth+"px;dialogHeight:"+(screen.availHeight+100)+"px;");//打开一个全屏的模式窗口.
    //window.close();//关闭模式窗口后,原窗口关闭.  
    var win = window.open('index.html','_self','');
    win.close();
}  
</script>  
<button onclick="fnOpen()">模式对话框打开新窗口</button> 
</body>
</html>