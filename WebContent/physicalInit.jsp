<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.mini.util.PageBean"%>

<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>体检信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	
		<!--<script type="text/javascript" src="js/myJs/jquery-2.0.2.min.js"></script> 
		<link href="css/css/bootstrap.css" rel="stylesheet" type="text/css"/>
		<link href="css/css/progress.css" rel="stylesheet" type="text/css"/>
		<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
		 <link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet"/>
		 <script type="javascript" src="script/function.js"></script>
		<script type="javascript" src="script/page.js"></script>
		<script type="javascript" src="script/validate.js"></script>
		<script type="javascript" src="script/pub.js"></script>
		<script type="javascript" src="js/myJs/jquery.min.js"></script>	
		
		
		
		 <script type="text/javascript" src="js/myJs/jquery.form.js"></script>
		-->

		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/bootstrap.min.js"></script>
   <script src="bootstrap/jquery.min.js"></script>
   <script type="text/javascript" src="js/body.js"></script>	
   <script type="text/javascript" src="js/myJs/physicalinit.js?v1.2.1"></script>
 
   <script type="text/javascript">
  
  
   </script>
<style type="text/css">
	
input[disabled]:hover{
		border:0px;
		background:#fff;
		color: graytext;
	}
	h1{
	font-size: 14px;
	color: #0C3A9C;
	text-indent: 10px;
	line-height: 28px;
	}	
	input{
	margin: 3px;}			
	body{
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-size: 13px;
line-height: 1.428571429;
color: #444;
margin:0px;}	

.form-group{
margin-right: 20px;
margin-top:8px}
.form-control{
height: 30px;
padding: 4px 12px;}
.btn{
float:right;
margin-right:55px;
margin-top:8px;
padding: 4px 12px;}

.radio{
 margin-top : 8px !important;}
 .form-control-static{
 font-size: 14px;}
</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【初始化信息】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:90%;margin:0 auto;">
				 
				<div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >体检批次：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="physicalPlan"  class="form-control">
				    
					 </select>
				</div><br>
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >不存在体检信息时的提示：</p>
				  </div>
				  <div style="width:100%;margin-top:10px">
					<textarea style="width:70%;"class="form-control" rows="3" id="nophysicalcontentinfor" ></textarea>
					</div>
					 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >查看体检日期的提示：</p>
				  </div>
				  <div style="width:100%;margin-top:10px">
					<textarea style="width:70%;"class="form-control" rows="3" id="physicaldateedit"></textarea>
					</div>
				 <button type="button" class="btn btn-primary " id="SaveInit">保存</button>
				
				
				 
			</div> 
				
			</form>
	
	</body>
</html>


