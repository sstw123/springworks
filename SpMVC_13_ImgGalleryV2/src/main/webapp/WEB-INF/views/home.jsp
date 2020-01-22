<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initian-scale=1">
	<title>이미지 갤러리</title>
	<!-- include libraries(jQuery, bootstrap) -->
	<script src="http://code.jquery.com/jquery-latest.min.js"></script>
	<!-- include summernote css/js -->
	<link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-lite.css" rel="stylesheet">
	<script src="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.12/summernote-lite.js"></script>
	<script src="${rootPath}/js/summernote-ko-KR.js"></script>
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.9.0/jquery.contextMenu.min.css" rel="stylesheet">
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.9.0/jquery.contextMenu.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-contextmenu/2.9.0/jquery.ui.position.min.js"></script>
	
	<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
	
	<link rel="stylesheet" type="text/css" href="${rootPath}/css/home.css">
	
	<script>
		// js 파일에서 사용할 public 변수 선언
		var rootPath = "${rootPath}"
	</script>
	
	<script src="${rootPath}/js/image_upload.js"></script>
	<script src="${rootPath}/js/images_upload.js"></script>
	<script src="${rootPath}/js/home.js"></script>
</head>
<body>
	<header onclick="location.href='${rootPath}/'" style="cursor:pointer">
		<h3>이미지 갤러리</h3>
	</header>
	
	<c:choose>
		<c:when test="${BODY == 'UPLOAD'}">
			<section>
				<%@ include file="/WEB-INF/views/body/img_upload.jsp" %>
			</section>
		</c:when>
		<c:otherwise>
			<section id="img_box">
				<c:forEach items="${imageList}" var="imageVO">
					<%@ include file="/WEB-INF/views/include/img_card.jsp" %>
				</c:forEach>
			</section>
		</c:otherwise>
	</c:choose>
	
	<section id="btn_save">
		<button id="btn_img_up" class="bz_button">이미지 올리기</button>
	</section>
</body>
</html>