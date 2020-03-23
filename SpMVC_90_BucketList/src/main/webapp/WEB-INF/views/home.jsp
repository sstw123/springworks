<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>My Bucket List</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<style>
		* {
			margin: 0;
			padding: 0;
		}
		
		header {
			text-align: center;
			padding: 30px 0;
		}
		
		nav ul {
			list-style: none;
			display: flex;
		}
		nav ul li {
			width: 80px;
			padding: 12px 0;
			text-align: center;
		}
		nav ul li:nth-child(2) {
			margin-left: auto;
		}
	</style>
</head>
<body>
	<header>
		<h2>My Bucket List</h2>
	</header>
	
	<section>
		<%@ include file="/WEB-INF/views/bucket_page.jsp" %>
	</section>
	
</body>
</html>