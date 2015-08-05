
	$(function() { 
		
		 
		initData();
		
	//查询 
		$("button#SaveInit").click(function() { 
			
			var plan =$("select#physicalPlan option:checked").val(); 
			var nophysicalcontentinfor=$('#nophysicalcontentinfor').val();
			var physicaldateedit=$('#physicaldateedit').val();
			//var number = $("textarea[id='nophysicalcontentinfor']");
			if(nophysicalcontentinfor===""){
				alert("请输入提示信息"); 
				return false;
			}
			//var date = $("textarea[id='physicaldateedit']");
			if(physicaldateedit===""){
				alert("请输入提示信息"); 
				return false;
			}
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "savephsicalinit.action", //要访问的后台地址 
				data: {"physicalplan":plan,"nophysicalcontentinfor":nophysicalcontentinfor,"physicaldateedit":physicaldateedit}, //要发送的数据 
				success: function(msg) {//msg为返回的数据，在这里做数据绑定 
					if(msg.message=="success"){
						alert('保存成功！');
					}
				}, 
				error: function() { 
					
					alert("保存数据失败"); 
				} //加载失败，请求错误处理 
				//ajaxStop:$("#load").hide() 
			}); 
		});  
		
	
		

		
	});
	
	
	//AJAX方法取得数据并初始化显示到页面上 
	
	function initData() { 
		
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "geteditphysicalinit.action", //要访问的后台地址 
			data: { }, //要发送的数据 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				var initdata=[];
				$.each(msg.context.physicalexamForm, function(i, item) {
					 	initdata[i]=item.physicalplan;
					});
					 
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
						 if(i===(initdata[0]-1)){
							$("select#physicalPlan").append(' <option value='+(i+1)+' selected="selected">'+item+'</option>');
						}else{
							$("select#physicalPlan").append(' <option value='+(i+1)+'>'+item+'</option>');
						}
					}
						
					});
				$('#nophysicalcontentinfor').val(initdata[1]);
				$('#physicaldateedit').val(initdata[2]);
			}, 
			error: function() { 
				
				alert("加载数据失败"); 
			}
		}); 
		
		
	} 
