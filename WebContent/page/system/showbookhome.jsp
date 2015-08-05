<%@ page language="java" pageEncoding="UTF-8"%>


<!DOCTYPE html>	
<html>
<head>


<title>图书信息</title>	
  <meta name="viewport" content="width=device-width, initial-scale=1" />
 
  <script src="bootstrap/jquery.min.js"></script>
 
  <script type="text/javascript" src="js/wchat/book.js?v1.1.1"></script>
  <!--<script type="text/javascript" src="../../js/wchat/jquery.mobile-1.4.2.min.js"></script>-->
  <link href="bootstrap/bootstrap.min.css" rel="stylesheet">
<style type="text/css">
html,body {            
	width:100%;            
	height:680px;            
	margin:0px;            
	padding:0px;  
	background-image: url(/images/11.jpg);
	background-color: #59a2c4;
	      
}
.center {           
  width:100%;      
  margin:auto;  
   height:65%;
  }
.top {
	height: 100%;
}


#searchdiv{
	height: 22%;
	margin-bottom: 3%;
}		
.searchinput {
	border-radius: 15px;
	width: 82%;
	height: 100%;
	text-align: left;
	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	border: 1px solid #cdcdcd;
	font-size: 15px;
	font-family: "Times New Roman","Tahoma","宋体","新宋体";
	margin-left: 8%;
	outline: none;
	outline-style: hidden;
	
	 
}

#borrowinghistory {

	margin-left: 2%;
}
#lendingranking {
	
	margin-left: 2%;
}
.classifi{
	text-align: center;
	width: 24%;
	height: 20%;
	margin-top: 2%;
	border: 1px solid #cdcdcd;
	margin-left:9%;
	background-color: #c6d5d9;
	opacity:100%;  
	font-size: medium;
}
.topcenter {
	height: 83%;
	outline:none;
}
.searchbutton {
	text-align: center;
	width: 35%;
	height: 25%;
	border-radius: 15px;
	border: 1px solid #cdcdcd;
	font-size: x-large;
	font-family: "Arial","Tahoma","华文楷体","楷体";
	margin-top: 6%;
	margin-left: 30%;
	background-color: #FFFFFF;
	color: #333333;
	
	
	
}
.divback {
	width: 95%;
	height: 23%;
	border-radius: 30px;
	margin-bottom: 2%;
	margin-left:3%;
	margin-right: 2%;
	background-image: url(${pageContext.request.contextPath }/images/bookbackdiv.png);
	
}
.bookleft {
	text-align: center;
	width: 24%;
	height: 90%;
	border-radius: 30px;
	border-radius: 30px 30px 30px 30px;
	margin-left: 0;
	margin-top: 1%;

	position: inherit;
	float: left;
}
.bookcenter {
	text-align: center;
	width: 70%;
	height: 96%;
	margin-left: 0%;
	margin-top: 0.3%;

	position: inherit;
	float: left;
}
.bookleftinner {
	text-align: center;
	width: 96%;
	height: 98%;
	border-radius: 30px;
	border-radius: 30px 30px 30px 30px;
	margin-left: 0%;
	margin-top: 0%;
	position: inherit;
	text-align: center;
	vertical-align: middle;
	background-size: cover;
}
.bookcentername {
	text-align: center;
	width: 98%;
	height: 30%;

	margin-left: 0%;
	margin-top: 0%;

	position: inherit;
	text-align: center;
	vertical-align: middle;
	background-size: cover;
	
	overflow:hidden;
	text-overflow: ellipsis;
}
.bookcentercontent {
	text-align: center;
	width: 98%;
	height: 65%;
	margin-left: 0%;
	margin-top: 0%;

	position: inherit;
	text-align: center;
	vertical-align: middle;
	background-size: cover;

	overflow:hidden;
	text-overflow: ellipsis;
}
.booknamefont {	
	color: #999;
	font-family: "Times New Roman","华文新魏";
	margin-left:0px;
	font-size: 20px;
	white-space: nowrap;/*设置不折行*/
	overflow:hidden;
	
	
}
.bookcontentfont {	
	color: #999;
	font-family: "Times New Roman","华文新魏";
	margin-left:0px;
	font-size: 10px;
	
}
a.bookcontentfont:hover,a.booknamefont:hover {	
	text-decoration: none;
	cursor: pointer;
}
#LoadMore{

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
</style>

</head>
<body>    
<form id="Form1" name="Form1"  method="post" style="width:100%;height:25%;margin-top:8%;">
<div  class=" center top">
   	  <div id="searchdiv" class="searchdiv">

		<input name="bookname" type="text" class="searchinput"  value="查询书籍"  id="bookname"
	 onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue;this.style.color='#999'}" 
	 style="color:#999999"	>

	</div>
  <div id="topcenter"  class="topcenter">
  
   	<input name="subjectcategory" type="button" class="classifi"   value="首页" id="indexPage"	>  
    <input name="borrowinghistory" type="button" class="classifi"   value="借阅历史" id="borrowinghistory"	>
    
    <input name="lendingranking" type="button" class="classifi"   value="借阅排行" id="lendingranking"	>

    <input type="button" class="searchbutton"   value="查询" id="searchbutton" >

       </div>
      
         
</div>
</form>
<!--   <table  style="width:80%;height:60%;margin:0 auto;border:0 cellpadding:0 cellspacing:1 " >
  	<tbody id="booklist">
  	
  	</tbody>
  	
<div  id="LoadMore"  >
	
</div>
  	
 </table>-->

 <div   class="center" id="module">

 	
  	
     
  </div>
     <div id="booknull">
  		没有所查询的书籍信息
 </div>  

 <button id="LoadMore" class="btn btn-success" >
		<span class="glyphicon glyphicon-search"></span>加载更多
</button>


 
</body>
</html>

