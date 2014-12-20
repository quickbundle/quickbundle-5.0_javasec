<%@page contentType="text/xml;charset=UTF-8" language="java" %><%
        DeepTreeXmlHandler dt = new DeepTreeXmlHandler();
        dt.addTreeNode(new DeepTreeVo("1", "销售部", "1", "deeptree_data.jsp?parentCode=" + "1"));
        dt.addTreeNode(new DeepTreeVo("2", "开发中心", "1", "xmlData.xml"));
        DeepTreeVo dtv = new DeepTreeVo("3", "质量管理部", "1", "xmlData.xml");
        dtv.addAttribute("isSubmit", "0");
        dtv.addAttribute("isSelected", "1");
        dtv.addAttribute("testAttribute","11111");
		dtv.setDefaultOpen("1");
        dt.addTreeNode(dtv);
        String xmlStr = dt.getStringFromDocument();
        System.out.println(xmlStr);
        out.print(xmlStr);
%><%@page import="org.quickbundle.tools.support.tree.DeepTreeVo"%><%@page import="org.quickbundle.tools.support.tree.DeepTreeXmlHandler"%>