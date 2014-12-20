<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="org.quickbundle.third.fileupload.RmUploadHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmProjectHelper"%>
<%@page import="org.quickbundle.modules.log.rmlog.util.IRmLogConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<%
	if(request.getParameter("isSubmit") != null && request.getParameter("isSubmit").length() > 0) {
		//RmAccessoryVo[] vo = RmUploadHelper.populateUploadInfo(request, "INSERT"); 
	}
%>
</head>
<body>
<script type="text/javascript">
	function insert_onClick() {
		form.action = "testReference.jsp?isSubmit=1";
		form.submit();
	}
</script>
<form name="form" method="post">
<table class="table_noFrame">
	<tr>
		<td valign="middle">
			<input name="button_update" class="button_ellipse" type="button" value="提交" onClick="javascript:insert_onClick();">
			<input name="button_back" class="button_ellipse" type="button" value="返回"  onclick="javascript:history.go(-1);" >
		</td>
	</tr>
</table>

<div id="ccParent0"> 
<table class="table_div_control">
	<tr> 
		<td>
			<img src="<%=request.getContextPath()%>/images/icon/07-0.gif" class="div_control_image" onClick="javascript:hideshow('ccChild0',this,'<%=request.getContextPath()%>/')">上传
		</td>
	</tr>
</table>
</div>

<div id="ccChild0"> 
	<table>
		<tr>
			<td width="15%" align="right" nowrap><strong>基本信息</strong><br><br></td>
			<td width="25%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="45%" align="left">&nbsp;</td>
		</tr>
		<tr>
			<td align="right"><span class="style_required_red">* </span>多附件</td>
	        <td align="left" colspan="3">
	            <input type="text" class="rm_affix" name="rm_affix_text" bs_keyword="TEST" record_id="11111" />
	        </td> 
		</tr>
		<tr>
			<td align="right">作者类型(单选框)</td>
	        <td align="left">
	            <%=org.quickbundle.tools.helper.RmJspHelper.getRadioField("rm_affix_author", -1, new String[][]{{"1", "商家logo"}, {"2", "周边环境"}}, null, "onclick='if(this.checked){form.rm_affix_text.setAttribute(\"author\", this.value);form.rm_affix_text.initAffix()}'")%>
	        </td> 
			<td align="right">作者类型(下拉选择)</td>
	        <td align="left">
	            <%=org.quickbundle.tools.helper.RmJspHelper.getSelectField("rm_affix_author2", -1,  new String[][]{{"1", "商家logo"}, {"2", "周边环境"}}, null, "onchange='form.rm_affix_text.setAttribute(\"author\", this.value);form.rm_affix_text.initAffix()'", true, null)%>
	        </td> 
		</tr>
		<tr>
			<td align="right">编码</td>
	        <td align="left">
	            <input type="text" class="text_field" name="rm_affix_encoding" />
	        </td> 
			<td align="right">描述</td>
	        <td align="left">
	            <input type="text" class="text_field" name="rm_affix_description" />
	        </td> 
		</tr>
		<tr>
			<td align="right"><span class="style_required_red">* </span>参照索引</td>
	        <td align="left">
	            <input type="text" class="text_qqs text_qqp" qq="RM_CODE_TYPE" qqpRegex="/[0-9]{19}/g" hiddenInputId="sampleReferIndex" name="sampleReferIndex_name" value="" maxLength="19" validate="notNull"/>
	            <input type="text" name="sampleReferIndex" /><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.sampleReferIndex, form.sampleReferIndex_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmCodeTypeAction.do?cmd=queryReference&referenceInputType=checkbox');"/>
	        </td> 
			<td align="right"><span class="style_required_red">* </span>参照索引2</td>
	        <td align="left">
	            <input type="text" class="text_qqs" qq="BD_CORP" hiddenInputId="sampleReferIndex2" name="sampleReferIndex2_name" value="" maxLength="19" validate="notNull"/>
	            <input type="text" name="sampleReferIndex2" /><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.sampleReferIndex2, form.sampleReferIndex2_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmCodeTypeAction.do?cmd=queryReference&referenceInputType=checkbox');"/>
	        </td> 
		</tr>
	</table>
</div>

