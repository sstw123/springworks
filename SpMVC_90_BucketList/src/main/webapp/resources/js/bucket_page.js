$(function() {
	
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