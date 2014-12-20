<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String info = null;
if("1".equals(request.getParameter("isSubmit"))) {
	//String[] result = CrmAddressService.importAddress("E:/address.txt");
	//info = "成功导入" + result.length + "条数据！";
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
<input type="submit" value="导入行政区划数据">
<input type="hidden" name="isSubmit" value="1" />
</form>
</body>
</html>
<script type="text/javascript">
<%if(info != null){%>
alert("<%=info%>");
<%}%>
</script>