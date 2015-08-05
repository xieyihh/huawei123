	var pageIndex = 1; //页索引 
	var pageSize=10;
	var usernumber="";
	var username="";
	var physicalposition="";
	var physicalreservedate="";
	var physicaldate="";
	var physicalplan="";
	var reviewcontent="";
	$(function() { 
		
		$('#pagesize').val(pageSize);
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
			
			var workNumber = $("input#workNumber").val(); 
			//alert(workNumber);
			if (workNumber != null ) { 
				usernumber=workNumber; 
			} 
			var workname = $("input#workname").val(); 
			//alert(source);
			if (workname != null ) { 
				username=workname; 
			} 
			var position =$("select#physicalposition option:checked").val(); 
			//alert(position);
			if (position != null ) { 
				physicalposition=position; 
			} 
			var reservedate = $("input#physicalreservedate").val(); 
			//alert(source);
			if (reservedate != null ) { 
				physicalreservedate=reservedate; 
			} 
			var date = $("input#physicaldate").val(); 
			//alert(name);
			if (date != null ) { 
				physicaldate=date; 
			} 
			var plan =$("select#physicalPlan option:checked").val(); 
			//alert(position);
			if (plan != null ) { 
				physicalplan=plan; 
			} 
			reviewcontent=$("input#reviewContent").val(); 
			
			pageIndex = 1; 
			BindData(); 
		});  
		
		//修改复查内容
		$(document).on("click","[id^='edit']",function(){//修改成这样的写法
				
				var listNum=$(this).attr("id").toString().slice(4);
				$(this).attr({id:"save"+listNum });
				var old = $('#reviewcontent'+listNum+'').text(); 
				//alert(old);
				
				$(this).html('<img src="img/save.gif" border="0" title="保存">');
				$('#reviewcontent'+listNum+'').text('');
				var input = '<textarea  id="content" value=' + old +'>';
				$('#reviewcontent'+listNum+'').append(input);
				
					
			 
		});
		
		//保存修改
		$(document).on("click","[id^='save']",function(){//修改成这样的写法
			
			var listNum=$(this).attr("id").toString().slice(4);
			$(this).attr({id:"edit"+listNum });
			$(this).html('<img src="img/edit.gif" border="0" title="解释复查内容">');
			var text = $("#content").val();
			
			//alert(old);
			$('#content').remove();
			
			$('#reviewcontent'+listNum+'').text(text);
			
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "savePhysicalreviewBymanager.action", 
				data: {"id":listNum,"reviewcontent":text}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) { 
					if(msg.message=="success"){
						alert('修改复查内容成功');
					}
				}, 
				error:function(msg){
					alert("error"); 
				}
				}); 	
				
		 
		});
		
	
//		$(document).on("click","[id^='delete']",function(){//修改成这样的写法
//			if(confirm("确定删除这条数据吗？"))
//			 {
//				var listNum=$(this).attr("id").toString().slice(6);
//				//alert(listNum);
//				$.ajax({ 
//					type: "get", 
//					dataType: "json", 
//					url: "deletephsicalreview.action", 
//					data: {"id": listNum}, //"wherePageCount" + where,个人建议不用这种方式 
//					async: false, 
//					success: function(msg) { 
//						
//						BindData();
//					}, 
//					error:function(msg){
//						alert(msg.message); 
//					}
//					}); 	
//					
//			 }
//
//			
//			
//		  });
		
		/*导出督查信息*/
		$("button#exportReviewinfo").click(function() { 
			
			var workNumber = $("input#workNumber").val(); 
			//alert(workNumber);
			if (workNumber != null ) { 
				usernumber=workNumber; 
			} 
			var workname = $("input#workname").val(); 
			//alert(source);
			if (workname != null ) { 
				username=workname; 
			} 
			var position =$("select#physicalposition option:checked").val(); 
			//alert(position);
			if (position != null ) { 
				physicalposition=position; 
			} 
			var reservedate = $("input#physicalreservedate").val(); 
			//alert(source);
			if (reservedate != null ) { 
				physicalreservedate=reservedate; 
			} 
			var date = $("input#physicaldate").val(); 
			//alert(name);
			if (date != null ) { 
				physicaldate=date; 
			} 
			
			var plan =$("select#physicalPlan option:checked").val(); 
			//alert(position);
			if (plan != null ) { 
				physicalplan=plan; 
			} 
			reviewcontent=$("input#reviewContent").val(); 
			exportReviewInfo();
		});  

	});
	
	
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
		
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "phsicalreviewsearch.action", //要访问的后台地址 
				data: {"pageSize": pageSize , "currentPage": pageIndex ,"usernumber":usernumber,"username":username,
						"physicalposition":physicalposition,"physicalreservedate":physicalreservedate,"physicalplan":physicalplan,
					 "physicaldate": physicaldate ,"reviewcontent":reviewcontent}, //要发送的数据 
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
					$("#lblPageCount").text(msg.context.totalPages); 
					$("#lblToatl").text(msg.context.totalRows); 
					
					$.each(msg.context.physicallist, function(i, item) {
						
						var bodyContent='<tr>'+
											'<td >' + item.usernumber + '</td>'+
											'<td >' + item.username + ' </td>'+
											'<td >' + item.physicalposition + '</td>'+
											'<td >' + item.physicalimportdate + '</td>'+
											'<td >' +item.physicaldate + '</td>'+
											'<td >' + item.physicalplan + ' </td>'+
											'<td  id="reviewcontent'+item.id+'" >' + item.reviewcontent + '</td>'+
											'<td ><a id="edit'+item.id+'" ><img src="img/edit.gif" border="0" title="解释复查内容"></a> </td>'+
										'</tr>';
						
						
						$("#tb_body").append(bodyContent); 
						
					});
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					 
					
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

function exportReviewInfo(){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportphysicalreviewinfo.action");
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","usernumber");
	input1.attr("value",usernumber);
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","username");
	input2.attr("value",username);
	var input3=$("<input>");
	input3.attr("type","hidden");
	input3.attr("name","physicalposition");
	input3.attr("value",physicalposition);
	var input4=$("<input>");
	input4.attr("type","hidden");
	input4.attr("name","physicalreservedate");
	input4.attr("value",physicalreservedate);
	var input5=$("<input>");
	input5.attr("type","hidden");
	input5.attr("name","physicaldate");
	input5.attr("value",physicaldate);
	
	var input6=$("<input>");
	input6.attr("type","hidden");
	input6.attr("name","reviewcontent");
	input6.attr("value",reviewcontent);
	var input7=$("<input>");
	input7.attr("type","hidden");
	input7.attr("name","physicalplan");
	input7.attr("value",physicalplan);
	
	$("body").append(form);//将表单放置在web中
	form.append(input1);
	form.append(input2);
	form.append(input3);
	form.append(input4);
	form.append(input5);
	
	form.append(input6);
	form.append(input7);
	form.submit();//表单提交 
	form.remove();
}