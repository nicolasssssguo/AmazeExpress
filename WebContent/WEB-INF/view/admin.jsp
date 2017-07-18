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
<title>快递管理</title>
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
</head>
<body>
	<header class="am-topbar am-topbar-inverse admin-header">
		<div class="am-topbar-brand">
			<strong>快递管理</strong>
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
					<li><a href="admin"><span class="am-icon-home"></span> 首页</a></li>
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
				<div class="am-padding">
					<div class="am-g am-margin-bottom">
						<div class="am-u-sm-12 am-u-md-6">
							<div class="am-btn-toolbar">
								<div class="am-btn-group am-btn-group-xs">
									<button type="button" class="am-btn am-btn-primary">
										<span class="am-icon-plus"></span> 新增
									</button>
									<button type="button" class="am-btn am-btn-primary">
										<span class="am-icon-save"></span> 保存
									</button>
									<button type="button" class="am-btn am-btn-primary">
										<span class="am-icon-archive"></span> 审核
									</button>
									<button type="button" class="am-btn am-btn-primary">
										<span class="am-icon-trash-o"></span> 删除
									</button>
								</div>
							</div>
						</div>
					</div>
					<div class="am-g">
						<div class="am-u-sm-12">
							<table
								class="am-table am-table-bd am-table-bordered am-table-centered admin-content-table"
								id="datatables">
								<thead>
									<tr class="am-primary">
										<th>姓名</th>
										<th>地址</th>
										<th>手机号码</th>
										<th>到达日期</th>
										<th>状态</th>
										<th>操作</th>
									</tr>
								</thead>
							</table>
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
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script>
		$(function() {
			$('#datatables').DataTable({
				"dom": 'tip'
			});
		});
	</script>
</body>
</html>