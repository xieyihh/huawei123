<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html manifest="mini1.appcache">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>忘记密码</title>
		<meta http-equiv="description" content="忘记密码">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta name="author" content="雷晓冰">
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/body.js"></script>
		<script type="text/javascript" src="js/forgetpassword.js?v1.4.1"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/body.css?1">
		<style type="text/css">
		
			.main_content .logo_div {
				padding: 10px 0 0px 0;
				text-align: center;
			}
			.main_content .logo_div img {
				max-width: 20%;
			}
			.main_content .component {
				margin-top: 20px;
				width:100%;
				max-width:800px;
			}
			.main_content .submit_div {
				text-align: center;
				overflow: hidden;
				
			}
			.main_content .submit_div .btn {
				width: 80%;
				margin:0 auto;
			}
			
			
			.main_content input[type="button"] {
				float:left;
				
			}
			.main_content  .valicode {
				float:right;
				width:50%;
				border-bottom-left-radius:4px;
				border-top-left-radius: 4px;
			}
			@media (max-width: 800px){
				.modal-dialog {
				position: absolute;
				width: 90%;
				left:5%;
				margin:0 auto;
				bottom: 10px;
				}
			}	
				.main_content .footer #register_button {
			float:right	;
			margin-right:0px;
			}
			.main_content .footer #reset_button {
				float:left;
			}
			.main_content .footer .btn {
				border:0px;
				background-color:transparent;
				font-size:13px;
				}
			.main_content .alert {
				margin-top: 30px;
				display: none;
			}
			.main_content .footer{
			position:fixed; 
			bottom:0;
			height:30px;
			margin-left:0px;
			max-width:800px;
			width:100%;
			}	
		</style>
		
	</head>
	<body>
		<!-- 网页背景图 -->
		<img class="background_img" src="img/background.png">
		
		<div class="main_content">
			<div class="logo_div">
				<img alt="logo" src="img/logo.png">
			</div>
			<form  method="post" >
				<div class="input-group component">
					<span class="input-group-addon">电话</span>
					<input type="text" class="form-control" name="phone" placeholder="请输入您的电话号码" autofocus="autofocus">
				</div>
				
				<div class="input-group component">
					<input type="button" class="btn-success btn" id="getMessageCode"name="getMessageCode" value="获取短信验证码">
					<input type="text" class="form-control valicode"style="border-bottom-left-radius:4px;
				border-top-left-radius: 4px;" name="valicode"id="valicode" placeholder="请输入验证码">
				</div>
				
				<div class="submit_div component">
					<button id="login_button" type="button" class="btn btn-success">确&nbsp;&nbsp;认</button>
				</div>
				
				<div class="footer" style="display:none">
					
					<a id="register_button" class="btn btn-success" href="feedback.jsp">问题反馈</a>
				</div>
				
			</form>
		</div>
	</body>
</html>
