<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@page import="org.quickbundle.tools.helper.RmJspHelper"%>
<%@page import="org.quickbundle.project.RmGlobalReference"%>
<%@page import="org.quickbundle.tools.helper.RmVoHelper"%>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<script src="<%=request.getContextPath()%>/js/rm-insert.js" type=text/javascript></script>
<script src="<%=request.getContextPath()%>/third/ckeditor/ckeditor.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm-based architecture project</title>
<%
	if("1".equals(request.getParameter("isSubmit"))) {
		System.out.println("brand=" + request.getParameter("brand"));
		System.out.println("out_stander=" + request.getParameter("out_stander"));
		System.out.println("leader_use=" + request.getParameter("leader_use"));
		System.out.println("insurance_type=" + request.getParameter("insurance_type"));
		System.out.println("insurance_type2=" + request.getParameter("insurance_type2"));
		System.out.println("vehicle_number=" + request.getParameter("vehicle_number"));
	}
%>
<script type="text/javascript">
	function insert_onClick(){  //插入单条数据
    	form.action="testValidate.jsp?isSubmit=1";
	    form.submit();
	}
</script>
</head>
<body>
<script type="text/javascript">
	writeTableTop('新增页面','<%=request.getContextPath()%>/');  //显示本页的页眉
</script>
<form name="form" method="post">

<table class="table_noFrame">
	<tr>
		<td>
			<input name="button_save" class="button_ellipse" type="button" value="保存" onClickTo="javascript:insert_onClick()">
			<input name="button_cancel" class="button_ellipse" type="button" value="取消"  onClick="javascript:history.go(-1)" >
		</td>
	</tr>
</table>

<div id="ccParent1"> 
<table class="table_div_control">
	<tr> 
		<td>
			<img src="<%=request.getContextPath()%>/images/icon/07-0.gif" class="div_control_image" onClick="javascript:hideshow('ccChild1',this,'<%=request.getContextPath()%>/')">新增
		</td>
	</tr>
</table>
</div>

<div id="ccChild1"> 
<table class="table_div_content">
<tr><td> 
	<table class="table_div_content_inner">
		<tr>
			<td width="15%" align="right" nowrap><strong>基本信息</strong><br><br></td>
			<td width="35%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="35%" align="left">&nbsp;</td>
		</tr>
