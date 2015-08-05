	
	$(function() { 
		
		
		DictionaryBindData(); 
		
		
		$("select#lists").change(function(){
			DictionaryBindData(); 
		});
		$("input#additem").click(function(){
			//alert('add'+$("#tb_body tr").length);
			var num=$("#tb_body tr").length +1;
			//$("#tb_body").append('<tr><td width="40%" class="bookList">' + num + '</td><td width="60%"><input style="margin-left:25%;;width:50%" type="text" name="itemname"  id="listname" ></td></tr>'); 
			$("#tb_body").append('<tr class="newAdd"><td width="40%" class="bookList">' + num + '</td><td width="60%" class="add bookList"></td></tr>'); 
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
			
			$('#save').css("display","block");
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
						alert('修改信息不能为空！');
						$('#save').css("display","none");
						return false;
						
					}
				})
				$('#save').css("display","block");
		});
		$("input#save").click(function(){
			 var selectedText= $("#lists").find("option:selected").text();
			 if(selectedText=="自定义"){
				 selectedText=$("#listname").val();	
			 }
			 if(selectedText==""||selectedText==null){
					 alert("请输入类型名称");
					 return false;
			}else{
//					 var flag=0;
//					 $('#tb_body tr.newAdd').each(function () { 
//					 	var item=$(this).children('td').eq(1).html();
//					 	var fdStart = item.indexOf("<input");
//		                if(item!=""&&fdStart!=0){
//		                	flag=1;
//		                }
//		            });
//					if(flag==1){
						 var itemname = new Array();
						 var i=0;
						 $('#tb_body tr').each(function () { 
							 	var item=$(this).children('td').eq(1).html();
				                //alert(item);
				                var fdStart = item.indexOf("<input");
				                //alert(fdStart);
				                if(item!=""&&fdStart!=0){
				                	itemname[i++]=item;
				                	//alert(item);
				                }
				            });
						 $.ajax({ 
								type: "get", //使用get方法访问后台 
								dataType: "json", //返回json格式的数据 
								url: "dictionarySave.action", //要访问的后台地址 
								data: {"keyword":selectedText,"itemname":itemname}, //要发送的数据 
								success: function(msg) {//msg为返回的数据，在这里做数据绑定 
									
									 
									DictionaryBindData();
									// $("select#lists").find("option[text='性别']").attr("selected",true);
									
									$('#lists option:contains(' + selectedText + ')').each(function(){ //遍历全部option
										//alert(";"+$(this).text()+";");
										if ($(this).text() == selectedText) {
									     $(this).attr('selected', true);
									  }
							     });

									DictionaryBindData();
									alert(msg.context);
									
								}, 
								error: function() { 
									
									alert("保存失败"); 
								} 
							}); 
			}	
//					 }else{
//						 alert('请添加类别值');
//					 }

				
			 
			
	
		
	});
	});
	
	function DictionaryBindData() { 
		
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				async:false,
				url: "dictionaryshow.action", //要访问的后台地址 
				data: { }, //要发送的数据 
				ajaxStart: function() { $("#load").show(); }, 
				complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示 
				success: function(msg) {//msg为返回的数据，在这里做数据绑定 
					// ajaxobj=eval("("+msg+")"); 
					
					//var data=msg.comments;
					if (msg.length != 0) { 
						var t = document.getElementById("tb_body"); //获取展示数据的表格 
						while (t.rows.length != 0) { 
							t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
						} 
						
					} 
					$("#listname").val("");
					
					var selectedText= $("#lists").find("option:selected").text();
					// alert(selectedText);
					 $.each(msg.context, function(i, item) {
						 var count=0;
						 var  j=i+1;
						 var sel = document.getElementById("lists");
						 $("#lists option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item.keyword!=txt){
									count++;
								}
						     });
						
						 if(count==sel.options.length){
							 $("select#lists").append(' <option value='+j+'>'+item.keyword+'</option>');
						 }
						 
						 if(selectedText==item.keyword){
							 $.each(item.itemname, function(i, item) {
								 	var k=i+1;
									$("#tb_body").append('<tr><td width="40%" >' + k+ '</td><td width=\"60%\" id="changeItem">' + item+ " </td></tr>"); 
								});
						 }
						 if(selectedText!="自定义"){
							 $('#addList').css("display","none");
						 }else{
							 $('#addList').css("display","block");
						 }
						});
					
					
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					 $('#save').css("display","none");
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
