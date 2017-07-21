<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="common/header.jsp" %>
		<div class="admin-content">
            <div class="admin-content-body">
                <div class="am-cf am-padding">
                    <div class="am-fl am-cf">
                        <strong class="am-text-primary am-text-lg">快递管理</strong> / <small>快递列表</small>
                    </div>
                </div>
                <div class="am-g">
                    <div class="am-u-sm-12">
                        <table
                            class="am-table am-table-bd am-table-bordered am-table-centered admin-content-table"
                            id="datatables">
                            <thead>
                                <tr class="am-primary">
                                    <th><input type="checkbox" /></th>
                                    <th>姓名</th>
                                    <th>地址</th>
                                    <th>手机号码</th>
                                    <th>到达日期</th>
                                    <th>状态</th>
                                    <th>管理</th>
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
	<script src="<%=basePath%>resources/js/amazeui.datetimepicker.min.js"></script>
	<script src="<%=basePath%>resources/js/locales/amazeui.datetimepicker.zh-CN.js"></script>
	<script src="<%=basePath%>resources/js/amazeui.dialog.min.js"></script>
	<script src="<%=basePath%>resources/js/area.js"></script>
	<script>
	$(function() {
		$.fn.dataTable.ext.legacy.ajax = true;
        $('#datatables').DataTable({
        	dom: '<"am-btn-toolbar am-fl">frtip',
            processing: true,
            serverSide: true,
            ajax : '${pageContext.request.contextPath}/express/list.action',
            deferRender: true,
            searchDelay: 700,
            columns:[
            	{
            		data: null,
            		render: function ( data, type, row ) {
                        return '<input type="checkbox" />';
                    }
            	},
            	{data:'recipient.name'},
            	{data:'area'},
            	{data:'recipient.phoneNumber'},
                {data:'arriveDate'},
                {
                	data:'status',
                	render: function ( data, type, row ) {
                        return data == 0?'未签收':'已签收';
                    }
                },
                {
                	data: null,
                	render: function ( data, type, row ) {
                		var $btngroup = $('<div class="am-btn-group"></div>');
                		var $editbtn = $('<button class="am-btn am-btn-primary am-btn-sm"><span class="am-icon-edit"></span></button>');
                		var $removebtn = $('<button class="am-btn am-btn-primary am-btn-sm"><span class="am-icon-trash"></span></button>');
                		$btngroup.append($editbtn);
                		$btngroup.append($removebtn);
                		return $btngroup.html();
                    }
                }
            ],
            columnDefs: [{
            	targets: '_all',
                orderable: false
            }],
            initComplete: function(settings, json){
            	var $thr = $('table thead tr');
                var $checkAll = $thr.find('input');
                var $tbr = $('table tbody tr');
                $checkAll.click(function(event) {
                    $tbr.find('input').prop('checked', $(this).prop('checked'));
                    if ($(this).prop('checked')) {
                        $tbr.find('input').parent().parent().addClass('am-active');
                    } else {
                        $tbr.find('input').parent().parent().removeClass('am-active');
                    }
                    $('div.toolbar button').prop('disabled',$tbr.find('input:checked').length==0);
                    event.stopPropagation();
                });
                $('table tbody').on('click','input',function(event){
                	$(this).parent().parent().toggleClass('am-active');
                	$checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.length ? true: false);
                	$('div.toolbar button').prop('disabled',$tbr.find('input:checked').length==0);
                	event.stopPropagation();
                }).on('click','tr',function(event){
                	$(this).find('input').click();
                });
            }
        });
        
        $("div.am-btn-toolbar").html(
        	'<div class="am-margin-bottom">'+
	        	'<div class="btn-group" role="group">'+
	                '<button class="am-btn am-btn-primary" type="button"  disabled>'+
	                    '<span class="am-icon-pencil-square"></span> 批量签收'+
	                '</button>'+
	                '<button class="am-btn am-btn-primary" type="button" disabled>'+
	                    '<span class="am-icon-trash"></span> 批量删除'+
	                '</button>'+
	                '<div class="am-form-group am-margin-left"><label><input type="checkbox" autocomplete="off" checked />只显示今日</label><div>'+
	            '</div>'+
            '</div>'
        );
        
        $('#datatables_filter').addClass('am-fr');
    });
	</script>
</body>
</html>