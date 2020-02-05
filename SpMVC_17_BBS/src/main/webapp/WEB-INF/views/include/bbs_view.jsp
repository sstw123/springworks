<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<style>
	.card-header h3 {
		display: inline-block;
		width: auto;
		padding: 5px 15px;
	}
</style>
<script>
	$(function() {
		// jQuery에서 이벤트 없을 시 페이지가 모두 그려진 후 바로 실행
		$.post("${rootPath}/bbs/cmt_list",
				{cmt_p_id : "${bbsVO.bbs_id}"},
				function(result) {
					$("#cmt_list").html(result)
				}
		)
		
		$("#btn_save_cmt").on("click",function() {
			let cmt_writer = $("#cmt_writer").val()
			let cmt_text = $("#cmt_text").val()
			
			if(cmt_writer == "") {
				alert("댓글 작성자를 입력해야합니다")
				$("cmt_writer").focus()
				return false
			} else if(cmt_text == "") {
				alert("댓글을 입력해야합니다")
				$("cmt_text").focus()
				return false
			}
			
			// json 형태로 데이터 생성
			let cmt_data = {
					cmt_p_id:"${bbsVO.bbs_id}",
					cmt_writer:cmt_writer,
					cmt_text:cmt_text
				}
			
			$.ajax({
				url : "${rootPath}/bbs/cmt_write",
				method : "POST",
				data : cmt_data,
				success : function(result) {
					$("#cmt_list").html(result)
				},
				error : function(error) {
					alert("서버 통신 오류")
				}
			})
			
		})
		
		$("#btn_delete").on("click", function() {
			if(confirm("정말로 삭제하시겠습니까?")) {
				document.location.href = "${rootPath}/bbs/delete?bbs_id=" + ${bbsVO.bbs_id}
				alert("삭제되었습니다")
			}
		})
		
		$("#btn_save_reply").on("click", function() {
			let bbs_writer = $("#bbs_writer").val()
			let bbs_content= $("#bbs_content").val()
			
			if(bbs_writer == "") {
				alert("작성자를 입력해야합니다")
				$("#bbs_writer").focus()
				return false
			} else if(bbs_content == "") {
				alert("내용을 입력해야합니다")
				$("#bbs_content").focus()
				return false
			}
			
			$("#form_reply").submit()
		})
	})
</script>
<article class="card">
	<div class="card-header bg-primary text-white">
		<h3>${bbsVO.bbs_subject}</h3>
		${bbsVO.bbs_writer} (${bbsVO.bbs_date} ${bbsVO.bbs_time})
	</div>
	
	<div class="card-body">
		${bbsVO.bbs_content}
	</div>
	
	<section class="card-body bg-info">
		<div id="cmt_list" class="form-group bg-white">
			<%@ include file="/WEB-INF/views/include/bbs_comment.jsp" %>
		</div>
		<div class="form-group">
			<input id="cmt_writer" class="form-control" name="cmt_writer" placeholder="작성자">
			<input id="cmt_text" class="form-control" name="cmt_text" >
			<button id="btn_save_cmt" class="btn btn-dark" type="button">저장</button>
		</div>
	</section>
</article>

<div class="btn-group">
	<button id="btn_list" class="btn btn-primary" type="button">목록</button>
	<button id="btn_update" class="btn btn-success" type="button">수정</button>
	<button id="btn_delete" class="btn btn-warning" type="button">삭제</button>
</div>
<c:if test="${bbsVO.bbs_p_id == 0}">
	<form:form id="form_reply" action="${rootPath}/bbs/reply" modelAttribute="bbsVO">
		<div class="form-group">
			<input id="bbs_writer" class="form-control" name="bbs_writer" placeholder="답글 작성자"/>
		</div>
		<div class="form-group">
			<textarea id="bbs_content" name="bbs_content" placeholder="답글을 입력하세요" cols="" rows="10"></textarea>
		</div>
		<div class="form-group">
			<button id="btn_save_reply" class="btn btn-info" type="button">답글저장</button>
		</div>
	</form:form>
</c:if>