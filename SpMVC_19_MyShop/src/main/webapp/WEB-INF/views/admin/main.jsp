<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		.in-errors {
			color: red;
			font-size: 8px;
		}
		/*
			col-md-7 col-12
			해상도가 768 이상이면 7칸
			그 미만이면 12칸 = 최대 width
		*/
		tr, th, td {
			white-space: nowrap;
		}
		
		.list-body {
			overflow: auto;
		}
		
		td.p_name {
			display:inline-block;
			width: 150px;
			padding: 0 5px;
			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}
	</style>
</head>
<body class="container-xl">
	<%@ include file="/WEB-INF/views/include/header_nav.jspf" %>
	<section>
		<c:choose>
			<c:when test="${BODY == 'PRODUCT'}">
				<%@ include file="/WEB-INF/views/admin/product.jsp" %>
			</c:when>
			<c:when test="${BODY == 'DEPT'}">
				<%@ include file="/WEB-INF/views/admin/dept.jsp" %>
			</c:when>
			<c:otherwise>
				<h3>장바구니에 담겨있는 상품 : ${COUNT_CART}</h3>
				<h3>배송중인 상품 : ${COUNT_DELIVERY}</h3>
			</c:otherwise>
		</c:choose>
	</section>
</body>
</html>