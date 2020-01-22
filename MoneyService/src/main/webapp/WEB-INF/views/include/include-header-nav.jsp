<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	body {
		margin: 0 auto;
		font-family: "맑은 고딕";
	}
	
	header {
		background-color: rgba(0, 255, 0, 0.5);
		text-align: center;
		padding: 10px;
	}
	
	h2, h3, p {
		width: 80%;
		text-align: center;
		margin: 5px auto;
	}
	
	p {
		font-size: 16px;
	}

	nav {
		background-color: rgb(118, 118, 118);
	}
	
	nav ul {
		list-style: none;
		display: flex;
		margin: 0;
		padding: 0;
		justify-content: center;
	}

	nav ul li a {
		display: block;
		color: white;
		padding: 14px 50px;
		margin: 0px 2px;
		text-decoration: none;
		background-color: rgb(100, 100, 100);
	}

	nav ul li a:hover {
		background-color: #ddd;
		color: black;
		font-weight: bold;
		cursor: pointer;
	}
</style>
<header>
	<h1 id="title">국가지원금 안내</h1>
</header>
<nav>
	<ul>
		<li><a href="${rootPath}/search">검색하기</a></li>
		<li><a href="${rootPath}/link">조회결과</a></li>
	</ul>
</nav>