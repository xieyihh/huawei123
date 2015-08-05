<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>

<!DOCTYPE html >
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<title>用户管理</title>
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
		<script type="javascript" src="js/myJs/jquery.min.js"></script>	-->
		
	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
   <script src="bootstrap/jquery.min.js"></script>
   <script src="bootstrap/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/myJs/page.js?v1.2.2"></script>
   <script type="text/javascript" src="js/myJs/jquery.form.js"></script>
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


</style>

 



		
</head>	
	<body  id="right" class="main1">
	<div id="sending" style="background:#628bca;position:absolute; z-index:10; width: 400px; margin:0 auto;visibility: hidden">  
		　<table width=400 height=80 border=0 cellspacing=2 cellpadding=0 > 
		　　<tr>  
		　　　<td align=center>正在导出, 请稍候...</td>
			<td><img style="height:60px;width:100px;"src="img/loading.jpg"></td> 
		　</tr> 
		　</table> 
	</div>
	<div class="modal  fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
        <h4 class="modal-title" id="myModalLabel">书籍信息更改</h4>
      </div>
      <div class="modal-body">
       <form name="Form1" id="myForm" method="post" action="saveimportbookinfo.action"enctype="multipart/form-data">
    	<div style="margin:0 auto; text-align:center">
		<div class="easyui-panel"  style="width:600px;padding:30px 60px">
		<div style="margin-bottom:0px;height:0px">
			<!--  <div style="float:left;width:18%;">id：</div>-->
			<input class="easyui-textbox" type="hidden"name="id" id="id" style="width:80%;"readonly="readonly"/>
		</div>
		<div style="margin-bottom:0px;height:0px">
			<!--<div style="float:left;width:18%;">isbn10：</div>-->
			<input class="easyui-textbox" type="hidden"name="isbn10" id="isbn10"  style="width:80%;"readonly="readonly"/>
		</div>
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">二维码：</div>
			<input class="easyui-textbox"  type="text" name="onlysign" id="onlysign" style="width:80%;"readonly="readonly"/>
		</div>
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">书名：</div>
			<input class="easyui-textbox"  type="text"name="bookName"  id="bookName" style="width:80%;"/>
		</div>
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">图书来源：</div>
			<input class="easyui-textbox"   type="text"name="source" id="source"  style="width:80%;"/>
		</div>		
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">位置：</div>
			<select style="width:80%;"  name="position" id="position" theme="css_xhtml"></select> 	
		</div>
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">分类：</div>
			<select style="width:80%;"  name="classifi" id="classifi" theme="css_xhtml">
			</select> 	
		</div>	
		<div style="margin-bottom:20px;height:32px">
			<div style="float:left;width:18%;">作者：</div>
			<input class="easyui-textbox"  type="text" name="auto" id="auto" style="width:80%;"/>
		</div>	
		<div style="margin-bottom:20px;">
			<div style="float:left;width:18%;">总结：</div>
			<textarea class="easyui-textbox"   name="summary" data-options="multiline:true"  rows="3"id="summary" style="width:80%;"></textarea>
		</div>
		<div style="margin-bottom:0px;">
			<div style="float:left;width:18%;">图片：</div>
			<input id="file" type="file" name="file" multiple="multiple"  />
			 <img  name="imag"  id="imag" style="height:120px;width:100px;"/>
		</div>
		</div>
		</div>	
	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" id="save" >保存</button>
      </div>
    </div>
  </div>
</div>		
		
 <div style="height: 30px;background: url(images/bg01.gif);margin-top:-15px;">
		<h1>【图书信息】</h1>
	</div>
  	
		<form id="Form2" name="Form2"  method="post" style="width:95%;margin:0 auto">	
				<div> 
						书名：<input id="txtSm" type="text" value="" /> 
						图书来源：<input id="txtSource" type="text"  /> 
						所属平台：<select id="txtPosition">  
								  <option value="0" selected="selected">所有</option>  
								 <!--   <option value="1">2楼</option>  
								  <option value="2" >3楼</option>  
								  <option value="3">未来城C栋7楼</option> --> 
								</select>  
						
						isbn号：<input id="txtIsbn" type="text"  />
						isbn是否重复：<input name="isIsbn"type="radio" checked="checked" value="1" />是
						<input name="isIsbn" type="radio" value="0" />否
						<input id="btnSearch" type="button" value="查询" /> 
						<input id="exportData" type="button" value="导出" />
						<input id="download" type="button" value="下载" />
				</div> 	
				<table style="width:100%;border:0 cellpadding:0 cellspacing:1 "  class="tableone ">
					<thead>
						  <tr class="title" >
							      <th width="6%">isbn号</th>
								  <th width="10%">书名</th>
								  <th width="6%">图书来源</th>
								  <th width="24%">二维码</th>
								  <th width="9%">所属平台</th>
								  <th width="8%">导入时间</th>
								  <th width="3%">数量</th>
								  <th width="8%">编辑</th>					  
								  <th width="10%">下载</th>
								  <th width="9%">删除</th>
								  
								  <th width="9%">唯一编号</th>
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


