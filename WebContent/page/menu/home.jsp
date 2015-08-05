<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>吾爱吾家管理后台</title>
<link rel="ICON NAME" href="huawei.ico"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<!--  <meta http-equiv="pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate"/>
<meta http-equiv="expires" content="0"/>-->

<script src="bootstrap/jquery.min.js"></script>
<script type="text/javascript">
var books=["bookinfo.jsp","importbook.jsp","bookRanking.jsp"];
var physicals=['physicalInfo.jsp','importPhysicalInfo.jsp','importHospitalInfo.jsp','hospitalInfo.jsp','hospitalReexamInfo.jsp','physicalReview.jsp','physicalInit.jsp'];
var activitys=['activityLaunch.jsp','activityInit.jsp','activityStatus.jsp'];
var feeaback=['feedbackhome.action','showFeedBack.jsp'];
var superAdmins=['authority.jsp','authorityPage.jsp','bookdictionary.action'];

var data=[books,physicals,activitys,feeaback,superAdmins];

	$(function() { 
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getuserrolepopedomBylogin.action", //要访问的后台地址 
			data: { }, //要发送的数据 
			async:false,
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message=="success"){
					var bodyContent="";
					$.each(msg.context.rolelist, function(i, item) {
						//alert(item.popedomcode);
						var arr=item.popedomcode.split(";")
						var  count=0;
						for(var i=0;i<arr.length;i++){
							if(arr[i]!=""){
								var content=arr[i].split(',');
								$('#mainFrame').attr('src',data[i][content[0]-1]);
								break;	
							}else{
								count++;
							}
						}
						if(count==arr.length){
							$('#mainFrame').attr('src',"error2.jsp");
						}
						
					 });
				}
				 
				
			},
			error: function() { 
				
				alert("加载数据失败1"); 
			} 
					
		});
	});
	

</script>
</head>

<frameset rows="97,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="page/menu/title.jsp" name="topFrame" scrolling="no" noresize="noresize" id="topFrame" />
  <frameset rows="*" cols="160,10,*" framespacing="0" frameborder="no" border="0" id="leftmenu">
    <frame src="menuleft.action" name="leftFrame" scrolling="no" noresize="noresize" id="leftFrame" />
      <frame src="btn.htm" name="btnFrame" scrolling="no" noresize="noresize" id="btnFrame" />
      <frame src="#" name="mainFrame" scrolling="auto" noresize="noresize" id="mainFrame" />
  </frameset>
</frameset>
<noframes><body>
</body>
</noframes></html>
