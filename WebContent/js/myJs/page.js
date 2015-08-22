	var pageIndex = 1; //页索引 
	var pageSize=10;
	var bookName="";
	var booksource="";
	var bookposition="";
	var bookibsn="";
	var bookIsibsn="";
	
	function bindPager() { 
		//填充分布控件信息 
		var pageCount = parseInt($("#lblPageCount").text()); //总页数 
		if (pageCount === 0) { 
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
		document.getElementById("first").disabled = (pageIndex === 1 || $("#lblCurent").text() === "0") ? true : false; 
		document.getElementById("previous").disabled = (pageIndex <= 1 || $("#lblCurent").text() === "0") ? true : false; 
		document.getElementById("next").disabled = (pageIndex >= pageCount) ? true : false; 
		document.getElementById("last").disabled = (pageIndex === pageCount || $("#lblCurent").text() === "0") ? true : false; 
		} 
	function bindData() { 
		
		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getallbookinfo.action", //要访问的后台地址 
			data: { "pageSize": pageSize ,"currentPage": pageIndex ,"bookName":bookName,"isbn10":bookibsn,"source":booksource,"position":bookposition,"Isisbn10":bookIsibsn}, //要发送的数据 
			ajaxStart: function() { $("#load").show(); }, 
			complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				try{
					var text = JSON.stringify(msg);
					var myData = JSON.parse(text);
				if (msg.length !== 0) { 
					var t = document.getElementById("tb_body"); //获取展示数据的表格 
					while (t.rows.length !== 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
					
				} 
				$("#lblPageCount").text(msg.context.totalPages); 
				$("#lblToatl").text(msg.context.totalRows); 
				
				$.each(msg.context.booklist, function(i, item) {
					
					$("#tb_body").append('<tr><td width="6%" id="isbn10'+(i+1)+'">' + item.isbn10 + "</td><td width=\"10%\">" + item.bookName + " </td><td width=\"6%\">" + item.source+ 
					'</td><td width=\"24%\" id="onlysign'+(i+1)+'">' + item.onlysign + " </td><td width=\"9%\">" + item.position + " </td><td width=\"8%\">" +item.importdate + 
					"</td><td width=\"3%\">" + item.number + '</td><td width="8%"><a id="edit'+(i+1)+'" >' + "编辑" + ' </a></td><td width="10%">'+item.downstate+
					'</td><td width="9%"><a id="delete'+(i+1)+'" > <img src="img/delete.gif" border="0" title="删除"></a> </td><td width="9%">' + item.bookid + " </td></tr>"); 
					
					
				});
				
				 $(".tableone tr:nth-child(even)").addClass("trOdd");
				 $("td").addClass("bookList");
				
				
				 var num=1;
				 $.each(msg.context.sourcelist, function(i, item) {
					 var count=0;
					 var sel = document.getElementById("txtPosition");
					 $("#txtPosition option").each(function(){ //遍历全部option
					        var txt = $(this).text(); //获取option的内容
					        
					         if(item!==txt){
								count++;
							}
					     });
					 
					 if(count===sel.options.length){
					 var val=(num++).toString();
					 	$("select#txtPosition").append(' <option value='+val+'>'+item+'</option>');
					 }
						
					});
					bindPager();
				}catch(error){
					alert('返回json不合法')
				}
			}, 
			error: function() { 
				var t = document.getElementById("tb_body"); //获取展示数据的表格 
				while (t.rows.length !== 0) { 
					t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
				} 
				alert("加载数据失败"); 
			} 
		}); 
		
		
	} 
