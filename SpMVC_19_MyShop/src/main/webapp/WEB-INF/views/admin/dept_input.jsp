<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
	
<form:form modelAttribute="deptVO" action="${rootPath}/admin/dept/detail">
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
		<button>다음 페이지 &gt;&gt;</button>
	</div>
</form:form>