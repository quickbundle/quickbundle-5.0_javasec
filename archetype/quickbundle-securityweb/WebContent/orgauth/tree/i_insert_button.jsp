<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
	String view_id = request.getParameter("view_id");
	String parent_party_id = request.getParameter("parent_party_id");
	String parent_party_type_id = request.getParameter("parent_party_type_id");
	String parent_party_code = request.getParameter("parent_party_code");
	String is_inherit = request.getParameter("is_inherit");
	//当前新增团体的类型
	String child_party_type_id = request.getParameter("party_type_id");
%>
<input type="hidden" name="party_view_id" value="<%=view_id%>">
<input type="hidden" name="parent_party_id" value="<%=parent_party_id%>" id="parent_party_id">
<input type="hidden" name="parent_party_type_id" value="<%=parent_party_type_id%>">
<input type="hidden" name="parent_party_code" value="<%=parent_party_code%>">
<input type="hidden" name="is_inherit" value="<%=is_inherit %>">
<input type="hidden" name="child_party_type_id" value="<%=child_party_type_id %>">

<% if(RmStringHelper.checkNotEmpty(parent_party_code)) {%>
<input type="button" onclickto="javascript:insert_onClick()" class="button_ellipse" value="保存" />
<% } %>
<!--<input type="button" class="button_ellipse" onclickto="javascript:insert_root_onClick()" value="保存成根节点">-->	
<script type="text/javascript">
function insert_root_onClick(){ //保存根节点
	  document.getElementById('parent_party_id').value='';
	  insert_onClick();
}
</script>