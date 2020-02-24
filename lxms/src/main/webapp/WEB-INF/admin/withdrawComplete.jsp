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
						url : "${pageContext.request.contextPath}/admin/withdraw/listHandle",
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
							
								{
									field : 'action',
									title : '操作',
									width : '200',
									align : 'center',
									formatter : function(val, row) {
										str = '';
										str += "<a href='javascript:pass("+row.withdrawId+")'class='easyui-linkbutton' iconCls='icon-remove' plain='true'>通过</a>";
										str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
										str += "<a href='javascript:NotPass("+row.withdrawId+")' class='easyui-linkbutton' iconCls='icon-remove' plain='true'>没通过</a>";
										return str;
									}
								} ] ],
						toolbar : '#toolbar'
					});
});


function pass(withdrawId) {
	$.messager
			.confirm(
					"系统提示",
					"您确定要完成<font color=red>" + withdrawId + "</font>的提现吗？",
					function(r) {
						if (r) {
							$.post("${pageContext.request.contextPath}/admin/withdraw/success",
											{
												'withdrawId' : withdrawId
												
											}, function(result) {
												if (result.success) {
													$.messager.alert(
															"系统提示",
															"操作成功！");
													$("#dataGrid").datagrid(
															"reload");
												} else {
													$.messager.alert(
															"系统提示",
															"操作失败");
												}
											}, "json");
						}
					});
}
function NotPass(withdrawId) {
	$.messager.confirm("系统提示","您确定要不完成<font color=red>" +withdrawId+ "</font>的提现吗？",
					function(r) {
						if (r) {
							$.post("${pageContext.request.contextPath}/admin/withdraw/fail",
											{
												'withdrawId' : withdrawId
											}, function(result) {
												if (result.success) {
													$.messager.alert(
															"系统提示",
															"操作成功！");
													$("#dataGrid").datagrid(
															"reload");
												} else {
													$.messager.alert(
															"系统提示",
															"操作失败！");
												}
											}, "json");
						}
					});
}


</script>
</head>
<body style="margin: 1px">
	<table id="dataGrid" title="提现" class="easyui-datagrid"
		fitColumns="true" fit="true" toolbar="#tb">

	</table>
	<div id="tb"></div>
</body>
</html>