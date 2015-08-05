
	$(function() { 
		
	
		/**
		 * 保存体检信息
		 */
		

		//批量增加
		$("#batchData").click(function() { 
			if($("#file").val()==""){
				alert("请选择上传文件!");
				return false;
			}
			
			
			var form = document.getElementById("bookForm");
			   form.submit();
			
		});
		

		$("#file").change(function() {
			 var objtype=$("#file").val().substring($("#file").val().lastIndexOf(".")).toLowerCase();
			 if(objtype!=".csv"){
				 alert("请选择.csv文件");
				 $("#file").val("");
				 return false;
			 }
		});
		$("button#downloadBookTemplates").click(function() { 
			
			var form=$("<form>");//定义一个form表单
			form.attr("style","display:none");
			form.attr("target","");
			form.attr("method","post");
			form.attr("action","downloadimportbooktemplates.action");
			$("body").append(form);//将表单放置在web中
			
			form.submit();//表单提交 
			form.remove();
			});  

		
	});
	


