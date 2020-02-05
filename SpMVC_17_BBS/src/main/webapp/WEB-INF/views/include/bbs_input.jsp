<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script>
	$(function() {
		
		let fluralSubmitFlag = false
		
		// 처음은 false반환 2번째부턴 true 반환
		function fluralSubmitCheck() {
			if(fluralSubmitFlag) {
				return true
			} else {
				fluralSubmitFlag = true
				return false
			}
		}
		
		$("#btn_input").on("click", function() {
			// 유효성 검사
			let bbs_writer = $("#bbs_writer").val()
			let bbs_subject= $("#bbs_subject").val()
			let bbs_content = $("#bbs_content").val()
			
			if(bbs_writer == "") {
				alert("작성자를 입력해야합니다")
				$("#bbs_writer").focus()
				return false
			} else if(bbs_subject== "") {
				alert("제목을 입력해야합니다")
				$("#bbs_subject").focus()
				return false
			} else if(bbs_content== "") {
				alert("내용을 입력해야합니다")
				$("#bbs_content").focus()
				return false
			}
				
			// true면 리턴, false면 진행
			if(fluralSubmitCheck()) return
			
			$("#form_input").submit()
		})
		
	})
</script>
<form:form action="${rootPath}/bbs/save" modelAttribute="bbsVO" id="form_input">
	<div class="form-group">
		<form:input class="form-control" path="bbs_date" placeholder="날짜" />
	</div>
	
	<div class="form-group">
		<form:input class="form-control" path="bbs_time" placeholder="시간" />
	</div>
	
	<div class="form-group">
		<form:input class="form-control" path="bbs_writer" placeholder="작성자" />
	</div>
	
	<div class="form-group">
		<form:input class="form-control" path="bbs_subject" placeholder="제목" />
	</div>
	
	<div class="form-group">
		<form:textarea class="form-control" path="bbs_content" placeholder="내용" />
	</div>
	
	<div class="form-group">
		<button id="btn_input" class="btn btn-primary" type="button">저장</button>
	</div>
</form:form>