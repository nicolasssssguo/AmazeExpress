<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
<meta charset="UTF-8">
<title>登录</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png"
	href="<%=basePath%>resources/i/favicon.png">
<link rel="stylesheet" href="<%=basePath%>resources/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/animate.css" />
</head>
<body>
	<div class="am-container">
		<div class="am-g">
			<div class="am-u-md-7 am-u-md-centered">
				<div class="am-panel am-panel-primary panel-login">
					<div class="am-panel-hd">登录</div>
					<!-- end of panel-heading -->
					<div class="am-panel-bd">
						<form class="am-form" id="login-form"
							action="${pageContext.request.contextPath}/user/login.do"
							method="POST">
							<div class="am-text-center">
								<img src="<%=basePath%>resources/i/logo.png" width="100"
									height="100" />
							</div>
							<hr>
							<!-- username field -->
							<div class="am-form-group">
								<label for="username">用户名:</label> <input type="text"
									name="username" minlength="3" placeholder="输入用户名" class=""
									id="username" value="admin" autofocus required />
							</div>
							<!-- password field -->
							<div class="am-form-group">
								<label for="password">密码:</label> <input type="password"
									name="password" placeholder="输入密码" class="" value="admin" required />
							</div>
							<button type="submit"
								class="am-btn am-btn-lg am-btn-primary am-btn-block">登录</button>
						</form>
					</div>
					<!-- end of panel-body -->
				</div>
				<!-- end of panel -->
			</div>
		</div>
	</div>
	<script src="<%=basePath%>resources/js/jquery-1.12.4.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script>
		var $url = '${url}';
		$url = $url ? $url
				: '${pageContext.request.contextPath}/admin';
		$(function() {
			var $form = $('#login-form');
			var $group = $form.find('.am-form-group').last();
			var $submit = $form.find('.am-btn-primary');
			var animating = false;
		    var animation = 'am-animation-shake';
			$form.validator({
				// 是否使用 H5 原生表单验证，不支持浏览器会自动退化到 JS 验证
				H5validation : false,
				submit : function() {
					var formValidity = this.isFormValid();
					if(formValidity) {
						$.AMUI.progress.start();
						$.ajax({
							type : 'POST',
							url : $form.attr('action'),
							data : $form.serialize(),
							success : function(result) {
								$.AMUI.progress.done();
								if (result.code == 200) {//验证成功
									window.location.href = $url;
								} else if (result.code == 400) {//验证失败
								      if (!animating) {
								          animating = true;
								          var dfd = new $.Deferred();
								          if ($.AMUI.support.animation) {
								        	  $form.addClass(animation).one($.AMUI.support.animation.end, function() {
								                  $form.removeClass(animation);
								                  dfd.resolve();
								                });
								          }
								          
								          $.when.apply(null).done(function() {
								              animating = false;
								            });
								      }
								}
							}
						});
					}
					return false;
				}
			});
		});
	</script>
</body>
</html>