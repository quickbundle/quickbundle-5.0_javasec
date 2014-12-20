<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmpartyview.vo.RmPartyViewVo" %>
<%@ page import="org.quickbundle.orgauth.rmpartyview.util.IRmPartyViewConstants" %>
<%  //判断是否为修改页面
  	RmPartyViewVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmPartyViewConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmPartyViewVo)request.getAttribute(IRmPartyViewConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.util.numeral.RmBaseXNumeral"%>
<%@page import="org.quickbundle.util.numeral.RmNumeralConfig"%>
<%@page import="org.quickbundle.project.common.vo.RmCommonVo"%><html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmPartyViewAction";
	function insert_onClick(){  //插入单条数据
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
    	form.submit();
	}
</script>
</head>
<body>
<form name="form" method="post">
<br/>
<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%></td>
		<td colspan="3">
		<%
			String defaultBk = "A";
			if(!isModify) {
				RmCommonVo cvo = RmProjectHelper.getCommonServiceInstance().doQueryForObject("select max(BS_KEYWORD) max_bs_keyword from RM_PARTY_VIEW");
				String maxBk = cvo.getString("max_bs_keyword");
				if(maxBk.length() > 1) {
					RmNumeralConfig conf = new RmNumeralConfig();
					conf.setCharLength(maxBk.length());
					RmBaseXNumeral num = new RmBaseXNumeral(conf, maxBk);
					defaultBk = num.getNext();
				} else if(maxBk.length() == 1){
					int newValue = (int)maxBk.charAt(0) + 1;
					if(newValue > (int)'Z') {
						defaultBk = "AA";
					} else {
						defaultBk = String.valueOf((char)newValue);
					}
				}
			}
		%>
			<input type="text" <%=isModify ? "readonly" : ""%> class="text_field" name="bs_keyword" inputName="<%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%>" value="<%=!isModify ? defaultBk : "" %>" maxLength="50" validate="notNull;"/>
			(此关键字作为本视图下所有团体关系表中的PARTY_CODE的前缀，一旦新增不可修改)
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
		<td colspan="3">
			<input type="text" class="text_field" name="name" inputName="<%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("view_desc")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="view_desc" inputName="<%=IRmPartyViewConstants.TABLE_COLUMN_CHINESE.get("view_desc")%>" maxLength="2000" ></textarea>
		</td>
	</tr>
	</table>
  
<input type="hidden" name="id" value="">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_save" value="保存" onClickTo="javascript:<%=isModify?"update_onClick()":"insert_onClick()"%>"/>
			<input type="button" class="button_ellipse" id="button_cancel" value="取消" onClick="javascript:history.go(-1)"/>
			<input type="reset" class="button_ellipse" id="button_reset" value="重置"/>
		</td>
	</tr>
</table>

</form>
</body>
</html>
<script type="text/javascript">
<%  //取出要修改的那条记录，并且回写表单
	if(isModify) {  //如果本页面是修改页面
		out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromVo(resultVo)));  //输出表单回写方法的脚本
  	}
%>
</script>