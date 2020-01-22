<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!--
	a 태그 (anchor : 닻)
	다른 곳으로 연결하는 링커
	hyper text의 꽃
	해당 문자열(네이버 검색)을 클릭하면 href="" 에 설정된 곳으로 jump하는 코드
-->
<p>구글 검색</p>

<!-- HTML에서 action : 주소 -->
<!-- action태그가 없거나 action 값이 비어있다면 자기 자신에게 보낸다 -->
<form action="https://www.google.com/search">
	<!-- input box 또는 input tag -->
	<p><input name="q">
	<p><input name="num1">
	<p><input name="num2">
	
	<!-- 누름단추 -->
	<button>검색</button>
</form>
</body>
</html>