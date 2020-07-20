<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>국가지원금 안내</title>
	<link rel="stylesheet" type="text/css" href="${rootPath}/css/include-link.css">
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header-nav.jsp" %>
	<c:choose>
		<c:when test="${empty SERV_LIST}">
			<p>조회된 데이터가 없습니다</p>
		</c:when>
		<c:otherwise>
			<c:forEach items="${SERV_LIST}" var="s">
				<div class="serv-link" onclick="window.open('${s.servDtlLink}')">
					<p>${s.servNm}</p>
					<p>조회수:${s.inqNum} 관련부서:${s.jurMnofNm} ${s.jurOrgNm} 서비스등록일:${s.svcfrstRegTs}</p>
					<p>${s.servDgst}</p>
				</div>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<hr/>
	<section>
		<%@ include file="/WEB-INF/views/include/pagination.jsp" %>
	</section>
</body>
</html>