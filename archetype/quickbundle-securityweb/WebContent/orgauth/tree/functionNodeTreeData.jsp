<%@page contentType="text/xml;charset=UTF-8" language="java" %><%
try {
	String xmlStr = "";
	//获取角色授权功能菜单数据
	if("roleData".equals(request.getParameter("cmd"))){ 
		DeepTreeXmlHandler dt = RmOrgHelper.getMenuTree(request, new IUtOrgTree() {
			public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String str, DeepTreeVo dtv, RmCommonVo cvo) {
				dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, "", dtv, cvo);
				StringBuilder sb = new StringBuilder();
				sb.append("functionNodeTreeData.jsp?cmd=roleData&total_code=" + cvo.getString("total_code"));
				//菜单全部加载不用懒加载
				sb.append("&lazy_init=0");
				dtv.setXmlSource(sb.toString());
				dtv.setTarget("operation_frame");
				return dtv;
			}
		});
		xmlStr = dt.getStringFromDocument();
	}
	//获取左侧功能菜单数据
	if("leftMenuData".equals(request.getParameter("cmd"))){
		DeepTreeXmlHandler dt = RmOrgHelper.getMenuTree(request, new IUtOrgTree() {
			public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String str, DeepTreeVo dtv, RmCommonVo cvo) {
				dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, "", dtv, cvo);
				StringBuilder sb = new StringBuilder();
				sb.append("functionNodeTreeData.jsp?cmd=leftMenuData&total_code=" + cvo.getString("total_code"));
				if(RmStringHelper.checkNotEmpty(request.getParameter("lazy_init"))) {
					sb.append("&lazy_init=" + request.getParameter("lazy_init"));
				}
				if(RmStringHelper.checkNotEmpty(request.getParameter("enable_status"))){ //过滤启用禁用状态
					sb.append("&enable_status=1");
				}
				if(RmStringHelper.checkNotEmpty(request.getParameter("node_type"))){ //过滤节点类型
					sb.append("&node_type=3");
				}
				
				dtv.setXmlSource(sb.toString());
				if(cvo.getString("data_value")!=null&&cvo.getString("data_value").length()>0){
					dtv.setHrefPath(request.getContextPath()+"/"+cvo.getString("data_value"));
				}
				dtv.setTarget("contentFrame");
				if(cvo.getString("icon").length() > 0 && cvo.getString("icon").startsWith("/")) {
					dtv.setLogoImagePath(request.getContextPath() + cvo.getString("icon"));
				}
				return dtv;
			}
		}, true); //true为带权限
		xmlStr = dt.getStringFromDocument();
	}
	//获取功能菜单数据，用于菜单维护页面
	if("manageFunctionNodeData".equals(request.getParameter("cmd"))){
		DeepTreeXmlHandler dt = RmOrgHelper.getMenuTree(request, new IUtOrgTree() {
			public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String str, DeepTreeVo dtv, RmCommonVo cvo) {
				dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, "", dtv, cvo);
				StringBuilder sb = new StringBuilder();
				sb.append("functionNodeTreeData.jsp?cmd=manageFunctionNodeData&total_code=" + cvo.getString("total_code"));
				if(RmStringHelper.checkNotEmpty(request.getParameter("lazy_init"))) {
					sb.append("&lazy_init=" + request.getParameter("lazy_init"));
				}
				if(RmStringHelper.checkNotEmpty(request.getParameter("is_href"))) {
					sb.append("&is_href=" + request.getParameter("is_href"));
				}
				dtv.setXmlSource(sb.toString());
				if("1".equals(request.getParameter("is_href"))) {
					
					dtv.setHrefPath(request.getContextPath() + "/RmFunctionNodeAction.do?cmd=detail&id=" + cvo.getString("id"));				
				}
				dtv.setTarget("operation_frame");
				return dtv;
			}
		});
		xmlStr = dt.getStringFromDocument();
	}
	if("".equals(request.getParameter("cmd"))){
		
	}
	out.print(xmlStr);
	//System.out.println(request.getRequestURL() + "\n" + xmlStr);
} catch(Exception e) {
	e.printStackTrace();
}
%>
<%@page import="org.quickbundle.orgauth.itf.IUtOrgTree"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgHelper"%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.config.RmConfig"%>
<%@page import="org.quickbundle.base.dao.RmJdbcTemplate"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeVo"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeXmlHandler"%>