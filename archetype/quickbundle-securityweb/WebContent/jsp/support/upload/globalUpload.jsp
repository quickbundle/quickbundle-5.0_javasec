<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ include file="/jsp/support/upload/i_getParameter.jsp" %>
<html>
<head>
<%@ include file="/jsp/include/rmGlobal.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>rm upload</title>

</head>
<body class="uploadPage">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr height="40%" id="tr_uploadContent">
    <td align="center" cellSpacing="1">
		<iframe name="uploadContent" src="upload4Global.jsp?uploadDir=<%=uploadDir%>" onLoad="window.setTimeout('uploadHandler.refreshInfo()',500);"  frameBorder="0" border="0" width="100%" height="150">
		</iframe>
	</td>
  </tr>
  <tr>
    <td align="center">
    	
		<table id="uploadListTable" class="table_div_content" >
			<tr bgcolor="#B8D5F5">
				<td align="center" width="20%">保存路径</td>
				<td align="center" width="10%">源文件名</td>
				<td align="center" width="10%">是否图片</td>
				<td align="center" width="20%">缩略图路径</td>
				<td align="center" width="10%">原图大小</td>
				<td align="center" width="10%">缩略图大小</td>
				<td align="center" width="10%">备注</td>
				<td align="center" width="10%">操作</td>
			</tr>
		</table>
    	
    </td>
  </tr>
  <tr>
    <td align="center">
    	<br>
		<span id="uploadInfoSpan" class="font_remain_prompt"></span>
    </td>
  </tr>
  <tr height="50%">
    <td align="center">
	    <input type="button" class="button_ellipse" name="button_ok" value="确定" onClick="ok_onClick();">&nbsp;&nbsp;&nbsp;&nbsp;
	    <input type="button" class="button_ellipse" name="button_cancel" value="取消" onClick="cancel_onClick();">
		<iframe name="deleteContent" src="upload4Delete.jsp?uploadDir=<%=uploadDir%>" width="0" height="0"></iframe>
    </td>
  </tr>
</table>
</body>
</html>

