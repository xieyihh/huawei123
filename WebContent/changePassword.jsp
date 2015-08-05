<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<% String account = request.getParameter("account"); %>

<!DOCTYPE html>
<html manifest="mini1.appcache">
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="format-detection" content="telephone=no">
		<title>修改密码</title>
		<meta http-equiv="description" content="修改密码">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta name="author" content="雷晓冰">
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/body.js"></script>
		<script type="text/javascript" src="js/changepassword.js?4"></script>
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
			<form  method="post" >
				<div class="input-group component">
					<span class="input-group-addon" >&nbsp;&nbsp;用户名&nbsp;&nbsp;</span>
					<input type="text" class="form-control" name="account" readonly="readonly" value="<%=account%>" id="account"style="width:80%;">
				</div>
				<div class="input-group component">
					<span class="input-group-addon">&nbsp;&nbsp;新密码&nbsp;&nbsp;</span>
					<input type="password" class="form-control" name="password" id="password"style="width:80%;float:left" onfocus="showDemand()"onblur="showOK1()">
					
				</div>
				<div id="demand" style="float:right"></div>
				<div class="input-group component">
					<span class="input-group-addon">确认密码</span>
					<input type="password" class="form-control"style="width:80%;border-bottom-right-radius:4px;border-top-right-radius: 4px;" name="confirmPassword"  id="confirmPassword" onfocus="showNO()" onblur="showOK()">
					<img name="checkPassword2" id="checkPassword2"  class="check img-circle" />
				</div>
				<div class="input-group component">
					<span class="input-group-addon">&nbsp;&nbsp;验证码&nbsp;&nbsp;</span>
					<input type="text" class="form-control" style="width:50%;border-bottom-right-radius:4px;border-top-right-radius: 4px;"name="rand" size="15">
					<img  name="randImage" id="randImage" alt="看不清？点击更换" style="width:100px ;height:32px; border:0px;margin-left:5px;border-radius:4px;" >
					
				</div>
				
				
				<div class="submit_div component">
					<button id="login_button" type="button" class="btn btn-success">确&nbsp;&nbsp;认</button>
				</div>
				
				
				
			</form>
		</div>
	</body>
</html>
