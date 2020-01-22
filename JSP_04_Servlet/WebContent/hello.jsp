<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String st_name = request.getParameter("st_name");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/JSP_04_Servlet/helloservlet">
	<input name = "st_name">
</form>
<input name="st_re_name" value="<%=st_name%>">
</body>
</html>