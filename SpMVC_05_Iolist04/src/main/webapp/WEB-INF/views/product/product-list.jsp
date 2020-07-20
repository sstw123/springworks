<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
	<%@ include file="/WEB-INF/views/include/include-product-css.jspf" %>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<style>
		p#insert {
			margin-left: 40px;
		}
	</style>
	<script>
		$(function(){
			$(".content-body").click(function() {
				let p_code = $(this).attr("id")
				alert(p_code)
				document.location.href = "${rootPath}/product/view?id=" + p_code
			})
		})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-product-header.jspf" %>
	<section>
		<article>
			<p id="insert">
				<a href="${rootPath}/product/insert"><button>새로등록</button></a>
			</p>
		</article>
		<%@ include file="/WEB-INF/views/product/product-list-body.jsp" %>
	</section>
	
</body>
</html>