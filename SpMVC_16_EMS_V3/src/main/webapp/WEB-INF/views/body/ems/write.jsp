<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/email_write.css">

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.js"></script>
<script src="${rootPath}/resources/js/summernote-ko-KR.js"></script>

<script>
$(function() {
	var toolbar = [ [ 'style', [ 'bold', 'italic', 'underline' ] ],
		[ 'fontsize', [ 'fontsize' ] ], [ 'font Style', [ 'fontname' ] ],
		[ 'color', [ 'color' ] ], [ 'para', [ 'ul', 'ol', 'paragraph' ] ],
		[ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
		[ 'insert', [ 'link', 'hr', 'picture' ] ],
		[ 'view', [ 'fullscreen', 'codeview' ] ] ]
		
	$("#content").summernote({
		disableDragAndDrop : false,
		lang : 'ko-KR',
		placeholder : '본문을 입력하세요',
		width : '100%',
		height : '200px',
		toolbar : toolbar
	})
})
</script>

<fieldset class="email_write_box">
	<form:form modelAttribute="emailVO">
		<div class="input_box">
			<label for="from_email">보낼 사람의 Email</label>
			<form:input path="from_email"/>
		</div>
		
		<div class="input_box">
			<label for="to_email">받을 사람의 Email</label>
			<form:input path="to_email"/>
		</div>
		
		<div class="input_box">
			<label for="send_date">작성일자</label>
			<form:input path="send_date"/>
		</div>
		
		<div class="input_box">
			<label for="send_time">보낸시간</label>
			<form:input path="send_time"/>
		</div>
		
		<div class="input_box">
			<label for="from_name">작성자</label>
			<form:input path="from_name"/>
		</div>
		
		<div class="input_box">
			<label for="to_name">받는사람</label>
			<form:input path="to_name"/>
		</div>
		
		<div class="input_box">
			<label for="subject">제목</label>
			<form:input path="subject"/>
		</div>
		
		<div class="input_box">
			<form:textarea path="content"/>
		</div>
		
		<div class="mail_box">
			<button id="send_mail">메일 보내기</button>
		</div>
	</form:form>
</fieldset>