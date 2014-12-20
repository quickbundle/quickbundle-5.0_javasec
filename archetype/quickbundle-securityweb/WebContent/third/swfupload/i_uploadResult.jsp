<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo"%>
<%
	String bs_keyword = request.getParameter("bs_keyword");
	String record_id = request.getParameter("record_id");
	int max_select_affix = 20;
	List<RmAffixVo> lResult = RmProjectHelper.getCommonServiceInstance().doQuery("SELECT * FROM RM_AFFIX WHERE BS_KEYWORD='" + bs_keyword + "' AND RECORD_ID='" + record_id + "' ORDER BY MODIFY_DATE DESC", new RowMapper() {
	    public RmAffixVo mapRow(ResultSet rs, int i) throws SQLException {
	    	RmAffixVo vo = new RmAffixVo();
	    	RmPopulateHelper.populate(vo, rs);
	    	return vo;
		    }
	}, 1, max_select_affix);
	for(RmAffixVo vo:lResult) {
%>
<span>
	<a target="_blank" href="<%=request.getContextPath() + "/upload/swf/" + vo.getSave_name()%>"><%=vo.getOld_name()%></a>&nbsp;&nbsp;
</span>
<span class="deleteUploadSpan">
	<a href="<%=request.getContextPath()%>/third/swfupload/globalUpload.jsp?bs_keyword=<%=bs_keyword%>&record_id=<%=record_id%>&to_delete_affix=<%=vo.getId()%>">删除</a>
</span><br/>
<%	}%>