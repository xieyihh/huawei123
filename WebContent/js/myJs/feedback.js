	var pageIndex = 1; //页索引 
	var pageSize=10;
	var usernumber=	"";
	var feedbackname="";
	var starttime="";
	var endtime="";
	var oldliid="";
	var oldview="";
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
			url: "getFeedbackImage.action", //要访问的后台地址 
			data: { "pageSize": pageSize ,"currentPage": pageIndex,}, //要发送的数据 
			ajaxStart: function() { $("#load").show(); }, 
			complete: function() { $("#load").hide(); }, //AJAX请求完成时隐藏loading提示 
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
//				try{
//					var text = JSON.stringify(msg);
//					var myData = JSON.parse(text);
				if (msg.length !== 0) { 
					var t = document.getElementById("feedbackInfo"); //获取展示数据的表格 
					while (t.rows.length !== 0) { 
						t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除 
					} 
					
				} 
				$("#lblPageCount").text(msg.context.totalPages); 
				$("#lblToatl").text(msg.context.totalRows); 
				var info=msg.context.feedbackInfo;
				$.each(info, function(i, item) {
					var time=item.feedbacktime;
					var date=time.replace(/\-/g,'.');
					var feedback="";
					if(item.phonenumber!==""){
						feedback ='<tr id="tr'+item.id+'">'+
										'<td class="master">'+
											'<div class="content" id="content'+item.id+'">'+
												'<div class="txt">'+item.feedbackcontent +'</div>'+
												
											'</div>'+
											'<div class="rate-date">'+date +'</div>'+
										'</td>'+
										'<td style="width:15%">'+
											'<div class="sku" >'+
												'<p title='+item.feedbackname+'>'+
													'<span>姓名：</span>'+item.feedbackname+
												'</p>'+
												'<p title='+item.usernumber+'>'+
													'<span>工号：</span>'+item.usernumber+
												'</p>'+
												'<p title='+item.phonenumber+'>'+
													'<span>电话：</span>'+item.phonenumber+
												'</p>'+
											'</div>'+
										'</td>'+
										'<td style="width:13%">'+
											'<div id="starBg'+item.id+'" class="star_bg">'+                   	
												'<a href="#" id="stars1'+item.id+'" class="star star_1" title="1"></a>'+
												'<a href="#" id="stars2'+item.id+'" class="star star_2" title="2"></a>'+
												'<a href="#" id="stars3'+item.id+'"class="star star_3" title="3"></a>'+
												'<a href="#" id="stars4'+item.id+'" class="star star_4" title="4"></a>'+
											  	'<a href="#" id="stars5'+item.id+'"class="star star_5" title="5"></a>'+
											  	
											  '</div>'+
										'</td>'+
										'<td style="width:6%" id="score'+item.id+'">'+
										
										'</td>'+
										'<td style="width:10%">'+
											'<button type="button" id="Reply'+item.id+'" >短信回复</button>'+
										'</td>'+
								'</tr>';
					}else{
						feedback ='<tr  id="tr'+item.id+'">'+
						'<td class="master">'+
							'<div class="content" id="content'+item.id+'">'+
								'<div class="txt">'+item.feedbackcontent +'</div>'+
								
							'</div>'+
							'<div class="rate-date">'+date +'</div>'+
						'</td>'+
						'<td style="width:15%">'+
							'<div class="sku" >'+
								'<p title='+item.feedbackname+'>'+
									'<span>姓名：</span>'+item.feedbackname+
								'</p>'+
								'<p title='+item.usernumber+'>'+
									'<span>工号：</span>'+item.usernumber+
								'</p>'+
								
							'</div>'+
						'</td>'+
						'<td style="width:13%">'+
							
						'</td>'+
						'<td style="width:6%" id="score'+item.id+'">'+
						
						'</td>'+
						'<td style="width:10%">'+
							'<button type="button" id="Reply" disabled>短信回复</button>'+
						'</td>'+
					'</tr>';
					}
					
					$("#feedbackInfo").append(feedback);
					if(item.phonenumber!==""){
						if(item.jifen!=0){
							$('td#score'+item.id).append('<span>'+item.jifen+'分</span>');
							setScore(item.id,item.jifen);
							//$('a#stars'+item.jifen+''+item.id)[0].click();
						}else{
							$('td#score'+item.id).append('<span>请打分</span>');
						}
						if(item.smsContent!==""){
							$('#content'+item.id).append('<div class="txt">[回复：]'+item.smsContent+'</div>');
							$('#Reply'+item.id).attr('disabled',true);
						}
					}
					
					if(item.imagename.length!==0){
						var photo ='<div class="photos">'+
										'<ul class="photos-thumb" id="ul'+item.id+'">'+
							
										'</ul>'+
										'<div class="tm-m-photo-viewer" style=" display: none;" id="viewer'+item.id+'">'+    
											'<img src=" " style="width:200px;height:200px"/>'+
											'</div>'+
									'</div>';
						$('#content'+item.id+'').append(photo);
						$.each(item.imagename, function(j, img) {
							var image ='<li data-src="/uploadImg/'+img+'" id="img'+item.id+''+j+'">'+
												'<img src="/uploadImg/'+img+'" style="width:60px;height:60px;" >'+
											'</li>';
							$('#ul'+item.id+'').append(image);
						});
					}
				});
				
				
				 bindPager();
