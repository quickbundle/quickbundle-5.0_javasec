<%@page contentType="text/html;charset=UTF-8" language="java"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgHelper"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgAuthSqlHelper"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<jsp:include page="/jsp/support/deeptree/include/globalDeepTree.jsp" />
<% 
	//获取是否参照树reference (0=否，1=是。默认=0)
	String reference = request.getParameter("reference");
	if(reference == null || reference.length() == 0 || (!"1".equals(reference) && !"0".equals(reference))) { 
		reference = "0";
	}
	//获取view_id
	String view_id = request.getParameter("view_id");
	if(view_id == null || view_id.trim().length() == 0) {
		//party_view_id的默认值是RM_PARTY_VIEW表的第一条记录
		List<RmCommonVo> lPartyViewId = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT ID FROM RM_PARTY_VIEW ORDER BY ID", 1, 1);
		if(lPartyViewId.size() > 0) {
			view_id = lPartyViewId.get(0).getString("id");
		} else {
			view_id = IOrgauthConstants.PartyView.DEFAULT.id();
		}
	}
	//定制变量
	String customRootXmlSource = null; //定制xmlSource的url
	String customAttribute = null; //定制的额外属性
	String title = null; //树的名称
	//默认在url传递的参数
	String show_bk = ""; //显示的团体类型
	String submit_bk = null; //可提交的团体类型
	
	if(IOrgauthConstants.OrgTree.COMPANY.value().equals(request.getParameter("cmd"))) { //公司树
		show_bk = IOrgauthConstants.OrgTree.COMPANY.value();
		submit_bk = IOrgauthConstants.OrgTree.COMPANY.value();
		title = IOrgauthConstants.OrgTree.COMPANY.title();
	} else if(IOrgauthConstants.OrgTree.DEPARTMENT.value().equals(request.getParameter("cmd"))) { //公司-部门树
		show_bk = IOrgauthConstants.OrgTree.COMPANY.value() + "," + IOrgauthConstants.OrgTree.DEPARTMENT.value();
		submit_bk = IOrgauthConstants.OrgTree.DEPARTMENT.value();
		title = IOrgauthConstants.OrgTree.COMPANY.title();
	} else if(IOrgauthConstants.OrgTree.STATION.value().equals(request.getParameter("cmd"))) { //公司-部门-岗位树
		show_bk = IOrgauthConstants.OrgTree.COMPANY.value() + "," + IOrgauthConstants.OrgTree.DEPARTMENT.value() + "," + IOrgauthConstants.OrgTree.STATION.value();
		submit_bk = IOrgauthConstants.OrgTree.STATION.value();
		title = IOrgauthConstants.OrgTree.STATION.title();
	} else if(IOrgauthConstants.OrgTree.USER.value().equals(request.getParameter("cmd"))) { //公司-部门-岗位-用户树
		show_bk = IOrgauthConstants.OrgTree.COMPANY.value() + "," + IOrgauthConstants.OrgTree.DEPARTMENT.value() + "," + IOrgauthConstants.OrgTree.STATION.value() + "," + IOrgauthConstants.OrgTree.USER.value();
		submit_bk = IOrgauthConstants.OrgTree.USER.value();
		title = IOrgauthConstants.OrgTree.USER.title();
	} else if(IOrgauthConstants.OrgTree.ROLE_EMPLOYEE.value().equals(request.getParameter("cmd"))) { //显示角色、公司-部门-岗位-用户树
		show_bk = IOrgauthConstants.OrgTree.COMPANY.value() + "," + IOrgauthConstants.OrgTree.DEPARTMENT.value() + "," + IOrgauthConstants.OrgTree.STATION.value() + "," + IOrgauthConstants.OrgTree.USER.value();
		submit_bk = IOrgauthConstants.OrgTree.USER.value();
		title = IOrgauthConstants.OrgTree.ROLE_EMPLOYEE.title();
		customAttribute = "include_self=1"; //额外属性：是否包含自身
		customRootXmlSource = request.getContextPath() + "/orgauth/tree/orgRoleData.jsp";
	} else if(IOrgauthConstants.OrgTree.DEFAULT.value().equals(request.getParameter("cmd"))) { //所有节点全部出
		//所有字段全显示、全可提交
	} else { //定制树
%>
<%@ include file="org_custom.jsp" %>
<%
	}
	//如果request.getParameter()有值，优先级最高
	{
		if(request.getParameter("show_bk") != null && request.getParameter("show_bk").trim().length() > 0) {
			show_bk = request.getParameter("show_bk").trim();
		}
		if(request.getParameter("submit_bk") != null && request.getParameter("submit_bk").trim().length() > 0) {
			submit_bk = request.getParameter("submit_bk").trim();
		}
	}
	//构建xmlSource的Url
	StringBuilder sbXmlUrl = new StringBuilder();
	if(customRootXmlSource != null && customRootXmlSource.length() > 0) {
		sbXmlUrl.append(customRootXmlSource);
	} else {
		sbXmlUrl.append(request.getContextPath() + "/orgauth/tree/orgData.jsp");
	}
	//额外属性
	if(customAttribute != null && customAttribute.length() > 0) {
		if(sbXmlUrl.indexOf("?") == -1) {
			sbXmlUrl.append("?");
		} else {
			sbXmlUrl.append("&");
		}
		sbXmlUrl.append(customAttribute);
	}
	//粘贴bs_keyword和request带过来的参数
	Map<String, Object> mPara = new HashMap<String, Object>();
	
	RmPopulateHelper.populate(mPara, request);
	mPara.put("view_id", view_id);
	mPara.put("show_bk", show_bk);
	if(submit_bk != null) {
		mPara.put("submit_bk", submit_bk);
	}
	String xmlUrl = RmOrgHelper.appendRequestParameter(sbXmlUrl, mPara, null);
