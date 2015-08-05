	var pageIndex = 1; //页索引 
	var pageSize=10;
	var starttime="";
	var endtime="";
	var bookclassfi="";
	$(function() { 
		$('#pagesize').val(pageSize);
		//
		// GetTotalCount(); //总记录条数 
		//GetPageCount(); //总页数绑定 
		//第一页按钮click事件 
//		var myDate = new Date();
//		starttime=myDate.getFullYear()+'-0'+(myDate.getMonth()+1)+'-0'+(myDate.getDate()-1) ;
//		endtime=myDate.getFullYear()+'-0'+(myDate.getMonth()+1)+'-0'+myDate.getDate();
//		$("#starttime").val(starttime);
//		$("#endtime").val(endtime);
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
			BindData(); 
		}); 
	//最后一页按钮click事件 
		$("#last").click(function() { 
			var pageCount = parseInt($("#lblPageCount").text()); 
			pageIndex = pageCount; 
			BindData(); 
		}); 
	//查询 
		$("input#check").click(function() { 
			
			var time1= $("input#starttime").val(); 
			//alert(time1);
			if (time1 != null && time1 != NaN) { 
				starttime=time1; 
			} 
			var time2 = $("input#endtime").val(); 
			//alert(time2);
			if (time2 != null ) { 
				endtime=time2; 
			} 
			var classfi =$("select#txtClassfi option:checked").val(); 
			//alert(classfi);
			if (classfi != null ) { 
				bookclassfi=classfi; 
			} 
			
			pageIndex = 1; 
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
		
		
	});
	
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
	
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "dateborrowranking.action", //要访问的后台地址 
				data: { "pageSize": pageSize ,"currentPage": pageIndex ,"starttime":starttime,"endtime":endtime,"classfi":bookclassfi}, //要发送的数据 
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
					//document.getElementById("lblPageCount").innerHTML = msg.context.totalPages;
					//document.getElementById("lblToatl").innerHTML = msg.context.totalRows;
					
					$.each(msg.context.bookList, function(i, item) {
						/*var year=item.importdate.year-100;
						var month=item.importdate.month+1;
						month=month<=9?"09":month;
						var date=item.importdate.date;
						date=date<=9?"09":date;
						var time="20"+year+"-"+month+"-"+date;*/
						//alert(time);
						i++;
						$("#tb_body").append('<tr><td width="60%" id="bokkname'+i+'">' + item.bookname + "</td><td width=\"40%\">" + item.borrownumber + " </td></tr>"); 
						/*$("#tb_body").append("<tr><td>" + 12+ "</td><td>" + 12 + " </td><td>" +12+ 
								" </td><td>" + 2 + " </td><td>" + 2 + " </td><td>" + 12 + 
								"</td><td>" + 12+ " </td></tr>"); */
						
					});
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					 
					 
					 $.each(msg.context.classifiList, function(i, item) {
						 var count=0;
						 var sel = document.getElementById("txtClassfi");
						 $("#txtClassfi option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item!=txt){
									count++;
								}
						     });
						 
						 if(count==sel.options.length){
							 $("select#txtClassfi").append(' <option value='+sel.options.length+'>'+item+'</option>');
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
				} 
			}); 
			
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
