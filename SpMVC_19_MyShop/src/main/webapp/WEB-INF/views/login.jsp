<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/login.css">

<script>var rootPath = "${rootPath}"</script>
<script src="${rootPath}/resources/js/login.js"></script>

<form:form method="POST" action="${rootPath}/auth/login" class="login-form">
	<h2>로그인</h2>
	<c:if test="${param.error ne null}">
		<h3>아이디 또는 비밀번호가<h3>
		<h3>잘못되었습니다</h3>
	</c:if>
	<c:if test="${LOGIN_MSG == 'TRY'}">
		<h3>로그인을 해야 합니다</h3>
	</c:if>
	<c:if test="${LOGIN_MSG == 'NO_AUTH'}">
		<h3>작성자만이 볼 수 있음!!!</h3>
	</c:if>
	<c:if test="${LOGIN_MSG == '0'}">
		<h3>로그인을 환영합니다</h3>
	</c:if>
	<!-- Spring Security는 spring form 태그 사용시
	input에 따로 _csrf.parameterName 변수에 _csrf.token 값을 넘겨주지 않아도
	자동으로 처리된다 -->
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	<input type="text" name="username" id="username" placeholder="ID">
	<input type="password" name="password" id="password" placeholder="비밀번호">
	<button type="submit" id="btn-login">로그인</button>
	<button type="submit" id="btn-join">회원가입</button>
</form:form>