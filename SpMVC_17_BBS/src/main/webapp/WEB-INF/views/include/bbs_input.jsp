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