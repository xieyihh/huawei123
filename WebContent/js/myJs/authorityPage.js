	var checkname=['book','physical','activity','feedback','superAdmin']
	$(function() { 
		
		
		BindData(); 
		/**
		 * 切换管理级别
		 */
		$("select#authority").change(function(){
			 showCurrentAuthority();
		});
		
	/**
	 * 保存权限设置
	 */
		$("button#btnSave").click(function() { 
			
			var authority =$("select#authority option:checked").val(); 
			if(authority==null){
				authority="";
			}
			var book="";
			$("input:checkbox[name=book]:checked").each(function(i){  
	            if(0==i){  
	            	book = $(this).val();  
	            }else{  
	            	book += (","+$(this).val());  
	            }  
	        });  
			var physical="";
			$("input:checkbox[name=physical]:checked").each(function(i){  
	            if(0==i){  
	            	physical = $(this).val();  
	            }else{  
	            	physical += (","+$(this).val());  
	            }  
	        });  
			var activity="";
		
			$("input:checkbox[name=activity]:checked").each(function(i){  
	            if(0==i){  
	            	activity = $(this).val();  
	            }else{  
	            	activity += (","+$(this).val());  
	            }  
	        }); 
			
			var feedback="";
			$("input:checkbox[name=feedback]:checked").each(function(i){  
	            if(0==i){  
	            	feedback = $(this).val();  
	            }else{  
	            	feedback += (","+$(this).val());  
	            }  
	        }); 
			var superAdmin="";
			$("input:checkbox[name=superAdmin]:checked").each(function(i){  
	            if(0==i){  
	            	superAdmin = $(this).val();  
	            }else{  
	            	superAdmin += (","+$(this).val());  
	            }  
	        }); 
			//alert("userlevel:"+authority+'\r\nbook:'+book+'\r\nphysical:'+physical+'\r\nsuperAdmin:'+superAdmin);
			$.ajax({ 
				type: "get", 
				dataType: "json", 
				url: "saveuserrolepopedom.action", 
				data: {'userlevel':authority,"book":book,"physical":physical,"activity":activity,'feedback':feedback,"superAdmin":superAdmin}, //"wherePageCount" + where,个人建议不用这种方式 
				async: false, 
				success: function(msg) { 
					if(msg.message=="success"){
						alert('保存成功');
					}
					
					
				}, 
				error:function(msg){
					alert(msg.message); 
				}
			}); 	
		});  
		
		
	

	
	});
	
	function showCurrentAuthority(){
		var authority =$("select#authority option:checked").val(); 
		if(authority==null){
			authority="";
		}
		$.ajax({
			type: "get", 
			dataType: "json", 
			url: "getuserrolepopedom.action", 
			data: {'userlevel':authority}, //"wherePageCount" + where,个人建议不用这种方式 
			async: false, 
			success: function(msg) { 
				if(msg.message==="success"){
					//alert(msg.context.rolelist[0].popedomcode);
					var arr=msg.context.rolelist[0].popedomcode.split(";"); 
					for(var i=0;i<arr.length;i++){
						if(arr[i]!==""){
							var content=arr[i].split(',');
							var boxname=checkname[i];
							$('input[name='+boxname+']').each(function () {
								var count=0;
								for(var j=0;j<content.length;j++){
				                    if ($(this).val()===content[j]) {
				                    	 $(this).prop("checked", true);
				                    	 break;
				                    }else{
				                    	count++;
				                    }
								}
								if(count===content.length){
									
									 $(this).prop("checked", false);
								}
								
			                 });

							
						}else{
							var boxname=checkname[i];
							$('input[name='+boxname+']').prop("checked", false);

						}
					}
					
				
				}
			}, 
			error:function(msg){
				alert(msg.message); 
			}
		}); 	
		
	}
	//AJAX方法取得数据并显示到页面上 
	function BindData() { 
		
		$.ajax({ 
			type: "get", 
			dataType: "json", 
			url: "getuserlevel.action", 
			data: {}, //"wherePageCount" + where,个人建议不用这种方式 
			async: false, 
			success: function(msg) { 

				 $.each(msg.context.userlevelarray, function(i, item) {
					 var count=0;
					 var sel = document.getElementById("authority");
					 $("#authority option").each(function(){ //遍历全部option
					        var txt = $(this).text(); //获取option的内容
					        //alert(txt);
					         if(item!=txt){
								count++;
							}
					     });
					 
					 if(count==sel.options.length){
						 if(i===0){
							 $("select#authority").append(' <option value='+(i+1)+' selected="selected">'+item+'</option>');
							
						 }else{
							 $("select#authority").append(' <option value='+(i+1)+'>'+item+'</option>');
						 }
					 }
					
					
					});
				 showCurrentAuthority();
				
			}, 
			error:function(msg){
				alert(msg.message); 
			}
			}); 	
		} 

