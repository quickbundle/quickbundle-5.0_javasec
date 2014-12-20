<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.List"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>

<%@page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo"%><div>
<%
String bs_keyword = request.getParameter("bs_keyword");
String record_id = request.getParameter("record_id");
	List<RmAffixVo> lResult = RmProjectHelper.getCommonServiceInstance().query("SELECT * FROM RM_AFFIX WHERE BS_KEYWORD='" + bs_keyword + "' AND RECORD_ID='" + record_id + "' ORDER BY MODIFY_DATE DESC", new RowMapper() {
	    public RmAffixVo mapRow(ResultSet rs, int i) throws SQLException {
	    	RmAffixVo vo = new RmAffixVo();
	    	RmPopulateHelper.populate(vo, rs);
	    	return vo;
		}
	});
%>		
<% if( lResult != null && lResult.size() > 0){ %>
<table width="48%" border="0" cellspacing="1" cellpadding="0"  class="mainTable" align="left">
<tr>
	<td align="center">
		<img alt="下载" src="<%=request.getContextPath()%>/images/default/donwload.jpg">
	</td>
</tr>
  <tr > 
    <td>
      <table width="100%" border="0" cellspacing="1" cellpadding="2" >
        <tr align="center" > 
			<td width="49%"><b>名称</b></td>
			<td width="15%"><b>格式</b></td>
			<td width="21%"><b>大小</b></td>
			<td width="15%"><b>下载</b></td>
        </tr>
        <% for(RmAffixVo vo:lResult) {%>
        <tr> 
          <td align=center><%=vo.getTitle().replaceAll("[\\.][^\\.]*$", "")%></td>
          <td align=center><%=vo.getOld_name().replaceAll(".*[\\.]([^\\.]*)$", "$1")%></td>
		  <td align=center><%=vo.getSave_size().longValue()/1024%>KB</td>
          <td align="center"><a target="_blank" href="<%=request.getContextPath() + "/upload/swf/" + vo.getSave_name()%>">下载</a></td>
        </tr>
        <% } %>     
      </table>
    </td>
  </tr>
</table>
<% } %>
</div>







