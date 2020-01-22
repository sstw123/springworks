<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>JSP 페이지</title>
	<link rel="stylesheet" type="text/css" href="/todo/css/home.css">
</head>
<body>
	<header>
		<h3>To Do List</h3>
	</header>
	<section>
		<article id="input">
			<form method="POST">
				<table class="input">
					<tr>
						<td><label for="tdFlag">중요도</label> <input id="tdFlag" type="number" value="1" min="1" max="5" name="tdFlag"></td>
						<td><label for="tdSub">제목</label> <input id="tdSub" type="text" name="tdSub"></td>
						<td><input id="tdAlarm" type="checkbox" name="tdAlarm" value="Y"><label for="tdAlarm">알람설정</label></td>
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
								<td>${dto.tdFlag}</td>
								<td <c:if test="${dto.tdComplete == 'Y'}">class="line-through"</c:if>>${dto.tdSub}</td>
								<td onclick="location.href='${rootPath}/complete?tdSeq=${dto.tdSeq}'" style="cursor: pointer">${dto.tdComplete}</td>
								<td onclick="location.href='${rootPath}/alarm?tdSeq=${dto.tdSeq}'" style="cursor: pointer">${dto.tdAlarm}</td>
								<td onclick="location.href='${rootPath}/update?tdSeq=${dto.tdSeq}'" style="cursor: pointer">수정</td>
								<td onclick="location.href='${rootPath}/delete?tdSeq=${dto.tdSeq}'" style="cursor: pointer">삭제</td>
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
</body>
</html>