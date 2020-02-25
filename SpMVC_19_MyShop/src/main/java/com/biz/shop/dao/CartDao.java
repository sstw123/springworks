package com.biz.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.biz.shop.domain.CartVO;

public interface CartDao {
	
	// 장바구니 보기 SELECT
	@Select("SELECT C.seq, C.p_code, P.p_name, C.username, C.p_oprice, C.p_qty "
			+ "FROM tbl_cart C "
			+ "LEFT JOIN tbl_product P "
			+ "ON C.p_code = P.p_code "
			+ "WHERE username = #{username} AND p_status = 'CART'")
	public List<CartVO> selectCart(@Param("username") String username);
	
	// 주문 완료 후 배송중 SELECT
	@Select("SELECT * FROM tbl_cart WHERE username = #{username} AND p_status = 'DELIVERY'")
	public List<CartVO> selectDelivery(@Param("username") String username);
	
	
	// 관리자가 카트에 몇 건이나 담겨있는지 조회할 때
	@Select("SELECT count(*) FROM tbl_cart WHERE p_status = 'CART'")
	public int countCart();
	
	// 관리자가 현재 배송중인 상품이 몇 건이나 되는지 조회할 때
	@Select("SELECT count(*) FROM tbl_cart WHERE p_status = 'DELIVERY'")
	public int countDelevery();
	
	// 장바구니 수량 UPDATE
	@Update("UPDATE tbl_cart SET p_qty = #{p_qty} WHERE seq = #{seq}")
	public int qty_update(@Param("seq")long seq, @Param("p_qty")int p_qty);

	public int cart_list_delete(String[] deleteArray);

	public int cart_order(String[] orderList);

}
