<%@page contentType="text/xml;charset=UTF-8" language="java" %><%
try {
	String total_code = request.getParameter("total_code");
	DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
	String sql = "SELECT CRM_ADDRESS.*, " + 
			"(select count(*) from CRM_ADDRESS CRM_ADDRESS2 where CRM_ADDRESS2.TOTAL_CODE like concat(CRM_ADDRESS.TOTAL_CODE, '_%')) has_child FROM CRM_ADDRESS where "; 
	if(total_code == null || total_code.length() == 0) {
		sql += "length(total_code)=2";
	} else {
		sql += "total_code like '" + total_code + "%' and " + 
			"length(total_code)=" + (total_code.length() + 2);
	}
	List<RmCommonVo> lvo = RmProjectHelper.getCommonServiceInstance().doQuery(sql);
	for(RmCommonVo vo : lvo) {
		DeepTreeVo dtv = new DeepTreeVo(vo.getString("total_code"), vo.getString("full_name"), 
				"0".equals(vo.getString("has_child")) ? "0" : "1", 
				request.getContextPath() + "/project/basedoc/adminDivisionData.jsp?total_code=" + vo.getString("total_code"));
		dtv.setReturnValue(vo.getString("id"));
		//if(vo.getString("total_code").length()==2) {
			dt.addTreeNode(dtv);
		//} else {
		//	dt.addTreeNode(vo.getString("total_code").substring(0, vo.getString("total_code").length()-3), dtv);
		//}
	}
	out.println(dt.getStringFromDocument());
} catch(Exception e) {
	e.printStackTrace();
}
%>
<%@page import="org.quickbundle.tools.helper.RmSqlHelper"%>
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