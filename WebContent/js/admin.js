current_user = undefined;	//当前登录的用户信息
user_pageNum = 1;				//当前显示的用户信息页号（用户管理）
user_pageSize = 10;			//一页显示的用户数量（用户管理）
user_total = undefined;		//当前取到的用户总数（用户管理）
current_users = undefined;		//当前取到的用户信息
admin_user_pageNum = 1;				//当前显示的用户信息页号（添加管理员的对话框）
admin_user_pageSize = 10;			//一页显示的用户数量（添加管理员的对话框）
admin_user_total = undefined;		//当前取到的用户总数（添加管理员的对话框）
moduleName = {					//模块名称
		"association" : "协会",
		"activity" : "大型活动",
		"bus" : "班车信息",
		"discount" : "打折信息"
};
feedback_pageNum = 1;		//当前显示的反馈信息页号
feedback_pageSize = 10;		//一页显示的反馈信息数量
feedback_total = undefined;	//当前取到的反馈信息总数
current_feedbacks = undefined;	//当前取到的反馈信息

$(function() {
	initPanelHeading();
	getCurrentUser();
	initAdministratorPanel();
	searchUser();
	searchFeedback();
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
 * 加载用户信息
 */
function searchUser() {
	var data = {
			pageNum : user_pageNum,
			pageSize : user_pageSize,
			limit : true
	};
	var keyword = $("#user_panel .panel-body .input-group input[name='keyword']").val();
	//如果输入的是数字，根据工号查询，否则根据姓名查询
	if(/^\d+$/.test(keyword)) {
		data.userNumber = keyword;
	} else {
		data.userName = keyword;
	}
	$.ajax({
		url : "userAction!find.action",
		type : "POST",
		data : data,
		dataType : "json",
		success : function(data) {
			//判断是否显示“加载更多”按钮
			var user_list = $("#user_panel .panel-body #user_list");
			if(user_pageNum == 1) {
				user_list.children().remove();
				current_users = data.list;
			} else {
				current_users = current_users.concat(data.list);
			}
			//判断是否显示“加载更多”按钮
			user_total = data.total;
			if(user_total == 0) {
				$("#user_panel .panel-body .msg_div").show();
			} else {
				$("#user_panel .panel-body .msg_div").hide();
			}
			if(user_pageNum * user_pageSize >= user_total) {
				$("#user_panel .panel-body #load_more_button").hide();
			} else {
				$("#user_panel .panel-body #load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var index = (user_pageNum - 1) * user_pageSize + i;
				var item = 
					"<a href='javascript:void(0)' class='list-group-item' onclick=viewUserDetail('" + index + "') index='" + index + "'>" +
						"<h4 class='list-group-item-heading'>" + current_users[index].name + "  " + current_users[index].number + "</h4>" + 
					"</a>";
				user_list.append(item);
			}
		}
	});
}
/**
 * 初始化显示模块管理员信息的面板
 */
function initAdministratorPanel() {
	//只对超级管理员开放此功能
	if(current_user.level != 2) {
		return false;
	}
	$("#administrator_panel").show();
	//给管理员的按钮绑定点击事件，点击按钮查看管理员详细信息
	$("#administrator_panel .panel-body .administrator_panel .panel-body").delegate("button", "click", function() {
		var user = loadUser($(this).attr("administratorId"));
		var inputList = $("#administrator_detail_dialog .modal-body form input");
		$.each(inputList, function(index) {
			var input = inputList.eq(index);
			input.val(user[input.attr("name")]);
		});
		//记录当前查看的管理员所属模块
		var module = $(this).parent().attr("module");
		$("#administrator_detail_dialog").attr("module", module);
		$("#administrator_detail_dialog").modal("show");
	});
	loadAdministrator();
}
/**
 * 加载系统管理员信息
 */
function loadAdministrator() {
	$.ajax({
		url : "userAction!find.action",
		async : false,
		type : "POST",
		data : {
			limit : false,
			userLevel : 1
		},
		dataType : "json",
		success : function(data) {
			if(data.msg != undefined) {
				showSystemMsg(data.msg, 2);
				return false;
			}
			var administrators = data.list;
			var panelBodyList = $("#administrator_panel .panel-body .administrator_panel .panel-body");
			for(var i=0; i<panelBodyList.length; i++) {
				var panelBody = panelBodyList.eq(i);
				var module = panelBody.attr("module");		//获取模块名称
				for(var j=0; j<administrators.length; j++) {
					//如果是该模块的管理员，就产生一个button，添加到对应的panelBody上面
					if(administrators[j].authority.indexOf(module) >= 0) {
						var button = "<button class='btn btn-success btn-xs' administratorId='" + administrators[j].id + "'>" + administrators[j].name + "</button>";
						panelBody.append(button);
					}
				}
			}
		}
	});
}
/**
 * 取消一个用户的管理员权限
 */
function cancelAdmin() {
	var administratorId = $("#administrator_detail_dialog .modal-body input[name='id']").val();
	var administratorName = $("#administrator_detail_dialog .modal-body input[name='name']").val();
	var module = $("#administrator_detail_dialog").attr("module");
	if(!confirm("确定要取消" + administratorName + "的管理员身份")) {
		return false;
	}
	$.ajax({
		url : "userAction!cancelAdmin.action",
		type : "POST",
		data : {
			userId : administratorId,
			module : module
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				//被取消的管理员所在模块
				var panelBody = $("#administrator_panel .panel-body .administrator_panel .panel-body[module='" + module + "']");
				panelBody.children("button[administratorId='" + administratorId + "']").remove();
				$("#administrator_detail_dialog").modal("hide");
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 打开添加管理员的对话框
 * @param module	模块名称
 */
function openAddAdminDialog(module) {
	//设置对话框标题
	$("#add_administrator_dialog .modal-header .modal-title .module_name").html(moduleName[module]);
	//记录module属性，后面判断搜索到的用户是否已经是该模块的管理员
	$("#add_administrator_dialog").attr("module", module);
	$("#add_administrator_dialog").modal("show");
}
/**
 * 搜索用于设置管理员的用户
 */
function searchAdminUser() {
	var keyword = $("#add_administrator_dialog .modal-body .input-group input[name='keyword']").val();
	if(keyword == "") {
		inputPopover($("#add_administrator_dialog .modal-body .input-group input[name='keyword']"), "请输入姓名或者工号", 2);
		return false;
	}
	var data = {
			pageNum : admin_user_pageNum,
			pageSize : admin_user_pageSize,
			limit : true
	};
	//如果输入的是数字，根据工号查询，否则根据姓名查询
	if(/^\d+$/.test(keyword)) {
		data.userNumber = keyword;
	} else {
		data.userName = keyword;
	}
	$.ajax({
		url : "userAction!find.action",
		type : "POST",
		data : data,
		dataType : "json",
		success : function(data) {
			//判断是否显示“加载更多”按钮
			admin_user_total = data.total;
			if(admin_user_pageNum * admin_user_pageSize >= admin_user_total) {
				$("#add_administrator_dialog .modal-body #load_more_button").hide();
			} else {
				$("#add_administrator_dialog .modal-body #load_more_button").show();
			}
			var admin_user_list = $("#add_administrator_dialog .modal-body #admin_user_list");
			if(admin_user_pageNum == 1) {
				admin_user_list.children().remove();
			}
			for(var i=0; i<data.list.length; i++) {
				var item = 
					"<a href='javascript:void(0)' userId='" + data.list[i].id + "' authority='" + data.list[i].authority + "' " +
							"class='list-group-item' onclick='addAdmin($(this))'>" +
						data.list[i].name +
					"</a>";
				admin_user_list.append(item);
			}
		}
	});
}
/**
 * 添加管理员
 * @param a	
 */
function addAdmin(a) {
	var authority = a.attr("authority");
	var module = $("#add_administrator_dialog").attr("module");
	var name = a.html();
	var userId = a.attr("userId");
	if(authority.indexOf(module) >= 0) {
		var msg = name + "已经是" + moduleName[module] + "的管理员，无法重复设置";
		showSystemMsg(msg, 2);
		return false;
	}
	if(!confirm("确定要将" + name + "设置为" + moduleName[module] + "管理员？")) {
		return false;
	}
	$.ajax({
		url : "userAction!addAdmin.action",
		type : "POST",
		data : {
			userId : userId,
			module : module
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				var panelBody = $("#administrator_panel .panel-body .administrator_panel .panel-body[module='" + module + "']");
				var button = "<button class='btn btn-success btn-xs' administratorId='" + userId + "'>" + name + "</button>";
				panelBody.append(button);
				$("#add_administrator_dialog").modal("hide");
				$("#add_administrator_dialog .modal-body .input-group input").val("");
				$("#add_administrator_dialog .modal-body #admin_user_list").children().remove();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 查看用户详细信息
 * @param index
 */
function viewUserDetail(index) {
	var inputList = $("#user_detail_dialog .modal-body form input");
	$.each(inputList, function(i) {
		var name = inputList.eq(i).attr("name");
		inputList.eq(i).val(current_users[index][name]);
	});
	$("#user_detail_dialog").attr("index", index);
	$("#user_detail_dialog").modal("show");
}
/**
 * 删除用户
 */
function deleteUser() {
	var index = $("#user_detail_dialog").attr("index");
	if(!confirm("确定要删除用户：" + current_users[index].name + " 吗？")) {
		return false;
	}
	$.ajax({
		url : "userAction!delete.action",
		type : "POST",
		data : {
			userId : current_users[index].id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#user_panel .panel-body #user_list .list-group-item[index='" + index + "']").remove();
				$("#user_detail_dialog").modal("hide");
			}
			showSystemMsg(data.msg, 2);
		}
	});
}
/**
 * 加载反馈信息
 */
function searchFeedback() {
	var data = {
			pageNum : feedback_pageNum,
			pageSize : feedback_pageSize,
			limit : true
	};
	var keyword = $("#feedback_panel .panel-body .input-group input[name='keyword']").val();
	//如果输入的是数字，根据工号查询，否则根据姓名查询
	if(/^\d+$/.test(keyword)) {
		data.number = keyword;
	} else {
		data.name = keyword;
	}
	$.ajax({
		url : "feedbackAction!find.action",
		type : "POST",
		data : data,
		dataType : "json",
		success : function(data) {
			//判断是否显示“加载更多”按钮
			var feedback_list = $("#feedback_panel .panel-body #feedback_list");
			if(feedback_pageNum == 1) {
				feedback_list.children().remove();
				current_feedbacks = data.list;
			} else {
				current_feedbacks = current_feedbacks.concat(data.list);
			}
			//判断是否显示“加载更多”按钮
			feedback_total = data.total;
			if(feedback_total == 0) {
				$("#feedback_panel .panel-body .msg_div").show();
			} else {
				$("#feedback_panel .panel-body .msg_div").hide();
			}
			if(feedback_pageNum * feedback_pageSize >= feedback_total) {
				$("#feedback_panel .panel-body #load_more_button").hide();
			} else {
				$("#feedback_panel .panel-body #load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var index = (feedback_pageNum - 1) * feedback_pageSize + i;
				var content = current_feedbacks[index].content;
				if(content.length > 10) {
					content = content.substring(0, 10) + "...";
				}
				var badge = "";
				if(current_feedbacks[index].state == 0) {
					badge = "<span class='unread badge'>未读</span>";
				} else {
					badge = "<span class='read badge'>已读</span>";
				}
				var item = 
					"<a href='javascript:void(0)' class='list-group-item' onclick=viewFeedbackDetail('" + index + "') index='" + index + "'>" +
						badge + 
						"<h4 class='list-group-item-heading'>" + content + "</h4>" +
						"<p>" + current_feedbacks[index].name + " " + parseTime(current_feedbacks[index].time) + "</p>" +
					"</a>";
				feedback_list.append(item);
			}
		}
	});
}
/**
 * 查看用户详细信息
 * @param index
 */
function viewFeedbackDetail(index) {
	var inputList = $("#feedback_detail_dialog .modal-body form input, " +
								"#feedback_detail_dialog .modal-body form textarea");
	$.each(inputList, function(i) {
		var name = inputList.eq(i).attr("name");
		inputList.eq(i).val(current_feedbacks[index][name]);
	});
	$("#feedback_detail_dialog").attr("index", index);
	$("#feedback_detail_dialog").modal("show");
	readFeedback(index);
}
/**
 * 将反馈信息的状态设为已读
 */
function readFeedback(index) {
	$.ajax({
		url : "feedbackAction!updateState.action",
		type : "POST",
		data : {
			id : current_feedbacks[index].id,
			state : 1
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				var badge = $("#feedback_panel .panel-body #feedback_list .list-group-item[index='" + index + "'] .badge");
				badge.html("已读");
				badge.removeClass("unread");
				badge.addClass("read");
			}
		}
	});
}
/**
 * 删除反馈信息
 */
function deleteFeedback() {
	var index = $("#feedback_detail_dialog").attr("index");
	if(!confirm("确定要删除这一条反馈信息吗？")) {
		return false;
	}
	$.ajax({
		url : "feedbackAction!delete.action",
		type : "POST",
		data : {
			id : current_feedbacks[index].id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#feedback_panel .panel-body #feedback_list .list-group-item[index='" + index + "']").remove();
				$("#feedback_detail_dialog").modal("hide");
			}
			showSystemMsg(data.msg, 2);
		}
	});
}