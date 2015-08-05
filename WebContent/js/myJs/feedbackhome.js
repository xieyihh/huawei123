$(function() { 
	$("input#feedbackexport").click(function() { 
		var form=$("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","feedbackexport.action");
		
		$("body").append(form);//将表单放置在web中
		
		form.submit();//表单提交 
	});  
	
});