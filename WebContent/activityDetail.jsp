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
		<meta http-equiv="description" content="Mini协会详情">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/activityDetail.js"></script>
		<script type="text/javascript">
			$(function() {
				var id = <%=request.getParameter("id")%>;
				if(id != null) {
					load(id);
				}
			});
		</script>
		<style type="text/css">
			/*****协会活动详细信息******/
			#activity_detail_panel .panel-footer button {
				display: none;
			}
			#activity_detail_panel .panel-body .input-group {
				margin-bottom: 10px;
				width: 100%;
			}
			/****协会活动参加者****/
			#activity_attendee_panel .panel-body button {
				font-weight: normal;
				margin-right: 5px;
				margin-bottom: 5px;
				display: inline-block;
			}
			/****活动报名和显示用户详细信息的对话框*****/
			#activity_property_dialog .modal-body .input-group, 
				#activity_attendee_detail_dialog .modal-body .input-group {
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
			<!-- 大型活动详细信息 -->
			<div id="activity_detail_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<span class="activity_name_span"></span>~基本信息
					</div>
				</div>
				<div class="panel-body">
					<form>
						<div class="input-group">
							<span class="input-group-addon">活动名称</span>
							<input type="text" class="form-control" name="name" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">活动说明</span>
							<textarea class="form-control" name="description" readonly="readonly"></textarea>
						</div>
						<div class="input-group">
							<span class="input-group-addon">发&nbsp;&nbsp;起&nbsp;&nbsp;人</span>
							<input type="text" class="form-control" name="initiator" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">报名截止</span>
							<input type="text" class="form-control" name="applyDeadline" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">开始日期</span>
							<input type="text" class="form-control" name="startDate" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">结束日期</span>
							<input type="text" class="form-control" name="endDate" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">人数上限</span>
							<input type="text" class="form-control" name="max" readonly="readonly">
						</div>
						<div class="input-group">
							<span class="input-group-addon">报名人数</span>
							<input type="text" class="form-control" name="size" readonly="readonly">
						</div>
					</form>
				</div>
				<div class="panel-footer">
					<button id="apply" class="btn btn-success">
						<span class="glyphicon glyphicon-user"></span> 报名
					</button>
					<button id="quit" class="btn btn-danger" onclick="quit()">
						<span class="glyphicon glyphicon-trash"></span> 退出
					</button>
				</div>
			</div>
			<!-- 活动报名人员 -->
			<div id="activity_attendee_panel" class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title">
						<span class="activity_name_span"></span>~报名/参加人员
						<span class="badge"></span>
					</div>
				</div>
				<div class="panel-body">
				</div>
			</div>
		</div>
		<!-- 大型活动报名的对话框，显示自定义属性 -->
		<div id="activity_property_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							<span class="activity_name_span"></span>~活动报名
						</h4>
					</div>
					<div class="modal-body">
						<form>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="apply()">
								<span class="glyphicon glyphicon-ok"></span> 确定
							</button>
							<button type="button" class="btn btn-default" onclick="$('#activity_property_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 查看参加活动的用户详细信息的对话框 -->
		<div id="activity_attendee_detail_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							活动参加者详细信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
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
							<div class="input-group">
								<span class="input-group-addon">报名日期</span>
								<input type="text" class="form-control property_input" readonly="readonly">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button id="update" type="button" class="btn btn-success" onclick="updateApplyInfo()">
							<span class="glyphicon glyphicon-circle-arrow-up"></span> 提交
						</button>
						<button type="button" class="btn btn-danger" onclick="$('#activity_attendee_detail_dialog').modal('hide')">
							<span class="glyphicon glyphicon-remove"></span> 关闭
						</button>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
