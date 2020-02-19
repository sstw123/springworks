<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="${rootPath}/resources/css/user_product_detail.css">
<script>
	$("#product_basket").on("click", function() {
		$.ajax
			url : "${rootPath}/user/product/basket",
			data : {id : $(this).data("id")},
			
		})
	})
</script>
<body>
	<section class="product_box">
		<article class="title">
			<p>상품정보</p>
		</article>
			
		<article id="product_info">
			<p class="product_name">${PRODUCT_VO.p_name}</p>
			<p>${PRODUCT_VO.p_oprice}원</p>
			<p>${PRODUCT_VO.p_detail}</p>
		</article>
		
		<article class="button_box">
			<button class="product_button" id="product_basket" data-id="${PRODUCT_VO.id}" type="button">장바구니</button>
			<button class="product_button" id="product_buy" data-id="${PRODUCT_VO.id}" type="button">구입</button>
		</article>
		
	</section>
</body>
</html>