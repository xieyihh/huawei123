<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html  >
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Mini~用户反馈</title>
		<meta http-equiv="description" content="Mini用户反馈">
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
		<meta name="author" content="雷晓冰">
		<script type="text/javascript" src="js/jsMuti/jquery.min.js"></script>
		<script type="text/javascript" src="js/body.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/jsMuti/ajaxfileupload.js"></script>

		 <script type="text/javascript" src="js/feedback.js?v2.1.3"></script>

		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="css/body.css?1">
		<!-- 
		
		<script type="text/javascript" src="js/jsMuti/jquery.js"></script> --> 
		
		
		<style type="text/css">
			.main_content .logo_div {
				padding: 12px 0;
				text-align: center;
			}
			.main_content .logo_div img {
				max-width: 40%;
			}
			.main_content .input-group, .main_content .submit_div {
				margin-top: 10px;
			}
			.main_content .submit_div {
				text-align: center;
			}
			.main_content .submit_div button {
				width: 80%;
			}
			.main_content .message_div {
				display: none;
			}
			.main_content .alert {
				margin-top: 20px;
				display: none;
			}
			

.border
{
   box-shadow: 2px 2px 3px  rgba(34,25,25,0.4) ;
    -moz-box-shadow: 2px 2px 3px  rgba(34,25,25,0.4) ;
    -webkit-box-shadow: 2px 2px 3px rgba(34,25,25,0.4) ;
  cursor:pointer;      
}
 .closeimg
{
    position : absolute;
   margin-left:50px;
    width : 10px;
    height : 10px;
    display:none;
}
			.file {
  	position: absolute;
	width: 100px;
	height: 120px;
	opacity: 0;
	z-index: 2;
	}
	.img img{
	 position: absolute;
	 width: 100px;
	height: 120px;
	 z-index: 1;
	}
.file input {
    position: absolute;
    width: 100px;
	height: 120px;
    font-size: 20px;
    right: 0;
    top: 10px;
    opacity: 0;
}

#divNewPreview{
float:left;
margin-left:105px;
}
 div[id^='divImg']{
 float:left;
 margin-right:5px;
 margin-top:10px;
 }
 .uploadimg{
 width:60px;
 height:50px;
 }
  .photos{
 background-color:#fff;
height:130px;
 }
@media (min-width: 768px){
.closeimg
{
    position : absolute;
   margin-left:65px;
    width : 15px;
    height : 15px;
    display:none;
}
			.file {
  	position: absolute;
	width: 100px;
	height: 100px;
	opacity: 0;
	z-index: 2;
	}
	.img img{
	 position: absolute;
	 width: 100px;
	height: 100px;
	 z-index: 1;
	}
.file input {
    position: absolute;
    width: 100px;
	height: 100px;
    font-size: 20px;
    right: 0;
    top: 0;
    opacity: 0;
}

#divNewPreview{
float:left;
margin-left:120px;
}
 div[id^='divImg']{
 float:left;
 margin-right:20px;
 margin-top:10px;
 }
 .uploadimg{
 width:80px;
 height:80px;
 }
 .photos{
 background-color:#fff;
 height:100px;
 }
} 

		</style>
		
	</head>
	
	<body>
		<!-- 网页背景图 -->
		<img class="background_img" src="img/background.png">
		
		<div class="main_content">
			<div class="logo_div">
				<img alt="logo" src="img/logo.png">
			</div>
			<!-- <form action="" enctype="multipart/form-data"> -->
			<div id="feedback_div">
				<div class="input-group">
					<span class="input-group-addon">姓名</span>
					<input type="text" class="form-control" name="name" placeholder="请输入您的真实姓名" autofocus="autofocus">
				</div>
				<div class="input-group">
					<span class="input-group-addon">工号</span>
					<input type="number" class="form-control" name="number" placeholder="请输入您的工号">
				</div>
				<div class="input-group">
					<span class="input-group-addon">内容</span>
					<textarea rows="5" class="form-control" name="content" placeholder="请输入反馈内容"></textarea>
				</div>
				<div class="input-group" id="picture">
					<span class="input-group-addon">照片</span>
					<div class="photos">
						<div class="img" ><img  src="img/feedback/AddImage_Up.png"  title="添加图片"/></div>
						<div class="file"><input type="file" name="file" id="file0"  accept="image/gif, image/jpeg, image/png" capture="camera"  onchange="PreviewImage(this)"/></div>
						<div id="divNewPreview" ></div>	
					</div>
				</div>
			</div>
			
			<div class="submit_div">
				<button class="btn btn-success" id="submitButton" >提&nbsp;&nbsp;交</button>
			</div>
		</div>
</body>
	
</html>