//页脚属性设置 

	function Delete(onlysign,isbn10) { 
		
		$.ajax({ 
		type: "get", 
		dataType: "json", 
		url: "deletebooknumber.action", 
		data: {"onlysign": onlysign,"isbn10":isbn10}, //"wherePageCount" + where,个人建议不用这种方式 
		async: false, 
		success: function(msg) { 
			if(msg.message==="success"){
				bindData();
			}
			
			
		}, 
		error:function(msg){
			alert(msg); 
		}
		}); 
	}
	function Edit(onlysign) { 
	$.ajax({ 
		type: "get", 
		dataType: "json", 
		url: "getoneimportbookinfo.action", 
		data: {"onlysign": onlysign}, //"wherePageCount" + where,个人建议不用这种方式 
		async: false, 
		success: function(msg) { 
			
			var bookform= msg.context.bookform;
			//alert(bookform.id);
			
			$("input#id").val(bookform.id);
			$("input#isbn10").val(bookform.isbn10);
			$("input#onlysign").val(bookform.onlysign);
			$("input#bookName").val(bookform.bookName);
			$("input#source").val(bookform.source);
			$("input#auto").val(bookform.author);
			//alert(bookform.summary);
			$("#summary").val(bookform.summary);
			$("#image").val(bookform.image); 
			//alert(bookform.image);
			
			var image=bookform.image.replace(/\\/,"/");
			//alert(image);
			
			$("#imag").attr('src',image + "?r="+Date()); 
			//$("#imag").append('src='+image+'');
			
			 var position=bookform.position;
			 var classfi=bookform.classifi;
			// alert(classfi);
			
			 $.each(msg.context.positionList, function(i, item) {
				 var j=i+1;
				 var count=0;
				 var sel = document.getElementById("position");
				 $("select#position option").each(function(){ //遍历全部option
				        var txt = $(this).text(); //获取option的内容
				       // alert(txt);
				         if(item.ddlName!==txt){
							count++;
						}
				     });
				 if(count===sel.options.length){
					if(position===item.ddlCode){
					$("select#position").append(' <option value='+j+' selected="selected">'+item.ddlName+'</option>');
					}else{
						$("select#position").append(' <option value='+j+'>'+item.ddlName+'</option>');
					}
				 }
				});
			
			 $.each(msg.context.classifiList, function(i, item) {
				 var j=i+1;
				 var count=0;
				 var sel = document.getElementById("classifi");
				 $("select#classifi option").each(function(){ //遍历全部option
				        var txt = $(this).text(); //获取option的内容
				        //alert(txt);
				         if(item.ddlName!==txt){
							count++;
						}
				     });
				 if(count===sel.options.length){
					 if(classfi===item.ddlCode){
						$("select#classifi").append(' <option value='+j+' selected="selected">'+item.ddlName+'</option>');
					 }else{
						 $("select#classifi").append(' <option value='+j+'>'+item.ddlName+'</option>'); 
					 }
				 }
				});
		}, 
		error:function(msg){
			alert(msg); 
		}
		});
	}
	$(function() { 
		
		$('#pagesize').val(pageSize);
		//alert('main');
		bindData(); 
		
		$("#first").click(function() {
			//alert('first');
			pageIndex = 1; 
			$("#lblCurent").text(1); 
			bindData(); 
		}); 
//上一页按钮click事件 
		$("#previous").click(function() { 
			if (pageIndex !== 1) { 
				pageIndex--; 
				$("#lblCurent").text(pageIndex); 
			} 
			bindData(); 
		}); 
//下一页按钮click事件 
		$("#next").click(function() { 
			var pageCount = parseInt($("#lblPageCount").text()); 
			if (pageIndex !== pageCount) { 
				pageIndex++; 
				$("#lblCurent").text(pageIndex); 
			} 
			bindData(); 
		}); 
	//最后一页按钮click事件 
		$("#last").click(function() { 
			var pageCount = parseInt($("#lblPageCount").text()); 
			pageIndex = pageCount; 
			bindData(); 
		}); 
		//跳转
		$("input#goPage").blur(function(){
			var page = $(this).val(); 
			var pageCount = parseInt($("#lblPageCount").text());
			// alert(page+" "+pageCount);
			if (page !== null &&   page !== "") { 
				var reg=/^\d+$/g;
				if (reg.test(page)) {
					if(page>pageCount||page<1){
						alert("不存在这一页");
					}else{
						pageIndex = page; 
						bindData();
					}
				}else{
					alert('页码必须为正整数');
				}
			}
			});
		$("input#goPage").keypress(function() {
			var page = $(this).val(); 
			var pageCount = parseInt($("#lblPageCount").text());
			if (event.keyCode == 13) {// 13 回车键
				if (page !== null &&   page !== "") { 
					var reg=/^\d+$/g;
					if (reg.test(page)) {
						if(page>pageCount||page<1){
							alert("不存在这一页");
						}else{
							pageIndex = page; 
							bindData();
						}
					}else{
						alert('页码必须为正整数');
					}
				}
			}
		});
//		$("input#goPage").blur(function(){
//			var page = $(this).val(); 
//			var pageCount = parseInt($("#lblPageCount").text());
//			//alert(page+"   "+pageCount);
//			if (page !== null &&   page !== "") { 
//				if(isNaN(page)){
//					alert('页码必须为数字')
//				}else if(page>pageCount||page<1){
//					alert("不存在这一页");
//				}else{
//					pageIndex = page; 
//					//alert(pageIndex);
//					bindData();
//				}
//				//alert(pageIndex);
//			} 
//			});
//		$("input#pagesize").blur(function(){
//			var pagesize = $(this).val(); 
//			
//			//alert(page+"   "+pageCount);
//			if (pagesize !== null &&   pagesize !== "") { 
//				if(isNaN(pagesize)){
//					alert('每行显示列数必须为数字')
//				}else{
//					pageSize = pagesize; 
//					//alert(pageSize);
//					bindData();
//				}
//				//alert(pageIndex);
//			} 
//			});
		
		$("input#pagesize").blur(function() {
			var pagesize = $(this).val();
			if (pagesize !== null && pagesize !== "") {
				var reg=/^\d+$/g
				if (reg.test(pagesize)) {
					pageSize = pagesize;
					bindData();
				} else {
					alert('每行显示行数必须为正整数')
					return false;
				}
			}
		});
		$("input#pagesize").keypress(function() {
			var pagesize = $(this).val();
			if (event.keyCode == 13) {// 13 回车键
				if (pagesize !== null && pagesize !== "") {
					var reg=/^\d+$/g
					if (reg.test(pagesize)) {
						pageSize = pagesize;
						bindData();
					} else {
						alert('每行显示行数必须为正整数')
						return false;
					}
				}
			}
		});
//		$("a#goto").click(function() { 
//			
//		});
	//查询 
		$("input#btnSearch").click(function() { 
			
			var name = $("input#txtSm").val(); 
			//alert(name);
			if (name !== null &&  isNaN(name)) { 
				bookName=name; 
			} 
			var source = $("input#txtSource").val(); 
			//alert(source);
			if (source !== null ) { 
				booksource=source; 
			} 
			var position =$("select#txtPosition option:checked").val(); 
			//alert(position);
			if (position !== null ) { 
				bookposition=position; 
			} 
			var ibsn = $("input#txtIsbn").val(); 
			//alert(ibsn);
			if (ibsn !== null ) { 
				bookibsn=ibsn; 
			} 
			var isIsbn=$('input:radio[name="isIsbn"]:checked').val();
			if(isIsbn!==null){
            	//alert(isIsbn);
            	bookIsibsn=isIsbn;
            }
			pageIndex = 1; 
			bindData(); 
		});  
		$("input#exportData").click(function() { 
			//alert(exportData);
			var name = $("input#txtSm").val(); 
			//alert(name);
			if (name !== null &&  isNaN(name)) { 
				bookName=name; 
			} 
			var source = $("input#txtSource").val(); 
			//alert(source);
			if (source !== null ) { 
				booksource=source; 
			} 
			var position =$("select#txtPosition option:checked").val(); 
			//alert(position);
			if (position !== null ) { 
				bookposition=position; 
			} 
			var ibsn = $("input#txtIsbn").val(); 
			//alert(ibsn);
			if (ibsn !== null ) { 
				bookibsn=ibsn; 
			} 
			var isIsbn=$('input:radio[name="isIsbn"]:checked').val();
			if(isIsbn!==null){
            	//alert(isIsbn);
            	bookIsibsn=isIsbn;
            }
			pageIndex = 1; 
			
			exportData();
		});  
		$("input#download").click(function() { 
			
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "downallbookinfo.action", //要访问的后台地址 
				data: { }, //要发送的数据 
				success: function(msg) {//msg为返回的数据，在这里做数据绑定 
					alert(msg.context);
				}, 
				error: function() { 
					alert("下载数据失败"); 
				} 
			}); 
			
		});  
		
		//删除
		
		$(document).on("click","[id^='delete']",function(){//修改成这样的写法
			var listNum=$(this).attr("id").toString().slice(6);
			//alert(listNum);alert('#onlysign'+listNum+'');
			var onlysign = $('#onlysign'+listNum+'').text(); 
			var isbn10 = $('#isbn10'+listNum+'').text(); 
			//alert(onlysign+" "+isbn10);
			if (onlysign !==null&& isbn10!==null) { 
				Delete(onlysign,isbn10); 
				//BindData();
			}  
		  });
		//编辑
		/*$("[id^='edit']").live('click',function(){ 
			var listNum=$(this).attr("id").toString().slice(4);
			var onlysign = $('#onlysign'+listNum+'').text(); 
			alert(onlysign);
			if (onlysign != null) { 
				//window.location.href="editbook.jsp?onlysign="+onlysign; 
				//BindData();
				Edit(onlysign);
				 //$("#Modal").addClass("show");
				 
				  $('#myModal').modal('show');
				// $('#Modal').modal('toogle');
				
			}  
		});*/
		$(document).on("click","[id^='edit']",function(){//修改成这样的写法
			var listNum=$(this).attr("id").toString().slice(4);
			var onlysign = $('#onlysign'+listNum+'').text(); 
			//alert(onlysign);
			if (onlysign !== null) { 
				//window.location.href="editbook.jsp?onlysign="+onlysign; 
				//BindData();
				Edit(onlysign);
				 //$("#Modal").addClass("show");
				 
				  $('#myModal').modal('show');
				// $('#Modal').modal('toogle');
				
			}  
		  });
		$("#save").click(function() {
			//alert('save');
			var form = document.getElementById("myForm");
			   form.submit();
			 //save();
			//$('#myForm').submit();
			 //  alert('12');
			 $('#myModal').modal('hide');
			 bindData();
			 ///document.Form1.action="saveimportbookinfo.action";
			 //document.Form1.submit();	  
			 //window.close();  
		}); 

		
		
	});
	
	//AJAX方法取得数据并显示到页面上 
	
	function exportData() { 
		//var newwindow=window.open('about:blank'); 
//		$.ajax({ 
//			type: "get", //使用get方法访问后台 
//			dataType: "json", //返回json格式的数据 
//			url: 'Excelexport.action', //要访问的后台地址 
//			data: { "bookName":bookName,"isbn10":bookibsn,"source":booksource,"position":bookposition,"Isisbn10":bookIsibsn}, //要发送的数据 
//			success: function(data) {//msg为返回的数据，在这里做数据绑定 
//				
//
//			}, 
//			error: function() { 
//				
//				alert("导出数据失败"); 
//			} 
//		}); 
		
		
//		var formData =new FormData();
//		formData.append('bookName',bookName);
//		formData.append('isbn10',bookibsn);
//		formData.append('source',booksource);
//		formData.append('position',bookposition);
//		formData.append('Isisbn10',bookIsibsn);
//		
//		var xhr =new XMLHttpRequest();
//		  xhr.open('POST','Excelexport.action',true);
//		  xhr.onload =function(e){
//			  if(this.status ==200){
//			  
//			  }
//			  };
//
//		  xhr.send(formData);
		
	//	sending.style.visibility="visible";
		var form=$("<form>");//定义一个form表单
		form.attr("style","display:none");
		form.attr("target","");
		form.attr("method","post");
		form.attr("action","Excelexport.action");
		var input1=$("<input>");
		input1.attr("type","hidden");
		input1.attr("name","bookName");
		input1.attr("value",bookName);
		var input2=$("<input>");
		input2.attr("type","hidden");
		input2.attr("name","isbn10");
		input2.attr("value",bookibsn);
		var input3=$("<input>");
		input3.attr("type","hidden");
		input3.attr("name","source");
		input3.attr("value",booksource);
		var input4=$("<input>");
		input4.attr("type","hidden");
		input4.attr("name","position");
		input4.attr("value",bookposition);
		var input5=$("<input>");
		input5.attr("type","hidden");
		input5.attr("name","Isisbn10");
		input5.attr("value",bookIsibsn);
		$("body").append(form);//将表单放置在web中
		form.append(input1);
		form.append(input2);
		form.append(input3);
		form.append(input4);
		form.append(input5);
		form.submit();//表单提交 
		form.remove();
		//
		} 
	//AJAX方法取得数据并显示到页面上 
	
