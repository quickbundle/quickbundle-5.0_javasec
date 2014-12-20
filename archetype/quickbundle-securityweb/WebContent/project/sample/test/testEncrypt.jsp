<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form">
<div align="center" style="padding:20px">
	<div>
		原文：<input type="text" size="100" name="value1" onchange="javascript:encryptBase64_onChange()" /><br/>
	</div>
	<div>
		密文：<input type="text" size="100" name="value2" />
	</div>
	<div>
		解密：<input type="text" size="100" name="value3" />
	</div>

</div>
</form>
</body>
</html>
<script type="text/javascript">
function encryptBase64_onChange() {
	form.value2.value = Base64.encodeURLSafe(form.value1.value);
	form.value3.value = Base64.decodeURLSafe(form.value2.value);
}
</script>