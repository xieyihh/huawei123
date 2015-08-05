<%@ page language="java" pageEncoding="UTF-8"%>
<%@page import="com.mini.util.PageBean"%>
<%

String physicalplan = new String(request.getParameter("physicalplan").getBytes("ISO-8859-1"),"utf-8");
String usernumber = new String(request.getParameter("usernumber").getBytes("ISO-8859-1"),"utf-8");
String username = new String(request.getParameter("username").getBytes("ISO-8859-1"),"utf-8");


	System.out.print(physicalplan+';'+usernumber+';'+username);
%>
<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>体检信息管理</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />	
	
		
		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
	<script src="bootstrap/bootstrap.min.js"></script>
   <script src="bootstrap/jquery.min.js"></script>
 
  

   <script type="text/javascript">
  	var pageIndex=1;
  	var physicalplan='<%=physicalplan %>';
	var usernumber='<%=usernumber%>';
   $(function() { 
	 //AJAX方法取得数据并显示到页面上 
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getuserphsicalrelativesOnback.action", //要访问的后台地址 
			data: { "usernumber":usernumber,"physicalplan":physicalplan}, //要发送的数据 
			ajaxStart: function() { $("#load").show(); }, 
			complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if (msg.length != 0) { 
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length != 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
					
				} 
				$.each(msg.context.physicalrelativeslist, function(i, item) {
					
					var bodyContent='<tr>'+
										'<td width="20%" >' + item.relativeName + '</td>'+
										'<td width="30%" >' + item.idnumber + '</td>'+
										'<td width="30%" >' + item.phonenumber + '</td>'+
										'<td width="20%" >' + item.relationship + '</td>'+
									'</tr>';
					$("#tb_body").append(bodyContent); 
					
					
				});
				
				 $(".tableone tr:nth-child(even)").addClass("trOdd");
				 $("td").addClass("bookList");
				 //$("td").css("color","#ccc");
				 //$("td").css("text-align","center");
				
				 $
			}, 
			error: function() { 
				var t = document.getElementById("tb_body"); //获取展示数据的表格 
				while (t.rows.length != 0) { 
					t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
				} 
				alert("加载数据失败"); 
			} 
		}); 
	});
	



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
	
	body{
font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
font-size: 13px;
line-height: 1.428571429;
color: #444;
margin:0px;}	


</style>


		
</head>	
	<body  id="right" class="main1">
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【家属信息】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" enctype="multipart/form-data" class="form-inline"style="width:95%;margin:0 auto">	
			<div style="width:90%;margin:0 auto;margin-top:20px">
				 
				 <div class="form-group">
					
				    <input type="text" class="form-control" readonly id="workNumber" value="工号：<%=usernumber%>"/>
				     
				</div>
				<div class="form-group" >
					
				     <input type="text" class="form-control" readonly id="workname" value="姓名：<%=username%>"/>
				 </div>
			</div>
				
			
				<table style="width:100%;border:0 cellpadding:0 cellspacing:1;margin-top:10px "  class="tableone ">
					<thead>
						  <tr class="title" >
							      <th width="20%">姓名</th>
								  <th width="30%">身份证号</th>
								  <th width="30%">电话</th>
								  <th width="20%">关系</th>
								  
			  			</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					<tfoot id="load"><tr> <td align="center" colspan="11"></td> </tr> </tfoot>
				    
		
				</table>		     	
			
				
			</form>
		<script type="text/javascript"> $("td").addClass("bookList");</script>
	</body>
</html>


