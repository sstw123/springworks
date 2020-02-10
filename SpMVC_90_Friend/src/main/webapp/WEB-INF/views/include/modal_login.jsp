<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	.modal_bg {
	  display: none; /* Hidden by default */
	  position: fixed; /* Stay in place */
	  z-index: 1; /* Sit on top */
	  left: 0;
	  top: 0;
	  width: 100%; /* Full width */
	  height: 100%; /* Full height */
	  overflow: auto; /* Enable scroll if needed */
	  background-color: rgba(0, 0, 0, 0.4); /* Black w/ opacity */
	}
	
	.modal_content {
	  background-color: #fefefe;
	  margin: 15% auto; /* 15% from the top and centered */
	  padding: 20px;
	  border: 1px solid #888;
	  width: 400px; /* Could be more or less, depending on screen size */
	}
	
	#h3_login {
		display: block;
		text-align: center;
		margin: 0 auto;
	}
	
	.login_form section, #m_id, #m_pw, #btn_login {
		width: 380px;
		margin: 0 auto;
	}
	.login_input_box, .login_btn_box {
		text-align: center;
	}
	
	.modal_close {
	    padding: 0 8px;
		float: right;	    
	    color: white;
	    font-size: 28px;
	    font-weight: bold;
	}
	
	.modal_close:hover, .modal_close:focus {
	  color: black;
	  background-color: rgba(200, 200, 200, 0.4);
	  cursor: pointer;
	}
</style>
<script>
	$(function() {
		$(".modal_close").on("click", function() {
			$("#modal_login").css("display", "none")
		})
		
		$(this).on("keydown", function(event) {
			if(event.keyCode == 27) {
				$("#modal_login").css("display", "none")
			}
		})
		
		$("#btn_login").on("click", function() {
		
			// 유효성 검사
			// id, pw가 입력되지 않았을 때 경고
			if($("#login_m_id").val() == "") {
				alert("아이디를 입력하세요")
				$("#login_m_id").focus()
				return false
			} else if($("#login_m_pw").val() == "") {
				alert("비밀번호를 입력하세요")
				$("#login_m_pw").focus()
				return false
			}
			
			
			$.post(
					"${rootPath}/member/login",
					$(".login_form").serialize(),
					function(result) {
						if(result) {
							//true면 새로고침
							document.location.replace(document.location.href)
						} else {
							alert("아이디 또는 비밀번호가 일치하지 않습니다")
							return false
						}
					}
			)
			
		})
	})
</script>
<section id="modal_login" class="modal_bg">
	<span class="modal_close">
		&times;
	</span>
	
	<article class="modal_content">
		
		<h3 id="h3_login">로그인</h3>
		<form class="login_form">
			<section>
				<label>아이디</label><br/>
			</section>
			
			<section class="login_input_box">
				<input id="login_m_id" name="m_id">
			</section>
			
			<section>
				<label>비밀번호</label><br/>
			</section>
			
			<section class="login_input_box">
				<input type="password" id="login_m_pw" name="m_pw">
			</section>
			<br/>
			<section class="login_btn_box">
				<button id="btn_login" type="button">로그인</button>
			</section>
		</form>
	</article>
</section>