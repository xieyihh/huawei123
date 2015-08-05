<%@ page language="java" import="java.util.*"  contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>书籍排名</title>
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
   <script src="bootstrap/jquery.min.js"></script>
  
    <script type="text/javascript" src="js/myJs/bookRanking.js?v1.2.2"></script>
	<!-- 日历 -->
<script type="text/javascript" src="My97DatePicker/WdatePicker.js" xml:space="preserve"></script>
	<style type="text/css">
	 .func{
	position:relative;
	top:15px;
	margin:0 auto;
	width:95%;
	height:40px;
	
	}
	.dateform{
	width:100%;
	
	color:#666;
	font-family:宋书;
	font-size:12px;
	float:left;}
	.inp-tex{
	width:15%;
	height:20px;
	margin-bottom:3px;
	float:left;
	}
	.dateform .op1{
	line-height:25px;
	width:2%;
	color:#666;
	font-family:宋书;
	font-size:13px;
	float:left;
	text-align:center
	}
	.dateform .op2{
	line-height:25px;
	width:5%;
	color:#666;
	font-family:宋书;
	font-size:13px;
	float:left;
	text-align:center
	}
	.dateform .op3{
	line-height:25px;
	width:10%;
	color:#666;
	font-family:宋书;
	font-size:13px;
	float:left;
	text-align:center
	}
	.dateform .op4{
	line-height:25px;
	width:5%;
	color:#666;
	font-family:宋书;
	font-size:13px;
	text-align:center
	}
	.sel{
	width:6%;
	margin-left:1%;
	height:25px;
	line-height:25px;
	float:left;
	}
	.inp-tex2{
	width:15%;
	height:25px;
	float:left;
	}
	.sub-mit{
	margin-left:2%;
	}
	/* page crumbs
	
	
	</style>
	
	<style type="text/css">
	.tableone{background:#eee}
	.tableone tr{line-height:22px;}
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
	font-weight: 100;
	}	
	input{
	margin: 3px;}	
	body{
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-size: 13px;
line-height: 1.428571429;
color: #444;
margin:0px;}	




</style>  
	
  </head>
  
  <body>
  <div style="height: 30px;background: url(images/bg01.gif);">
		<h1>【书籍借阅排行】</h1>
	</div>
	<div style="width:80%;margin:0 auto">
  <div class="func"  >
  	<form class="dateform" id="dateform" method="post">
		日期：<input  type="text" id="starttime"name="starttime" onfocus="WdatePicker({skin:'green',lang:'zh-cn',dateFmt:'yyyy-MM-dd'})"  />
		到
		<input type="text" id="endtime"name="endtime" onfocus="WdatePicker({skin:'green',lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" />	
		类型：<select id="txtClassfi">  
								  <option value="" selected="selected">所有</option>  
								 <!--   <option value="1">2楼</option>  
								  <option value="2" >3楼</option>  
								  <option value="3">未来城C栋7楼</option> --> 
								</select>  
   		<input class="sub-mit" type="button" id="check" style="float:right"value="查询" />
	</form>
	
</div>
 	<div style="positon:relative;width:95%;margin:0 auto;margin-top:3px;">
				<table style="width:100%;border:0 cellpadding:0 cellspacing:1 bgcolor:#BBC0C6"  class="tableone ">
					<thead>
						  <tr class="title" >
							      <th width="60%">书籍名称</th>
								 
								  <th width="40%">借阅次数</th>
								  
			  			</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					<tfoot id="load"><tr> <td align="center" colspan="11"></td> </tr> </tfoot>
				    
		
				</table>		     	
			
				<div class="linebox botm01"style="width:95%;margin:0 auto;">
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
			</div>
			<script type="text/javascript"> $("td").addClass("bookList");</script>
	</div>
  </body>
</html>
