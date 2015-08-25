<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.mini.util.PageBean"%>

<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>体检信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	
		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
<!-- 	<script src="bootstrap/bootstrap.min.js"></script>
   <script src="bootstrap/jquery.min.js"></script>
   <script type="text/javascript" src="js/body.js"></script> -->
  
		<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
		<script type="text/javascript" src="js/lib/bootstrap.min.js"></script>
		<script type="text/javascript" src="js/body.js"></script>
   	<script type="text/javascript" src="js/myJs/importHospitalInfo.js?v1.1.3"></script>

   <script type="text/javascript">
  
  
   </script>
<style type="text/css">
.tableone{background:#eee}
	.tableone tr{line-height:30px;cursor: pointer;}
	.tableone tr th{background-color:#ccc;text-align:center;color:#fff}
	 .trOdd{background-color:#cdf;}
	 .trEven{background-color:#ccc;}
	 .trChoose{background-color:#000;opacity:0.8;filter:alpha(opacity=80); }
	 .bookList{
	font-size:12px;
	color:#333;
	text-align:center;
	}
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
.space{
width:100%;height:100px}
</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【体检信息导入】</h1>
	</div>
	
		
		<div class="main_content">
  			
  		
  		<form id="hospitalForm" name="hospitalForm" action="savePhysicaldateByimport.action" method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto;">	
			<div style="width:85%;margin:0 auto;">
				
				<div class="form-group" style="color:#bbb;width:100%">
				    <p class="form-control-static"  >选择体检批次以及医院提供的体检信息csv文件，批量导入体检信息</p>
				 </div>
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >体检批次：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="physicalPlanBat" name="physicalplan" class="form-control">
					 </select>
				</div>
				  <div class="form-group">	
				 <input type="file" id="file" name="file" accept=".csv">
				 </div>
				 <button type="button" class="btn btn-primary " id="batchData" >导入体检信息</button>
				  <button type="button" class="btn btn-success " id="downloadPhysicaldateTemplates" title="导入前请先下载模板核对格式要求" >下载模版</button>
			</div> 
				
			</form>
			
			<form id="ReviewForm" name="ReviewForm" action="importphysicalreview.action" method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto;">	
			<div style="width:85%;margin:0 auto;margin-top:60px;">
				
				<div class="form-group" style="color:#bbb;width:100%">
				    <p class="form-control-static"  >选择体检批次以及医院提供的复查信息csv文件，批量导入复查信息</p>
				 </div>
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static"  >体检批次：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="physicalPlanBat2" name="physicalplan" class="form-control">
					 </select>
				</div>
				  <div class="form-group">	
				 <input type="file" id="file2" name="file" accept=".csv">
				 </div>
				 <button type="button" class="btn btn-primary " id="batchReview" >导入复查信息</button>
				  <button type="button" class="btn btn-success " id="downloadReviewTemplates" title="导入前请先下载模板核对格式要求" >下载模版</button>
			</div> 
				
			</form>
		
		</div>		 
	</body>
</html>


