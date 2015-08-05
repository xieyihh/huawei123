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
   <script type="text/javascript" src="js/myJs/importPhysical.js?v1.2.1"></script>

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
margin-right:55px;
margin-top:8px;
}

.form-control-static{
 font-size: 14px;}
</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【体检安排导入】</h1>
	</div>
  		<!-- 提示信息 -->
		<div class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  			<div style="width:85%;margin:0 auto;">
  				<div class="form-group" style="color:#bbb">
				    <p class="form-control-static"  >导入体检安排信息</p>
				 </div>
			</div>
		</div>
		<form id="importForm" name="importForm"  action="importphysicalexam.action"method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:85%;margin:0 auto;">
				 
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >体检批次：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="physicalPlan" name="physicalplan" class="form-control">
					 </select>
				</div>
				  <div class="form-group">	
				 <input type="file" id="file" name="file" accept=".csv">
				 </div>
				 <button type="button" class="btn btn-primary " id="importData" >导入</button>
				  <button type="button" class="btn btn-success " id="downloadPhysicalTemplates" >下载模版</button>
				 
			</div> 
				
			</form>
		
	</body>
</html>


