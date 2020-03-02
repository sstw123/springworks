<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/views/include/include-head.jspf" %>
	<style>
		div.delete_comment {
			cursor:pointer;
			width:20px;
			height:20px;
		}
		
		div.modal-main {
			display: none;
			position: fixed;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			
			overflow: auto;
			
			background-color: rgba(0, 0, 0, 0.4);
			z-index: 10;
		}
		
		div.modal-content {
			position: relative;
			margin: auto;
			top: 300px;
			width: 80%;
			padding: 0;
		}
		
		div.modal-heaver {
			display: flex;
			justify-content: flex-end;
		}
		
		span.modal-close {
			cursor: pointer;
			font-size: 30px;
			font-weight: bold;
			color: black;
		}
		
		span.modal-close:hover {
			color: red;
		}
	</style>
	
	<script>
	$(function(){
		
		$(document).on("click","button",function() {
			let txt = $(this).text()
			
			if(txt == '수정') {
				document.location.href="${rootPath}/update?b_id=${BBS.b_id}"
			} else if(txt == '삭제') {
				if(confirm("삭제할까요")) {
					document.location.replace("${rootPath}/delete/${BBS.b_id}")    
				}
			} else if(txt == '저장') {
				let formData = $("#comment_form").serialize()
				
				$.ajax({
					url : "${rootPath}/comment/insert",
					type : "POST",
					data : formData
					/*{
						c_b_id : "${BBS.b_id}",
						c_writer : $("#c_writer").val(),
						c_subject : $("#c_subject").val()
					}*/
					,
					success : function(result) {
						$("#comment_list").html(result)
						
						$(".c_writer").val("")
						$(".c_subject").val("")
					},
					error : function(error) {
						alert("서버 통신 오류")
					}
				})
				
			} else if(txt == '답글저장') {
				let formData = $("#comment_modal_form").serialize()
				
				$.ajax({
					url : "${rootPath}/comment/insert",
					type : "POST",
					data : formData,
					success : function(result) {
						$("#comment_list").html(result)
						
						$(".c_writer").val("")
						$(".c_subject").val("")
						
						$(".modal-main").css("display", "none")
					},
					error : function(error) {
						alert("서버 통신 오류")
					}
				})
				
			} else if(txt == '답글') {
				document.location.href = "${rootPath}/reply?b_p_id=${BBS.b_id}"
				
			} else if(txt == '목록') {
				document.location.href="${rootPath}/list"
			}
			
		})
		
		$(document).on("click", ".delete_comment", function() {
			
			if(!confirm("정말 삭제하시겠습니까?")) {
				return false
			}
			
			$.ajax({
				url : "${rootPath}/comment/delete",
				type : "POST",
				data : {
					c_id : $(this).data("c_id"),
					c_b_id : "${BBS.b_id}"
				},
				success : function(result) {
					$("#comment_list").html(result)
				},
				error : function(error) {
					alert("서버 통신 오류")
				}
			})
			
		})
		
		$(document).on("click", ".reply_comment", function(event) {
			
			event.stopPropagation()
			
			let b_id = "${BBS.b_id}"
			let c_id = $(this).parent("div").data("id")
			let data = {c_p_id : c_id, c_b_id : b_id}
			
			$.get("${rootPath}/comment/reply", data, function(result) {
				$(".modal-body").html(result)
				$(".modal-main").css("display", "block")
			})
		})
		
		$(".modal-close").click(function() {
			$(".modal-main").css("display", "none")
		})
		
		$(document).on("click", ".cmt-item", function() {
			let id = $(this).data("id")
			let writer = $(this).find(".c_writer").text()
			let subject = $(this).find(".c_subject").text()
			
			$("#c_id").val(id)
			$("#c_writer").val(writer)
			$("#c_subject").val(subject)
		})
		
	})
	</script>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/include-header.jspf" %>
	<section class="container-fluid">
		<h2 class="p-3">${BBS.b_subject}</h2>
		<div>
			<small class="m-3">${BBS.b_writer}</small>
			<small class="m-3">${BBS.b_date_time}</small>
		</div>
		<hr/>
		<div class="p-3">
		${BBS.b_content}
		</div>
	</section>
	<div class="form-group d-flex justify-content-end">
		<button class="btn btn-primary mr-3">수정</button>
		<button class="btn btn-danger mr-3">삭제</button>
		<button class="btn btn-info mr-3">답글</button>
		<button class="btn btn-success">목록</button>
	</div>
	<hr/>
	<section class="container-fluid p-4">
		<div class="p-2">
			<b>댓글을 남겨주세요</b>
		</div>
		
		<form id="comment_form" class="row p-4 bg-light" method="post" action="${rootPath}/comment/insert2">
			<input id="c_id" name="c_id" type="hidden" value="0">
			<input id="c_b_id" name="c_b_id" type="hidden" value="${BBS.b_id}">
			<div class="col-2">
				<input id="c_writer" name="c_writer" class="form-control" placeholder="작성자">
			</div>
			<div class="col-8">			
				<input id="c_subject" name="c_subject" class="form-control" placeholder="댓글을 입력하세요">
			</div>
			<div class="col-2  d-flex justify-content-center">
				<button type="button" class="btn btn-success">저장</button>
			</div>
		</form>

		<div class="p-2">
			<b>댓글 리스트</b>
		</div>
		
		<div id="comment_list" class="p-4 cmt-list">
			<%@ include file="/WEB-INF/views/comment_list.jsp" %>
		</div>
	</section>
	
	
	<div class="modal-main">
		<div class="modal-content">
			<div class="modal-header">
				<span class="modal-close">&times;</span>
			</div>
			<div class="modal-body">
				
			</div>
		</div>
	</div>
		


</body>
</html>
