$(function(){
	$(".naver_login").on("click", function() {
		document.location.href = rootPath + "/member/naver"
	})
	
	$("#btn-join").on("click", function() {
		document.location.href = rootPath + "/member/join"
	})
	
	$("#btn-login").on("click", function() {
		
		// 유효성 검사
		// id, pw가 입력되지 않았을 때 경고
		if($("#u_id").val() == "") {
			alert("아이디를 입력하세요")
			$("#u_id").focus()
			return false;
		} else if($("#u_pw").val() == "") {
			alert("비밀번호를 입력하세요")
			$("#u_pw").focus()
			return false;
		}

		
		$.post(
				rootPath + "/rest/member/login",
				$("form").serialize(),
				function(result) {
					if(result == 'LOGIN_OK') {
						document.location.href = document.location.href
					} else if (result == 'LOGIN_FAIL') {
						alert("아이디나 비밀번호가 일치하지 않습니다")
						return false
					}
		})
		
		/*
		$.ajax({
				url : rootPath + "/rest/member/login",
				type : 'POST',
				data : $("form").serialize(),
				success : function(result) {
					document.location.href = document.location.href
				},
				error : function(error) {
					alert("아이디나 비밀번호가 일치하지 않습니다")
					return false
				}
		})
		*/
	})
})