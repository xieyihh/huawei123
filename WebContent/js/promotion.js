current_promotions = undefined;		//当前取到的打折信息
promotion_pageNum = 1;				//当前显示的打折信息页号
promotion_pageSize = 10;			//一页显示的打折信息数量
promotion_total = undefined;		//当前取到的打折信息总数

$(function() {
	load();
	//选择一张照片触发的操作
	$("#publish_promotion_dialog .modal-body #photo_chooser_table").delegate(".photo_chooser_input", "change", function() {
		var tr = $(this).parent().parent().parent();
		var src = "";
		if(window.URL) {
			src = window.URL.createObjectURL($(this)[0].files[0]);
		} else {
			var reader = new FileReader();
			reader.readAsDataURL($(this)[0].files[0]);
			reader.onload = function(e){
				src = this.result;
			};
		}
		var img = "<img src='" + src+ "'>";
		tr.find(".photo_chooser_td").children().hide();
		tr.find(".photo_chooser_td").append(img);
		tr.find(".state_td").html("<span class='label label-danger'>待上传</span>");
	});
});
/**
 * 加载打折信息
 */
function load() {
	showLoading("正在加载···");
	$.ajax({
		url : "promotionAction!find.action",
		type : "POST",
		data : {
			pageNum : promotion_pageNum,
			pageSize : promotion_pageSize,
			title : $("#search_div input[name='keyword']").val()
		},
		dataType : "json",
		success : function(data) {
			hideLoading();
			var promotion_list = $("#promotion_list_div");
			if(promotion_pageNum == 1) {
				promotion_list.children().remove();
				current_promotions = data.list;
			} else {
				current_promotions = current_promotions.concat(data.list);
			}
			promotion_total = data.total;
			//判断是否显示“加载更多”按钮
			if(promotion_total == 0) {
				$(".msg_div").show();
			} else {
				$(".msg_div").hide();
			}
			if(promotion_pageNum * promotion_pageSize >= promotion_total) {
				$("#load_more_button").hide();
			} else {
				$("#load_more_button").show();
			}
			for(var i=0; i<data.list.length; i++) {
				var span = "";
				if(data.list[i].state == 1) {
					span = "<span class='glyphicon glyphicon-heart-empty'></span> <span class='favor_amount'>" + data.list[i].favorAmount + "</span>";
				} else {
					span = "<span class='label label-danger'>待审核</span>";
				}
				var promotion =
					"<div class='promotion'>" +
						"<a href='promotionDetail.jsp?id=" + data.list[i].id + "'>" +
							"<h4 class='promotion_div'>" + data.list[i].title + "</h4>" +
							"<div class='promotion_info'>" +
								"<span class='float_right'>" +
									span +
								"</span>" +
								"<span class='publisher'>" + data.list[i].publisher + "</span>" +
								"<span class='date'>" + parseDate(data.list[i].date) + "</span>" +
							"</div>" +
						"</a>" +
					"</div>";
				promotion_list.append(promotion);
			}
		}
	});
}
/**
 * 刷新打折信息列表
 */
function refresh() {
	$("#search_div input[name='keyword']").val("");
	promotion_pageNum = 1;
	load();
}
/**
 * 添加一个照片选择器
 */
function addPhotoChooser() {
	var tbody = $("#publish_promotion_dialog .modal-body #photo_chooser_table tbody");
	var rows = tbody.find("tr").length;	//当前的行数
	var tr = 
		"<tr>" +
			"<td class='delete_td'>" +
				"<button class='btn btn-danger btn-xs' onclick='deletePhoto($(this))'>" +
					"<span class='glyphicon glyphicon-trash'></span>" +
				"</button>" +
			"</td>" +
			"<td class='photo_chooser_td'>" +
				"<span class='btn btn-success fileinput-button'>" +
					"<span class='glyphicon glyphicon-ok'></span>" +
					"<span>选择照片</span>" +
					"<input id='photo_chooser_input_" + rows + "' class='photo_chooser_input' type='file' name='upload' accept='image/*'>" + 
				"</span>" +
			"</td>" +
			"<td class='state_td'>" +
			"</td>" +
		"</tr>";
	tbody.append(tr);
	$("#publish_promotion_dialog .modal-body #add_photo_div button .button_text").html("继续上图");
}
/**
 * 删除一张照片
 */
function deletePhoto(button) {
	var tr = button.parent().parent();
	tr.remove();
	var tbody = $("#publish_promotion_dialog .modal-body #photo_chooser_table tbody");
	var rows = tbody.find("tr").length;	//当前的行数
	if(rows == 0) {
		$("#publish_promotion_dialog .modal-body #add_photo_div button .button_text").html("上图");
	}
}
/**
 * 发布打折促销信息
 */
function publish() {
	var inputList = $("#publish_promotion_dialog .modal-body form .input-group input, " +
								"#publish_promotion_dialog .modal-body form .input-group textarea");
	if(!validateInputList(inputList)) {
		return false;
	}
	//先上传照片，再提交标题和详情
	uploadPhoto();
}
/**
 * 上传照片
 */
function uploadPhoto() {
	var inputs = $("#publish_promotion_dialog .modal-body #photo_chooser_table tbody tr .photo_chooser_td .photo_chooser_input");
	var inputList = new Array();
	for(var i=0; i<inputs.length; i++) {
		if(inputs.eq(i).val() == "") {
			showSystemMsg("有照片未被选择", 2);
			return false;
		} else {
			inputList.push(inputs.eq(i));
		}
	}
	if(inputs.length == 0) {
		if(!confirm("无图无真相，确定不上传照片吗？")) {
			return false;
		}
		uploadText();
	} else {
		inputList.reverse();
		loopUploadPhoto(inputList);
	}
//	$.each(inputs, function() {
//		inputList.push($(this));
//	});
}
/**
 * 依次上传每一张照片
 * @param inputList
 */
function loopUploadPhoto(inputList) {
	var tr = inputList[inputList.length - 1].parent().parent().parent();
	tr.find(".state_td").html("<span class='label label-warning'>正在上传···</span>");
	$.ajaxFileUpload({
		url : "promotionAction!uploadPhoto.action",
		async : false,
		type : "POST",
		secureuri : false,
		fileElementId : inputList[inputList.length - 1].attr("id"),
		dataType : "json",
		success : function(data) {
			tr.find(".state_td").html("<span class='label label-success'>上传成功</span>");
			inputList.pop();
			//如果还有照片没有上传，继续上传，全部上传后清除对话框，上传文本信息
			if(inputList.length > 0) {
				loopUploadPhoto(inputList);
			} else {
				uploadText();
			}
		}
	});
}
/**
 * 上传文本信息
 */
function uploadText() {
	$.ajax({
		url : "promotionAction!add.action",
		type : "POST",
		data : {
			title : $("#publish_promotion_dialog .modal-body form .input-group input[name='title']").val(),
			detail : $("#publish_promotion_dialog .modal-body form .input-group textarea[name='detail']").val()
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				//关闭对话框，删除每一行
				refresh();
				showSystemMsg(data.msg, 1);
				$("#publish_promotion_dialog").modal("hide");
				$("#publish_promotion_dialog .modal-body #photo_chooser_table tbody tr").remove();
				$("#publish_promotion_dialog .modal-body #photo_chooser_table tbody tr .photo_chooser_td .photo_chooser_input").val("");
				$("#publish_promotion_dialog .modal-body form .input-group input, " +
					"#publish_promotion_dialog .modal-body form .input-group textarea").val("");
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}