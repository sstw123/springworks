<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	jQuery(document).ready(function() {
		$("tr.bok-body").click(function() {
			let servId = $(this).attr("data-id")
			document.location.href = "${rootPath}/detail?id=" + servId
		})
	})
</script>
<style>
	table.bok-list {
		border-collapse: collapse;
		border-spacing: 0;
		width: 95%;
		margin: 10px auto;
		border: 1px solid green;
		
		table-layout: fixed; /* width 강제적용 */
	}
	
	table.bok-list tr, table.bok-list th {
		border: 1px solid #ddd;
	}
	
	table.bok-list th {
		text-align: left;
	}
	
	table.bok-list th, table.bok-list td {
		padding: 8px;
		vertical-align: top;
		white-space: nowrap;
	}
	
	table.bok-list td {
	
		/*
		col의 width 값보다 내용이 커지면 잘라내고 ...을 표시하기
		1. white-space: nowrap이 설정되어 있어야 한다
		2. 아래의 3개는 하나의 묶음처럼 사용
		*/
		overflow: hidden;
		text-overflow: ellipsis;
		border: none;
	}
	
	table.bok-list tr {
		background-color: gray;
	}
	
	tr.bok-body:nth-child(even) {
		background-color: #ccc;
	}
	
	tr.bok-body:nth-child(odd) {
		background-color: #fff;
	}
	
	tr.bok-body:hover {
		background-color: #ddd;
		cursor: pointer;
	}
</style>
<body>
	<table class="bok-list">
		<colgroup>
			<col width="120px">
			<col width="120px">
			<col width="100px">
			<col width="120px">
			<col width="80x">
			<col width="300px">
		</colgroup>
		<tr>
			<th>서비스등록일</th>
			<th>서비스명</th>
			<th>소관부처명</th>
			<th>소관조직명</th>
			<th>조회수</th>
			<th>서비스 요약</th>
		</tr>
		<c:choose>
			<c:when test="${empty BOK_LIST}">
				<tr>
					<td colspan="6">조회된 데이터가 없습니다</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${BOK_LIST}" var="bok" varStatus="status">
					<tr class="bok-body" data-id="${bok.servId}">
						<td>${bok.svcfrstRegTs}</td>
						<td>${bok.servNm}</td>
						<td>${bok.jurMnofNm}</td>
						<td>${bok.jurOrgNm}</td>
						<td>${bok.inqNum}</td>
						<td>${bok.servDgst}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</body>