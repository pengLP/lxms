<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现</title>
<link rel="stylesheet" type="text/css"
	href="http://139.129.221.22/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="http://139.129.221.22/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript"
	src="http://139.129.221.22/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript"
	src="http://139.129.221.22/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="http://139.129.221.22/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
	<!-- 扩展EasyUI -->
<script type="text/javascript" src="http://139.129.221.22/static/extEasyUI.js" charset="utf-8"></script>
<!-- 扩展Jquery -->
<script type="text/javascript" src="http://139.129.221.22/static/extJquery.js" charset="utf-8"></script>
<script type="text/javascript">
var dataGrid;
$(function() {
	dataGrid = $('#dataGrid')
			.datagrid(
					{
						url : "${pageContext.request.contextPath}/admin/withdraw/listNotHandle",
						striped : true,
						rownumbers : true,
						pagination : true,
						singleSelect : true,
						idField : 'withdrawId',
						sortName : 'createDate',
						sortOrder : 'asc',
						pageSize : 50,
						pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
								400, 500 ],
						frozenColumns : [ [ {
							width : '100',
							title : '提现ID',
							field : 'withdrawId',
							align : 'center',
							sortable : true
						} ] ],
						columns : [ [
						         	{
										width : '200',
										title : '邮件',
										field : 'email',
										align : 'center',
										sortable : false
									},
									{
										width : '200',
										title : '姓名',
										field : 'realName',
										align : 'center',
										sortable : false
									},
									{
										width : '200',
										title : '金额',
										field : 'total',
										align : 'center',
										sortable : true
									},
									{
										width : '200',
										title : '状态',
										field : 'status',
										align : 'center',
										sortable : false
									},
							
							] ],
						toolbar : '#toolbar'
					});
});

function exportUser(){
	window.open('${pageContext.request.contextPath}/admin/withdraw/export')
}


</script>
</head>
<body style="margin: 1px">
	<div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="exportUser()">导出表格</a>
    </div>
	<table id="dataGrid" title="提现" class="easyui-datagrid"
		fitColumns="true" fit="true" toolbar="#tb">

	</table>
	<div id="tb"></div>
</body>
</html>