<tr>
					<td align="right"><span class="style_required_red">* </span>牌号</td>
					<td align="left">
						<input type="text" class="text_field" name="vehicle_number" inputName="牌号" value="123" maxLength="50" validate="notNull;" disabled='true'/>
					</td>
					<td align="right">车身颜色</td>
					<td align="left">
						<input type="text" class="text_field" name="colour" inputName="车身颜色" value="" maxLength="200" />
					</td>
				</tr>		
				<tr>
					<td align="right">品牌</td>
					<td align="left">
						<input type="text" class="text_field" name="brand" inputName="品牌" value="" maxLength="200" />
					</td>
					<td align="right">制造商</td>
					<td align="left">
						<input type="text" class="text_field" name="manufacturer" inputName="制造商" value="" maxLength="200" />
					</td>
				</tr>
            	<tr>
					<td align="right">厂牌车型</td>
					<td align="left">
						<input type="text" class="text_field" name="factory_type" inputName="厂牌车型" value="" maxLength="200" />
					</td>
					<td align="right">座位数</td>
					<td align="left">
						<input type="text" class="text_field" name="seat_number" maxLength="11" integerDigits="10" decimalDigits="0" />
					</td>
				</tr>
				<tr>
					<td align="right">吨位(吨)</td>
					<td align="left">
						<input type="text" class="text_field" name="tonnage" maxLength="13" integerDigits="10" decimalDigits="2" />
					</td>
					<td align="right">排量(升)</td>
					<td align="left">
						<input type="text" class="text_field" name="releaser" maxLength="13" integerDigits="10" decimalDigits="2" />
					</td>
				</tr>
				<tr>
					<td align="right">车型</td>
					<td align="left">
						<input type="text" class="text_field" name="vehicle_type" inputName="车型" value="" maxLength="50" />
					</td>
					<td align="right">变速器</td>
					<td align="left">
						<%=RmJspHelper.getSelectField("derailleur", -1, RmGlobalReference.get("VEHICLE_DERAILLEUR"), "", " inputName='变速器' ", true)%>
					</td>
				</tr>
				<tr>
					<td align="right">车架号</td>
					<td align="left">
						<input type="text" class="text_field" name="body_number" inputName="车架号" value="" maxLength="200" />
					</td>
					<td align="right">发动机号</td>
					<td align="left">
						<input type="text" class="text_field" name="engine_number" inputName="发动机号" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right">客货类型</td>
					<td align="left">
					<%=RmJspHelper.getSelectField("useful_type", -1,  RmGlobalReference.get("VEHICLE_USEFULTYPE"), "", " inputName='客货类型' ",true)%>
					</td>
					<td align="right">车辆来源</td>
					<td align="left">
					<%=RmJspHelper.getSelectField("vehicle_source", -1,  RmGlobalReference.get("VEHICLE_SOUCE"), "", " inputName='车辆来源'",true)%>
					</td>
				</tr>
				<tr>
					<td align="right">购置方式</td>
					<td align="left">
						<input type="text" class="text_field" name="buy_mode" inputName="购置方式" value="" maxLength="50" />
					</td>
					<td align="right">新车购置日期</td>
					<td align="left">
						<input type="text" class="text_field_reference_readonly" name="buy_date" maxLength="25" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.buy_date,'<%=request.getContextPath()%>/');"/>
					</td>
				</tr>
				<tr>
					<td align="right">新车购置价(元)</td>
					<td align="left">
						<input type="text" class="text_field" name="buy_price" maxLength="24" integerDigits="19" decimalDigits="4" />
					</td>
					<td align="right">初次登记日期</td>
					<td align="left">
						<input type="text" class="text_field_reference_readonly" name="register_date" maxLength="25" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.register_date,'<%=request.getContextPath()%>/');"/>
					</td>
				</tr>
				
		<tr>
			<td width="15%" align="right" nowrap><strong>使用信息</strong><br><br></td>
			<td width="35%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="35%" align="left">&nbsp;</td>
		</tr>
				<tr>
					<td align="right"><span class="style_required_red">* </span>车辆档案号</td>
					<td align="left">
						<input type="text" class="text_field" name="record_number" inputName="车辆档案号" value="" maxLength="200" validate="notNull;"/>
					</td>
					<td align="right">资产卡片编号</td>
					<td align="left">
						<input type="text" class="text_field" name="asset_number" inputName="资产卡片编号" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right">资产原值(元)</td>
					<td align="left">
						<input type="text" class="text_field" name="price" maxLength="24" integerDigits="19" decimalDigits="4" />
					</td>
					<td align="right"><span class="style_required_red">* </span>所属单位</td>
					<td align="left">
						<input type="text" class="text_field" name="belong_unit" inputName="所属单位" value="" maxLength="50" validate="notNull;"/>
					</td>
				</tr>
				<tr>
					<td align="right">使用部门</td>
					<td align="left">
						<input type="text" class="text_field" name="use_department" inputName="使用部门" value="" maxLength="50" />
					</td>
					<td align="right">机构代码</td>
					<td align="left">
						<input type="text" class="text_field" name="org_code" inputName="机构代码" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right">支局</td>
					<td align="left">
						<input type="text" class="text_field" name="office" inputName="支局" value="" maxLength="200" />
					</td>
					<td align="right">班组</td>
					<td align="left">
						<input type="text" class="text_field" name="at_group" inputName="班组" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right"><span class="style_required_red">* </span>行驶证车主</td>
					<td align="left">
						<input type="text" class="text_field" name="owner" inputName="行驶证车主" value="" maxLength="50" validate="notNull;"/>
					</td>
					<td align="right">使用人</td>
					<td align="left">
						<input type="text" class="text_field" name="usename" inputName="使用人" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right">开始使用日期</td>
					<td align="left">
						<input type="text" class="text_field_reference_readonly" name="start_use_date" maxLength="25" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.start_use_date,'<%=request.getContextPath()%>/');"/>
					</td>
					<td align="right">使用方式</td>
					<td align="left">
					<%=RmJspHelper.getSelectField("mode", -1, RmGlobalReference.get("VEHICLE_USE_MODE"), "", " inputName='使用方式'",true)%>
					</td>
				</tr>
				<tr>
					<td align="right">驾驶员</td>
					<td align="left">
						<input type="text" class="text_field" name="driver" inputName="驾驶员" value="" maxLength="200" />
					</td>
					<td align="right">驾照号</td>
					<td align="left">
						<input type="text" class="text_field" name="driver_card" inputName="驾照号" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
					<td align="right">使用能源</td>
					<td align="left">
					   <%=RmJspHelper.getSelectField("energy", -1, RmGlobalReference.get("VEHICLE_ENERGY"), "", " inputName='使用能源' ", true)%>
					</td>
					<td align="right">已行驶里程(公里)</td>
					<td align="left">
						<input type="text" class="text_field" name="run_mile" maxLength="11" integerDigits="10" decimalDigits="0" />
					</td>
				</tr>
				<tr>
					<td align="right">大修次数</td>
					<td align="left">
						<input type="text" class="text_field" name="repair_number" maxLength="11" integerDigits="10" decimalDigits="0" />
					</td>
					<td align="right">大修累计费用(元)</td>
					<td align="left">
						<input type="text" class="text_field" name="repair_price" maxLength="24" integerDigits="19" decimalDigits="4" />
					</td>
				</tr>
				<tr>
					<td align="right">大修备注</td>
					<td colspan="3" align="left">
						<textarea class="textarea_limit_words" cols="60" rows="5" name="repair_note" maxLength="500"  id="repair_noteId"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right"><span class="style_required_red">* </span>车辆使用简介</td>
					<td colspan="3" align="left">
						<textarea class="textarea_limit_words" cols="60" rows="5"  id="use_note" maxLength="500" name="use_note" validate="notNull;"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right">车辆状态</td>
					<td align="left">
					<org.quickbundle.project.sn.RmJspHelper.getSelectField("estate", -1,org.quickbundle.project.RmGlobalReference.get("VEHICLE_ESTATE"), ""," inputName='车辆状态'",true)%>
					</td>
					<td align="right">用车性质</td>
					<td align="left">
