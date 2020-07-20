<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	
	<%@ include file="/WEB-INF/views/include/include_head.jspf" %>
	
	<!-- include summernote css/js -->
	<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.css" rel="stylesheet">
	<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.15/dist/summernote-lite.min.js"></script>
	<script src="${rootPath}/js/summernote-ko-KR.js"></script>

	<script>var rootPath = "${rootPath}"</script>
	
	<script>
		$(function() {
			let star = "${RBOOK.rb_star}"
			if(star == "") {
				star = 1
			} else {
				star = parseInt(star)
			}
			star = star * 10
			
			$("span.star_red").css("width", star + "%")
			
			$("button").on("click", function() {
				let btn_id = $(this).attr("id")
				let url = "${rootPath}/rbook/"
				
				if(btn_id == "btn_update") {
					url += "update/${RBOOK.rb_seq}"
				} else if(btn_id == "btn_delete") {
					if(!confirm("독서록을 삭제할까요?")) {
						return false
					}
					url += "delete/${RBOOK.rb_seq}"
				} else if(btn_id == "btn_list") {
					url += "list"
				}
				
				document.location.href = url
				
			})
			
		})
	</script>
	<style>
		article {
			display: flex;
			flex-flow: wrap;
		}
		
		div {
			display: inline-block;
			width: 70%;
			padding: 5px 16px;
			margin: 5px;
			background-color: #eee;
		}
		
		div.title_box {
			width: 24%;
			background-color: #ccc;
			text-align: right;
		}
		
		span.star_box {
			width: 205px;
		}
		
		span.star_box, span.star_red {
			display: inline-block;
			height: 20px;
			overflow: hidden;
			
			background: url("${rootPath}/image/star.png") no-repeat;
			/*배경이미지 width: 자동, height: 40px*/
			background-size: auto 40px;
		}
		
		span.star_red {
			/* 배경이미지를 채울 때, 왼쪽 아래 꼭지점을 기준으로 배치*/
			background-position: left bottom;
			line-height: 0;
			vertical-align: top;
			width: 50%;
		}
		
		div.text_box {
			width: 100%;
		}
	</style>
</head>
<body>
	<header>
		<h2>My Read Book</h2>
	</header>
	
	<%@ include file="/WEB-INF/views/include/include_nav.jspf" %>
	
	<section>
		<article>
			<%@ include file="/WEB-INF/views/books/book_view.jsp" %>
		</article>
		
		<article>
			<div class="title_box">읽은 때</div>
			<div>${RBOOK.rb_date}, ${RBOOK.rb_stime}부터 ${RBOOK.rb_rtime}시간동안 읽음</div>
			
			<div class="title_box">한줄평</div>
			<div>${RBOOK.rb_subject},
				<span class="star_box">
					<span class="star_red"></span>
				</span>
			</div>
			
			<div class="text_box">
				${RBOOK.rb_text}
			</div>
		</article>
		
		<article>
			<button type="button" id="btn_update" class="flex_right biz_blue">수정</button>
			<button type="button" id="btn_delete" class="biz_red">삭제</button>
			<button type="button" id="btn_list" class="biz_orange">리스트보기</button>
		</article>
	</section>
	
	<%@ include file="/WEB-INF/views/rbooks/list_body.jsp" %>
</body>
</html>