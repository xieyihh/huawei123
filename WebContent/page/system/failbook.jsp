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
		<title></title>	
		<script language="javascript" src="/script/function.js"></script>
		<script language="javascript" src="/script/page.js"></script>
		<script language="javascript" src="/script/validate.js"></script>
		<script language="javascript" src="/script/pub.js"></script>			
		 <script > 
 		
     </script>
	</HEAD>		
	<body onLoad="init()">
		<s:form id="Form1" name="Form1"  method="post" cssStyle="margin:0px;" >
		 
		  <table cellspacing="1" cellpadding="0" width="100%" align="center" bgcolor="#f5fafe" border="0">
		    <TR height=10><td></td></TR>
			  <tr>
				  <td height="17" colspan=2 align="center" background="../images/b-info.gif" class="ta_01">
					  <font face="宋体" size="8"><strong>下载失败的图书信息</strong></font>
				  </td>					
			</tr>
		</table>	
	</s:form>
		<s:form id="Form2" name="Form2"  method="post">		
		     <table cellSpacing="1" cellPadding="0" width="100%" bgColor="#f5fafe" border="0">
			<TBODY>						
				<tr>
					<td class="ta_01" align="center" bgColor="#f5fafe" colspan="5">			
						<table width="100%" border="1" cellpadding="1" cellspacing="0" bordercolor="gray" rules="all" id="DataGrid1">
							<tr style="FONT-WEIGHT:bold;FONT-SIZE:12pt;HEIGHT:25px;BACKGROUND-COLOR:#afd1f3">
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">书名</td>
								<td align="center" width="10%" height=50 background="/images/tablehead.jpg">isbn</td>
							</tr>
							<s:if test="#request.bookList!=null && #request.bookList.size()>0">
								<s:iterator value="%{#request.bookList}" var="user" status="st">							
									<tr onmouseover="this.style.backgroundColor = 'white'" onmouseout="this.style.backgroundColor = '#F5FAFE';">
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#user.name}"/></a>
										</td>	
										<td style="HEIGHT:50px" align="center" width="10%">																					
											<a><s:property value="%{#user.isbn10}"/></a>
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
