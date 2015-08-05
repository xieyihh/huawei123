<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>
<!DOCTYPE html>	
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>无标题文档</title>
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

.divback {<!--书籍整个框-->
	width: 95%;
	height: 40px;
	
	word-spacing: normal;
	border-radius: 30px;
	margin-bottom:2%;
	margin-left:1%;

}
.divimg {<!--书的封面图-->
	width: 1px;
	height: 75px;
	word-spacing: normal;
	border-radius: 30px;
	position: absolute;
	margin-top: 2px;
	margin-left: 10px;
	border-color: #00F;
	border: 10;
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
.divimgright {<!--借阅图标-->
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
.bodycss {

	border-color: #000;
	border-radius: 1px;
	font-size: 10px;
	background-image: url(images/11.jpg);
	background-size: cover;
		
}
.position {<!--按地点-->
	color: #000;
	position:absolute;
	font-family: "楷体";
	font-size: 30px;
	margin-left:25%; 
	margin-top: 8px;
	height:30px;
	color: #ff0000;
}
.classifi {<!--按分类-->

	position: absolute;
	font-family: "Times New Roman","楷体";
	font-size: 30px;
	margin-left: 25%;
	margin-top: 8px;

	height:30px;
	color: #FFF;
}

.bookinfo{<!--简介-->
	font-size: 10px;
	margin-left: 10px;
  font-family: "楷体";
}

.left {<!--按地点的那个圆框-->
	background-attachment: absolute;
	position: absolute;
	margin-left: 0;
	font-style: normal;
	margin-top: 0px;
	width: 50%;
	height: 60px;

}
.right {<!--按分类的那个框-->
	background-attachment: absolute;
	position: absolute;
	margin-left: 40%;
	font-style: normal;
	margin-top: 0;
	width: 47%;
	height: 66px;
	left: 21px;
	top: 10px;
}
.searchinput {<!--你的提示文字-->
	border-radius: 30px;
	border-radius: 30px 0px 0px 30px;
	width: 55%;
	height: 100%;
	text-align: left;
	border-top: 0;
	border-right: 0;
	border-bottom: 0;
	border-left: 0;
	border:1px solid #cdcdcd;
	font-size: 15px;
	font-family: "Times New Roman","Tahoma","楷体","新楷体";
}
.searchbutton {<!--查询-->
	border-radius: 30px;
	border-radius: 30px 30px 30px 30px;
	width: 40%;
	height: 40px;
	border: 1px solid #cdcdcd;
	background-color: #0fff;
	margin-top:7%;
	margin-left: 25%;
	margin-bottom:6%;
	color: #806600;
	font-size: x-large;
	font-family:"Arial","Tahoma","华文楷体","楷体";

}
.center {<!--竖向分割线-->
	background-attachment: absolute;
	position: absolute;
	width: 5px;
	height: 45px;
	margin-left: 45%;
	background-color: #903;
}
.searchdiv {<!--提示文字和分类-->
	border-radius: 30px 30px 30px 30px;
	background-color: transparent;
	width: 50%;
	height:20px;
	margin-left: 20%;
}
.fenge {<!--横向线-->
	position: absolute;
	position: absolute;
	margin-left: 0px;
	background-color: #009;
	left: 1px;
	top: 94px;
	width: 95%;
	height: 4px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;

	position: static;
	margin-bottom: 2%;
	margin-left: 1%;	
}
.special {<!--计算机类的大框-->
	width: 95%;
	height: 40px;
	background-attachment: inherit;
	word-spacing: normal;
	border-radius: 30px;
	right: auto;
	left: auto;
	float: top;
	margin-bottom: 0%;
	margin-left: 1%;
	position: static;
}
.specialleft {<!--计算机类-->

	position: absolute;
	color: #FFF;
	font-size: 25px;
	margin-left: 10%;
	margin-top: 20px;
	left: -2px;
	top: -20px;
}
.specialright {<!--箭头那个框-->
	position:absolute;
	margin-left:0px; 
	margin-top: 0px;
}
.classfileft {<!--计算机类的那个圆框-->
	background-attachment: absolute;
	position: absolute;
	margin-left: 0px;
	margin-top: 0px;
	font-style: normal;
	width: 70%;
	height: 65px;
}

.classfiright {<!--箭头的那个框-->
	background-attachment: absolute;
	position: absolute;
	margin-left: 70%;
	width: 27%;
	height: 85px;
}
.txt{ }
		.btn{ position:absolute; background-color:#FFF; border:1px solid #CDCDCD;height:24px; width:70px;right:20px}	
</style>
</head>
<body class="bodycss"  >
<div class="divback" >
	 <div class="left" >  
        <a class="position" id="a1" onclick="changeclo1(this)" >按地点</a> 
    </div>
    <div class="center"> 
      
    </div>
    <div class="right">  
        <a class="classifi" id="a2" onclick="changeclo2(this)">按分类</a> 
    </div>     
</div>

<div class="fenge" >	 
</div>

<div class="special" >
  <div class="classfileft" >  
        <a class="specialleft">计算机类</a> 
  </div>
 
  <div class="classfiright">  
    <a href=""> 
     <img src="images/arrow.png" width="25" height="35" class="specialright" />
     </a>
   </div>
	 
</div>
<div class="fenge" >	 
</div>
<div class="special" >
  <div class="classfileft" >  
        <a class="specialleft">计算机类</a> 
  </div>
 
  <div class="classfiright">  
    <a href=""> 
     <img src="images/arrow.png" width="25" height="35" class="specialright" />
     </a>
   </div>
	 
</div>
<div class="fenge" >	 
</div>
	 
</div>
</body>
</html>

