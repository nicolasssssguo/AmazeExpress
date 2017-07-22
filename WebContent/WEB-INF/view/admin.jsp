<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>
		<div class="admin-content">
            <div class="admin-content-body">
                <div class="am-cf am-padding">
                    <div class="am-fl am-cf">
                        <strong class="am-text-primary am-text-lg">快递管理系统</strong> / <small>今日快递</small>
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
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>

            </div>
            <div class="admin-content-footer"></div>
        </div>

	</div>
	<script src="<%=basePath%>resources/js/jquery-1.12.4.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.datatables.min.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.dialog.js"></script>
	<script src="<%=basePath%>resources/js/area.js"></script>
	<script>
	var EXPRESS_URL = '${pageContext.request.contextPath}/express';
	var EXPRESS_LIST_URL = EXPRESS_URL + '/list.do';
	$(function() {
		$.fn.dataTable.ext.legacy.ajax = true;
		var $thr = $('table thead tr');
        var $checkAll = $thr.find('input');
        var $datatable = $('#datatables').DataTable({
        	dom: 'frtip',
            processing: true,
            serverSide: true,
            ajax : EXPRESS_LIST_URL,
            deferRender: true,
            searchDelay: 700,
            columns:[
            	{data:'recipient.name'},
            	{data:'area'},
            	{data:'recipient.phoneNumber'},
                {data:'arriveDate'},
                {
                	data:'status',
                	render: function ( data, type, row ) {
                        return data == 0?'<span class="am-badge am-badge-warning">未签收</span>':'<span class="am-badge am-badge-info">已签收</span>';
                    }
                }
            ],
            initComplete: function(settings, json){
            	$('#datatables_paginate').addClass('am-fr');
                $('#datatables_info').addClass('am-fl');
            }
        });
    });
	</script>
</body>
</html>