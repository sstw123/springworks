<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<script>
	$(function() {
		$("tr[data-id]").on("click", function() {
			document.location.href = "${rootPath}/bbs/view?bbs_id=" + $(this).attr("data-id")
		})
	})
</script>
<article class="container">
	<table class="table table-striped table-hover">
		<tr>
			<th>No</th>
			<th>작성자</th>
			<th>작성일자</th>
			<th>작성시간</th>
			<th>제목</th>
		</tr>
		
		<c:choose>
			<c:when test="${empty BBS_LIST}">
				<tr>
					<td colspan="5">데이터가 없습니다</td>
				</tr>
			</c:when>
			<c:otherwise>
				<c:forEach var="BBS" items="${BBS_LIST}" varStatus="status">
					<tr data-id="${BBS.bbs_id}">
						<td>${status.count}</td>
						<td>${BBS.bbs_writer}</td>
						<td>${BBS.bbs_date}</td>
						<td>${BBS.bbs_time}</td>
						<td>${BBS.bbs_subject}</td>
					</tr>
					<c:if test="${not empty BBS.bbs_reply}">
						<c:set var="BBS_SUB" value="${BBS.bbs_reply}"/>
						<%@ include file="/WEB-INF/views/include/bbs_reply.jsp" %>
					</c:if>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</article>
<div class="input-group">
	<div class="input-group-btn">
		<button class="btn btn-primary" id="btn-write" type="button">작성</button>
	</div>
</div>