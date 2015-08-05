<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>

<div id="module"  class="center">
<s:if test="#request.bookList!=null && #request.bookList.size()>0">
	<s:iterator value="%{#request.bookList}" var="book" status="st1">
	 
 	
  	<div class="divback" onclick="hrefaction(<s:property value="%{#book.isbn10}"/>)" >
      
          <div class="bookleft">  
                 <div class="bookleftinner" style="background-image:url('<s:property value="%{#book.image}"/>');">  
                  
          	</div>   
          </div>
            </a> 
        
	  <div class="bookcenter">  
                <div class="bookcentername" >  
                      <a class="booknamefont">&nbsp;<s:property value="%{#book.bookName}"/></a>    
            	</div>
                <div class="bookcentercontent" >  
                      <a class="bookcontentfont">&nbsp;<s:property value="%{#book.summary}"/></a>    
            	</div>     
            </div>
       </a> 
        
    
        
     
  </div>
 
  </s:iterator>
    </s:if>   
        
</div>