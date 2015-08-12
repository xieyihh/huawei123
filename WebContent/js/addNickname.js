/**
 * 注册之前进行验证
 */
$(function() {
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getuserinfo.action", //要访问的后台地址 
		data: {}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message==="success"){
					if(msg.context==="noUser"){
						$("#addnickname_form").css('display','none');
					}else{
						var form=msg.context.userinfo;
						$("input[name='name']").val(form.username);
						$("input[name='number']").val(form.usernumber);
						$("#addnickname_form").css('display','table');
					}
				}
		}, 
		error: function() { 
			alert("保存数据失败"); 
		} 
	}); 
	});
function check() {
	
	var inputList = $(".main_content #addnickname_form .input-group input[name!='remark']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	
	var nickname = $(".main_content #addnickname_form .input-group input[name='nickname']");
	//alert(nickname.val())
	var exist = checkExist(nickname.val());
	if(exist) {
		return false;
	}
	//检查昵称是否存在
	
	
	
	return true;
}

/**
 * 判断昵称是否已经存在
 * @param account
 */
function checkExist(nickname) {
	var exist = false;
	$.ajax({
		url : "userAction!exist.action",
		type : "POST",
		async : false,
		data : {
			
			nickname:nickname
		},
		dataType : "json",
		success : function(data) {
			exist = data.exist;
			if(exist) {
				var input = $(".main_content #addnickname_form .input-group input[name='" + data.field + "']");
				inputPopover(input, data.name + "已经存在", 2);
			}
		}
	});
	return exist;
}