<table style="border: 1px solid #9EB4CD; border-spacing: 1px;padding: 1px;">
	<tr>
		<td width="15%" align="right"><strong>基本信息</strong><br><br></td>
		<td width="35%">&nbsp;</td>
		<td width="15%" align="right">&nbsp;</td>
		<td width="35%">&nbsp;</td>
	</tr>
		<tr>
			<td align="right"><span class="style_required_red">* </span>栏目</td>
			<td align="left">
				<input type="text" class="text_field_reference_readonly" name="upload_oldName" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getUploadWindow(new Array(form.upload_saveName),'<%=request.getContextPath()%>/', 800, 600, '1-2', 'upload/haha');"/>
			</td>
			<td align="right"/>
			<td align="left">
				<input type="text" class="text_field" name="upload_saveName"><br>
				<input type="text" class="text_field" name="upload_isImage"><br>
				<input type="text" class="text_field" name="upload_abbreviatoryName"><br>
				<input type="text" class="text_field" name="upload_saveNameWidthHeight"><br>
				<input type="text" class="text_field" name="upload_abbreviatoryNameWidthHeight"><br>
				<input type="text" class="text_field" name="upload_remark"><br>
			</td>
		</tr>
		<tr>
			<td width="15%" align="right"><input name="button_back1" class="button_ellipse"" type="button" value="测试对象传值的上传"  onclick="thisData = getUploadWindowWithObj(thisData, '<%=request.getContextPath()%>/');" ></td>
			<td width="25%" align="left">&nbsp;</td>
			<td width="15%" align="right"><input name="button_back2" class="button_ellipse" type="button" value="察看值"  onclick="alert(getDataValue(thisData));" ></td>
			<td width="45%" align="left">&nbsp;</td>
		</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>主办部门</td>
        <td align="left">
            <input type="text" class="text_field_reference_readonly"  name="major_dept_name" hiddenInputId="major_dept,major_dept_name" inputName="主办部门" validate="notNull;" maxlength="500"/><input type="hidden" name="major_dept"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getPartyWindow(new Array(form.major_dept, form.major_dept_name),'<%=request.getContextPath()%>/', 'http://127.0.0.1:8080/jsp/support/deeptree/deeptree.jsp?inputType=radio&rootXmlSource=deeptree_data.jsp', 350, 600);" /> 
        </td> 
		<td align="right">协办部门</td>
        <td align="left">
            <input type="text" class="text_field_reference_readonly"  name="assist_dept_name" hiddenInputId="assist_dept,assist_dept_name" inputname="协办部门" maxlength="500"/><input type="hidden" name="assist_dept"/><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="var hiddenValue = assist_dept.value.replace(/;/g, ','); getPartyWindow(new Array(form.assist_dept, form.assist_dept_name),'<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/jsp/support/deeptree/deeptree.jsp?inputType=checkbox&defaultSelectedNodesValue=' + hiddenValue + '&rootXmlSource=<%=RmStringHelper.encodeUrl(request.getContextPath() + "/jsp/support/deeptree/xmlData.xml?submit_type=5")%>', 350, 600);"/> 
        </td> 
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>年月</td>
		<td align="left">
			<input type="text" class="text_field_reference_readonly" name="date0" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonth(form.date0,'<%=request.getContextPath()%>/', 800, 600);"/>
		</td>
		<td align="right"/>
		<td/>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>年月日</td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="date_112" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.date_112,'<%=request.getContextPath()%>/', 800, 600);"/>
		</td>
		<td align="right"/>
		<td/>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>时分秒</td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="date2" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getHourMinuteSecond(form.date2,'<%=request.getContextPath()%>/', 800, 600);"/>
		</td>
		<td align="right"/>
		<td/>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>年月日时分秒</td>
		<td>
			<input type="text" class="text_field_reference_readonly" name="date3" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDayHourMinuteSecond(form.date3,'<%=request.getContextPath()%>/', 800, 600);"/>
		</td>
		<td align="right"/>
		<td/>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>打开新页面</td>
		<td>
			<a href="javascript:getInfoDialog('testReference.jsp')" >点此打开新页面</a>
		</td>
		<td align="right">网页对话框</td>
		<td/>
			<a href="#" onclick="javascript:getDialog('<%=request.getContextPath()%>/RmCodeTypeAction.do?cmd=queryAll')" >点此打开网页对话框</a>
		<td/>
	</tr>
	<tr>
		<td align="right">SQL动态-select</td>
		<td>
			<%= RmJspHelper.getSelectField("test_name1", 20, RmProjectHelper.getCommonServiceInstance().paseToArrays("select record_id, old_name from RM_AFFIX"), "", "", false, null) %>
		</td>
		<td align="right">SQL动态-多选select</td>
		<td>
			<%= RmJspHelper.getSelectFieldMultiple("test_name5", 20, RmProjectHelper.getCommonServiceInstance().paseToArrays("select record_id, old_name from RM_AFFIX"), new String[0], "", 2) %>
		</td>
	</tr>
	<tr>
		<td align="right">SQL动态-radio</td>
		<td>
			<%= RmJspHelper.getRadioField("test_name2", 20, RmProjectHelper.getCommonServiceInstance().paseToArrays("select record_id, old_name from RM_AFFIX"), "", "") %>
		</td>
		<td align="right">SQL动态-checkbox</td>
		<td>
			<%= RmJspHelper.getCheckboxField("test_name3", 20, RmProjectHelper.getCommonServiceInstance().paseToArrays("select record_id, old_name from RM_AFFIX"), new String[0], "") %>
		</td>
	</tr>
	<tr>
		<td align="right">SQL动态-定制select</td>
		<td>
			<%= RmJspHelper.getSelectField("test_name1", 20, RmProjectHelper.getCommonServiceInstance().paseToArrays("select record_id, old_name, id from RM_AFFIX", new RowMapper() {
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					return new String[]{rs.getString(1), rs.getString(2) + "," + rs.getString(3)};
				}
			}), "", "", false, null) %>
		</td>
		<td align="right">用户参照</td>
		<td>
			<input type="text" class="text_field_reference" validate='notNull;' hiddenInputId="log_type_id" name="log_type_id_name" inputName="用户名" value="" /><input type="hidden" name="log_type_id"><img class="refButtonClass" src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getReference(new Array(form.log_type_id, form.log_type_id_name), '<%=request.getContextPath()%>/', '<%=request.getContextPath()%>/RmUserAction.do?cmd=queryReference&referenceInputType=checkbox');"/>
		</td>
	</tr>
