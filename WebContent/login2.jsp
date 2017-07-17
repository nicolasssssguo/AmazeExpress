<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
<meta charset="UTF-8">
<title>登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png" href="<%=basePath %>resources/i/favicon.png">
<link rel="stylesheet" href="<%=basePath %>resources/css/amazeui.min.css" />
<style>
.header {
	text-align: center;
}

.header h1 {
	font-size: 200%;
	color: #333;
	margin-top: 30px;
}

.header p {
	font-size: 14px;
}
</style>
</head>
<body>
	<div class="header">
		<div class="am-g">
			<h1>Web ide</h1>
			<p>
				Integrated Development Environment<br />代码编辑，代码生成，界面设计，调试，编译
			</p>
		</div>
		<hr />
	</div>
	<div class="am-g">
		<div class="am-u-lg-6 am-u-md-8 am-u-sm-centered">
			<h3>登录</h3>
			<hr>
			<form method="post" class="am-form">
				<label for="username">用户名:</label> <input type="text" name="username"
					id="email" value=""> <br> <label for="password">密码:</label>
				<input type="password" name="password" id="password" value=""> <br>
				<div class="am-cf">
					<input type="submit" name="" value="登 录"
						class="am-btn am-btn-primary am-btn-sm am-fl"> <input
						type="submit" name="" value="忘记密码 ^_^? "
						class="am-btn am-btn-default am-btn-sm am-fr">
				</div>
			</form>
			<hr>
			<p>© 2017 nicolasguo, Inc. </p>
		</div>
	</div>
</body>
</html>