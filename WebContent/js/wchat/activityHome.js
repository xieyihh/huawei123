$(function(){
	/**
	 * 根据活动名称获取活动发布的详情
	 */
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getActivityStart.action", //要访问的后台地址 
		data: {"activityName":activityName}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message==="success"){
					
					if(msg.context==="fail"){
						
					}else{
						var activity=msg.context.activitylist;
						$('#title h2').text(activity.activityTitle);
						$.each(activity.activityContent,function(i,item){
							if(i==0){
								$('#content p').text(item);
							}else{
								 var content='<p class="p'+i+'">'+item+'</p>';;
								 $('#content').append(content);
								 
							}
						});
						$.each(activity.imagename,function(i,item){
							var src='/uploadImg/'+item+'';
							if(i===0){
								$('#image img').attr('src',src); 
							}
			   	    		
						});
						
						
					}
		   	    }
		}, 
		error: function() { 
			alert("加载数据失败"); 
		} 
	}); 
	var content='<a href="activitySignup.jsp?activityName='+activityName+'">报名</a>';
	$('#status').append(content);
	
	/**
	 * 根据用户名判断用户是否已经报名
	 * 返回用户报名状态 1表示已报名 0表示为报名
	 */
	$.ajax({ 
		type: "get", //使用get方法访问后台 
		dataType: "json", //返回json格式的数据 
		url: "getUserActivityStatus.action", //要访问的后台地址 
		data: {"activityName":activityName}, //要发送的数据 
		success: function(msg) {//msg为返回的数据，在这里做数据绑定 
			if(msg.message==="Pleaselogin"){
				alert('请登录')
			}else if(msg.message==="success"){
				$('#status').html('');
					if(msg.context==="1"){
						var content='<p>已报名</p>';
						
					}else if(msg.context==="0"){
						var content='<a href="activitySignup.jsp?activityName='+activityName+'">报名</a>';
						
					}
					$('#status').append(content);
		   	    }
		}, 
		error: function() { 
			alert("加载数据失败"); 
		} 
	}); 
});