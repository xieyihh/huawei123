<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML >

<html  >
	<head>
		<base href="<%=basePath%>">
		<title>Mini~讨论交流区</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini讨论交流区">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/forum.js"></script>
		<style type="text/css">
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
				#topic_list_div .topic {
				padding: 0 5%;
			}
			#topic_list_div .topic {
				border-top: 1px solid #ddd;
				padding-bottom: 5px;
			}
			#topic_list_div .topic:last-child {
				border-bottom: 1px solid #ddd;
			}
			#topic_list_div .topic a {
				width: 100%;
				color: black;
				margin-bottom: 5px;
			}
			#topic_list_div .topic a:hover {
				text-decoration: none;
			}
			#topic_list_div .topic .topic_info {
				font-size: 14px;
				color: #999;
			}
			#topic_list_div .topic .topic_info .float_right {
				float: right;
			}
			#topic_list_div .topic .topic_info .float_right .favor_amount {
				margin-right: 10px;
			}
			#topic_list_div .topic .topic_info .founder, 
				#topic_list_div .topic .topic_info .time {
				margin-right: 5px;
			}
			#load_more_button {
				width: 100%;
				display: none;
			}
			/*****创建话题的对话框****/
			#create_topic_dialog .modal-body .input-group:FIRST-CHILD {
				margin-bottom: 10px;
			}
		</style>
		<script language="javascript"> 
	  function checkchas(){
		 
	     document.Form1.action="getallbookinfo.action";
	     document.Form1.submit();
	  }

  </script>
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
		<!-- 话题搜索框 -->
		<div id="search_div" class="input-group">
			<div class="input-group-btn">
				<button class="btn btn-default" data-toggle="modal" data-target="#create_topic_dialog">
					<span class="glyphicon glyphicon-pencil"></span> 发起讨论
				</button>
			</div>
			<input type="text" class="form-control" name="keyword" placeholder="输入关键字查询">
			<div class="input-group-btn">
				<button class="btn btn-default" onclick="topic_pageNum=1;load()">
					<span class="glyphicon glyphicon-search"></span>
				</button>
			</div>
		</div>
		<!-- 话题列表 -->
		<div id="topic_list_div">
		</div>
		<button id="load_more_button" class="btn btn-default" onclick="++topic_pageNum;load()">
			<span class="glyphicon glyphicon-search"></span> <span>加载更多</span>
		</button>
		<!-- 提示信息 -->
		<div class="msg_div">
			没有找到对应的话题
		</div>
		<!-- 发起话题的对话框 -->
		<div id="create_topic_dialog" class="modal fade" role="dialog" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title">
							发起新的讨论
						</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="input-group">
								<span class="input-group-addon">标题</span>
								<input type="text" class="form-control" name="title" placeholder="请输入话题的标题" autofocus="autofocus">
							</div>
							<div class="input-group">
								<span class="input-group-addon">详情</span>
								<textarea rows="5" class="form-control" name="detail" placeholder="请输入话题的详情"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<div class="btn-group">
							<button type="button" class="btn btn-success" onclick="addTopic()">
								<span class="glyphicon glyphicon-ok"></span> 提交
							</button>
							<button type="button" class="btn btn-default" onclick="$('#create_topic_dialog').modal('hide')">
								<span class="glyphicon glyphicon-remove"></span> 取消
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
