﻿<%@ page language="java" pageEncoding="UTF-8"%>
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
<title>武研图书角-个人借阅历史</title>
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
	width: 90%;
	border-color: #000;
	border-radius: 1px;
	font-size: 10px;
	background-size: cover;
	background-color: #59a2c4;	
}

.center {
	position: absolute;
	width: 70%;
	height: 100px;
	margin-left: 20%;
	margin-top: 0px;
}

.divback {
	position: static;
	width: 91%;
	height: 100px;
	border-radius:10px ;
	margin-top: 10px;
	margin-left: 6%;

	background-color: #e2e4db;
}
.divimg {
	height: 90px;
	width: 90%;
	word-spacing: normal;
	position: absolute;
	margin-top: 6px;
	margin-left: 6%;
	left: 0;
	border-radius: 15px;
}

.bookname {
	position: absolute;
	text-align:left;
	height: 30px;
	width:85%;
	font-family: "Times New Roman","华文新魏";
	margin-left: auto;
	margin-right: auto;
	font-size: 20px;
	color: #7f7f7f;
	overflow:hidden;
}
.backinfo{
	font-size: 14px;
	position:absolute;
	margin-right: 0%;
	margin-top: 50px;
	width: 60%;
	height: 45px;
	overflow:hidden;
	font-family: "楷体","宋体";
	color: #7f7f7f;
	text-align:right;

}

.left {
	position: absolute;
	margin-left: 0;
	font-style: normal;
	margin-top: 0px;
	border-radius: 15px;
	width: 20%;
	height: 90px;
}



.borrowhistory {
	position: static;
	position: static;
	width: 90%;
	height: 35px;
	background-attachment: inherit;
	word-spacing: normal;
	border:solid#FFF;
	border-radius: 15px;
	margin-top: 20px;
	margin-left: 6%;
}


.specialleft {
	position: absolute;
	color: #fff;
	font-family: "Times New Roman","宋体";
	font-size: 18px;
	width: 80%;
	text-align: center;
	margin-top: 6px;
	left:auto;
	right:auto;	
}
.centerleft {
	font-size: 14px;
	position: static;
	margin-left: 0;
	margin-top: 50px;
	width: 40%;
	height: 25px;
	font-family: "楷体","宋体";
	color: #7f7f7f;
}
.centerright {
	font-size: 14px;
	position: static;
	margin-left: 0;
	margin-top: 50px;
	width: 50%;
	height: 25px;
	font-family: "楷体","宋体";
	color: #7f7f7f;
}
.bookinfo{
	position: absolute;
	color: #fff;
	width: 100%;
	text-align: left;
	margin-top: 70px;
	left:auto;
	right:auto;	
}




</style>
</head>
<body class="bodycss"  >



    <div class="borrowhistory" >
        <a class="specialleft">我的借阅历史</a> 
    </div>
    <s:if test="#request.bookList!=null && #request.bookList.size()>0">
	<s:iterator value="%{#request.bookList}" var="book" status="st">
    <div class="divback" >
        <div class="left">  
           <img src="<s:property value="%{#book.image}"/>" class="divimg" />
        </div>
        
        <div class="center"> 
       		<div>
            <a class="bookname"><s:property value="%{#book.bookName}"/></a>
             
        	</div>
            
        	<div class="bookinfo">       
                <a class="centerleft">借书:<s:property value="%{#book.borrowdate}"/></a>
       
            	<a class="centerright">还书:<s:property value="%{#book.backdate}"/></a>        
            </div>
        </div>
        
    </div> 
    
</s:iterator>
		</s:if>
   
</body>
</html>

