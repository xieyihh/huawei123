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
		<title>Mini~协会活动</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini协会活动页面">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/association.js"></script>
		<style type="text/css">
			#association_panel .panel-heading .panel-title button {
				margin-left: 10px;
			}
			#add_association_dialog .modal-body .input-group:first-child {
				margin-bottom: 20px;
			}
			#add_association_dialog .modal-body .input-group textarea {
				height: 100px;
			}
			#search_div {
				margin-bottom: 5px;
			}
			#load_more_button {
				width: 100%;
				display: none;
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
			<div id="association_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						协会列表
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<!-- 协会搜索框 -->
					<div id="search_div" class="input-group">
						<input type="text" class="form-control" name="keyword" placeholder="输入关键字查询">
						<div class="input-group-btn">
							<button class="btn btn-default" onclick="association_pageNum=1;loadAssociation()">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
					</div>
					<div class="association_list list-group">
					</div>
					<button id="load_more_button" class="btn btn-default" onclick="++association_pageNum;loadAssociation()">
						<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
					</button>
					<!-- 提示信息 -->
					<div class="msg_div">
						没有找到对应的协会
					</div>
				</div>
				<div class="panel-footer">
					<button class="btn btn-success" data-toggle="modal" data-target="#add_association_dialog">
						<span class="glyphicon glyphicon-plus"></span> 创建协会
					</button>
				</div>
			</div>
		</div>
		<!-- 创建协会的对话框 -->
		<div id="add_association_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">添加新的协会</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">名称</span>
								<input type="text" class="form-control" name="name" placeholder="请输入协会名称" autofocus="autofocus">
							</div>
							<div class="input-group">
								<span class="input-group-addon">简介</span>
								<textarea class="form-control" name="description" placeholder="请输入协会简介"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="addAssociation()">
								<span class="glyphicon glyphicon-ok"></span>确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#add_association_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span>取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
