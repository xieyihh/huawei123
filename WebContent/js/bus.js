current_user = undefined;	//当前登录的班车信息
current_buses = undefined;		//当前取到的班车信息
bus_pageNum = 1;				//当前显示的班车信息页号
bus_pageSize = 10;			//一页显示的班车数量
bus_total = undefined;		//当前取到的班车总数

$(function() {
	getCurrentUser();
	//如果是班车管理员，则显示添加、导入、保存、删除班车信息的按钮
	if(current_user.level == 2 || 
			(current_user.level == 1 && current_user.authority.indexOf("bus&&") >= 0)) {
		$("#bus_panel .panel-footer button").show();
		$("#bus_detail_dialog .modal-footer #saveBus, " +
				"#bus_detail_dialog .modal-footer #deleteBus").show();
		$("#bus_detail_dialog .modal-body .input-group input, " +
				"#bus_detail_dialog .modal-body .input-group textarea").removeAttr("readonly");
	}
	//给选择条件下拉项设置点击事件
	$("#bus_panel .panel-body .input-group ul li a").click(function() {
		var type = $(this).html();
		var button = $("#bus_panel .panel-body .input-group .input-group-btn button");
		button.children(".bus_type").html(type);
	});
	//加载班车信息
	loadBus();
});
/**
 * 加载班车信息
 */
function loadBus() {
	var data = {
			pageNum : bus_pageNum,
			pageSize : bus_pageSize
	};
	var type = $("#bus_panel .panel-body .input-group .input-group-btn button .bus_type").html();
	if(type != "全部") {
		data.type = type;
	}
	var keyword = $("#bus_panel .panel-body .input-group input[name='keyword']").val();
	if(keyword != "") {
		//如果是字母和数字的组合，作为编号查询；否则作为线路查询
		if(/^[a-zA-Z]*\d+$/.test(keyword)) {
			data.number = keyword;
		} else {
			data.line = keyword;
		}
	}
	showLoading("正在加载···");
	$.ajax({
		url : "busAction!find.action",
		type : "POST",
		data : data,
		dataType : "json",
		success : function(data) {
			hideLoading();
			var bus_list = $("#bus_panel .panel-body #bus_list");
			if(bus_pageNum == 1) {
				bus_list.children().remove();
				current_buses = data.list;
			} else {
				current_buses = current_buses.concat(data.list);
			}
			//判断是否显示“加载更多”按钮
			bus_total = data.total;
			if(bus_total == 0) {
				$("#bus_panel .panel-body .msg_div").show();
			} else {
				$("#bus_panel .panel-body .msg_div").hide();
			}
			if(bus_pageNum * bus_pageSize >= bus_total) {
				$("#bus_panel .panel-body #load_more_button").hide();
			} else {
				$("#bus_panel .panel-body #load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var index = (bus_pageNum - 1) * bus_pageSize + i;
				var badge = "";
				if(data.list[i].time != "") {
					badge = "<span class='badge'>" + data.list[i].time + "发车</span>";
				}
				var item = 
					"<a href='javascript:void(0)' class='list-group-item' onclick=viewDetail('" + index + "') index='" + index + "'>" +
						"<h4 class='list-group-item-heading'>" + data.list[i].type + "  " + data.list[i].number + "</h4>" + badge + 
						"<p>" + data.list[i].start + "~" + data.list[i].end + "</p>" + 
					"</a>";
				bus_list.append(item);
			}
		}
	});
}
/**
 * 查看班车详细信息
 */
function viewDetail(index) {
	var inputList = $("#bus_detail_dialog .modal-body .input-group input");
	$.each(inputList, function(i) {
		var name = inputList.eq(i).attr("name");
		inputList.eq(i).val(current_buses[index][name]);
	});
	$("#bus_detail_dialog .modal-body .input-group textarea").val(current_buses[index].line);
	$("#bus_detail_dialog").attr("index", index);		//在对话框的属性中记录当前查看的班车索引
	$("#bus_detail_dialog").modal("show");
}
/**
 * 添加班车信息
 */
function addBus() {
	var typeInput = $("#add_bus_dialog .modal-body .input-group input[name='type']");
	if(typeInput.val() == "") {
		inputPopover(typeInput, "请输入班车类型", 2);
		return false;
	}
	$.ajax({
		url : "busAction!add.action",
		type : "POST",
		data : {
			type : typeInput.val(),
			number : $("#add_bus_dialog .modal-body .input-group input[name='number']").val(),
			time : $("#add_bus_dialog .modal-body .input-group input[name='time']").val(),
			start : $("#add_bus_dialog .modal-body .input-group input[name='start']").val(),
			end : $("#add_bus_dialog .modal-body .input-group input[name='end']").val(),
			line : $("#add_bus_dialog .modal-body .input-group textarea[name='line']").val(),
			price : $("#add_bus_dialog .modal-body .input-group input[name='price']").val(),
			vehicleType : $("#add_bus_dialog .modal-body .input-group input[name='vehicleType']").val(),
			plateNumber : $("#add_bus_dialog .modal-body .input-group input[name='plateNumber']").val(),
			driver : $("#add_bus_dialog .modal-body .input-group input[name='driver']").val(),
			remark : $("#add_bus_dialog .modal-body .input-group input[name='remark']").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				$("#add_bus_dialog").modal("hide");
				$("#add_bus_dialog .modal-body .input-group input, " +
						"#add_bus_dialog .modal-body .input-group textarea").val("");
				refresh();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2000);
			}
		}
	});
}
/**
 * 导入班车信息
 */
