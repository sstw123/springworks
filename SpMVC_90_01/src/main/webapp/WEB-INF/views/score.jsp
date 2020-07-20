<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
</head>
<body>
	<h2>점수계산결과</h2>
	<p>과목 점수 총 합 : ${arrScore[0]}
	<p>과목 점수 평균 : ${arrScore[1]}
	<p>과목 개수 : ${arrScore[2]}
</body>
</html>