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
<link rel="stylesheet" href="<%=basePath%>resources/css/admin.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/amazeui.datatables.min.css" />
</head>
<body>
	<header class="am-topbar am-topbar-inverse admin-header">
	   <div class="am-topbar-brand">
	       <strong>快递管理</strong>
	   </div>
	   
	   <div class="am-collapse am-topbar-collapse">
	       <ul class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
	           <li class="am-dropdown" data-am-dropdown="">
	               <a class="am-dropdown-toggle" data-am-dropdown-toggle="" href="javascript:;">
	                   <span class="am-icon-users"></span>${sessionScope.LOGIN_USER.loginName} <span class="am-icon-caret-down"></span>
	               </a>
	               <ul class="am-dropdown-content">
	                   <li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
	                   <li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
	                   <li><a href="#"><span class="am-icon-power-off"></span> 退出</a></li>
	               </ul>
	           </li>
	       </ul>
	   </div>
	</header>
	
	<div class="am-cf admin-main">
	   <div class="admin-sidebar am-offcanvas">
	       <div class="am-offcanvas-bar admin-offcanvas-bar">
	           <ul class="am-list admin-sidebar-list">
	               <li><a href="admin"><span class="am-icon-home"></span> 首页</a></li>
	               <li><a href="${pageContext.request.contextPath}/customer/admin"><span class="am-icon-user"></span> 客户管理 </a></li>
	               <li><a href="${pageContext.request.contextPath}/user/logout.action"><span class="am-icon-sign-out"></span> 注销</a></li>
	           </ul>
	       </div>
	   </div>
	</div>
	
	<script src="<%=basePath%>resources/js/jquery-1.12.4.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.datatables.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script>
		var $requestUri = '${requestUri}';
		$requestUri = $requestUri ? $requestUri
				: '${pageContext.request.contextPath}/index';
		$(function() {
			var $form = $('#login-form');
			var $group = $form.find('.am-form-group').last();
			var $alert = $group.find('.am-alert');
			var $submit = $form.find('.am-btn-primary');
			$form.validator({
				// 是否使用 H5 原生表单验证，不支持浏览器会自动退化到 JS 验证
				H5validation : false,
				onValid: function(validity) {
					$alert.hide();
				},
				submit : function() {
					$.AMUI.progress.start();
					$.ajax({
						type : 'POST',
						url : $form.attr('action'),
						data : $form.serialize(),
						success : function(result) {
							$.AMUI.progress.done();
							if (result.code == 200) {//验证成功
								window.location.href = $requestUri;
							} else if (result.code == 400) {//验证失败
								if (!$alert.length) {
							        $alert = $('<div class="am-text-danger"></div>').hide().
							          appendTo($group);
							      }
							      $alert.html(result.msg).show();
							}

						}
					});
					return false;
				}
			});
		});
	</script>
</body>
</html>