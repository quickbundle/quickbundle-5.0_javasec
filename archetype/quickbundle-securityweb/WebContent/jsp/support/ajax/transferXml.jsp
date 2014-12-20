<%@page import="org.quickbundle.tools.helper.xml.RmXmlConverter"%>
<%@page import="org.quickbundle.tools.helper.xml.RmXmlHelper"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>
<%@ page contentType="text/xml;charset=UTF-8" language="java" %><%try {
	Object obj = request.getAttribute(IGlobalConstants.REQUEST_OUTPUT_OBJECT);
	
	String xmlStr = RmXmlHelper.getStringFromDocument((org.dom4j.Document) RmXmlConverter.getDocByObj(obj));
	//System.out.println(xmlStr);
	out.print(xmlStr);
} catch(Exception e) {
	e.printStackTrace();
}%>
