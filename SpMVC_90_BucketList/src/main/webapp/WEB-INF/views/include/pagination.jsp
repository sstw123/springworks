<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/pagination.css"/>
<script>
	$(function() {
		$("a.middot").on("click", function() {
			let href_page = prompt('이동할 페이지 (1~' + ${PAGE_DTO.lastPageNo} + ")")
			if(href_page != null) {
				document.location.href = "${URL}?currPage=" + href_page
			}
		})
	})
</script>
<c:if test="${not empty PAGE_DTO}">
	<article class="page_box">
		<ul class="page_body">
			<c:if test="${PAGE_DTO.startPageNo > 1}">
				<li class="page_item"><a class="page_link" href="${URL}?currPage=1">처음</a></li>
				<li class="page_item"><a class="page_link middot">&middot;&middot;&middot;</a></li>
			</c:if>
			
			<c:forEach begin="${PAGE_DTO.startPageNo}" end="${PAGE_DTO.endPageNo}" var="pageNo">
				<li class="page_item <c:if test='${pageNo == PAGE_DTO.currentPageNo}'>active</c:if>"><a class="page_link" href="${URL}?currPage=${pageNo}">${pageNo}</a></li>
			</c:forEach>
			
			<c:if test="${PAGE_DTO.endPageNo != PAGE_DTO.lastPageNo}">
				<li class="page_item"><a class="page_link middot">&middot;&middot;&middot;</a></li>
				<li class="page_item"><a class="page_link" href="${URL}?currPage=${pagiDTO.lastPageNo}">끝</a></li>
			</c:if>
		</ul>
	</article>
</c:if>