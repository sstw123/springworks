<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<style>
	.card-header {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
</style>

<header class="jumbotron text-center">
		<h3>네이버 영화 랭크</h3>
</header>

<body class="container-fluid">
	<section class="row">
		<c:forEach var="NAVER" items="${NAVER_LIST}">
			<%@ include file="/WEB-INF/views/naver_list_body.jsp" %>
		</c:forEach>
	</section>
</body>
</html>