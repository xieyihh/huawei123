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
   	<script type="text/javascript" src="js/myJs/hospitalPhysical.js?v1.1.3"></script>

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
		<h1>【医院体检信息】</h1>
	</div>
	
		
		<div class="main_content">
  			<form  class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  				<div id="innerdiv"style="width:85%;margin:0 auto;">
	  				<div class="form-group" style="color:#bbb;width:100%">
					    <p class="form-control-static"  >输入工号，核对姓名信息、体检批次，再保存体检信息</p>
					 </div>
  				
					 <div class="form-group" style="margin-right:0px">
					 
					    <p class="form-control-static"  id="physicalPlan"  ></p>
					  </div>
					 
					 <div class="form-group" >
					    <label class="sr-only" for="workNumber">工号</label>
					    <input type="text" class="form-control" name="workNumber" id="workNumber" placeholder="工号"/>
					 </div>
				<button type="button" class="btn btn-primary " id="singleData" >保存体检信息</button>
				 <button type="button" class="btn btn-success " id="downloadPhysicaldateTemplates" title="如需批量处理，请点击下载此模板，按模板要求填写" >下载模版</button>
				<table id="userinfo" style="display:none;width:90%;border:0 cellpadding:0 cellspacing:1;padding:0;margin-top:10px "  class="tableone ">
					 <thead >
						  <tr class="title" >
						  		  <th width="15%">工号</th>
								  <th width="20%">姓名</th>
								 <!--   <th width="20%">体检号</th>-->
								  <th width="20%">体检地点</th>
								  <th width="25%">安排体检日期</th>
								  <th width="20%">体检状态</th>
								 			  
			  			</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
				</table>
			</div>
  		</form><br>
  		
  		
		
		</div>		 
	</body>
</html>