org.quickbundle.project.tools.helper.RmJspHelper.getSelectField("use_type", -1,org.quickbundle.project.RmGlobalReference.get("VEHICLE_USE_TYPE"), ""," inputName='用车性质'",true)%>
					</td>
				</tr>
				<tr>
					<td align="right">状态变更</td>
					<td align="leforg.quickbundle.projectap.rm.tools.helper.RmJspHelper.getSelectField("change_note", -1,org.quickbundle.project.RmGlobalReference.get("VEHICLE_CHANGE_NOTE"), ""," inputName='状态变更'",true)%>
					</td>
					<td align="right">出租单位名称</td>
					<td align="left">
						<input type="text" class="text_field" name="hire_company_name" inputName="出租单位名称" value="" maxLength="200" />
					</td>
				</tr>
				<tr>
			<td width="15%" align="right" nowrap><strong>保险信息</strong><br><br></td>
			<td width="35%" align="left">&nbsp;</td>
			<td width="15%" align="right">&nbsp;</td>
			<td width="35%" align="left">&nbsp;</td>
		</tr>
				<tr>
					<td align="right">被保险人名称</td>
					<td align="left">
						<input type="text" class="text_field" name="insurance_name" inputName="被保险人名称" value="" maxLength="200" />
					</td>
					<td align="right"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right">生效日期</td>
					<td align="left">
						<input type="text" class="text_field_reference_readonly" name="start_date" maxLength="25" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.start_date,'<%=request.getContextPath()%>/');"/>
					</td>
					<td align="right"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right">失效日期</td>
					<td align="left">
						<input type="text" class="text_field_reference_readonly" name="end_date" maxLength="25" /><img class="refButtonClass"	src="<%=request.getContextPath()%>/images/09.gif" onClick="javascript:getYearMonthDay(form.end_date,'<%=request.getContextPath()%>/');"/>
					</td>
					<td align="right"></td>
					<td align="left"></td>
				</tr>
				<tr>
					<td align="right">备注</td>
					<td colspan="3" align="left">
						<textarea class="textarea_limit_words" cols="60" rows="5" name="vehicle_note" maxLength="500"  id="vehicle_noteId"></textarea>
					</td>
				</tr>
				</table>
</td></tr>
</table>
</div>
            
<input type="hidden" name="id" value="">
</form>			
<script type="text/javascript">
	writeTableBottom('<%=request.getContextPath()%>/');  //显示本页的页脚
</script>
</body>
</html>
<script type="text/javascript">
<%
	out.print(RmVoHelper.writeBackMapToForm(RmVoHelper.getMapFromRequest(request)));  //输出表单回写方法的脚本
	out.print("writeBackMapToForm();\n");  //输出执行回写方法
%>
</script>