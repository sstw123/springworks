package com.biz.shop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.shop.dao.CartDao;
import com.biz.shop.domain.CartListVO;
import com.biz.shop.domain.CartVO;
import com.biz.shop.persistence.CartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CartService {
	
	private final CartDao cartDao;
	private final CartRepository cartRepo;
	
	public List<CartVO> selectCart(String username) {
		return cartDao.selectCart(username);
	}
	
	public List<CartVO> selectDelivery(String username) {
		return cartDao.selectDelivery(username);
	}
	
	public int countCart() {
		return cartDao.countCart();
	}
	
	public int countDelevery() {
		return cartDao.countDelevery();
	}

	public void save(CartVO cartVO) {
		cartVO.setP_status("CART");
		
		cartRepo.save(cartVO);
	}
	
	public int qty_update(long seq, int p_qty) {
		return cartDao.qty_update(seq, p_qty);
	}
	
	public void cart_delete(long seq) {
		cartRepo.deleteById(seq);
	}

	public int cart_list_delete(String[] deleteArray) {
		return cartDao.cart_list_delete(deleteArray);
	}

	public void qty_list_update(CartListVO cartListVO) {
		
		int nSize = cartListVO.getP_qty().size();
		for(int i = 0; i < nSize ; i++) {
			cartDao.qty_update(cartListVO.getSeq().get(i), cartListVO.getP_qty().get(i));
			
		};
		
	}

	public Integer cart_order(String[] orderList) {
		return cartDao.cart_order(orderList);
	}

}
