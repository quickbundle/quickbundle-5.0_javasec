<%@ page contentType="text/html; charset=UTF-8" language="java" %><%@ include file="/jsp/support/upload/i_getParameter.jsp" %><%
    //判断是否只读
	boolean isReadOnly = false;
	if("1".equals(request.getParameter(IGlobalConstants.REQUEST_IS_READ_ONLY))) {
		isReadOnly = true;
	}else if("1".equals(request.getParameter(IGlobalConstants.REQUEST_IS_READ_ONLY))){
		isReadOnly = true;
	}
String[] aTo_delete_affix = request.getParameterValues("to_delete_affix");
if(aTo_delete_affix != null && aTo_delete_affix.length > 0) {
	final HttpServlet thisJsp = this;
	List<RmAffixVo> lResult = RmProjectHelper.getCommonServiceInstance().query("SELECT * FROM RM_AFFIX WHERE ID IN (" + RmStringHelper.parseToSQLString(aTo_delete_affix) + ")", new RowMapper() {
	    public RmAffixVo mapRow(ResultSet rs, int i) throws SQLException {
		    	RmAffixVo vo = new RmAffixVo();
		    	RmPopulateHelper.populate(vo, rs);
		    	return vo;
	    }
	});
	if(lResult.size() > 0) {
		RmProjectHelper.getCommonServiceInstance().doUpdate("DELETE FROM RM_AFFIX WHERE ID IN (" + RmStringHelper.parseToSQLString(aTo_delete_affix) + ")");		
	}
		//删除附件
		for(RmAffixVo vo : lResult) {
			File f = new File(thisJsp.getServletContext().getRealPath("/upload/swf/" + vo.getSave_name()));
			RmFileHelper.delete(f);
		}
	if("json".equals(request.getParameter("output_type"))) {
		Map result = new HashMap();
		result.put("result", "删除成功");
		out.print(RmObjectMapper.getInstance().writeValueAsString(result));
		return;
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SWFUpload Demos - Simple Demo</title>
<link href="css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/swfupload.js"></script>
<script type="text/javascript" src="js/swfupload.queue.js"></script>
<script type="text/javascript" src="js/fileprogress.js"></script>
<script type="text/javascript" src="js/handlers.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.2.min.js"></script>
<script type="text/javascript">
var swfu;
window.onload = function() {
	var settings = {
		flash_url : "flash/swfupload.swf",
		upload_url: "upload.jsp?bs_keyword=<%=request.getParameter("bs_keyword")%>&record_id=<%=request.getParameter("record_id")%><%
			if(request.getParameter("encoding") != null) {
				out.print("&encoding=" + request.getParameter("encoding"));
			}
			if(request.getParameter("description") != null) {
				out.print("&description=" + request.getParameter("description"));
			}
			if(request.getParameter("author") != null) {
				out.print("&author=" + request.getParameter("author"));
			}
		%>",
		file_size_limit : "<%=RmUploadHelper.DEFAULT_sizeMax/(1024*1024)%> MB",
		file_types : "*.*",
		file_types_description : "All Files",
		file_upload_limit_max : <%=maxFileNumber%>,
		file_upload_limit : <%=maxFileNumber%>,
		file_queue_limit : 0,
		custom_settings : {
			progressTarget : "fsUploadProgress",
			cancelButtonId : "btnCancel"
		},
		debug: false,

		// Button settings
		button_image_url: "images/TestImageNoText_65x29.png",
		button_width: "65",
		button_height: "29",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '<span class="theFont">添加</span>',
		button_text_style: ".theFont { font-size: 16; }",
		button_text_left_padding: 12,
		button_text_top_padding: 3,
		
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete,
		queue_complete_handler : queueComplete	// Queue plugin event
	};
	swfu = new SWFUpload(settings);
<%
	if(!isReadOnly) {
%>
	window.setTimeout("refreshFileUploadLimit()", 500);
<%	}%>	
};
function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.progressTarget);
		progress.setComplete();
		progress.setStatus("上传完毕!\n");
		progress.toggleCancel(false);
		//写到结果
		document.getElementById("uploadResult").innerHTML = serverData;
		window.setTimeout("refreshFileUploadLimit()", 500);
		window.setTimeout("resetParentIframe()", 800);
	} catch (ex) {
		//alert(ex.message);
		this.debug(ex);
	}
}

