<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改个人信息页面</title>
<link rel="stylesheet" type="text/css" href="http://139.129.221.22/static/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="http://139.129.221.22/static/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="http://139.129.221.22/static/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="http://139.129.221.22/static/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="http://139.129.221.22/static/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>


<script type="text/javascript">
	

	function submitData(){
		var name=$("#name").val();
		var sign=$("#sign").val();
		
		if(name==null || name==''){
			alert("请输入昵称！");
		}else if(tel==null || tel==''){
			alert("请输入电话！");
		}else if(email==null || email==''){
			alert("请输入邮箱！");
		}else{
			$("#pF").val(profile);
			$("#form1").submit();
		}
	}
</script>
</head>
<body style="margin: 10px">

<div id="p" class="easyui-panel" title="修改个人信息" style="padding: 10px">
	<form id="form1" action="${pageContext.request.contextPath}/user/save" method="post" enctype="multipart/form-data">
		<table cellspacing="20px">
			<tr>
				<td width="80px">昵称：</td>
				<td>
					<input type="hidden" id="id" name="id" value="${currentUser.uid }"/>
					<input type="text" id="userName" name="userName" style="width: 200px" value="${currentUser.name }" readonly="readonly" />
				</td>
			</tr>

			<tr>
				<td>电话：</td>
				<td>
					<input type="text" id="tel" name="tel" style="width: 400px" value="${currentUser.tel }" />
				</td>
			</tr>
			<tr>
				<td>邮件：</td>
				<td>
					<input type="text" id="email" name="email" style="width: 400px" value="${currentUser.email }"/>
				</td>
			</tr>
			
			
		</table>
	</form>
</div>

</body>
</html>