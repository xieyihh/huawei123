<%@ page language="java" pageEncoding="UTF-8"%>

<HTML>
	<HEAD>
		<title>活动初始化</title>		
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
		<script src="bootstrap/jquery.min.js"></script>
   		
   		<script src="bootstrap/bootstrap.min.js"></script>
	 	<script type="text/javascript" src="js/myJs/activityInit.js?v1.3.3"></script>
	 	
		
	   <script src="bootstrap/jquery.min.js"></script>
	  
    	
		<script type="text/javascript">
		</script>
		<style type="text/css">
			.tableone{
			display:none;
			margin-top:15px;
			border: 1px solid #ddd;
			border-collapse: separate;
			border-left:0;
			border-radius: 4px;}
			.tableone tr{line-height:33px;}
			.tableone th{
			background-color:#d9edf7;
			font-size:14px;
			text-align:center;
			color:#333;
			 border-top: 1px solid #ddd;
			border-left: 1px solid #ddd;}
			 .trOdd{background-color:#cdf;}
			
			.bookList{
			font-size:14px;
			color:#333;
			text-align:center;
			 border-top: 1px solid #ddd;
			border-left: 1px solid #ddd;
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
			.btn{
			float:right;
			margin-right:30px;
			margin-top:8px;
			padding: 4px 12px;}
			#saveInitInfo{
			display:none;}
			
		</style>
 </HEAD>	
	<body  >
	<div style="height: 30px;background: url(images/bg01.gif);">
		<h1>【活动初始化】</h1>
	</div>
	 <form name="Form1" id="Form1"  method="post" >
		<div class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  			<div style="width:85%;margin:0 auto;">
  				<div class="form-group">
				    <p class="form-control-static"  >活动列表：</p>
				  </div>
				 <div class="form-group">	
				    <select   id="activityLists" name="activityLists" class="form-control"></select>
				</div>
				<button type="button" class="btn btn-primary " id="addContent" >添加文本</button>
				<button type="button" class="btn btn-primary " id="addFile" >添加文件</button><br>
			</div>
  		
		<table style="width:100%;padding:0px; display:none"  class="tableone ">
					<thead>
						  <tr >
							      <th width="30%">编号</th>
							      <th width="30%">类型</th>
								  <th width="40%">名称</th>
							</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					<tfoot id="load"><tr> <td align="center" colspan="11"></td> </tr> </tfoot>
		</table>
		<button type="button" class="btn btn-success " id="saveInitInfo" >保存</button>
	</div>	
	
    	
	</form>
  </body>
</HTML>


