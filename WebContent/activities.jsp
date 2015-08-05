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
		<title>活动进行</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini大型活动">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript" src="js/wchat/activities.js?v1.1.1"></script>
		<style type="text/css">
			.main_content{
			}
			.title{
			margin-top:30px;
			width:100%;
			height:40px;}
			.divblock{
			margin:0 auto;
			
			width: 95%;
			height: 50px;
			background:#da532c;
			margin-bottom:10px;}
			
			a{
			width:100%;
			text-align:center;
			font-size:14px;
			line-height:50px;
			color:#fff;
			display: block;
			}
			a:hover{
			width:100%;
			text-align:center;
			font-size:14px;
			line-height:50px;
			color:#525252;
			display: block;
			text-decoration: none;
			}
		</style>
	</head>
	
	<body >
		
		<div>
			<div class="title">
				<p>正在报名中的活动……</p>
			</div>
			<div class="main_content">
			</div>
		</div>
				
			
		
	
	</body>
</html>
