<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	window.onload = function() {
		var img = document.getElementsByClassName("img");
		img.ondblclick = function() {
			if (this.width == this.attributes['default_width'].value
					&& this.height == this.attributes['default_height'].value) {
				this.width *= 3;
				this.height *= 3;
			} else {
				this.width = this.attributes['default_width'].value;
				this.height = this.attributes['default_height'].value;
			}
		}
	};
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title=""
		style="overflow: hidden;padding: 3px;">
		<c:forEach items="${pictureList}" var="picture">
			<img src="${picture}" width="100" height="200" class="img">
		</c:forEach>
	</div>
</div>