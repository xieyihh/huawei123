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
</head>
<body>

<div data-role="page" id="pageone">
  <div data-role="header" >
  <h1>书籍信息</h1>
  </div>	 
  <div data-role="content" >
	 <table width="100%">
		<tr >
		 <td enter" width=50 height=22>书名：	
	     <td align="center" width=220 height=22>	
	     <s:property value="#request.bookList.name"/>								
		 </td>	     					
		</tr>
	    <tr>
		 <td align="center" width=50 height=67>图片：</td>		
	     <td align="center" width=220 height=67>	         
	     <img src="<s:property value="#request.bookList.image"/>" />
	    	
	     </td>	
		</tr>
		<!-- 
	    <tr>
		 <td align="center" width=50 height=22>价格：</td>		
	     <td align="center" width=220 height=22>		
	     <s:property value="#request.bookList.price"/>	
	     </td>	
		</tr> -->
	    <tr>
		 <td align="center" width=50 height=22>作者：</td>		
	     <td align="center" width=220 height=22>		
	     <s:property value="#request.bookList.auto"/>	
	     </td>	
		</tr>
	    <tr>	     
		 <td align="center" width=50 height=67>总结：</td>		
	     <td align="center"  height=67 >	    
			<s:property value="#request.bookList.summary"/>			
	     </td>	
		</tr>
	</table>

 </div>
</div>

</html>