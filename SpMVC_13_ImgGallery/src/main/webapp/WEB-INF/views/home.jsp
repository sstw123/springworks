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
	
	<style>
		* {
			box-sizing: border-box;
			margin: 0px;
			padding: 0px;
		}
		
		body {
			width: 100%;
		}
		
		header {
			background-color: rgb(52, 152, 219);
			margin: 0;
			padding: 1rem;
			color: white;
		}
		
		section {
			width: 90%;
			margin: 10px auto;
		}
		
		#img_box {
			margin: 10px auto;
			display: flex;
			flex-wrap: wrap;
		}
		
		.img_panel {
			padding: 0.01rem 16px;
			margin-top: 16px;
			margin-bottom: 16px;
		}
		
		.img_card {
			width: 200px;
			height: 200px;
			margin: 10px;
			box-shadow: 0 4px 10px 0 rgba(0, 0, 0, 0.16),
						0 4px 20px 0 rgba(0, 0, 0, 0.19);
			
			/* 이미지가 카드박스보다 클 때 이미지 자르기 */
			overflow: hidden;
			
			display: flex;
			justify-content: center;
			flex-flow: column;
		}
		
		.img_card .img_title {
			padding: 0.5rem;
			text-align: center;
		}
		
		#btn_save {
			text-align: center;
		}
		
		.bz_button {
			display: inline-block;
			padding: 8px 16px;
			vertical-align: middle;
			text-decoration: none;
			color: white;
			background-color: blue;
			text-align: center;
			cursor: pointer;
			white-space: nowrap;
			margin: 5px;
		}
		
		.bz_button:hover {
			box-shadow: 0 8px 16px 0 rgba(0, 0, 0, 0.2),
						0 6px 20px 0 rgba(0, 0, 0, 0.2);
		}
		
		div.input_box {
			width: 90%;
			margin: 5px auto;
		}
		
		input[type="text"] {
			padding: 8px;
			display: block;
			border: none;
			border-bottom: 1px solid #ccc;
			width: 100%;
			margin: 8px auto;
		}
		
		div#DragAndDrop {
			width: 100%;
			height: 300px;
			color: #aaa;
			border: 1px solid black;
			display: flex;
			justify-content: center;
			align-items: center;
		}
		
		#img_title {
			margin: 10px auto;
		}
		
		#img_view {
			display: none;
		}
	</style>
	<script>
		// js 파일에서 사용할 public 변수 선언
		var rootPath = "${rootPath}"
	</script>
	<script src="${rootPath}/js/image_upload.js"></script>
	<script src="${rootPath}/js/images_upload.js"></script>
	<script>
		$(function() {
			var toolbar = [
				['style', ['bold','italic','underline']],
				['fontsize', ['fontsize']],
				['font Style',['fontname']],
				['color',['color']],
				['para',['ul','ol','paragraph']],
				['height',['height']],
				['table',['table']],
				['insert',['link','hr', 'picture']],
				['view',['fullscreen','codeview']]
			]
			
			$("#btn_img_up").on("click", function() {
				document.location.href = "${rootPath}/image/upload"
			})
			
			$("#DragAndDrop").on("dragover", function(e) {
				$("#DragAndDrop h3").text("파일을 내려놓으세요")
				return false
			})
			
			/* file 1개를 Drag and Drop으로 업로드하기, 업로드 되면서 e가 그 정보를 담고 있음*/
			$("#DragAndDrop").on("drop", function(e) {
				$("#DragAndDrop h3").text("파일 업로드 중")
				
				// drop한 파일리스트 추출
				let files = e.originalEvent.dataTransfer.files
				
				let filesLength = files.length
				
				// 멀티파일 업로드시
				if(filesLength > 1) {
					// 파일 업로드를 위한 객체 만들기
					let formData = new FormData()
					
					// Drop한 파일들을 모두 추가
					for(let i = 0; i < filesLength; i++) {
						formData.append('files', files[i])
					}
					
					alert("파일 개수 : " + filesLength)
					files_up(formData)
					
					return false
					
				// 단일파일 업로드시
				} else {
					// 리스트에서 첫 번째 파일만 추출
					let file = files[0]
					alert(file.name)
					
					// 추출된 파일 정보를 서버에 먼저 업로드
					
					
					// js FormData 클래스를 사용해서 서버에 파일 업로드 준비
					let formData = new FormData()
					formData.append('file',file)
					
					file_up(formData)
				}
				
				return false
				
			})
			
			$("#img_text").summernote({
				disableDragAndDrop: true,
				lang: 'ko-KR',
				placeholder: '본문을 입력하세요',
				width: '100%',
				height: '200px',
				toolbar: toolbar
			})
			
			var contextCallBack = function(key, options) {
				if(key == 'edit') {
					let img_seq = $(this).attr("data-seq")
					document.location.href = "${rootPath}/image/update/" + img_seq
				}
				
				if(key == 'delete') {
					if(confirm("이미지를 삭제할까요?")) {
						let img_seq = $(this).attr("data-seq")
						
						$.ajax({
							url: "${rootPath}/image/delete",
							data: {img_seq : $(this).attr("data-seq")},//앞은 controller에서 받은 변수명, 뒤는 JS에서 보낼 값
							type: 'POST',
							success: function(result) {
								if(result < 1)
									alert("삭제 도중 문제 발생")
							},
							error: function() {
								alert("서버 통신오류")
							}
						})
						// document.location.href = "${rootPath}/image/delete/" + img_seq
						document.location.replace("${rootPath}/")
						
						// 이벤트 버블링 금지
						return false;
					}
				}
			}
			
			/*
				jQuery 응용
				마우스를 제어해서 context menu를 만들어주는 tool
				
				selector에는 어떤 태그에서 우클릭 할지 정한다
				(items에 있는 edit와 delete를 만들겠다)
			
				icon은 https://github.com/swisnl/jQuery-contextMenu/tree/master/src/icons에 있는 아이콘들임
			*/
			$.contextMenu({
				selector: '.img_card',
				items: {
					'edit' : {name: '수정', icon: 'edit'},
					'delete' : {name: '삭제', icon: 'delete'}
				},
				callback: contextCallBack
			})
			
		})
	</script>
</head>
<body>
	<header onclick='location.href="${rootPath}/"' style="cursor:pointer">
		<h3>이미지 갤러리</h3>
	</header>
	
	<section>
		<c:if test="${BODY == 'UPLOAD'}">
			<%@ include file="/WEB-INF/views/body/img_upload.jsp" %>
		</c:if>
	</section>
	
	<section id="img_box">
		<c:forEach items="${imageList}" var="imageVO">
			<%@ include file="/WEB-INF/views/include/img_card.jsp" %>
		</c:forEach>
	</section>
	
	<section id="btn_save">
		<button id="btn_img_up" class="bz_button">이미지 올리기</button>
	</section>
</body>
</html>