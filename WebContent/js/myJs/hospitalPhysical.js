	var usernumber="";

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
		 * 医生输入部分工号后选择某一个
		 * 
		 */
		$(document).on("click","[id^='number']",function(){//修改成这样的写法
			var listNum=$(this).attr("id").toString().slice(6);
			$("#workNumber").val(listNum);
			$(".tableone tr").removeClass("trChoose");
			$(this).addClass("trChoose");
		  });
		/**
		 * 查看工号相关信息 Dom事件
		 */
//		$("select#physicalPlanSin").change(function(){
//			var workNumber = $("#workNumber").val();
//			if(workNumber.length<7||workNumber.length>8){
//				return false;
//			}
//			var plan=$("#physicalPlanSin").val();
//			if(plan===""){
//				alert("请选择次体检批次");
//				return false;
//			}
//			
//			checkData(workNumber,plan);
//		});
		var number = $(".main_content form #innerdiv .form-group input[name='workNumber']");
		var workNumberObject=document.getElementById('workNumber');
	//	if(workNumberObject.addEventListener){
		workNumberObject.addEventListener('input', function (){
				
				var workNumber = workNumberObject.value;
				var re = /^\d+$/;
				if(!re.test(workNumber)){
					inputPopover(number,"请输入数字", 2); 
					return false;
				}
				if(workNumber.length<7||workNumber.length>8){
					return false;
				}else{
					
					//http://localhost:8080/mini/getUsernameByusernumber.action?usermunber=2115323&physicalplan=1
					checkData(workNumber);
					
				}
				
		},false);
//		} else{
//			workNumberObject.attachEvent('input', function (){
//				alert(2)
//				var workNumber = workNumberObject.value;
//				var re = /^\d+$/;
//				if(!re.test(workNumber)){
//					inputPopover(number,"请输入数字", 2); 
//					return false;
//				}
//				if(workNumber.length<7||workNumber.length>8){
//					return false;
//				}else{
//					
//					//http://localhost:8080/mini/getUsernameByusernumber.action?usermunber=2115323&physicalplan=1
//					checkData(workNumber);
//					
//				}
//				
//		});
//		}
		/**
		 * 保存体检信息
		 */
		$("#singleData").click(function() {
			
			var number = $(".main_content form #innerdiv .form-group input[name='workNumber']");
			var workNumber = $("input#workNumber").val(); 
			if(workNumber===""){
				inputPopover(number,"请输入工号", 2); 
				return false;
			}
			if(workNumber.length!==8){
				inputPopover(number,"请填写8位工号!", 2); 
				return false;
			}	
			
			$('table#userinfo').css('display','none')	;
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "getUsernameByusernumber.action", 
				data: {"usernumber": workNumber}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) {
					if(msg.message!=="success"){
						alert(msg.message);
						return false;
					}else{
						
					id=msg.context.physicalexamForm.id;
					if(id===""){
							//alert("请在工号文本框中点击enter核对体检者姓名信息");
							return false;
						}
						
						$.ajax({ 
							type: "get", 
							dataType: "json", 
							url: "savePhysicaldate.action", 
							data: {"id": id}, //"wherePageCount" + where,个人建议不用这种方式 
							async: false, 
							success: function(msg) {
								if(msg.context==="hasimport"){
									alert("该员工之前已完成体检");
								}else{
									alert("完成体检信息保存");
								}
									
							}, 
							error:function(msg){
								alert(msg.message); 
							}
							}); 	
					}
					
				}, 
				error:function(msg){
					
				}
				}); 
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

		
	});
	
function bindData(){
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "searchphsicalinit.action", //要访问的后台地址 
		data: { }, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			$('#physicalPlan').text("体检批次："+msg.context.physicalinit.physicalplan);
			$('#physicalPosition').text("体检地点："+msg.context.position);
		}, 
		error: function() { 
			
			alert("加载数据失败"); 
		} 
	}); 
}	

function checkData(workNumber){
	//alert(workNumber)
	$.ajax({ 
		type: "get", 
		dataType: "json", 
		url: "getUsernameByusernumberlike.action", 
		data: {"usernumber": workNumber}, //"wherePageCount" + where,个人建议不用这种方式 
		async: false, 
		success: function(msg) {
			if(msg.message==="noUser"){
				$('table#userinfo').css('display','none')	;
				$('div.nouser').css('display','block');
			}else if(msg.message==="success"){
				$('div.nouser').css('display','none');
				if (msg.length !== 0) { 
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length !== 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
				} 
			
				if(msg.context.physicalexamForm.length!==0){
					$('table#userinfo').css('display','inline-table')	;
					$.each(msg.context.physicalexamForm, function(i, item) {
						var bodyContent='<tr id="number'+item.usernumber+'">'+
											'<td  >' + item.usernumber + '</td>'+
											'<td  >' + item.username + '</td>' +
										//	'<td width="20%">' + item.physicalnumber+ '</td>'+
										//	'<td width="20%" >' + item.physicalposition + '</td>'+
											'<td  >' + item.physicalreservedate + '</td>' + 
											'<td  >' + item.physicalstate + '</td>'+
										'</tr>';
						
						
						$("#tb_body").append(bodyContent);
					});
					$(".tableone tr:nth-child(even)").addClass("trOdd");
					$("td").addClass("bookList");
				}else{
					$('table#userinfo').css('display','none')	;
				}
			}else{
				alert(msg.message);
				return false;
			}
		}, 
		error:function(msg){
			
		}
		}); 
}