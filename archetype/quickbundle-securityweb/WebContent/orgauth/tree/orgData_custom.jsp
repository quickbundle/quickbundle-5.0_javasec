<%
//begin cmd mode project custom
/*
if(IOrgauthConstants.OrgTree.TM_PARTY.value().equals(request.getParameter("cmd"))){ //tm org tree
	dt = RmOrgHelper.getOrgTree(request,new IUtOrgTree(){
		public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String view_id, DeepTreeVo dtv, RmCommonVo cvo) {
			dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, view_id, dtv, cvo);
			String show_bk = request.getParameter("show_bk");
			if(!"0".equals(request.getParameter("lazy_init"))) {
				StringBuilder sb = new StringBuilder();
				sb.append("orgData.jsp?parent_party_code=" + cvo.getString("child_party_code"));
				RmOrgHelper.appendRequestParameter(sb, request, new String[]{"include_self"});
				dtv.setXmlSource(sb.toString());
			}
			if("1".equals(request.getParameter("is_href"))) {
				String child_is_main_relation=cvo.getString("child_is_main_relation");
				String child_is_leaf = "1";
				if(RmStringHelper.checkEmpty(cvo.getString("child_is_leaf")) || "1".equals(cvo.getString("child_is_leaf"))){
					child_is_leaf="0";
				}
				dtv.setHrefPath(request.getContextPath()+"/orgauth/tree/tmParty.jsp?view_id=" + view_id + "&child_party_code=" + cvo.getString("child_party_code")+"&party_id="+cvo.get("child_party_id")+"&bs_keyword="+cvo.get("child_bs_keyword")+"&hasChild="+child_is_leaf+"&child_is_main_relation="+child_is_main_relation+"&show_bk="+show_bk);				
			}
			dtv.setTarget("right_org_name");
			return dtv;
			}
		});
}
*/
%><%@page import="org.quickbundle.orgauth.itf.IUtOrgTree"%><%@page import="org.quickbundle.orgauth.util.RmOrgHelper"%><%@page import="org.quickbundle.orgauth.IOrgauthConstants"%><%@page import="java.sql.SQLException"%><%@page import="java.sql.ResultSet"%><%@page import="org.springframework.jdbc.core.RowMapper"%><%@page import="org.quickbundle.config.RmConfig"%><%@page import="org.quickbundle.base.dao.RmJdbcTemplate"%><%@page import="org.quickbundle.tools.helper.RmStringHelper"%><%@page import="org.quickbundle.project.RmProjectHelper"%><%@page import="org.quickbundle.project.common.vo.RmCommonVo"%><%@page import="java.util.List"%><%@page import="org.quickbundle.tools.support.tree.DeepTreeVo"%><%@page import="org.quickbundle.tools.support.tree.DeepTreeXmlHandler"%>