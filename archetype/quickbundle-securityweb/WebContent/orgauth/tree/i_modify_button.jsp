<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<% String child_is_leaf = request.getParameter("hasChild"); %>
<input type="button" onclickto="javascript:update_onClick()" class="button_ellipse" value="保存" />
<% if(child_is_leaf!=null&&!child_is_leaf.equals("")&&child_is_leaf.equals("0")){ %>
<input type="button" onclick="javascript:dele_onClick()" class="button_ellipse" value="删除" />
<% } %>

