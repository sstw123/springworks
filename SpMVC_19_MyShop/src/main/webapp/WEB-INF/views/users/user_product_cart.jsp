<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
	.cart_item {
		border-bottom : 1px solid green;
		background-color: #ddd;
	}
	
	.cart_list_check {
		width: 50px;
		height: 50px;
	}
</style>
<script>
	$(function() {
		let check = 1
		
		$(".btn_change_qty").on("click", function() {
			let id = $(this).data("id")
			let qty = $("#qty_" + id).val()
			
			// alert("수량 : " + qty)
			
			$.ajax({
				url : "${rootPath}/user/product/qty_update",
				type : "GET",
				data : {
					seq : id,
					p_qty : qty
				},
				success : function(result) {
					if(parseInt(result) > 0) {
						alert("수량을 변경했습니다")
					} else {
						alert("수량 변경에 실패했습니다")
					}
				},
				error : function() {
					alert("서버 통신 오류")
				}
			})
		})
		
		$(".btn_delete").on("click", function() {
			let id = $(this).data("id")
			if(confirm("상품을 삭제할까요?")) {
				document.location.replace("${rootPath}/user/product/cart_delete/" + id)
			}
		})
		
		$(".btn_list_delete").on("click", function() {
			// js에서 비어있는 배열을 생성하는 코드
			let cart_array = Array()
			
			// 본문의 cart_list_check 클래스를 전부 선택한다. 한 개 이상일 경우 자동으로 배열이 된다.
			let checkList = $(".cart_list_check")
			
			// 배열을 0부터 끝까지 checked 되어있는 checkbox만 찾아서
			// value 값을 cart_array에 추가한다
			let index = 0//cart_array의 인덱스
			for(i = 0; i < checkList.length; i++) {
				if(checkList[i].checked) {
					cart_array[index++] = checkList[i].value
				}
			}
			//alert(cart_array)
			if(confirm("선택된 상품을 삭제할까요?")) {
				$.ajax({
					url : "${rootPath}/user/product/cart_list_delete",
					type : "POST",
				    //traditional : true,
					data : {
						"${_csrf.parameterName}" : "${_csrf.token}",
						deleteArray : cart_array
					},
					success : function(result) {
						if(result > 0) {
							alert("삭제 성공")
							document.location.replace(document.location.href)
						} else {
							alert("삭제 실패")
						}
					},
					error : function() {
						alert("서버 통신 오류")
					}
				})
			}
		})
		
		
		$(".btn_change_list_qty").on("click", function() {
			if(confirm("전체 수량을 변경합니다")) {
				$("form#cart_form").submit()
			}
		})
		
		$(".btn_checkall").on("click", function() {
			
			if(check == 1) {
				$(".cart_list_check").prop("checked", true)
				$(this).text("전체해제")
			} else {
				$(".cart_list_check").prop("checked", false)
				$(this).text("전체선택")
			}
			check *= -1
			
		})
		
		$(".btn_order").on("click", function() {
			let cart_array = Array()
			
			let checkList = $(".cart_list_check")
			
			let index = 0//cart_array의 인덱스
			for(i = 0; i < checkList.length; i++) {
				if(checkList[i].checked) {
					cart_array[index++] = checkList[i].value
				}
			}
			
			if(cart_array.length < 1) {
				alert("선택된 상품이 없습니다.\n상품을 선택한 후 주문버튼을 클릭하세요")
				return false
			}
			
			if(confirm(cart_array.length + "개의 상품이 선택되었습니다.\n이대로 주문할까요?")) {
				$.ajax({
					url : "${rootPath}/user/product/cart_order",
					type : "POST",
				    //traditional : true,
					data : {
						"${_csrf.parameterName}" : "${_csrf.token}",
						orderList : cart_array
					},
					success : function(result) {
						if(result > 0) {
							document.location.replace("${rootPath}/user/product/delivery_list")
						} else {
							alert("주문 실패")
						}
					},
					error : function() {
						alert("서버 통신 오류")
					}
				})
			}
			
		})
		
	})
</script>
<c:choose>
	<c:when test="${empty CART_LIST}">
		<section>
			<p>장바구니가 비어있습니다</p>
		</section>
	</c:when>
	<c:when test="${!empty CART_LIST}">
		<form id="cart_form" method="POST" action="${rootPath}/user/product/cart_change_list_qty">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<c:forEach items="${CART_LIST}" var="CART">
				<section class="cart_item" data-id="${CART.seq}">
					<div class="col-9">
						<p>상품이름 : ${CART.p_name}</p>
						<p>상품가격 : ${CART.p_oprice}</p>
						<label>수량 : </label>
						
						<input type="number" name="p_qty" value="${CART.p_qty}" id="qty_${CART.seq}">
						<input type="hidden" name="seq" value="${CART.seq}">
						
						<button class="btn_change_qty" data-id="${CART.seq}">수량변경</button>
						<span class="btn_delete" data-id="${CART.seq}">&times;</span>
						<input type="checkbox" class="cart_list_check" value="${CART.seq}">
					</div>
				</section>
			</c:forEach>
		</form>
	</c:when>
</c:choose>
<section>
	<button class="btn_checkall">전체선택</button>
	<button class="btn_order">주문하기</button>
	<button class="btn_change_list_qty">수량변경</button>
	<button class="btn_list_delete">삭제</button>
</section>