current_user = undefined;	//当前登录的用户信息


$(function() {
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getuserphsicalinfo.action", //要访问的后台地址 
		data: {}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			 if(msg.message==="noImformation"){
					
					$('#user_info_panel').empty();
					$('#user_association_panel').css('display','none');
					var userTips='<div class="panel-body" style="margin-top:30px">' +
									'<div class="input-group">'+
										'<span class="input-group-addon " >'+msg.context.physicalinit.physicalplan+'</span>'+
									'</div>'+
								'</div>';
					$('#user_info_panel').append(userTips);
					return false;
			 }else if(msg.message==="success"){
					var form=msg.context.physicalexamForm;
					if(form.physicalstate==="已体检"){
						$('#user_info_panel').empty();
						$('#user_association_panel').css('display','none');
						var userTips='<div class="panel-body" style="margin-top:30px">' +
										'<div class="input-group">'+
											'<span class="input-group-addon " >在此批次体检中，您已参与体检</span>'+
										'</div>'+
									'</div>';
						$('#user_info_panel').append(userTips);
						return false;
					}else if(form.physicalstate==="体检过期"){
						$('#user_info_panel').empty();
						$('#user_association_panel').css('display','none');
						var userTips='<div class="panel-body" style="margin-top:30px">' +
										'<div class="input-group">'+
											'<span class="input-group-addon " >在此批次体检中，您的体检信息已过期</span>'+
										'</div>'+
									'</div>';
						$('#user_info_panel').append(userTips);
						return false;
					}
			 }
		}, 
				error: function() { 
					alert("保存数据失败"); 
					return false;
				} 
	});
	initPanelHeading();
	getCurrentUser();
	//load(current_user);
	loadRelatives();
	saveRelatives();
	resetRelatives();
	
	/**
	 * 删除添加的家属信息
	 */
	$(document).on("click","[id^='deleteRelative']",function(){//修改成这样的写法
		var id=$(this).attr("id").toString().slice(14);
		
		if(confirm('确认删除此家属信息吗？')){
			var id=
			$.ajax({
				url : "deleteuserphsicalrelatives.action",
				type : "POST",
				data : {'id':id},
				dataType : "json",
				success : function(data) {
					if(data.message=='success') {
						loadRelatives();
						showSystemMsg('删除成功', 1);
						
						//showLoading('验证码发送成功');
					} else {
						
					}
				}
			});
		}
	  });
	$('.name').css('width',$(".time").outerWidth());
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
 * 加载用户已添加的家属信息
 */
function loadRelatives() {
	$.ajax({
		url : "getuserphsicalrelatives.action",
		type : "POST",
		data : { },
		dataType : "json",
		success : function(data) {
			var relativeLists=data.context.physicalrelativeslist;
			var total=data.context.physicalrelativessize;
			$("#user_association_panel .panel-heading .panel-title .badge").html( total+ "个家属");
			var association_list = $("#user_association_panel .panel-body #association_list");
			association_list.empty();
			$(relativeLists).each(function(index, item) {
				var list = 
					'<div class="input-group" style="margin-bottom:7px;width:100%">'+
						'<span class="input-group-addon name">姓名</span>'+
						'<input type="text" class="form-control"  value="'+ item.relativeName+'" readonly>'+
					'</div>'+
					'<div class="input-group"style="margin-bottom:7px;width:100%">'+
						'<span class="input-group-addon name">身份证号</span>'+
						'<input type="text" class="form-control"  value="'+ item.idnumber+'" readonly>'+
					'</div>'+
					'<div class="input-group" style="margin-bottom:7px;width:100%">'+
						'<span class="input-group-addon name">关系</span>'+
						'<input type="text" class="form-control" value="'+ item.relationship+'" readonly>'+
					'</div>'+
					'<div class="input-group" style="margin-bottom:7px;width:100%">'+
						'<span class="input-group-addon name">电话</span>'+
						'<input type="text" class="form-control" value="'+ item.phonenumber+'" readonly>'+
					'</div>'+	
					'<button id="deleteRelative'+ item.id+'"class="btn btn-danger delete" style="margin-bottom:7px;" >'+
						'<span class="glyphicon glyphicon-remove-circle"></span> 删除'+
					'</button>';
				association_list.append(list);
				$('.name').css('width',$(".time").outerWidth());
				$('.delete').css('width',$(".reset").outerWidth());
				var left=$(".input-group").outerWidth()-$('.delete').outerWidth();
				$('.delete').css('margin-left',left);
				
			});
		}
	});
}


/**
 * 保存添加的家属信息
 */
function saveRelatives() {
	$('#saveRelative').click(function(){
		var inputList = $("#user_info_panel .panel-body input[required='required']");
		var validateResult = validateInputList(inputList);
		if(!validateResult) {
			return false;
		}
		var identifyNumber=$("#user_info_panel .panel-body input[name='identifyNumber']").val();
		var phonenumber=$("#user_info_panel .panel-body input[name='phone']").val();
		if(validateResult){
			if(!identifyNumber.match(/\d{18}/g)){
				var msg='身份证号码有误，请重新输入'
				inputPopover($("#user_info_panel .panel-body input[name='identifyNumber']"), msg, 1);
				return false;
			}
			var isPhone=isphone2(phonenumber);
			if(isPhone==false){
				 var msg='输入手机号码有误，请重新输入'
		    	inputPopover($("#user_info_panel .panel-body input[name='phone']"), msg, 2);
				 return false;
			}
		}
		$.ajax({
			url : "saveuserphsicalrelatives.action",
			type : "POST",
			data : {
				relativeName : $("#user_info_panel .panel-body input[name='name']").val(),
				idnumber : $("#user_info_panel .panel-body input[name='identifyNumber']").val(),
				relationship : $("#user_info_panel .panel-body input[name='relationship']").val(),
				phonenumber : $("#user_info_panel .panel-body input[name='phone']").val(),
				
			},
			dataType : "json",
			success : function(data) {
				if(data.message=='success') {
					showSystemMsg('添加成功', 1);
					loadRelatives();
					$("#user_info_panel .panel-body input[name='name']").val("");
					$("#user_info_panel .panel-body input[name='identifyNumber']").val("");
					$("#user_info_panel .panel-body input[name='relationship']").val("");
					$("#user_info_panel .panel-body input[name='phone']").val("");
				} else {
					if(data.context.indexOf("存在") >=0 ) {
						inputPopover($("#user_info_panel .panel-body input[name='name']"), data.context, 1);
					} else {
						showSystemMsg(data.context, 2);
					}
				}
			}
		});
	});
}
//重置表单
function resetRelatives() {
	$('#resetRelative').click(function(){
		$("#user_info_panel .panel-body input[name='name']").val('');
		$("#user_info_panel .panel-body input[name='identifyNumber']").val('');
		$("#user_info_panel .panel-body input[name='relationship']").val('');
		$("#user_info_panel .panel-body input[name='phone']").val('');
		
	});
}
function isphone2(inputString)
{
     var partten = /^1[3,5,8]\d{9}$/;
     var fl=false;
     if(partten.test(inputString))
     {
          //alert('是手机号码');
          return true;
     }
     else
     {
 			return false;
    	    
     }
}