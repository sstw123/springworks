<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
<style type="text/css">
	header {
		background-color: rgba(0, 255, 0, 0.7);
		padding: 1rem;
		color: white;
	}
	
	nav {
		display: flex;
		background-color: rgba(0, 255, 0, 0.7);
		padding:14px 16px;
	}
	
	nav button {
		background-color: rgba(0, 0, 255, 1);
		padding: 14px 16px;
		color: white;
		outline: 0;
	}
	
	section.main-container {
		display: flex;
		flex-wrap: wrap;
	}
	
	div.d-box {
		width: 300px;
		margin: 20px;
		border: 1px solid red;
		border-radius: 10px;
		padding: 10px 16px;
	}
	
	div.d-box:hover {
		background-color: #ddd;
	}
	
	b {
		color: red;
	}
	
	a.title {
		color: inherit;
		text-decoration: none;
	}
	
	p.title {
		font-size: 20px;
		font-weight: bold;
	}
	
	.eng {
		white-space: nowrap;
		overflow: hidden;
		text-overflow: ellipsis;
	}
	
	input,select {
		padding: 8px;
		margin: 5px;
	}
	
</style>
</head>
<body>
	<header>
		<h3>네이버 검색하기</h3>
	</header>
	<nav>
		<form action="${rootPath}/search">
			<select name="cate">
				<option value="news">뉴스</option>
				<option value="book">도서</option>
				<option value="movie">영화</option>
			</select>
			
			<input name="search" placeholder="검색어 입력">
			<button>검색</button>
		</form>
	</nav>
	<section class="main-container">
		<c:forEach items="${NAVER_ITEMS}" var="item">
			<a href="${item.link}" target="_new" class="title">
				<div class="d-box">
					<p class="title">title : ${item.title}</p>
					<c:if test="${item.image != null}">
						<img src="${item.image}">
					</c:if>
					<p class="eng">originallink : ${item.originallink}</p>
					<p class="eng">link : ${item.link}</p>
					<p>description : ${item.description}</p>
					<p>pubDate : ${item.pubDate}</p>
				</div>
			</a>
		</c:forEach>
	</section>
	<section>
		<%@ include file="/WEB-INF/views/pagination.jsp" %>
	</section>
</body>
</html>