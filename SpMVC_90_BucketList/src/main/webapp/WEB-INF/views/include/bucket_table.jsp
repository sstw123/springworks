<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${rootPath}/resources/css/bucket_table.css"/>

<table>
	<thead>
		<tr>
			<th>번호</th>
			<th>내용</th>
			<th>성공</th>
		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${EMPTY_LIST eq true}">
				<tr>
					<td class="center" colSpan="3">버킷리스트를 추가하세요!</td>
				</tr>
			</c:when>
			<c:when test="${!empty BUCKET_LIST}">
				<c:forEach var="bucket" items="${BUCKET_LIST}" varStatus="status">
					<tr data-id="${bucket.b_id}">
						<td class="center">${bucket.b_order}</td>
						<td class="b_content">${bucket.b_content}</td>
						<c:choose>
							<c:when test="${bucket.b_success == true}">
								<td class="success center">&#10003;</td>
							</c:when>
							<c:when test="${bucket.b_success == false}">
								<td class="success center"></td>
							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</tbody>
</table>