current_user = undefined;	//当前登录的用户信息
current_associations = undefined;	//当前取到的用户所参加协会信息
current_associationAssociations = undefined;		//当前取到的用户所参加协会活动信息
current_activities = undefined;		//当前取到的大型活动信息
association_pageNum = 1;			//当前显示的用户所参加协会的页号
association_pageSize = 10;			//一页显示的用户所参加协会的数量
association_total = undefined;		//当前取到的用户所参加协会总数
associationActivity_pageNum = 1;		//当前显示的协会活动页号
associationActivity_pageSize = 10;		//一页显示的协会活动数量
associationActivity_total = undefined;	//当前取到的协会活动总数
activity_pageNum = 1;		//当前显示的大型活动页号
activity_pageSize = 10;		//一页显示的大型活动数量
activity_total = undefined;	//当前取到的大型活动总数

$(function() {
	initPanelHeading();
	getCurrentUser();
	load(current_user);
	loadAssociation();
	loadAssociationActivity();
	loadActivity();
});
/**
 * 初始化面板的标题，点击收起/展开
 */
function initPanelHeading() {
	$(".panel .panel-heading").click(function() {
		if($(this).parent().children(".panel-body").css("display") == "block") {
			$(this).parent().children(".panel-body, .panel-footer").hide(200);
			$(this).find(".glyphicon")
				.removeClass("glyphicon-chevron-up")
				.addClass("glyphicon-chevron-down");
		} else {
			$(this).parent().children(".panel-body, .panel-footer").show(200);
			$(this).find(".glyphicon")
				.removeClass("glyphicon-chevron-down")
				.addClass("glyphicon-chevron-up");
		}
	});
}
/**
 * 加载用户基本信息
 * @param user
 */
function load(user) {
	$("#user_info_panel .panel-body input[name='name']").val(user.name);
	$("#user_info_panel .panel-body input[name='account']").val(user.account);
	$("#user_info_panel .panel-body input[name='number']").val(user.number);
	$("#user_info_panel .panel-body input[name='phone']").val(user.phone);
	$("#user_info_panel .panel-body input[name='remark']").val(user.remark);
}
/**
 * 加载用户所参加的协会
 */
function loadAssociation() {
	$.ajax({
		url : "associationAction!find.action",
		type : "POST",
		data : {
			pageNum : association_pageNum,
			pageSize : association_pageSize,
			members : current_user.id
		},
		dataType : "json",
		success : function(data) {
			current_associations = data.list;
			association_total = data.total;
			$("#user_association_panel .panel-heading .panel-title .badge").html(association_total + "个协会");
			var association_list = $("#user_association_panel .panel-body #association_list");
			$(current_associations).each(function(index, association) {
				var item = 
					"<a href='associationDetail.jsp?id=" + association.id + "' class='list-group-item'>" +
						"<span class='badge'>" + association.size + "</span>" +
						association.name +
					"</a>";
				association_list.append(item);
			});
		}
	});
}
/**
 * 加载用户参加的协会活动
 */
function loadAssociationActivity() {
	$.ajax({
		url : "associationActivityAction!find.action",
		type : "POST",
		data : {
			pageNum : associationActivity_pageNum,
			pageSize : associationActivity_pageSize,
			attendees : current_user.id
		},
		dataType : "json",
		success : function(data) {
			current_associationAssociations = data.list;
			associationActivity_total = data.total;
			//显示协会活动总数
			$("#user_associationActivity_panel .panel-heading .panel-title .badge").html(current_associationAssociations.length + "项活动");
			var associationActivity_list = $("#user_associationActivity_panel .panel-body #associationActivity_list");
			for(var i=0; i<current_associationAssociations.length; i++) {
				var item = 
					"<a href='associationActivity.jsp?id=" + current_associationAssociations[i].id + "' class='list-group-item'>" +
						"<span class='badge'>" + current_associationAssociations[i].size + "人</span>" +
						"<span class='name'>" + current_associationAssociations[i].name + "<span>" + 
					"</a>";
				associationActivity_list.append(item);
			}
		}
	});
}
/**
 *	加载用户参加的大型活动
 */
function loadActivity() {
	$.ajax({
		url : "activityAction!find.action",
		type : "POST",
		data : {
			pageNum : activity_pageNum,
			pageSize : activity_pageSize,
			attendees : current_user.id
		},
		dataType : "json",
		success : function(data) {
			current_activities = data.list;
			activity_total = data.total;
			//显示协会活动总数
			$("#user_activity_panel .panel-heading .panel-title .badge").html(current_activities.length + "项活动");
			var activity_list = $("#user_activity_panel .panel-body #activity_list");
			for(var i=0; i<current_activities.length; i++) {
				var item = 
					"<a href='activity.jsp?id=" + current_activities[i].id + "' class='list-group-item'>" +
						"<span class='badge'>" + current_activities[i].size + "人</span>" +
						"<span class='name'>" + current_activities[i].name + "<span>" + 
					"</a>";
				activity_list.append(item);
			}
		}
	});
}
/**
 * 保存修改后的用户信息
 */
function save() {
	var inputList = $("#user_info_panel .panel-body input[required='required']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	if(validateResult)
	$.ajax({
		url : "userAction!update.action",
		type : "POST",
		data : {
			userName : $("#user_info_panel .panel-body input[name='name']").val(),
			userAccount : $("#user_info_panel .panel-body input[name='account']").val(),
			userNumber : $("#user_info_panel .panel-body input[name='number']").val(),
			userPhone : $("#user_info_panel .panel-body input[name='phone']").val(),
			userRemark : $("#user_info_panel .panel-body input[name='remark']").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				showSystemMsg(data.msg, 1);
			} else {
				if(data.msg.indexOf("存在") >=0 ) {
					inputPopover($("#user_info_panel .panel-body input[name='account']"), data.msg, 1);
				} else {
					showSystemMsg(data.msg, 2);
				}
			}
		}
	});
}
/**
 * 修改密码
 */
function updatePassword() {
	var inputList = $("#update_password_dialog .modal-body input[required='required']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	var oldPassword = $("#update_password_dialog .modal-body input[name='oldPassword']");
	var newPassword = $("#update_password_dialog .modal-body input[name='newPassword']");
	var confirmPassword = $("#update_password_dialog .modal-body input[name='confirmPassword']");
	if(newPassword.val() != confirmPassword.val()) {
		inputPopover(confirmPassword, "两次输入的密码不一致", 2);
		return false;
	}
	$.ajax({
		url : "userAction!updatePassword.action",
		type : "POST",
		data : {
			oldPassword : oldPassword.val(),
			newPassword : newPassword.val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#update_password_dialog .modal-body input").val("");
				$("#update_password_dialog").modal("hide");
				showSystemMsg(data.msg, 1);
			} else {
				if(data.msg == "旧密码有误") {
					inputPopover(oldPassword, data.msg, 2);
				} else {
					showSystemMsg(data.msg, 2);
				}
			}
		}
	});
}