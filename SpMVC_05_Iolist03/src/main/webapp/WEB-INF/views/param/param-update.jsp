<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initian-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
</head>
<body>
	<!--
		현재 페이지를 열기 위한 url : /CONTEXT/param/업데이트?code=10
		action을 지정하지 않으면
		action="/CONTEXT/param/업데이트?code=10" 현재 페이지 값으로 자동 지정된다
		이 상태에서 또 다시 code라는 변수에 값을 담아 보내면 GET 방식의 경우 자동으로 code 부분의 값이 변하지만
		POST 방식의 경우, 이미 주소창의 10이라는 값에 더해 또 입력한 값이 들어가서 10,123 형식으로 배열처럼 전달된다
		따라서 form의 변수명과 겹치지 않는 값으로 만들어야 한다 
	-->
	<form method="POST">
		<label for=code>코드</label>
		<input name="code">
		<button>수정</button>
	</form>

</body>
</html>