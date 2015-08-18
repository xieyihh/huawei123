/**
 * 登录页面对应的JS
 */

$(function() {
	read();		//读取缓存的用户名密码
	if($(".main_content .alert .message").html() != "") {
		$(".main_content .alert").show();
		setTimeout(function() {
			$(".main_content .alert").hide();
		}, 2000);
	}
	/**
	 * 注册
	 */
	$("#register_button").click(function() { 

		document.forms[0].action =  "register.jsp";
        document.forms[0].submit();
	});
	
});
/**
 * 读取缓存的用户名密码
 */
function read() {
	var account = localStorage.getItem('account');	
	var password = localStorage.getItem('password');
	var isRemmeberPassword = localStorage.getItem('isRemmeberPassword');
	//alert(account+';'+password+';'+isRemmeberPassword);
	if(account != undefined) {
		$(".main_content form .input-group input[name='account']").val(account);
	}
//	else{
//		$(".main_content form .input-group input[name='account']").val(getCookie("account"));
//	}
	if(password != undefined) {
		$(".main_content form .input-group input[name='password']").val(password);
	}
//	else{
//		$(".main_content form .input-group input[name='password']").val(getCookie("password"));
//	}
	if(isRemmeberPassword != undefined) {	
		$('#RemmeberPassword').attr('checked',isRemmeberPassword); 
	}
//	else{
//		$('#RemmeberPassword').attr('checked',getCookie("isRemmeberPassword"));
//	}
}
/**
 * 存储用户名和密码
 */
function check() {
	
	var inputList = $(".main_content form .input-group input[name!='remark']");
	var validateResult = validateInputList(inputList);
	if(!validateResult) {
		return false;
	}
	 var password = $(".main_content form .input-group input[name='password']").val();
	//alert($("#RemmeberPassword").attr("checked"));
	var isChecked =$('#RemmeberPassword').prop('checked');  
	 if (isChecked) {
		 localStorage.setItem('password',password);	
		 localStorage.setItem('isRemmeberPassword',true);	
		 //setCookie("password",password,100);
		 //setCookie("isRemmeberPassword",true,100);
		 
     }else{
    	 localStorage.removeItem('password');
    	 localStorage.removeItem('isRemmeberPassword');
    	 //delCookie('password');
    	 //delCookie('isRemmeberPassword');
     }
	 var account = $(".main_content form .input-group input[name='account']").val();
	 localStorage.setItem('account',account);	
	// setCookie("account",account,100);
	
	return true;
}
//function setCookie(key, value, iDay){ 
//	var cookieDate=new Date();   
//	cookieDate.setDate(cookieDate.getDate()+iDay);
//    document.cookie=key+'='+escape(value)+'; expires='+cookieDate.toGMTString()+'; path=/';
//}
//function getCookie(key){
//	var string=document.cookie;
//    var arr=string.split('; '); 
//    for(var i=0;i<arr.length;i++){
//        var arr2= arr[i].split('='); 
//        if(arr2[0]== key){           
//        	return unescape(arr2[1]);   
//        }   
//   	}       
//    return " ";
//}
//function delCookie(key){   
//	setCookie(key, "", -1);
//}