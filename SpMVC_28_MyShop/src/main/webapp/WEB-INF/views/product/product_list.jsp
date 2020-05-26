<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		
	</style>
	<script>
		$(function() {
			
			$(".p_list").click(function() {
				let p_code = $(this).data("pcode")
				document.location.href = "${rootPath}/product/detail/" + p_code
			})
			
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<section class="container-fluid body">
		<h3>상품 리스트</h3>
		<table class="table table-all">
			<thead>
				<tr>
					<th>상품코드</th>
					<th>상품명</th>
					<th>매입가격</th>
					<th>판매가격</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty proList}">
						<tr>
							<td colspan="4">상품정보가 없습니다</td>
						</tr>
					</c:when>
					<c:when test="${!empty proList}">
						<c:forEach items="${proList}" var="P" varStatus="s">
							<tr class="p_list" data-pcode="${P.p_code}">
								<td>${P.p_code}</td>
								<td>${P.p_name}</td>
								<td>${P.p_iprice}</td>
								<td>${P.p_oprice}</td>
							</tr>
						</c:forEach>
					</c:when>
				</c:choose>
			</tbody>
		</table>
		
		<div class="btn_box text-right">
			<button onclick="document.location.href='${rootPath}/product/save'" class="btn btn-primary">등록</button>
		</div>
	</section>
</body>
</html>