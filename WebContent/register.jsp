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
		<title>Mini~新用户注册</title>
		<meta http-equiv="description" content="Mini新用户注册页面">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta name="author" content="雷晓冰">
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/body.js"></script>
		<script type="text/javascript" src="js/register.js?v1.1"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/body.css">
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
			
			.main_content .alert {
				margin-top: 30px;
				display: none;
			}
			.main_content input[type="button"] {
				float:left;
				
			}
			.main_content  .valicode {
				float:right;
				width:50%;
			}
			.check{
				margin-top:5px;
				margin-left:15px;
				width:20px;
				border:0;
				
			}
			.smallcheck{
				height:10px;
				width:10px;
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
			
		</style>
	</head>
	<body>
		<!-- 网页背景图 -->
		<img class="background_img" src="img/background.png">
		
		<div class="main_content">
			<div class="logo_div">
				<img alt="logo" src="img/logo.png">
			</div>
			<form id="register_form" action="userAction!register.action" method="post" onsubmit="return check()">
				<div class="input-group component" >
					<span class="input-group-addon">姓名</span>
					<input type="text" class="form-control" name="name" placeholder="请输入您的真实姓名" autofocus="autofocus"style="width:80%;">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">账号</span>
					<input type="text" class="form-control" name="account" placeholder="请输入您的微信号"style="width:80%;">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">密码</span>
					<input type="password" class="form-control" name="password" placeholder="请输入您的密码"style="width:80%;float:left" id="password" onfocus="showDemand()"onblur="showOK1()">
					
				</div>
				<div id="demand" style="float:right;"></div>
				<div class="input-group component">
					<span class="input-group-addon">确认</span>
					
					<input type="password" class="form-control" placeholder="请确认您的密码" style="width:80%;border-bottom-right-radius:4px;border-top-right-radius: 4px;"name="confirmPassword"  id="confirmPassword" onfocus="showNO()" onblur="showOK()">
					<img name="checkPassword2" id="checkPassword2"  class="check img-circle" />
				</div>
				<div class="input-group component">
					<span class="input-group-addon">工号</span>
					<input type="number" class="form-control" name="number" placeholder="请输入您的8位工号" style="width:80%;">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">电话</span>
					<input type="number" class="form-control" name="phone" placeholder="请输入您的电话号码" style="width:80%;">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">备注</span>
					<input type="text" class="form-control" name="remark" placeholder="备注信息，可选" style="width:80%;">
				</div>
				
				<div class="submit_div component">
					<input type="submit" class="btn btn-success" value="提&nbsp;&nbsp;交" style="width:80%;">
				</div>
			</form>
			
			
			
		</div>
	</body>
</html>
