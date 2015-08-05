
	$(function() { 
		
		
		//导入跳转
		$("#importData").click(function() { 
			if($("#file").val()==""){
				alert("请选择上传文件!");
				return false;
			}
			
			if($("#physicalPlan").val()==""){
				alert("请选择此次体检批次");
				return false;
			}
			var form = document.getElementById("importForm");
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
		
	$("button#downloadPhysicalTemplates").click(function() { 
		
		var form=$("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","downloadphysicaltemplates.action");
		$("body").append(form);//将表单放置在web中
		
		form.submit();//表单提交 
		form.remove();
		});  

		
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getphysicalplan.action", //要访问的后台地址 
			data: { }, //要发送的数据 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				$.each(msg.context.physical_plan, function(i, item) {
					 var count=0;
					 var sel = document.getElementById("physicalPlan");
					 $("#physicalPlan option").each(function(){ //遍历全部option
					        var txt = $(this).text(); //获取option的内容
					        //alert(txt);
					         if(item!=txt){
								count++;
							}
					     });
					 
					 if(count==sel.options.length){
						 $("select#physicalPlan").append(' <option value='+(i+1)+'>'+item+'</option>');
					 }
						
					});
				
			}, 
			error: function() { 
				
				alert("加载数据失败"); 
			} 
		}); 
	});
	
	