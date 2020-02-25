<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/user_product_detail.css">
<script>
	$(function() {
		$("#product_cart").on("click", function() {
			let p_qty = parseInt($("#p_qty").val())
			
			if(p_qty <= 0) {
				alert("수량은 최소 1개 이상이어야 합니다")
				return false
			}
			
			// document.location.href = "${rootPath}/user/product/cart?p_code=${PRODUCT_VO.p_code}&p_oprice=${PRODUCT_VO.p_oprice}&p_qty=" + p_qty
			
			$.ajax({
				url : "${rootPath}/user/product/cart",
				type : "POST",
				data : {
					p_code : "${PRODUCT_VO.p_code}",
					p_oprice : "${PRODUCT_VO.p_oprice}",
					p_qty : p_qty,
					"${_csrf.parameterName}" : "${_csrf.token}"
				},
				success : function(result) {
					if(result == false) {
						alert("먼저 로그인해야 합니다")
						return false
					} else {
						if(confirm("상품을 장바구니에 담았습니다.\n장바구니로 이동할까요?")) {
							document.location.href = "${rootPath}/user/product/cart"
						}
					}
				},
				error : function(error) {
					alert("서버 통신 오류")
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
					<span class="product_price"><fmt:formatNumber value="${PRODUCT_VO.p_oprice}" type="currency"/></span>
				</section>
				
				<section class="product_detail_box">
					<span class="product_detail">${PRODUCT_VO.p_detail}</span>
				</section>
				
				<section class="product_price_box">
					<input type="number" id="p_qty" name="p_qty" value="1">
				</section>
			</article>
		</section>
		
		<article class="button_box">
			<button class="product_button" id="product_cart" type="button">장바구니</button>
			<button class="product_button" id="product_buy" type="button">구매</button>
		</article>
		
	</section>
</body>
</html>