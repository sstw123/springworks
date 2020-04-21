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
			background-color: rgba(0,0,0,0.55);
			border-radius: 15px;
			color: white;
			box-shadow:0 1px 0 #cfcfcf;
			padding: 20px;
		}
		.label {
			display: inline-block;
			width: 30%;
			min-width: 200px;
			text-align: right;
			padding: 10px;
		}
		.inline-block {
			display: inline-block;
			padding: 10px;
		}
		#pw_change {
			padding: 5px 10px;
			border: none;
			background-color: rgba(255, 255, 255, 0.85);
			cursor: pointer;
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
			background-color: CornflowerBlue;
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
	<form:form class="mypage_form" action="${rootPath}/user/mypage" autocomplete="off">
		<div class="mypage-div">
			<span class="label">아이디</span>
			<div class="inline-block">
				${loginVO.username}
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="label">비밀번호</span>
			<div class="inline-block">
				<button id="pw_change" type="button">비밀번호 변경</button>
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="label">이메일</span>
			<div class="inline-block">
				<input id="email" name="email" value="${loginVO.email}" />
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="label">핸드폰</span>
			<div class="inline-block">
				<input id="phone" name="phone" value="${loginVO.phone}" />
			</div>
		</div>
		
		<div class="mypage-div">
			<span class="label">주소</span>
			<div class="inline-block">
				<input id="address" name="address" value="${loginVO.address}" />
			</div>
		</div>
		<div class="flex">
			<button id="btn_exit" type="button">회원탈퇴</button>
			<button id="btn_edit">수정</button>
		</div>
	</form:form>
</body>
</html>