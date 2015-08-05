<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<%
String onlysign= request.getParameter("onlysign");
System.out.print(onlysign);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>书籍信息</title>
   <script language="javascript" src="script/function.js"></script>
    <script type="text/javascript" src="script/validate.js"></script>
   <script type="text/javascript" language="JavaScript" src="script/calendar.js" charset="utf-8"></script>
   <link rel="stylesheet" type="text/css" href="themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="themes/icon.css">

	<script language="javascript" src="js/myJs/jquery.min.js"></script>
	
		
	
<Script type="text/javascript">
//var Request = GetRequest();
var onlysign = '<%=onlysign%>';
$(function() { 
//alert('12');
//alert(onlysign);
BindData(); 

});

//AJAX方法取得数据并显示到页面上 
function BindData() { 

$.ajax({ 
	type: "get", 
	dataType: "json", 
	url: "getoneimportbookinfo.action", 
	data: {"onlysign": onlysign}, //"wherePageCount" + where,个人建议不用这种方式 
	async: false, 
	success: function(msg) { 
		
		var bookform= msg.context.bookform;
		//alert(bookform.id);
		
		$("input#id").val(bookform.id);
		$("input#isbn10").val(bookform.isbn10);
		$("input#onlysign").val(bookform.onlysign);
		$("input#bookName").val(bookform.bookName);
		$("input#source").val(bookform.source);
		$("input#auto").val(bookform.author);
		$("input#summary").val(bookform.summary);
		$("#image").attr('src',bookform.image); 
		
		 var position=bookform.position;
		 var classfi=bookform.classifi;
		// alert(classfi);
		 $.each(msg.context.positionList, function(i, item) {
				if(position==item.ddlCode){
				$("select#position").append(' <option value='+i+' selected="selected">'+item.ddlName+'</option>');
				}else{
					$("select#position").append(' <option value='+i+'>'+item.ddlName+'</option>');
				}
			});
		
		 $.each(msg.context.classifiList, function(i, item) {
			 if(classfi==item.ddlCode){
				$("select#classifi").append(' <option value='+i+' selected="selected">'+item.ddlName+'</option>');
			 }else{
				 $("select#classifi").append(' <option value='+i+'>'+item.ddlName+'</option>'); 
			 }
			});
	}, 
	error:function(msg){
		alert(msg); 
	}
	});
$("a#save").click(function() {
	alert('save');
	 save();
	 ///document.Form1.action="saveimportbookinfo.action";
	 //document.Form1.submit();	  
	 //window.close();  
}); 
} 

	function save(){  
		
		$.ajax({
            cache: true,
            type: "POST",
            url:"saveimportbookinfo.action",
            data:$('#form1').serialize(),// 你的formid
            async: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
               
            }
        });
	  
	   
	}

	
</script>


</head>
<body>
<form name="Form1" id="form1" method="post" action="saveimportbookinfo.action" enctype="multipart/form-data">
    	
	<div style="width: 600px;margin-right:auto; margin-left:auto; text-align:center">
	<h2 >书籍信息更改</h2>
	 <div class="easyui-panel"  style="width:600px;padding:30px 60px">
		<div style="margin-bottom:20px">
			<div>id:</div>
			<input class="easyui-textbox" type="text"name="id" id="id" style="width:100%;height:32px" readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<div>isbn10：</div>
			<input class="easyui-textbox" type="text"name="isbn10" id="isbn10"  style="width:100%;height:32px"readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<div>二维码：</div>
			<input class="easyui-textbox"  type="text" name="onlysign" id="onlysign" style="width:100%;height:32px"readonly="readonly">
		</div>
		<div style="margin-bottom:20px">
			<div>书名:</div>
			<input class="easyui-textbox"  type="text"name="bookName"  id="bookName" style="width:100%;height:32px">
		</div>
		<div style="margin-bottom:20px">
			<div>图书来源：</div>
			<input class="easyui-textbox"   type="text"name="source" id="source"  style="width:100%;height:32px">
		</div>		
		<div style="margin-bottom:20px" >
			<div>位置：</div>
			<select Style="width:100%;height:32px"  name="position" id="position" theme="css_xhtml"></select> 	
		</div>
		<div style="margin-bottom:20px">
			<div>分类：</div>
			<select Style="width:100%;height:32px"  name="classifi" id="classifi" theme="css_xhtml">
			</select> 	
		</div>	
		<div style="margin-bottom:20px">
			<div>作者：</div>
			<input class="easyui-textbox"  name="auto" id="auto" style="width:100%;height:32px">
		</div>	
		<div style="margin-bottom:20px">
			<div>总结：</div>
			<input class="easyui-textbox"  name="summary" data-options="multiline:true"  id="summary" style="width:100%;height:132px">
		</div>
		<div style="margin-bottom:20px">
			<div>图片：</div>
			<img  name="image"  id="image"/>
		</div>
		
			
	
		<div>
		
			<a href="javascript:saveimportbookinfo()" id="save" class="easyui-linkbutton" iconCls="icon-ok" style="width:100%;height:32px">保存</a>
		</div>
	</div>
	
	</div>
		
</form>

</body>
</html>