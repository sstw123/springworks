<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		h2 {
			padding: 20px;
		}
		.email_body {
			width: 80%;
			margin: 120px auto;
			display: flex;
			flex-flow: column;
			justify-content: center;
			align-items: center;
		}
		.send_mail {
			padding: 0.5rem 1rem;
			border: none;
			background-color: rgba(0, 0, 0, 0.8);
			cursor: pointer;
			color: white;
		}
		.send_mail:hover {
			background-color: rgba(0, 0, 0, 0.6);
		}
		#secret {
			display: none;
		}
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn_email_ok", function() {
				let secret_key = $("#secret").text()
				let secret_value = $("#email_ok").val()
				
				if(secret_value == "") {
					alert("인증코드를 입력한 후 인증버튼을 클릭하세요")
					$("#email_ok").focus()
					return false
				}
				
				$.ajax({
					url : "${rootPath}/join/email_token_check",
					method : "POST",
					data : {
						"${_csrf.parameterName}" : "${_csrf.token}",
						secret_id : "${username}",
						secret_key : secret_key,
						secret_value : secret_value
					},
					success : function(result) {
						if(result) {
							document.location.href = "${rootPath}/user/login"
						} else {
							alert("문제가 발생했습니다\n다시 시도해주세요")
						}
					},
					error : function() {
						alert("서버 통신 오류")
					}
				})
				
				$("#secret").css("display", "inline")
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<section class="email_body">
		<h2>Email 인증</h2>
		<p>회원가입을 진행하려면 Email 인증을 완료해야합니다</p><br/>
		<form:form action="${rootPath}/join/join_last" modelAttribute="userVO">
			<form:input type="email" path="email" placeholder="Email 입력"/>
			<c:choose>
				<c:when test="${JOIN == true}">
					<button class="send_mail">인증메일 재발송</button>
					<p>Email로 발송된 인증코드를 아래 칸에 입력 후 입증 버튼을 클릭하세요</p>
					<div>
						<span id="secret">${MY_EMAIL_SECRET}</span>
						<input id="email_ok"/>
						<button type="button" id="btn_email_ok">인증하기</button>
					</div>
				</c:when>
				<c:when test="${JOIN != true}">
					<button class="send_mail">인증메일 발송</button>
				</c:when>
			</c:choose>
		</form:form>
	</section>
</body>
</html>