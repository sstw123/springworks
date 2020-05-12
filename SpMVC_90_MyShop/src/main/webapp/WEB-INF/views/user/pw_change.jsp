<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		#pw_change_form {
			width: 420px;
			height: 445px;
			margin: 0 auto;
			text-align: center;
		}
		.label {
			padding: 0.5rem 0px;
		}
		#btn_confirm {
			margin: 0.8rem;
			padding: 0.5rem 1rem;
			border: none;
			background-color: var(--button-bg-color);
			cursor: pointer;
			color: white;
		}
		#btn_confirm:hover {
			background-color: var(--button-hover-bg-color);
		}
	</style>
	<script>
		$(function () {
			$("#password").focus()
			
			let enable_btn_pw_change = true
			
			// ajax 완료 전까지 버튼 기능 사용 불가
			function toggle_btn(button) {
				if(button) button = false
				else return false
			}
			
			// ----------------------------------------------------------
			
			$(document).on("click", "#btn_confirm", function() {
				if(!enable_btn_pw_change) return false
				
				let password = $("#password")
				let re_password = $("#re_password")
				
				// 유효성 검사
				if (password.val() == "") {
					alert("비밀번호를 입력하세요.")
					password.focus()
					return false
				} else if (re_password.val() == "") {
					alert("비밀번호 확인을 입력하세요.")
					re_password.focus()
					return false
				} else if (password.val() != re_password.val()) {
					alert("비밀번호가 다릅니다.\n다시 확인해주세요.")
					re_password.focus()
					return false
				}
				
				// 유효성 검사 통과 시
				// 서버 부하를 줄이기 위해 버튼 클릭 시 ajax 완료될 때까지 기능 끄기
				toggle_btn(enable_btn_pw_change)
				
				$.ajax({
					url : "${rootPath}/user/pwchange",
					type : "POST",
					data : {
							"${_csrf.parameterName}" : "${_csrf.token}",
							password : $("#password").val(),
							re_password : $("#re_password").val()
					},
					success : function(result) {
						// 비밀번호 유효성 검사가 true면
						if(result) {
							alert("비밀번호가 변경되었습니다.")
							document.location.replace("${rootPath}/")
						} else {
							// 비밀번호 유효성 검사가 false면
							alert("비밀번호를 정확히 입력하세요.")
							re_password.focus()
							return false
						}
					},
					error : function(result) {
						alert("서버 통신 오류")
					}
				}).always(function() {
					toggle_btn(enable_btn_pw_change)
				})
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>비밀번호 변경</h2>
	<form:form id="pw_change_form" action="${rootPath}/user/pwchange" autocomplete="off">
		<div class="label">
			<label for="password">새 비밀번호</label>
		</div>
		
		<div>
			<input id="password" name="password" type="password"/>
		</div>
		
		<div class="label">
			<label for="re_password">새 비밀번호 확인</label>
		</div>
		
		<div>
			<input id="re_password" name="re_password" type="password"/>
		</div>
		
		<button id="btn_confirm" type="button">확인</button>
	</form:form>
</body>
</html>