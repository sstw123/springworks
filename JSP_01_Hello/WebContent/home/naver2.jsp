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
<a href="https://search.naver.com/search.naver?query=123">네이버 검색1(query)</a><br/>
<a href="https://search.naver.com/search.naver?q=123">네이버 검색2(q)</a>
<p>네이버 검색</p>

<!-- HTML에서 action : 주소 -->
<!-- home1.jsp로 보낸다. 주소 뒤에 ?query=X&num1=X&num2=X 가 붙어서 들어갈 것 -->
<form action="/JSP_01_Hello/home/home1.jsp">
	<!-- input box 또는 input tag -->
	<p><input name="query">
	<p><input name="num1">
	<p><input name="num2">
	
	<!-- 누름단추 -->
	<button>실행</button>
</form>
</body>
</html>