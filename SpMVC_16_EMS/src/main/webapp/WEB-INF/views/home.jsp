<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>My EMS</title>
	<script src="https://code.jquery.com/jquery-latest.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#myEms").on("click", function() {
				document.location.href = "${rootPath}/"
			})
			
			$(".nav_link li").on("click", function() {
				//let href = $(this).attr("data-menu")
				let href = $(this).data("menu")//data-menu 속성에 지정된 값 가져오기
				
				if($(this).text() == "EMS") {
					let menuName = $(this).data("menu-name")
					//let menuName = $(this).data("menuName")
					//data() 함수는 menu-name 또는 menuName 둘 다 사용 가능하다
					//과거에 menu-name으로 가져올 경우 오류가 발생할 수 있었기 때문
					alert(menuName)
				}
				
				document.location.href = "${rootPath}/" + href
			})
		})
	</script>
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
	
</body>
</html>