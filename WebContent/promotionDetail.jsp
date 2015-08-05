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
		<title>Mini~打折信息</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini话题详情">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/promotionDetail.js"></script>
		<script type="text/javascript">
			$(function() {
				var id = <%=request.getParameter("id")%>;
				if(id != null) {
					load(id);
				}
			});
		</script>
		<style type="text/css">
			/*****打折详细信息******/
			#promotion_detail_panel {
				margin-bottom: 0px;
			}
			#promotion_detail_panel .panel-heading .promotion_info {
				font-size: 14px;
				color: #999;
			}
			#promotion_detail_panel .panel-heading .promotion_info .float_right,
				#comment_list .float_right {
				float: right;
			}
			#promotion_detail_panel .panel-heading .promotion_info .float_right .favor_amount {
				margin-right: 10px;
			}
			#promotion_detail_panel .panel-heading .promotion_info .founder, 
				#promotion_detail_panel .panel-heading .promotion_info .date {
				margin-right: 5px;
			}
			#promotion_detail_panel .panel-body {
				font-size: 16px;
			}
			#promotion_detail_panel .panel-body .img_div {
				text-align: center;
				margin-top: 5px;
			}
			#promotion_detail_panel .panel-body .img_div img {
				max-width: 80%;
				border-radius: 5px;
			}
			#promotion_detail_panel .panel-footer {
				text-align: right;
			}
			#promotion_detail_panel .panel-footer button {
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
		<div id="promotion_detail_panel" class="panel panel-default">
			<div class="panel-heading">
				<div class="panel-title">
					<span class="promotion_title_span"></span>
				</div>
				<div class='promotion_info'>
					<span class='float_right'>
						<span class='glyphicon glyphicon-heart-empty'></span> <span class='favor_amount'></span>
					</span>
					<span class='founder'></span>
					<span class='date'></span>
				</div>
			</div>
			<div class="panel-body">
			</div>
			<div class="panel-footer">
				<button id="authorize_button" type="button" class="btn btn-success" onclick="authorize()">
					<span class="glyphicon glyphicon-user"></span> 批准
				</button>
				<button id="delete_button" type="button" class="btn btn-danger" onclick="deletePromotion()">
					<span class="glyphicon glyphicon-trash"></span> 删除
				</button>
				<button id="favor_button" type="button" class="btn btn-success" onclick="favor()">
					<span class="glyphicon glyphicon-heart-empty"></span> 赞
				</button>
			</div>
		</div>
	</body>
</html>
