var books=["bookinfo.jsp","importbook.jsp","bookRanking.jsp"];
var physicals=['physicalInfo.jsp','importPhysicalInfo.jsp','importHospitalInfo.jsp','physicalnoUserInfo.jsp','hospitalInfo.jsp','hospitalReexamInfo.jsp','physicalReview.jsp','physicalInit.jsp'];
var activitys=['activityLaunch.jsp','activityInit.jsp','activityStatus.jsp'];//activityStatus
var feeaback=['feedbackhome.action','showFeedBack.jsp'];
var superAdmins=['authority.jsp','authorityPage.jsp','bookdictionary.action'];
var bookch=["书籍信息查询","导入书籍","借阅排行榜"];
var physicalch=['体检信息','体检安排导入','体检信息导入','新加员工信息','医院体检信息','医院复查信息','复查信息','初始化信息'];
var activitych =['活动发布','活动初始化','活动状态'];
var feeabackch=['图书反馈','信息反馈'];
var superAdminch=['权限管理','权限分配','数据字典'];
var bigname=["图书后台管理","体检后台管理","活动管理","反馈信息管理","超级管理员"];
var data=[books,physicals,activitys,feeaback,superAdmins];
var chName=[bookch,physicalch,activitych,feeabackch,superAdminch];
	$(function() { 
		
		getData();
		

	
	});
	
	function getData(){

		$.ajax({ 
			type: "get", //使用get方法访问后台 
			dataType: "json", //返回json格式的数据 
			url: "getuserrolepopedomBylogin.action", //要访问的后台地址 
			data: { }, //要发送的数据 
		//	async:false,
			success: function(msg) {//msg为返回的数据，在这里做数据绑定 
				if(msg.message=="success"){
					var bodyContent="";
					$.each(msg.context.rolelist, function(i, item) {
						var arr=item.popedomcode.split(";"); 
						for(var i=0;i<arr.length;i++){
							if(arr[i]!=""){
								bodyContent+='<dl>'+
												'<dt onclick="javascript:show(this);">'+bigname[i]+'</dt>';
								var content=arr[i].split(',');
								for(var j=0;j<content.length;j++){
									bodyContent+='<dd><a href="'+data[i][content[j]-1]+'" target="mainFrame">'+chName[i][content[j]-1]+'</a></dd>';
								}
								bodyContent+='</dl>';
							}
						}
						$("#leftBar").append(bodyContent);
					 });
					$('dl:first dt').css('class', 'dtjian');
					$('dl:first dd').css('display', 'block');
				}
				 
//				 
				
			},
			error: function() { 
				
				alert("加载数据失败2"); 
			} 
					
		});
				
	}
	 function showdd(mydl){//显示指定DL下的DD
			
			var alldl=document.getElementsByTagName("dl");
			
			var thedt=alldl[mydl].getElementsByTagName("dt");
			thedt[0].className="dtjian";
			var thedd=alldl[mydl].getElementsByTagName("dd");
			for (var ii=0;ii<thedd.length;ii++){
				
				thedd[ii].style.display="block";
				}
			}
	 
	 function hidd(){//隐藏所有DD
			var alldd = document.getElementsByTagName("dd");
			for (i=0;i<alldd.length;i++){
		    alldd[i].style.display="none";}
			}

			
		function show(strtype){
			if (strtype.className=="dtjian"){ strtype.className="";hidd();}
			
			else{
			hidd();//隐藏所有DD
			var alldt = document.getElementsByTagName("dt");
			for (i=0;i<alldt.length;i++){
		    alldt[i].className="";}
		    strtype.className="dtjian";
			
			for (i=0;i<alldt.length;i++){if (strtype==alldt[i]){showdd(i);}}

			}
		    }
	