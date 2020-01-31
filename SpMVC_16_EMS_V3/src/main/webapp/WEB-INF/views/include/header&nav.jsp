<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/header&nav.css">

<script type="text/javascript">
	$(function() {
		if("${LOGIN}" == "NO") {
			alert("로그인을 해야합니다")
			$(".login_modal").css("display", "block")
			return
		}
		
		$("#myEms").on("click", function() {
			document.location.href = "${rootPath}/"
		})
		
		$(".nav_link li").on("click", function() {
			//let href = $(this).attr("data-menu")
			let href = $(this).data("menu")//data-menu 속성에 지정된 값 가져오기
			
			if($(this).text() == "로그인") {
				$(".login_modal").css("display", "block")
				return false
			}
			
			if($(this).text() == "EMS") {
				let menuName = $(this).data("menu-name")
				//let menuName = $(this).data("menuName")
				//data() 함수는 menu-name 또는 menuName 둘 다 사용 가능하다
				//과거에 menu-name으로 가져올 경우 오류가 발생할 수 있었기 때문
				alert(menuName)
			}
			
			document.location.href = "${rootPath}/" + href
		})
	})
</script>
<header>
	<h2 id="myEms">My EMS</h2>
</header>

<nav>
	<ul class="nav_link">
		<li data-menu="list" data-menu-name="홈으로">EMS</li>
		<li data-menu="bbs/free">자유게시판</li>
		<li data-menu="bbs/notice">공지사항</li>
		<c:if test="${empty MEMBER}">
			<li data-menu="member/login">로그인</li>
			<li data-menu="member/join">회원가입</li>
		</c:if>
		
		<c:if test="${!empty MEMBER}">
			<li data-menu="member/myinfo">${MEMBER.nickname}(${MEMBER.email})</li>
			<li data-menu="member/logout">로그아웃</li>
		</c:if>
	</ul>
</nav>