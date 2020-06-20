$(function() {
	
	let last_click_tr_data_id
	
	// .use_context 클릭 시 컨텍스트 메뉴 보이기
	$(document).on("click", ".use_context", function(event) {
		event.stopPropagation()
		
		// data 함수는 동적 태그를 가져오기 못하므로 attr 함수 사용
		let data_id = $(this).attr("data-id")
		
		if(data_id == last_click_tr_data_id && $(".context").hasClass("context_active")) {
			// 새로 클릭한 data-id가 마지막으로 클릭한 닉네임 tr의 data-id와 같고 
			// 컨텍스트 메뉴가 보이는 상태(active)라면 안보이게
			$(".context").removeClass("context_active")
			$(".context").addClass("context_inactive")

		} else {
			// 1. 컨텍스트 메뉴 안보이는 상태(inactive)에서 클릭 시 컨텍스트 메뉴 마우스 위치에 표시
			let mouseX = event.pageX
			let mouseY = event.pageY
			
			$(".context").css({"left": mouseX, "top": mouseY})
			
			$(".context").removeClass("context_inactive")
			$(".context").addClass("context_active")
			
			/*
			// 컨텍스트 메뉴에 각각 data-id 추가하기
			$(".dropdown .btn_success").attr("data-id", data_id)
			$(".dropdown .btn_edit").attr("data-id", data_id)
			$(".dropdown .btn_delete").attr("data-id", data_id)
			*/
			
			// 컨텍스트 메뉴 박스에 data-id 추가하기
			$(".context").attr("data-id", data_id)
			
			// 2. last_click_tr_data_id는 지금 클릭한 tr의 data-id로 변경
			last_click_tr_data_id = data_id
		}
	})
	
	// DOM에서 .use_context를 제외한 모든 곳 클릭 시 컨텍스트 메뉴 안보이게(사라지게)
	$(document).click(function(e) {
		if( !$(e.target).is("use_context, .btn_order_up, .btn_order_down") ) {
			$(".context").removeClass("context_active")
			$(".context").addClass("context_inactive")
		}
	})
	
	// 컨텍스트 메뉴 완료 클릭 시
	$(document).on("click", ".btn_success", function(event) {
		let data_id = $(this).closest(".context").attr("data-id")
		
		$.ajax({
			url: rootPath + "/sc_update",
			type: "POST",
			data: {b_id : data_id},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
	
	// 컨텍스트 메뉴 수정 클릭 시
	$(document).on("click", ".btn_edit", function(event) {
		let data_id = $(this).closest(".context").attr("data-id")
		
		// 리스트의 id에 해당하는 b_content 텍스트 가져오기
		let b_content = $("tr[data-id='" + data_id + "'] td.b_content").text()
		
		// 수정 창에 히든 id값과 b_content 텍스트 세팅하기
		$("#text_b_content").text(b_content)
		$(".edit_form input#edit_b_id").val(data_id)
		$(".edit_form input#edit_b_content").val(b_content)
		
		// 수정 모달 창 css display 보이기
		$(".edit_modal").css("display", "block")
		
		// 수정 b_content 창 focus주기
		$(".edit_form input#edit_b_content").focus()
	})
	
	// 컨텍스트 메뉴 삭제 클릭 시
	$(document).on("click", ".btn_delete", function(event) {
		if(confirm("정말로 삭제하시겠습니까?")) {
			let data_id = $(this).closest(".context").attr("data-id")
			
			$.ajax({
				url: rootPath + "/delete",
				type: "POST",
				data: {b_id : data_id},
				success: function(result) {
					$(".bucket_table").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		}
	})
	
	// 컨텍스트 메뉴 ▲ 버튼 클릭 시
	$(document).on("click", ".btn_order_up", function(event) {
		let data_id = $(this).closest(".context").attr("data-id")
		$.ajax({
			url: rootPath + "/order",
			type: "POST",
			data: {b_id : data_id, order : "1"},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
	
	// 컨텍스트 메뉴 ▼ 버튼 클릭 시
	$(document).on("click", ".btn_order_down", function(event) {
		let data_id = $(this).closest(".context").attr("data-id")
		$.ajax({
			url: rootPath + "/order",
			type: "POST",
			data: {b_id : data_id, order : "-1"},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
})