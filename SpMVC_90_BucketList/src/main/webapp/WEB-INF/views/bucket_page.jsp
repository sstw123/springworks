<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}"/>
<c:set var="isEditing" value="false" />
<style>
	/* bucket_page css */
	.bucket_page {
		width: 768px;
		margin: 0 auto;
	}
	
	/* dropdown css */
	.dropdown {
		position: absolute;
		border: 1px solid black;
		border-radius: 10px;
		width: 100px;
		text-align: center;
		background-color: white; 
	}
	.dropdown ul {
		list-style: none;
	}
	.dropdown li {
		padding: 8px 0;
	}
	.dropdown li:hover {
		background-color: skyblue;
		cursor: pointer;
	}
	.dropdown li:first-child:hover{
		border-top-left-radius: 10px;
		border-top-right-radius: 10px;
	}
	.dropdown li:last-child:hover{
		border-bottom-left-radius: 10px;
		border-bottom-right-radius: 10px;
	}
	
	.dropdown_inactive {
		display: none;
	}
	.dropdown_active {
		display: block;
	}
	
	/* add_box css */
	.add_box {
		display: flex;
		margin: 0 auto;
		margin-top: 20px;
	}
	.input_add {
		display: block;
		width: 70%;
		
		/* flex: 1; */
		flex-grow: 1;
		flex-shrink: 1;
		flex-basis: 0;
		
		margin-left: 13%;
		margin-right: 3%;
	}
	@keyframes example {
		from {
			background-color: red;
		}
		to {
			background-color:yellow;
		}
	}
	.btn_add {
		display: block;
		margin-left: auto;
		
		padding: 10px 0;
		width: 10%;
		text-align: center;
		
		background-color: white;
		border: 1px solid skyblue;
		border-radius: 3px;
		color: skyblue;
		
		cursor: pointer;
	}
	.btn_add:hover {
		background-color: skyblue;
		color: white;
	}
	.btn_add:active .input_add {
		animation-name: example;
		animation-duration: 0.1s;
	}
</style>
<script>
	$(function() {
		
		let last_click_tr_b_id
		
		$(document).on("click", "tbody tr", function(event) {
			event.stopPropagation()
			
			if($(this).data("id") == last_click_tr_b_id && $(".dropdown").hasClass("dropdown_active")) {
				// 드롭다운 보이는 상태(active)에서 같은 tr 클릭 시 드롭다운 안보이게
				
				$(".dropdown").removeClass("dropdown_active")
				$(".dropdown").addClass("dropdown_inactive")

			} else {
				// 1. 드롭다운 안보이는 상태(inactive)라면 드롭다운 마우스 위치에 표시
				// 2. last_click_tr_b_id는 지금 클릭한 tr의 b_id로 변경
				
				let b_id = $(this).data("id")
				
				let mouseX = event.pageX
				let mouseY = event.pageY
				
				$(".dropdown").css({"left": mouseX, "top": mouseY})
				
				$(".dropdown").removeClass("dropdown_inactive")
				$(".dropdown").addClass("dropdown_active")
				
				$(".dropdown .btn_success").data("id", b_id)
				$(".dropdown .btn_edit").data("id", b_id)
				$(".dropdown .btn_delete").data("id", b_id)
				
				last_click_tr_b_id = b_id
			}
		})
		
		// tbody tr을 제외한 모든 곳 클릭 시 드롭다운 안보이게
		$(document).click(function(e) {
			if( !$(e.target).is("tbody tr") ) {
				$(".dropdown").removeClass("dropdown_active")
				$(".dropdown").addClass("dropdown_inactive")
			}
		})
		
		
		// 추가버튼 클릭 시
		$(document).on("click", ".btn_add", function(event) {
			//$(".input_add").toggleClass("input_add_active")
			$.ajax({
				url: "${rootPath}/save",
				type: "POST",
				data: $("form.add_box").serialize(),
				success: function(result) {
					$(".bucket_list").html(result)
					$(".input_add").val("")
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		})
		
		// 드롭다운 완료 클릭 시
		$(document).on("click", ".btn_success", function(event) {
			let b_id = $(this).data("id")
			
			$.ajax({
				url: "${rootPath}/sc_update",
				type: "POST",
				data: {b_id : b_id},
				success: function(result) {
					$(".bucket_list").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
			
			//$("tr[data-id='" + id + "'] .check").
		})
		
		// 드롭다운 수정 클릭 시
		$(document).on("click", ".btn_edit", function(event) {
			let b_id = $(this).data("id")
			
			$("tr[data-id='" + b_id + "'] td.input_box").css("display", "flex")
			$("tr[data-id='" + b_id + "'] td.b_content").css("display", "none")
		})
		
		// 드롭다운 삭제 클릭 시
		$(document).on("click", ".btn_delete", function(event) {
			if(confirm("정말로 삭제하시겠습니까?")) {
				let b_id = $(this).data("id")
				
				$.ajax({
					url: "${rootPath}/delete",
					type: "POST",
					data: {b_id : b_id},
					success: function(result) {
						$(".bucket_list").html(result)
					},
					error: function(error) {
						alert("서버 통신 오류")
					}
				})
			}
		})
		
	})
</script>

<article class="bucket_page">
	<div class="bucket_list">
		<%@ include file="/WEB-INF/views/bucket_list.jsp" %>
	</div>
	
	<form class="add_box">
		<input class="input_add" name="b_content"/>
		<button class="btn_add" type="button">추가</button>
	</form>
	
	<div class="dropdown dropdown_inactive">
		<ul>
			<li class="btn_success">완료</li>
			<li class="btn_edit">수정</li>
			<li class="btn_delete">삭제</li>
		</ul>
	</div>
</article>