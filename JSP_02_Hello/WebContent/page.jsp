<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// request.getParameter("strName");
// 웹브라우저에서 query로 전달받은 변수값(strName= 뒤의 문자열)을 추출하여 문자열 변수 strName에 저장한다
// 2000년대 초반 사용하던 코드
String strName = request.getParameter("strName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>나의 정보</h3>
<p>나는 (<%=strName %>) 입니다</p>
<p><% out.write("대한민국"); %></p>
<p><% out.write(30 * 40); %></p>
<p>
<%
	int sum = 30 + 40;
	out.write(sum);
%>
</p>
</body>
</html>