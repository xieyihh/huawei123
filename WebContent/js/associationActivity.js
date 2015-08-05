current_user = undefined;		//当前登录的用户信息
current_associationActivity = undefined;		//当前取到的协会活动信息
/**
 * 加载协会活动信息
 * @param id
 */
function load(id) {
	getCurrentUser();
	showLoading("正在加载···");
	$.ajax({
		url : "associationActivityAction!viewDetail.action",
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			current_associationActivity = data;
			$(".associationActivity_name_span").html(current_associationActivity.name);
			$("#associationActivity_detail_panel .panel-body input[name='name']").val(current_associationActivity.name);
			$("#associationActivity_detail_panel .panel-body textarea[name='description']").val(current_associationActivity.description);
			$("#associationActivity_detail_panel .panel-body input[name='initiator']").val(current_associationActivity.initiator);
			$("#associationActivity_detail_panel .panel-body input[name='applyDeadline']").val(current_associationActivity.applyDeadline.substring(0, 10));
			$("#associationActivity_detail_panel .panel-body input[name='startDate']").val(current_associationActivity.startDate.substring(0, 10));
			$("#associationActivity_detail_panel .panel-body input[name='endDate']").val(current_associationActivity.endDate.substring(0, 10));
			$("#associationActivity_detail_panel .panel-body input[name='association']").val(current_associationActivity.association);
			$("#associationActivity_detail_panel .panel-body input[name='size']").val(current_associationActivity.size);
			//判断“报名”等按钮是否显示
			initButtons();
			//加载协会活动参加的人员姓名
			loadAttendees();
		}
	});
}
/**
 * 判断“报名”等按钮是否显示，给相关按钮绑定事件
 */
function initButtons() {
	//获取当前日期~yyyy-MM-dd格式
	var time = new Date();
	var month = time.getMonth() + 1;
	if(month < 10) {
		month = "0" + month;
	}
	var dateOfMonth = time.getDate();
	if(dateOfMonth < 10) {
		dateOfMonth = "0" + dateOfMonth;
	}
	var date = time.getFullYear() + "-" + month + "-" + dateOfMonth;
	//在报名截止日期前可以显示报名/退出按钮
	if(date <= current_associationActivity.applyDeadline) {
		//如果已报名显示退出按钮，否则显示报名按钮
		if(current_associationActivity.attendees.indexOf(current_user.id + "&&") < 0) {
			$("#associationActivity_detail_panel .panel-heading .right #apply").show();
		} else {
			$("#associationActivity_detail_panel .panel-heading .right #quit").show();
		}
	}
	//给活动参加人员的按钮绑定点击事件，点击按钮查看用户详细信息
	$("#associationActivity_attendee_panel .panel-body").delegate("button", "click", function() {
		var user = loadUser($(this).attr("attendeeId"));
		var inputList = $("#associationActivity_attendee_detail_dialog .modal-body form input");
		$.each(inputList, function(index) {
			var input = inputList.eq(index);
			input.val(user[input.attr("name")]);
		});
		$("#associationActivity_attendee_detail_dialog").modal("show");
	});
}
/**
 * 加载协会活动参加的人员姓名
 */
function loadAttendees() {
	$.ajax({
		url : "associationActivityAction!getAttendees.action",
		type : "POST",
		data : {
			attendees : current_associationActivity.attendees
		},
		dataType : "json",
		success : function(map) {
			$("#associationActivity_attendee_panel .panel-heading .panel-title .badge").html(current_associationActivity.size + "人");
			var panel_body = $("#associationActivity_attendee_panel .panel-body");
			panel_body.children().remove();
			var button = "<button class='btn btn-primary btn-xs' attendeeId='" 
										+ current_associationActivity.initiatorId + "'>" 
										+ current_associationActivity.initiator + "</button>";
			panel_body.append(button);
			$.each(map, function(key, value) {
				if(key != current_associationActivity.initiatorId) {
					button = "<button class='btn btn-success btn-xs' attendeeId='" + key + "'>" + value + "</button>";
					panel_body.append(button);
				}
			});
		}
	});
}
/**
 * 协会活动报名
 */
function apply() {
	if(!confirm("确认参加" + current_associationActivity.name + "？")) {
		return false;
	}
	$.ajax({
		url : "associationActivityAction!apply.action",
		type : "POST",
		data : {
			id : current_associationActivity.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#associationActivity_detail_panel .panel-body input[name='size']").val(++current_associationActivity.size);
				$("#associationActivity_attendee_panel .panel-heading .panel-title .badge").html(current_associationActivity.size + "人");
				var button = "<button class='btn btn-success btn-xs' attendeeId='" + current_user.id + "'>" + current_user.name + "</button>";
				$("#associationActivity_attendee_panel .panel-body").append(button);
				$("#associationActivity_detail_panel .panel-heading .right #apply").hide();
				$("#associationActivity_detail_panel .panel-heading .right #quit").show();
			}
			showSystemMsg(data.msg, 2);
		}
	});
}
/**
 * 退出协会活动
 */
function quit() {
	if(!confirm("确认退出" + current_associationActivity.name + "？")) {
		return false;
	}
	$.ajax({
		url : "associationActivityAction!quit.action",
		type : "POST",
		data : {
			id : current_associationActivity.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#associationActivity_detail_panel .panel-body input[name='size']").val(--current_associationActivity.size);
				$("#associationActivity_attendee_panel .panel-heading .panel-title .badge").html(current_associationActivity.size + "人");
				$("#associationActivity_attendee_panel .panel-body button[attendeeId='" + current_user.id + "']").remove();
				$("#associationActivity_detail_panel .panel-heading .right #quit").hide();
				$("#associationActivity_detail_panel .panel-heading .right #apply").show();
			}
			showSystemMsg(data.msg, 2);
		}
	});
}