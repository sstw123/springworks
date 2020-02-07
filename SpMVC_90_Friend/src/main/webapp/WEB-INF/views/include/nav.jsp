<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	nav {
		background-color: #7AEBFF;
	}
	ul {
		display: flex;
		margin: 0;
		padding: 0;
	}
	li {
		list-style: none;
		width: 90px;
		padding: 15px 0;
		text-align: center;
		cursor: pointer;
	}
	li:nth-of-type(2) {
		margin-left: auto;
	}
	li:hover {
		background-color: black;
		color: white;
	}
</style>
<script>
	$(function() {
		$("li").on("click", function() {
			let text = $(this).text()
			
			if(text == "홈") {
				document.location.href = "${rootPath}/"
			} else if(text == "로그인") {
				$("#modal_login").css("display", "block")
			} else if(text == "회원가입") {
				$("#modal_join").css("display", "block")
			}
		})
	})
</script>
<nav>
	<ul>
		<li>홈</li>
		<li>로그인</li>
		<li>회원가입</li>
	</ul>
</nav>