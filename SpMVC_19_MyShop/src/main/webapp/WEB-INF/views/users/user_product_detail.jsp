<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/user_product_detail.css">
<script>
	$(function() {
		$("#product_basket").on("click", function() {
			$.ajax({
				url : "${rootPath}/user/product/basket",
				type : "POST",
				data : {
					id : "${PRODUCT_VO.id}",
					"${_csrf.parameterName}" : "${_csrf.token}"
					},
				success : function(success) {
					alert(success)
				},
				error : function(error) {
					alert("통신 오류")
				}
			})
		})
	})
</script>
<body>
	<section class="product_box">
		<article class="title">
			<p>상품정보</p>
		</article>
		
		<section class="product_flex_box">
			<article id="product_image_box">
				이미지
			</article>
			
			<article id="product_info_box">
				<section class="product_name_box">
					<span class="product_name">${PRODUCT_VO.p_name}</span>
				</section>
				
				<section class="product_price_box">
					<span class="product_price">${PRODUCT_VO.p_oprice}원</span>
				</section>
				
				<section class="product_detail_box">
					<span class="product_detail">${PRODUCT_VO.p_detail}</span>
				</section>
			</article>
		</section>
		
		<article class="button_box">
			<button class="product_button" id="product_basket" type="button">장바구니</button>
			<button class="product_button" id="product_buy" type="button">구매</button>
		</article>
		
	</section>
</body>
</html>