<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	
	<script>var rootPath = "${rootPath}"</script>
	
	<script>
		$(function() {
			$("#btn_write").on("click", function() {
				document.location.href = rootPath + "/rbook/insert"
			})
			
			$("#main_table tbody tr").on("click", function() {
				let rb_seq = $(this).attr("data-id")
				document.location.href = "${rootPath}/rbook/view/" + rb_seq
			})
		})
	</script>
</head>
<body>
	<section id="main_list">
		<table id="main_table">
			<thead>
				<tr>
					<th>사용자ID</th>
					<th>도서코드</th>
					<th>도서이름</th>
					<th>독서일자</th>
					<th>독서시간</th>
					<th>한줄평</th>
					<th>별점</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${RBOOKS}" var="rBook" varStatus="status">
					<tr data-id="${rBook.rb_seq}">
						<td>사용자ID${status.count}</td>
						<td>${rBook.rb_bcode}</td>
						<td>${rBook.b_name}</td>
						<td>${rBook.rb_date}</td>
						<td>${rBook.rb_stime}</td>
						<td>${rBook.rb_subject}</td>
						<td>${rBook.rb_star}</td>
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