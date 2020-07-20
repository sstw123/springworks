<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script src="${rootPath}/resources/js/ajax.js"></script>
	<style>
		section {
			border: 1px solid blue;
			padding: 1rem;
		}
	</style>
	<script>
		// jsp 외부에 script를 작성할 때는 EL tag로 작성한 변수를 사용할 수 없기 때문에
		// 임의로 script 변수를 선언하여 전달해주어야 한다
		var rootPath = "${rootPath}"
		
		$(function() {
			// 동적 태그도 선택 가능한 방식
			$(document).on("click", "#call_ajax", function() {
				
				$.ajax({
					url: "${rootPath}/ajax",
					type: "get",
					success: function(result) {
						alert(result)
					},
					error: function(error) {
						alert("서버 통신 오류")
					}
				})
			})
		})
	</script>
</head>
<body>
	<section id="main">
		<button id="call_ajax">Ajax 호출</button>
		<input id="msg"/>
		<button id="call_msg">메시지 호출</button>
		<button id="call_addr">주소 호출</button>
		<button id="call_addr_view">주소 view 호출</button>
	</section>
	
	<section id="sub">
		
	</section>
</body>
</html>