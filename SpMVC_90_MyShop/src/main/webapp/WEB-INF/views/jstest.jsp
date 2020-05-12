<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function() {
			let enable_btn = true
			
			function toggle_btn(button) {
				if(button) {
					button = false
				} else {
					// ajax 완료 전까지 버튼 기능 사용 불가
					return false
				}
			}
			// ----------------------------------------------------------
			
			$(document).on("click", "#btn", function(e) {
				if(!enable_btn) return false
				console.log("버튼 안꺼짐")
				
				// 서버 부하를 줄이기 위해 버튼 클릭 시 ajax 완료될 때까지 기능 끄기
				toggle_btn(enable_btn)
				
				$.ajax({
					url : "${rootPath}/user/find-id",
					type : "POST",
					data : $("#find_id_form").serialize(),
					success : function(result) {
						if(result == "") {
							// 백엔드에서 null을 jsp와 렌더링하면 ""를 받게된다
							// result가 비어있으면
							alert("해당 이메일로 가입된 아이디가 없습니다.")
						} else {
							// result가 들어있으면
							$(".result-id").css("display", "block")
							result.forEach(function(username){
								$(".result-id").append("<p>" + username + "</p>")
							})
						}
					},
					error : function() {
						alert("서버 통신 오류")
					}
				}).always(function() {
					toggle_btn(enable_btn_find_id)
				})
			})
			
			$(document).on("click", "#btn-find-pw", function() {
				if(!enable_btn_find_pw) return false
				
				let username = $("#pw_username")
				let email = $("#pw_email")
				
				// 유효성 검사
				if(username.val() == "") {
					alert("아이디를 입력하세요.")
					username.focus()
					return false
				} else if ( !isEmail(email.val()) ) {
					alert("이메일을 정확히 입력하세요.")
					email.focus()
					return false
				}
				
				// 유효성 검사 통과 시
				// 서버 부하를 줄이기 위해 버튼 클릭 시 ajax 완료될 때까지 기능 끄기
				toggle_btn(enable_btn_find_pw)
				
				$.ajax({
					url : "${rootPath}/user/find-pw",
					type : "POST",
					data : $("#find_pw_form").serialize(),
					success : function(result) {
						if(result == 1) {
							alert("등록되지 않은 아이디입니다.")
						} else if (result == 2) {
							alert("등록된 email이 다릅니다.\n정확히 입력해주세요.")
						} else if (result == 3) {
							alert("비밀번호 재설정 링크가 메일로 발송되었습니다.")
							username.val("")
							email.val("")
						} else if (result == 4) {
							alert("메일 발송에 실패했습니다.\n다시 시도해주세요.")
						} else {
							alert("문제가 발생했습니다.\n다시 시도해주세요.")
						}
					},
					error : function() {
						alert("서버 통신 오류")
					}
				}).always(function() {
					toggle_btn(enable_btn_find_pw)
				})
			})
			
		})
	</script>
</head>
<body>
	<button id="a">버튼</button>
</body>
</html>