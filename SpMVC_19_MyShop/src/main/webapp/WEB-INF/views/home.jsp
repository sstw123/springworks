<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function() {
			$("a.logout").on("click", function() {
				$("#logout").submit()
			})
		})
	</script>
	<style>
		.in-errors {
			color: red;
			font-size: 8px;
		}
		/*
			col-md-7 col-12
			해상도가 768 이상이면 7칸
			그 미만이면 12칸 = 최대 width
		*/
		tr, th, td {
			white-space: nowrap;
		}
		
		.list-body {
			overflow: auto;
		}
		
		td.p_name {
			display:inline-block;
			width: 150px;
			padding: 0 5px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
	</style>
</head>
<body>
	<header class="jumbotron">
		<h3>My Shop</h3>
	</header>
	
	<nav class="navbar navbar-expand-sm bg-light">
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="${rootPath}/">Home</a></li>
			<sec:authorize access="isAnonymous()">
				<li class="nav-item"><a class="nav-link" href="${rootPath}/auth/login">로그인</a></li>
			</sec:authorize>
			<sec:authorize access="isAuthenticated()">
				<form id="logout" method="POST" action="${rootPath}/logout">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
					<li class="nav-item"><a class="nav-link logout" href="#">로그아웃</a></li>
				</form>
			</sec:authorize>
			<sec:authorize access="hasRole('ADMIN')">
				<li class="nav-item"><a class="nav-link" href="${rootPath}/admin/">관리자</a></li>
			</sec:authorize>
		</ul>
	</nav>
	
	
</body>
</html>