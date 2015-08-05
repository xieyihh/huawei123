
var colors=['#da532c','#2d89ef','#00a300','#b91d47','rgb(101, 130, 66)','#2b5797','rgb(143, 65, 8)','rgb(146, 42, 123)','rgb(19, 175, 144)','rgb(9, 87, 142)'];
$(function() {
	var activityStatus = 2;//报名状态
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getActivityDictionary.action", //要访问的后台地址 
		data: {"activityStatus":activityStatus}, //要发送的数据 
		//data: {}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			if(msg.message==="success"){
				$('.main_content').html("");
				$.each(msg.context.activitydictionary, function(i, item) {
					var content='<div class="divblock" id="img'+i+'">'+
									//'<a href="activitySignup.jsp?activityName='+(i+1)+'">'+item+'</a>'+
									'<a href="activityHome.jsp?activityName='+item.activityid+'">'+item.activityName+'</a>'+
								'</div>';
					
					$('.main_content').append(content);
					$('#img'+i+'').css('background',colors[i]);
				});
			}
		}, 
		error: function() { 
			alert("保存数据失败"); 
		} 
	}); 
	
	
});
