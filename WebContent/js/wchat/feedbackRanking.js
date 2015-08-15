	var pageIndex = 1; //页索引 
	var totalPage=0;
	var contentHeight=0;
	var nickname="";
	var first="first";
	$(function() { 
		
		//alert('1');
		BindFeedbackData(); 
	 
//下一页按钮click事件 
		
		$("#LoadMore").click(function() { 
			if (pageIndex != totalPage) { 
				pageIndex++; 
				first="else";
				BindFeedbackData();
			}else{
				alert("没有更多加载项");
			} 
			 
		}); 
//		$("#indexPage").click(function() { 
//			document.forms.Form1.action="showbookinfohome.action";
//		     document.forms.Form1.submit();
//			 
//		});
//		$("#borrowinghistory").click(function() { 
//			document.forms.Form1.action="borrowhistory.action";
//		     document.forms.Form1.submit();
//			 
//		}); 
//		$("#lendingranking").click(function() { 
//			document.forms.Form1.action="borrowranking.action";
//		     document.forms.Form1.submit();  
//			 
//		}); 
	//查询 
		$("input#searchbutton").click(function() { 
			first="check";
			var name = $("input#bookname").val(); 
			if(name=="查询书籍"){
				name="";
			}
			//alert(name);
			if (name != null && name != NaN) { 
				bookName=name; 
			} 
			
			pageIndex = 1; 
			BindFeedbackData(); 
		});  
		
		
		
		
	});
	
	
	//AJAX方法取得数据并显示到页面上 
	function BindFeedbackData() { 
		//showLoading("正在加载···");
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url:"phonebooksearch.action", //要访问的后台地址 
				data: { "currentPage": pageIndex ,"nickname":nickname}, //要发送的数据 
				success: function(msg) {//msg为返回的数据，在这里做数据绑定 
					
					if(first==="first"||first==="check"){
						if (msg.length != 0) { 
							$("#module").empty();
							//Height=$("#module").height();
						} 
						
					}
					
					if(msg.context.totalRows==0){
						$('#LoadMore').hide();
						$('#booknull').show();
					}else{
						
						$('#booknull').hide();
						$.each(msg.context.booklist, function(i, item) {
							
							var list='<tr>'+
											'<td >' + item.usernumber + '</td>'+
											'<td >' + item.username + '</td>'+
											'<td >' + item.physicalposition + '</td>'+
										'</tr>';
							
							$("#tb_body").append(list);
//							if(first==='first'){
//								contentHeight=$("div.divback").height();
//								//alert(contentHeight);
//								first="else";
//							}else{
//								$("div.divback").css("height",contentHeight);
//							}
//							
//							//var oldHeight=$("#module").height();
//							//$("#module").css("height",oldHeight+Height/4);
//							//$("#booklist").append('<tr style="height:25%;border-radius: 30px;border-radius: 30px 30px 30px 30px;"><td rowspan="2" style="width:15%height:100%"><img style="width:90%;height:80%;margin-top:10%"src="'+item.image+'"  border="0" title=""></td><td width="70%" height="30px">' + item.bookName + ' </td></tr><tr><td width="70%" style="height:70px" >' + item.summary+ "</td></tr>"); 
						});
//						
//						//var num=0;
//						var OuterHeight=0;
//						//var Height=0;
//						$("div.divback").each(function(){
//							OuterHeight+=$(this).outerHeight(true);
//							//Height+=$(this).height();
//							//num++;
//						  });
//						//alert(Height);
//						//alert(num);
//						$("#module").css("height",OuterHeight);
//						$("div.divback").css("height",contentHeight);
						if(pageIndex==msg.context.totalPages){
							$('#LoadMore').hide();
						}else{
							$('#LoadMore').show();
						}
						   
					}
					
					
					
					
				}, 
				error: function() { 
//					var t = document.getElementById("booklist"); //获取展示数据的表格 
//					while (t.rows.length != 0) { 
//						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
//					} 
					alert("加载数据失败"); 
				} 
			}); 
			
			
		} 
 
	
