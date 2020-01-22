<%@ page
	language="java"
	contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
%>
<%
/*
	contentType : HTML 코드 인코딩
	pageEncoding : 자바 코드 인코딩
*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>학생정보 보기</h2>
<p/>학번:${stDTO.st_num}
<p/>이름:${stDTO.st_name}
<p/>학과:${stDTO.st_dept}
<p/>학년:${stDTO.st_grade}
<p/>전화번호:${stDTO.st_tel}
</body>
</html>