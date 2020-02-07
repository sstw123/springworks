$(function() {
	
	$("#btn_input_submit").on("click", function() {
		
		if($("#frd_name").val() == "") {
			alert("이름을 입력하세요")
			$("#frd_name").focus()
			return false
		}
		
		$("#frd_input_form").submit()
	})
	
	$("#btn_edit_submit").on("click", function() {
		
		if($("#frd_name").val() == "") {
			alert("이름을 입력하세요")
			$("#frd_name").focus()
			return false
		}
		$("#frd_edit_form").submit()
		
	})
})