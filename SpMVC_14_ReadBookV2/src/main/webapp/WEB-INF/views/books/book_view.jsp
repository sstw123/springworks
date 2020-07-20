<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.js"></script>
	<script src="${rootPath}/js/summernote-ko-KR.js"></script>

	<script>var rootPath = "${rootPath}"</script>
	
	<script>
	
	</script>
	<style>
		article {
			display: flex;
			flex-flow: wrap;
		}
		
		.book_title, .book_body {
			display: inline-block;
			width: 24%;
			padding: 5px 16px;
			margin: 5px;
			background-color: #eee;
		}
		
		div.book_title {
			background-color: #ccc;
			text-align: right;
		}
		
	</style>
</head>
<body>
	<div class="book_title">도서코드</div>
	<div class="book_body">${BOOK.b_code}</div>
	
	<div class="book_title">도서명</div>
	<div class="book_body">${BOOK.b_name}</div>
	
	<div class="book_title">출판사</div>
	<div class="book_body">${BOOK.b_comp}</div>
	
	<div class="book_title">저자</div>
	<div class="book_body">${BOOK.b_auther}</div>
	
	<div class="book_title">구입연도</div>
	<div class="book_body">${BOOK.b_year}</div>
	
	<div class="book_title">구입가격</div>
	<div class="book_body">
		<fmt:formatNumber value="${BOOK.b_iprice}" var="commaPrice" type="number" maxFractionDigits="3" />
		${commaPrice}
		
		<fmt:formatNumber value="${BOOK.b_iprice}" var="currPrice" type="currency" maxFractionDigits="3" />
		${currPrice}
		
		<fmt:setLocale value="en_US" scope="session"/>
		<fmt:formatNumber value="${BOOK.b_iprice}" type="currency" maxFractionDigits="3" />
		
		<fmt:formatNumber value="0.1" type="percent" maxFractionDigits="3" />
	</div>	
</body>
</html>