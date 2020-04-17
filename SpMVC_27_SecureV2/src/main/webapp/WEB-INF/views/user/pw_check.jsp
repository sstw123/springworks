<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function() {
			$("#password").focus()
		})
	</script>
</head>
<body>
	<form:form action="${rootPath}/user/pwcheck" id="pwcheck_form">
		<div>
			<label for="password">현재 비밀번호를 입력하세요</label>
			<input id="password" name="password" autocomplete="off" type="password"/>
		</div>
		<button>확인</button>
	</form:form>
</body>
</html>