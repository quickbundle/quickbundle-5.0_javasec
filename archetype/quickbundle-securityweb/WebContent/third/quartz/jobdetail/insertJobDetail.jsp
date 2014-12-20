<%@page import="java.util.Map.Entry"%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@ page import="org.quickbundle.tools.helper.RmVoHelper" %>
<%@ page import="org.quickbundle.third.quartz.jobdetail.vo.JobDetailVo" %>
<%@ page import="org.quickbundle.third.quartz.jobdetail.util.IJobDetailConstants" %>
<%  //判断是否为修改页面
  	JobDetailVo resultVo = null;  //定义一个临时的vo变量
	boolean isModify = false;  //定义变量,标识本页面是否修改(或者新增)
	if(request.getParameter("isModify") != null) {  //如果从request获得参数"isModify"不为空
		isModify = true;  //赋值isModify为true
  		if(request.getAttribute(IJobDetailConstants.REQUEST_BEAN) != null) {  //如果request中取出的bean不为空
  			resultVo = (JobDetailVo)request.getAttribute(IJobDetailConstants.REQUEST_BEAN);  //从request中取出vo, 赋值给resultVo
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
	var rmActionName = "JobDetailAction";
	function insert_onClick(){  //插入单条数据
    	form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=insert";
    	caculate_params();
	    form.submit();
	}
  	function update_onClick(id){  //保存修改后的单条数据
    	if(!getConfirm()) {  //如果用户在确认对话框中点"取消"
  			return false;
		}
	    form.action="<%=request.getContextPath()%>/" + rmActionName + ".do?cmd=update";
	    caculate_params();
    	form.submit();
	}
	/**
	 *计算params 计算参数
	 */
	function caculate_params(){
		var params={"params":[{"name":"","des":"","value":""}]};
		$.each($("tr[param='1']"), function(index,dom){
				var name,des;
				name=$(dom).find("input[pname]").first().attr("value").toString();					
				des=$(dom).find("input[pdes]").first().attr("value").toString();
				var o={"name":name,"des":des,"value":"none"}											
				params.params[index]=o;					
			});				
		$("input[name='<%=IJobDetailConstants.REQ_DATA_MAP %>']").attr("value",JSON.stringify(params));	
	}
	//Table_params 点击后处理
	function toggle_tb_params(){
		if($("input[name='chbox_params']").get(0).checked) {
			$("table[name='tb_params']").show();				
		} else {
			$("table[name='tb_params']").hide();
		}
	}
	$(function(){
		//bind event
		$("tr[param='0'] input").bind('focus',new_param);
	});
	//New param blank tr 新增行
	function new_param(){
		var otr=$("tr[param='0']");
		if(otr==null) return;
		var ntr=otr.clone();
		otr.after(ntr);
		otr.attr('param','1');
		otr.find("input").unbind('focus');
		ntr.find("input").bind('focus',new_param);
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
		<td align="right"><span class="style_required_red">* </span><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_name")%></td>
		<td>
			<input type="text" class="text_field" name="job_name" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_name")%>" value="" maxLength="200" validate="notNull;"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_group")%></td>
		<td>
			<input type="text" class="text_field" name="job_group" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_group")%>" value="" maxLength="200"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right"><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("description")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="description" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("description")%>" maxLength="250" ></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_class_name")%></td>
		<td colspan="3">
			<textarea class="textarea_limit_words" cols="60" rows="5" name="job_class_name" inputName="<%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("job_class_name")%>" maxLength="250" validate="notNull;"></textarea>
		</td>
	</tr>
	<tr>
		<td align="right"><span class="style_required_red">* </span><%=IJobDetailConstants.TABLE_COLUMN_CHINESE.get("requests_recovery")%></td>
		<td>
			<input type="checkbox" class="rm_checkbox" hiddenInputId="requests_recovery" name="requests_recovery_rmCheckbox"/><input type="hidden" name="requests_recovery"/>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<tr>
		<td align="right">参数</td>
		<td>
			<input type="checkbox" checked="true" name="chbox_params" onclick="toggle_tb_params();" ></input>
			<input type="hidden" name="<%=IJobDetailConstants.REQ_DATA_MAP %>" value=""></input>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
</table>
<table name="tb_params">
	<tr>
		<th>名称</th>
		<th>描述</th>
		<th></th>
		<th></th>
	</tr>
	<%
	if(resultVo != null && resultVo.getDataMap() != null){
		for(Entry<String, Object> en : resultVo.getDataMap().entrySet()){
	%>		
	<tr param='1'>
		<td >
			<input type="text"  pname="1" value="<%=en.getKey() %>"></input>
		</td>
		<td>
			<input type="text"  pdes="1" value="<%=en.getValue() %>"></input>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
	<%} }%>
	<tr param='0'>
		<td >
			<input pname="1" type="text" ></input>
		</td>
		<td>
			<input pdes="1" type="text" ></input>
		</td>
		<td align="right"></td>
		<td></td>
	</tr>
</table>  
<input type="hidden" name="is_durable" value="1"/>
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