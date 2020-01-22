<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
</head>
<body>
<h3>학생리스트</h3>
<%
/*
	Controller로부터 List 데이터를 수신하면 forEach 명령을 이용해서
	List를 view에 구현한다
	<c:forEach>를 사용해서 구현한다
	items : controller의 model에서 받은 List의 속성(Attribute) 이름
	var : items 리스트의 요소를 하나씩 저장해서 받을 DTO 이름
*/
%>
<c:forEach items="${STD_LIST}" var="std" varStatus="itemNum">

	<p>${itemNum.count}, ${itemNum.index} : ${std}
	
</c:forEach>
</body>
</html>