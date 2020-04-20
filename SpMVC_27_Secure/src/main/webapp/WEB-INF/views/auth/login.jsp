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
		:root {
			--bg-color-input: rgba(0, 0, 0, 0.05);
			--border-width-input: 2px;
			--border-color-input: rgba(0, 0, 0, 0.05);
			--color-text-label: #18181b;
		}
		
		form {
			display: flex;
			flex-direction: column;
			width: 420px;
			height: 445px;
			margin: 0 auto;
			margin-top: 60px;
			
		}
		form h2 {
			align-self: center;
		}
		
		.form_item_label {
			color: var(--color-text-label);
			font-weight: 700;
		}
		
		.form_item input {
			box-sizing: border-box;
			display: block;
			width: 100%;
			background-color: var(--bg-color-input);
			/* border: var(--border-width-input) solid var(--border-color-input); */
			border: none;
			line-height: 1.5;
			padding: 0.5rem 1rem;
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
			background-color: silver;
			cursor: pointer;
		}
		.btn_box button:hover {
			background-color: rgba(0, 0, 0, 0.4);
		}
		
		
		
	</style>
	<script>
		$(function() {
			$(document).on("click", "button.join", function() {
				document.location.href = "${rootPath}/join"
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<!-- Spring Security는 보통 Spring Form 태그와 연계하여 사용한다 -->
	<form:form class="login_form" action="${rootPath}/login" method="post" autocomplete="off">
		<h2>로그인</h2>
		
		<div>
			<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
				<span>${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
			</c:if>
		</div>
		
		<div class="form_item_label">
			<label for="username">ID</label>
		</div>
		
		<div class="form_item">
			<input id="username" name="username" />
		</div>
		
		<div class="form_item_label">
			<label for="password">비밀번호</label><br/>
		</div>
		
		<div class="form_item">
			<input id="password" name="password" type="password" >
		</div>
		
		<div class="form_item btn_box">
			<button class="login">로그인</button>
			<button id="btn-find" type="button">ID/PW 찾기</button>
			<button class="join" type="button">회원가입</button>
		</div>
	</form:form>
</body>
</html>