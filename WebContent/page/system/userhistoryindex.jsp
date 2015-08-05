<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>

 <script language="javascript"> 
 	var msg = "${downallresult}"; 
 	if(msg!=" "&&msg!= null&&msg!=""){
	 alert(msg);
	 } 
  function exportExcel(){
    var path = 'exportExcel.action';
  	openWindow(path,'700','400');
  }
 
  function downloadall(){
	  		document.Form1.action="downallbookinfo.action";
	  		document.Form1.submit()
	  }
  </script>
<HTML>
	<HEAD>
		<title>人员管理</title>	
		<link href="common/style.css" rel="stylesheet" type="text/css" />
		<script language="javascript" src="/script/function.js"></script>
		<script language="javascript" src="/script/page.js"></script>
		<script language="javascript" src="/script/validate.js"></script>
		<script language="javascript" src="/script/pub.js"></script>			
		 <script > 
 		
     </script>
	</HEAD>		
	<body onLoad="init()" id="right" class="main1">
		<div class="ttitle">
			<h1>【人员管理】</h1>
		</div>
		<s:form id="Form1" name="Form1"  method="post" cssStyle="margin:0px;" >
			<div class="linebox botm01"><strong>帐号检索</strong>
			   <input name="username" id="username" size="21"/>
			  <input name="BT_find" type="button" class="btn1"  onclick="gotoquery('userborrowhistory.action')"/>
			</div>		 
				  <input type="hidden" name="initflag" id="initflag" value="1"/>
				  <input type="hidden" name="pageNO" id="pageNO" value=""/>
				  <input type="hidden" name="pageSize" id="pageSize" value=""/>		
		</s:form>		
		<s:form id="Form2" name="Form2"  method="post">		
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#BBC0C6" class="tableone botm01">
			  <tr class="title" >
				      <td width="10%">帐号</td>
					  <td width="10%">工号</td>
					  <td width="10%">电话</td>
					  <td width="10%">借阅历史</td>	
  			</tr>
  			<s:if test="#request.userList!=null && #request.userList.size()>0">
				<s:iterator value="%{#request.userList}" var="user" status="st">							
					<tr style="HEIGHT:50px" align="center" >
						<td  width="10%" style="color: #000">																					
							<s:property value="%{#user.username}"/>
						</td>	
						<td style="color: #000">																					
							<a><s:property value="%{#user.number}"/>
						</td>
						<td style="color: #000">																					
							<s:property value="%{#user.phone}"/>
						</td>
						<td style="color: #000">																					
							<a href="#" onclick="openWindow('personborrowhistory.action?userid=<s:property value="%{#user.userid}"/>','700','400');">
							借阅历史
							</a>
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
		</s:form>
	</body>
</HTML>
