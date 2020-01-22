<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${rootPath}/css/member-join.css">

<form method="POST" action="${rootPath}/member/join" class="join-form">
	<h2>회원가입</h2>
	<input type="text" name="u_id" placeholder="ID">
	<input type="password" name="u_pw" placeholder="비밀번호">
	<input type="password" name="u_pwcheck" placeholder="비밀번호 확인">
	<button>회원가입</button></a>
</form>