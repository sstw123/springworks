<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>쇼핑몰 메인 페이지</h2>
	
	<sec:authorize access="isAnonymous()">
		<p>로그인 하지 않았을 때 보이는 정보</p>
	</sec:authorize>
	
	<sec:authorize access="isAuthenticated()">
		<h3>로그인 했을 때 보이는 정보</h3>
		<p>Principle : <sec:authentication property="principal"/></p>
		<p>로그인 username(VO에 설정한 변수명) : <sec:authentication property="principal.username"/></p>
		<p>로그인 password(VO에 설정한 변수명) : <sec:authentication property="principal.password"/></p>
		<p>로그인 email(VO에 설정한 변수명) : <sec:authentication property="principal.email"/></p>
		<p>로그인 phone(VO에 설정한 변수명) : <sec:authentication property="principal.phone"/></p>
		<p>로그인 age(VO에 설정한 변수명) : <sec:authentication property="principal.age"/></p>
		<p>권한 리스트 : <sec:authentication property="principal.authorities"/></p>
	</sec:authorize>
</body>
</html>