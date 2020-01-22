<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="https://search.naver.com/search.naver?" target="new">
	<label>네이버 검색</label>
	<input name="query">
</form>

<form action="https://search.daum.net/search?" target="new">
	<label>다음 검색</label>
	<input name="q">
	<button>검색</button>
</form>

<form action="https://www.google.com/search?" target="new">
	<label>구글 검색</label>
	<input name="q">
	<button>검색</button>
</form>
</body>
</html>