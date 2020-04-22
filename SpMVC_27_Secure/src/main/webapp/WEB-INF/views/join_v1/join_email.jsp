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
			margin: 120px auto;
			display: flex;
			flex-flow: column;
			justify-content: center;
			align-items: center;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h3>Email 인증</h3>
	<section class="email_body">
		<div>회원가입을 완료하려면 Email 인증을 해야합니다</div>
		<form:form action="${rootPath}/join/emailauth" method="POST" modelAttribute="userVO" autocomplete="off">
			<form:input type="email" path="email" placeholder="Email 입력"/>
			<c:choose>
				<c:when test="${empty userVO.email}">
					<button>인증메일 발송</button>
				</c:when>
				<c:when test="${not empty userVO.email}">
					<button>인증메일 재발송</button>
				</c:when>
			</c:choose>
		</form:form>
	</section>
</body>
</html>