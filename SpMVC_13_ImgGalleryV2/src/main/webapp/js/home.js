$(function() {
	var toolbar = [ [ 'style', [ 'bold', 'italic', 'underline' ] ],
			[ 'fontsize', [ 'fontsize' ] ], [ 'font Style', [ 'fontname' ] ],
			[ 'color', [ 'color' ] ], [ 'para', [ 'ul', 'ol', 'paragraph' ] ],
			[ 'height', [ 'height' ] ], [ 'table', [ 'table' ] ],
			[ 'insert', [ 'link', 'hr', 'picture' ] ],
			[ 'view', [ 'fullscreen', 'codeview' ] ] ]

	$("#btn_img_up").on("click", function() {
		document.location.href = rootPath + "/image/upload"
	})

	$("#DragAndDrop").on("dragover", function(e) {
		$("#DragAndDrop h3").text("파일을 내려놓으세요")
		return false
	})

	/* file 1개를 Drag and Drop으로 업로드하기, 업로드 되면서 e가 그 정보를 담고 있음 */
	$("#DragAndDrop").on("drop", function(e) {
		$("#DragAndDrop h3").text("파일 업로드 중")

		// drop한 파일리스트 추출
		let files = e.originalEvent.dataTransfer.files

		let filesLength = files.length

		// 멀티파일 업로드시
		if (filesLength > 1) {
			// 파일 업로드를 위한 객체 만들기
			let formData = new FormData()

			// Drop한 파일들을 모두 추가
			for (let i = 0; i < filesLength; i++) {
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
			formData.append('file', file)

			file_up(formData)
		}

		return false

	})

	$("#img_text").summernote({
		disableDragAndDrop : true,
		lang : 'ko-KR',
		placeholder : '본문을 입력하세요',
		width : '100%',
		height : '200px',
		toolbar : toolbar
	})

	var contextCallBack = function(key, options) {
		if (key == 'edit') {
			let img_seq = $(this).attr("data-seq")
			document.location.href = rootPath + "/image/update/" + img_seq
		}

		if (key == 'delete') {
			if (confirm("이미지를 삭제할까요?")) {
				// let img_seq = $(this).attr("data-seq")

				$.ajax({
					url : rootPath + "/image/delete",
					data : {
						img_seq : $(this).attr("data-seq")
					},// 앞은 controller에서 받은 변수명, 뒤는 JS에서 보낼 값
					type : 'POST',
					success : function(result) {
						if (result < 1)
							alert("삭제 도중 문제 발생")
						else
							document.location.replace(rootPath + "/")
					},
					error : function() {
						alert("서버 통신오류")
					}
				})
				// document.location.href = rootPath + "/image/delete/" +
				// img_seq
				

				// 이벤트 버블링 금지
				return false;
			}
		}
	}

	/*
	 * jQuery 응용 마우스를 제어해서 context menu를 만들어주는 tool
	 * 
	 * selector에는 어떤 태그에서 우클릭 할지 정한다 (items에 있는 edit와 delete를 만들겠다)
	 * 
	 * icon은 https://github.com/swisnl/jQuery-contextMenu/tree/master/src/icons에
	 * 있는 아이콘들임
	 */
	$.contextMenu({
		selector : '.img_card',
		items : {
			'edit' : {
				name : '수정',
				icon : 'edit'
			},
			'delete' : {
				name : '삭제',
				icon : 'delete'
			}
		},
		callback : contextCallBack
	})
	
	var img_context = function(key, option) {
		let img_seq = $(this).attr("data-seq")
		
		if(key == "delete") {
			if(confirm("이미지를 삭제할까요?")) {
				
				$.post(rootPath + "/rest/image_delete",
						{img_file_seq : $(this).attr("data-id")},
						function (result) {
							if(result == "OK") {
								// document.location.replace(rootPath + "/image/update/" + img_seq)
								document.location.replace(document.location.href)// 현재 화면 새로고침하기
							} else {
								alert("삭제 도중 문제 발생")
								document.location.replace(document.location.href)// 현재 화면 새로고침하기
							}
								
						})

//				$.ajax({
//					url : rootPath + "/image/deleteFile",
//					data : {
//						img_file_seq : $(this).attr("data-id")
//					},// 앞은 controller에서 받을 변수명, 뒤는 JS에서 보낼 값
//					type : 'POST',
//					success : function(result) {
//						if (result < 1)
//							alert("삭제 도중 문제 발생")
//						else
//							document.location.replace(rootPath + "/image/update/" + img_seq)
//					},
//					error : function() {
//						alert("서버 통신오류")
//					}
//				})

				// 이벤트 버블링 금지
				return false;
			}
		} else if (key == "copy") {
			
		} else if (key == "loading") {
			let file = $(this).find("img").attr("src")
			window.open(file, "target=_new")
		}
	}
	
	$.contextMenu({
		selector : '.img_view',
		items : {
			'loading' : {
				name : '크게보기',
				icon : 'fas fa-search'
			},
			'copy' : {
				name : '다운로드',
				icon : 'copy'
			},
			'delete' : {
				name : '삭제',
				icon : 'delete'
			},
			'search' : {
				name : '검색',
				icon : 'fas fa-search'
			}
		},
		callback : img_context
	})

})