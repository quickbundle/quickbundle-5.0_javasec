<%@page import="org.quickbundle.base.beans.factory.RmBeanFactory"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.io.File"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.util.IRmAffixConstants"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.service.IRmAffixService"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page impoorg.quickbundle.tools.helper.RmStringHelperlper"%>
<%@page impoorg.quickbundle.base.beans.factory.RmBeanFactorytory"%>
<%@page impoorg.quickbundle.tools.helper.RmJspHelperlper"%>
<%@page impoorg.quickbundle.tools.helper.RmVoHelperlper"%>
<%@page impojava.util.ListList"%>
<%@page impoorg.quickbundle.project.RmProjectHelperlper"%>
<%@page impoorg.springframework.jdbc.core.RowMapperpper"%>
<%@page impojava.sql.ResultSettSet"%>
<%@page impojava.sql.SQLExceptiontion"%>
<%@page impoorg.quickbundle.tools.helper.RmPopulateHelperlper"%>
<%@ include file="/jsp/support/upload/i_getParameter.jsp" %>
<%
try{
	Object[] aResult = RmUploadHelper.uploadRequestFiles(request, RmUploadHelper.getConfigurationInstance(new File(this.getServletContext().getRealPath("/upload/swf"))));
	RmAffixVo[] aAffixVo = new RmAffixVo[aResult.length - 1];
	for(int i=1; i<aResult.length; i++) {
		String[] fileInfo = (String[])aResult[i];
		aAffixVo[i-1] = new RmAffixVo();
		aAffixVo[i-1].setBs_keyword(request.getParameter("bs_keyword"));
		aAffixVo[i-1].setRecord_id(request.getParameter("record_id"));
		aAffixVo[i-1].setOrder_number(new BigDecimal(i+1));
		aAffixVo[i-1].setTitle(fileInfo[1].indexOf(".") > 0 ? fileInfo[1].substring(0,fileInfo[1].indexOf(".")) : fileInfo[1]);
		aAffixVo[i-1].setOld_name(fileInfo[1]);
		aAffixVo[i-1].setSave_name(fileInfo[0]);
		aAffixVo[i-1].setSave_size(new BigDecimal(new File(this.getServletContext().getRealPath("/upload/swf/" + fileInfo[0])).length()));
		aAffixVo[i-1].setMime_type(RmUploadHelper.getMimeTypeFromFile(new File(this.getServletContext().getRealPath("/upload/swf/" + fileInfo[0]))));
		aAffixVo[i-1].setEncoding(request.getParameter("encoding"));
		aAffixVo[i-1].setDescription(request.getParameter("description"));
		aAffixVo[i-1].setAuthor(request.getParameter("author"));
		RmVoHelper.markCreateStamp(request, aAffixVo[i-1]);
	}
	IRmAffixService affixService = (IRmAffixService)RmBeanFactory.getBean(IRmAffixConstants.SERVICE_KEY);
	affixService.insert(aAffixVo);
} catch(Exception e) {
	e.printStackTrace();
}
%>
<%@include file="/third/swfupload/i_uploadResult.jsp" %>
