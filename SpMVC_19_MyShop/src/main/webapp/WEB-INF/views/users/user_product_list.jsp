<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="rootPath" value="${pageContext.request.contextPath}" />
<link href="${rootPath}/resources/css/user_product_list.css" rel="stylesheet">
<script>
	$(function() {
		$(".product_item_box").on("click", function() {
			document.location.href = "${rootPath}/user/product/detail?id=" + $(this).data("id")
		})
	})
</script>
<section class="product_list">
	<c:choose>
		<c:when test="${empty PRODUCT_LIST}">
			<article class="product_no_item">
				<p>상품 정보가 없습니다</p>
			</article>
		</c:when>
		<c:otherwise>
			<c:forEach var="PRO" items="${PRODUCT_LIST}" varStatus="i">
				<article class="product_item_box" data-id="${PRO.id}">
					<div class="product_item_image_box">
						이미지
					</div>
					
					<div class="product_item_info_box">
						<div>
							<span class="item_name">${PRO.p_name}</span>
						</div>
						
						<div>
							<span class="item_oprice">${PRO.p_oprice}원</span>
						</div>
					</div>
				</article>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</section>