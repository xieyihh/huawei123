<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>
<script language="javascript" src="/script/function.js"></script>
<script language="javascript" src="/script/page.js"></script>
<script language="javascript" src="/script/validate.js"></script>
<script language="javascript" src="/script/pub.js"></script>	
<!DOCTYPE html>	
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>武研图书角-借书成功</title>
<SCRIPT> 
function changeclo1(obj){ 
var lab = document.getElementById('a1'); 
lab.style.color="#ff0000";
var lab2 = document.getElementById('a2'); 
lab2.style.color="#fff";
gotoquery('bookclassifilist.action')
}
function changeclo2(obj){ 
	var lab1 = document.getElementById('a2'); 
	lab1.style.color="#ff0000";
	var lab2 = document.getElementById('a1'); 
	lab2.style.color="#fff";
	gotoquery('bookclassifilist.action')
	}
</SCRIPT> 
<style type="text/css">
.bodycss {

	border-color: #000;
	border-radius: 1px;
	font-size: 10px;
	background-size: cover;
	background-color: #59a2c4;		
}
.fenge {
	position: absolute;
	background-color: #fff;
	width: 92%;
	height: 2px;	
	word-spacing: normal;
	border-radius: 30px;
	top: 220px;
	margin-left:2%;
}
.divback {
	position: absolute;
	width: 90%;
	height: 200px;
	word-spacing: normal;
	margin-top: 2%;
	margin-left: 1%;
	top: 0 ;
}
.divimg {
	height: 191px;
	word-spacing: normal;
	position: absolute;
	margin-top: 2px;
	margin-left: 10px;
	border-color: #00F;
	border: 10;
	width: 100%;
	left: -6px;
}
.divimgcenter {
	width: 75px;
	height: 75px;
	background-color: #FFF;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	position: inherit;	
}
.divimgright {
	width: 40px;
	height: 36px;
	background-color: #fff;
	word-spacing: normal;
	border-radius: 30px;
	position: absolute;
	margin-top: 2px;
	border-color: #00F;
	border: 10;
	left: 452px;
	top: 14px;
}
.classifi {
	background-attachment: absolute;
	position: absolute;
	font-family: "Times New Roman","宋体";
	font-size: 40px;
	margin-left: 25%;
	margin-top: 20px;
}
.bookname {
	position: absolute;
	text-align:center;
	height: 27px;
	width:100%;
	font-family: "Times New Roman","华文新魏";
	margin-left: auto;
	margin-right: auto;
	font-size: 19px;
	overflow:hidden;
	line-height：26px;
}
.successtext{
	position: absolute;
	font-family: "宋体";
	font-size: 25px;
	top: 80px;
	height: 40px;
	width:100%;
	text-align: center;
	left:auto;
	right:auto;
}
.left {
	position: absolute;
	margin-left: 0;
	font-style: normal;
	margin-top: 0px;
	width: 40%;
	height: 200px;
}
.right {
	position: absolute;
	margin-left: 42%;
	font-style: normal;
	color:#fff;
	margin-top: 0;
	width: 50%;
	height: 200px;
}
.fenge2 {
	position: absolute;
	background-color: #fff;
	width: 92%;
	height: 2px;	
	word-spacing: normal;
	border-radius: 30px;
	top: 295px;
	margin-left:2%;
}
.showbacktime {
	width: 90%;
	height: 35px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	right: auto;
	left: auto;
	float: top;
	margin-top: 220px;
	margin-left: 1%;
	position: static;
}
.thedonor {
	width: 90%;
	height: 25px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	right: auto;
	left: auto;
	float: top;
	margin-top: 5px;
	margin-left: 1%;
	position: static;
}
.special {
	position: static;
	width: 92%;
	height: 35px;
	background-attachment: inherit;
	word-spacing: normal;
	border:solid#FFF;
	border-radius: 30px;
	margin-top: 20px;
	margin-left: 2%;
}
.historyinfo {
	position: static;
	width: 93%;
	height: 40px;
	background-attachment: inherit;
	word-spacing: normal;
	border-bottom:solid#FFF;
	margin-top: 7px;
	margin-left: 2%;
}

.backtimetext {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","微软雅黑","宋体";
	font-size: 17px;
	margin-left: 8%;
	margin-top: 10px;
	width: 80%;
	text-align:left;	
}
.donorname {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","微软雅黑","宋体";
	font-size: 17px;
	margin-left: 8%;
	margin-top: 0px;
	width: 80%;
	text-align:left;
}
.historyinfoleft {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 16px;
	margin-left: 0;
	margin-top: 0;
	width: 60%;
	height:40px;
	text-align:left;
	overflow:hidden;	
}

.historyinforight {
	position: absolute;
	color: #fff;
	margin-left: 60%;
	margin-top: 0;
	width: 30%;
	height:40px;
}

.specialleft {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 16px;
	width: 90%;
	text-align: center;
	margin-top: 9px;
	left:auto;
	right:auto;	
}
.historybookname {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 16px;
	margin-left: 0%;
	margin-top: 18px;
	width: 100%;
	height: 22px;
	text-align:left;
	overflow:hidden;	
}
.historyaddress {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 11px;
	margin-left: 0%;
	margin-top: 5px;
	width: 100%;
	height: 15px;
	text-align: center;
	left:auto;
	right:auto;
}

.historybookbacktime {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 11px;
	margin-left: 0%;
	margin-top: 25px;
	width: 100%;
	height: 11px;
	text-align:left;
}


</style>
</head>
<body class="bodycss"  >

<div class="divback"  >
    <div class="left">  
        <img src="<s:property value="#request.image"/>" class="divimg" />
    </div>
    <div class="right"> 
        <a class="bookname"><s:property value="#request.bookname"/></a>    
        <br>
        <br>
		<a class="successtext">借阅成功</a>
        <br>
        <br>
    </div>
</div>

<div class="fenge" >	 
</div>

<div class="showbacktime" >
        <a class="backtimetext">还书时间：<s:property value="#request.backdate"/>前</a> 
</div>

<div class="fenge2" >	 
</div>
 
<div class="thedonor" >
	<a class="donorname">图书捐赠人：<s:property value="#request.donor"/></a> 
</div>
<!-- 
<div class="special" >
	<a class="specialleft">您已借阅书籍数量: 3</a> 
</div>

<div class="historyinfo" >
	<div class="historyinfoleft">
		<a class="historybookname">1.没有女人的男人们</a> 
	</div>
    <div class="historyinforight">
    	<a class="historyaddress">鉴二图书馆</a>
		<a class="historybookbacktime">还书时间:2015-5-23</a>
    </div> 

</div>

<div class="historyinfo" >
	<div class="historyinfoleft">
		<a class="historybookname">1.没有女人的男人们</a> 
	</div>
    <div class="historyinforight">
    	<a class="historyaddress">鉴二图书馆</a>
		<a class="historybookbacktime">还书时间:2015-5-23</a>
    </div> 

</div>
-->
</body>
</html>

