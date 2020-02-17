<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		
		// tr tag가 클릭되면 id값을 가져와서 update method로 전달하기
		$(".dept-tr").on("click", function() {
			let id = $(this).data("id")
			
			document.location.href = "${rootPath}/admin/dept/update/" + id
		})
	})
</script>
<table class="col-md-4 col-12">
	<tr>
		<th>거래처</th>
		<th>대표자</th>
		<th>대표전화</th>
		<th>주소</th>
		<th>담당자</th>
		<th>담당자 전화</th>
		<th>비고</th>
	</tr>
	<c:choose>
		<c:when test="${empty DEPT_LIST}">
			<tr>
				<td colspan="7">거래처 정보가 없습니다</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="DEPT" items="${DEPT_LIST}" varStatus="i">
				<tr class="dept-tr" data-id="${DEPT.id}">
					<td>${DEPT.d_name}</td>
					<td>${DEPT.d_ceo}</td>
					<td>${DEPT.d_tel}</td>
					<td>${DEPT.d_addr}</td>
					<td>${DEPT.d_manager}</td>
					<td>${DEPT.d_mtel}</td>
					<td>${DEPT.d_rem}</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
	<div>
		<a href="/list?pageno=1&search=${search}&text=${text}">1</a>
		<a href="/list?pageno=2&search=${search}&text=${text}">2</a>
		<a href="/list?pageno=3&search=${search}&text=${text}">3</a>
		<a href="/list?pageno=4&search=${search}&text=${text}">4</a>
		<a href="/list?pageno=5&search=${search}&text=${text}">5</a>
	</div>
</table>