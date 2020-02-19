<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function() {
			$("a.logout").on("click", function() {
				$("#logout").submit()
			})
		})
	</script>
</head>
<body class="container-xl">
	<%@ include file="/WEB-INF/views/include/header_nav.jspf" %>
</body>
</html>