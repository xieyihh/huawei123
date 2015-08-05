<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>反馈信息</title>
<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
   <script src="bootstrap/jquery.min.js"></script>
   <script src="bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/myJs/feedback.js?v1.2.1"></script>
  <style type="text/css">
	
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

.master ,td{
padding: 16px 7px;
border-bottom: 1px solid #e3e3e3;
}
.content{
color: #333;
word-wrap: break-word;
word-break: break-all;
line-height: 19px;
overflow: hidden;}
.photos-thumb{
margin: 10px 0;
list-style-type: none;
height: 60px;
zoom: 1;}
.photos-thumb li{
float: left;
border: 2px solid #f2f2f2;
padding: 2px;
margin-right: 8px;
position: relative;
transition: border-color .2s ease-out;
cursor: zoom-in;

}
.tm-m-photo-viewer{
position: relative;
margin: 10px 0 10px 30px;

border: 1px solid #ccc;
background: #fff;
padding: 2px;
float: left;
overflow: hidden;}
.rate-date{
clear: both;
color: #ccc;}
</style>
</head>
<body>
	
	<div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【反馈信息】</h1>
	</div>
	<div style="width:80%;margin:0 auto;">
			<table style="width:100%;margin:0 auto;border:0 cellpadding:0 cellspacing:1 "  class="tableone ">
						
							<tbody id="feedbackInfo"> </tbody> 
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
			 	</div>
</body>
</html>