<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>网页上内容导出到word</title>
</head>
<body>
<div id="Layer1" >Happy New Year ! ! !<img src="<%=request.getContextPath()%>/images/icon/add.gif" class="div_control_image">
<input type=button name='button_export' title='导出到word'  onclick=OpenWord() value=下载到word>
</div>
<p>this is a test!</p>
<table align="center" width="300" border="1" bordercolor="#2baeff" cellpadding="0" cellspacing="0" bgcolor="lightblue">
<Tr><Td>1</td><Td>2</td><Td>3</td></tr>
<Tr><Td>4</td><Td>5</td><Td>6</td></tr>
<Tr><Td>7</td><Td>8</td><Td>9</td></tr>
</table>
</body>
</html>
<script type="text/javascript">
function OpenWord(){
	

	Layer1.style.border=0
	ExcelSheet = new ActiveXObject('Word.Application');
	ExcelSheet.Application.Visible = true;
	var mydoc=ExcelSheet.Documents.Add('',0,1);
	myRange =mydoc.Range(0,1)
	var sel=Layer1.document.body.createTextRange()
	sel.select()
	Layer1.document.execCommand('Copy')
	sel.moveEnd('character')
	myRange.Paste();
	location.reload()
	ExcelSheet.ActiveWindow.ActivePane.View.Type=7
}
</script>
