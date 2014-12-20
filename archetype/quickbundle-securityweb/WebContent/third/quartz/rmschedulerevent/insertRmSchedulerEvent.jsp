<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.vo.RmSchedulerEventVo" %>
<%@ page import="org.quickbundle.third.quartz.rmschedulerevent.util.IRmSchedulerEventConstants" %>
<%  //判断是否为修改页面
  	RmSchedulerEventVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IRmSchedulerEventConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (RmSchedulerEventVo)request.getAttribute(IRmSchedulerEventConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
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
	var rmActionName = "RmSchedulerEventAction";
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
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%></td>
		<td>
			<input type="text" class="text_field" name="job_name" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%></td>
		<td>
			<input type="text" class="text_field" name="job_group" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("job_group")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%></td>
		<td>
			<input type="text" class="text_field" name="trigger_name" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_name")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%></td>
		<td>
			<input type="text" class="text_field" name="trigger_group" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("trigger_group")%>" value="" maxLength="100" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%></td>
		<td>
			<input type="text" class="text_field" name="fire_instance_id" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("fire_instance_id")%>" value="" maxLength="100" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%></td>
		<td>
			<input type="text" class="text_field" name="event_type" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("event_type")%>" value="" maxLength="25" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%></td>
		<td>
			<input type="text" class="text_field" name="cost_millis" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("cost_millis")%>" value="" integerDigits="38" decimalDigits="0" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%></td>
		<td>
			<input type="text" class="text_field" name="result_status" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result_status")%>" value="" maxLength="1" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%></td>
		<td>
			<input type="text" class="text_date" name="create_time" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_time")%>" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%></td>
		<td>
			<input type="text" class="text_field" name="create_ip" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("create_ip")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%></td>
		<td>
			<input type="text" class="text_field" name="uuid" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("uuid")%>" value="" maxLength="25" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("is_archive")%></td>
		<td>
			<input type="text" class="text_field" name="is_archive" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("is_archive")%>" value="" maxLength="1" />
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="result" inputName="<%=IRmSchedulerEventConstants.TABLE_COLUMN_CHINESE.get("result")%>" maxLength="2000" ></textarea>
		</td>
	</tr>
	</table>
  
<input type="hidden" name="id" value="">

<table align="center">
	<tr>
		<td><br>
			<input type="button" class="button_ellipse" id="button_save" value="保存" onclickto="javascript:<%=isModify?"update_onClick()":"insert_onClick()"%>"/>
			<input type="button" class="button_ellipse" id="button_cancel" value="取消" onclick="javascript:history.go(-1)"/>
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