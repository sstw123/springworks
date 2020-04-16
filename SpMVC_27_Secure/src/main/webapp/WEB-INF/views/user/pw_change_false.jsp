<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<script>
		$(function() {
			alert("비밀번호가 다릅니다.")
			document.location.replace("${rootPath}/user/pwchange")
		})
	</script>
</head>
<body>
	
</body>
</html>