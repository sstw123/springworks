<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
</head>
<body class="container-xl">
	<%@ include file="/WEB-INF/views/include/header_nav.jspf" %>
	<section>
		<c:choose>
			<c:when test="${BODY == 'LIST'}">
				<%@ include file="/WEB-INF/views/users/user_product_list.jsp" %>
			</c:when>
			<c:when test="${BODY == 'DETAIL'}">
				<%@ include file="/WEB-INF/views/users/user_product_detail.jsp" %>
			</c:when>
			<c:when test="${BODY == 'CART'}">
				<%@ include file="/WEB-INF/views/users/user_product_cart.jsp" %>
			</c:when>
		</c:choose>
	</section>
</body>
</html>