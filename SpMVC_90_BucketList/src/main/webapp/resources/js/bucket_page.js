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
			
			$(".dropdown .btn_success").data("id", b_id)
			$(".dropdown .btn_edit").data("id", b_id)
			$(".dropdown .btn_delete").data("id", b_id)
			
			last_click_tr_b_id = b_id
		}
	})
	
	// add_form submit 시
	$(document).on("submit", ".add_form", function(event) {
		
		if($("#add_b_content").val() == "") {
			alert("내용을 입력하세요!")
			$("#add_b_content").focus()
			return false
		}
		
		$.ajax({
			url: rootPath + "/save",
			type: "POST",
			data: $("form.add_form").serialize(),
			success: function(result) {
				$(".bucket_table").html(result)
				$("#add_b_content").val("")
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
		
		// submit 방지
		return false
	})
	
})