<%@ page language="java" pageEncoding="UTF-8"%>

<HTML>
	<HEAD>
		<title>数据字典</title>		
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	   <script src="bootstrap/jquery.min.js"></script>
	  
    	<script type="text/javascript" src="js/myJs/dictionary.js?v1.2.2"></script>
		<script type="text/javascript">
		</script>
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
			margin:0px;
			}	
			
		</style>
 </HEAD>	
	<body  >
	<div style="height: 30px;background: url(images/bg01.gif);">
		<h1>【数据字典维护】</h1>
	</div>
	 <form name="Form1" id="Form1"  method="post" style="margin:0 auto;width:80%"  >
		<div style="width:95%;margin:0 auto;height:35px;margin-bottom:1px;">
			 <div style="float:left;margin-right:5px;line-height:33px;">
					类型列表：<select  id="lists" name="lists">
										<option value="0" selected="selected">自定义</option> 
							</select>
			</div>
			<div id="addList"  style="float:left;line-height:33px;">
				类型名称：<input type="text" name="listname"  id="listname" >
			</div>	
			<div style="float:right;height:22px;margin-top:4px;">
				<input style="padding-top:0px;height:21px;" type="button" name="additem" id="additem" value="添加选项">
			</div>
				
		</div>
		<table style="width:100%;padding:0px;border:0; cellpadding:0; cellspacing:1 ; "  class="tableone ">
					<thead>
						  <tr >
							      <th width="40%">编号</th>
								  <th width="60%">名称</th>
							</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					<tfoot id="load"><tr> <td align="center" colspan="11"></td> </tr> </tfoot>
		</table>	
    	<input id="save" type="button" name="saveitem" value="保存" style="float:right;display:none;font-size:12px; color:black; height:20px;width:50px" >
	</form>
  </body>
</HTML>


