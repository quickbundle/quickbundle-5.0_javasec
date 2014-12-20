<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.orgauth.rmauthorize.vo.RmAuthorizeVo" %>
<%@ page import="org.quickbundle.orgauth.rmauthorize.util.IRmAuthorizeConstants" %>
<%  //判断是否为修改页面
  	RmAuthorizeVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmAuthorizeConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmAuthorizeVo)request.getAttribute(IRmAuthorizeConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
  		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<%@ include file="/jsp/include/rmGlobal_insert.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
	var rmActionName = "RmAuthorizeAction";
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
		<td align="right"><span class="style_required_red">* </span><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("name")%></td>
		<td>
			<input type="text" class="text_field" name="name" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%></td>
		<td>
			<input type="text" class="text_field" name="bs_keyword" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("bs_keyword")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("is_alone_table")%></td>
		<td>
			<input type="checkbox" class="rm_checkbox" hiddenInputId="is_alone_table" name="is_alone_table_rmCheckbox"/><input type="hidden" name="is_alone_table"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_resource_table_name")%></td>
		<td>
			<input type="text" class="text_field" name="authorize_resource_table_name" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_resource_table_name")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_resrec_table_name")%></td>
		<td>
			<input type="text" class="text_field" name="authorize_resrec_table_name" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_resrec_table_name")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_affix_table_name")%></td>
		<td>
			<input type="text" class="text_field" name="authorize_affix_table_name" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("authorize_affix_table_name")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("settiing_option")%></td>
		<td>
			<%=RmJspHelper.getSelectField("settiing_option", -1, RmGlobalReference.get(IRmAuthorizeConstants.DICTIONARY_RM_OPTION_TYPE), "", "inputName='" + IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("settiing_option") + "' ", true) %>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("custom_code")%></td>
		<td colspan="3" >
			<div class="style_required_red">注意：sql_after_from=""的引号内一定要大写<div>
			<textarea class="textarea_limit_words" cols="80" rows="15" name="custom_code" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("custom_code")%>" maxLength="4000" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="80" rows="6" name="description" inputName="<%=IRmAuthorizeConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="500" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">简单样例（单表权限）</td>
		<td colspan="3">
			<textarea readonly cols="80" rows="6">
<authorize sql_after_from="RM_CODE_TYPE" join_table="RM_CODE_TYPE" join_table_column="TYPE_KEYWORD" join_table_column_full="" join_table_column_pk="ID" join_table_column_key="NAME" sql_after_where="">
  <consumer>
    <table sql_after_from="RM_CODE_TYPE" join_table="RM_CODE_TYPE" join_table_column="TYPE_KEYWORD" old_resource_id_full="" />
  </consumer>
  <rm_authorize_resource>
    <default_access>0</default_access>
    <default_is_affix_data>0</default_is_affix_data>
    <default_is_recursive>0</default_is_recursive>
    <default_access_type>1</default_access_type>
  </rm_authorize_resource>
</authorize>
			</textarea>
		</td>
	</tr>
	<tr>
		<td align="right">复杂样例1（字典表权限）</td>
		<td colspan="3">
			<textarea readonly cols="80" rows="6">
<authorize sql_after_from="RM_CODE_TYPE JOIN RM_CODE_DATA ON RM_CODE_TYPE.ID=RM_CODE_DATA.CODE_TYPE_ID" join_table="RM_CODE_DATA" join_table_column="DATA_KEY" join_table_column_pk="ID" join_table_column_key="DATA_VALUE" sql_after_where="RM_CODE_TYPE.TYPE_KEYWORD='STMNT_BUSINESS_TYPE'">
  <consumer>
    <table sql_after_from="STMNT_WAYBILL" join_table="STMNT_WAYBILL" join_table_column="STMNT_BUSINESS_TYPE" old_resource_id_full=""/>
  </consumer>
  <rm_authorize_resource>
    <default_access>0</default_access>
    <default_is_affix_data>0</default_is_affix_data>
    <default_is_recursive>0</default_is_recursive>
    <default_access_type>1</default_access_type>
  </rm_authorize_resource>
</authorize>
			</textarea>
		</td>
	</tr>
	<tr>
		<td align="right">复杂样例2（组织机构权限）</td>
		<td colspan="3">
			<textarea readonly cols="80" rows="6">
<authorize sql_after_from="RM_PARTY_RELATION JOIN RM_PARTY ON RM_PARTY_RELATION.CHILD_PARTY_ID = RM_PARTY.ID JOIN RM_PARTY_TYPE ON RM_PARTY.PARTY_TYPE_ID = RM_PARTY_TYPE.ID" join_table="RM_PARTY" join_table_column="ID" join_table_column_full="TO_CHAR(RM_PARTY.ID)" join_table_column_pk="ID" join_table_column_key="NAME" sql_after_where="RM_PARTY_RELATION.PARTY_VIEW_ID = 1000200700000000001 AND RM_PARTY_TYPE.BS_KEYWORD = 'COMPANY' AND LENGTH(RM_PARTY_RELATION.CHILD_PARTY_CODE) = 20 AND RM_PARTY.ID != 1000201100000000001 ORDER BY RM_PARTY_RELATION.ORDER_CODE, RM_PARTY.ID">
  <consumer>
    <table sql_after_from="STMNT_WAYBILL" join_table="STMNT_WAYBILL" join_table_column="STMNT_BUSINESS_TYPE" old_resource_id_full="" />
  </consumer>
  <rm_authorize_resource>
    <default_access>0</default_access>
    <default_is_affix_data>0</default_is_affix_data>
    <default_is_recursive>0</default_is_recursive>
    <default_access_type>1</default_access_type>
  </rm_authorize_resource>
</authorize>
			</textarea>
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