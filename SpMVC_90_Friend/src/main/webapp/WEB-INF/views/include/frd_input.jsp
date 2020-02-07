<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/frd_input_edit.css">
<script src="${rootPath}/resources/js/frd_input_edit.js"></script>

<form method="POST" id="frd_input_form">
	<fieldset>
		<legend>입력</legend>
		
		<div>
			<label for="frd_name">이름</label><br/>
			<input type="text" id="frd_name" name="frd_name" placeholder="이름" maxlength="30"/>
		</div>
		
		<div>
			<label for="frd_tel">전화번호</label><br/>
			<input type="text" id="frd_tel" name="frd_tel" placeholder="전화번호" maxlength="20"/>
		</div>
		
		<div>
			<label for="frd_addr">주소</label><br/>
			<input type="text" id="frd_addr" name="frd_addr" placeholder="주소" maxlength="125"/>
		</div>
		
		<div>
			<label for="frd_hobby">취미</label><br/>
			<input type="text" id="frd_hobby" name="frd_hobby" placeholder="취미" maxlength="30"/>
		</div>
		
		<div>
			<label for="frd_rel">관계</label><br/>
			<input type="text" id="frd_rel" name="frd_rel" placeholder="관계" maxlength="30"/>
		</div>
		<div>
			<button id="btn_input_submit" type="button">입력</button>
		</div>
	</fieldset>
</form>