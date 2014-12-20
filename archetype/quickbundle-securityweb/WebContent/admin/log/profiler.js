function setTimeRange(startMs) {
    var startDate = new Date();
    startDate.setTime(startMs);
    var now = new Date();
    form.date_from.value = formatDate(startDate);
    form.date_to.value = formatDate(now);
}
function validateDateOk() {
    if(form.date_from.value != "" && form.date_to.value != "" && form.date_to.value >= form.date_from.value) {
        return true;
    }
    alert("开始日期不得晚于结束日期！");
    form.date_from.focus();
    return false;
}
function formatDate(now) {
    var str = "";
    
    str += now.getFullYear();
    str += "-";
    
    if(String(now.getMonth()+1).length == 1) {
        str += "0";
    }
    str += String(now.getMonth()+1);
    str += "-";
    
    if(String(now.getDate()).length == 1) {
        str += "0";
    }
    str += String(now.getDate());
    str += " ";

    if(String(now.getHours()).length == 1) {
        str += "0";
    }
    str += String(now.getHours());
    str += ":";
    
    if(String(now.getMinutes()).length == 1) {
        str += "0";
    }
    str += String(now.getMinutes());
    str += ":";
    
    if(String(now.getSeconds()).length == 1) {
        str += "0";
    }
    str += String(now.getSeconds());
    return str;
}

function request_onclick(span, url) {
	$.ajax({
		type: "POST",
		url: "request_detail.py",
		data: {
			url: url,
			log: $("#log").val(),
			date_from: $("#date_from").val(),
			date_to: $("#date_to").val(),
			match_user: $("#match_user").val(),
			match_url: $("#match_url").val()
		}
	}).done(function( msg ) {
		$(span.parentNode).append("<div style='color:#000000'>" + msg + "</div>");
		$(span).text("-");
		$(span).unbind('click').removeAttr('onclick');
		$(span).css('cursor', 'auto');
	});
}