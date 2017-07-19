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
<title>快递管理系统</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="format-detection" content="telephone=no">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="alternate icon" type="image/png"
	href="<%=basePath%>resources/i/favicon.png">
<link rel="stylesheet" href="<%=basePath%>resources/css/amazeui.min.css" />
<link rel="stylesheet" href="<%=basePath%>resources/css/admin.css" />
<link rel="stylesheet"
	href="<%=basePath%>resources/css/amazeui.datatables.min.css" />
<link rel="stylesheet"
    href="<%=basePath%>resources/css/amazeui.datetimepicker.css" />
</head>
<body>
	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<strong>快递管理系统</strong>
		</div>

		<div class="am-collapse am-topbar-collapse">
			<ul
				class="am-nav am-nav-pills am-topbar-nav am-topbar-right admin-header-list">
				<li class="am-dropdown" data-am-dropdown=""><a
					class="am-dropdown-toggle" data-am-dropdown-toggle=""
					href="javascript:;"> <span class="am-icon-users"></span>
						${sessionScope.LOGIN_USER.loginName} <span
						class="am-icon-caret-down"></span>
				</a>
					<ul class="am-dropdown-content">
						<li><a href="#"><span class="am-icon-user"></span> 资料</a></li>
						<li><a href="#"><span class="am-icon-cog"></span> 设置</a></li>
						<li><a href="#"><span class="am-icon-power-off"></span>
								退出</a></li>
					</ul></li>
			</ul>
		</div>
	</header>

	<div class="am-cf admin-main">
		<div class="admin-sidebar am-offcanvas">
			<div class="am-offcanvas-bar admin-offcanvas-bar">
				<ul class="am-list admin-sidebar-list">
				    <li><a href="${pageContext.request.contextPath}/admin"><span class="am-icon-home"></span> 首页</a></li>
					<li class="am-panel"><a
						data-am-collapse="{target: '#express-nav'}"> <i
							class="am-icon-archive"></i> 快递管理 <i
							class="am-icon-angle-right am-fr am-margin-right"></i>
					</a>
						<ul class="am-list admin-sidebar-sub am-collapse am-in"
							id="express-nav">
							<li><a href="#/userAdd"><i class="am-icon-archive"></i>
									录入快递 </a></li>
							<li><a href="#/userList/0"><i class="am-icon-archive"></i>
									快递列表 </a></li>
						</ul></li>
					<li><a
						href="${pageContext.request.contextPath}/customer/admin"><span
							class="am-icon-user"></span> 客户管理 </a></li>
					<li><a
						href="${pageContext.request.contextPath}/user/logout.action"><span
							class="am-icon-sign-out"></span> 注销</a></li>
				</ul>
			</div>
		</div>
		<!-- sidebar end -->

		<div class="admin-content">
			<div class="admin-content-body">
				<div class="am-cf am-padding">
					<div class="am-fl am-cf">
						<strong class="am-text-primary am-text-lg">快递管理</strong> / <small>录入快递</small>
					</div>
				</div>
				<div class="am-g">
					<div class="am-u-sm-6 am-u-sm-centered">
						<div class="am-panel am-panel-primary">
							<div class="am-panel-hd">录入快递</div>
							<div class="am-panel-bd">
								<form class="am-form" id="express-form"
									action="${pageContext.request.contextPath}/express/create.action"
									method="POST">
									<div class="am-g">
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<label for="name">姓名:</label> <input type="text"
													name="name" placeholder="输入姓名" />
											</div>
											<div class="am-form-group">
												<label for="phone_number">手机号码:</label> <input type="text"
													name="phone_number" placeholder="输入手机号码" />
											</div>
											<div class="am-form-group">
												<label for="name">地址:</label> <select name="area" placeholder="地址"></select>
											</div>
											<div class="am-form-group">
												<label for="phone_number">日期:</label>
												<div class="am-input-group date">
												  <input type="text" class="am-form-field" readonly>
												  <span class="am-input-group-label add-on"><i class="icon-th am-icon-calendar"></i></span>
												</div>
											</div>
											<button class="am-btn am-btn-primary am-btn-lg am-btn-block" type="submit">录入</button>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>

			</div>
			<div class="admin-content-footer"></div>
		</div>

	</div>

	<script src="<%=basePath%>resources/js/jquery-1.12.4.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.datatables.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.datetimepicker.min.js"></script>
	<script src="<%=basePath%>resources/js/locales/amazeui.datetimepicker.zh-CN.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script>
		$(function() {
			$('.date').datetimepicker({
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				endDate:new Date()
			}).datetimepicker('update', new Date());
		});
	</script>
</body>
</html>