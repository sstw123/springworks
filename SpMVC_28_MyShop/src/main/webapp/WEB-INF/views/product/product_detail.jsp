<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<c:set var="baseURL" value="${pageContext.request.requestURI}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		div.p_detail_box {
			display: flex;
		}
		div.p_detail_label, div.p_detail_data {
			padding: 10px;
			margin: 5px;
		}
		div.p_detail_label {
			flex: 1;
			text-align: right;
			background-color: lightgreen;
			font-weight: bold;
		}
		div.p_detail_data {
			flex: 3;
			background-color: lime;
		}
		
		.p_detail_white {
			flex: 1;
			text-align: right;
			font-weight: bold;
		}
	</style>
	<script>
		$(function() {
			$("button.size-add").click(function() {
				let size_standard = $("#m_size_list option:selected").val()
				let size_name= $("#m_size_list option:selected").text()
				
				// select box의 기본값이 선택되어 있으면 필요없는 스크립트 
				if(!size_standard) {
					alert("추가할 사이즈를 선택하세요.")
				}
				
				$.ajax({
					url: "${rootPath}/product/insert_size",
					type: "POST",
					data: {
						s_size: size_standard,
						p_code: "${PRODUCT_VO.p_code}"
					},
					beforeSend: function(ajx) {
						ajx.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
					}
				})
				.done(function(result) {
					if(result == "false") {
						alert("이미 등록된 사이즈입니다.")
					} else {
						$("#p_size_list").append(
							$("<option/>", {value:size_standard, text:size_name})
						)
					}
				})
			})
			
			$("button.size-delete").click(function() {
				$.ajax({
					url: "${rootPath}/product/delete_size",
					type: "POST",
					data: {p_code:"${PRODUCT_VO.p_code}", s_size:size_standard},
					beforeSend: function(ajx) {
						ajx.setRequestHeader("${_csrf.headerName}", "${_csrf.token}")
					},
				})
				.done(function(result) {
					$("#p_size_list option:selected").remove()
				})
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<section class="container-fluid body m-5">
		<article class="container p-5 bg-light">
			<div class="p_detail_box">
				<div class="p_detail_label">상품코드</div>
				<div class="p_detail_data">${PRODUCT_VO.p_code}</div>
			</div>
			<div class="p_detail_box">
				<div class="p_detail_label">상품명</div>
				<div class="p_detail_data">${PRODUCT_VO.p_name}</div>
			</div>
			<div class="p_detail_box">
				<div class="p_detail_label">판매가격</div>
				<div class="p_detail_data">${PRODUCT_VO.p_oprice}</div>
			</div>
			<div class="p_detail_box">
				<div class="p_detail_label">대표 이미지</div>
				<div class="p_detail_data"><img width="100" src="${rootPath}/upload/${PRODUCT_VO.p_file}"></div>
			</div>
			
			<div class="p_detail_box">
				<div class="p_detail_white">
					<button class="btn btn-success" onclick="document.location.href='${rootPath}/product' ">목록으로</button>
				</div>
			</div>
		</article>
		
		<article class="container p-5 bg-light">
			<form:form modelAttribute="productVO">
				<div class="p_detail_box">
					<div class="p_detail_white">
						<h5>사이즈 선택</h5>
						<form:select path="m_size_list" class="form-control" multiple='false'>
							<form:options items="${m_size_list}" itemLabel="o_name" itemValue="o_standard" />
						</form:select>
						<button type="button" class="btn btn-primary size-add">▼ 추가</button>
						<button type="button" class="btn btn-primary size-delete">▲ 삭제</button>
						
						<form:select path="p_size_list" class="form-control">
							<form:options items="${PRODUCT_VO.p_size_list}" itemLabel="s_size" itemValue="s_size"/>
						</form:select>
					</div>
					<div class="p_detail_white">
						<h5>컬러 리스트 선택</h5>
						<form:select path="m_color_list" class="form-control" multiple='false'>
							<form:options items="${m_color_list}" itemLabel="o_name" itemValue="o_standard" />
						</form:select>
						<button type="button" class="btn btn-primary">▼ 추가</button>
						<form:select path="p_color_list" class="form-control"/>
					</div>
				</div>
			</form:form>
		</article>
		
	</section>
</body>
</html>