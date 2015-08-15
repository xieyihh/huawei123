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
   <script type="text/javascript" src="js/myJs/authorityPage.js?v1.1.2"></script>
 
  
<style type="text/css">
	.tableone{background:#eee}
	.tableone tr{line-height:30px;}
	.tableone tr th{background-color:#ccc;text-align:center;color:#fff}
	 .trOdd{background-color:#cdf;}
	.bookList{
	font-size:12px;
	color:#333;
	text-align:center;
	}
	a{
	cursor:pointer;}
	.pageButton {
		margin:0px;
		padding:0px;
		border:0px;
		background:#fff;
		}
	.pageButton:hover {
		border:0px;
		background:#fff;
		color: #428bca;
	}
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
		<h1>【权限分配】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:90%;margin:0 auto;">
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >角色：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="authority"  class="form-control">
				    
					</select>
				</div>
				<br>
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >图书后台管理：</p>
				  </div>
				<label class="checkbox-inline">
				  <input type="checkbox" name="book" id="book1" value="1"> 书籍信息查询
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="book" id="book2" value="2"> 导入书籍
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="book" id="book3" value="3"> 借阅排行榜
				</label>
				
				
				<br>
				<div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >体检后台管理：</p>
				  </div>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical1" value="1"> 体检信息
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical2" value="2"> 体检安排导入
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical3" value="3"> 体检信息导入
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical4" value="4"> 新加员工信息
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical5" value="5"> 医院体检信息
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical6" value="6"> 医院复查信息
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical7" value="7"> 复查信息
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="physical" id="physical8" value="8"> 初始化信息
				</label>
				<br>
				<div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >活动管理：</p>
				  </div>
				<label class="checkbox-inline">
				  <input type="checkbox" name="activity" id="activity1" value="1"> 活动发布
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="activity" id="activity2" value="2"> 活动初始化
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="activity" id="activity3" value="3"> 活动状态
				</label>
				<br>
				<div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >反馈信息管理：</p>
				  </div>
				<label class="checkbox-inline">
				  <input type="checkbox" name="feedback" id="feedback1" value="1"> 图书反馈
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="feedback" id="feedback2" value="2"> 信息反馈
				</label>
				
				<br>
				<div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >超级管理员：</p>
				  </div>
				<label class="checkbox-inline">
				  <input type="checkbox" name="superAdmin" id="superAdmin1" value="1"> 权限管理
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="superAdmin" id="superAdmin2" value="2"> 权限分配
				</label>
				<label class="checkbox-inline">
				  <input type="checkbox" name="superAdmin" id="superAdmin3" value="3"> 数据字典
				</label>
				 <button type="button" class="btn btn-primary " id="btnSave">保存</button>
				
				
				 
			</div> 
				
			</form>
		
	</body>
</html>


