<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>JSP 페이지</title>
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<style>
		body {
			margin: 0 auto;
			width: 1024px;
		}
		header {
			text-align: center;
			padding: 20px;
			background-color: #eee;
			cursor: pointer;
		}
	</style>
	<script>
		$(function() {
			$("header").on("click", function() {
				document.location.href = "${rootPath}/"
			})
		})
	</script>
</head>
<body>
	<header>
		<h3>친구 정보 관리</h3>
	</header>
	
	<%@ include file="/WEB-INF/views/include/nav.jsp" %>
	
	<section>
		<c:choose>
			<c:when test="${BODY == 'LIST'}">
				<%@ include file="/WEB-INF/views/include/frd_list.jsp" %>
			</c:when>
			<c:when test="${BODY == 'VIEW'}">
				<%@ include file="/WEB-INF/views/include/frd_view.jsp" %>
			</c:when>
			<c:when test="${BODY == 'INPUT'}">
				<%@ include file="/WEB-INF/views/include/frd_input.jsp" %>
			</c:when>
			<c:when test="${BODY == 'EDIT'}">
				<%@ include file="/WEB-INF/views/include/frd_edit.jsp" %>
			</c:when>
		</c:choose>
	</section>
	
	<%@ include file="/WEB-INF/views/include/modal_login.jsp" %>
	<%@ include file="/WEB-INF/views/include/modal_join.jsp" %>
</body>
</html>