<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	// Java Scriptlet 코드(JavaScript와 다름 / jsp에서만 사용 가능하다)
	String strName = request.getParameter("strName");
	String strNum1 = request.getParameter("num1");
	String strNum2 = request.getParameter("num2");
	
	int intNum1 = 0;
	int intNum2 = 0;
	
	try {
		intNum1 = Integer.valueOf(strNum1);
		intNum2 = Integer.valueOf(strNum2);	
	} catch (Exception e) {
		
	}
	
	int intSum = intNum1 + intNum2;
	
%>
<!--
	주석코드
	XML 주석코드 내에선 꺽쇠 태그 사용 금지
	본문 코드와 겹쳐서 Web에 보여질 때 의도하지 않은 결과가 나타날 수 있다
	
	느낌표 기호도 가급적 사용하지 말 것
-->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form>
	<p><label>num1</label><input name="num1">
	<p><label>num2</label><input name="num2">
	<p><button>계산</button>
</form>
<h3><%= intSum %></h3>
</body>
</html>