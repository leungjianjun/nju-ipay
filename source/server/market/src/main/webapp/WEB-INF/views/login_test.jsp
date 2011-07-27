<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<script src="/IpayServerMarket/resources/js/jquery-1.6.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$.ajax({
	    url: "/IpayServerMarket/j_spring_security_check",
	    type: "POST",
	    data: {"j_username":"ljj","j_password":"123456"},
	    contentType:"application/json",
	    dataType:"json",
	    success: function(result) {
	        alert(result)
	    }
	});
})

</script>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!
</h1>
<form method="post" action="j_spring_security_check">
<p>
					<label>帐号：</label>
					<input type="text" name="j_username" size="20" class="login_input" />
				</p>
				<p>
					<label>密码：</label>
					<input type="password" name="j_password" size="20" class="login_input" />

				</p>
				<p>
					<label style="width:130px">两周内不用再登录：</label>
					<input type="checkbox" name="_spring_security_remember_me"/>
				</p>
				<div class="login_bar">
					<input class="sub" type="submit" value="登录 " />

				</div>
</form>

</body>
</html>
