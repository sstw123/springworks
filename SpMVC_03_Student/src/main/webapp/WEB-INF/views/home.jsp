<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>Home</title>
	<style>
	header {
		background: #0872F5;
		margin: 0;
		padding: 1rem;
		color: white;
	}
	
	header h3 {
		font-size: 2rem;
	}
	
	ol {
		list-style: none;
		margin: 0;
		padding: 0;
		display: flex;
		background-color: #fff000;
	}
	
	ol a {
		display: block;
		text-decoration: none;
		padding: 14px 16px;
		
	}
	
	ol a:hover{
		background-color: #dddfff;
		color: blue;
		border-bottom: 3px solid blue;
	}
	</style>
</head>
<body>
<header>
	<h3>학생정보 관리</h3>
</header>
rootPath : ${rootPath}
<!-- ol : Ordered List 약자(기본값:숫자) -->
<!-- ul : Unordered List 약자(기본값:전부 같은 기호) -->
<ol>
	<!-- li : List Item의 약자 -->
	<li><a href="${rootPath}/list">학생리스트</a></li>
	<li><a href="${rootPath}/search">학생검색</a></li>
	<li><a href="#">로그인</a></li>
	<li><a href="#">회원가입</a></li>
</ol>

</body>
</html>
