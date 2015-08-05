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
		<title>武研图书角-个人借阅历史</title>	
		<script language="javascript" src="/script/function.js"></script>
		<script language="javascript" src="/script/page.js"></script>
		<script language="javascript" src="/script/validate.js"></script>
		<script language="javascript" src="/script/pub.js"></script>			
		 <script > 
 		
     </script>
	</HEAD>		
	<body onLoad="init()">
		
		<s:form id="Form2" name="Form2"  method="post">		
		     <table cellSpacing="1" cellPadding="0" width="100%" bgColor="#f5fafe" border="0">
			<TBODY>						
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe" colspan="5">			
						<table width="100%" border="1" cellpadding="1" cellspacing="0" bordercolor="gray" rules="all" id="DataGrid1">
							<tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
							    <td align="center" width="10%" height=50 background="/images/tablehead.jpg">书名</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">借阅时间</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">还书时间</td>				
							</tr>
							<s:if test="#request.userList!=null && #request.userList.size()>0">
								<s:iterator value="%{#request.userList}" var="user" status="st">							
									<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#user.bookname}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#user.borrowdate}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#user.backdate}"/></a>
										</td>									
                                       
									</tr>
								</s:iterator>
							</s:if>
						</table>		
					</td>
				</tr> 
				
				
			</TBODY>
		</table>
		</s:form>
	</body>
</HTML>
