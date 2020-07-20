<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>My EMS</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	
	<style>
		.login_modal {
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: rgba(0, 0, 0, 0.4);
			
			display: none;
		}
		
		.login_modal form {
			position: relative;
			top: 200px;
			margin: 10px auto;
		}
	</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/header&nav.jsp" %>
	
	<section>
		<article>
			<c:choose>
				<c:when test="${BODY == 'WRITE'}">
					<%@ include file="/WEB-INF/views/body/ems/write.jsp" %>
				</c:when>
				<c:when test="${BODY == 'VIEW'}">
					<%@ include file="/WEB-INF/views/body/ems/view.jsp" %>
				</c:when>
				<c:otherwise>
					<%@ include file="/WEB-INF/views/body/ems/list.jsp" %>
				</c:otherwise>
			</c:choose>
		</article>
	</section>
	
	<section class="login_modal">
		<%@ include file="/WEB-INF/views/member-login.jsp" %>
	</section>
	
</body>
</html>