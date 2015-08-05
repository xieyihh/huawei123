	var pageIndex = 1; //页索引 
	var pageSize=10;
	var usernumber="";
	var username="";
//	var physicalnumber="";
	var physicalposition="";
	var physicalreservedate="";
	var physicaldate="";
	var physicalplan="";
	var physicalstate="";
	var hasRelatives="";
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
//			var physicalNumber = $("input#physicalNumber").val(); 
//			//alert(name);
//			if (physicalNumber != null ) { 
//				physicalnumber=physicalNumber; 
//			} 
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
			
			var position =$("select#physicalposition option:checked").val(); 
			//alert(position);
			if (position != null ) { 
				physicalposition=position; 
			} 
			var state =$("select#physicalState option:checked").val(); 
			//alert(position);
			if (state != null ) { 
				physicalstate=state; 
			} 
			var plan =$("select#physicalPlan option:checked").val(); 
			//alert(position);
			if (plan != null ) { 
				physicalplan=plan; 
			} 
			
			var relatives=$('input:radio[name="hasRelatives"]:checked').val();
			if(relatives!=null){
            	//alert(isIsbn);
				hasRelatives=relatives;
            }
			pageIndex = 1; 
			BindData(); 
		});  
		//导出体检信息
		$("button#exportPhysicalinfo").click(function() { 
			
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
//			var physicalNumber = $("input#physicalNumber").val(); 
//			//alert(name);
//			if (physicalNumber != null ) { 
//				physicalnumber=physicalNumber; 
//			} 
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
			
			var position =$("select#physicalposition option:checked").val(); 
			//alert(position);
			if (position != null ) { 
				physicalposition=position; 
			} 
			var state =$("select#physicalState option:checked").val(); 
			//alert(position);
			if (state != null ) { 
				physicalstate=state; 
			} 
			var plan =$("select#physicalPlan option:checked").val(); 
			//alert(position);
			if (plan != null ) { 
				physicalplan=plan; 
			} 
			
			var relatives=$('input:radio[name="hasRelatives"]:checked').val();
			if(relatives!=null){
            	//alert(isIsbn);
				hasRelatives=relatives;
            }
			
			exportPhysicalInfo();
		});  
		//导出家属信息
		$("button#exportRelativeinfo").click(function() { 
			
			
			var plan =$("select#physicalPlan option:checked").val(); 
			//alert(position);
			if (plan != null ) { 
				physicalplan=plan; 
			} 
			
			exportRelativeInfo();
		});  
		
	//查看家属
		$(document).on("click","[id^='relative']",function(){//修改成这样的写法
			var listNum=$(this).attr("id").toString().slice(8);
			//alert(listNum)
			var plan = $('#plan'+listNum+'').text(); 
			var number = $('#number'+listNum+'').text(); 
			var name = $('#name'+listNum+'').text(); 
			//alert(plan+';'+number)
			var form=$("<form>");//定义一个form表单
			form.attr("style","display:none");
			form.attr("target","");
			form.attr("method","post");
			form.attr("action","relatives.jsp");
			var input1=$("<input>");
			input1.attr("type","hidden");
			input1.attr("name","physicalplan");
			input1.attr("value",plan);
			var input2=$("<input>");
			input2.attr("type","hidden");
			input2.attr("name","usernumber");
			input2.attr("value",number);
			var input3=$("<input>");
			input3.attr("type","hidden");
			input3.attr("name","username");
			input3.attr("value",name);
			
			
			$("body").append(form);//将表单放置在web中
			form.append(input1);
			form.append(input2);
			form.append(input3);
			

			form.submit();//表单提交 
			form.remove();
			//document.forms[0].action= "relatives.jsp?physicalplan="+plan+"&usernumber="+number+"&username="+name;
			//document.forms[0].submit();
		  });
		
