<%@ page language="java" pageEncoding="UTF-8"%>

<HTML>
	<HEAD>
		<title>活动发布</title>		
		
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
		<script src="bootstrap/jquery.min.js"></script>
   		<script type="text/javascript" src="js/body.js"></script>
   		<script src="bootstrap/bootstrap.min.js"></script>
	 	<script type="text/javascript" src="js/myJs/activityLaunch.js?v1.4.2"></script> 
	 	<link rel="stylesheet" type="text/css" href="css/body.css?1"> 	
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
			@media (min-width: 768px){
			body{
			
			max-width: 100%;
			font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
			font-size: 13px;
			line-height: 1.428571429;
			color: #444;
			margin:0px;
			}	
			}
			.inputfix{
			margin-right: 2.5%;
			margin-top: 8px;
			width:70%;
			}
			.form-group{
			margin-top: 8px;}
			.form-control{
			height: 30px;
			padding: 4px 12px;}
			.btn{
			float:right;
			margin-right:30px;
			margin-top:8px;
			padding: 4px 12px;}
			
			.radio{
			 margin-top : 8px !important;}
			 .form-control-static{
			 font-size: 14px;}
			 #divNewPreview{
				float:left;
				margin-left:45px;
				height:100px;
				width:80%;
				}
				 div[id^='divImg']{
				 float:left;
				 margin-right:20px;
				 margin-top:10px;
				 }
				 .uploadimg{
				 width:80px;
				 height:80px;
				 }
				 .photos{
				 background-color:#fff;
				 height:100px;
				 }
				 .closeimg
					{
					    position : absolute;
					   margin-left:65px;
					    width : 15px;
					    height : 15px;
					    display:none;
					}
				
		</style>
 </HEAD>	
	<body  >
	<div style="height: 30px;background: url(images/bg01.gif);">
		<h1>【活动发布】</h1>
	</div>
	 
	
	<div class="form-inline" style="width: 95%;margin: 0 auto;margin-top:20px">
  			<div style="width:85%;margin:0 auto;">
  				<div id="groups">
					 <div class="form-group">
					    <p class="form-control-static"  >活动列表：</p>
					  </div>
					 <div class="form-group">	
					    <select   id="activityLists" name="activityLists" class="form-control"></select>
					</div><br>
					<div class="form-group" >
					    <p class="form-control-static">主题：</p>
					  </div>
					 <div class="form-group inputfix" >	
					    <input  type="text" class="form-control" id="title"  name="title"/>
					</div><br>
					<div id="contentDiv">
						<div class="form-group" >
						    <p class="form-control-static">内容：</p>
						</div>
						<div class="form-group inputfix" >	
						    <textarea   class="form-control"rows="4" name="content"id="content0" /></textarea>
						</div>
						<button type="button" class="btn btn-primary " id="addContent" >添加内容</button><br>
					</div>
					 <div id="imageDiv">
						<div class="form-group">
						    <p class="form-control-static">图片：</p>
						 </div>
						
						 <div class="form-group inputfix file" >	
						    <input type="file" class="form-control" name="file" id="file0"  accept="image/gif, image/jpeg, image/png" capture="camera" onchange="PreviewImage(this)"/>
						</div>
					</div>
				</div>
				<div id="divNewPreview" ></div>	
				<button type="button" class="btn btn-success " id="saveActivityData" >保存活动信息</button>
			</div>
  		</div>
  		<div id="filename" style="display:none"></div>
  </body>
</HTML>


