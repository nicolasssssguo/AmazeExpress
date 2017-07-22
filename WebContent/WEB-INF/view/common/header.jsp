<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    href="<%=basePath%>resources/css/app.css" />
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
				</a></li>
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
                        <ul class="am-list admin-sidebar-sub am-collapse<c:if test="${module=='express'}"> am-in</c:if>"
                            id="express-nav">
                            <li><a href="${pageContext.request.contextPath}/express/create"><i class="am-icon-archive"></i>
                                    录入快递 </a></li>
                            <li><a href="${pageContext.request.contextPath}/express/list"><i class="am-icon-archive"></i>
                                    快递列表 </a></li>
                        </ul></li>
                    <li class="am-panel"><a
                        data-am-collapse="{target: '#customer-nav'}"> <i
                            class="am-icon-users"></i> 客户管理 <i
                            class="am-icon-angle-right am-fr am-margin-right"></i>
                    </a>
                        <ul class="am-list admin-sidebar-sub am-collapse<c:if test="${module=='customer'}"> am-in</c:if>"
                            id="customer-nav">
                            <li><a href="${pageContext.request.contextPath}/customer/create"><i class="am-icon-users"></i>
                                    增加客户 </a></li>
                            <li><a href="${pageContext.request.contextPath}/customer/list"><i class="am-icon-users"></i>
                                    客户列表 </a></li>
                        </ul></li>
					<li><a
						href="${pageContext.request.contextPath}/user/logout.do"><span
							class="am-icon-sign-out"></span> 注销</a></li>
				</ul>
			</div>
		</div>
		<!-- sidebar end -->