$(function() {
			
	// 생년월일 숫자이외 입력불가능 jQuery
	$("#birth").on("keyup", function(e) {
		$(this).val( $(this).val().replace(/[^0-9]/g, "") )
	})
	
	// 남성 여성 버튼 클릭시 반응 jQuery
	$("button.gender").click(function() {
		// 1. 눌리지 않은 버튼을 클릭했을 경우
		if($(this).attr("data-gender") == "0") {
			$("button.gender").attr("data-gender", "0") // 다른 버튼이 눌려있을 경우 data-gender 속성값 전부 0으로 초기화
			$(this).attr("data-gender", "1") // 클릭한 버튼 data-gender 값 1로 변경해서 css로 누른 효과 적용시키기
			
			// 1-1. 클릭된 버튼이 남성일 경우
			if($(this).attr("id") == "male") {
				$("input#gender").val("male")
			// 1-2. 클릭된 버튼이 여성일 경우
			} else if ($(this).attr("id") == "female") {
				$("input#gender").val("female")
			}
		// 2. 눌려있는 버튼을 클릭했을 경우
		} else if($(this).attr("data-gender") == "1") {
			$("button.gender").attr("data-gender", "0") // data-gender 속성값 전부 0으로 초기화해서 값 1짜리 css 적용 안되도록
			$("input#gender").val("") // input#gender에 성별값 ""로 세팅
		}
	})
	
	// 유형에서 장애 선택시에만 보이도록
	// 장애 선택 안했을 경우 안보이도록+무력화
	$("select#charTrgterArray").change(function() {
		// 유형으로 장애 선택시
		if($(this).val() == "004") {
			$("div.disorder").removeClass("blind")
			$("select#obstKiArray").removeAttr("disabled")
			$("select#obstAbtArray").removeAttr("disabled")
		// 유형으로 장애 외 선택시
		} else {
			//if(!$("div.disorder").hasClass("blind")) {
			//	$("div.disorder").addClass("blind")
			//}
			$("select#obstKiArray").attr("disabled", "true")
			$("select#obstAbtArray").attr("disabled", "true")
		}
	})
	
	// 검색어 열기 접기
	$("p.more").click(function() {
		
		if($("p.more").hasClass("close")) {
			$("span#open").text("(접기)")
			// $("div.searchWord").css("visibility", "visible")
			$("div.searchWord").removeClass("blind")
			$("p.more").removeClass("close")
		} else {
			$("span#open").text("(열기)")
			// $("div.searchWord").css("visibility", "hidden")
			$("div.searchWord").addClass("blind")
			$("p.more").addClass("close")
		}
	})
	
	// 조회 버튼 클릭시
	$("#btn-search").click(function() {
		
		let birth = $("#birth").val()
        
        // 생년월일 8자리 아니면 경고창 + 포커스 + jQuery 중단 
        if(birth.length != 8) {
        	alert("생년월일 8자리를 입력하세요")
        	$("#birth").focus()
        	return false
        	
        // 생년월일은 8자리이지만 월이나 일이 잘못되었을 경우
        } else if (birth.substring(4,6) > 12 || birth.substring(4,6) < 1 || birth.substring(6,8) > 31 || birth.substring(6,8) < 1) {
        	alert("올바른 생년월일을 입력하세요")
        	$("#birth").focus()
        	return false
        }
		
		$("form").submit()
	})
})