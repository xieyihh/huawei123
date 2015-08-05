<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html  >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~个人中心</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini个人中心">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/user.js"></script>
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
						基本信息
						<span class="glyphicon glyphicon-chevron-up"></span>
					</div>
				</div>
				<div class="panel-body">
					<form>
						<div class="input-group">
							<span class="input-group-addon">姓名</span>
							<input type="text" class="form-control" name="name" placeholder="请输入您的真实姓名" autofocus="autofocus" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon">账号</span>
							<input type="text" class="form-control" name="account" placeholder="请输入您的微信号" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon">工号</span>
							<input type="number" class="form-control" name="number" placeholder="请输入您的工号" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon">电话</span>
							<input type="number" class="form-control" name="phone" placeholder="请输入您的电话号码" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon">备注</span>
							<input type="text" class="form-control" name="remark" placeholder="备注信息">
						</div>
					</form>
				</div>
				<div class="panel-footer">
					<button id="save" class="btn btn-success" onclick="save()">
						<span class="glyphicon glyphicon-upload"></span> 保存
					</button>
					<button id="updatePassword" class="btn btn-danger" data-toggle="modal" data-target="#update_password_dialog">
						<span class="glyphicon glyphicon-pencil"></span> 修改密码
					</button>
				</div>
			</div>
			<!-- 参加的协会 -->
			<div id="user_association_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						参加的协会
						<span class="glyphicon glyphicon-chevron-up"></span>
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<div id="association_list" class="list-group">
					</div>
				</div>
			</div>
			<!-- 参加的协会活动 -->
			<div id="user_associationActivity_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						参加的协会活动
						<span class="glyphicon glyphicon-chevron-up"></span>
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<div id="associationActivity_list" class="list-group">
					</div>
				</div>
			</div>
			<!-- 参加的大型活动 -->
			<div id="user_activity_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						参加的大型活动
						<span class="glyphicon glyphicon-chevron-up"></span>
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<div id="activity_list" class="list-group">
					</div>
				</div>
			</div>
		</div>
		<!-- 修改密码的对话框 -->
		<div id="update_password_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">修改密码</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">旧密码</span>
								<input type="password" class="form-control" name="oldPassword" placeholder="请输入您的旧密码" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">新密码</span>
								<input type="password" class="form-control" name="newPassword" placeholder="请输入您的新密码" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">确&nbsp;&nbsp;&nbsp;&nbsp;认</span>
								<input type="password" class="form-control" name="confirmPassword" placeholder="请确认您的新密码" required="required">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="updatePassword()">
								<span class="glyphicon glyphicon-ok"></span> 提交
							</button>
							<button type="button" class="btn btn-default" onclick="$('#update_password_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
