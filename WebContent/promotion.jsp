<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >
<html  >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~打折信息区</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini打折信息区">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/lib/ajaxfileupload.js"></script>
		<script type="text/javascript" src="js/promotion.js"></script>
		<style type="text/css">
			/******打折信息列表*******/
			#search_div {
				margin: 5px 0;
			}
			#search_div .input-group-addon,
			#search_div input, 
			#search_div .input-group-btn button {
				font-size: 16px;
			}
			#search_div input {
				height: 36px;
			}
			#search_div,
				#promotion_list_div .promotion {
				padding: 0 5%;
			}
			#promotion_list_div .promotion {
				border-top: 1px solid #ddd;
				padding-bottom: 5px;
			}
			#promotion_list_div .promotion:last-child {
				border-bottom: 1px solid #ddd;
			}
			#promotion_list_div .promotion a {
				width: 100%;
				color: black;
				margin-bottom: 5px;
			}
			#promotion_list_div .promotion a:hover {
				text-decoration: none;
			}
			#promotion_list_div .promotion .promotion_info {
				font-size: 14px;
				color: #999;
			}
			#promotion_list_div .promotion .promotion_info .float_right {
				float: right;
			}
			#promotion_list_div .promotion .promotion_info .publisher, 
				#promotion_list_div .promotion .promotion_info .date {
				margin-right: 5px;
			}
			#load_more_button {
				width: 100%;
				display: none;
			}
			/******发布打折信息的对话框******/
			#publish_promotion_dialog .modal-body .input-group:FIRST-CHILD {
				margin-bottom: 10px;
			}
			#publish_promotion_dialog .modal-body #add_photo_div {
				padding: 10px;
				font-size: 16px;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table {
				width: 100%;
				margin-top: 5px;
				margin-bottom: 0px;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table tr td {
				text-align: center;
				vertical-align: middle;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table .photo_chooser_td {
				width: 60%;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table .photo_chooser_td .fileinput-button {
				position: relative;
				overflow: hidden;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table .photo_chooser_td .fileinput-button input {
				position: absolute;
				top: 0;
				right: 0;
				margin: 0;
				opacity: 0;
				filter: alpha(opacity = 0);
				transform: translate(-300px, 0) scale(4);
				font-size: 23px;
				direction: ltr;
				cursor: pointer;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table .photo_chooser_td img {
				max-width: 70%;
				max-height: 100px;
			}
			#publish_promotion_dialog .modal-body #photo_chooser_table .delete_td {
				width: 10%;
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
		<!-- 打折信息搜索框 -->
		<div id="search_div" class="input-group">
			<div class="input-group-btn">
				<button class="btn btn-default" data-toggle="modal" data-target="#publish_promotion_dialog">
					<span class="glyphicon glyphicon-plus"></span> 发布
				</button>
			</div>
			<input type="text" class="form-control" name="keyword" placeholder="输入关键字查询">
			<div class="input-group-btn">
				<button class="btn btn-default" onclick="promotion_pageNum=1;load()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>
		</div>
		<!-- 打折信息列表 -->
		<div id="promotion_list_div">
		</div>
		<button id="load_more_button" class="btn btn-default" onclick="++promotion_pageNum;load()">
			<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
		</button>
		<!-- 提示信息 -->
		<div class="msg_div">
			没有找到对应的打折信息
		</div>
		<!-- 发布打折信息的对话框 -->
		<div id="publish_promotion_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							发布打折信息
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">标题</span>
								<input type="text" class="form-control" name="title" placeholder="输入打折标题">
							</div>
							<div class="input-group">
								<span class="input-group-addon">详情</span>
								<textarea rows="5" class="form-control" name="detail" placeholder="输入打折详情"></textarea>
							</div>
						</form>
						<!-- 添加照片 -->
						<div id="add_photo_div">
							无图无真相，
							<button type="button" class="btn btn-success" onclick="addPhotoChooser()">
								<span class="glyphicon glyphicon-plus"></span> <span class="button_text">上图</span>
							</button>
						</div>
						<table id="photo_chooser_table" class="table table-bordered">
							<tbody>
								
							</tbody>
						</table>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="publish()">
								<span class="glyphicon glyphicon-ok"></span> 提交
							</button>
							<button type="button" class="btn btn-default" onclick="$('#publish_promotion_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
