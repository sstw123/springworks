<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String strName = request.getParameter("strName");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h3><%=strName %></h3>
<h3>EL TAG : ${param.strName }</h3>
<!-- EL TAG의 장점은 Null값인 경우 표시하지 않기 때문에 오류 상황에서도 디자인을 해치지 않는다 -->

<form>
<p><label>숫자1</label><input name="num1"></p>
<p><label>숫자2</label><input name="num2"></p>
<p><button>덧셈수행</button></p>
</form>
<%
/*
	HTML 주석
	<!-- -->을 사용하면 웹페이지에서 마우스 우클릭-소스보기 시 나타나며
	자바 주석은 나타나지 않는다
	
	lavel은 그냥 텍스트이며
	input은 웹페이지에 보낼 쿼리의 내용이며
	input 내의 name은 주소표시줄에 나타낼 변수이름이고 입력창에 입력한 결과를 받는다
	
	form으로 묶은 버튼은 form 내의 모든 input을 실행한다
	form 태그에 action 속성을 지정하면 원하는 주소(페이지,서버)에 값을 전송할 수 있다
	form 태그에 action 속성을 지정하지 않으면 현재 자신의 페이지에 전송하게 된다 
*/
%>
<p>숫자1 ${param.num1}과</p>
<p>숫자2 ${param.num2}의 덧셈 결과는</p>
<p>${param.num1 + param.num2}</p>
</body>
</html>