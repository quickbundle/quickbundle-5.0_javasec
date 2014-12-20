<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>组织机构</title>
<style type=text/css>
	body {
	font: 100% verdana, arial, sans-serif;
	background-color: #fff;
	margin: 0px;
	}
	/* begin css tabs */
	ul#tabnav { /* general settings */
	text-align: left; /* set to left, right or center */
	margin: 1em 0 1em 0; /* set margins as desired */
	font: bold 11px verdana, arial, sans-serif; /* set font as desired */
	border-bottom: 1px solid #f11217; /* set border COLOR as desired */
	list-style-type: none;
	padding: 3px 10px 3px 10px; /* THIRD number must change with respect to padding-top (X) below */
	}
	ul#tabnav li { /* do not change */
	display: inline;
	}
	body#tab1 li.tab1, body#tab2 li.tab2, body#tab3 li.tab3, body#tab4 li.tab4 { /* settings for selected tab */
	border-bottom: 1px solid #fff; /* set border color to page background color */
	background-color: #fff; /* set background color to match above border color */
	}
	body#tab1 li.tab1 a, body#tab2 li.tab2 a, body#tab3 li.tab3 a, body#tab4 li.tab4 a { /* settings for selected tab link */
	background-color: #fff; /* set selected tab background color as desired 选中选项卡颜色*/
	color: #f11217; /* set selected tab link color as desired  字体颜色*/ 
	position: relative;
	top: 1px;
	padding-top: 4px; /* must change with respect to padding (X) above and below */
	}
	 
	ul#tabnav li a { /* settings for all tab links */
	padding: 3px 4px; /* set padding (tab size) as desired; FIRST number must change with respect to padding-top (X) above */
	border: 1px solid #f11217; /* set border COLOR as desired; usually matches border color specified in #tabnav */
	background-color: #e3e3e3; /* set unselected tab background color as desired  未选中默认颜色 */
	color: #666; /* set unselected tab link color as desired */
	margin-right: 0px; /* set additional spacing between tabs as desired */
	text-decoration: none;
	border-bottom: none;
	}
	ul#tabnav a:hover { /* settings for hover effect */
	background: #fff; /* set desired hover color */
	}
	/* end css tabs 
	#content {	float: left;width: 25%;}
	#content2 {	float: right;width: 75%;white-space: pre-wrap;*white-space: pre;*word-wrap: break-word;}
	*/	
	/**************************/
	
	.tabbb {  /* settings for selected tab link */
	background-color: #66666; /* set selected tab background color as desired 选中选项卡颜色*/
	color: #f11217; /* set selected tab link color as desired  字体颜色*/ 
	position: relative;
	top: 1px;
	padding-top: 4px; /* must change with respect to padding (X) above and below */
	}
	
</style>
<%
	String view_id = request.getParameter("view_id");
	List<RmCommonVo> lcvo = RmProjectHelper.getCommonServiceInstance().doQuery("select ID, NAME from RM_PARTY_VIEW");
	Map<String, String> mpv = new HashMap<String, String>();
	for(RmCommonVo cvo : lcvo) {
		mpv.put(cvo.getString("id"), cvo.getString("name"));
		if(view_id == null) {
			view_id = cvo.getString("id");
		}
	}
	int i = 1 ;
%>
</head>
<body id="tab1">
<ul id="tabnav">
<%	for(Map.Entry<String,String> mValue : mpv.entrySet()){%>		
		  <li class="<%=mValue.getKey().equals(view_id) ? "tab1":"tab"%>"><a href="<%=request.getContextPath()+"/orgauth/tree/managePartyTab.jsp?view_id="+mValue.getKey() + (request.getParameter("cmd") != null ? "&cmd=" + request.getParameter("cmd") : "")%>"><%=mValue.getValue()%></a> </li>
<%	i++;} %>
</ul>
<table width="100%" border="0">
	<tr>
		<td width="200">
			<div id="content">
			<%
				String sValue = "";
				if(IOrgauthConstants.PartyView.DEFAULT.id().equals(view_id)){
					sValue = IOrgauthConstants.OrgTree.DEFAULT.value();
				}
			%>
				<iframe id="left_ifram_id" name="left_org_name" frameborder="0" width="200" height="500" src="<%=request.getContextPath()%>/orgauth/tree/org.jsp?cmd=<%=request.getParameter("cmd") != null ? request.getParameter("cmd") : sValue %>&view_id=<%=RmStringHelper.prt(view_id)%>&is_href=1&reference=1&enableCookie=true" scrolling="yes"></iframe>
			</div>
		</td>
		<td align="right">
			<div id="content2">
				<iframe id="right_ifram_id" name="right_org_name" frameborder="0" width="100%" height="500" src="<%="org".equals(request.getParameter("cmd"))?"manageParty.jsp?view_id="+RmStringHelper.prt(view_id):"tmParty.jsp?view_id="+RmStringHelper.prt(view_id)%>" ></iframe>
			</div>
		</td>
	</tr>
</table>
</body>
</html>
