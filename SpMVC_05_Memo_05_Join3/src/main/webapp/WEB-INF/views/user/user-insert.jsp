<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<%/*
	spring-form tag lib
	html의 input, form 등의 입력box용 tag를 확장하여
	spring의 controller와 연동을 쉽게 해주는 lib이다
*/%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initian-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>

<style>
	fieldset {
		width: 70%;
		margin: 20px auto;
		border: 1px solid green;
		border-radius: 10px;
	}
	
	legend {
		font-weight: bold;
		font-size: 20px;
	}
	
	input, textarea {
		display: inline-block;
		width: 90%;
		padding: 8px;
		margin: 5px;
	}
	
	input:focus, textarea:focus, button {
		border: 2px solid blue;
		outline: none;
	}
	
	input:hover {
		background-color: #ddd;
		border: 2px solid red;
	}
	
	button {
		margin: 1px auto;
	}
	
	.in-error {
		display: inline-block;
		margin-left: 20px;
		font-size: 12px;
		font-weight: bold;
		color: red;
	}
	
	span#u_id_msg {
		display: none;
	}
	
	#u_id {
		width: 70%;
		
	}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		
		// js 함수는 1급 함수
		// 새로운 방식 : 함수 = 객체 = 변수
		// let idcheck = function() { }
		
		// 새 창을 열어서 id 검사하는 코드를 함수로 선언		
		// 표준 함수 선언 방식
		function idcheck() {
				let top = 500
				let left = 600
				
				let u_id = $("#u_id").val()
				let status = "toobar=no, scrollbars=yes, resizable=no, top=500, left=500, width=700, height=400"
				
				if(u_id == "") {
					alert("ID를 입력하고 Enter")
					return false;
				}
				let openWindow = window.open("${rootPath}/user/idcheck?u_id=" + u_id, "_new", status)
				openWindow.moveTo(left, top)
		}
		
		$("#u_id").keypress(function(e) {
			if(e.keyCode == 13) {
				// 표준, 일반적으로 함수를 호출하는 방법
				idcheck()
			}
		})
		
		$("#btn-idcheck").click(idcheck)
		// event handler에 함수 목록을 등록하는 절차
		// 이 때는 함수가 마치 객체처럼 등록되기 때문에 함수 이름에 ()를 넣지 말아야 한다
		// 이러한 함수 방식을 call back 함수라고 한다
		
		$("#u_id").blur(function(e) {
			$("#u_id_msg").text("blur")
			$("#u_id_msg").css("display", "block")
		})
		
		$("button#btn-save").click(function() {
			
			let pw = $("#u_pw").val()
			if(pw == "") {
				alert("비밀번호를 입력하세요")
				$("#u_pw").focus()
				return false
			}
			
			let pw_check = $("#pw_check").val()
			if(pw_check == "") {
				alert("비밀번호를 한번 더 입력해주세요")
				$("#pw_check").focus()
				return false
			}
			
			if(pw != pw_check) {
				alert("비밀번호가 일치하지 않습니다\n" + "다시 입력해주세요")
				return false
			}
			
			let u_name = $("#u_name").val()
			if(u_name == "") {
				alert("이름은 반드시 입력해야 합니다")
				$("#u_name").focus()
				return false
			}
			
			
			// $(form).submit() 브라우저마다 안되는 경우가 있어서 form에 따옴표를 묶어준다
			$("form").submit()
		})
	})
</script>
</head>
<body>

<%/*
	html의 form은 default method가 GET이지만
	form:form은 default method가 POST이다
*/%>
<fieldset>
<legend>
	<c:if test="${TITLE == null}">회원가입</c:if>
	<c:if test="${TITLE != null}">${TITLE}</c:if>
</legend>
<form:form modelAttribute="userDTO" autocomplete="off" class="user-form">
	<%/* html tag의 input box와 달리 name이라는 속성을 사용하지 않고 path라는 속성이 변수를 설정한다 */%>
	
	<div class="in-box-border">
		<form:input path="u_id" type="text" placeholder="사용자 ID를 입력하세요" class="in-box" />
		<button type="button" id="btn-idcheck">중복확인</button><br/>
		<form:errors path="u_id" class="in-error" /><br/>
		<span id="u_id_msg"></span>
	</div>
	
	<div class="in-box-border">
		<form:input path="u_pw" type="password" placeholder="비밀번호를 입력하세요" class="in-box" /><br/>
		<form:errors path="u_pw" class="in-error" />
	</div>
	
	<div class="in-box-border">
		<form:input path="pw_check" type="password" placeholder="비밀번호 확인" class="in-box" />
		<form:errors path="pw_check" class="in-error" />
	</div>
	<!-- 비밀번호 확인은 서버로 데이터를 전송할 필요가 없기 때문에 그냥 input으로 작성한다
	또한 form:input은 path가 필수이기 때문에 DTO에 새로운 필드변수를 작성해야 한다 -->
	
	<div class="in-box-border">
		<form:input path="u_name" type="text" placeholder="이름" class="in-box" /><br/>
		<form:errors path="u_name" class="in-error" />
	</div>
	
	<div class="in-box-border">
		<form:input path="u_nick" placeholder="별명" class="in-box" /><br/>
		<form:errors path="u_nick" class="in-error" />
	</div>
	
	<div class="in-box-border">
		<form:input path="u_tel" type="number" placeholder="전화번호 - 를 제외하고 입력하세요" class="in-box" /><br/>
		<form:errors path="u_tel" class="in-error" />
	</div>
	
	<button type="button" id="btn-save">저장</button>
</form:form>
</fieldset>

</body>
</html>