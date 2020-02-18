<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<form:form modelAttribute="deptVO" action="${rootPath}/admin/dept/input">
	<div class="form-group">
		<form:input class="form-control" placeholder="비고" path="d_rem"/>
		<form:errors path="d_rem" class="in-errors"/>
	</div>
	
	<div class="form-group">
		<button>저장</button>
	</div>
</form:form>