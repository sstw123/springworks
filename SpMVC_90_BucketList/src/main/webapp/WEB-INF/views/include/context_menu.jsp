<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${rootPath}/resources/css/context_menu.css"/>
<script src="${rootPath}/resources/js/context_menu.js"></script>

<div class="context context_inactive">
	<ul>
		<li class="btn_edit">보기/수정</li>
		<li class="btn_success">완료</li>
		<li class="btn_delete">삭제</li>
		<div class="order_box">
			<li class="btn_order_up">▲</li>
			<li class="btn_order_down">▼</li>
		</div>
	</ul>
</div>