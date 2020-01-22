<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%/*
	spring-form tag lib
	html의 input, form 등의 입력box용 tag를 확장하여
	spring의 controller와 연동을 쉽게 해주는 lib이다
*/%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initian-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
<style>
	fieldset {
		width: 70%;
		margin: 20px auto;
		border: 1px solid green;
		border-radius: 10px;
	}
	
	legend {
		font-weight: bold;
		font-size: 20px;
	}
	
	input, textarea {
		display: inline-block;
		width: 90%;
		padding: 8px;
		margin: 5px;
	}
	
	input:focus, textarea:focus, button {
		border: 2px solid blue;
		outline: none;
	}
	
	button {
		display: flex;
		margin: 5px auto;
	}
	
	div.c-box {
		width: 90%;
		padding: 6px;
		padding-top: 4px;
		padding-bottom: 10px;
	}
	
	[type="radio"],[type="checkbox"] {
		width: 20px;
		height: 20px;
		position: relative;
		margin-left: 15px;
		top: 5px;
		text-align: top;
	}
	</style>
</head>
<body>

<%/*
	html의 form은 default method가 GET이지만
	form:form은 default method가 POST이다
*/%>
<fieldset>
	<legend>회원가입</legend>
	<form:form modelAttribute="userDTO" autocomplete="on" class="user-form">
		<%/*
			html tag의 input box와 달리	name이라는 속성이 아닌 path라는 속성이 변수를 설정한다
		*/%>
		
		<form:input type="email" path="u_id" class="in-box" placeholder="이메일 주소를 입력하세요" /><br/>
		<form:input type="password" path="u_pw" class="in-box" placeholder="비밀번호를 입력하세요" /><br/>
		
		<input type="password" id="re_password" class="in-box" placeholder="비밀번호를 한 번 더 입력하세요" /><br/>
		
		<form:input path="u_name" class="in-box" placeholder="이름을 입력하세요" /><br/>
		<form:input path="u_nick" class="in-box" placeholder="닉네임을 입력하세요" /><br/>
		<form:input path="u_tel" type="number" class="in-box" placeholder="전화번호를 입력하세요" /><br/>

		<div class="cate-box">
			<form:checkboxes path="u_hobby" items="${HB_LIST}" itemLabel="h_hobby" itemValue="h_code"/>
		</div>
		
		<button>저장</button>
	</form:form>
</fieldset>

</body>
</html>