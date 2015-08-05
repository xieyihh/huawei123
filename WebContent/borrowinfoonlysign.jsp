<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<script language="javascript"> 
  function borrow(){
	
  	document.Form1.action="borrowbook.action?"+"${bookList.isbn10}";
  	document.Form1.submit();
  }
  function back(){
	
  	document.Form1.action="backbook.action";
  	document.Form1.submit();
  }
  </script>
</head>
<body>
 <div data-role="page" id="pagetwo">
  
  <h1>显示借阅书籍的二维码</h1>
  
  <div data-role="content">
  	<s:if test="#request.signList!=null && #request.signList.size()>0">
      	<s:iterator value="%{#request.signList}" var="onlysign">
      	 <div data-role="navbar">
	      <ul>
	        <li>
		        <a href="clickborrowbook.action?onlysign=<s:property value="%{#onlysign}"/>"><s:property value="%{#onlysign}"/>		        
	         </a> 		       
	     	</li>	     	     
      	</ul>
      	 </div> 
      	</s:iterator>
      </s:if> 
     </div>   
  </div>
 
</div> 

</body>
</html>