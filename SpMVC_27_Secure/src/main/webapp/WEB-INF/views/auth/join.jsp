<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		:root {
			--bg-color-input: rgba(0, 0, 0, 0.05);
			--border-width-input: 2px;
			--border-color-input: rgba(0, 0, 0, 0.05);
			--color-text-label: #18181b;
		}
		
		* {
			margin: 0;
			padding: 0;
		}
		
		body {
			width: 420px;
			height: 445px;
			margin: 0 auto;
		}
		
		form {
			display: flex;
			flex-direction: column;
			width: 95%;
			height: 95%;
			margin: 0 auto;
			
		}
		form h2 {
			align-self: center;
		}
		
		.form_item_label {
			color: var(--color-text-label);
			font-weight: 700;
		}
		
		.form_item input {
			display: block;
			width: 100%;
			background-color: var(--bg-color-input);
			/* border: var(--border-width-input) solid var(--border-color-input); */
			border: none;
			line-height: 1.5;
			padding: 0.5rem 1rem;
		}
		.btn_box {
			display: flex;
			justify-content: center;
		}
		.btn_box button {
			margin: 20px;
			padding: 10px;
			border: none;
			background-color: silver;
			cursor: pointer;
		}
		.btn_box button:hover {
			background-color: rgba(0, 0, 0, 0.4);
		}
		
		.message {
			background-color: inherit;
			font-weight: bold;
			font-size: 1rem;
		}
		
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn-join", function() {
				let username = $("#username")
				let password = $("#password")
				let re_password = $("#re_password")
				
				if(username.val() == "") {
					alert("아이디를 입력하세요")
					username.focus()
					return false
				} else if (password.val() == "") {
					alert("비밀번호를 입력하세요")
					password.focus()
					return false
				} else if (re_password.val() == "") {
					alert("비밀번호 확인을 입력하세요")
					re_password.focus()
					return false
				} else if (password.val() != re_password.val()) {
					alert("비밀번호가 서로 다릅니다\n다시 입력하세요")
					re_password.focus()
					return false
				}
				
				$(".join-form").submit()
			})
			
			// 현재 입력박스에서 포커스가 벗어났을때 발생하는 이벤트
			// 아이디 중복 확인
			$(document).on("blur", "#username", function() {
				let username = $(this).val()
				if(username == "") {
					$("#m_username").text("아이디는 반드시 입력해야합니다")
					$("#username").focus()
					return false
				}
				
				$.ajax({
					url : "${rootPath}/user/idcheck",
					type : "GET",
					data : {username : username},
					success : function(result) {
						if(result) {
							$("#m_username").text("이미 사용중인 ID입니다")
							$("#m_username").css("color", "red")
							$("#username").focus()
						} else {
							$("#m_username").text("사용 가능한 ID입니다")
							$("#m_username").css("color", "lightgreen")
						}
					},
					error : function() {
						$("#m_username").text("서버 통신 오류")
					}
				})
				
			})
		})
	</script>
</head>
<body>
	<form:form class="join-form" action="${rootPath}/user/join" method="post" autocomplete="off">
		<h2>회원가입</h2>
		<!--
		<input name="${_csrf.parameterName}" value="${_csrf.token}">
		 -->
		 
		<div class="form_item_label">
			<label for="username">ID</label>
		</div>
		
		<div id="m_username" class="message">
		</div>
		
		<div class="form_item">
			<input id="username" name="username" />
		</div>
		
		<div class="form_item_label">
			<label for="password">비밀번호</label><br/>
		</div>
		
		<div class="form_item">
			<input id="password" name="password" type="password" >
		</div>
		
		<div class="form_item_label">
			<label for="re_password">비밀번호 확인</label><br/>
		</div>
		
		<div class="form_item">
			<input id="re_password" name="re_password" type="password" >
		</div>
		
		<div class="form_item btn_box">
			<button id="btn-join" type="button">회원가입</button>
			<button id="btn-find" type="button">ID/PW 찾기</button>
		</div>
	</form:form>
</body>
</html>