/**
 * imgk 图片个数
 * 
 * D 删除的个数
* pictute 文件二进制流
 */
var imgk=0;
var dataNum=0;
var D=0;

var deletePic= [];
var datas=[];
var contentnum=1;
/**
 * 填入已有信息
 */
$(function(){
	var activityStatus=1;//发布状态
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getActivityDictionary.action", //要访问的后台地址 
		data: {"activityStatus":activityStatus}, //要发送的数据 
		//data: {}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message==="success"){
					$.each(msg.context.activitydictionary, function(i, item) {
						 var count=0;
						// var sel = document.getElementById("activityLists");
						 $("#activityLists option").each(function(){ //遍历全部option
						        var txt = $(this).text(); //获取option的内容
						        //alert(txt);
						         if(item!==txt){
									count++;
								}
						     });
						 
						 if(count=== $("#activityLists option").length){
							 if(i===0){
								$("select#activityLists").append(' <option value='+item.activityid+' selected="selected">'+item.activityName+'</option>');
							}else{
								$("select#activityLists").append(' <option value='+item.activityid+'>'+item.activityName+'</option>');
							}
						}
						});
						var activity=$("select#activityLists option:selected").val();
						//alert(activity);
						dataInit(activity);
				}
		}, 
		error: function() { 
			alert("加载数据失败"); 
		} 
	}); 
	
	$("select#activityLists").change(function(){
		var activity=$("select#activityLists option:selected").val();
		//alert(activity)
		imgk=0;
		dataNum=0;
		D=0;
		deletePic= [];
		datas=[];
		contentnum=1;
		dataInit(activity);
	});
	$(document).on("mouseover","[id^='divImg']",function(){//修改成这样的写法
		
        $(this).addClass("border");
        //显示删除叉
        $(this).find(".closeimg").css("display", "block");
    });
	$(document).on("mouseout","[id^='divImg']",function(){//修改成这样的写法
	
	        $(this).removeClass("border");
	        //隐藏删除叉
	        $(this).find(".closeimg").css("display", "none");
	
	    });
	
	$(document).on("click","[id^='closeimg']",function(){//修改成这样的写法
		var listNum=$(this).attr("id").toString().slice(8);
		$('#divImg'+listNum+'').remove();
		deletePic[D]=listNum;
	     D++;
	    
	     canAdd();
		});
	$(document).on("click","[id='addContent']",function(){
		var content='<br><div class="form-group" style="margin-right:0px">'+
		 				'<p class="form-control-static">内容：</p>'+
		 			'</div>'+
		 			'<div class="form-group inputfix" style="margin-left:5px;" >'	+
		 				'<textarea   class="form-control"rows="4"  id="content'+contentnum+'" /></textarea>'+
		 			'</div>';
		 $('#addContent').before(content);
		 contentnum++;
		 if(contentnum===3){
			 $('#addContent').css('display','none');
		 }
	})
	
	 $('#saveActivityData').click(function() {
		 var activityContent=[];
		
		
		var title = $("#groups .form-group input[name='title']").val(); 
		if(title===""){
			inputPopover( $("#groups .form-group input[name='title']"),"请输入主题", 2); 
			return false;
		}
		var content = $("#groups .form-group textarea[name='content']").val(); 
		if(content===""){
			inputPopover( $("#groups .form-group textarea[name='content']"),"请输入内容", 2); 
			return false;
		}
		var i=0;
		$("textarea[id^='content']").each(function(){
			if($(this).val()!==""){
				activityContent[i]=$(this).val();
				i++;
			}
		})
		
		var files=[];
		var picname=[];
		var fileNum=0;
		//if((imgk-D)===dataNum){
	  	for(var i=0;i<imgk;i++){
	  		var unEqe=0;
				for(var j=0;j<D;j++){
					if(i!=deletePic[j]){
						unEqe++;
					}
				}
				if(unEqe===D){
					//var num=i+1;
					files[fileNum]=datas[i];
	  				var picpath=$('#file'+i+'').val();
	  				if(picpath==""){
	  					picpath=$('#filename').text();
	  					picname[fileNum]=picpath.substring(picpath.lastIndexOf('\/')+1);
	  				}else{
	  					picname[fileNum]=picpath.substring(picpath.lastIndexOf('\\')+1);
	  				}
	  				
	  				
	  				
	  				fileNum++;
				}
			}
//			for(var i=0;i<files.length;i++){
//					alert(files[i])
//					alert(picname[i]);
//			}
	  	 var activityData;
//	  	if(picname.length!==0){
//	  		alert("y有图片");
	  		activityData={
	    		"activityName":$("select#activityLists option:checked").val(),
	    		"activityContent":activityContent,
	    		"activityTitle" : title,
	    		"imagename" :picname,
				"imagedata" : files
	  		};
//	  		
//	  	}else{
//	  		alert("wu图片");
//	  		activityData={
//	    		"activityName":$("select#activityLists option:checked").val(),
//	    		"activityContent":activityContent,
//	    		"activityTitle" : title,
//	    		"imagename" :null,
//				"imagedata" : null
//	  		};
//	  	}
	  	var activityDatas=$.param(activityData,true);
	   
	   // alert(activityDatas);
	  

			$.ajax( {

				url : "saveActivityStart.action",//"./test/res.jsp",//
				data : activityDatas,
				type : "post",
				// 期待的返回值类型
				dataType: "json",
				success : function (data, status){
					if(data.message==="success") {
        					$(".uploadimg").css('display','none');
        					$("#groups .form-group input[name='title']").val("");
        					$("#groups .form-group textarea[id^='content']").val("");
        					msg = "保存成功";
        					var activity=$("select#activityLists option:selected").val();
        					//alert(activity)
        					imgk=0;
        					dataNum=0;
        					D=0;
        					deletePic= [];
        					datas=[];
        					contentnum=1;
        					dataInit(activity);
        				}else{
        					msg = "反馈信息提交失败，请稍后重试";
        				}
        				alert(msg);
        	 if(typeof(data.error) !== 'undefined'){
                if(data.error !== ''){
                    alert(data.error);
                }else{
                    alert(data.msg);
                }
            }
        },
        error: function(data, status, e){
            alert(e);
        }
	});
	  	
//		}else{
//			alert('图片名和图片数据个数不一致');
//		}		
	 });
})

