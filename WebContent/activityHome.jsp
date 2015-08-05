<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
String activity_Name = new String(request.getParameter("activityName").getBytes("ISO-8859-1"),"utf-8");
System.out.println(activity_Name);
%>

<!DOCTYPE HTML>
<html >
	<head>
		
		<title>活动首页</title>
		<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
		<jsp:include page="verifyLogin.jsp" flush="true"></jsp:include>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="description" content="Mini个人中心">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<jsp:include page="base.jsp" flush="true"></jsp:include>
		<script type="text/javascript">
		var activityName='<%=activity_Name%>';
		
		//alert(activityName);
		</script>
		<script type="text/javascript" src="js/wchat/activityHome.js?v1.2.3"></script>
		<style type="text/css">
		#activity{
		padding: 10px 15px 15px;
		background-color: #fff;}
			h2{
			margin-top:0px;
			margin-bottom: 12px;
			line-height: 1.4;
			font-weight: 400;
			font-size: 24px;
			}
			p{
			clear: both;
			min-height: 1em;
			white-space: pre-wrap;
			}
			.p0{
			background-color:#da532c;}
			.p1{
			background-color:#00a300;
			
			}
			.p2{
			background-color:#2d89ef;}
			img{
			width:100%;}
			#status{
			float:right;
			margin-right:10px;}
		</style>
		
	</head>
  
	<body>
		<div id="activity">
			<div id="title">
				<h2></h2>
			</div>
			<div id="status">
			
			</div>
			<div id="content">
				<p class="p0"></p>
			</div>
			<div id="image">
				<img src="">
			</div>
		</div>
	</body>
</html>
