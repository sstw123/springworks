<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<link rel="stylesheet" type="text/css" href="${rootPath}/css/pagenation.css?ver=5">
</head>
<body>
	<article class="page-box">
		<ul class="page-body">
			<li class="page-item"><a href="${rootPath}/search?cate=${cate}&search=${search}&currentPageNo=${PAGE.firstPageNo}" class="page-link">[처음]</a></li>
			<li class="page-item"><a href="${rootPath}/search?cate=${cate}&search=${search}&currentPageNo=${PAGE.prePageNo}" class="page-link">&lt;</a></li>
			
			<c:forEach begin="${PAGE.startPageNo}" end="${PAGE.endPageNo}" var="page">
				<li class="page-item <c:if test="${page == PAGE.currentPageNo}">active</c:if>" >
					<a href="${rootPath}/search?cate=${cate}&search=${search}&currentPageNo=${page}" class="page-link">${page}</a>
				</li>
			</c:forEach>
			
			<li class="page-item"><a href="${rootPath}/search?cate=${cate}&search=${search}&currentPageNo=${PAGE.nextPageNo}" class="page-link">&gt;</a></li>
			<li class="page-item"><a href="${rootPath}/search?cate=${cate}&search=${search}&currentPageNo=${PAGE.lastPageNo}" class="page-link">[끝]</a></li>
		</ul>
	</article>
</body>
</html>