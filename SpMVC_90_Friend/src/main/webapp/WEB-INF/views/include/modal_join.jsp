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
		
		$.ajax({
			url : ${rootPath}/member/join,
			type : "POST",
			data : "",
			success : function(success) {
				
			},
			error : function(error) {
				
			}
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
				<input id="m_id" name="m_id">
			</section>
			
			<section>
				<label>비밀번호</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="m_pw" name="m_pw">
			</section>
			
			<section>
				<label>이름</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="m_name" name="m_name">
			</section>
			
			<section>
				<label>닉네임</label><br/>
			</section>
			
			<section class="join_input_box">
				<input id="m_nick" name="m_nick">
			</section>
			
			<br/>
			<section class="join_btn_box">
				<button id="btn_join" type="button">회원가입</button>
			</section>
		</form>
	</article>
</section>