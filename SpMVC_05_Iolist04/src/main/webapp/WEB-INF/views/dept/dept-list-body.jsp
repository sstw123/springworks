<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${rootPath}/css/list-table.css?ver=2019-11-31-001">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(function() {
		$(".content-body").click(function() {
			// td 들의 목록 추출하기
			let td = $(this).children()
			
			let no = td.eq(0).text()
			let d_code = td.eq(1).text()
			let d_name = td.eq(2).text()
			let d_ceo = td.eq(3).text()
			let d_tel = td.eq(4).text()
			let d_addr = td.eq(5).text()
			
			
			// 나(새창)를 열어준 view의 요소에 값을 write
			$(opener.document).find("#dcode").val(d_code)
			$(opener.document).find("#dname").text(
					d_name + "(" + d_ceo + ")"
			)
			
			// 클릭 후 현재 열린 검색창 닫기
			window.close()
			
			// IE를 위한 코드
			window.open("about:blank","_self").self.close()
			
		})
	})
</script>
<style>
	div.s-box {
		width: 95%;
		margin: 0 auto;
	}
	
	div.s-box input {
		padding: 8px;
		width: 100%;
	}
</style>
<article>
	<div class="s-box">
		<form>
			<input name="text">
		</form>
	</div>
	<table>
		<tr>
			<th>No</th>
			<th>거래처코드</th>
			<th>거래처명</th>
			<th>대표</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>
		<c:choose>
			<c:when test="${empty DEPT_LIST}">
				<tr><td colspan="5">데이터가 없음</td></tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${DEPT_LIST}" var="dto" varStatus="info">
					<tr class="content-body" id="${dto.d_code}">
						<td>${info.count}</td>
						<td>${dto.d_code}</td>
						<td>${dto.d_name}</td>
						<td>${dto.d_ceo}</td>
						<td>${dto.d_tel}</td>
						<td>${dto.d_addr}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</article>