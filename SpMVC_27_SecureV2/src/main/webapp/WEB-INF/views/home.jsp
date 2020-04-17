<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		* {
			box-sizing: border-box;
			margin: 0;
			padding: 0;
		}
		
		body {
			height: 2000px;
		}
		
		.mypage {
			margin-top: 60px;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<c:choose>
		<c:when test="${BODY == 'MYPAGE'}">
			<section class="mypage">
				<%@ include file="/WEB-INF/views/user/mypage.jsp" %>
			</section>
		</c:when>
	</c:choose>
</body>
</html>