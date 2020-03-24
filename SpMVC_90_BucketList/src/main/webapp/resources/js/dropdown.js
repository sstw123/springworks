$(function() {
	// DOM에서 tbody tr을 제외한 모든 곳 클릭 시 드롭다운 안보이게(사라지게)
	$(document).click(function(e) {
		if( !$(e.target).is("tbody tr") ) {
			$(".dropdown").removeClass("dropdown_active")
			$(".dropdown").addClass("dropdown_inactive")
		}
	})
	
	// 드롭다운 완료 클릭 시
	$(document).on("click", ".btn_success", function(event) {
		let b_id = $(this).data("id")
		
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
		let b_id = $(this).data("id")
		
		let b_content = $("tr[data-id='" + b_id + "'] td.b_content").text()
		
		$(".edit_form input#edit_b_id").val(b_id)
		$(".edit_form input#edit_b_content").val(b_content)
		
		$(".edit_modal").css("display", "block")
	})
	
	// 드롭다운 삭제 클릭 시
	$(document).on("click", ".btn_delete", function(event) {
		if(confirm("정말로 삭제하시겠습니까?")) {
			let b_id = $(this).data("id")
			
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
})