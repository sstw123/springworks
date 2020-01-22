jQuery(document).ready(function() {
	
	/*
	 * 키보드 이벤트 : 입력 box에 키보드로 뭔가를 입력할 때마다 발생하는 이벤트
	 * 
	 * keypress : 문자열이 입력되어서 컴퓨터로 입력되는 순간
	 *  - 어떤 문자열이 입력되었는지 캐치하기 위한 이벤트 (제일 많이 사용)
	 *  
	 * keydown : 키보드를 누르는 순간
	 * 
	 * keyup : 키보드를 눌렀다가 뗀 순간
	 * 
	 */
	$("input#pcode").keypress(function( event ) {
		// keypress 함수
		// 키보드에서 어떤 문자를 입력하면 event의 keyCode라는 속성에
		// 문자의 ASCII 코드 값이 저장되어 전달된다
		if(event.keyCode == 13) { // ASCII Code의 13: Enter 키, 28: Esc 키
			
			let inputPCode = $(this).val()
			
			let query = rootPath + "/product/search?text=" + inputPCode
			
			let status = "toolbar=no, scrollbars=yes, resizable=no, top=300, left=500, width=700, height=700" 
			
			// window.open(주소, 새창여부, 페이지옵션)
			window.open(query, "_new", status)
			
		}
	})
	
	// 입력박스에서 키 입력감지 
	$("input#dcode").keypress(function(e) {
		// 입력된 키가 엔터일 경우
		if(e.keyCode == 13) {
			// input#dcode에 입력된 값 추출
			let inputDCode = $(this).val()
			
			let query = rootPath + "/dept/search?text=" + inputDCode
			
			let options = "toolbar=no, scrollbars=yes, resizable=no, top=200, left=200, width=500, height=500"
			
			// 새 창 열기
			window.open(query, "new", options)
		}
	})
	
	var calc = function() {
		let price = parseInt($(this).val())
		let qty = parseInt($("input#qty").val())
		let total = price * qty
		
		$("input#total").val(total)
	}
	
	// on 함수
	// 이벤트 여러개를 동시에 묶어서 사용할 때 사용한다
	$("input#price").on("change keyup paste input propertychange", calc)
	$("input#qty").on("change keyup paste input propertychange", calc)
	
	// --------------------
	
	$("btn-save").click(function() {
		let d_code = $("#dcode").val()
		if(d_code == "") {
			alert("거래처 코드는 반드시 입력해야 합니다")
			$("#dcode").focus()
			return false
		}
		
		let p_code = $("#pcode").val()
		if(p_code == "") {
			alert("상품 코드는 반드시 입력해야 합니다")
			$("#pcode").focus()
			return false
		}
		
		if( confirm("저장할까요?") ) {
			$(form).submit()
		}
	})
	
	
})