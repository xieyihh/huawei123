<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<style type="text/css">
.star_bg {
    width: 120px; height: 20px;
    background: url(img/star.png) repeat-x;
    position: relative;
    overflow: hidden;
}
.star {
    height: 100%; width: 24px;
    line-height: 6em;
    position: absolute;
    z-index: 3;
}
.star_1 { left: 0; }
.star_2 { left: 24px; }
.star_3 { left: 48px; }
.star_4 { left: 72px; }
.star_5 { left: 96px; }

.starhover1 {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
    width: 24px;
}
.starhover2 {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
    width: 48px;
}
.starhover3 {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
    width: 72px;
}
.starhover4 {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
    width: 96px;
}
.starhover5 {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
    width: 120px;
}
.scorechecked1 {    
     background: url(img/star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
    width: 24px;
}
.scorechecked2 {    
    background: url(img/star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
    width: 48px;
}
.scorechecked3 {    
     background: url(img/star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
    width: 72px;
}
.scorechecked4 {    
    background: url(img/star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
    width: 96px;
}
.scorechecked5 {    
    background: url(img/star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
    width: 120px;
}
.starbghover{  background-image: none; }
/*.star:hover {    
    background: url(img/star.png) repeat-x 0 -20px!important;
    left: 0; z-index: 2;
}

.star_1:hover { width: 24px; }
.star_2:hover { width: 48px; }
.star_3:hover { width: 72px; }
.star_4:hover { width: 96px; }
.star_5:hover { width: 120px; }

*/

 幕后的英雄，单选按钮 
/*
.score:checked + .star {    
    background: url(star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
}
.score_1:checked ~ .star_1 { width: 24px; }
.score_2:checked ~ .star_2 { width: 48px; }
.score_3:checked ~ .star_3 { width: 72px; }
.score_4:checked ~ .star_4 { width: 96px; }
.score_5:checked ~ .star_5 { width: 120px; }

.star_bg:hover .star {  background-image: none; }
*/
/* for IE6-IE8 JS 交互 */
.star_checked {    
    background: url(star.png) repeat-x 0 -20px;
    left: 0; z-index: 1;
}
</style>
 <script src="bootstrap/jquery.min.js"></script>
<script type="text/javascript">

$(function () {
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
		alert(num)
		
		
	});
	
  });


</script>
</head>
<body>
<body>
<div id="starBg1" class="star_bg">                    	
   <a href="#" id="stars11" class="star star_1" title="1"></a>
	<a href="#" id="stars21" class="star star_2" title="2"></a>
   <a href="#" id="stars31"class="star star_3" title="3"></a>
   <a href="#"id="stars41" class="star star_4" title="4"></a>
  	<a href="#" id="stars51"class="star star_5" title="5"></a>
</div>
<div id="starBg2" class="star_bg">                    	
   <a href="#" id="stars12" class="star star_1" title="1"></a>
	<a href="#" id="stars22" class="star star_2" title="2"></a>
   <a href="#" id="stars32"class="star star_3" title="3"></a>
   <a href="#"id="stars42" class="star star_4" title="4"></a>
  	<a href="#" id="stars52"class="star star_5" title="5"></a>
</div>
</body>
</html>