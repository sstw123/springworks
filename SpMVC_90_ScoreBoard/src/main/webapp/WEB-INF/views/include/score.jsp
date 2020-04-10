<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script>
	$(function() {
		$("tbody tr").click(function() {
			document.location.href = "${rootPath}/" + $(this).attr("data-id")
		})
		
		$("button").click(function() {
			document.location.href = "${rootPath}/save"
		})
	})
</script>

<table class="table table-hover">
	<thead>
		<tr>
			<th>학번</th>
			<th>이름</th>
			<th>학년</th>
			<th>반</th>
			<th>국어</th>
			<th>영어</th>
			<th>수학</th>
			<th>총점</th>
			<th>평균</th>
			<th>석차</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="SC" items="${SC_LIST}">
			<tr data-id="${SC.id}">
				<td>${SC.st_num}</td>
				<td>${SC.st_name}</td>
				<td>${SC.st_grade}</td>
				<td>${SC.st_class}</td>
				<td>${SC.s_kor}</td>
				<td>${SC.s_eng}<td>
				<td>${SC.s_math}<td>
				<td>${SC.s_sum}<td>
				<td>${SC.s_avg}<td>
				<td>${SC.s_rank}<td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<button class="btn btn-primary float-right" type="button">추가</button>