//		$(document).on("dblclick","[id^='changeDate']",function(){//修改成这样的写法
//			var myDate = new Date();
//			var year=myDate.getFullYear();    //获取完整的年份(4位,1970-????)
//			var month=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
//			var date=myDate.getDate();        //获取当前日(1-31)
//			var old=$(this).text();
//			var oldyear=old.slice(0,4);
//			var oldmonth=old.slice(5,7);
//			var oldday=old.slice(8,10);
//			//alert(year+'-'+month+'-'+date);
//			//alert(oldyear+'-'+oldmonth+'-'+oldday);
//			if(year<oldyear||month<oldmonth||date<(oldday-2)){//提前三天
//				var id=$(this).attr("id").toString();
//				var listNum=id.slice(10);
//				var temp=$(this);
//				$(this).text("");
//				var input = "<input type='text' id='changedate' value="+old+" >";
//				$(this).append(input);
//				//$("input#changedate").focus();
//				$("input#changedate").focus(function() {
//					WdatePicker({skin:'ext',dateFmt:'yyyy-MM-dd',minDate:'%y-%M-#{%d+1}',lang:'zh-cn',startDate:old,highLineWeekDay:true,	
//						onpicked:function(dp){			
//							//alert($(this).val().toString().length+';'+old.toString().trim().length)
//							if(($(this).val()!="") &&($(this).val()!=old.trim())){
//								//alert('input');
//								if(confirm("确定修改体检预约时间吗？")){
//									$(this).closest("td").text($(this).val());
//									$.ajax({ 
//										type: "get", 
//										dataType: "json", 
//										url: "savephysicalreservedate.action", 
//										data: {"id": listNum,"physicalreservedate":$(this).val()}, //"wherePageCount" + where,个人建议不用这种方式 
//										async: false, 
//										success: function(msg) { 
//											alert('修改成功');
//											BindData();
//										}, 
//										error:function(msg){
//											alert(msg.message); 
//										}
//										}); 
//								}else{
//									//alert(old)
//									$(this).closest("td").text(old);
//									$(this).remove();
//								}
//							}else{
//								//alert(old)
//								$(this).closest("td").text(old);
//								$(this).remove();
//							}
//						}
//					});
//					});
//				document.onclick = function(e) {
//					
//					var ele = e ? e.target : window.event.srcElement;
//					var fdStart = ele.id.toString().indexOf("changedate");
//					if(ele.id !== 'WdateDiv' && fdStart!=0) {
//						$('input#changedate').closest("td").text(old);
//						$('input#changedate').remove();
//					}
//				}
//			}else{
//				alert('需要提前7天修改');
//				
//			}
//			
//		  });
		
