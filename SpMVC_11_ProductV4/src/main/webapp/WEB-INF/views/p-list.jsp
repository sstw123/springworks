<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<script>
	$(function() {
		// jQuery 1.4.3 전버전에서 사용하던 코드
		// $("img.sub-img").live(function())
		
		// jQuery 1.7 이상에서 사용하던 코드
		// $("img.sub-img").on("click", function())
		
		// 현재 버전에서 사용하는 코드
		// 동적 태그 : 스크립트로 임의로 추가(append)한 태그
		// jQuery에서 동적으로 생성한 태그에 이벤트를 주는 방법
		$(document).on("click", "p.img-delete", function() { // html 문서에 click 이벤트를 설정하고 클릭된 요소가 img.sub-img인지 확인하기
			let origin_file_name = $(this).attr("data-originName")
			
			if(confirm(origin_file_name + " 이미지를 삭제할까요?")) {
				let file_seq = $(this).attr("data-id")
				
				$.ajax({
					url : "${rootPath}/rest/subImgDelete",
					data : {file_seq : file_seq},
					success : function(result) {
						getProductInfo(result)
					}
				
				})
				
				// document.location.href = "${rootPath}/subImgDelete?file_seq=" + file_seq
			}
		})
		
		$(document).on("click", "p.img-view", function() {
			let upload_file_name = $(this).attr("data-uploadName")
			
			window.open("${rootPath}/files/" + upload_file_name, "_new","toolbar=no, scrollbars=no")
			
		})
		
		// 이미 만들어진 태그에 이벤트를 주는 방법
		$(".p-row").click(function() {
			let p_code = $(this).attr("data-id")
			getProductInfo(p_code)
		})
			
		var getProductInfo = function(p_code){
			$.ajax({
				// "getProduct?p_code" + p_code
				// url : "${rootPath}/getProduct",
				url : "${rootPath}/rest/getProduct",
				data : {p_code : p_code}, // 앞의 p_code는 쿼리의 변수명, 뒤의 p_code는 js의 변수명
				dataType : 'json',
				
				success : function(result) {
					$("#p_code").val(result.p_code)
					$("#p_name").val(result.p_name)
					$("#p_iprice").val(result.p_iprice)
					$("#p_oprice").val(result.p_oprice)
					$("#p_file").val(result.p_file)
					
					let profileImage = "${rootPath}/files/noimage.png"
					
					if(result.p_file != null) {
						profileImage = "${rootPath}/files/" + result.p_file
					}
					$("#p_image").attr("src", profileImage)
					
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
								src : "${rootPath}/files/" + file.file_upload_name
							})
							
							var p_delTag = $("<p/>",{
								"class" : "img-delete",
								"text" : "삭제",
								"data-id" : file.file_seq,
								"data-originName" : file.file_origin_name
							})
							
							var p_viewTag = $("<p/>",{
								"class": "img-view",
								"text" : "미리보기",
								"data-id" : file.file_seq,
								"data-originName" : file.file_origin_name,
								"data-uploadName" : file.file_upload_name
							})
							
							$("#p_sub_list").append(
								$("<div/>",{"class":'container'}).append(
									imgTag,
									$("<div/>",{"class":'over'}).append(
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
			// document.location.href = "${rootPath}/getProduct?p_code=" + p_code
		}
	})
</script>
<style>
	td, th {
		white-space: nowrap;
	}
	
	div.container {
		border: 1px solid black;
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	div.container .over {
		position: absolute;
		top: 0;
		bottom: 0;
		left: 0;
		right: 0;
		opacity: 0;
		transition: 0.5s ease;
		background-color: #008CBA;
	}
	
	div.container:hover .over {
		opacity: .6;
	}
	
	p.img-delete, p.img-view {
		border: 1px solid red;
		display: inline-block;
		width: 150px;
		color: white;
		font-size: 20px;
		cursor: pointer;
	}
	
	p.img-delete:hover, p.img-view:hover {
		text-decoration: underline;
		color: yellow;
	}
</style>
<body>
	<table class="p-list table table-striped table-hover">
		<thead class="thead-dark">
			<tr>
				<th>상품코드</th>
				<th>상품명</th>
				<th>매입단가</th>
				<th>매출단가</th>
				<th>대표이미지</th>
				<th>보조이미지</th>
			</tr>
		</thead>
		
		<c:forEach items="${PLIST}" var="dto">
			<tr class="p-row" data-id="${dto.p_code}">
				<td>${dto.p_code}</td>
				<td>${dto.p_name}</td>
				<td>${dto.p_iprice}</td>
				<td>${dto.p_oprice}</td>
				<td><c:if test="${!empty dto.p_file}">O</c:if></td>
				<td><c:if test="${!empty dto.p_files}">O ( DB 테이블에 저장된 파일명: ${fn:length(dto.p_files)}개 )</c:if></td>
			</tr>
		</c:forEach>
		
	</table>
</body>
</html>