<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	h3 {
		display: inline-block;
	}
</style>
<script>
	$(function() {
		$("#btn_delete").on("click", function() {
			if(confirm("정말로 삭제하시겠습니까?")) {
				document.location.href = "${rootPath}/bbs/delete?bbs_id=" + ${BBS_VO.bbs_id}
				alert("삭제되었습니다")
			}
			
		})
	})
</script>
<article class="card">
	<div class="card-header bg-primary text-white">
		<h3>${BBS_VO.bbs_subject}</h3>
		${BBS_VO.bbs_writer} (${BBS_VO.bbs_date} ${BBS_VO.bbs_time})
	</div>
	
	<div class="card-body">
		${BBS_VO.bbs_content}
	</div>
</article>
<div class="btn-group">
	<button id="btn_list" class="btn btn-primary" type="button">목록</button>
	<button id="btn_update" class="btn btn-success" type="button">수정</button>
	<button id="btn_delete" class="btn btn-warning" type="button">삭제</button>
	<button id="btn_reply" class="btn btn-dark" type="button">댓글</button>
</div>
<form:form action="${rootPath}/bbs/reply" modelAttribute="bbsVO">
	<form:textarea path="bbs_content" placeholder="답글을 입력하세요" cols="" rows="10"/>
	<button class="btn btn-info">답글저장</button>
</form:form>