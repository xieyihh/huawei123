	var pageIndex = 1; //页索引 
	var pageSize=10;
	var name="";
	var status="";

	$(function() { 
		$('#pagesize').val(pageSize);
		statusData();
		BindData(); 
		
		$("#first").click(function() {
			//alert('first');
			pageIndex = 1; 
			$("#lblCurent").text(1); 
			BindData(); 
		}); 
//上一页按钮click事件 
		$("#previous").click(function() { 
			if (pageIndex != 1) { 
				pageIndex--; 
				$("#lblCurent").text(pageIndex); 
			} 
			BindData(); 
		}); 
//下一页按钮click事件 
		$("#next").click(function() { 
			var pageCount = parseInt($("#lblPageCount").text()); 
			if (pageIndex != pageCount) { 
				pageIndex++; 
				$("#lblCurent").text(pageIndex); 
			} 
			//alert(pageIndex);
			BindData(); 
		}); 
	//最后一页按钮click事件 
		$("#last").click(function() { 
			var pageCount = parseInt($("#lblPageCount").text()); 
			pageIndex = pageCount; 
			BindData(); 
		}); 
		$("input#goPage").blur(function(){
			var page = $(this).val(); 
			var pageCount = parseInt($("#lblPageCount").text());
			//alert(page+"   "+pageCount);
			if (page !== null &&   page !== "") { 
				if(isNaN(page)){
					alert('页码必须为数字')
				}else if(page>pageCount||page<1){
					alert("不存在这一页");
				}else{
					pageIndex = page; 
					//alert(pageIndex);
					BindData();
				}
				//alert(pageIndex);
			} 
			});
		$("input#pagesize").blur(function(){
			var pagesize = $(this).val(); 
			
			//alert(page+"   "+pageCount);
			if (pagesize !== null &&   pagesize !== "") { 
				if(isNaN(pagesize)){
					alert('每行显示列数必须为数字')
				}else{
					pageSize = pagesize; 
					//alert(pageSize);
					BindData();
				}
				//alert(pageIndex);
			} 
			});
	//查询 
		$("button#btnSearch").click(function() { 
			name = $("input#name").val();
			status =$("select#status option:selected").val(); 
			
			pageIndex = 1; 
			BindData(); 
		});  
		
		
		
	

		

		//修改权限
		$(document).on("click","[id^='edit']",function(){//修改成这样的写法
				
				var listNum=$(this).attr("id").toString().slice(4);
				$(this).attr({id:"save"+listNum });
				var old = $('#status'+listNum+'').text(); 
				//alert(old);
				
				$(this).html('<img src="img/save.gif" border="0" title="保存">');
				$('#status'+listNum+'').text('');
				var input = '<select   id="activity"  class="form-control">'+
					   				
					   		'</select>';
				$('#status'+listNum+'').append(input);
				$.ajax({ 
					type: "get", 
					dataType: "json", 
					url: "getActivityStatusDictionary.action", 
					data: {}, //"wherePageCount" + where,个人建议不用这种方式 
					async: false, 
					success: function(msg) { 

						 $.each(msg.context.activitystatus, function(i, item) {
							 var count=0;
							 var sel = document.getElementById("activity");
							 $("#activity option").each(function(){ //遍历全部option
							        var txt = $(this).text(); //获取option的内容
							        //alert(txt);
							         if(item!=txt){
										count++;
									}
							     });
							 
							 if(count==sel.options.length){
								 $("select#activity").append(' <option value='+(i+1)+'>'+item+'</option>');
							 }
							 $('#activity option:contains(' + old + ')').each(function(){ //遍历全部option
									//alert(";"+$(this).text()+";");
									if ($(this).text() == old) {
								     $(this).attr('selected', true);
								  }
							 });
							// $("select#authority").find("option[text="+old+"]").attr("selected",true);	
							});
						
					}, 
					error:function(msg){
						alert(msg.message); 
					}
					}); 	
					
			 
		});
		
		//保存修改
		$(document).on("click","[id^='save']",function(){//修改成这样的写法
			
			var listNum=$(this).attr("id").toString().slice(4);
			$(this).attr({id:"edit"+listNum });
			$(this).html('<img src="img/edit.gif" border="0" title="修改权限">');
			var old = $("#activity").find("option:selected").text();
			var statusid= $("#activity").find("option:selected").val();
			//alert(listNum+';'+statusid);
			$('#activity').remove();
			
			$('#status'+listNum+'').text(old);
			
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "updateActivityStatus.action", 
				data: {"activityid":listNum,"activityStatus":statusid}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) { 
					if(msg.message=="success"){
						alert('修改成功');
					}
				}, 
				error:function(msg){
					alert("error"); 
				}
				}); 	
				
		 
		});
	});
	
	function statusData(){
		$.ajax({ 
			type: "get", 
			dataType: "json", 
			url: "getActivityStatusDictionary.action", 
			data: {}, //"wherePageCount" + where,个人建议不用这种方式 
			async: false, 
			success: function(msg) { 

				$.each(msg.context.activitystatus, function(i, item) {
					 var count=0;
					 var sel = document.getElementById("status");
					 $("#status option").each(function(){ //遍历全部option
					        var txt = $(this).text(); //获取option的内容
					        //alert(txt);
					         if(item!=txt){
								count++;
							}
					     });
					 
					 if(count==sel.options.length){
						 $("select#status").append(' <option value='+(i+1)+'>'+item+'</option>');
					 }
						
					});
			}, 
			error:function(msg){
				alert("加载状态失败"); 
			}
			}); 
	}
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
		
		$.ajax({ 
			type: "get", 
			dataType: "json", 
			url: "getallActivityStatus.action", 
			data: {'pageSize':pageSize,"currentPage": pageIndex ,"activityName":name,"activityStatus":status}, //"wherePageCount" + where,个人建议不用这种方式 
			async: false, 
			success: function(msg) { 
				if(msg.context==="fail"){//无数据
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length != 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
					$("#lblPageCount").text(msg.context.totalPages); 
					$("#lblToatl").text(msg.context.totalRows);
					bindPager();
				}
				else{
					
						var t = document.getElementById("tb_body"); //获取展示数据的表格 
						while (t.rows.length != 0) { 
							t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
						} 
					 
					$("#lblPageCount").text(msg.context.totalPages); 
					$("#lblToatl").text(msg.context.totalRows); 
					
					$.each(msg.context.activitydictionary, function(i, item) {
						
						var bodyContent='<tr>'+
											'<td width="30%"  >' + item.activityname + '</td>' +
											'<td width="40%" id="status'+item.activityid+'">' + item.statusName + '</td>' +		
											'<td width="30%"><a id="edit'+item.activityid+'" > <img src="img/edit.gif" border="0" title="修改权限"></a> </td></tr>'
						
						
						$("#tb_body").append(bodyContent); 
						
					});
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					
						bindPager();
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
// 页脚属性设置 
function bindPager() { 
	//填充分布控件信息 
	var pageCount = parseInt($("#lblPageCount").text()); //总页数 
	if (pageCount == 0) { 
		document.getElementById("lblCurent").innerHTML = "0"; 
	} 
	else { 
	if (pageIndex > pageCount) { 
		$("#lblCurent").text(1); 
	} 
	else { 
		$("#lblCurent").text(pageIndex); //当前页 
	} 
	} 
	document.getElementById("first").disabled = (pageIndex == 1 || $("#lblCurent").text() == "0") ? true : false; 
	document.getElementById("previous").disabled = (pageIndex <= 1 || $("#lblCurent").text() == "0") ? true : false; 
	document.getElementById("next").disabled = (pageIndex >= pageCount) ? true : false; 
	document.getElementById("last").disabled = (pageIndex == pageCount || $("#lblCurent").text() == "0") ? true : false; 
} 
