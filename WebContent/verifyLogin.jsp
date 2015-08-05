<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!-- 验证用户是否登录，如果未登录，调用缓存中的账号密码登录 -->
<c:if test="${sessionScope.user == null }">
	<script type="text/javascript" src="js/lib/jquery-1.11.1.min.js"></script>
	<script type="text/javascript">
		var account = localStorage.account;
		var password = localStorage.password;
		if(account == undefined) {
			location.href = "login.jsp";
		}
		$.ajax({
			url : "userAction!loginByAjax.action",
			type : "POST",
			async : false,
			data : {
				account : account,
				password : password
			},
			dataType : "json",
			success : function(data) {
				if(!data.success) {
					location.href = "login.jsp";
				} else {
					//在页面上方显示用户姓名
					$(function() {
						$(".title_div .right_title_div .user_name").html(data.user.name);
					});
				}
			}
		});
	</script>
</c:if>