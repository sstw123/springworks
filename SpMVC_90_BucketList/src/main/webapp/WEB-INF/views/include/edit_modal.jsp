<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${rootPath}/resources/css/edit_modal.css"/>
<script src="${rootPath}/resources/js/edit_modal.js"></script>

<div class="edit_modal">
	<div class="close_box">
		<span id="close">&times;</span>
	</div>
	
	<form class="edit_form">
		<input id="edit_b_id" name="b_id" type="hidden"/>
		<input id="edit_b_content" name="b_content"/>
		<button class="btn_edit_done">수정</button>
	</form>
</div>