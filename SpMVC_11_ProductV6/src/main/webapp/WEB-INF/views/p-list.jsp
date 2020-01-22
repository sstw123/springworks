<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<body>
	<form id="search-form">
		<input type="search" 
			class="form-control" 
			id="search-input"
			name="search"
			value="${search}"
			placeholder="상품이름을 입력한 후 Enter...">
	</form>
	<hr/>
	<table class="p-list table table-striped table-hover">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품명</th>
				<th>매입단가</th>
				<th>매출단가</th>
				<th>대표이미지</th>
				<th>보조이미지</th>
			</tr>
		</thead>
		
		<c:forEach items="${PLIST}" var="dto">
			<tr class="p-row" data-id="${dto.p_code}">
				<td>${dto.p_code}</td>
				<td>${dto.p_name}</td>
				<td>${dto.p_iprice}</td>
				<td>${dto.p_oprice}</td>
				<td><c:if test="${!empty dto.p_file}">O</c:if></td>
				<td><c:if test="${!empty dto.p_files}">O ( DB 테이블에 저장된 파일명: ${fn:length(dto.p_files)}개 )</c:if></td>
			</tr>
		</c:forEach>
		
	</table>
	<hr/>
	<section>
		<%@ include file="/WEB-INF/views/pagination.jsp" %>
	</section>
</body>
</html>