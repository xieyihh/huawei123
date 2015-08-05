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
<title>武研图书角-书籍信息</title>
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
	width: 90%;
	height: 2px;
	word-spacing: normal;
	border-radius: 30px;
	top: 220px;
	margin-left: 2%;
	left: 3px;
}
.divback {
	position: absolute;
	width: 90%;
	height: 200px;
	word-spacing: normal;
	border-radius: 30px;
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
	color: #fff;
	position: absolute;
	text-align:center;
	height: 31px;
	width:100%;
	font-family: "Times New Roman","华文新魏";
	margin-left: auto;
	margin-right: auto;
	font-size: 22px;
	white-space: nowrap;/*设置不折行*/
	overflow:hidden;
	text-overflow: ellipsis;
}
.bookinfo{
	color: #fff;
	position: absolute;
	font-family: "宋体";
	font-size: 15px;
	top: 30px;
	height: 168px;
	text-align:left;
	margin-left: auto;
	margin-top: auto;
	overflow:hidden;
	 line-height:24px;
	overflow:hidden;
	text-overflow: ellipsis;
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
	margin-left: 50%;
	font-style: normal;
	margin-top: 0;
	width: 50%;
	height: 200px;
}
.fenge2 {
	position: absolute;
	background-color: #fff;
	width: 90%;
	height: 2px;
	word-spacing: normal;
	border-radius: 30px;
	top: 270px;
	margin-left: 2%;
	left: 3px;
}
.author {
	color: #fff;
	width: 90%;
	height: 40px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	right: auto;
	left: auto;
	float: top;
	margin-top: 220px;
	margin-left: 1%;
	position: static;
	white-space: nowrap;/*设置不折行*/
	overflow:hidden;
	text-overflow: ellipsis;
}
.booklastnum {
	width: 90%;
	height: 40px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	right: auto;
	left: auto;
	float: top;
	margin-top: 20px;
	margin-left: 1%;
	position: static;
}
.special {
	position: static;
	width: 90%;
	height: 50px;
	background-attachment: inherit;
	word-spacing: normal;

	border:solid#FFF;
	border-radius: 15px;
	margin-top: 10px;
	margin-left: 1%;	


}
.lablecss1 {
	line-height:50px;
	width: 87%;
	font-size:18px;
	height: 50px;
	position: absolute;	
	background-image:url(images/null.png);
 	background-size: 100% 50px;
	background-color: transparent;
	color: #fff;
	-webkit-tap-highlight-color:rgba(0,0,0,0);
}
.lablecss2 {
	line-height:50px;
	width: 87%;
	font-size:18px;
	height: 50px;
	position: absolute;	
	background-image:url(images/all.png);
 	background-size: 100% 50px;
	background-color: transparent;
	-webkit-tap-highlight-color:rgba(0,0,0,0);
}

.specialleft {
	position: absolute;
	
	font-family: "Times New Roman","宋体";
	font-size: 20px;
	margin-left: 2%;
	margin-top: 13px;
	width: 50%;
	text-align:left;	
	color: #fff;
}
.choosebutton {
	color: #000;
	font-family: "Times New Roman","宋体";
	font-size: 20px;
	width: 100%;
	height: 50px;
	text-align: left;
 	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	float: top;	
	background-image:url(images/null.png);
 	background-size: 100% 50px;
	background-color: transparent;
}
.specialright {
	color: #000;
	font-family: "Times New Roman","宋体";
	font-size: 20px;
	margin-left: 60%;
	width: 40%;
	height:50px;
	text-align: left;

	
}
.radiocss {
	display:none;
	
}
.choosebutton1 {
	font-size: 20px;
	width: 100%;
	height:50px;
	text-align: left;
	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	float: top;
	background-image:url(images/all.png);
 	background-size: 100% 50px;
	background-color: transparent;
	
}
.borrowdiv {
	position: absolute;
	font-style: normal;
	margin-top: 0;
	width: 30%;
	height: 100%;
	margin-left: 10%;	
}
.backdiv {
	position: absolute;
	font-style: normal;
	margin-top: 0;
	width: 30%;
	height: 100%;
	margin-left: 60%;	
}

.borrowbutton {
	font-style: normal;
	font-size: 20px;
	margin-top: 0;
	width: 100%;
	height: 100%;
	background-image: url(images/borrowbutton.png);
	background-size: 100% 100%;
	border-radius: 30px;
	-webkit-tap-highlight-color:rgba(0,0,0,0);
	background-color: transparent;
	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	float: top;
}
.backbutton {
	font-style: normal;
	font-size: 20px;
	margin-top: 0;
	width: 100%;
	height: 100%;
	background-image: url(images/borrowbutton.png);
	background-size: 100% 100%;
	border-radius: 20px;
	-webkit-tap-highlight-color:rgba(0,0,0,0);
	background-color: transparent;
	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	float: top;
}
</style>

<script type="text/javascript">
function borrow(){
	
	
       document.forms.Form2.action="scanbook.action";
       document.forms.Form2.submit();
}
function back(){
	

    document.forms.Form2.action="clickbackbook.action";
    document.forms.Form2.submit();
}
</script>
</head>
<body class="bodycss"  >

<div class="divback"  >
    <div class="left">  
        <img src="<s:property value="#request.bookimage"/>" class="divimg" />
    </div>
    <div class="right"> 
        <a class="bookname">&nbsp;<s:property value="#request.bookname"/></a>    
        <br>
        <br>
		<a class="bookinfo">&nbsp;<s:property value="#request.booksummary"/></a>
        <br>
        <br>
    </div>
</div>




<div class="fenge" >	 
</div>

<div class="author" >
  <a class="specialleft">作者：<s:property value="#request.author"/> </a> 
</div>

<div class="fenge2" >	 
</div>

<div class="booklastnum" >
       <a class="specialleft">在册情况</a> 
</div>


<form id="Form2" name="Form2"  method="post" action="" onsubmit="return false;"  >		
<s:if test="#request.bookList!=null && #request.bookList.size()>0">
	<s:iterator value="%{#request.bookList}" var="book" status="st">

	<div class="special"  >
	
	<label for="<s:property value="%{#book.radioname}"/>" name="lastnumber" class="lablecss1"  onClick="changeclo(this)" > &nbsp;&nbsp;<s:property value="%{#book.infor}"/>
	 <input name="infolable" type="radio" id="<s:property value="%{#book.radioname}"/>" value="<s:property value="%{#book.lablevalue}"/>" class="radiocss"/>
	</label>
	 
	
	</div>

	</s:iterator>
</s:if>


	 
</div>

<input type="hidden" name="onlysign" id="onlysign" value="<s:property value="#request.onlysign"/>"/>

</form>	 



</body>
</html>