</table>

<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/datepicker/WdatePicker.js"></script>

<div width="100%">
<table style="border: 1px solid #9EB4CD; border-spacing: 1px;padding: 1px;" width="100%">
	<tr>
		<td width="15%" align="right"><strong>基本信息</strong><br><br></td>
		<td width="35%">&nbsp;</td>
		<td width="15%" align="right">&nbsp;</td>
		<td width="35%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>老日期短</td>
        <td align="left">
            <input type="text" class="text_field_half_reference_readonly" name="dateMy97-01" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.dateMy97,'<%=request.getContextPath()%>/', 800, 600);"/> 到 <input type="text" class="text_field_half_reference_readonly" name="dateMy97-02" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.dateMy97,'<%=request.getContextPath()%>/', 800, 600);"/> 
        </td> 
		<td align="right">老日期长</td>
        <td align="left">
            <input type="text" class="text_field_reference_readonly" name="date1" inputName="栏目" value="" maxLength="19" validate="notNull"/><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.date1,'<%=request.getContextPath()%>/', 800, 600);"/> 
        </td> 
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span>新日期短</td>
        <td align="left">
            <input type="text" class="text_date_half" name="dateMy971" inputName="栏目" value="" maxLength="19" validate="notNull"/> 到 <input type="text" class="text_date_half" name="dateMy972" inputName="栏目" value="" maxLength="19" validate="notNull"/> 
        </td> 
		<td align="right">新日期长</td>
        <td align="left">
            <input type="text" class="text_date" name="dateMy973" inputName="栏目" value="" maxLength="19" validate="notNull"/> 
        </td> 
		<tr>
			<td align="right"><span class="style_required_red">* </span>单附件</td>
	        <td align="left" colspan="3">
	            <input type="text" class="rm_affix_single" name="rm_affix_single_id_name" />
	        </td> 
		</tr>
	</tr>
</table>
</div>
</form>			
<script type="text/javascript">
	var thisData = null;
	function getDataValue(thisData) {
		var str = "";
		try {
			if(thisData != null) {
				for(var i=0; i<thisData.length; i++) {
					for(var j=0; j<thisData[i].length; j++) {
						str += "\n" + thisData[i][j];
					}
					str += "\n";
				}
			}
		} catch(e) {

		}
		return str;
	}
</script>
</body>
</html>
<%
	//System.out.println(request.getRealPath("/upload") + "  " + request.getRealPath("upload") + "  " + request.getRealPath("upload/"));
	//System.out.println(request.getRealPath("/upload/dialog") + "  " + request.getRealPath("upload/dialog") + "  " + request.getRealPath("/upload/dialog/"));
 %>
