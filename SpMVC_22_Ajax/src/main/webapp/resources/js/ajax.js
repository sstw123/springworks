$(function() {
	// input box 입력한 문자열을 서버로 전송하고
	// 서버에서 받은 값에 따라 응답 처리
	$("#call_msg").click(function() {
		$.ajax({
			url: rootPath + "/ajax/msg",
			type: "POST",
			data: { msg : $("#msg").val() },
			success: function(result) {
				if(result == "NOT KOREA") {
					alert("서버에 전달한 문자열이 잘못되었음")
				} else {
					alert(result)
				}
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
	
	$(document).on("click", "#call_addr", function() {
		$.ajax({
			url: rootPath + "/ajax/addr",
			type: "POST",
			success: function(result) {
				// json형 객체를 문자열로 변환
				alert(JSON.stringify(result))
				
				// json형 객체의 값을 HTML tag로 묶어서 DOM 문서를 만들고
				// section#sub에 html 코드로 넣기
				let addr = "<div>" + result.a_name + "</div>"
				+ "<div>" + result.a_addr + "</div>"
				+ "<div>" + result.a_tel + "</div>"
				+ "<div>" + result.a_age + "</div>"
				
				$("#sub").html(addr)
			},
			error: function() {
				alert("서버 통신 오류")
			}
		})
	})
	
	$(document).on("click", "#call_addr_view", function() {
		$.ajax({
			url: rootPath + "/ajax/addr_view",
			type: "get",
			success: function(result) {
				$("#sub").html(result)
			}
		})
	})
	
	/*
	 	톰캣은 기본적으로 GET,POST는 사용할 수 있지만
	 	PUT, DELETE 등의 RESTFul 메소드는 사용할 수 없도록 설정되어 있다
	 */
	$(document).on("click", "#a_name", function() {
		$.ajax({
			url: rootPath + "/ajax/put",
			type: "put",
			data: { msg: $(this).text() },
			success: function(result) {
				alert(result)
			},
			error: function(error) {
				alert("서버 통신 오류")
			}
		})
	})
})