	var pageIndex = 1; //页索引 
	var pageSize=10;
	var usernumber="";
	var username="";
	var physicalposition="";
	var physicaldate="";
	var physicalplan="";
	
	
	$(function() { 
		$('#pagesize').val(pageSize);
		//
		// GetTotalCount(); //总记录条数 
		//GetPageCount(); //总页数绑定 
		//第一页按钮click事件 
		//alert('main');
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
		//跳转
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
			
			usernumber = $("input#usernumber").val(); 
			username = $("input#username").val(); 
			
			physicaldate= $("input#physicaldate").val(); 
			
			
			physicalposition=$("select#physicalposition option:checked").val(); 
			
			
			physicalplan =$("select#physicalplan option:checked").val(); 
			
			
			pageIndex = 1; 
			BindData(); 
		});  
		
		
	
		

		

		
	});
	
	
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
		alert(pageSize+';'+pageIndex+';'+usernumber+';'+username+';'+physicalposition)
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "searchPhysicalNoifor.action", //要访问的后台地址 
				data: { "pageSize": pageSize ,"currentPage": pageIndex ,"usernumber":usernumber,"username":username,
					"physicalposition":physicalposition,
					 "physicaldate": physicaldate ,"physicalplan":physicalplan}, //要发送的数据 
			
				success: function(msg) {
					if (msg.length != 0) { 
						var t = document.getElementById("tb_body"); //获取展示数据的表格 
						while (t.rows.length != 0) { 
							t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
						} 
						
					} 
					$("#lblPageCount").text(msg.context.totalPages); 
					$("#lblToatl").text(msg.context.totalRows); 
					
					$.each(msg.context.physicallist, function(i, item) {
						
						var bodyContent='<tr>'+
											'<td  >' + item.usernumber + '</td>'+
											'<td  >' + item.username + '</td>'+
											'<td  >' + item.physicalposition + '</td>'+
											'<td  >' + item.physicaldate + '</td>'+//id="changeDate'+item.id+'"
											'<td >' + item.physicalplan + '</td>'+
											'<td >' + item.remark + '</td>'+
										'</tr>';
						$("#tb_body").append(bodyContent); 
						
						
					});
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					
					 $.each(msg.context.physical_plan, function(i, item) {
						 var count=0;
						 var sel = document.getElementById("physicalplan");
						 $("#physicalplan option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item!=txt){
									count++;
								}
						     });
						 
						 if(count==sel.options.length){
							 $("select#physicalplan").append(' <option value='+(i+1)+'>'+item+'</option>');
						 }
							
						});
					 $.each(msg.context.physical_position, function(i, item) {
						 var count=0;
						 var sel = document.getElementById("physicalposition");
						 $("#physicalposition option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item!=txt){
									count++;
								}
						     });
						 
						 if(count==sel.options.length){
							 $("select#physicalposition").append(' <option value='+(i+1)+'>'+item+'</option>');
						 }
							
						});
					 
						bindPager();
				}, 
				error: function() { 
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length != 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
					} 
					alert("加载数据失败"); 
				} //加载失败，请求错误处理 
				//ajaxStop:$("#load").hide() 
			}); 
			//GetTotalCount(); 
			//GetPageCount(); 
			//bindPager();
			
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

