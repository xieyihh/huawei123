<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~体检中心</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录
		 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini个人中心">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script  src="js/wchat/addRelatives.js?v1.2.6"></script>
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
			.tips{
			line-height: 2em;
			white-space: normal;
			display: inline;
			}
			.smalltips{
			float:right;
			color:red;
			font-size:12px;}
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
						添加家属信息
						<span class="glyphicon glyphicon-chevron-up"></span>
						<p class="smalltips"></p>
					</div>
				</div>
				<!-- 添加家属信息 -->
				<div class="panel-body">
				
					<form>
						
						<div class="input-group">
							<span class="input-group-addon name">姓名</span>
							<input type="text" class="form-control" name="name" placeholder="请输入家属姓名"  required="required">						
						</div>
						<div class="input-group">
							<span class=" input-group-addon time">身份证号</span>
							<input type="text" class="form-control" name="identifyNumber" placeholder="请输入家属身份证号" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon name">关系</span>
							<input type="text" class="form-control" name="relationship" placeholder="请输入你与此家属的关系" required="required">
						</div>
						<div class="input-group">
							<span class="input-group-addon name">电话</span>
							<input type="text" class="form-control" name="phone" placeholder="请输入家属电话号码" required="required">
						</div>
					</form>
				</div>
				<div class="panel-footer">
					<button id="saveRelative" class="btn btn-success" >
						<span class="glyphicon glyphicon-save"></span> 保存
					</button>
					<button id="resetRelative" class="btn btn-danger reset" >
						<span class="glyphicon glyphicon-pencil"></span> 重置
					</button>
				</div>
			</div>
			<!-- 已有家属信息 -->
			<div id="user_association_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						已有家属信息
						<span class="glyphicon glyphicon-chevron-up"></span>
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<div id="association_list" class="list-group">
					
					</div>
				</div>
			</div>
			
		</div>
	</body>
</html>
