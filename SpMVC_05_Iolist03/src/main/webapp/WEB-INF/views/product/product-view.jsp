<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<%@ include file="/WEB-INF/views/include/include-product-css.jspf" %>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<style>
		p#insert {
			margin-left: 40px;
		}
		
		th {
			background-color: #ccc;
			text-align: right;
		}
		td {
			background-color: #ddd;
		}
		
		div.btn-box {
			display: flex;
			justify-content: center;
			align-items: center;
			
			width: 100%;
			background-color: #fff;
			
			padding: 10px;
			margin: 0px;
		}
		
		button {
			font-size: 12pt;
			border-radius: 10px;
			outline: none;
			margin-right: 10px;
			padding: 10px 20px;
		}
		
		button:hover {
			background-color: #ddd;
			color: black;
			cursor: pointer;
		}
		
		.btn-update {
			background-color: blue;
			color: white;
		}
		
		.btn-delete{
			background-color: orange;
			color: white;
		}
	</style>
	<script>
	$(function(){
		
		$(".btn-update").click(function() {
			document.location.href = "${rootPath}/product/update?id=${PD_DTO.p_code}"
		})
		
		$(".btn-delete").click(function() {
			
			let msg = "상품코드 : ${PD_DTO.p_code}\n"
			+ "상품명 : ${PD_DTO.p_name}\n"
			+ "삭제할까요?"

			if( confirm(msg) ) {
				document.location.href = "${rootPath}/product/delete?id=${PD_DTO.p_code}"
			}
			
			alert("삭제되었습니다")
		})
	})
	</script>
	
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-product-header.jspf" %>
	<section>
		<table>
			<tr>
				<th>상품코드</th><td>${PD_DTO.p_code}</td>
				<th>상품명</th><td>${PD_DTO.p_name}</td>
			</tr>
			<tr>
				<th>매입가격</th><td>${PD_DTO.p_iprice}</td>
				<th>매출가격</th><td>${PD_DTO.p_oprice}</td>
			</tr>
			<tr>
				<th>부가세</th><td colspan="3">${PD_DTO.p_vat}</td>
			</tr>
			<tr>
				<td colspan="4">
					<div class="btn-box">
						<button class="btn-update">수정</button>
						<button class="btn-delete">삭제</button>
					</div>
				</td>
			</tr>
		</table>
	</section>
</body>
</html>