function canAdd(){
	if((imgk-D)>=3){
		
		$('[id="file'+(imgk)+'"]').attr('disabled','disabled');
		$('[id="file'+(imgk)+'"]').attr('title','最多上传3张');
	}else{
		$('[id="file'+(imgk)+'"]').attr('disabled',false);
		$('[id="file'+(imgk)+'"]').attr('title','未选择文件');
	}
}

function PreviewImage(obj) {
	
	$('.img>img').attr('src','./img/feedback/AddImage_Up.png'); 
	var allowExtention = ".jpg,.bmp,.gif,.png";
	//,允许上传文件的后缀名
	var extention = obj.value.substring(obj.value.lastIndexOf(".") + 1).toLowerCase();
	var browserVersion = window.navigator.userAgent.toUpperCase();
	if (allowExtention.indexOf(extention) > -1) {
		if (browserVersion.indexOf("MSIE") > -1) {
		
		} else if (obj.files) {
			//兼容chrome、火狐等，HTML5获取路径
			if ( typeof FileReader !== "undefined") {
//				var reader = new FileReader();
//				//alert(k+';'+D)
//				reader.onload = function(e) {
//						//document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
//						var div=document.createElement('div');     
//						div.setAttribute('id','divImg'+imgk);  
//						document.getElementById('divNewPreview').appendChild(div);
//						var imgclose=document.createElement('img');     
//						imgclose.setAttribute('class','closeimg'); 
//						imgclose.setAttribute('id','closeimg'+imgk); 
//						imgclose.setAttribute('src','./img/feedback/delete.png');  
//						imgclose.setAttribute('title','删除图片');
//						var img=document.createElement('img');     
//		   	    		img.setAttribute('src',e.target.result);  
//		   	    		img.setAttribute('class','uploadimg');    
//		   	    		img.setAttribute('id','img'+imgk);  
//		   	    		div.appendChild(imgclose);
//		   	    		div.appendChild(img);
//		   	    		
//		   	    		
//					
//					
//		   	    		var dataURL = e.target.result;
//		   	    		var canvas = document.createElement("canvas"); 
//		   	    	
//		   	    		var img = new Image(); 
//		   	    		img.onload = function() { 
//		   	    			var square = 300; 
//		   	    			
//		   	    			
//		   	    			var imageWidth;
//		   	    			var imageHeight; 
//		   	    			var offsetX = 0; 
//		   	    			var offsetY = 0; 
//		   	    			if (this.width > this.height) { 
//		   	    				imageWidth = Math.round(square * this.width / this.height); 
//		   	    				imageHeight = square; 
//		   	    				offsetX = - Math.round((imageWidth - square) / 2); 
//		   	    			} else {
//		   	    				imageHeight = Math.round(square * this.height / this.width);
//		   	    				imageWidth = square;  
//		   	    				offsetY = - Math.round((imageHeight - square) / 2);  
//		   	    				}	
//		   	    			canvas.width = imageWidth;
//		   	    			canvas.height = imageHeight;
//		   	    			var context = canvas.getContext('2d');
//		   	    			context.clearRect(0, 0, imageWidth, imageHeight); 
//		   	    			context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
//		   	    			//context.drawImage(this,0,0);
//		   	    			var dataurl = canvas.toDataURL("image/png");
//		   	    			//console.log(dataurl);
//		   	    			var imagedata= encodeURIComponent(dataurl);
//		   	    			//console.log(imagedata);
//		   	    			datas[dataNum++]=imagedata;
//		   	    			
//		   	    			//console.log(datas[imgk-1]);
//		   	    			
//		   	    			};
//		   	    		img.src = dataURL;
//		   	    		
//		   	    		$('#file'+imgk).css('display','none');
//		   	    		imgk++;
//		   	    		var input='<input type="file" class="form-control"  id="file'+(imgk)+'" name="file" capture="camera" accept="image/gif, image/jpeg, image/png" onchange="PreviewImage(this)"/>';
//		   	    		$('.file').append(input);
//		   	    		canAdd();
//		   	    	}
//				reader.readAsDataURL(obj.files[0]);
				var reader = new FileReader();
				//alert(k+';'+D)
				reader.onload = function(e) {
						//document.getElementById(imgPreviewId).setAttribute("src", e.target.result);
						var div=document.createElement('div');     
						div.setAttribute('id','divImg'+imgk);  
						document.getElementById('divNewPreview').appendChild(div);
						var imgclose=document.createElement('img');     
						imgclose.setAttribute('class','closeimg'); 
						imgclose.setAttribute('id','closeimg'+imgk); 
						imgclose.setAttribute('src','./img/feedback/delete.png');  
						imgclose.setAttribute('title','删除图片');
						var img=document.createElement('img');     
		   	    		img.setAttribute('src',e.target.result);  
		   	    		img.setAttribute('class','uploadimg');    
		   	    		img.setAttribute('id','img'+imgk);  
		   	    		div.appendChild(imgclose);
		   	    		div.appendChild(img);
		   	    		
		   	    		
					
					
		   	    		var dataURL = e.target.result;
		   	    		var canvas = document.createElement("canvas"); 
		   	    	
		   	    		var img = new Image(); 
		   	    		img.onload = function() { 
		   	    			var square = 100; 
		   	    			 
		   	    			var imageWidth;
		   	    			var imageHeight; 
		   	    			var offsetX = 0; 
		   	    			var offsetY = 0; 
		   	    			if (this.width > this.height) { 
		   	    				imageWidth = Math.round(square * this.width / this.height); 
		   	    				imageHeight = square; 
		   	    				//offsetX = - Math.round((imageWidth - square) / 2); 
		   	    			} else {
		   	    				imageHeight = Math.round(square * this.height / this.width);
		   	    				imageWidth = square;  
		   	    				//offsetY = - Math.round((imageHeight - square) / 2);  
		   	    				}	
		   	    			canvas.width = imageWidth;
		   	    			canvas.height = imageHeight;
		   	    			var context = canvas.getContext('2d');
		   	    			context.clearRect(0, 0, imageWidth, imageHeight); 
		   	    			
		   	    			context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
//							var div=document.createElement('div');     
//							div.setAttribute('id','divImg'+imgk);  
//							document.getElementById('divNewPreview').appendChild(div);
//							var imgclose=document.createElement('img');     
//							imgclose.setAttribute('class','closeimg'); 
//							imgclose.setAttribute('id','closeimg'+imgk); 
//							imgclose.setAttribute('src','./img/feedback/delete.png');  
//							imgclose.setAttribute('title','删除图片');
//							
//			   	    		div.appendChild(imgclose);
//			   	    		div.appendChild(canvas);
		   	    			//context.drawImage(this,0,0);
		   	    			var dataurl = canvas.toDataURL("image/png");
		   	    			//console.log(dataurl);
		   	    			var imagedata= encodeURIComponent(dataurl);
		   	    			//console.log(imagedata);
		   	    			datas[imgk-1]=imagedata;
		   	    			
		   	    			//console.log(datas[imgk-1]);
		   	    			
		   	    			};
		   	    		img.src = dataURL;
		   	    		
		   	    		$('#file'+imgk).css('display','none');
		   	    		imgk++;
		   	    		var input='<input type="file" id="file'+(imgk)+'" name="file" capture="camera" accept="image/gif, image/jpeg, image/png" onchange="PreviewImage(this)"/>';
		   	    		$('.file').append(input);
		   	    		canAdd();
		   	    	}
				reader.readAsDataURL(obj.files[0]);

			} else if (browserVersion.indexOf("SAFARI") > -1) {
				alert("暂时不支持Safari浏览器!");
			}
		} 
	} else {
		alert("仅支持" + allowSuffix + "为后缀名的文件!");
		obj.value = "";
		//清空选中文件
		if (browserVersion.indexOf("MSIE") > -1) {
			obj.select();
			document.selection.clear();
		}
		obj.outerHTML = obj.outerHTML;
	}
	
}
function dataInit(activityName){
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getActivityStart.action", //要访问的后台地址 
		data: {"activityName":activityName}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message==="success"){
					$('#title').val("");
					$("#divNewPreview").html("");
					$("#contentDiv").html("");
					 var content0='<div class="form-group" style="margin-right:0px">'+
		 								'<p class="form-control-static">内容：</p>'+
						 			'</div>'+
						 			'<div class="form-group inputfix" style="margin-left:5px;" >'	+
						 				'<textarea   class="form-control"rows="4"  id="content0" /></textarea>'+
						 			'</div>'+
						 			'<button type="button" class="btn btn-primary " id="addContent" >添加内容</button><br>';
					 $('#contentDiv').append(content0);
					 $("#imageDiv").html("");
					 var image0='<div class="form-group">'+
									   '<p class="form-control-static">图片：</p>'+
									'</div>'+
									'<div class="form-group inputfix file" >'	+
									    '<input type="file" class="form-control" name="file" id="file0"  accept="image/gif, image/jpeg, image/png" capture="camera" onchange="PreviewImage(this)"/>'+
									'</div>';
					 $('#imageDiv').append(image0);
					if(msg.context==="fail"){
						
					}else{
						var activity=msg.context.activitylist;
						$('#title').val(activity.activityTitle);
						$.each(activity.activityContent,function(i,item){
							if(i==0){
								$('#content0').val(item);
							}else{
								 var content='<br><div class="form-group" style="margin-right:0px">'+
									 				'<p class="form-control-static">内容：</p>'+
									 			'</div>'+
									 			'<div class="form-group inputfix" style="margin-left:5px;">'	+
									 				'<textarea   class="form-control"rows="4"  id="content'+contentnum+'" /></textarea>'+
									 			'</div>';
								 $('#addContent').before(content);
								 $('#content'+contentnum+'').val(item);
								 contentnum++;
								 if(contentnum===3){
									 $('#addContent').css('display','none');
								 }
							}
						});
						$.each(activity.imagename,function(i,item){
							var src='/uploadImg/'+item+'';
							var div=document.createElement('div');     
							div.setAttribute('id','divImg'+imgk);  
							document.getElementById('divNewPreview').appendChild(div);
							var imgclose=document.createElement('img');     
							imgclose.setAttribute('class','closeimg'); 
							imgclose.setAttribute('id','closeimg'+imgk); 
							imgclose.setAttribute('src','./img/feedback/delete.png');  
							imgclose.setAttribute('title','删除图片');
							var img=document.createElement('img');     
			   	    		img.setAttribute('src',src);  
			   	    		img.setAttribute('class','uploadimg');    
			   	    		img.setAttribute('id','img'+imgk);  
			   	    		div.appendChild(imgclose);
			   	    		div.appendChild(img);
			   	    		
			   	    		$('#file'+imgk).css('display','none');
			   	    		imgk++;
			   	    		var input='<input type="file" class="form-control"  id="file'+(imgk)+'" name="file" capture="camera" accept="image/gif, image/jpeg, image/png" onchange="PreviewImage(this)"/>';
			   	    		$('.file').append(input);
			   	    		canAdd();
			   	    		$('#filename').text(src)
			   	    		
			   	    		var canvas = document.createElement("canvas"); 
				   	    	var imgs = new Image(); 
				   	    	imgs.src="";
			   	    		imgs.onload = function() { 
			   	    			var square = 300; 
			   	    			
			   	    			
			   	    			var imageWidth;
			   	    			var imageHeight; 
			   	    			var offsetX = 0; 
			   	    			var offsetY = 0; 
			   	    			if (this.width > this.height) { 
			   	    				imageWidth = Math.round(square * this.width / this.height); 
			   	    				imageHeight = square; 
			   	    				//offsetX = - Math.round((imageWidth - square) / 2); 
			   	    			} else {
			   	    				imageHeight = Math.round(square * this.height / this.width);
			   	    				imageWidth = square;  
			   	    				//offsetY = - Math.round((imageHeight - square) / 2);  
			   	    				}	
			   	    			canvas.width = imageWidth;
			   	    			canvas.height = imageHeight;
			   	    			var context = canvas.getContext('2d');
			   	    			context.clearRect(0, 0, imageWidth, imageHeight); 
			   	    			context.drawImage(this, offsetX, offsetY, imageWidth, imageHeight);
			   	    			//context.drawImage(this,0,0);
			   	    			var dataurl = canvas.toDataURL("image/png");
			   	    			//console.log(dataurl);
			   	    			var imagedata= encodeURIComponent(dataurl);
			   	    			//console.log(imagedata);
			   	    			datas[dataNum++]=imagedata;
			   	    			
			   	    			};
			   	    		imgs.src = src;
						});
						
						
					}
		   	    }
		}, 
		error: function() { 
			alert("加载数据失败"); 
		} 
	}); 
}