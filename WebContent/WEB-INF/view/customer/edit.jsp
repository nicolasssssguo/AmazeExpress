<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

		<div class="admin-content">
			<div class="admin-content-body">
				<div class="am-cf am-padding">
					<div class="am-fl am-cf">
						<strong class="am-text-primary am-text-lg">客户管理</strong> / <small>编辑客户信息</small>
					</div>
				</div>
				<div class="am-g">
					<div class="am-u-sm-6 am-u-sm-centered">
						<div class="am-panel am-panel-primary">
							<div class="am-panel-hd">编辑客户信息</div>
							<div class="am-panel-bd">
								<form class="am-form" id="customer-form"
									action="${pageContext.request.contextPath}/customer/update.do"
									method="POST">
									<div class="am-g">
										<div class="am-u-sm-12">
											<input type="hidden" name="id" value="${customer.id}" />
											<div class="am-form-group">
												<label for="name">姓名:</label> <input type="text"
													name="name" minlength="3" placeholder="输入姓名" value="${customer.name}" required />
											</div>
											<div class="am-form-group">
												<label for="phone_number">手机号码:</label> <input class="js-pattern-mobile" type="text"
													name="phone_number" placeholder="输入手机号码" value="${customer.phoneNumber}" required />
											</div>
											<div class="am-form-group">
												<label for="name">地址:</label> <select class="area" name="area" data-am-selected="{btnWidth: '100%'}" data-parent="350681110000" required></select>
											</div>
											<button class="am-btn am-btn-primary am-btn-lg am-btn-block" type="submit">保存</button>
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
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script src="<%=basePath%>resources/js/area.js"></script>
	<script>
	var selectedArea = '${customer.area}';
	var $url = '${url}';
	if ($.AMUI && $.AMUI.validator) {
	    $.AMUI.validator.patterns.mobile = /^1((3|5|8){1}\d{1}|70)\d{8}$/;
	  }
		$(function() {
			$select = $('select.area');
			var parent = $select.attr('data-parent');
			$.each(area, function(index, data){
				if(data.parent == parent){
					var $option = $('<option></option>');
					$option.text(data.name);
					if(data.name == selectedArea){
						$option.prop('selected',true);
					}
					$select.append($option);
				}
			});
			var $form = $('#customer-form');
			var $alert = $form.find('.am-alert');
			if(!$alert.length) {
                $alert = $('<div class="bb-alert am-alert am-alert-info"></div>');
                $alert.appendTo($('body')).hide();
            }
			$form.validator({
				// 是否使用 H5 原生表单验证，不支持浏览器会自动退化到 JS 验证
				H5validation : false,
				submit : function() {
					var formValidity = this.isFormValid();
					if(formValidity) {
						$.ajax({
							type : 'POST',
							url : $form.attr('action'),
							data : $form.serialize(),
							success : function(result) {
								if (result.code == 200) {
									window.location.href = $url;
								}else {
									$alert.text(result.msg)
	                                .show()
	                                .delay(3000)
	                                .fadeOut();
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