%>
<html>
<head>
<title><%=title == null ? "组织树" : title%></title>
 
<script type="text/javascript">
	rootXmlSource = "<%=xmlUrl%>";

	//根据需要可以重写的方法
	function toDoMouseClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseDbClick(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
		var thisHiddenValue = getHiddenValueByEvent(obj.id);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.outerHTML);
		//alert("事件对象的id是" + obj.id + "\n隐藏值的代码是\n" + thisHiddenValue.parentNode.outerHTML);
	}
	
	function toDoMouseOver(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}
	
	function toDoMouseOut(e) {
		e = window.event || e;
		var obj = e.srcElement || e.target;
	}

/**********************开始表单提交相关方法 **********************/

	function returnValueName() {  //获得所有选择的checkbox
		currentId = "";
		var checkedArray = new Array(0);		
		var submitStringArray = new Array(0);
		var submitObjectArray = new Array(0);
				
		var obj = null;
		if(inputType == "checkbox") {
			obj = window.document.getElementsByName(prefixCheckbox);
			for(i=0;i<obj.length;i++){
				if(obj[i].checked){
					checkedArray.push(obj[i].id.substring(prefixCheckbox.length));
				}
			}
			if(submitType == "parentPriority") {
				submitStringArray = clearChild(checkedArray);  //全面扫描checkedArray,把其中冗余的字节点信息去掉,Id列表放入submitStringArray
			} else {
				submitStringArray = filterHidden(checkedArray);
			}
		} else if(inputType == "radio") {
			obj = window.document.getElementsByName(prefixRadio);
			for(i=0;i<obj.length;i++){
				if(obj[i].checked){
					checkedArray.push(obj[i].id.substring(prefixRadio.length));
				}
			}
			submitStringArray = checkedArray;
		}
		for(var i=0; i<submitStringArray.length; i++) {  //把要提交的checkbox属性填满放入submitObjectArray
			var tempObj = new Object();
			var thisObj = getObjectById(prefixDiv + submitStringArray[i]);
			var parentObj = getParentObject(thisObj);
			tempObj["childName"] = thisObj.getAttribute("text");
			tempObj["childId"] = thisObj.getAttribute("returnValue");
			tempObj["returnValue"] = thisObj.getAttribute("returnValue");
			tempObj["parentName"] = parentObj["parentName"];
			tempObj["parentId"] = parentObj["parentId"];
			//tempObj["detailedType"] = thisObj.detailedType; 
			
			submitObjectArray[submitObjectArray.length] = tempObj;
		}

		//测试一下
		/*
		var alertStr = "";
		for(var i=0; i<submitObjectArray.length; i++) {
			alertStr += submitObjectArray[i]["returnValue"] + prefixConcat_returnText + submitObjectArray[i]["childName"] + prefixConcat_returnText + submitObjectArray[i]["childId"] + prefixConcat_returnText + submitObjectArray[i]["parentName"] + prefixConcat_returnText + submitObjectArray[i]["parentId"] + "\n";
		}
		alert(alertStr);
		*/
		if(submitObjectArray.length == 0) {
			alert("请选择一条记录!");
			return false;
		}
		window.returnValue = submitObjectArray;
		if(outputType == "doubleClick") {
			window.close();
		} else {
			window.close();
		}
	}

/**********************结束表单提交相关方法 **********************/

</script>
</head>
<body onLoad="doOnLoad()" topmargin=0 leftmargin=0 >
<form name="form" method="post">

	<table border="0" width="100%" cellspacing="0" cellpadding="0">
	  <tr> 
	     <td width="100%" valign="top" align="left">    
			<!--树开始11-->    
			<div name="deeptree" id="deeptree" class="deeptree" style="<% if(!"1".equals(reference)){%>width:400px;<%}%>height:500px;overflow-y:auto;border:1px solid;"></div>
			<!--树结束-->
	    </td>
	  </tr>
	</table>
	
	<% if(!"1".equals(reference)){ %>
	<div id="footerDiv">  
		<table border="0" width="100%" cellspacing="0" cellpadding="0">
		  <tr align="center"> 
		    <td>
		    	<%if("checkbox".equals(request.getParameter("inputType"))){%><input type="checkbox" title="全选/不选" onclick="setAllCheckbox(this.checked)"><%} %>
		    	&nbsp;&nbsp;<input type="button" value="确定" onClick="javascript:returnValueName();">&nbsp;&nbsp;<input type="button" value="取消" onClick="window.close();">
		    </td>
		  </tr>
		</table>
	</div> 
	<% } %>
</form>
</body>
</html>