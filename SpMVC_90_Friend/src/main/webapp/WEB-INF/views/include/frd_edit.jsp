<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/frd_input_edit.css">
<script src="${rootPath}/resources/js/frd_input_edit.js"></script>

<form:form id="frd_edit_form" modelAttribute="frdDTO">
	<fieldset>
		<legend>수정</legend>
		
		<div>
			<label for="frd_name">이름</label><br/>
			<form:input type="text" id="frd_name" path="frd_name" placeholder="이름" maxlength="30"/>
		</div>
		
		<div>
			<label for="frd_tel">전화번호</label><br/>
			<form:input type="text" id="frd_tel" path="frd_tel" placeholder="전화번호" maxlength="20"/>
		</div>
		
		<div>
			<label for="frd_addr">주소</label><br/>
			<form:input type="text" id="frd_addr" path="frd_addr" placeholder="주소" maxlength="125"/>
		</div>
		
		<div>
			<label for="frd_hobby">취미</label><br/>
			<form:input type="text" id="frd_hobby" path="frd_hobby" placeholder="취미" maxlength="30"/>
		</div>
		
		<div>
			<label for="frd_rel">관계</label><br/>
			<form:input type="text" id="frd_rel" path="frd_rel" placeholder="관계" maxlength="30"/>
		</div>
		<div>
			<button id="btn_edit_submit" type="button">수정</button>
		</div>
	</fieldset>
</form:form>