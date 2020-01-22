<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" type="text/css" href="${rootPath}/css/list-table.css?ver=2019-11-31-001">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	jQuery(document).ready(function() {
		$(".content-body").click(function() {
			// this는 .content-body 클래스를 가진 태그이므로 tr
			// children은 그 자손들
			let mTd = $(this).children();
			
			let no = mTd.eq(0).text()
			let p_code = mTd.eq(1).text()
			let p_name = mTd.eq(2).text()
			let p_iprice = mTd.eq(3).text()
			let p_oprice = mTd.eq(4).text()
			let p_vat= mTd.eq(5).text()
			
			/*
			alert(no + "\n"
					+ p_code + "\n"
					+ p_name + "\n"
					+ p_iprice + "\n"
					+ p_oprice + "\n"
					+ p_vat)
			*/
			
			$("#pcode", opener.document).val(p_code)
			$("#pname", opener.document).text(p_name)
			
			// let io_inout = $("#io_inout", opener.document).val()
			let io_inout = $(opener.document).find("#inout").val()
			
			if(io_inout == "1") {
				$("#price", opener.document).val(p_iprice)
			} else {
				$("#price", opener.document).val(p_oprice)
			}
			
			// 수량 * 단가 = 합계 계산하여 input box에 자동으로 넣기
			let price = $("#price", opener.document).val()
			let qty = $("#qty", opener.document).val()
			
			$("#total", opener.document).val(
				parseInt(price) * parseInt(qty)
			)
			
			window.close()
			// IE에서 필요한 코드. 창을 닫을까요 물어보지 않고 바로 닫기
			window.open("about:black", "self").self.close()
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
			<th>No.</th>
			<th>상품코드</th>
			<th>상품명</th>
			<th>매입가격</th>
			<th>매출가격</th>
			<th>부가세</th>
		</tr>
		
		<c:choose>
			<c:when test="${empty PD_LIST}">
				<tr><td colspan="5">데이터가 없습니다</td></tr>
			</c:when>
			<c:otherwise>
				<c:forEach items="${PD_LIST}" var="dto" varStatus="status">
					<tr class="content-body" id="${dto.p_code}">
						<td>${status.count}</td>
						<td>${dto.p_code}</td>
						<td>${dto.p_name}</td>
						<td>${dto.p_iprice}</td>
						<td>${dto.p_oprice}</td>
						<td>${dto.p_vat}</td>
					</tr>
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</table>
</article>