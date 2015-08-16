<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>	
<html>
<head>


<title>图书信息</title>	
  <meta name="viewport" content="width=device-width, initial-scale=1" />
 
  	<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
   <script src="bootstrap/jquery.min.js"></script>
   <script src="bootstrap/bootstrap.min.js"></script>
  <script type="text/javascript" src="js/wchat/feedbackRanking.js?v1.1.1"></script>
  <!--<script type="text/javascript" src="../../js/wchat/jquery.mobile-1.4.2.min.js"></script>-->
  <link href="bootstrap/bootstrap.min.css" rel="stylesheet">
  
<style type="text/css">
input.search-query {
padding-right: 14px;
padding-right: 4px \9;
padding-left: 14px;
padding-left: 4px \9;
margin-bottom: 0;
-webkit-border-radius: 15px;
-moz-border-radius: 15px;
border-radius: 15px;
}
.form-search{
display: block;
width: 85%;
margin:0 auto;
margin-top: 20px;
margin-bottom: 20px;
vertical-align: middle;

}
.input-medium {
width: 85%;
line-height:30px;
}
#LoadMore,#booknull{

width:95%;
margin-left:3%;
margin-top:1.5%;
margin-bottom:2%;
border-radius: 30px;

text-align: center;
line-height: 27px; }
.glyphicon {
position: relative;
top: 1px;
display: inline-block;
font-family: 'Glyphicons Halflings';
font-style: normal;
font-weight: normal;
line-height: 1;
-webkit-font-smoothing: antialiased;
}
thead tr th{
text-align:center;
color:#f89406;
font-weight:4}
table{
text-align:center;
color:#51a351;}
</style>

</head>
<body>    

<form class=" form-search">
            <input type="text" class="input-medium search-query" name="nickname" id="nickname" placeholder="昵称查询">
            <button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span></button>
          </form>


 <div  class="table-responsive" id="module">
	<table class="table table-hover">
					<thead>
						  <tr >
							      <th width="30%">昵称</th>
								  <th width="40%">积分</th>
							
								  <th width="30%">排名</th>
								 
			  			</tr>
					</thead>
					<tbody id="tb_body"> </tbody> 
					
				    
		
				</table>		     
 	
  	
     
  </div>
   
  		<button id="booknull" class="btn btn-danger" disabled >
				<span class="glyphicon glyphicon-search"></span>没有相关数据
		</button>
   

 <button id="LoadMore" class="btn btn-success" >
		<span class="glyphicon glyphicon-search"></span>加载更多
</button>
<div style="margin-top:20px;">
<a href="http://www.weixinguanjia.cn/gzzh/artview-1.html?wxid=ogdP-jg6nhwczjOU6xdLvlyx40ec&wid=62293&rid=202572#mp.weixin.qq.com">活动介绍</a>
 </div>
</body>
</html>

