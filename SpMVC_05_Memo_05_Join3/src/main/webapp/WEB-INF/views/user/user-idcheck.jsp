<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initian-scale=1">
<title>☆☆☆ 나의 JSP 페이지 ☆☆☆</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script>
	$(function() {
		
		// ID 사용 버튼을 클릭하면
		$("#id_use").click(function(e) {
			
			// 회원가입 창의 #u_id input box에
			// 현재 창의 u_id 값을 보내기 
			$("#u_id", opener.document).val( $("#u_id").val() )
			
			window.close()
			
			window.open('about:blank', '_self').self.close()
		})
	})
</script>
<body>
	<%/* 새 창, 크기조절, 중복확인버튼 */%>
	<form>
		<input name="u_id" id="u_id" value="${u_id}">
		<button>중복확인</button>
	</form>
	
	
	<c:choose>
		<c:when test="${idTF}">
			<h3>이미 등록된 ID입니다</h3>
		</c:when>
		
		<c:when test="${idTF == false && empty u_id}">
			<h3>ID를 입력해주세요</h3>
		</c:when>
		
		<c:otherwise>
			<h3>사용할 수 있는 ID입니다</h3>
			<button type="button" id="id_use">ID 사용</button>
		</c:otherwise>
	</c:choose>
	
	

</body>
</html>