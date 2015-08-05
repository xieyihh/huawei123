current_user = undefined;		//当前登录的用户信息
current_activity = undefined;		//当前取到的协会活动信息
/**
 * 加载协会活动信息
 * @param id
 */
function load(id) {
	getCurrentUser(current_user);
	showLoading("正在加载···");
	$.ajax({
		url : "activityAction!viewDetail.action",
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			current_activity = data;
			$(".activity_name_span").html(current_activity.name);
			$("#activity_detail_panel .panel-body input[name='name']").val(current_activity.name);
			$("#activity_detail_panel .panel-body textarea[name='description']").val(current_activity.description);
			$("#activity_detail_panel .panel-body input[name='initiator']").val(current_activity.initiator);
			$("#activity_detail_panel .panel-body input[name='applyDeadline']").val(current_activity.applyDeadline.substring(0, 10));
			$("#activity_detail_panel .panel-body input[name='startDate']").val(current_activity.startDate.substring(0, 10));
			$("#activity_detail_panel .panel-body input[name='endDate']").val(current_activity.endDate.substring(0, 10));
			$("#activity_detail_panel .panel-body input[name='max']").val(current_activity.max);
			$("#activity_detail_panel .panel-body input[name='size']").val(current_activity.size);
			//判断“报名”等按钮是否显示
			initButtons();
			//加载协会活动参加的人员姓名
			loadAttendees();
			//根据协会自定义属性产生填写属性信息的表单
			initPropertyDialog();
			//根据协会自定义属性产生显示活动参加者属性信息的表单
			initAttendeeDetailDialog();
		}
	});
}
/**
 * 判断页面相关按钮是否显示、添加事件
 */
function initButtons() {
	//点击“报名”时，如果有自定义属性则打开填写属性信息的对话框，否则调用apply()
	$("#activity_detail_panel .panel-footer #apply").click(function() {
		if(current_activity.property == "") {
			apply();
		} else {
			$("#activity_property_dialog").modal("show");
		}
	});
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
	if(date <= current_activity.applyDeadline.substring(0, 10)) {
		//如果已报名显示退出按钮，否则显示报名按钮
		if(current_activity.attendees.indexOf(current_user.id + "&&") < 0) {
			$("#activity_detail_panel .panel-footer #apply").show();
		} else {
			$("#activity_detail_panel .panel-footer #quit").show();
		}
	}
	//如果报名人数达到上限，隐藏报名按钮
	if(current_activity.size >= current_activity.max) {
		$("#activity_detail_panel .panel-footer #apply").hide();
	}
	//给活动参加人员的按钮绑定点击事件，点击按钮查看用户详细信息
	$("#activity_attendee_panel .panel-body").delegate("button", "click", function() {
		$("#activity_attendee_detail_dialog .modal-body form input").val("");
		var user = loadUser($(this).attr("attendeeId"));
		var inputList = $("#activity_attendee_detail_dialog .modal-body form input");
		//显示用户基本信息
		$.each(inputList, function(index) {
			var input = inputList.eq(index);
			input.val(user[input.attr("name")]);
		});
		//显示活动自定义属性值，从 3~180cm;125斤;&&10~175cm;130斤;&& 中提取180cm;125斤;
		var string = $(this).attr("attendeeId") + "~";
		var start = current_activity.attendeesProperty.indexOf(string) + string.length;
		var end = current_activity.attendeesProperty.indexOf("&&", start);
		string = current_activity.attendeesProperty.substring(start, end);
		if(string.charAt(string.length - 1) == ";") {
			string = string.substring(0, string.length - 1);
		}
		var values = string.split(";");
		inputList = $("#activity_attendee_detail_dialog .modal-body form .property_input");
		inputList.val("");
		$.each(inputList, function(index) {
			var input = inputList.eq(index);
			input.val(values[index]);
		});
		//如果查看的是自己并且有自定义属性，将自定义的文本框设为可编辑状态，并显示“提交”按钮；否则隐藏，并将文本框设为只读状态
		if($(this).attr("attendeeId") == current_user.id && current_activity.property != "") {
			$("#activity_attendee_detail_dialog .modal-body form .property_input:gt(0)").removeAttr("readonly");
			$("#activity_attendee_detail_dialog .modal-footer #update").show();
		} else {
			$("#activity_attendee_detail_dialog .modal-body form .property_input:gt(0)").attr("readonly", "readonly");
			$("#activity_attendee_detail_dialog .modal-footer #update").hide();
		}
		$("#activity_attendee_detail_dialog").modal("show");
	});
}
/**
 * 初始化用户报名时填写自定义属性的对话框
 */
