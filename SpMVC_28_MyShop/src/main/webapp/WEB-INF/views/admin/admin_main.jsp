<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	<style>
		section.body {
			display: flex;
		}
		
		article {
			border: 1px solid blue;
			height: 500px;
		}
		
		article.menu {
			flex-basis: 300px;
		}
	</style>
	<script>
		
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<section class="container-fluid body p-2">
		<article class="container menu m-2">
			<%@ include file="/WEB-INF/views/admin/admin_nav.jspf" %>
		</article>
		
		<article class="container-fluid content m-2">
		</article>
	</section>
	
	
</body>
</html>