//删除
		
		$(document).on("click","[id^='delete']",function(){//修改成这样的写法
			if(confirm("确定删除这条数据吗？"))
			 {
				var listNum=$(this).attr("id").toString().slice(6);
				//alert(listNum);
				$.ajax({ 
					type: "get", 
					dataType: "json", 
					url: "deletephysicalreservedate.action", 
					data: {"id": listNum}, //"wherePageCount" + where,个人建议不用这种方式 
					async: false, 
					success: function(msg) { 
						
						BindData();
					}, 
					error:function(msg){
						alert(msg.message); 
					}
					}); 	
					
			 }
		});
		
	});
	
	
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
		
			$.ajax({ 
				type: "get", //使用get方法访问后台 
				dataType: "json", //返回json格式的数据 
				url: "phsicalexamsearch.action", //要访问的后台地址 
				data: { "pageSize": pageSize ,"currentPage": pageIndex ,"usernumber":usernumber,"username":username,
					"physicalposition":physicalposition,"physicalreservedate":physicalreservedate,
					 "physicaldate": physicaldate ,"physicalplan":physicalplan,"physicalstate":physicalstate,"hasRelatives":hasRelatives}, //要发送的数据 
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
											'<td width="10%" id="number'+item.id+'" >' + item.usernumber + '</td>'+
											'<td width="10%" id="name'+item.id+'">' + item.username + '</td>'+
											'<td width="15%" >' + item.physicalposition + '</td>'+
											'<td width="15%"  >' + item.physicalreservedate + '</td>'+//id="changeDate'+item.id+'"
											'<td width="15%">' +item.physicaldate + '</td>'+
											'<td width="5%">' + item.physicalstate + '</td>'+
											'<td width="10%" id="plan'+item.id+'" >' + item.physicalplan + ' </td>';
						//" </td><td width=\"10%\">" + item.physicalnumber+
						if(item.hasRelatives=="无"){
							 bodyContent+='<td width="10%">'+item.hasRelatives+'</td>';
						}else{
							bodyContent+='<td width="10%"><a id="relative'+item.id+'" >' + "查看家属详情" + ' </a></td>';
						}
						bodyContent+='<td width="10%"><a id="delete'+item.id+'" > <img src="img/delete.gif" border="0" title="删除"></a> </td></tr>'
						$("#tb_body").append(bodyContent); 
						/*<td width="10%"><a id="edit'+list+'" >' + "编辑" + ' </a></td>
						 * $("#tb_body").append("<tr><td>" + 12+ "</td><td>" + 12 + " </td><td>" +12+ 
								" </td><td>" + 2 + " </td><td>" + 2 + " </td><td>" + 12 + 
								"</td><td>" + 12+ " </td></tr>"); */
						
					});
					
					 $(".tableone tr:nth-child(even)").addClass("trOdd");
					 $("td").addClass("bookList");
					 //$("td").css("color","#ccc");
					 //$("td").css("text-align","center");
					
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
					 $.each(msg.context.physical_state, function(i, item) {
						 var count=0;
						 var sel = document.getElementById("physicalState");
						 $("#physicalState option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item!=txt){
									count++;
								}
						     });
						 
						 if(count==sel.options.length){
							 $("select#physicalState").append(' <option value='+(i+1)+'>'+item+'</option>');
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
function exportRelativeInfo(){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportphysicalrelativesinfo.action");
	
	var input7=$("<input>");
	input7.attr("type","hidden");
	input7.attr("name","physicalplan");
	input7.attr("value",physicalplan);
	$("body").append(form);//将表单放置在web中
	form.append(input7);
	form.submit();//表单提交 
	form.remove();
}
//导出体检信息
function exportPhysicalInfo(){
	var form=$("<form>");//定义一个form表单
	form.attr("style","display:none");
	form.attr("target","");
	form.attr("method","post");
	form.attr("action","exportphysicalinfo.action");
	var input1=$("<input>");
	input1.attr("type","hidden");
	input1.attr("name","usernumber");
	input1.attr("value",usernumber);
	var input2=$("<input>");
	input2.attr("type","hidden");
	input2.attr("name","username");
	input2.attr("value",username);
//	var input3=$("<input>");
//	input3.attr("type","hidden");
//	input3.attr("name","physicalnumber");
//	input3.attr("value",physicalnumber);
	
	var input4=$("<input>");
	input4.attr("type","hidden");
	input4.attr("name","physicalposition");
	input4.attr("value",physicalposition);
	var input5=$("<input>");
	input5.attr("type","hidden");
	input5.attr("name","physicalreservedate");
	input5.attr("value",physicalreservedate);
	var input6=$("<input>");
	input6.attr("type","hidden");
	input6.attr("name","physicaldate");
	input6.attr("value",physicaldate);
	var input7=$("<input>");
	input7.attr("type","hidden");
	input7.attr("name","physicalplan");
	input7.attr("value",physicalplan);
	var input8=$("<input>");
	input8.attr("type","hidden");
	input8.attr("name","physicalstate");
	input8.attr("value",physicalstate);
	var input9=$("<input>");
	input9.attr("type","hidden");
	input9.attr("name","hasRelatives");
	input9.attr("value",hasRelatives);
	$("body").append(form);//将表单放置在web中
	form.append(input1);
	form.append(input2);
//	form.append(input3);
	form.append(input4);
	form.append(input5);
	form.append(input6);
	form.append(input7);
	form.append(input8);
	form.append(input9);

	form.submit();//表单提交 
	form.remove();
}