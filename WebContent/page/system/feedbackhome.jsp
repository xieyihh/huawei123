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
		<title>反馈信息</title>	
		<script language="javascript" src="/script/function.js"></script>
		<script language="javascript" src="/script/page.js"></script>
		<script language="javascript" src="/script/validate.js"></script>
		<script language="javascript" src="/script/pub.js"></script>
		
		 <script src="bootstrap/jquery.min.js"></script>
		  <script type="text/javascript" src="js/myJs/feedbackhome.js"></script>
   
		 <link href="common/style.css" rel="stylesheet" type="text/css" />		
	</HEAD>		
	<body onLoad="init()" id="right" class="main1">
	<div class="ttitle" >
		<h1 style="float:left">【反馈信息】</h1><input id="feedbackexport" type="button" style="float:right;margin-right:10%;margin-top:4px;" value="导出" />
	</div>
				
		<s:form id="Form2" name="Form2"  method="post">		
		<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#BBC0C6" class="tableone botm01">
			  <tr class="title" >
				         <td align="center" width="6%" >姓名</td>
						<td align="center" width="10%" >工号</td>
						<td align="center" width="10%" >反馈内容</td>
						<td align="center" width="10%"  >反馈时间</td>
  			</tr>
		   
		   <s:if test="#request.feedbacklist!=null && #request.feedbacklist.size()>0">
				<s:iterator value="%{#request.feedbacklist}" var="user" status="st">
					<tr style="HEIGHT:50px"  align="center">
						<td sstyle="color: #000" >																					
							<s:property value="%{#user.name}"/>
						</td>	
                        <td  style="color: #000" >
							<s:property value="%{#user.number}"/>
						</td>	
                         <td style="color: #000" >
							<s:property value="%{#user.content}"/>
						</td>	
                        <td style="color: #000" >
							<s:property value="%{#user.time}"/>
						</td>										
                                    
						
					</tr>
					</s:iterator>
			</s:if>
		 

</table>		     	

		</s:form>
	</body>
</HTML>
