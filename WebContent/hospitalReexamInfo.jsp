<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.mini.util.PageBean"%>

<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>体检信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	
		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/bootstrap.min.js"></script>
   <script src="bootstrap/jquery.min.js"></script>
   <script type="text/javascript" src="js/myJs/hospitalReexam.js?v1.2.4"></script>
 
   <script type="text/javascript">
  
  
   </script>
<style type="text/css">
	h1{
	font-size: 14px;
	color: #0C3A9C;
	text-indent: 10px;
	line-height: 28px;
	}	
	body{
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-size: 13px;
line-height: 1.428571429;
color: #444;
margin:0px;}	

.form-group{
margin-right: 20px;
margin-top:8px}

.btn{
float:right;
margin-right:30%;
margin-top:8px;
}

.form-control-static{
 font-size: 14px;}
 .space{
 width:100%;
 height:100px;}
</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【医院复查信息】</h1>
	</div>
  		<!-- 提示信息 -->
		<div class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  			<div style="width:85%;margin:0 auto;">
  				<div class="form-group" style="color:#bbb">
				    <p class="form-control-static"  >输入工号，核对体检者姓名等信息，再保存体检信息</p>
				 </div>
			</div>
		</div>
  		<div class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  			<div style="width:85%;margin:0 auto;">
  			
  			<div id="groups">
				 <div class="form-group" style="margin-right:20px">
					 
					    <p class="form-control-static"  id="physicalPlan"  ></p>
					  </div>
					  <div class="form-group" style="margin-right:20px">
					 
					    <p class="form-control-static"  id="physicalPosition"  ></p>
					  </div>
				
				 <div class="form-group" >
				    <label class="sr-only" for="workNumber">工号</label>
				    <input type="text" class="form-control" id="workNumber" placeholder="工号"/>
				 </div>
				 <button type="button" class="btn btn-primary " id="checkData" >核对信息</button>
			</div>
			<div style="width:100%;margin-top:10px">
			<textarea style="width:70%;"class="form-control" rows="6" id="reviewReport"></textarea>
			</div>
			<button type="button" class="btn btn-primary " id="saveData" >保存复查信息</button>
			</div>
  		</div>
  		
		
		
	</body>
</html>


