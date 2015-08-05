association_pageNum = 1;			//页号
association_pageSize = 10;			//一页显示的数量或一次加载的数量
association_total = 0;			//当前取到的协会总数
current_associations = undefined;	//当前取到的协会信息

$(function() {
	loadAssociation();
});
/**
 * 查找/获取协会信息
 */
function loadAssociation() {
	showLoading("正在加载···");
	$.ajax({
		url : "associationAction!find.action",
		type : "POST",
		data : {
			pageNum : association_pageNum,
			pageSize : association_pageSize,
			name : $("#search_div input[name='keyword']").val()
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			var association_list = $("#association_panel .panel-body .association_list");
			if(association_pageNum == 1) {
				association_list.children().remove();
				current_associations = data.list;
			} else {
				current_associations = current_associations.concat(data.list);
			}
			association_total = data.total;
			if(association_total == 0) {
				$("#association_panel .panel-body .msg_div").show();
			} else {
				$("#association_panel .panel-body .msg_div").hide();
			}
			//判断是否显示“加载更多”按钮
			if(association_pageNum * association_pageSize >= association_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			$("#association_panel .panel-heading .panel-title .badge").html(association_total + "个协会");
			for(var i=0; i<data.list.length; i++) {
				var badge;
				if(data.list[i].state == 0) {
					badge = "待处理申请";
				} else {
					badge = data.list[i].size + "人";
				}
				var item = 
					"<a href='associationDetail.jsp?id=" + data.list[i].id + "' class='list-group-item'>" +
						"<span class='badge'>" + badge + "</span>" +
						data.list[i].name +
					"</a>";
				association_list.append(item);
			}
		}
	});
}
/**
 * 刷新协会列表
 */
function refresh() {
	$("#search_div input[name='keyword']").val("");
	association_pageNum = 1;
	loadAssociation();
}
/**
 *	创建协会
 * @returns {Boolean}
 */
function addAssociation() {
	var name = $("#add_association_dialog .modal-body form input[name='name']");
	var description = $("#add_association_dialog .modal-body form textarea[name='description']");
	if(name.val() == "") {
		inputPopover(name, "协会名称不能为空", 2);
		return false;
	}
	if(description.val() == "") {
		inputPopover(description, "协会简介不能为空", 2);
		return false;
	}
	var exist = checkExist(name.val());
	if(exist) {
		inputPopover(name, name.val() + "已经存在", 2);
		return false;
	}
	$.ajax({
		url : "associationAction!add.action",
		type : "POST",
		data : {
			name : name.val(),
			description : description.val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				loadAssociation();
				$("#add_association_dialog").modal("hide");
				$("#add_association_dialog .modal-body form input, " +
						"#add_association_dialog .modal-body form textarea").val("");
			}
			showSystemMsg(data.msg);
		},
		error:function(){
			showSystemMsg("无法连接服务器，请检查您的网络连接");
		}
	});
}
/**
 * 检查是否已经存在同名的协会
 * @param name
 */
function checkExist(name) {
	var exist = false;
	$.ajax({
		url : "associationAction!exist.action",
		type : "POST",
		async : false,
		data : {
			name : name
		},
		dataType : "json",
		success : function(data) {
			exist = data;
		}
	});
	return exist;
}