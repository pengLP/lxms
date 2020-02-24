<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名验证</title>
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
							url : "${pageContext.request.contextPath}/admin/userRcertification/list",
							striped : true,
							rownumbers : true,
							pagination : true,
							singleSelect : true,
							idField : 'userRcertificationId',
							sortName : 'createDate',
							sortOrder : 'asc',
							pageSize : 50,
							pageList : [ 10, 20, 30, 40, 50, 100, 200, 300,
									400, 500 ],
							frozenColumns : [ [ {
								width : '100',
								title : '真实姓名',
								field : 'realName',
								align : 'center',
								sortable : true
							} ] ],
							columns : [ [
									{
										width : '200',
										title : '身份证',
										field : 'carded',
										align : 'center',
										sortable : true
									},
									{
										width : '100',
										title : '查看图片',
										align : 'center',
										formatter : function(val, row) {
											return "<a href='javascript:void(0)' onclick=\"picture('"+row.cardedPicture+"','"+row.passportPicture+"')\" >验证</a>";
										}
									},
									{
										field : 'action',
										title : '操作',
										width : '200',
										align : 'center',
										formatter : function(val, row) {
											str = '';
											str += "<a href='javascript:pass("+row.uid+","+row.userRcertificationId+")'class='easyui-linkbutton' iconCls='icon-remove' plain='true'>通过</a>";
											str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
											str += "<a href='javascript:NotPass("+row.uid+","+row.userRcertificationId+")' class='easyui-linkbutton' iconCls='icon-remove' plain='true'>没通过</a>";
											return str;
										}
									} ] ],
							toolbar : '#toolbar'
						});
	});
	

	function pass(uid, userRcertificationId) {
		$.messager
				.confirm(
						"系统提示",
						"您确定要通过<font color=red>" + uid + "</font>的实名认证吗？",
						function(r) {
							if (r) {
								$.post("${pageContext.request.contextPath}/admin/userRcertification/pass",
												{
													'uid' : uid,
													'userRcertificationId' : userRcertificationId
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
	function NotPass(uid, userRcertificationId) {
		$.messager.confirm("系统提示","您确定要不通过<font color=red>" + uid+ "</font>的实名认证吗？",
						function(r) {
							if (r) {
								$.post("${pageContext.request.contextPath}/admin/userRcertification/notPass",
												{
													'uid' : uid,
													'userRcertificationId' : userRcertificationId
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

	function picture(cardedPicture, passportPicture) {
		$.modalDialog({
					title : '查看证件图片',
					width : 700,
					height : 500,
					href : '${pageContext.request.contextPath}/admin/userRcertification/lookPicture?cardedPicture='
							+ cardedPicture
							+ '&passportPicture='
							+ passportPicture
				});
	}
</script>
</head>
<body style="margin: 1px">
	<table id="dataGrid" title="实名验证" class="easyui-datagrid"
		fitColumns="true" fit="true" toolbar="#tb">

	</table>
	<div id="tb"></div>
</body>
</html>