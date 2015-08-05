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
		<title>Mini~首页</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini首页">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/body.css">
		<style type="text/css">
			.module_ul {
				list-style: none;
				width: 100%;
			}
			.module_ul li {
				float: left;
				text-align: center;
				padding: 20px 20px 0 20px;
				width: 33%;
			}
			.module_ul li img {
				width: 100%;
				margin-bottom: 5px;
			}
			.module_ul li span {
				font-size: 16px;
				color: #39A337;
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
		<ul class="module_ul">
			<li>
				<a href="association.jsp">
					<img alt="协会活动" src="img/association.png">
					<span>协会活动</span>
				</a>
			</li>
			<li>
				<a href="user.jsp">
					<img alt="个人中心" src="img/personal.png">
					<span>个人中心</span>
				</a>
			</li>
			<li>
				<a href="activity.jsp">
					<img alt="大型互动" src="img/activity.png">
					<span>大型活动</span>
				</a>
			</li>
			<li>
				<a href="bus.jsp">
					<img alt="班车信息" src="img/bus.png">
					<span>班车信息</span>
				</a>
			</li>
			<li>
				<a href="forum.jsp">
					<img alt="讨论交流" src="img/forum.png">
					<span>讨论交流</span>
				</a>
			</li>
			<li>
				<a href="promotion.jsp">
					<img alt="打折信息" src="img/promotion.png">
					<span>打折信息</span>
				</a>
			</li>
			<c:if test="${sessionScope.user.level == 2 }">
				<li>
					<a href="admin.jsp">
						<img alt="系统管理" src="img/personal.png">
						<span>系统管理</span>
					</a>
				</li>
			</c:if>
		</ul>
	</body>
</html>
