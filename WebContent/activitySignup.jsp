<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String activity_Name = new String(request.getParameter("activityName").getBytes("ISO-8859-1"),"utf-8");
System.out.println(activity_Name);
%>

<!DOCTYPE HTML>
<html >
	<head>
		
		<title>活动报名</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini个人中心">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
		var activityName='<%=activity_Name%>';
		
		//alert(activityName);
		</script>
		<script type="text/javascript" src="js/wchat/activitySignup.js?v1.1.3"></script>
		<style type="text/css">
			.panel .panel-heading .panel-title .glyphicon {
				float: right;
			}
			
			.panel .panel-heading .panel-title .tips {
				float: right;
				color:red;
				font-size:12px;
				margin-top:5px;
				margin-right:10px;
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
			.file {
    position: relative;
	background: #D0EEFF;
	border: 1px solid #99D3F5;
	border-radius: 4px;
	padding: 0px 12px;
	overflow: hidden;
	color: #1E88C7;
	text-decoration: none;
	text-indent: 0;
	line-height: 30px;
	font-size: 13px;
	float:right;
	display: block;
	width: 70px;
	}
.file input {
    position: absolute;
	font-size: 20px;
	width: 70px;
	right: 0;
	top: 0;
	opacity: 0;
}
.file:hover {
    background: #AADFFD;
    border-color: #78C3F3;
    color: #004974;
    text-decoration: none;
}
.fileinfo{
float:left;
line-height:30px;}
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
					活动报名
			
					</div>
				</div>
				<form id="activity" action="saveActivitySignup.action" method="post" enctype="multipart/form-data" >
					<div class="panel-body">
						
							
						
					</div>
					<div class="panel-footer">
						<button id="submitData" class="btn btn-success">
							<span class="glyphicon glyphicon-upload"></span> 报名
						</button>
						<button id="resetData" class="btn btn-danger" >
							<span class="glyphicon glyphicon-pencil"></span>重置
						</button>
					</div>
				</form>
			</div>
		</div>
	</body>
</html>
