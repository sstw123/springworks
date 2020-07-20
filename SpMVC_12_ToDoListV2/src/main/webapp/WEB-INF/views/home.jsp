<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
	<link rel="stylesheet" type="text/css" href="/todo/css/home.css?ver=1">
	<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
	<script>
		$(function() {
			$("#login").on("click", function() {
				$("#myModal").css("display", "block")
			})
		})
	</script>
</head>
<body>
	<header>
		<h3>To Do List</h3>
	</header>
	<nav>
		<ul>
			<li id="login">login</li>
		</ul>
	</nav>
	<section>
		<article id="input">
			<form method="POST">
				<input type="hidden" name="td_seq" value='<c:out value="${todoDTO.td_seq}" default="0" />'>
				<input type="hidden" name="td_complete" value="${todoDTO.td_complete}">
				<input type="hidden" name="td_date" value="${todoDTO.td_date}">
				<input type="hidden" name="td_time" value="${todoDTO.td_time}">
				<table class="input">
					<tr>
						<td><label for="td_flag">중요도</label> <input id="td_flag" type="number" value='<c:out value="${todoDTO.td_flag}" default="1"/>' min="1" max="5" name="td_flag"></td>
						<td><label for="td_sub">제목</label> <input id="td_sub" type="text" <c:out value='${todoDTO.td_sub}'/> name="td_sub"></td>
						<td><input id="td_alarm" type="checkbox" name="td_alarm" <c:if test="${todoDTO.td_alarm == 'Y'}">checked= "checked"</c:if> value="Y"><label for="td_alarm">알람설정</label></td>
						<td><button>저장</button></td>
					</tr>
				</table>
			</form>
		</article>
		
		<article id="list">
			<table class="list">
				<tr>
					<th>No.</th>
					<th>Flag</th>
					<th>제목</th>
					<th>달성</th>
					<th>알람</th>
					<th>수정</th>
					<th>삭제</th>
				</tr>
				<c:choose>
					<c:when test="${!empty toDoList}">
						<c:forEach items="${toDoList}" var="dto" varStatus="status">
							<tr class="todoList">
								<td>${status.count}</td>
								<td>${dto.td_flag}</td>
								<td <c:if test="${dto.td_complete == 'Y'}">class="line-through"</c:if>>${dto.td_sub}</td>
								<td onclick="location.href='${rootPath}/complete?td_seq=${dto.td_seq}'" style="cursor: pointer">${dto.td_complete}</td>
								<td onclick="location.href='${rootPath}/alarm?td_seq=${dto.td_seq}'" style="cursor: pointer">${dto.td_alarm}</td>
								<td onclick="location.href='${rootPath}/update?td_seq=${dto.td_seq}'" style="cursor: pointer">수정</td>
								<td onclick="location.href='${rootPath}/delete?td_seq=${dto.td_seq}'" style="cursor: pointer">삭제</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr>
							<td colspan="6">데이터가 없습니다</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</article>
	</section>
	<%@ include file="/WEB-INF/views/modal-box.jsp" %>
</body>
</html>