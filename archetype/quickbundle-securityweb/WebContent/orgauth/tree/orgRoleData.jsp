<%@page contentType="text/xml;charset=UTF-8" language="java" %><%

	try {
		DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
		String parent_party_id = request.getParameter("parent_party_id");
		StringBuilder sbRole = new StringBuilder();
		sbRole.append("select RM_ROLE.ID, RM_ROLE.NAME, RM_ROLE.IS_SYSTEM_LEVEL from RM_ROLE where RM_ROLE.IS_SYSTEM_LEVEL='1'");
		if(parent_party_id != null && parent_party_id.trim().length() > 0) {
			sbRole.append(" or RM_ROLE.OWNER_ORG_ID=" +  parent_party_id );
		}
		List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQuery(sbRole.toString());
		DeepTreeVo dtvRootRole = new DeepTreeVo("-1", "角色", "1", "");
		dtvRootRole.setIsSubmit("0");
		dtvRootRole.setDefaultOpen("1");
		dt.addTreeNode(dtvRootRole);
		{ //全局角色
			DeepTreeVo dtv2 = new DeepTreeVo("-2", "全局角色", "1", "");
			dtv2.setIsSubmit("0");
			dtv2.setDefaultOpen("1");
			dt.addTreeNode("-1", dtv2);
			for(RmCommonVo vo : lvo) {
				if("1".equals(vo.get("is_system_level"))) {
					DeepTreeVo dtv = new DeepTreeVo(vo.getString("id"), vo.getString("name"), "0", "");
					dtv.setReturnValue("r" + vo.getString("id"));
					dtv.setIsSubmit("1");
					dt.addTreeNode("-2", dtv);
				}
			}
		}
		
		{ //公司角色
			DeepTreeVo dtv2 = new DeepTreeVo("-3", "公司角色", "1", "");
			dtv2.setIsSubmit("0");
			dtv2.setDefaultOpen("1");
			dt.addTreeNode("-1", dtv2);
			for(RmCommonVo vo : lvo) {
				if("0".equals(vo.get("is_system_level"))) {
					DeepTreeVo dtv = new DeepTreeVo(vo.getString("id"), vo.getString("name"), "0", "");
					dtv.setReturnValue("r" + vo.getString("id"));
					dtv.setIsSubmit("1");
					dt.addTreeNode("-3", dtv);
				}
			}
		}
		DeepTreeXmlHandler dtFinal = null;
		if(IOrgauthConstants.OrgTree.ROLE_EMPLOYEE.value().equals(request.getParameter("cmd"))){
			DeepTreeVo dtvRootEmployee = new DeepTreeVo("-4", "员工", "1", "");
			dtvRootEmployee.setIsSubmit("0");
			dtvRootEmployee.setDefaultOpen("1");
			dt.addTreeNode(dtvRootEmployee);
			dtFinal = RmOrgHelper.getOrgTree(request, new IUtOrgTree() {
				public DeepTreeVo getDeepTreeVo(HttpServletRequest request, String party_view_id, DeepTreeVo dtv, RmCommonVo cvo) {
					dtv = RmOrgHelper.defaultOrgTree.getDeepTreeVo(request, party_view_id, dtv, cvo);
					//没有显式指定硬加载的情况下，一律为懒加载
					if(!"0".equals(request.getParameter("lazy_init"))) {
						StringBuilder sb = new StringBuilder();
						sb.append("orgData.jsp");
						//粘贴request带过来的属性(include_self除外)
						RmOrgHelper.appendRequestParameter(sb, request, new String[]{"include_self"});
						dtv.setXmlSource(sb.toString());
						if(request.getParameter("parent_party_id") == null 
								|| request.getParameter("parent_party_id").trim().length() == 0
								|| cvo.getString("child_party_id").equals(request.getParameter("parent_party_id"))) {
							dtv.setParentId("-4");
							dtv.setXmlSource(dtv.getXmlSource() + (dtv.getXmlSource().indexOf("?") > -1 ? "&" : "?") + "parent_party_code=" + cvo.getString("child_party_code"));
						}
					}
					//如果是类型员工，前边加u
					if(IOrgauthConstants.OrgTree.EMPLOYEE.value().equals(cvo.get("CHILD_BS_KEYWORD"))) {
						dtv.setReturnValue("u" + dtv.getReturnValue());
					}
					return dtv;
				}
			}, dt);
		}
		String xmlStr = dt.getStringFromDocument();
		out.print(xmlStr);
	} catch(Exception e) {
		e.printStackTrace();
	}

%>
<%@page import="org.quickbundle.orgauth.IOrgauthConstants"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeVo"%>
<%@page import="org.quickbundle.orgauth.itf.IUtOrgTree"%>
<%@page import="org.quickbundle.orgauth.util.RmOrgHelper"%>
<%@page import="org.quickbundle.tools.support.tree.DeepTreeXmlHandler"%>