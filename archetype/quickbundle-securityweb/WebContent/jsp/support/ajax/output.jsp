<%@ page contentType="text/xml;charset=UTF-8" language="java" %><%@page import="java.util.HashMap"%><%@page import="java.util.Map"%><%@page import="org.quickbundle.project.serializer.RmObjectMapper"%><%@page import="org.quickbundle.base.vo.RmValueObject"%><%@page import="org.dom4j.Document"%><%@page import="org.quickbundle.tools.helper.xml.RmXmlConverter"%><%@page import="org.quickbundle.base.web.page.RmPageVo"%><%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%><%@page import="java.sql.Date"%><%@page import="org.quickbundle.tools.helper.RmStringHelper"%><%@page import="java.sql.Timestamp"%><%@page import="java.util.List"%><%@page import="org.quickbundle.project.IGlobalConstants"%><%
try {
	Object obj = request.getAttribute(IGlobalConstants.REQUEST_OUTPUT_OBJECT);
	if(obj == null) {
		obj = request.getAttribute(IGlobalConstants.REQUEST_BEANS);
	} 
	if(obj == null) {
		obj = request.getAttribute(IGlobalConstants.REQUEST_BEAN);
	} 
	if(obj == null) {
		obj = request.getAttribute(IGlobalConstants.EXECUTE_ROW_COUNT);
	}
	String output_type = request.getParameter("output_type"); //json or xml
	if("xml".equals(output_type)) {
		Document doc = RmXmlConverter.getDocByObj(obj);
		out.print(RmXmlConverter.getDocByObj(obj).asXML());
	} if("excel".equals(output_type)) {
		Document doc = RmXmlConverter.getDocByObj(obj);
		out.print(RmXmlConverter.getDocByObj(obj).asXML());
	} else {
		if(obj != null) {
			if(obj instanceof List) {
				Map<String, Object> result = new HashMap<String, Object>();
				if(request.getAttribute(IGlobalConstants.RM_PAGE_VO) != null) {
					RmPageVo pageVo = (RmPageVo)request.getAttribute(IGlobalConstants.RM_PAGE_VO);
					result.put("totalCount", pageVo.getRecordCount());
				}
				result.put(IGlobalConstants.REQUEST_BEANS, obj);
				out.print(RmObjectMapper.getInstance().writeValueAsString(result));
			} else if(obj instanceof int[]) {
				Map result = new HashMap();
				result.put(IGlobalConstants.EXECUTE_ROW_COUNT, obj);
				out.print(RmObjectMapper.getInstance().writeValueAsString(result));
			} else {
				out.print(RmObjectMapper.getInstance().writeValueAsString(obj));
			} 
		}
	}
} catch(Exception e) {
	e.printStackTrace();
}
%>