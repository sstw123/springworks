<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<%@ include file="/WEB-INF/views/include/include-product-css.jspf" %>
	<style>
		.in-box {
			display: inline-block;
			width: 70%;
		}
		.in-box > input {
			padding: 8px;
			margin: 3px;
			display: inline-block;
			width: 70%;
			border: 1px solid #ccc;
		}
		.in-box > input:hover {
			background-color: #ddd;
			border: 1px solid blue;
		}
		.in-label {
			display: inline-block;
			width: 20%;
			
			text-align: right;
			margin-right: 5px;
			padding: 8px;
		}
		fieldset {
			width: 70%;
			margin: 20px auto;
			border-radius: 5px;	
		}
		
		legend {
			font-size: 12pt;
			font-weight: bold;
			color: #3d65ff;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-product-header.jspf"%>
	<form method="POST">
		<fieldset>
			<legend>상품정보 입력</legend>
			
			<label for="pdcode" class="in-label">상품코드</label>
			<div class="in-box">
				<input name="p_code" id="pdcode" value="${PD_DTO.p_code}" readonly="readonly">
			</div>
			
			<label for="pdname" class="in-label">상품명</label>
			<div class="in-box">
				<input name="p_name" id="pdname" value="${PD_DTO.p_name}">
			</div>
			
			<label for="pdiprice" class="in-label">매입가격</label>
			<div class="in-box">
				<input name="p_iprice" id="pdiprice" value="${PD_DTO.p_iprice}">
			</div>
			
			<label for="pdoprice" class="in-label">매출가격</label>
			<div class="in-box">
				<input name="p_oprice" id="pdoprice" value="${PD_DTO.p_oprice}">
			</div>
			
			<label for="pdvat" class="in-label">부가세</label>
			<div class="in-box">
				<input name="p_vat" id="pdvat" value="${PD_DTO.p_vat}">
			</div>
			
			<div class="in-box">
				<button id="btn-save">저장</button>
			</div>
			
		</fieldset>
	</form>
</body>
</html>