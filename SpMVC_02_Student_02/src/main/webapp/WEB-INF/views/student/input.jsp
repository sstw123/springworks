<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>학생정보입력</h2>
<form method="POST">
	<p/><label>학번:</label><input name="st_num">
	<p/><label>이름:</label><input name="st_name">
	<p/><label>학과:</label><input name="st_dept">
	<p/><label>학년:</label><input name="st_grade">
	<p/><label>전화번호:</label><input name="st_tel">
	<p/><button>저장(form 내에서 input태그가 2개 이상일 경우 버튼을 만들어주자 안만들어도 enter를 누르면 되긴 한다)</button>
</form>
</body>
</html>