<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="com.mini.util.PageBean"%>	

<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#BBC0C6" class="tableone botm01">
			  <tr class="title" >
				      <td width="6%">isbn号</td>
					  <td width="10%">书名</td>
					  <td width="6%">图书来源</td>
					  <td width="15%">二维码</td>
					  <td width="9%">所属平台</td>
					  <td width="8%">导入时间</td>
					  <td width="3%">数量</td>
					  <td width="8%">编辑</td>					  
					  <td width="10%">下载</td>
					  <td width="9%">删除</td>
					  <td width="9%">剩余数量查询</td>
					  <td width="9%">借阅历史</td>
  			</tr>

		   
		    <s:if test="#request.bookList!=null && #request.bookList.size()>0">
					<s:iterator value="%{#request.bookList}" var="user" status="st">
					 <tr style="HEIGHT:50px">
						<td  align="center" style="color: #000">																					
							<s:property value="%{#user.isbn10}"/></a>
						</td>	
                        <td  align="center" style="color: #000" >
                        <!-- 
                                   	 <a href="#" onClick="openWindow('getdoubaninfor.action?isbn10=<s:property value="%{#user.isbn10}"/>','700','400');">
							 --><s:property value="%{#user.name}"/>
						</td>	
                       <td  align="center" style="color: #000">
							<s:property value="%{#user.source}"/>
						</td>	
                        <td  align="center" style="color: #000">
							<s:property value="%{#user.onlysign}"/>
						</td>										
                         <td align="center" style="color: #000">
							<s:property value="%{#user.position}"/>
						</td>                                        
                         <td  align="center"style="color: #000" >
							<s:property value="%{#user.importdate}"/>
						</td>
						<td  align="center" style="color: #000">
							<s:property value="%{#user.number}"/>
						</td>
						<td  align="center" style="color: #000">
							<a href="#" onclick="openWindow('getoneimportbookinfo.action?onlysign=<s:property value="%{#user.onlysign}"/>','700','400');">
							编辑
						</td>
						<td  align="center" style="color: #000" >
							<a href="#" onclick="openWindow('downloadbookinfo.action?isbn10=<s:property value="%{#user.isbn10}"/>','700','400');">
							下载
						</td>
						<td  align="center"style="color: #000" >
							<a href="deletebooknumber.action?onlysign=<s:property value="%{#user.onlysign}"/>&isbn10=<s:property value="%{#user.isbn10}"/>" onclick="return confirm('你确定要删除  <s:property value="%{#user.name}"/>？')">
																					
							<img src="images/delete.gif" width="16" height="16" border="0" style="CURSOR:hand"></a>												

						</td>
						<td " align="center" style="color: #000">
							<a href="#" onclick="openWindow('bookremainingamount.action?isbn10=<s:property value="%{#user.isbn10}"/>','700','400');">
							剩余数量
						</td>
						<td align="center" style="color: #000">
							<a href="#" onclick="openWindow('bookborrowhistory.action?onlysign=<s:property value="%{#user.onlysign}"/>','700','400');">
							借阅历史
						</td>
		 			</tr>
					</s:iterator>
			</s:if>
		 

</table>		     	
			
				<div class="linebox botm01">
			         <table border="0" width="100%" cellspacing="0" cellpadding="0">
			         <%PageBean pagebean=(PageBean)request.getAttribute("page");%>
			           <tr>
			             <td width="15%" align="left">总记录数：<%=pagebean.getTotalResult() %>条</td> 
			             <td width="14%" align="right"></td>      
			             <%if(pagebean.getFirstPage()){ %>           
			             <td width="8%" align="center">首页&nbsp;&nbsp;|</td>
			             <td width="10%" align="center">上一页&nbsp;&nbsp;&nbsp;|</td>
			             <%}else{ %>
			             <td width="8%" align="center"><u><a href="#" onClick="gotopage('getallbookinfo.action','start')">首页&nbsp;&nbsp;|</a></u></td>
			             <td width="10%" align="center"><u><a href="#" onClick="gotopage('getallbookinfo.action','prev')">上一页&nbsp;&nbsp;&nbsp;|</a></u></td>
			             <%} %>
			             <%if(pagebean.getLastPage()){ %>
						 <td width="10%" align="center">下一页&nbsp;&nbsp;&nbsp;|</td>
			             <td width="8%" align="center">末页</td>
			             <%}else{ %>
			             <td width="10%" align="center"><u><a href="#" onClick="gotopage('getallbookinfo.action','next')">下一页&nbsp;&nbsp;&nbsp;|</a></u></td>
			             <td width="8%" align="center"><u><a href="#" onClick="gotopage('getallbookinfo.action','end')">末页</a></u></td>
			             <%} %>
			             <td width="6%" align="center">第<%=pagebean.getPageNo() %>页</td>
			             <td width="6%" align="center">共<%=pagebean.getSumPage() %>页</td>
			             <td width="21%" align="right">至第<input size="1" type="text" name="goPage" >页		
			
			             <u><a href="#" onClick="gotopage('getallbookinfo.action','go')">确定</a></u></td>
			             
			             <td><input type="hidden" name="pageNO" value="<%=pagebean.getPageNo()%>" ></td> 
			             <td><input type="hidden" name="prevpageNO" value="<%=(pagebean.getPageNo()-1)%>"></td>
			             <td><input type="hidden" name="nextpageNO" value="<%=(pagebean.getPageNo()+1)%>"></td>
			             <td><input type="hidden" name="sumPage" value="<%=pagebean.getSumPage() %>" ></td>
			             <td><input type="hidden" name="pageSize" value="" ></td> 
			           </tr>
			         </table>       
			 
				</div>