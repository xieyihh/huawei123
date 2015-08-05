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
		<title>Mini~话题详情</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini话题详情">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/topicDetail.js"></script>
		<script type="text/javascript">
			$(function() {
				var id = <%=request.getParameter("id")%>;
				if(id != null) {
					load(id);
				}
			});
		</script>
		<style type="text/css">
			/*****话题详细信息******/
			#topic_detail_panel {
				margin-bottom: 0px;
			}
			#topic_detail_panel .panel-heading .topic_info {
				font-size: 14px;
				color: #999;
			}
			#topic_detail_panel .panel-heading .topic_info .float_right,
				#comment_list .float_right {
				float: right;
			}
			#topic_detail_panel .panel-heading .topic_info .float_right .favor_amount {
				margin-right: 10px;
			}
			#topic_detail_panel .panel-heading .topic_info .founder, 
				#topic_detail_panel .panel-heading .topic_info .time {
				margin-right: 5px;
			}
			#topic_detail_panel .panel-body {
				font-size: 16px;
			}
			#topic_detail_panel .panel-footer {
				text-align: right;
			}
			#topic_detail_panel .panel-footer #delete_button,
			#topic_detail_panel .panel-footer #favor_button {
				display: none;
			}
			/********评论列表********/
			#comment_list .comment {
				border-bottom: 1px dashed #CCC;
				padding: 15px;
			}
			#comment_list .comment .comment_info {
				font-size: 14px;
				color: #999;
				margin-bottom: 8px;
			}
			#comment_list .comment .comment_info .time {
				margin-right: 5px;
			}
			#comment_list .comment .comment_detail {
				font-size: 14px;
			}
			#load_more_button {
				width: 100%;
				display: none;
			}
			/****添加评论的对话框****/
			#add_comment_dialog .modal-body textarea {
				font-size: 16px;
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
		<div id="topic_detail_panel" class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					<span class="topic_title_span"></span>
				</div>
				<div class='topic_info'>
					<span class='float_right'>
						<span class='glyphicon glyphicon-heart-empty'></span> <span class='favor_amount'></span>
						<span class='glyphicon glyphicon-comment'></span> <span class='comment_amount'></span>
					</span>
					<span class='founder'></span>
					<span class='time'></span>
				</div>
			</div>
			<div class="panel-body">
			</div>
			<div class="panel-footer">
				<button id="delete_button" type="button" class="btn btn-danger" onclick="deleteTopic()">
					<span class="glyphicon glyphicon-trash"></span> 删除
				</button>
				<button id="favor_button" type="button" class="btn btn-primary" onclick="favor()">
					<span class="glyphicon glyphicon-heart-empty"></span> 赞
				</button>
				<button id="add_comment_button" type="button" class="btn btn-success" data-toggle="modal" data-target="#add_comment_dialog">
					<span class="glyphicon glyphicon-comment"></span> 回复
				</button>
			</div>
		</div>
		<!-- 评论列表 -->
		<div id="comment_list">
		</div>
		<button id="load_more_button" class="btn btn-default" onclick="++comment_pageNum;loadComment()">
			<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
		</button>
		<!-- 添加评论的对话框 -->
		<div id="add_comment_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-body">
						<textarea rows="4" class="form-control" name="detail" placeholder="请输入您的评论" autofocus="autofocus"></textarea>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="addComment()">
								<span class="glyphicon glyphicon-ok"></span> 提交
							</button>
							<button type="button" class="btn btn-default" onclick="$('#add_comment_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
