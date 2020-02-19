<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/views/include/context_menu.jsp" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		
		$(".pro-tr").on("click", function() {
			let id = $(this).data("id")
			
			//document.location.href = "${rootPath}/admin/product/update?id=" + id
			document.location.href = "${rootPath}/admin/product/update/" + id
		})
		
		//$.contextMenu("html5")
		var pro_call_func = function(key) {
			var id = $(this).data("id")
			if(key == "edit") {
				document.location.href = "${rootPath}/admin/product/update/" + id
			} else if (key == "delete") {
				if(confirm("정말 삭제하시겠습니까?")) {
					document.location.href = "${rootPath}/admin/product/delete/" + id
				}
			}
		}
		
		$.contextMenu({
			selector : ".pro-tr",
			items : {
				"edit" : {name : "수정", icon : "edit"},
				"delete" : {name : "삭제", icon : "ddelete"}
			},
			callback : pro_call_func
		})
		
	})
</script>
<style>
tr, th, td {
	white-space: nowrap;
}
</style>
<table class="col-md-4 col-12">
	<tr>
		<th>상품코드</th>
		<th>상품명</th>
		<th>거래처</th>
		<th>품목</th>
		<th>매입가격</th>
		<th>판매가격</th>
	</tr>
	<c:choose>
		<c:when test="${empty PRO_LIST}">
			<tr>
				<td colspan="6">상품정보가 없습니다</td>
			</tr>
		</c:when>
		<c:otherwise>
			<c:forEach var="PRO" items="${PRO_LIST}" varStatus="i">
				<tr class="pro-tr" data-id="${PRO.id}">
					<td>${PRO.p_code}</td>
					<td class="p_name">${PRO.p_name}</td>
					<td>${PRO.p_dcode}</td>
					<td>${PRO.p_bcode}</td>
					<td>${PRO.p_iprice}</td>
					<td>${PRO.p_oprice}</td>
				</tr>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</table>