//				}catch(error){
//					alert('返回json不合法')
//				}
			}, 
			error: function() { 
				var t = document.getElementById("feedbackInfo"); //获取展示数据的表格 
				while (t.rows.length !== 0) { 
					t.removeChild(t.rows[0]); //在读取数据时如果表格已存在行．一律删除   
				} 
				alert("加载数据失败"); 
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
			//alert(page+"   "+pageCount);
			if (page !== null &&   page !== "") { 
				if(isNaN(page)){
					alert('页码必须为数字')
				}else if(page>pageCount||page<1){
					alert("不存在这一页");
				}else{
					pageIndex = page; 
					//alert(pageIndex);
					bindData();
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
					bindData();
				}
				//alert(pageIndex);
			} 
			});
		
		//查询 
		$("button#btnSearch").click(function() { 
		
			 usernumber=	$("input#usernumber").val();
			 feedbackname= $("input#feedbackname").val();
			 starttime= $("input#starttime").val();
			 endtime=$("input#endtime").val();	
					
				
			pageSize=1;
			bindData(); 
		});  
		
		$(document).on("click","[id^='img']",function(){//修改成这样的写法
			
			var liid=$(this).attr("id").toString();
			var view=liid.substring(3,liid.length-1);
			//alert(view)
			var img_src=$('#'+liid+'>img').attr("src");
			/**
			 * 操作同一张图片时 没图片点击显示 有图片点击隐藏
			 * 操作同一个div中的不同图片 没图片点击显示 有图片点击更换图片同时更改边框颜色
			 * 操作不同div 前一个div有图片点击前一个div不显示同时显示当前div这张图片 前一个div没图片则显示这个div的图片
			 */
			if(liid===oldliid){
				if($('#viewer'+view+'').is(":hidden")){
					$('#viewer'+view+' img').attr('src',img_src);
					$(this).css('border','2px solid #f23d6a');
					$(this).css('cursor',"zoom-out");
					$('#viewer'+view+'').css('display','block');
				}else{
					$(this).css('cursor',"zoom-in");
					$(this).css('border','2px solid #f2f2f2');
					$('#viewer'+view+'').css('display','none');
				}
			}else if(oldview===view){
				if($('#viewer'+view+'').is(":hidden")){
					$('#viewer'+view+' img').attr('src',img_src);
					$(this).css('border','2px solid #f23d6a');
					$(this).css('cursor',"zoom-out");
					$('#viewer'+view+'').css('display','block');
				}else{
					$('#'+oldliid+'').css('cursor',"zoom-in");
					$('#'+oldliid+'').css('border','2px solid #f2f2f2');
					$('#viewer'+view+' img').attr('src',img_src);
					$(this).css('border','2px solid #f23d6a');
					$(this).css('cursor',"zoom-out");
					$('#viewer'+view+'').css('display','block');
				}
			}else{
				if($('#'+oldliid+'').is(":hidden")){
					if($('#viewer'+view+'').is(":hidden")){
						$('#viewer'+view+' img').attr('src',img_src);
						$(this).css('border','2px solid #f23d6a');
						$(this).css('cursor',"zoom-out");
						$('#viewer'+view+'').css('display','block');
					}
//					else{
//						//$('#'+oldliid+'').css('cursor',"zoom-in");
//						//$('#'+oldliid+'').css('border','2px solid #f2f2f2');
//						$(this).css('cursor',"zoom-in");
//						$(this).css('border','2px solid #f2f2f2');
//						$('#viewer'+view+'').css('display','none');
//					}
				}else{
					$('#'+oldliid+'').css('cursor',"zoom-in");
					$('#'+oldliid+'').css('border','2px solid #f2f2f2');
					$('#viewer'+oldview+'').css('display','none');
					$('#viewer'+view+' img').attr('src',img_src);
					$(this).css('border','2px solid #f23d6a');
					$(this).css('cursor',"zoom-out");
					$('#viewer'+view+'').css('display','block');
				}
					

			}
			
			oldliid=liid;
			oldview=view;
		});
		$(document).on("mouseenter","[id^='stars']",function(){//修改成这样的写法
			
			var num=$(this).attr("id").toString().substring(5,6);
			var id=$(this).attr("id").toString().slice(6);
			
			if(num==1){
				 $(this).addClass("starhover1"); 
			}
			if(num==2){
				 $(this).addClass("starhover2"); 
			}
			if(num==3){
				 $(this).addClass("starhover3"); 
			}
			if(num==4){
				 $(this).addClass("starhover4"); 
			}
			if(num==5){
				 $(this).addClass("starhover5"); 
			}
		});
		$(document).on("mouseleave","[id^='stars']",function(){ 
			//alert($(this).attr("id").toString())
			var num=$(this).attr("id").toString().substring(5,6);
			var id=$(this).attr("id").toString().slice(6);
			if(num==1){
				 $(this).removeClass("starhover1");  
			}
			if(num==2){
				 $(this).removeClass("starhover2");  
			}
			if(num==3){
				 $(this).removeClass("starhover3");  
			}
			if(num==4){
				 $(this).removeClass("starhover4");  
			}
			if(num==5){
				 $(this).removeClass("starhover5");  
			}
	        
		  });
		$(document).on("mouseenter","[id^='starBg']",function(){//修改成这样的写法
			var id=$(this).attr("id").toString();

			$('#'+id+' a').addClass("starbghover"); 
			
		});
		$(document).on("mouseleave","[id^='starBg']",function(){//修改成这样的写法
			var id=$(this).attr("id").toString();
			 $('#'+id+' a').removeClass("starbghover"); 
		
	});
		$(document).on("click","[id^='stars']",function(){ 
			var stars=$(this).attr("id").toString();
		
				var num=stars.substring(5,6);
				id=stars.slice(6);
				setScore(id,num);
			//alert(num)
				$.ajax({ 
					type: "get", 
					dataType: "json", 
					url: "addJifen.action", 
					data: {"id":id,"jifen":num}, //"wherePageCount" + where,个人建议不用这种方式 
					async: false, 
					success: function(msg) { 
						if(msg.message==="success"){
							$('td#score'+id+' span').html(num+"分");
						}
					}, 
					error:function(msg){
						alert(msg); 
					}
					}); 
			
			
		});
		/*
		 * 编辑短信内容窗口
		 */
		$(document).on("click","[id^='Reply']",function(){//修改成这样的写法
			
			var id=$(this).attr("id").toString().slice(5);
			$('#tr'+id+' td').css('border-bottom','0px');
			var Editmessage='<tr id="editTr'+id+'" style="width:100%">'+
								'<td colspan="4">'+
									'<textarea rows="6" style="width:80%;margin-left:4%;"id="msg'+id+'"></textarea>'+
								'</td>'+
								'<td >'+
									'<button type="button" id="saveMsg'+id+'" >发送短信</button>'+
								'</td>'+
							'</tr>';
			$('#tr'+id+'').after(Editmessage);
			
		});
		/*
		 * 发送短信
		 */
		$(document).on("click","[id^='saveMsg']",function(){//修改成这样的写法
			
			var id=$(this).attr("id").toString().slice(7);
			if($('#msg'+id+'').val()==""){
				alert("请填写短信内容");
				return false;
			}
			var data={
					id:id,
					phonenumber:$('#tr'+id+' td:nth-child(2) div p:nth-child(3)').attr('title'),
					smsContent:$('#msg'+id+'').val(),	
					usernumber:$('#tr'+id+' td:nth-child(2) div p:nth-child(2)').attr('title')
			}
			//alert(data.id+";"+data.phonenumber+";"+data.smsContent+";"+data.usernumber);
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "feedBackSendSms.action", 
				data: data, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) { 
					if(msg.message==="success"){
						alert('短信回复成功')
						bindData()
					}else{
						alert('短信回复失败')
					}
				}, 
				error:function(msg){
					alert(msg); 
				}
				}); 
		});
});
	
	function setScore(id ,num){
		 $('a#stars1'+id+'').removeClass("scorechecked1"); 
		 $('a#stars2'+id+'').removeClass("scorechecked2");
		 $('a#stars3'+id+'').removeClass("scorechecked3"); 
		 $('a#stars4'+id+'').removeClass("scorechecked4"); 
		 $('a#stars5'+id+'').removeClass("scorechecked5"); 
		if(num==1){
			 $('a#stars1'+id+'').addClass("scorechecked1"); 
		}
		if(num==2){
			$('a#stars2'+id+'').addClass("scorechecked2"); 
		}
		if(num==3){
			$('a#stars3'+id+'').addClass("scorechecked3"); 
		}
		if(num==4){
			$('a#stars4'+id+'').addClass("scorechecked4"); 
		}
		if(num==5){
			$('a#stars5'+id+'').addClass("scorechecked5"); 
		}
	}