<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>

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
									action="${pageContext.request.contextPath}/express/create.do"
									method="POST">
									<div class="am-g">
										<div class="am-u-sm-12">
											<div class="am-form-group">
												<label for="name">姓名:</label> <input id="name" type="text"
													name="name" minlength="3" placeholder="输入姓名" required />
											</div>
											<div class="am-form-group">
												<label for="phone_number">手机号码:</label> <input class="js-pattern-mobile" id="phone-number" type="text"
													name="phone_number" placeholder="输入手机号码" autofocus autocomplete="off" required />
											</div>
											<div class="am-form-group">
												<label for="name">地址:</label> <select id="area" class="" name="area" data-am-selected="{btnWidth: '100%'}" data-parent="350681110000" required></select>
											</div>
											<div class="am-form-group">
												<label for="phone_number">日期:</label>
												<div class="am-input-group am-datepicker-date" data-am-datepicker>
												  <input class="am-form-field" name="arrive_date" type="text" readonly required>
												  <span class="am-input-group-btn am-datepicker-add-on">
												  	<button class="am-btn am-btn-default" type="button"><span class="am-icon-calendar"></span> </button>
												  </span>
												</div>
											</div>
											<div class="am-form-group">
												<label for="phone_number">状态:</label>
												<label class="am-radio-inline">
													<input type="radio" name="status" value="0" checked> 未签收
												</label>
												<label class="am-radio-inline">
													<input type="radio" name="status" value="1"> 已签收
												</label>
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
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script src="<%=basePath%>resources/js/area.js"></script>
	<script src="<%=basePath%>resources/js/typeahead.js"></script>
	<script>
	if ($.AMUI && $.AMUI.validator) {
	    $.AMUI.validator.patterns.mobile = /^1((3|5|8){1}\d{1}|70)\d{8}$/;
	  }
		$(function() {
			$select = $('#area');
			var parent = $select.attr('data-parent');
			$.each(area, function(index, data){
				if(data.parent == parent){
					var $option = $('<option></option>');
					$option.text(data.name);
					$select.append($option);
				}
			});
			var $form = $('#express-form');
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
									window.location.reload();
									/* $('input[name="name"]').val('');
									$('input[name="phone_number"]').val('');
	                                $alert.text('录入成功')
	                                .show()
	                                .delay(3000)
	                                .fadeOut(); */
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
			
			$('#phone-number').typeahead({
				source: function(number, process) {
		            return $.post('${pageContext.request.contextPath}/customer/search.do',{endingNumber:number},
		            function(data) {
		                return process(data);
		            });
		        },
		        displayText: function(item) {
		            return typeof item !== 'undefined' && typeof item.phoneNumber != 'undefined' ? item.phoneNumber: item;
		        },
		        afterSelect: function(item) {
		        	$select.val(item.area);
		        	$('#name').val(item.name);
		        },
		        delay: 300
			});
		});
		$('.am-datepicker-date').datepicker('setValue', new Date());
	</script>
</body>
</html>