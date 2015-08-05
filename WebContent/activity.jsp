<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~大型活动</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini大型活动">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/activity.js"></script>
		<style type="text/css">
			/****大型活动列表******/
			#activity_panel .panel-heading .panel-title button {
				margin-left: 10px;
				display: none;
			}
			#activity_panel .panel-body .list-group .list-group-item .right {
				font-size: 16px;
				font-weight: normal;
			}
			#search_div {
				margin-bottom: 5px;
			}
			#load_more_button {
				width: 100%;
				display: none;
			}
			/****发起活动的对话框*****/
			#initiate_activity_dialog .modal-body .input-group {
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
			<!-- 大型活动列表 -->
			<div id="activity_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						大型活动
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<!-- 协会搜索框 -->
					<div id="search_div" class="input-group">
						<input type="text" class="form-control" name="keyword" placeholder="输入关键字查询">
						<div class="input-group-btn">
							<button class="btn btn-default" onclick="activity_pageNum=1;load()">
								<span class="glyphicon glyphicon-search"></span>
							</button>
						</div>
					</div>
					<div id="activity_list" class="list-group">
					</div>
					<button id="load_more_button" class="btn btn-default" onclick="++activity_pageNum;load()">
						<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
					</button>
					<!-- 提示信息 -->
					<div class="msg_div">
						没有找到对应的大型活动
					</div>
				</div>
				<div class="panel-footer">
					<button id="initiateActivity" class="btn btn-success" data-toggle="modal" data-target="#initiate_activity_dialog">
						<span class="glyphicon glyphicon-plus"></span> 发起活动
					</button>
				</div>
			</div>
		</div>
		<!-- 发起大型活动的对话框 -->
		<div id="initiate_activity_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">发起大型活动</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">活动名称</span>
								<input type="text" class="form-control" name="name" placeholder="请输入活动名称" autofocus="autofocus" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">活动说明</span>
								<textarea class="form-control" name="description" placeholder="请输入活动说明" required="required"></textarea>
							</div>
							<div class="input-group">
								<span class="input-group-addon">报名截止</span>
								<input type="date" class="form-control" name="applyDeadline" placeholder="请选择报名截止日期" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">开始日期</span>
								<input type="date" class="form-control" name="startDate" placeholder="请选择活动开始日期" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">结束日期</span>
								<input type="date" class="form-control" name="endDate" placeholder="请选择活动结束日期" required="required">
							</div>
							<div class="input-group">
								<span class="input-group-addon">自定义属性</span>
								<textarea class="form-control" name="property" placeholder="多个属性用&分隔"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="initiateActivity()">
								<span class="glyphicon glyphicon-ok"></span>确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#initiate_activity_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span>取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
