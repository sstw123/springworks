<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		$("tr.p-row").click(function() {
			let p_code = $(this).attr("data-id")
			
			$.ajax({
				// "getProduct?p_code" + p_code
				url : "${rootPath}/getProduct",
				data : {p_code : p_code},
				dataType : 'json',
				success : function(result) {
					$("#p_code").val(result.p_code)
					$("#p_name").val(result.p_name)
					$("#p_iprice").val(result.p_iprice)
					$("#p_oprice").val(result.p_oprice)
				},
				error : function(error) {
					alert("서버 통신 오류")
				}
				
			})
			// document.location.href = "${rootPath}/getProduct?p_code=" + p_code
		})
	})
</script>
<body>
	<table border="1" class="p-list">
		<tr>
			<th>상품코드</th>
			<th>상품명</th>
			<th>매입단가</th>
			<th>매출단가</th>
		</tr>
		
		<c:forEach items="${PLIST}" var="dto">
			<tr class="p-row" data-id="${dto.p_code}">
				<td>${dto.p_code}</td>
				<td>${dto.p_name}</td>
				<td>${dto.p_iprice}</td>
				<td>${dto.p_oprice}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>