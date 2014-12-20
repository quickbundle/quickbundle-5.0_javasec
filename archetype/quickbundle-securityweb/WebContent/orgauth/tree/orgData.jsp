<%@page contentType="text/xml;charset=UTF-8" language="java" %><%
try {
	DeepTreeXmlHandler dt = null;
%><%@ include file="orgData_custom.jsp" %><%
	if(dt == null) {
		dt = RmOrgHelper.getOrgTree(request, new IUtOrgTree() {
			public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String view_id, DeepTreeVo dtv, RmCommonVo cvo) {
				dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, view_id, dtv, cvo);
				//如果懒加载,接力传递参数
				if(!"0".equals(request.getParameter("lazy_init"))) {
					StringBuilder sb = new StringBuilder();
					sb.append("orgData.jsp?parent_party_code=" + cvo.getString("child_party_code"));
					//粘贴request带过来的属性(include_self除外)
					RmOrgHelper.appendRequestParameter(sb, request, new String[]{"include_self"});
					dtv.setXmlSource(sb.toString());
				}
				if("1".equals(request.getParameter("is_href"))) {
					dtv.setHrefPath("manageParty.jsp?view_id=" + view_id + "&child_party_code=" + cvo.getString("child_party_code")+"&party_id="+cvo.getString("id"));				
					dtv.setTarget("right_org_name");
				}
				return dtv;
			}
		});
	}
	String xmlStr = dt.getStringFromDocument();
	out.print(xmlStr);
	//System.out.println(xmlStr);
} catch(Exception e) {
	e.printStackTrace();
}
%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeVo"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeXmlHandler"%>
<%@page import="org.quickbundle.orgauth.itf.IUtOrgTree"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgHelper"%>