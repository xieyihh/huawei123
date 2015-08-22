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
   <script type="text/javascript" src="js/myJs/physicalReview.js?v1.3.2"></script>
	 <!-- 日历 -->
<script type="text/javascript" src="My97DatePicker/WdatePicker.js?v1.1" ></script>

   <script type="text/javascript">
  
  
   </script>
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
margin-top:8px;
width:15%}
.form-control{
height: 30px;
padding: 4px 12px;}
.btn{
float:right;
margin-right:35px;
margin-top:8px;
padding: 4px 12px;}


 .form-control-static{
 font-size: 14px;}
</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【复查信息】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:95%;margin:0 auto;">
				 <div class="form-group" >
				    <label class="sr-only" for="workNumber">工号</label>
				    <input type="text" class="form-control" id="workNumber" placeholder="工号"/>
				 </div>
				 <div class="form-group">
				    <label class="sr-only" for="workname">姓名</label>
				    <input type="text" class="form-control" id="workname" placeholder="姓名"/>
				 </div>
				 <div class="form-group">
				    <label class="sr-only" for="reviewContent">复查内容</label>
				    <input type="text" class="form-control" id="reviewContent" placeholder="复查内容"/>
				 </div>
				 <div class="form-group" style="margin-right:0px;width:7%">
				    <p class="form-control-static"  >体检批次：</p>
				  </div>
				 <div class="form-group"style="width:10%">	
				    <select   id="physicalPlan"  class="form-control">
				     <option value="0" selected="selected">所有</option> 
					 </select>
				</div> 
				<div class="form-group" style="margin-right:0px;width:7%">
				    <p class="form-control-static">体检地点：</p>
				  </div>
				 <div class="form-group"style="width:10%">	
				    <select   id="physicalposition"  class="form-control">
					   <option value="0" selected="selected">所有</option> 
					</select>
				</div>
				 <div class="form-group ">
				    <label class="sr-only" for="physicalreservedate">复查导入日期</label>
				    <input type="text" class="form-control" id="physicalreservedate" placeholder="复查导入日期"onfocus="WdatePicker({skin:'ext',lang:'zh-cn',dateFmt:'yyyy-MM-dd'})"/>
				 </div>		
				<div class="form-group ">
				    <label class="sr-only" for="physicaldate">实际体检日期</label>
				    <input type="text" class="form-control" id="physicaldate" placeholder="实际体检日期" onfocus="WdatePicker({skin:'ext',lang:'zh-cn',dateFmt:'yyyy-MM-dd'})"/>
				 </div>
				<button type="button" class="btn btn-success " id="exportReviewinfo">导出复查信息</button>
				 <button type="button" class="btn btn-primary " id="btnSearch">查询</button>
				
				 
			</div> 
			
				<table style="width:100%;border:0 cellpadding:0 cellspacing:1;margin-top:10px "  class="tableone ">
					<thead>
						  <tr class="title" >
							      <th width="8%">工号</th>
								  <th width="8%">姓名</th>
								  <th width="8%">体检地点</th>
								  <th width="8%">复查导入日期</th>
								  <th width="8%">实际体检日期</th>
								  <th width="8%">体检批次</th>
								  <th width="46%">复查内容</th>					  
								  <th width="6%">操作</th>
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
		<script type="text/javascript"> $("td").addClass("bookList");</script>
	</body>
</html>


