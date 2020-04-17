<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	form {
		width: 50%;
		margin: 10px auto;
	}
	
	form div.password {
		display: none;
	}
	
	input {
		display: block;
	}
</style>
<script>
	$(function() {
		$(".mypage_form").submit(function() {
			if(!confirm("이대로 수정하시겠습니까?")) {
				return false
			}
		})
		
		$(document).on("click", "#pw_change", function() {
			document.location.href = "${rootPath}/user/pwcheck"
		})
	})
</script>

<form:form class="mypage_form" action="${rootPath}/user/mypage">
	<div>
		<span>아이디</span>
		<span>${loginVO.username}</span>
	</div>
	
	<div>
		<span>비밀번호</span>
		<button id="pw_change" type="button">비밀번호 변경</button>
	</div>
	
	<div>
		<label for="email">이메일</label>
		<input id="email" name="email" value="${loginVO.email}" />
	</div>
	
	<div>
		<label for="phone">핸드폰</label>
		<input id="phone" name="phone" value="${loginVO.phone}" />
	</div>
	
	<div>
		<label for="address">주소</label>
		<input id="address" name="address" value="${loginVO.address}" />
	</div>
	
	<button id="btn_edit">수정</button>
</form:form>

<button id="btn_exit" type="button">회원탈퇴</button>