<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
 
<head>

<title>吾爱吾家管理后台</title>
<script src="../../bootstrap/jquery.min.js"></script>
<script type="text/javascript">
	$(function(){
		
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "backlogin.action", //要访问的后台地址 
			data: { }, //要发送的数据 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				//alert(msg.context.name);
				$('div#username').append(msg.context.name+"  欢迎登录!");
			}, 
			error: function() { 
				alert("登陆失败"); 
			} 
		}); 
		
	});
</script>
<link href="../../common/style.css" rel="stylesheet" type="text/css" />
</head>

<body id="top">
<div class="top_bg">

<div class="logo"></div>
<div class="top_line" id="username"><span class="right"><a href="userAction!logout.action" target="_parent">安全退出</a></span></div>
</div>

</body>
</html>
