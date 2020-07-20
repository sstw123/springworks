<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<c:set var="URL" value="${rootPath}/crawl/site/board"/>
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
	nav ul {
		display: flex;
		list-style: none;
		padding: 0;
	}
	
	nav li {
		margin: 10px 20px;
	}
	
	nav li:hover {
		cursor: pointer;
	}
</style>
<script type="text/javascript">
	$(function () {
		
		$("nav ul li").on("click", function() {
			let text = $(this).text()
			let url = ""
			let query = ""
			
			if(text == "홈") {
				url = "${rootPath}/"
			} else if(text == "실시간 유저 정보") {
				url = "${rootPath}/crawl/site/board"
				query = "?c_site=롤인벤&c_board=실시간유저정보&currPage=1"
			} else if(text == "팁과 노하우") {
				url = "${rootPath}/crawl/site/board"
				query = "?c_site=롤인벤&c_board=팁과노하우&currPage=1"
			} else if(text == "자유게시판") {
				url = "${rootPath}/crawl/site/board"
				query = "?c_site=롤인벤&c_board=자유게시판&currPage=1"
			}
			
			document.location.href = url + query
			
		})
	})
</script>

<body class="container-fluid">
	<header class="jumbotron text-center">
		<h3>크롤링 리스트</h3>
	</header>
	<nav>
		<ul>
			<li>홈</li>
			<li>실시간 유저 정보</li>
			<li>팁과 노하우</li>
			<li>자유게시판</li>
		</ul>
	</nav>
	
	<section>
		<c:choose>
			<c:when test="${BODY == '롤인벤실시간유저정보'}">
				<c:set var="SITE_URL" value="http://www.inven.co.kr/board/lol/2778"/>
				<c:set var="HEADER" value="실시간 유저 정보"/>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
			
			<c:when test="${BODY == '롤인벤팁과노하우'}">
				<c:set var="SITE_URL" value="http://www.inven.co.kr/board/lol/2778"/>
				<c:set var="HEADER" value="팁과 노하우"/>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
			
			<c:when test="${BODY == '롤인벤자유게시판'}">
				<c:set var="SITE_URL" value="http://www.inven.co.kr/board/lol/4626"/>
				<c:set var="HEADER" value="팁과 노하우"/>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
		</c:choose>
	</section>
</body>
</html>