function importBus() {
	var filepath = $("#import_bus_dialog input[name='upload']").val();
	var reg = new RegExp("(\.xlsx$)|(\.xls$)");
	if(!reg.test(filepath)) {
		showSystemMsg("请选择一个Excel文件", 2);
		$("#import_bus_dialog input[name='upload']").val("");
		return false;
	}
	$("#import_bus_dialog").modal("hide");
	$.ajaxFileUpload({
		url : "busAction!importBus.action",
		type : "POST",
		secureuri : false,
		fileElementId : "bus_file_upload",
		dataType : "json",
		success : function(data) {
//			hideLoading();
			if(data.success) {
				refresh();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2000);
			}
			$("#import_bus_dialog input[name='upload']").val("");
		}
	});
}
/**
 * 保存班车信息
 */
function updateBus() {
	var bus = {
		id : $("#bus_detail_dialog .modal-body .input-group input[name='id']").val(),
		type : $("#bus_detail_dialog .modal-body .input-group input[name='type']").val(),
		number : $("#bus_detail_dialog .modal-body .input-group input[name='number']").val(),
		time : $("#bus_detail_dialog .modal-body .input-group input[name='time']").val(),
		line : $("#bus_detail_dialog .modal-body .input-group textarea[name='line']").val(),
		price : $("#bus_detail_dialog .modal-body .input-group input[name='price']").val(),
		vehicleType : $("#bus_detail_dialog .modal-body .input-group input[name='vehicleType']").val(),
		plateNumber : $("#bus_detail_dialog .modal-body .input-group input[name='plateNumber']").val(),
		driver : $("#bus_detail_dialog .modal-body .input-group input[name='driver']").val(),
		remark : $("#bus_detail_dialog .modal-body .input-group input[name='remark']").val()
	};
	$.ajax({
		url : "busAction!update.action",
		type : "POST",
		data : bus,
		dataType : "json",
		success : function(data) {
			if(data.success) {
				var index = $("#bus_detail_dialog").attr("index");
				current_buses[index] = bus;
				$("#bus_detail_dialog").modal("hide");
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 删除班车信息
 */
function deleteBus() {
	if(!confirm("确定要删除当前查看的班车信息吗？")) {
		return false;
	}
	$.ajax({
		url : "busAction!delete.action",
		type : "POST",
		data : {
			id : $("#bus_detail_dialog .modal-body .input-group input[name='id']").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				//删除当前班车数组中对应的班车信息
				var index = $("#bus_detail_dialog").attr("index");
				current_buses.splice(index, 1);
				$("#bus_detail_dialog").modal("hide");
				$("#bus_panel .panel-body #bus_list a[index='" + index + "']").remove();
				showSystemMsg(data.msg, 1);
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 刷新班车列表
 */
function refresh() {
	$("#bus_panel .panel-body .input-group input[name='keyword']").val("");
	bus_pageNum = 1;
	loadBus();
}