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
		<title>Mini~系统管理</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini系统管理">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<c:if test="${sessionScope.user.level != 2 }">
			<script type="text/javascript">
				location.href = "login.jsp";
			</script>
		</c:if>
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/admin.js"></script>
		<style type="text/css">
			.panel .panel-heading .panel-title .glyphicon {
				float: right;
			}
			/******设置模块管理员******/
			#administrator_panel {
				display: none;
			}
			#administrator_panel .panel-body .administrator_panel {
				margin-bottom: 5px;
			}
			#administrator_panel .panel-body .administrator_panel .panel-heading {
				padding: 5px;
			}
			#administrator_panel .panel-body .administrator_panel .panel-heading .panel-title {
				font-size: 18px;
			}
			#administrator_panel .panel-body .administrator_panel .panel-heading:hover {
				cursor: pointer;
			}
			#administrator_panel .panel-body .administrator_panel .panel-body, 
				#administrator_panel .panel-body .administrator_panel .panel-footer {
				display: none;
			}
			#administrator_panel .panel-body .administrator_panel .panel-body button {
				font-size: 16px;
				font-weight: normal;
				margin-right: 5px;
				margin-bottom: 5px;
				display: inline-block;
			}
			/******添加模块管理员的对话框******/
			#add_administrator_dialog .modal-body #admin_user_list {
				margin-top: 10px;
				margin-bottom: 5px;
			}
			#add_administrator_dialog .modal-body #admin_user_list .list-group-item {
				font-size: 16px;
				background-color: #59C129;
				color: white;
			}
			#add_administrator_dialog .modal-body #admin_user_list .list-group-item:hover {
				background-color: #39A337;
			}
			#add_administrator_dialog .modal-body #load_more_button {
				width: 100%;
				display: none;
			}
			/******用户管理******/
			#user_panel .panel-body #user_list {
				margin-top: 10px;
				margin-bottom: 5px;
			}
			#user_panel .panel-body #user_list .list-group-item {
				font-size: 16px;
				background-color: #59C129;
				color: white;
			}
			#user_panel .panel-body #user_list .list-group-item h4 {
				color: white;
			}
			#user_panel .panel-body #user_list .list-group-item p {
				font-size: 14px;
				margin-bottom: 0;
			}
			#user_panel .panel-body #user_list .list-group-item:hover {
				background-color: #39A337;
			}
			#user_panel .panel-body #load_more_button {
				width: 100%;
				display: none;
			}
			/****反馈信息****/
			#feedback_panel .panel-body #feedback_list {
				margin-top: 10px;
				margin-bottom: 5px;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item {
				font-size: 16px;
				background-color: #59C129;
				color: white;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item h4 {
				color: white;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item p {
				font-size: 14px;
				margin-bottom: 0;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item .unread {
				background-color: #d9534f;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item .read {
				background-color: #337ab7;
			}
			#feedback_panel .panel-body #feedback_list .list-group-item:hover {
				background-color: #39A337;
			}
			#feedback_panel .panel-body #load_more_button {
				width: 100%;
				display: none;
			}
			/****显示管理员、用户详细信息的对话框****/
			#administrator_detail_dialog .modal-body .input-group,
			#user_detail_dialog .modal-body .input-group,
			#feedback_detail_dialog .modal-body .input-group {
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
			<!-- 设置模块管理员~只有超级管理员有此权限 -->
			<div id="administrator_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						设置模块管理员
						<span class="glyphicon glyphicon-chevron-up"></span>
					</div>
				</div>
				<div class="panel-body">
					<!-- 协会管理员列表 -->
					<div id="association_administrator_panel" class="administrator_panel panel panel-primary">
						<div class="panel-heading">
							<div class="panel-title">
								协会管理员
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
						<div class="panel-body" module="association">
						</div>
						<div class="panel-footer right">
							<button class="btn btn-success" onclick="openAddAdminDialog('association')">
								<span class="glyphicon glyphicon-plus"></span> 添加管理员
							</button>
						</div>
					</div>
					<!-- 大型活动管理员列表 -->
					<div id="activity_administrator_panel" class="administrator_panel panel panel-primary">
						<div class="panel-heading">
							<div class="panel-title">
								大型活动管理员
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
						<div class="panel-body" module="activity">
						</div>
						<div class="panel-footer right">
							<button class="btn btn-success" onclick="openAddAdminDialog('activity')">
								<span class="glyphicon glyphicon-plus"></span> 添加管理员
							</button>
						</div>
					</div>
					<!-- 班车管理员列表 -->
					<div id="bus_administrator_panel" class="administrator_panel panel panel-primary">
						<div class="panel-heading">
							<div class="panel-title">
								班车管理员
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
						<div class="panel-body" module="bus">
						</div>
						<div class="panel-footer right">
							<button class="btn btn-success" onclick="openAddAdminDialog('bus')">
								<span class="glyphicon glyphicon-plus"></span> 添加管理员
							</button>
						</div>
					</div>
					<!-- 打折信息管理员列表 -->
					<div id="discount_administrator_panel" class="administrator_panel panel panel-primary">
						<div class="panel-heading">
							<div class="panel-title">
								打折信息管理员
								<span class="glyphicon glyphicon-chevron-down"></span>
							</div>
						</div>
						<div class="panel-body" module="discount">
						</div>
						<div class="panel-footer right">
							<button class="btn btn-success" onclick="openAddAdminDialog('discount')">
								<span class="glyphicon glyphicon-plus"></span> 添加管理员
							</button>
						</div>
					</div>
				</div>
			</div><!-- End of administrator_panel body -->
		</div>
		<!-- 用户管理~查看/删除用户 -->
		<div id="user_panel" class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					用户管理
					<span class="glyphicon glyphicon-chevron-up"></span>
				</div>
			</div>
			<div class="panel-body">
				<div class="input-group">
					<input type="text" class="form-control" name="keyword" placeholder="请输入姓名或者工号">
					<div class="input-group-btn">
						<button class="btn btn-default" onclick="user_pageNum=1;searchUser()">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</div>
				</div>
				<!-- 搜索到的用户列表 -->
				<div id="user_list" class="list-group">
				</div>
				<button id="load_more_button" class="btn btn-default" onclick="++user_pageNum;searchUser()">
						<span class="glyphicon glyphicon-plus"></span> 加载更多
					</button>
				<!-- 提示信息 -->
				<div class="msg_div">
					没有找到对应的用户信息
				</div>
			</div>
			<div class="panel-footer">
				<a class="btn btn-success" href="userAction!exportUser.action">
					<span class="glyphicon glyphicon-download"></span> 导出用户
				</a>
			</div>
		</div>
		<!-- 显示反馈信息列表 -->
		<div id="feedback_panel" class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					反馈信息
					<span class="glyphicon glyphicon-chevron-up"></span>
				</div>
			</div>
			<div class="panel-body">
				<div class="input-group">
					<input type="text" class="form-control" name="keyword" placeholder="请输入姓名或者工号">
					<div class="input-group-btn">
						<button class="btn btn-default" onclick="feedback_pageNum=1;searchFeedback()">
							<span class="glyphicon glyphicon-search"></span>
						</button>
					</div>
				</div>
				<!-- 搜索到的用户列表 -->
				<div id="feedback_list" class="list-group">
				</div>
				<button id="load_more_button" class="btn btn-default" onclick="++feedback_pageNum;searchFeedback()">
						<span class="glyphicon glyphicon-plus"></span> 加载更多
					</button>
				<!-- 提示信息 -->
				<div class="msg_div">
					没有找到对应的反馈信息
				</div>
			</div>
		</div>
		<!-- 查看管理员详细信息的对话框 -->
		<div id="administrator_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							管理员详细信息
						</h4>
						<div class="right">
							<button id="cancelAdmin" class="btn btn-danger" onclick="cancelAdmin()">取消管理员</button>
						</div>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group" style="display: none;">
								<span class="input-group-addon">ID</span>
								<input type="text" class="form-control" name="id" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">姓名</span>
								<input type="text" class="form-control" name="name" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">微信</span>
								<input type="text" class="form-control" name="account" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">工号</span>
								<input type="number" class="form-control" name="number" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">电话</span>
								<input type="number" class="form-control" name="phone" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">备注</span>
								<input type="text" class="form-control" name="remark" readonly="readonly">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" onclick="$('#administrator_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 添加管理员的对话框 -->
		<div id="add_administrator_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							添加<span class="module_name"></span>管理员
						</h4>
					</div>
					<div class="modal-body">
						<div class="input-group">
							<input type="text" class="form-control" name="keyword" placeholder="请输入姓名或者工号">
							<div class="input-group-btn">
								<button class="btn btn-default" onclick="admin_user_pageNum=1;searchAdminUser()">
									<span class="glyphicon glyphicon-search"></span>
								</button>
							</div>
						</div>
						<!-- 搜索到的用户列表 -->
						<div id="admin_user_list" class="list-group">
						</div>
						<button id="load_more_button" class="btn btn-default" onclick="++admin_user_pageNum;searchAdminUser()">
							<span class="glyphicon glyphicon-plus"></span> 加载更多
						</button>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-danger" onclick="$('#add_administrator_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span>关闭
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看用户详细信息的对话框 -->
		<div id="user_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							协会成员详细信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group" style="display: none;">
								<span class="input-group-addon">ID</span>
								<input type="text" class="form-control" name="id" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">姓名</span>
								<input type="text" class="form-control" name="name" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">微信</span>
								<input type="text" class="form-control" name="account" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">工号</span>
								<input type="number" class="form-control" name="number" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">电话</span>
								<input type="number" class="form-control" name="phone" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">备注</span>
								<input type="text" class="form-control" name="remark" readonly="readonly">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button id="deleteUser" class="btn btn-success" onclick="deleteUser()">
							<span class="glyphicon glyphicon-trash"></span> 删除用户
						</button>
						<button type="button" class="btn btn-danger" onclick="$('#user_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看反馈信息详情的对话框 -->
		<div id="feedback_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							协会成员详细信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group" style="display: none;">
								<span class="input-group-addon">ID</span>
								<input type="text" class="form-control" name="id" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">姓名</span>
								<input type="text" class="form-control" name="name" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">工号</span>
								<input type="number" class="form-control" name="number" readonly="readonly">
							</div>
							<div class="input-group">
								<span class="input-group-addon">内容</span>
								<textarea rows="5" class="form-control" name="content" readonly="readonly"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button id="deleteFeedback" class="btn btn-success" onclick="deleteFeedback()">
							<span class="glyphicon glyphicon-trash"></span> 删除
						</button>
						<button type="button" class="btn btn-danger" onclick="$('#feedback_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
