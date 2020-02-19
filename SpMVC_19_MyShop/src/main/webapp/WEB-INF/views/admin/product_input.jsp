<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		
		// JS에서 상수를 선언하는 키워드 const(자바의 final)
		const KEY_F10 = 121
		const KEY_F9 = 120
		const KEY_F8 = 119
		const KEY_F7 = 118
		
		
		$("#p_dcode").on("keydown", function(e) {
			/*
				keyCode
				F10 : 121
				F9 : 120
				F8 : 119
				F7 : 118
			*/
			if(e.keyCode == KEY_F9) {
				let search = $(this).val()
				let dept_url = "${rootPath}/admin/dept/search/" + search
				
				let win_style = "toolbar=no, scrollbar=yes, resizable=no, top=500, left=500, width=400, height=400"
				window.open(dept_url, "_black", win_style)
			}
		})
	})
</script>
<form:form modelAttribute="productVO" action="${rootPath}/admin/product/detail">
	<div class="form-group">
		<form:select class="custom-select-sm" path="p_bcode">
			<form:option value="0">품목을 선택하세요</form:option>
			<form:option value="B0001">공산품</form:option>
			<form:option value="B0002">농산물</form:option>
			<form:option value="B0003">수산물</form:option>
		</form:select>
		<form:errors path="p_bcode" class="in-errors"/>
	</div>
	<div class="form-group">
		<div class="container-fluid row">
			<form:input path="p_dcode" class="custom-select-sm col-6" placeholder="거래처코드"/>
			<form:errors path="p_dcode" class="in-errors col-6"/>
			<span id="span_d_name" class="col-5"></span>
		</div>
	</div>
	
	<div class="form-group">
		<form:input class="form-control" placeholder="상품코드" path="p_code"/>
		<form:errors path="p_code" class="in-errors"/>
	</div>
	
	<div class="form-group">
		<form:input class="form-control" placeholder="상품명" path="p_name"/>
		<form:errors path="p_name" class="in-errors"/>
	</div>
	
	<div class="container-fluid form-group row">
		<form:input class="form-control col-6" placeholder="매입단가" path="p_iprice"/>
		<form:errors path="p_iprice" class="in-errors"/>
		
		<form:input class="form-control col-6" placeholder="판매단가" path="p_oprice"/>
		<form:errors path="p_oprice" class="in-errors"/>
	</div>
	
	<div class="form-group">
		<button>상세정보 입력 &gt;&gt;</button>
	</div>
</form:form>