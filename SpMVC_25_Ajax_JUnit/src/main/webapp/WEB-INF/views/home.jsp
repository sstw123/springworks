<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>JSP 페이지</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script>
		$(function() {
			// 화면상의 DOM 객체가 모두 그려지기 전에 event를 설정하면 기능이 제대로 작동하지 않는다
			// jquery에서 $(function() { 코드 }) 형식으로 작성하면
			// 화면상의 모든 DOM이 다 그려진 다음 코드가 작동되어 문제가 일어나지 않는다
			
			$("button[type='button'].userId").on("click", function() {
				//alert("ajax 버튼 클릭")
				$.ajax({
					url : "senduserid",
					data : $("form").serialize(),
					type : "post",
					success : function(result) {
						alert("Response CODE : " + result.RES_CODE)
						if(result.RES_CODE == "RECEIVED") {
							alert("사용자 ID : " + JSON.stringify(result.USER_VO))
						}
					},
					error : function(error) {
						
					}
				})
				
			})
			
			$("button.user").click(function() {
				$.ajax({
					url: "senduser",
					type: "post",
					data: $("form").serialize(),
					success: function(userVO) {
						// json은 기본적으로 객체이다
						// 문자열로 확인하기 위해선 JSON.stringify()로 확인 가능하다
						
						let htmlCode = "<p>" + userVO.userId + "</p>"
						+ "<p>" + userVO.password+ "</p>"
						+ "<p>" + userVO.userName + "</p>"
						+ "<p>" + userVO.role + "</p>"
				
						$("#return_html").html(htmlCode)
					}
				})
			})
			
			$("button.user_html").click(function() {
				$.ajax({
					url: "html",
					type: "get",
					data: $("form").serialize(),
					success: function(userVO) {
						$("#return_html").html(userVO)
					}
				})
			})
		})
	</script>
</head>
<body>
	<section>
		<h2>사용자 정보</h2>
		<h5>사용자 ID : ${USER_VO.userId}</h5>
		<h5>비밀번호 : ${USER_VO.password}</h5>
		<h5>사용자 이름 : ${USER_VO.userName}</h5>
		<h5>사용자 권한 : ${USER_VO.role}</h5>
	</section>
	
	<section id="return_html">
		
	</section>
	
	<section>
		<form action="saveuser" method="post">
			<div>
				<input id="userId" placeholder="사용자 ID" name="userId"/>
			</div>
			
			<div>
				<input id="password" placeholder="비밀번호" name="password"/>
			</div>
			
			<div>
				<input id="userName" placeholder="사용자 이름" name="userName"/>
			</div>
			
			<div>
				<input id="role" placeholder="사용자 권한" name="role"/>
			</div>
			
			<button type="submit">저장</button>
			<button type="button" class="userId">사용자 ID 전송</button>
			<button type="button" class="user">입력값 전송</button>
			<button type="button" class="user_html">입력값 HTML로 받기</button>
		</form>
	</section>
</body>
</html>