//AJAX方法取得总页数 
/*function GetPageCount() { 
	
		$.ajax({ 
		type: "get", 
		dataType: "json", 
		url: "getTotalPage.action", 
		data: { }, //"wherePageCount" + where,个人建议不用这种方式 
		async: false, 
		success: function(msg) { 
			
			//alert(ajaxobj.totalPages);
			document.getElementById("lblPageCount").innerHTML = msg; 
		} 
		}); 
} 

//AJAX方法取得记录总数 
function GetTotalCount() { 
	
	$.ajax({ 
		type: "get", 
		dataType: "json", 
		url: "getTotal.action", 
		data: {}, 
		async: false, 
		success: function(msg) {
			//ajaxobj=eval("("+msg+")"); 
			//alert(ajaxobj.totalRows);
			document.getElementById("lblToatl").innerHTML = msg; 
		} 
	}); 
} 
function Download() { 
	
	$.ajax({ 
	type: "get", 
	dataType: "json", 
	url: "getallbookinfo.action", 
	data: { }, //"wherePageCount" + where,个人建议不用这种方式 
	async: false, 
	success: function(msg) { 
		
	}, 
	error:function(msg){
		alert(msg); 
	}
	}); 
} */


/*function save(){  
	
//	$.ajax({
//        cache: true,
//        type: "post",
//        contentType:'text/html',
//        url:"saveimportbookinfo.action",
//        data:$("#myForm").serialize(),// 你的formid
//        async: false,
//        error: function(request) {
//            alert("Connection error");
//        },
//        success: function(msg) {
//        	ajaxobj=eval("("+msg+")")
//        	
//           alert(ajaxobj.context);
//        }
//    });
	var options = {
			url:"saveimportbookinfo.action",
			 type: "post",
			dataType : "json",
			beforeSubmit : function() {
				alert("正在上传");
			},
			success : function(result) {
				ajaxobj=eval("("+msg+")")      	
		           alert(ajaxobj.context);
			},
			error : function(result) {
				var data = jQuery.parseJSON(result.result);
				var message = data.message;
				$file.remove();
				alert(message);
			}
	};
	$("#myForm").ajaxForm(options);
   
  
   
}*/