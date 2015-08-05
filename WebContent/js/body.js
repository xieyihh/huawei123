/**
 * 获取当前登录的用户信息
 */
function getCurrentUser() {
	$.ajax({
		url : "userAction!getCurrent.action",
		async : false,
		type : "POST",
		dataType : "json",
		success : function(user) {
			current_user = user;
		}
	});
}
/**
 * 全局Ajax配置
 */
$.ajaxSetup({
	contentType : "application/x-www-form-urlencoded;charset=utf-8",
	complete : function(XMLHttpRequest, textStatus) {
		if(XMLHttpRequest.getResponseHeader != undefined) {
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus，
			if (sessionstatus == "timeout") {
				// 如果超时就处理 ，指定要跳转的页面
				window.location.href = "login.jsp";
			}
		}
	},
	error : function() {
		hideLoading();
		showSystemMsg("无法连接服务器，请检查您的网络连接", 2);
	}
});
/**
 * 打开等待提示框
 * @param msg	提示消息
 */
function showLoading(msg) {
	if($(".loading_dialog").length == 0) {
		var div = 
			"<div class='loading_dialog modal fade' role='dialog' aria-hidden='true'>" +
				"<center><div class='modal-dialog'>" +
					"<div class='modal-content'>" +
						"<div class='modal-header'>" +
							"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>" +
							"<h4>提示信息</h4>" +
						"</div>" +
						"<div class='modal-body'>" +
							"<div class='loading_msg_div'></div>" +
						"</div>" +
					"</div>" +
				"</div></center>" + 
			"</div>";
		$("body").append(div);
	}
	$(".loading_dialog .modal-backdrop").remove();
	$(".loading_dialog .loading_msg_div").html(msg);
	$(".loading_dialog").modal("show");
}
/**
 * 关闭等待提示框
 */
function hideLoading() {
	$(".loading_dialog").modal("hide");
	$(".loading_dialog .modal-backdrop").remove();
}
/**
 * 弹出文本框的错误提示
 */
function inputPopover(input, msg, time) {
	if(time == undefined) {	//默认显示时间
		time = 1;
	}
	input.popover({
		content : msg,
		trigger : "manual",
		placement : "top"
	});
	input.popover("show");
	input.focus();
	setTimeout(function() {
		input.popover("destroy");
	}, time * 1000);
}
/**
 * 打开系统通知的对话框
 * @param msg	通知消息
 * @param time	关闭时间，如果未定义，默认为1秒
 */
function showSystemMsg(msg, time) {
	if($(".system_msg_dialog").length == 0) {
		var div = 
			"<div class='system_msg_dialog modal fade' role='dialog' aria-hidden='true'>" +
				"<center><div class='modal-dialog'>" +
					"<div class='modal-content'>" +
						"<div class='modal-header'>" +
							"<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>" +
							"<h4>系统通知</h4>" +
						"</div>" +
						"<div class='modal-body'>" +
							"<div class='system_msg_div'></div>" +
						"</div>" +
					"</div>" +
				"</center></div>" + 
			"</div>";
		$("body").append(div);
	}
	$(".system_msg_dialog .system_msg_div").html(msg);
	$(".system_msg_dialog").modal("show");
	if(time == undefined) {
		time = 1;
	}
	setTimeout(function() {
		$(".system_msg_dialog").modal("hide");
		$(".modal-backdrop").remove();
	}, time * 1000);
}
/**
 * 验证必填的属性是否合法
 */
function validateInputList(inputList) {
	for(var i=0; i<inputList.length; i++) {
		if(inputList.eq(i).val() == "") {
			var msg = inputList.eq(i).parent().find("span").html() + "不能为空";
			
			inputPopover(inputList.eq(i), msg, 2);
			return false;
		}
	}
	return true;
}
/**
 * 根据ID加载用户信息
 * @param id
 */
function loadUser(id) {
	var user = undefined;
	$.ajax({
		url : "userAction!findById.action",
		async : false,
		type : "POST",
		data : {
			userId : id
		},
		dataType : "json",
		success : function(data) {
			user = data;
		}
	});
	return user;
}
/**
 * 请求出错时显示错误信息
 */
function showError() {
	
}
/**
 * 解析时间，以便更好的显示
 * 如果是当年，返回MM-dd HH:mm；否则返回yyyy-MM-dd HH:mm
 */
function parseTime(time) {
	var current = new Date();
	if(current.getFullYear() == time.substring(0, 4)) {
		return time.substring(5, 16);
	} else {
		return time.substring(0, 16);
	}
}
/**
 * 解析日期，以便更好地显示
 * 如果是当年，返回MM-dd；否则返回yyyy-MM-dd
 * @param date
 * @returns
 */
function parseDate(date) {
	var current = new Date();
	if(current.getFullYear() == date.substring(0, 4)) {
		return date.substring(5, 10);
	} else {
		return date.substring(0, 10);
	}
}