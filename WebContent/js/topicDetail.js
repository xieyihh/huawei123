current_user = undefined;		//当前登录的用户
current_topic = undefined;		//当前查看的话题
current_comments = undefined;	//当前取到的评论
comment_pageNum = 1;			//当前取到的评论页号
comment_pageSize = 10;			//一页显示的评论数量
comment_total = undefined;	//当前条件下的评论总数

function load(id) {
	getCurrentUser();
	showLoading("正在加载···");
	$.ajax({
		url : "topicAction!findById.action",
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(topic) {
			hideLoading();
			current_topic = topic;
			$("#topic_detail_panel .panel-heading .topic_title_span").html(topic.title);
			$("#topic_detail_panel .panel-heading .topic_info .float_right .favor_amount").html(topic.favorAmount);
			$("#topic_detail_panel .panel-heading .topic_info .float_right .comment_amount").html(topic.commentAmount);
			$("#topic_detail_panel .panel-heading .topic_info .founder").html(topic.founderName);
			$("#topic_detail_panel .panel-heading .topic_info .time").html(parseTime(topic.foundTime));
			$("#topic_detail_panel .panel-body").html(topic.detail);
			initButtons();
			loadComment();
		}
	});
}
/**
 * 初始化页面相关按钮的显示方式
 */
function initButtons() {
	//如果当前用户是管理员，显示删除按钮
	if(current_user.level == 2) {
		$("#topic_detail_panel .panel-footer #delete_button").show();
	}
	//已经点过赞就隐藏点赞按钮
	if(current_topic.favorId.indexOf(current_user.id + "&&") >= 0) {
		$("#topic_detail_panel .panel-footer #favor_button").hide();
	} else {
		$("#topic_detail_panel .panel-footer #favor_button").show();
	}
}
/**
 * 点赞
 */
function favor() {
	$.ajax({
		url : "topicAction!favor.action",
		type : "POST",
		data : {
			id : current_topic.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#topic_detail_panel .panel-heading .topic_info .float_right .favor_amount").html(++current_topic.favorAmount);
				$("#topic_detail_panel .panel-footer #favor_button").hide();
				current_topic.favorId += current_user.id + "&&";
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 删除话题
 */
function deleteTopic() {
	if(!confirm("确定要删除当前的话题吗？")) {
		return false;
	}
	$.ajax({
		url : "topicAction!delete.action",
		type : "POST",
		data : {
			id : current_topic.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				window.location.href = "forum.jsp";
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 加载评论
 */
function loadComment() {
	$.ajax({
		url : "commentAction!find.action",
		type : "POST",
		data : {
			pageNum : comment_pageNum,
			pageSize : comment_pageSize,
			topicId : current_topic.id
		},
		dataType : "json",
		success : function(data) {
			var commentList = $("#comment_list");
			if(comment_pageNum == 1) {
				commentList.children().remove();
				current_comments = data.list;
			} else {
				current_comments = current_comments.concat(data.list);
			}
			comment_total = data.total;
			//判断是否显示“加载更多”按钮
			if(comment_pageNum * comment_pageSize >= comment_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var comment = 
					"<div class='comment'>" +
						"<div class='comment_info'>" +
							"<span class='userName'>" + data.list[i].userName + "</span>" +
							"<span class='float_right'>" +
								"<span class='time'>" + parseTime(data.list[i].time) + "</span>" +
								"<span class='floor'>" + (i + 1) + "楼</span>" +
							"</span>" +
						"</div>" +
						"<div class='comment_detail'>" +
							data.list[i].detail +
						"</div>" +
					"</div>";
				commentList.append(comment);
			}
		}
	});
}
/**
 * 刷新评论列表
 */
function refreshComment() {
	comment_pageNum = 1;
	loadComment();
}
/**
 * 添加评论
 */
function addComment() {
	var detail = $("#add_comment_dialog .modal-content textarea[name='detail']");
	if(detail.val() == "") {
		inputPopover(detail, "请输入您的评论", 2);
		return false;
	}
	$.ajax({
		url : "commentAction!add.action",
		type : "POST",
		data : {
			topicId : current_topic.id,
			detail : detail.val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#topic_detail_panel .panel-heading .topic_info .float_right .comment_amount").html(++current_topic.commentAmount);
				refreshComment();
				$("#add_comment_dialog").modal("hide");
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}