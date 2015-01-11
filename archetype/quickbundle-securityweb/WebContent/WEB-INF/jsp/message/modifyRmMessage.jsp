<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="com.cipher.rmmessage.vo.RmMessageVo" %>
<%@ page import="com.cipher.rmmessage.IRmMessageConstants" %>

<%  //取出本条记录
    RmMessageVo resultVo = null;  //定义一个临时的vo变量
    resultVo = (RmMessageVo)request.getAttribute(IRmMessageConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
    RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<div class="modal-header">
					<button data-dismiss="modal" class="close" type="button"></button>
					<h3>修改</h3>
				</div>
				<div class="modal-body">
					<form action="<%=request.getContextPath()%>/rmmessage/update" method="post">
						<div class="control-group">
							<div class="controls">
							<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%></span> 
					<input class="m-wrap" name="biz_keyword" id="biz_keyword" type="text" value ="${bean.biz_keyword}" maxLength="25"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%></span> 
					<input class="m-wrap" name="sender_id" id="sender_id" type="text" value ="${bean.sender_id}" maxLength="9"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%></span> 
					<input class="m-wrap" name="parent_message_id" id="parent_message_id" type="text" value ="${bean.parent_message_id}" maxLength="9"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%></span> 
					<input class="m-wrap" name="owner_org_id" id="owner_org_id" type="text" value ="${bean.owner_org_id}" maxLength="25"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%></span> 
					<input class="m-wrap" name="template_id" id="template_id" type="text" value ="${bean.template_id}" maxLength="9"/>
					</div>
	<div class="input-prepend">
		<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%></span> 
		<span class="rm_affix" bs_keyword="<%=IRmMessageConstants.TABLE_NAME%>" record_id="<%=resultVo.getId()%>">${bean.is_affix}&nbsp;</span>
	</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%></span> 
					<input class="m-wrap" name="record_id" id="record_id" type="text" value ="${bean.record_id}" maxLength="25"/>
					</div>
	<div class="input-prepend">
		<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%></span> 
		${message_xml_context}
	</div>
	<div class="input-prepend">
				<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("usable_status")%></span> 
				<%=RmGlobalReference.get(IRmMessageConstants.DICTIONARY_RM_ADMIN_TYPE, resultVo.getUsable_status())%>&nbsp;</div>
			<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_date")%></span> 
					<input class="m-wrap" name="modify_date" id="modify_date" type="text" value ="${bean.modify_date}" maxLength="9"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_ip")%></span> 
					<input class="m-wrap" name="modify_ip" id="modify_ip" type="text" value ="${bean.modify_ip}" maxLength="22"/>
					</div>
	<div class="input-prepend">
					<span class="add-on"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("modify_user_id")%></span> 
					<input class="m-wrap" name="modify_user_id" id="modify_user_id" type="text" value ="${bean.modify_user_id}" maxLength="9"/>
					</div>
	<p>
									<input id="id" name="id" type="hidden" /> <input type="submit" class="btn blue btn-block" value="提交" />
								</p>
							</div>
						</div>
					</form>
				</div>
</body>
</html>
	