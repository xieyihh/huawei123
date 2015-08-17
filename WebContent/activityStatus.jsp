<%@ page language="java" pageEncoding="UTF-8"%>



<html>
<head>
		<title>活动状态</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	
		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/bootstrap.min.js"></script>
   <script src="bootstrap/jquery.min.js"></script>
 <script type="text/javascript" src="js/myJs/activityStatus.js?v1.3.2"></script>
 
  
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
		<h1>【活动状态】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:90%;margin:0 auto;">
				 <div class="form-group" >
				    <label class="sr-only" for="name">活动名称</label>
				    <input type="text" class="form-control" id="name" placeholder="活动名称"/>
				 </div>
				
				 <div class="form-group" style="margin-right:0px">
				    <p class="form-control-static">活动状态：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="status"  class="form-control">
					   <option value="0" selected="selected">所有</option> 
					</select>
				</div>
				
				 <button type="button" class="btn btn-primary " id="btnSearch">查询</button>
				
				
				 
			</div> 
				<table style="width:100%;border:0 cellpadding:0 cellspacing:1;margin-top:10px "  class="tableone ">
					<thead>
						  <tr class="title" >
							      <th width="30%">活动名称</th>
								  <th width="40%">活动状态</th>
								  <th width="30%">操作</th>
			  			</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					<tfoot id="load"><tr> <td align="center" colspan="11"></td> </tr> </tfoot>
				    
		
				</table>		     	
			
				<div class="linebox botm01">
			        <div style="text-align: left; float: left; width: 260px;"> 
						共<label id="lblToatl"></label>条数据 
						第[<label id="lblCurent"></label>]页/共[<label id="lblPageCount"></label>]页 
					</div> 
					<div  style="text-align: right; float: right;" > 
						<input class="pageButton"type="button" id="first" value="首页"></input>
						<input class="pageButton"type="button" id="previous" value="上一页"></input>
						<input class="pageButton"type="button" id="next" value="下一页"></input>
						<input class="pageButton"type="button" id="last" value="末页"></input>
						至第<input type="text" size='4' id="goPage" name="goPage"/>页
						每页显示<input  type="text"size='4'  id="pagesize" name="pagesize"/>行
					</div> 
					
			 	</div>
			</form>
		<!-- <script type="text/javascript">$("td").addClass("bookList"); </script> -->
	</body>
</html>


