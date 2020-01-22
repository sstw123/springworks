<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${rootPath}/css/member-login.css">

<script>var rootPath = "${rootPath}"</script>
<script src="${rootPath}/js/member-login.js"></script>
<form method="POST" action="${rootPath}/member/login" class="login-form">
	<h2>로그인</h2>
	<c:if test="${LOGIN_MSG == 'FAIL'}">
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
	
	<input type="text" name="u_id" id="u_id" placeholder="ID">
	<input type="password" name="u_pw" id="u_pw" placeholder="비밀번호">
	<button type="button" id="btn-login">로그인</button>
	<button type="button" id="btn-join">회원가입</button>
	<!-- button type을 button으로 설정하면 action으로 submit하는 기능 꺼짐 -->
</form>