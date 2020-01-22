<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/list.css">

<script>
	$(function() {
		$("#send_mail").on("click", function() {
			document.location.href = "${rootPath}/ems/input"
		})
		
		$(".email_row").on("click", function() {
			document.location.href = "${rootPath}/ems/view/" + $(this).data("seq")
		})
	})
</script>

<table>
	<thead>
		<tr>
			<th>No.</th>
			<th>받는Email</th>
			<th>받는사람</th>
			<th>제목</th>
			<th>작성일자</th>
			<th>작성시각</th>
		</tr>
	</thead>
	
	<tbody>
		<c:choose>
			<c:when test="${empty LIST}">
				<td colspan="6">데이터가 없습니다</td>
			</c:when>
			<c:otherwise>
				<c:forEach items="${LIST}" var="VO" varStatus="status">
					<tr class="email_row" data-seq="${VO.emsSeq}">
						<td>${status.count}</td>
						<td>${VO.fromEmail}</td>
						<td>${VO.fromName}</td>
						<td>${VO.subject}</td>
						<td>${VO.sendDate}</td>
						<td>${VO.sendTime}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
<article class="mail_box">
	<button id="send_mail">메일 보내기</button>
</article>