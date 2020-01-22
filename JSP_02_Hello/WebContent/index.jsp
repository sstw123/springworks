<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/*
	index.jsp
	보통 도메인 네임을 브라우저에 입력하고 Enter를 눌렀을 때
	최초로 보여지는 화면을 index 화면이라고 하고
	이 때 index 화면을 구현한 파일들은 index.html, index.htm, index.php, index.asp, index.jsp 등이 있다
	html, htm : static 방식으로 구현된 서버
	php, asp, jsp : dynamic 방식으로 구현된 서버
	화면구현에서 최초로 만들게 되는 화면 : index page
	
	static 방식 : 문서파일 형태로 화면을 구현하고, 누구에게나 거의 유사한 화면을 보여주는 방식
	
	dynamic 방식 : DB, App과 연관되어서 사용자의 요구사항에 따라 화면을 다르게 보여주는 능동적인 방식
*/

String strName = request.getParameter("strName");
// 웹브라우저에서 query로 전달받은 변수값(strName= 뒤의 문자열)을 추출하여,
// 문자열 변수 strName에 저장한다

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3>나의 홈페이지</h3>
<p>나(<%= strName %>)의 홈페이지에 오신 것을 환영합니다</p>
<p><a href=https://www.naver.com target="new">네이버</a></p>
<p><a href=https://www.daum.net target="new">다음</a></p>
<p><a href=https://www.google.com target="new">구글</a></p>

<p><a href=http://localhost:8080/JSP_02_Hello/page.jsp target="new">page.jsp</a></p>
<p><a href=/JSP_02_Hello/page.jsp target="new">page.jsp</a></p>
<!-- 내 서버 내의 링크는 프로젝트이름/파일이름까지만 써줘도 자동으로 연결된다 -->

<p><a href=/JSP_02_Hello/page.jsp?strName=<%=strName%> target="new">나의 정보</a></p>
<!-- strName query로 전달받은 값을 다시 page.jsp에게 보내기 -->
<p><a href=/JSP_02_Hello/mypage/mypage1.jsp?strName=성춘향&num1=40&num2=50 target="new">성춘향의 정보</a></p>
</body>
</html>