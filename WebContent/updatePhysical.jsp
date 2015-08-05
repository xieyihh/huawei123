<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>



<!DOCTYPE HTML>
<html  >
	<head>
		
		<title>Mini~体检中心</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini个人中心">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
		 $(function(){
			 $.ajax({ 
					type: "get", //使用get方法访问后台 
					dataType: "json", //返回json格式的数据 
					url: "geteditphysicalinit.action", //要访问的后台地址 
					data: { }, //要发送的数据 
					success: function(msg) {//msg为返回的数据，在这里做数据绑定 
						var initdata="";
						$.each(msg.context.physicalexamForm, function(i, item) {
							if(i===2){
								$('#tips').text(item.physicalplan);
							}
						});
					}, 
					error: function() { 
						alert("加载数据失败"); 
					}  
				}); 
		 });
		</script>
		<style type="text/css">
			.panel .panel-heading .panel-title .glyphicon {
				float: right;
			}
			/****用户基本信息*****/
			#user_info_panel .panel-body .input-group {
				margin-bottom: 10px;
				width: 100%;
			}
			/*****修改用户密码的对话框******/
			#update_password_dialog .modal-body .input-group {
				margin-bottom: 10px;
			}
			/********显示管理员详细信息的对话框*********/
			#administrator_detail_dialog .modal-body .input-group {
				margin-bottom: 10px;
			}
		</style>
	</head>
  
	<body>
		<div class="title_div">
			<img alt="logo" src="img/logo2.png">
			<div class="right_title_div">
				<span class="right_title_span">
					<span class="user_name">${sessionScope.user.name }</span>
					<a href="userAction!logout.action">[退出]</a>
				</span>
			</div>
		</div>
		<div class="main_content">
			<!-- 个人基本信息 -->
			<div id="user_info_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						修改体检时间
						
					</div>
				</div>
				
				<div class="panel-body">
					
						<div class="input-group">
							<span class="input-group-addon name" id="tips"></span>
							
						</div>
						
					
				</div>
				
			</div>
		</div>
	</body>
</html>
