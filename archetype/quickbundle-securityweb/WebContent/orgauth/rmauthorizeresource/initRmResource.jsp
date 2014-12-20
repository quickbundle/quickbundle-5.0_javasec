<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.orgauth.rmauthorizeresource.vo.RmAuthorizeResourceVo"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%  
	String bs_keyword = request.getParameter("bs_keyword");
	String old_resource_id = request.getParameter("old_resource_id");
	List<RmCommonVo> listVo= RmProjectHelper.getCommonServiceInstance().doQuery("select AR.id,AR.default_access,AR.name  from RM_AUTHORIZE_RESOURCE AR join RM_AUTHORIZE  A on AR.AUTHORIZE_ID=A.Id where A.BS_KEYWORD = '"+bs_keyword+"'AND AR.OLD_RESOURCE_ID = '"+old_resource_id+"'");
	RmCommonVo resultVo = null;	
	if(listVo.size()>0){
		resultVo = listVo.get(0);
	}
%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script language="javascript">
	var rmActionName = "RmAuthorizeResourceAction";
  	function update_onClick(id){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=updateDefaultAccess";
    	form.submit();
	}
</script>
</head>
<body onload="refreshAuthorizeFrame();">
<form name="form" method="post">
<table class="mainTable">
	<tr align="center">
		<td width="50%" align="right"" colspan="2">
		公开<input name="default_access" type="radio" value="1" onclick="refreshAuthorizeFrame()" <%=(resultVo != null && "1".equals(resultVo.getString("default_access"))) ? "checked" : ""%> validate="notNull;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		受控<input name="default_access" type="radio" value="0" onclick="refreshAuthorizeFrame()" <%=(resultVo != null && "0".equals(resultVo.getString("default_access"))) ? "checked" : ""%> validate="notNull;">
		</td>
		<td width="50%" align="left" colspan="2">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input name="button_save" class="button_ellipse" type="button" value="保存" onClickTo="javascript:update_onClick()"></td>
	</tr>
	<tr>
		<td align="center" colspan="4"></td>
	</tr>
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>	
</table>
<input type="hidden"" name="name" value="<%=resultVo != null ? resultVo.getString("name") : ""%>">
<input type="hidden"" name="bs_keyword" value="<%=request.getParameter("bs_keyword")%>">
<input type="hidden" name="old_resource_id" value="<%=request.getParameter("old_resource_id")%>">
<input type="hidden" name="resource_id" value="<%=resultVo != null ? resultVo.getString("id") : ""%>">
</form>	
<br>		
</body>
</html>
<script language="javascript">
  	function refreshAuthorizeFrame(){  //保存修改后的单条数据
		for(var i=0; i<form.default_access.length; i++) {
			if(form.default_access[i].value == "0" && form.default_access[i].checked) {
				parent.document.getElementById("authorize_frame_tr").style.display = "block";
			} else {
				parent.document.getElementById("authorize_frame_tr").style.display = "none";
			}
		}
	}
</script>