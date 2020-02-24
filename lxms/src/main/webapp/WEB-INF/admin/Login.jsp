<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="login">
<meta name="author" content="huige">
<link rel="icon" href="../../favicon.ico">

<title>旅行买手</title>
<jsp:include page="../inc.jsp"></jsp:include>
<link href="http://139.129.221.22/css/signin.css" rel="stylesheet">

</head>

<body>
	<div
		style="position: absolute; width: 100%; height: 100%; z-index: -1; left: 0; top: 0;">
		<img src="http://139.129.221.22/images/background.jpg" height="100%" width="100%"
			style="left: 0; top: 0;">
	</div>

	<div class="container">
		<div
			class="col-lg-4 col-lg-offset-4 col-sm-6 col-sm-offset-3 col-xs-8 col-xs-offset-2"
			id="logindev">

			<form class="form-signin" action="${ctx}/admin/login" method="post">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="inputUserName" class="sr-only">账号</label> <input
					type="text" id="inputUserName" name="username"
					value="${admin.username }" class="form-control"
					placeholder="UserName" required autofocus><span><font
					color="red" id="error">${error }</font></span><label for="inputPassword"
					class="sr-only">密码</label> <input type="password"
					id="inputPassword" name="password" value="${admin.password }"
					class="form-control" placeholder="Password" required>
				<div class="checkbox">
					<label> <input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div>
				<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			</form>
		</div>
	</div>
</body>
</html>
