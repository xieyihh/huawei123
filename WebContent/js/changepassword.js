/**
 * 修改密码对应的JS
 */
var CODE={
				
			    //初始化化验证码
			    initCode:function(){
			    	
			     $("#randImage").attr("src","RandomImageServlet?rmd="+new Date().getTime())//如果需要点击图片改变图片的内容，则必须添加时间搓
			     .click(function(){
			      $(this).attr("src","RandomImageServlet?rmd="+new Date().getTime());
			     }); 
			    }
		};
		
$(function() {
	CODE.initCode();


	//确认短信验证码
	$("button#login_button").bind("click",function(){
		
		var password = $(".main_content form .input-group input[name='password']");
		var confirmPassword = $(".main_content form .input-group input[name='confirmPassword']");
		showOK1();
		if(password.val() != confirmPassword.val()) {
			inputPopover(confirmPassword, "两次输入的密码不一致", 2);
			return false;
			
		}
		
		//alert('12');
		var randCode=$(".main_content form .input-group input[name='rand']");
		//alert(randCode.val());
		var account=$("input#account").val();
		//alert(account);
		$.ajax({
			url : "Changepassword.action",
			type : "POST",
			async : false,
			data : {
				"randCode" : randCode.val(),
				"password":password.val(),
				"account":account
			},
			dataType : "json",
			success : function(data) {//json传回用户名
				if(data.message=="RandCodeerror"){
					//alert('验证码错误');
					showLoading('验证码错误');
					CODE.initCode();
				}else if(data.message=="error"){
					//alert('用户名不存在')
					showLoading('用户名不存在');
				}else{
					//alert('修改成功');
					localStorage.setItem('account',account);
					localStorage.removeItem('password');
					document.forms[0].action =  "login.jsp";
			        document.forms[0].submit();
			        showLoading('修改成功');
				}
			}
		});
	});
	//Dom事件
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

