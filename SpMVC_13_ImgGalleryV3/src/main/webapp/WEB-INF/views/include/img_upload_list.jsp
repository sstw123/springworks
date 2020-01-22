<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<style>
	.img_list {
		display: flex;
		flex-wrap: wrap;
	}
	
	.img_view img {
		width: 100px;
		height: 100px;
		margin: 5px;
	}
</style>
<div class="img_list">
	<c:forEach items="${imgList}" var="images" varStatus="status">
		<input type="hidden" name="img_up_files[${status.index}].img_file_upload_name" value="${images.img_file_upload_name}">
		<div class="img_view" data-id="${images.img_file_seq}">
			<img src="${rootPath}/images/${images.img_file_upload_name}" alt="img">
		</div>
	</c:forEach>
</div>