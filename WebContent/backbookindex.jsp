<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.css">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<script src="http://code.jquery.com/mobile/1.3.2/jquery.mobile-1.3.2.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1" />
</head>
<body>
<div data-role="page" id="pageone">
  <div data-role="header" >
  <h1>搜索</h1>
  </div>
 <div data-role="content">
    <form method="post" action="getallbookinfo.action">  	
      <s:if test="#request.backbookList!=null && #request.backbookList.size()>0">
      	<s:iterator value="%{#request.backbookList}" var="book">
      	 <div data-role="navbar">
	      <ul>
	        <li>		        
		        <a href="clickbackbook.action?onlysign=<s:property value="%{#book.onlysign}"/> ">
		        二维码：<s:property value="%{#book.onlysign}"/>  &nbsp &nbsp  &nbsp &nbsp&nbsp &nbsp    
		         书名：<s:property value="%{#book.name}"/>
		        
		        </a>
	     	</li>
	     	       
      	</ul>
      	 </div> 
      	</s:iterator>
      </s:if>    
     
    </form>
  </div>
</div>




</body>
</html>
