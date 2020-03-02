<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<form id="comment_modal_form" class="row p-4 bg-light">
	<input id="c_p_id" name="c_p_id" type="text" value="${CMT.c_p_id}">
	<input id="c_b_id" name="c_b_id" type="text" value="${CMT.c_b_id}">
	<div class="col-2">
		<input class="form-control c_writer" name="c_writer" placeholder="작성자">
	</div>
	<div class="col-8">			
		<input class="form-control c_subject" name="c_subject" placeholder="댓글을 입력하세요">
	</div>
	<div class="col-2  d-flex justify-content-center">
		<button type="button" class="btn btn-success">답글저장</button>
	</div>
</form>