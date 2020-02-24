<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
</head>
<body>

	<span id="totalSecond">4</span>

	<script language="javascript" type="text/javascript">
		var second = document.getElementById('totalSecond').textContent;

		if (navigator.appName.indexOf("Explorer") > -1) {
			second = document.getElementById('totalSecond').innerText;
		} else {
			second = document.getElementById('totalSecond').textContent;
		}

		setInterval("redirect()", 1000);
		function redirect() {
			if (second < 0) {
				location.href = '${pageContext.request.contextPath}/index';
			} else {
				if (navigator.appName.indexOf("Explorer") > -1) {
					document.getElementById('totalSecond').innerText = second--;
				} else {
					document.getElementById('totalSecond').textContent = second--;
				}
			}
		}
	</script>
</body>
</html>