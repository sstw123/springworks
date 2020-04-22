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
		.forms {
			width: 80%;
			display: flex;
			justify-content: center;
			margin: 0 auto;
		}
		.result-id {
			display: none;
			padding: 10px;
			background-color: rgba(0,0,0,0.3)
		}
		.forms form {
			margin: 0 50px;
		}
		.form_item {
			margin-bottom: 10px;
		}
		.form_item label {
			color: var(--label-text-color);
			font-weight: 700;
		}
		.form_item input {
			box-sizing: border-box;
			display: block;
			width: 100%;
			background-color: var(--input-bg-color);
			/* border: var(--border-width-input) solid var(--border-color-input); */
			border: none;
			line-height: 1.5;
		}
		.btn_box {
			display: flex;
			justify-content: center;
		}
		.btn_box button {
			width: 100%;
			margin-top: 20px;
			padding: 10px;
			border: none;
			background-color: var(--button-bg-color);
			color: var(--button-color);
			cursor: pointer;
		}
		.btn_box button:hover {
			background-color: var(--button-hover-bg-color);
		}
	</style>
	<script>
		$(function() {
			$(document).on("click", "#btn-find-id", function() {
				let email = $("#id_email")
				
				if(email.val() == "") {
					alert("이메일을 입력하세요.")
					email.focus()
					return false
				}
				
				$.ajax({
					url : "${rootPath}/user/find-id",
					type : "POST",
					data : $("#find_id_form").serialize(),
					success : function(result) {
						if(result != null) {
							$(".result-id").css("display", "block")
						}
						result.forEach(function(username){
							$(".result-id").append("<p>" + username + "</p>")
						})
					},
					error : function() {
						alert("서버 통신 오류")
					}
				})
			})
			
			$(document).on("click", "#btn-find-pw", function() {
				let email = $("#pw_email")
				let username = $("#pw_username")
				
				if(username.val() == "") {
					alert("아이디를 입력하세요.")
					username.focus()
					return false
				} else if (email.val() == "") {
					alert("이메일을 입력하세요.")
					email.focus()
					return false
				}
				
				$("#find_pw_form").submit()
			})
			
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>ID/PW 찾기</h2>
	<section class="forms">
		<form:form id="find_id_form" action="${rootPath}/user/find-id" method="POST" autocomplete="off">
			<div class="form_item">
				<p>회원가입 시 등록한 메일을 입력하세요</p>
			</div>
			
			<div class="form_item">
				<input type="email" id="id_email" name="email" placeholder="Email 입력"/>
			</div>
			
			<div class="form_item result-id">
			</div>
			
			<div class="form_item btn_box">
				<button id="btn-find-id" type="button">ID 찾기</button>
			</div>
		</form:form>
		
		<form:form id="find_pw_form" action="${rootPath}/user/find-id" method="POST" autocomplete="off">
			<div class="form_item">
				<p>ID와 회원가입 시 등록한 메일을 입력하세요</p>
			</div>
			
			<div class="form_item">
				<input id="pw_username" name="username" placeholder="ID 입력"/>
			</div>
			
			<div class="form_item">
				<input type="email" id="pw_email" name="email" placeholder="Email 입력"/>
			</div>
			
			<div class="form_item btn_box">
				<button id="btn-find-pw" type="button">PW 찾기</button>
			</div>
		</form:form>
	</section>
</body>
</html>