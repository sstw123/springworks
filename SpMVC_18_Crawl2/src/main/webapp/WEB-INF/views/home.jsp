<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
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

<body class="container-fluid">
	<header class="jumbotron text-center">
		<h3>크롤링 리스트</h3>
	</header>
	<nav>
		<ul>
			<li>실시간 유저 정보</li>
			<li>팁/전략 게시판</li>
			<li>자유게시판</li>
		</ul>
	</nav>
	<p>총 조회수 : ${CRAWL_DTO.sumOfHit}</p>
	<c:forEach var="CRAWL" items="${CRAWL_DTO.crawlSubList}">
		<%@ include file="/WEB-INF/views/crawl_list_body.jsp" %>
	</c:forEach>
</body>
</html>