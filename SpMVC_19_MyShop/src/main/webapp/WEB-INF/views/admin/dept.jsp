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
	
	.dept-list {
		overflow: auto;
	}
	
	td.p_name {
		display:inline-block;
		width: 150px;
		padding: 0 5px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>
<section class="mt-5 row">
	<article class="col-xl-7 col-sm-12 bg-light dept-input">
		<form:form modelAttribute="deptVO" action="${rootPath}/admin/dept/input">
			<div class="form-group">
				<form:input class="form-control" placeholder="거래처코드" path="d_code"/>
				<form:errors path="d_code" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="거래처" path="d_name"/>
				<form:errors path="d_name" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="대표자" path="d_ceo"/>
				<form:errors path="d_ceo" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="사업자번호" path="d_sid"/>
				<form:errors path="d_sid" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="대표전화" path="d_tel"/>
				<form:errors path="d_tel" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="주소" path="d_addr"/>
				<form:errors path="d_addr" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="담당자" path="d_manager"/>
				<form:errors path="d_manager" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="담당자 전화" path="d_mtel"/>
				<form:errors path="d_mtel" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<form:input class="form-control" placeholder="비고" path="d_rem"/>
				<form:errors path="d_rem" class="in-errors"/>
			</div>
			
			<div class="form-group">
				<button>저장</button>
			</div>
		</form:form>
	</article>
	
	<article class="col-xl-4 col-sm-12 bg-light dept-list">
		<%@ include file="/WEB-INF/views/admin/dept_list.jsp" %>
	</article>
</section>