<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.tools.helper.RmStringHelper" %>
<%@ page import="org.quickbundle.modules.message.vo.RmMessageVo" %>
<%@ page import="org.quickbundle.modules.message.IRmMessageConstants" %>
<%  //判断是否只读
    boolean isReadOnly = false;
    if("1".equals(request.getAttribute(IRmMessageConstants.REQUEST_IS_READ_ONLY))) {
        isReadOnly = true;
    } else if("1".equals(request.getParameter(IRmMessageConstants.REQUEST_IS_READ_ONLY))){
        isReadOnly = true;
    } 
%>
<%  //取出本条记录
	RmMessageVo resultVo = null;  //定义一个临时的vo变量
	resultVo = (RmMessageVo)request.getAttribute(IRmMessageConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
	RmVoHelper.replaceToHtml(resultVo);  //把vo中的每个值过滤
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><bean:message key="qb.web_title"/></title>
<script type="text/javascript">
<%if(!isReadOnly) {%>
	function find_onClick(){  //直接点到修改页面
		window.location.href="<%=request.getContextPath()%>/message/update/" + $("#head_id").val();
	}
	function delete_onClick(){  //直接点删除单条记录
		if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
			return false;
		}
		form.action="<%=request.getContextPath()%>/message/delete";
		form.submit();
	}  <%} %>
</script>
</head>
<body>
<form name="form" method="post">

<div class="button_area">
<%if(!isReadOnly) {%>
	<input type="button" class="button_ellipse" id="button_update" value="修改" onclick="javascript:find_onClick();" />
	<input type="button" class="button_ellipse" id="button_delete" value="删除" onclickto="javascript:delete_onClick();" /><%} %>
	<input type="button" class="button_ellipse" id="button_back" value="返回"  onclick="javascript:history.go(-1);" />
</div>

<table class="mainTable">
	<tr>
		<td align="right" width="20%">&nbsp;</td>
		<td width="35%">&nbsp;</td>
		<td align="right" width="20%">&nbsp;</td>
		<td width="25%">&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("biz_keyword")%>：</td>
		<td>${bean.biz_keyword}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("sender_id")%>：</td>
		<td>${bean.sender_id}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("parent_message_id")%>：</td>
		<td>${bean.parent_message_id}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("owner_org_id")%>：</td>
		<td>${bean.owner_org_id}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("template_id")%>：</td>
		<td>${bean.template_id}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("is_affix")%>：</td>
		<td>${bean.is_affix}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("record_id")%>：</td>
		<td>${bean.record_id}&nbsp;</td>
		<td align="right">&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="right"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY.get("message_xml_context")%>：</td>
		<td colspan="3">${bean.message_xml_context}&nbsp;</td>
	</tr>
	</table>

<input id="head_id" type="hidden" name="id" value="<%=RmStringHelper.prt(resultVo.getId())%>" />

<!-- child table begin -->
<div id="rowTabs">
    <ul>
        <li><a href="#rowTabs-<%=IRmMessageConstants.TABLE_NAME_RM_MESSAGE_RECEIVER%>"><%=IRmMessageConstants.TABLE_NAME_DISPLAY_RM_MESSAGE_RECEIVER %>列表</a></li>
        <li><a href="#rowTabs-rm_m_message_user">关联用户列表</a></li>
    </ul>
    <div id="rowTabs-<%=IRmMessageConstants.TABLE_NAME_RM_MESSAGE_RECEIVER%>">
        <div class="rowContainer">
            <table class="rowTable" namespace="<%=IRmMessageConstants.TABLE_NAME_RM_MESSAGE_RECEIVER%>" id="rowTable">
                <tr class="trheader">
                    <td align="left" style="width:8%;"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER.get("message_id")%></td>
                    <td align="left" style="width:8%;"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER.get("receiver_id")%></td>
                    <td align="left" style="width:8%;"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER.get("is_handle")%></td>
                    <td align="left" style="width:8%;"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER.get("handle_date")%></td>
                    <td align="left" style="width:8%;"><%=IRmMessageConstants.TABLE_COLUMN_DISPLAY_RM_MESSAGE_RECEIVER.get("handle_result")%></td>
                </tr>
            <c:forEach items="${bean.body}" var="v">
                <tr>
                    <td>${v.message_id}</td>
                    <td>${v.receiver_id}</td>
                    <td>${v.is_handle}</td>
                    <td>${v.handle_date}</td>
                    <td>${v.handle_result}</td>
                </tr>
			</c:forEach>
            </table>
        </div>
    </div>
    <div id="rowTabs-rm_m_message_user">
        <iframe id="tabInfo_frame" src="<%=request.getContextPath()%>/message/rm_m_message_user?message_id=<%=resultVo.getId()%><%if(isReadOnly) {%>&<%=IRmMessageConstants.REQUEST_IS_READ_ONLY %>=1<%} %>" frameborder="0" width="100%" height="500px" scrolling="yes"/>
    </div>
</div>
<!-- child table end -->

</form>
</body>
</html>