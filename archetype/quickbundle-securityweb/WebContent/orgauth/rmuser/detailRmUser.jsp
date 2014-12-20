<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.orgauth.rmuser.vo.RmUserVo" %>
<%@ page import="org.quickbundle.orgauth.rmuser.util.IRmUserConstants" %>
<%  //取出本条记录
	RmUserVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmUserVo)request.getAttribute(IRmUserConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgAuthSqlHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.TreeMap"%>
<%@page import="org.quickbundle.util.RmOrderCodes"%>
<%@page import="java.util.HashSet"%><html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	resetListJspQueryInputValue=false;
	var rmActionName = "RmUserAction";
	function find_onClick(){  //直接点到修改页面
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=find";
		form.submit();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=delete";
		form.submit();
	}  
	function goUrl(){
		form.action="<%=request.getContextPath()%>/RmUserAction.do?cmd=simpleQuery";
		form.submit();
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("name")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getName())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("lock_status")%>：</td>
		<td><%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_LOCK_STATUS, resultVo.getLock_status())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_id())%>&nbsp;</td>
		<td align="right"></td>
		<td>&nbsp;</td>
		<!--
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("organization_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getOrganization_id())%></td>
		-->
	</tr>
	<tr>
		<!--<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("employee_id")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getEmployee_id())%>&nbsp;</td>-->
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("email")%>：</td>
		<td colspan="3"><%=RmStringHelper.prt(resultVo.getEmail())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("admin_type")%>：</td>
		<td><%=RmGlobalReference.get(IRmUserConstants.DICTIONARY_RM_ADMIN_TYPE, resultVo.getAdmin_type())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("description")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getDescription())%>&nbsp;</td>
	</tr>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_status")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_status())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_date")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLast_login_date())%></td>
	</tr>
	<tr>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("last_login_ip")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLast_login_ip())%>&nbsp;</td>
		<td align="right"><%=IRmUserConstants.TABLE_COLUMN_CHINESE.get("login_sum")%>：</td>
		<td><%=RmStringHelper.prt(resultVo.getLogin_sum())%></td>
	</tr>
</table>
<br>
<%try{%>
<table class="mainTable" style="width:80%" align="center" border="1">
	<tr>
		<td align="center"><h3>本用户所属组织</h3></td>
	</tr>
	<%
		Map<RmOrderCodes, RmCommonVo> mvo  = new TreeMap<RmOrderCodes, RmCommonVo>();
		Set<String> sAncestorPartyCode = new HashSet<String>();
		List<RmCommonVo> lpr = RmProjectHelper.getCommonServiceInstance().doQuery("select child_party_code from RM_PARTY_RELATION where child_party_id=" + resultVo.getId()+" and party_view_id!="+IOrgauthConstants.PartyView.DEFAULT.id());
		for(RmCommonVo vo : lpr) {
			String code = vo.getString("child_party_code");
			while(code.length() >= 3) {
				sAncestorPartyCode.add(code);
				code = code.substring(0, code.length()-3);
			}
		}
		List<RmCommonVo> lAllpr = RmProjectHelper.getCommonServiceInstance().doQuery("select pv.name, pr.child_party_id, pr.parent_party_code, pr.child_party_code, p.name as parent_party_name, pr.child_party_name from RM_PARTY_RELATION pr join RM_PARTY_VIEW pv on pr.party_view_id=pv.id left join RM_PARTY p on pr.parent_party_id=p.id where p.USABLE_STATUS='1' and pr.child_party_code in (" + RmStringHelper.parseToSQLStringApos(sAncestorPartyCode.toArray(new String[0])) + ")  and party_view_id!="+IOrgauthConstants.PartyView.DEFAULT.id());
		for(RmCommonVo vo : lAllpr) {
			mvo.put(new RmOrderCodes(vo.getString("name"), vo.getString("child_party_code")), vo);
		}

	%>	
	<tr>
		<td align="left">
		<%
			String strs = "";
			for(Map.Entry<RmOrderCodes, RmCommonVo> en : mvo.entrySet()) {
				RmCommonVo vo = en.getValue();
				if(resultVo.getId().equals(vo.getString("child_party_id"))){
					strs +="&nbsp;<B style='color:RED'>"+vo.getString("child_party_name")+"</B>&nbsp;&nbsp;<B>"+vo.getString("name")+"</B><BR>";
				}else{
					strs +=""+vo.getString("child_party_name")+"&nbsp;&gt;&gt;&nbsp;";
				}
			}	
		 %>
		 &nbsp;<%=strs %>
		</td>
	</tr>
	<tr>
		<td  align="center">&nbsp;</td>
	</tr>
</table>
<%}catch(Exception e){e.printStackTrace();} %>
<input type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_update" value="修改" onClick="javascript:find_onClick();">
			<input type="button" class="button_ellipse" id="button_back" value="返回"  onClick="javascript:goUrl();" >
		</td>
	</tr>
</table>

<!-- 开始子表信息，带页签集成多个子表 -->
<script type="text/javascript">
var childTableTabs  =  new Array(

	new Array ('用户在线记录','<%=request.getContextPath()%>/RmUserOnlineRecordConditionAction.do?cmd=queryAll&user_id=<%=resultVo.getId()%>&RM_ORDER_STR=login_time DESC'),
	new Array ('数据权限-编码类型','<%=request.getContextPath()%>/orgauth/rmparty/middle/listRm_authorize_resource_record.jsp?party_id=<%=resultVo.getId()%>&bs_keyword=<%=IOrgauthConstants.Authorize.RM_CODE_TYPE.bsKeyword()%>'),
	null
);
</script>
<br/><br/>
<table class="table_div_content">
	<tr>
		<td>
			<div id="childTableTabsDiv"></div>
		</td>
	</tr>
</table>
<!-- 结束子表信息 -->

</form>
</body>
</html>