function resetParentIframe() {
	//重设高度
	var iframes = parent.document.frames;
	for(var i=0; i<iframes.length; i++) {
		if(iframes[i].name = "contentFrame") {
			reinitIframe(iframes[i]);
		}
	}
}

function reinitIframe(iframe){
	//alert(iframe + " " + iframe.document + " " + iframe.document.body.outerHTML);
	var bHeight = iframe.document.body.scrollHeight;
	var dHeight = iframe.document.documentElement.scrollHeight;
	//alert(bHeight + " " + dHeight);
	var height = Math.max(bHeight, dHeight);
	iframe.height =  height;
}

function refreshFileUploadLimit() {
	var currentFileCount = document.getElementById("uploadResult").getElementsByTagName("SPAN").length;
	var remainUploadCount = swfu.settings.file_upload_limit_max - currentFileCount;
	if(remainUploadCount < 1) {
		swfu.setButtonDisabled(true);
		setFeedback("您最多能上传" + swfu.settings.file_upload_limit_max + "个文件，已经上传了" + document.getElementById("uploadResult").getElementsByTagName("SPAN").length + "个，不能继续上传了");
	} else {
		swfu.setFileUploadLimit(remainUploadCount);
		<%if(maxFileNumber < maxFeedback) {%>
		setFeedback("您最多能上传" + swfu.settings.file_upload_limit_max + "个文件，还能上传" + swfu.settings.file_upload_limit + "个");
		<%}%>
	}
	//setFeedback2("max=" + swfu.settings.file_upload_limit_max + "<br>length=" + document.getElementById("uploadResult").getElementsByTagName("SPAN").length + "<br> swfu.settings.file_upload_limit=" +  swfu.settings.file_upload_limit);
	try {
		var iframes = parent.document.getElementsByTagName("IFRAME");
		for(var i=0; i<iframes.length; i++) {
			if(window == iframes[i].contentWindow) {
				var inputObj = iframes[i].parentNode.children[0];
				inputObj.value = currentFileCount > 0 ? 1 : 0;
			}
		}
	} catch(e){alert(e.message);}
}

function setFeedback(msg) {
	document.getElementById("divStatusFeedback").innerHTML = msg;
}

function setFeedback2(msg) {
	document.getElementById("divStatusFeedback2").innerHTML = msg;
}
<%if(isReadOnly) {%>
$(document).ready(function () {
	var aSpanDelete = $("span[class='deleteUploadSpan']");
	for(var i=0; i<aSpanDelete.length; i++) {
		aSpanDelete[i].style.display = "none";
	}
});
<%}%>
</script>
</head>
<body>
<div id="content">
	<form id="form1" action="index.jsp" method="post" enctype="multipart/form-data">
		<div style="display:<%=isReadOnly ? "none" : "block"%>">
			<span id="spanButtonPlaceHolder"></span>
			<input id="btnCancel" type="button" value="取消所有上传" onclick="swfu.cancelQueue();" disabled="disabled" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
			<!-- 
			<input type="button" id="button_cancel" value="返回" onClick="javascript:history.go(-1)" style="margin-left: 2px; font-size: 8pt; height: 29px;" />
			 -->
		</div>
		<table>
			<tr>
				<td style="display:<%=isReadOnly ? "none" : "block"%>">
					<div class="fieldset flash" id="fsUploadProgress">
						<span class="legend">上传队列</span>
					</div>
					<div id="divStatus">0 个附件被上传</div>
					<div id="divStatusFeedback"></div>
					<div id="divStatusFeedback2"></div>
				</td>
				<td>
					<div class="fieldset flash" id="uploadResult_outer">
						<span class="legend">附件列表</span>
						<div id="uploadResult">
<%@include file="/third/swfupload/i_uploadResult.jsp" %>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>
<%@page import="org.quickbundle.project.serializer.RmObjectMapper"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.quickbundle.tools.helper.io.RmFileHelper"%>
<%@page import="org.quickbundle.tools.helper.RmStringHelper"%>
<%@page import="java.io.File"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="org.springframework.jdbc.core.RowMapper"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.quickbundle.tools.helper.RmPopulateHelper"%>
<%@page import="org.quickbundle.modules.affix.rmaffix.vo.RmAffixVo"%>
<%@page import="org.quickbundle.project.IGlobalConstants"%>