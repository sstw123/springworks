<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		.login_form {
			display: flex;
			flex-direction: column;
			width: 420px;
			height: 445px;
			margin: 0 auto;
		}
		#message {
			display: block;
			margin: 0px 0px 10px 0px;
			font-weight: bold;
			color: var(--color-danger)
		}
		.form_item {
			margin-bottom: 10px;
		}
		.form_item label {
			color: var(--label-text-color);
			font-weight: 700;
		}
		.form_item input {
			box-sizing: border-box;
			display: block;
			width: 100%;
			background-color: var(--input-bg-color);
			/* border: var(--border-width-input) solid var(--border-color-input); */
			border: none;
			line-height: 1.5;
		}
		.btn_box {
			display: flex;
			justify-content: space-around;
		}
		.btn_box button {
			flex: 1;
			margin: 20px;
			padding: 10px;
			border: none;
			background-color: var(--button-bg-color);
			color: var(--button-color);
			cursor: pointer;
		}
		.btn_box button:hover {
			background-color: var(--button-hover-bg-color);
		}
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn-join", function() {
				document.location.href = "${rootPath}/join"
			})
			
			$(document).on("click", "#btn-find", function() {
				document.location.href = "${rootPath}/user/find-id"
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<!-- Spring Security는 보통 Spring Form 태그와 연계하여 사용한다 -->
	<h2>로그인</h2>
	<form:form class="login_form" action="${rootPath}/login" method="post" autocomplete="off">
		<div>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<span id="message">${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
			</c:if>
		</div>
		
		<div class="form_item">
			<label for="username">ID</label>
		</div>
		
		<div class="form_item">
			<input id="username" name="username" />
		</div>
		
		<div class="form_item">
			<label for="password">비밀번호</label><br/>
		</div>
		
		<div class="form_item">
			<input id="password" name="password" type="password" >
		</div>
		
		<div class="form_item btn_box">
			<button id="btn-login">로그인</button>
			<button id="btn-find" type="button">ID/비밀번호 찾기</button>
			<button id="btn-join" type="button">회원가입</button>
		</div>
	</form:form>
</body>
</html>