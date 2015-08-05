/**
 * 忘记密码对应的JS
 */

$(function() {
	//点击获取短信验证码
	$("input#getMessageCode").bind("click",function(){
		//alert('12');
		var phoneinput = $(".main_content form .input-group input[name='phone']");//判断是否填入了电话号码
		if(phoneinput.eq(0).val()==""){
			var msg="电话不能为空"
			inputPopover(phoneinput.eq(0), msg, 2);
			return false;
		}
		var isPhone=isphone2(phoneinput.eq(0).val());
		if(isPhone==false){
			 var msg='输入手机号码有误，请重新输入'
	    	inputPopover(phoneinput.eq(0), msg, 2);
			 return false;
		}
		
		$.ajax({
			url : "Sendsecuritycode.action",
			type : "POST",
			async : false,
			data : {
				"phone" : phoneinput.val(),	
			},
			dataType : "json",
			success : function(data) {
				if(data.message==="success"){
					//alert('验证码发送成功');
					showLoading('验证码发送成功');
					$('.footer').css('display','none');
				}else if(data.message==="noUser"){
					//alert('验证码发送失败，重新获取');
					showLoading('此电话号码未注册，请核对电话');
					$('.footer').css('display','block');
				}else{
					showLoading('验证码发送失败，重新获取');
					$('.footer').css('display','none');
				}
				
			},
			error:function(data){
				alert(data.message);
			}
		});
	});

	
	//确认短信验证码
	$("button#login_button").bind("click",function(){
		var phoneinput=$(".main_content form .input-group input[name='phone']");
		var valicodeinput= $(".main_content form .input-group input[name='valicode']");
		if(phoneinput.val()==""){
			var msg="电话不能为空"
			inputPopover(phoneinput, msg, 2);
			return false;
		}
		if(valicodeinput.val()==""){
			var msg="验证码不能为空,请获取短信验证码"
			inputPopover(valicodeinput, msg, 2);
			return false;
		}
		$.ajax({
			url : "Judgecode.action",
			type : "POST",
			//async : false,
			data : {
				"phone" : phoneinput.val(),	
				"valicode":valicodeinput.val()
			},
			dataType : "json",
			success : function(data) {//json传回用户名
				if(data.message=="error"){
					alert('验证码错误，请重新获取')
				}else{
					//alert('验证码成功'+data.context.username);
					//goTOjsp(data.context.username);
					//window.location.assign("changePassword.jsp?account="+data.context.username)
					document.forms[0].action = "changePassword.jsp?account="+data.context.username;
			        document.forms[0].submit();
					
				}
				
			}
		});
	});
	
});

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



