package com.biz.shop.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.biz.shop.domain.ProductVO;

// 상품관리 CRUD 수행할 Service
public interface ProductService {
	
public List<ProductVO> selectAll();
	
	public ProductVO findByPCode(String p_code);
	public List<ProductVO> findByPName(String p_name);
	
	public int save(ProductVO productVO, MultipartFile file);
	public int delete(long p_code);
	
}
