/**
 * 注册之前进行验证
 */
$(function() {
	var confirmPassword=document.getElementById('confirmPassword');
	confirmPassword.addEventListener('input', function (){
			var password = $(".main_content form .input-group input[name='password']");
	
			var confirmPassword = $(".main_content form .input-group input[name='confirmPassword']");
			if(password.val() != confirmPassword.val()) {
				
				return false;
			}
			 $("#checkPassword2").attr("src","img/password_ok.png");
	},false);
	
//	$('#confirmPassword').bind('keyup', function () {
//		var password = $(".main_content form .input-group input[name='password']");
//
//		var confirmPassword = $(".main_content form .input-group input[name='confirmPassword']");
//		if(password.val() != confirmPassword.val()) {
//			
//			return false;
//		}
//		 $("#checkPassword2").attr("src","img/password_ok.png");
//	});
	$('#password').bind('keyup', function () {
		var password = $(".main_content form .input-group input[name='password']");
		if(password.eq(0).val()==""){
			
			return false;
		}
		if(password.eq(0).val().length>=8) {
			$("#imgNum").attr("src","img/password_ok.png");
		}else{
			$("#imgNum").attr("src","img/password_no.png");
		}
		if(password.eq(0).val().match(/\d+/g)&&password.eq(0).val().match(/[A-Za-z]+/g)) {
			$("#imgContent").attr("src","img/password_ok.png");
		}else{
			$("#imgContent").attr("src","img/password_no.png");
		    return false;
		}
		
//		 $("#demand").empty();
//		 var demand='<img name="checkPassword" id="checkPassword"  class="check img-circle" src="img/password_ok.png"/>';
//		 $("#demand").append(demand);
	});	
	});
function check() {
	
	var inputList = $(".main_content #register_form .input-group input[name!='remark']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	var password = $(".main_content #register_form .input-group input[name='password']");
	var confirmPassword = $(".main_content #register_form .input-group input[name='confirmPassword']");
	if(password.val() != confirmPassword.val()) {
		inputPopover(confirmPassword, "两次输入的密码不一致", 2);
		return false;
	}
	//检查账号是否已经存在
	var account = $(".main_content #register_form .input-group input[name='account']");
	var number = $(".main_content #register_form .input-group input[name='number']");
	var nickname = $(".main_content #register_form .input-group input[name='nickname']");
	if(!number.val().match(/\d{8}/g)){
		inputPopover(number, "工号必须为8位数字", 2);
		return false;
	}
	var phonenumber=$(".main_content #register_form .input-group input[name='phone']");
	
	if(!isphone2(phonenumber.val())){
		 var msg='输入手机号码有误，请重新输入'
    	inputPopover(phonenumber, msg, 2);
		 return false;
	}
	var exist = checkExist(account.val(), number.val(),nickname.val());
	if(exist) {
		return false;
	}
	//检查昵称是否存在
	
	
	//修改缓存中的登录用户名，清除登录密码
	localStorage.account = $(".main_content form .input-group input[name='account']").val();
	localStorage.password = "";
	return true;
}

/**
 * 判断账号和工号是否已经存在
 * @param account
 */
function checkExist(account, number,nickname) {
	var exist = false;
	$.ajax({
		url : "userAction!exist.action",
		type : "POST",
		async : false,
		data : {
			account : account,
			number : number,
			nickname:nickname
		},
		dataType : "json",
		success : function(data) {
			exist = data.exist;
			if(exist) {
				var input = $(".main_content #register_form .input-group input[name='" + data.field + "']");
				inputPopover(input, data.name + "已经存在", 2);
			}
		}
	});
	return exist;
}

/**
 * 是否为手机号
*/
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
function showOK1(){
	var password = $(".main_content form .input-group input[name='password']");//判断是否填入了电话号码
	if(password.eq(0).val()==""){
		var msg="密码不能为空"
		inputPopover(password.eq(0), msg, 2);
		return false;
	}
	if(password.eq(0).val().length<8) {
		var msg="至少含有8个字符"
		inputPopover(password.eq(0), msg, 2);
		return false;
	}
	if(!(password.eq(0).val().match(/\d+/g))) {
		var msg="必须含有数字"
		inputPopover(password.eq(0), msg, 2);
		return false;
	}
	if(!(password.eq(0).val().match(/[A-Za-z]+/g))){
		var msg="必须含有字母"
			inputPopover(password.eq(0), msg, 2);
			return false;
	    }
	
}
function showNO(){
	var password = $(".main_content form .input-group input[name='password']");//判断是否填入了电话号码
	var confirmPassword = $(".main_content form .input-group input[name='confirmPassword']");
	if(password.val()==""){
		return false;
	}
	if(password.val() == confirmPassword.val()) {
		$("#checkPassword2").attr("src","img/password_ok.png");
	}else{
		 $("#checkPassword2").attr("src","img/password_no.png");
	}
	
	
}

function showOK(){
	var password = $(".main_content form .input-group input[name='password']");
	var confirmPassword = $(".main_content form .input-group input[name='confirmPassword']");
	if(password.val() != confirmPassword.val()) {
		inputPopover(confirmPassword, "两次输入的密码不一致", 2);
		
	}
	
}

function showDemand(){
	$("#demand").empty();
	var demand= '<p style="font-size:10px;height:12px;margin-bottom:2px"><img src="img/password_no.png" id="imgNum" class="smallcheck img-circle"/>至少包含8个字符;</p>'+
		'<p style="font-size:10px;height:12px;margin-bottom:2px"><img src="img/password_no.png" id="imgContent" class="smallcheck img-circle"/>必须包含数字及字母</P>';
	$("#demand").append(demand);
	var password = $(".main_content form .input-group input[name='password']");
	if(password.eq(0).val()==""){
		return false;
	}
	if(password.eq(0).val().length>=8) {
		$("#imgNum").attr("src","img/password_ok.png");
	}else{
		$("#imgNum").attr("src","img/password_no.png");
	}
	if(password.eq(0).val().match(/\d+/g)&&password.eq(0).val().match(/[A-Za-z]+/g)) {
		$("#imgContent").attr("src","img/password_ok.png");
	}else{
		$("#imgContent").attr("src","img/password_no.png");
	  
	}
}