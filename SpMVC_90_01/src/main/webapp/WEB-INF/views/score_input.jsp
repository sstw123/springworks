<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
</head>
<body>
	<header>
		<h2>점수계산하기</h2>
	</header>
	
	<section>
		<form action="${rootPath}/number/score_input" method="post">
			<article>
				<input name="kor" placeholder="국어점수">
			</article>
			<article>
				<input name="eng" placeholder="영어점수">
			</article>
			<article>
				<input name="math" placeholder="수학점수">
			</article>
			<article>
				<input name="sci" placeholder="과학점수">
			</article>
			<article>
				<input name="music" placeholder="음악점수">
			</article>
			<article>
				<button>계산</button>
			</article>
		</form>
	</section>
	
	<section>
		<h2>점수계산결과</h2>
		<p>과목 점수 총 합 : ${scoreVO.sum}
		<p>과목 점수 평균 : ${scoreVO.avg}
		<p>과목 개수 : ${scoreVO.count}
	</section>
</body>
</html>