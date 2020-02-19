<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link href="${rootPath}/resources/css/user_product_list.css" rel="stylesheet">
<script>
	$(function() {
		$("tr.pro_tr").on("click", function() {
			document.location.href = "${rootPath}/user/product/detail?id=" + $(this).data("id")
		})
	})
</script>
<table>
	<tr>
		<th>상품코드</th>
		<th>상품명</th>
		<th>가격</th>
	</tr>
	<c:choose>
		<c:when test="${empty PRODUCT_LIST}">
			<tr>
				<td colspan="3">상품 정보가 없습니다</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="PRO" items="${PRODUCT_LIST}" varStatus="i">
				<tr class="pro_tr" data-id="${PRO.id}">
					<td>${PRO.p_code}</td>
					<td>${PRO.p_name}</td>
					<td>${PRO.p_oprice}</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>