<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>var rootPath = "${rootPath}"</script>
	
	<script>
		$(function() {
			$("#btn_write").on("click", function() {
				document.location.href = rootPath + "/rbook/insert"
			})
		})
	</script>
</head>
<body>
	<header>
		<h2>My Read Book</h2>
	</header>
	
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<section id="main_list">
		<table id="main_table">
			<thead>
				<tr>
					<th>도서코드</th>
					<th>도서이름</th>
					<th>출판사</th>
					<th>저자</th>
					<th>구입일자</th>
					<th>가격</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${BLIST}" var="BOOK" varStatus="status">
					<tr>
						<td>${BOOK.b_code}</td>
						<td>${BOOK.b_name}</td>
						<td>${BOOK.b_comp}</td>
						<td>${BOOK.b_auther}</td>
						<td>${BOOK.b_year}</td>
						<td>${BOOK.b_iprice}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</section>
	
	<section>
		<div id="main_button">
			<button id="btn_write" class="biz_blue flex_right" type="button">독서록 작성</button>
		</div>
	</section>
</body>
</html>