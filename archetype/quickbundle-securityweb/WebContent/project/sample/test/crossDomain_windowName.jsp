<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function getWindowName(targetUrl) {
	var state = 0;
    iframe2 = document.createElement('iframe');
    loadfn = function() {
        if (state == 1) {
            var data = iframe2.contentWindow.name;    // 读取数据
            alert(data);
			document.getElementById("iframe2").contentWindow.document.write('');
			document.getElementById("iframe2").contentWindow.close();
			document.body.removeChild(document.getElementById("iframe2"));
        } else if (state == 0) {
            state = 1;
            iframe2.contentWindow.location = "proxy.html";    // 设置的代理文件
        }  
    };
	iframe2.id = 'iframe2';
	iframe2.height = '150';
	iframe2.width = '600';
    iframe2.src = targetUrl;
    if (iframe2.attachEvent) {
        iframe2.attachEvent('onload', loadfn);
    } else {
        iframe2.onload  = loadfn;
    }
    document.body.appendChild(iframe2);
}


</script>
</head>
<body>
<div>
	<div>
		<h3><%=request.getRequestURL()%></h3>
		<input type="button" value="取跨域的iframe值" onclick="javascript:getWindowName('http://localhost:8080/project/sample/test/service.jsp')">
	</div>
		<br/>
</div>
</body>
</html>