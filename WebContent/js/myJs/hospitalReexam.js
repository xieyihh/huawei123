	
	var id="";
	var usernumber="";
	var username="";
	var physicalnumber="";
	var physicalstate="";
	var physicalplan="";
	
	$(function() { 
		
		bindData();
		//核查体检基本信息
		$("#checkData").click(function() {
			var workNumber = $("input#workNumber").val(); 
			if(workNumber==""){
				alert("请填写工号!");
				return false;
			}
//			var plan=$("#physicalPlan").val();
//			if(plan==""){
//				alert("请选择次体检批次");
//				return false;
//			}
			//http://localhost:8080/mini/getUsernameByusernumber.action?usermunber=2115323&physicalplan=15年上半年
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "getUsernameByusernumber.action", 
				data: {"usernumber": workNumber}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) {
					if(msg.message!="success"){
						if($("#addMessage").length >0){
							$('#addMessage').remove();
						}
						alert(msg.message); 
					}else{
						if($("#addMessage").length >0){
							$('#addMessage').remove();
						}
						physicalstate=msg.context.physicalexamForm.physicalstate;
					
					id=msg.context.physicalexamForm.id;
					username=msg.context.physicalexamForm.username;
					
					physicalnumber=msg.context.physicalexamForm.physicalnumber;
					usernumber=workNumber;
					var name='<br><div  id="addMessage">'+
								'<div class="form-group" style="margin-right:0px">'+
									'<p class="form-control-static"  >姓名：' +username+'&nbsp;&nbsp;</p>'+
								'</div>'+
								'<div class="form-group" style="margin-right:0px">'+
									'<p class="form-control-static"  >体检日期：' +msg.context.physicalexamForm.physicaldate+'&nbsp;&nbsp;</p>'+
								'</div>'+
//								'<div class="form-group" style="margin-right:0px">'+
//									'<p class="form-control-static"  >体检号：' +physicalnumber+'&nbsp;&nbsp;</p>'+
//								'</div>'+
							 '</div>';
					$('#groups').append(name);
					}
				}, 
				error:function(msg){
					
				}
				});   
		});
		
		$("#saveData").click(function() { 
			if(id==""){
				alert("请核对体检者基本信息");
				return false;
			}
			var content=$('#reviewReport').val();
			if(content==""){
				alert("请填写复查信息");
				return false;
			}
			if(physicalstate!='已体检'){
				alert("此员工并未体检，请核对工号及体检批次");
				return false;
			}
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "savePhysicalreview.action", 
				data: {"username": username,'usernumber':usernumber,'reviewcontent':content}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) { 
					if(msg.message!="success"){
						alert('error')
					}else if(msg.context=="haveimport"){
						alert("该员工此次复查信息已添加");
					}else{
						
						alert("复查信息添加成功");
						//window.location.href="physicalReview.jsp"
					}
						
				}, 
				error:function(msg){
					alert(msg.message); 
				}
				}); 	
			
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
			
		}, 
		error: function() { 
			
			alert("加载数据失败"); 
		} 
	}); 
}	