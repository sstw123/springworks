$(function() {
	$("#search-input").on("search", function(){
		$("#search-form").submit()
	})
	
	$("#pro-img-delete").click(function() {
		if(confirm("대표이미지를 삭제할까요?")) {
			let p_code = $("#p_code").val()
			document.location.href = rootPath + "/productImgDelete?p_code=" + p_code
			
		}
	})
	
	// jQuery 1.4.3 전버전에서 사용하던 코드
	// $("img.sub-img").live(function())
	
	// jQuery 1.7 이상에서 사용하던 코드
	// $("img.sub-img").on("click", function())
	
	// 현재 버전에서 사용하는 코드
	// jQuery에서 동적으로 생성한 태그에 이벤트를 주는 방법
	// 동적 태그 : 스크립트로 임의로 추가(append)한 태그
	$(document).on("click", "span.img-delete", function() { // html 문서에 click 이벤트를 설정하고 클릭된 요소가 img.sub-img인지 확인하기
		let origin_file_name = $(this).attr("data-originName")
		
		if(confirm(origin_file_name + " 이미지를 삭제할까요?")) {
			let file_seq = $(this).attr("data-id")
			
			$.ajax({
				url : rootPath + "/rest/subImgDelete",
				data : {file_seq : file_seq},
				success : function(result) {
					getProductInfo(result)
				}
			
			})
			
			// document.location.href = rootPath + "/subImgDelete?file_seq=" + file_seq
		}
	})
	
	$(document).on("click", ".img-view", function() {
		let upload_file_name = $(this).attr("data-uploadName")
		window.open(rootPath + "/files/" + upload_file_name, "_new","toolbar=no, scrollbars=no")
	})
	
	// 이미 만들어진 태그에 이벤트를 주는 방법
	$("tr.p-row").click(function() {
		let p_code = $(this).attr("data-id")
		
		// 현재 클릭된 tr 전체 DOM을 tr 변수에 담기
		let tr = $(this)
		
		// tr이 담고있는 td를 배열로 생성하라
		let tds = tr.children()
		
		let tp_code = tds.eq(0).text()
		let tp_name = tds.eq(1).text()
		let tp_iprice = tds.eq(2).text()
		let tp_oprice = tds.eq(3).text()
		
		$("#p_code").val(tp_code)
		$("#p_name").val(tp_name)
		$("#p_iprice").val(tp_iprice)
		$("#p_oprice").val(tp_oprice)
		
		$.ajax({
			url : rootPath + "/getFiles",
			data : {p_code : p_code},
			success : function(result) {
				$("#p_sub_list").html("")
				$("#p_sub_list").html(result)
			}
		})
		
		// getProductInfo(p_code)
	})
		
	var getProductInfo = function(p_code){
		$.ajax({
			// "getProduct?p_code" + p_code
			// url : rootPath + "/getProduct",
			url : rootPath + "/rest/getProduct",
			data : {p_code : p_code}, // 앞의 p_code는 쿼리의 변수명, 뒤의 p_code는 js의 변수명
			dataType : 'json',
			
			success : function(result) {
				$("#p_code").val(result.p_code)
				$("#p_name").val(result.p_name)
				$("#p_iprice").val(result.p_iprice)
				$("#p_oprice").val(result.p_oprice)
				$("#p_file").val(result.p_file)
				
				let profileImage = rootPath + "/files/noimage.png"
				
				if(result.p_file != null) {
					profileImage = rootPath + "/files/" + result.p_file
				}
				$("#p_image").attr("src", profileImage)
				$("#pro-img-search").attr("data-uploadName", result.p_file)
				
				if(result.p_vat == "1") {
					$("#p_vat").attr("checked", "checked")
				} else {
					$("#p_vat").attr("checked", "")
				}
				
				$("#p_sub_list").html("") // 초기화
				if(result.p_files.length > 0) {
					
					result.p_files.forEach(function(file) {
						var imgTag = $("<img/>",{
							width : '100px',
							height : '100px',
							margin: '10px',
							"class" : 'sub-img',
							"data-id" : file.file_seq,
							"data-originName" : file.file_origin_name,
							"alt" : file.file_origin_name,
							src : rootPath + "/files/" + file.file_upload_name
						})
						
						var p_delTag = $("<span/>",{
							"class" : "img-delete",
							"data-id" : file.file_seq,
							"data-originName" : file.file_origin_name
						}).append( $("<i/>",{"class":"fa fa-trash"}) )
						
						var p_viewTag = $("<span/>",{
							"class": "img-view",
							"data-id" : file.file_seq,
							"data-originName" : file.file_origin_name,
							"data-uploadName" : file.file_upload_name
						}).append( $("<i/>",{"class":"fa fa-search"}) )
						
						$("#p_sub_list").append(
							$("<div/>",{"class":"sub-img-box"}).append(
								imgTag,
								$("<div/>",{"class":"img-menu"}).append(
									p_delTag
									,
									p_viewTag
								)
							)
						)
					})
					
				}
					
			},
			error : function(error) {
				alert("서버 통신 오류")
			}
			
		})
		// document.location.href = rootPath + "/getProduct?p_code=" + p_code
	}
	
	if(bStart == 0) {
		// input box에 있는 상품코드
		var p_code = $("#p_code").val()
		
		// 없으면 리스트의 첫번째 항목에서 추출한 상품코드
		if(p_code == "") {
			// list table의 첫번째 tr 가져오기
			let tr = $("tr.p-row:eq(0)")
			p_code = tr.attr("data-id")
		}
		// 상품코드를 getProInfo() 함수에 전달하여 input box의 값을 초기화시키기
		getProductInfo(p_code)
		bStart = 1;
	}

	
})