current_association = undefined;		//当前的协会对象
current_user = undefined;		//当前用户
current_members = undefined;	//当前协会的成员
current_administrators = undefined;	//当前协会的管理员
current_activities = undefined;		//当前取到的协会活动信息
associationActivity_pageNum = 1;		//当前显示的协会活动页号
associationActivity_pageSize = 10;		//一页显示的协会活动数量
associationActivity_total = undefined;	//当前取到的协会活动总数
/**
 * 加载协会信息
 * @param id
 */
function load(id) {
	getCurrentUser();
	showLoading("正在加载···");
	$.ajax({
		url : "associationAction!viewDetail.action",
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(association) {
			hideLoading();
			if(association == null) {
				showSystemMsg("对应的协会不存在，可能已被删除", 2);
				return false;
			}
			current_association = association;
			document.title = "Mini~协会~" + association.name;
			$(".association_name_span").html(association.name);
			$("#association_detail_panel .panel-body input[name='name']").val(association.name);
			$("#association_detail_panel .panel-body textarea[name='description']").val(association.description);
			$("#association_detail_panel .panel-body input[name='date']").val(association.date.substring(0, 10));
			$("#association_detail_panel .panel-body input[name='founder']").val(association.founder);
			$("#association_detail_panel .panel-body input[name='size']").val(association.size);
			initButtons();
			loadAdministrators();
			loadActivities();
		}
	});
}
/**
 * 判断批注成立、加入、退出、发起活动等按钮是否显示，给相关按钮绑定事件
 */
function initButtons() {
	//是否显示”批准成立“按钮
	if(current_association.state == 0) {
		if(current_user.level == 2 || 
				(current_user.level == 1 && current_user.authority.indexOf("association") >= 0)) {
			$("#association_detail_panel .panel-footer #authorize_button").show();
		}
		return;
	}
	//是否显示删除协会的按钮
	if(current_association.state == 1) {
		if(current_user.level == 2 || 
				(current_user.level == 1 && current_user.authority.indexOf("association") >= 0)) {
			$("#association_detail_panel .panel-footer #delete_button").show();
		}
	}
	//显示”加入“或者”退出“按钮
	if(current_association.members.indexOf(current_user.id + "&&") >= 0) {
		$("#association_detail_panel .panel-footer #quit").show();
	} else {
		$("#association_detail_panel .panel-footer #join").show();
	}
	//是否显示“发起活动”按钮
	if(current_association.administrators.indexOf(current_user.id + "&&") >= 0) {
		$("#association_activity_panel .panel-footer #initiateActivity").show();
	}
	//给协会成员的按钮绑定点击事件，点击按钮查看用户详细信息
	$("#association_member_panel .panel-body").delegate("button", "click", function() {
		var user = loadUser($(this).attr("memberId"));
		var inputList = $("#association_member_detail_dialog .modal-body form input");
		$.each(inputList, function(index) {
			var input = inputList.eq(index);
			input.val(user[input.attr("name")]);
		});
		//如果当前登录的用户是协会创始人，并且当前查看的用户不是自己，判断是否显示“设为管理员”或者“取消管理权”按钮
		if((current_association.founderId == current_user.id) && (user.id != current_user.id)) {
			if(current_association.administrators.indexOf(user.id) >= 0) {
				$("#association_member_detail_dialog .modal-footer #setAdmin").hide();
				$("#association_member_detail_dialog .modal-footer #cancelAdmin").show();
			} else {
				$("#association_member_detail_dialog .modal-footer #cancelAdmin").hide();
				$("#association_member_detail_dialog .modal-footer #setAdmin").show();
			}
		} else {
			$("#association_member_detail_dialog .modal-footer  #setAdmin, " +
					"#association_member_detail_dialog .modal-footer #setAdmin").hide();
		}
		$("#association_member_detail_dialog").modal("show");
	});
}
/**
 * 加载管理员姓名
 */
function loadAdministrators() {
	$.ajax({
		url : "associationAction!getAdministrators.action",
		type : "POST",
		data : {
			administrators : current_association.administrators
		},
		dataType : "json",
		success : function(map) {
			current_administrators = map;
			var panel_body = $("#association_member_panel .panel-body");
			panel_body.children().remove();
			$.each(current_administrators, function(key, value) {
				var button = "<button class='btn btn-primary btn-xs btn_admin' memberId='" + key + "'>" + value + "</button>";
				panel_body.append(button);
			});
			//协会管理员加载成功后加载成员
			loadMembers();
		}
	});
}
/**
 * 加载协会成员姓名
 */
