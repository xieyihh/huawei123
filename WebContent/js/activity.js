current_user = undefined;		//当前用户
current_activities = undefined;	//当前取到的大型活动信息
activity_pageNum = 1;		//当前显示的大型活动活动页号
activity_pageSize = 10;		//一页显示的大型活动活动数量
activity_total = undefined;	//当前取到的大型活动活动总数

$(function() {
	getCurrentUser();
	if(current_user.level == 2 || 
			(current_user.level == 1 && current_user.authority.indexOf("activity") >= 0)) {
		$("#activity_panel .panel-heading #initiateActivity").show();
	}
	load();
});
/**
 * 加载大型活动信息
 */
function load() {
	showLoading("正在加载···");
	$.ajax({
		url : "activityAction!find.action",
		type : "POST",
		data : {
			pageNum : activity_pageNum,
			pageSize : activity_pageSize,
			name : $("#search_div input[name='keyword']").val()
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			var activity_list = $("#activity_panel .panel-body #activity_list");
			if(activity_pageNum == 1) {
				activity_list.children().remove();
				current_activities = data.list;
			} else {
				current_activities = current_activities.concat(data.list);
			}
			activity_total = data.total;
			if(activity_total == 0) {
				$("#activity_panel .panel-body .msg_div").show();
			} else {
				$("#activity_panel .panel-body .msg_div").hide();
			}
			//判断是否显示“加载更多”按钮
			if(activity_pageNum * activity_pageSize >= activity_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			//显示大型活动总数
			$("#activity_panel .panel-heading .panel-title .badge").html(activity_total + "项活动");
			for(var i=0; i<data.list.length; i++) {
				var item = 
					"<a href='activityDetail.jsp?id=" + data.list[i].id + "' class='list-group-item'>" +
						"<span class='badge'>" + data.list[i].size + "人</span>" +
						"<span class='name'>" + data.list[i].name + "<span>" + 
						"<div class='right'>" + 
							"报名截止：" + data.list[i].applyDeadline.substring(0, 10) + 
						"</div>" + 
					"</a>";
				activity_list.append(item);
			}
		}
	});
}
/**
 * 刷新大型活动列表
 */
function refresh() {
	$("#search_div input[name='keyword']").val("");
	activity_pageNum = 1;
	load();
}
/**
 * 发起大型活动
 */
function initiateActivity() {
	var inputList = $("#initiate_activity_dialog .modal-body input[required='required'], " +
										"#initiate_activity_dialog .modal-body textarea[required='required']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	var name = $("#initiate_activity_dialog .modal-body input[name='name']");
	var description = $("#initiate_activity_dialog .modal-body textarea[name='description']");
	var applyDeadline = $("#initiate_activity_dialog .modal-body input[name='applyDeadline']");
	var startDate = $("#initiate_activity_dialog .modal-body input[name='startDate']");
	var endDate = $("#initiate_activity_dialog .modal-body input[name='endDate']");
	var property = $("#initiate_activity_dialog .modal-body textarea[name='property']");
	$.ajax({
		url : "activityAction!add.action",
		type : "POST",
		data : {
			name : name.val(),
			description : description.val(),
			applyDeadline : applyDeadline.val(),
			startDate : startDate.val(),
			endDate : endDate.val(),
			property : property.val().replace(/\s+/g, "")		//消除空格
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#initiate_activity_dialog .modal-body input, " +
						"#initiate_activity_dialog .modal-body textarea").val("");
				//先清空然后重新加载活动列表
				$("#activity_panel .panel-body #activity_list .list-group-item").remove();
				//刷新大型活动列表
				refresh();
				$("#initiate_activity_dialog").modal("hide");
			}
			showSystemMsg(data.msg, 2);
		}
	});
}