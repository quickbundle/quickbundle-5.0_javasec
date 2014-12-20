<%@page import="org.quickbundle.tools.helper.xml.RmXmlConverter"%>
<%@page import="org.quickbundle.tools.helper.xml.RmXmlHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@ page contentType="text/xml;charset=UTF-8" language="java" %><%try {
	org.dom4j.Document doc = null;
	String strsql = request.getParameter("strsql");
	if(strsql != null && strsql.length() >= 0) {
		java.util.List lResult = RmProjectHelper.getCommonServiceInstance().query(strsql, new org.springframework.jdbc.core.RowMapper() {
	        public Object mapRow(java.sql.ResultSet rs, int i) throws java.sql.SQLException {
				RmCommonVo vo  = new RmCommonVo();
				RmPopulateHelper.populate(vo, rs);
				return vo;
	        }
		});
		doc = (org.dom4j.Document) RmXmlConverter.getDocByObj(lResult);
		System.out.println("mineDataXml.jsp: mine " + lResult.size() + " records! ");
	}
	String xmlStr = RmXmlHelper.getStringFromDocument(doc);
	//System.out.println(xmlStr);
	out.print(xmlStr);
} catch(Exception e) {
	e.printStackTrace();
}%>
