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
	
	#h3_join {
		display: block;
		text-align: center;
		margin: 0 auto;
	}
	
	.join_form section, #m_id, #m_pw, #btn_login {
		width: 380px;
		margin: 0 auto;
	}
	.join_input_box, .join_btn_box {
		text-align: center;
	}
	
	input {
		-webkit-ime-mode:disabled;
		-moz-ime-mode:disabled;
		-ms-ime-mode:disabled;
		ime-mode:disabled;
	}
	
	.modal_close {
	    padding: 0 8px;
	    color: white;
	    float: right;
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
			$("#modal_join").css("display", "none")
		})
		
		$(this).on("keydown", function(event) {
			if(event.keyCode == 27) {
				$("#modal_join").css("display", "none")
			}
		})
		
		$("#btn_join").on("click", function() {
			
			var id = $("#join_m_id").val()
			var pw1 = $("#join_m_pw1").val()
			var pw2 = $("#join_m_pw2").val()
			
			if(id == "") {
				alert("ID를 입력하세요")
				return false
			} else if (pw1 == "") {
				alert("비밀번호를 입력하세요")
				return false
			} else if (pw2 == "") {
				alert("비밀번호 확인을 입력하세요")
				return false
			} else if (pw1 != pw2) {
				alert("비밀번호가 서로 다릅니다")
				return false
			}
			
			$.ajax({
				url : "${rootPath}/member/isMemberExists",
				type : "POST",
				data : {m_id : $("#join_m_id").val()},
				success : function(result) {
					if(result) {
						alert("이미 존재하는 아이디입니다")
						return false
					} else {
						//중복된 아이디가 없으면 form submit
						$.ajax({
							url : "${rootPath}/member/join",
							type : "POST",
							data : $(".join_form").serialize(),
							success : function(result) {
								if(result) {
									document.location.href = document.location.href
								} else {
									alert("회원가입에 실패했습니다")
									return false
								}
							},
							error : function(error) {
								alert("서버 통신 오류")
								return false
							}
						})
					}
				},
				error : function(error) {
					alert("서버 통신 오류")
					return false
				}
			})
		})
		
	})
</script>
<section id="modal_join" class="modal_bg">
	
	<span class="modal_close">
		&times;
	</span>
	<article class="modal_content">
		<h3 id="h3_join">회원가입</h3>
		<form class="join_form">
			<section>
				<label>아이디</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="join_m_id" name="m_id">
			</section>
			
			<section>
				<label>비밀번호</label><br/>
			</section>
			
			<section class="join_input_box">
				<input type="text" id="join_m_pw1" name="m_pw">
			</section>
			
			<section>
				<label>비밀번호 확인</label><br/>
			</section>
			
			<section class="join_input_box">
				<input type="password" id="join_m_pw2">
			</section>
			
			<section>
				<label>이름</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="join_m_name" name="m_name">
			</section>
			
			<section>
				<label>닉네임</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="join_m_nick" name="m_nick">
			</section>
			
			<br/>
			<section class="join_btn_box">
				<button id="btn_join" type="button">회원가입</button>
			</section>
		</form>
	</article>
</section>