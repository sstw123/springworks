$(function() {
	
	let last_click_tr_b_id
	
	// tr 클릭시 드롭다운 보이기
	$(document).on("click", "tbody tr[data-id]", function(event) {
		event.stopPropagation()
		
		let element = $(this)
		
		if(element.data("id") == last_click_tr_b_id && $(".dropdown").hasClass("dropdown_active")) {
			// 드롭다운 보이는 상태(active)에서 같은 tr 클릭 시 드롭다운 안보이게
			$(".dropdown").removeClass("dropdown_active")
			$(".dropdown").addClass("dropdown_inactive")

		} else {
			// 1. 드롭다운 안보이는 상태(inactive)라면 드롭다운 마우스 위치에 표시
			// 2. last_click_tr_b_id는 지금 클릭한 tr의 b_id로 변경
			let b_id = element.data("id")
			
			let mouseX = event.pageX
			let mouseY = event.pageY
			
			$(".dropdown").css({"left": mouseX, "top": mouseY})
			
			$(".dropdown").removeClass("dropdown_inactive")
			$(".dropdown").addClass("dropdown_active")
			
			/*
			// 드롭다운 버튼에 각각 data-id 추가하기
			$(".dropdown .btn_success").attr("data-id", b_id)
			$(".dropdown .btn_edit").attr("data-id", b_id)
			$(".dropdown .btn_delete").attr("data-id", b_id)
			*/
			
			// 드롭다운 박스에 data-id 추가하기, data("id", b_id)는 인식을 못하는 버그가 있기 때문에 attr("data-id", b_id)로 사용한다
			$(".dropdown").attr("data-id", b_id)
			
			last_click_tr_b_id = b_id
		}
	})
	
	// DOM에서 tbody tr을 제외한 모든 곳 클릭 시 드롭다운 안보이게(사라지게)
	$(document).click(function(e) {
		if( !$(e.target).is("tbody tr") ) {
			$(".dropdown").removeClass("dropdown_active")
			$(".dropdown").addClass("dropdown_inactive")
		}
	})
	
	// 드롭다운 완료 클릭 시
	$(document).on("click", ".btn_success", function(event) {
		let b_id = $(this).closest(".dropdown").attr("data-id")
		
		$.ajax({
			url: rootPath + "/sc_update",
			type: "POST",
			data: {b_id : b_id},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
		
		//$("tr[data-id='" + id + "'] .check").
	})
	
	// 드롭다운 수정 클릭 시
	$(document).on("click", ".btn_edit", function(event) {
		let b_id = $(this).closest(".dropdown").attr("data-id")
		
		// 리스트의 id에 해당하는 b_content 텍스트 가져오기
		let b_content = $("tr[data-id='" + b_id + "'] td.b_content").text()
		
		// 수정 창에 히든 id값과 b_content 텍스트 세팅하기
		$(".edit_form input#edit_b_id").val(b_id)
		$(".edit_form input#edit_b_content").val(b_content)
		
		// 수정 모달 창 css display 보이기
		$(".edit_modal").css("display", "block")
		
		// 수정 b_content 창 focus주기
		$(".edit_form input#edit_b_content").focus()
	})
	
	// 드롭다운 삭제 클릭 시
	$(document).on("click", ".btn_delete", function(event) {
		if(confirm("정말로 삭제하시겠습니까?")) {
			let b_id = $(this).closest("div.dropdown").attr("data-id")
			
			$.ajax({
				url: rootPath + "/delete",
				type: "POST",
				data: {b_id : b_id},
				success: function(result) {
					$(".bucket_table").html(result)
				},
				error: function(error) {
					alert("서버 통신 오류")
				}
			})
		}
	})
	
	// 드롭다운 ▲ 버튼 클릭 시
	$(document).on("click", ".btn_order_up", function(event) {
		let b_id = $(this).closest(".dropdown").attr("data-id")
		
		$.ajax({
			url: rootPath + "/order",
			type: "POST",
			data: {b_id : b_id, order : "1"},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
	
	// 드롭다운 ▼ 버튼 클릭 시
	$(document).on("click", ".btn_order_down", function(event) {
		let b_id = $(this).closest(".dropdown").attr("data-id")
		
		$.ajax({
			url: rootPath + "/order",
			type: "POST",
			data: {b_id : b_id, order : "-1"},
			success: function(result) {
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
})