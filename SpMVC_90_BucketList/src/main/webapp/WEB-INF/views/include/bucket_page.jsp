<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${rootPath}/resources/css/bucket_page.css"/>
<script src="${rootPath}/resources/js/bucket_page.js"></script>

<article class="bucket_page">
	<div class="bucket_table">
		<%@ include file="/WEB-INF/views/include/bucket_table.jsp" %>
	</div>
	
	<form class="add_form">
		<input id="add_b_content" name="b_content"/>
		<button class="btn_add" type="submit">추가</button>
	</form>
	
	<%@ include file="/WEB-INF/views/include/dropdown.jsp" %>
	<%@ include file="/WEB-INF/views/include/edit_modal.jsp" %>
</article>