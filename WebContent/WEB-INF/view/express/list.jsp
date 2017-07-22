<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/header.jsp" %>
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
	<script src="<%=basePath%>resources/js/amazeui.dialog.js"></script>
	<script src="<%=basePath%>resources/js/area.js"></script>
	<script>
	var EXPRESS_URL = '${pageContext.request.contextPath}/express';
	var CREATE_URL = EXPRESS_URL + '/create.do';
	var UPDATE_URL = EXPRESS_URL + '/update.do';
	var EDIT_URL = EXPRESS_URL + '/edit.do';
	var SIGN_URL = EXPRESS_URL + '/sign.do';
	var REMOVE_URL = EXPRESS_URL + '/remove.do';
	var RETRIEVE_URL = EXPRESS_URL + '/retrieve.do';
	var EXPRESS_LIST_URL = EXPRESS_URL + '/list.do';
	$(function() {
		$.fn.dataTable.ext.legacy.ajax = true;
		var $thr = $('table thead tr');
        var $checkAll = $thr.find('input');
        var $datatable = $('#datatables').DataTable({
        	dom: '<"toolbar am-fl">frtip',
            processing: true,
            serverSide: true,
            ajax : EXPRESS_LIST_URL,
            deferRender: true,
            searchDelay: 700,
            ordering: false,
            columns:[
            	{
            		data: null,
            		render: function ( data, type, row ) {
                        return '<input id="'+row.id+'" type="checkbox" />';
                    }
            	},
            	{data:'recipient.name'},
            	{data:'area'},
            	{data:'recipient.phoneNumber'},
                {data:'arriveDate'},
                {
                	data:'status',
                	render: function ( data, type, row ) {
                        return data == 0?'<span class="am-badge am-badge-warning">未签收</span>':'<span class="am-badge am-badge-info">已签收</span>';
                    }
                },
                {
                	data: null,
                	render: function ( data, type, row ) {
                		var $btngroup = $('<div class="am-btn-group"></div>');
                		var $signbtn = $('<button class="am-btn am-btn-primary am-btn-sm sign"><span class="am-icon-pencil-square"></span></button>');
                		var $editbtn = $('<button class="am-btn am-btn-primary am-btn-sm edit"><span class="am-icon-edit"></span></button>');
                		var $removebtn = $('<button class="am-btn am-btn-primary am-btn-sm remove"><span class="am-icon-trash"></span></button>');
                		if(row.status == 0){
                			$btngroup.append($signbtn);
                		}
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
            createdRow: function(row, data, dataIndex){
            	$(row).click(function(event){
                	$(this).find('input').click();
                }).find('input').click(function(event){
                	var $tbr = $('table tbody tr');
                	$(this).closest('tr').toggleClass('am-active');
                	$checkAll.prop('checked', $tbr.find('input:checked').length == $tbr.length ? true: false);
                	$('div.toolbar button.am-btn-primary').prop('disabled',$tbr.find('input:checked').length==0);
                	event.stopPropagation();
                });
            	
            	$(row).find('button.sign').click(function(event){
            		signExpress([data.id]);
            		event.stopPropagation();
            	});
            	$(row).find('button.edit').click(function(event){
            		editExpress(data.id);
            		event.stopPropagation();
            	});
            	$(row).find('button.remove').click(function(event){
            		removeExpress([data.id]);
            		event.stopPropagation();
            	});
            },
            initComplete: function(settings, json){
            	var $todayonly = $('div.toolbar input[type="checkbox"]');
            	$todayonly.click(function(){
            		$.post('${pageContext.request.contextPath}/express/todayonly.do', {todayonly: $todayonly.prop('checked')})
		    	    .success( function(result) { 
		    	    	if (result.code == 200) {//验证成功
		    	    		$datatable.ajax.reload();
						}
		    	     });
            	});
                $checkAll.click(function(event) {
                	var $tbr = $('table tbody tr');
                    $tbr.find('input').prop('checked', $(this).prop('checked'));
                    if ($(this).prop('checked')) {
                        $tbr.find('input').parent().parent().addClass('am-active');
                    } else {
                        $tbr.find('input').parent().parent().removeClass('am-active');
                    }
                    $('div.toolbar button.am-btn-primary').prop('disabled',$tbr.find('input:checked').length==0);
                    event.stopPropagation();
                });
                $('#datatables_paginate').addClass('am-fr');
                $('#datatables_info').addClass('am-fl');
            }
        });
        
        $('div.toolbar').html(
        	'<div class="am-margin-bottom">'+
	        	'<div class="btn-group" role="group">'+
	                '<button class="am-btn am-btn-primary" type="button" onclick="batchSign();" disabled>'+
	                    '<span class="am-icon-pencil-square"></span> 批量签收'+
	                '</button>'+
	                '<button class="am-btn am-btn-primary" type="button" onclick="batchRemove();" disabled>'+
	                    '<span class="am-icon-trash"></span> 批量删除'+
	                '</button>'+
	                '<a class="am-btn am-btn-secondary" href="${pageContext.request.contextPath}/express/export.do">'+
	                    '<span class="am-icon-file-excel-o"></span> 导出'+
	                '</a>'+
	                '<div class="am-form-group am-margin-left"><label><input type="checkbox" autocomplete="off" <c:if test="${sessionScope.TODAY_ONLY == null || sessionScope.TODAY_ONLY == true}">checked</c:if> />只显示今日</label><div>'+
	            '</div>'+
            '</div>'
        );
        
        $('#datatables_filter').addClass('am-fr');
    });
	
	function batchSign(){
		var ids = new Array();
	    var $tbr = $('table tbody tr');
	    $tbr.find('input:checked').each(function() {
	        ids.push($(this).attr('id'));
	    });
	    signExpress(ids);
	}
	function signExpress(ids){
		$.post(SIGN_URL, {
	        "ids[]": ids
	    }).success(function(){
	    	var $datatable = $('#datatables').DataTable();
	    	$datatable.ajax.reload();
	    });
	}
	function editExpress(id){
		window.location.href = EDIT_URL + '/' + id + '?url=<%=path%>/express/list';
	}
	function batchRemove(){
		var ids = new Array();
	    var $tbr = $('table tbody tr');
	    $tbr.find('input:checked').each(function() {
	        ids.push($(this).attr('id'));
	    });
	    removeExpress(ids);
	}
	function removeExpress(ids){
		AMUI.dialog.confirm({content: '确认删除快递信息（无法恢复）？', 
			onConfirm: function(){
				$.post(REMOVE_URL, {
			        "ids[]": ids
			    }).success(function(){
			    	var $datatable = $('#datatables').DataTable();
			    	$datatable.ajax.reload();
			    });
			}
		});
	}
	</script>
</body>
</html>