/**
 * imgk 图片个数
 * 
 * D 删除的个数
* pictute 文件二进制流
 */
var imgk=0;
var D=0;

var deletePic= [];
var datas=[];
/**
 * 填入已有信息
 */
$(function(){
	
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getuserinfo.action", //要访问的后台地址 
		data: {}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message==="success"){
					if(msg.context==="noUser"){
						$("#picture").css('display','none');
					}else{
						var form=msg.context.userinfo;
						$("input[name='name']").val(form.username);
						$("input[name='number']").val(form.usernumber);
						$("#picture").css('display','table');
					}
				}
		}, 
		error: function() { 
			alert("保存数据失败"); 
		} 
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
	$('.file').click(function(){
		
		$('.img>img').attr('src','./img/feedback/AddImage_Down.png'); 
	})
	 $('#submitButton').click(function() {
		 
		 var inputList = $("#feedback_div .input-group input[name='name'],#feedback_div .input-group input[name='number'], #feedback_div .input-group textarea");
		if(!validateInputList(inputList)) {
			return false;
		}
		var numberObj=$("#feedback_div .input-group input[name='number']");
		if(!numberObj.val().match(/\d{8}/g)){
			inputPopover(numberObj, "工号必须为8位数字", 2);
			return false;
			
		}
		var files=[];
		var picname=[];
		var fileNum=0;
	  	for(var i=0;i<imgk;i++){
	  		var unEqe=0;
				for(var j=0;j<D;j++){
					if(i!==deletePic[j]){
						unEqe++;
					}
				}
				if(unEqe===D){
					//var num=i+1;
					files[fileNum]=datas[i];
	  				var picpath=$('#file'+i+'').val();
	  				picname[fileNum]=picpath.substring(picpath.lastIndexOf('\\')+1);
	  				fileNum++;
				}
			}
//			for(var i=0;i<files.length;i++){
//					alert(files[i])
//					alert(picname[i]);
//			}
	     // alert(picname.join('%;-%').toString());
	  	if(files.length!==0){

	        			$.ajax( {

	        				url : "fileUploadAction.action",//"./test/res.jsp",//
	        				data : {
	        					"imagename" :picname.join('%;-%'),//picname[0],
	        					"imagedata" : files.join('%;-%'),//files[0],//
	        					"feedbackname" : $("#feedback_div .input-group input[name='name']").val(),
	        		    	  	"usernumber" : $("#feedback_div .input-group input[name='number']").val(),
	        		    	  	"feedbackcontent" : $("#feedback_div .input-group textarea[name='content']").val()
		        			},
	        				type : "post",
	        				// 期待的返回值类型
	        				dataType: "json",
	        				success : function (data, status){
			        	
			        				if(data.message==="success") {
			        					$(".uploadimg").css('display','none');
			    	  					inputList.val("");
			        					msg = "反馈信息提交成功，等待管理员处理";
			        				}else{
			        					msg = "反馈信息提交失败，请稍后重试";
			        				}
			        				showSystemMsg(msg, 2);
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
	  	}else{
	  		$.ajax({ 
	  			type: "get", //使用get方法访问后台 
	  			dataType: "json", //返回json格式的数据 
	  			url: 'fileUploadAction.action', //要访问的后台地址 
	  			data : {
	  				"feedbackname" : $("#feedback_div .input-group input[name='name']").val(),
		    	  	"usernumber" : $("#feedback_div .input-group input[name='number']").val(),
		    	  	"feedbackcontent" : $("#feedback_div .input-group textarea[name='content']").val()
	  			},
	  			success: function(data) {//msg为返回的数据，在这里做数据绑定 
	  				
	  				if(data.message==="success") {
	  					inputList.val("");
    					msg = "反馈信息提交成功，等待管理员处理";
    					
    				}else{
    					msg = "反馈信息提交失败，请稍后重试";
    				}
    				
    				showSystemMsg(msg, 2);
	  			}, 
	  			error: function() { 
	  				alert("保存数据失败"); 
	  			} 
	  		}); 
	  		
	  	}	
			
	 });
})

function canAdd(){
	if((imgk-D)>=5){
		
		$('[id="file'+(imgk)+'"]').attr('disabled','disabled');
		$('[id="file'+(imgk)+'"]').attr('title','最多上传5张');
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
			/*if (browserVersion.indexOf("MSIE 6.0") > -1) {//ie6
				document.getElementById(imgPreviewId).setAttribute("src", obj.value);
			} else {//ie[7-8]、ie9
				obj.select();
				var newPreview = document.getElementById(divPreviewId + "New");
				if (newPreview == null) {
					newPreview = document.createElement("div");
					newPreview.setAttribute("id", divPreviewId + "New");
					newPreview.style.width = 160;
					newPreview.style.height = 170;
					newPreview.style.border = "solid 1px #d2e2e2";
				}
				newPreview.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')";
				var tempDivPreview = document.getElementById(divPreviewId);
				tempDivPreview.parentNode.insertBefore(newPreview, tempDivPreview);
				tempDivPreview.style.display = "none";
			}*/
		} else if (browserVersion.indexOf("FIREFOX") > -1) {//firefox
			var firefoxVersion = parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);
			if (firefoxVersion < 7) {//firefox7以下版本
				//document.getElementById(imgPreviewId).setAttribute("src", obj.files[0].getAsDataURL());
				var img=document.createElement('img');     
   	    		img.setAttribute('src',obj.files[0].getAsDataURL());  
   	    		img.setAttribute('width',width);    
   	    		img.setAttribute('height',height); 
   	    		
   	    		img.setAttribute('id','img'+k);    	k++;
   	    		document.getElementById('divNewPreview').appendChild(img);
			} else {//firefox7.0+
				//document.getElementById(imgPreviewId).setAttribute("src", window.URL.createObjectURL(obj.files[0]));
				var img=document.createElement('img');     
   	    		img.setAttribute('src',window.URL.createObjectURL(obj.files[0]));  
   	    		img.setAttribute('width',width);    
   	    		img.setAttribute('height',height); 
   	    		
   	    		img.setAttribute('id','img'+k);  	k++;  
   	    		document.getElementById('divNewPreview').appendChild(img);
			}
		} else if (obj.files) {
			//兼容chrome、火狐等，HTML5获取路径
			if ( typeof FileReader !== "undefined") {
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
		   	    			var square = 300; 
		   	    			 
		   	    			var imageWidth;
		   	    			var imageHeight; 
		   	    			var offsetX = 0; 
		   	    			var offsetY = 0; 
		   	    			if (this.width > this.height) { 
		   	    				imageWidth = Math.round(square * this.width / this.height); 
		   	    				imageHeight = square; 
		   	    				offsetX = - Math.round((imageWidth - square) / 2); 
		   	    			} else {
		   	    				imageHeight = Math.round(square * this.height / this.width);
		   	    				imageWidth = square;  
		   	    				offsetY = - Math.round((imageHeight - square) / 2);  
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
		} else {
			//document.getElementById(divPreviewId).setAttribute("src", obj.value);
			var img=document.createElement('img');     
	    		img.setAttribute('src',obj.value);  
	    		img.setAttribute('width',height);    
	    		img.setAttribute('height',height);  
	    		
	    		img.setAttribute('id','img'+k);	k++;    
	    		document.getElementById('divNewPreview').appendChild(img);
           
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
function dataURItoBlob(dataURI) {
    // convert base64 to raw binary data held in a string 
    var byteString 
        ,mimestring 

    if(dataURI.split(',')[0].indexOf('base64') !== -1 ) {
        byteString = atob(dataURI.split(',')[1])
    } else {
        byteString = decodeURI(dataURI.split(',')[1])
    }

    mimestring = dataURI.split(',')[0].split(':')[1].split(';')[0]

    var content = new Array();
    for (var i = 0; i < byteString.length; i++) {
        content[i] = byteString.charCodeAt(i)
    }

    return new Blob([new Uint8Array(content)], {type: mimestring});
}