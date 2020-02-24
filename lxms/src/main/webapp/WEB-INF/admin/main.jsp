<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>旅行买手台管理页面</title>
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
<script type="text/javascript">
	var url;

	function openTab(text, url, iconCls) {
		if ($("#tabs").tabs("exists", text)) {
			$("#tabs").tabs("select", text);
		} else {
			var content = "<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='${ctx}/admin/"
					+ url + "'></iframe>";
			$("#tabs").tabs("add", {
				title : text,
				iconCls : iconCls,
				closable : true,
				content : content
			});
		}
	}

	function openPasswordModifyDialog() {
		$("#dlg").dialog("open").dialog("setTitle", "修改密码");
		url = "${ctx}/admin/blogger/modify";
	}

	function modifyPassword() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				var newPassword = $("#newPassword").val();
				var newPassword2 = $("#newPassword2").val();
				if (!$(this).form("validate")) {
					return false;
				}
				if (newPassword != newPassword2) {
					$.messager.alert("系统提示", "确认密码输入错误！");
					return false;
				}
				return true;
			},
			success : function(result) {
				var result = eval('(' + result + ')');
				if (result.success) {
					$.messager.alert("系统提示", "密码修改成功,下一次登录失效！");
					resetValue();
					$("#dlg").dialog("close");
				} else {
					$.messager.alert("系统提示", "密码修改失败！");
					return;
				}
			}
		});
	}

	function closePasswordModifyDialog() {
		resetValue();
		$("#dlg").dialog("close");
	}

	function resetValue() {
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}

	function logout() {
		$.messager.confirm("系统提示", "您确定要退出系统吗?", function(r) {
			if (r) {
				window.location.href = "${ctx}/user/logout";
			}
		});
	}
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 78px; background-color: #E0ECFF">
		<table style="padding: 5px" width="100%">
			<tr>
				<td width="50%"><img alt="logo" src="http://139.129.221.22/images/logo2.png">
				</td>
				<td valign="bottom" align="right" width="50%"><font size="3">&nbsp;&nbsp;<strong>欢迎：</strong>${currentUser.name }</font>
				</td>
			</tr>
		</table>
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" data-options="iconCls:'icon-home'">
				<div align="center" style="padding-top: 100px">
					<font color="red" size="10">欢迎使用</font>
				</div>
			</div>
		</div>
	</div>
	<div region="west" style="width: 200px" title="导航菜单" split="true">
		<div class="easyui-accordion" data-options="fit:true,border:false">
			<div title="常用操作" data-options="selected:true,iconCls:'icon-item'"
				style="padding: 10px">
				<a
					href="javascript:openTab('实名验证','userRcertification','icon-writeblog')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-writeblog'"
					style="width: 150px">实名验证</a> <a
					href="javascript:openTab('机票验证','orderOver','icon-writeblog')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">机票验证</a>
					 <a
					href="javascript:openTab('提现申请','withdraw/notHandle','icon-writeblog')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">提现申请</a>
					 <a
					href="javascript:openTab('提现处理','withdraw/handle','icon-writeblog')"
					class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-bkgl'" style="width: 150px;">提现完成处理</a>

			</div>
			<div title="系统管理" data-options="iconCls:'icon-system'"
				style="padding: 10px">
				<a href="javascript:logout()" class="easyui-linkbutton"
					data-options="plain:true,iconCls:'icon-exit'" style="width: 150px;">安全退出</a>
			</div>
		</div>
	</div>
	<div region="south" style="height: 25px; padding: 5px" align="center">
		Copyright © 2012-2016 旅行买手</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 400px; height: 200px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="8px">
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="userName" name="userName"
						value="${currentUser.name }" readonly="readonly"
						style="width: 200px" /></td>
				</tr>
				<tr>
					<td>新密码：</td>
					<td><input type="password" id="newPassword" name="newPassword"
						class="easyui-validatebox" required="true" style="width: 200px" />
					</td>
				</tr>
				<tr>
					<td>确认新密码：</td>
					<td><input type="password" id="newPassword2"
						name="newPassword2" class="easyui-validatebox" required="true"
						style="width: 200px" /></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:modifyPassword()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a
			href="javascript:closePasswordModifyDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>

</body>
</html>