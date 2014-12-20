<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

function getCrossDomainValue(targetUrl) {
	CallJSONPServer(targetUrl);
}

function CallJSONPServer(url){                                 // 调用JSONP服务器，url为请求服务器地址
    var oldScript = document.getElementById(url);       // 如果页面中注册了调用的服务器，则重新调用
	alert(oldScript)
    if(oldScript){
    	oldScript.setAttribute("src",url);
    	return;
    }
    var script =document.createElement("script");       // 如果未注册该服务器，则注册并请求之
    script.setAttribute("type", "text/javascript");
    script.setAttribute("src",url);
    script.setAttribute("id", url);
    window.document.body.appendChild(script);
}

function OnJSONPServerResponse(obj){
    alert(obj);
}

</script>
</head>
<body>
<div>
	<div>
		<h3><%=request.getRequestURL()%></h3>
		<input type="button" value="取跨域的json" onclick="javascript:getCrossDomainValue('http://localhost:8080/project/sample/test/crossDomain_json.jsp')">
	</div>
		<br/>
</div>
</body>
</html>