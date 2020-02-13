<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<style>
	.in-errors {
		color: red;
		font-size: 8px;
	}
	/*
		col-md-7 col-12
		해상도가 768 이상이면 7칸
		그 미만이면 12칸 = 최대 width
	*/
	tr, th, td {
		white-space: nowrap;
	}
	
	.pro-list {
		overflow: auto;
	}
	
	td.p_name {
		width: 30px;
		padding: 0 5px;
		overflow: hidden;
		text-overflow: ellipsis;
	}
</style>
<section class="mt-5 row">
	<article class="col-xl-7 col-sm-12 bg-light pro-input">
		<form:form modelAttribute="productVO" action="${rootPath}/admin/product/input">
			<div class="container-fluid form-group row">
				<form:select class="custom-select-sm col-6" path="p_bcode">
					<option value="0">품목을 선택하세요</option>
					<option value="B0001">공산품</option>
					<option value="B0002">농산물</option>
					<option value="B0003">수산물</option>
				</form:select>
				
				<form:select class="custom-select-sm col-6" path="p_dcode">
					<option value="0">거래처를 선택하세요</option>
					<option value="D0001">대덕물산</option>
					<option value="D0002">태경농산</option>
					<option value="D0003">목포수산</option>
				</form:select>
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
				<form:textarea class="form-control" rows="" cols="" placeholder="상세정보" path="p_detail"/>
			</div>
			
			<div class="form-group">
				<button>저장</button>
			</div>
		</form:form>
	</article>
	
	<article class="col-xl-4 col-sm-12 bg-light pro-list">
		<%@ include file="/WEB-INF/views/admin/product_list.jsp" %>
	</article>
</section>