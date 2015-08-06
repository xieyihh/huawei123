<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
  <script language="javascript"> 
   function checkchar(){
	   alert("YES");
  document.Form1.action="importexcel.action";
  document.Form1.submit();
  alert(" 保存成功!");
  }

  </script>
</head>
<body>
<form name="Form1" id="Form1" method="post">
<input type="file" id="file" name="file"/>&nbsp;&nbsp;  
<input type="button"  value="导入Excel" onclick="checkchar()"/>  
<img alt="" src="problemimage/1.jpg">
</form>
</body>
</html>