function loadMembers() {
	$.ajax({
		url : "associationAction!getMembers.action",
		type : "POST",
		data : {
			members : current_association.members
		},
		dataType : "json",
		success : function(map) {
			current_members = map;
			//显示协会成员总数
			$("#association_member_panel .panel-heading .panel-title .badge").html(current_association.size + "人");
			var panel_body = $("#association_member_panel .panel-body");
			$.each(current_members, function(key, value) {
				//如果是管理员就不再重复显示
				if(current_administrators[key] == undefined) {
					var button = "<button class='btn btn-success btn-xs' memberId='" + key + "'>" + value + "</button>";
					panel_body.append(button);
				}
			});
		}
	});
}
/**
 * 加载协会活动信息
 */
function loadActivities() {
	$.ajax({
		url : "associationActivityAction!find.action",
		type : "POST",
		data : {
			pageNum : associationActivity_pageNum,
			pageSize : associationActivity_pageSize,
			associationId : current_association.id
		},
		dataType : "json",
		success : function(data) {
			var associationActivity_list = $("#association_activity_panel .panel-body #associationActivity_list");
			if(associationActivity_pageNum == 1) {
				associationActivity_list.children().remove();
				current_activities = data.list;
			} else {
				current_activities = current_activities.concat(data.list);
			}
			associationActivity_total = data.total;
			//判断是否显示“加载更多”按钮
			if(associationActivity_pageNum * associationActivity_pageSize >= associationActivity_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			//显示协会活动总数
			$("#association_activity_panel .panel-heading .panel-title .badge").html(associationActivity_total + "项活动");
			for(var i=0; i<data.list.length; i++) {
				var item = 
					"<a href='associationActivity.jsp?id=" + data.list[i].id + "' class='list-group-item'>" +
						"<span class='badge'>" + data.list[i].size + "人</span>" +
						"<span class='name'>" + data.list[i].name + "<span>" + 
						"<div class='right'>" + 
							"报名截止：" + data.list[i].applyDeadline.substring(0, 10) + 
						"</div>" + 
					"</a>";
				associationActivity_list.append(item);
			}
		}
	});
}
/**
 * 刷新协会活动
 */
function refreshActivities() {
	associationActivity_pageNum = 1;
	loadActivities();
}
/**
 * 批准成立
 */
function authorize() {
	if(!confirm("确认批准" + current_association.name + "成立？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!authorize.action",
		type : "POST",
		data : {
			id : current_association.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#association_detail_panel .panel-footer #authorize_button").hide();
				$("#association_detail_panel .panel-footer #delete_button").show();
				$("#association_detail_panel .panel-footer #join").show();
			}
			showSystemMsg(data.msg);
		}
	});
}
/**
 * 删除协会
 */
function deleteAssociation() {
	if(!confirm("确定要删除" + current_association.name + "？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!delete.action",
		type : "POST",
		data : {
			id : current_association.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				window.location.href = "association.jsp";
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 加入协会
 */
function join() {
	if(!confirm("确定要加入" + current_association.name + "？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!join.action",
		type : "POST",
		data : {
			id : current_association.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#association_detail_panel .panel-body input[name='size']").val(++current_association.size);
				$("#association_member_panel .panel-heading .panel-title .badge").html(current_association.size + "人");
				$("#association_detail_panel .panel-footer #join").hide();
				$("#association_detail_panel .panel-footer #quit").show();
				//在成员列表中加上当前用户的姓名
				var panel_body = $("#association_member_panel .panel-body");
				var button = "<button class='btn btn-success btn-xs' memberId='" + current_user.id + "'>" + current_user.name + "</button>";
				panel_body.append(button);
			}
			showSystemMsg(data.msg);
		}
	});
}
/**
 * 退出协会
 */
function quit() {
	if(!confirm("确定要退出" + current_association.name + "？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!quit.action",
		type : "POST",
		data : {
			id : current_association.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#association_detail_panel .panel-body input[name='size']").val(--current_association.size);
				$("#association_member_panel .panel-heading .panel-title .badge").html(current_association.size + "人");
				$("#association_detail_panel .panel-footer #quit").hide();
				$("#association_detail_panel .panel-footer #join").show();
				//在成员列表中去掉当前用户的姓名
				$("#association_member_panel .panel-body button[memberId='" + current_user.id + "']").remove();
			}
			showSystemMsg(data.msg);
		}
	});
}
/**
 * 设置一个成员作为协会管理员
 */
function setAdmin() {
	var memberId = $("#association_member_detail_dialog .modal-body form input[name='id']").val();
	var memberName = $("#association_member_detail_dialog .modal-body form input[name='name']").val();
	if(!confirm("确认要将" + memberName + "设为协会的管理员？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!setAdmin.action",
		dataType : "POST",
		data : {
			id : current_association.id,
			memberId : memberId
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				current_association.administrators = data.administrators;
				var button = "<button class='btn btn-primary btn-xs btn_admin' memberId='" + memberId + "'>" + memberName + "</button>";
				$("#association_member_panel .panel-body button[memberId='" + memberId + "']").remove();
				$("#association_member_panel .panel-body .btn_admin:last").after(button);
				$("#association_member_detail_dialog").modal("hide");
			}
			showSystemMsg(data.msg);
		}
	});
}
/**
 * 收回一个成员的管理权限
 */
function cancelAdmin() {
	var memberId = $("#association_member_detail_dialog .modal-body form input[name='id']").val();
	var memberName = $("#association_member_detail_dialog .modal-body form input[name='name']").val();
	if(!confirm("确认要取消" + memberName + "的协会管理员身份？")) {
		return false;
	}
	$.ajax({
		url : "associationAction!cancelAdmin.action",
		type : "POST",
		data : {
			id : current_association.id,
			memberId : memberId
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				current_association.administrators = data.administrators;
				var button = "<button class='btn btn-success btn-xs' memberId='" + memberId + "'>" + memberName + "</button>";
				$("#association_member_panel .panel-body button[memberId='" + memberId + "']").remove();
				$("#association_member_panel .panel-body").append(button);
				$("#association_member_detail_dialog").modal("hide");
			}
			showSystemMsg(data.msg);
		}
	});
}
/**
 * 发起活动
 */
