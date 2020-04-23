<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		.mypage_form {
			display: block;
			width: 50%;
			margin: 10px auto;
			border: 1px solid rgba(0,0,0,0.55);
			border-radius: 15px;
			color: black;
			box-shadow:0 1px 0 #cfcfcf;
			padding: 20px;
		}
		.mypage-div {
			display: flex;
		}
		.mypage-label {
			flex: 1;
			text-align: right;
			align-self: center;
			padding: 10px;
		}
		.mypage-content {
			flex: 3;
			align-self: center;
			padding: 10px;
		}
		.mypage-content input {
			width: 70%;
		}
		#pw_change {
			padding: 0.5rem 1rem;
			border: none;
			background-color: var(--button-bg-color);
			color: white;
			cursor: pointer;
			font-size: 0.9rem;
		}
		#pw_change:hover {
			opacity: 0.8;
		}
		#btn_exit, #btn_edit {
			display: block;
			width: 100px;
			padding: 10px;
			border: none;
			color: white;
			font-size: large;
			margin-top: 20px;
		}
		.flex {
			display: flex;
		}
		#btn_exit {
			background-color: Crimson;
		}
		#btn_edit {
			margin-left: auto;
			background-color: var(--button-bg-color);
		}
		
	</style>
	<script>
		$(function() {
			$(".mypage_form").submit(function() {
				if(!confirm("이대로 수정하시겠습니까?")) {
					return false
				}
			})
			
			$(document).on("click", "#pw_change", function() {
				document.location.href = "${rootPath}/user/pwcheck"
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<h2>My Page</h2>
	<form:form class="mypage_form" action="${rootPath}/user/mypage" autocomplete="off">
		<div class="mypage-div">
			<span class="mypage-label">아이디</span>
			<div class="mypage-content">
				${loginVO.username}
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="mypage-label">비밀번호</span>
			<div class="mypage-content">
				<button id="pw_change" type="button">비밀번호 변경</button>
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="mypage-label">이메일</span>
			<div class="mypage-content">
				<input id="email" name="email" value="${loginVO.email}" />
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="mypage-label">핸드폰</span>
			<div class="mypage-content">
				<input id="phone" name="phone" value="${loginVO.phone}" />
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="mypage-label">주소</span>
			<div class="mypage-content">
				<input id="address" name="address" value="${loginVO.address}" />
			</div>
		</div>
		<div class="flex">
			<button id="btn_edit">수정</button>
		</div>
	</form:form>
</body>
</html>