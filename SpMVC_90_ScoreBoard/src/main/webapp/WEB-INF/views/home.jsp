<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>TEMPLATE</title>
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>
<body>
	<header class="jumbotron jumbotron-fluid text-center">
		<h2>성적관리</h2>
	</header>
	
	<c:if test="${BODY == 'SCORE'}">
		<section>
			<%@ include file="/WEB-INF/views/include/score.jsp" %>
		</section>
	</c:if>
	
	<c:if test="${BODY == 'SCORE_INFO'}">
		<section>
			<%@ include file="/WEB-INF/views/include/score_info.jsp" %>
		</section>
	</c:if>
	
	<c:if test="${BODY == 'SAVE'}">
		<section>
			<%@ include file="/WEB-INF/views/include/score_save.jsp" %>
		</section>
	</c:if>
	
</body>
</html>