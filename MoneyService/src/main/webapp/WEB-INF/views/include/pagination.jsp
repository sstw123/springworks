<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<article class="page-box">
	<ul class="page-body">
		<c:if test="${pagiDTO.startPageNo > 1}">
			<li class="page-item"><a class="page-link" href="${rootPath}/link?currPage=1">처음</a></li>
			<li class="page-item"><a class="page-link">&middot;&middot;&middot;</a></li>
		</c:if>
		
		<c:forEach begin="${pagiDTO.startPageNo}" end="${pagiDTO.endPageNo}" var="pageNo">
			<li class="page-item <c:if test='${pageNo == pagiDTO.currentPageNo}'>active</c:if>"><a class="page-link" href="${rootPath}/link?currPage=${pageNo}">${pageNo}</a></li>
		</c:forEach>
		
		<c:if test="${pagiDTO.endPageNo != pagiDTO.lastPageNo}">
			<li class="page-item"><a class="page-link">&middot;&middot;&middot;</a></li>
			<li class="page-item"><a class="page-link" href="${rootPath}/link?currPage=${pagiDTO.lastPageNo}">끝</a></li>
		</c:if>
	</ul>
</article>