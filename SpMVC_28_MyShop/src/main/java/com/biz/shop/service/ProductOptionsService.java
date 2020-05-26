package com.biz.shop.service;

import java.util.List;

import com.biz.shop.domain.ProductColorVO;
import com.biz.shop.domain.ProductOptionsVO;
import com.biz.shop.domain.ProductSizeVO;

public interface ProductOptionsService {
	
	public List<ProductOptionsVO> getColorList();
	public List<ProductOptionsVO> getSizeList();
	
	// tbl_product 테이블에 상품코드와 사이즈가 같은 튜플이 이미 등록되어있는지 판단하기 위해
	// 이미 저장되어 있는지 확인하는 메소드
	public int countProductSize (ProductSizeVO proSizeVO);
	
	public int insert_size(ProductSizeVO proSizeVO);
	public int insert_color(ProductColorVO proColorVO);
	public int delete_size(ProductSizeVO proSizeVO);

}
