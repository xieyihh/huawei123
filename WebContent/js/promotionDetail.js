current_user = undefined;		//当前登录的用户
current_promotion = undefined;		//当前查看的打折信息
/**
 * 加载打折信息
 * @param id
 */
function load(id) {
	getCurrentUser();
	showLoading("正在加载···");
	$.ajax({
		url : "promotionAction!findById.action",
		type : "POST",
		data : {
			id : id
		},
		dataType : "json",
		success : function(promotion) {
			hideLoading();
			if(promotion == null) {
				showSystemMsg("对应的打折信息不存在，可能已被删除", 2);
				return false;
			}
			current_promotion = promotion;
			//显示文本信息
			$("#promotion_detail_panel .panel-heading .promotion_title_span").html(promotion.title);
			$("#promotion_detail_panel .panel-heading .promotion_info .float_right .favor_amount").html(promotion.favorAmount);
			$("#promotion_detail_panel .panel-heading .promotion_info .founder").html(promotion.publisher);
			$("#promotion_detail_panel .panel-heading .promotion_info .date").html(parseDate(promotion.date));
			//显示图片
			var panelBody = $("#promotion_detail_panel .panel-body");
			panelBody.html(promotion.detail);
			var photos = promotion.photoUrl.split("&&");
			$.each(photos, function(index) {
				if(photos[index] != "") {
					var div = "<div class='img_div'><img src='" + photos[index] + "'></div>";
					panelBody.append(div);
				}
			});
			initButtons();
		}
	});
}
/**
 * 初始化页面相关按钮的显示方式
 */
function initButtons() {
	//如果当前是管理员，显示删除按钮。如果当前打折信息未被审核，显示审核按钮
	if(current_user.level == 2 || 
			(current_user.level == 1 && current_user.authority.indexOf("discount&&") >= 0)) {
		$("#promotion_detail_panel .panel-footer #delete_button").show();
		if(current_promotion.state == 0) {
			$("#promotion_detail_panel .panel-footer #authorize_button").show();
		}
	}
	//已经点过赞就隐藏点赞按钮
	if(current_promotion.favorId.indexOf(current_user.id + "&&") >= 0) {
		$("#promotion_detail_panel .panel-footer #favor_button").hide();
	} else {
		$("#promotion_detail_panel .panel-footer #favor_button").show();
	}
}
/**
 * 点赞
 */
function favor() {
	$.ajax({
		url : "promotionAction!favor.action",
		type : "POST",
		data : {
			id : current_promotion.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				showSystemMsg(data.msg, 1);
				$("#promotion_detail_panel .panel-heading .promotion_info .float_right .favor_amount").html(++current_promotion.favorAmount);
				$("#promotion_detail_panel .panel-footer #favor_button").hide();
				current_promotion.favorId += current_user.id + "&&";
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 批准打折信息发布
 */
function authorize() {
	$.ajax({
		url : "promotionAction!authorize.action",
		type : "POST",
		data : {
			id : current_promotion.id
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				showSystemMsg(data.msg, 1);
				$("#promotion_detail_panel .panel-footer #authorize_button").hide();
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}
/**
 * 删除打折信息
 */
function deletePromotion() {
	if(!confirm("确定要删除当前的打折信息？")) {
		return false;
	}
	$.ajax({
		url : "promotionAction!delete.action",
		type : "POST",
		data : {
			id : current_promotion.id,
			photoUrl : current_promotion.photoUrl
		},
		dataType : "json",
		success : function(data) {
			if(data.success) {
				window.location.href = "promotion.jsp";
			} else {
				showSystemMsg(data.msg, 2);
			}
		}
	});
}