<script type="text/javascript">
	var config = {
		defaultUploadDialogDir : "<%=uploadDir%>",
		defaultUploadDialogFullDir : "<%=request.getContextPath() + "/" + uploadDir%>",
		minFileNumber : "<%=minFileNumber%>",
		maxFileNumber : "<%=maxFileNumber%>",
		openType : "<%=openType%>"
	}
	var rm_tempParentPageValue = null;
	var myObject = window.dialogArguments;
	var uploadHandler = new UploadHandler();
	if(myObject != null) {
		try {
			if(myObject.urlPath != null) {
				document.all['uploadContent'].src = myObject.urlPath;
			}
			if(myObject.oldData != null) {
				for(var i=0; i<myObject.oldData.length; i++) {
					uploadHandler.insertFileArray(myObject.oldData[i]);
				}
			}
		} catch(e) {
			//alert("a  1:" + e.message);
		}		
	}

	//控制权限
	if(config.openType == "READ") {
		document.getElementById("tr_uploadContent").style.display = "none";
		document.getElementById("uploadInfoSpan").style.display = "none";
	}

	function ok_onClick(){  //确定
		if(uploadHandler.getLength() < config.minFileNumber) {
			alert("请至少上传" + config.minFileNumber + "个附件！");
			return false;
		} else if(uploadHandler.getLength() > config.maxFileNumber) {
			alert("最多能上传" + config.maxFileNumber + "个附件，请删除多余的附件！");
			return false;
		} else {
	 	 	window.returnValue = uploadHandler.getValue();
			window.close();
		}
  	}

	function cancel_onClick(){  //取消
		if(uploadHandler.getLength() == 0 || config.openType == "READ" ) {
			window.close();
			return true;
		} else if( confirm('您已经上传了文件,但又试图直接关闭窗口,这样会在服务器上产生垃圾文件,确定这样作吗?')) {
			window.close();
			return true;
		} else {
			return false;
		}
	}

	function onUploadDone( errorNumber, myResultFile, customMsg ) {
		switch ( errorNumber )
		{
			case 0 :	// No errors
				//alert( 'Your file has been successfully uploaded' ) ;
				alert( '成功上传文件：\n' + myResultFile[1] ) ;
				break ;
			case 1 :	// Custom error
				alert( customMsg ) ;
				return ;
			case 101 :	// Custom warning
				alert( customMsg ) ;
				break ;
			case 201 :
				alert( 'A file with the same name is already available. The uploaded file has been renamed to "' + fileName + '"' ) ;
				break ;
			case 202 :
				alert( 'Invalid file type' ) ;
				return ;
			case 203 :
				alert( "Security error. You probably don't have enough permissions to upload. Please check your server." ) ;
				return ;
			default :
				alert( 'Error on file upload. Error number: ' + errorNumber ) ;
				return ;
		}
		uploadHandler.insertFileArray(myResultFile);
	}


	function UploadHandler() {
		this.table = document.getElementById("uploadListTable");
		this.currentIndex = 0;
		this.fileInfos = new Array();

		this.refreshInfo = function() {
			try {
				var currentNumber = this.getLength();
				var str = "您一共可以上传" + config.minFileNumber + "到" + config.maxFileNumber + "个文件";
				if(currentNumber >= config.maxFileNumber) {
					str += "，您不能再上传文件了!";
					uploadContent.document.getElementById("button_upload_id").disabled = true;
				} else {
					str += "，现在最多还能上传" + (config.maxFileNumber-currentNumber) + "个文件!"
					uploadContent.document.getElementById("button_upload_id").disabled = false;
				}
				document.getElementById("uploadInfoSpan").innerHTML = str;
			} catch(e) {
				//alert(e.message);
			}
		}

		this.getLength = function() {  //得到有效的文件个数
			var thisLength = 0;
			for(var i=0; i<this.fileInfos.length; i++) {
				if(this.fileInfos[i] != null) {
					thisLength ++;
				}
			}
			return thisLength;
		}
		
		this.getFileInfo = function(index) {  //得到某个文件对象
			return this.fileInfos[index];
		}

		this.insertTr = function (index) {  // 新增行
			// 使用insertRow方法新增一个Row，该方法返回值为新增的Row
			myNewRow = this.table.insertRow();
			myNewRow.bgColor = "#FFFFFF";
			myNewRow.id = "fileInfoTr_" + index;
	    	// 使用insertCell方法添加单元格，该方法返回值为新增的单元格
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).save_name;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).old_name;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).is_image;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).abbreviatory_name == null ? "": this.getFileInfo(index).abbreviatory_name;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).old_width_height == null ? "": this.getFileInfo(index).old_width_height;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).abbreviatory_width_height == null ? "": this.getFileInfo(index).abbreviatory_width_height;
			myNewRow.insertCell().innerHTML = this.getFileInfo(index).upload_remark;
			if(config.openType == "READ") {
				myNewRow.insertCell().innerHTML = "<span style='cursor:pointer' title='预览' onClick='uploadHandler.previewFile(" + index + ")'><img src='<%=request.getContextPath()%>/images/icon/preview.jpg' /></span>" + "&nbsp;&nbsp;<span style='cursor:pointer' title='下载' onClick='uploadHandler.downloadFile(" + index + ")'><img src='<%=request.getContextPath()%>/images/icon/download.gif' /></span>";
			} else {
				myNewRow.insertCell().innerHTML = "<span style='cursor:pointer' title='删除' onClick='uploadHandler.deleteFile(" + index + ")'><img src='<%=request.getContextPath()%>/images/icon/delete.gif' /></span>" + "&nbsp;&nbsp;<span style='cursor:pointer' title='预览' onClick='uploadHandler.previewFile(" + index + ")'><img src='<%=request.getContextPath()%>/images/icon/preview.jpg' /></span>";
			}
			this.refreshInfo();
		}

		this.insertFileObject = function(singleFileInfo) {  //插入文件
			singleFileInfo.index = this.currentIndex;
			this.fileInfos.push(singleFileInfo);
			this.insertTr(singleFileInfo.index);
			this.currentIndex ++;
			return singleFileInfo.index;
		}

		this.insertFileArray = function(myResultFile) {  //插入文件
			var singleFileInfo = new FileInfo(myResultFile);
			return this.insertFileObject(singleFileInfo);
		}

		this.insertFileList = function(save_name, old_name, old_name, abbreviatory_name) {  //插入文件
			var tempArray = new Array();
			tempArray.push(save_name);
			tempArray.push(old_name);
			tempArray.push(is_image);
			tempArray.push(abbreviatory_name);
			tempArray.push(old_width_height);
			tempArray.push(abbreviatory_width_height);
			tempArray.push(upload_remark);

			return this.insertFileArray(tempArray);
		}

		this.deleteFile = function(index) {  //删除文件
			if(!confirm("是否彻底删除该文件？")) {
				return false;
			}
			var thisFileInfo = this.getFileInfo(index);
			deleteContent.form.toDeleteFile.value = thisFileInfo.save_name;
			if(thisFileInfo.is_image == "2" && thisFileInfo.abbreviatory_name != null && thisFileInfo.abbreviatory_name != "") {
				deleteContent.form.toDeleteFile.value += "," + thisFileInfo.abbreviatory_name;
			}        
			deleteContent.form.submit();
			this.fileInfos[index] = null;
			var thisTr = document.getElementById("fileInfoTr_" + index);
			this.table.deleteRow( thisTr.rowIndex );  // 使用deleteRow方法删除当前行	
			this.refreshInfo();
		}

		this.previewFile = function(index) {  //预览文件
			var thisFileInfo = this.getFileInfo(index);
			var previewContent = "";
			if(thisFileInfo.is_image == "2" && thisFileInfo.abbreviatory_name != null && thisFileInfo.abbreviatory_name != "") {
				previewContent = "缩略图：<br><img src='" + config.defaultUploadDialogFullDir + "/" + thisFileInfo.abbreviatory_name + "' /><br><br>";
				previewContent += "原图：<br><img src='" + config.defaultUploadDialogFullDir + "/" + thisFileInfo.save_name + "' /><br>";
				var childWindow = window.open("uploadBlank.htm");
				rm_tempParentPageValue = previewContent;
			} else {
				//var childWindow = window.open("testBlank.htm");
				//childWindow.location = config.defaultUploadDialogFullDir + "/" + thisFileInfo.save_name;
				window.open(config.defaultUploadDialogFullDir + "/" + thisFileInfo.save_name);
			}
		}

		this.downloadFile = function(index) {  //预览文件
			var thisFileInfo = this.getFileInfo(index);
			var childWindow = window.open("<%=request.getContextPath()%>/jsp/support/upload/downloadFile.jsp?save_name_in_war=" + config.defaultUploadDialogDir + "/" + thisFileInfo.save_name + "&file_name=" + thisFileInfo.old_name);
		}

		this.getValue = function() {  //获得返回值
			var rtArray = new Array();
			for(var i=0; i<this.fileInfos.length; i++) {
				if(this.fileInfos[i] != null) {
					rtArray.push(this.fileInfos[i].clone().toArray());
				}
			}
			return rtArray;

		}

		this.toString = function() {
			var rtStr = "";
			for(var i=0; i<this.fileInfos.length; i++) {
				if(this.fileInfos[i] != null) {
					rtStr += this.fileInfos[i] + "";
					rtStr += "\n\n";
				}
			}
			return rtStr;
		}

	}

	function FileInfo(singleFileObj) {
		this.save_name = singleFileObj[0];
		this.old_name = singleFileObj[1];
		this.is_image = singleFileObj[2];
		this.abbreviatory_name = singleFileObj[3];
		this.old_width_height = singleFileObj[4];
		this.abbreviatory_width_height = singleFileObj[5];
		this.upload_remark = singleFileObj[6];
		this.index = -1;
		this.toString = function() {
			return "index等于" + this.index + "的对象:" + "\nsave_name=" + this.save_name + "\nold_name=" + this.old_name + "\nis_image=" + this.is_image + "\nabbreviatory_name=" + this.abbreviatory_name + "\nold_width_height=" + this.old_width_height + "\nabbreviatory_width_height=" + this.abbreviatory_width_height + "\nupload_remark=" + this.upload_remark;
		}

		this.toArray = function() {
			var rtArray = new Array();
			rtArray.push(this.save_name);
			rtArray.push(this.old_name);
			rtArray.push(this.is_image);
			rtArray.push(this.abbreviatory_name);
			rtArray.push(this.old_width_height);
			rtArray.push(this.abbreviatory_width_height);
			rtArray.push(this.upload_remark);
			return rtArray;
		}

		this.clone = function() {
			return new FileInfo(this.toArray());
		}

	}

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