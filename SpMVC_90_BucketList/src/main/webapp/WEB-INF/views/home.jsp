<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>My Bucket List</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<link href="https://fonts.googleapis.com/css?family=Kalam:400,700|Noto+Sans+KR:400,500,700&display=swap" rel="stylesheet">
	<link href="${rootPath}/resources/css/home.css" rel="stylesheet">
	<script src="${rootPath}/resources/js/home.js"></script>
	<script>
		var rootPath = "${rootPath}"
	</script>
</head>
<body>
	<header>
		<h1>My Bucket List</h1>
	</header>
	
	<nav>
		<ul class="tab">
			<li class="tab_active" data-tab="tab_all">All</li>
			<li data-tab="tab_incomplete">Incomplete</li>
			<li data-tab="tab_complete">Complete</li>
		</ul>
	</nav>
	
	<section>
		<%@ include file="/WEB-INF/views/include/bucket_page.jsp" %>
	</section>
	
	<footer>
	</footer>
</body>
</html>