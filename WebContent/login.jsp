<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Mini~用户登录</title>
		<meta http-equiv="description" content="Mini用户登录页面">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<link rel="ICON NAME" href="huawei.ico"/>
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/body.js?1"></script>
		<script type="text/javascript" src="js/login.js?3"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/body.css?2">
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
			}
			.main_content .submit_div {
				text-align: center;
				overflow: hidden;
				
			}
			.main_content .submit_div .btn {
				width: 80%;
				margin:0 auto;
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
			<form action="userAction!login.action" method="post" onsubmit="return check()">
				<div class="input-group component">
					<span class="input-group-addon">账号</span>
					<input type="text" class="form-control" name="account" placeholder="请输入8位工号/帐号/手机" autofocus="autofocus" required="required">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">密码</span>
					<input type="password" class="form-control" name="password" placeholder="请输入您的密码" required="required">
				</div>
				<div class="input-group component">
					<label class="checkbox-inline">
	 					 <input type="checkbox" id="RemmeberPassword"  style="height:20px;width:20px;margin-right:5px;margin-top: 0px;"> 记住密码
					</label>
				</div>
				<div class="submit_div component">
					<button id="login_button" type="submit" class="btn btn-success">登&nbsp;&nbsp;录</button>
				</div>
				<div class="alert alert-success alert-dismissible fade in" role="alert">
					<button type="button" class="close" data-dismiss="alert">
						<span aria-hidden="true">×</span>
						<span class="sr-only">Close</span>
					</button>
					<span class="message">${message }</span>
				</div>
				<div class="footer">
					<a id="reset_button" class="btn btn-success" href="forgetPassword.jsp">忘记密码/用户名?</a>
					<a id="register_button" class="btn btn-success" href="register.jsp">注册</a>
				</div>
			</form>
		</div>
	</body>
</html>
