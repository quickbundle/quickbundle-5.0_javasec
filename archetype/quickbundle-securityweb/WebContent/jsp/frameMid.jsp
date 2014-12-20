<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>无标题文档</title>
<style type="text/css">
<!--

body {
	margin:0;
}
#bar{
	background-color:#EBF4FD;
	border-right:1px solid #ADCAE6;
	padding-top:200px;
	height:120%;
}
#unexpand{
	display:none;
}
-->
</style>
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
<script language="JavaScript" type="text/JavaScript">
<!--
var hadExpanded=true;
function expand_Button()
{
	//parent.leftFrame.style.display="none";
	var expand = document.getElementById("expand");
	var unexpand = document.getElementById("unexpand");
	if(hadExpanded)
	{
		parent.document.getElementById('mainFrameSet').cols = "0,9,*";
		hadExpanded = false;
		expand.style.display = "none";
		unexpand.style.display = "block";
	}
	else
	{
		parent.document.getElementById('mainFrameSet').cols = "181,9,*";
		hadExpanded = true;
		expand.style.display = "block";
		unexpand.style.display = "none";
	}
}
//-->
</script>
</head>
<body>
<div id="bar">
	<div id="expand"><a href="#" onClick="expand_Button()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image1','','<%=request.getContextPath()%>/images/default/left_frame_Rightbutton.gif',1)"><img src="<%=request.getContextPath()%>/images/default/left_frame_Rightbutton.gif" height="91" border="0" hspace="1" width="6" name="Image1"></img></a></div>
	<div id="unexpand"><a href="#" onClick="expand_Button()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image2','','<%=request.getContextPath()%>/images/default/left_frame_Leftbutton.gif',1)"><img src="<%=request.getContextPath()%>/images/default/left_frame_Leftbutton.gif" height="91" border="0" hspace="1" width="6" name="Image2"></img></a></div>
</div>
</body>
</html>
