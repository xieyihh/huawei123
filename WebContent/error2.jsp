<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>

<!DOCTYPE html>	
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>错误信息</title>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
.bodycss {
	
	border-color: #000;
	border-radius: 1px;
	font-size: 10px;
	background-size: cover;
	background-color:#f9fee8;
}
.errorDiv{
text-align:center;
width:30%;
margin:0 auto;
margin-top:20px;}

img{
margin:0 auto;
background-color:blue;
}
h1{
width:auto; 
display:inline-block !important; 
display:inline;

height:100px;
line-height:80px;}
</style>

</head>
<body class="bodycss"  >
	<div class="errorDiv">
    	<img src="images/error.png">
    	
    	<h1>没有权限访问</h1>
    	
    </div>
    

</body>
</html>

