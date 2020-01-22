<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script>
	$(function() {
		$("#btn_all").click(function() {
			$.ajax({
				url: '${rootPath}/plist',
				success: function(proList){
					
					$("article.detail").html(proList)
					
					/*
					proList.forEach(function(dto){
						$("article.detail").append(
							// $("<p/>", { text : dto.p_name } )
							"<p><span>" + dto.p_code + "</span> : "
							+ "<span>" + dto.p_name + "</span> : "
							+ "<span>" + dto.p_iprice + "</span> : "
							+ "<span>" + dto.p_oprice + "</span>"
						)
					})
					*/
				},
				error: function(errorMsg) {
					$("article.detail").text(errorMsg)
				}
			})
		})
		
		$("#btn_search").click(function() {
			var input_p_code = $("#p_code").val()
			$.ajax(
				{
					url : '${rootPath}/pname',
					data : {p_code : input_p_code},
					success : function(result) {
						//$("article.detail").text(result)
						$("article.detail").append(
							// '<p>' + result + '</p>'
							$("<p/>",{'html' : result, 'class' : 'pc'})
						)
					},
					error: function(error) {
						//$("article.detail").text(error)
						$("article.detail").append(
							$("<p/>",{text:error})
						)
					}
				}
			)
			
		})
	})
</script>

<style>
	* {
		margin: 0;
		padding: 0;
		box-sizing: border-box;
		border: 1px solid black;
	}
	header {
		background-color: green;
		color: white;
		padding: 0.8rem;
	}
	
	nav {
		background-color: rgba(0, 255, 0, 0.3);
	}
	
	article {
		border: 1px solid blue;
		margin: 10px 0;
	}
	
	article.detail {
		height: 300px;
		overflow-y: scroll;
	}
	
	table.p-main {
		width: 95%;
		margin: 10px auto;
		border-collapse: collapse;
		border: 1px solid green;
	}
	
	ul {
		display: flex;
		list-style: none;
		align-items: center;
	}
	
	ul a {
		text-decoration: none;
		display: inline-block;
		padding: 14px 10px;
	}
	
	table.p-list {
		width:95%;
		margin: 10px auto;
		border-collapse: collapse;;
		border: 1px solid green;
	}
</style>
</head>
<body>
	<header>
		<h3>Product</h3>
	</header>
	
	<nav>
		<ul>
			<li><a href="#">Logo</a></li>
			<li><input id="p_code"><button id="btn_search">검색</button></li>
			<li><button id="btn_all">상품리스트 가져오기</button></li>
		</ul>
	</nav>
	
	<section>
		<article class="main">
			<table class="p-main">
				<tr>
					<th>상품코드</th>
					<th>상품이름</th>
				</tr>
				
				<tr class="p-list">
					<td>11</td>
					<td>22</td>
				</tr>
			</table>
		</article>
		
		<article class="detail">
		
		</article>
	</section>
</body>
</html>