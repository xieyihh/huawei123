	
	$(function() { 
		
		initSelectData();
		
		
		
		$("select#activityLists").change(function(){
			var activity=$("select#activityLists option:selected").val();
			initData(activity); 
		});
		$("#addContent").click(function(){
			$('table').css("display","block");
			
			var num=$("#tb_body tr").length +1;
			//$("#tb_body").append('<tr><td width="40%" class="bookList">' + num + '</td><td width="60%"><input style="margin-left:25%;;width:50%" type="text" name="itemname"  id="listname" ></td></tr>'); 
			$("#tb_body").append('<tr class="newContent"><td width="30%" class="bookList">' + num + '</td><td width="30%" class="bookList">文本</td><td width="40%" class="add bookList"></td></tr>'); 
			 $(".tableone tr:nth-child(even)").addClass("trOdd");
			$("td.add").dblclick(function(){
				$(this).text("");
				var input = "<input type='text' id='addtext' style='margin-left:25%;width:50%'>"
				$(this).append(input);
				$("input").focus();
				$("input").blur(function(){
					if($(this).val()!=""){
						$(this).closest("td").text($(this).val());
					}else{
						$(this).remove();
						
					}
				})
			})
			
			$('#saveInitInfo').css("display","block");
		});
		$("#addFile").click(function(){
			$('table').css("display","block");
			//alert('add'+$("#tb_body tr").length);
			var num=$("#tb_body tr").length +1;
			//$("#tb_body").append('<tr><td width="40%" class="bookList">' + num + '</td><td width="60%"><input style="margin-left:25%;;width:50%" type="text" name="itemname"  id="listname" ></td></tr>'); 
			$("#tb_body").append('<tr class="newFile" ><td width="30%" class="bookList">' + num + '</td><td width="30%" class="bookList">文件</td><td width="40%" class="add bookList"></td></tr>'); 
			 $(".tableone tr:nth-child(even)").addClass("trOdd");
			$("td.add").dblclick(function(){
				$(this).text("");
				var input = "<input type='text' id='addtext' style='margin-left:25%;width:50%'>"
				$(this).append(input);
				$("input").focus();
				$("input").blur(function(){
					if($(this).val()!=""){
						$(this).closest("td").text($(this).val());
					}else{
						$(this).remove();
						
					}
				})
			})
			
			$('#saveInitInfo').css("display","block");
		});
		$(document).on("dblclick","[id^='changeItem']",function(){//修改成这样的写法
			var old=$(this).text();
			$(this).text("");
			var input = "<input type='text' id='addtext'style='margin-left:25%;width:50%' value="+old+" >"
				$(this).append(input);
				$("input").focus();
				$("input").blur(function(){
					if($(this).val()!=""){
						$(this).closest("td").text($(this).val());
					}else{
						$(this).remove();
//						alert('修改信息不能为空！');
//						$('#save').css("display","none");
//						return false;
						
					}
				})
				$('#saveInitInfo').css("display","block");
		});
		$("#saveInitInfo").click(function(){
			 var selectedVal= $("select#activityLists option:checked").val();
			 if(selectedVal===""||selectedVal===null){
				 alert("请输入类型名称");
				 return false;
			}else{
				var itemName = new Array();
				var itemFlag= new Array();
				var i=0;var j=0;
//				 $('#tb_body tr[class~="newFile"]').each(function () { 
//					 	var item=$(this).children('td').eq(2).html();
//					 	var fdStart = item.indexOf("<input");
//		                if(item!=""&&fdStart!=0){
//		                	itemFile[i++]=item;
//		                }
//		            });
				 $('#tb_body tr').each(function () { 
					 	var item=$(this).children('td').eq(2).html();
		                var fdStart = item.indexOf("<input");
		                if(item!=""&&fdStart!=0){
		                	if($(this).is(".newContent")){
		                		itemFlag[i++]=2;
		                	}else if($(this).hasClass("newFile")){
		                		itemFlag[i++]=3;
		                	}
		                	itemName[j++]=item;
		                }
					});
//					for(var i=0;i<itemName.length;i++){
//					alert(itemName[i]);
//					}
//					for(var i=0;i<itemFlag.length;i++){
//						alert(itemFlag[i]);
//					}
					console.log(itemName);
					console.log(itemFlag);
					var data={"activityName":selectedVal,"itemFlag":itemFlag,"itemName":itemName};
					 var datas=$.param(data,true);
				 $.ajax({ 
						type: "get", //使用get方法访问后台 
						dataType: "json", //返回json格式的数据 
						url: "saveActivityInit.action", //要访问的后台地址 
						data: datas, //要发送的数据 
						success: function(msg) {//msg为返回的数据，在这里做数据绑定 
							// $("select#lists").find("option[text='性别']").attr("selected",true);
							if(msg.message==="success"){
								
								initData(selectedVal);
								alert("初始化成功");
							}
						}, 
						error: function() { 
							alert("保存失败"); 
						} 
					}); 
				}	

		});
	});
	function initData(activityname){
		
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			async:false,
			url: "getActivityInit.action", //要访问的后台地址 
			data: {"activityName":activityname }, //要发送的数据 
			ajaxStart: function() { $("#load").show(); }, 
			complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if (msg.length != 0) { 
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length != 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
				} 
				if(msg.context!=="fail"){
					$('table').css("display","block");
					var initdata=msg.context.aactivityInit;
					var itemFlags=[];
					$.each(initdata.itemFlag, function(i, item) {
						itemFlags[i]=item;
					});
					$.each(initdata.itemName, function(i, item) {
							if(itemFlags[i]==="2"){
								$("#tb_body").append('<tr class="newContent"><td width="30%" >' + (i++)+ '</td><td width="30%" >文本</td><td width=\"40%\" id="changeItem">' + item+ " </td></tr>");
							}else if(itemFlags[i]==="3"){
								$("#tb_body").append('<tr class="newFile"><td width="30%" >' + (i++)+ '</td><td width="30%" >文件</td><td width=\"40%\" id="changeItem">' + item+ " </td></tr>"); 
							}
						});
					
					$(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					 $('#saveInitInfo').css("display","none");
				}else{
					$('table').css("display","none");
				}
			}, 
			error: function() { 
				var t = document.getElementById("tb_body"); //获取展示数据的表格 
				while (t.rows.length != 0) { 
					t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
				} 
				alert("加载数据失败"); 
			} 
		}); 
	}
	
	function initSelectData() {
		var activityStatus=1;//发布状态
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getActivityDictionary.action", //要访问的后台地址 
			data: {"activityStatus":activityStatus}, //要发送的数据 
			//data: {}, //要发送的数据 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
					if(msg.message==="success"){
						$.each(msg.context.activitydictionary, function(i, item) {
							 var count=0;
							// var sel = document.getElementById("activityLists");
							 $("#activityLists option").each(function(){ //遍历全部option
							        var txt = $(this).text(); //获取option的内容
							        //alert(txt);
							         if(item!==txt){
										count++;
									}
							     });
							 
							 if(count=== $("#activityLists option").length){
								 if(i===0){
									$("select#activityLists").append(' <option value='+item.activityid+' selected="selected">'+item.activityName+'</option>');
								}else{
									$("select#activityLists").append(' <option value='+item.activityid+'>'+item.activityName+'</option>');
								}
							}
							});
						var activity=$("select#activityLists option:selected").val();
						initData(activity); 
					}
			}, 
			error: function() { 
				alert("数据加载失败"); 
			} 
		}); 			
} 
