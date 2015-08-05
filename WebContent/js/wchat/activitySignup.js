

$(function() {
	
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getActivityInit.action", //要访问的后台地址 
		data: {"activityName":activityName}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			if(msg.message==="success"){
				$('.panel-body').html("");
				var initdata=msg.context.aactivityInit;
				var nameAndnumber='<div class="input-group">'+
										'<span class="input-group-addon" >姓名</span>'+
										'<input type="text" class="form-control" name="username" value="'+msg.context.username+'" readonly >'+
							'</div>'+
							'<div class="input-group">'+
								'<span class="input-group-addon" >工号</span>'+
								'<input type="text" class="form-control" name="usernumber"  value="'+msg.context.usernumber+'" readonly >'+
							'</div>';
				$('.panel-body').append(nameAndnumber);
				var itemFlag=[];
				$.each(initdata.itemFlag, function(i, item) {
					itemFlag[i]=item;
				});
				var txt=0;var file=0;
				$.each(initdata.itemName, function(i, item) {
						if(itemFlag[i]==="2"){
							var content='<div class="input-group">'+
											'<span class="input-group-addon" >'+item+'</span>'+
											'<input type="text" class="form-control" name="text" id="text'+txt+'" required="required" >'+
										'</div>';
							txt++;
						}else if(itemFlag[i]==="3"){
							var content='<div class="input-group">'+
											'<span class="input-group-addon" >'+item+'</span>'+
											'<div class="form-control" style="padding: 0px;">'+
												'<a href="javascript:;" class="file" id="aclick'+file+'">浏览...'+
									    			'<input type="file"  name="file" id="file'+file+'" required="required"/>'+
												'</a>'+
											'</div>'+
											//accept="image/gif, image/jpeg, image/png"
											//'<input type="file" class="form-control" name="file" id="file'+file+'" required="required" >'+
										'</div>';
							file++;
						}
						$('.panel-body').append(content);
					});
//				var res='<input id="res" name="res" type="reset" style="display:none;" /> ';
//				$('.panel-body').append(res);
				var name='<input  name="activityName" type="text"  value="'+activityName+'" readonly style="display:none;" /> ';
				$('.panel-body').append(name);
				var width=0;
				$('.input-group-addon').each(function(){
					if($(this).outerWidth()>width){
						width=$(this).outerWidth();
					}
				});
				$('.input-group-addon').css('width',width);
			}
		}, 
		error: function() { 
			alert("保存数据失败"); 
		} 
	}); 
	/**
	 * 提交报名信息
	 */
	$("#submitData").click(function() { 
		
		var inputList = $("#activity .panel-body input[required='required']");
		var validateResult = validateInputList(inputList);
		if(!validateResult) {
			return false;
		}
//		document.forms[0].action =  "saveActivitySignup.action";
//        document.forms[0].submit();
		$('#activity').submit()

	 });
	/**
	 * 重置数据
	 */
	$("#resetData").click(function() {
		//document.forms[0].reset();
		//$("input[name='res']").click(); 
		$("#activity").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
		return false;
	});

	/**
	 * 选择文件后，显示文件名陈和文件大小
	 */
	$(document).on("change","input[id^='file']",function(){//修改成这样的写法
		var fileid=$(this).attr("id").toString().slice(4);
		var f = document.getElementById("file"+fileid).files;  
        //上次修改时间  
		// alert(f[0].lastModifiedDate);  
        //名称  
       // alert(f[0].name);  
        //大小 字节  
        //alert((f[0].size/1024).toFixed(2)+"KB");  
        //类型  
        //alert(f[0].type);  
		var content='<p  class="fileinfo">文件名：'+f[0].name+';大小：'+(f[0].size/1024).toFixed(2)+"KB"+';类型：'+f[0].type+'</p>';
		$('#aclick'+fileid+'').before(content);
	  });
});
