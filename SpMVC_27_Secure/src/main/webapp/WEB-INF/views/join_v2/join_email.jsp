<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		.email_body {
			width: 80%;
			margin: 0 auto;
			display: flex;
			flex-flow: column;
			justify-content: center;
			align-items: center;
		}
		#email_form div {
			text-align: center;
		}
		#email_form p {
			padding: 0.8rem 0;
		}
		.btn_email {
			padding: 0.5rem 1rem;
			border: none;
			background-color: var(--button-bg-color);
			cursor: pointer;
			color: white;
		}
		.btn_email:hover {
			background-color: var(--button-hover-bg-color);
		}
		#secret {
			display: none;
		}
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn_email_auth", function() {
				let secret_key = $("#secret").text()
				let secret_value = $("#email_auth").val()
				
				if(secret_value == "") {
					alert("인증코드를 입력한 후 인증버튼을 클릭하세요")
					$("#email_auth").focus()
					return false
				}
				
				$.ajax({
					url : "${rootPath}/join/email_token_check",
					method : "POST",
					data : {
						"${_csrf.parameterName}" : "${_csrf.token}",
						//secret_id : "${username}",
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
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>Email 인증</h2>
	<section class="email_body">
		<p>회원가입을 진행하려면 Email 인증을 완료해야합니다</p><br/>
		<form:form id="email_form" action="${rootPath}/join/join-s2" method="POST" modelAttribute="userVO" autocomplete="off">
			<c:choose>
				<c:when test="${JOIN_S2 != true}">
					<div class="email_form_item">
						<form:input type="email" path="email" placeholder="Email 입력"/>
						<button id="btn_send_mail" class="btn_email">인증메일 발송</button>
					</div>
				</c:when>
				<c:when test="${JOIN_S2 == true}">
					<div>
						<form:input type="email" path="email" placeholder="Email 입력"/>
						<button id="btn_send_mail" class="btn_email">인증메일 재발송</button>
					</div>
					<p>Email로 발송된 인증코드를 아래 칸에 입력 후 인증 버튼을 클릭하세요</p>
					<div class="email_form_item">
						<span id="secret">${MY_EMAIL_SECRET}</span>
						<input id="email_auth"/>
						<button id="btn_email_auth" class="btn_email" type="button">인증하기</button>
					</div>
				</c:when>
			</c:choose>
		</form:form>
			
	</section>
</body>
</html>