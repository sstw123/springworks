$(function() {
	$(".edit_form").submit(function() {
		if($("#edit_b_content").val() == "") {
			alert("내용을 입력하세요!")
			$("#edit_b_content").focus()
			return false
		}
		
		$.ajax({
			url: rootPath + "/save",
			type: "POST",
			data: $("form.edit_form").serialize(),
			success: function(result) {
				$(".edit_modal").css("display", "none")
				$(".bucket_table").html(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
		
		// submit 방지
		return false
	})
	
	$("span#close").click(function() {
		$(".edit_modal").css("display", "none")
	})
})