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
	
	input:focus, textarea:focus {
		border: 2px solid blue;
	}
	
	button {
		margin: 1px auto;
	}
</style>
</head>
<body>

<%/*
	html의 form은 default method가 GET이지만
	form:form은 default method가 POST이다
*/%>
<fieldset>
<legend>메모작성</legend>
<form:form modelAttribute="MEMO_DTO" class="memo-form">
	<%/*
		html tag의 input box와 달리
		name이라는 속성을 사용하지 않고
		path라는 속성이 변수를 설정한다
	*/%>
	<form:input path="m_date" class="in-box" placeholder="작성일자" /><br/>
	<form:input path="m_time" class="in-box" placeholder="작성시각" /><br/>
	<form:input path="m_auth" class="in-box" placeholder="작성자 id" /><br/>
	<form:input path="m_cate" class="in-box" placeholder="카테고리를 입력하세요" /><br/>
	<form:input path="m_subject" class="in-box" placeholder="제목을 입력하세요" /><br/>
	<form:textarea path="m_text" rows="5"/><br/>
	<button>저장</button>
</form:form>
</fieldset>

</body>
</html>