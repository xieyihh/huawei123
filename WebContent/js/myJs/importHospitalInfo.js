	var usernumber="";
	var physicalplan="";
	var id="";
	$(function() { 
		bindData();
	
		//增加一个体检信息
//		 $("input#workNumber,#physicalPlanSin").change(function(){
//			 var workNumber = $("input#workNumber").val(); 
//				if(workNumber==""){
//					alert("请填写工号!");
//					return false;
//				}
//				var plan=$("#physicalPlanSin").val();
//				if(plan==""){
//					alert("请选择次体检批次");
//					return false;
//				}
//				//http://localhost:8080/mini/getUsernameByusernumber.action?usermunber=2115323&physicalplan=15年上半年
//				$.ajax({ 
//					type: "get", 
//					dataType: "json", 
//					url: "getUsernameByusernumber.action", 
//					data: {"usernumber": workNumber,"physicalplan":plan}, //"wherePageCount" + where,个人建议不用这种方式 
//					async: false, 
//					success: function(msg) {
//						if(msg.message!="success"){
//							alert(msg.message); 
//						}else{
//						id=msg.context.physicalexamForm.id;
//						//alert(id)
//						var name='<div class="form-group" style="margin-right:0px">'+
//									'<p class="form-control-static"  >' + "姓名:  " +msg.context.physicalexamForm.username+'</p>'+
//								'</div>'
//						$('#groups').append(name);
//						}
//					}, 
//					error:function(msg){
//						
//					}
//					}); 
//		 })
		
		/**
		 * 点击回车执行
		 * **/
//		document.onkeypress=function(){
//		  if(event.keyCode==13){//13 回车键
//			var workNumber = $("input#workNumber").val(); 
//			if(workNumber==""){
//				alert("请填写工号!");
//				return false;
//			}
//			var plan=$("#physicalPlanSin").val();
//			if(plan==""){
//				alert("请选择次体检批次");
//				return false;
//			}
//			//http://localhost:8080/mini/getUsernameByusernumber.action?usermunber=2115323&physicalplan=15年上半年
//			$.ajax({ 
//				type: "get", 
//				dataType: "json", 
//				url: "getUsernameByusernumber.action", 
//				data: {"usernumber": workNumber,"physicalplan":plan}, //"wherePageCount" + where,个人建议不用这种方式 
//				async: false, 
//				success: function(msg) {
//					if(msg.message!="success"){
//						if($("#addName").length >0){
//							$('#addName').remove();
//						}
//						alert(msg.message); 
//					}else{
//						if($("#addName").length >0){
//							$('#addName').remove();
//						}
//					id=msg.context.physicalexamForm.id;
//					//alert(id)
//					var name='<div id="addName"class="form-group" style="margin-right:0px">'+
//								'<p class="form-control-static"  >' + "姓名:  " +msg.context.physicalexamForm.username+'</p>'+
//							'</div>'
//					$('#groups').append(name);
//					}
//				}, 
//				error:function(msg){
//					
//				}
//				});   
//		  }
//		}
		
	
		/**
		 * 保存体检信息
		 */
		

		//批量增加
		$("#batchData").click(function() { 
			if($("#file").val()==""){
				alert("请选择上传文件!");
				return false;
			}
			
			if($("#physicalPlanBat").val()==""){
				alert("请选择次体检批次");
				return false;
			}
			var form = document.getElementById("hospitalForm");
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
		
		$("button#downloadPhysicaldateTemplates").click(function() { 
			
			var form=$("<form>");//定义一个form表单
			form.attr("style","display:none");
			form.attr("target","");
			form.attr("method","post");
			form.attr("action","downloadphysicaldatetemplates.action");
			$("body").append(form);//将表单放置在web中
			
			form.submit();//表单提交 
			form.remove();
			});  

		
		//批量增加复查信息
		$("#batchReview").click(function() { 
			if($("#file2").val()==""){
				alert("请选择上传文件!");
				return false;
			}
			
			if($("#physicalPlanBat2").val()==""){
				alert("请选择次体检批次");
				return false;
			}
			var form = document.getElementById("ReviewForm");
			   form.submit();
			
		});
		

		$("#file2").change(function() {
			 var objtype=$("#file2").val().substring($("#file2").val().lastIndexOf(".")).toLowerCase();
			 if(objtype!=".csv"){
				 alert("请选择.csv文件");
				 $("#file2").val("");
				 return false;
			 }
		});
		
		$("button#downloadReviewTemplates").click(function() { 
			
			var form=$("<form>");//定义一个form表单
			form.attr("style","display:none");
			form.attr("target","");
			form.attr("method","post");
			form.attr("action","downloadphysicalreviewtemplates.action");
			$("body").append(form);//将表单放置在web中
			
			form.submit();//表单提交 
			form.remove();
			});  

		
	});
	
function bindData(){
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getphysicalplan.action", //要访问的后台地址 
		data: { }, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			$.each(msg.context.physical_plan, function(i, item) {
				 var count=0;
				 var sel = document.getElementById("physicalPlanBat");
				$("#physicalPlanBat option").each(function(){ //遍历全部option
				        var txt = $(this).text(); //获取option的内容
				        //alert(txt);
				         if(item!=txt){
							count++;
						}
				     });
				 
				 if(count==sel.options.length){
					 $("select#physicalPlanBat").append(' <option value='+(i+1)+'>'+item+'</option>');
					
				 }
					
				});
			$.each(msg.context.physical_plan, function(i, item) {
				 var count=0;
				 var sel = document.getElementById("physicalPlanBat2");
				$("#physicalPlanBat2 option").each(function(){ //遍历全部option
				        var txt = $(this).text(); //获取option的内容
				        //alert(txt);
				         if(item!=txt){
							count++;
						}
				     });
				 
				 if(count==sel.options.length){
					 $("select#physicalPlanBat2").append(' <option value='+(i+1)+'>'+item+'</option>');
					
				 }
					
				});
			
		}, 
		error: function() { 
			
			alert("加载数据失败"); 
		} 
	}); 
}	

