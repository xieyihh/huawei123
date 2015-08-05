<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.mini.util.PageBean"%>

<HTML>
	<HEAD>
		<title>武研图书角-图书借阅历史</title>	
		<script language="javascript" src="/script/function.js"></script>
		<script language="javascript" src="/script/page.js"></script>
		<script language="javascript" src="script/validate.js"></script>
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
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">二维码</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">借书地点</td>	
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">还书地点</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">借书时间</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">还书时间</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">借阅人</td>
								
							</tr>
							<s:if test="#request.bookList!=null && #request.bookList.size()>0">
								<s:iterator value="%{#request.bookList}" var="book" status="st">							
									<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.bookname}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.onlysign}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.borrowposition}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.backposition}"/></a>
										</td>
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.borrowdate}"/></a>
										</td>
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.backdate}"/></a>
										</td>
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#book.borrower}"/></a>
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
