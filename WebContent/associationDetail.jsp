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
		<title>Mini~协会详情</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini协会详情">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/associationDetail.js"></script>
		<script type="text/javascript">
			$(function() {
				var id = <%=request.getParameter("id")%>;
				if(id != null) {
					load(id);
				}
			});
			
		</script>
		<style type="text/css">
			/****协会详细信息****/
			#association_detail_panel .panel-footer button, 
				#association_activity_panel .panel-footer button {
				display: none;
			}
			#association_detail_panel .panel-body .input-group {
				margin-bottom: 10px;
				width: 100%;
			}
			#association_detail_panel .panel-body .input-group span {
				width: 98px;
				text-align: right;
			}
			#association_detail_panel .panel-body .input-group textarea {
				height: 100px;
			}
			/****协会成员****/
			#association_member_panel .panel-body button, #association_member_panel .panel-body span {
				font-size: 16px;
				font-weight: normal;
				margin-right: 5px;
				margin-bottom: 5px;
				display: inline-block;
			}
			/*****协会活动*****/
			#association_activity_panel .panel-body .list-group .list-group-item .right {
				font-size: 16px;
				font-weight: normal;
			}
			#association_activity_panel .panel-body #load_more_button {
				width: 100%;
				display: none;
			}
			/****发起活动和查看协会成员详细信息的对话框*****/
			#initiate_associationActivity_dialog .modal-body .input-group, 
				#association_member_detail_dialog .modal-body .input-group {
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
			<!-- 协会基本信息 -->
			<div id="association_detail_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<span class="association_name_span"></span>~基本信息
					</div>
				</div>
				<div class="panel-body">
					<form>
						<div class="input-group">
							<span class="input-group-addon">名称</span>
							<input type="text" class="form-control" name="name" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">简介</span>
							<textarea class="form-control" name="description" readonly="readonly"></textarea>
						</div>
						<div class="input-group">
							<span class="input-group-addon">创建时间</span>
							<input type="text" class="form-control" name="date" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">创建人</span>
							<input type="text" class="form-control" name="founder" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">人数</span>
							<input type="text" class="form-control" name="size" readonly="readonly">
						</div>
					</form>
				</div>
				<div class="panel-footer">
					<button id="authorize_button" class="btn btn-success" onclick="authorize()">
						<span class="glyphicon glyphicon-user"></span> 批准
					</button>
					<button id="delete_button" type="button" class="btn btn-primary" onclick="deleteAssociation()">
						<span class="glyphicon glyphicon-trash"></span> 删除
					</button>
					<button id="join" class="btn btn-success" onclick="join()">
						<span class="glyphicon glyphicon-user"></span> 加入
					</button>
					<button id="quit" class="btn btn-danger" onclick="quit()">
						<span class="glyphicon glyphicon-remove"></span> 退出
					</button>
				</div>
			</div>
			<!-- 协会成员 -->
			<div id="association_member_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<span class="association_name_span"></span>~协会成员
						<span class="badge"></span>
					</div>
					<div class="right">
						<span class="label label-primary">管理员</span>
						<span class="label label-success">普通成员</span>
					</div>
				</div>
				<div class="panel-body">
				</div>
			</div>
			<!-- 协会活动 -->
			<div id="association_activity_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<span class="association_name_span"></span>~协会活动
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
					<div id="associationActivity_list" class="list-group">
					</div>
					<button id="load_more_button" class="btn btn-default" onclick="++associationActivity_pageNum;loadActivities()">
						<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
					</button>
				</div>
				<div class="panel-footer">
					<button id="initiateActivity" class="btn btn-success" data-toggle="modal" data-target="#initiate_associationActivity_dialog">
						<span class="glyphicon glyphicon-plus"></span> 发起活动
					</button>
				</div>
			</div>
		</div>
		<!-- 发起协会活动的对话框 -->
		<div id="initiate_associationActivity_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">发起协会活动</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">活动名称</span>
								<input type="text" class="form-control" name="name" placeholder="请输入活动名称" autofocus="autofocus">
							</div>
							<div class="input-group">
								<span class="input-group-addon">活动说明</span>
								<textarea class="form-control" name="description" placeholder="请输入活动说明"></textarea>
							</div>
							<div class="input-group">
								<span class="input-group-addon">报名截止</span>
								<input type="date" class="form-control" name="applyDeadline" placeholder="请选择报名截止日期">
							</div>
							<div class="input-group">
								<span class="input-group-addon">开始日期</span>
								<input type="date" class="form-control" name="startDate" placeholder="请选择活动开始日期">
							</div>
							<div class="input-group">
								<span class="input-group-addon">结束日期</span>
								<input type="date" class="form-control" name="endDate" placeholder="请选择活动结束日期">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="initiateActivity()">
								<span class="glyphicon glyphicon-ok"></span>确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#initiate_associationActivity_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span>取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看协会成员详细信息的对话框 -->
		<div id="association_member_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
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
						<button id="setAdmin" class="btn btn-success" onclick="setAdmin()">
							<span class="glyphicon glyphicon-user"></span> 设为管理员
						</button>
						<button id="cancelAdmin" class="btn btn-danger" onclick="cancelAdmin()">
							<span class="glyphicon glyphicon-trash"></span> 取消管理员
						</button>
						<button type="button" class="btn btn-danger" onclick="$('#association_member_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