function initiateActivity() {
	var validateResult = validateActivity();
	if(!validateResult) {
		return false;
	}
	var name = $("#initiate_associationActivity_dialog .modal-body input[name='name']");
	var description = $("#initiate_associationActivity_dialog .modal-body textarea[name='description']");
	var applyDeadline = $("#initiate_associationActivity_dialog .modal-body input[name='applyDeadline']");
	var startDate = $("#initiate_associationActivity_dialog .modal-body input[name='startDate']");
	var endDate = $("#initiate_associationActivity_dialog .modal-body input[name='endDate']");
	$.ajax({
		url : "associationActivityAction!add.action",
		type : "POST",
		data : {
			name : name.val(),
			description : description.val(),
			applyDeadline : applyDeadline.val(),
			startDate : startDate.val(),
			endDate : endDate.val(),
			associationId : current_association.id,
			association : current_association.name
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#initiate_associationActivity_dialog .modal-body input, " +
						"#initiate_associationActivity_dialog .modal-body textarea").val("");
				//刷新协会活动列表
				refreshActivities();
				$("#initiate_associationActivity_dialog").modal("hide");
			}
			showSystemMsg(data.msg, 2);
		},
		error : function() {
			showSystemMsg("无法连接服务器，请检查您的网络连接", 2);
		}
	});
}
/**
 * 验证新发起的活动属性是否合法
 */
function validateActivity() {
	var name = $("#initiate_associationActivity_dialog .modal-body input[name='name']");
	var description = $("#initiate_associationActivity_dialog .modal-body textarea[name='description']");
	var applyDeadline = $("#initiate_associationActivity_dialog .modal-body input[name='applyDeadline']");
	var startDate = $("#initiate_associationActivity_dialog .modal-body input[name='startDate']");
	var endDate = $("#initiate_associationActivity_dialog .modal-body input[name='endDate']");
	if(name.val() == "") {
		inputPopover(name, "活动名称不能为空", 2);
		return false;
	}
	if(description.val() == "") {
		inputPopover(description, "活动名称不能为空", 2);
		return false;
	}
	if(applyDeadline.val() == "") {
		inputPopover(applyDeadline, "报名截止日期不能为空", 2);
		return false;
	}
	if(startDate.val() == "") {
		inputPopover(startDate, "开始日期不能为空", 2);
		return false;
	}
	if(endDate.val() == "") {
		inputPopover(endDate, "结束日期不能为空", 2);
		return false;
	}
	if(applyDeadline.val() > startDate.val()) {
		inputPopover(applyDeadline, "报名截止日期不能超过开始日期", 2);
		return false;
	}
	if(startDate.val() > endDate.val()) {
		inputPopover(startDate, "开始日期不能超过结束日期", 2);
		return false;
	}
	return true;
}