function initPropertyDialog() {
	//去除最后一个&
	if(current_activity.property.charAt(current_activity.property.length - 1) == "&") {
		current_activity.property = current_activity.property.substring(0, current_activity.property.length - 1);
	}
	if(current_activity.property == "") {
		return false;
	}
	var properties = current_activity.property.split("&");
	var form = $("#activity_property_dialog .modal-body form");
	$.each(properties, function(index, property) {
		var div = 
			"<div class='input-group'>" +
				"<span class='input-group-addon'>" + property + "</span>" +
				"<input type='text' class='form-control' required='required' placeholder='请输入" + property + "'>" +
			"</div>";
		form.append(div);
	});
}
/**
 * 初始化显示活动参加者详细信息的对话框
 */
function initAttendeeDetailDialog() {
	if(current_activity.property == "") {
		return false;
	}
	var properties = current_activity.property.split("&");
	var form = $("#activity_attendee_detail_dialog .modal-body form");
	$.each(properties, function(index, property) {
		var div = 
			"<div class='input-group'>" +
				"<span class='input-group-addon'>" + property + "</span>" +
				"<input type='text' class='form-control property_input' readonly='readonly'>" +
			"</div>";
		form.append(div);
	});
}
/**
 * 加载活动参加人员信息
 */
function loadAttendees() {
	$.ajax({
		url : "activityAction!getAttendees.action",
		type : "POST",
		data : {
			attendees : current_activity.attendees
		},
		dataType : "json",
		success : function(map) {
			$("#activity_attendee_panel .panel-heading .panel-title .badge").html(current_activity.size + "人");
			var panel_body = $("#activity_attendee_panel .panel-body");
			panel_body.children().remove();
			var button;
			if(map[current_activity.initiatorId] != undefined) {
				button = "<button class='btn btn-primary btn-xs' attendeeId='" 
									+ current_activity.initiatorId + "'>" 
									+ current_activity.initiator + "</button>";
				panel_body.append(button);
			}
			$.each(map, function(key, value) {
				if(key != current_activity.initiatorId) {
					button = "<button class='btn btn-success btn-xs' attendeeId='" + key + "'>" + value + "</button>";
					panel_body.append(button);
				}
			});
		}
	});
}
/**
 * 大型活动报名
 */
function apply() {
	if(!confirm("确认参加" + current_activity.name + "？")) {
		return false;
	}
	//拼接属性值，格式为：1~178CM;63KG&&，其中178CM和63KG分别对应“身高”和“体重”
	var time = new Date();
	var userProperty = current_user.id + "~" + time.getFullYear() + "年" + (time.getMonth() + 1) + "月" + time.getDate() + "日;";
	var inputList = $("#activity_property_dialog .modal-body form input");
	$.each(inputList, function(index) {
		userProperty += inputList.eq(index).val() + ";";
	});
	userProperty += "&&";
	//提交
	$.ajax({
		url : "activityAction!apply.action",
		type : "POST",
		data : {
			id : current_activity.id,
			userProperty : userProperty
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#activity_detail_panel .panel-body input[name='size']").val(++current_activity.size);
				$("#activity_attendee_panel .panel-heading .panel-title .badge").html(current_activity.size + "人");
				$("#activity_property_dialog .modal-body form input").val("");
				var button = "<button class='btn btn-success btn-xs' attendeeId='" 
									+ current_user.id + "'>" 
									+ current_user.name + "</button>";
				$("#activity_attendee_panel .panel-body").append(button);
				$("#activity_detail_panel .panel-footer #apply").hide();
				$("#activity_detail_panel .panel-footer #quit").show();
				$("#activity_property_dialog").modal("hide");
			}
			showSystemMsg(data.msg, 2);
		}
	});
}
/**
 * 推出大型活动
 */
function quit() {
	if(!confirm("确认退出" + current_activity.name + "？")) {
		return false;
	}
	$.ajax({
		url : "activityAction!quit.action",
		type : "POST",
		data : {
			id : current_activity.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#activity_detail_panel .panel-body input[name='size']").val(--current_activity.size);
				$("#activity_attendee_panel .panel-heading .panel-title .badge").html(current_activity.size + "人");
				$("#activity_attendee_panel .panel-body button[attendeeId='" + current_user.id + "']").remove();
				$("#activity_detail_panel .panel-footer #quit").hide();
				$("#activity_detail_panel .panel-footer #apply").show();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 修改报名信息
 */
function updateApplyInfo() {
	var inputList = $("#activity_attendee_detail_dialog .modal-body form .property_input");
	var userProperty = current_user.id + "~";
	$.each(inputList, function(index) {
		userProperty += inputList.eq(index).val() + ";";
	});
	userProperty += "&&";
	$.ajax({
		url : "activityAction!updateApplyInfo.action",
		type : "POST",
		data : {
			id : current_activity.id,
			userProperty : userProperty
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#activity_attendee_detail_dialog").modal("hide");
				current_activity.attendeesProperty = data.attendeesProperty;
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}