<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		#body {
			position: fixed;
			top: 60px;
			left: 0;
			width: 100%;
			display: flex;
		}
		
		#body nav {
			flex: 1;
			border: 1px solid blue;
			margin: 5px;
		}
		
		#body nav ul {
			list-style: none;
		}
		
		#body nav a {
			display: inline-block;
			padding: 5px 10px;
			text-decoration: none;
			color: black;
			background-color: #aabbcc;
			width: 150px;
			margin-left: 10px;
		}
		
		#body nav a:hover {
			color: white;
			background-color: #ccaaee;
		}
		
		#body article {
			flex: 3;
			border: 1px solid blue;
			margin: 5px;
		}
	</style>
	<script>
		$(function() {
			
			$(document).on("click", "#user_list", function() {
				$.get("${rootPath}/admin/userlist", function(result) {
					$("#admin_content").html(result)
				})
			})
			
			$(document).on("click", "tr.tr_user", function() {
				let username = $(this).attr("data-id")
				$.get("${rootPath}/admin/user_detail_view/" + username, function(result) {
					$("#admin_content").html(result)
				})
			})
			
			$(document).on("click", "#btn_edit", function() {
				let formdata = $("form").serialize()
				formdata += "&username=" + $("#btn_edit").attr("data-id")
				alert(formdata)
				$.post("${rootPath}/admin/user_detail_view", formdata, function(result) {
					$("#admin_content").html(result)
				})
			})
			
			$(document).on("click","#auth_append",function(){
				let auth_input = $("<input/>", {"class":"auth","name":"auth"})
				//auth_input.append($("<p/>", {"text":"제거","class":"auth_delete"}))
				$("div#auth_box").append(auth_input)
			})
			
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	<section id="body">
		<nav>
			<h3>관리자 페이지</h3>
			<ul>
				<li><a href="javascript:void(0)" id="user_list">유저 목록</a></li>
				<li><a href="#">메뉴1</a></li>
				<li><a href="#">메뉴2</a></li>
			</ul>
		</nav>
		<article id="admin_content">
			
		</article>
	</section>
</body>
</html>