<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="en">
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.bundle.min.js"></script>
	<style>
		body {
			padding-top: 56px;
		}
	</style>

	<script>
		$(function(){
			
			$(".search").keypress(function(key){
				if(key.keyCode == 13){
					let search = $(this).val()
					
					if($.trim(search) == ""){
						alert("문자를 입력하세요")
						return false;
					}
					
					document.location.href = "${rootPath}/user/product/list?search=" + search
				}
			})
			
		})
	</script>
</head>

<body>
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="${rootPath}/">My Shop</a>
			
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<!-- Home -->
					<li class="nav-item active"><a class="nav-link" href="${rootPath}/">Home<span class="sr-only">(current)</span></a></li>
					
					<!-- 장바구니 -->
					<li class="nav-item"><a class="nav-link" href="${rootPath}/user/product/cart_view">장바구니</a></li>
					
					<!-- 로그인 -->
					<sec:authorize access="isAnonymous()">
						<li class="nav-item"><a class="nav-link" href="${rootPath}/auth/login">로그인</a></li>
					</sec:authorize>
					
					<!-- 로그아웃 -->
					<sec:authorize access="isAuthenticated()">
						<li class="nav-item">
							<form:form action="${rootPath}/logout" name="logout_form2">
								<a class="nav-link" onclick="document.logout_form2.submit()" href="javascript:void(0)">로그아웃</a>
							</form:form>
						</li>
					</sec:authorize>
					
					<li class="nav-item"><input type="text" name="search" class="search" ></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<header class="jumbotron my-4">
			<h1 class="display-3">클릭하고 주문하는데까지 1분</h1>
			<p class="lead">안사도 됩니다. 구경만 하고 가세요 !!</p>
			<a href="#" class="btn btn-primary btn-lg">추천상품 바로사기</a>
		</header>

		<div class="row text-center">
			<c:choose>
				<c:when test="${empty PRODUCT_LIST}">
					<div>
						<p>상품 정보가 없습니다.</p>
					</div>
				</c:when>
				<c:otherwise>
					<c:forEach var="PRO" items="${PRODUCT_LIST}" varStatus="i">
						<div class="col-lg-3 col-md-6 mb-4">
							<div class="card h-100">
								<img class="card-img-top" src="http://placehold.it/500x325" alt="">
								<div class="card-body">
									<h4 class="card-title">${PRO.p_name}</h4>
									<fmt:setLocale value="ko_KR" scope="session"/>
									<p><fmt:formatNumber value="${PRO.p_oprice}" type="currency"/></p>
								</div>
								<div class="card-footer">
									<a href="${rootPath}/user/product/detail?id=${PRO.id}" class="btn btn-primary">자세히 보기</a>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:otherwise>
			</c:choose>
		</div>

	</div>

	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your Website 2019</p>
		</div>
	</footer>

</body>
</html>