<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		.find_pw_box {
			width: 80%;
			margin: 120px auto;
			display: flex;
			flex-flow: column;
			justify-content: center;
			align-items: center;
		}
		#btn_confirm {
			padding: 0.5rem 1rem;
			border: none;
			background-color: var(--button-bg-color);
			cursor: pointer;
			color: white;
		}
		#btn_confirm:hover {
			background-color: var(--button-hover-bg-color);
		}
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn-confirm", function() {
				let password = $("#password")
				let re_password = $("#re_password")
				
				if (password.val() == "") {
					alert("비밀번호를 입력하세요.")
					password.focus()
					return false
				} else if (re_password.val() == "") {
					alert("비밀번호 확인을 입력하세요.")
					re_password.focus()
					return false
				} else if (password.val() != re_password.val()) {
					alert("비밀번호가 다릅니다.\n다시 확인해주세요.")
					re_password.focus()
					return false
				}
				
				$("#find-form").submit()
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>비밀번호 재설정</h2>
	<section class="find_pw_box">
		<p>새로운 비밀번호를 입력하세요</p><br/>
		<form:form id="find-form" action="${rootPath}/user/find-pw" method="POST" modelAttribute="userVO" autocomplete="off">
			<input type="hidden" name="username" value="${ENC}"/>
			<input type="password" id="password" name="password"/>
			<input type="password" id="re_password" name="re_password"/>
			<button type="button" id="btn-confirm">확인</button>
		</form:form>
	</section>
</body>
</html>