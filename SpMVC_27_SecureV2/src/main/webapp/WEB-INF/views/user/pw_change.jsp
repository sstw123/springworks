<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function () {
			$(document).on("click", "#pw_change", function() {
				let password = $("#password")
				let re_password = $("#re_password")
				
				if (password.val() == "") {
					alert("비밀번호를 입력하세요")
					password.focus()
					return false
				} else if (re_password.val() == "") {
					alert("비밀번호 확인을 입력하세요")
					re_password.focus()
					return false
				} else if (password.val() != re_password.val()) {
					alert("비밀번호가 서로 다릅니다\n다시 입력하세요")
					re_password.focus()
					return false
				}
				
				$(".pw_change_form").submit()
			})
		})
	</script>
</head>
<body>
	<form:form class="pw_change_form" action="${rootPath}/user/pwchange" autocomplete="off">
		<div>
			<label for="password">새 비밀번호</label>
			<input id="password" name="password" type="password"/>
		</div>
		
		<div>
			<label for="re_password">새 비밀번호 확인</label>
			<input id="re_password" name="re_password" type="password"/>
		</div>
		<button id="pw_change">확인</button>
	</form:form>
</body>
</html>