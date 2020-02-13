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
			if(text == "홈") {
				document.location.href = "${rootPath}/"
			} else if(text == "실시간 유저 정보") {
				document.location.href = "${rootPath}/crawl/lol/userinfo"
			} else if(text == "팁과 노하우") {
				document.location.href = "${rootPath}/crawl/lol/tip"
			} else if(text == "자유게시판") {
				document.location.href = "${rootPath}/crawl/lol/freeboard"
			}
			
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
		<form>
			<label for="srchStartDate">시작날짜</label>
			<input name="srchStartDate" placeholder="날짜입력">
			
			<label for="srchLastDate">마지막날짜</label>
			<input name="srchLastDate" placeholder="날짜입력">
			
			<button>검색</button>
		</form>
	</section>
	
	<section>
		<c:choose>
			<c:when test="${BODY == 'USERINFO'}">
				<h3>실시간 유저 정보</h3>
				<c:set var="URL" value="${rootPath}/crawl/lol/userinfo"/>
				<form method="POST" action="${URL}/save">
					<button class="save_crawling" type="submit">크롤링 DB 저장</button>
				</form>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
			
			<c:when test="${BODY == 'TIP'}">
				<h3>팁과 노하우</h3>
				<c:set var="URL" value="${rootPath}/crawl/lol/tip"/>
				<form method="POST" action="${URL}/save">
					<button class="save_crawling" type="submit">크롤링 DB 저장</button>
				</form>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
			
			<c:when test="${BODY == 'FREEBOARD'}">
				<h3>자유게시판</h3>
				<c:set var="URL" value="${rootPath}/crawl/lol/freeboard"/>
				<form method="POST" action="${URL}/save">
					<button class="save_crawling" type="submit">크롤링 DB 저장</button>
				</form>
				<%@ include file="/WEB-INF/views/list_body.jsp" %>
			</c:when>
		</c:choose>
	</section>
</body>
</html>