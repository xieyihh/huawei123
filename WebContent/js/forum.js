current_topics = undefined;		//当前取到的话题
topic_pageNum = 1;				//当前显示的话题页号
topic_pageSize = 10;			//一页显示的话题数量
topic_total = undefined;		//当前取到的话题总数

$(function() {
	load();
});
/**
 * 加载话题列表
 */
function load() {
	showLoading("正在加载···");
	$.ajax({
		url : "topicAction!find.action",
		type : "POST",
		data : {
			pageNum : topic_pageNum,
			pageSize : topic_pageSize,
			title : $("#search_div input[name='keyword']").val()
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			var topic_list = $("#topic_list_div");
			if(topic_pageNum == 1) {
				topic_list.children().remove();
				current_topics = data.list;
			} else {
				current_topics = current_topics.concat(data.list);
			}
			topic_total = data.total;
			if(topic_total == 0) {
				$(".msg_div").show();
			} else {
				$(".msg_div").hide();
			}
			//判断是否显示“加载更多”按钮
			if(topic_pageNum * topic_pageSize >= topic_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var topic =
					"<div class='topic'>" +
						"<a href='topicDetail.jsp?id=" + data.list[i].id + "'>" +
							"<h4 class='topic_div'>" + data.list[i].title + "</h4>" +
							"<div class='topic_info'>" +
								"<span class='float_right'>" +
									"<span class='glyphicon glyphicon-heart-empty'></span> <span class='favor_amount'>" + data.list[i].favorAmount + "</span>" +
									"<span class='glyphicon glyphicon-comment'></span> <span class='comment_amount'>" + data.list[i].commentAmount + "</span>" +
								"</span>" +
								"<span class='founder'>" + data.list[i].founderName + "</span>" +
								"<span class='time'>" + parseTime(data.list[i].updateTime) + "</span>" +
							"</div>" +
						"</a>" +
					"</div>";
				topic_list.append(topic);
			}
		}
	});
}
/**
 * 刷新话题列表
 */
function refresh() {
	$("#search_div input[name='keyword']").val("");
	topic_pageNum = 1;
	load();
}
/**
 * 创建话题
 */
function addTopic() {
	var inputList = $("#create_topic_dialog .modal-body .input-group input, " +
									"#create_topic_dialog .modal-body .input-group textarea");
	if(!validateInputList(inputList)) {
		return false;
	}
	$.ajax({
		url : "topicAction!add.action",
		type : "POST",
		data : {
			title : $("#create_topic_dialog .modal-body .input-group input[name='title']").val(),
			detail : $("#create_topic_dialog .modal-body .input-group textarea[name='detail']").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#create_topic_dialog").modal("hide");
				$("#create_topic_dialog .modal-body .input-group input, " +
						"#create_topic_dialog .modal-body .input-group textarea").val("");
				refresh();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}